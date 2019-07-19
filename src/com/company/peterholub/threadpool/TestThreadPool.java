package com.company.peterholub.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestThreadPool {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 5; i++) {
            executorService.submit(new Work(i));
        }
        executorService.shutdown();

        executorService.awaitTermination(1, TimeUnit.HOURS);
    }
}

class Work implements Runnable {

    private int id;

    Work(int id) {

        this.id = id;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Work" + id + " was completed");
    }

}