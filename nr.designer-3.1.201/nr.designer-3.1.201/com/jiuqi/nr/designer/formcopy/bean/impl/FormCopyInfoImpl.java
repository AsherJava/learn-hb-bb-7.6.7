/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.designer.formcopy.bean.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.designer.formcopy.bean.IFormCopyInfo;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_COPY_FORM_INFO")
public class FormCopyInfoImpl
implements IFormCopyInfo {
    public static final String CLZ_FIELD_SRCFORMKEY = "srcFormKey";
    public static final String CLZ_FIELD_FORMSCHEMEKEY = "formSchemeKey";
    public static final String CLZ_FIELD_SRCFORMSCHEMEKEY = "srcFormSchemeKey";
    private static final long serialVersionUID = 8711467502770789922L;
    @DBAnno.DBField(dbField="CFI_FORM_KEY", isPk=true)
    private String formKey;
    @DBAnno.DBField(dbField="CFI_FS_KEY")
    private String formSchemeKey;
    @DBAnno.DBField(dbField="CFI_SRC_FORM_KEY")
    private String srcFormKey;
    @DBAnno.DBField(dbField="CFI_SRC_FS_KEY")
    private String srcFormSchemeKey;
    @DBAnno.DBField(dbField="CFI_UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class)
    private Date updateTime;

    @Override
    public String getFormKey() {
        return this.formKey;
    }

    @Override
    public void setFormKey(String formKey) {
        this.formKey = formKey;
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
    public String getSrcFormKey() {
        return this.srcFormKey;
    }

    @Override
    public void setSrcFormKey(String srcFormKey) {
        this.srcFormKey = srcFormKey;
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
    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

