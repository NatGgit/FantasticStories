package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Calculator c = new Calculator();
        char input;

        do {
            System.out.println("Options: (A)dd, (M)ultiply, (F)actorial, (Q)uit.");
            Scanner s1 = new Scanner(System.in);
            input = s1.next().charAt(0);

            switch (input) {
                case 'Q':
                    System.out.println("Goodbye!");
                    break;
                case 'A':
                    System.out.println("Please enter the first number: ");
                    Scanner s3 = new Scanner(System.in);
                    double x = s3.nextDouble();
                    System.out.println("Please enter the second number: ");
                    Scanner s4 = new Scanner(System.in);
                    double y = s4.nextDouble();
                    System.out.println(x + " plus " + y + " equals " + c.add(x, y));
                    break;
                case 'M':
                    System.out.println("Please enter the first number: ");
                    Scanner s5 = new Scanner(System.in);
                    x = s5.nextDouble();
                    System.out.println("Please enter the second number: ");
                    Scanner s6 = new Scanner(System.in);
                    y = s6.nextDouble();
                    System.out.println(x + " times " + y + " equals " + c.multiply(x, y));
                    break;
                case 'F':
                    System.out.println("Please enter the number you want to calculate the factorial of ");
                    Scanner s7 = new Scanner(System.in);
                    int n = s7.nextInt();
                    System.out.println("The factorial of " + n + " equals " + c.factorial(n));
                    break;
                default:
                    System.out.println("Invalid input, please use given options");
                    break;
            }
        } while (!(input == 'Q'));
        }
    }






