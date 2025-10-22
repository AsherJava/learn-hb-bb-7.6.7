/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.model;

import com.jiuqi.nr.dafafill.model.enums.OrderMode;
import java.io.Serializable;

public class OrderField
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fullCode;
    private OrderMode mode = OrderMode.ASC;

    public String getFullCode() {
        return this.fullCode;
    }

    public void setFullCode(String fullCode) {
        this.fullCode = fullCode;
    }

    public OrderMode getMode() {
        return this.mode;
    }

    public void setMode(OrderMode mode) {
        this.mode = mode;
    }
}

