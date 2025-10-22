/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 */
package nr.single.para.asyn;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import java.util.List;
import java.util.Map;
import nr.single.para.compare.bean.ParaCompareOption;
import nr.single.para.compare.bean.ParaCompareResult;
import nr.single.para.compare.bean.ParaImportResult;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.service.TaskFileCompareService;
import nr.single.para.parain.controller.SingleParaImportOption;
import nr.single.para.parain.service.ITaskFileImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_SINGLEIMPORT", groupTitle="JIO\u53c2\u6570\u5bfc\u5165")
public class SingleImportAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(SingleImportAsyncTaskExecutor.class);

    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        ITaskFileImportService taskFileImportService = (ITaskFileImportService)BeanUtil.getBean(ITaskFileImportService.class);
        TaskFileCompareService taskCompareService = (TaskFileCompareService)BeanUtil.getBean(TaskFileCompareService.class);
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_BATCHDATASUM.getName(), jobContext);
        try {
            if (params != null && params.get("NR_ARGS") != null) {
                Map argsMap = (Map)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
                String importType = (String)argsMap.get("importType");
                if ("0".equalsIgnoreCase(importType)) {
                    String taskKey = (String)argsMap.get("taskKey");
                    String schemeKey = (String)argsMap.get("schemeKey");
                    String file = (String)argsMap.get("file");
                    SingleParaImportOption option = (SingleParaImportOption)argsMap.get("option");
                    taskFileImportService.ImportSingleToFormScheme(taskKey, schemeKey, file, option, (AsyncTaskMonitor)monitor);
                } else if ("1".equalsIgnoreCase(importType)) {
                    String taskKey = (String)argsMap.get("taskKey");
                    String schemeKey = (String)argsMap.get("schemeKey");
                    String dataSchemeKey = (String)argsMap.get("dataSchemeKey");
                    String file = (String)argsMap.get("file");
                    SingleParaImportOption option = (SingleParaImportOption)argsMap.get("option");
                    taskFileImportService.ImportSingleToFormScheme(taskKey, schemeKey, dataSchemeKey, file, option, (AsyncTaskMonitor)monitor);
                } else if ("10".equalsIgnoreCase(importType)) {
                    String compareKey = (String)argsMap.get("compareKey");
                    String taskKey = (String)argsMap.get("taskKey");
                    String formSchemeKey = (String)argsMap.get("formSchemeKey");
                    String dataSchemeKey = (String)argsMap.get("dataSchemeKey");
                    ParaCompareOption option = (ParaCompareOption)argsMap.get("option");
                    taskCompareService.compareSingleToTasK(compareKey, taskKey, formSchemeKey, dataSchemeKey, option, (AsyncTaskMonitor)monitor);
                } else if ("11".equalsIgnoreCase(importType)) {
                    String compareKey = (String)argsMap.get("compareKey");
                    ParaCompareOption option = (ParaCompareOption)argsMap.get("option");
                    ParaCompareResult result = taskCompareService.importSingleToTask(compareKey, option, (AsyncTaskMonitor)monitor);
                    String formSchemeKey = result.getFormSchemeKey();
                    ParaImportResult importResult = result.getImportResult();
                    String finalResult = formSchemeKey + ";" + importResult.getLogFileKey() + ";" + result.getMapSchemeKey();
                    monitor.finish("\u5bfc\u5165\u5b8c\u6210;" + finalResult, (Object)"");
                } else if ("12".equalsIgnoreCase(importType)) {
                    CompareDataType dataType = (CompareDataType)((Object)argsMap.get("dataType"));
                    List compareDataKeys = (List)argsMap.get("compareDataKeys");
                    String compareInfoKey = (String)argsMap.get("compareInfoKey");
                    ParaCompareOption option = (ParaCompareOption)argsMap.get("option");
                    taskCompareService.batchCompareSingleToTasKByType(dataType, compareDataKeys, compareInfoKey, option, (AsyncTaskMonitor)monitor);
                } else if ("13".equalsIgnoreCase(importType)) {
                    String compareKey = (String)argsMap.get("compareKey");
                    taskCompareService.batchDelete(compareKey, (AsyncTaskMonitor)monitor);
                } else if ("14".equalsIgnoreCase(importType)) {
                    List compareKeys = (List)argsMap.get("compareKeys");
                    taskCompareService.batchDeleteByKeys(compareKeys, (AsyncTaskMonitor)monitor);
                }
            }
        }
        catch (Exception e) {
            monitor.error("\u4efb\u52a1\u51fa\u9519\uff1a" + e.getMessage(), (Throwable)e);
            log.error(e.getMessage(), e);
        }
    }

    public String getTaskPoolType() {
        return AsynctaskPoolType.ASYNCTASK_SINGLEIMPORT.getName();
    }
}

