/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.logic.facade.param.input;

import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.data.logic.common.CommonUtils;
import com.jiuqi.nr.data.logic.facade.param.input.CustomQueryCondition;
import com.jiuqi.nr.data.logic.facade.param.input.GroupType;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.facade.param.input.QueryCondition;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckResultQueryParam
implements Serializable {
    private static final long serialVersionUID = 6717886487761560522L;
    private String batchId;
    private DimensionCollection dimensionCollection;
    private boolean queryByDim;
    private String filterCondition;
    private List<String> formulaSchemeKeys = new ArrayList<String>();
    private Mode mode;
    private List<String> rangeKeys = new ArrayList<String>();
    @Deprecated
    private Map<Integer, Boolean> checkTypes = new HashMap<Integer, Boolean>();
    private QueryCondition queryCondition;
    private CustomQueryCondition customCondition;
    private PagerInfo pagerInfo;
    private GroupType groupType;

    public DimensionCollection getDimensionCollection() {
        return this.dimensionCollection;
    }

    public void setDimensionCollection(DimensionCollection dimensionCollection) {
        this.dimensionCollection = dimensionCollection;
    }

    @Deprecated
    public Map<String, Object> getVariableMap() {
        return null;
    }

    @Deprecated
    public void setVariableMap(Map<String, Object> variableMap) {
        CommonUtils.addVariables2Context(variableMap);
    }

    public String getFilterCondition() {
        return this.filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    public List<String> getFormulaSchemeKeys() {
        return this.formulaSchemeKeys;
    }

    public void setFormulaSchemeKeys(List<String> formulaSchemeKeys) {
        this.formulaSchemeKeys = formulaSchemeKeys;
    }

    public Mode getMode() {
        return this.mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public List<String> getRangeKeys() {
        return this.rangeKeys;
    }

    public void setRangeKeys(List<String> rangeKeys) {
        this.rangeKeys = rangeKeys;
    }

    @Deprecated
    public Map<Integer, Boolean> getCheckTypes() {
        if (this.queryCondition != null) {
            return Collections.emptyMap();
        }
        return this.checkTypes;
    }

    @Deprecated
    public void setCheckTypes(Map<Integer, Boolean> checkTypes) {
        this.checkTypes = checkTypes;
    }

    public PagerInfo getPagerInfo() {
        return this.pagerInfo;
    }

    public void setPagerInfo(PagerInfo pagerInfo) {
        this.pagerInfo = pagerInfo;
    }

    public GroupType getGroupType() {
        if (this.groupType == null) {
            return GroupType.unit;
        }
        return this.groupType;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    public CustomQueryCondition getCustomCondition() {
        return this.customCondition;
    }

    public void setCustomCondition(CustomQueryCondition customCondition) {
        this.customCondition = customCondition;
    }

    @Deprecated
    public DimensionCollection getQueryDimension() {
        return null;
    }

    @Deprecated
    public void setQueryDimension(DimensionCollection queryDimension) {
        this.queryByDim = true;
    }

    public String getBatchId() {
        return this.batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public boolean isQueryByDim() {
        return this.queryByDim;
    }

    public void setQueryByDim(boolean queryByDim) {
        this.queryByDim = queryByDim;
    }

    public QueryCondition getQueryCondition() {
        return this.queryCondition;
    }

    public void setQueryCondition(QueryCondition queryCondition) {
        this.queryCondition = queryCondition;
    }
}

