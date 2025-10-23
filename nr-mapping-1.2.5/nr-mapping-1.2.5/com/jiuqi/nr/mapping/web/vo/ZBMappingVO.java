/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping.web.vo;

import com.jiuqi.nr.mapping.bean.ZBMapping;

public class ZBMappingVO {
    private String key;
    private String msKey;
    private String form;
    private String table;
    private String regionKey;
    private String zbCode;
    private String mapping;
    private Boolean isHistory;
    private int row;
    private int col;

    public ZBMappingVO() {
    }

    public ZBMappingVO(ZBMapping zbMapping) {
        this.key = zbMapping.getKey();
        this.msKey = zbMapping.getMsKey();
        this.form = zbMapping.getForm();
        this.table = zbMapping.getTable();
        this.zbCode = zbMapping.getZbCode();
        this.mapping = zbMapping.getMapping();
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMsKey() {
        return this.msKey;
    }

    public void setMsKey(String msKey) {
        this.msKey = msKey;
    }

    public String getForm() {
        return this.form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getTable() {
        return this.table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public String getZbCode() {
        return this.zbCode;
    }

    public void setZbCode(String zbCode) {
        this.zbCode = zbCode;
    }

    public String getMapping() {
        return this.mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return this.col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Boolean getHistory() {
        return this.isHistory;
    }

    public void setIsHistory(Boolean history) {
        this.isHistory = history;
    }
}

