/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.fmdm.domain;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.fmdm.IFMDMUpdateResult;
import com.jiuqi.nr.fmdm.internal.check.FMDMCheckResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ApiModel(value="FMDMUpdateResult", description="\u5c01\u9762\u4ee3\u7801\u4fdd\u5b58\u7ed3\u679c")
public class FMDMUpdateResult
implements IFMDMUpdateResult {
    @ApiModelProperty(value="\u4fdd\u5b58\u524d\u540ecode\u4e0ekey\u7684\u6620\u5c04", name="codeToMap")
    private Map<String, String> codeToMap;
    @ApiModelProperty(value="\u4fdd\u5b58\u6210\u529f\u7684\u7ef4\u5ea6\u4fe1\u606f", name="dimensionValueSet")
    private List<DimensionValueSet> dimensionValueSet;
    @ApiModelProperty(value="\u65e0\u5199\u5165\u6743\u9650\u7684\u7ef4\u5ea6\u4fe1\u606f", name="noAccessDims")
    private final Set<DimensionCombination> noAccessDims = new HashSet<DimensionCombination>();
    @ApiModelProperty(value="\u5c01\u9762\u5ba1\u6838\u7ed3\u679c", name="fmdmCheckResult")
    private FMDMCheckResult FMDMCheckResult;

    public void setFMDMCheckResult(FMDMCheckResult FMDMCheckResult2) {
        this.FMDMCheckResult = FMDMCheckResult2;
    }

    public void addSuccess(String code, String key) {
        this.getCodeToMap().put(code, key);
    }

    public void setCodeToMap(Map<String, String> codeToMap) {
        this.codeToMap = codeToMap;
    }

    public Map<String, String> getCodeToMap() {
        return this.codeToMap;
    }

    @Override
    public String getSaveKey(String code) {
        return this.getCodeToMap().get(code);
    }

    @Override
    public List<String> getUpdateKeys() {
        return new ArrayList<String>(this.getCodeToMap().values());
    }

    @Override
    public List<String> getUpdateCodes() {
        return new ArrayList<String>(this.getCodeToMap().keySet());
    }

    @Override
    public List<DimensionValueSet> getUpdateDimensions() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(List<DimensionValueSet> dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    @Override
    public FMDMCheckResult getFMDMCheckResult() {
        return this.FMDMCheckResult;
    }

    @Override
    public Set<DimensionCombination> getNoAccessDims() {
        return this.noAccessDims;
    }
}

