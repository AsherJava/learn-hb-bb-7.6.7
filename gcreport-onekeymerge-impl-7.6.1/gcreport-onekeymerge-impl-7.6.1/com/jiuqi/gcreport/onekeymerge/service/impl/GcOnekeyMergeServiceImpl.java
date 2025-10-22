/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.RedisUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  com.jiuqi.common.expimp.progress.service.ProgressService
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.calculate.common.GcCalcLogOperateEnum
 *  com.jiuqi.gcreport.calculate.dao.GcCalcLogDao
 *  com.jiuqi.gcreport.calculate.entity.GcCalcLogEO
 *  com.jiuqi.gcreport.calculate.service.GcCalcLogService
 *  com.jiuqi.gcreport.calculate.vo.DebugZbInfoVO
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.conversion.common.GcConversionContextEnv
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcBaseTaskStateVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcTaskResultVO
 *  com.jiuqi.gcreport.onekeymerge.vo.ReturnObject
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.temp.dto.Message
 *  com.jiuqi.gcreport.temp.dto.Message$ProgressResult
 *  com.jiuqi.gcreport.temp.dto.OnekeyProgressDataImpl
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.onekeymerge.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.RedisUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.common.expimp.progress.service.ProgressService;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.calculate.common.GcCalcLogOperateEnum;
import com.jiuqi.gcreport.calculate.dao.GcCalcLogDao;
import com.jiuqi.gcreport.calculate.entity.GcCalcLogEO;
import com.jiuqi.gcreport.calculate.service.GcCalcLogService;
import com.jiuqi.gcreport.calculate.vo.DebugZbInfoVO;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.conversion.common.GcConversionContextEnv;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool;
import com.jiuqi.gcreport.onekeymerge.dao.TaskResultDao;
import com.jiuqi.gcreport.onekeymerge.entity.GcOnekeyMergeResultEO;
import com.jiuqi.gcreport.onekeymerge.entity.GcTaskResultEO;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyMergeService;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyMergeTaskService;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyProcessService;
import com.jiuqi.gcreport.onekeymerge.service.impl.GcOnekeyMergeResultServiceImpl;
import com.jiuqi.gcreport.onekeymerge.task.rewrite.impl.GcDiffUnitOrdinaryReWriteSubTaskImpl;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyMergeUtils;
import com.jiuqi.gcreport.onekeymerge.util.OrgUtils;
import com.jiuqi.gcreport.onekeymerge.util.TaskTypeEnum;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcBaseTaskStateVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcTaskResultVO;
import com.jiuqi.gcreport.onekeymerge.vo.ReturnObject;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.temp.dto.Message;
import com.jiuqi.gcreport.temp.dto.OnekeyProgressDataImpl;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GcOnekeyMergeServiceImpl
implements GcOnekeyMergeService {
    private static final Logger logger = LoggerFactory.getLogger(GcOnekeyMergeServiceImpl.class);
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    private GcOnekeyProcessService onekeyProcessService;
    @Autowired
    private GcOnekeyMergeResultServiceImpl onekeyMergeResultService;
    @Autowired
    private GcCalcLogDao calcLogDao;
    @Autowired
    private GcCalcLogService calcLogService;
    @Autowired
    private TaskResultDao taskResultDao;
    @Autowired
    private GcOnekeyMergeTaskService taskService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ProgressService<OnekeyProgressDataImpl, Message.ProgressResult> progressService;

    @Override
    @Async
    public void doMergeAsync(GcActionParamsVO paramsVO) {
        this.doMerge(paramsVO);
    }

    @Override
    public void doMerge(GcActionParamsVO paramsVO) {
        NpContextHolder.setContext((NpContext)paramsVO.getNpContext());
        List taskCodes = paramsVO.getTaskCodes();
        GcOnekeyMergeResultEO eo = new GcOnekeyMergeResultEO();
        BeanUtils.copyProperties(paramsVO, (Object)eo);
        eo.setId(paramsVO.getTaskLogId());
        eo.setTaskTime(new Date());
        String nickname = OneKeyMergeUtils.getUser().getFullname();
        String name = OneKeyMergeUtils.getUser().getName();
        eo.setUserName(org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)nickname) ? name : nickname);
        eo.setTaskCodes(String.join((CharSequence)";", paramsVO.getTaskCodes()));
        boolean flag = true;
        OnekeyProgressDataImpl onekeyProgressData = new OnekeyProgressDataImpl(OneKeyMergeUtils.generateSN("process", paramsVO.getTaskLogId()));
        this.progressService.createProgressData((ProgressData)onekeyProgressData);
        TaskLog taskLog = new TaskLog(onekeyProgressData);
        int i = 0;
        for (String taskCode : taskCodes) {
            taskLog.setProcess(i);
            taskLog.syncTaskLog();
            ++i;
            ReturnObject returnObject = null;
            try {
                returnObject = this.taskService.doTask(paramsVO, TaskTypeEnum.getByCode(taskCode));
                if (returnObject.isSuccess()) continue;
                eo.setLastTask(taskCode);
                flag = false;
            }
            catch (Exception e) {
                eo.setLastTask(taskCode);
                logger.error(e.getMessage(), e);
                flag = false;
            }
            break;
        }
        taskLog.setProcessPercent(Float.valueOf(1.0f));
        taskLog.setFinish(true);
        taskLog.setState(flag ? TaskStateEnum.SUCCESS : TaskStateEnum.ERROR);
        eo.setTaskState(flag ? 1 : 0);
        taskLog.syncTaskLog();
        this.onekeyMergeResultService.saveResult(eo);
    }

    @Override
    public void stopMerge(GcActionParamsVO paramsVO) {
        String taskLogId = paramsVO.getTaskLogId().replace("-", "").toUpperCase();
        this.redisUtils.set("ONEKEYMERGE_" + taskLogId, (Object)"STOP");
        List taskCodes = paramsVO.getTaskCodes();
        if (taskCodes.contains(TaskTypeEnum.CALC.getCode()) || taskCodes.contains(TaskTypeEnum.FINISHCALC.getCode())) {
            this.stopCalcAndFinishCalc(paramsVO);
        }
    }

    @Override
    public boolean getStopOrNot(String taskLogId) {
        taskLogId = taskLogId.replace("-", "").toUpperCase();
        String state = String.valueOf(this.redisUtils.get("ONEKEYMERGE_" + taskLogId));
        return "STOP".equalsIgnoreCase(state);
    }

    @Override
    public DebugZbInfoVO debugZbReWrite(GcActionParamsVO gcActionParamsVO, String zbCode) {
        return ((GcDiffUnitOrdinaryReWriteSubTaskImpl)SpringContextUtils.getBean(GcDiffUnitOrdinaryReWriteSubTaskImpl.class)).debugZbReWrite(gcActionParamsVO, zbCode);
    }

    @Override
    public ReturnObject checkUpLoadAndFinishCalState(GcActionParamsVO paramsVO, String orgid) {
        ReturnObject x = this.checkUploadState(paramsVO, orgid);
        if (x.isSuccess()) {
            return x;
        }
        return new ReturnObject(false);
    }

    @Override
    public ReturnObject checkUploadState(GcActionParamsVO paramsVO, String orgid) {
        UploadState uploadSate = this.getUploadSate(paramsVO, orgid);
        if (uploadSate.equals((Object)UploadState.UPLOADED)) {
            return new ReturnObject(true, "\u5df2\u4e0a\u62a5");
        }
        if (uploadSate.equals((Object)UploadState.CONFIRMED)) {
            return new ReturnObject(true, "\u5df2\u786e\u8ba4");
        }
        if (uploadSate.equals((Object)UploadState.SUBMITED)) {
            return new ReturnObject(true, "\u5df2\u9001\u5ba1");
        }
        return new ReturnObject(false);
    }

    @Override
    public Boolean checkDiffCurrency(GcActionParamsVO paramsVO, GcOrgCacheVO org) {
        String beforeCurrency = StringUtils.toViewString((Object)org.getTypeFieldValue("CURRENCYID"));
        return beforeCurrency.equalsIgnoreCase(paramsVO.getCurrency());
    }

    @Override
    public ReturnObject checkCurrencyDataAndUploadState(GcActionParamsVO paramsVO, GcOrgCacheVO org) {
        ReturnObject returnObject1 = this.checkUploadState(paramsVO, org.getId());
        if (returnObject1.isSuccess()) {
            return returnObject1;
        }
        return new ReturnObject(false);
    }

    @Override
    public ReturnObject checkTarCurrencyData(GcActionParamsVO paramsVO, GcOrgCacheVO org) {
        List formDefines = new ArrayList();
        try {
            formDefines = this.iRunTimeViewController.queryAllFormDefinesByFormScheme(paramsVO.getSchemeId());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        FormDefine formDefine = (FormDefine)formDefines.stream().filter(define -> "\u8d44\u4ea7\u8d1f\u503a\u8868".equals(define.getTitle())).collect(Collectors.toList()).get(0);
        GcConversionContextEnv env = this.buildConversionEnv(paramsVO, org);
        List allFieldsInTable = new ArrayList();
        try {
            TableDefine tableDefine = this.iDataDefinitionRuntimeController.queryTableDefineByCode("ZCOX_" + formDefine.getFormCode());
            allFieldsInTable = this.iDataDefinitionRuntimeController.getAllFieldsInTable(tableDefine.getKey());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        String[] def = new String[]{"DATATIME", "MD_CURRENCY", "MD_GCORGTYPE", "MDCODE", "id", "version_1"};
        String queryFields = allFieldsInTable.stream().filter(fieldDefine -> !Arrays.asList(def).contains(fieldDefine.getCode().toLowerCase())).map(fieldDefine -> fieldDefine.getCode()).collect(Collectors.joining(","));
        String selectSql2 = "select " + queryFields + " from  ZCOX_" + formDefine.getFormCode() + " where " + "MDCODE" + " = '" + org.getId() + "'   and " + "DATATIME" + " = '" + env.getPeriodStr() + "'   and " + "MD_CURRENCY" + " = '" + env.getAfterCurrencyCode() + "'   and " + "MD_GCORGTYPE" + " = '" + env.getOrgTypeId() + "'";
        int length = queryFields.split(",").length;
        boolean empty = OneKeyMergeUtils.checkTableDataEmpty(selectSql2, length);
        return empty ? new ReturnObject(false) : new ReturnObject(true, "\u76ee\u6807\u5e01\u79cd\u5df2\u6709\u6570\u636e");
    }

    @Override
    public ReturnObject checkCurCurrencyData(GcActionParamsVO paramsVO, GcOrgCacheVO org) {
        List formDefines = new ArrayList();
        String curCurrency = StringUtils.toViewString((Object)org.getTypeFieldValue("CURRENCYID"));
        if (null == curCurrency) {
            return new ReturnObject(true, org.getTitle() + "\u6ca1\u6709\u8bbe\u7f6e\u672c\u4f4d\u5e01,\u8df3\u8fc7");
        }
        try {
            formDefines = this.iRunTimeViewController.queryAllFormDefinesByFormScheme(paramsVO.getSchemeId());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        FormDefine formDefine = (FormDefine)formDefines.stream().filter(define -> "\u8d44\u4ea7\u8d1f\u503a\u8868".equals(define.getTitle())).collect(Collectors.toList()).get(0);
        GcConversionContextEnv env = this.buildConversionEnv(paramsVO, org);
        List allFieldsInTable = new ArrayList();
        try {
            TableDefine tableDefine = this.iDataDefinitionRuntimeController.queryTableDefineByCode("ZCOX_" + formDefine.getFormCode());
            allFieldsInTable = this.iDataDefinitionRuntimeController.getAllFieldsInTable(tableDefine.getKey());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        String[] def = new String[]{"DATATIME", "MD_CURRENCY", "MD_GCORGTYPE", "MDCODE", "id", "version_1"};
        String queryFields = allFieldsInTable.stream().filter(fieldDefine -> !Arrays.asList(def).contains(fieldDefine.getCode().toLowerCase())).map(fieldDefine -> fieldDefine.getCode()).collect(Collectors.joining(","));
        String selectSql2 = "select " + queryFields + " from  ZCOX_" + formDefine.getFormCode() + " where " + "MDCODE" + " = '" + org.getId() + "'   and " + "DATATIME" + " = '" + env.getPeriodStr() + "'   and " + "MD_CURRENCY" + " = '" + curCurrency + "'   and " + "MD_GCORGTYPE" + " = '" + env.getOrgTypeId() + "'";
        int length = queryFields.split(",").length;
        boolean empty = OneKeyMergeUtils.checkTableDataEmpty(selectSql2, length);
        return empty ? new ReturnObject(false) : new ReturnObject(true, "\u672c\u4f4d\u5e01\u79cd\u5df2\u6709\u6570\u636e");
    }

    @Override
    public GcConversionContextEnv buildConversionEnv(GcActionParamsVO paramsVO, GcOrgCacheVO org) {
        GcBaseData beforeCurrency = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_CURRENCY", StringUtils.toViewString((Object)org.getTypeFieldValue("CURRENCYID")));
        GcBaseData afterCurrency = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_CURRENCY", paramsVO.getCurrency());
        if (beforeCurrency == null) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230\u6298\u7b97\u524d\u5e01\u522b\u4e3b\u4f53\u3002");
        }
        if (afterCurrency == null) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230\u6298\u7b97\u524d\u5e01\u522b\u4e3b\u4f53\u3002");
        }
        String periodStr = paramsVO.getPeriodStr();
        String orgTypeId = paramsVO.getOrgType();
        String beforeCurrencyId = beforeCurrency.getCode();
        String taskId = paramsVO.getTaskId();
        String schemeId = paramsVO.getSchemeId();
        String afterCurrencyId = afterCurrency.getCode();
        List<String> orgIds = Collections.singletonList(org.getId());
        String adjustCode = org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)paramsVO.getSelectAdjustCode()) ? paramsVO.getSelectAdjustCode() : "0";
        return new GcConversionContextEnv(UUIDUtils.newUUIDStr(), taskId, schemeId, orgIds, null, orgTypeId, orgTypeId, periodStr, beforeCurrencyId, afterCurrencyId, Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(true), adjustCode);
    }

    @Override
    public TaskLog getTaskLog(String taskLogId) {
        Assert.isNotNull((Object)taskLogId, (String)"\u83b7\u53d6\u5373\u65f6\u65e5\u5fd7\u7684ID\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        TaskLog taskProcess = this.onekeyProcessService.getTaskProcess(taskLogId);
        if (null == taskProcess) {
            return new TaskLog();
        }
        if (taskProcess.isFinish()) {
            this.onekeyProcessService.removeTaskLog(taskLogId);
        }
        return taskProcess;
    }

    @Override
    public TaskLog getTaskLogWithCode(String taskLogId, String taskCode) {
        List messages;
        Assert.isNotNull((Object)taskLogId, (String)"\u83b7\u53d6\u5373\u65f6\u65e5\u5fd7\u7684ID\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)taskCode, (String)"\u83b7\u53d6\u5373\u65f6\u65e5\u5fd7\u7684\u4efb\u52a1code\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        ProgressData progressData = this.progressService.queryProgressData(taskCode + "_" + taskLogId);
        TaskLog taskLog = new TaskLog();
        if (progressData == null) {
            return taskLog;
        }
        Message.ProgressResult result = (Message.ProgressResult)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)progressData.getResult()), Message.ProgressResult.class);
        BeanUtils.copyProperties(result, taskLog);
        if (taskCode.equals("finishCalc") && (messages = result.getMessages()).size() > 2) {
            Message userMsg = (Message)messages.remove(1);
            Message dateMsg = (Message)messages.remove(1);
            taskLog.setStartTime(dateMsg.getMessage().toString().replace("[INFO]", "").replace(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.startTime"), "").replace("[\u4fe1\u606f]", ""));
            taskLog.setUserName(userMsg.getMessage().toString().replace("[INFO]", "").replace(GcI18nUtil.getMessage((String)"gc.calculate.onekeymerge.calcDone.process.executor") + ":  ", "").replace("[\u4fe1\u606f]", ""));
        }
        return taskLog;
    }

    private List<String> filterFormDefines(GcActionParamsVO paramsVO, String orgId, List<String> formDefines) {
        return formDefines;
    }

    @Override
    public Map<String, DimensionValue> buildDimensionMap(String taskId, String currencyId, String periodStr, String orgType, String orgId, String selectAdjustCode) {
        GcOrgCacheVO currentUnit = OrgUtils.getCurrentUnit(orgType, periodStr, orgId);
        String orgTypeId = currentUnit.getOrgTypeId();
        Assert.isNotEmpty((String)orgTypeId, (String)(currentUnit.getTitle() + "\u5355\u4f4d\u7c7b\u578b\u4e3a\u7a7a"), (Object[])new Object[0]);
        return DimensionUtils.buildDimensionMap((String)taskId, (String)currencyId, (String)periodStr, (String)orgTypeId, (String)orgId, (String)selectAdjustCode);
    }

    @Transactional(rollbackFor={Exception.class})
    public void stopCalcAndFinishCalc(GcActionParamsVO paramsVO) {
        this.calcLogService.updateCalcLogEO(GcCalcLogOperateEnum.STATR_CALC, paramsVO.getTaskId(), paramsVO.getCurrency(), paramsVO.getPeriodStr(), paramsVO.getOrgType(), paramsVO.getOrgId(), "\u5408\u5e76\u4e2d\u5fc3\u5f3a\u5236\u7ed3\u675f\u4efb\u52a1", TaskStateEnum.ERROR, paramsVO.getSelectAdjustCode());
        this.calcLogService.updateCalcLogEO(GcCalcLogOperateEnum.COMPLETE_CALC, paramsVO.getTaskId(), paramsVO.getCurrency(), paramsVO.getPeriodStr(), paramsVO.getOrgType(), paramsVO.getOrgId(), "", TaskStateEnum.ERROR, paramsVO.getSelectAdjustCode());
    }

    @Override
    public boolean getFinishCalcState(GcActionParamsVO paramsVO, String orgId) {
        GcCalcLogEO calcLogInfoEO = this.calcLogDao.queryLatestLogs(GcCalcLogOperateEnum.COMPLETE_CALC, paramsVO.getTaskId(), paramsVO.getCurrency(), paramsVO.getPeriodStr(), paramsVO.getOrgType(), paramsVO.getOrgId(), paramsVO.getSelectAdjustCode());
        if (calcLogInfoEO != null) {
            return TaskStateEnum.SUCCESS.getCode().equalsIgnoreCase(calcLogInfoEO.getTaskState());
        }
        return false;
    }

    @Override
    public UploadState getUploadSate(GcActionParamsVO paramsVO, String orgId) {
        paramsVO.setOrgType(paramsVO.getOrgType());
        return new UploadStateTool().getUploadSate((Object)paramsVO, orgId);
    }

    @Override
    public void saveTaskResult(GcActionParamsVO vo, GcTaskResultEO eo, List<GcBaseTaskStateVO> result, String state) {
        eo.setGroupId(vo.getTaskLogId());
        eo.setAcctPeriod(vo.getAcctPeriod());
        eo.setAcctYear(vo.getAcctYear());
        eo.setAcctPeriod(vo.getAcctPeriod());
        eo.setOrgId(vo.getOrgId());
        eo.setOrgType(vo.getOrgType());
        eo.setSchemeId(vo.getSchemeId());
        eo.setTaskData(JsonUtils.writeValueAsString(result));
        eo.setTaskState(state);
        eo.setTaskId(vo.getTaskId());
        eo.setSchemeId(vo.getSchemeId());
        eo.setCurrency(vo.getCurrency());
        eo.setId(UUIDUtils.newUUIDStr());
        this.taskResultDao.save(eo);
    }

    @Override
    public GcTaskResultVO convertTaskResultEO2VO(GcTaskResultEO eo) {
        if (null == eo) {
            return new GcTaskResultVO();
        }
        GcTaskResultVO reportVO = new GcTaskResultVO();
        BeanUtils.copyProperties((Object)eo, reportVO);
        return reportVO;
    }

    @Override
    public ReturnObject checkFinishCalcState(GcActionParamsVO paramsVO) {
        ReturnObject ret = new ReturnObject(true);
        GcCalcLogEO exam = new GcCalcLogEO();
        exam.setTaskState(TaskStateEnum.EXECUTING.getCode());
        try {
            this.calcLogService.unLockTimeOutLogOperate(GcCalcLogOperateEnum.COMPLETE_CALC, paramsVO.getTaskId(), paramsVO.getCurrency(), paramsVO.getPeriodStr(), paramsVO.getOrgType(), paramsVO.getOrgId(), Long.valueOf(1800000L), paramsVO.getSelectAdjustCode());
        }
        catch (Exception e) {
            GcCalcLogEO gcCalcLogEO = this.calcLogService.queryLatestCalcLogEO(GcCalcLogOperateEnum.COMPLETE_CALC, paramsVO.getTaskId(), paramsVO.getCurrency(), paramsVO.getPeriodStr(), paramsVO.getOrgType(), paramsVO.getOrgId(), paramsVO.getSelectAdjustCode());
            logger.error(e.getMessage(), e);
            if (gcCalcLogEO != null) {
                String username = gcCalcLogEO.getUsername();
                username = "admin".equals(username) ? "\u7cfb\u7edf\u7ba1\u7406\u5458" : username;
                ret.setSuccess(false);
                ret.setErrorMessage(username + "\u6b63\u5728\u6267\u884c\u5f53\u524d\u5355\u4f4d\u7684\u5b8c\u6210\u5408\u5e76\uff0c\u8bf7\u7a0d\u540e");
            }
            return ret;
        }
        return ret;
    }

    private boolean filterExpire(GcCalcLogEO logInfoEO) {
        Long start = logInfoEO.getBegintime();
        Long end = System.currentTimeMillis();
        long maxExpireTime = 1800000L;
        return end - start < maxExpireTime;
    }
}

