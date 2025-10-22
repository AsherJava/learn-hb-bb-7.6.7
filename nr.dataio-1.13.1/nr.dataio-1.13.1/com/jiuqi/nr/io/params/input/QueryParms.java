/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.params.input;

import com.jiuqi.nr.io.params.input.OptTypes;
import java.util.Map;

public class QueryParms {
    private String taskKey;
    private String formSchemeKey;
    private String formKey;
    private Map<String, Object> dimensionSet;
    private String optType = OptTypes.FORM.toString();
    private String fileType = ".txt";
    private String split = ",";
    private String syncTaskID;
    private boolean isAttachment = false;
    private String attachmentArea = "JTABLEAREA";
    private String expEntryFields = "code";
    private String expEnumFields = "codetitle";
    private int floatImpOpt = 2;

    public int getFloatImpOpt() {
        return this.floatImpOpt;
    }

    public void setFloatImpOpt(int floatImpOpt) {
        this.floatImpOpt = floatImpOpt;
    }

    public boolean isAttachment() {
        return this.isAttachment;
    }

    public void setAttachment(boolean isAttachment) {
        this.isAttachment = isAttachment;
    }

    public String getAttachmentArea() {
        return this.attachmentArea;
    }

    public void setAttachmentArea(String attachmentArea) {
        this.attachmentArea = attachmentArea;
    }

    public String getExpEntryFields() {
        return this.expEntryFields;
    }

    public void setExpEntryFields(String expEntryFields) {
        this.expEntryFields = expEntryFields;
    }

    public String getExpEnumFields() {
        return this.expEnumFields;
    }

    public void setExpEnumFields(String expEnumFields) {
        this.expEnumFields = expEnumFields;
    }

    public String getSyncTaskID() {
        return this.syncTaskID;
    }

    public void setSyncTaskID(String syncTaskID) {
        this.syncTaskID = syncTaskID;
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

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public Map<String, Object> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, Object> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public String getOptType() {
        return this.optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public String getSplit() {
        return this.split;
    }

    public void setSplit(String split) {
        this.split = split;
    }

    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}

