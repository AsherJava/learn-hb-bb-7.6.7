/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.calculate.common.GcCalcLogOperateEnum
 *  com.jiuqi.gcreport.calculate.entity.GcCalcLogEO
 *  com.jiuqi.gcreport.calculate.service.GcCalcLogService
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.util.ConsolidatedSystemUtils
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigDTO
 *  com.jiuqi.gcreport.formulaschemeconfig.service.FormulaSchemeConfigService
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.inputdata.util.InputDataConver
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.offsetitem.service.GcEndCarryForwardService
 *  com.jiuqi.gcreport.offsetitem.task.GcReclassifyTask
 *  com.jiuqi.gcreport.offsetitem.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.onekeymerge.config.FinishCalcConfig
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcBaseTaskStateVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcFinishCalcResultVO
 *  com.jiuqi.gcreport.onekeymerge.vo.ReturnObject
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.dataentry.asynctask.BatchDataSumAsyncTaskExecutor
 *  com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo
 *  com.jiuqi.nr.dataentry.paramInfo.BatchDataSumInfo
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.dataentry.service.IBatchCalculateService
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  javax.annotation.Resource
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.gcreport.onekeymerge.task;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.calculate.common.GcCalcLogOperateEnum;
import com.jiuqi.gcreport.calculate.entity.GcCalcLogEO;
import com.jiuqi.gcreport.calculate.service.GcCalcLogService;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.util.ConsolidatedSystemUtils;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigDTO;
import com.jiuqi.gcreport.formulaschemeconfig.service.FormulaSchemeConfigService;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.util.InputDataConver;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.offsetitem.service.GcEndCarryForwardService;
import com.jiuqi.gcreport.offsetitem.task.GcReclassifyTask;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.onekeymerge.config.FinishCalcConfig;
import com.jiuqi.gcreport.onekeymerge.entity.GcTaskResultEO;
import com.jiuqi.gcreport.onekeymerge.exception.OneKeyMergeDateSumTimeOutException;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyMergeService;
import com.jiuqi.gcreport.onekeymerge.task.GcCenterTask;
import com.jiuqi.gcreport.onekeymerge.task.GcDiffUnitReWriteTask;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyMergeUtils;
import com.jiuqi.gcreport.onekeymerge.util.OrgUtils;
import com.jiuqi.gcreport.onekeymerge.util.TaskTypeEnum;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcBaseTaskStateVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcFinishCalcResultVO;
import com.jiuqi.gcreport.onekeymerge.vo.ReturnObject;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.dataentry.asynctask.BatchDataSumAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchDataSumInfo;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.service.IBatchCalculateService;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcFinishCalcTaskImpl
implements GcCenterTask {
    private static final Logger logger = LoggerFactory.getLogger(GcFinishCalcTaskImpl.class);
    @Autowired
    private DefinitionAuthorityProvider authorityProvider;
    @Autowired
    private FinishCalcConfig finishCalcConfig;
    @Autowired
    private GcOnekeyMergeService onekeyMergeService;
    @Autowired
    private GcEndCarryForwardService gcEndCarryForwardService;
    @Autowired
    private IBatchCalculateService batchOperationService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private ConsolidatedTaskService taskService;
    @Autowired
    private ConsolidatedOptionService consolidatedOptionService;
    @Resource
    AsyncTaskManager asyncTaskManager;
    @Autowired
    private GcCalcLogService calcLogService;
    @Autowired
    private GcDiffUnitReWriteTask diffUnitReWriteTask;
    @Autowired
    private FormulaSchemeConfigService formulaSchemeConfigService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private List<GcReclassifyTask> reclassifyTasks;

    @Override
    public ReturnObject doTask(GcActionParamsVO paramsVO) {
        return this.doFinishCalc2(paramsVO);
    }

    @Override
    public ReturnObject paramCheck(GcActionParamsVO paramsVO) {
        ReturnObject returnObject = new ReturnObject(true);
        try {
            ConsolidatedTaskVO taskOption = this.taskService.getTaskBySchemeId(paramsVO.getSchemeId(), paramsVO.getPeriodStr());
            Boolean enableFinishCalc = taskOption.getEnableFinishCalc();
            if (!enableFinishCalc.booleanValue()) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.check.enableCalcDone"));
            }
            boolean canReadFormScheme = this.authorityProvider.canReadFormScheme(paramsVO.getSchemeId());
            if (!canReadFormScheme) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.check.noSchemeAuth"));
            }
            DimensionParamsVO dimensionParamsVO = OneKeyMergeUtils.convert2DimParamVO(paramsVO);
            GcOrgCacheVO currentUnit = OrgUtils.getCurrentUnit(paramsVO);
            ReadWriteAccessDesc writeable = new UploadStateTool().writeable(dimensionParamsVO);
            if (!writeable.getAble().booleanValue()) {
                throw new BusinessRuntimeException(currentUnit.getTitle() + "|" + paramsVO.getOrgId() + "\uff1a" + writeable.getDesc());
            }
            List children = currentUnit.getChildren();
            if (CollectionUtils.isEmpty((Collection)children)) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.check.useConsolidationunits"));
            }
            String diffUnitId = currentUnit.getDiffUnitId();
            if (StringUtils.isEmpty((CharSequence)diffUnitId)) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.check.consolidationWithoutDifference"));
            }
            dimensionParamsVO.setOrgId(diffUnitId);
            ReadWriteAccessDesc writeable2 = new UploadStateTool().writeable(dimensionParamsVO);
            if (!writeable2.getAble().booleanValue()) {
                throw new BusinessRuntimeException(diffUnitId + ":" + writeable2.getDesc());
            }
        }
        catch (BusinessRuntimeException e) {
            logger.error(e.getMessage(), e);
            returnObject.setSuccess(false);
            returnObject.setErrorMessage(e.getMessage());
        }
        return returnObject;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ReturnObject doFinishCalc2(GcActionParamsVO paramsVO) {
        TaskLog taskLog = new TaskLog(paramsVO.getOnekeyProgressData());
        taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.startCompleteMerger"), Float.valueOf(1.0f));
        TaskStateEnum state = TaskStateEnum.EXECUTING;
        ArrayList<GcBaseTaskStateVO> restList = new ArrayList<GcBaseTaskStateVO>();
        GcTaskResultEO taskResultEO = new GcTaskResultEO();
        BeanUtils.copyProperties(paramsVO, (Object)taskResultEO);
        String processUnitName = "";
        try {
            ReturnObject checkResult = this.paramCheck(paramsVO);
            if (!checkResult.isSuccess()) {
                taskLog.writeWarnLog(checkResult.getErrorMessage(), Float.valueOf(1.0f));
                throw new BusinessRuntimeException(checkResult.getErrorMessage());
            }
            String fullname = OneKeyMergeUtils.getUser().getFullname();
            String name = StringUtils.isEmpty((CharSequence)fullname) ? OneKeyMergeUtils.getUser().getName() : fullname;
            taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.executor") + ":  " + name + "", Float.valueOf(1.0f));
            taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.startTime") + DateUtils.nowTimeStr((String)"yyyy-MM-dd HH:mm:ss") + "", Float.valueOf(1.0f));
            taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.unitParameters"), Float.valueOf(2.0f));
            List<GcOrgCacheVO> orgs = OrgUtils.getAllHbUnitSortByParentsLengthAsc(paramsVO);
            taskLog.setTotalNum(Integer.valueOf(orgs.size()));
            for (GcOrgCacheVO org : orgs) {
                processUnitName = org.getTitle();
                GcFinishCalcResultVO finishCalcResultVO = new GcFinishCalcResultVO();
                finishCalcResultVO.setStartTime(new Date());
                int onceLogStartIndex = orgs.size() == 1 ? 0 : taskLog.getCompleteMessage().size();
                taskLog.setDoneNum(Integer.valueOf(taskLog.getDoneNum() + 1));
                taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.start") + ": " + org.getTitle(), Float.valueOf(taskLog.getProcessPercent())).syncTaskLog();
                this.checkStopOrNot(paramsVO, taskLog);
                DimensionParamsVO dimensionParamsVO = OneKeyMergeUtils.convert2DimParamVO(paramsVO);
                dimensionParamsVO.setOrgId(org.getId());
                ReadWriteAccessDesc writeable = new UploadStateTool().writeable(dimensionParamsVO);
                if (!writeable.getAble().booleanValue()) {
                    taskLog.writeWarnLog(org.getTitle() + writeable.getDesc() + ", " + GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.skip"), null);
                    restList.add((GcBaseTaskStateVO)this.buildFinishCalcResultVO(finishCalcResultVO, org, writeable.getDesc(), null));
                    continue;
                }
                String diffUnitId = OrgUtils.getCurrentUnit(paramsVO).getDiffUnitId();
                if (StringUtils.isEmpty((CharSequence)diffUnitId)) {
                    taskLog.writeWarnLog(org.getTitle() + writeable.getDesc() + ", " + GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.skip"), null);
                    restList.add((GcBaseTaskStateVO)this.buildFinishCalcResultVO(finishCalcResultVO, org, writeable.getDesc() + ", " + GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.skip"), null));
                    continue;
                }
                GcOrgCacheVO diffUnitOrg = OrgUtils.getCurrentUnit(paramsVO.getOrgType(), paramsVO.getPeriodStr(), diffUnitId);
                if (null == org.getOrgTypeId()) {
                    taskLog.writeWarnLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.check.noOrgTypeNoCalcDone"), null);
                    restList.add((GcBaseTaskStateVO)this.buildFinishCalcResultVO(finishCalcResultVO, org, "\u5f53\u524d\u5355\u4f4d\u7248\u672c\u4e2d\u7684\u5408\u5e76\u5355\u4f4d\u7684\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u4e3a\u7a7a,\u4e0d\u5141\u8bb8\u6267\u884c\u5b8c\u6210\u5408\u5e76", null));
                    continue;
                }
                if (!org.getOrgTypeId().equals(diffUnitOrg.getOrgTypeId())) {
                    taskLog.writeWarnLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.check.sameOrgTypeCalcDone"), null);
                    restList.add((GcBaseTaskStateVO)this.buildFinishCalcResultVO(finishCalcResultVO, org, "\u5f53\u524d\u5355\u4f4d\u7248\u672c\u4e2d\u7684\u5408\u5e76\u5355\u4f4d\u548c\u5dee\u989d\u5355\u4f4d\u7684\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u4e0d\u4e00\u81f4,\u4e0d\u5141\u8bb8\u6267\u884c\u5b8c\u6210\u5408\u5e76", null));
                    continue;
                }
                String failReason = this.failReasonByScheme(paramsVO, org, diffUnitOrg);
                if (failReason != null) {
                    taskLog.writeWarnLog(failReason, null);
                    restList.add((GcBaseTaskStateVO)this.buildFinishCalcResultVO(finishCalcResultVO, org, failReason, null));
                    continue;
                }
                DimensionParamsVO dimensionParamsVO2 = OneKeyMergeUtils.convert2DimParamVO(paramsVO);
                dimensionParamsVO2.setOrgId(diffUnitId);
                ReadWriteAccessDesc writeable2 = new UploadStateTool().writeable(dimensionParamsVO2);
                if (!writeable2.getAble().booleanValue()) {
                    taskLog.writeWarnLog(org.getTitle() + writeable.getDesc() + ", " + GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.skip"), null);
                    restList.add((GcBaseTaskStateVO)this.buildFinishCalcResultVO(finishCalcResultVO, org, writeable.getDesc() + ", " + GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.skip"), null));
                    continue;
                }
                taskResultEO.setUserName(name);
                taskResultEO.setTaskTime(DateUtils.now());
                taskResultEO.setTaskCode(TaskTypeEnum.FINISHCALC.getCode());
                List<String> hbUnitIds = Collections.singletonList(org.getId());
                List<String> diffUnitIds = Collections.singletonList(org.getDiffUnitId());
                GcActionParamsVO param = new GcActionParamsVO();
                BeanUtils.copyProperties(paramsVO, param);
                param.setOrgId(org.getId());
                ReturnObject returnObject = this.finishCalc(param, diffUnitIds, hbUnitIds, taskLog, 0);
                finishCalcResultVO.setEndTime(new Date());
                List onceLogs = taskLog.getCompleteMessage().subList(onceLogStartIndex, taskLog.getCompleteMessage().size());
                finishCalcResultVO.setPrcessResult(JsonUtils.writeValueAsString(onceLogs));
                if (!returnObject.isSuccess()) {
                    this.buildFinishCalcResultVO(finishCalcResultVO, org, "\u5931\u8d25", finishCalcResultVO.getStartTime().getTime());
                    restList.add((GcBaseTaskStateVO)finishCalcResultVO);
                    throw new BusinessRuntimeException(returnObject.getErrorMessage());
                }
                this.buildFinishCalcResultVO(finishCalcResultVO, org, "\u6210\u529f", finishCalcResultVO.getStartTime().getTime());
                restList.add((GcBaseTaskStateVO)finishCalcResultVO);
            }
            state = TaskStateEnum.SUCCESS;
        }
        catch (Exception e) {
            state = TaskStateEnum.ERROR;
            logger.error("\u5b8c\u6210\u5408\u5e76\u51fa\u73b0\u5f02\u5e38,\u4efb\u52a1\u7ec8\u6b62", e);
        }
        finally {
            taskLog.setState(state);
            taskLog.endTask();
            this.onekeyMergeService.saveTaskResult(paramsVO, taskResultEO, restList, state.getCode());
        }
        return new ReturnObject(state.equals((Object)TaskStateEnum.SUCCESS), restList.stream().sorted((o1, o2) -> -1).collect(Collectors.toList()));
    }

    private String failReasonByScheme(GcActionParamsVO paramsVO, GcOrgCacheVO org, GcOrgCacheVO diffUnitOrg) {
        GcOrgCacheVO corporateDiffUnitOrg;
        ConsolidatedTaskVO taskVO = this.taskService.getTaskBySchemeId(paramsVO.getSchemeId(), paramsVO.getPeriodStr());
        if (null == taskVO) {
            return "\u5f53\u524d\u62a5\u8868\u65b9\u6848\u65e2\u4e0d\u662f\u91c7\u96c6\u586b\u62a5\u65b9\u6848\u53c8\u4e0d\u662f\u7ba1\u7406\u67b6\u6784\u65b9\u6848";
        }
        List allInputSchemeList = ConsolidatedSystemUtils.listAllInputSchemeByConTaskVO((ConsolidatedTaskVO)taskVO);
        boolean isCorporate = allInputSchemeList.contains(paramsVO.getSchemeId());
        if (isCorporate) {
            return null;
        }
        if (null != taskVO.getManageCalcUnitCodes() && taskVO.getManageCalcUnitCodes().contains(org.getId())) {
            return null;
        }
        String inputSchemeId = ConsolidatedSystemUtils.getSchemeIdByTaskIdAndPeriod((String)taskVO.getTaskKey(), (String)paramsVO.getPeriodStr());
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(inputSchemeId);
        if (null == formScheme || StringUtils.isEmpty((CharSequence)formScheme.getDw())) {
            return null;
        }
        String currDiffOrgTypeId = diffUnitOrg.getOrgTypeId();
        if (null == currDiffOrgTypeId) {
            return "\u5dee\u989d\u5355\u4f4d\u672a\u8bbe\u7f6e\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b";
        }
        String corporateOrgTypeId = taskVO.getCorporateEntity();
        if (!taskVO.isTaskVersion2_0()) {
            TableModelDefine tableDefine = this.entityMetaService.getTableModel(formScheme.getDw());
            corporateOrgTypeId = tableDefine.getName();
        }
        if (null != (corporateDiffUnitOrg = OrgUtils.getCurrentUnit(corporateOrgTypeId, paramsVO.getPeriodStr(), org.getDiffUnitId())) && currDiffOrgTypeId.equals(corporateDiffUnitOrg.getOrgTypeId())) {
            return "\u5f53\u524d\u62a5\u8868\u65b9\u6848\u4e0e\u91c7\u96c6\u586b\u62a5\u65b9\u6848\u3010" + formScheme.getTitle() + "\u3011\u7684\u5dee\u989d\u5355\u4f4d\u7684\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u4e00\u81f4,\u65e0\u9700\u91cd\u590d\u5b8c\u6210\u5408\u5e76";
        }
        return null;
    }

    private QueryParamsVO generateLossGainVO(GcActionParamsVO paramsVO) {
        QueryParamsVO queryParamsVO = new QueryParamsVO();
        BeanUtils.copyProperties(paramsVO, queryParamsVO);
        queryParamsVO.setCurrency(paramsVO.getCurrency());
        queryParamsVO.setOrgId(paramsVO.getOrgId());
        return queryParamsVO;
    }

    private ReturnObject dataRewrite(GcActionParamsVO paramsVO, List<String> hbUnitIds, List<String> diffUnitIds, TaskLog taskLog) {
        return this.diffUnitReWriteTask.doTask(paramsVO, hbUnitIds, diffUnitIds, taskLog);
    }

    public ReturnObject calculateForm(GcActionParamsVO paramsVO, List<String> orgIds, TaskLog taskLog) {
        ReturnObject returnObject = new ReturnObject(true);
        GcOrgTypeUtils.setContextEntityId((String)paramsVO.getOrgType());
        for (String orgId : orgIds) {
            this.checkStopOrNot(paramsVO, taskLog);
            BatchCalculateInfo batchCalculateInfo = new BatchCalculateInfo();
            Map<String, DimensionValue> dimensionValueMap = this.onekeyMergeService.buildDimensionMap(paramsVO.getTaskId(), paramsVO.getCurrency(), paramsVO.getPeriodStr(), paramsVO.getOrgType(), orgId, paramsVO.getSelectAdjustCode());
            batchCalculateInfo.setDimensionSet(dimensionValueMap);
            batchCalculateInfo.setTaskKey(paramsVO.getTaskId());
            batchCalculateInfo.setFormSchemeKey(paramsVO.getSchemeId());
            FormulaSchemeConfigDTO dto = this.formulaSchemeConfigService.getSchemeConfigByOrgAndAssistDim(paramsVO.getSchemeId(), orgId, InputDataConver.getDimFieldValueMap(dimensionValueMap, (String)paramsVO.getTaskId()));
            List completeMergeIds = dto == null ? null : dto.getCompleteMergeId();
            IFormulaRunTimeController formulaRunTimeController = (IFormulaRunTimeController)SpringContextUtils.getBean(IFormulaRunTimeController.class);
            if (CollectionUtils.isEmpty((Collection)completeMergeIds)) {
                FormulaSchemeDefine formula = this.getFormulaSchemeDefine(paramsVO);
                batchCalculateInfo.setFormulaSchemeKey(formula.getKey());
                logger.debug("\u5b8c\u6210\u5408\u5e76\u6267\u884c\u5168\u7b97\uff1a" + formula.getTitle());
                taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.calcDoneExeCalc") + "\uff1a" + formula.getTitle(), Float.valueOf(taskLog.getProcessPercent()));
            } else {
                ArrayList<FormulaSchemeDefine> formulas = new ArrayList<FormulaSchemeDefine>();
                for (String completeMergeId : completeMergeIds) {
                    FormulaSchemeDefine formula = formulaRunTimeController.queryFormulaSchemeDefine(completeMergeId);
                    formulas.add(formula);
                }
                String formulaKeys = formulas.stream().map(IBaseMetaItem::getKey).collect(Collectors.joining(";"));
                batchCalculateInfo.setFormulaSchemeKey(formulaKeys);
                String formulaTitles = formulas.stream().map(IBaseMetaItem::getTitle).collect(Collectors.joining(";"));
                logger.debug("\u5b8c\u6210\u5408\u5e76\u6267\u884c\u5168\u7b97\uff1a" + formulaTitles);
                taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.calcDoneExeCalc") + "\uff1a" + formulaTitles, Float.valueOf(taskLog.getProcessPercent()));
            }
            try {
                this.batchOperationService.batchCalculateForm(batchCalculateInfo);
            }
            catch (Exception e) {
                returnObject.setSuccess(false);
                returnObject.setErrorMessage(e.getMessage());
                logger.error(e.getMessage(), e);
            }
            if (returnObject.isSuccess()) continue;
            break;
        }
        return returnObject;
    }

    private FormulaSchemeDefine getFormulaSchemeDefine(GcActionParamsVO paramsVO) {
        IFormulaRunTimeController formulaRunTimeController = (IFormulaRunTimeController)SpringContextUtils.getBean(IFormulaRunTimeController.class);
        List definesByFormScheme = formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(paramsVO.getSchemeId());
        List formulaSchemeDefines = definesByFormScheme.stream().filter(formulaSchemeDefine -> {
            FormulaSchemeType schemeType = formulaSchemeDefine.getFormulaSchemeType();
            return schemeType.equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT) && formulaSchemeDefine.isDefault();
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(formulaSchemeDefines)) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230\u9ed8\u8ba4\u516c\u5f0f\u65b9\u6848");
        }
        return (FormulaSchemeDefine)formulaSchemeDefines.get(0);
    }

    private void checkStopOrNot(GcActionParamsVO paramsVO, TaskLog taskLog) {
        if (this.onekeyMergeService.getStopOrNot(paramsVO.getTaskLogId().toString())) {
            throw new BusinessRuntimeException("\u624b\u52a8\u505c\u6b62");
        }
    }

    public ReturnObject dataSum(GcActionParamsVO paramsVO, List<String> orgIds, TaskLog taskLog) {
        return this.dataSum(paramsVO, orgIds, taskLog, null);
    }

    public ReturnObject dataSum(GcActionParamsVO paramsVO, List<String> orgIds, TaskLog taskLog, String currFormKey) {
        ReturnObject returnObject = new ReturnObject(true);
        DimensionParamsVO param = new DimensionParamsVO();
        BeanUtils.copyProperties(paramsVO, param);
        for (String orgId : orgIds) {
            List<String> lockedForm;
            String lockTitle;
            this.checkStopOrNot(paramsVO, taskLog);
            Map<String, DimensionValue> dimensionValueMap = this.onekeyMergeService.buildDimensionMap(paramsVO.getTaskId(), paramsVO.getCurrency(), paramsVO.getPeriodStr(), paramsVO.getOrgType(), orgId, paramsVO.getSelectAdjustCode());
            List<String> filterForms = OneKeyMergeUtils.getFilterLockedAndHiddenForm(paramsVO.getSchemeId(), orgId, paramsVO);
            if (!StringUtils.isEmpty((CharSequence)currFormKey)) {
                filterForms = filterForms.stream().filter(formKey -> formKey.equals(currFormKey)).collect(Collectors.toList());
            }
            if (StringUtils.isNotEmpty((CharSequence)(lockTitle = (lockedForm = OneKeyMergeUtils.getLockedForm(paramsVO.getSchemeId(), orgId, paramsVO)).stream().map(s -> {
                FormDefine formDefine = this.runTimeViewController.queryFormById(s);
                return formDefine.getTitle();
            }).collect(Collectors.joining("\uff0c"))))) {
                taskLog.writeWarnLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.lockAndSkipSum") + "\uff1a" + lockTitle, Float.valueOf(taskLog.getProcessPercent()), Integer.valueOf(101));
            }
            IFormulaRunTimeController formulaRunTimeController = (IFormulaRunTimeController)SpringContextUtils.getBean(IFormulaRunTimeController.class);
            BatchDataSumInfo batchDataSumInfo = new BatchDataSumInfo();
            batchDataSumInfo.setFormKeys(String.join((CharSequence)";", filterForms));
            JtableContext jtableContext = new JtableContext();
            batchDataSumInfo.setContext(jtableContext);
            jtableContext.setTaskKey(paramsVO.getTaskId());
            jtableContext.setFormSchemeKey(paramsVO.getSchemeId());
            jtableContext.setDimensionSet(dimensionValueMap);
            jtableContext.setContextEntityId(DsContextHolder.getDsContext().getContextEntityId());
            List definesByFormScheme = formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(paramsVO.getSchemeId());
            List formulaSchemeDefines = definesByFormScheme.stream().filter(formulaSchemeDefine -> {
                FormulaSchemeType schemeType = formulaSchemeDefine.getFormulaSchemeType();
                return schemeType.equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT) && formulaSchemeDefine.isDefault();
            }).collect(Collectors.toList());
            FormulaSchemeDefine defaultFormula = (FormulaSchemeDefine)formulaSchemeDefines.get(0);
            jtableContext.setFormulaSchemeKey(defaultFormula.getKey());
            try {
                AsyncTask asyncTask;
                block8: {
                    AsyncTaskInfo resource = new AsyncTaskInfo();
                    NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
                    npRealTimeTaskInfo.setTaskKey(batchDataSumInfo.getContext().getTaskKey());
                    npRealTimeTaskInfo.setFormSchemeKey(batchDataSumInfo.getContext().getFormSchemeKey());
                    npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)batchDataSumInfo));
                    npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new BatchDataSumAsyncTaskExecutor());
                    String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
                    resource.setId(asynTaskID);
                    resource.setUrl("/api/asynctask/query?asynTaskID=");
                    int i = 0;
                    while (true) {
                        asyncTask = this.asyncTaskManager.queryTask(resource.getId());
                        TaskState state = this.asyncTaskManager.queryTaskState(resource.getId());
                        if (OneKeyMergeUtils.efdcProcessON(state)) {
                            Thread.sleep(1000L);
                            if (++i <= 60 * this.finishCalcConfig.getDataSumTimeOut()) continue;
                            String msg = "\u6570\u636e\u6c47\u603b\u8d85\u8fc7" + this.finishCalcConfig.getDataSumTimeOut() + "\u5206\u949f\u9608\u503c\uff0c\u5c06\u81ea\u52a8\u8f6c\u5165\u5f02\u6b65\u4efb\u52a1\u6c60\uff0c\u8bf730\u5206\u949f\u540e\u5230\u6570\u636e\u5f55\u5165\u67e5\u770b\uff0c\u5e76\u6267\u884c\u8fd0\u7b97\u3002";
                            taskLog.writeWarnLog(msg, Float.valueOf(100.0f));
                            throw new OneKeyMergeDateSumTimeOutException(msg);
                        }
                        if (OneKeyMergeUtils.efdcProcessEnd(state) || "summary_warn_info".equals(asyncTask.getResult())) break block8;
                        if (OneKeyMergeUtils.efdcProcessError(state)) break;
                    }
                    String detail = this.asyncTaskManager.queryDetailString(resource.getId());
                    String msg = GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.ConsolidationSumEnd.error") + (StringUtils.isEmpty((CharSequence)detail) ? "" : ": " + detail);
                    throw new BusinessRuntimeException(msg);
                }
                OneKeyMergeUtils.getResultFromDataSumAsyncTask(returnObject, asyncTask);
                returnObject.setSuccess(true);
            }
            catch (OneKeyMergeDateSumTimeOutException e) {
                throw e;
            }
            catch (Exception e) {
                returnObject.setSuccess(false);
                returnObject.setErrorMessage(e.getMessage());
                logger.error(e.getMessage(), e);
            }
            if (returnObject.isSuccess()) continue;
            break;
        }
        return returnObject;
    }

    public ReturnObject finishCalc(GcActionParamsVO paramsVO, List<String> diffUnitIds, List<String> hbUnitIds, TaskLog taskLog, int startIndex) {
        GcCalcLogEO insertCalcLogEO = this.calcLogService.insertCalcLogEO(Long.valueOf(1800000L), GcCalcLogOperateEnum.COMPLETE_CALC, paramsVO.getTaskId(), paramsVO.getCurrency(), paramsVO.getPeriodStr(), paramsVO.getOrgType(), paramsVO.getOrgId(), paramsVO.getSelectAdjustCode());
        try {
            boolean canDataSum;
            this.calcLogService.updateCalcLog(insertCalcLogEO.getId(), TaskStateEnum.EXECUTING);
            this.checkStopOrNot(paramsVO, taskLog);
            ConsolidatedTaskVO consolidatedTaskVO = this.taskService.getTaskBySchemeId(paramsVO.getSchemeId(), paramsVO.getPeriodStr());
            ConsolidatedOptionVO optionData = this.consolidatedOptionService.getOptionData(consolidatedTaskVO.getSystemId());
            if (paramsVO.getRewriteDiff().booleanValue()) {
                boolean enableLossGain;
                for (GcReclassifyTask reclassifyTask : this.reclassifyTasks) {
                    com.jiuqi.gcreport.offsetitem.vo.GcActionParamsVO paramsVO1 = new com.jiuqi.gcreport.offsetitem.vo.GcActionParamsVO();
                    BeanUtils.copyProperties(paramsVO, paramsVO1);
                    reclassifyTask.doTask(paramsVO1, hbUnitIds, taskLog);
                }
                boolean bl = enableLossGain = null != consolidatedTaskVO && (consolidatedTaskVO.getEnableDeferredIncomeTax() != false || consolidatedTaskVO.getEnableMinLossGainRecovery() != false || consolidatedTaskVO.getEnableLossGain() != false);
                if (enableLossGain) {
                    taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.carryForwardStarted"), Float.valueOf(26.0f)).syncTaskLog();
                    Date datejz = DateUtils.now();
                    QueryParamsVO lossGainVO = this.generateLossGainVO(paramsVO);
                    try {
                        this.gcEndCarryForwardService.doLossGain(lossGainVO);
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), e);
                        taskLog.writeErrorLog(e.getMessage(), Float.valueOf(taskLog.getProcessPercent()));
                        throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
                    }
                    taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.carryForwardEnd") + "," + GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.use") + ":" + DateUtils.diffOf((Date)datejz, (Date)DateUtils.now(), (int)13) + "s", Float.valueOf(35.0f));
                }
                YearPeriodObject yp = new YearPeriodObject(null, paramsVO.getPeriodStr());
                GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)paramsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
                GcOrgCacheVO diffOrg = tool.getOrgByCode(diffUnitIds.get(0));
                taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.balanceWriteStarted") + ":" + diffUnitIds.get(0) + "|" + diffOrg.getTitle(), Float.valueOf(36.0f)).syncTaskLog();
                Date daterw = DateUtils.now();
                OneKeyMergeUtils.buildSetpMessage(taskLog, this.dataRewrite(paramsVO, hbUnitIds, diffUnitIds, taskLog));
                taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.balanceWriteEnd") + "," + GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.use") + ":" + DateUtils.diffOf((Date)daterw, (Date)DateUtils.now(), (int)13) + "s", Float.valueOf(50.0f));
                taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.balanceCalc"), Float.valueOf(51.0f)).syncTaskLog();
                Date datecf = DateUtils.now();
                OneKeyMergeUtils.buildSetpMessage(taskLog, this.calculateForm(paramsVO, diffUnitIds, taskLog));
                taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.balanceCalcEnd") + "," + GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.use") + ":" + DateUtils.diffOf((Date)datecf, (Date)DateUtils.now(), (int)13) + "s", Float.valueOf(70.0f));
            }
            if (canDataSum = (CollectionUtils.isEmpty((Collection)paramsVO.getTaskCodes()) ? optionData.getEnableFinishCalcDataSum() : paramsVO.getDataSum()).booleanValue()) {
                taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.ConsolidationSum"), Float.valueOf(71.0f)).syncTaskLog();
                Date datesum = DateUtils.now();
                try {
                    OneKeyMergeUtils.buildSetpMessage(taskLog, this.dataSum(paramsVO, hbUnitIds, taskLog));
                }
                catch (OneKeyMergeDateSumTimeOutException e) {
                    logger.error(e.getMessage(), e);
                    this.calcLogService.updateCalcLogEO(insertCalcLogEO.getId(), JsonUtils.writeValueAsString((Object)taskLog.getCompleteMessage()), TaskStateEnum.SUCCESS);
                    return ReturnObject.ofSuccess(null);
                }
                taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.ConsolidationSumEnd") + "," + GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.use") + ":" + DateUtils.diffOf((Date)datesum, (Date)DateUtils.now(), (int)13) + "s", Float.valueOf(80.0f));
                taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.ConsolidationCalc"), Float.valueOf(81.0f)).syncTaskLog();
                Date datehcf = DateUtils.now();
                OneKeyMergeUtils.buildSetpMessage(taskLog, this.calculateForm(paramsVO, hbUnitIds, taskLog));
                taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.ConsolidationCalcEnd") + "," + GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.use") + ":" + DateUtils.diffOf((Date)datehcf, (Date)DateUtils.now(), (int)13) + "s", Float.valueOf(95.0f));
            }
            taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.calcDoneEnd"), Float.valueOf(96.0f));
            insertCalcLogEO.setEndtime(Long.valueOf(System.currentTimeMillis()));
            taskLog.writeInfoLog(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.endTime") + DateUtils.nowTimeStr((String)"yyyy-MM-dd HH:mm:ss"), Float.valueOf(100.0f));
            TaskStateEnum taskStateEnum = TaskStateEnum.SUCCESS;
            List onceLogs = taskLog.getCompleteMessage().subList(startIndex, taskLog.getCompleteMessage().size());
            this.calcLogService.updateCalcLogEO(insertCalcLogEO.getId(), JsonUtils.writeValueAsString(onceLogs), taskStateEnum);
            taskLog.syncTaskLog();
            return ReturnObject.ofSuccess(null);
        }
        catch (Exception e) {
            logger.error("\u5b8c\u6210\u5408\u5e76\u5931\u8d25\uff1a" + e.getMessage(), e);
            TaskStateEnum taskStateEnum = TaskStateEnum.ERROR;
            GcOrgCacheVO org = OrgUtils.getCurrentUnit(paramsVO);
            taskLog.writeErrorLog(org.getTitle() + GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.breakup"), Float.valueOf(taskLog.getProcessPercent()));
            List onceLogs = taskLog.getCompleteMessage().subList(startIndex, taskLog.getCompleteMessage().size());
            this.calcLogService.updateCalcLogEO(insertCalcLogEO.getId(), JsonUtils.writeValueAsString(onceLogs), taskStateEnum);
            return ReturnObject.ofFailed((String)e.getMessage(), null);
        }
    }

    public ReturnObject finishCalcByUnit(GcActionParamsVO paramsVO, TaskLog taskLog) {
        GcOrgCacheVO org = OrgUtils.getCurrentUnit(paramsVO);
        int index = taskLog.getCompleteMessage().size();
        taskLog.writeInfoLog("\u5f00\u59cb\u5b8c\u6210\u5408\u5e76", Float.valueOf(1.0f));
        String nickname = OneKeyMergeUtils.getUser().getFullname();
        String name = StringUtils.isEmpty((CharSequence)nickname) ? OneKeyMergeUtils.getUser().getName() : nickname;
        taskLog.writeInfoLog("\u6267\u884c\u4eba:  " + name + "", Float.valueOf(1.0f));
        taskLog.writeInfoLog("\u5f00\u59cb\u65f6\u95f4" + DateUtils.nowTimeStr((String)"yyyy-MM-dd HH:mm:ss") + "", Float.valueOf(1.0f));
        taskLog.writeInfoLog("\u51c6\u5907\u5355\u4f4d\u53c2\u6570", Float.valueOf(2.0f));
        taskLog.writeInfoLog("\u5f00\u59cb\u6267\u884c: " + org.getTitle(), Float.valueOf(taskLog.getProcessPercent()));
        DimensionParamsVO dimensionParamsVO = OneKeyMergeUtils.convert2DimParamVO(paramsVO);
        dimensionParamsVO.setOrgId(org.getId());
        ReadWriteAccessDesc writeable = new UploadStateTool().writeable(dimensionParamsVO);
        if (!writeable.getAble().booleanValue()) {
            taskLog.writeWarnLog(org.getTitle() + writeable.getDesc() + ", \u8df3\u8fc7", null);
            return ReturnObject.ofFailed((String)(org.getTitle() + writeable.getDesc() + ", \u8df3\u8fc7"), null);
        }
        String diffUnitId = OrgUtils.getCurrentUnit(paramsVO).getDiffUnitId();
        if (StringUtils.isEmpty((CharSequence)diffUnitId)) {
            taskLog.writeWarnLog(org.getTitle() + "\u6ca1\u6709\u627e\u5230\u5173\u8054\u7684\u5dee\u989d\u5355\u4f4d\uff0c\u8df3\u8fc7", null);
            return ReturnObject.ofFailed((String)(org.getTitle() + "\u6ca1\u6709\u627e\u5230\u5173\u8054\u7684\u5dee\u989d\u5355\u4f4d\uff0c\u8df3\u8fc7"), null);
        }
        DimensionParamsVO dimensionParamsVO2 = OneKeyMergeUtils.convert2DimParamVO(paramsVO);
        dimensionParamsVO2.setOrgId(diffUnitId);
        ReadWriteAccessDesc writeable2 = new UploadStateTool().writeable(dimensionParamsVO2);
        if (!writeable2.getAble().booleanValue()) {
            taskLog.writeWarnLog(org.getTitle() + writeable.getDesc() + ", \u8df3\u8fc7", null);
            return ReturnObject.ofFailed((String)(org.getTitle() + writeable.getDesc() + ", \u8df3\u8fc7"), null);
        }
        List<String> hbUnitIds = Collections.singletonList(org.getId());
        List<String> diffUnitIds = Collections.singletonList(org.getDiffUnitId());
        ReturnObject returnObject = this.finishCalc(paramsVO, diffUnitIds, hbUnitIds, taskLog, index);
        return returnObject;
    }

    private GcFinishCalcResultVO buildFinishCalcResultVO(GcFinishCalcResultVO vo, GcOrgCacheVO org, String msg, Long start) {
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

