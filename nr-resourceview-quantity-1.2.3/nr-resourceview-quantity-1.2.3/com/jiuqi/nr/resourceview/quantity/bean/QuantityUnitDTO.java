/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.resourceview.quantity.bean;

import com.jiuqi.nr.resourceview.quantity.bean.QuantityBaseDTO;

public class QuantityUnitDTO
extends QuantityBaseDTO {
    private String quantityId;
    private String categoryId;
    private boolean base;
    private double rate;

    public String getQuantityId() {
        return this.quantityId;
    }

    public void setQuantityId(String quantityId) {
        this.quantityId = quantityId;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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

