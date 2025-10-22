/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.carryover.entity.CarryOverLogEO
 *  com.jiuqi.gcreport.carryover.enums.CarryOverTypeEnum
 *  com.jiuqi.gcreport.carryover.task.GcCarryOverTaskExecutor
 *  com.jiuqi.gcreport.carryover.utils.CarryOverLogUtil
 *  com.jiuqi.gcreport.carryover.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 */
package com.jiuqi.gcreport.invest.investbillcarryover.task;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.carryover.entity.CarryOverLogEO;
import com.jiuqi.gcreport.carryover.enums.CarryOverTypeEnum;
import com.jiuqi.gcreport.carryover.task.GcCarryOverTaskExecutor;
import com.jiuqi.gcreport.carryover.utils.CarryOverLogUtil;
import com.jiuqi.gcreport.carryover.vo.QueryParamsVO;
import com.jiuqi.gcreport.invest.investbillcarryover.asynctask.GcCarryOverInvestAsyncTaskExecutor;
import com.jiuqi.gcreport.invest.investbillcarryover.service.InvestBillCarryOverTaskService;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvestCarryOverTaskExecutorImpl
implements GcCarryOverTaskExecutor {
    @Autowired
    private InvestBillCarryOverTaskService investBillCarryOverTaskService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    public String getName() {
        return CarryOverTypeEnum.INVEST.getCode();
    }

    public String publishTask(QueryParamsVO queryParamsVO) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(queryParamsVO.getTaskId());
        npRealTimeTaskInfo.setFormSchemeKey(queryParamsVO.getSchemeId());
        npRealTimeTaskInfo.setArgs(JsonUtils.writeValueAsString((Object)queryParamsVO));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new GcCarryOverInvestAsyncTaskExecutor());
        return this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
    }

    public void checkParam(QueryParamsVO queryParamsVO) {
        this.judgeUser();
    }

    public List<CarryOverLogEO> createTasks(QueryParamsVO queryParamsVO) {
        ArrayList<CarryOverLogEO> eos = new ArrayList<CarryOverLogEO>();
        List orgList = queryParamsVO.getOrgList();
        orgList.forEach(org -> {
            CarryOverLogEO eo = CarryOverLogUtil.initCarryOverLogEO((GcOrgCacheVO)org, (QueryParamsVO)queryParamsVO);
            eos.add(eo);
        });
        return eos;
    }

    private void judgeUser() {
        UserLoginDTO user = ShiroUtil.getUser();
        if (null != user && "admin".equals(user.getUsername())) {
            throw new RuntimeException("\u7cfb\u7edf\u7ba1\u7406\u5458admin\u7528\u6237\u65e0\u53f0\u8d26\u64cd\u4f5c\u6743\u9650\uff0c\u8bf7\u7528\u5176\u4ed6\u7528\u6237\u8fdb\u884c\u64cd\u4f5c");
        }
    }
}

