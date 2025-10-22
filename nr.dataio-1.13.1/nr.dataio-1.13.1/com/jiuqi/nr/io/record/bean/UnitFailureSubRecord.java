/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBField
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.io.record.bean;

import com.jiuqi.nr.datascheme.internal.anno.DBAnno;

@DBAnno.DBTable(dbTable="NR_DX_UNIT_FAIL_SUB")
public class UnitFailureSubRecord {
    @DBAnno.DBField(dbField="UNIT_FAIL_SUB_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="REC_KEY")
    private String recKey;
    @DBAnno.DBField(dbField="DW_KEY")
    private String dwKey;
    @DBAnno.DBField(dbField="FACTORY_ID")
    private String factoryId;
    @DBAnno.DBField(dbField="DESCRIPTION")
    private String desc;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRecKey() {
        return this.recKey;
    }

    public void setRecKey(String recKey) {
        this.recKey = recKey;
    }

    public String getDwKey() {
        return this.dwKey;
    }

    public void setDwKey(String dwKey) {
        this.dwKey = dwKey;
    }

    public String getFactoryId() {
        return this.factoryId;
    }

    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

