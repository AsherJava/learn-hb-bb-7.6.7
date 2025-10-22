/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.logic.facade.extend.param;

import com.jiuqi.nr.data.logic.facade.extend.param.BaseFmlFactoryParam;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DefaultFmlFactoryParam
extends BaseFmlFactoryParam {
    private static final long serialVersionUID = 6078510050846370538L;
    private String formulaSchemeKey;
    private DimensionCollection dimensionCollection;
    private Mode mode;
    private List<String> rangeKeys = new ArrayList<String>();
    private Set<String> ignoreItems = new HashSet<String>();
    private boolean fmlJIT;

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public DimensionCollection getDimensionCollection() {
        return this.dimensionCollection;
    }

    public void setDimensionCollection(DimensionCollection dimensionCollection) {
        this.dimensionCollection = dimensionCollection;
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

    public Set<String> getIgnoreItems() {
        return this.ignoreItems;
    }

    public void setIgnoreItems(Set<String> ignoreItems) {
        this.ignoreItems = ignoreItems;
    }

    public boolean isFmlJIT() {
        return this.fmlJIT;
    }

    public void setFmlJIT(boolean fmlJIT) {
        this.fmlJIT = fmlJIT;
    }
}

