/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntaxnb.bean;

import com.jiuqi.nr.single.core.syntax.bean.BaseCellDataType;
import com.jiuqi.nr.single.core.syntax.bean.CommonDataType;

public class EnumDictValueType
extends BaseCellDataType {
    private String enumFlag;
    private String codeStr;
    private String fieldFlag;
    private int fieldIndex;
    private CommonDataType value;

    public String getEnumFlag() {
        return this.enumFlag;
    }

    public String getCodeStr() {
        return this.codeStr;
    }

    public String getFieldFlag() {
        return this.fieldFlag;
    }

    public int getFieldIndex() {
        return this.fieldIndex;
    }

    public CommonDataType getValue() {
        return this.value;
    }

    public void setEnumFlag(String enumFlag) {
        this.enumFlag = enumFlag;
    }

    public void setCodeStr(String codeStr) {
        this.codeStr = codeStr;
    }

    public void setFieldFlag(String fieldFlag) {
        this.fieldFlag = fieldFlag;
    }

    public void setFieldIndex(int fieldIndex) {
        this.fieldIndex = fieldIndex;
    }

    public void setValue(CommonDataType value) {
        this.value = value;
    }
}

