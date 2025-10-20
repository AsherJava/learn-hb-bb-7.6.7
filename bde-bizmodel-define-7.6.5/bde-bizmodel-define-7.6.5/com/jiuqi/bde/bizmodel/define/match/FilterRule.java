/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ParamTypeEnum
 */
package com.jiuqi.bde.bizmodel.define.match;

import com.jiuqi.bde.common.constant.ParamTypeEnum;
import java.util.Arrays;
import java.util.Objects;

public class FilterRule {
    private ParamTypeEnum paramTypeEnum;
    private String[] paramValues;

    public FilterRule(ParamTypeEnum paramTypeEnum, String[] paramValues) {
        this.paramTypeEnum = paramTypeEnum;
        this.paramValues = paramValues;
    }

    public FilterRule(ParamTypeEnum paramTypeEnum) {
        this.paramTypeEnum = paramTypeEnum;
    }

    public ParamTypeEnum getParamTypeEnum() {
        return this.paramTypeEnum;
    }

    public void setParamTypeEnum(ParamTypeEnum paramTypeEnum) {
        this.paramTypeEnum = paramTypeEnum;
    }

    public String[] getParamValues() {
        return this.paramValues;
    }

    public void setParamValues(String[] paramValues) {
        this.paramValues = paramValues;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        FilterRule that = (FilterRule)o;
        return this.paramTypeEnum == that.paramTypeEnum && Objects.deepEquals(this.paramValues, that.paramValues);
    }

    public int hashCode() {
        return Objects.hash(this.paramTypeEnum, Arrays.hashCode(this.paramValues));
    }
}

