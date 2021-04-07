package Business;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class Employee {
    final int IDLENGTH = 9;//magic number
    private String firstName;
    private String lastName;
    private String id;
    private String backAccountNumber;
    private int salary;
    private String empConditions;
    private Date startWorkingDate;
    private List<TypeOfEmployee> skills;
    private List<Pair<Date,TypeOfShift>> availableShifts;



    //Constructor---------------------------------------------------------------------------------------------------------------------------
    public Employee(String firstName, String lastName, String id, String bankAccountNumber, int salary, String empConditions, Date startWorkingDate, List<TypeOfEmployee> skills) throws Exception
    {
        validityCheckEmp(firstName, lastName,id,bankAccountNumber,salary,empConditions,startWorkingDate, skills);//Checks validity and throws exception if found invalid field
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.backAccountNumber = bankAccountNumber;
        this.salary = salary;
        this.empConditions = empConditions;
        this.startWorkingDate = startWorkingDate;
        this.skills=skills;
        this.availableShifts = new LinkedList<>();
    }

     //methods----------------------------------------------------------------------------------------------------------------------
     //https://stackoverflow.com/questions/46326822/java-regex-first-name-validation
     private  boolean nameValidation(String name){

         return name!=null && name.matches("(?i)(^[a-z]+)[a-z .,-]((?! .,-)$){1,25}$");
     }

    private boolean isValidId(String id)
    {

        if (id==null ||id.length()!=IDLENGTH)
        {
            return  false;
        }
        for (int i=0;i<id.length();i++)
        {
            if (id.charAt(i)<'0' || id.charAt(i)>'9')
            {
                return false;
            }

        }
        return true;
    }


    private void validityCheckEmp(String firstName, String lastName, String id, String bankAccountNumber, int salary, String empConditions, Date startWorkingDate, List<TypeOfEmployee> skills) throws Exception
    {
        // maybe add valid check for salary , bankaccount, start working date
        if(!nameValidation(firstName))
        {
            throw new Exception("first name is not valid");
        }
        if(!nameValidation(lastName))
        {
            throw new Exception("last name is not valid");
        }
        if(!isValidId(id))
        {
            throw new Exception("invalid id");
        }
        if (skills==null || skills.size()==0)
        {
            throw  new Exception("employee must have skills");
        }

        if (salary<0)
        {
            throw new Exception("Salary must be greater than 0");

        }
        if(bankAccountNumber==null || bankAccountNumber.length()<=0)
        {
            throw new Exception("Bank account is empty");
        }
        if(empConditions== null) //can be empty
        {
            throw new Exception("Employee conditions cant be null");

        }
        if(startWorkingDate==null)
        {
            throw new  Exception("Date was not inserted");
        }
    }
    //-----------------------------------------------------------------getters----------------------------------------------------------

    public Date getStartWorkingDate() {
        return startWorkingDate;
    }

    public int getSalary() {
        return salary;
    }

    public List<TypeOfEmployee> getSkills() {
        return skills;
    }

    public String getBackAccountNumber() {
        return backAccountNumber;
    }

    public List<Pair<Date, TypeOfShift>> getAvailableShifts() {
        return availableShifts;
    }

    public String getEmpConditions() {
        return empConditions;
    }

    public String getFirstName() {

        return firstName;
    }

    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }
    //------------------------------------------------------------setters----------------------------------------------------------------------------------------------

    public void setAvailableShifts(List<Pair<Date, TypeOfShift>> availableShifts) throws Exception{
        if(availableShifts==null)
            throw new Exception("available shifts cant be null");
        this.availableShifts = availableShifts;
    }

    public void setBackAccountNumber(String bankAccountNumber)throws Exception {
        if(bankAccountNumber==null || bankAccountNumber.length()<=0)
        {
            throw new Exception("Bank account is empty");
        }
        this.backAccountNumber = backAccountNumber;
    }

    public void setEmpConditions(String empConditions) throws Exception {
        if(empConditions== null) //can be empty
        {
            throw new Exception("Employee conditions cant be null");

        }
        this.empConditions = empConditions;
    }

    public void setFirstName(String firstName) {
        nameValidation(firstName);
        this.firstName = firstName;
    }

    public void setId(String id)throws Exception
    {
        if(!isValidId(id))
        {
            throw new Exception("Invalid id");
        }
        this.id = id;
    }

    public void setLastName(String lastName) {
        nameValidation(lastName);
        this.lastName = lastName;
    }

    public void setSalary(int salary) throws Exception{
        if (salary<0)
        {
            throw new Exception("Salary must be greater than 0");

        }
        this.salary = salary;
    }

    public void setSkills(List<TypeOfEmployee> skills) throws Exception{
        if (skills==null || skills.size()==0)
        {
            throw  new Exception("employee must have skills");
        }
        this.skills = skills;
    }

    public void setStartWorkingDate(Date startWorkingDate)throws Exception {
        if(startWorkingDate==null)
        {
            throw new  Exception("Date was not inserted");
        }
        this.startWorkingDate = startWorkingDate;
    }

    public void addSkill(TypeOfEmployee type) throws Exception
    {
        if(this.skills.contains(type))
        {
            throw new Exception("Skill already exists");
        }
        this.skills.add(type);
    }

    public void removeSkill(TypeOfEmployee type) throws  Exception {
        if (this.skills.size()<=1)
        {
            throw new Exception("sole skill cant be removed");
        }
        if(!this.skills.contains(type))
        {
            throw new Exception("Skill doesn't exists");
        }
        this.skills.remove(type);

    }

    public void addAvailableShift(Pair<Date, TypeOfShift> shift) throws Exception{
        Date date = shift.first;
        long m = System.currentTimeMillis();
        if (date.before(new Date(m)))
        {
            throw new  Exception("date of available shift cant be in the past");
        }
        if (this.availableShifts.contains(shift))
        {
            throw new  Exception("available shift already exist");
        }
        this.availableShifts.add(shift);
    }

    public void removeAvailableShift(Pair<Date, TypeOfShift> shift) throws Exception {
        if (!this.availableShifts.contains(shift))
        {
            throw new Exception("available shift doesn't exist");
        }
        this.availableShifts.remove(shift);
    }
}
