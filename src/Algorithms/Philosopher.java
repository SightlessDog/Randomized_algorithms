package Algorithms;



public class Philosopher implements Runnable {
    private Thread t;
    protected String name;
    private String[] diningActions = {"is thinking", "picks up left fork", "picks up right fork", "is eating"};
    private int eatTime = 50;
    private int thinkTime = 50;
    protected int rForkID = -1;
    protected int lForkID = -1;
    protected int lForkRef = -1;
    protected int rForkRef = -1;
    int numForks =0;
    public boolean queued = false;

    private boolean running = true;

    public enum State {
        ordering,
        waiting,
        eating
    }

    private State currentState;

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    protected boolean canEat() {

        return lForkRef == lForkID && rForkRef == rForkID;
    }

    Philosopher(String name, int lForkID, int rForkID) {
        this.name = name;
        this.currentState = State.waiting;
        this.lForkID = lForkID;
        this.rForkID = rForkID;
        System.out.println("Creating " + name);
        System.out.println("Fork L :: " + lForkID + "  Fork R :: " + rForkID);
    }

    public void run() {
        System.out.println("Running " + name);

        while(running){



            // if I am waiting
            while (currentState == State.waiting) {
                // try and take a fork, but only take the lowest fork in heirarchy
                // check heirarchy choose lowest

                int targetFork = (lForkID < rForkID) ? lForkID : rForkID;
                // if I already have a fork, make sure to choose the other one
                if (rForkRef >=0|| lForkRef >= 0){
                    targetFork = (lForkRef >=0) ? rForkID : lForkID;
                }
                // try and get that fork
               boolean take = DiningProblem.takeFork(targetFork);
               if (take){
                   if(targetFork == lForkID) lForkRef = targetFork;
                   else rForkRef = targetFork;
               }

               if(canEat()) currentState = State.eating;


            }
            while(currentState == State.eating){
                System.out.println(name + " starting to eat");
                // think first
                try {
                    Thread.sleep(eatTime);
                } catch (InterruptedException e) {
                    System.out.println("Thread " + name + " interrupted.");
                }

                System.out.println(name + " finished eating");
                currentState = State.waiting;
                DiningProblem.dropFork(lForkRef);
                lForkRef= -1;
                DiningProblem.dropFork(rForkRef);
                rForkRef= -1;

            }







        }

        System.out.println("Thread " + name + " exiting.");
    }

   /* public void run() {
        System.out.println("Running " + name);

        while(running){


            while (currentState == State.ordering){
                if(!queued && !canEat()){
                    DiningProblem.waiter.requestService(this);
                    queued = true;
                }

            }


            while (currentState == State.waiting) {

            }


            while(currentState == State.eating){
                System.out.println(name + " starting to eat");
                // think first
                try {
                    Thread.sleep(eatTime);
                } catch (InterruptedException e) {
                    System.out.println("Thread " + name + " interrupted.");
                }

                System.out.println(name + " finished eating");
                currentState = State.waiting;
                DiningProblem.waiter.requestCleanUp(this);
            }

            //stop();

        }

        System.out.println("Thread " + name + " exiting.");
    }*/

    private void stop(){
        this.running = false;
    }



    private void removeForks(){
        DiningProblem.dropFork(lForkRef);
        DiningProblem.dropFork(rForkRef);

        lForkRef = -1;
        rForkRef = -1;

        System.out.println(name + "placed down their forks");
    }

    public void start() {
        System.out.println("Starting " + name);
        if (t == null) {
            t = new Thread(this, name);
            t.start();
        }
    }
}
