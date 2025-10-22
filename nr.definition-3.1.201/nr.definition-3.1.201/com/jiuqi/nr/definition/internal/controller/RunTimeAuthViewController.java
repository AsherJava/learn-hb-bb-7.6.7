/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 */
package com.jiuqi.nr.definition.internal.controller;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.common.DataLinkEditMode;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.EnumLinkageSettingDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.RegionPartitionDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class RunTimeAuthViewController {
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private DefinitionAuthorityProvider authorityProvider;

    public IRunTimeViewController getRuntimeView() {
        return this.runtimeView;
    }

    public DefinitionAuthorityProvider getAuthorityProvider() {
        return this.authorityProvider;
    }

    public TaskDefine queryTaskDefine(String taskKey) throws Exception {
        if (this.authorityProvider.canReadTask(taskKey)) {
            return this.runtimeView.queryTaskDefine(taskKey);
        }
        return null;
    }

    public List<TaskDefine> getAllTaskDefines() {
        List<TaskDefine> allTaskDefines = this.runtimeView.getAllTaskDefines();
        if (allTaskDefines != null && !allTaskDefines.isEmpty()) {
            return allTaskDefines.stream().filter(task -> task != null && this.authorityProvider.canReadTask(task.getKey())).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<TaskDefine> getTaskDefinesFromGroup(String taskGroupKey) {
        List<TaskDefine> taskDefinesFromGroup = this.runtimeView.getAllRunTimeTasksInGroup(taskGroupKey);
        if (taskDefinesFromGroup != null && !taskDefinesFromGroup.isEmpty()) {
            return taskDefinesFromGroup.stream().filter(task -> task != null && this.authorityProvider.canReadTask(task.getKey())).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<TaskDefine> getAllTaskDefinesByType(TaskType type) {
        List<TaskDefine> allTaskDefines = this.runtimeView.getAllTaskDefinesByType(type);
        if (allTaskDefines != null && !allTaskDefines.isEmpty()) {
            return allTaskDefines.stream().filter(task -> task != null && this.authorityProvider.canReadTask(task.getKey())).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<TaskDefine> getAllReportTaskDefines() {
        List<TaskDefine> allTaskDefines = this.runtimeView.getAllReportTaskDefines();
        if (allTaskDefines != null && !allTaskDefines.isEmpty()) {
            return allTaskDefines.stream().filter(task -> task != null && this.authorityProvider.canReadTask(task.getKey())).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public TaskDefine queryTaskDefineByCode(String code) {
        TaskDefine task = this.runtimeView.queryTaskDefineByCode(code);
        if (task != null && this.authorityProvider.canReadTask(task.getKey())) {
            return task;
        }
        return null;
    }

    public TaskDefine queryTaskDefineByFilePrefix(String FilePrefix) {
        TaskDefine task = this.runtimeView.queryTaskDefineByFilePrefix(FilePrefix);
        if (task != null && this.authorityProvider.canReadTask(task.getKey())) {
            return task;
        }
        return null;
    }

    public TaskGroupDefine queryTaskGroupDefine(String taskGroupKey) {
        TaskGroupDefine taskGroupDefine = this.runtimeView.queryTaskGroupDefine(taskGroupKey);
        if (taskGroupDefine != null && this.authorityProvider.canReadTaskGroup(taskGroupDefine.getKey())) {
            return taskGroupDefine;
        }
        return null;
    }

    public List<TaskGroupDefine> getChildTaskGroups(String taskGroupKey, boolean isRecursion) {
        List<TaskGroupDefine> childTaskGroups = this.runtimeView.getChildTaskGroups(taskGroupKey, isRecursion);
        if (childTaskGroups != null && !childTaskGroups.isEmpty()) {
            return childTaskGroups.stream().filter(tashGroup -> tashGroup != null && this.authorityProvider.canReadTaskGroup(tashGroup.getKey())).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<TaskGroupDefine> getGroupByTask(String taskKey) {
        List<TaskGroupDefine> groupByTasks = this.runtimeView.getGroupByTask(taskKey);
        if (groupByTasks != null && !groupByTasks.isEmpty()) {
            return groupByTasks.stream().filter(tashGroup -> tashGroup != null && this.authorityProvider.canReadTaskGroup(tashGroup.getKey())).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private boolean isEntityForm(FormDefine form) {
        return null != form && form.getFormType().getValue() == FormType.FORM_TYPE_ENTITY.getValue();
    }

    private boolean isEntityForm(String formKey) {
        FormDefine form = this.runtimeView.queryFormById(formKey);
        return null != form && form.getFormType().getValue() == FormType.FORM_TYPE_ENTITY.getValue();
    }

    public FormDefine queryFormById(String formKey) {
        FormDefine form = this.runtimeView.queryFormById(formKey);
        if (this.isEntityForm(form) || this.authorityProvider.canReadForm(formKey)) {
            return form;
        }
        return null;
    }

    public FormDefine queryFormById(String formKey, String entityKeyData, String entityId) {
        FormDefine form = this.runtimeView.queryFormById(formKey);
        if (this.isEntityForm(form) || this.authorityProvider.canReadForm(formKey, entityKeyData, entityId)) {
            return form;
        }
        return null;
    }

    public FormDefine queryEntityForm(String formKey) throws Exception {
        FormDefine form = this.runtimeView.queryFormById(formKey);
        if (this.isEntityForm(form) || this.authorityProvider.canReadForm(formKey)) {
            return form;
        }
        return null;
    }

    public FormDefine queryEntityForm(String formKey, String entityKeyData, String entityId) throws Exception {
        FormDefine form = this.runtimeView.queryFormById(formKey);
        if (this.isEntityForm(form) || this.authorityProvider.canReadForm(formKey, entityKeyData, entityId)) {
            return form;
        }
        return null;
    }

    public FormDefine queryFormByCodeInScheme(String formSchemeKey, String formDefineCode) throws Exception {
        FormDefine form = this.runtimeView.queryFormByCodeInScheme(formSchemeKey, formDefineCode);
        if (null != form && (this.isEntityForm(form) || this.authorityProvider.canReadForm(form.getKey()))) {
            return form;
        }
        return null;
    }

    public FormDefine queryFormByCodeInScheme(String formSchemeKey, String formDefineCode, String entityKeyData, String entityId) throws Exception {
        FormDefine form = this.runtimeView.queryFormByCodeInScheme(formSchemeKey, formDefineCode);
        if (null != form && (this.isEntityForm(form) || this.authorityProvider.canReadForm(form.getKey(), entityKeyData, entityId))) {
            return form;
        }
        return null;
    }

    public List<FormDefine> queryAllFormDefinesByTask(String taskKey) {
        List<FormDefine> forms = this.runtimeView.queryAllFormDefinesByTask(taskKey);
        if (forms != null && !forms.isEmpty()) {
            return forms.stream().filter(form -> this.isEntityForm((FormDefine)form) || this.authorityProvider.canReadForm(form.getKey())).collect(Collectors.toList());
        }
        return null;
    }

    public List<FormDefine> queryAllFormDefinesByTask(String taskKey, String entityKeyData, String entityId) {
        List<FormDefine> forms = this.runtimeView.queryAllFormDefinesByTask(taskKey);
        if (forms != null && !forms.isEmpty()) {
            return forms.stream().filter(form -> this.isEntityForm((FormDefine)form) || this.authorityProvider.canReadForm(form.getKey(), entityKeyData, entityId)).collect(Collectors.toList());
        }
        return null;
    }

    public List<FormDefine> queryAllFormDefinesByFormScheme(String formSchemeKey) throws Exception {
        List<FormDefine> forms = this.runtimeView.queryAllFormDefinesByFormScheme(formSchemeKey);
        return this.filterForms(forms);
    }

    public List<FormDefine> queryAllFormDefinesByFormScheme(String formSchemeKey, String entityKeyData, String entityId) throws Exception {
        List<FormDefine> forms = this.runtimeView.queryAllFormDefinesByFormScheme(formSchemeKey);
        if (forms != null && !forms.isEmpty()) {
            return forms.stream().filter(form -> this.isEntityForm((FormDefine)form) || this.authorityProvider.canReadForm(form.getKey(), entityKeyData, entityId)).collect(Collectors.toList());
        }
        return null;
    }

    public List<FormSchemeDefine> queryFormSchemeByTask(String taskKey) throws Exception {
        List<FormSchemeDefine> formSchemes = this.runtimeView.queryFormSchemeByTask(taskKey);
        if (formSchemes != null && !formSchemes.isEmpty()) {
            return formSchemes.stream().filter(scheme -> scheme != null && this.authorityProvider.canReadFormScheme((FormSchemeDefine)scheme)).collect(Collectors.toList());
        }
        return null;
    }

    public FormSchemeDefine getFormScheme(String formSchemeKey) throws Exception {
        if (this.authorityProvider.canReadFormScheme(formSchemeKey)) {
            return this.runtimeView.getFormScheme(formSchemeKey);
        }
        return null;
    }

    public FormGroupDefine queryFormGroup(String formGroupKey) throws Exception {
        if (this.authorityProvider.canReadFormGroup(formGroupKey)) {
            return this.runtimeView.queryFormGroup(formGroupKey);
        }
        return null;
    }

    public FormGroupDefine queryFormGroup(String formGroupKey, String entityKeyData, String entityId) throws Exception {
        if (this.authorityProvider.canReadFormGroup(formGroupKey, entityKeyData, entityId)) {
            return this.runtimeView.queryFormGroup(formGroupKey);
        }
        return null;
    }

    public List<FormGroupDefine> queryRootGroupsByFormScheme(String formSchemeKey) {
        List<FormGroupDefine> formGroups = this.runtimeView.queryRootGroupsByFormScheme(formSchemeKey);
        if (formGroups != null && !formGroups.isEmpty()) {
            return formGroups.stream().filter(group -> group != null && this.authorityProvider.canReadFormGroup(group.getKey())).collect(Collectors.toList());
        }
        return null;
    }

    public List<FormGroupDefine> queryRootGroupsByFormScheme(String formSchemeKey, String entityKeyData, String entityId) {
        List<FormGroupDefine> formGroups = this.runtimeView.queryRootGroupsByFormScheme(formSchemeKey);
        if (formGroups != null && !formGroups.isEmpty()) {
            return formGroups.stream().filter(group -> group != null && this.authorityProvider.canReadFormGroup(group.getKey(), entityKeyData, entityId)).collect(Collectors.toList());
        }
        return null;
    }

    public List<FormGroupDefine> getFormGroupsByFormKey(String formKey) {
        List<FormGroupDefine> formGroups = this.runtimeView.getFormGroupsByFormKey(formKey);
        if (formGroups != null && !formGroups.isEmpty()) {
            return formGroups.stream().filter(group -> group != null && this.authorityProvider.canReadFormGroup(group.getKey())).collect(Collectors.toList());
        }
        return null;
    }

    public List<FormGroupDefine> getFormGroupsByFormKey(String formKey, String entityKeyData, String entityId) {
        List<FormGroupDefine> formGroups = this.runtimeView.getFormGroupsByFormKey(formKey);
        if (formGroups != null && !formGroups.isEmpty()) {
            return formGroups.stream().filter(group -> group != null && this.authorityProvider.canReadFormGroup(group.getKey(), entityKeyData, entityId)).collect(Collectors.toList());
        }
        return null;
    }

    public List<FormGroupDefine> getChildFormGroups(String formGroupKey) {
        List<FormGroupDefine> formGroups = this.runtimeView.getChildFormGroups(formGroupKey);
        if (formGroups != null && !formGroups.isEmpty()) {
            return formGroups.stream().filter(group -> group != null && this.authorityProvider.canReadFormGroup(group.getKey())).collect(Collectors.toList());
        }
        return null;
    }

    public List<FormGroupDefine> getChildFormGroups(String formGroupKey, String entityKeyData, String entityId) {
        List<FormGroupDefine> formGroups = this.runtimeView.getChildFormGroups(formGroupKey);
        if (formGroups != null && !formGroups.isEmpty()) {
            return formGroups.stream().filter(group -> group != null && this.authorityProvider.canReadFormGroup(group.getKey(), entityKeyData, entityId)).collect(Collectors.toList());
        }
        return null;
    }

    public List<FormDefine> getAllFormsInGroup(String formGroupKey) throws Exception {
        List<FormDefine> forms = this.runtimeView.getAllFormsInGroup(formGroupKey);
        return this.filterForms(forms);
    }

    public List<FormDefine> getAllFormsInGroup(String formGroupKey, String entityKeyData, String entityId) throws Exception {
        List<FormDefine> forms = this.runtimeView.getAllFormsInGroup(formGroupKey);
        if (forms != null && !forms.isEmpty()) {
            return forms.stream().filter(form -> this.isEntityForm((FormDefine)form) || this.authorityProvider.canReadForm(form.getKey(), entityKeyData, entityId)).collect(Collectors.toList());
        }
        return null;
    }

    public List<FormDefine> getAllFormsInGroup(String formGroupKey, boolean isRecursion) throws Exception {
        List<FormDefine> forms = this.runtimeView.getAllFormsInGroup(formGroupKey, isRecursion);
        return this.filterForms(forms);
    }

    public List<FormDefine> getAllFormsInGroup(String formGroupKey, boolean isRecursion, String entityKeyData, String entityId) throws Exception {
        List<FormDefine> forms = this.runtimeView.getAllFormsInGroup(formGroupKey, isRecursion);
        if (forms != null && !forms.isEmpty()) {
            return forms.stream().filter(form -> this.isEntityForm((FormDefine)form) || this.authorityProvider.canReadForm(form.getKey(), entityKeyData, entityId)).collect(Collectors.toList());
        }
        return null;
    }

    public int getFormsCountInGroup(String formGroupKey, boolean isRecursion) throws Exception {
        List<FormDefine> forms = this.runtimeView.getAllFormsInGroupWithoutOrder(formGroupKey, isRecursion);
        if (forms != null && !forms.isEmpty()) {
            return (int)forms.stream().filter(form -> this.isEntityForm((FormDefine)form) || this.authorityProvider.canReadForm(form.getKey())).count();
        }
        return 0;
    }

    public int getFormsCountInGroup(String formGroupKey, boolean isRecursion, String entityKeyData, String entityId) throws Exception {
        List<FormDefine> forms = this.runtimeView.getAllFormsInGroupWithoutOrder(formGroupKey, isRecursion);
        if (forms != null && !forms.isEmpty()) {
            return (int)forms.stream().filter(form -> this.isEntityForm((FormDefine)form) || this.authorityProvider.canReadForm(form.getKey(), entityKeyData, entityId)).count();
        }
        return 0;
    }

    public List<FormGroupDefine> getAllGroupsInGroup(String formGroupKey) {
        List<FormGroupDefine> formGroups = this.runtimeView.getAllGroupsInGroup(formGroupKey);
        if (formGroups != null && !formGroups.isEmpty()) {
            return formGroups.stream().filter(group -> group != null && this.authorityProvider.canReadFormGroup(group.getKey())).collect(Collectors.toList());
        }
        return null;
    }

    public List<FormGroupDefine> getAllGroupsInGroup(String formGroupKey, String entityKeyData, String entityId) {
        List<FormGroupDefine> formGroups = this.runtimeView.getAllGroupsInGroup(formGroupKey);
        if (formGroups != null && !formGroups.isEmpty()) {
            return formGroups.stream().filter(group -> group != null && this.authorityProvider.canReadFormGroup(group.getKey(), entityKeyData, entityId)).collect(Collectors.toList());
        }
        return null;
    }

    public List<FormGroupDefine> getAllFormGroupsInFormScheme(String formSchemeKey) {
        List<FormGroupDefine> formGroups = this.runtimeView.getAllFormGroupsInFormScheme(formSchemeKey);
        return this.filterFormGroups(formGroups);
    }

    public List<FormGroupDefine> getAllFormGroupsInFormScheme(String formSchemeKey, String entityKeyData, String entityId) {
        List<FormGroupDefine> formGroups = this.runtimeView.getAllFormGroupsInFormScheme(formSchemeKey);
        if (formGroups != null && !formGroups.isEmpty()) {
            return formGroups.stream().filter(group -> group != null && this.authorityProvider.canReadFormGroup(group.getKey(), entityKeyData, entityId)).collect(Collectors.toList());
        }
        return null;
    }

    public DataRegionDefine queryDataRegionDefine(String dataRegionKey) {
        return this.runtimeView.queryDataRegionDefine(dataRegionKey);
    }

    public List<DataRegionDefine> getAllRegionsInForm(String formKey) {
        return this.runtimeView.getAllRegionsInForm(formKey);
    }

    public RegionSettingDefine getRegionSetting(String dataRegionKey) {
        return this.runtimeView.getRegionSetting(dataRegionKey);
    }

    @Deprecated
    public List<RegionPartitionDefine> getRegionPartitionDefines(String dataRegionKey) {
        return this.runtimeView.getRegionPartitionDefines(dataRegionKey);
    }

    public DataLinkDefine queryDataLinkDefine(String dataLinkKey) {
        return this.runtimeView.queryDataLinkDefine(dataLinkKey);
    }

    public List<DataLinkDefine> getAllLinksInForm(String formKey) {
        return this.runtimeView.getAllLinksInForm(formKey);
    }

    @Deprecated
    public List<String> getAllDimensionsInForm(String formKey) {
        return this.runtimeView.getAllDimensionsInForm(formKey);
    }

    @Deprecated
    public List<DataLinkDefine> getAllLinksInFormFilterBySelectorViewId(String formKey, DataLinkEditMode dataLinkEditMode) {
        return this.runtimeView.getAllLinksInFormFilterBySelectorViewId(formKey, dataLinkEditMode);
    }

    public List<DataLinkDefine> getAllLinksInRegion(String dataRegionKey) {
        return this.runtimeView.getAllLinksInRegion(dataRegionKey);
    }

    public List<String> getAllFieldsByLinksInRegion(String dataRegionKey) throws Exception {
        return this.runtimeView.getFieldKeysInRegion(dataRegionKey);
    }

    public DataLinkDefine queryDataLinkDefineByXY(String formKey, int posX, int posY) {
        return this.runtimeView.queryDataLinkDefineByXY(formKey, posX, posY);
    }

    public DataLinkDefine queryDataLinkDefineByColRow(String formKey, int col, int row) {
        return this.runtimeView.queryDataLinkDefineByColRow(formKey, col, row);
    }

    public DataLinkDefine queryDataLinkDefineByUniquecode(String formKey, String uniqueCode) {
        return this.runtimeView.queryDataLinkDefineByUniquecode(formKey, uniqueCode);
    }

    @Deprecated
    public EnumLinkageSettingDefine getEnumLinkage(String dataLinkKey) {
        return this.runtimeView.getEnumLinkage(dataLinkKey);
    }

    @Deprecated
    public List<EnumLinkageSettingDefine> getEnumLinkages(String[] dataLinkKeys) {
        return this.runtimeView.getEnumLinkages(dataLinkKeys);
    }

    public void initFormCache(String formKey) {
        this.runtimeView.initFormCache(formKey);
    }

    public void initTask(String taskKey) throws Exception {
        this.runtimeView.initTask(taskKey);
    }

    @Deprecated
    public List<TaskLinkDefine> queryLinksByCurrentTask(String currentTaskKey) {
        return this.runtimeView.queryLinksByCurrentTask(currentTaskKey);
    }

    @Deprecated
    public TaskLinkDefine queryLinkByCurrentTaskAndNum(String currentTaskKey, String serialNumber) {
        return this.runtimeView.queryLinkByCurrentTaskAndNum(currentTaskKey, serialNumber);
    }

    public void initCache() throws Exception {
        this.runtimeView.initCache();
    }

    @Deprecated
    public FormDefine getLinkTaskForm(String formSchemeKey, String linkName, String formCode) {
        FormDefine form = this.runtimeView.getLinkTaskForm(formSchemeKey, linkName, formCode);
        if (this.isEntityForm(form) || this.authorityProvider.canReadForm(form.getKey())) {
            return form;
        }
        return null;
    }

    public FormDefine getLinkTaskForm(String formSchemeKey, String linkName, String formCode, String entityKeyData, String entityId) {
        FormDefine form = this.runtimeView.getLinkTaskForm(formSchemeKey, linkName, formCode);
        if (this.isEntityForm(form) || this.authorityProvider.canReadForm(form.getKey(), entityKeyData, entityId)) {
            return form;
        }
        return null;
    }

    public List<DataLinkDefine> getLinksInFormByField(String formKey, String fieldKey) {
        return this.runtimeView.getLinksInFormByField(formKey, fieldKey);
    }

    public List<DataLinkDefine> getLinksInRegionByField(String regionKey, String fieldKey) {
        return this.runtimeView.getLinksInRegionByField(regionKey, fieldKey);
    }

    public void initEntityFormCache(String formKey) throws Exception {
        this.runtimeView.initEntityFormCache(formKey);
    }

    public String getFormSchemeEntity(String formSchemeKey) throws JQException {
        if (this.authorityProvider.canReadFormScheme(formSchemeKey)) {
            return this.runtimeView.getFormSchemeEntity(formSchemeKey);
        }
        return null;
    }

    public String getFormEntity(String formSchemeKey, String formKey) throws JQException {
        if (this.isEntityForm(formKey) || this.authorityProvider.canReadForm(formKey)) {
            this.runtimeView.getFormEntity(formSchemeKey, formKey);
        }
        return null;
    }

    public String getFormEntity(String formSchemeKey, String formKey, String entityKeyData, String entityId) throws JQException {
        if (this.isEntityForm(formKey) || this.authorityProvider.canReadForm(formKey, entityKeyData, entityId)) {
            this.runtimeView.getFormEntity(formSchemeKey, formKey);
        }
        return null;
    }

    public FormSchemeDefine getFormschemeByCode(String code) {
        FormSchemeDefine formscheme = this.runtimeView.getFormschemeByCode(code);
        if (formscheme != null && this.authorityProvider.canReadFormScheme(formscheme.getKey())) {
            return formscheme;
        }
        return null;
    }

    public List<String> getFieldKeysInRegion(String dataRegionKey) {
        return this.runtimeView.getFieldKeysInRegion(dataRegionKey);
    }

    public List<String> getFieldKeysInForm(String formKey) {
        if (this.isEntityForm(formKey) || this.authorityProvider.canReadForm(formKey)) {
            return this.runtimeView.getFieldKeysInForm(formKey);
        }
        return Collections.emptyList();
    }

    public List<String> getFieldKeysInForm(String formKey, String entityKeyData, String entityId) {
        if (this.isEntityForm(formKey) || this.authorityProvider.canReadForm(formKey, entityKeyData, entityId)) {
            return this.runtimeView.getFieldKeysInForm(formKey);
        }
        return Collections.emptyList();
    }

    public BigDataDefine getReportDataFromForm(String formKey) {
        if (this.isEntityForm(formKey) || this.authorityProvider.canReadForm(formKey)) {
            return this.runtimeView.getReportDataFromForm(formKey);
        }
        return null;
    }

    public BigDataDefine getReportDataFromForm(String formKey, String entityKeyData, String entityId) {
        if (this.isEntityForm(formKey) || this.authorityProvider.canReadForm(formKey, entityKeyData, entityId)) {
            return this.runtimeView.getReportDataFromForm(formKey);
        }
        return null;
    }

    public String getSurveyDataFromForm(String formKey) {
        if (this.isEntityForm(formKey) || this.authorityProvider.canReadForm(formKey)) {
            return this.runtimeView.getSurveyDataFromForm(formKey);
        }
        return null;
    }

    public String getSurveyDataFromForm(String formKey, String entityKeyData, String entityId) {
        if (this.isEntityForm(formKey) || this.authorityProvider.canReadForm(formKey, entityKeyData, entityId)) {
            return this.runtimeView.getSurveyDataFromForm(formKey);
        }
        return null;
    }

    public String getFrontScriptFromForm(String formKey) {
        if (this.isEntityForm(formKey) || this.authorityProvider.canReadForm(formKey)) {
            return this.runtimeView.getFrontScriptFromForm(formKey);
        }
        return null;
    }

    public String getFrontScriptFromForm(String formKey, String entityKeyData, String entityId) {
        if (this.isEntityForm(formKey) || this.authorityProvider.canReadForm(formKey, entityKeyData, entityId)) {
            return this.runtimeView.getFrontScriptFromForm(formKey);
        }
        return null;
    }

    public List<FormSchemeDefine> queryFormSchemeByEntity(String entityKey) throws JQException {
        List<FormSchemeDefine> schemes = this.runtimeView.queryFormSchemeByEntity(entityKey);
        if (schemes != null && !schemes.isEmpty()) {
            return schemes.stream().filter(scheme -> scheme != null && this.authorityProvider.canReadFormScheme(scheme.getKey())).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<FormDefine> getAllFormsInGroupWithoutOrder(String formGroupKey) throws Exception {
        List<FormDefine> forms = this.runtimeView.getAllFormsInGroupWithoutOrder(formGroupKey);
        return this.filterForms(forms);
    }

    public List<FormDefine> getAllFormsInGroupWithoutOrder(String formGroupKey, String entityKeyData, String entityId) throws Exception {
        List<FormDefine> forms = this.runtimeView.getAllFormsInGroupWithoutOrder(formGroupKey);
        if (forms != null && !forms.isEmpty()) {
            return forms.stream().filter(form -> this.isEntityForm((FormDefine)form) || this.authorityProvider.canReadForm(form.getKey(), entityKeyData, entityId)).collect(Collectors.toList());
        }
        return null;
    }

    public List<FormDefine> getAllFormsInGroupWithoutOrder(String formGroupKey, boolean isRecursion) throws Exception {
        List<FormDefine> forms = this.runtimeView.getAllFormsInGroupWithoutOrder(formGroupKey, isRecursion);
        return this.filterForms(forms);
    }

    public List<FormDefine> getAllFormsInGroupWithoutOrder(String formGroupKey, boolean isRecursion, String entityKeyData, String entityId) throws Exception {
        List<FormDefine> forms = this.runtimeView.getAllFormsInGroupWithoutOrder(formGroupKey, isRecursion);
        if (forms != null && !forms.isEmpty()) {
            return forms.stream().filter(form -> this.isEntityForm((FormDefine)form) || this.authorityProvider.canReadForm(form.getKey(), entityKeyData, entityId)).collect(Collectors.toList());
        }
        return null;
    }

    private List<FormDefine> filterForms(List<FormDefine> formDefines) {
        if (!CollectionUtils.isEmpty(formDefines)) {
            List<String> formKeys = formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            List entityForm = formDefines.stream().filter(form -> this.isEntityForm((FormDefine)form)).map(IBaseMetaItem::getKey).collect(Collectors.toList());
            Set<String> hasAuthForm = this.authorityProvider.canReadForms(formKeys);
            hasAuthForm.addAll(entityForm);
            ArrayList<FormDefine> authFormDefines = new ArrayList<FormDefine>();
            for (FormDefine formDefine : formDefines) {
                if (!hasAuthForm.contains(formDefine.getKey())) continue;
                authFormDefines.add(formDefine);
            }
            return authFormDefines;
        }
        return null;
    }

    private List<FormGroupDefine> filterFormGroups(List<FormGroupDefine> formGroupDefines) {
        if (!CollectionUtils.isEmpty(formGroupDefines)) {
            List<String> formGroupKeys = formGroupDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            Set<String> hasAuthFormGroup = this.authorityProvider.canReadFormGroups(formGroupKeys);
            ArrayList<FormGroupDefine> authFormGroupDefines = new ArrayList<FormGroupDefine>();
            for (FormGroupDefine formGroupDefine : formGroupDefines) {
                if (!hasAuthFormGroup.contains(formGroupDefine.getKey())) continue;
                authFormGroupDefines.add(formGroupDefine);
            }
            return authFormGroupDefines;
        }
        return null;
    }
}

