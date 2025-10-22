/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.state.beans;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.state.common.StateConst;
import java.util.Map;

public class StateInfoBean {
    private boolean defaultStateRole;
    private Map<DimensionValueSet, StateConst> stateInfo;

    public boolean isDefaultStateRole() {
        return this.defaultStateRole;
    }

    public void setDefaultStateRole(boolean defaultStateRole) {
        this.defaultStateRole = defaultStateRole;
    }

    public Map<DimensionValueSet, StateConst> getStateInfo() {
        return this.stateInfo;
    }

    public void setStateInfo(Map<DimensionValueSet, StateConst> stateInfo) {
        this.stateInfo = stateInfo;
    }
}

