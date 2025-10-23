/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.model;

import com.jiuqi.nr.zbquery.model.OrderMode;

public class OrderField {
    private String fullName;
    private OrderMode mode = OrderMode.ASC;

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public OrderMode getMode() {
        return this.mode;
    }

    public void setMode(OrderMode mode) {
        this.mode = mode;
    }
}

