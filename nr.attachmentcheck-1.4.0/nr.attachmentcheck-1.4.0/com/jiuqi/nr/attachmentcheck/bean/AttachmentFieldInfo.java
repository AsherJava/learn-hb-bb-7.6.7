/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachmentcheck.bean;

import com.jiuqi.nr.attachmentcheck.bean.AttachmentFieldStruct;
import java.util.ArrayList;
import java.util.List;

public class AttachmentFieldInfo
extends AttachmentFieldStruct {
    private static final long serialVersionUID = 1L;
    private boolean isCheckFileSize;
    private double minValue;
    private double maxValue;
    private int maxNumber;
    private int minNumber;
    private boolean canNull;
    private List<String> extList = new ArrayList<String>();

    public void setBlobFieldStruct(AttachmentFieldStruct struct) {
        this.setKey(struct.getKey());
        this.setDataLinkKey(struct.getDataLinkKey());
        this.setFlag(struct.getFlag());
        this.setFormCode(struct.getFormCode());
        this.setFormKey(struct.getFormKey());
        this.setFormTitle(struct.getFormTitle());
        this.setTitle(struct.getTitle());
    }

    public boolean isCheckFileSize() {
        return this.isCheckFileSize;
    }

    public void setCheckFileSize(boolean checkFileSize) {
        this.isCheckFileSize = checkFileSize;
    }

    public double getMinValue() {
        return this.minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return this.maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public int getMaxNumber() {
        return this.maxNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public int getMinNumber() {
        return this.minNumber;
    }

    public void setMinNumber(int minNumber) {
        this.minNumber = minNumber;
    }

    public boolean isCanNull() {
        return this.canNull;
    }

    public void setCanNull(boolean canNull) {
        this.canNull = canNull;
    }

    public List<String> getExtList() {
        return this.extList;
    }

    public void setExtList(List<String> extList) {
        this.extList = extList;
    }
}

