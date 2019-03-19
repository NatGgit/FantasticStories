package com.company;

class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void print() {
        System.out.println("(" + x + "," + y + ")");
    }

    public void scale () {
        x /= 2;
        y /= 2;
    }

}
//    Write a new method in Point called scale, that will make the point closer by half to (0,0).
//    So for example, point (8, 4) after scale will be (4, 2).



//public class Main {
//    public static void main(String[] args) {
//        Point p = new Point(32, 32);
//        for (int i = 0; i < 5; i++) {
//            p.scale();
//            p.print();
//        }
//    }
//}

