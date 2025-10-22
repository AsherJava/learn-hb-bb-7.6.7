/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.impl.measure;

import java.math.BigDecimal;

public class MeasureData {
    private String id;
    private String code;
    private String title;
    private String type;
    private boolean base;
    private BigDecimal rateValue;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isBase() {
        return this.base;
    }

    public void setBase(boolean base) {
        this.base = base;
    }

    public BigDecimal getRateValue() {
        return this.rateValue;
    }

    public void setRateValue(BigDecimal rateValue) {
        this.rateValue = rateValue;
    }
}

