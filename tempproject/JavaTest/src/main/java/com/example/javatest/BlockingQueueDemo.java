package com.example.javatest;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;


/**
 * Created by suxiaohan on 2018/9/20.
 */

public class BlockingQueueDemo {

    public static void main(String[] args) throws Exception {

//        BlockingQueue queue = new ArrayBlockingQueue(10);
        BlockingQueue queue = new SynchronousQueue();

        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        new Thread(producer).start();
        new Thread(consumer).start();

        Thread.sleep(4000);
    }
}

class Producer implements Runnable {

    protected BlockingQueue queue = null;

    public Producer(BlockingQueue queue) {
        this.queue = queue;
    }

    public void run() {

        for (int i = 0; i < 20; i++) {
            try {
                if (queue.size()==10){
                    System.out.println("queue is full ,wait for consumer");
                }
                queue.put("" + i);
                System.out.println("I put "+i+" in queue");
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable {

    protected BlockingQueue queue = null;

    public Consumer(BlockingQueue queue) {
        this.queue = queue;
    }

    public void run() {

        while (queue.size() >= 0) {

            try {
                System.out.println(queue.take());//
//                System.out.println(queue.remove());//

                Thread.sleep(2500);
            } catch (InterruptedException e) {

            }
        }
//        try {
//            System.out.println(queue.size());
//            System.out.println(queue.take());
////            Thread.sleep(1000);
//            System.out.println(queue.take());
////            Thread.sleep(1000);
//            System.out.println(queue.take());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}