/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 */
package com.jiuqi.nr.task.form.dto;

import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;

public class EntityFieldDTO {
    private String key;
    private String code;
    private String title;
    private String category;
    private Integer dataFieldType = 0;
    private Integer precision = 0;
    private Integer decimal = 0;
    private String refDataEntityKey;
    private String refDataEntityCode;
    private String refDataEntityTitle;
    private Integer dataFieldGatherType = DataFieldGatherType.NONE.getValue();
    private String tableName;
    protected Boolean nullable = false;

    public String getRefDataEntityTitle() {
        return this.refDataEntityTitle;
    }

    public void setRefDataEntityTitle(String refDataEntityTitle) {
        this.refDataEntityTitle = refDataEntityTitle;
    }

    public String getRefDataEntityKey() {
        return this.refDataEntityKey;
    }

    public void setRefDataEntityKey(String refDataEntityKey) {
        this.refDataEntityKey = refDataEntityKey;
    }

    public Integer getDataFieldType() {
        return this.dataFieldType;
    }

    public void setDataFieldType(Integer dataFieldType) {
        this.dataFieldType = dataFieldType;
    }

    public Integer getPrecision() {
        return this.precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    public Integer getDecimal() {
        return this.decimal;
    }

    public void setDecimal(Integer decimal) {
        this.decimal = decimal;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getDataFieldGatherType() {
        return this.dataFieldGatherType;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRefDataEntityCode() {
        return this.refDataEntityCode;
    }

    public void setRefDataEntityCode(String refDataEntityCode) {
        this.refDataEntityCode = refDataEntityCode;
    }

    public Boolean isNullable() {
        return this.nullable;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }
}

