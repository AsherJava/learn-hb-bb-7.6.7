/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam
 *  com.jiuqi.nr.dataentry.bean.ExecuteTaskParam
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.workflow2.engine.core.event.IActionEventExecutor
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsServiceImpl
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils
 *  com.jiuqi.nr.workflow2.service.enumeration.IProcessActionExecuteAttrKeys
 *  com.jiuqi.nr.workflow2.service.enumeration.IProcessEventExecuteAttrKeys
 *  com.jiuqi.nr.workflow2.service.helper.IProcessEntityQueryHelper
 *  com.jiuqi.nr.workflow2.service.helper.IProcessRuntimeParamHelper
 *  com.jiuqi.nr.workflow2.service.helper.ProcessEntityQueryHelper
 *  com.jiuqi.nr.workflow2.service.helper.ProcessRuntimeParamHelper
 *  com.jiuqi.nr.workflow2.service.para.IProcessRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.events.executor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.ExecuteTaskParam;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.workflow2.engine.core.event.IActionEventExecutor;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsServiceImpl;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils;
import com.jiuqi.nr.workflow2.service.enumeration.IProcessActionExecuteAttrKeys;
import com.jiuqi.nr.workflow2.service.enumeration.IProcessEventExecuteAttrKeys;
import com.jiuqi.nr.workflow2.service.helper.IProcessEntityQueryHelper;
import com.jiuqi.nr.workflow2.service.helper.IProcessRuntimeParamHelper;
import com.jiuqi.nr.workflow2.service.helper.ProcessEntityQueryHelper;
import com.jiuqi.nr.workflow2.service.helper.ProcessRuntimeParamHelper;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv;
import java.util.Map;
import org.json.JSONObject;

public abstract class AbstractActionEventExecutor
implements IActionEventExecutor {
    protected final JSONObject eventJsonConfig;
    protected IProcessEntityQueryHelper entityQueryHelper;
    protected IProcessRuntimeParamHelper runtimeParamHelper;
    protected WorkflowSettingsService workflowSettingsService;

    public AbstractActionEventExecutor(JSONObject eventJsonConfig) {
        this.eventJsonConfig = eventJsonConfig;
        this.entityQueryHelper = (IProcessEntityQueryHelper)SpringBeanUtils.getBean(ProcessEntityQueryHelper.class);
        this.runtimeParamHelper = (IProcessRuntimeParamHelper)SpringBeanUtils.getBean(ProcessRuntimeParamHelper.class);
        this.workflowSettingsService = (WorkflowSettingsService)SpringBeanUtils.getBean(WorkflowSettingsServiceImpl.class);
    }

    protected Map<String, DimensionValue> getDimensionValueMap(IActionArgs actionArgs) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return (Map)mapper.readValue(actionArgs.getString(IProcessEventExecuteAttrKeys.ENV_DIMENSION_VALUE_MAP.attrKey), (TypeReference)new TypeReference<Map<String, DimensionValue>>(){});
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected ProcessExecuteEnv getEnvParam(IActionArgs actionArgs) {
        JSONObject actionExecuteParam = JavaBeanUtils.toJSONObject((String)actionArgs.getString(IProcessActionExecuteAttrKeys.process_action_execute.attrKey));
        return (ProcessExecuteEnv)JavaBeanUtils.toJavaBean((JSONObject)actionExecuteParam, ProcessExecuteEnv.class);
    }

    protected ExecuteTaskParam getExecuteTaskParam(IActionArgs actionArgs) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return (ExecuteTaskParam)mapper.readValue(actionArgs.getString(IProcessEventExecuteAttrKeys.EXECUTE_TASK_PARAM.attrKey), (TypeReference)new TypeReference<ExecuteTaskParam>(){});
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected BatchExecuteTaskParam getBatchExecuteTaskParam(IActionArgs actionArgs) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return (BatchExecuteTaskParam)mapper.readValue(actionArgs.getString(IProcessEventExecuteAttrKeys.BATCH_EXECUTE_TASK_PARAM.attrKey), (TypeReference)new TypeReference<BatchExecuteTaskParam>(){});
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected IEntityTable getEntityTable(IProcessRunPara runEnvPara, IEntityDefine entityDefine) throws Exception {
        return this.getEntityTable(runEnvPara, entityDefine, AuthorityType.Read);
    }

    protected IEntityTable getEntityTable(IProcessRunPara runEnvPara, IEntityDefine entityDefine, AuthorityType authorityType) throws Exception {
        String taskKey = runEnvPara.getTaskKey();
        String period = runEnvPara.getPeriod();
        IEntityQuery entityQuery = this.getEntityQuery(taskKey, period, authorityType, entityDefine);
        ExecutorContext executorContext = this.entityQueryHelper.makeExecuteContext(taskKey, period);
        return this.entityQueryHelper.getIEntityTable(entityQuery, executorContext);
    }

    protected IEntityTable getEntityTableFullBuilder(IProcessRunPara runEnvPara, IEntityDefine entityDefine, AuthorityType authorityType) throws Exception {
        String taskKey = runEnvPara.getTaskKey();
        String period = runEnvPara.getPeriod();
        IEntityQuery entityQuery = this.getEntityQuery(taskKey, period, authorityType, entityDefine);
        ExecutorContext executorContext = this.entityQueryHelper.makeExecuteContext(taskKey, period);
        return entityQuery.executeFullBuild((IContext)executorContext);
    }

    private IEntityQuery getEntityQuery(String taskKey, String period, AuthorityType authorityType, IEntityDefine entityDefine) {
        IEntityQuery entityQuery = this.entityQueryHelper.makeIEntityQuery(taskKey, period);
        entityQuery.setAuthorityOperations(authorityType);
        EntityViewDefine entityViewDefine = this.runtimeParamHelper.buildEntityView(taskKey, period, entityDefine.getId());
        entityQuery.setEntityView(entityViewDefine);
        return entityQuery;
    }

    protected IEntityRow findEntityRow(IProcessRunPara runEnvPara, IBusinessKey businessKey) throws Exception {
        String unitId = businessKey.getBusinessObject().getDimensions().getDWDimensionValue().getValue().toString();
        IEntityDefine entityDefine = this.runtimeParamHelper.getProcessEntityDefinition(businessKey.getTask());
        IPeriodEntity periodEntityDefine = this.runtimeParamHelper.getPeriodEntityDefine(businessKey.getTask());
        IEntityTable entityTable = this.getEntityTable(runEnvPara, entityDefine);
        return entityTable.findByEntityKey(unitId);
    }

    protected boolean isLeafNode(IProcessRunPara runEnvPara, IBusinessKey businessKey) throws Exception {
        IEntityRow entityRow = this.findEntityRow(runEnvPara, businessKey);
        return entityRow != null && entityRow.isLeaf();
    }

    protected Integer getBusinessObjectLevel(IBusinessObject businessObject, IEntityTable entityTable) {
        String unitId = businessObject.getDimensions().getDWDimensionValue().getValue().toString();
        IEntityRow entityRow = entityTable.findByEntityKey(unitId);
        if (entityRow != null) {
            return entityRow.getParentsEntityKeyDataPath().length;
        }
        return null;
    }
}

