/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.workflow2.service.para;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.workflow2.service.exception.OperateStateCode;
import com.jiuqi.util.StringUtils;
import java.util.Objects;

public class ProcessOneDim {
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private String dimensionName;
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private String dimensionKey;
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private String dimensionValue;

    public ProcessOneDim() {
    }

    ProcessOneDim(String dimensionName, String dimensionKey) {
        this.dimensionName = dimensionName;
        this.dimensionKey = dimensionKey;
    }

    public ProcessOneDim(String dimensionName, String dimensionKey, String dimensionValue) {
        this(dimensionName, dimensionKey);
        this.dimensionValue = dimensionValue;
    }

    public String getDimensionName() {
        return this.dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public String getDimensionKey() {
        return this.dimensionKey;
    }

    public void setDimensionKey(String dimensionKey) {
        this.dimensionKey = dimensionKey;
    }

    public String getDimensionValue() {
        return this.dimensionValue;
    }

    public void setDimensionValue(String dimensionValue) {
        this.dimensionValue = dimensionValue;
    }

    public OperateStateCode checkPara() {
        if (StringUtils.isEmpty((String)this.dimensionName)) {
            return OperateStateCode.ERR_DIMENSION_NAME_NOT_FOUND;
        }
        if (StringUtils.isEmpty((String)this.dimensionKey)) {
            return OperateStateCode.ERR_DIMENSION_KEY_NOT_FOUND;
        }
        if (StringUtils.isEmpty((String)this.dimensionValue)) {
            return OperateStateCode.ERR_DIMENSION_VALUE_NOT_FOUND;
        }
        return OperateStateCode.OPT_SUCCESS;
    }

    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ProcessOneDim that = (ProcessOneDim)o;
        return Objects.equals(this.dimensionName, that.dimensionName);
    }

    public int hashCode() {
        return Objects.hashCode(this.dimensionName);
    }
}

