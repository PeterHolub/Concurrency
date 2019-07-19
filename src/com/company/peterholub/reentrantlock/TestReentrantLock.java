package com.company.peterholub.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class TestReentrantLock {
    public static void main(String[] args) {
        Task task = new Task();

        Thread thread1 = new Thread(task::firstThread);
        Thread thread2 = new Thread(task::secondThread);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        task.showCounter();
    }
}

class Task {
    private int counter;
    private Lock lock = new ReentrantLock();

    private void increment() {
        IntStream.range(0, 10000).forEach(i -> counter++);
    }

     void firstThread() {
        lock.lock();
        increment();
        lock.unlock();
    }

     void secondThread() {
        lock.lock();
        increment();
        lock.unlock();
    }

     void showCounter() {
        System.out.println(counter);
    }
}

