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
 *  com.jiuqi.nr.single.core.data.bean.SingleDataSplictInfo
 */
package nr.single.client.web;

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
import com.jiuqi.nr.single.core.data.bean.SingleDataSplictInfo;
import java.util.Map;
import nr.single.client.service.ISingleSplictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_SINGLESPLIT", groupTitle="JIO\u6587\u4ef6\u62c6\u5206")
public class SingleClientAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(SingleClientAsyncTaskExecutor.class);

    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        ISingleSplictService splictService = (ISingleSplictService)BeanUtil.getBean(ISingleSplictService.class);
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_BATCHDATASUM.getName(), jobContext);
        try {
            if (params != null && params.get("NR_ARGS") != null) {
                Map argsMap = (Map)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
                String opertionType = (String)argsMap.get("opertionType");
                if ("0".equalsIgnoreCase(opertionType)) {
                    String jioFile = (String)argsMap.get("jioFile");
                    String paramFile = (String)argsMap.get("paramFile");
                    String dataFile = (String)argsMap.get("dataFile");
                    splictService.splitSingleFile(jioFile, paramFile, dataFile, (AsyncTaskMonitor)monitor);
                } else if ("1".equalsIgnoreCase(opertionType)) {
                    SingleDataSplictInfo jioSplictInfo = (SingleDataSplictInfo)argsMap.get("jioSplictInfo");
                    splictService.splitSingleFileByOption(jioSplictInfo, (AsyncTaskMonitor)monitor);
                }
            }
        }
        catch (Exception e) {
            monitor.error("\u4efb\u52a1\u51fa\u9519\uff1a" + e.getMessage(), (Throwable)e);
            log.error(e.getMessage(), e);
        }
    }

    public String getTaskPoolType() {
        return AsynctaskPoolType.ASYNCTASK_SINGLESPLIT.getName();
    }
}

