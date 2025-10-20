/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

import com.jiuqi.nr.definition.common.ParamNum;

public class NumAuthInfo {
    private int maxNumber = 0;
    private String info = "";
    private ParamNum paramNum = ParamNum.NONE;

    public ParamNum getParamNum() {
        return this.paramNum;
    }

    public void setParamNum(ParamNum paramNum) {
        this.paramNum = paramNum;
    }

    public int getMaxNumber() {
        return this.maxNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

