package Presentation;

import Business.Controllers.EmployeesFacade;
import Business.Misc.TypeOfEmployee;

import java.util.Scanner;

public class main {



    public static void main(String [] args)
    {
        EmployeesFacade facade = new EmployeesFacade(selectType());
        new Menus(facade).start();
    }

    private static TypeOfEmployee selectType()
    {
        Scanner s = new Scanner((System.in));
        while (true)
        {
            System.out.println("Please choose your employee type");
            int counter=1;
            for (TypeOfEmployee type: TypeOfEmployee.values())
            {
                System.out.println(counter + ") "+type);
                counter++;
            }
            int choose;
            choose=Integer.parseInt(inValidInputDigits(s.nextLine()));
            if (choose<1 || choose > TypeOfEmployee.values().length)
            {
                System.out.println("employee type chosen is invalid");
            }
            else
            {
                return TypeOfEmployee.values()[choose-1];
            }
        }
    }
    private static String inValidInputDigits(String s) {
        Scanner scan = new Scanner(System.in);
        while(!checkAllDigits(s))
        {
            System.out.println("Invalid Input, please enter again");
            s=scan.nextLine();
        }
        return s;
    }
    private static boolean checkAllDigits(String num) {
        if(num.length()<=0)
        {
            return false;
        }
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






}
