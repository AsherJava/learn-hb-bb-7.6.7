/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.concurrent;

import com.jiuqi.bi.concurrent.ScheduleException;
import com.jiuqi.bi.trace.ResourceMonitor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class Scheduler<T> {
    private final TThread[] consumers;
    private final TThread producer;
    private final Semaphore lock = new Semaphore(0);
    private volatile State currentState;
    private volatile boolean hasError;
    private volatile ITaskListener<T> listener;
    private final Map<String, Task<T>> tasks;
    private final Queue<Task<T>> waitTasks;
    private final Queue<Task<T>> readyTasks;
    private long maxSleepMills = 256L;
    private boolean ignoreMissDepends = false;
    private static final String TRACE_CATAGORY = "SCHEDULE";

    public Scheduler(int threads) {
        this(threads, null);
    }

    public Scheduler(int threads, ITaskListener<T> listener) {
        this.consumers = new TThread[threads];
        for (int i = 0; i < threads; ++i) {
            this.consumers[i] = new TThread(new Consumer(), "CONSUMER-" + Integer.toString(i + 1));
        }
        this.producer = new TThread(new Producer(), "PRODUCER");
        this.currentState = State.WAITING;
        this.tasks = new ConcurrentHashMap<String, Task<T>>();
        this.waitTasks = new ConcurrentLinkedQueue<Task<T>>();
        this.readyTasks = new ConcurrentLinkedQueue<Task<T>>();
        this.listener = listener;
    }

    public synchronized void start() {
        if (this.currentState != State.WAITING) {
            throw new IllegalStateException();
        }
        this.producer.start();
        ResourceMonitor.createResource(this.producer.target);
        for (TThread t : this.consumers) {
            t.start();
            ResourceMonitor.createResource(t.target);
        }
        this.currentState = State.RUNNING;
    }

    private void checkTask(Task<T> task) throws ScheduleException {
        if (task.task == null) {
            throw new ScheduleException("\u4efb\u52a1\u4e0d\u5141\u8bb8\u4e3a\u7a7a\uff01");
        }
        if (this.tasks.containsKey(task.name)) {
            throw new ScheduleException("\u91cd\u590d\u7684\u8c03\u5ea6\u4efb\u52a1\u540d\u79f0\uff01");
        }
        if (task.depends != null && task.depends.length > 0) {
            HashSet<Task<T>> visited = new HashSet<Task<T>>();
            this.detectCycle(task, visited, new Stack<String>());
        }
    }

    private void detectCycle(Task<T> current, Set<Task<T>> visited, Stack<String> route) throws ScheduleException {
        if (visited.contains(current)) {
            route.add(current.name);
            throw new ScheduleException("\u4efb\u52a1\u4f9d\u8d56\u5b58\u5728\u5faa\u73af\u8def\u5f84\uff1a" + route.toString());
        }
        if (current.depends == null || current.depends.length == 0) {
            return;
        }
        visited.add(current);
        route.push(current.name);
        for (String depend : current.depends) {
            Task<T> t = this.tasks.get(depend);
            if (t == null && !route.isEmpty() && depend.equals(route.firstElement())) {
                route.add(depend);
                throw new ScheduleException("\u4efb\u52a1\u4f9d\u8d56\u5b58\u5728\u5faa\u73af\u8def\u5f84\uff1a" + route.toString());
            }
            if (t == null) continue;
            this.detectCycle(t, visited, route);
        }
        route.pop();
        visited.remove(current);
    }

    public void addTask(Callable<T> task) throws ScheduleException {
        this.addTask(null, task);
    }

    public void addTask(String taskName, Callable<T> task) throws ScheduleException {
        Task<T> newTask = new Task<T>(taskName, task, null);
        if (taskName != null) {
            this.checkTask(newTask);
            this.tasks.put(taskName, newTask);
        }
        this.readyTasks.offer(newTask);
        this.lock.release();
    }

    public void addTask(String taskName, Callable<T> task, String depend) throws ScheduleException {
        this.addTask(taskName, task, new String[]{depend});
    }

    public void addTask(String taskName, Callable<T> task, String[] depends) throws ScheduleException {
        Task<T> newTask = new Task<T>(taskName, task, depends);
        this.checkTask(newTask);
        this.tasks.put(taskName, newTask);
        this.waitTasks.offer(newTask);
    }

    public State getState(String taskName) {
        Task<T> task = this.tasks.get(taskName);
        return task == null ? null : task.state.get();
    }

    public T getResult(String taskName) {
        Task<T> t = this.tasks.get(taskName);
        return t == null || t.state.get() != State.TERMINATED ? null : (T)t.result;
    }

    public boolean hasError() {
        return this.hasError;
    }

    public void join() throws InterruptedException {
        long s = 1L;
        while (this.currentState == State.RUNNING) {
            if (this.tryJoin(0L)) {
                return;
            }
            Thread.sleep(s);
            if (s >= this.maxSleepMills) continue;
            s *= 2L;
        }
    }

    public boolean tryJoin(long timeout) throws InterruptedException {
        if (timeout < 0L) {
            throw new IllegalArgumentException();
        }
        long startTime = System.currentTimeMillis();
        do {
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            boolean allOK = true;
            for (Task<T> t : this.tasks.values()) {
                if (t.state.get() == State.TERMINATED) continue;
                allOK = false;
                break;
            }
            if (!allOK) continue;
            return true;
        } while (startTime + timeout >= System.currentTimeMillis());
        return false;
    }

    public void join(String taskName) throws InterruptedException {
        while (this.currentState == State.RUNNING) {
            if (!this.tryJoin(taskName, 0L)) continue;
            return;
        }
    }

    public boolean tryJoin(String taskName, long timeout) throws InterruptedException {
        if (timeout < 0L) {
            throw new IllegalArgumentException();
        }
        long startTime = System.currentTimeMillis();
        Task<T> t = this.tasks.get(taskName);
        if (t == null) {
            return false;
        }
        do {
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            if (t.state.get() != State.TERMINATED) continue;
            return true;
        } while (startTime + timeout >= System.currentTimeMillis());
        return false;
    }

    public void join(String[] taskNames) throws InterruptedException {
        while (this.currentState == State.RUNNING) {
            if (!this.tryJoin(taskNames, 0L)) continue;
            return;
        }
    }

    public boolean tryJoin(String[] taskNames, long timeout) throws InterruptedException {
        if (timeout < 0L) {
            throw new IllegalArgumentException();
        }
        long startTime = System.currentTimeMillis();
        do {
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            boolean allOK = true;
            for (String t : taskNames) {
                if (this.getState(t) == State.TERMINATED) continue;
                allOK = false;
                break;
            }
            if (!allOK) continue;
            return true;
        } while (startTime + timeout >= System.currentTimeMillis());
        return false;
    }

    public synchronized void stop(long timeout) {
        if (timeout < 0L) {
            throw new IllegalArgumentException();
        }
        if (this.currentState != State.RUNNING) {
            return;
        }
        this.currentState = State.TERMINATED;
        this.lock.release(this.consumers.length);
        long startTime = System.currentTimeMillis();
        try {
            this.producer.join(timeout);
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        if (this.producer.isAlive()) {
            this.producer.interrupt();
        }
        ResourceMonitor.closeResource(this.producer.target);
        for (TThread t : this.consumers) {
            if (timeout > 0L) {
                long nextTime = System.currentTimeMillis();
                timeout -= nextTime - startTime;
                startTime = nextTime;
            }
            try {
                if (timeout > 0L) {
                    t.join(timeout);
                }
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
            if (t.isAlive()) {
                t.interrupt();
            }
            ResourceMonitor.closeResource(t.target);
        }
    }

    public void setListener(ITaskListener<T> listener) {
        this.listener = listener;
    }

    public ITaskListener<T> getListener() {
        return this.listener;
    }

    public void setMaxSleepMills(long mills) {
        this.maxSleepMills = mills;
    }

    public long getMaxSleepMills() {
        return this.maxSleepMills;
    }

    public void setIgnoreMissDepends(boolean ignore) {
        this.ignoreMissDepends = ignore;
    }

    public int statWorkers() {
        try {
            Thread.sleep(0L);
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        int c = 0;
        TThread p = this.producer;
        if (p != null && p.isAlive()) {
            ++c;
        }
        for (TThread t : this.consumers) {
            if (t == null || !t.isAlive()) continue;
            ++c;
        }
        return c;
    }

    public boolean getIgnoreMissDepends() {
        return this.ignoreMissDepends;
    }

    static {
        ResourceMonitor.defineResource(TRACE_CATAGORY, "PRODUCER", IProducer.class);
        ResourceMonitor.defineResource(TRACE_CATAGORY, "CONSUMER", IConsumer.class);
    }

    private static final class TThread
    extends Thread {
        public final Runnable target;

        public TThread(Runnable target, String name) {
            super(target, name);
            this.target = target;
        }
    }

    private static final class Task<V> {
        public final String name;
        public final String[] depends;
        public final AtomicReference<State> state;
        public volatile V result;
        public final Callable<V> task;

        public Task(String name, Callable<V> task, String[] depends) {
            this.name = name;
            this.task = task;
            this.depends = depends;
            this.state = new AtomicReference<State>(depends == null || depends.length == 0 ? State.WAITING : State.BLOCKED);
        }

        public void invoke() throws Exception {
            this.result = this.task.call();
        }

        public int hashCode() {
            return this.name == null ? 0 : this.name.hashCode();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Task)) {
                return false;
            }
            String another = ((Task)obj).name;
            return this.name == null ? another == null : this.name.equals(another);
        }

        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append('[').append(this.name).append(',').append(this.state).append(',').append(Arrays.toString(this.depends)).append(',').append(this.result).append(']');
            return buffer.toString();
        }
    }

    private final class Consumer
    implements IConsumer {
        private Consumer() {
        }

        @Override
        public void run() {
            while (Scheduler.this.currentState != State.TERMINATED) {
                Task t;
                block9: {
                    block8: {
                        long s = 1L;
                        try {
                            if (Scheduler.this.lock.tryAcquire(1L, TimeUnit.MILLISECONDS)) {
                                s = 1L;
                                break block8;
                            }
                            Thread.sleep(s);
                            if (s >= Scheduler.this.maxSleepMills) continue;
                            s *= 2L;
                            continue;
                        }
                        catch (InterruptedException e) {
                            break;
                        }
                    }
                    t = (Task)Scheduler.this.readyTasks.poll();
                    if (t == null) continue;
                    ITaskListener l = Scheduler.this.listener;
                    try {
                        t.invoke();
                        if (l != null) {
                            l.notifyFinish(t.name, t.result);
                        }
                    }
                    catch (Exception e) {
                        if (l != null) {
                            l.handleError(t.name, e);
                        }
                        Scheduler.this.currentState = State.TERMINATED;
                        Scheduler.this.hasError = true;
                        if (Scheduler.this.consumers.length <= 1) break block9;
                        Scheduler.this.lock.release(Scheduler.this.consumers.length - 1);
                    }
                }
                t.state.set(State.TERMINATED);
            }
        }
    }

    private final class Producer
    implements IProducer {
        private Producer() {
        }

        @Override
        public void run() {
            long s = 1L;
            while (Scheduler.this.currentState != State.TERMINATED) {
                boolean hit = false;
                Iterator itr = Scheduler.this.waitTasks.iterator();
                while (itr.hasNext()) {
                    Task t = (Task)itr.next();
                    boolean ready = true;
                    for (String prev : t.depends) {
                        State state = Scheduler.this.getState(prev);
                        if (state == null) {
                            if (Scheduler.this.ignoreMissDepends) continue;
                            ready = false;
                            break;
                        }
                        if (state == State.TERMINATED) continue;
                        ready = false;
                        break;
                    }
                    if (!ready) continue;
                    itr.remove();
                    t.state.set(State.WAITING);
                    Scheduler.this.readyTasks.offer(t);
                    Scheduler.this.lock.release();
                    hit = true;
                }
                try {
                    Thread.sleep(s);
                }
                catch (InterruptedException e) {
                    break;
                }
                if (hit) {
                    s = 1L;
                    continue;
                }
                if (s >= Scheduler.this.maxSleepMills) continue;
                s *= 2L;
            }
        }
    }

    public static interface IConsumer
    extends Runnable {
    }

    public static interface IProducer
    extends Runnable {
    }

    public static interface ITaskListener<T> {
        public void handleError(String var1, Throwable var2);

        public void notifyFinish(String var1, T var2);
    }

    public static enum State {
        BLOCKED,
        WAITING,
        RUNNING,
        TERMINATED;

    }
}

