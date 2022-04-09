package com.example.handle;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadTest{
    @Deprecated
    public static void main1(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        AtomicInteger atomicInteger = new AtomicInteger();
        ExecutorService executorService1 = Executors.newFixedThreadPool(10);
        executorService1.submit(() -> {
            try {
                int Cnt = task();
                countDownLatch.countDown();
                System.out.println(Thread.currentThread()+"执行任务...");
                atomicInteger.getAndAdd(Cnt);
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                 executorService1.shutdown();
            }
        });
        countDownLatch.await();

    }

    public static int task() {
        return 1;
    }

    //间隔输出；
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService1 = Executors.newFixedThreadPool(3);
        for (int i = 1; i < 10; i++) {
            int finalI = i;
            Future<?> sub = executorService1.submit(() -> {
                try {
                    System.out.print(Thread.currentThread());
                    System.out.println(finalI);
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            System.out.println(sub.get());
        }
        executorService1.shutdown();
    }
}
