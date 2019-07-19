package com.company.peterholub.sync;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestMultipleObjectsSynchronization {
    public static void main(String[] args) {
        new Worker().main();

    }
}

class Worker {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    private Random random = new Random();

    private List<Integer> list1 = new ArrayList<>();
    private List<Integer> list2 = new ArrayList<>();

    private void addToList1() {
        synchronized (lock1) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            list1.add(random.nextInt(100));
        }
    }

    private synchronized void addToList2() {
        synchronized (lock2) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list2.add(random.nextInt(100));

        }
    }


    private void work() {
        for (int i = 0; i < 1000; i++) {
            addToList1();
            addToList2();
        }
    }

    void main() {

        long before = System.currentTimeMillis();
        Thread thread1 = new Thread(this::work);
        Thread thread2 = new Thread(this::work);

        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long after = System.currentTimeMillis();
        System.out.println("Program took " + (after - before) + "ms to run");

        System.out.println(list1.size());
        System.out.println(list2.size());
    }

}

