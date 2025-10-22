/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bql.intf;

import java.util.HashMap;
import java.util.Map;

public class TableRelation {
    private String srcTableName;
    private String destTableName;
    private Map<String, String> fieldMap = new HashMap<String, String>();

    public String getSrcTableName() {
        return this.srcTableName;
    }

    public void setSrcTableName(String srcTableName) {
        this.srcTableName = srcTableName;
    }

    public String getDestTableName() {
        return this.destTableName;
    }

    public void setDestTableName(String destTableName) {
        this.destTableName = destTableName;
    }

    public Map<String, String> getFieldMap() {
        return this.fieldMap;
    }

    public void setFieldMap(Map<String, String> fieldMap) {
        this.fieldMap = fieldMap;
    }
}

