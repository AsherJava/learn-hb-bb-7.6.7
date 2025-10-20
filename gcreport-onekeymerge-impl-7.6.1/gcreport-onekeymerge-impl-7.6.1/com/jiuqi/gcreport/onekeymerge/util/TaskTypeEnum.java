/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.onekeymerge.util;

import com.jiuqi.gcreport.onekeymerge.task.GcCalcAndFinishTaskImpl;
import com.jiuqi.gcreport.onekeymerge.task.GcCenterTask;
import com.jiuqi.gcreport.onekeymerge.task.GcConversionTaskImpl;
import com.jiuqi.gcreport.onekeymerge.task.GcDataPickTaskImpl;
import com.jiuqi.gcreport.onekeymerge.task.GcDataProcessTaskImpl;
import com.jiuqi.gcreport.onekeymerge.task.GcDoCalcTaskImpl;
import com.jiuqi.gcreport.onekeymerge.task.GcFinishCalcTaskImpl;
import com.jiuqi.gcreport.onekeymerge.task.GcRelationToMergeTaskImpl;
import java.util.ArrayList;
import java.util.List;

public enum TaskTypeEnum {
    DATAPROCESS(0, "dataProcess", "\u6570\u636e\u9884\u5904\u7406", GcDataProcessTaskImpl.class),
    DATAPICK(1, "dataPick", "\u6570\u636e\u63d0\u53d6", GcDataPickTaskImpl.class),
    CONVERSION(2, "conversion", "\u6279\u91cf\u6298\u7b97", GcConversionTaskImpl.class),
    CALC(3, "calc", "\u5408\u5e76\u8ba1\u7b97", GcDoCalcTaskImpl.class),
    FINISHCALC(4, "finishCalc", "\u5b8c\u6210\u5408\u5e76", GcFinishCalcTaskImpl.class),
    RELATIONTOMERGE(5, "relationToMerge", "\u5173\u8054\u8f6c\u5408\u5e76", GcRelationToMergeTaskImpl.class),
    CALCANDFINISH(6, "calcAndFinish", "\u5408\u5e76\u8ba1\u7b97/\u5b8c\u6210\u5408\u5e76", GcCalcAndFinishTaskImpl.class);

    private int state;
    private String code;
    private String stateInfo;
    private Class<? extends GcCenterTask> clazz;
    public static List<TaskTypeEnum> MERGEORGTASKS;
    public static List<TaskTypeEnum> SINGLEORGTASKS;

    private TaskTypeEnum(int state, String code, String stateInfo, Class<? extends GcCenterTask> clazz) {
        this.state = state;
        this.code = code;
        this.stateInfo = stateInfo;
        this.clazz = clazz;
    }

    public int getState() {
        return this.state;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return this.stateInfo;
    }

    public Class<? extends GcCenterTask> getClazz() {
        return this.clazz;
    }

    public void setClazz(Class<? extends GcCenterTask> clazz) {
        this.clazz = clazz;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public static TaskTypeEnum statOf(int index) {
        for (TaskTypeEnum state : TaskTypeEnum.values()) {
            if (state.getState() != index) continue;
            return state;
        }
        return null;
    }

    public static TaskTypeEnum getByCode(String code) {
        for (TaskTypeEnum state : TaskTypeEnum.values()) {
            if (!state.code.equalsIgnoreCase(code)) continue;
            return state;
        }
        return null;
    }

    static {
        MERGEORGTASKS = new ArrayList<TaskTypeEnum>();
        SINGLEORGTASKS = new ArrayList<TaskTypeEnum>();
        MERGEORGTASKS.add(FINISHCALC);
        MERGEORGTASKS.add(CALC);
        MERGEORGTASKS.add(CONVERSION);
        SINGLEORGTASKS.add(CONVERSION);
        SINGLEORGTASKS.add(DATAPICK);
    }
}

