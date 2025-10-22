/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.setting.OrderType
 */
package com.jiuqi.nr.data.engine.gather;

import com.jiuqi.nr.entity.engine.setting.OrderType;
import java.io.Serializable;

public class OrderFieldInfo
implements Serializable {
    private String fieldKey;
    private OrderType orderType;

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public OrderType getOrderType() {
        return this.orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }
}

