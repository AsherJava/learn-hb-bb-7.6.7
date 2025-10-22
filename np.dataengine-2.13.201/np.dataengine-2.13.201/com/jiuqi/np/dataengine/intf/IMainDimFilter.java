/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import java.util.List;
import java.util.Map;

public interface IMainDimFilter {
    public Map<String, List<String>> filterByFormulas(ExecutorContext var1, DimensionValueSet var2, List<Formula> var3, TempAssistantTable var4) throws Exception;

    public Map<String, List<String>> filterByFormulas(ExecutorContext var1, DimensionValueSet var2, List<Formula> var3) throws Exception;

    public List<String> filterByCondition(ExecutorContext var1, DimensionValueSet var2, String var3) throws Exception;
}

