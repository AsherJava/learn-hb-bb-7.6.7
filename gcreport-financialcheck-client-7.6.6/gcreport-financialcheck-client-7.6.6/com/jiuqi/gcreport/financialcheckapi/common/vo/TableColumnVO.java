/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckapi.common.vo;

import java.io.Serializable;

public class TableColumnVO
implements Serializable {
    private String key;
    private String label;
    private String align;
    private Integer width;
    private String sortOrder;
    private String columnType;
    private boolean on;
    private boolean show;
    private boolean queryCondition;

    public TableColumnVO(String key, String label, String align) {
        this.key = key;
        this.label = label;
        this.align = align;
        this.sortOrder = "asc";
    }

    public TableColumnVO(String key, String label, String align, Integer width) {
        this.key = key;
        this.label = label;
        this.align = align;
        this.width = width;
        this.sortOrder = "asc";
        this.columnType = "STRING";
        this.show = true;
        this.queryCondition = true;
        this.on = true;
    }

    public TableColumnVO(String key, String label, String align, Integer width, String type) {
        this.key = key;
        this.label = label;
        this.align = align;
        this.width = width;
        this.sortOrder = "asc";
        this.columnType = type;
        this.show = true;
        this.queryCondition = true;
        this.on = true;
    }

    public TableColumnVO(String key, String label, String align, Integer width, String sortOrder, String columnType, boolean show) {
        this.key = key;
        this.label = label;
        this.align = align;
        this.width = width;
        this.sortOrder = sortOrder;
        this.columnType = columnType;
        this.show = show;
        this.queryCondition = true;
        this.on = true;
    }

    public TableColumnVO() {
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAlign() {
        return this.align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public Integer getWidth() {
        return this.width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String toString() {
        return "TableColumnVO [key=" + this.key + ", label=" + this.label + ", align=" + this.align + ", width=" + this.width + "]";
    }

    public String getColumnType() {
        return this.columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public boolean isShow() {
        return this.show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public boolean isQueryCondition() {
        return this.queryCondition;
    }

    public void setQueryCondition(boolean queryCondition) {
        this.queryCondition = queryCondition;
    }

    public boolean isOn() {
        return this.on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }
}

