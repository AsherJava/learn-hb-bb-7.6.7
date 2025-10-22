/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntaxnb.bean;

import com.jiuqi.nr.single.core.syntax.bean.BaseCellDataType;
import com.jiuqi.nr.single.core.syntax.common.CommonDataTypeType;

public class NBTableCellType
extends BaseCellDataType {
    private int taskTag;
    private String tableSign;
    private int year;
    private int time;
    private boolean assignValue;
    private boolean isSign;
    private String fieldSign;
    private int hNum;
    private int lNum;
    private boolean fetchMean;
    private boolean hasCond;
    private String valueCond;
    private CommonDataTypeType dataType;
    private Object valuePtr;
    private double extend;
    private Object data;

    public NBTableCellType() {
        this.cellType = 0;
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

    public int getTaskTag() {
        return this.taskTag;
    }

    public String getTableSign() {
        return this.tableSign;
    }

    public int getYear() {
        return this.year;
    }

    public int getTime() {
        return this.time;
    }

    public boolean isAssignValue() {
        return this.assignValue;
    }

    public boolean isSign() {
        return this.isSign;
    }

    public String getFieldSign() {
        return this.fieldSign;
    }

    public int gethNum() {
        return this.hNum;
    }

    public int getlNum() {
        return this.lNum;
    }

    public boolean isFetchMean() {
        return this.fetchMean;
    }

    public boolean isHasCond() {
        return this.hasCond;
    }

    public String getValueCond() {
        return this.valueCond;
    }

    public Object getValuePtr() {
        return this.valuePtr;
    }

    public double getExtend() {
        return this.extend;
    }

    public void setTaskTag(int taskTag) {
        this.taskTag = taskTag;
    }

    public void setTableSign(String tableSign) {
        this.tableSign = tableSign;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setAssignValue(boolean assignValue) {
        this.assignValue = assignValue;
    }

    public void setSign(boolean isSign) {
        this.isSign = isSign;
    }

    public void setFieldSign(String fieldSign) {
        this.fieldSign = fieldSign;
    }

    public void sethNum(int hNum) {
        this.hNum = hNum;
    }

    public void setlNum(int lNum) {
        this.lNum = lNum;
    }

    public void setFetchMean(boolean fetchMean) {
        this.fetchMean = fetchMean;
    }

    public void setHasCond(boolean hasCond) {
        this.hasCond = hasCond;
    }

    public void setValueCond(String valueCond) {
        this.valueCond = valueCond;
    }

    public void setValuePtr(Object valuePtr) {
        this.valuePtr = valuePtr;
    }

    public void setExtend(double extend) {
        this.extend = extend;
    }

    @Override
    public void copyFrom(BaseCellDataType source) {
        NBTableCellType source1 = (NBTableCellType)source;
        this.cellType = source1.getCellType();
        this.taskTag = source1.getTaskTag();
        this.tableSign = source1.getTableSign();
        this.year = source1.getYear();
        this.time = source1.getTime();
        this.assignValue = source1.isAssignValue();
        this.isSign = source1.isSign;
        this.fieldSign = source1.getFieldSign();
        this.hNum = source1.gethNum();
        this.lNum = source1.getlNum();
        this.fetchMean = source1.isFetchMean();
        this.hasCond = source1.isHasCond();
        this.valueCond = source1.getValueCond();
        this.extend = source1.getExtend();
        this.data = source1.getData();
        this.dataType = source1.getDataType();
    }
}

