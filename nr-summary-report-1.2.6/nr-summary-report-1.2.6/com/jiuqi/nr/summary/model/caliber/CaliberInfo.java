/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.model.caliber;

import com.jiuqi.nr.summary.model.caliber.CaliberApplyType;
import com.jiuqi.nr.summary.model.caliber.CaliberFloatInfo;
import com.jiuqi.nr.summary.model.caliber.CaliberValue;
import java.io.Serializable;

public class CaliberInfo
implements Serializable {
    private String fieldName;
    private String title;
    private String type;
    private CaliberApplyType applyType;
    private CaliberFloatInfo floatInfo;
    private CaliberValue value;

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CaliberApplyType getApplyType() {
        return this.applyType;
    }

    public void setApplyType(CaliberApplyType applyType) {
        this.applyType = applyType;
    }

    public CaliberFloatInfo getFloatInfo() {
        return this.floatInfo;
    }

    public void setFloatInfo(CaliberFloatInfo floatInfo) {
        this.floatInfo = floatInfo;
    }

    public CaliberValue getValue() {
        return this.value;
    }

    public void setValue(CaliberValue value) {
        this.value = value;
    }

    public String toString() {
        return this.fieldName + ":" + this.value.getValue();
    }
}

