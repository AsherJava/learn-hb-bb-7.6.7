/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.form.analysis.dto;

import com.jiuqi.nr.form.analysis.common.FormAnalysisParamEnum;
import java.util.List;

public class SrcEntityDimParamDTO {
    private String entityId;
    private FormAnalysisParamEnum.DimDataRangeType dataRangeType;
    private List<String> dimDatas;
    private String condition;

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public FormAnalysisParamEnum.DimDataRangeType getDataRangeType() {
        return this.dataRangeType;
    }

    public void setDataRangeType(FormAnalysisParamEnum.DimDataRangeType dataRangeType) {
        this.dataRangeType = dataRangeType;
    }

    public List<String> getDimDatas() {
        return this.dimDatas;
    }

    public void setDimDatas(List<String> dimDatas) {
        this.dimDatas = dimDatas;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}

