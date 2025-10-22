/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntaxnb.bean;

import com.jiuqi.nr.single.core.syntax.bean.BaseCellDataType;

public class FinanceDataType
extends BaseCellDataType {
    private String financeSign;
    private Object valuePtr;

    public String getFinanceSign() {
        return this.financeSign;
    }

    public Object getValuePtr() {
        return this.valuePtr;
    }

    public void setFinanceSign(String financeSign) {
        this.financeSign = financeSign;
    }

    public void setValuePtr(Object valuePtr) {
        this.valuePtr = valuePtr;
    }
}

