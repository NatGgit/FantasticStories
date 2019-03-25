package com.company;

import java.util.Scanner;

public class Calculator {
    public Calculator() {
    }

    public double add(double x, double y) {
        return x + y;
    }

    public double multiply(double x, double y) {
        return x * y;
    }

    public long factorial(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Invalid input. Next time please enter a number greater than zero");
        } else {
            long result = 1;
            for (int i = 1; i <= n; i++) {
                result *= i;
            }
            return result;
        }
    }
}



