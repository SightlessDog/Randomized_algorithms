package Algorithms;

import jdk.dynalink.Operation;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

class Waiter implements Runnable {
    private ArrayBlockingQueue operations = new ArrayBlockingQueue(1024);
    private Thread t;
    private String threadName = "Waiter";
    private Philosopher currentlyServing = null;
    private boolean exit = false;
    boolean once;

    class DiningOperation {
        public Philosopher author = null;
        public boolean take;
        private Waiter myWaiter;

        public DiningOperation(Philosopher author, boolean take, Waiter myWaiter) {
            this.author = author;
            this.take = take;
            this.myWaiter = myWaiter;
        }

        public void process() {
            if (take) {
                // get left fork
                boolean didTakeLeft = DiningProblem.takeFork(author.lForkID);

                if (didTakeLeft) {
                    System.out.println(author.name + " just took Fork No. " + author.lForkID);

                    // get right fork
                    boolean didTakeRight = DiningProblem.takeFork(author.rForkID);
                    if (didTakeRight) {
                        // this means both forks can be taken and the philosopher can eat
                        DiningProblem.forks.get(author.rForkID).amTaken = true;
                        DiningProblem.forks.get(author.lForkID).amTaken = true;
                        author.rForkRef = author.rForkID;
                        author.lForkRef = author.lForkID;
                        author.setCurrentState(Philosopher.State.eating);
                        myWaiter.currentlyServing = null;
                    }

                } else {
                    System.out.println(author.name + " cannot eat since one fork is taken");
                    myWaiter.currentlyServing = null;
                }
            } else {
                DiningProblem.dropFork(author.rForkID);
                DiningProblem.dropFork(author.lForkID);
                author.lForkRef = -1;
                author.rForkRef = -1;
                author.setCurrentState(Philosopher.State.ordering);
                System.out.println("Finished Service");

            }

        }
    }


    Waiter(String threadName) {
        this.threadName = threadName;
        System.out.println("Creating " + this.threadName);
    }

    public void requestService(Philosopher customer) {
        System.out.println(customer.name + " requested Service");
        DiningOperation getForks = new DiningOperation(customer, true, this);
        operations.add(getForks);
        customer.setCurrentState(Philosopher.State.waiting);
    }

    public void requestCleanUp(Philosopher customer) {
        DiningOperation dropForks = new DiningOperation(customer, false, this);

        operations.add(dropForks);
    }

    public void run() {
        System.out.println("Running " + threadName);

        while (!exit) {


            if (!operations.isEmpty()) {
                DiningOperation currentOP = (DiningOperation) operations.remove();
                if (currentlyServing == null) {
                    currentlyServing = currentOP.author;
                }
                // check if operations belongs to currently served customer
             /*   if (currentOP.author == currentlyServing) {
                    currentOP.process();
                } else System.out.println("wrong customer");*/
                currentOP.process();
                once = false;
            } else {
                if (!once) {
                    System.out.println("no current requests");
                    once = true;
                }
            }

        }
    }

    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}
