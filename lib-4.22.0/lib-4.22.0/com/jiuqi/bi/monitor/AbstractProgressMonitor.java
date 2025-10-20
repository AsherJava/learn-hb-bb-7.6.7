/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.monitor;

import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.bi.monitor.ProgressException;
import com.jiuqi.bi.monitor.ProgressTask;
import com.jiuqi.bi.text.DecimalFormat;
import java.math.RoundingMode;
import java.util.Stack;

public abstract class AbstractProgressMonitor
implements IProgressMonitor {
    private volatile boolean canceled;
    private volatile double position;
    private double notifyPos;
    private double delta = 1.0E-4;
    private Stack<ProgressTask> tasks = new Stack();
    private static final double POS_DELTA = 1.0E-4;

    @Override
    public void cancel() {
        this.canceled = true;
    }

    @Override
    public void finishTask() {
        if (this.tasks.isEmpty()) {
            return;
        }
        this.tasks.pop();
        if (this.tasks.isEmpty()) {
            this.position = 1.0;
            this.notify(this.position);
            this.notifyPos = this.position;
        }
    }

    @Override
    public void finishTask(String taskName) throws ProgressException {
        if (this.tasks.isEmpty()) {
            throw new ProgressException("\u8fdb\u5ea6\u63a7\u5236\u9519\u8bef\uff0c\u91cd\u590d\u7ed3\u675f\u6216\u7ed3\u675f\u672a\u542f\u52a8\u7684\u4efb\u52a1[" + taskName + "]\u3002");
        }
        ProgressTask task = this.tasks.peek();
        if (!taskName.equals(task.name())) {
            throw new ProgressException("\u8fdb\u5ea6\u63a7\u5236\u9519\u8bef\uff0c\u5728\u5f53\u524d\u4efb\u52a1\u4e3a[" + task.name() + "]\u65f6\u5c1d\u8bd5\u7ed3\u675f\u4efb\u52a1[" + taskName + "]");
        }
        this.tasks.pop();
        if (this.tasks.isEmpty()) {
            this.position = 1.0;
            this.notify(this.position);
            this.notifyPos = this.position;
        }
    }

    @Override
    public double getPosition() {
        return this.position;
    }

    @Override
    public boolean isCanceled() {
        return this.canceled;
    }

    @Override
    public abstract void prompt(String var1);

    @Override
    public void startTask(String taskName, int stepCount) {
        ProgressTask curTask = this.tasks.isEmpty() ? null : this.tasks.peek();
        curTask = new ProgressTask(curTask, taskName, stepCount);
        this.tasks.push(curTask);
    }

    @Override
    public void startTask(String taskName, int[] steps) {
        ProgressTask curTask = this.tasks.isEmpty() ? null : this.tasks.peek();
        curTask = new ProgressTask(curTask, taskName, steps);
        this.tasks.push(curTask);
    }

    protected abstract void notify(double var1);

    @Override
    public void stepIn() {
        if (this.tasks.empty()) {
            return;
        }
        ProgressTask curTask = this.tasks.peek();
        curTask.stepIn();
        double pos = curTask.position();
        if (pos > this.position) {
            if (pos - this.notifyPos > this.delta) {
                this.notify(pos);
                this.notifyPos = pos;
            }
            this.position = pos;
        }
    }

    @Override
    public String getCurrentTask() {
        return this.tasks.isEmpty() ? null : this.tasks.peek().name();
    }

    @Override
    public int getCurrentLevel() {
        return this.tasks.size();
    }

    public void setPosDelta(double delta) {
        this.delta = delta;
    }

    public String toString() {
        DecimalFormat fmt = new DecimalFormat("0.00%");
        fmt.setRoundingMode(RoundingMode.HALF_UP);
        return fmt.format(this.position) + " @" + this.tasks.toString();
    }
}

