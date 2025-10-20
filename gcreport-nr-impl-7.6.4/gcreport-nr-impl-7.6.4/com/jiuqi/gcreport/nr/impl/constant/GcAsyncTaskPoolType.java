/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.nr.impl.constant;

import java.util.HashMap;

public enum GcAsyncTaskPoolType {
    ASYNCTASK_SAMECTRL_EXTRACTREPORTINFO(805, "ASYNCTASK_SAMECTRL_EXTRACTREPORTINFO"),
    ASYNCTASK_CHECKDESCOPYINFO(804, "ASYNCTASK_CHECKDESCOPYINFO"),
    OFFSET(803, "OFFSET"),
    ASYNCTASK_GC_BATCH_COPY(802, "GC_ASYNCTASK_GC_BATCH_COPY"),
    ASYNCTASK_BATCHEFDCCHECK(801, "GC_ASYNCTASK_BATCHEFDCCHECK"),
    ASYNCTASK_BATCHCHECKDESCOPYINFO(806, "ASYNCTASK_BATCHCHECKDESCOPYINFO"),
    ASYNCTASK_GC_BATCH_FORM_LOCK(807, "ASYNCTASK_GC_BATCH_FORM_LOCK"),
    ASYNCTASK_BATCHCHECKDESGATHER(808, "ASYNCTASK_BATCHCHECKDESGATHER"),
    ASYNCTASK_CHECKDESGATHER(809, "ASYNCTASK_CHECKDESGATHER");

    private int typeValue;
    private String typeName;
    private static HashMap<Integer, GcAsyncTaskPoolType> mappings;

    private static synchronized HashMap<Integer, GcAsyncTaskPoolType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private GcAsyncTaskPoolType(int value, String name) {
        this.typeValue = value;
        this.typeName = name;
        GcAsyncTaskPoolType.getMappings().put(value, this);
    }

    public int getValue() {
        return this.typeValue;
    }

    public String getName() {
        return this.typeName;
    }

    public static GcAsyncTaskPoolType forValue(int value) {
        return GcAsyncTaskPoolType.getMappings().get(value);
    }
}

