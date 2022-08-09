package com.example.leetcode.daily.august;

import org.junit.Test;

import java.util.Arrays;

public class Leet0802 {
    class MyCircularQueue {

        private int rear;
        private int front;
        private int[] elements;
        private int capacity;

        public MyCircularQueue(int k) {
            elements = new int[k+1];
            rear = front = 0;
            capacity = k + 1;
        }

        /**
         * 进入队列
         * @param value
         * @return
         */
        public boolean enQueue(int value) {
            if(isFull()) {
                return false;
            }
            elements[rear] = value;
            rear = (rear + 1) % capacity;
            return true;
        }

        public boolean deQueue() {
            if (isEmpty()) {
                return false;
            }
            front = (front + 1) % capacity;
            return true;
        }


        public int Front() {
            if (isEmpty()) {
                return -1;
            }
            return elements[front];
        }

        public int Rear() {
            if (isFull()) {
                return -1;
            }
            return elements[(rear - 1 + capacity) % capacity];
        }

        public boolean isEmpty() {
            return rear == front;
        }

        public boolean isFull() {
            return ((rear + 1) % capacity) == front;
        }
    }

    @Test
    public void main() {
        MyCircularQueue myCircularQueue = new MyCircularQueue(10);
        for (int i = 1; i <12; i++) {
            boolean b = myCircularQueue.enQueue(i);
            System.out.println(i + "----"+ b);
        }
        myCircularQueue.deQueue();
        myCircularQueue.enQueue(11);
        System.out.println(myCircularQueue.Front());
        System.out.println(myCircularQueue.Rear());
        System.out.println(Arrays.toString(myCircularQueue.elements));
        myCircularQueue.deQueue();
        myCircularQueue.enQueue(100);
        System.out.println("-----------------------");
        System.out.println(myCircularQueue.Front());
        System.out.println(myCircularQueue.Rear());
        System.out.println(Arrays.toString(myCircularQueue.elements));

    }
}
