/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.bpm.de.dataflow.step.inter;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import java.util.List;

public interface IQueryState {
    public ActionStateBean queryAllState(DimensionValueSet var1, List<String> var2, List<String> var3);

    public ActionStateBean queryOnlyState(DimensionValueSet var1, String var2, String var3);
}

