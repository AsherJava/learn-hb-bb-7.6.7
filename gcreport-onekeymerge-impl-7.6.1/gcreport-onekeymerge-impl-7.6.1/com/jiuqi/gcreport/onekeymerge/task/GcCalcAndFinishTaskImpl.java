/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.service.GcCalcService
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcBaseTaskStateVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcCalcAndFinishResultVO
 *  com.jiuqi.gcreport.onekeymerge.vo.ReturnObject
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.util.UnionRuleUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.exception.BusinessException
 */
package com.jiuqi.gcreport.onekeymerge.task;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.service.GcCalcService;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.onekeymerge.entity.GcTaskResultEO;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyMergeService;
import com.jiuqi.gcreport.onekeymerge.task.GcCenterTask;
import com.jiuqi.gcreport.onekeymerge.task.GcFinishCalcTaskImpl;
import com.jiuqi.gcreport.onekeymerge.util.OrgUtils;
import com.jiuqi.gcreport.onekeymerge.util.TaskTypeEnum;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcBaseTaskStateVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcCalcAndFinishResultVO;
import com.jiuqi.gcreport.onekeymerge.vo.ReturnObject;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.util.UnionRuleUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.exception.BusinessException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcCalcAndFinishTaskImpl
implements GcCenterTask {
    private static final Logger logger = LoggerFactory.getLogger(GcCalcAndFinishTaskImpl.class);
    @Autowired
    private GcOnekeyMergeService onekeyMergeService;
    @Autowired
    private GcCalcService calcService;
    @Autowired
    GcFinishCalcTaskImpl finishCalcTask;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;

    @Override
    public ReturnObject doTask(GcActionParamsVO paramsVO) {
        ConsolidatedTaskVO taskOption = this.consolidatedTaskService.getTaskBySchemeId(paramsVO.getSchemeId(), paramsVO.getPeriodStr());
        Boolean enableCalc = taskOption.getEnableCalc();
        Boolean enableFinishCalc = taskOption.getEnableFinishCalc();
        if (!enableCalc.booleanValue() && !enableFinishCalc.booleanValue()) {
            throw new BusinessException("\u8be5\u62a5\u8868\u65b9\u6848\u4e0b\u65e0\u9700\u8fdb\u884c\u8be5\u64cd\u4f5c");
        }
        return this.doCalc(paramsVO);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ReturnObject doCalc(GcActionParamsVO paramsVO) {
        TaskLog taskLog = new TaskLog(paramsVO.getOnekeyProgressData());
        taskLog.writeInfoLog("\u5f00\u59cb\u5408\u5e76\u8ba1\u7b97", Float.valueOf(1.0f));
        taskLog.writeInfoLog("\u5f00\u59cb\u65f6\u95f4" + DateUtils.nowTimeStr((String)"yyyy-MM-dd HH:mm:ss") + "", Float.valueOf(1.0f));
        TaskStateEnum state = TaskStateEnum.EXECUTING;
        ArrayList<GcBaseTaskStateVO> restList = new ArrayList<GcBaseTaskStateVO>();
        GcTaskResultEO taskResultEO = new GcTaskResultEO();
        BeanUtils.copyProperties(paramsVO, (Object)taskResultEO);
        String processUnitName = "";
        try {
            String name = NpContextHolder.getContext().getUser().getName();
            taskResultEO.setUserName(name);
            Date start = DateUtils.now();
            taskResultEO.setTaskTime(start);
            taskResultEO.setTaskCode(TaskTypeEnum.CALCANDFINISH.getCode());
            boolean isCorporate = this.consolidatedTaskService.isCorporate(paramsVO.getTaskId(), paramsVO.getPeriodStr(), paramsVO.getOrgType());
            if (CollectionUtils.isEmpty((Collection)paramsVO.getRuleIds()) && isCorporate) {
                List unionRuleIds = UnionRuleUtils.selectRuleListBySchemeId((String)paramsVO.getSchemeId(), (String)paramsVO.getPeriodStr()).stream().map(AbstractUnionRule::getId).collect(Collectors.toList());
                paramsVO.setRuleIds(unionRuleIds);
            }
            taskLog.writeInfoLog("\u51c6\u5907\u5355\u4f4d\u53c2\u6570", Float.valueOf(2.0f));
            List<GcOrgCacheVO> orgs = OrgUtils.getAllHbUnitSortByParentsLengthAsc(paramsVO);
            Map<String, GcOrgCacheVO> filterMap = OrgUtils.getUnUploadUnit(paramsVO).stream().collect(Collectors.toMap(GcOrgCacheVO::getId, o -> o));
            GcCalcArgmentsDTO calcArgments = new GcCalcArgmentsDTO();
            calcArgments.setOrgType(paramsVO.getOrgType());
            calcArgments.setRuleIds(paramsVO.getRuleIds());
            calcArgments.setAcctPeriod(paramsVO.getAcctPeriod());
            calcArgments.setTaskId(paramsVO.getTaskId());
            calcArgments.setAcctYear(paramsVO.getAcctYear());
            calcArgments.setSchemeId(paramsVO.getSchemeId());
            calcArgments.setPeriodType(paramsVO.getPeriodType());
            calcArgments.setPeriodStr(paramsVO.getPeriodStr());
            calcArgments.setSn(paramsVO.getTaskLogId());
            calcArgments.setCurrency(paramsVO.getCurrency());
            calcArgments.setSelectAdjustCode(paramsVO.getSelectAdjustCode());
            taskLog.setTotalNum(Integer.valueOf(orgs.size()));
            for (GcOrgCacheVO org : orgs) {
                GcCalcAndFinishResultVO gcCalcResultVO = new GcCalcAndFinishResultVO();
                processUnitName = org.getTitle();
                taskLog.setDoneNum(Integer.valueOf(taskLog.getDoneNum() + 1));
                taskLog.writeInfoLog("\u5f00\u59cb\u6267\u884c: " + org.getTitle(), Float.valueOf(taskLog.getProcessPercent()));
                if (!isCorporate) {
                    taskLog.writeWarnLog("\u7ba1\u7406\u67b6\u6784\u65b9\u6848\u590d\u7528\u6cd5\u4eba\u53e3\u5f84\u7684\u62b5\u9500\u6570\u636e\uff0c\u65e0\u9700\u6267\u884c\u5408\u5e76\u8ba1\u7b97\u3002", null);
                    break;
                }
                if (this.onekeyMergeService.getStopOrNot(paramsVO.getTaskLogId().toString())) {
                    throw new RuntimeException("\u624b\u52a8\u505c\u6b62");
                }
                boolean containsKey = filterMap.containsKey(org.getId());
                if (!containsKey) {
                    taskLog.writeWarnLog(org.getTitle() + "\u5df2\u7ecf\u4e0a\u62a5\u6216\u9001\u5ba1\uff0c\u8df3\u8fc7", null);
                    restList.add((GcBaseTaskStateVO)this.buildCalcAndFinishResultVO(gcCalcResultVO, org, org.getTitle() + "\u5df2\u7ecf\u4e0a\u62a5\u6216\u9001\u5ba1\uff0c\u8df3\u8fc7", null));
                    continue;
                }
                taskLog.writeInfoLog(org.getTitle() + "\u6b63\u5728\u5408\u5e76\u8ba1\u7b97", Float.valueOf(taskLog.getProcessPercent()));
                logger.debug(org.getTitle() + ":\u6b63\u5728\u8fdb\u884c\u5408\u5e76\u8ba1\u7b97");
                calcArgments.setOrgId(org.getId());
                Date calcStart = DateUtils.now();
                GcCalcEnvContext envContext = this.calcService.calc(calcArgments);
                boolean successFlag = envContext.isSuccessFlag();
                List result = envContext.getResult();
                gcCalcResultVO.setOrgName(org.getTitle());
                gcCalcResultVO.setCalcUseTime(Integer.valueOf((int)DateUtils.diffOf((Date)calcStart, (Date)DateUtils.now(), (int)14)));
                gcCalcResultVO.setCalcPrcessResult(String.join((CharSequence)";\n", result));
                if (successFlag) {
                    taskLog.writeInfoLog(org.getTitle() + "\u5408\u5e76\u8ba1\u7b97\u6210\u529f: ", null);
                    int onceLogStartIndex = taskLog.getCompleteMessage().size();
                    Date finishCalcStart = DateUtils.now();
                    paramsVO.setOrgId(org.getId());
                    ReturnObject returnObject = this.finishCalcTask.finishCalcByUnit(paramsVO, taskLog);
                    List onceLogs = taskLog.getCompleteMessage().subList(onceLogStartIndex, taskLog.getCompleteMessage().size());
                    gcCalcResultVO.setPrcessResult(JsonUtils.writeValueAsString(onceLogs));
                    gcCalcResultVO.setUseTime(Long.valueOf(DateUtils.diffOf((Date)finishCalcStart, (Date)DateUtils.now(), (int)14)));
                    if (!returnObject.isSuccess()) {
                        restList.add((GcBaseTaskStateVO)gcCalcResultVO);
                        throw new RuntimeException(org.getTitle() + "\u5b8c\u6210\u5408\u5e76\u5931\u8d25:" + returnObject.getErrorMessage());
                    }
                    restList.add((GcBaseTaskStateVO)gcCalcResultVO);
                    continue;
                }
                restList.add((GcBaseTaskStateVO)gcCalcResultVO);
                throw new RuntimeException(org.getTitle() + "\u5408\u5e76\u8ba1\u7b97\u5931\u8d25:");
            }
            state = TaskStateEnum.SUCCESS;
        }
        catch (Exception e) {
            state = TaskStateEnum.ERROR;
            taskLog.setFinish(true);
            taskLog.writeErrorLog(processUnitName + "\u5b8c\u6210\u5408\u5e76\u7ec8\u6b62: " + e.getMessage(), Float.valueOf(taskLog.getProcessPercent()));
            logger.error("\u5408\u5e76\u8ba1\u7b97\u51fa\u73b0\u5f02\u5e38,\u7a0b\u5e8f\u7ec8\u6b62", e);
        }
        finally {
            taskLog.setFinish(true);
            taskLog.setState(state);
            this.onekeyMergeService.saveTaskResult(paramsVO, taskResultEO, restList, state.getCode());
        }
        taskLog.endTask();
        return new ReturnObject(state.equals((Object)TaskStateEnum.SUCCESS), restList.stream().sorted((o1, o2) -> -1).collect(Collectors.toList()));
    }

    private GcCalcAndFinishResultVO buildCalcAndFinishResultVO(GcCalcAndFinishResultVO vo, GcOrgCacheVO org, String msg, Long start) {
        vo.setId(UUIDUtils.newUUIDStr());
        vo.setOrgName(org.getTitle());
        vo.setState(msg);
        if (null != start) {
            Long useTime = System.currentTimeMillis() - start;
            vo.setUseTime(useTime);
        }
        return vo;
    }
}

