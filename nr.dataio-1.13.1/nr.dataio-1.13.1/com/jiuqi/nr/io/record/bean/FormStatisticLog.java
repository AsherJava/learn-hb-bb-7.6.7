/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBField
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.io.record.bean;

import com.jiuqi.nr.datascheme.internal.anno.DBAnno;

@DBAnno.DBTable(dbTable="NR_DX_FORM_STATISTIC")
public class FormStatisticLog {
    @DBAnno.DBField(dbField="STATISTIC_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="REC_KEY")
    private String recKey;
    @DBAnno.DBField(dbField="FORM_KEY")
    private String formKey;
    @DBAnno.DBField(dbField="FORM_ORDER")
    private String formOrder;
    @DBAnno.DBField(dbField="FACTORY_ID")
    private String factoryId;
    @DBAnno.DBField(dbField="DESCRIPTION")
    private String desc;
    private String formCode;
    private String formTitle;

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

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormOrder() {
        return this.formOrder;
    }

    public void setFormOrder(String formOrder) {
        this.formOrder = formOrder;
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

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }
}

