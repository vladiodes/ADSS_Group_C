package Presentation;

import Business.Controllers.TransportsEmployeesFacade;
import Business.Misc.TypeOfEmployee;
import Business.Misc.TypeOfShift;


import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.exit;


public class Menus {
    private final int NUMBER_OF_EMPLOYEE_FIELDS =7; //magic number
    private final int NUMBER_OF_DRIVER_FIELDS = 8; //magic number

    //================================================Fields===========================================================
    private Map menuMain ;
    private int menuMainOption;

    private Map menuEdit ;
    private int menuEditOption;

    private Map AREmployee;
    private int AREmployeeOption;

    private Map ARDriverEmployee;
    private int ARDriverEmployeeOption;

    private Map ARAvailableShift;
    private int ARAvailableShiftOption;

    private Map ARSkills;
    private int ARSkillOption;

    private Map AREmployeeToShift;
    private int AREmployeeToShiftOption;

    private Map ARConstraint ;
    private int ARConstraintOption;

    private Map ARShift ;
    private int ARShiftOption;

    private TransportsEmployeesFacade facade;

    //================================================Constructor===========================================================
    public Menus(TransportsEmployeesFacade facade)
    {
        this.facade =facade;
    }

    //================================================Methods===========================================================


    /**
     * Main Function for running all menus and functions in the program
     */
    public void start()
    {

        Scanner s = new Scanner(System.in);
        initializeAllMenus();
        while(true)
        {
            this.menuMainOption =  printMenu(menuMain);
            switch (this.menuMainOption)
            {
                case(1): //Add/Remove employee
                {
                    this.AREmployeeOption = printMenu(AREmployee);
                    switch (this.AREmployeeOption)
                    {
                        case(1): //Add Employee
                        {
                            String[] employeeFields=new String[NUMBER_OF_EMPLOYEE_FIELDS]; //0-FirstName 1-LastName 2-ID 3-BankAccountNumber 4-Salary 5-empConditions 6-skill
                            //First Name
                            System.out.println("Please Enter First Name");
                            employeeFields[0]=s.nextLine();
                            employeeFields[0]=inValidInputLetters(employeeFields[0]);
                            //Last Name
                            System.out.println("Please Enter Last Name");
                            employeeFields[1]=s.nextLine();
                            employeeFields[1]=inValidInputLetters(employeeFields[1]);

                            //ID
                            System.out.println("Please Enter ID");
                            employeeFields[2]=s.nextLine();
                            employeeFields[2]=inValidInputDigits(employeeFields[2]);

                            //Bank Account Number
                            System.out.println("Please Enter bank Account Number");
                            employeeFields[3]=s.nextLine();
                            while (!checkBankAccountNumber(employeeFields[3])) //Ask for valid input until received
                            {
                                System.out.println("Invalid Input, please enter again");
                                employeeFields[3]=s.nextLine();
                            }

                            //Salary
                            System.out.println("Please Enter salary");
                            employeeFields[4]=s.nextLine();
                            employeeFields[4]=inValidInputDigits(employeeFields[4]);

                            //Employee Conditions
                            System.out.println("Please Enter Employee conditions");
                            employeeFields[5]=s.nextLine();//free text , no need to check

                            //Skill
                            System.out.println("Please Enter 1 skill of the employee");
                            employeeFields[6]=s.nextLine();

                            //---------All input from user is ready-------------

                            List<TypeOfEmployee> l = new LinkedList<TypeOfEmployee>();
                            TypeOfEmployee typeOfEmployee;
                            typeOfEmployee= parseTypeOfEmp(employeeFields[6]);
                            while (typeOfEmployee==null) //Ask for valid input until received
                            {
                                System.out.println("Invalid Input, please enter again");
                                employeeFields[6]=s.nextLine();
                                typeOfEmployee= parseTypeOfEmp(employeeFields[6]);
                            }
                            l.add(typeOfEmployee); //Adding the skill to the new list created for the new employee
                            System.out.println(facade.addEmployee(employeeFields[0], employeeFields[1], employeeFields[2], employeeFields[3], Integer.parseInt(employeeFields[4]), employeeFields[5],new Date(System.currentTimeMillis()),l));

                            break;
                        }
                        case(2): //Remove Employee
                        {
                            System.out.println("Enter ID Of The Employee To Remove");
                            String id=s.nextLine();
                            id=inValidInputDigits(id);
                            System.out.println(facade.RemoveEmployee(id));
                            break;
                        }
                        case(3)://back To main menu
                        {
                            continue;
                        }
                    }
                    break;
                }
                case(2): //Add/Remove driver employee
                {
                    this.AREmployeeOption = printMenu(AREmployee);
                    switch (this.AREmployeeOption)
                    {
                        case(1): //Add Driver Employee
                        {
                            String[] driverFields=new String[NUMBER_OF_DRIVER_FIELDS]; //0-FirstName 1-LastName 2-ID 3-BankAccountNumber 4-Salary 5-empConditions 6-skill
                            //First Name
                            System.out.println("Please Enter First Name");
                            driverFields[0]=s.nextLine();
                            driverFields[0]=inValidInputLetters(driverFields[0]);
                            //Last Name
                            System.out.println("Please Enter Last Name");
                            driverFields[1]=s.nextLine();
                            driverFields[1]=inValidInputLetters(driverFields[1]);

                            //ID
                            System.out.println("Please Enter ID");
                            driverFields[2]=s.nextLine();
                            driverFields[2]=inValidInputDigits(driverFields[2]);

                            //Bank Account Number
                            System.out.println("Please Enter bank Account Number");
                            driverFields[3]=s.nextLine();
                            while (!checkBankAccountNumber(driverFields[3])) //Ask for valid input until received
                            {
                                System.out.println("Invalid Input, please enter again");
                                driverFields[3]=s.nextLine();
                            }

                            //Salary
                            System.out.println("Please Enter salary");
                            driverFields[4]=s.nextLine();
                            driverFields[4]=inValidInputDigits(driverFields[4]);

                            //Employee Conditions
                            System.out.println("Please Enter Employee conditions");
                            driverFields[5]=s.nextLine();//free text , no need to check


                            //License
                            System.out.println("Please Enter License Number");
                            driverFields[7]=s.nextLine();
                            driverFields[7]=inValidInputDigits(driverFields[7]);

                            //---------All input from user is ready-------------

                            List<TypeOfEmployee> skills = new LinkedList<TypeOfEmployee>();
                            skills.add(TypeOfEmployee.Driver); //Adding the skill to the new list created for the new employee
                            System.out.println(facade.addDriverEmployee(driverFields[0], driverFields[1], driverFields[2], driverFields[3], Integer.parseInt(driverFields[4]), driverFields[5],new Date(System.currentTimeMillis()),skills, Integer.parseInt(driverFields[7])));

                            break;
                        }
                        case(2): //Remove Driver Employee
                        {
                            System.out.println("Enter ID Of The Employee To Remove");
                            String id=s.nextLine();
                            id=inValidInputDigits(id);
                            System.out.println(facade.RemoveEmployee(id));
                            break;
                        }
                        case(3)://back To main menu
                        {
                            continue;
                        }
                    }
                    break;
                }
                case(3): //Edit employee details
                {
                    String idToEdit=checkIdExist();
                    this.menuEditOption=printMenu(menuEdit);
                    switch (menuEditOption)
                    {

                        case(1)://Edit First Name
                        {
                            System.out.println("Enter new First Name");
                            String firstName = s.nextLine();
                            firstName= inValidInputLetters(firstName);
                            System.out.println(facade.editFirstName(idToEdit, firstName));
                            break;
                        }
                        case(2)://Edit Last Name
                        {
                            System.out.println("Enter new Last Name");
                            String lastName = s.nextLine();
                            lastName= inValidInputLetters(lastName);
                            System.out.println(facade.editLastName(idToEdit, lastName));
                            break;

                        }
                        case(3)://Edit ID
                        {
                            System.out.println("Enter new ID");
                            String newId = s.nextLine();
                            newId=inValidInputDigits(newId);
                            System.out.println(facade.editID(idToEdit, newId));
                            break;

                        }
                        case(4)://Edit Bank Account Number
                        {
                            System.out.println("Enter new Bank Account Number");
                            String newBankAccountNumber = s.nextLine();
                            while (!checkBankAccountNumber(newBankAccountNumber)) //Ask for valid input until received
                            {
                                System.out.println("Invalid Input, please enter again");
                                newBankAccountNumber=s.nextLine();
                            }
                            System.out.println(facade.editBankAccountNumber(idToEdit, newBankAccountNumber));
                            break;

                        }
                        case(5)://Edit Salary
                        {
                            System.out.println("Enter new Salary");
                            String newSalary = s.nextLine();
                            newSalary=inValidInputDigits(newSalary);
                            System.out.println(facade.editSalary(idToEdit, Integer.parseInt(newSalary)));
                            break;

                        }
                        case(6)://Edit Employee Conditions
                        {
                            System.out.println("Enter new Employee Conditions");
                            String newEmpConditions = s.nextLine();
                            System.out.println(facade.editEmpConditions(idToEdit, newEmpConditions));
                            break;

                        }
                        case(7)://Back To Main Menu
                        {
                            continue;
                        }

                    }
                    break;
                }
                case(4): //Add/Remove available shift
                {
                    String idToEdit=checkIdExist();
                    this.ARAvailableShiftOption=printMenu(ARAvailableShift);
                    switch (ARAvailableShiftOption)
                    {

                        case(1)://Add available shift

                        {
                            Date date = getDateFromUser();
                            System.out.println("Please Enter Type Of Shift To Add");
                            String type = s.nextLine();
                            TypeOfShift typeOfShift;
                            typeOfShift=parseTypeOfShift(type);
                            while(typeOfShift==null) //Ask for valid input until received
                            {
                                System.out.println("Invalid Input, please enter again");
                                type = s.nextLine();
                                typeOfShift=parseTypeOfShift(type);
                            }

                            System.out.println(facade.addAvailableShift(idToEdit, date, typeOfShift));
                            break;
                        }
                        case(2)://Remove available shift
                        {
                            System.out.println("Please Enter Date Of Shift To Remove in format dd/mm/yyyy");
                            String dateOfShift = s.nextLine();
                            boolean validDate=false;
                            Date date=null;
                            while (!validDate)
                            {
                                validDate=true;
                                try
                                {
                                    date = new SimpleDateFormat("dd/MM/yyyy").parse(dateOfShift);
                                }
                                catch (Exception e)
                                {
                                    System.out.println("Invalid Input, please enter again");
                                    dateOfShift = s.nextLine();
                                    validDate=false;
                                }
                            }
                            System.out.println("Please Enter Type Of Shift To Remove");
                            String type = s.nextLine();
                            TypeOfShift typeOfShift;
                            typeOfShift=parseTypeOfShift(type);
                            while(typeOfShift==null) //Ask for valid input until received
                            {
                                System.out.println("Invalid Input, please enter again");
                                type = s.nextLine();
                                typeOfShift=parseTypeOfShift(type);
                            }
                            System.out.println(facade.removeAvailableShift(idToEdit, date, typeOfShift));
                            break;
                        }
                        case(3)://Back to main menu
                        {
                            continue;
                        }
                    }
                    break;
                }
                case(5): //Add/Remove skills
                {
                    String idToEdit=checkIdExist();
                    this.ARSkillOption=printMenu(ARSkills);
                    switch (ARSkillOption)
                    {
                        case(1)://Add skills
                        {
                            System.out.println("Please Enter new skill");
                            String skill = s.nextLine();
                            TypeOfEmployee typeOfEmployee;
                            typeOfEmployee= parseTypeOfEmp(skill);
                            while (typeOfEmployee==null)  //Ask for valid input until received
                            {
                                System.out.println("Invalid Input, please enter again");
                                skill=s.nextLine();
                                typeOfEmployee= parseTypeOfEmp(skill);
                            }
                            System.out.println(facade.addSkill(idToEdit, typeOfEmployee));
                            break;
                        }
                        case(2)://Remove skills
                        {
                            System.out.println("Please Enter skill to remove");
                            String skill = s.nextLine();
                            TypeOfEmployee typeOfEmployee;
                            typeOfEmployee= parseTypeOfEmp(skill);
                            while (typeOfEmployee==null)  //Ask for valid input until received
                            {
                                System.out.println("Invalid Input, please enter again");
                                skill=s.nextLine();
                                typeOfEmployee= parseTypeOfEmp(skill);
                            }
                            System.out.println(facade.removeSkill(idToEdit, typeOfEmployee));
                            break;
                        }
                        case(3)://Back to main menu
                        {
                            continue;
                        }
                    }

                    break;
                }
                case(6): //Add/Remove employee to shift
                {

                    String idToEdit=checkIdExist();
                    this.AREmployeeToShiftOption=printMenu(AREmployeeToShift);
                    switch (AREmployeeToShiftOption)
                    {

                        case(1)://Add Employee to shift
                        {
                            System.out.println("Enter skill of the employee");
                            String skill = s.nextLine();
                            TypeOfEmployee typeOfEmployee;
                            typeOfEmployee= parseTypeOfEmp(skill);
                            while (typeOfEmployee==null)  //Ask for valid input until received
                            {
                                System.out.println("Invalid Input, please enter again");
                                skill=s.nextLine();
                                typeOfEmployee= parseTypeOfEmp(skill);
                            }
                            Date date = getDateFromUser();
                            System.out.println("Enter Type of shift");
                            String type = s.nextLine();
                            TypeOfShift typeOfShift;
                            typeOfShift=parseTypeOfShift(type);
                            while(typeOfShift==null)  //Ask for valid input until received
                            {
                                System.out.println("Invalid Input, please enter again");
                                type = s.nextLine();
                                typeOfShift=parseTypeOfShift(type);
                            }
                            System.out.println(facade.addEmployeeToShift(idToEdit, typeOfEmployee, date, typeOfShift));
                            break;
                        }
                        case(2)://Remove Employee to shift
                        {
                            Date date = getDateFromUser();
                            System.out.println("Enter Type of shift");
                            String type = s.nextLine();
                            TypeOfShift typeOfShift;
                            typeOfShift=parseTypeOfShift(type);
                            while(typeOfShift==null)  //Ask for valid input until received
                            {
                                System.out.println("Invalid Input, please enter again");
                                type = s.nextLine();
                                typeOfShift=parseTypeOfShift(type);
                            }
                            System.out.println(facade.removeEmployeeFromShift(idToEdit, date, typeOfShift));
                            break;
                        }
                        case(3)://Back to main menu
                        {
                            continue;
                        }


                    }
                    break;


                }
                case(7): //Add/Remove constraints
                {

                    this.ARConstraintOption=printMenu(ARConstraint);
                    switch (ARConstraintOption)
                    {

                        case(1)://Add Constraint
                        {
                            //Get Shift identifiers from user (date and type)
                            Date date = getDateFromUser();
                            System.out.println("Enter Type of shift");
                            String type = s.nextLine();
                            TypeOfShift typeOfShift;
                            typeOfShift=parseTypeOfShift(type);
                            while(typeOfShift==null)  //Ask for valid input until received
                            {
                                System.out.println("Invalid Input, please enter again");
                                type = s.nextLine();
                                typeOfShift=parseTypeOfShift(type);
                            }
                            //Get constraint from user (TypeOfEmployee and Integer)
                            System.out.println("Enter Type of the employee to restrict in the shift");
                            String consType = s.nextLine();
                            TypeOfEmployee typeOfEmployee=null;
                            typeOfEmployee= parseTypeOfEmp(consType);
                            while (typeOfEmployee==null)  //Ask for valid input until received
                            {
                                System.out.println("Invalid Input, please enter again");
                                consType=s.nextLine();
                                typeOfEmployee= parseTypeOfEmp(consType);
                            }
                            int numOfEmp=-1;
                            System.out.println("Enter amount of employees for this type in the shift");

                            while (numOfEmp==-1)
                            {
                                try
                                {
                                    numOfEmp=Integer.parseInt(inValidInputDigits(s.nextLine()));
                                }
                                catch (Exception e)
                                {
                                    System.out.println("Invalid Input, please enter again");
                                }
                            }

                            System.out.println(facade.addConstraintToShift(date, typeOfShift, typeOfEmployee, numOfEmp));
                            break;
                        }
                        case(2)://Remove Constraint
                        {
                            //Get Shift identifiers from user (date and type)
                            Date date = getDateFromUser();
                            System.out.println("Enter Type of shift");
                            String type = s.nextLine();
                            TypeOfShift typeOfShift;
                            typeOfShift=parseTypeOfShift(type);
                            while(typeOfShift==null)  //Ask for valid input until received
                            {
                                System.out.println("Invalid Input, please enter again");
                                type = s.nextLine();
                                typeOfShift=parseTypeOfShift(type);
                            }
                            //Get constraint from user (TypeOfEmployee only)
                            System.out.println("Enter Type of the employee of the constraint you want to remove");
                            String consType = s.nextLine();
                            TypeOfEmployee typeOfEmployee;
                            typeOfEmployee= parseTypeOfEmp(consType);
                            while (typeOfEmployee==null)  //Ask for valid input until received
                            {
                                System.out.println("Invalid Input, please enter again");
                                consType=s.nextLine();
                                typeOfEmployee= parseTypeOfEmp(consType);
                            }

                            System.out.println(facade.removeConstraintToShift(date, typeOfShift, typeOfEmployee));
                            break;
                        }
                        case(3)://Back to main menu
                        {
                            continue;
                        }

                    }
                    break;

                }
                case(8): //Add/Remove shift
                {

                    this.ARShiftOption=printMenu(ARShift);
                    switch (ARShiftOption)
                    {

                        case(1)://Add shift
                        {
                            //Get Shift identifiers from user (date and type)
                            Date date = getDateFromUser();
                            System.out.println("Enter Type of shift to add");
                            String type = s.nextLine();
                            TypeOfShift typeOfShift;
                            typeOfShift=parseTypeOfShift(type);
                            while(typeOfShift==null)  //Ask for valid input until received
                            {
                                System.out.println("Invalid Input, please enter again");
                                type = s.nextLine();
                                typeOfShift=parseTypeOfShift(type);
                            }
                            System.out.println(facade.addShift(date, typeOfShift));
                            break;
                        }
                        case(2)://Remove Shift
                        {
                            //Get Shift identifiers from user (date and type)
                            Date date = getDateFromUser();
                            System.out.println("Enter Type of shift to remove");
                            String type = s.nextLine();
                            TypeOfShift typeOfShift;
                            typeOfShift=parseTypeOfShift(type);
                            while(typeOfShift==null) //Ask for valid input until received
                            {
                                System.out.println("Invalid Input, please enter again");
                                type = s.nextLine();
                                typeOfShift=parseTypeOfShift(type);
                            }
                            System.out.println(facade.removeShift(date, typeOfShift));
                            break;
                        }
                        case(3)://Back to main menu
                        {
                            continue;
                        }


                    }
                    break;

                }
                case(9): //pre-made scenario
                {
                    TypeOfEmployee loggedIn = this.facade.getTypeOfLoggedIn();
                    this.facade.setTypeOfLoggedIn(TypeOfEmployee.HRManager);
                    TypeOfEmployee temp = this.facade.getTypeOfLoggedIn();
                    this.facade.setTypeOfLoggedIn(TypeOfEmployee.HRManager);
                    //Creates 4 employees, a shift, and adds 1 employee to the shift
                    Date date1= null;
                    Date date2 = null;
                    Date date3 = null;
                    try
                    {
                        date1 = new SimpleDateFormat("dd/MM/yyyy").parse("20/04/2022");
                        date2 = new SimpleDateFormat("dd/MM/yyyy").parse("22/04/2022");
                        date3 = new SimpleDateFormat("dd/MM/yyyy").parse("25/04/2022");
                    }
                    catch (Exception e)
                    {
                        System.out.println("Scenario failed because of date parsing");
                    }
                    List<TypeOfEmployee> skillsNeta = new LinkedList<TypeOfEmployee>();
                    skillsNeta.add(TypeOfEmployee.ShiftManager);

                    List<TypeOfEmployee> skillsBahar = new LinkedList<TypeOfEmployee>();
                    skillsBahar.add(TypeOfEmployee.BranchManager);


                    List<TypeOfEmployee> skillsOded = new LinkedList<TypeOfEmployee>();
                    skillsOded.add(TypeOfEmployee.Storage);


                    List<TypeOfEmployee> skillsTom = new LinkedList<TypeOfEmployee>();
                    skillsTom.add(TypeOfEmployee.Cashier);
                    System.out.println(this.facade.addEmployee("Neta", "Lavi", "111111111", "132/13", 10000, "Sick Days 2", date1, skillsNeta));
                    System.out.println(this.facade.addSkill("111111111", TypeOfEmployee.Storage));
                    System.out.println(this.facade.addEmployee("Barak", "Bahar", "222222222", "132/13", 10000, "Sick Days 1", date1, skillsBahar));
                    System.out.println(this.facade.addEmployee("Oded", "Gal", "333333333", "132/13", 10000, "Sick Days 5", date1, skillsOded));
                    System.out.println(this.facade.addEmployee("Tom", "Nisim", "444444444", "132/13", 10000, "Sick Days 4", date1, skillsTom));

                    System.out.println(this.facade.addShift(date2, TypeOfShift.Morning));
                    System.out.println(this.facade.addConstraintToShift(date2, TypeOfShift.Morning, TypeOfEmployee.Cashier, 1));
                    System.out.println(this.facade.addEmployeeToShift("444444444", TypeOfEmployee.Cashier, date2, TypeOfShift.Morning));//22/04/2022
                    System.out.println(this.facade.addEmployeeToShift("111111111", TypeOfEmployee.ShiftManager,date2,TypeOfShift.Morning));

                    System.out.println(this.facade.addAvailableShift("111111111",date3, TypeOfShift.Evening));//25/04/2022
                    this.facade.setTypeOfLoggedIn(loggedIn);
                    System.out.println("Initialized Successfully");
                    this.facade.setTypeOfLoggedIn(temp);
                    break;

                }
                case(10): //Print schedule
                {
                    System.out.println(this.facade.printSchedule());
                    break;
                }
                case(11): //Print Employee's Personal Details
                {
                    String idToPrint=checkIdExist();
                    System.out.println(this.facade.printPersonalDetails(idToPrint));
                    break;
                }

                case(12): //Exit The Program
                {
                    exit(0);
                }

            }
        }
    }

    /**
     * Asks for ID from the user until valid ID, that exists in the list of employees is inserted
      * @return Valid ID that exists in the list of employees
     */
    private String checkIdExist() {
        Scanner s = new Scanner(System.in);
        boolean idExist=false;
        System.out.println("Enter ID Of The Employee");
        String idToEdit=s.nextLine();
        idToEdit=inValidInputDigits(idToEdit);
        idExist=facade.checkIfEmpExist(idToEdit);
        while (!idExist)
        {
            System.out.println("Invalid Input, please enter again");
            idToEdit=s.nextLine();
            idToEdit=inValidInputDigits(idToEdit);
            idExist=facade.checkIfEmpExist(idToEdit);
        }
        return idToEdit;
    }

    /**
     * Converts a string to a TypeOfShift object with the value of the string
     * @param type
     * @return if type is valid TypeOfShift, returns converted TypeOfShift object, else return null
     */
    private TypeOfShift parseTypeOfShift(String type)
    {
        TypeOfShift typeOfShift;
        try
        {
            typeOfShift=TypeOfShift.valueOf(type);
        }
        catch (Exception e)
        {
            typeOfShift=null;
        }
        return typeOfShift;
    }

    /**
     * Converts a string to a TypeOfEmployee object with the value of the string
     * @param type
     * @return if type is valid TypeOfEmployee, returns converted TypeOfEmployee object, else return null
     */
    private TypeOfEmployee parseTypeOfEmp(String type)
    {
        TypeOfEmployee typeOfEmployee;
        try
        {
            typeOfEmployee=TypeOfEmployee.valueOf(type);
        }
        catch (Exception e)
        {
            typeOfEmployee=null; //return null if conversion failed
        }
        return typeOfEmployee;
    }

    /**
     * Asks Valid date from user in a format of dd/MM/yyyy until a valid Date was inserted by the user
     * @return Valid Date from user
     */
    private Date getDateFromUser() {
        Scanner s = new Scanner(System.in);
        System.out.println("Please Enter Date in format dd/MM/yyyy");
        String dateOfShift = s.nextLine();
        boolean validDate=false;
        Date date=null;
        while (!validDate)
        {
            validDate=true;
            try
            {
                date = new SimpleDateFormat("dd/MM/yyyy").parse(dateOfShift);
            }
            catch (Exception e)
            {
                System.out.println("Invalid Input, please enter again");
                dateOfShift = s.nextLine();
                validDate=false;
            }
        }
        return date;
    }


    /**
     * Checks validity of input (Letters only) and keeps asking for a new input until input is valid
      * @param s
     * @return Valid input from user
     */
    private String inValidInputLetters(String s) {
        Scanner scan = new Scanner(System.in);
        while(!checkAllLetters(s))
        {
            System.out.println("Invalid Input, please enter again");
            s=scan.nextLine();
        }
        return s;
    }

    /**
     * Checks validity of input (Digits only) and keeps asking for a new input until input is valid
      * @param s
     * @return Valid input from user
     */
    private String inValidInputDigits(String s) {
        Scanner scan = new Scanner(System.in);
        while(!checkAllDigits(s))
        {
            System.out.println("Invalid Input, please enter again");
            s=scan.nextLine();
        }
        return s;
    }

    /**
     * Checks validity of string for a bank account number
     * @param bankAccountNumber
     * @return
     */
    private boolean checkBankAccountNumber(String bankAccountNumber) {
        if(bankAccountNumber.length()<=0)
            return false;
        for (int i=0;i<bankAccountNumber.length();i++)
        {
            char ch = bankAccountNumber.charAt(i);
            if ((ch<'0' || ch>'9' ) && ch!='/' && ch!='-')
            {
                return false;
            }
        }
        return true;
    }


    /**
     * Checks if every character in the given string is representing an English letter
     * @param s
     * @return
     */
    private boolean checkAllLetters(String s) {
        if(s.length()<=0)
            return false;
        for (int i=0;i<s.length();i++)
        {
            char ch = s.charAt(i);
            if (!(ch>='a' && ch<='z') && !(ch>='A' && ch<='Z')) //is an uppercase letter or lower case letter
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if every character in the given string is representing a number
     * @param num
     * @return
     */
    private boolean checkAllDigits(String num) {
        if(num.length()<=0)
            return false;
        for (int i=0;i<num.length();i++) //iterating string
        {
            char ch = num.charAt(i);
            if (ch<'0' || ch>'9' ) //is a char representing a number
            {
                return false;
            }
        }
        return true;
    }
    //Menus Initialization and creations-----------------------

    /**
     * Initializes all menus so they will be ready for printing and using
     */
    private  void initializeAllMenus()
    {
        createMenuMain();
        createARAvailableShift();
        createARSkills();
        createEditMenu();
        createEmployeeToShift();
        createAREmployee();
        createARDriverEmployee();
        createARConstraint();
        createARShift();
    }


    //Creations
    private void createMenuMain() {
        menuMain=new HashMap<>();
        menuMain.put(1,"Add/Remove Employee");
        menuMain.put(2,"Add/Remove Driver Employee");
        menuMain.put(3,"Edit employee details");
        menuMain.put(4,"Add/Remove available shift");
        menuMain.put(5,"Add/Remove skills");
        menuMain.put(6,"Add/Remove Employee to shift");
        menuMain.put(7,"Add/Remove Constraint To Existing shift - Only allowed for HRManger");
        menuMain.put(8,"Add/Remove Shift - Only allowed for HRManger");
        menuMain.put(9,"Initialize System with pre-made scenario");
        menuMain.put(10,"Print Schedule");
        menuMain.put(11,"Print Employee Personal Details");
        menuMain.put(12,"Exit");


    }
    private void createAREmployee() {
        AREmployee = new HashMap<>();
        AREmployee.put(1, "Add Employee");
        AREmployee.put(2, "Remove Employee");
        AREmployee.put(3, "Back to main menu");
    }
    private void createARDriverEmployee() {
        ARDriverEmployee = new HashMap<>();
        ARDriverEmployee.put(1, "Add Driver Employee");
        ARDriverEmployee.put(2, "Remove Driver Employee");
        ARDriverEmployee.put(3, "Back to main menu");
    }
    private void createEditMenu()
    {
        menuEdit=new HashMap<>();
        menuEdit.put(1,"Edit First Name");
        menuEdit.put(2,"Edit Last Name");
        menuEdit.put(3,"Edit ID");
        menuEdit.put(4,"Edit Bank Account Number");
        menuEdit.put(5,"Edit Salary");
        menuEdit.put(6,"Edit Employee Conditions");
        menuEdit.put(7,"Back To Main Menu");
    }
    private void createARAvailableShift()
    {
        ARAvailableShift=new HashMap<>();
        ARAvailableShift.put(1,"Add Available Shift");
        ARAvailableShift.put(2,"Remove Available Shift");
        ARAvailableShift.put(3,"Back To Main Menu");
    }
    private void createARSkills()
    {
        ARSkills=new HashMap<>();
        ARSkills.put(1,"Add Skill");
        ARSkills.put(2,"Remove Skill");
        ARSkills.put(3,"Back To Main Menu");
    }
    private void createARConstraint()
    {
        ARConstraint=new HashMap<>();
        ARConstraint.put(1,"Add Constraint");
        ARConstraint.put(2,"Remove Constraint");
        ARConstraint.put(3,"Back To Main Menu");
    }
    private void createARShift()
    {
        ARShift=new HashMap<>();
        ARShift.put(1,"Add Shift");
        ARShift.put(2,"Remove Shift");
        ARShift.put(3,"Back To Main Menu");
    }
    private void createEmployeeToShift()
    {
        AREmployeeToShift=new HashMap<>();
        AREmployeeToShift.put(1,"Add Employee To Shift");
        AREmployeeToShift.put(2,"Remove Employee From Shift");
        AREmployeeToShift.put(3,"Back To Main Menu");
    }

    //Print
    /**
     *
     * @param menu
     * @return returns the option that was chosen is the menu
     */
    private int printMenu(Map<Integer, String> menu)
    {
        System.out.println("\n------------------------------------\n");
        boolean flag = false;
        int choose=-1;
        Scanner s = new Scanner((System.in));
        for(int i=1;i<=menu.keySet().size();i++)
        {
            System.out.println(i+") "+menu.get(i));
        }
        while(!flag)
        {
            try
            {
                 flag=false;
                 choose = s.nextInt();
                 flag = isWithinBounds(choose, menu.size());
            }
            catch (Exception e)
            {
                System.out.println("Invalid Input, please enter again");
                flag=true;
            }
        }

        return choose;
    }

    private boolean isWithinBounds(int choose, int bound) throws Exception{
        if(choose<1 || choose > bound) {
            System.out.println("Option chosen is out of bounds, please enter again");
            return false;
        }
        return true;
    }
}
