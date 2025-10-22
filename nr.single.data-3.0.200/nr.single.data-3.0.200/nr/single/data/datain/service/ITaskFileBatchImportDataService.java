/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.io.dataset.IRegionDataSet
 *  com.jiuqi.nr.io.params.base.RegionData
 *  com.jiuqi.nr.io.params.base.TableContext
 *  nr.single.map.data.TaskDataContext
 */
package nr.single.data.datain.service;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.io.dataset.IRegionDataSet;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;
import java.util.List;
import java.util.Map;
import nr.single.map.data.TaskDataContext;

public interface ITaskFileBatchImportDataService {
    public Map<String, DimensionValue> getNewDimensionSet(Map<String, DimensionValue> var1);

    public IRegionDataSet getImportBatchRegionDataSet(TableContext var1, int var2, List<String> var3);

    public IRegionDataSet getBatchExportRegionDataSet(TableContext var1, RegionData var2);

    public void openTempTable(TaskDataContext var1, List<String> var2);

    public void closeTempTable(TaskDataContext var1);

    public void setTempTable(TaskDataContext var1, TableContext var2);

    public IRunTimeViewController getViewController();

    public TableContext getTableContex(Map<String, DimensionValue> var1, String var2, String var3, String var4, String var5);

    public List<RegionData> getFormRegions(String var1);

    public RegionData getRegionDataByForm(String var1, int var2);

    public boolean isTzDataRegion(TableContext var1, RegionData var2);
}

