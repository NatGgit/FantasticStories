package com.company;

public class Conditionals {
    public static void main(String[] args) {
        for (int i = 1; i <= 100; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                System.out.println(i + "***");
            } else if (i % 5 == 0) {
                System.out.println(i + "**");
            } else if (i % 3 == 0) {
                System.out.println(i + "*");
            } else {
                System.out.println(i);
            }

        }


    }
}

//    write a program that prints the numbers from 1 to 100
//    (one in every line), but for numbers that are multiplies of tree print a star, and for numbers
//    that are multipliers of 5 print 2 stars, and if a number is a multiple of 3 and 5 print 3 stars