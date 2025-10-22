/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean;

import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFileSizeCheckFileItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BlobFileSizeCheckFieldResultItem
implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<BlobFileSizeCheckFileItem> filesInField = new ArrayList<BlobFileSizeCheckFileItem>();
    private List<BlobFileSizeCheckFileItem> errFilesInField = new ArrayList<BlobFileSizeCheckFileItem>();
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

    public List<BlobFileSizeCheckFileItem> getFilesInField() {
        return this.filesInField;
    }

    public void setFilesInField(List<BlobFileSizeCheckFileItem> filesInField) {
        this.filesInField = filesInField;
    }

    public List<BlobFileSizeCheckFileItem> getErrFilesInField() {
        return this.errFilesInField;
    }

    public void setErrFilesInField(List<BlobFileSizeCheckFileItem> errFilesInField) {
        this.errFilesInField = errFilesInField;
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

    public void addFile(BlobFileSizeCheckFileItem item) {
        this.filesInField.add(item);
    }

    public void addErrFile(BlobFileSizeCheckFileItem item) {
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

