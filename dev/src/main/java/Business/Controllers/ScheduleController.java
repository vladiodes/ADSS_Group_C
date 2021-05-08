package Business.Controllers;


import Business.Objects.Employee;
import Data.DAO.ShiftDAO;
import Data.DTO.ShiftDTO;
import Misc.Pair;
import Misc.TypeOfEmployee;
import Misc.TypeOfShift;
import Business.Objects.DailySchedule;
import Business.Objects.Shift;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import static Misc.TypeOfEmployee.*;
public class ScheduleController {

    private int shiftId=0;
    //========================================================Fields====================================================
    private TypeOfEmployee typeOfLoggedIn;
    private Map<Date, DailySchedule> schedule;
    private StaffController staffController;
    private ShiftDAO shiftDAO;

    //========================================================Constructor====================================================

    public ScheduleController(TypeOfEmployee type, StaffController sc)
    {
        this.staffController = sc;
        this.typeOfLoggedIn =type;
        this.schedule = new HashMap<>();
        this.shiftDAO = new ShiftDAO();
    }
    //========================================================Methods====================================================


    /**
     * Creates and adds a new shift to the and daily schedule and adds the new daily schedule to the schedule
     * Only an HRManager can add shifts
     * @param date
     * @param type
     * @return Success/Fail message
     */

    public String addShift(Date date, TypeOfShift type)
    {
        if(this.typeOfLoggedIn!=HRManager)//Only HRManager can add shifts
        {
            return "Only HRManager can add shifts";
        }
        if(isShiftExists(date, type))
        {
            return "Shift already exists";
        }
        try
        {
            Shift toAddShift = new Shift(shiftId, type, date);

            if(!schedule.containsKey(date))
            {
                DailySchedule dailySchedule = new DailySchedule(toAddShift);
                this.schedule.put(date,dailySchedule);
            }
            else
            {
                schedule.get(date).addShift(toAddShift);
            }
            this.shiftDAO.insert(toAddShift.toDTO());
        }
        catch(Exception e)
        {
            return e.getMessage();
        }
        shiftId++;// ----------------- AI -----------------------------
        return "Shift added successfully";
    }

    private boolean isShiftExists(Date date, TypeOfShift type) {
        if(!schedule.containsKey(date))
        {
            return false;
        }
        DailySchedule cur = schedule.get(date);
        return cur.isTypeOfShiftExists(type);

    }

    /**
     * Removes the shift at date "date" and of type "type" from the schedule
     * Only a HRManager can remove shifts
     * @param date
     * @param type
     * @return Success/Fail message
     */
    public String removeShift(Date date, TypeOfShift type)
    {

        if(this.typeOfLoggedIn!=HRManager)//Only HRManager can remove shifts
        {
            return "Only HRManager can remove shifts";
        }
        if(!isShiftExists(date,type))
        {
            return "Shift doesn't exist";
        }
        DailySchedule dailySchedule = this.schedule.get(date);
        int shiftIdToRemove=dailySchedule.getShift(type).getID();
        dailySchedule.removeShift(date,type);
        this.shiftDAO.removeShift(shiftIdToRemove);
        return "Shift was removed successfully";
    }


    /**
     * Adds employee with id "id" to the shift with date "date" and of type "type"
     * @param id
     * @param toSkill
     * @param date
     * @param type
     * @return Success/Fail message
     */
    public String addEmployeeToShift(String id,TypeOfEmployee toSkill, Date date, TypeOfShift type)
    {
        if(!isShiftExists(date, type)) //Cant add an employee to a shift that doesn't exist
        {
            return "Shift doesn't exist";
        }
        try
        {
            Shift s= getShift(date,type);
            Employee toAdd = staffController.getEmployeeByID(id);
            s.addEmployeeToShift(toAdd, toSkill);
            if (this.staffController.getEmployeeByID(id).getSkills().contains(Driver))
            {
                this.shiftDAO.addDriverToShift(id,s.getID(),toSkill.toString());
            }
            else
            {
                this.shiftDAO.addEmployeeToShift(id,s.getID(),toSkill.toString());
            }

        }
        catch(Exception e)
        {
            return  e.getMessage();
        }

        return "Employee added successfully to shift";
    }

    /**
     * Remove employee with id "id" from shift with date "date" and of type "type"
      * @param id
     * @param date
     * @param type
     * @return Success/Fail message
     */
    public String removeEmployeeFromShift(String id,Date date, TypeOfShift type)
    {
        if(!isShiftExists(date,type))
        {
            return "Shift doesn't exist";
        }
        Shift s= getShift(date,type);
        if(!s.isEmployeeInShift(id)) //Check if the employee is in the shift
        {
            return "Shift doesn't contain this employee";
        }

        if (!s.removeEmployee(id))
        {
            return "Employee was not removed from shift";
        }
        TypeOfEmployee typeOfEmp = s.getTypeOfSpecificEmployee(id);//already check if shift contain this employee
        if (this.staffController.getEmployeeByID(id).getSkills().contains(Driver))
        {
            this.shiftDAO.removeEmployeeFromShift(id,s.getID(),typeOfEmp.toString());
        }
        else
        {
            this.shiftDAO.removeDriverFromShift(id,s.getID(),typeOfEmp.toString());
        }

        return "Employee removed successfully from shift";
    }

    /**
     * Adds a constraint/Edits an existing constraint to a specific shift
     * Only HRManager can add constraints
     * @param date
     * @param typeOfShift
     * @param typeOfEmployee
     * @param numOfEmp
     * @return Success/Fail message
     */
    public String addConstraint(Date date, TypeOfShift typeOfShift, TypeOfEmployee typeOfEmployee, Integer numOfEmp) {
        if (typeOfLoggedIn!= HRManager)
        {
            return "Only a HR Manager is allowed to modify number and type of employees in a shift";
        }
        try
        {
            if (!this.schedule.containsKey(date))
            {
                return "No such shift";
            }
            DailySchedule dailySchedule=this.schedule.get(date);
            if (!dailySchedule.isTypeOfShiftExists(typeOfShift))
            {
                return "No such shift";
            }
            Shift shift = dailySchedule.getShift(typeOfShift);
            shift.addConstraint(typeOfEmployee, numOfEmp);
////////////////////////////
            this.shiftDAO.removeConstraints(shift.getID(), typeOfEmployee.toString());
            this.shiftDAO.addConstraints(shift.getID(), typeOfEmployee.toString(), numOfEmp);
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
        return "Constraint added successfully";
    }

    /**
     * Removes a constraint from a specific shift
     * @param date
     * @param typeOfShift
     * @param typeOfEmployee
     * @return Success/Fail message
     */
    public String removeConstraint(Date date, TypeOfShift typeOfShift, TypeOfEmployee typeOfEmployee)
    {
        if (typeOfLoggedIn!= HRManager)
        {
            return "only a HR Manager is allowed to modify number and type of employees in a shift";
        }
        try
        {
            if (!this.schedule.containsKey(date))
            {
                return "no such shift";
            }
            DailySchedule dailySchedule=this.schedule.get(date);
            if (!dailySchedule.isTypeOfShiftExists(typeOfShift))
            {
                return "no such shift";
            }
            Shift shift = dailySchedule.getShift(typeOfShift);
            shift.removeConstraint(typeOfEmployee);
            this.shiftDAO.removeConstraints(shift.getID(), typeOfEmployee.toString());
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
        return "constraint removed successfully";
    }

    /**
     * Checks if the requested shift contains the specific employee identified by it's ID
     * @param id
     * @param date
     * @param type
     * @return true is the shift requested contains the specific employee, false if not contains
     */
    public boolean shiftContainsEmployee(String id,Date date,TypeOfShift type )
    {
        return this.getShift(date, type).isEmployeeInShift(id);
    }

    /**
     * Checks if the requested shift contains an employee that is assigned to the skill "empType"
     * @param empType
     * @param date
     * @param shiftType
     * @return true if contains, false if not contains
     */
    public boolean shiftContainsTypeOfEmployee(TypeOfEmployee empType, Date date, TypeOfShift shiftType)
    {
        return this.getShift(date, shiftType).isTypeEmployeeInShift(empType);
    }

    @Override
    public String toString()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder builder=new StringBuilder();

        for(Date d:schedule.keySet())
        {
            builder.append("\nDate Of Daily Schedule: " + dateFormat.format(d));
            builder.append("\n"+ schedule.get(d).toString(this.staffController));
        }
        builder.append("\n");
        return builder.toString();
    }

    //-----------------------------------------------------getters-----------------------------------------------

    public TypeOfEmployee getTypeOfLoggedIn() {
        return typeOfLoggedIn;
    }

    public StaffController getStaffController() {
        return staffController;
    }

    public Map<Date,DailySchedule> getSchedule()
    {
        return this.schedule;
    }

    public Shift getShift(Date date,TypeOfShift type)
    {
        DailySchedule ds = schedule.get(date);
        Shift s = ds.getShift(type);
        return s;
    }
    //---------------------------------------------------setters--------------------------------------------------
    public void setTypeOfLoggedIn(TypeOfEmployee typeOfLoggedIn) {
        this.typeOfLoggedIn = typeOfLoggedIn;
    }

    public void setSchedule(Map<Date, DailySchedule> schedule) {
        this.schedule = schedule;
    }

    public void setStaffController(StaffController staffController) {
        this.staffController = staffController;
    }


    public List<Shift> getShiftWithEmp(String id) {
        List<Shift> toReturn = new LinkedList<>();

        for (Date d:schedule.keySet()) {
            DailySchedule daily = schedule.get(d);
            for (Shift s:daily.getShifts()) {
                if (s.isEmployeeInShift(id))
                {
                    toReturn.add(s);
                }
            }

        }
        return toReturn;
    }
    /*
     private int shiftId=0;
    //========================================================Fields====================================================
    private TypeOfEmployee typeOfLoggedIn;
    private Map<Date, DailySchedule> schedule;
    private StaffController staffController;
    private ShiftDAO shiftDAO = new ShiftDAO();
     */
    public void getAllShifts()
    {


        Map<Date, List<Shift>> shiftsBus = new HashMap<>();
        List<ShiftDTO> allShiftsDTO = this.shiftDAO.getAll();
        this.restoreMaxShiftID(allShiftsDTO);
        for(ShiftDTO s : allShiftsDTO) //Getting shifts from DB
        {
            if(!shiftsBus.containsKey(s.date))
            {
                shiftsBus.put(s.date,new LinkedList<Shift>());
            }
            shiftsBus.get(s.date).add(new Shift(s));
        }
       for(Date d : shiftsBus.keySet())
       {
           List<Shift> toAdd = shiftsBus.get(d);
           DailySchedule ds = new DailySchedule(toAdd);
           this.schedule.put(d,ds);
       }
    }

    private void restoreMaxShiftID(List<ShiftDTO> allShift)
    {
        int max=0;
        int currId=0;
        for (ShiftDTO s:allShift)
        {
           currId=s.shiftId;
           if (currId>max)
           {
               max=currId;
           }
        }
        this.shiftId = max+1;
    }

    public void incNumOfConstraint(Date date, TypeOfShift type, TypeOfEmployee empType) throws Exception {
        Shift s = getShift(date, type);
        Map<TypeOfEmployee, Integer> cons = s.getConstraints();
        for(TypeOfEmployee currType:cons.keySet())
        {
            if (empType==currType)
            {
                int curr =cons.get(currType);
                s.addConstraint(empType,curr+1);
            }
        }
    }
}
