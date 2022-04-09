package com.example.leetcode.thread;

public class ThreadOrderWithSync {

    private volatile int flag = 'A';
    private final static Object LOCK = new Object();
    private volatile int count = 0;

    Runnable a = () -> {
        while (count < 100) {
            synchronized (LOCK) {
                if (flag == 'A' ) {
                    System.out.println(Thread.currentThread().getName()+"A");
                    count++;
                    flag = 'B';
                    // let other thread race to get the monitor
                    LOCK.notifyAll();
                } else {
                    try {
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    Runnable b = () -> {
        while (count < 100) {
            synchronized (LOCK) {
                if (flag == 'B' ) {
                    System.out.println(Thread.currentThread().getName()+"B");
                    count++;
                    flag = 'C';
                    // let other thread race to get the monitor
                    LOCK.notifyAll();
                } else {
                    try {
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    Runnable c = () -> {
        while (count < 100) {
            synchronized (LOCK) {
                if (flag == 'C' ) {
                    System.out.println(Thread.currentThread().getName()+"C");
                    count++;

                    flag = 'A';
                    // let other thread race to get the monitor
                    LOCK.notifyAll();
                } else {
                    try {
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    public void runTest() {
        Thread ta = new Thread(a);
        Thread tb = new Thread(b);
        Thread tc = new Thread(c);

        ta.start();
        tb.start();
        tc.start();
    }

    public static void main(String[] args) {
        ThreadOrderWithSync sync = new ThreadOrderWithSync();
        sync.runTest();
    }
}