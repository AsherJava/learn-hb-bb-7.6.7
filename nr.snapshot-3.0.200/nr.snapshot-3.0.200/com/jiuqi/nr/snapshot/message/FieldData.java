/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 */
package com.jiuqi.nr.snapshot.message;

import com.jiuqi.np.dataengine.data.AbstractData;

public class FieldData {
    private String fieldKey;
    private String fieldCode;
    private String fieldTitle;
    private String maskCode;
    private AbstractData data;

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
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

    public String getMaskCode() {
        return this.maskCode;
    }

    public void setMaskCode(String maskCode) {
        this.maskCode = maskCode;
    }

    public AbstractData getData() {
        return this.data;
    }

    public void setData(AbstractData data) {
        this.data = data;
    }
}

