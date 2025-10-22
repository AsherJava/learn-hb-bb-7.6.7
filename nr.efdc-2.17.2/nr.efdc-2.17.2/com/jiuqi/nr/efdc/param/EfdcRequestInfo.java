/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.efdc.param;

import com.jiuqi.nr.jtable.params.base.JtableContext;

public class EfdcRequestInfo {
    private JtableContext jtableContext;
    private String linkKey;
    private String unitCode;
    private boolean floatType;
    private String fieldCode;
    private String regionId;
    private String rowId;

    public String getRegionId() {
        return this.regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRowId() {
        return this.rowId;
    }

    public void setRowId(String rowid) {
        this.rowId = rowid;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public boolean isFloatType() {
        return this.floatType;
    }

    public void setFloatType(boolean floatType) {
        this.floatType = floatType;
    }

    public JtableContext getJtableContext() {
        return this.jtableContext;
    }

    public void setJtableContext(JtableContext jtableContext) {
        this.jtableContext = jtableContext;
    }

    public String getLinkKey() {
        return this.linkKey;
    }

    public void setLinkKey(String linkKey) {
        this.linkKey = linkKey;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }
}

