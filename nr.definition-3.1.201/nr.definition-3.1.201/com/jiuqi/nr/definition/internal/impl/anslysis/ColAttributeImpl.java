/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.impl.anslysis;

import com.jiuqi.nr.definition.common.SumType;
import com.jiuqi.nr.definition.facade.analysis.ColAttribute;

public class ColAttributeImpl
implements ColAttribute {
    private int colNum;
    private String fieldKey;
    private String filedTitle;
    private SumType sumType;

    @Override
    public int getColNum() {
        return this.colNum;
    }

    @Override
    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    @Override
    public String getFieldKey() {
        return this.fieldKey;
    }

    @Override
    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    @Override
    public String getFiledTitle() {
        return this.filedTitle;
    }

    @Override
    public void setFiledTitle(String filedTitle) {
        this.filedTitle = filedTitle;
    }

    @Override
    public SumType getSumType() {
        return null == this.sumType ? SumType.SUM : this.sumType;
    }

    @Override
    public void setSumType(SumType sumType) {
        this.sumType = sumType;
    }
}

