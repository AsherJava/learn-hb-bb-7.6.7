/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.designer.formcopy.bean.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.designer.formcopy.bean.IFormCopyRecord;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_COPY_RECORD")
public class FormCopyRecordImpl
implements IFormCopyRecord {
    public static final String CLZ_FIELD_FORMSCHEMEKEY = "formSchemeKey";
    private static final long serialVersionUID = 691025824322102621L;
    @DBAnno.DBField(dbField="CR_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="CR_FS_KEY")
    private String formSchemeKey;
    @DBAnno.DBField(dbField="CR_FORMKEYS", tranWith="transClob", dbType=Clob.class, appType=String.class)
    private String formKeys;
    @DBAnno.DBField(dbField="CR_ATTSCHEME", tranWith="transClob", dbType=Clob.class, appType=String.class)
    private String attScheme;
    @DBAnno.DBField(dbField="CR_UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true, isOrder=true)
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
    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    @Override
    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
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

    @Override
    public String getFormKeys() {
        return this.formKeys;
    }

    @Override
    public void setFormKeys(String formKeys) {
        this.formKeys = formKeys;
    }
}

