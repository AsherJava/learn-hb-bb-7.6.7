/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntaxnb.bean;

import com.jiuqi.nr.single.core.syntax.bean.BaseCellDataType;
import com.jiuqi.nr.single.core.syntax.bean.CommonDataType;

public class FVParams
extends BaseCellDataType {
    private String keyValue;
    private String keyFields;
    private String fieldCode;
    private String condition;
    private CommonDataType value;

    public String getKeyValue() {
        return this.keyValue;
    }

    public String getKeyFields() {
        return this.keyFields;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public String getCondition() {
        return this.condition;
    }

    public CommonDataType getValue() {
        return this.value;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public void setKeyFields(String keyFields) {
        this.keyFields = keyFields;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setValue(CommonDataType value) {
        this.value = value;
    }
}

