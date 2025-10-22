/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.dispatch;

import com.jiuqi.nr.attachment.transfer.dispatch.TransferThread;
import com.jiuqi.nr.attachment.transfer.log.AttachmentLogHelper;
import com.jiuqi.nr.attachment.transfer.monitor.TaskStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class TaskScheduler {
    private final ArrayBlockingQueue<TransferThread> queue;
    private final ReentrantLock mainLock = new ReentrantLock();
    private final int MAX_THREAD;
    private final AtomicBoolean alive = new AtomicBoolean(true);
    private final Semaphore semaphore = new Semaphore(2, true);
    private final List<TransferThread> workers = new ArrayList<TransferThread>();

    public TaskScheduler(int threadCount) {
        this.MAX_THREAD = threadCount;
        this.queue = new ArrayBlockingQueue(threadCount, true);
    }

    public void addTask(TransferThread task) throws InterruptedException {
        ReentrantLock lock = this.mainLock;
        this.queue.put(task);
        try {
            lock.lock();
            this.workers.add(task);
        }
        finally {
            lock.unlock();
        }
    }

    public void submitTask(TransferThread task) {
        task.getMonitor().setStatus(TaskStatus.READY);
        task.start();
    }

    public TransferThread take() throws InterruptedException {
        return this.queue.poll(10L, TimeUnit.SECONDS);
    }

    public boolean fullWork() {
        return this.getActiveCount() >= (long)this.MAX_THREAD;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long getActiveCount() {
        ReentrantLock lock = this.mainLock;
        int n = 0;
        try {
            lock.lock();
            for (TransferThread worker : this.workers) {
                if (worker.getMonitor().getStatus() != TaskStatus.RUNNING && worker.getMonitor().getStatus() != TaskStatus.READY) continue;
                ++n;
            }
        }
        finally {
            lock.unlock();
        }
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void closeLatest() {
        ReentrantLock lock = this.mainLock;
        try {
            lock.lock();
            for (int i = this.workers.size() - 1; i > 0; --i) {
                TransferThread transferThread = this.workers.get(i);
                if (transferThread.getMonitor().getStatus() != TaskStatus.READY && transferThread.getMonitor().getStatus() != TaskStatus.RUNNING) continue;
                AttachmentLogHelper.debug("\u53d6\u6d88\u4efb\u52a1\uff1a" + transferThread.getTaskId());
                transferThread.stopThead();
                this.workers.remove(i);
                break;
            }
        }
        finally {
            lock.unlock();
        }
    }

    public boolean hasTask() {
        return !this.queue.isEmpty() || this.getActiveCount() > 0L;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void shutdown(boolean cancel, boolean force) {
        this.alive.compareAndSet(true, false);
        if (!force) {
            return;
        }
        ReentrantLock lock = this.mainLock;
        try {
            lock.lock();
            AttachmentLogHelper.debug("\u6e05\u7a7a\u961f\u5217");
            this.queue.clear();
            for (TransferThread worker : this.workers) {
                if (worker.getMonitor().getStatus() != TaskStatus.RUNNING && worker.getMonitor().getStatus() != TaskStatus.READY) continue;
                try {
                    worker.setStatus(cancel ? TaskStatus.CANCEL : TaskStatus.INTERRUPT);
                }
                catch (Exception e) {
                    AttachmentLogHelper.error(e.getMessage(), e);
                }
            }
            this.workers.clear();
        }
        finally {
            lock.unlock();
        }
    }

    public boolean isAlive() {
        return this.alive.get();
    }

    public void lock() {
        try {
            this.semaphore.acquire(1);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void unlock() {
        this.semaphore.release(1);
    }

    public void pause() {
        try {
            this.semaphore.tryAcquire(2L, TimeUnit.MINUTES);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void resume() {
        this.semaphore.release(2);
    }

    public int thread() {
        return this.MAX_THREAD;
    }
}

