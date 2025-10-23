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
import com.jiuqi.nr.workflow2.service.para.EProcessRangeDimType;
import com.jiuqi.nr.workflow2.service.para.ProcessOneDim;
import com.jiuqi.util.StringUtils;
import java.util.List;

public final class ProcessRangeDims
extends ProcessOneDim
implements Cloneable {
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private List<String> rangeDims;
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private EProcessRangeDimType rangeType;

    private ProcessRangeDims(String dimensionName, String dimensionKey, EProcessRangeDimType rangeType) {
        super(dimensionName, dimensionKey);
        this.rangeType = rangeType;
    }

    public ProcessRangeDims() {
    }

    public ProcessRangeDims(String dimensionName, String dimensionKey) {
        this(dimensionName, dimensionKey, EProcessRangeDimType.ALL);
    }

    public ProcessRangeDims(String dimensionName, String dimensionKey, List<String> rangeDims) {
        this(dimensionName, dimensionKey, EProcessRangeDimType.RANGE);
        this.rangeDims = rangeDims;
    }

    public ProcessRangeDims(String dimensionName, String dimensionKey, String dimensionValue) {
        super(dimensionName, dimensionKey, dimensionValue);
        this.rangeType = EProcessRangeDimType.ONE;
    }

    public List<String> getRangeDims() {
        return this.rangeDims;
    }

    public void setRangeDims(List<String> rangeDims) {
        this.rangeDims = rangeDims;
    }

    public EProcessRangeDimType getRangeType() {
        return this.rangeType;
    }

    public void setRangeType(EProcessRangeDimType rangeType) {
        this.rangeType = rangeType;
    }

    @Override
    public OperateStateCode checkPara() {
        if (StringUtils.isEmpty((String)this.getDimensionName())) {
            return OperateStateCode.ERR_DIMENSION_NAME_NOT_FOUND;
        }
        if (StringUtils.isEmpty((String)this.getDimensionKey())) {
            return OperateStateCode.ERR_DIMENSION_KEY_NOT_FOUND;
        }
        if (this.rangeType == EProcessRangeDimType.ONE && StringUtils.isEmpty((String)this.getDimensionValue())) {
            return OperateStateCode.ERR_DIMENSION_VALUE_NOT_FOUND;
        }
        if (this.rangeType == EProcessRangeDimType.RANGE && (this.rangeDims == null || this.rangeDims.isEmpty())) {
            return OperateStateCode.ERR_DIMENSION_VALUE_NOT_FOUND;
        }
        return OperateStateCode.OPT_SUCCESS;
    }

    public ProcessRangeDims clone() {
        try {
            return (ProcessRangeDims)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public ProcessRangeDims cloneAndRestValues(List<String> rangeDimensionValues) {
        ProcessRangeDims clone = this.clone();
        clone.setRangeDims(rangeDimensionValues);
        clone.setRangeType(EProcessRangeDimType.RANGE);
        return clone;
    }
}

