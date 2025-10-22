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
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CalculateParam
implements BaseEnv {
    private static final long serialVersionUID = 2539805602723694617L;
    private DimensionCollection dimensionCollection;
    private String formulaSchemeKey;
    private Mode mode;
    private List<String> rangeKeys = new ArrayList<String>();
    @Deprecated
    private Set<String> ignoreItems = new HashSet<String>();
    private FmlExeContextProvider fmlExeContextProvider;
    private boolean fmlJIT;

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

    @Override
    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
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

    public void setRangeKeys(List<String> rangeKeys) {
        this.rangeKeys = rangeKeys;
    }

    @Override
    public FmlExeContextProvider getFmlExeContextProvider() {
        return this.fmlExeContextProvider;
    }

    @Override
    public boolean isFmlJIT() {
        return this.fmlJIT;
    }

    public void setFmlJIT(boolean fmlJIT) {
        this.fmlJIT = fmlJIT;
    }

    public void setFmlExeContextProvider(FmlExeContextProvider fmlExeContextProvider) {
        this.fmlExeContextProvider = fmlExeContextProvider;
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
}

