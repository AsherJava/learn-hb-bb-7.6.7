/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.plantask.extend.job.PlanTaskRunner
 *  com.jiuqi.common.plantask.extend.job.Runner
 *  com.jiuqi.common.plantask.extend.utils.ChangeContextUtils
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.common.task.common.TaskPeriodUtils
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.onekeymerge.dto.AutoMergeDTO
 *  com.jiuqi.gcreport.onekeymerge.dto.MergeTaskProcessDTO
 *  com.jiuqi.gcreport.onekeymerge.dto.MergeTaskResultLogDTO
 *  com.jiuqi.gcreport.onekeymerge.enums.CalPeriodWayEnum
 *  com.jiuqi.gcreport.onekeymerge.enums.MergeTypeEnum
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.temp.dto.Message
 *  com.jiuqi.gcreport.temp.dto.OnekeyProgressDataImpl
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.gcreport.onekeymerge.runner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.plantask.extend.job.PlanTaskRunner;
import com.jiuqi.common.plantask.extend.job.Runner;
import com.jiuqi.common.plantask.extend.utils.ChangeContextUtils;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.common.task.common.TaskPeriodUtils;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.onekeymerge.dto.AutoMergeDTO;
import com.jiuqi.gcreport.onekeymerge.dto.MergeTaskProcessDTO;
import com.jiuqi.gcreport.onekeymerge.dto.MergeTaskResultLogDTO;
import com.jiuqi.gcreport.onekeymerge.enums.CalPeriodWayEnum;
import com.jiuqi.gcreport.onekeymerge.enums.MergeTypeEnum;
import com.jiuqi.gcreport.onekeymerge.service.GcMergeTaskService;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyMergeUtils;
import com.jiuqi.gcreport.onekeymerge.util.OrgUtils;
import com.jiuqi.gcreport.onekeymerge.util.TaskTypeEnum;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.temp.dto.Message;
import com.jiuqi.gcreport.temp.dto.OnekeyProgressDataImpl;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@PlanTaskRunner(id="479CF3EE4AO84HF7B63E119B7JC74273", settingPage="autoMergeConfig", name="com.jiuqi.gcreport.automerge.runner.AutoMergeRunner", title="\u5408\u5e76\u4e2d\u5fc3\u8ba1\u5212\u4efb\u52a1")
public class AutoMergeRunner
extends Runner {
    private final Logger logger = LoggerFactory.getLogger(((Object)((Object)this)).getClass());
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private GcMergeTaskService gcMergeTaskService;
    @Value(value="${gc.autoMerge.timeout:24}")
    private Long timeout;

    public boolean excute(JobContext jobContext) {
        try {
            ChangeContextUtils.buildContext((JobContext)jobContext);
        }
        catch (JQException e) {
            this.appendLog("\u8bbe\u7f6e\u7528\u6237\u4e0a\u4e0b\u6587\u5931\u8d25");
            this.logger.info("\u8bbe\u7f6e\u7528\u6237\u4e0a\u4e0b\u6587\u5931\u8d25", e);
            return false;
        }
        AutoMergeDTO autoMergeDTO = (AutoMergeDTO)JsonUtils.readValue((String)jobContext.getJob().getExtendedConfig(), (TypeReference)new TypeReference<AutoMergeDTO>(){});
        if (null == autoMergeDTO) {
            this.appendLog("\u672a\u8bbe\u7f6e\u9ad8\u7ea7\u53c2\u6570");
            return false;
        }
        try {
            StringBuilder paramLog = new StringBuilder();
            ArrayList<TaskTypeEnum> taskTypeEnums = new ArrayList<TaskTypeEnum>();
            GcActionParamsVO gcActionParamsVO = this.buildParam(autoMergeDTO, paramLog, taskTypeEnums);
            paramLog.append("\u5e01\u79cd\uff1a").append(this.getCurrencyTitle(gcActionParamsVO.getCurrency())).append("\n").append("\u5355\u4f4d\u7c7b\u578b\uff1a").append(this.getOrgTypeTitle(gcActionParamsVO.getOrgType())).append("\n").append("\u5408\u5e76\u65b9\u5f0f\uff1a").append(MergeTypeEnum.getEnumByCode((String)autoMergeDTO.getMergeMode()).getTitle()).append("\n").append("\u65f6\u671f\uff1a").append(gcActionParamsVO.getPeriodStr()).append("\n").append("\u5408\u5e76\u5c42\u7ea7\uff1a").append(autoMergeDTO.getMergeOrgProp().stream().map(GcOrgCacheVO::getTitle).collect(Collectors.toList())).append("\n").append("\u5408\u5e76\u4e8b\u9879\uff1a").append(taskTypeEnums.stream().map(TaskTypeEnum::getStateInfo).collect(Collectors.toList())).append("\n");
            GcOrgTypeUtils.setContextEntityId((String)gcActionParamsVO.getOrgType());
            OnekeyProgressDataImpl onekeyProgressData = new OnekeyProgressDataImpl(OneKeyMergeUtils.generateSN("process", gcActionParamsVO.getTaskLogId()));
            gcActionParamsVO.setOnekeyProgressData(onekeyProgressData);
            gcActionParamsVO.setNpContext(NpContextHolder.getContext());
            List mergeOrgProp = autoMergeDTO.getMergeOrgProp();
            ArrayList<GcOrgCacheVO> gcOrgCacheVOS = new ArrayList<GcOrgCacheVO>();
            mergeOrgProp.forEach(org -> {
                GcOrgCacheVO orgByCode = OrgUtils.getOrgByCode(gcActionParamsVO.getPeriodStr(), gcActionParamsVO.getOrgType(), org.getCode());
                gcOrgCacheVOS.add(orgByCode);
            });
            jobContext.getDefaultLogger().info("\n\u3010\u4efb\u52a1\u53c2\u6570\u3011:\n" + paramLog);
            this.logger.info("\u5408\u5e76\u4e2d\u5fc3\u6267\u884c\u53c2\u6570={}", (Object)JsonUtils.writeValueAsString((Object)gcActionParamsVO));
            String logId = UUIDUtils.newUUIDStr();
            gcActionParamsVO.setTaskLogId(logId);
            if (mergeOrgProp.size() == 1) {
                gcActionParamsVO.setOrgId(((GcOrgCacheVO)gcOrgCacheVOS.get(0)).getId());
            }
            this.gcMergeTaskService.createTaskTree(gcOrgCacheVOS, gcActionParamsVO);
            this.gcMergeTaskService.mergeTask(gcOrgCacheVOS, gcActionParamsVO);
            long begin = System.currentTimeMillis();
            while (true) {
                if (this.isTimeOut(begin)) {
                    jobContext.getDefaultLogger().info("\u6267\u884c\u4efb\u52a1\u8d85\u65f6\u3002\n");
                    this.logger.info("\u6267\u884c\u4efb\u52a1\u8d85\u65f6,\u8d85\u65f6\u65f6\u95f4={}", (Object)this.timeout);
                    return false;
                }
                MergeTaskProcessDTO process = this.gcMergeTaskService.getProcess(logId);
                if (!TaskStateEnum.EXECUTING.getCode().equals(process.getTaskState())) {
                    MergeTaskResultLogDTO mergeTaskLogs = this.gcMergeTaskService.getMergeTaskLogs(logId);
                    List mergeTaskInfos = mergeTaskLogs.getMergeTaskInfos();
                    List errorLogs = mergeTaskLogs.getErrorLogs();
                    mergeTaskInfos.addAll(errorLogs);
                    for (Message message : mergeTaskInfos) {
                        jobContext.getDefaultLogger().info(message.getMessage() + "\n");
                    }
                    if (TaskStateEnum.ERROR.getCode().equals(process.getTaskState())) {
                        this.appendLog("\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5931\u8d25");
                        return false;
                    }
                    break;
                }
                TimeUnit.MILLISECONDS.sleep(2000L);
            }
        }
        catch (Exception e) {
            this.appendLog("\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5f02\u5e38\uff1a" + JsonUtils.writeValueAsString((Object)e));
            this.logger.info("\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5f02\u5e38", e);
        }
        this.appendLog("\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u6210\u529f");
        return true;
    }

    private GcActionParamsVO buildParam(AutoMergeDTO autoMergeDTO, StringBuilder paramLog, List<TaskTypeEnum> taskTypeEnums) {
        GcActionParamsVO gcActionParamsVO = new GcActionParamsVO();
        gcActionParamsVO.setTaskId(autoMergeDTO.getTaskId());
        gcActionParamsVO.setCurrency(autoMergeDTO.getCurrency().getCode());
        gcActionParamsVO.setOrgType(autoMergeDTO.getOrgType());
        gcActionParamsVO.setMergeType(MergeTypeEnum.getEnumByCode((String)autoMergeDTO.getMergeMode()));
        gcActionParamsVO.setRuleIds(autoMergeDTO.getRuleIds());
        ArrayList<String> finalTaskNodes = new ArrayList<String>();
        for (TaskTypeEnum taskTypeEnum : TaskTypeEnum.values()) {
            if (!autoMergeDTO.getMergeTasks().contains(taskTypeEnum.getCode())) continue;
            finalTaskNodes.add(taskTypeEnum.getCode());
            taskTypeEnums.add(taskTypeEnum);
        }
        gcActionParamsVO.setTaskCodes(finalTaskNodes);
        this.buildPeriod(autoMergeDTO, gcActionParamsVO, paramLog);
        SchemePeriodLinkDefine schemePeriodLinkDefine = null;
        try {
            schemePeriodLinkDefine = this.iRunTimeViewController.querySchemePeriodLinkByPeriodAndTask(gcActionParamsVO.getPeriodStr(), autoMergeDTO.getTaskId());
            FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
            paramLog.append("\u65b9\u6848\uff1a").append(formScheme.getTitle()).append("\n");
        }
        catch (Exception exception) {
            // empty catch block
        }
        assert (schemePeriodLinkDefine != null);
        gcActionParamsVO.setSchemeId(schemePeriodLinkDefine.getSchemeKey());
        gcActionParamsVO.setSelectAdjustCode("0");
        return gcActionParamsVO;
    }

    private void buildPeriod(AutoMergeDTO autoMergeDTO, GcActionParamsVO gcActionParamsVO, StringBuilder paramLog) {
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(autoMergeDTO.getTaskId());
        paramLog.append("\u4efb\u52a1\uff1a").append(taskDefine.getTitle()).append("\n");
        CalPeriodWayEnum periodWayEnum = CalPeriodWayEnum.getEnumByCode((String)autoMergeDTO.getPeriodType());
        paramLog.append("\u65f6\u671f\u7c7b\u578b\uff1a").append(periodWayEnum.getTitle()).append("\n");
        PeriodWrapper currentPeriod = TaskPeriodUtils.getCurrentPeriod((int)taskDefine.getPeriodType().type());
        String priorPeriodStr = autoMergeDTO.getPeriodStr();
        switch (Objects.requireNonNull(periodWayEnum)) {
            case BEFORE: {
                DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
                PeriodWrapper periodWrapper = new PeriodWrapper(currentPeriod.toString());
                defaultPeriodAdapter.priorPeriod(periodWrapper);
                priorPeriodStr = periodWrapper.toString();
                break;
            }
            case CURRENT: {
                priorPeriodStr = currentPeriod.toString();
            }
        }
        gcActionParamsVO.setPeriodStr(priorPeriodStr);
        gcActionParamsVO.setPeriodType(Integer.valueOf(taskDefine.getPeriodType().type()));
        gcActionParamsVO.setAcctYear(Integer.valueOf(priorPeriodStr.substring(0, 4)));
        gcActionParamsVO.setAcctPeriod(Integer.valueOf(priorPeriodStr.substring(priorPeriodStr.length() - 4)));
    }

    private String getOrgTypeTitle(String orgType) {
        GCOrgTypeEnum enumByCode = GCOrgTypeEnum.getEnumByCode((String)orgType);
        return Objects.isNull(enumByCode) ? orgType : enumByCode.getTitle();
    }

    private String getCurrencyTitle(String currency) {
        BaseDataVO baseDataVO = GcBaseDataCenterTool.getInstance().queryBaseDataVoByCode("MD_CURRENCY", currency);
        return Objects.isNull(baseDataVO) ? currency : baseDataVO.getTitle();
    }

    private boolean isTimeOut(long begin) {
        long end = System.currentTimeMillis();
        long time = (end - begin) / 1000L / 60L / 60L;
        return time > this.timeout;
    }
}

