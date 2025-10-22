/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachmentcheck.bean;

import com.jiuqi.nr.attachmentcheck.bean.AttachmentCheckFileItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AttachmentCheckFieldResultItem
implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<AttachmentCheckFileItem> filesInField = new ArrayList<AttachmentCheckFileItem>();
    private List<AttachmentCheckFileItem> errFilesInField = new ArrayList<AttachmentCheckFileItem>();
    private boolean limitMaxNum = false;
    private boolean limitMinNum = false;
    private String formCode;
    private String formTitle;
    private String formKey;
    private String dataRegionKey;
    private String dataLinkKey;
    private String fieldCode;
    private String fieldTitle;
    private String fieldKey;
    private String rowId;
    private boolean isNullable;
    private double totleFilesSize = 0.0;
    private int recordIndex = 0;

    public double getTotleFilesSize() {
        return this.totleFilesSize;
    }

    public void setTotleFilesSize(double totleFilesSize) {
        this.totleFilesSize = totleFilesSize;
    }

    public void addFileSize(double fileSize) {
        this.totleFilesSize += fileSize;
    }

    public List<AttachmentCheckFileItem> getFilesInField() {
        return this.filesInField;
    }

    public void setFilesInField(List<AttachmentCheckFileItem> filesInField) {
        this.filesInField = filesInField;
    }

    public List<AttachmentCheckFileItem> getErrFilesInField() {
        return this.errFilesInField;
    }

    public void setErrFilesInField(List<AttachmentCheckFileItem> errFilesInField) {
        this.errFilesInField = errFilesInField;
    }

    public boolean isLimitMaxNum() {
        return this.limitMaxNum;
    }

    public void setLimitMaxNum(boolean limitMaxNum) {
        this.limitMaxNum = limitMaxNum;
    }

    public boolean isLimitMinNum() {
        return this.limitMinNum;
    }

    public void setLimitMinNum(boolean limitMinNum) {
        this.limitMinNum = limitMinNum;
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

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public void addFile(AttachmentCheckFileItem item) {
        this.filesInField.add(item);
    }

    public void addErrFile(AttachmentCheckFileItem item) {
        this.errFilesInField.add(item);
    }

    public boolean getNullable() {
        return this.isNullable;
    }

    public void setNullable(boolean isNullable) {
        this.isNullable = isNullable;
    }

    public int getRecordIndex() {
        return this.recordIndex;
    }

    public void setRecordIndex(int recordIndex) {
        this.recordIndex = recordIndex;
    }

    public String getDataRegionKey() {
        return this.dataRegionKey;
    }

    public void setDataRegionKey(String dataRegionKey) {
        this.dataRegionKey = dataRegionKey;
    }

    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public void setDataLinkKey(String dataLinkKey) {
        this.dataLinkKey = dataLinkKey;
    }

    public String getRowId() {
        return this.rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }
}

