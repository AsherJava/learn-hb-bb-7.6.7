/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.transfer.vo;

import com.jiuqi.gcreport.transfer.vo.TableFormatEnum;

public class TransferColumnVo {
    private String key;
    private String label;
    private String align;
    private String headerAlign;
    private Integer defaultWidth;
    private boolean defaultShow;
    private TableFormatEnum defaultFormatter;
    private String columnType;
    private String referTableCode;

    public String getHeaderAlign() {
        return this.headerAlign;
    }

    public void setHeaderAlign(String headerAlign) {
        this.headerAlign = headerAlign;
    }

    public TableFormatEnum getDefaultFormatter() {
        return this.defaultFormatter;
    }

    public void setDefaultFormatter(TableFormatEnum defaultFormatter) {
        this.defaultFormatter = defaultFormatter;
    }

    public boolean isDefaultShow() {
        return this.defaultShow;
    }

    public void setDefaultShow(boolean defaultShow) {
        this.defaultShow = defaultShow;
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

    public Integer getDefaultWidth() {
        return this.defaultWidth;
    }

    public void setDefaultWidth(Integer defaultWidth) {
        this.defaultWidth = defaultWidth;
    }

    public String getColumnType() {
        return this.columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getReferTableCode() {
        return this.referTableCode;
    }

    public void setReferTableCode(String referTableCode) {
        this.referTableCode = referTableCode;
    }
}

