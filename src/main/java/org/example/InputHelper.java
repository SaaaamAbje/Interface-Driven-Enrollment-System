package org.example;

import java.util.Scanner;

public class InputHelper {
    private static final Scanner sc = new Scanner(System.in);

    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    public static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number (e.g. 1500.00).");
            }
        }
    }

    public static String readString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }
}