/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.asynctask.impl;

import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class LinkedSetBlockingQueue<E>
extends LinkedBlockingQueue<E> {
    private final ReentrantLock takeLock = new ReentrantLock();
    private final ReentrantLock addLock = new ReentrantLock();
    private final transient Condition notEmpty = this.takeLock.newCondition();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean offer(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        ReentrantLock addLock = this.addLock;
        addLock.lock();
        try {
            boolean success;
            boolean bl = success = super.contains(e) ? true : super.offer(e);
            if (success) {
                this.signalNotEmpty();
            }
            boolean bl2 = success;
            return bl2;
        }
        finally {
            addLock.unlock();
        }
    }

    @Override
    public void put(E e) throws InterruptedException {
        if (e == null) {
            throw new NullPointerException();
        }
        try {
            ReentrantLock addLock = this.addLock;
            addLock.lock();
            if (super.contains(e)) {
                return;
            }
            super.put(e);
            this.signalNotEmpty();
        }
        finally {
            this.addLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean add(E e) {
        ReentrantLock addLock = this.addLock;
        addLock.lock();
        try {
            boolean success;
            boolean bl = success = super.contains(e) ? true : super.add(e);
            if (success) {
                this.signalNotEmpty();
            }
            boolean bl2 = success;
            return bl2;
        }
        finally {
            addLock.unlock();
        }
    }

    public Set<E> takeAll() throws InterruptedException {
        Set<E> x;
        try {
            ReentrantLock takeLock = this.takeLock;
            takeLock.lockInterruptibly();
            while (super.size() <= 0) {
                this.notEmpty.await();
            }
            x = this.dequeueAll();
            if (!CollectionUtils.isEmpty(x)) {
                this.notEmpty.signal();
            }
        }
        finally {
            this.takeLock.unlock();
        }
        return x;
    }

    private Set<E> dequeueAll() {
        Set x = super.stream().collect(Collectors.toSet());
        super.removeAll(x);
        return x;
    }

    private void signalNotEmpty() {
        ReentrantLock takeLock = this.takeLock;
        takeLock.lock();
        try {
            this.notEmpty.signal();
        }
        finally {
            takeLock.unlock();
        }
    }
}

