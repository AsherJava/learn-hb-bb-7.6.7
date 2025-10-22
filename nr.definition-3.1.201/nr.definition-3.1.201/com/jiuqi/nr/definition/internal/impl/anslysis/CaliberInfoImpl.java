/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.impl.anslysis;

import com.jiuqi.nr.definition.common.DimensionType;
import com.jiuqi.nr.definition.facade.analysis.CaliberInfo;

public class CaliberInfoImpl
implements CaliberInfo {
    private String key;
    private String code;
    private String title;
    private DimensionType type;
    private String tableKey;
    private String tableCode;
    private String tableName;

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getTableKey() {
        return this.tableKey;
    }

    @Override
    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getTableCode() {
        return this.tableCode;
    }

    @Override
    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    @Override
    public String getTableName() {
        return this.tableName;
    }

    @Override
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public DimensionType getType() {
        return this.type;
    }

    @Override
    public void setType(DimensionType type) {
        this.type = type;
    }
}

