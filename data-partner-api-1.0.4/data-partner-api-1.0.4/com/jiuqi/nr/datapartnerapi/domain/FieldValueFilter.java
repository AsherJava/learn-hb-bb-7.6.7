/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datapartnerapi.domain;

import com.jiuqi.nr.datapartnerapi.common.FieldFilterTypeEnum;
import java.util.ArrayList;
import java.util.List;

public class FieldValueFilter {
    private String fieldKey;
    private FieldFilterTypeEnum type;
    private String value;
    private final List<String> values = new ArrayList<String>();

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getType() {
        return this.type.getCode();
    }

    public void setType(String type) {
        if (FieldFilterTypeEnum.EQUAL.getCode().equals(type)) {
            this.type = FieldFilterTypeEnum.EQUAL;
        } else if (FieldFilterTypeEnum.CONTAIN.getCode().equals(type)) {
            this.type = FieldFilterTypeEnum.CONTAIN;
        } else if (FieldFilterTypeEnum.IN.getCode().equals(type)) {
            this.type = FieldFilterTypeEnum.IN;
        }
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getValues() {
        return this.values;
    }
}

