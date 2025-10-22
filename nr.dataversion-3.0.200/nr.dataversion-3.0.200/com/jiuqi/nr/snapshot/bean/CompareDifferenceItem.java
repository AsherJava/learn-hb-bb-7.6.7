/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.bean;

import com.jiuqi.nr.snapshot.bean.ICompareDifferenceItem;

public class CompareDifferenceItem
implements ICompareDifferenceItem {
    private String initialValue;
    private String compareValue;
    private String difference;
    private String dataLinkKey;
    private String fieldKey;
    private String fieldCode;
    private String fieldTitle;

    @Override
    public String getInitialValue() {
        return this.initialValue;
    }

    public void setInitialValue(String initialValue) {
        this.initialValue = initialValue;
    }

    @Override
    public String getCompareValue() {
        return this.compareValue;
    }

    public void setCompareValue(String compareValue) {
        this.compareValue = compareValue;
    }

    @Override
    public String getDifference() {
        return this.difference;
    }

    public void setDifference(String difference) {
        this.difference = difference;
    }

    @Override
    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public void setDataLinkKey(String dataLinkKey) {
        this.dataLinkKey = dataLinkKey;
    }

    @Override
    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    @Override
    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    @Override
    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }
}

