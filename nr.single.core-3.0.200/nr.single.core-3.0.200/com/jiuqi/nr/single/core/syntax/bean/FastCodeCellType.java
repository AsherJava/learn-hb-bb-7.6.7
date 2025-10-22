/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntax.bean;

import com.jiuqi.nr.single.core.syntax.bean.BaseCellDataType;
import com.jiuqi.nr.single.core.syntax.common.CommonDataTypeType;

public class FastCodeCellType
extends BaseCellDataType {
    private int index;
    private String value;
    private CommonDataTypeType dataType;
    private Object data;

    public FastCodeCellType() {
        this.cellType = 4;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
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

