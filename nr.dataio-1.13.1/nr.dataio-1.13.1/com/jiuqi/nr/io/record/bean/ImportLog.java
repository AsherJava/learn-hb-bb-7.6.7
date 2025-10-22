/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBField
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.io.record.bean;

import com.jiuqi.nr.datascheme.internal.anno.DBAnno;

@DBAnno.DBTable(dbTable="NR_DX_LOG_TABLE")
public class ImportLog {
    @DBAnno.DBField(dbField="LOG_KEY", isPk=true, notUpdate=true)
    private String key;
    @DBAnno.DBField(dbField="REC_KEY", notUpdate=true)
    private String recKey;
    @DBAnno.DBField(dbField="FACTORY_ID", notUpdate=true)
    private String factoryId;
    @DBAnno.DBField(dbField="DESCRIPTION")
    private String desc;
    @DBAnno.DBField(dbField="STATE", notUpdate=true)
    private int state;

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

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }
}

