/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.examine.distributor;

import com.jiuqi.nr.examine.factory.DistributorFactory;
import com.jiuqi.nr.examine.task.ExamineTask;
import java.util.Collections;
import java.util.List;

public abstract class RestoreTaskDispatcher {
    protected ExamineTask task;

    public RestoreTaskDispatcher(ExamineTask task) {
        this.task = task;
    }

    protected List<ExamineTask> childrenTask() {
        return Collections.emptyList();
    }

    public void dispatch() {
        this.assignTask();
        this.assignChildrenTask();
    }

    protected abstract void assignTask();

    protected void assignChildrenTask() {
        List<ExamineTask> childrenTask = this.childrenTask();
        childrenTask.stream().forEach(task -> DistributorFactory.getDistributor(task).dispatch());
    }
}

