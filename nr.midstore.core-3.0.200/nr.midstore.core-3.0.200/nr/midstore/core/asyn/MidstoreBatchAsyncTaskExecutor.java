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
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nr.common.util.JsonUtil
 *  org.apache.commons.lang3.StringUtils
 */
package nr.midstore.core.asyn;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.common.util.JsonUtil;
import java.util.Map;
import nr.midstore.core.definition.bean.MidstoreParam;
import nr.midstore.core.definition.bean.MidstoreResultObject;
import nr.midstore.core.param.service.IMidstoreBatchParamService;
import nr.midstore.core.param.service.IMidstoreCheckParamService;
import nr.midstore.core.param.service.IMidstoreDocumentService;
import nr.midstore.core.publish.service.IMidstorePublishTaskService;
import nr.midstore.core.work.service.IMidstoreExcuteGetService;
import nr.midstore.core.work.service.IMidstoreExcutePostService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_MIDSTORE_DEF_BATCH", groupTitle="\u4e2d\u95f4\u5e93\u5b9a\u4e49\u6279\u91cf\u64cd\u4f5c")
public class MidstoreBatchAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(MidstoreBatchAsyncTaskExecutor.class);

    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        CacheObjectResourceRemote cacheObjectResourceRemote = (CacheObjectResourceRemote)BeanUtil.getBean(CacheObjectResourceRemote.class);
        IMidstorePublishTaskService publishService = (IMidstorePublishTaskService)BeanUtil.getBean(IMidstorePublishTaskService.class);
        IMidstoreDocumentService documentService = (IMidstoreDocumentService)BeanUtil.getBean(IMidstoreDocumentService.class);
        IMidstoreExcuteGetService getDataSerivce = (IMidstoreExcuteGetService)BeanUtil.getBean(IMidstoreExcuteGetService.class);
        IMidstoreExcutePostService postDataSerivce = (IMidstoreExcutePostService)BeanUtil.getBean(IMidstoreExcutePostService.class);
        IMidstoreBatchParamService batchParamService = (IMidstoreBatchParamService)BeanUtil.getBean(IMidstoreBatchParamService.class);
        IMidstoreCheckParamService checkParamService = (IMidstoreCheckParamService)BeanUtil.getBean(IMidstoreCheckParamService.class);
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_BATCHDATASUM.getName(), jobContext);
        try {
            if (params != null && params.get("NR_ARGS") != null) {
                Map argsMap = (Map)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
                String optType = (String)argsMap.get("optType");
                if ("10".equalsIgnoreCase(optType)) {
                    String schemeKey = (String)argsMap.get("midstoreSchemeKey");
                    MidstoreResultObject result = publishService.publishTask(schemeKey, (AsyncTaskMonitor)monitor);
                    monitor.progressAndMessage(1.0, "\u53d1\u5e03\u5b8c\u6210");
                    cacheObjectResourceRemote.create((Object)(monitor.getTaskId() + "_result"), (Object)result);
                    String objectToJson = JsonUtil.objectToJson((Object)result);
                    if (result.isSuccess()) {
                        monitor.finish("midstore_success_info", (Object)result);
                    } else {
                        String msg = "midstore_fail_info";
                        if (result.getMessage() != null && StringUtils.isNotEmpty((CharSequence)result.getMessage())) {
                            String msg2 = result.getMessage();
                            if (msg2.length() > 200) {
                                msg2 = msg2.substring(0, 200);
                            }
                            msg = msg + ";" + msg2;
                        }
                        monitor.error(msg, null, objectToJson);
                    }
                } else if ("11".equalsIgnoreCase(optType)) {
                    String schemeKey = (String)argsMap.get("midstoreSchemeKey");
                    MidstoreResultObject result = getDataSerivce.excuteGetData(schemeKey, (AsyncTaskMonitor)monitor);
                    monitor.progressAndMessage(1.0, "\u62c9\u53d6\u6570\u636e\u5b8c\u6210");
                    cacheObjectResourceRemote.create((Object)(monitor.getTaskId() + "_result"), (Object)result);
                    String objectToJson = JsonUtil.objectToJson((Object)result);
                    if (result.isSuccess()) {
                        monitor.finish("midstore_success_info", (Object)result);
                    } else {
                        monitor.error("midstore_fail_info", null, objectToJson);
                    }
                } else if ("111".equalsIgnoreCase(optType)) {
                    MidstoreParam param = (MidstoreParam)argsMap.get("MidstoreParam");
                    MidstoreResultObject result = getDataSerivce.excuteGetData(param, (AsyncTaskMonitor)monitor);
                    monitor.progressAndMessage(1.0, "\u62c9\u53d6\u6570\u636e\u5b8c\u6210");
                    cacheObjectResourceRemote.create((Object)(monitor.getTaskId() + "_result"), (Object)result);
                    String objectToJson = JsonUtil.objectToJson((Object)result);
                    if (result.isSuccess()) {
                        monitor.finish("midstore_success_info", (Object)result);
                    } else {
                        monitor.error("midstore_fail_info", null, objectToJson);
                    }
                } else if ("12".equalsIgnoreCase(optType)) {
                    String schemeKey = (String)argsMap.get("midstoreSchemeKey");
                    MidstoreResultObject result = postDataSerivce.excutePostData(schemeKey, (AsyncTaskMonitor)monitor);
                    monitor.progressAndMessage(1.0, "\u63a8\u9001\u6570\u636e\u5b8c\u6210");
                    cacheObjectResourceRemote.create((Object)(monitor.getTaskId() + "_result"), (Object)result);
                    String objectToJson = JsonUtil.objectToJson((Object)result);
                    if (result.isSuccess()) {
                        monitor.finish("midstore_success_info", (Object)result);
                    } else {
                        monitor.error("midstore_fail_info", null, objectToJson);
                    }
                } else if ("121".equalsIgnoreCase(optType)) {
                    MidstoreParam param = (MidstoreParam)argsMap.get("MidstoreParam");
                    MidstoreResultObject result = postDataSerivce.excutePostData(param, (AsyncTaskMonitor)monitor);
                    monitor.progressAndMessage(1.0, "\u63a8\u9001\u6570\u636e\u5b8c\u6210");
                    cacheObjectResourceRemote.create((Object)(monitor.getTaskId() + "_result"), (Object)result);
                    String objectToJson = JsonUtil.objectToJson((Object)result);
                    if (result.isSuccess()) {
                        monitor.finish("midstore_success_info", (Object)result);
                    } else {
                        monitor.error("midstore_fail_info", null, objectToJson);
                    }
                } else if ("13".equalsIgnoreCase(optType)) {
                    String schemeKey = (String)argsMap.get("midstoreSchemeKey");
                    MidstoreResultObject result = batchParamService.doLinkBaseDataFromFields(schemeKey, (AsyncTaskMonitor)monitor);
                    monitor.progressAndMessage(1.0, "\u6dfb\u52a0\u5173\u8054\u57fa\u7840\u6570\u636e\u96c6\u5b8c\u6210");
                    String objectToJson = JsonUtil.objectToJson((Object)result);
                    if (result.isSuccess()) {
                        monitor.finish("midstore_success_info", (Object)result);
                    } else {
                        monitor.error("midstore_fail_info", null, objectToJson);
                    }
                } else if ("14".equalsIgnoreCase(optType)) {
                    String schemeKey = (String)argsMap.get("midstoreSchemeKey");
                    MidstoreResultObject result = checkParamService.doCheckParams(schemeKey, (AsyncTaskMonitor)monitor);
                    monitor.progressAndMessage(1.0, "\u68c0\u67e5\u53c2\u6570");
                    cacheObjectResourceRemote.create((Object)(monitor.getTaskId() + "_result"), (Object)result);
                    String objectToJson = JsonUtil.objectToJson((Object)result);
                    if (result.isSuccess()) {
                        monitor.finish("midstore_success_info", (Object)result);
                    } else {
                        monitor.error("midstore_fail_info", null, objectToJson);
                    }
                } else if ("15".equalsIgnoreCase(optType)) {
                    String schemeKey = (String)argsMap.get("midstoreSchemeKey");
                    MidstoreResultObject result = documentService.exportDocument(schemeKey, (AsyncTaskMonitor)monitor);
                    monitor.progressAndMessage(1.0, "\u5bfc\u51fa\u6587\u6863\u5b8c\u6210");
                    cacheObjectResourceRemote.create((Object)(monitor.getTaskId() + "_result"), (Object)result);
                    String objectToJson = JsonUtil.objectToJson((Object)result);
                    if (result.isSuccess()) {
                        monitor.finish("midstore_success_info", (Object)result);
                    } else {
                        String msg = "midstore_fail_info";
                        if (result.getMessage() != null && StringUtils.isNotEmpty((CharSequence)result.getMessage())) {
                            String msg2 = result.getMessage();
                            if (msg2.length() > 200) {
                                msg2 = msg2.substring(0, 200);
                            }
                            msg = msg + ";" + msg2;
                        }
                        monitor.error(msg, null, objectToJson);
                    }
                }
            }
        }
        catch (Exception e) {
            monitor.error("\u4efb\u52a1\u51fa\u9519\uff1a" + e.getMessage(), (Throwable)e);
            log.error(e.getMessage(), e);
        }
    }

    public String getTaskPoolType() {
        return "ASYNCTASK_MIDSTORE_DEF_BATCH";
    }
}

