/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para;

import com.jiuqi.nr.single.core.para.consts.ZBDataType;

public class FormulaVariableInfo {
    private String varCode;
    private String varTitle;
    private ZBDataType dataType;
    private int dataSize;
    private int decimal;
    private int dftType;
    private String defaultValue;
    private String enumDict;

    public String getVarCode() {
        return this.varCode;
    }

    public String getVarTitle() {
        return this.varTitle;
    }

    public ZBDataType getDataType() {
        return this.dataType;
    }

    public int getDataSize() {
        return this.dataSize;
    }

    public int getDecimal() {
        return this.decimal;
    }

    public int getDftType() {
        return this.dftType;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public String getEnumDict() {
        return this.enumDict;
    }

    public void setVarCode(String varCode) {
        this.varCode = varCode;
    }

    public void setVarTitle(String varTitle) {
        this.varTitle = varTitle;
    }

    public void setDataType(ZBDataType dataType) {
        this.dataType = dataType;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }

    public void setDftType(int dftType) {
        this.dftType = dftType;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setEnumDict(String enumDict) {
        this.enumDict = enumDict;
    }
}

