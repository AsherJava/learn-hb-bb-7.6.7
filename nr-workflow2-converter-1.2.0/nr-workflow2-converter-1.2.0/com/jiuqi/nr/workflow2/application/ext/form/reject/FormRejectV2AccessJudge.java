/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.bpm.de.dataflow.access.WorklfowAccessService
 *  com.jiuqi.nr.data.access.common.WorkflowState
 *  com.jiuqi.nr.data.access.exception.AccessException
 *  com.jiuqi.nr.data.access.param.AccessCode
 *  com.jiuqi.nr.data.access.param.FormBatchAccessCache
 *  com.jiuqi.nr.data.access.param.IAccessMessage
 *  com.jiuqi.nr.data.access.param.IBatchAccess
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus$DataAccessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.DimensionObject
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils
 *  com.jiuqi.nr.workflow2.form.reject.enumeration.FormRejectStatus
 *  com.jiuqi.nr.workflow2.form.reject.ext.service.FormRejectExecuteParam
 *  com.jiuqi.nr.workflow2.service.IProcessQueryService
 *  com.jiuqi.nr.workflow2.service.enumeration.IProcessFormRejectAttrKeys
 *  com.jiuqi.nr.workflow2.service.para.IProcessRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessOneRunPara
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.application.ext.form.reject;

import com.jiuqi.nr.bpm.de.dataflow.access.WorklfowAccessService;
import com.jiuqi.nr.data.access.common.WorkflowState;
import com.jiuqi.nr.data.access.exception.AccessException;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.FormBatchAccessCache;
import com.jiuqi.nr.data.access.param.IAccessMessage;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.workflow2.converter.workflow.access.WorkflowUnitAccessConverter;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.DimensionObject;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils;
import com.jiuqi.nr.workflow2.form.reject.enumeration.FormRejectStatus;
import com.jiuqi.nr.workflow2.form.reject.ext.service.FormRejectExecuteParam;
import com.jiuqi.nr.workflow2.service.IProcessQueryService;
import com.jiuqi.nr.workflow2.service.enumeration.IProcessFormRejectAttrKeys;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessOneRunPara;
import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

public class FormRejectV2AccessJudge
extends WorklfowAccessService {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private IProcessQueryService processQueryService;
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;

    public String name() {
        return "workflow2-form-reject-access-judge";
    }

    public IAccessMessage getAccessMessage() {
        return code -> {
            if (FormRejectStatus.locked.value.equalsIgnoreCase(code)) {
                return "\u5f53\u524d\u62a5\u8868\u65e0\u9700\u66f4\u6b63";
            }
            return (String)this.noAccessReason.apply(code);
        };
    }

    public boolean isEnable(String taskKey, String formSchemeKey) {
        Assert.notNull((Object)formSchemeKey, "formSchemeKey is must not be null!");
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        boolean workflowEnable = this.workflowSettingsService.queryTaskWorkflowEnable(formSchemeDefine.getTaskKey());
        if (!workflowEnable) {
            return false;
        }
        boolean taskAndEngineVersion10 = this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(taskKey);
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(formSchemeDefine.getTaskKey());
        return !taskAndEngineVersion10 && workflowObjectType.equals((Object)WorkflowObjectType.MD_WITH_SFR);
    }

    public AccessCode visible(String formSchemeKey, DimensionCombination masterKey, String formKey) throws AccessException {
        Assert.notNull((Object)formSchemeKey, "formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, "masterKey is must not be null!");
        return new AccessCode(this.name());
    }

    public AccessCode readable(String formSchemeKey, DimensionCombination masterKey, String formKey) throws AccessException {
        Assert.notNull((Object)formSchemeKey, "formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, "masterKey is must not be null!");
        return new AccessCode(this.name());
    }

    public AccessCode writeable(String formSchemeKey, DimensionCombination masterKey, String formKey) throws AccessException {
        Assert.notNull((Object)formSchemeKey, "formSchemeKey is must not be null!");
        Assert.notNull((Object)masterKey, "masterKey is must not be null!");
        String period = masterKey.getPeriodDimensionValue().getValue().toString();
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        ProcessOneRunPara oneRunPara = new ProcessOneRunPara();
        oneRunPara.setTaskKey(formSchemeDefine.getTaskKey());
        oneRunPara.setPeriod(period);
        JSONObject envVariable = new JSONObject();
        envVariable.put(IProcessFormRejectAttrKeys.process_form_reject.attrKey, (Object)JavaBeanUtils.toJSONStr((Object)new FormRejectExecuteParam(formKey)));
        envVariable.put(IProcessFormRejectAttrKeys.use_form_reject_query.attrKey, true);
        oneRunPara.setEnvVariables(envVariable);
        BusinessKey businessKey = new BusinessKey(formSchemeDefine.getTaskKey(), (IBusinessObject)new DimensionObject(masterKey));
        IProcessStatus processStatus = this.processQueryService.queryInstanceState((IProcessRunPara)oneRunPara, (IBusinessKey)businessKey);
        if (processStatus != null && IProcessStatus.DataAccessStatus.READONLY == processStatus.getDataAccessStatus()) {
            if (FormRejectStatus.locked.value.equalsIgnoreCase(processStatus.getCode())) {
                return new AccessCode(this.name(), FormRejectStatus.locked.value);
            }
            WorkflowState workflowState = WorkflowUnitAccessConverter.transferWorkflowState(processStatus.getCode());
            String accCode = (String)this.accessCodeCompute.apply(String.valueOf(workflowState.getValue()));
            return new AccessCode(this.name(), accCode);
        }
        return new AccessCode(this.name());
    }

    public IBatchAccess getBatchVisible(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) throws AccessException {
        return new FormBatchAccessCache(this.name(), formSchemeKey);
    }

    public IBatchAccess getBatchReadable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) throws AccessException {
        return new FormBatchAccessCache(this.name(), formSchemeKey);
    }

    public IBatchAccess getBatchWriteable(String formSchemeKey, DimensionCollection masterKeys, List<String> formKeys) throws AccessException {
        return new FormBatchAccessCache(this.name(), formSchemeKey);
    }
}

