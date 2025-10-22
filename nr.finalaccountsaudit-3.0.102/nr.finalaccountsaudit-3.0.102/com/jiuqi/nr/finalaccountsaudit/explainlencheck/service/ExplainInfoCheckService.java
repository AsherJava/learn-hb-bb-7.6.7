/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.finalaccountsaudit.explainlencheck.service;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.finalaccountsaudit.explainlencheck.bean.ExplainInfoCheckParam;
import com.jiuqi.nr.finalaccountsaudit.explainlencheck.bean.ExplainInfoCheckResultItem;
import com.jiuqi.nr.finalaccountsaudit.explainlencheck.bean.ExplainInfoCheckReturnInfo;
import com.jiuqi.nr.finalaccountsaudit.explainlencheck.bean.QueryExplainCheckResultInfo;
import com.jiuqi.nr.finalaccountsaudit.explainlencheck.service.ExplainInfoCheck;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ExplainInfoCheckService {
    public ExplainInfoCheckReturnInfo checkExplainInfo(ExplainInfoCheckParam param, AsyncTaskMonitor asyncTaskMonitor, JobContext jobContext) throws Exception {
        ExplainInfoCheck check = new ExplainInfoCheck();
        return check.checkExplainInfo(param, asyncTaskMonitor, jobContext);
    }

    public List<ExplainInfoCheckResultItem> QueryExplainInfoCheckResult(QueryExplainCheckResultInfo info) {
        ExplainInfoCheck check = new ExplainInfoCheck();
        return check.QueryExplainInfoCheckResult(info);
    }
}

