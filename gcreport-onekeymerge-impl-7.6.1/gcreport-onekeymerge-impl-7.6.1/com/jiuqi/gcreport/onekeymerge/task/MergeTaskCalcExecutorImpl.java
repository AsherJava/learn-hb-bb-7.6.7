/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.util.UnionRuleUtils
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 */
package com.jiuqi.gcreport.onekeymerge.task;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.onekeymerge.entity.MergeTaskEO;
import com.jiuqi.gcreport.onekeymerge.task.MergeTaskExecutor;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyMergeUtils;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyTaskPoolEnum;
import com.jiuqi.gcreport.onekeymerge.util.OrgUtils;
import com.jiuqi.gcreport.onekeymerge.util.TaskTypeEnum;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.util.UnionRuleUtils;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="calc")
public class MergeTaskCalcExecutorImpl
implements MergeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(MergeTaskCalcExecutorImpl.class);
    public static final String TASKCODE = TaskTypeEnum.CALC.getCode();
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @Override
    public String getTaskType() {
        return TASKCODE;
    }

    @Override
    public MergeTaskEO createTask(GcActionParamsVO param) {
        MergeTaskEO mergeTaskEO = OneKeyMergeUtils.buildMergeTask(param);
        GcOrgCacheVO currentUnit = OrgUtils.getOrgByCode(param.getPeriodStr(), param.getOrgType(), param.getOrgId());
        List children = currentUnit.getChildren();
        if (CollectionUtils.isEmpty((Collection)children)) {
            mergeTaskEO.setTaskState(TaskStateEnum.SKIP.getCode());
            mergeTaskEO.setTaskData("\u975e\u5408\u5e76\u5355\u4f4d\u3002");
            return mergeTaskEO;
        }
        ConsolidatedTaskVO taskOption = this.consolidatedTaskService.getTaskBySchemeId(param.getSchemeId(), param.getPeriodStr());
        Boolean enableCalc = taskOption.getEnableCalc();
        if (!enableCalc.booleanValue()) {
            mergeTaskEO.setTaskState(TaskStateEnum.SKIP.getCode());
            mergeTaskEO.setTaskData("\u5408\u5e76\u8ba1\u7b97\u65b9\u6848\u4e0d\u5141\u8bb8\u5408\u5e76\u8ba1\u7b97\u3002");
            return mergeTaskEO;
        }
        boolean isCorporate = this.consolidatedTaskService.isCorporate(param.getTaskId(), param.getPeriodStr(), param.getOrgType());
        if (!isCorporate) {
            if (CollectionUtils.isEmpty((Collection)taskOption.getManageCalcUnitCodes()) || !taskOption.getManageCalcUnitCodes().contains(currentUnit.getCode())) {
                mergeTaskEO.setTaskState(TaskStateEnum.SKIP.getCode());
                mergeTaskEO.setTaskData("\u7ba1\u7406\u67b6\u6784\u4efb\u52a1\u672a\u914d\u7f6e\u6b64\u5355\u4f4d\u5141\u8bb8\u5408\u5e76\u8ba1\u7b97");
            }
            return mergeTaskEO;
        }
        return mergeTaskEO;
    }

    @Override
    public Object buildAsyncTaskParam(GcActionParamsVO param, String orgId) {
        if (CollectionUtils.isEmpty((Collection)param.getRuleIds())) {
            List unionRuleIds = UnionRuleUtils.selectRuleListBySchemeId((String)param.getSchemeId(), (String)param.getPeriodStr()).stream().map(AbstractUnionRule::getId).collect(Collectors.toList());
            param.setRuleIds(unionRuleIds);
        }
        GcCalcArgmentsDTO calcArgments = new GcCalcArgmentsDTO();
        calcArgments.setOrgType(param.getOrgType());
        calcArgments.setRuleIds(param.getRuleIds());
        calcArgments.setAcctPeriod(param.getAcctPeriod());
        calcArgments.setTaskId(param.getTaskId());
        calcArgments.setAcctYear(param.getAcctYear());
        calcArgments.setSchemeId(param.getSchemeId());
        calcArgments.setPeriodType(param.getPeriodType());
        calcArgments.setPeriodStr(param.getPeriodStr());
        calcArgments.setSn(param.getTaskLogId());
        calcArgments.setCurrency(param.getCurrency());
        calcArgments.setOrgId(orgId);
        calcArgments.setSelectAdjustCode(param.getSelectAdjustCode());
        return JsonUtils.writeValueAsString((Object)calcArgments);
    }

    @Override
    public String publishTask(GcActionParamsVO param, Object asyncTaskParam, OneKeyTaskPoolEnum taskPoolEnum) {
        return this.asyncTaskManager.publishTask(asyncTaskParam, taskPoolEnum.getTaskPool());
    }
}

