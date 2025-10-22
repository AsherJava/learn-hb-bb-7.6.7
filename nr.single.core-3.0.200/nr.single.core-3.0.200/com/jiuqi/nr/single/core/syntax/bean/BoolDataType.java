/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntax.bean;

import com.jiuqi.nr.single.core.syntax.bean.BaseCellDataType;

public class BoolDataType
extends BaseCellDataType {
    private boolean value;

    public boolean isValue() {
        return this.value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public void copyFrom(BaseCellDataType source) {
        BoolDataType source1 = (BoolDataType)source;
        this.cellType = source1.getCellType();
        this.value = source1.isValue();
    }
}

