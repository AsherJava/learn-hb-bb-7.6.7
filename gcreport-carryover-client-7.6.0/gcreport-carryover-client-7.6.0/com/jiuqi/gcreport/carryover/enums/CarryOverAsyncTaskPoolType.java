/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.carryover.enums;

import java.util.HashMap;

public enum CarryOverAsyncTaskPoolType {
    ASYNCTASK_OFFSET("gcOffsetCarryOver", "ASYNCTASK_CARRYOVER_OFFSET"),
    ASYNCTASK_INVEST("gcInvestCarryOver", "ASYNCTASK_CARRYOVER_INVEST");

    private String typeCode;
    private String typeName;
    private static HashMap<String, CarryOverAsyncTaskPoolType> mappings;

    public static CarryOverAsyncTaskPoolType getEnumByCode(String code) {
        for (CarryOverAsyncTaskPoolType asyncTaskPoolType : CarryOverAsyncTaskPoolType.values()) {
            if (!asyncTaskPoolType.getCode().equals(code)) continue;
            return asyncTaskPoolType;
        }
        return null;
    }

    private static synchronized HashMap<String, CarryOverAsyncTaskPoolType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private CarryOverAsyncTaskPoolType(String code, String name) {
        this.typeCode = code;
        this.typeName = name;
        CarryOverAsyncTaskPoolType.getMappings().put(code, this);
    }

    public String getCode() {
        return this.typeCode;
    }

    public String getName() {
        return this.typeName;
    }

    public static CarryOverAsyncTaskPoolType forValue(String code) {
        return CarryOverAsyncTaskPoolType.getMappings().get(code);
    }
}

