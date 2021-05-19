package PresentationLayer;

import BusinessLayer.Facade.Response;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class utills {
    public static final Scanner scanner=new Scanner(System.in);

    /**
     * Checks if a given string represents a number that is {@code 0<number<bound}
     * @param number - the string that represents the number
     * @param bound - the upper bound
     * @return the number that the string represents if the condition stands, or -1 otherwise
     */
    public static int checkIfInBounds(String number,int bound) {
        try {
            int num = Integer.parseInt(number);
            return num > 0 && num < bound ? num : -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Checks if the string that number represents is a positive number (including zero)
     * @param number the number
     * @return the number the string represents if positive, or -1 otherwise
     */
    public static int checkPositiveNumber(String number) {
        try {
            int num = Integer.parseInt(number);
            return num >= 0 ? num : -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Checks if the string represents a double bigger of equal to zero number
     * @param number
     * @return the number if the condition stands, or -1 otherwise
     */
    public static double checkDoubleNumber(String number) {
        try {
            double num = Double.parseDouble(number);
            return num >= 0 ? num : -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Prints the message of the response in case of an exception or a success message
     * @param response response object
     * @param successMessage the success message to print
     */
    public static void printMessageOrSuccess(Response<? extends Object> response, String successMessage) {
        if(response.WasException())
            System.out.println(response.getMessage());
        else
            System.out.println(successMessage);
    }

    /**
     * Prints an error message in case of an error issued with the response object
     * if no error, prints the value issued with the response object
     * @param response reponse object
     * @param <T> The type of value the response object wraps
     */
    public static <T> void printErrorMessageOrListOfValues(Response<List<T>> response) {
        if (response.WasException())
            System.out.println(response.getMessage());
        else {
            for (T elem : response.getValue()) {
                System.out.println(elem + "\n");
            }
        }
    }

    public static int getNonNegativeNumber(String prompt){
        int output=-1;
        System.out.println(prompt);
        for(output= checkPositiveNumber(scanner.nextLine()); output==-1; output= checkPositiveNumber(scanner.nextLine()))
            System.out.println("Wrong input, please enter a non negative number");
        return output;
    }

    public static LocalDate getDateFromUser(String prompt) {
        System.out.println(prompt);
        int day = getNonNegativeNumber("Please enter day:");
        while (day > 30 || day < 1)
            day = getNonNegativeNumber("Please enter day:");
        int month = getNonNegativeNumber("Please enter month:");
        while (month > 12 || month < 1)
            month = getNonNegativeNumber("Please enter month:");
        int year = getNonNegativeNumber("Please enter year:");
        while (year < 2021 || year > 2023)
            year = getNonNegativeNumber("Please enter year:");
        return LocalDate.of(year, month, day);
    }
}
