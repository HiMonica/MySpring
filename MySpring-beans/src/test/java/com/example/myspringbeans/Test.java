package com.example.myspringbeans;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class Test {
    public static void main(String[] args) {
        ThreadLocal<Object> objectThreadLocal = new ThreadLocal<>();
        new Thread(() -> {
           objectThreadLocal.set(1);
        }).start();
    }
}

abstract class MyTest{

    public void test(){
        refreshBeanFactory();
    }

    public abstract void refreshBeanFactory();
}

class MyTest1 extends MyTest{

    @Override
    public void refreshBeanFactory() {
        Semaphore semaphore = new Semaphore(1);
        semaphore.tryAcquire(1);
        System.out.println("MyTest1");

        LongAdder longAdder = new LongAdder();
        longAdder.increment();
        AtomicLong atomicLong = new AtomicLong();
        long l = atomicLong.incrementAndGet();
    }
}

class MyTest2 extends MyTest{

    @Override
    public void refreshBeanFactory() {
        System.out.println("MyTest2");
    }
}

class Key implements Comparable<Key>{

    @Override
    public int compareTo(Key o) {
        return -1;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }
}


