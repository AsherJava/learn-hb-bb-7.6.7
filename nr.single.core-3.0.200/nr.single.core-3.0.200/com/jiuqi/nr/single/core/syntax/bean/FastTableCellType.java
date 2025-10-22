/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntax.bean;

import com.jiuqi.nr.single.core.syntax.bean.BaseCellDataType;
import com.jiuqi.nr.single.core.syntax.common.CommonDataTypeType;

public class FastTableCellType
extends BaseCellDataType {
    private int tabIndex;
    private int index;
    private double value;
    private CommonDataTypeType dataType;
    private Object data;

    public FastTableCellType() {
        this.cellType = 3;
    }

    public int getTabIndex() {
        return this.tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public CommonDataTypeType getDataType() {
        return this.dataType;
    }

    public void setDataType(CommonDataTypeType dataType) {
        this.dataType = dataType;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

