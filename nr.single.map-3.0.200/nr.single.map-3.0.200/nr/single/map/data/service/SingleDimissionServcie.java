/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package nr.single.map.data.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import java.util.List;
import java.util.Map;
import nr.single.map.data.TaskDataContext;

public interface SingleDimissionServcie {
    public void doAnalSingleUnitDims(TaskDataContext var1, List<Map<String, DimensionValue>> var2, boolean var3, List<Map<String, DimensionValue>> var4, List<String> var5, List<String> var6, Map<String, DimensionValue> var7);

    public Map<String, DimensionValue> getDimensionMapFromSet(DimensionValueSet var1);

    public List<Map<String, DimensionValue>> getDimensionMaspFromSet(List<DimensionValueSet> var1);

    public void setDimensionByUnitSingleDim(TaskDataContext var1, Map<String, DimensionValue> var2);

    public void judgeAndUseTempTable(TaskDataContext var1);

    public void judgeAndUseTempTableByUnits(TaskDataContext var1, List<String> var2);

    public void judgeAndFreeTempTable(TaskDataContext var1);
}

