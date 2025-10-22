/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.designer.formcopy.bean.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.designer.formcopy.bean.IFormCopyAttSchemeInfo;
import com.jiuqi.nr.designer.formcopy.common.SchemeType;

@DBAnno.DBTable(dbTable="NR_PARAM_COPY_SCHEME_INFO")
public class FormCopyAttSchemeInfoImpl
implements IFormCopyAttSchemeInfo {
    public static final String CLZ_FIELD_FORMSCHEMEKEY = "formSchemeKey";
    public static final String CLZ_FIELD_SRCFORMSCHEMEKEY = "srcFormSchemeKey";
    private static final long serialVersionUID = -5608885638974022602L;
    @DBAnno.DBField(dbField="CSI_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="CSI_FS_KEY")
    private String formSchemeKey;
    @DBAnno.DBField(dbField="CSI_SRC_FS_KEY")
    private String srcFormSchemeKey;
    @DBAnno.DBField(dbField="CSI_TYPE", tranWith="transSchemeType", dbType=Integer.class, appType=SchemeType.class)
    private SchemeType schemeType;
    @DBAnno.DBField(dbField="CSI_SCHEME_KEY")
    private String schemeKey;
    @DBAnno.DBField(dbField="CSI_SRC_SCHEME_KEY")
    private String srcSchemeKey;

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
    public String getSrcFormSchemeKey() {
        return this.srcFormSchemeKey;
    }

    @Override
    public void setSrcFormSchemeKey(String srcFormSchemeKey) {
        this.srcFormSchemeKey = srcFormSchemeKey;
    }

    @Override
    public SchemeType getSchemeType() {
        return this.schemeType;
    }

    @Override
    public void setSchemeType(SchemeType schemeType) {
        this.schemeType = schemeType;
    }

    @Override
    public String getSchemeKey() {
        return this.schemeKey;
    }

    @Override
    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    @Override
    public String getSrcSchemeKey() {
        return this.srcSchemeKey;
    }

    @Override
    public void setSrcSchemeKey(String srcSchemeKey) {
        this.srcSchemeKey = srcSchemeKey;
    }
}

