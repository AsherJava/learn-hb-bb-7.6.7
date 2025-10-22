/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nr.common.exception.NrCommonException
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.JsonUtil
 */
package com.jiuqi.nr.dataentry.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.common.exception.NrCommonException;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.NodeCheckInfo;
import com.jiuqi.nr.dataentry.bean.NodeCheckResultInfo;
import com.jiuqi.nr.dataentry.bean.NodeCheckResultItem;
import com.jiuqi.nr.dataentry.service.IBatchDataSumService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.JsonUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_BATCHNODECHECK", groupTitle="\u6279\u91cf\u8282\u70b9\u68c0\u67e5", subject="\u62a5\u8868", tags={"\u957f\u4efb\u52a1"})
public class BatchNodeCheckAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(BatchNodeCheckAsyncTaskExecutor.class);

    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        String errorInfo = "task_error_info";
        String cancelInfo = "task_cancel_info";
        String nodecheckFail = "node_check_fail_info";
        String nodecheckFinish = "node_check_finish_info";
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtils.getBean(IJtableParamService.class);
        IBatchDataSumService batchDataSumService = (IBatchDataSumService)BeanUtils.getBean(IBatchDataSumService.class);
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_BATCHNODECHECK.getName(), jobContext);
        try {
            String args = (String)params.get("NR_ARGS");
            if (Objects.nonNull(params) && Objects.nonNull(args)) {
                NodeCheckInfo nodeCheckInfo = (NodeCheckInfo)SimpleParamConverter.SerializationUtils.deserialize((String)args);
                EntityViewData dwEntity = jtableParamService.getDwEntity(nodeCheckInfo.getContext().getFormSchemeKey());
                String mainDim = dwEntity.getDimensionName();
                int totalCount = 0;
                int unPassCount = 0;
                HashMap<String, List<NodeCheckResultItem>> allNodeCheckResult = new HashMap<String, List<NodeCheckResultItem>>();
                String dimensionValue = ((DimensionValue)nodeCheckInfo.getContext().getDimensionSet().get(mainDim)).getValue();
                ArrayList<Map<String, DimensionValue>> dimensionList = new ArrayList<Map<String, DimensionValue>>();
                for (int i = 0; i < dimensionValue.split(";").length; ++i) {
                    ((DimensionValue)nodeCheckInfo.getContext().getDimensionSet().get(mainDim)).setValue(dimensionValue.split(";")[i]);
                    NodeCheckResultInfo nodeCheckResultInfo = batchDataSumService.nodeCheck(nodeCheckInfo, null);
                    if (nodeCheckResultInfo != null && nodeCheckResultInfo.getNodeCheckResult() != null && nodeCheckResultInfo.getNodeCheckResult().size() > 0) {
                        for (String key : nodeCheckResultInfo.getNodeCheckResult().keySet()) {
                            List<NodeCheckResultItem> list = nodeCheckResultInfo.getNodeCheckResult().get(key);
                            for (NodeCheckResultItem item : list) {
                                item.setDimensionIndex(dimensionList.size());
                            }
                            dimensionList.add(nodeCheckResultInfo.getDimensionList().get(0));
                        }
                    }
                    if (nodeCheckResultInfo != null) {
                        allNodeCheckResult.putAll(nodeCheckResultInfo.getNodeCheckResult());
                        if (nodeCheckResultInfo.getUnPassUnit() > 0) {
                            ++unPassCount;
                        }
                        ++totalCount;
                    }
                    if (!asyncTaskMonitor.isCancel()) continue;
                    asyncTaskMonitor.canceled("stop_execute", (Object)cancelInfo);
                    LogHelper.info((String)"\u6279\u91cf\u8282\u70b9\u68c0\u67e5", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
                    return;
                }
                NodeCheckResultInfo newNodeCheckResultInfo = new NodeCheckResultInfo();
                newNodeCheckResultInfo.setTotalCheckUnit(totalCount);
                newNodeCheckResultInfo.setNodeCheckResult(allNodeCheckResult);
                newNodeCheckResultInfo.setUnPassUnit(unPassCount);
                newNodeCheckResultInfo.setDimensionList(dimensionList);
                if (asyncTaskMonitor != null) {
                    String objectToJson = JsonUtil.objectToJson((Object)newNodeCheckResultInfo);
                    if (newNodeCheckResultInfo.getUnPassUnit() == 0) {
                        asyncTaskMonitor.finish(nodecheckFinish, (Object)objectToJson);
                    } else {
                        asyncTaskMonitor.error(nodecheckFail, null, objectToJson);
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

    public String getTaskPoolType() {
        return AsynctaskPoolType.ASYNCTASK_BATCHNODECHECK.getName();
    }
}

