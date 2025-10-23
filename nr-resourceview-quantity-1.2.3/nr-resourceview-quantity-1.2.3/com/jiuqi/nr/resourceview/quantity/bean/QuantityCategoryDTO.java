/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.resourceview.quantity.bean;

import com.jiuqi.nr.resourceview.quantity.bean.QuantityBaseDTO;

public class QuantityCategoryDTO
extends QuantityBaseDTO {
    private String quantityId;
    private boolean base;
    private double rate;

    public String getQuantityId() {
        return this.quantityId;
    }

    public void setQuantityId(String quantityId) {
        this.quantityId = quantityId;
    }

    public boolean isBase() {
        return this.base;
    }

    public void setBase(boolean base) {
        this.base = base;
    }

    public double getRate() {
        return this.rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}

