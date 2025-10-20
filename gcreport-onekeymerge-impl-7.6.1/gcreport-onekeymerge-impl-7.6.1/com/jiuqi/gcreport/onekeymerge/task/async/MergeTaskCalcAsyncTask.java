/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.service.GcCalcService
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpAsyncTaskExecutor
 */
package com.jiuqi.gcreport.onekeymerge.task.async;

import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.service.GcCalcService;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.onekeymerge.util.OrgUtils;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpAsyncTaskExecutor;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class MergeTaskCalcAsyncTask
implements NpAsyncTaskExecutor {
    @Lazy
    @Autowired
    private GcCalcService calcService;

    public void execute(Object args, AsyncTaskMonitor monitor) {
        try {
            if (Objects.nonNull(args)) {
                GcCalcArgmentsDTO gcCalcArgmentsDTO = (GcCalcArgmentsDTO)JsonUtils.readValue((String)args.toString(), GcCalcArgmentsDTO.class);
                GcOrgCacheVO currentOrg = OrgUtils.getOrgByCode(gcCalcArgmentsDTO.getPeriodStr(), gcCalcArgmentsDTO.getOrgType(), gcCalcArgmentsDTO.getOrgId());
                GcCalcEnvContext envContext = this.calcService.calc(gcCalcArgmentsDTO);
                boolean successFlag = envContext.isSuccessFlag();
                List result = envContext.getResult();
                if (successFlag) {
                    monitor.finish(currentOrg.getTitle() + "\u5408\u5e76\u8ba1\u7b97\u6210\u529f", (Object)String.join((CharSequence)";\n", result));
                } else {
                    monitor.error(currentOrg.getTitle() + "\u5408\u5e76\u8ba1\u7b97\u5931\u8d25", null, String.join((CharSequence)";\n", result));
                }
            }
        }
        catch (Exception e) {
            monitor.error("\u5408\u5e76\u8ba1\u7b97\u5f02\u5e38", (Throwable)e);
        }
    }

    public String getTaskPoolType() {
        return "GC_ASYNCTASK_CALC";
    }
}

