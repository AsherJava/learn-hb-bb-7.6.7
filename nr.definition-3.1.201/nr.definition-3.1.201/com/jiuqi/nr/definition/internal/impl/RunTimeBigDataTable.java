/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_BIGDATATABLE")
public class RunTimeBigDataTable
implements BigDataDefine {
    private static final long serialVersionUID = -1514597455813965227L;
    @DBAnno.DBField(dbField="bd_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="bd_code")
    private String code;
    @DBAnno.DBField(dbField="bd_lang")
    private int lang;
    @DBAnno.DBField(dbField="bd_data", dbType=Blob.class)
    private byte[] data;
    @DBAnno.DBField(dbField="bd_version")
    private String version;
    @DBAnno.DBField(dbField="bd_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class)
    private Date updateTime;

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int getLang() {
        return this.lang;
    }

    public void setLang(int lang) {
        this.lang = lang;
    }

    @Override
    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String getVersion() {
        return this.version;
    }

    public void setVersion(String vesion) {
        this.version = vesion;
    }

    @Override
    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

