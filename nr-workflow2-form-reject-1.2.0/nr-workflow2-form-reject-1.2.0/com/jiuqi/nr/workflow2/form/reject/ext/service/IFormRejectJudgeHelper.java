/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.service.IProcessQueryService
 *  com.jiuqi.nr.workflow2.service.common.IProcessCustomVariable
 *  com.jiuqi.nr.workflow2.service.enumeration.IProcessFormRejectAttrKeys
 *  com.jiuqi.nr.workflow2.service.para.IProcessExecutePara
 *  com.jiuqi.nr.workflow2.service.para.IProcessRunPara
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.workflow2.form.reject.ext.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.form.reject.ext.service.FormRejectExecuteParam;
import com.jiuqi.nr.workflow2.form.reject.service.IFormRejectQueryService;
import com.jiuqi.nr.workflow2.service.IProcessQueryService;
import com.jiuqi.nr.workflow2.service.common.IProcessCustomVariable;
import com.jiuqi.nr.workflow2.service.enumeration.IProcessFormRejectAttrKeys;
import com.jiuqi.nr.workflow2.service.para.IProcessExecutePara;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;

public class IFormRejectJudgeHelper {
    @Resource(name="com.jiuqi.nr.workflow2.service.impl.ProcessQueryService")
    private IProcessQueryService processQueryService;
    @Autowired
    protected WorkflowSettingsService settingService;
    @Autowired
    private IFormRejectQueryService formRejectQueryService;

    public static FormRejectExecuteParam getFormRejectExecuteParam(IProcessCustomVariable customVariable) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return (FormRejectExecuteParam)mapper.readValue(customVariable.getValue().getString(IProcessFormRejectAttrKeys.process_form_reject.attrKey), (TypeReference)new TypeReference<FormRejectExecuteParam>(){});
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isInstanceAtAuditAndUpload(IProcessRunPara runEnvPara, IBusinessKey businessKey) {
        IProcessInstance processInstance = this.processQueryService.queryInstances(runEnvPara, businessKey);
        if (processInstance != null && this.isFormRejectMode(runEnvPara)) {
            IProcessStatus processStatus = this.processQueryService.queryInstanceState(runEnvPara, businessKey);
            return "tsk_audit".equals(processInstance.getCurrentUserTask()) && "reported".equals(processStatus.getCode());
        }
        return false;
    }

    public boolean isInstanceAtReportAndReject(IProcessRunPara runEnvPara, IBusinessKey businessKey) {
        IProcessInstance processInstance = this.processQueryService.queryInstances(runEnvPara, businessKey);
        if (processInstance != null && this.isFormRejectMode(runEnvPara)) {
            IProcessStatus processStatus = this.processQueryService.queryInstanceState(runEnvPara, businessKey);
            return "tsk_upload".equals(processInstance.getCurrentUserTask()) && "rejected".equals(processStatus.getCode());
        }
        return false;
    }

    public boolean useFormRejectQuery(IProcessRunPara runEnvPara) {
        return runEnvPara.getCustomVariable().getValue().has(IProcessFormRejectAttrKeys.use_form_reject_query.attrKey) && runEnvPara.getCustomVariable().getBoolean(IProcessFormRejectAttrKeys.use_form_reject_query.attrKey);
    }

    public boolean isFormRejectMode(IProcessRunPara runEnvPara) {
        WorkflowObjectType workflowObjectType = this.settingService.queryTaskWorkflowObjectType(runEnvPara.getTaskKey());
        return WorkflowObjectType.MD_WITH_SFR == workflowObjectType;
    }

    public boolean isFormRejectAction(IProcessExecutePara executePara) {
        IProcessCustomVariable actionArgs = executePara.getCustomVariable();
        return "act_reject".equals(executePara.getActionCode()) && actionArgs != null && actionArgs.getBoolean(IProcessFormRejectAttrKeys.is_form_reject_button.attrKey);
    }

    public boolean canShowFormRejectIcon(IProcessRunPara runEnvPara, IBusinessKey businessKey) {
        DimensionCombination dimensions = businessKey.getBusinessObject().getDimensions();
        return !this.formRejectQueryService.isRejectAllFormsInUnit(runEnvPara.getTaskKey(), runEnvPara.getPeriod(), dimensions) && this.isInstanceAtReportAndReject(runEnvPara, businessKey);
    }
}

