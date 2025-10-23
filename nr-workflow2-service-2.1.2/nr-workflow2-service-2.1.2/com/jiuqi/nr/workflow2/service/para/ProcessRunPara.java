/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.util.StringUtils
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.service.para;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.service.common.IProcessCustomVariable;
import com.jiuqi.nr.workflow2.service.common.ProcessCustomVariable;
import com.jiuqi.nr.workflow2.service.exception.OperateStateCode;
import com.jiuqi.nr.workflow2.service.helper.IProcessRuntimeParamHelper;
import com.jiuqi.nr.workflow2.service.helper.ProcessRuntimeParamHelper;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessJsonDataDeserializer;
import com.jiuqi.nr.workflow2.service.para.ProcessJsonDataSerializer;
import com.jiuqi.util.StringUtils;
import org.json.JSONObject;

public class ProcessRunPara
implements IProcessRunPara {
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private String period;
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private String taskKey;
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private JSONObject envVariables;

    @Override
    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Override
    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    @JsonSerialize(using=ProcessJsonDataSerializer.class)
    public JSONObject getEnvVariables() {
        return this.envVariables;
    }

    @JsonDeserialize(using=ProcessJsonDataDeserializer.class)
    public void setEnvVariables(JSONObject envVariables) {
        this.envVariables = envVariables;
    }

    @Override
    @JsonIgnore
    public IProcessCustomVariable getCustomVariable() {
        return new ProcessCustomVariable(this.envVariables != null ? this.envVariables : new JSONObject());
    }

    @JsonIgnore
    public OperateStateCode checkPara() {
        IProcessRuntimeParamHelper runtimeParamHelper = (IProcessRuntimeParamHelper)SpringBeanUtils.getBean(ProcessRuntimeParamHelper.class);
        TaskDefine taskDefine = runtimeParamHelper.getTaskDefine(this.taskKey);
        if (taskDefine == null) {
            return OperateStateCode.ERR_TASK_NOT_FOUND;
        }
        WorkflowSettingsService settingService = (WorkflowSettingsService)SpringBeanUtils.getBean(WorkflowSettingsService.class);
        WorkflowSettingsDO flowSettings = settingService.queryWorkflowSettings(this.taskKey);
        if (!flowSettings.isWorkflowEnable()) {
            return OperateStateCode.ERR_PROCESS_NOT_ENABLED;
        }
        if (StringUtils.isEmpty((String)this.period)) {
            return OperateStateCode.ERR_PERIOD_DIM_VALUE_CAN_NOT_BE_NULL;
        }
        FormSchemeDefine formScheme = runtimeParamHelper.getFormScheme(this.taskKey, this.period);
        if (formScheme == null) {
            return OperateStateCode.ERR_FORM_SCHEME_NOT_FOUND;
        }
        return OperateStateCode.OPT_SUCCESS;
    }
}

