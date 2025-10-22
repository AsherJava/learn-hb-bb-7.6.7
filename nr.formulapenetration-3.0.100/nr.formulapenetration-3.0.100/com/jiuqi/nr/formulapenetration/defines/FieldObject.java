/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 */
package com.jiuqi.nr.formulapenetration.defines;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.formulapenetration.deserializer.FieldObjectDeserializer;
import com.jiuqi.nr.formulapenetration.serializer.FieldObjectSerializer;

@JsonSerialize(using=FieldObjectSerializer.class)
@JsonDeserialize(using=FieldObjectDeserializer.class)
public class FieldObject {
    public static final String FIELDOBJECT_DATALINK = "datalink";
    public static final String FIELDOBJECT_CUSTOMVALUE = "customvalue";
    public static final String FIELDOBJECT_CUSTOM = "iscustom";
    public static final String FIELDOBJECT_CODE = "code";
    public static final String FIELDOBJECT_TITLE = "title";
    public static final String FIELDOBJECT_FORMSCHEMEID = "formSchemeId";
    public static final String FIELDOBJECT_FORMKEY = "formkey";
    public static final String FIELDOBJECT_REPORTNAME = "reportName";
    private String code;
    private String title;
    private boolean isCustom = false;
    private String customvalue;
    private String dataLink;
    private String formKey;
    private String formSchemeId;
    private String reportName;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getCustom() {
        return this.isCustom;
    }

    public void setCustom(boolean isCustom) {
        this.isCustom = isCustom;
    }

    public String getCustomValue() {
        return this.customvalue;
    }

    public void setCustomValue(String customValue) {
        this.customvalue = customValue;
    }

    public void setDataLink(String dataLink) {
        this.dataLink = dataLink;
    }

    public String getDataLink() {
        return this.dataLink;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    public String getReportName() {
        return this.reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
}

