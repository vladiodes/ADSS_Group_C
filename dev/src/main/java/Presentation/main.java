package Presentation;

import Business.Facade;
import Business.TypeOfEmployee;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class main {



    public static void main(String [] args)
    {
        Facade facade = new Facade(selectType());
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
            int choose = s.nextInt();
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





}
