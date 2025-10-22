/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntaxnb.bean;

import com.jiuqi.nr.single.core.syntax.bean.BaseCellDataType;

public class GetEnumMeaningType
extends BaseCellDataType {
    private String codeStr;
    private String enumName;
    private boolean inEnum;
    private String meaningStr;
    private boolean leaf;

    public String getCodeStr() {
        return this.codeStr;
    }

    public String getEnumName() {
        return this.enumName;
    }

    public boolean isInEnum() {
        return this.inEnum;
    }

    public String getMeaningStr() {
        return this.meaningStr;
    }

    public void setCodeStr(String codeStr) {
        this.codeStr = codeStr;
    }

    public void setEnumName(String enumName) {
        this.enumName = enumName;
    }

    public void setInEnum(boolean inEnum) {
        this.inEnum = inEnum;
    }

    public void setMeaningStr(String meaningStr) {
        this.meaningStr = meaningStr;
    }

    public boolean isLeaf() {
        return this.leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }
}

