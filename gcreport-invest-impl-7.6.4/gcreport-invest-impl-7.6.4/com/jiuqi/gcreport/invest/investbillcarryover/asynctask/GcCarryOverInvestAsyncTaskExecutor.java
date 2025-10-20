/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.carryover.enums.CarryOverAsyncTaskPoolType
 *  com.jiuqi.gcreport.carryover.vo.QueryParamsVO
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.gcreport.invest.investbillcarryover.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.carryover.enums.CarryOverAsyncTaskPoolType;
import com.jiuqi.gcreport.carryover.vo.QueryParamsVO;
import com.jiuqi.gcreport.invest.investbillcarryover.service.InvestBillCarryOverTaskService;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_CARRYOVER_INVEST", groupTitle="\u6295\u8d44\u5e74\u7ed3\u6267\u884c\u5f02\u6b65\u4efb\u52a1")
public class GcCarryOverInvestAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger log = LoggerFactory.getLogger(GcCarryOverInvestAsyncTaskExecutor.class);

    public void execute(JobContext jobContext) {
        RealTimeTaskMonitor asyncTaskMonitor = null;
        try {
            String taskId = jobContext.getInstanceId();
            AbstractRealTimeJob job = jobContext.getRealTimeJob();
            Map params = job.getParams();
            InvestBillCarryOverTaskService taskService = (InvestBillCarryOverTaskService)BeanUtil.getBean(InvestBillCarryOverTaskService.class);
            asyncTaskMonitor = new RealTimeTaskMonitor(taskId, CarryOverAsyncTaskPoolType.ASYNCTASK_INVEST.getName(), jobContext);
            if (Objects.nonNull(params) && Objects.nonNull(params.get("NR_ARGS"))) {
                QueryParamsVO queryParamsVO = (QueryParamsVO)JsonUtils.readValue((String)((String)params.get("NR_ARGS")), QueryParamsVO.class);
                taskService.doTask(queryParamsVO, (AsyncTaskMonitor)asyncTaskMonitor);
            }
        }
        catch (Throwable nrCommonException) {
            if (null != asyncTaskMonitor) {
                asyncTaskMonitor.error("error", nrCommonException);
            }
            log.error(nrCommonException.getMessage(), nrCommonException);
        }
    }

    public String getTaskPoolType() {
        return CarryOverAsyncTaskPoolType.ASYNCTASK_INVEST.getName();
    }
}

