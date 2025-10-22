/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.dataentry.bean.BatchExportData
 *  com.jiuqi.nr.dataentry.bean.BatchExportInfo
 *  com.jiuqi.nr.dataentry.bean.ExportParam
 *  com.jiuqi.nr.dataentry.model.BatchDimensionParam
 */
package nr.single.client.service.export;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataentry.bean.BatchExportData;
import com.jiuqi.nr.dataentry.bean.BatchExportInfo;
import com.jiuqi.nr.dataentry.bean.ExportParam;
import com.jiuqi.nr.dataentry.model.BatchDimensionParam;
import java.util.List;
import nr.single.client.bean.JioExportData;

public interface IExportJioTaskDataService {
    public JioExportData export(ExportParam var1, AsyncTaskMonitor var2) throws Exception;

    public JioExportData ExportBathchDataByPeriods(BatchExportInfo var1, AsyncTaskMonitor var2, List<BatchDimensionParam> var3, List<String> var4, List<String> var5, String var6, List<BatchExportData> var7) throws Exception;
}

