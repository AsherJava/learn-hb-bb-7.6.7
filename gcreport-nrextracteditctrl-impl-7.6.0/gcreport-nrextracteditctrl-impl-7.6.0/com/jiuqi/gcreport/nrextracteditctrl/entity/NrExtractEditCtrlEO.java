/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Transient
 */
package com.jiuqi.gcreport.nrextracteditctrl.entity;

import java.util.Date;
import javax.persistence.Transient;

public class NrExtractEditCtrlEO {
    public static final String TABLE_NAME = "GC_NR_EXTRACT_EDIT_CTRL";
    public static final String ALL_ORG = "*";
    public static final String ALL_FORM = "*";
    public static final String SPLITTER = ",";
    private String id;
    private String taskKey;
    private String formSchemeKey;
    private String unitCode;
    private int stopFlag;
    private Date createTime;
    @Transient
    private String itemId;
    @Transient
    private String editCtrlConfId;
    @Transient
    private String formKey;
    @Transient
    private String LinkKey;
    @Transient
    private String fieldKey;
    @Transient
    private String regionKey;
    @Transient
    private String fieldCode;
    @Transient
    private String fieldTitle;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public int getStopFlag() {
        return this.stopFlag;
    }

    public void setStopFlag(int stopFlag) {
        this.stopFlag = stopFlag;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getItemId() {
        return this.itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getEditCtrlConfId() {
        return this.editCtrlConfId;
    }

    public void setEditCtrlConfId(String editCtrlConfId) {
        this.editCtrlConfId = editCtrlConfId;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getLinkKey() {
        return this.LinkKey;
    }

    public void setLinkKey(String linkKey) {
        this.LinkKey = linkKey;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }
}

