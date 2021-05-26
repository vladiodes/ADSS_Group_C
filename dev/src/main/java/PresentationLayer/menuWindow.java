package PresentationLayer;


import java.util.HashMap;
import java.util.Scanner;

public abstract class menuWindow {
    protected Scanner scanner=utills.scanner;
    protected String description;
    protected HashMap<Integer,String> menu;

    public menuWindow(String description){
        this.description=description;
    }

    public abstract void start();
    protected abstract void createMenu();

    public void printDescription(){
        System.out.println("Welcome to the " + description);
    }

    protected int printMenu() {
        int input = -2;
        System.out.println("Please choose an option");
        while (input < 0) {
            if (input == -1)
                System.out.println("Wrong input. Please choose one of the following numbers:");
            for (int i = 1; menu.get(i) != null; i++)
                System.out.println(i + ". " + menu.get(i));
            System.out.println("\nEnter your choice:");
            String choice = scanner.nextLine();
            input = utills.checkIfInBounds(choice, menu.size() + 1);
        }
        return input;
    }
}
