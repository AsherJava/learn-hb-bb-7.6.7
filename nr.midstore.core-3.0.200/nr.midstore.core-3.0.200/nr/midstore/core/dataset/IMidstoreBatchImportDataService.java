/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 */
package nr.midstore.core.dataset;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import java.util.List;
import java.util.Map;
import nr.midstore.core.dataset.IMidstoreDataSet;
import nr.midstore.core.dataset.MidsotreTableContext;
import nr.midstore.core.definition.bean.MidstoreContext;

public interface IMidstoreBatchImportDataService {
    public Map<String, DimensionValue> getNewDimensionSet(Map<String, DimensionValue> var1);

    public IMidstoreDataSet getImportBatchRegionDataSet(MidsotreTableContext var1, String var2, String var3, List<String> var4);

    public IMidstoreDataSet getBatchExportRegionDataSet(MidsotreTableContext var1, String var2, String var3);

    public void openTempTable(MidstoreContext var1, List<String> var2);

    public void closeTempTable(MidstoreContext var1);

    public void setTempTable(MidstoreContext var1, MidsotreTableContext var2);

    public IRunTimeViewController getViewController();

    public MidsotreTableContext getTableContext(Map<String, DimensionValue> var1, String var2, String var3, String var4, String var5);
}

