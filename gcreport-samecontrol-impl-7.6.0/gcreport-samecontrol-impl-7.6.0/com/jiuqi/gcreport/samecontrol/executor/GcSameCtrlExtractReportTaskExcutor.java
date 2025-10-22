/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractReportCond
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpAsyncTaskExecutor
 */
package com.jiuqi.gcreport.samecontrol.executor;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType;
import com.jiuqi.gcreport.samecontrol.env.impl.SameCtrlChgEnvContextImpl;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlExtractDataService;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractReportCond;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpAsyncTaskExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcSameCtrlExtractReportTaskExcutor
implements NpAsyncTaskExecutor {
    private Logger logger = LoggerFactory.getLogger(GcSameCtrlExtractReportTaskExcutor.class);
    @Autowired
    private SameCtrlExtractDataService sameCtrlExtractDataService;

    public void execute(Object args, AsyncTaskMonitor monitor) {
        try {
            SameCtrlExtractReportCond ctrlExtractReportCond = (SameCtrlExtractReportCond)JsonUtils.readValue((String)((String)args), SameCtrlExtractReportCond.class);
            monitor.progressAndMessage(0.05, "\u6b63\u5728\u5904\u7406\u540c\u63a7\u63d0\u53d6...");
            SameCtrlChgEnvContextImpl sameCtrlChgEnvContext = new SameCtrlChgEnvContextImpl();
            sameCtrlChgEnvContext.setSameCtrlExtractReportCond(ctrlExtractReportCond);
            sameCtrlChgEnvContext.setSuccessFlag(true);
            this.sameCtrlExtractDataService.extractReportData(sameCtrlChgEnvContext);
            monitor.progressAndMessage(0.8, "\u540c\u63a7\u63d0\u53d6\u5b8c\u6210...");
            monitor.progressAndMessage(0.92, "\u5168\u7b97\u5b8c\u6210...");
            monitor.finish("\u540c\u63a7\u63d0\u53d6\u5b8c\u6210", (Object)"");
        }
        catch (Exception e) {
            monitor.error("\u540c\u63a7\u63d0\u53d6\u5904\u7406\u4efb\u52a1\u51fa\u9519:" + e.getMessage(), (Throwable)e);
            this.logger.error("\u540c\u63a7\u63d0\u53d6\u5904\u7406:" + e.getMessage());
            throw new BusinessRuntimeException("\u540c\u63a7\u63d0\u53d6\u5904\u7406:" + e.getMessage(), (Throwable)e);
        }
    }

    public String getTaskPoolType() {
        return GcAsyncTaskPoolType.ASYNCTASK_SAMECTRL_EXTRACTREPORTINFO.getName();
    }
}

