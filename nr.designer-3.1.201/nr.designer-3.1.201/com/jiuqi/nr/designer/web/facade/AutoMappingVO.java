/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.datascheme.api.type.DataTableType;

public class AutoMappingVO {
    private String dtCode;
    private String dfCode;
    private String fieldKey;
    private int x;
    private int y;
    private DataTableType dataTableType;
    private boolean isFmdm = false;

    public boolean isFmdm() {
        return this.isFmdm;
    }

    public void setFmdm(boolean fmdm) {
        this.isFmdm = fmdm;
    }

    public DataTableType getDataTableType() {
        return this.dataTableType;
    }

    public void setDataTableType(DataTableType dataTableType) {
        this.dataTableType = dataTableType;
    }

    public String getDtCode() {
        return this.dtCode;
    }

    public void setDtCode(String dtCode) {
        this.dtCode = dtCode;
    }

    public String getDfCode() {
        return this.dfCode;
    }

    public void setDfCode(String dfCode) {
        this.dfCode = dfCode;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

