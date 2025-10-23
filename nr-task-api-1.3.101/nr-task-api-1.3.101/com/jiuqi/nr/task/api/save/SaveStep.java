/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.save;

import com.jiuqi.nr.task.api.save.SaveStepBuilder;
import com.jiuqi.nr.task.api.save.StepDetail;
import com.jiuqi.nr.task.api.save.StepStatus;
import java.io.Serializable;

public class SaveStep
implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String id;
    private final Integer total;
    private final Integer current;
    private final StepStatus status;
    private final StepDetail detail;

    public static SaveStepBuilder builder() {
        return new SaveStepBuilder();
    }

    public SaveStep(String id, Integer total, Integer current, StepStatus status, StepDetail detail) {
        this.id = id;
        this.total = total;
        this.current = current;
        this.status = status;
        this.detail = detail;
    }

    public String getId() {
        return this.id;
    }

    public StepDetail getDetail() {
        return this.detail;
    }

    public Integer getTotal() {
        return this.total;
    }

    public Integer getCurrent() {
        return this.current;
    }

    public StepStatus getStatus() {
        return this.status;
    }
}

