/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package nr.single.map.data.util;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.List;
import nr.single.map.data.DataEntityInfo;
import nr.single.map.data.TaskDataContext;

public interface SingleMapEntityUtil {
    public List<DataEntityInfo> queryEntityDataRows(String var1);

    public List<DataEntityInfo> queryEntityDataRows(String var1, List<String> var2);

    public List<DataEntityInfo> queryEntityDataRowsByDims(String var1, DimensionValueSet var2);

    public List<DataEntityInfo> queryEntityDataRowsByKeys(String var1, String var2, List<String> var3);

    public List<String> queryEntityDataKeys(String var1, String var2, String var3);

    public void MapSingleEnityData(TaskDataContext var1) throws Exception;

    public String getNetPeriodCode(TaskDataContext var1, String var2);

    public String getSinglePeriodCode(TaskDataContext var1, String var2, int var3);
}

