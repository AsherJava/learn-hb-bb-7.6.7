/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.io.params.base.RegionData
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.facade.SingleFileRegionInfo
 *  nr.single.map.data.facade.SingleFileTableInfo
 */
package nr.single.data.dataout.service;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.io.params.base.RegionData;
import java.util.Map;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.facade.SingleFileRegionInfo;
import nr.single.map.data.facade.SingleFileTableInfo;

public interface ITaskFileBatchExportFMDMService {
    public void batchOperRegionData(TaskDataContext var1, String var2, String var3, String var4, Map<String, DimensionValue> var5, RegionData var6, SingleFileTableInfo var7, SingleFileRegionInfo var8) throws Exception;
}

