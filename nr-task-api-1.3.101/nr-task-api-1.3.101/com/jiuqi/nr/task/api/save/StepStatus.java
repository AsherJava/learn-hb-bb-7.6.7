/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonValue
 */
package com.jiuqi.nr.task.api.save;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StepStatus {
    WAIT("wait"),
    PROCESS("process"),
    FINISH("finish"),
    ERROR("error");

    private final String status;

    private StepStatus(String status) {
        this.status = status;
    }

    @JsonValue
    public String getStatus() {
        return this.status;
    }

    @JsonCreator
    public static StepStatus getStatus(String status) {
        for (StepStatus stepStatus : StepStatus.values()) {
            if (!stepStatus.getStatus().equals(status)) continue;
            return stepStatus;
        }
        return null;
    }

    public String toString() {
        return this.status;
    }
}

