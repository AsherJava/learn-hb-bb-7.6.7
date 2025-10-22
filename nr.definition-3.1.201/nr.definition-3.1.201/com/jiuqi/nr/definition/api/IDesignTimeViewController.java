/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.definition.api;

import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.facade.DesignAnalysisFormParamDefine;
import com.jiuqi.nr.definition.facade.DesignAnalysisSchemeParamDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkMappingDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignDimensionFilter;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormFoldingDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignRegionSettingDefine;
import com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.DesignTaskLinkDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink;
import com.jiuqi.nr.definition.util.AttachmentObj;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IDesignTimeViewController {
    public DesignTaskDefine initTask();

    public void insertTask(DesignTaskDefine var1);

    public void updateTask(DesignTaskDefine var1);

    public void deleteTask(String[] var1);

    public DesignTaskDefine getTask(String var1);

    public DesignTaskDefine getTaskByCode(String var1);

    public DesignTaskDefine getTaskByFilePrefix(String var1);

    public DesignTaskDefine getTaskByTitle(String var1);

    public List<DesignTaskDefine> listAllTask();

    public List<DesignTaskDefine> listTaskByType(TaskType var1);

    public List<DesignTaskDefine> listTaskByTaskGroup(String var1);

    public List<DesignTaskDefine> listTaskByDataScheme(String var1);

    public void insertTaskFlows(String var1, DesignTaskFlowsDefine var2);

    public void updateTaskFlows(String var1, DesignTaskFlowsDefine var2);

    public void deleteTaskFlows(String var1);

    public DesignTaskGroupDefine initTaskGroup();

    public void insertTaskGroup(DesignTaskGroupDefine var1);

    public void updateTaskGroup(DesignTaskGroupDefine var1);

    public void deleteTaskGroup(String[] var1);

    public DesignTaskGroupDefine getTaskGroup(String var1);

    public List<DesignTaskGroupDefine> listAllTaskGroup();

    public List<DesignTaskGroupDefine> listTaskGroupByTask(String var1);

    public List<DesignTaskGroupDefine> listTaskGroupByParentGroup(String var1);

    public DesignTaskGroupLink initTaskGroupLink();

    public void insertTaskGroupLink(DesignTaskGroupLink[] var1);

    public void updateTaskGroupLink(DesignTaskGroupLink[] var1);

    public void deleteTaskGroupLink(DesignTaskGroupLink[] var1);

    public DesignFormSchemeDefine initFormScheme();

    public void insertFormScheme(DesignFormSchemeDefine var1);

    public void updateFormScheme(DesignFormSchemeDefine var1);

    public void deleteFormScheme(String[] var1);

    public List<DesignFormSchemeDefine> listFormScheme(Collection<String> var1);

    public List<DesignFormSchemeDefine> listFormSchemeByTask(String var1);

    public DesignFormSchemeDefine getFormScheme(String var1);

    public DesignFormSchemeDefine getFormSchemeByFilePrefix(String var1);

    public DesignFormSchemeDefine getFormSchemeByTaskPrefix(String var1);

    public DesignFormSchemeDefine getFormSchemeByCode(String var1);

    public List<DesignFormSchemeDefine> listAllFormScheme();

    public DesignAnalysisSchemeParamDefine getAnalysisSchemeParamDefine(String var1);

    public void updateAnalysisSchemeParamDefine(String var1, DesignAnalysisSchemeParamDefine var2);

    public void deleteAnalysisSchemeParamDefine(String var1);

    public boolean enableAnalysisScheme(String var1);

    public DesignFormGroupDefine initFormGroup();

    public void insertFormGroup(DesignFormGroupDefine var1);

    public void insertFormGroups(DesignFormGroupDefine[] var1);

    public void updateFormGroup(DesignFormGroupDefine var1);

    public void deleteFormGroup(String[] var1);

    public DesignFormGroupDefine getFormGroup(String var1);

    public List<DesignFormGroupDefine> listFormGroupByFormSchemeAndTitle(String var1, String var2);

    public List<DesignFormGroupDefine> listFormGroupByFormScheme(String var1);

    public List<DesignFormGroupDefine> listFormGroupByForm(String var1);

    public List<DesignFormGroupDefine> listAllFormGroupDefine();

    public DesignFormDefine initForm();

    public void insertForm(DesignFormDefine var1);

    public void insertForms(DesignFormDefine[] var1);

    public void updateForm(DesignFormDefine var1);

    public void updateFormTime(String var1);

    public void batchUpdateFormTime(String ... var1);

    public void deleteForm(String[] var1);

    public void deleteFormDefine(String var1);

    public List<DesignFormDefine> listFormByGroup(String var1);

    public DesignFormDefine getForm(String var1);

    public DesignFormDefine getFormByFormSchemeAndCode(String var1, String var2);

    public List<DesignFormDefine> listForm(Collection<String> var1);

    public List<DesignFormDefine> listFormByFormSchemeAndType(String var1, FormType var2);

    public List<DesignFormDefine> listFormByFormScheme(String var1);

    public List<DesignFormDefine> listAllFormDefine();

    public void insertFormStyle(String var1, Grid2Data var2);

    public void updateFormStyle(String var1, Grid2Data var2);

    public Grid2Data getFormStyle(String var1);

    public Grid2Data getFormStyle(String var1, int var2);

    public void deleteFormStyle(String var1);

    public void insertSurveyData(String var1, byte[] var2);

    public void updateSurveyData(String var1, byte[] var2);

    public void deleteSurveyData(String var1);

    public byte[] getSurveyData(String var1);

    public DesignAnalysisFormParamDefine getAnalysisData(String var1);

    public void updataAnalysisData(String var1, DesignAnalysisFormParamDefine var2);

    public void deleteAnalysisData(String var1);

    public void insertAnalysisData(String var1, DesignAnalysisFormParamDefine var2);

    public DesignFormGroupLink initFormGroupLink();

    public void insertFormGroupLink(DesignFormGroupLink[] var1);

    public void updateFormGroupLink(DesignFormGroupLink[] var1);

    public void deleteFormGroupLink(DesignFormGroupLink[] var1);

    public List<DesignFormGroupLink> listFormGroupLinkByForm(String var1);

    public DesignDataRegionDefine initDataRegion();

    public void insertDataRegion(DesignDataRegionDefine[] var1);

    public void updateDataRegion(DesignDataRegionDefine[] var1);

    public void deleteDataRegion(String[] var1);

    public List<DesignDataRegionDefine> listAllDataRegion();

    public List<DesignDataRegionDefine> listDataRegionByForm(String var1);

    public DesignDataRegionDefine getDataRegion(String var1);

    public DesignDataRegionDefine getDataRegion(String var1, String var2);

    public DesignRegionSettingDefine initRegionSetting();

    public void insertRegionSetting(DesignRegionSettingDefine var1);

    public void updateRegionSetting(DesignRegionSettingDefine var1);

    public void deleteRegionSetting(String var1);

    public DesignRegionSettingDefine getRegionSettingByRegion(String var1);

    public List<DesignRegionSettingDefine> listRegionSetting(List<String> var1);

    public DesignDataLinkDefine initDataLink();

    public void insertDataLink(DesignDataLinkDefine[] var1);

    public void updateDataLink(DesignDataLinkDefine[] var1);

    public void deleteDataLink(String[] var1);

    public void deleteDataLinkByDataRegion(String var1);

    public DesignDataLinkDefine getDataLink(String var1);

    public DesignDataLinkDefine getDataLinkByFormAndPos(String var1, int var2, int var3);

    public DesignDataLinkDefine getDataLinkByFormAndColRow(String var1, int var2, int var3);

    public List<DesignDataLinkDefine> listDataLinkByForm(String var1);

    public List<DesignDataLinkDefine> listDataLinkByDataRegion(String var1);

    public DesignDataLinkDefine getDataLinkByUniqueCode(String var1, String var2);

    public List<DesignDataLinkDefine> listDataLinkByFormAndFieldKey(String var1, String var2);

    public List<String> listFieldKeyByDataRegion(String var1);

    public List<String> listFieldKeyByForm(String var1);

    public void insertAttachment(String var1, AttachmentObj var2);

    public void updateAttachment(String var1, AttachmentObj var2);

    public void deleteAttachment(String var1);

    public AttachmentObj getAttachment(String var1);

    public DesignTaskLinkDefine initTaskLink();

    public void insertTaskLink(DesignTaskLinkDefine[] var1);

    public void updateTaskLink(DesignTaskLinkDefine[] var1);

    public void deleteTaskLink(String[] var1);

    public void deleteTaskLinkByFormScheme(String var1);

    public DesignTaskLinkDefine getTaskLink(String var1);

    public List<DesignTaskLinkDefine> listTaskLinkByFormScheme(String var1);

    public DesignTaskLinkDefine getTaskLinkByFormSchemeAndAlias(String var1, String var2);

    public DesignDimensionFilter initIDimensionFilter();

    public void insertIDimensionFilter(DesignDimensionFilter[] var1);

    public void updateIDimensionFilter(DesignDimensionFilter[] var1);

    public void deleteIDimensionFilterByTask(String var1);

    public DesignDimensionFilter getDimensionFilterByTaskAndEntity(String var1, String var2);

    public List<DesignDimensionFilter> listDimensionFilterByTask(String var1);

    public DesignDataLinkMappingDefine initDataLinkMapping();

    public void insertDataLinkMapping(String var1, DesignDataLinkMappingDefine[] var2);

    public void insertDataLinkMapping(DesignDataLinkMappingDefine[] var1);

    public void deleteDataLinkMappingByForm(String var1);

    public List<DesignDataLinkMappingDefine> listDataLinkMappingByForm(String var1);

    public DesignSchemePeriodLinkDefine initSchemePeriodLink();

    public void insertSchemePeriodLink(DesignSchemePeriodLinkDefine[] var1);

    public void deleteSchemePeriodLinkByFormScheme(String var1);

    public void deleteSchemePeriodLinkByTask(String var1);

    public List<DesignSchemePeriodLinkDefine> listSchemePeriodLinkByFormScheme(String var1);

    public List<DesignSchemePeriodLinkDefine> listSchemePeriodLinkByTask(String var1);

    public DesignSchemePeriodLinkDefine getSchemePeriodLinkByPeriodAndFormScheme(String var1, String var2);

    public DesignSchemePeriodLinkDefine getSchemePeriodLinkByPeriodAndTask(String var1, String var2);

    public void insertFormFolding(DesignFormFoldingDefine[] var1);

    public void deleteFormFolding(String[] var1);

    public void deleteFormFoldingByFormKey(String var1);

    public DesignFormFoldingDefine getFormFoldingByKey(String var1);

    public List<DesignFormFoldingDefine> listFormFoldingByFormKey(String var1);

    public void insertTaskOrgLink(TaskOrgLinkDefine[] var1);

    public void deleteTaskOrgLink(String[] var1);

    public void deleteTaskOrgLinkByTask(String var1);

    public void updateTaskOrgLink(TaskOrgLinkDefine[] var1);

    public TaskOrgLinkDefine getTaskOrgLinkByKey(String var1);

    public List<TaskOrgLinkDefine> listTaskOrgLinkByTask(String var1);

    public TaskOrgLinkDefine getTaskOrgLinkByTaskAndEntity(String var1, String var2);

    public List<String> listDataTableByForm(Set<String> var1);

    public List<DesignDataLinkDefine> getReferencedDataLinkByFields(List<String> var1);
}

