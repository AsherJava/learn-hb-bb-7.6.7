/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.form.analysis.dto;

import com.jiuqi.nr.form.analysis.common.FormAnalysisParamEnum;
import java.util.List;

public class PeriodDimParamDTO {
    private String entityId;
    private FormAnalysisParamEnum.PeriodDataRangeType dataRangeType;
    private List<String> dimDatas;

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public FormAnalysisParamEnum.PeriodDataRangeType getDataRangeType() {
        return this.dataRangeType;
    }

    public void setDataRangeType(FormAnalysisParamEnum.PeriodDataRangeType dataRangeType) {
        this.dataRangeType = dataRangeType;
    }

    public List<String> getDimDatas() {
        return this.dimDatas;
    }

    public void setDimDatas(List<String> dimDatas) {
        this.dimDatas = dimDatas;
    }
}

