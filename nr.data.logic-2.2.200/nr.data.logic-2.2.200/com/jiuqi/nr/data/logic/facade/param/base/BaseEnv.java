/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.logic.facade.param.base;

import com.jiuqi.nr.data.logic.facade.extend.FmlExeContextProvider;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BaseEnv
extends Serializable {
    public DimensionCollection getDimensionCollection();

    @Deprecated
    public Map<String, Object> getVariableMap();

    public String getFormulaSchemeKey();

    public Mode getMode();

    public List<String> getRangeKeys();

    public FmlExeContextProvider getFmlExeContextProvider();

    public boolean isFmlJIT();

    @Deprecated
    public Set<String> getIgnoreItems();
}

