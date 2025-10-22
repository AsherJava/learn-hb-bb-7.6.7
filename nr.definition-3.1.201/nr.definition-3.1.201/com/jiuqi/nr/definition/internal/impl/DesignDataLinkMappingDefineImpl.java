/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.facade.DesignDataLinkMappingDefine;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_DATALINKMAPPING_DES")
public class DesignDataLinkMappingDefineImpl
implements DesignDataLinkMappingDefine {
    public static final String CLZ_FIELD_FORMKEY = "formKey";
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="dlm_id", isPk=true)
    private String id;
    @DBAnno.DBField(dbField="fm_key")
    private String formKey;
    @DBAnno.DBField(dbField="left_dl_key")
    private String leftDataLinkKey;
    @DBAnno.DBField(dbField="right_dl_key")
    private String rightDataLinkKey;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getFormKey() {
        return this.formKey;
    }

    @Override
    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    @Override
    public String getLeftDataLinkKey() {
        return this.leftDataLinkKey;
    }

    @Override
    public void setLeftDataLinkKey(String leftDataLinkKey) {
        this.leftDataLinkKey = leftDataLinkKey;
    }

    @Override
    public String getRightDataLinkKey() {
        return this.rightDataLinkKey;
    }

    @Override
    public void setRightDataLinkKey(String rightDataLinkKey) {
        this.rightDataLinkKey = rightDataLinkKey;
    }

    public String getKey() {
        return this.id;
    }

    public String getTitle() {
        return null;
    }

    public String getOrder() {
        return null;
    }

    public String getVersion() {
        return null;
    }

    public String getOwnerLevelAndId() {
        return null;
    }

    public Date getUpdateTime() {
        return null;
    }
}

