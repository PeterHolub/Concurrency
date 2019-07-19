package com.company.peterholub.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class TestSemaphore {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(200);
        Connection connection = Connection.getInstance();

        IntStream.range(0, 200).forEach(i -> executorService.submit(() -> {
            try {
                connection.work();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);
    }

}

class Connection {
    private static Connection connection = new Connection();
    private int connectionsCount;
    private Semaphore semaphore = new Semaphore(10);

    private Connection() {
    }

    static Connection getInstance() {
        return connection;
    }

    void work() throws InterruptedException {
        semaphore.acquire();
        try {
            doWork();
        } finally {
            semaphore.release();
        }
    }

    private void doWork() throws InterruptedException {
        synchronized (this) {
            connectionsCount++;
            System.out.println(connectionsCount);
        }
        Thread.sleep(5000);
        synchronized (this) {
            connectionsCount--;
        }
    }
}

