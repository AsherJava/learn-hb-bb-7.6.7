/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.workflow2.service.helper;

import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.workflow2.service.helper.IProcessRuntimeParamHelper;
import com.jiuqi.nr.workflow2.service.helper.IReportDimensionHelper;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessRuntimeParamHelper
implements IProcessRuntimeParamHelper {
    @Autowired
    public IEntityMetaService metaService;
    @Autowired
    public IPeriodEntityAdapter periodAdapter;
    @Autowired
    private IRunTimeViewController modelDefineService;
    @Autowired
    private IReportDimensionHelper reportDimensionHelper;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;

    @Override
    public TaskDefine getTaskDefine(String taskKey) {
        return this.modelDefineService.getTask(taskKey);
    }

    @Override
    public FormSchemeDefine getFormScheme(String taskKey, String period) {
        SchemePeriodLinkDefine schemePeriodLinkDefine = this.modelDefineService.getSchemePeriodLinkByPeriodAndTask(period, taskKey);
        if (schemePeriodLinkDefine != null) {
            return this.modelDefineService.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
        }
        return null;
    }

    @Override
    public List<FormDefine> listFormByFormScheme(String taskKey, String period) {
        FormSchemeDefine formScheme = this.getFormScheme(taskKey, period);
        return this.modelDefineService.listFormByFormScheme(formScheme.getKey());
    }

    @Override
    public List<FormDefine> listFormByFormScheme(String taskKey, String period, List<String> rangeFormKeys) {
        FormSchemeDefine formScheme = this.getFormScheme(taskKey, period);
        ArrayList<FormDefine> formDefines = new ArrayList<FormDefine>();
        for (String rangeFormKey : rangeFormKeys) {
            FormDefine form = this.modelDefineService.getForm(formScheme.getKey(), rangeFormKey);
            if (form == null) continue;
            formDefines.add(form);
        }
        return formDefines;
    }

    @Override
    public List<FormGroupDefine> listFormGroupByFormScheme(String taskKey, String period) {
        FormSchemeDefine formScheme = this.getFormScheme(taskKey, period);
        return this.modelDefineService.listFormGroupByFormScheme(formScheme.getKey());
    }

    @Override
    public List<IEntityDefine> listAllProcessEntityDefine(String taskKey, boolean isAuth) {
        ArrayList<IEntityDefine> entityDefines = new ArrayList<IEntityDefine>();
        TaskOrgLinkListStream taskOrgLinkListStream = this.modelDefineService.listTaskOrgLinkStreamByTask(taskKey);
        List orgLinkDefines = isAuth ? taskOrgLinkListStream.auth().getList() : taskOrgLinkListStream.getList();
        for (TaskOrgLinkDefine orgLinkDefine : orgLinkDefines) {
            IEntityDefine entityDefine = this.metaService.queryEntity(orgLinkDefine.getEntity());
            if (entityDefine == null) continue;
            entityDefines.add(entityDefine);
        }
        return entityDefines;
    }

    @Override
    public List<FormulaSchemeDefine> listAllFormulaSchemeByFormScheme(String taskKey, String period) {
        FormSchemeDefine formScheme = this.getFormScheme(taskKey, period);
        return this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(formScheme.getKey());
    }

    @Override
    public IPeriodEntity getPeriodEntityDefine(String taskKey) {
        TaskDefine taskDefine = this.getTaskDefine(taskKey);
        if (taskDefine != null) {
            return this.periodAdapter.getPeriodEntity(taskDefine.getDateTime());
        }
        return null;
    }

    @Override
    public EntityViewDefine buildEntityView(String taskKey, String period) {
        FormSchemeDefine formScheme = this.getFormScheme(taskKey, period);
        if (null != formScheme) {
            return this.modelDefineService.getViewByFormSchemeKey(formScheme.getKey());
        }
        return null;
    }

    @Override
    public EntityViewDefine buildEntityView(String taskKey, String period, String entityDefinitionId) {
        FormSchemeDefine formScheme = this.getFormScheme(taskKey, period);
        if (null != formScheme) {
            String filterExpression = formScheme.getFilterExpression();
            RunTimeEntityViewDefineImpl entityViewDefine = new RunTimeEntityViewDefineImpl();
            entityViewDefine.setEntityId(entityDefinitionId);
            entityViewDefine.setRowFilterExpression(StringUtils.isNotEmpty((String)filterExpression) ? filterExpression : null);
            entityViewDefine.setFilterRowByAuthority(true);
            return entityViewDefine;
        }
        return null;
    }

    @Override
    public IEntityDefine getProcessEntityDefinition(String taskKey) {
        IEntityDefine entityDefine;
        DsContext dsContext = DsContextHolder.getDsContext();
        if (StringUtils.isNotEmpty((String)dsContext.getContextEntityId()) && (entityDefine = this.metaService.queryEntity(dsContext.getContextEntityId())) != null) {
            return entityDefine;
        }
        TaskDefine taskDefine = this.modelDefineService.getTask(taskKey);
        return this.metaService.queryEntity(taskDefine.getDw());
    }
}

