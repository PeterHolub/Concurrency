package com.company.peterholub.interrupt;

import java.util.Random;

public class InterruptTest {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            Random random = new Random();

            for (int i = 0; i < 1_000_000_000; i++) {
                if (Thread.currentThread().isInterrupted()) {

                    break;
                }
                Math.sin(random.nextDouble());
            }
        });

        System.out.println("Starting Thread");
        thread.start();
        thread.interrupt();
        thread.join();


        System.out.println("Thread has finished");
    }
}
