package PresentationLayer;

import BusinessLayer.Facade.ISuppliersFacade;

import java.util.Scanner;

public abstract class Window {
    protected Scanner scanner=new Scanner(System.in);
    protected ISuppliersFacade facade;
    protected String description;

    public Window(ISuppliersFacade facade,String description){
        this.facade=facade;
        this.description=description;
    }

    public abstract void start();

    public void printDescription(){
        System.out.println("Welcome to the " + description);
    }
}
