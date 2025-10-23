/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormGroupObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.dflt.utils.StringUtils;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BusinessKeyUtil {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    private PeriodEngineService periodEngineService;

    public RuntimeBusinessKey buildRuntimeBusinessKey(IBusinessKey businessKey) {
        return this.verifyBusinessKey(businessKey);
    }

    public RuntimeBusinessKey verifyBusinessKey(IBusinessKey businessKey) {
        if (businessKey == null) {
            throw new IllegalArgumentException("'businesskey' must not be null.");
        }
        String taskKey = businessKey.getTask();
        if (StringUtils.isEmpty(taskKey)) {
            throw new IllegalArgumentException("'businesskey#getTask' must not be null.");
        }
        TaskDefine task = this.runTimeViewController.getTask(taskKey);
        if (task == null) {
            throw new ProcessRuntimeException("jiuqi.nr.default", String.format("\u4efb\u52a1 %s \u4e0d\u5b58\u5728\u3002", taskKey));
        }
        IBusinessObject businessObject = businessKey.getBusinessObject();
        if (businessObject == null) {
            throw new IllegalArgumentException("'businesskey#getBusinessObject' must not be null.");
        }
        if (businessObject.getDimensions() == null) {
            throw new IllegalArgumentException("'businesskey#getBusinessObject#getDimensions' must not be null.");
        }
        FixedDimensionValue mdDimension = businessObject.getDimensions().getDWDimensionValue();
        if (mdDimension == null || mdDimension.getValue() == null) {
            throw new IllegalArgumentException("dw dimension in businesskey must not be null.");
        }
        FixedDimensionValue periodDimension = businessObject.getDimensions().getPeriodDimensionValue();
        if (periodDimension == null || periodDimension.getValue() == null) {
            throw new IllegalArgumentException("period dimension in businesskey must not be null.");
        }
        String period = (String)periodDimension.getValue();
        SchemePeriodLinkDefine schemePeriodLink = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(period, taskKey);
        if (schemePeriodLink == null) {
            throw new ProcessRuntimeException("jiuqi.nr.default", String.format("\u4efb\u52a1  %s \u5728\u65f6\u671f{1}\u4e0b\u65e0\u53ef\u7528\u7684\u62a5\u8868\u65b9\u6848\u3002", taskKey, period));
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(schemePeriodLink.getSchemeKey());
        if (formScheme == null) {
            throw new ProcessRuntimeException("jiuqi.nr.default", String.format("\u4efb\u52a1  %s \u5728\u65f6\u671f{1}\u4e0b\u65e0\u53ef\u7528\u7684\u62a5\u8868\u65b9\u6848\u3002", taskKey, period));
        }
        WorkflowSettingsDO workflowSettings = this.workflowSettingsService.queryWorkflowSettings(businessKey.getTask());
        if (workflowSettings == null) {
            throw new ProcessRuntimeException("jiuqi.nr.default", String.format("\u4efb\u52a1  %s \u4e0b\u65e0\u53ef\u7528\u7684\u586b\u62a5\u8ba1\u5212\u8bbe\u7f6e\u3002", businessKey.getTask()));
        }
        switch (workflowSettings.getWorkflowObjectType()) {
            case FORM: {
                if (!(businessObject instanceof IFormObject)) {
                    throw new IllegalArgumentException("BusinessObject must be instance of class IFormObject.");
                }
                if (!StringUtils.isEmpty(((IFormObject)businessObject).getFormKey())) break;
                throw new IllegalArgumentException("FormObject#getFormKey must not be null.");
            }
            case FORM_GROUP: {
                if (!(businessObject instanceof IFormGroupObject)) {
                    throw new IllegalArgumentException("BusinessObject must be instance of class IFormGroupObject.");
                }
                if (!StringUtils.isEmpty(((IFormGroupObject)businessObject).getFormGroupKey())) break;
                throw new IllegalArgumentException("FormGroupObject#getFormGroupKey must not be null.");
            }
        }
        return new RuntimeBusinessKey(businessKey, task, formScheme, workflowSettings);
    }

    public RuntimeBusinessKeyCollection verifyBusinessKeyCollection(IBusinessKeyCollection businessKeys) {
        if (businessKeys == null) {
            throw new IllegalArgumentException("'businessKeys' must not be null.");
        }
        String taskKey = businessKeys.getTask();
        if (StringUtils.isEmpty(taskKey)) {
            throw new IllegalArgumentException("'businessKeys#getTask' must not be null.");
        }
        TaskDefine task = this.runTimeViewController.getTask(taskKey);
        if (task == null) {
            throw new ProcessRuntimeException("jiuqi.nr.default", String.format("\u4efb\u52a1  %s \u4e0d\u5b58\u5728\u3002", taskKey));
        }
        if (businessKeys.getBusinessObjects() == null || businessKeys.getBusinessObjects().size() == 0) {
            throw new IllegalArgumentException("'businessKeys#getBusinessObjects' must not be null.");
        }
        WorkflowSettingsDO workflowSettings = this.workflowSettingsService.queryWorkflowSettings(businessKeys.getTask());
        if (workflowSettings == null) {
            throw new ProcessRuntimeException("jiuqi.nr.default", String.format("\u4efb\u52a1  %s \u4e0b\u65e0\u53ef\u7528\u7684\u586b\u62a5\u8ba1\u5212\u8bbe\u7f6e\u3002", businessKeys.getTask()));
        }
        HashMap<String, FormSchemeDefine> period2FormScheme = new HashMap<String, FormSchemeDefine>(1);
        for (IBusinessObject businessObject : businessKeys.getBusinessObjects()) {
            if (businessObject == null) continue;
            if (businessObject.getDimensions() == null) {
                throw new IllegalArgumentException("'businessObject#getDimensions' must not be null.");
            }
            FixedDimensionValue mdDimension = businessObject.getDimensions().getDWDimensionValue();
            if (mdDimension == null || mdDimension.getValue() == null) {
                throw new IllegalArgumentException("dw dimension in businesskey must not be null.");
            }
            FixedDimensionValue periodDimension = businessObject.getDimensions().getPeriodDimensionValue();
            if (periodDimension == null || periodDimension.getValue() == null) {
                throw new IllegalArgumentException("period dimension in businesskey must not be null.");
            }
            String period = (String)periodDimension.getValue();
            FormSchemeDefine formScheme = (FormSchemeDefine)period2FormScheme.get(period);
            if (formScheme == null) {
                SchemePeriodLinkDefine schemePeriodLink = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(period, taskKey);
                if (schemePeriodLink == null) {
                    throw new ProcessRuntimeException("jiuqi.nr.default", String.format("\u4efb\u52a1  %s \u5728\u65f6\u671f{1}\u4e0b\u65e0\u53ef\u7528\u7684\u62a5\u8868\u65b9\u6848\u3002", taskKey, period));
                }
                formScheme = this.runTimeViewController.getFormScheme(schemePeriodLink.getSchemeKey());
                if (formScheme == null) {
                    throw new ProcessRuntimeException("jiuqi.nr.default", String.format("\u4efb\u52a1  %s \u5728\u65f6\u671f{1}\u4e0b\u65e0\u53ef\u7528\u7684\u62a5\u8868\u65b9\u6848\u3002", taskKey, period));
                }
                period2FormScheme.put(period, formScheme);
            }
            switch (workflowSettings.getWorkflowObjectType()) {
                case FORM: {
                    if (!(businessObject instanceof IFormObject)) {
                        throw new IllegalArgumentException("BusinessObject must be instance of class IFormObject.");
                    }
                    if (!StringUtils.isEmpty(((IFormObject)businessObject).getFormKey())) break;
                    throw new IllegalArgumentException("FormObject#getFormKey must not be null.");
                }
                case FORM_GROUP: {
                    if (!(businessObject instanceof IFormGroupObject)) {
                        throw new IllegalArgumentException("BusinessObject must be instance of class IFormGroupObject.");
                    }
                    if (!StringUtils.isEmpty(((IFormGroupObject)businessObject).getFormGroupKey())) break;
                    throw new IllegalArgumentException("FormGroupObject#getFormGroupKey must not be null.");
                }
            }
        }
        if (period2FormScheme.size() > 1) {
            throw new ProcessRuntimeException("jiuqi.nr.default", "\u9ed8\u8ba4\u6d41\u7a0b\u5f15\u64ce\u6682\u4e0d\u652f\u6301\u8de8\u62a5\u8868\u65b9\u6848\u6279\u91cf\u6267\u884c\u3002");
        }
        FormSchemeDefine formScheme = (FormSchemeDefine)((Map.Entry)period2FormScheme.entrySet().stream().findFirst().get()).getValue();
        return new RuntimeBusinessKeyCollection(businessKeys, task, formScheme, workflowSettings);
    }

    public String getTitle(RuntimeBusinessKey rtBusinessKey) {
        StringBuilder builder = new StringBuilder();
        builder.append("[").append(rtBusinessKey.getTask().getTitle()).append("]");
        IBusinessObject businessObject = rtBusinessKey.getBusinessKey().getBusinessObject();
        DimensionCombination dimensions = businessObject.getDimensions();
        String period = (String)dimensions.getPeriodDimensionValue().getValue();
        builder.append("[").append(this.getPeriodTitle(rtBusinessKey.getTask(), period)).append("]");
        IEntityTable mdEntityTable = this.createUnitQuery(BusinessKeyUtil.getTaskMasterEntityId(rtBusinessKey.getTask()), period, rtBusinessKey.getTask().getFilterExpression());
        IEntityRow mdRow = mdEntityTable.findByEntityKey((String)dimensions.getDWDimensionValue().getValue());
        if (mdRow != null) {
            builder.append("[").append(mdRow.getTitle()).append("]");
        }
        return builder.toString();
    }

    private String getPeriodTitle(TaskDefine task, String period) {
        try {
            IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(task.getDateTime());
            PeriodWrapper periodWrapper = new PeriodWrapper(period);
            return periodProvider.getPeriodTitle(periodWrapper);
        }
        catch (Exception e) {
            return period;
        }
    }

    private IEntityTable createUnitQuery(String entityId, String period, String filterExpression) {
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.setAuthorityOperations(AuthorityType.None);
        DimensionValueSet masterKeys = new DimensionValueSet();
        masterKeys.setValue("DATATIME", (Object)period);
        query.setMasterKeys(masterKeys);
        EntityViewDefine entityView = this.entityViewController.buildEntityView(entityId, filterExpression);
        query.setEntityView(entityView);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionController);
        context.setVarDimensionValueSet(masterKeys);
        try {
            return query.executeReader((IContext)context);
        }
        catch (Exception e) {
            throw new ProcessRuntimeException(null, "\u67e5\u8be2\u4e3b\u7ef4\u5ea6\u53d1\u751f\u9519\u8bef\u3002", (Throwable)e);
        }
    }

    private static String getTaskMasterEntityId(TaskDefine task) {
        String entityId = DsContextHolder.getDsContext().getContextEntityId();
        if (entityId == null || entityId.length() == 0) {
            return task.getDw();
        }
        return entityId;
    }
}

