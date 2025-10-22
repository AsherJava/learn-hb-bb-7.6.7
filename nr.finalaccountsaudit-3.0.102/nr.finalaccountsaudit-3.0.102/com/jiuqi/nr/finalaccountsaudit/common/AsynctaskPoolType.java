/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.common;

import java.util.HashMap;

public enum AsynctaskPoolType {
    ASYNCTASK_INTEGRITYCHECK(501, "ASYNCTASK_INTEGRITYCHECK"),
    ASYNCTASK_QUERYENUMDATA(509, "ASYNCTASK_QUERYENUMDATA"),
    ASYNCTASK_ENUMDATACHECK(510, "ASYNCTASK_ENUMDATACHECK"),
    ASYNCTASK_ENTITYCHECK(511, "ASYNCTASK_ENTITYCHECK"),
    ASYNCTASK_MULTCHECK(515, "ASYNCTASK_MULTCHECK"),
    ASYNCTASK_QUERYCHECK(516, "ASYNCTASK_QUERYCHECK"),
    ASYNCTASK_BLOBFILESIZECHECK(517, "ASYNCTASK_BLOBFILESIZECHECK"),
    ASYNCTASK_EXPLAININFOCHECK(518, "ASYNCTASK_EXPLAININFOCHECK"),
    ASYNCTASK_ENTITYTREECHECK(519, "ASYNCTASK_ENTITYTREECHECK"),
    ASYNCTASK_ZBQUERYCHECK(520, "ASYNCTASK_ZBQUERYCHECK"),
    ASYNCTASK_DATAANALYSISCHECK(521, "ASYNCTASK_DATAANALYSISCHECK");

    private static HashMap<Integer, AsynctaskPoolType> mappings;
    private final int typeValue;
    private final String typeName;

    private AsynctaskPoolType(int value, String name) {
        this.typeValue = value;
        this.typeName = name;
        AsynctaskPoolType.getMappings().put(value, this);
    }

    private static synchronized HashMap<Integer, AsynctaskPoolType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    public static AsynctaskPoolType forValue(int value) {
        return AsynctaskPoolType.getMappings().get(value);
    }

    public int getValue() {
        return this.typeValue;
    }

    public String getName() {
        return this.typeName;
    }
}

