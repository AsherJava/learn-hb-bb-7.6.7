/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.examine.distributor;

import com.jiuqi.nr.examine.bean.ParamExamineDetailInfo;
import com.jiuqi.nr.examine.examiner.AbstractExaminer;
import com.jiuqi.nr.examine.factory.DistributorFactory;
import com.jiuqi.nr.examine.factory.ExaminerFactory;
import com.jiuqi.nr.examine.task.ExamineTask;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ExamineTaskDispatcher {
    protected ExamineTask task;
    protected AbstractExaminer examiner;
    protected List<ExamineTaskDispatcher> childrenDispatcher;

    public ExamineTaskDispatcher(ExamineTask task) {
        this.task = task;
    }

    protected List<ExamineTask> childrenTask() {
        return Collections.emptyList();
    }

    public void dispatch() {
        this.assignTask();
        this.assignChildrenTask();
    }

    public AbstractExaminer getExaminer() {
        return this.examiner;
    }

    public void assignTask() {
        this.examiner = ExaminerFactory.getExaminer(this.task);
        this.examiner.examine();
    }

    public List<ParamExamineDetailInfo> getErrors() {
        ArrayList<ParamExamineDetailInfo> errors = new ArrayList<ParamExamineDetailInfo>();
        if (this.examiner != null && this.examiner.getErrors() != null) {
            errors.addAll(this.examiner.getErrors());
        }
        if (this.childrenDispatcher != null) {
            this.childrenDispatcher.stream().forEach(d -> errors.addAll(d.getErrors()));
        }
        return errors;
    }

    protected void assignChildrenTask() {
        List<ExamineTask> childrenTask = this.childrenTask();
        if (childrenTask.size() > 0) {
            this.childrenDispatcher = new ArrayList<ExamineTaskDispatcher>();
            childrenTask.stream().forEach(task -> {
                ExamineTaskDispatcher distributor = DistributorFactory.getDistributor(task);
                this.childrenDispatcher.add(distributor);
                distributor.dispatch();
            });
        }
    }
}

