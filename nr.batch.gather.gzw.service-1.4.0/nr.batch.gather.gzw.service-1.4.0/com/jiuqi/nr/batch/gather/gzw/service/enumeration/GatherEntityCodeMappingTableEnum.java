/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.gather.gzw.service.enumeration;

public enum GatherEntityCodeMappingTableEnum {
    ECM_ENTITY_CODE("ECM_ENTITY_CODE"),
    ECM_TASK("ECM_TASK"),
    ECM_PERIOD("ECM_PERIOD"),
    ECM_GATHER_SCHEME_KEY("ECM_GATHER_SCHEME_KEY"),
    ECM_CUSTOMIZED_CONDITION_CODE("ECM_CUSTOMIZED_CONDITION_CODE"),
    ECM_EXECUTE_DATETIME("ECM_EXECUTE_DATETIME"),
    ECM_ENTITY_ID("ECM_ENTITY_ID");

    public final String column;

    private GatherEntityCodeMappingTableEnum(String column) {
        this.column = column;
    }
}

