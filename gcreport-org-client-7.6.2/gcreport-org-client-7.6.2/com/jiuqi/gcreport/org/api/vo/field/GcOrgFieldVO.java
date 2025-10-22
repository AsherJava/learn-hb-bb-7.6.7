/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.api.vo.field;

public class GcOrgFieldVO {
    private String id;
    private Long recver;
    private Double sortOrder;
    private String initName;
    private String label;
    private String name;
    private String code;
    private Integer enableVersion;
    private Integer allowMultiple;
    private Integer nullable;
    private Integer readOnly;
    private Integer checked;
    private String refTableName;
    private String editable;

    public String getRefTableName() {
        return this.refTableName;
    }

    public void setRefTableName(String refTableName) {
        this.refTableName = refTableName;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getRecver() {
        return this.recver;
    }

    public void setRecver(Long recver) {
        this.recver = recver;
    }

    public Double getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Double sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getEnableVersion() {
        return this.enableVersion;
    }

    public void setEnableVersion(Integer enableVersion) {
        this.enableVersion = enableVersion;
    }

    public Integer getAllowMultiple() {
        return this.allowMultiple;
    }

    public void setAllowMultiple(Integer allowMultiple) {
        this.allowMultiple = allowMultiple;
    }

    public Integer getNullable() {
        return this.nullable;
    }

    public void setNullable(Integer nullable) {
        this.nullable = nullable;
    }

    public Integer getReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(Integer readOnly) {
        this.readOnly = readOnly;
    }

    public String getInitName() {
        return this.initName;
    }

    public void setInitName(String initName) {
        this.initName = initName;
    }

    public Integer getChecked() {
        return this.checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public String getEditable() {
        return this.editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

