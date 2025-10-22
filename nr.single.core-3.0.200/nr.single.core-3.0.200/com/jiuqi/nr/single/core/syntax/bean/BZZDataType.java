/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntax.bean;

import com.jiuqi.nr.single.core.syntax.bean.BaseCellDataType;

public class BZZDataType
extends BaseCellDataType {
    private String sign;
    private String zbSign;
    private int bzzType;
    private double value;

    public BZZDataType() {
        this.cellType = 6;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getZbSign() {
        return this.zbSign;
    }

    public void setZbSign(String zbSign) {
        this.zbSign = zbSign;
    }

    public int getBzzType() {
        return this.bzzType;
    }

    public void setBzzType(int bzzType) {
        this.bzzType = bzzType;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public void copyFrom(BaseCellDataType source) {
        BZZDataType source1 = (BZZDataType)source;
        this.cellType = source1.getCellType();
        this.zbSign = source1.getZbSign();
        this.bzzType = source1.getBzzType();
        this.sign = source1.getSign();
        this.value = source1.getValue();
    }
}

