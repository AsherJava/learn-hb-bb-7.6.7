/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntax.bean;

import com.jiuqi.nr.single.core.syntax.bean.BaseCellDataType;
import com.jiuqi.nr.single.core.syntax.common.CommonDataTypeType;

public class TableCellType
extends BaseCellDataType {
    private String tabSign;
    private boolean isSign;
    private String sign;
    private int number;
    private double value;
    private int tabIndex;
    private int index;
    private CommonDataTypeType dataType;
    private Object data;

    public TableCellType() {
        this.cellType = 0;
    }

    public String getTabSign() {
        return this.tabSign;
    }

    public void setTabSign(String tabSign) {
        this.tabSign = tabSign;
    }

    public boolean getIsSign() {
        return this.isSign;
    }

    public void setIsSign(boolean isSign) {
        this.isSign = isSign;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
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
        TableCellType source1 = (TableCellType)source;
        this.cellType = source1.getCellType();
        this.tabSign = source1.getTabSign();
        this.isSign = source1.getIsSign();
        this.sign = source1.getSign();
        this.number = source1.getNumber();
        this.value = source1.getValue();
        this.tabIndex = source1.getTabIndex();
        this.index = source1.getIndex();
        this.data = source1.getData();
        this.dataType = source1.getDataType();
    }
}

