/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.facade.IDimensionFilter
 *  com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl
 *  com.jiuqi.np.definition.proxy.EntityMetaServiceProxy
 *  com.jiuqi.np.sql.exception.NotImplementedException
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.util.JacksonUtils
 */
package com.jiuqi.nr.definition.internal.runtime.controller;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.facade.IDimensionFilter;
import com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl;
import com.jiuqi.np.definition.proxy.EntityMetaServiceProxy;
import com.jiuqi.np.sql.exception.NotImplementedException;
import com.jiuqi.nr.definition.common.DataLinkEditMode;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.AnalysisFormGroupDefine;
import com.jiuqi.nr.definition.facade.AnalysisFormParamDefine;
import com.jiuqi.nr.definition.facade.AnalysisSchemeParamDefine;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataLinkMappingDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.EntityLinkageDefine;
import com.jiuqi.nr.definition.facade.EnumLinkageSettingDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormFieldInfoDefine;
import com.jiuqi.nr.definition.facade.FormFoldingDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.facade.RegionPartitionDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.ReportTagDefine;
import com.jiuqi.nr.definition.facade.ReportTemplateDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.internal.ScriptParser;
import com.jiuqi.nr.definition.internal.impl.AnalysisFormGroupDefineImpl;
import com.jiuqi.nr.definition.internal.impl.AnalysisFormParamDefineImpl;
import com.jiuqi.nr.definition.internal.impl.AnalysisSchemeParamDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormSchemeDefineGetterImpl;
import com.jiuqi.nr.definition.internal.runtime.controller.IDimensionFilterService;
import com.jiuqi.nr.definition.internal.runtime.controller.IFormFieldInfoService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRunTimeEntityLinkageDefineService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRunTimeFilterTemplateService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRunTimeFormulaVariableService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeBigDataService;
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
import com.jiuqi.nr.definition.internal.runtime.service.NrFormParamCacheService;
import com.jiuqi.nr.definition.internal.service.FormFoldingService;
import com.jiuqi.nr.definition.internal.service.RuntimeReportTemplateService;
import com.jiuqi.nr.definition.reportTag.InjectContext;
import com.jiuqi.nr.definition.reportTag.service.IInjectReplyToRpt;
import com.jiuqi.nr.definition.reportTag.service.IRuntimeReportTagService;
import com.jiuqi.nr.definition.util.AttachObj;
import com.jiuqi.nr.definition.util.AttachmentObj;
import com.jiuqi.nr.definition.util.SerializeUtils;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.util.JacksonUtils;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class RuntimeViewController
implements IRunTimeViewController {
    private static final Logger logger = LoggerFactory.getLogger(RuntimeViewController.class);
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
    private IRunTimeEntityLinkageDefineService entityLinkageDefineService;
    @Autowired
    private IRuntimeBigDataService runtimeBigDataService;
    @Autowired
    private IRunTimeFormulaVariableService runtimeFormulaVariableService;
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    private IRuntimeFormSchemePeriodService iRuntimeFormSchemePeriodService;
    @Autowired
    private IRuntimeDataLinkMappingService runtimeDataLinkMappingService;
    @Autowired
    private RuntimeReportTemplateService runtimeReportTemplateService;
    @Autowired
    private IRuntimeReportTagService runtimeReportTagService;
    @Autowired(required=false)
    private IInjectReplyToRpt injectReplyToRptHelper;
    @Autowired
    private IDimensionFilterService runTimeDimensionFilterService;
    @Autowired
    private IFormFieldInfoService formFieldInfoService;
    @Autowired
    private EntityMetaServiceProxy metaServiceProxy;
    @Autowired
    private IRunTimeFilterTemplateService filterTemplateService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private FormFoldingService formFoldingService;

    @Override
    public TaskGroupDefine queryTaskGroupDefine(String taskGroupKey) {
        return this.taskGroupService.queryTaskGroup(taskGroupKey);
    }

    @Override
    public List<TaskGroupDefine> getChildTaskGroups(String taskGroupKey, boolean isRecursion) {
        return this.taskGroupService.getChildTaskGroups(taskGroupKey, isRecursion);
    }

    @Override
    public List<TaskGroupDefine> getGroupByTask(String taskKey) {
        return this.taskGroupService.getAllGroupsFromTask(taskKey);
    }

    @Override
    public List<TaskGroupDefine> getAllTaskGroup() {
        return this.taskGroupService.getAllTaskGroupDefines();
    }

    @Override
    public TaskDefine queryTaskDefine(String taskKey) {
        TaskDefine taskDefine = this.taskService.queryTaskDefine(taskKey);
        return taskDefine;
    }

    @Override
    public List<TaskDefine> getAllTaskDefines() {
        List<TaskDefine> allTaskDefines = this.taskService.getAllTaskDefines();
        return allTaskDefines.stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder).reversed()).collect(Collectors.toList());
    }

    @Override
    public List<TaskDefine> getAllReportTaskDefines() {
        List<TaskDefine> allTasks = this.getAllTaskDefines();
        List<TaskDefine> allTasksOrder = allTasks.stream().filter(t -> t.getTaskType() == TaskType.TASK_TYPE_DEFAULT || t.getTaskType() == TaskType.TASK_TYPE_SURVEY).collect(Collectors.toList());
        return allTasksOrder;
    }

    @Override
    public List<TaskDefine> getAllTaskDefinesByType(TaskType type) {
        List<TaskDefine> allTaskDefinesByType = this.taskService.getAllTaskDefinesByType(type);
        return allTaskDefinesByType.stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder).reversed()).collect(Collectors.toList());
    }

    @Override
    public TaskDefine queryTaskDefineByCode(String code) {
        return this.taskService.queryTaskDefineByCode(code);
    }

    @Override
    public TaskDefine queryTaskDefineByFilePrefix(String FilePrefix) {
        return this.taskService.queryTaskDefineByFilePrefix(FilePrefix);
    }

    @Override
    public List<TaskDefine> getAllRunTimeTasksInGroup(String taskGroupKey) {
        List<TaskDefine> taskDefines = this.taskService.queryTaskDefinesInGroup(taskGroupKey);
        Collections.reverse(taskDefines);
        return taskDefines;
    }

    @Override
    public List<FormSchemeDefine> listAllFormScheme() {
        List<FormSchemeDefine> formSchemeDefines = this.formSchemeService.queryAllFormScheme();
        if (formSchemeDefines != null && !formSchemeDefines.isEmpty()) {
            ArrayList<FormSchemeDefine> runTimeFormSchemeDefineGetterImpls = new ArrayList<FormSchemeDefine>();
            for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                runTimeFormSchemeDefineGetterImpls.add(new RunTimeFormSchemeDefineGetterImpl(formSchemeDefine));
            }
            return runTimeFormSchemeDefineGetterImpls;
        }
        return formSchemeDefines;
    }

    @Override
    public List<FormSchemeDefine> queryFormSchemeByTask(String taskKey) throws Exception {
        List<FormSchemeDefine> formSchemeDefines = this.formSchemeService.queryFormSchemeByTask(taskKey);
        if (formSchemeDefines != null && formSchemeDefines.size() > 0) {
            ArrayList<FormSchemeDefine> runTimeFormSchemeDefineGetterImpls = new ArrayList<FormSchemeDefine>();
            for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                runTimeFormSchemeDefineGetterImpls.add(new RunTimeFormSchemeDefineGetterImpl(formSchemeDefine));
            }
            return runTimeFormSchemeDefineGetterImpls;
        }
        return formSchemeDefines;
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
    public FormSchemeDefine getFormschemeByCode(String code) {
        FormSchemeDefine formSchemeDefine = this.formSchemeService.getFormschemeByCode(code);
        if (formSchemeDefine != null) {
            return new RunTimeFormSchemeDefineGetterImpl(formSchemeDefine);
        }
        return formSchemeDefine;
    }

    @Override
    public FormGroupDefine queryFormGroup(String formGroupKey) {
        return this.formGroupService.queryFormGroup(formGroupKey);
    }

    @Override
    public List<FormGroupDefine> queryRootGroupsByFormScheme(String formSchemeKey) {
        List<FormGroupDefine> formGroupDefines = this.formGroupService.queryRootGroupsByFormScheme(formSchemeKey);
        return formGroupDefines;
    }

    @Override
    public List<FormGroupDefine> getFormGroupsByFormKey(String formKey) {
        return this.formGroupService.getFormGroupsByForm(formKey);
    }

    @Override
    public List<FormGroupDefine> getChildFormGroups(String formGroupKey) {
        return this.formGroupService.getChildFormGroups(formGroupKey, false);
    }

    @Override
    public List<FormGroupDefine> getAllGroupsInGroup(String formGroupKey) {
        return this.formGroupService.getChildFormGroups(formGroupKey, true);
    }

    @Override
    public List<FormGroupDefine> getAllFormGroupsInFormScheme(String formSchemeKey) {
        return this.formGroupService.getAllFormGroupsInFormScheme(formSchemeKey);
    }

    @Override
    public List<FormDefine> getAllFormsInGroup(String formGroupKey) throws Exception {
        List<FormDefine> formDefines = this.getAllFormsInGroup(formGroupKey, false);
        return formDefines;
    }

    @Override
    public FormDefine queryFormById(String formKey) {
        return this.formService.queryForm(formKey);
    }

    @Override
    public List<FormDefine> queryFormsById(List<String> formKeys) {
        return this.formService.queryForms(formKeys);
    }

    @Override
    public FormDefine queryEntityForm(String formKey) throws Exception {
        return this.queryFormById(formKey);
    }

    @Override
    public FormDefine queryFormByCodeInScheme(String formSchemeKey, String formDefineCode) throws Exception {
        return this.formService.queryFormByCodeInScheme(formSchemeKey, formDefineCode);
    }

    @Override
    public List<FormDefine> queryAllFormDefinesByTask(String taskKey) {
        return this.formService.queryFormDefinesByTask(taskKey);
    }

    @Override
    public List<FormDefine> queryAllFormDefinesByFormScheme(String formSchemeKey) {
        return this.formService.queryFormDefinesByFormScheme(formSchemeKey);
    }

    @Override
    public List<String> queryAllFormKeysByFormScheme(String formSchemeKey) {
        return this.formService.queryFormKeysByFormScheme(formSchemeKey);
    }

    @Override
    public FormDefine queryFmdmFormDefineByFormScheme(String formSchemeKey) {
        List<FormDefine> allForms = this.queryAllFormDefinesByFormScheme(formSchemeKey);
        FormDefine form = null;
        Optional<FormDefine> first = allForms.stream().filter(form1 -> form1.getFormType() == FormType.FORM_TYPE_NEWFMDM).findFirst();
        if (first.isPresent()) {
            form = first.get();
        }
        return form;
    }

    @Override
    public BigDataDefine getReportDataFromForm(String formKey) {
        BigDataDefine bigDataDefine = this.formService.getReportDataFromForm(formKey);
        return bigDataDefine;
    }

    @Override
    public String getFillingGuideFromForm(String formKey) {
        return this.formService.getFillingGuideFromForm(formKey);
    }

    @Override
    public List<FormDefine> getAllFormsInGroup(String formGroupKey, boolean isRecursion) throws Exception {
        return this.formService.getFormsInGroupOrderByGroupLink(formGroupKey, isRecursion);
    }

    public int getFormsCountInGroup(String formGroupKey) throws Exception {
        return this.getFormsCountInGroup(formGroupKey, false);
    }

    @Override
    public int getFormsCountInGroup(String formGroupKey, boolean isRecursion) throws Exception {
        return this.formService.getFormsCountInGroup(formGroupKey, isRecursion);
    }

    @Override
    public DataRegionDefine queryDataRegionDefine(String dataRegionKey) {
        return this.dataRegionService.queryDataRegion(dataRegionKey);
    }

    @Override
    public List<DataRegionDefine> getAllRegionsInForm(String formKey) {
        return this.dataRegionService.getDataRegionsInForm(formKey);
    }

    @Override
    public List<RegionPartitionDefine> getRegionPartitionDefines(String dataRegionKey) {
        return this.dataRegionService.getRegionPartitionDefines(dataRegionKey);
    }

    @Override
    public RegionSettingDefine getRegionSetting(String dataRegionKey) {
        RegionSettingDefine regionSettingDefine = this.dataRegionSettingService.getRegionSetting(dataRegionKey);
        return regionSettingDefine;
    }

    @Override
    public DataLinkDefine queryDataLinkDefine(String dataLinkKey) {
        return this.dataLinkService.queryDataLink(dataLinkKey);
    }

    @Override
    public List<DataLinkDefine> getAllLinksInForm(String formKey) {
        return this.dataLinkService.getDataLinksInForm(formKey);
    }

    @Override
    public List<String> getAllDimensionsInForm(String formKey) {
        throw new NotImplementedException();
    }

    @Override
    public List<DataLinkDefine> getAllLinksInFormFilterBySelectorViewId(String formKey, DataLinkEditMode dataLinkEditMode) {
        throw new NotImplementedException();
    }

    @Override
    public List<DataLinkDefine> getAllLinksInRegion(String dataRegionKey) {
        return this.dataLinkService.getDataLinksInRegion(dataRegionKey);
    }

    @Override
    public List<String> getFieldKeysInRegion(String dataRegionKey) {
        return this.dataLinkService.getFieldKeysInRegion(dataRegionKey);
    }

    @Override
    public List<String> getFieldKeysInForm(String formKey) {
        return this.dataLinkService.getFieldKeysInForm(formKey);
    }

    @Override
    public DataLinkDefine queryDataLinkDefineByXY(String formKey, int posX, int posY) {
        return this.dataLinkService.queryDataLinkDefineByXY(formKey, posX, posY);
    }

    @Override
    public DataLinkDefine queryDataLinkDefineByColRow(String formKey, int col, int row) {
        return this.dataLinkService.queryDataLinkDefineByColRow(formKey, col, row);
    }

    @Override
    public DataLinkDefine queryDataLinkDefineByUniquecode(String formKey, String uniqueCode) {
        return this.dataLinkService.queryDataLinkDefineByUniquecode(formKey, uniqueCode);
    }

    @Override
    public List<DataLinkDefine> queryDataLinkDefineByUniquecodes(String formKey, Collection<String> uniqueCodes) {
        return this.dataLinkService.queryDataLinkDefineByUniquecodes(formKey, uniqueCodes);
    }

    @Override
    public List<DataLinkDefine> getLinksInFormByField(String formKey, String fieldKey) {
        return this.dataLinkService.getDataLinksInFormByField(formKey, fieldKey);
    }

    @Override
    public List<DataLinkDefine> getLinksInRegionByField(String regionKey, String fieldKey) {
        return this.dataLinkService.getDataLinksInRegionByField(regionKey, fieldKey);
    }

    @Override
    public EnumLinkageSettingDefine getEnumLinkage(String dataLinkKey) {
        throw new NotImplementedException();
    }

    @Override
    public List<EnumLinkageSettingDefine> getEnumLinkages(String[] dataLinkKeys) {
        throw new NotImplementedException();
    }

    @Override
    public List<TaskLinkDefine> queryLinksByCurrentTask(String currentTaskKey) {
        throw new NotImplementedException();
    }

    @Override
    public TaskLinkDefine queryLinkByCurrentTaskAndNum(String currentTaskKey, String serialNumber) {
        throw new NotImplementedException();
    }

    @Override
    public FormDefine getLinkTaskForm(String formSchemeKey, String linkName, String formCode) {
        throw new NotImplementedException();
    }

    @Override
    public void initCache() throws Exception {
    }

    @Override
    public void initFormCache(String formKey) {
    }

    @Override
    public void initTask(String taskKey) throws Exception {
    }

    @Override
    public void initEntityFormCache(String formKey) throws Exception {
    }

    @Override
    @Deprecated
    public String getFormSchemeEntity(String formSchemeKey) throws JQException {
        FormSchemeDefine queryFormSchemeDefine = null;
        try {
            queryFormSchemeDefine = this.getFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_001, (Throwable)e);
        }
        if (queryFormSchemeDefine == null) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_001, formSchemeKey);
        }
        return queryFormSchemeDefine.getMasterEntitiesKey();
    }

    @Override
    @Deprecated
    public String getFormEntity(String formSchemeKey, String formKey) throws JQException {
        FormDefine queryFormDefine = this.queryFormById(formKey);
        if (queryFormDefine == null) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_004, formKey);
        }
        return queryFormDefine.getMasterEntitiesKey();
    }

    @Override
    public List<TaskLinkDefine> queryLinksByCurrentFormScheme(String formSchemeKey) {
        return this.taskLinkService.queryTaskLink(formSchemeKey);
    }

    @Override
    public TaskLinkDefine queryTaskLinkByCurrentFormSchemeAndNumber(String currentFormSchemeKey, String serialNumber) {
        return this.taskLinkService.queryTaskLinkByCurrentFormSchemeAndNumber(currentFormSchemeKey, serialNumber);
    }

    @Override
    public EntityLinkageDefine queryEntityLinkageDefine(String elKey) {
        return this.entityLinkageDefineService.queryEntityLinkageDefine(elKey);
    }

    @Override
    public List<FormSchemeDefine> queryFormSchemeByField(String fieldKey) throws Exception {
        ArrayList<FormSchemeDefine> schemeDefines = new ArrayList<FormSchemeDefine>();
        List<DataLinkDefine> links = this.dataLinkService.getDataLinksByField(fieldKey);
        if (links.size() > 0) {
            HashSet<String> schemeKeys = new HashSet<String>();
            for (DataLinkDefine link : links) {
                List<FormGroupDefine> formGroups;
                DataRegionDefine dataRegionDefine = this.queryDataRegionDefine(link.getRegionKey());
                if (dataRegionDefine == null || (formGroups = this.getFormGroupsByFormKey(dataRegionDefine.getFormKey())).size() <= 0) continue;
                for (FormGroupDefine group : formGroups) {
                    FormSchemeDefine formScheme;
                    if (!schemeKeys.add(group.getFormSchemeKey()) || (formScheme = this.getFormScheme(group.getFormSchemeKey())) == null) continue;
                    schemeDefines.add(new RunTimeFormSchemeDefineGetterImpl(formScheme));
                }
            }
        }
        return schemeDefines;
    }

    @Override
    public String getFrontScriptFromForm(String formKey) {
        String scriptValue = this.formService.getFrontScriptFromForm(formKey);
        if (StringUtils.isEmpty((String)scriptValue)) {
            return null;
        }
        return new ScriptParser(formKey).parser(scriptValue);
    }

    @Override
    public String getSurveyDataFromForm(String formKey) {
        return this.formService.getSurveyDataFromForm(formKey);
    }

    @Override
    public AttachObj getAttachment(String linkKey) throws Exception {
        String attachment;
        BigDataDefine bigData = this.dataLinkService.getAttachmentDataFromLink(linkKey);
        AttachmentObj attachmentObj = null;
        if (bigData != null && bigData.getData() != null && StringUtils.isNotEmpty((String)(attachment = DesignFormDefineBigDataUtil.bytesToString(bigData.getData())))) {
            attachmentObj = (AttachmentObj)JacksonUtils.jsonToObject((String)attachment, AttachmentObj.class);
        }
        return new AttachObj(attachmentObj);
    }

    @Override
    public List<FormSchemeDefine> queryFormSchemeByEntity(String entityKey) throws JQException {
        ArrayList<FormSchemeDefine> result = new ArrayList<FormSchemeDefine>();
        List<TaskDefine> taskDefines = this.getAllTaskDefines();
        for (TaskDefine taskDefine : taskDefines) {
            try {
                List<FormSchemeDefine> schemes = this.queryFormSchemeByTask(taskDefine.getKey());
                for (FormSchemeDefine scheme : schemes) {
                    RunTimeFormSchemeDefineGetterImpl define = new RunTimeFormSchemeDefineGetterImpl(scheme);
                    if (!entityKey.equals(scheme.getDw())) continue;
                    result.add(define);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }

    @Override
    public List<FormSchemeDefine> queryFormSchemeByForm(String formKey) throws Exception {
        List<FormGroupDefine> groups = this.getFormGroupsByFormKey(formKey);
        HashSet schemeKeySet = new HashSet();
        List<FormSchemeDefine> schemes = groups.stream().map(group -> {
            FormSchemeDefine formScheme = this.getFormScheme(group.getFormSchemeKey());
            if (schemeKeySet.add(formScheme.getKey())) {
                return formScheme;
            }
            return null;
        }).filter(scheme -> scheme != null).collect(Collectors.toList());
        return schemes;
    }

    @Override
    public List<FormDefine> getAllFormsInGroupWithoutOrder(String formGroupKey) throws Exception {
        List<FormDefine> forms = this.getAllFormsInGroup(formGroupKey, false);
        return forms;
    }

    @Override
    public List<FormDefine> getAllFormsInGroupWithoutOrder(String formGroupKey, boolean isRecursion) throws Exception {
        List<FormDefine> forms = this.formService.getFormsInGroup(formGroupKey, isRecursion);
        return forms;
    }

    @Override
    public AnalysisFormParamDefine queryAnalysisFormParamDefine(String formKey) throws Exception {
        BigDataDefine dataDefine = this.runtimeBigDataService.getBigDataDefineFromForm(formKey, "ANALYSIS_FORM_PARAM");
        if (null == dataDefine) {
            return null;
        }
        byte[] data = dataDefine.getData();
        if (null != data && data.length > 0) {
            return SerializeUtils.jsonDeserialize(AnalysisFormParamDefineImpl.getDefaultModule(), data, AnalysisFormParamDefineImpl.class);
        }
        return null;
    }

    @Override
    public AnalysisSchemeParamDefine queryAnalysisSchemeParamDefine(String formSchemeKey) throws Exception {
        BigDataDefine dataDefine = this.runtimeBigDataService.getBigDataDefineFromForm(formSchemeKey, "ANALYSIS_PARAM");
        if (null == dataDefine) {
            return null;
        }
        byte[] data = dataDefine.getData();
        if (null != data && data.length > 0) {
            return SerializeUtils.jsonDeserialize(AnalysisSchemeParamDefineImpl.getDefaultModule(), data, AnalysisSchemeParamDefineImpl.class);
        }
        return null;
    }

    @Override
    public AnalysisFormGroupDefine queryAnalysisFormGroupDefine(String formGroupKey) throws Exception {
        BigDataDefine dataDefine = this.runtimeBigDataService.getBigDataDefineFromForm(formGroupKey, "ANALYSIS_GROUP_PARAM");
        if (null == dataDefine) {
            return null;
        }
        byte[] data = dataDefine.getData();
        if (null != data && data.length > 0) {
            return SerializeUtils.jsonDeserialize(data, AnalysisFormGroupDefineImpl.class);
        }
        return null;
    }

    @Override
    public List<FormulaVariDefine> queryAllFormulaVariable(String formSchemeKey) {
        return this.runtimeFormulaVariableService.queryAllFormulaVariable(formSchemeKey);
    }

    @Override
    public FieldDefine queryFieldDefine(String fieldKey) throws Exception {
        FieldDefine fieldDefine = this.iDataDefinitionRuntimeController.queryFieldDefine(fieldKey);
        return fieldDefine;
    }

    @Override
    public List<SchemePeriodLinkDefine> querySchemePeriodLinkByScheme(String scheme) throws Exception {
        return this.iRuntimeFormSchemePeriodService.querySchemePeriodLinkByScheme(scheme);
    }

    @Override
    public List<SchemePeriodLinkDefine> querySchemePeriodLinkBySchemeSort(String scheme) throws Exception {
        return this.iRuntimeFormSchemePeriodService.querySchemePeriodLinkByScheme(scheme);
    }

    @Override
    public List<SchemePeriodLinkDefine> querySchemePeriodLinkByTask(String task) throws Exception {
        List<FormSchemeDefine> formSchemeDefines = this.queryFormSchemeByTask(task);
        ArrayList<SchemePeriodLinkDefine> res = new ArrayList<SchemePeriodLinkDefine>();
        for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
            res.addAll(this.iRuntimeFormSchemePeriodService.querySchemePeriodLinkByScheme(formSchemeDefine.getKey()));
        }
        return res;
    }

    @Override
    public SchemePeriodLinkDefine querySchemePeriodLinkByPeriodAndScheme(String period, String scheme) throws Exception {
        return this.iRuntimeFormSchemePeriodService.querySchemePeriodLinkBySchemeAndPeriod(scheme, period);
    }

    @Override
    public SchemePeriodLinkDefine querySchemePeriodLinkByPeriodAndTask(String period, String task) throws Exception {
        List<FormSchemeDefine> formSchemeDefines = this.queryFormSchemeByTask(task);
        SchemePeriodLinkDefine result = null;
        for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.iRuntimeFormSchemePeriodService.querySchemePeriodLinkBySchemeAndPeriod(formSchemeDefine.getKey(), period);
            if (null == schemePeriodLinkDefine) continue;
            result = schemePeriodLinkDefine;
        }
        return result;
    }

    @Override
    public List<DataLinkMappingDefine> queryDataLinkMapping(String formKey) {
        return this.runtimeDataLinkMappingService.queryDataLinkMappingByFormKey(formKey);
    }

    @Override
    public ReportTemplateDefine getReportTemplate(String key) {
        return this.runtimeReportTemplateService.getReportTemplate(key);
    }

    @Override
    public List<ReportTemplateDefine> getReportTemplateByTask(String taskKey) {
        return this.runtimeReportTemplateService.getReportTemplateByTask(taskKey);
    }

    @Override
    public List<ReportTemplateDefine> getReportTemplateByScheme(String formSchemeKey) {
        return this.runtimeReportTemplateService.getReportTemplateByScheme(formSchemeKey);
    }

    @Override
    public byte[] getReportTemplateFile(String fileKey) {
        return this.runtimeReportTemplateService.getReportTemplateFile(fileKey);
    }

    @Override
    public void getReportTemplateFile(String fileKey, OutputStream outputStream) {
        this.runtimeReportTemplateService.getReportTemplateFile(fileKey, outputStream);
    }

    @Override
    public List<ReportTagDefine> queryAllTagsByRptKey(String rptKey) {
        return this.runtimeReportTagService.queryAllTagsByRptKey(rptKey);
    }

    @Override
    public byte[] injectReplyToRpt(byte[] rptTemp, InjectContext context) {
        return this.injectReplyToRptHelper.injectReplyToRpt(rptTemp, context);
    }

    @Override
    public EntityViewDefine getViewByTaskDefineKey(String taskKey) {
        RunTimeEntityViewDefineImpl entityViewDefine;
        if (taskKey == null) {
            return null;
        }
        TaskDefine task = this.taskService.queryTaskDefine(taskKey);
        if (task == null) {
            throw new IllegalArgumentException("\u672a\u67e5\u8be2\u5230\u4efb\u52a1");
        }
        if (StringUtils.isEmpty((String)task.getFilterExpression())) {
            entityViewDefine = this.filterTemplateService.getFilterTemplate(task.getFilterTemplate());
            if (entityViewDefine != null) {
                entityViewDefine.setFilterRowByAuthority(true);
                return entityViewDefine;
            }
            entityViewDefine = this.buildEntityView(task.getDw(), null, true);
        } else {
            entityViewDefine = this.buildEntityView(task.getDw(), task.getFilterExpression(), true);
        }
        return entityViewDefine;
    }

    @Override
    public EntityViewDefine getViewByFormSchemeKey(String schemeKey) {
        if (schemeKey == null) {
            return null;
        }
        FormSchemeDefine formSchemeDefine = this.getFormScheme(schemeKey);
        if (formSchemeDefine == null) {
            throw new IllegalArgumentException("\u672a\u67e5\u8be2\u5230\u62a5\u8868\u65b9\u6848");
        }
        return this.getViewByTaskDefineKey(formSchemeDefine.getTaskKey());
    }

    @Override
    public EntityViewDefine getViewByLinkDefineKey(String linkKey) {
        DataLinkDefine dataLink = this.dataLinkService.queryDataLink(linkKey);
        if (dataLink == null) {
            throw new IllegalArgumentException("\u672a\u67e5\u8be2\u5230\u94fe\u63a5");
        }
        if (StringUtils.isEmpty((String)dataLink.getFilterExpression())) {
            RunTimeEntityViewDefineImpl entityViewDefine = this.filterTemplateService.getFilterTemplate(dataLink.getFilterTemplate());
            if (entityViewDefine != null) {
                entityViewDefine.setFilterRowByAuthority(!dataLink.isIgnorePermissions());
                return entityViewDefine;
            }
            return this.getLinkViewDefine(dataLink);
        }
        return this.getLinkViewDefine(dataLink);
    }

    private EntityViewDefine getLinkViewDefine(DataLinkDefine dataLink) {
        if (dataLink.getType() == DataLinkType.DATA_LINK_TYPE_FMDM && !StringUtils.isEmpty((String)dataLink.getLinkExpression())) {
            String fmdmEntityId = null;
            DataRegionDefine regionDefine = this.queryDataRegionDefine(dataLink.getRegionKey());
            FormDefine formDefine = this.queryFormById(regionDefine.getFormKey());
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
        FieldDefine fieldDefine = null;
        try {
            fieldDefine = this.iDataDefinitionRuntimeController.queryFieldDefine(dataLink.getLinkExpression());
        }
        catch (Exception e2) {
            return null;
        }
        if (fieldDefine != null) {
            return this.buildEntityView(fieldDefine.getEntityKey(), dataLink.getFilterExpression(), !dataLink.isIgnorePermissions());
        }
        return null;
    }

    private RunTimeEntityViewDefineImpl buildEntityView(String entityID, String filterExpression, boolean filterRowByAuthority) {
        RunTimeEntityViewDefineImpl entityViewDefine = new RunTimeEntityViewDefineImpl();
        entityViewDefine.setEntityId(entityID);
        entityViewDefine.setRowFilterExpression(filterExpression);
        entityViewDefine.setFilterRowByAuthority(filterRowByAuthority);
        return entityViewDefine;
    }

    @Override
    public List<IDimensionFilter> getDimensionFilter(String formSchemeKey) {
        return this.runTimeDimensionFilterService.getByFormSchemeKey(formSchemeKey);
    }

    @Override
    public IDimensionFilter getDimensionFilter(String formSchemeKey, String entityId) {
        return this.runTimeDimensionFilterService.getByFormSchemeAndEntityId(formSchemeKey, entityId);
    }

    @Override
    public List<EntityViewDefine> listDimensionViewsByTask(String taskKey) {
        List<EntityViewDefine> taskEntityViews = new ArrayList<EntityViewDefine>();
        try {
            List<FormSchemeDefine> formSchemes = this.queryFormSchemeByTask(taskKey);
            if (formSchemes != null && !CollectionUtils.isEmpty(formSchemes)) {
                taskEntityViews = this.listDimensionViewsByFormScheme(formSchemes.get(0).getKey());
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (CollectionUtils.isEmpty(taskEntityViews)) {
            return this.buildEmptyDimFilterEntityView(taskKey);
        }
        return taskEntityViews;
    }

    @Override
    public List<EntityViewDefine> listDimensionViewsByFormScheme(String formSchemeKey) {
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
        } else {
            FormSchemeDefine formSchemeDefine = this.getFormScheme(formSchemeKey);
            return this.buildEmptyDimFilterEntityView(formSchemeDefine.getTaskKey());
        }
        return entityViewDefines;
    }

    public List<EntityViewDefine> buildEmptyDimFilterEntityView(String taskKey) {
        TaskDefine task = this.queryTaskDefine(taskKey);
        ArrayList<EntityViewDefine> entityViewDefines = new ArrayList<EntityViewDefine>();
        String dims = task.getDims();
        if (!StringUtils.isEmpty((String)dims)) {
            String[] dimKeys = dims.split(";");
            for (int i = 0; i < dimKeys.length; ++i) {
                RunTimeEntityViewDefineImpl entityViewDefine = new RunTimeEntityViewDefineImpl();
                entityViewDefine.setEntityId(dimKeys[i]);
                entityViewDefines.add((EntityViewDefine)entityViewDefine);
            }
        }
        return entityViewDefines;
    }

    @Override
    public EntityViewDefine getDimensionViewByTaskAndEntity(String taskKey, String entityId) {
        EntityViewDefine entityViewDefine = null;
        try {
            List<FormSchemeDefine> formSchemes = this.queryFormSchemeByTask(taskKey);
            if (formSchemes != null && !CollectionUtils.isEmpty(formSchemes)) {
                entityViewDefine = this.getDimensionViewByFormSchemeAndEntity(formSchemes.get(0).getKey(), entityId);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (entityViewDefine == null) {
            RunTimeEntityViewDefineImpl entityView = new RunTimeEntityViewDefineImpl();
            entityView.setEntityId(entityId);
            return entityView;
        }
        return entityViewDefine;
    }

    @Override
    public EntityViewDefine getDimensionViewByFormSchemeAndEntity(String formSchemeKey, String entityId) {
        IDimensionFilter dimensionFilter = this.runTimeDimensionFilterService.getByFormSchemeAndEntityId(formSchemeKey, entityId);
        RunTimeEntityViewDefineImpl entityViewDefine = new RunTimeEntityViewDefineImpl();
        if (dimensionFilter == null) {
            RunTimeEntityViewDefineImpl entityView = new RunTimeEntityViewDefineImpl();
            entityView.setEntityId(entityId);
            return entityView;
        }
        entityViewDefine.setEntityId(dimensionFilter.getEntityId());
        entityViewDefine.setRowFilterExpression(this.metaServiceProxy.getExpression(dimensionFilter));
        entityViewDefine.setFilterRowByAuthority(true);
        return entityViewDefine;
    }

    @Override
    public List<DataLinkDefine> getDataLinksByField(String fieldKey) throws Exception {
        return this.dataLinkService.getDataLinksByField(fieldKey);
    }

    @Override
    public Collection<String> getFormKeysByField(String dataFieldKey) {
        return this.formFieldInfoService.getFormKeys(dataFieldKey);
    }

    @Override
    public Collection<FormFieldInfoDefine> getFormInfosByField(String dataFieldKey) {
        return this.formFieldInfoService.getFormFieldInfos(dataFieldKey);
    }

    @Override
    public DataRegionDefine getDataRegion(String regionCode, String formKey, String formSchemeKey) {
        return this.dataRegionService.getDataRegion(regionCode, formKey, formSchemeKey);
    }

    @Override
    public List<FormFoldingDefine> listFormFoldingByFormKey(String formKey) {
        return this.formFoldingService.getByFormKey(formKey);
    }

    @Override
    public List<DataLinkDefine> listDataLinkByFormAndLinkExp(String formSchemeKey, String formKey, String linkExpression) {
        return this.dataLinkService.getDataLinkByFormAndLinkExp(formSchemeKey, formKey, linkExpression);
    }

    @Override
    public Set<String> listLinkExpressionByFormKey(String formSchemeKey, String formKey) {
        return this.dataLinkService.listLinkExpressionByFormKey(formSchemeKey, formKey);
    }

    public RuntimeViewController getRuntimeViewController(NrFormParamCacheService service) {
        RuntimeViewController clone = new RuntimeViewController();
        clone.taskGroupService = this.taskGroupService;
        clone.taskService = this.taskService;
        clone.entityLinkageDefineService = this.entityLinkageDefineService;
        clone.iDataDefinitionRuntimeController = this.iDataDefinitionRuntimeController;
        clone.iRuntimeFormSchemePeriodService = this.iRuntimeFormSchemePeriodService;
        clone.runtimeReportTemplateService = this.runtimeReportTemplateService;
        clone.runtimeReportTagService = this.runtimeReportTagService;
        clone.injectReplyToRptHelper = this.injectReplyToRptHelper;
        clone.runTimeDimensionFilterService = this.runTimeDimensionFilterService;
        clone.formFieldInfoService = this.formFieldInfoService;
        clone.metaServiceProxy = this.metaServiceProxy;
        clone.filterTemplateService = this.filterTemplateService;
        clone.entityMetaService = this.entityMetaService;
        clone.formFoldingService = this.formFoldingService;
        clone.formSchemeService = service;
        clone.formGroupService = service;
        clone.formService = service;
        clone.dataRegionService = service;
        clone.dataRegionSettingService = service;
        clone.dataLinkService = service;
        clone.taskLinkService = service;
        clone.runtimeBigDataService = service;
        clone.runtimeFormulaVariableService = service;
        clone.runtimeDataLinkMappingService = service;
        return clone;
    }
}

