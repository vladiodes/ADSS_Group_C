package Presentation;

import Business.Facade;
import Business.TypeOfEmployee;
import Business.TypeOfShift;


import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.exit;
import static java.lang.System.setOut;

public class Menus {
    private Map menuMain ;
    private int menuMainOption;

    private Map menuEdit ;
    private int menuEditOption;

    private Map AREmployee;
    private int AREmployeeOption;

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

    private Facade facade;

    public Menus(Facade facade)
    {
        this.facade =facade;
    }
    public void start()
    {

        Scanner s = new Scanner(System.in);
        String[] employeeFields=new String[7]; //0-FirstName 1-LastName 2-ID 3-BankAccountNumber 4-Salary 5-empConditions
        initializeAllMenus();
        while(true)
        {
            this.menuMainOption =  printMenu(menuMain);
            switch (this.menuMainOption)
            {
                case(1): //Add/Remove Employee
                {
                    this.AREmployeeOption = printMenu(AREmployee);
                    switch (this.AREmployeeOption)
                    {
                        case(1): //Add Employee
                        {

                            System.out.println("Please Enter First Name");
                            employeeFields[0]=s.nextLine();
                            employeeFields[0]=inValidInputLetters(employeeFields[0]);

                            System.out.println("Please Enter Last Name");
                            employeeFields[1]=s.nextLine();
                            employeeFields[1]=inValidInputLetters(employeeFields[1]);


                            System.out.println("Please Enter ID");
                            employeeFields[2]=s.nextLine();
                            employeeFields[2]=inValidInputDigits(employeeFields[2]);


                            System.out.println("Please Enter bank Account Number");
                            employeeFields[3]=s.nextLine();
                            while (!checkBankAccountNumber(employeeFields[3]))
                            {
                                System.out.println("Invalid Input, please enter again");
                                employeeFields[3]=s.nextLine();
                            }

                            System.out.println("Please Enter salary");
                            employeeFields[4]=s.nextLine();
                            employeeFields[4]=inValidInputDigits(employeeFields[4]);


                            System.out.println("Please Enter Employee conditions");
                            employeeFields[5]=s.nextLine();//free text , no need to check

                            System.out.println("Please Enter 1 skill of the employee");
                            employeeFields[6]=s.nextLine();

                            //All input from user is ready
                            List<TypeOfEmployee> l = new LinkedList<>();
                            TypeOfEmployee typeOfEmployee;
                            typeOfEmployee= parseTypeOfEmp(employeeFields[6]);
                            while (typeOfEmployee==null)
                            {
                                System.out.println("Invalid Input, please enter again");
                                employeeFields[6]=s.nextLine();
                                typeOfEmployee= parseTypeOfEmp(employeeFields[6]);
                            }
                            l.add(typeOfEmployee);
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
                case(2): //Edit Employee details

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
                            newBankAccountNumber=inValidInputLetters(newBankAccountNumber);
                            System.out.println(facade.editBankAccountNumber(idToEdit, newBankAccountNumber));
                            break;

                        }
                        case(5)://Edit Salary
                        {
                            System.out.println("Enter new Salary");
                            String newSalary = s.nextLine();
                            newSalary=inValidInputLetters(newSalary);
                            System.out.println(facade.editSalary(idToEdit, Integer.parseInt(newSalary)));
                            break;

                        }
                        case(6)://Edit Employee Conditions
                        {
                            System.out.println("Enter new Employee Conditions");
                            String newEmpConditions = s.nextLine();
                            newEmpConditions=inValidInputLetters(newEmpConditions);
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
                case(3): //Add/Remove available shift
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
                            while(typeOfShift==null)
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
                            System.out.println("Please Enter Date Of Shift To Remove Like xx/xx/xxxx");
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
                            while(typeOfShift==null)
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
                case(4): //Add/Remove skills
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
                            typeOfEmployee= parseTypeOfEmp(employeeFields[6]);
                            while (typeOfEmployee==null)
                            {
                                System.out.println("Invalid Input, please enter again");
                                employeeFields[6]=s.nextLine();
                                typeOfEmployee= parseTypeOfEmp(employeeFields[6]);
                            }
                            System.out.println(facade.addSkill(idToEdit, typeOfEmployee));
                            break;
                        }
                        case(2)://Remove skills
                        {
                            System.out.println("Please Enter skill to remove");
                            String skill = s.nextLine();
                            TypeOfEmployee typeOfEmployee;
                            typeOfEmployee= parseTypeOfEmp(employeeFields[6]);
                            while (typeOfEmployee==null)
                            {
                                System.out.println("Invalid Input, please enter again");
                                employeeFields[6]=s.nextLine();
                                typeOfEmployee= parseTypeOfEmp(employeeFields[6]);
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
                case(5): //Add/Remove Employee to shift
                {

                    String idToEdit=checkIdExist();
                    this.AREmployeeToShiftOption=printMenu(AREmployeeToShift);
                    switch (AREmployeeToShiftOption)
                    {

                        case(1)://Add Employee to shift
                        {
                            System.out.println("Enter skill of the employee");
                            String skill = s.nextLine();
                            Date date = getDateFromUser();
                            System.out.println("Enter Type of shift");
                            String type = s.nextLine();
                            TypeOfEmployee typeOfEmployee;
                            typeOfEmployee= parseTypeOfEmp(employeeFields[6]);
                            while (typeOfEmployee==null)
                            {
                                System.out.println("Invalid Input, please enter again");
                                employeeFields[6]=s.nextLine();
                                typeOfEmployee= parseTypeOfEmp(employeeFields[6]);
                            }
                            TypeOfShift typeOfShift;
                            typeOfShift=parseTypeOfShift(type);
                            while(typeOfShift==null)
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
                            while(typeOfShift==null)
                            {
                                System.out.println("Invalid Input, please enter again");
                                type = s.nextLine();
                                typeOfShift=parseTypeOfShift(type);
                            }
                            System.out.println(facade.removeEmployeeToShift(idToEdit, date, typeOfShift));
                            break;
                        }
                        case(3)://Back to main menu
                        {
                            continue;
                        }


                    }
                    break;


                }
                case(6): //Add/Remove constraints
                {

                    this.ARConstraintOption=printMenu(ARConstraint);
                    switch (ARConstraintOption)
                    {

                        case(1)://Add Constraint
                        {

                            Date date = getDateFromUser();
                            System.out.println("Enter Type of shift");
                            String type = s.nextLine();
                            TypeOfShift typeOfShift;
                            typeOfShift=parseTypeOfShift(type);
                            while(typeOfShift==null)
                            {
                                System.out.println("Invalid Input, please enter again");
                                type = s.nextLine();
                                typeOfShift=parseTypeOfShift(type);
                            }
                            //get constraints
                            System.out.println("Enter Type of the employee to restrict in the shift");
                            String consType = s.nextLine();
                            TypeOfEmployee typeOfEmployee;
                            typeOfEmployee= parseTypeOfEmp(consType);
                            while (typeOfEmployee==null)
                            {
                                System.out.println("Invalid Input, please enter again");
                                consType=s.nextLine();
                                typeOfEmployee= parseTypeOfEmp(consType);
                            }
                            Integer numOfEmp=-1;
                            System.out.println("Enter amount of employees for this type in the shift");
                            while (numOfEmp==-1)
                            {
                                try
                                {
                                    numOfEmp = s.nextInt();
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

                            Date date = getDateFromUser();
                            System.out.println("Enter Type of shift");
                            String type = s.nextLine();
                            TypeOfShift typeOfShift;
                            typeOfShift=parseTypeOfShift(type);
                            while(typeOfShift==null)
                            {
                                System.out.println("Invalid Input, please enter again");
                                type = s.nextLine();
                                typeOfShift=parseTypeOfShift(type);
                            }
                            //get constraints
                            System.out.println("Enter Type of the employee of the constraint you want to remove");
                            String consType = s.nextLine();
                            TypeOfEmployee typeOfEmployee;
                            typeOfEmployee= parseTypeOfEmp(consType);
                            while (typeOfEmployee==null)
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
                case(7): //Add/Remove Shift
                {

                    this.ARShiftOption=printMenu(ARShift);
                    switch (ARShiftOption)
                    {

                        case(1)://Add shift
                        {

                            Date date = getDateFromUser();
                            System.out.println("Enter Type of shift to add");
                            String type = s.nextLine();
                            TypeOfShift typeOfShift;
                            typeOfShift=parseTypeOfShift(type);
                            while(typeOfShift==null)
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
                            Date date = getDateFromUser();
                            System.out.println("Enter Type of shift to remove");
                            String type = s.nextLine();
                            TypeOfShift typeOfShift;
                            typeOfShift=parseTypeOfShift(type);
                            while(typeOfShift==null)
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
                case(8): //scenario
                {
                    Date date1= null;
                    Date date2 = null;
                    try
                    {
                        date1 = new SimpleDateFormat("dd/MM/yyyy").parse("20/04/2022");
                        date2 = new SimpleDateFormat("dd/MM/yyyy").parse("22/04/2022");
                    }
                    catch (Exception e)
                    {
                        System.out.println("Scenario failed because of date parsing");
                    }
                    List<TypeOfEmployee> skillsNeta = new LinkedList<>();
                    skillsNeta.add(TypeOfEmployee.ShiftManager);

                    List<TypeOfEmployee> skillsBahar = new LinkedList<>();
                    skillsBahar.add(TypeOfEmployee.BranchManager);

                    List<TypeOfEmployee> skillsOded = new LinkedList<>();
                    skillsOded.add(TypeOfEmployee.Storage);

                    List<TypeOfEmployee> skillsTom = new LinkedList<>();
                    skillsTom.add(TypeOfEmployee.Cashier);
                    this.facade.addEmployee("Neta", "Lavi", "11111111", "132/13", 10000, "Sick Days 5", date1, skillsNeta);
                    this.facade.addEmployee("Barak", "Bahar", "222222222", "132/13", 10000, "Sick Days 5", date1, skillsBahar);
                    this.facade.addEmployee("Oded", "Gal", "333333333", "132/13", 10000, "Sick Days 5", date1, skillsOded);
                    this.facade.addEmployee("Tom", "Nisim", "444444444", "132/13", 10000, "Sick Days 5", date1, skillsTom);

                    this.facade.addShift(date2, TypeOfShift.Morning);
                    this.facade.addEmployeeToShift("123456789", TypeOfEmployee.ShiftManager,date2,TypeOfShift.Morning);


                    System.out.println("Initialized Successfully");

                    break;

                }
                case(9): //Print schedule
                {
                    System.out.println(this.facade.printSchedule());
                    break;
                }
                case(10): //Exit
                {
                    exit(0);
                }

            }
        }
    }

    private String checkIdExist() {
        Scanner s = new Scanner(System.in);
        boolean idExist=false;
        System.out.println("Enter ID Of The Employee To Edit");
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

    private TypeOfEmployee parseTypeOfEmp(String type)
    {
        TypeOfEmployee typeOfEmployee;
        try
        {
            typeOfEmployee=TypeOfEmployee.valueOf(type);
        }
        catch (Exception e)
        {
            typeOfEmployee=null;
        }
        return typeOfEmployee;
    }

    private Date getDateFromUser() {
        Scanner s = new Scanner(System.in);
        System.out.println("Please Enter Date in format xx/xx/xxxx");
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


    private String inValidInputLetters(String s) {
        Scanner scan = new Scanner(System.in);
        while(!checkAllLetters(s))
        {
            System.out.println("Invalid Input, please enter again");
            s=scan.nextLine();
        }
        return s;
    }
    private String inValidInputDigits(String s) {
        Scanner scan = new Scanner(System.in);
        while(!checkAllDigits(s))
        {
            System.out.println("Invalid Input, please enter again");
            s=scan.nextLine();
        }
        return s;
    }

    private boolean checkBankAccountNumber(String bankAccountNumber) {
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


    private boolean checkAllLetters(String s) {
        for (int i=0;i<s.length();i++)
        {
            char ch = s.charAt(i);
            if (!(ch>='a' && ch<='z') && !(ch>='A' && ch<='Z'))
            {
                return false;
            }
        }
        return true;
    }

    private boolean checkAllDigits(String num) {
        for (int i=0;i<num.length();i++)
        {
            char ch = num.charAt(i);
            if (ch<'0' || ch>'9' )
            {
                return false;
            }
        }
        return true;
    }

    private  void initializeAllMenus()
    {
        createMenuMain();
        createARAvailableShift();
        createARSkills();
        createEditMenu();
        createEmployeeToShift();
        createAREmployee();
        createARConstraint();
        createARShift();
    }


    private void createMenuMain() {
        menuMain=new HashMap<>();
        menuMain.put(1,"Add/Remove Employee");
        menuMain.put(2,"edit employee details");
        menuMain.put(3,"Add/Remove available shift");
        menuMain.put(4,"Add/Remove skills");
        menuMain.put(5,"Add/Remove Employee to shift");
        menuMain.put(6,"Add/Remove Constraint To Existing shift - only HRManger");
        menuMain.put(7,"Add/Remove Shift");
        menuMain.put(8,"scenario");
        menuMain.put(9,"Print Schedule");
        menuMain.put(10,"Exit");


    }
    private void createAREmployee() {
        AREmployee = new HashMap<>();
        AREmployee.put(1, "Add Employee");
        AREmployee.put(2, "Remove Employee");
        AREmployee.put(3, "Back to main menu");
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


    private int printMenu(Map<Integer, String> menu)
    {
        Scanner s = new Scanner((System.in));
        for(int i=1;i<=menu.keySet().size();i++)
        {
            System.out.println(i+") "+menu.get(i));
        }
        int choose = s.nextInt();
        return choose;
    }
}
