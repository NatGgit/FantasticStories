package com.company;

public class Student {
        private String firstName;
        private String lastName;

        public Student(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public void printFullName(){
            System.out.println(this.firstName + " " + this.lastName);
        }
//    Write the method printFullName of student which prints the full name of a student.

}

// Your code goes here
//}
//
//public class Main {
//    public static void main(String[] args) {
//        Student[] students = new Student[] {
//                new Student("Morgan", "Freeman"),
//                new Student("Brad", "Pitt"),
//                new Student("Kevin", "Spacey"),
//        };
//        for (Student s : students) {
//            s.printFullName();
//        }
//    }
//}