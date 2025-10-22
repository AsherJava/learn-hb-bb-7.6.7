/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.engine.setting;

import com.jiuqi.nr.entity.engine.setting.OrderType;

public class OrderAttribute {
    private String attributeCode;
    private OrderType type;

    public String getAttributeCode() {
        return this.attributeCode;
    }

    public void setAttributeCode(String attributeCode) {
        this.attributeCode = attributeCode;
    }

    public OrderType getType() {
        return this.type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }
}

