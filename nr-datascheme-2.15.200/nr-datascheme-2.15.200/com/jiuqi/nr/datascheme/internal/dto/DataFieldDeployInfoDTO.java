/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 */
package com.jiuqi.nr.datascheme.internal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import java.io.Serializable;
import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DataFieldDeployInfoDTO
implements DataFieldDeployInfo,
Serializable {
    private static final long serialVersionUID = -2795213395896862062L;
    private String dataSchemeKey;
    private String dataTableKey;
    private String sourceTableKey;
    private String dataFieldKey;
    private String tableModelKey;
    private String columnModelKey;
    private String fieldName;
    private String tableName;
    private String version;
    private Instant updateTime;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getDataTableKey() {
        return this.dataTableKey;
    }

    public void setDataTableKey(String dataTableKey) {
        this.dataTableKey = dataTableKey;
    }

    public String getSourceTableKey() {
        return this.sourceTableKey;
    }

    public void setSourceTableKey(String sourceTableKey) {
        this.sourceTableKey = sourceTableKey;
    }

    public String getDataFieldKey() {
        return this.dataFieldKey;
    }

    public void setDataFieldKey(String dataFieldKey) {
        this.dataFieldKey = dataFieldKey;
    }

    public String getTableModelKey() {
        return this.tableModelKey;
    }

    public void setTableModelKey(String tableModelKey) {
        this.tableModelKey = tableModelKey;
    }

    public String getColumnModelKey() {
        return this.columnModelKey;
    }

    public void setColumnModelKey(String columnModelKey) {
        this.columnModelKey = columnModelKey;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }
}

