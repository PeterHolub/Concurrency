package com.company.peterholub.waitnotify;

import java.util.LinkedList;
import java.util.Queue;


public class TestWaitNotify {
    public static void main(String[] args) throws InterruptedException {
        ProducerConsumer producerConsumer = new ProducerConsumer();

        Thread thread1 = new Thread(() -> {
            try {
                producerConsumer.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                producerConsumer.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}

class ProducerConsumer {
    private final Object lock = new Object();
    private Queue<Integer> queue = new LinkedList<>();

     void produce() throws InterruptedException {
        int value = 0;

        while (true) {
            synchronized (lock) {
                int LIMIT = 10;
                while (queue.size() == LIMIT) {
                    lock.wait();
                }

                queue.offer(value++);
                lock.notify();
            }
        }

    }

     void consume() throws InterruptedException {
        while (true) {
            synchronized (lock) {
                while (queue.size() == 0) {
                    lock.wait();
                }

                int value = queue.poll();
                System.out.println(value);
                System.out.println("Queue size is " + queue.size());
                lock.notify();
            }

            Thread.sleep(1000);
        }
    }
}