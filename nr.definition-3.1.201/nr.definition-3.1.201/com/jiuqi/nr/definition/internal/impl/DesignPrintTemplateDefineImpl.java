/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_PRINTTEMPLATE_DES")
public class DesignPrintTemplateDefineImpl
implements DesignPrintTemplateDefine {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="pt_print_scheme_key", isPk=false)
    private String printSchemeKey;
    @DBAnno.DBField(dbField="pt_form_key", isPk=false)
    private String formKey;
    @DBAnno.DBField(dbField="pt_auto_refresh_form", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean isAutoRefreshForm;
    @DBAnno.DBField(dbField="pt_form_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class)
    private Date formUpdateTime;
    @DBAnno.DBField(dbField="pt_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="pt_title")
    private String title;
    @DBAnno.DBField(dbField="pt_order")
    private String order;
    @DBAnno.DBField(dbField="pt_version")
    private String version;
    @DBAnno.DBField(dbField="pt_level")
    private String ownerLevelAndId;
    @DBAnno.DBField(dbField="pt_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class)
    private Date updateTime;
    @DBAnno.DBField(dbField="pt_desc")
    private String description;
    private byte[] templateData;
    private byte[] labelData;
    @DBAnno.DBField(dbField="pt_comtem_code")
    private String comTemCode;

    public String getKey() {
        return this.key;
    }

    public String getTitle() {
        return this.title;
    }

    public String getOrder() {
        return this.order;
    }

    public String getVersion() {
        return this.version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    @Override
    public void setDescription(String desc) {
        this.description = desc;
    }

    @Override
    public void setPrintSchemeKey(String printSchemeKey) {
        this.printSchemeKey = printSchemeKey;
    }

    @Override
    public void setTemplateData(byte[] templateData) {
        this.templateData = templateData;
    }

    @Override
    public void setLabelData(byte[] labelData) {
        this.labelData = labelData;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getPrintSchemeKey() {
        return this.printSchemeKey;
    }

    @Override
    public String getFormKey() {
        return this.formKey;
    }

    @Override
    public byte[] getTemplateData() {
        return this.templateData;
    }

    @Override
    public byte[] getLabelData() {
        return this.labelData;
    }

    @Override
    public String getComTemCode() {
        return this.comTemCode;
    }

    @Override
    public void setComTemCode(String comTemCode) {
        this.comTemCode = comTemCode;
    }

    @Override
    public Date getFormUpdateTime() {
        return this.formUpdateTime;
    }

    @Override
    public void setFormUpdateTime(Date formUpdateTime) {
        this.formUpdateTime = formUpdateTime;
    }

    @Override
    public boolean isAutoRefreshForm() {
        return this.isAutoRefreshForm;
    }

    @Override
    public void setAutoRefreshForm(boolean autoRefreshForm) {
        this.isAutoRefreshForm = autoRefreshForm;
    }
}

