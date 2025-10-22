/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntax.bean;

import com.jiuqi.nr.single.core.syntax.bean.BaseCellDataType;

public class IntegerDataType
extends BaseCellDataType {
    private int value;

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public void copyFrom(BaseCellDataType source) {
        IntegerDataType source1 = (IntegerDataType)source;
        this.cellType = source1.getCellType();
        this.value = source1.getValue();
    }
}

