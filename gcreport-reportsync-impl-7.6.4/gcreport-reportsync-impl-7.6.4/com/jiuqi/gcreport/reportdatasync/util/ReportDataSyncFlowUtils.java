/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.ProcessEngineProvider
 *  com.jiuqi.nr.bpm.businesskey.BusinessKey
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg
 *  com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ExecuteParam
 *  com.jiuqi.nr.bpm.de.dataflow.constont.UploadStateEnum
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator
 *  com.jiuqi.nr.bpm.impl.process.consts.ProcessType
 *  com.jiuqi.nr.bpm.service.RunTimeService
 *  com.jiuqi.nr.bpm.setting.utils.SettingUtil
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.gcreport.reportdatasync.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.ProcessEngineProvider;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg;
import com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.ExecuteParam;
import com.jiuqi.nr.bpm.de.dataflow.constont.UploadStateEnum;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.impl.process.consts.ProcessType;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.nr.bpm.setting.utils.SettingUtil;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ReportDataSyncFlowUtils {
    public static boolean executeDataFlow(String taskCode, String orgCode, String periodStr, UploadStateEnum uploadStateEnum, String comment, String selectAdjust) {
        IDataentryFlowService dataFlowService = (IDataentryFlowService)SpringContextUtils.getBean(IDataentryFlowService.class);
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        try {
            TaskDefine taskDefine = runTimeViewController.queryTaskDefineByCode(taskCode);
            SchemePeriodLinkDefine schemePeriodLinkDefine = runTimeViewController.querySchemePeriodLinkByPeriodAndTask(periodStr, taskDefine.getKey());
            if (schemePeriodLinkDefine == null) {
                return false;
            }
            FormSchemeDefine formSchemeDefine = runTimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
            if (formSchemeDefine == null) {
                return false;
            }
            if (UploadStateEnum.ACTION_REJECT == uploadStateEnum) {
                ActionStateBean actionState = ReportDataSyncFlowUtils.queryUnitState(formSchemeDefine.getKey(), orgCode, periodStr, selectAdjust);
                if (!(UploadState.UPLOADED.name().equals(actionState.getCode()) || UploadState.SUBMITED.name().equals(actionState.getCode()) || UploadState.CONFIRMED.name().equals(actionState.getCode()))) {
                    throw new BusinessRuntimeException("\u4e0a\u62a5\u4efb\u52a1[" + taskDefine.getTitle() + "]-\u5355\u4f4d[" + orgCode + "]-\u65f6\u671f[" + periodStr + "]\u672a\u4e0a\u62a5\uff0c\u4e0d\u5141\u8bb8\u9000\u56de\u3002");
                }
            }
            ReportDataSyncFlowUtils.batchStartProcessByBusinessKey(orgCode, periodStr, formSchemeDefine, uploadStateEnum, selectAdjust);
            ExecuteParam executeParam = ReportDataSyncFlowUtils.getExecuteParam(orgCode, periodStr, uploadStateEnum, comment, selectAdjust);
            executeParam.setFormSchemeKey(formSchemeDefine.getKey());
            CompleteMsg completeMsg = dataFlowService.executeTask(executeParam);
            if (!completeMsg.isSucceed()) {
                throw new BusinessRuntimeException(completeMsg.getMsg());
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(uploadStateEnum.getName() + "\u5931\u8d25:" + e.getMessage(), (Throwable)e);
        }
        return Boolean.TRUE;
    }

    private static void batchStartProcessByBusinessKey(String orgCode, String periodStr, FormSchemeDefine formSchemeDefine, UploadStateEnum uploadStateEnum, String selectAdjust) {
        if (UploadStateEnum.ACTION_UPLOAD != uploadStateEnum) {
            return;
        }
        SettingUtil untilsMethod = (SettingUtil)SpringContextUtils.getBean(SettingUtil.class);
        Map alreadyStartFlow = untilsMethod.queryAlreadyStart(formSchemeDefine.getKey(), new HashSet<String>(Collections.singletonList(orgCode)), null, periodStr, selectAdjust);
        BusinessGenerator businessGenerator = (BusinessGenerator)SpringContextUtils.getBean(BusinessGenerator.class);
        if (!alreadyStartFlow.containsKey(orgCode)) {
            List businessKey = businessGenerator.buildBusinessKey(formSchemeDefine.getKey(), orgCode, periodStr, untilsMethod.getDefaultFormId(formSchemeDefine.getKey()), selectAdjust, null);
            String workflowKey = untilsMethod.queryFlowDefineKey(formSchemeDefine.getKey());
            HashMap<BusinessKey, String> startParam = new HashMap<BusinessKey, String>();
            for (BusinessKey businessKey2 : businessKey) {
                startParam.put(businessKey2, workflowKey);
            }
            ProcessEngineProvider processEngineProvider = (ProcessEngineProvider)SpringContextUtils.getBean(ProcessEngineProvider.class);
            Optional processEngine = processEngineProvider.getProcessEngine(ProcessType.DEFAULT);
            RunTimeService runTimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
            runTimeService.batchStartProcessByBusinessKey(startParam, new HashMap());
        }
    }

    private static ExecuteParam getExecuteParam(String orgCode, String periodStr, UploadStateEnum uploadStateEnum, String comment, String selectAdjust) {
        ExecuteParam executeParam = new ExecuteParam();
        executeParam.setActionId(uploadStateEnum.getCode());
        executeParam.setComment(comment);
        switch (uploadStateEnum) {
            case ACTION_UPLOAD: {
                executeParam.setTaskId("tsk_upload");
                break;
            }
            case ACTION_REJECT: {
                executeParam.setTaskId("tsk_audit");
                break;
            }
            default: {
                throw new BusinessRuntimeException("");
            }
        }
        ContextUser user = NpContextHolder.getContext().getUser();
        executeParam.setUserId(user.getId());
        DimensionValueSet dimensionValueSet = ReportDataSyncFlowUtils.getDimensionValueSet(orgCode, periodStr, selectAdjust);
        executeParam.setDimSet(dimensionValueSet);
        executeParam.setForceUpload(Boolean.FALSE.booleanValue());
        executeParam.setSendEmaill(Boolean.FALSE.booleanValue());
        executeParam.setFormKey("");
        executeParam.setGroupKey("");
        return executeParam;
    }

    private static DimensionValueSet getDimensionValueSet(String orgId, String periodStr, String selectAdjust) {
        DimensionValueSet dimSet = new DimensionValueSet();
        dimSet.setValue("MD_ORG", (Object)orgId);
        dimSet.setValue("DATATIME", (Object)periodStr);
        String adjustCode = "0";
        if (selectAdjust != null) {
            adjustCode = selectAdjust;
        }
        dimSet.setValue("ADJUST", (Object)adjustCode);
        return dimSet;
    }

    public static ActionStateBean queryUnitState(String formSchemeKey, String orgId, String periodStr, String adjustCode) {
        IDataentryFlowService dataFlowService = (IDataentryFlowService)SpringContextUtils.getBean(IDataentryFlowService.class);
        DataEntryParam dataEntryParam = new DataEntryParam();
        dataEntryParam.setFormSchemeKey(formSchemeKey);
        dataEntryParam.setDim(ReportDataSyncFlowUtils.getDimensionValueSet(orgId, periodStr, adjustCode));
        ActionStateBean actionState = dataFlowService.queryUnitState(dataEntryParam);
        return actionState;
    }
}

