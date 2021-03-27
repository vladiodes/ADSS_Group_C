package PresentationLayer;

import java.io.IOException;

public class inputChecker {

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
}
