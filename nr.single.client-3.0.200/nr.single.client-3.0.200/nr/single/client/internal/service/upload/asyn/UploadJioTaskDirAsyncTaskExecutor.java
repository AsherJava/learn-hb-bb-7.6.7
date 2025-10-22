/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nr.common.exception.NrCommonException
 *  com.jiuqi.nr.dataentry.bean.JIOUnitImportResult
 *  com.jiuqi.nr.dataentry.bean.UploadParam
 *  com.jiuqi.nr.jtable.util.JsonUtil
 *  nr.single.map.configurations.bean.ISingleMappingConfig
 *  nr.single.map.data.exception.SingleDataException
 *  nr.single.map.data.service.SingleMappingService
 */
package nr.single.client.internal.service.upload.asyn;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.common.exception.NrCommonException;
import com.jiuqi.nr.dataentry.bean.JIOUnitImportResult;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.jtable.util.JsonUtil;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import nr.single.client.bean.JIOImportResultObject;
import nr.single.client.service.upload.IUploadJioAfterService;
import nr.single.client.service.upload.IUploadJioTaskDataService;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.data.exception.SingleDataException;
import nr.single.map.data.service.SingleMappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_UPLOAD_JIOTASKDIR", groupTitle="\u4e0a\u8f6cJIO\u4efb\u52a1\u76ee\u5f55", subject="\u62a5\u8868", tags={"\u957f\u4efb\u52a1"})
public class UploadJioTaskDirAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(UploadJioTaskDirAsyncTaskExecutor.class);

    public void executeWithNpContext(JobContext jobContext) {
        String errorInfo = "task_error_info";
        String cancelInfo = "task_cancel_info";
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_BATCHCHECKANALYSISFORM.getName(), jobContext);
        CacheObjectResourceRemote cacheObjectResourceRemote = (CacheObjectResourceRemote)BeanUtil.getBean(CacheObjectResourceRemote.class);
        try {
            if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                UploadParam param = (UploadParam)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
                String sourceTaskDir = (String)param.getVariableMap().get("JioImportTaskDir");
                JIOImportResultObject res = this.uploadTaskDir(sourceTaskDir, param, (AsyncTaskMonitor)asyncTaskMonitor);
                if (asyncTaskMonitor.isCancel()) {
                    asyncTaskMonitor.canceled(cancelInfo, (Object)cancelInfo);
                } else {
                    List sortedErrors = res.getErrorUnits().stream().sorted(Comparator.comparing(JIOUnitImportResult::getMessage, Comparator.nullsLast(String::compareTo))).collect(Collectors.toList());
                    res.setErrorUnits(sortedErrors);
                    String importSuccess = "import_finish_info";
                    String importError = "import_fail_info";
                    cacheObjectResourceRemote.create((Object)(asyncTaskMonitor.getTaskId() + "_result"), (Object)res);
                    String objectToJson = JsonUtil.objectToJson((Object)res);
                    if (res.isSuccess()) {
                        asyncTaskMonitor.finish(importSuccess, (Object)res);
                    } else {
                        asyncTaskMonitor.error(importError, null, objectToJson);
                    }
                }
            }
        }
        catch (NrCommonException nrCommonException) {
            asyncTaskMonitor.error(errorInfo, (Throwable)nrCommonException);
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + nrCommonException.getMessage(), nrCommonException);
        }
        catch (Exception e) {
            asyncTaskMonitor.error(errorInfo, (Throwable)e);
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    private JIOImportResultObject uploadTaskDir(String taskDir, UploadParam param, AsyncTaskMonitor asyncTaskMonitor) throws SingleDataException {
        IUploadJioTaskDataService uploadTypeJioService = (IUploadJioTaskDataService)BeanUtil.getBean(IUploadJioTaskDataService.class);
        SingleMappingService mappingConfigService = (SingleMappingService)BeanUtil.getBean(SingleMappingService.class);
        IUploadJioAfterService jioAfterService = (IUploadJioAfterService)BeanUtil.getBean(IUploadJioAfterService.class);
        ISingleMappingConfig mapConfig = mappingConfigService.getConfigByKey(param.getConfigKey().toString());
        asyncTaskMonitor.progressAndMessage(0.0, "jio\u5bfc\u5165\u9636\u6bb5");
        JIOImportResultObject res = uploadTypeJioService.uploadJioTaskData(taskDir, param, asyncTaskMonitor, 0.01, 0.98);
        logger.info("\u5bfc\u5165JIO\u6570\u636e\uff1a\u6210\u529f\u5355\u4f4d\u6570\uff1a" + String.valueOf(res.getSuccesssUnitNum()) + ",\u72b6\u6001\uff1a" + res.isSuccess());
        jioAfterService.uploadJioAfterSuccess(res, mapConfig, param, asyncTaskMonitor);
        logger.info("\u5bfc\u5165JIO\u6570\u636e\uff1ajio\u5bfc\u5165\u9636\u6bb5\u5b8c\u6210");
        return res;
    }
}

