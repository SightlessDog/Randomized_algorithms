package Algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class DiningProblem {

    public static ArrayList<Fork> forks = new ArrayList<Fork>();
    public static ArrayList<Philosopher> philosophers = new ArrayList<Philosopher>();
    static int  noPhilosophers  = 5;
    static int noForks = 5;

    public static Waiter waiter;


    static class Fork {
        public int number;
        public boolean amTaken;


        public Fork (int number){
            this.number = number;
            this.amTaken = false;
        }
    }

    static void init(){
        int i = 0;
       for (; i< noForks; i++){
           Fork f = new Fork(i);
           forks.add(f);
        }

       waiter  = new Waiter("Alfred");
       waiter.start();
        i = 0;
        for (; i< noPhilosophers; i++){
            Philosopher p = new Philosopher("Philosopher " +i, i, (i+1 >= noPhilosophers)? 0 : i+1);
            philosophers.add(p);
            p.start();
        }
    }

    public static boolean canTakeFork(int number){

        if (forks.get(number).amTaken)return false;
        else{
            return true;
        }
    }


    public static boolean takeFork(int number){

        if (forks.get(number).amTaken)return false;
        else{
            forks.get(number).amTaken =true;
            return true;
        }
    }

    public static void dropFork(int number){
        if (forks.get(number).amTaken) {
            forks.get(number).amTaken = false;
            System.out.println("Fork "+number +" dropped");
        }
        else System.out.println("Fork "+number +" already dropped");

    }

    public static void clearQueued(){
        for (Philosopher p : philosophers
             ) {
            p.setCurrentState(Philosopher.State.ordering);
         //   p.queued = false;
        }
    }

    public static void main(String[] args) {
        init();
    }

}
