package Business.Objects;


import Business.Controllers.StaffController;
import Data.DTO.ShiftDTO;
import Misc.Pair;
import Misc.TypeOfEmployee;
import Misc.TypeOfShift;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static Misc.TypeOfEmployee.*;


public class Shift implements persistentObject {

    //==================================================================Fields==================================================================
    private int id;
    private TypeOfShift type;
    private Date date;
    private Map<TypeOfEmployee, Integer> constraints;
    private List<Pair<String,TypeOfEmployee>> currentShiftEmployees;
    private boolean isSealed;


    //==================================================================Constructor==============================================================
    public Shift(int id,TypeOfShift type, Date date) throws Exception
    {

        shiftValidityCheck(date);
        this.id=id;
        this.type = type;
        this.date = date;
        this.currentShiftEmployees = new LinkedList<>();
        this.constraints = new HashMap<>(); //init
        this.constraints.put(ShiftManager, 1); //Default constraint
        this.isSealed = false;
    }
    public Shift(ShiftDTO shift)
    {
        this.id=shift.shiftId;
        this.type = TypeOfShift.valueOf(shift.type);
        this.date = shift.date;
        this.currentShiftEmployees = currShiftEmpDTOToBuss(shift.currentShiftEmployees);
        this.constraints = constraintDTOToBuss(shift.constraints);
        this.isSealed = shift.isSealed;
    }

    private Map<TypeOfEmployee, Integer> constraintDTOToBuss(Map<String, Integer> constraints) {
        Map<TypeOfEmployee, Integer> toReturn = new HashMap<>();
        for (String type:constraints.keySet())
        {
            toReturn.put(TypeOfEmployee.valueOf(type), constraints.get(type));
        }
        return toReturn;
    }

    private List<Pair<String, TypeOfEmployee>> currShiftEmpDTOToBuss(List<Pair<String, String>> currentShiftEmployees) {
        List<Pair<String, TypeOfEmployee>> toReturn = new LinkedList<>();
        for (Pair<String, String> p:currentShiftEmployees)
        {
            toReturn.add(new Pair<String, TypeOfEmployee>(p.first,TypeOfEmployee.valueOf(p.second)));

        }
        return toReturn;
    }


    //==================================================================Methods==================================================================

    /**
     * Checks validity of the given "date"
     * @param date
     * @throws Exception
     */
    private void shiftValidityCheck (Date date) throws Exception
    {

        if (date == null)
        {
            throw new Exception("Date can't be null");
        }
        long m = System.currentTimeMillis();
        if (date.before(new Date(m)))
        {
            throw new  Exception("Date of available shift cant be in the past");
        }

    }

    /**
     * Add given employee for the role of "type" to the shift
     * @param toAdd
     * @param type
     * @throws Exception
     */
    public void addEmployeeToShift(Employee toAdd, TypeOfEmployee type) throws Exception
    {
        if(toAdd==null)
        {
            throw new Exception("employee can not be null");
        }
        if (!toAdd.getSkills().contains((type)))
        {
            throw new Exception("employee cant be assigned to a skill he doesnt have");
        }
        if (!checkConstraints(type))
        {
            throw new Exception("employee cant be assigned to a skill he doesnt have");
        }
        currentShiftEmployees.add((new Pair<String, TypeOfEmployee>(toAdd.getId(),type)));
        isSealed=sealShift();


    }

    /**
     * Check numbers of employees in the shift doesn't exceed the requested number of employees
     * @param type
     * @return
     * @throws Exception
     */
    private boolean checkConstraints( TypeOfEmployee type) throws  Exception
    {
        if(this.constraints.containsKey(type))
        {
            Integer numOfType = this.constraints.get(type);
            if (getNumberOfCurrType(type) >= numOfType) {
                throw new Exception(("number of employees of this type is exceeded"));
            }
        }
        return true;

    }
    private int getNumberOfCurrType(TypeOfEmployee type) {
        int ans=0;
        for (Pair p :currentShiftEmployees) {
            if (p.second==type)
            {
                ans++;
            }
        }
        return  ans;
    }

    /**
     * Compares each amount of employees of each type with requested amount in the shift
     * @return
     */
    public boolean checkFull()
    {
        Map <TypeOfEmployee, Integer> numOfEmp = new HashMap<>(); //Counts the number of employees of each type in the current shift
        for (Pair pair:currentShiftEmployees)
        {
            TypeOfEmployee typeOfCurrEmp=(TypeOfEmployee) pair.second; //Type of the current employee
            if (!numOfEmp.containsKey(typeOfCurrEmp)) //If current type was yet to be found
            {
                numOfEmp.put(typeOfCurrEmp,1);//Insert new element with value of 1
            }
            else //Increment number of types found
            {
                numOfEmp.put(typeOfCurrEmp, numOfEmp.get(typeOfCurrEmp) + 1);
            }

        }
        for (TypeOfEmployee type: constraints.keySet())
        {
            if (constraints.get(type)!=numOfEmp.get(type))
            {
                return  false; //Found a type of employee in the constraints that isnt satisfied or exceeded maximum value
            }

        }
        return  true;
    }

    public boolean isEmployeeInShift(String id) {
        for (Pair p: currentShiftEmployees)
        {
            Employee currEmp = (Employee)p.first;
            if(currEmp.getId().equals(id))
            {
                return true;
            }
        }
        return false;

    }

    public boolean removeEmployee(String id) {
        Pair<String, TypeOfEmployee> toRemove=null;
        for (Pair<String, TypeOfEmployee> p:currentShiftEmployees) {
            {
                String currId = p.first;
                if (currId.equals(id))
                {
                    toRemove=p;
                    break;
                }
            }

        }
        if (!currentShiftEmployees.remove(toRemove))
        {
            return false;
        }
        isSealed=sealShift();
        return true;

    }

    /**
     * Adds a new constraint/Edits a value of an existing constraint
     * @param typeOfEmployee
     * @param numOfEmp
     * @throws Exception
     */
    public void addConstraint(TypeOfEmployee typeOfEmployee, Integer numOfEmp) throws Exception{
        if (numOfEmp<0)
        {
            throw new Exception("Amount of Employees must be positive");
        }
        if(typeOfEmployee == ShiftManager && numOfEmp<=0)
        {
            throw new Exception("Constraint of type ShiftManager must be 1 or greater");
        }
        this.constraints.put(typeOfEmployee, numOfEmp);
        isSealed=sealShift();
    }

    /**
     * Removes a constraint from the shift
     * ShiftManager constraint cant be removed since it is mandatory constraint - a exception will be thrown if type is ShiftManager
     * @param typeOfEmployee
     * @throws Exception
     */
    public void removeConstraint(TypeOfEmployee typeOfEmployee)throws Exception {
        if (typeOfEmployee == ShiftManager)
        {
            throw new Exception("Number of ShiftManagers in a shift must be restricted");
        }
        if (this.constraints.remove(typeOfEmployee)==null)
        {
            throw new Exception("No such restriction");
        }

        isSealed=sealShift();

    }

    private String printStatus()
    {
        if(isSealed)
            return "Sealed";
        return "Open";
    }

    public boolean sealShift() {

        return this.checkFull();
    }

    public String toString(StaffController staffController) {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder builder=new StringBuilder();
        builder.append("Shift: \n");
        builder.append("\t\tType: " + type);
        builder.append("\n\t\tShift Date: " + dateFormat.format(date));
        builder.append("\n\t\tConstraints:");
        for(TypeOfEmployee type:constraints.keySet())
        {
            builder.append("\n\t\t\tType Of Employee: " + type.toString());
            builder.append("\n\t\t\tAmount: " + constraints.get(type).toString());
        }
        builder.append("\n");
        builder.append("\n\t\tCurrent Shift Employees:");

        for(Pair<String, TypeOfEmployee> p:currentShiftEmployees)
        {
            Employee emp = staffController.getEmployeeByID(p.first);
            builder.append("\n\t\t\tName: " + emp.getFirstName() +" "+ emp.getLastName());
            builder.append("\n\t\t\tType: " + p.second.toString());

            List<TypeOfEmployee> currentEmpSkills = emp.getSkills();
            builder.append("\n\t\t\tSkills: \n\t\t\t\t");
            for(TypeOfEmployee type:currentEmpSkills)
            {

                builder.append((type.toString())+ " | ");
            }
            builder.append("\n");
        }
        builder.append("\n\t\tShift status: " + printStatus());
        builder.append("\n");

        return builder.toString();
    }


    //-------------------------------------------------------------------------getters-------------------------------------------------------------------------


    public Date getDate() {
        return date;
    }

    public List<Pair<String, TypeOfEmployee>> getCurrentShiftEmployees() {
        return currentShiftEmployees;
    }

    public Map<TypeOfEmployee, Integer> getConstraints() {
        return constraints;
    }

    public TypeOfShift getType() {
        return type;
    }

    public boolean isSealed()
    {
        return this.isSealed;
    }
    //--------------------------------------------------------------------------------setters---------------------------------------------------------------------


    public void setConstraints(Map<TypeOfEmployee, Integer> constraints) throws Exception
    {
        if(constraints == null)
        {
            throw new Exception("Constraints list is null");
        }
        else
        {
            if((!constraints.containsKey(ShiftManager)))
            {
                throw new Exception("Shift must contain a shift manager constraint");
            }
            else if((constraints.get(ShiftManager)<1))
            {
                throw new Exception("Number of shift managers in a shift must be at least 1");
            }
        }
        this.constraints = constraints;
    }

    public void setCurrentShiftEmployees(List<Pair<String, TypeOfEmployee>> currentShiftEmployees) throws Exception {
        if(currentShiftEmployees == null)
        {
            throw new Exception("currentShiftEmployees list is null");
        }
        this.currentShiftEmployees = currentShiftEmployees;
    }

    public void setDate(Date date) throws Exception
    {
        if (date == null)
        {
            throw new Exception("date can't be null");
        }
        long m = System.currentTimeMillis();
        if (date.before(new Date(m)))
        {
            throw new  Exception("date of available shift cant be in the past");
        }
        this.date = date;
    }

    public void setType(TypeOfShift type) {
        this.type = type;
    }


    public boolean isTypeEmployeeInShift(TypeOfEmployee empType) {

        for (Pair p: currentShiftEmployees)
        {
            Employee currEmp = (Employee)p.first;
            if(currEmp.getSkills().contains(empType))
            {
                return true;
            }
        }
        return false;

    }

    @Override
    public ShiftDTO toDTO() {
        return new ShiftDTO(this.id, this.type.toString(), this.date, this.isSealed ? 1 :0 ,constraintsBussToDTO(this.constraints) , currEmpBusinessToDTO(this.currentShiftEmployees));
    }
    private Map<String, Integer> constraintsBussToDTO(Map<TypeOfEmployee, Integer> constraintsBusiness)
    {
        Map<String, Integer> toReturn = new HashMap<>();
        for(TypeOfEmployee currType : constraintsBusiness.keySet())
        {
            toReturn.put(currType.toString(), constraintsBusiness.get(currType));
        }
        return toReturn;
    }
    private List<Pair<String,String>> currEmpBusinessToDTO (List<Pair<String,TypeOfEmployee>> currEmpBusiness)
    {
        List<Pair<String,String>> toReturn = new LinkedList<>();
        for(Pair<String,TypeOfEmployee> p : currEmpBusiness)
        {
            toReturn.add(new Pair(p.first, p.second.toString()));
        }
        return toReturn;
    }


    public int getID() {
        return this.id;
    }

    public TypeOfEmployee getTypeOfSpecificEmployee(String empID)
    {
        if(this.isEmployeeInShift(empID))
        {
            for(Pair<String,TypeOfEmployee>  p : getCurrentShiftEmployees())
            {
                if (p.first.equals(empID))
                {
                    return p.second;
                }
            }
        }
        return null;
    }
}
