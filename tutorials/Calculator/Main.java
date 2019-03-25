package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Calculator c = new Calculator();
        char input;

        do {
            System.out.println("Options: (A)dd, (M)ultiply, (F)actorial, (Q)uit.");
            Scanner s = new Scanner(System.in);
            input = s.next().charAt(0);

            switch (input) {
                case 'Q':
                    System.out.println("Goodbye!");
                    break;
                case 'A':
                    System.out.println("Please enter the first number: ");
                    double x = s.nextDouble();
                    System.out.println("Please enter the second number: ");
                    double y = s.nextDouble();
                    System.out.println(x + " plus " + y + " equals " + c.add(x, y));
                    break;
                case 'M':
                    System.out.println("Please enter the first number: ");
                    x = s.nextDouble();
                    System.out.println("Please enter the second number: ");
                    y = s.nextDouble();
                    System.out.println(x + " times " + y + " equals " + c.multiply(x, y));
                    break;
                case 'F':
                    System.out.println("Please enter the number you want to calculate the factorial of ");
                    try {
                    int n = s.nextInt();
                    System.out.println("The factorial of " + n + " equals " + c.factorial(n));
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                    break;
                default:
                    System.out.println("Invalid input, please use given options");
                    break;
            }
        } while (!(input == 'Q'));
        }
    }






