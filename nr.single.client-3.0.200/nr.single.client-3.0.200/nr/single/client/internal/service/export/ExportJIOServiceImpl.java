/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.dataentry.bean.BatchExportData
 *  com.jiuqi.nr.dataentry.bean.BatchExportInfo
 *  com.jiuqi.nr.dataentry.bean.ExportData
 *  com.jiuqi.nr.dataentry.bean.ExportParam
 *  com.jiuqi.nr.dataentry.export.IDataEntryExportService
 *  com.jiuqi.nr.dataentry.internal.service.JioBatchExportExecuter
 *  com.jiuqi.nr.dataentry.model.BatchDimensionParam
 *  com.jiuqi.nr.dataentry.service.IDataEntryParamService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  javax.annotation.Resource
 */
package nr.single.client.internal.service.export;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.dataentry.bean.BatchExportData;
import com.jiuqi.nr.dataentry.bean.BatchExportInfo;
import com.jiuqi.nr.dataentry.bean.ExportData;
import com.jiuqi.nr.dataentry.bean.ExportParam;
import com.jiuqi.nr.dataentry.export.IDataEntryExportService;
import com.jiuqi.nr.dataentry.internal.service.JioBatchExportExecuter;
import com.jiuqi.nr.dataentry.model.BatchDimensionParam;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import nr.single.client.service.export.IExportJioTaskDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="EXPORT_JIO")
public class ExportJIOServiceImpl
implements IDataEntryExportService,
JioBatchExportExecuter {
    @Resource
    private IRunTimeViewController runtimeView;
    @Resource
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Resource
    private IDataEntryParamService dataEntryParamService;
    @Autowired
    private IExportJioTaskDataService exportJioTaskService;

    public ExportData export(ExportParam param, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        return this.exportJioTaskService.export(param, asyncTaskMonitor);
    }

    public ExportData export(BatchExportInfo info, AsyncTaskMonitor asyncTaskMonitor, BatchDimensionParam dimensionInfoBuild, List<String> entityKeys, List<String> multiplePeriodList, List<String> formKeys, String dateDir, List<BatchExportData> datas) throws Exception {
        ArrayList<BatchDimensionParam> dimensionInfoBuilds = new ArrayList<BatchDimensionParam>();
        dimensionInfoBuilds.add(dimensionInfoBuild);
        return this.exportJioTaskService.ExportBathchDataByPeriods(info, asyncTaskMonitor, dimensionInfoBuilds, multiplePeriodList, formKeys, dateDir, datas);
    }

    public ExportData exportOfMultiplePeriod(BatchExportInfo info, AsyncTaskMonitor asyncTaskMonitor, List<BatchDimensionParam> dimensionInfoBuild, List<String> multiplePeriodList, List<String> formKeys, String dateDir, List<BatchExportData> datas) throws Exception {
        return this.exportJioTaskService.ExportBathchDataByPeriods(info, asyncTaskMonitor, dimensionInfoBuild, multiplePeriodList, formKeys, dateDir, datas);
    }
}

