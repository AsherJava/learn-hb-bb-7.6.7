/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntax.bean;

import com.jiuqi.nr.single.core.syntax.bean.BaseCellDataType;
import com.jiuqi.nr.single.core.syntax.common.CommonDataTypeType;

public class CodeCellType
extends BaseCellDataType {
    private String sign;
    private String value;
    private int index;
    private CommonDataTypeType dataType;
    private Object data;

    public CodeCellType() {
        this.cellType = 1;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    @Override
    public void copyFrom(BaseCellDataType source) {
        CodeCellType source1 = (CodeCellType)source;
        this.cellType = source1.getCellType();
        this.sign = source1.getSign();
        this.value = source1.getValue();
        this.index = source1.getIndex();
        this.dataType = source1.getDataType();
        this.data = source1.getData();
    }
}

