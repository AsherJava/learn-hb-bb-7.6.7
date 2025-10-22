/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.engine.condition;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.List;

public interface IConditionCache {
    public boolean canSee(DimensionValueSet var1, String var2);

    public List<String> getSeeForms(DimensionValueSet var1);

    public List<String> getSeeFormGroups(DimensionValueSet var1);
}

