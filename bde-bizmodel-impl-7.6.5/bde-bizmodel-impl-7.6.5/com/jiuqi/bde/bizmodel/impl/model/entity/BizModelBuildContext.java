/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.SelectOptionVO
 *  com.jiuqi.gcreport.dimension.vo.DimensionQueryVO
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 */
package com.jiuqi.bde.bizmodel.impl.model.entity;

import com.jiuqi.bde.common.dto.SelectOptionVO;
import com.jiuqi.gcreport.dimension.vo.DimensionQueryVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.List;
import java.util.Map;

public class BizModelBuildContext {
    private Map<String, String> baseDataInputConfigMap;
    private List<DimensionVO> dimensionVOS;
    private List<DimensionQueryVO> dimensionQueryVOS;
    private List<SelectOptionVO> matchRuleBaseDataOptions;

    public Map<String, String> getBaseDataInputConfigMap() {
        return this.baseDataInputConfigMap;
    }

    public void setBaseDataInputConfigMap(Map<String, String> baseDataInputConfigMap) {
        this.baseDataInputConfigMap = baseDataInputConfigMap;
    }

    public List<DimensionVO> getDimensionVOS() {
        return this.dimensionVOS;
    }

    public void setDimensionVOS(List<DimensionVO> dimensionVOS) {
        this.dimensionVOS = dimensionVOS;
    }

    public List<DimensionQueryVO> getDimensionQueryVOS() {
        return this.dimensionQueryVOS;
    }

    public void setDimensionQueryVOS(List<DimensionQueryVO> dimensionQueryVOS) {
        this.dimensionQueryVOS = dimensionQueryVOS;
    }

    public List<SelectOptionVO> getMatchRuleBaseDataOptions() {
        return this.matchRuleBaseDataOptions;
    }

    public void setMatchRuleBaseDataOptions(List<SelectOptionVO> matchRuleBaseDataOptions) {
        this.matchRuleBaseDataOptions = matchRuleBaseDataOptions;
    }
}

