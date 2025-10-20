/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.extend.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BillReuseField
implements Serializable {
    public static final String REUSE_ID = "id";
    public static final String REUSE_TABLECODE = "tableCode";
    public static final String REUSE_TABLENAME = "tableName";
    public static final String REUSE_FIELDCODE = "fieldCode";
    public static final String REUSE_FIELDNAME = "fieldName";
    public static final String REUSE_MAPPINGTYPE = "mappingType";
    public static final String REUSE_MAPPING = "mapping";
    private String id;
    private String tableCode;
    private String tableName;
    private String fieldCode;
    private String fieldName;
    private Integer mappingType;
    private String mapping;

    public BillReuseField() {
    }

    public BillReuseField(String tableCode, String fieldCode, Integer mappingType, String mapping) {
        this.tableCode = tableCode;
        this.fieldCode = fieldCode;
        this.mappingType = mappingType;
        this.mapping = mapping;
    }

    public String getTableCode() {
        return this.tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Integer getMappingType() {
        return this.mappingType;
    }

    public void setMappingType(Integer mappingType) {
        this.mappingType = mappingType;
    }

    public String getMapping() {
        return this.mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getMap() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put(REUSE_ID, this.getId());
        result.put(REUSE_TABLECODE, this.getTableCode());
        result.put(REUSE_TABLENAME, this.getTableName());
        result.put(REUSE_FIELDCODE, this.getFieldCode());
        result.put(REUSE_FIELDNAME, this.getFieldName());
        result.put(REUSE_MAPPINGTYPE, this.getMappingType());
        result.put(REUSE_MAPPING, this.getMapping());
        return result;
    }
}

