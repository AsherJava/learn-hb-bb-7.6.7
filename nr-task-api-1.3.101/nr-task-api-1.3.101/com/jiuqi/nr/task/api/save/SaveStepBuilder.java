/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.save;

import com.jiuqi.nr.task.api.save.SaveStep;
import com.jiuqi.nr.task.api.save.StepDetail;
import com.jiuqi.nr.task.api.save.StepStatus;

public class SaveStepBuilder {
    public String id;
    private Integer total;
    private Integer current;
    private StepStatus status;
    private StepDetail detail;

    public SaveStepBuilder id(String id) {
        this.id = id;
        return this;
    }

    public SaveStepBuilder total(Integer total) {
        this.total = total;
        return this;
    }

    public SaveStepBuilder current(Integer current) {
        this.current = current;
        return this;
    }

    public SaveStepBuilder status(StepStatus status) {
        this.status = status;
        return this;
    }

    public SaveStepBuilder detail(StepDetail detail) {
        this.detail = detail;
        return this;
    }

    public SaveStep build() {
        return new SaveStep(this.id, this.total, this.current, this.status, this.detail);
    }
}

