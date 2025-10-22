/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntax.bean;

import com.jiuqi.nr.single.core.syntax.common.StackDataType;
import java.util.Date;

public class DataStackStruct {
    private StackDataType stackType;
    private double sdReal;
    private String sdString;
    private Date sdDate;
    private String sdText;

    public StackDataType getStackType() {
        return this.stackType;
    }

    public void setStackType(StackDataType stackType) {
        this.stackType = stackType;
    }

    public double getSdReal() {
        return this.sdReal;
    }

    public void setSdReal(double sdReal) {
        this.sdReal = sdReal;
    }

    public String getSdString() {
        return this.sdString;
    }

    public void setSdString(String sdString) {
        this.sdString = sdString;
    }

    public Date getSdDate() {
        return this.sdDate;
    }

    public void setSdDate(Date sdDate) {
        this.sdDate = sdDate;
    }

    public String getSdText() {
        return this.sdText;
    }

    public void setSdText(String sdText) {
        this.sdText = sdText;
    }
}

