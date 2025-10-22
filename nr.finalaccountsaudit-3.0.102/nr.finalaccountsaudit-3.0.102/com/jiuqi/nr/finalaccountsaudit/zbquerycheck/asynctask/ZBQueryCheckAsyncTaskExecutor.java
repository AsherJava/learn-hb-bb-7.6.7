/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.nr.common.exception.NrCommonException
 *  com.spire.ms.System.Collections.ArrayList
 */
package com.jiuqi.nr.finalaccountsaudit.zbquerycheck.asynctask;

import com.google.gson.Gson;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.common.exception.NrCommonException;
import com.jiuqi.nr.finalaccountsaudit.common.AsynctaskPoolType;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.AnalysisResultInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.DataAnalysisItem;
import com.jiuqi.nr.finalaccountsaudit.zbquerycheck.bean.ZBQueryParamInfo;
import com.jiuqi.nr.finalaccountsaudit.zbquerycheck.service.IZBQueryCheckServices;
import com.spire.ms.System.Collections.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_ZBQUERYCHECK", groupTitle="\u6307\u6807\u67e5\u8be2\u5ba1\u6838")
public class ZBQueryCheckAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(ZBQueryCheckAsyncTaskExecutor.class);

    public void execute(JobContext jobContext) {
        block18: {
            IZBQueryCheckServices zbQueryService = (IZBQueryCheckServices)BeanUtil.getBean(IZBQueryCheckServices.class);
            AnalysisResultInfo resultInfos = new AnalysisResultInfo();
            ArrayList analysisItems = new ArrayList();
            resultInfos.setResult(true);
            String errorInfo = "task_error_info";
            String finishInfo = "task_success_info";
            Gson gson = new Gson();
            String taskId = jobContext.getInstanceId();
            AbstractRealTimeJob job = jobContext.getRealTimeJob();
            Map params = job.getParams();
            RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_ZBQUERYCHECK.getName(), jobContext);
            try {
                if (!Objects.nonNull(params) || !Objects.nonNull(params.get("NR_ARGS"))) break block18;
                Object args = SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
                if (args instanceof ZBQueryParamInfo) {
                    if (monitor != null) {
                        monitor.progressAndMessage(0.1, "");
                    }
                    ZBQueryParamInfo param = (ZBQueryParamInfo)args;
                    for (String modelid : param.getZbQueryModelIds()) {
                        try {
                            DataAnalysisItem analysisItem = zbQueryService.hasQueryData(modelid, param.getContext());
                            if (!analysisItem.getResult()) {
                                resultInfos.setResult(false);
                            }
                            analysisItems.add(analysisItem);
                        }
                        catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                    resultInfos.setAnalysisItems((List<DataAnalysisItem>)analysisItems);
                    if (monitor != null) {
                        monitor.finish(finishInfo, (Object)gson.toJson((Object)resultInfos));
                    }
                    if (monitor != null) {
                        monitor.progressAndMessage(1.0, "");
                    }
                    break block18;
                }
                if (!(args instanceof String)) break block18;
                if (monitor != null) {
                    monitor.progressAndMessage(0.1, "");
                }
                String modelid = (String)args;
                try {
                    DataAnalysisItem analysisItem = zbQueryService.hasQueryData(modelid, null);
                    if (!analysisItem.getResult()) {
                        resultInfos.setResult(false);
                    }
                    analysisItems.add(analysisItem);
                    resultInfos.setAnalysisItems((List<DataAnalysisItem>)analysisItems);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
                if (monitor != null) {
                    monitor.finish(finishInfo, (Object)resultInfos);
                }
                if (monitor != null) {
                    monitor.progressAndMessage(1.0, "");
                }
            }
            catch (NrCommonException nrCommonException) {
                monitor.error(errorInfo, (Throwable)nrCommonException);
                logger.error(nrCommonException.getMessage(), nrCommonException);
                if (monitor != null) {
                    monitor.finish("", (Object)resultInfos);
                }
            }
            catch (Exception e) {
                monitor.error(errorInfo, (Throwable)e);
                logger.error(e.getMessage(), e);
                if (monitor == null) break block18;
                monitor.finish("", (Object)resultInfos);
            }
        }
    }

    public String getTaskPoolType() {
        return AsynctaskPoolType.ASYNCTASK_ZBQUERYCHECK.getName();
    }
}

