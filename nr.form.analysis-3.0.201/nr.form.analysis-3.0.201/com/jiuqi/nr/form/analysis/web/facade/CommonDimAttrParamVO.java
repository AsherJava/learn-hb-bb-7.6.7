/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonSetter
 *  com.jiuqi.nr.definition.common.DimensionRange
 *  com.jiuqi.nr.definition.common.PeriodRangeType
 */
package com.jiuqi.nr.form.analysis.web.facade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.jiuqi.nr.definition.common.DimensionRange;
import com.jiuqi.nr.definition.common.PeriodRangeType;
import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CommonDimAttrParamVO
implements Serializable {
    private static final long serialVersionUID = -1073290283469715386L;
    private String periodRange;
    private PeriodRangeType periodRangeType;
    private List<String> dimDataCodes;
    private DimensionRange unitRangeType;
    private String condition;

    public String getPeriodRange() {
        return this.periodRange;
    }

    public void setPeriodRange(String periodRange) {
        this.periodRange = periodRange;
    }

    public PeriodRangeType getPeriodRangeType() {
        return this.periodRangeType;
    }

    public void setPeriodRangeType(PeriodRangeType periodRangeType) {
        this.periodRangeType = periodRangeType;
    }

    public List<String> getDimDataCodes() {
        return this.dimDataCodes;
    }

    @JsonSetter(value="unitKeys")
    public void setDimDataCodes(List<String> dimDataCodes) {
        this.dimDataCodes = dimDataCodes;
    }

    public DimensionRange getUnitRangeType() {
        return this.unitRangeType;
    }

    @JsonSetter(value="unitRange")
    public void setUnitRangeType(DimensionRange unitRangeType) {
        this.unitRangeType = unitRangeType;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}

