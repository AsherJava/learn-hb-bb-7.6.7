/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.snapshot.message;

import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.FieldDefine;

public class FieldInfo {
    private String code;
    private FieldType type;
    private String dimName;
    private FieldDefine fieldDefine;

    public FieldInfo(String code, FieldType type, String dimName, FieldDefine fieldDefine) {
        this.code = code;
        this.type = type;
        this.dimName = dimName;
        this.fieldDefine = fieldDefine;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public FieldType getType() {
        return this.type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    public String getDimName() {
        return this.dimName;
    }

    public void setDimName(String dimName) {
        this.dimName = dimName;
    }

    public FieldDefine getFieldDefine() {
        return this.fieldDefine;
    }

    public void setFieldDefine(FieldDefine fieldDefine) {
        this.fieldDefine = fieldDefine;
    }
}

