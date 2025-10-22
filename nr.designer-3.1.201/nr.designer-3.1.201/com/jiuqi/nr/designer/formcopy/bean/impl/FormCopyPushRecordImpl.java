/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.designer.formcopy.bean.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.designer.formcopy.bean.IFormCopyPushRecord;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_COPY_PUSH_RECORD")
public class FormCopyPushRecordImpl
implements IFormCopyPushRecord {
    public static final String CLZ_FIELD_PUSH_FORMSCHEMEKEY = "srcFormSchemeKey";
    private static final long serialVersionUID = 691025824322102623L;
    @DBAnno.DBField(dbField="CR_PUSH_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="CR_FS_PUSH_KEY")
    private String srcFormSchemeKey;
    @DBAnno.DBField(dbField="CR_PUSH_FORM_SCHEME", tranWith="transClob", dbType=Clob.class, appType=String.class)
    private String formSchemeMap;
    @DBAnno.DBField(dbField="CR_PUSH_ATTSCHEME", tranWith="transClob", dbType=Clob.class, appType=String.class)
    private String attScheme;
    @DBAnno.DBField(dbField="CR_PUSH_UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true, isOrder=true)
    private Date updateTime;

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getSrcFormSchemeKey() {
        return this.srcFormSchemeKey;
    }

    @Override
    public void setSrcFormSchemeKey(String srcFormSchemeKey) {
        this.srcFormSchemeKey = srcFormSchemeKey;
    }

    @Override
    public String getFormSchemeMap() {
        return this.formSchemeMap;
    }

    @Override
    public void setFormSchemeMap(String formSchemeMap) {
        this.formSchemeMap = formSchemeMap;
    }

    @Override
    public String getAttScheme() {
        return this.attScheme;
    }

    @Override
    public void setAttScheme(String attScheme) {
        this.attScheme = attScheme;
    }

    @Override
    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

