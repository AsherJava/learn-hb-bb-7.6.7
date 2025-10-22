/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.state.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.state.beans.StateInfoBean;
import com.jiuqi.nr.state.common.StateConst;
import com.jiuqi.nr.state.pojo.StateEntites;
import com.jiuqi.nr.state.pojo.StatePojo;
import java.util.Map;

public interface IStateSevice {
    public boolean saveOrUpdateData(StatePojo var1);

    public StateInfoBean getStatesInfo(StateEntites var1);

    public Map<DimensionValueSet, StateConst> getStateInfo(StateEntites var1);

    public void deleteData(String var1, DimensionValueSet var2);
}

