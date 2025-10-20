/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.facade.IDimensionFilter
 *  com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl
 *  com.jiuqi.np.definition.proxy.EntityMetaServiceProxy
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.util.JacksonUtils
 */
package com.jiuqi.nr.definition.internal.controller2;

import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.facade.IDimensionFilter;
import com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl;
import com.jiuqi.np.definition.proxy.EntityMetaServiceProxy;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataLinkMappingDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormFoldingDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormSchemeDefineGetterImpl;
import com.jiuqi.nr.definition.internal.runtime.controller.IDimensionFilterService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRunTimeFilterTemplateService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataLinkMappingService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataLinkService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataRegionService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataRegionSettingService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormGroupService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemePeriodService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemeService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskGroupService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskLinkService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService;
import com.jiuqi.nr.definition.internal.service.FormFoldingService;
import com.jiuqi.nr.definition.internal.service.ParamStreamService;
import com.jiuqi.nr.definition.internal.service.TaskOrgLinkService;
import com.jiuqi.nr.definition.internal.stream.param.FormGroupListStream;
import com.jiuqi.nr.definition.internal.stream.param.FormGroupStream;
import com.jiuqi.nr.definition.internal.stream.param.FormListStream;
import com.jiuqi.nr.definition.internal.stream.param.FormSchemeListStream;
import com.jiuqi.nr.definition.internal.stream.param.FormSchemeStream;
import com.jiuqi.nr.definition.internal.stream.param.FormStream;
import com.jiuqi.nr.definition.internal.stream.param.RegionSettingStream;
import com.jiuqi.nr.definition.internal.stream.param.TaskGroupListStream;
import com.jiuqi.nr.definition.internal.stream.param.TaskGroupStream;
import com.jiuqi.nr.definition.internal.stream.param.TaskListStream;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkStream;
import com.jiuqi.nr.definition.internal.stream.param.TaskStream;
import com.jiuqi.nr.definition.util.AttachmentObj;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.util.JacksonUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class RunTimeViewController
implements IRunTimeViewController {
    private static final Logger logger = LoggerFactory.getLogger(RunTimeViewController.class);
    @Autowired
    private IRuntimeTaskGroupService taskGroupService;
    @Autowired
    private IRuntimeTaskService taskService;
    @Autowired
    private IRuntimeFormSchemeService formSchemeService;
    @Autowired
    private IRuntimeFormGroupService formGroupService;
    @Autowired
    private IRuntimeFormService formService;
    @Autowired
    private IRuntimeDataRegionService dataRegionService;
    @Autowired
    private IRuntimeDataRegionSettingService dataRegionSettingService;
    @Autowired
    private IRuntimeDataLinkService dataLinkService;
    @Autowired
    private IRuntimeTaskLinkService taskLinkService;
    @Autowired
    private IRuntimeFormSchemePeriodService iRuntimeFormSchemePeriodService;
    @Autowired
    private IRuntimeDataLinkMappingService runtimeDataLinkMappingService;
    @Autowired
    private IDimensionFilterService runTimeDimensionFilterService;
    @Autowired
    private ParamStreamService paramStreamService;
    @Autowired
    private EntityMetaServiceProxy metaServiceProxy;
    @Autowired
    private IEntityViewRunTimeController viewRunTimeController;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private FormFoldingService formFoldingService;
    @Autowired
    private TaskOrgLinkService taskOrgLinkService;
    @Autowired
    private IRunTimeFilterTemplateService filterTemplateService;

    @Override
    public TaskStream getTaskStream(String taskKey) {
        return this.paramStreamService.getTaskStream(this.getTask(taskKey));
    }

    @Override
    public TaskDefine getTask(String taskKey) {
        TaskDefine taskDefine = this.taskService.queryTaskDefine(taskKey);
        return taskDefine;
    }

    @Override
    public TaskListStream listAllTaskStream() {
        return this.paramStreamService.getTaskListStream(this.listAllTask());
    }

    @Override
    public List<TaskDefine> listAllTask() {
        List<TaskDefine> allTaskDefines = this.taskService.getAllTaskDefines();
        allTaskDefines = allTaskDefines.stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder).reversed()).collect(Collectors.toList());
        return allTaskDefines;
    }

    @Override
    public TaskListStream listTaskByTypeStream(TaskType taskType) {
        return this.paramStreamService.getTaskListStream(this.listTaskByType(taskType));
    }

    @Override
    public List<TaskDefine> listTaskByType(TaskType taskType) {
        List<TaskDefine> allTaskDefinesByType = this.taskService.getAllTaskDefinesByType(taskType);
        allTaskDefinesByType = allTaskDefinesByType.stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder).reversed()).collect(Collectors.toList());
        return allTaskDefinesByType;
    }

    @Override
    public TaskStream getTaskByCodeStream(String taskCode) {
        return this.paramStreamService.getTaskStream(this.getTaskByCode(taskCode));
    }

    @Override
    public TaskDefine getTaskByCode(String taskCode) {
        return this.taskService.queryTaskDefineByCode(taskCode);
    }

    @Override
    public TaskStream getTaskByFilePrefixStream(String filePrefix) {
        return this.paramStreamService.getTaskStream(this.getTaskByFilePrefix(filePrefix));
    }

    @Override
    public TaskDefine getTaskByFilePrefix(String filePrefix) {
        return this.taskService.queryTaskDefineByFilePrefix(filePrefix);
    }

    @Override
    public TaskListStream listTaskByTaskGroupStream(String taskGroup) {
        return this.paramStreamService.getTaskListStream(this.listTaskByTaskGroup(taskGroup));
    }

    @Override
    public List<TaskDefine> listTaskByTaskGroup(String taskGroup) {
        List<TaskDefine> taskDefines = this.taskService.queryTaskDefinesInGroup(taskGroup);
        Collections.reverse(taskDefines);
        return taskDefines;
    }

    @Override
    public List<TaskDefine> listTaskByDataScheme(String dataScheme) {
        List<TaskDefine> taskDefines = this.taskService.queryTaskDefinesByDataScheme(dataScheme);
        Collections.reverse(taskDefines);
        return taskDefines;
    }

    @Override
    public List<TaskDefine> listTaskByCaliber(String caliber) {
        List<TaskDefine> taskDefines = this.taskService.queryTaskDefinesByCaliber(caliber);
        Collections.reverse(taskDefines);
        return taskDefines;
    }

    @Override
    public TaskGroupDefine getTaskGroup(String taskGroupKey) {
        return this.taskGroupService.queryTaskGroup(taskGroupKey);
    }

    @Override
    public TaskGroupStream getTaskGroupStream(String taskGroupKey) {
        return this.paramStreamService.getTaskGroupStream(this.getTaskGroup(taskGroupKey));
    }

    @Override
    public List<TaskGroupDefine> listTaskGroupByParentGroup(String taskGroup) {
        return this.taskGroupService.getChildTaskGroups(taskGroup, false);
    }

    @Override
    public TaskGroupListStream listTaskGroupByParentGroupStream(String taskGroup) {
        return this.paramStreamService.getTaskGroupListStream(this.listTaskGroupByParentGroup(taskGroup));
    }

    @Override
    public List<TaskGroupDefine> listTaskGroupByTask(String taskKey) {
        return this.taskGroupService.getAllGroupsFromTask(taskKey);
    }

    @Override
    public List<TaskGroupDefine> listAllTaskGroup() {
        return this.taskGroupService.getAllTaskGroupDefines();
    }

    @Override
    public FormSchemeListStream listFormSchemeByTaskStream(String taskKey) {
        return this.paramStreamService.getFormSchemeListStream(this.listFormSchemeByTask(taskKey));
    }

    @Override
    public List<FormSchemeDefine> listAllFormScheme() {
        List<FormSchemeDefine> formSchemeDefines = this.formSchemeService.queryAllFormScheme();
        if (formSchemeDefines != null && !formSchemeDefines.isEmpty()) {
            return formSchemeDefines.stream().map(RunTimeFormSchemeDefineGetterImpl::new).collect(Collectors.toList());
        }
        return formSchemeDefines;
    }

    @Override
    public List<FormSchemeDefine> listFormSchemeByTask(String taskKey) {
        List<FormSchemeDefine> formSchemeDefines = this.formSchemeService.queryFormSchemeByTask(taskKey);
        if (formSchemeDefines != null && formSchemeDefines.size() > 0) {
            return formSchemeDefines.stream().map(e -> new RunTimeFormSchemeDefineGetterImpl((FormSchemeDefine)e)).collect(Collectors.toList());
        }
        return formSchemeDefines;
    }

    @Override
    public FormSchemeStream getFormSchemeStream(String formSchemeKey) {
        return this.paramStreamService.getFormSchemeStream(this.getFormScheme(formSchemeKey));
    }

    @Override
    public FormSchemeDefine getFormScheme(String formSchemeKey) {
        FormSchemeDefine formSchemeDefine = this.formSchemeService.getFormScheme(formSchemeKey);
        if (formSchemeDefine != null) {
            return new RunTimeFormSchemeDefineGetterImpl(formSchemeDefine);
        }
        return formSchemeDefine;
    }

    @Override
    public FormSchemeStream getFormSchemeByCodeStream(String formSchemeCode) {
        return this.paramStreamService.getFormSchemeStream(this.getFormSchemeByCode(formSchemeCode));
    }

    @Override
    public FormSchemeDefine getFormSchemeByCode(String formSchemeCode) {
        FormSchemeDefine formSchemeDefine = this.formSchemeService.getFormschemeByCode(formSchemeCode);
        if (formSchemeDefine != null) {
            return new RunTimeFormSchemeDefineGetterImpl(formSchemeDefine);
        }
        return formSchemeDefine;
    }

    @Override
    public FormGroupStream getFormGroupStream(String formGroupKey, String formScheme) {
        FormGroupDefine formGroupDefine = this.getFormGroup(formGroupKey, formScheme);
        return this.paramStreamService.getFormGroupStream(formGroupDefine);
    }

    @Override
    public FormGroupDefine getFormGroup(String formGroupKey, String formScheme) {
        return this.formGroupService.queryFormGroup(formGroupKey, formScheme);
    }

    @Override
    public FormGroupListStream listFormGroupByFormSchemeStream(String formScheme) {
        return this.paramStreamService.getFormGroupListStream(this.listFormGroupByFormScheme(formScheme));
    }

    @Override
    public List<FormGroupDefine> listFormGroupByFormScheme(String formScheme) {
        return this.formGroupService.queryRootGroupsByFormScheme(formScheme);
    }

    @Override
    public FormGroupListStream listFormGroupByFormStream(String formKey, String formScheme) {
        return this.paramStreamService.getFormGroupListStream(this.listFormGroupByForm(formKey, formScheme));
    }

    @Override
    public List<FormGroupDefine> listFormGroupByForm(String formKey, String formScheme) {
        return this.formGroupService.getFormGroupsByForm(formKey, formScheme);
    }

    @Override
    public FormStream getFormStream(String formKey, String formScheme) {
        return this.paramStreamService.getFormStream(this.getForm(formKey, formScheme));
    }

    @Override
    public FormDefine getForm(String formKey, String formScheme) {
        return this.formService.queryForm(formKey, formScheme);
    }

    @Override
    public FormStream listFormByCodeAndFormSchemeStream(String formCode, String formScheme) {
        return this.paramStreamService.getFormStream(this.listFormByCodeAndFormScheme(formCode, formScheme));
    }

    @Override
    public FormDefine listFormByCodeAndFormScheme(String formCode, String formScheme) {
        return this.formService.queryFormByCodeInScheme(formScheme, formCode);
    }

    @Override
    public FormListStream listFormByFormSchemeStream(String formScheme) {
        return this.paramStreamService.getFormListStream(this.listFormByFormScheme(formScheme));
    }

    @Override
    public List<FormDefine> listFormByFormScheme(String formScheme) {
        return this.formService.queryFormDefinesByFormScheme(formScheme);
    }

    @Override
    public FormStream getFmdmFormByFormSchemeStream(String formScheme) {
        return this.paramStreamService.getFormStream(this.getFmdmFormByFormScheme(formScheme));
    }

    @Override
    public FormDefine getFmdmFormByFormScheme(String formScheme) {
        List<FormDefine> allForms = this.listFormByFormScheme(formScheme);
        FormDefine form = null;
        Optional<FormDefine> first = allForms.stream().filter(form1 -> form1.getFormType() == FormType.FORM_TYPE_NEWFMDM).findFirst();
        if (first.isPresent()) {
            form = first.get();
        }
        return form;
    }

    @Override
    public FormListStream listFormByGroupStream(String formGroupKey, String formScheme) {
        return this.paramStreamService.getFormListStream(this.listFormByGroup(formGroupKey, formScheme));
    }

    @Override
    public List<FormDefine> listFormByGroup(String formGroupKey, String formScheme) {
        return this.formService.listFormByGroup(formGroupKey, formScheme);
    }

    @Override
    public BigDataDefine getFormStyle(String formKey, String formScheme) {
        return this.formService.getFormStyle(formKey, formScheme);
    }

    @Override
    public DataRegionDefine getDataRegion(String regionKey, String formSchemeKey) {
        return this.dataRegionService.getDataRegion(regionKey, formSchemeKey);
    }

    @Override
    public List<DataRegionDefine> listDataRegionByForm(String formKey, String formSchemeKey) {
        return this.dataRegionService.listDataRegionByForm(formKey, formSchemeKey);
    }

    @Override
    public RegionSettingStream getRegionSettingByRegionStream(String dataRegionKey, String formSchemeKey) {
        return this.paramStreamService.getRegionSettingStream(this.getRegionSettingByRegion(dataRegionKey, formSchemeKey));
    }

    @Override
    public RegionSettingDefine getRegionSettingByRegion(String dataRegionKey, String formSchemeKey) {
        return this.dataRegionSettingService.getRegionSettingByRegion(dataRegionKey, formSchemeKey);
    }

    @Override
    public DataLinkDefine getDataLink(String dataLinkKey, String formSchemeKey) {
        return this.dataLinkService.getDataLink(dataLinkKey, formSchemeKey);
    }

    @Override
    public AttachmentObj getLinkFileSetting(String dataLinkKey, String formSchemeKey) {
        String attachment;
        AttachmentObj attachmentObj = null;
        BigDataDefine bigData = this.dataLinkService.getLinkFileSetting(dataLinkKey, formSchemeKey);
        if (bigData != null && bigData.getData() != null && !"".equals(attachment = DesignFormDefineBigDataUtil.bytesToString(bigData.getData()))) {
            attachmentObj = (AttachmentObj)JacksonUtils.jsonToObject((String)attachment, AttachmentObj.class);
        }
        return attachmentObj;
    }

    @Override
    public List<DataLinkDefine> listDataLinkByForm(String formKey, String formSchemeKey) {
        return this.dataLinkService.listDataLinkByForm(formKey, formSchemeKey);
    }

    @Override
    public List<DataLinkDefine> listDataLinkByDataRegion(String dataRegionKey, String formSchemeKey) {
        return this.dataLinkService.listDataLinkByDataRegion(dataRegionKey, formSchemeKey);
    }

    @Override
    public DataLinkDefine getDataLinkByFormAndPos(String formKey, int posX, int posY, String formSchemeKey) {
        return this.dataLinkService.getDataLinkByFormAndPos(formKey, posX, posY, formSchemeKey);
    }

    @Override
    public DataLinkDefine getDataLinkByFormAndColRow(String formKey, int colNum, int rowNum, String formSchemeKey) {
        return this.dataLinkService.getDataLinkByFormAndColRow(formKey, colNum, rowNum, formSchemeKey);
    }

    @Override
    public DataLinkDefine getDataLinkByFormAndUniquecode(String formKey, String uniqueCode, String formSchemeKey) {
        return this.dataLinkService.getDataLinkByFormAndUniquecode(formKey, uniqueCode, formSchemeKey);
    }

    @Override
    public List<String> listFieldKeyByDataRegion(String dataRegionKey, String formSchemeKey) {
        return this.dataLinkService.listFieldKeyByDataRegion(dataRegionKey, formSchemeKey);
    }

    @Override
    public List<String> listFieldKeyByForm(String formKey, String formSchemeKey) {
        return this.dataLinkService.listFieldKeyByForm(formKey, formSchemeKey);
    }

    @Override
    public List<TaskLinkDefine> listTaskLinkByFormScheme(String formSchemeKey) {
        return this.taskLinkService.queryTaskLink(formSchemeKey);
    }

    @Override
    public List<SchemePeriodLinkDefine> listSchemePeriodLinkByFormScheme(String formSchemeKey) {
        return this.iRuntimeFormSchemePeriodService.querySchemePeriodLinkByScheme(formSchemeKey);
    }

    @Override
    public List<SchemePeriodLinkDefine> listSchemePeriodLinkByTask(String taskKey) {
        List<FormSchemeDefine> formSchemeDefines = this.listFormSchemeByTask(taskKey);
        ArrayList<SchemePeriodLinkDefine> result = new ArrayList<SchemePeriodLinkDefine>();
        formSchemeDefines.forEach(e -> result.addAll(this.listSchemePeriodLinkByFormScheme(e.getKey())));
        return result;
    }

    @Override
    public SchemePeriodLinkDefine getSchemePeriodLinkByPeriodAndFormScheme(String period, String formScheme) {
        return this.iRuntimeFormSchemePeriodService.querySchemePeriodLinkBySchemeAndPeriod(formScheme, period);
    }

    @Override
    public SchemePeriodLinkDefine getSchemePeriodLinkByPeriodAndTask(String period, String task) {
        List<FormSchemeDefine> formSchemeDefines = this.listFormSchemeByTask(task);
        SchemePeriodLinkDefine result = null;
        for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.iRuntimeFormSchemePeriodService.querySchemePeriodLinkBySchemeAndPeriod(formSchemeDefine.getKey(), period);
            if (null == schemePeriodLinkDefine) continue;
            result = schemePeriodLinkDefine;
        }
        return result;
    }

    @Override
    public List<DataLinkMappingDefine> listDataLinkMappingByForm(String formKey, String formSchemeKey) {
        return this.runtimeDataLinkMappingService.listDataLinkMappingByForm(formKey, formSchemeKey);
    }

    @Override
    public IDimensionFilter getDimensionFilterBySchemeAndEntity(String formSchemeKey, String entity) {
        return this.runTimeDimensionFilterService.getByFormSchemeAndEntityId(formSchemeKey, entity);
    }

    @Override
    public List<IDimensionFilter> listDimensionFilterByScheme(String formSchemeKey) {
        return this.runTimeDimensionFilterService.getByFormSchemeKey(formSchemeKey);
    }

    @Override
    public List<EntityViewDefine> listDimensionViews(String formSchemeKey) {
        List<IDimensionFilter> dimensionFilters = this.runTimeDimensionFilterService.getByFormSchemeKey(formSchemeKey);
        ArrayList<EntityViewDefine> entityViewDefines = new ArrayList<EntityViewDefine>();
        if (dimensionFilters != null && dimensionFilters.size() > 0) {
            for (IDimensionFilter dimensionFilter : dimensionFilters) {
                RunTimeEntityViewDefineImpl entityViewDefine = new RunTimeEntityViewDefineImpl();
                entityViewDefine.setEntityId(dimensionFilter.getEntityId());
                entityViewDefine.setRowFilterExpression(this.metaServiceProxy.getExpression(dimensionFilter));
                entityViewDefine.setFilterRowByAuthority(true);
                entityViewDefines.add((EntityViewDefine)entityViewDefine);
            }
        }
        return entityViewDefines;
    }

    @Override
    public EntityViewDefine getDimensionView(String formSchemeKey, String entityId) {
        IDimensionFilter dimensionFilter = this.runTimeDimensionFilterService.getByFormSchemeAndEntityId(formSchemeKey, entityId);
        RunTimeEntityViewDefineImpl entityViewDefine = new RunTimeEntityViewDefineImpl();
        if (dimensionFilter != null) {
            entityViewDefine.setEntityId(dimensionFilter.getEntityId());
            entityViewDefine.setRowFilterExpression(this.metaServiceProxy.getExpression(dimensionFilter));
            entityViewDefine.setFilterRowByAuthority(true);
        }
        return entityViewDefine;
    }

    @Override
    public EntityViewDefine getViewByTaskDefineKey(String taskKey) {
        TaskDefine taskDefine = this.getTask(taskKey);
        if (taskDefine == null) {
            return null;
        }
        return this.viewRunTimeController.buildEntityView(taskDefine.getDw(), taskDefine.getFilterExpression());
    }

    @Override
    public EntityViewDefine getViewByFormSchemeKey(String schemeKey) {
        EntityViewDefine viewByTaskDefineKey;
        FormSchemeDefine formScheme = this.getFormScheme(schemeKey);
        if (formScheme == null) {
            return null;
        }
        String dw = formScheme.getDw();
        String filterExpression = formScheme.getFilterExpression();
        String taskKey = formScheme.getTaskKey();
        if (!StringUtils.hasLength(dw) && (viewByTaskDefineKey = this.getViewByTaskDefineKey(taskKey)) != null) {
            dw = viewByTaskDefineKey.getEntityId();
        }
        if (!StringUtils.hasLength(filterExpression) && (viewByTaskDefineKey = this.getViewByTaskDefineKey(taskKey)) != null) {
            filterExpression = viewByTaskDefineKey.getRowFilterExpression();
        }
        return this.viewRunTimeController.buildEntityView(dw, filterExpression);
    }

    @Override
    public EntityViewDefine getViewByLinkAndFormScheme(String linkKey, String formSchemeKey) {
        DataLinkDefine dataLink = this.getDataLink(linkKey, formSchemeKey);
        if (dataLink == null) {
            return null;
        }
        if (com.jiuqi.bi.util.StringUtils.isEmpty((String)dataLink.getFilterExpression())) {
            RunTimeEntityViewDefineImpl entityViewDefine = this.filterTemplateService.getFilterTemplate(dataLink.getFilterTemplate());
            if (entityViewDefine != null) {
                entityViewDefine.setFilterRowByAuthority(!dataLink.isIgnorePermissions());
                return entityViewDefine;
            }
            return this.getLinkViewDefine(dataLink, formSchemeKey);
        }
        return this.getLinkViewDefine(dataLink, formSchemeKey);
    }

    private EntityViewDefine getLinkViewDefine(DataLinkDefine dataLink, String formSchemeKey) {
        if (dataLink.getType() == DataLinkType.DATA_LINK_TYPE_FMDM && !com.jiuqi.bi.util.StringUtils.isEmpty((String)dataLink.getLinkExpression())) {
            String fmdmEntityId = null;
            DataRegionDefine regionDefine = this.getDataRegion(dataLink.getRegionKey(), formSchemeKey);
            FormDefine formDefine = this.getForm(regionDefine.getFormKey(), formSchemeKey);
            FormSchemeDefine formScheme = this.getFormScheme(formDefine.getFormScheme());
            List entityRefer = this.entityMetaService.getEntityRefer(formScheme.getDw());
            Optional<IEntityRefer> referEntity = entityRefer.stream().filter(e -> e.getOwnField().equals(dataLink.getLinkExpression())).findFirst();
            if (referEntity.isPresent()) {
                fmdmEntityId = referEntity.get().getReferEntityId();
            } else {
                IEntityModel entityModel = this.entityMetaService.getEntityModel(formScheme.getDw());
                IEntityAttribute parentField = entityModel.getParentField();
                if (parentField != null && parentField.getCode().equalsIgnoreCase(dataLink.getLinkExpression())) {
                    fmdmEntityId = formScheme.getDw();
                }
            }
            if (fmdmEntityId == null) {
                logger.warn("\u5c01\u9762\u4ee3\u7801\u94fe\u63a5\u914d\u7f6e\u6709\u8bef\uff0c\u94fe\u63a5{}\u627e\u4e0d\u5230\u5b9e\u4f53\u5c5e\u6027", (Object)dataLink.getKey());
                return null;
            }
            return this.buildEntityView(fmdmEntityId, dataLink.getFilterExpression(), !dataLink.isIgnorePermissions());
        }
        try {
            DataField dataField = this.dataSchemeService.getDataField(dataLink.getLinkExpression());
            return this.buildEntityView(dataField.getRefDataEntityKey(), dataLink.getFilterExpression(), !dataLink.isIgnorePermissions());
        }
        catch (Exception e2) {
            logger.warn("\u6307\u6807\u67e5\u8be2\u51fa\u9519,\u94fe\u63a5{}\u627e\u4e0d\u5230\u6620\u5c04\u6307\u6807", (Object)dataLink.getKey());
            return null;
        }
    }

    private RunTimeEntityViewDefineImpl buildEntityView(String entityID, String filterExpression, boolean filterRowByAuthority) {
        RunTimeEntityViewDefineImpl entityViewDefine = new RunTimeEntityViewDefineImpl();
        entityViewDefine.setEntityId(entityID);
        entityViewDefine.setRowFilterExpression(filterExpression);
        entityViewDefine.setFilterRowByAuthority(filterRowByAuthority);
        return entityViewDefine;
    }

    @Override
    public List<FormFoldingDefine> listFormFoldingByFormKey(String formKey) {
        return this.formFoldingService.getByFormKey(formKey);
    }

    @Override
    public TaskOrgLinkStream getTaskOrgLinkStream(String key) {
        return this.paramStreamService.getTaskOrgLinkStream(this.getTaskOrgLink(key));
    }

    @Override
    public TaskOrgLinkDefine getTaskOrgLink(String key) {
        return this.taskOrgLinkService.getByKey(key);
    }

    @Override
    public TaskOrgLinkListStream listTaskOrgLinkStreamByTask(String task) {
        return this.paramStreamService.getTaskOrgLinkListStream(this.listTaskOrgLinkByTask(task));
    }

    @Override
    public List<TaskOrgLinkDefine> listTaskOrgLinkByTask(String task) {
        ArrayList<TaskOrgLinkDefine> orgLinkDefines = new ArrayList();
        try {
            orgLinkDefines = this.taskOrgLinkService.getByTask(task);
        }
        catch (Exception e) {
            throw new RuntimeException("\u6839\u636e\u4efb\u52a1\u83b7\u53d6\u4efb\u52a1-\u5b9e\u4f53\u94fe\u63a5\u8bb0\u5f55\u51fa\u9519", e);
        }
        return orgLinkDefines;
    }

    @Override
    public TaskOrgLinkStream getTaskOrgLinkStreamByTaskAndEntity(String task, String entity) {
        return this.paramStreamService.getTaskOrgLinkStream(this.getTaskOrgLinkByTaskAndEntity(task, entity));
    }

    @Override
    public TaskOrgLinkDefine getTaskOrgLinkByTaskAndEntity(String task, String entity) {
        return this.taskOrgLinkService.getByTaskAndEntity(task, entity);
    }

    @Override
    public DataRegionDefine getDataRegion(String regionCode, String formKey, String formSchemeKey) {
        return this.dataRegionService.getDataRegion(regionCode, formKey, formSchemeKey);
    }
}

