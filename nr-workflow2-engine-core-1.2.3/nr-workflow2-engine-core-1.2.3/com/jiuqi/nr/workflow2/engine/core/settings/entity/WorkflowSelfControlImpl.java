/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.settings.entity;

import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInDaysConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.WorkflowSelfControl;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.StartTimeStrategy;
import java.time.LocalTime;
import java.util.Objects;

public class WorkflowSelfControlImpl
implements WorkflowSelfControl {
    private boolean enable;
    private StartTimeStrategy type;
    private FillInDaysConfig fillInDaysConfig;
    private LocalTime bootTime;

    @Override
    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public StartTimeStrategy getType() {
        return this.type;
    }

    public void setType(StartTimeStrategy type) {
        this.type = type;
    }

    @Override
    public FillInDaysConfig getFillInDaysConfig() {
        return this.fillInDaysConfig;
    }

    public void setFillInDaysConfig(FillInDaysConfig fillInDaysConfig) {
        this.fillInDaysConfig = fillInDaysConfig;
    }

    @Override
    public LocalTime getBootTime() {
        return this.bootTime;
    }

    public void setBootTime(LocalTime bootTime) {
        this.bootTime = bootTime;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        WorkflowSelfControlImpl that = (WorkflowSelfControlImpl)o;
        return this.enable == that.enable && this.type == that.type && Objects.equals(this.fillInDaysConfig, that.fillInDaysConfig) && Objects.equals(this.bootTime, that.bootTime);
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.enable, this.type, this.fillInDaysConfig, this.bootTime});
    }
}

