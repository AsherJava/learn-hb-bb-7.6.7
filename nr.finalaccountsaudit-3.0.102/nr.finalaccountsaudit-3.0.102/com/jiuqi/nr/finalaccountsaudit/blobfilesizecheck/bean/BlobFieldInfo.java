/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean;

import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFieldStruct;
import java.util.ArrayList;
import java.util.List;

public class BlobFieldInfo
extends BlobFieldStruct {
    private static final long serialVersionUID = 1L;
    private boolean isCheckFileSize;
    private double minValue;
    private double maxValue;
    private boolean canNull;
    private List<String> extList = new ArrayList<String>();

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

    public boolean getIsCheckFileSize() {
        return this.isCheckFileSize;
    }

    public void setIsCheckFileSize(boolean isCheckFileSize) {
        this.isCheckFileSize = isCheckFileSize;
    }

    public List<String> getExtList() {
        return this.extList;
    }

    public void setExtList(List<String> extList) {
        this.extList = extList;
    }

    public void setBlobFieldStruct(BlobFieldStruct struct) {
        this.setKey(struct.getKey());
        this.setDataLinkKey(struct.getDataLinkKey());
        this.setFlag(struct.getFlag());
        this.setFormCode(struct.getFormCode());
        this.setFormKey(struct.getFormKey());
        this.setFormTitle(struct.getFormTitle());
        this.setTitle(struct.getTitle());
    }

    public boolean getCanNull() {
        return this.canNull;
    }

    public void setCanNull(boolean canNull) {
        this.canNull = canNull;
    }
}

