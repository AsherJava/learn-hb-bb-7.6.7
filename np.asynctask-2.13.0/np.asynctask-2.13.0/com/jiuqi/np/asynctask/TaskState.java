/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.monitor.State
 */
package com.jiuqi.np.asynctask;

import com.jiuqi.bi.core.jobs.monitor.State;
import java.util.Arrays;
import java.util.Optional;

public enum TaskState {
    WAITING(0, "\u6392\u961f"),
    PROCESSING(1, "\u8fdb\u884c\u4e2d"),
    CANCELED(2, "\u53d6\u6d88"),
    CANCELING(3, "\u53d6\u6d88\u4e2d"),
    FINISHED(4, "\u5b8c\u6210"),
    ERROR(5, "\u5f02\u5e38\u7ed3\u675f"),
    OVERTIME(6, "\u8d85\u65f6"),
    NONE(7, "\u67e5\u8be2\u4e0d\u5230"),
    OUTOFQUEUE(8, "\u8d85\u51fa\u6392\u961f\u6570");

    private int value;
    private String title;

    private TaskState(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static TaskState getTaskStateByValue(int value) {
        Optional<TaskState> authority = Arrays.stream(TaskState.values()).filter(o -> o.getValue() == value).findFirst();
        return authority.orElse(NONE);
    }

    public static TaskState convertRealTimeTaskState(int realTimeTaskState, int realTimeTaskResult) {
        TaskState taskState;
        if (realTimeTaskResult == 5) {
            return OVERTIME;
        }
        if (realTimeTaskResult == 4 || realTimeTaskResult == -100) {
            return ERROR;
        }
        if (realTimeTaskState == State.FINISHED.getValue() && realTimeTaskResult == 2) {
            return CANCELED;
        }
        switch (realTimeTaskState) {
            case -1: {
                taskState = WAITING;
                break;
            }
            case 0: {
                taskState = PROCESSING;
                break;
            }
            case 1: {
                taskState = FINISHED;
                break;
            }
            case -2: {
                taskState = CANCELING;
                break;
            }
            default: {
                taskState = NONE;
            }
        }
        return taskState;
    }
}

