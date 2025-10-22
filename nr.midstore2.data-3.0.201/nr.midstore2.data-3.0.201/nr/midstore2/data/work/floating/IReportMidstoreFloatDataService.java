/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DEFieldInfo
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.DataField
 *  nr.midstore2.core.dataset.IMidstoreDataSet
 */
package nr.midstore2.data.work.floating;

import com.jiuqi.bi.core.midstore.dataexchange.model.DEFieldInfo;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datascheme.api.DataField;
import java.util.List;
import java.util.Map;
import nr.midstore2.core.dataset.IMidstoreDataSet;
import nr.midstore2.data.extension.bean.ReportMidstoreContext;

public interface IReportMidstoreFloatDataService {
    public void saveFloatDataToNR(ReportMidstoreContext var1, IMidstoreDataSet var2, String var3, String var4, String var5, List<String> var6, Map<String, DEFieldInfo> var7, Map<String, Map<String, DataField>> var8, Map<String, DimensionValue> var9, Map<String, String> var10, List<String> var11, MemoryDataSet var12) throws Exception;
}

