/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 */
package com.jiuqi.gcreport.onekeymerge.util;

import com.jiuqi.nr.common.constant.AsynctaskPoolType;

public enum OneKeyTaskPoolEnum {
    DATAPICK("dataPick", AsynctaskPoolType.ASYNCTASK_EFDC.getName()),
    CONVERSION("conversion", "GC_ASYNCTASK_CONVERSION"),
    CALC("calc", "GC_ASYNCTASK_CALC"),
    FINISHCALC("finishCalc", "GC_ASYNCTASK_FINISHCALC");

    private String taskCode;
    private String taskPool;

    private OneKeyTaskPoolEnum(String taskCode, String taskPool) {
        this.taskCode = taskCode;
        this.taskPool = taskPool;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskPool() {
        return this.taskPool;
    }

    public void setTaskPool(String taskPool) {
        this.taskPool = taskPool;
    }

    public static OneKeyTaskPoolEnum getByTaskCode(String code) {
        for (OneKeyTaskPoolEnum state : OneKeyTaskPoolEnum.values()) {
            if (!state.taskCode.equalsIgnoreCase(code)) continue;
            return state;
        }
        return null;
    }
}

