package Algorithms;

import java.awt.*;
import java.util.Random;

public class Main {
    static int d = 3000 ;
    static int rect = 0  ;
    static int circle = 0 ;
    static Point circleCenter = new Point(d/2, d/2);
    Random random = new Random();
    int upperbound = d ;
    int i = random.nextInt(upperbound);
    static Point p = new Point();

    public static void main(String[] args) {
       /* Point p1 = new Point(11,11);
        System.out.println(InCercle(p1,circleCenter));
        System.out.println(Math.PI);*/
        Main pi = new Main();
        pi.computePi(d);
    }

    public void computePi(int number) {
        circle = 0 ;
        rect = 0 ;
        for (int i = 0 ; i< number ; i++) {
                Point p = randomPoint();
                if (InCercle(p,circleCenter)) {
                    circle ++ ;
                    rect++;
                } else {
                    rect ++ ;
                }
        }
        double result = (double)circle/(double)rect ;
        System.out.println(result);

    }


    public Point randomPoint() {
        int x = random.nextInt(upperbound);
        int y = random.nextInt(upperbound);
        return p = new Point(x,y);
    }


    public static boolean InCercle (Point p,Point c) {
        int xPoint = (int)p.getX();
        int yPoint = (int)p.getY();
        int xCercle = (int)c.getX();
        int yCercle = (int)c.getY();
        return Math.sqrt((xPoint-xCercle)*(xPoint-xCercle) + (yPoint-yCercle)*(yPoint-yCercle))<=d/2;
    }
}
