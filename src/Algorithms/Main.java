package Algorithms;

import java.awt.*;
import java.util.Random;

public class Main {
    static int d = 20 ;
    static Point circleCenter = new Point(d/2, d/2);
    Random random = new Random();
    int upperbound = d ;
    int i = random.nextInt(upperbound);
    static Point p = new Point();

    public static void main(String[] args) {
        Point p1 = new Point(11,11);
        System.out.println(InCercle(p1,circleCenter));
    }

    public void computePi(int number) {
        int x = random.nextInt(upperbound);
        int y = random.nextInt(upperbound);
        p = new Point(x,y);
    }


    public static boolean InCercle (Point p,Point c) {
        int xPoint = (int)p.getX();
        int yPoint = (int)p.getY();
        int xCercle = (int)c.getX();
        int yCercle = (int)c.getY();
        System.out.println();
        return Math.sqrt((xPoint-xCercle)*(xPoint-xCercle) + (yPoint-yCercle)*(yPoint-yCercle))<=d/2;


    }
}
