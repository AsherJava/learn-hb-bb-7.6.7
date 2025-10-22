/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.logic.facade.param.input;

import com.jiuqi.nr.data.logic.common.CommonUtils;
import com.jiuqi.nr.data.logic.facade.extend.FmlExeContextProvider;
import com.jiuqi.nr.data.logic.facade.param.base.BaseEnv;
import com.jiuqi.nr.data.logic.facade.param.input.CheckMax;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CheckParam
implements BaseEnv {
    private static final long serialVersionUID = 8567499317846860763L;
    private DimensionCollection dimensionCollection;
    private String filterCondition;
    private String formulaSchemeKey;
    private Mode mode;
    private List<String> rangeKeys = new ArrayList<String>();
    private List<Integer> formulaCheckType = new ArrayList<Integer>();
    @Deprecated
    private CheckMax checkMax = new CheckMax();
    private String actionId;
    @Deprecated
    private Set<String> ignoreItems = new HashSet<String>();
    private FmlExeContextProvider fmlExeContextProvider;
    private boolean fmlJIT;
    private boolean checkDes;

    @Override
    public DimensionCollection getDimensionCollection() {
        return this.dimensionCollection;
    }

    public void setDimensionCollection(DimensionCollection dimensionCollection) {
        this.dimensionCollection = dimensionCollection;
    }

    @Override
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

    @Override
    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public List<Integer> getFormulaCheckType() {
        return this.formulaCheckType;
    }

    public void setFormulaCheckType(List<Integer> formulaCheckType) {
        this.formulaCheckType = formulaCheckType;
    }

    @Deprecated
    public CheckMax getCheckMax() {
        return this.checkMax;
    }

    @Deprecated
    public void setCheckMax(CheckMax checkMax) {
        this.checkMax = checkMax;
    }

    @Override
    public Mode getMode() {
        return this.mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    @Override
    public List<String> getRangeKeys() {
        return this.rangeKeys;
    }

    @Override
    public FmlExeContextProvider getFmlExeContextProvider() {
        return this.fmlExeContextProvider;
    }

    public void setFmlExeContextProvider(FmlExeContextProvider fmlExeContextProvider) {
        this.fmlExeContextProvider = fmlExeContextProvider;
    }

    @Override
    public boolean isFmlJIT() {
        return this.fmlJIT;
    }

    public void setFmlJIT(boolean fmlJIT) {
        this.fmlJIT = fmlJIT;
    }

    public void setRangeKeys(List<String> rangeKeys) {
        this.rangeKeys = rangeKeys;
    }

    public String getActionId() {
        return this.actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    @Override
    @Deprecated
    public Set<String> getIgnoreItems() {
        return this.ignoreItems;
    }

    @Deprecated
    public void setIgnoreItems(Set<String> ignoreItems) {
        this.ignoreItems = ignoreItems;
    }

    public boolean isCheckDes() {
        return this.checkDes;
    }

    public void setCheckDes(boolean checkDes) {
        this.checkDes = checkDes;
    }
}

