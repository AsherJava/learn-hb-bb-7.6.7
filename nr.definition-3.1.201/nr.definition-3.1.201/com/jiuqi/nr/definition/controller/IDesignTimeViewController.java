/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 */
package com.jiuqi.nr.definition.controller;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.datalinkmapping.vo.DataLinkMappingVO;
import com.jiuqi.nr.definition.facade.DesignAnalysisFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignAnalysisFormParamDefine;
import com.jiuqi.nr.definition.facade.DesignAnalysisSchemeParamDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkMappingDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignDimensionFilter;
import com.jiuqi.nr.definition.facade.DesignEntityLinkageDefine;
import com.jiuqi.nr.definition.facade.DesignEnumLinkageSettingDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignRegionSettingDefine;
import com.jiuqi.nr.definition.facade.DesignReportTagDefine;
import com.jiuqi.nr.definition.facade.DesignReportTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.DesignTaskLinkDefine;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.facade.report.TransformReportDefine;
import com.jiuqi.nr.definition.internal.impl.DesignCaliberGroupLink;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface IDesignTimeViewController {
    public DesignTaskDefine createTaskDefine();

    public String insertTaskDefine(DesignTaskDefine var1) throws JQException;

    public void updateTaskDefine(DesignTaskDefine var1) throws JQException;

    public void deleteTaskDefine(String var1) throws JQException;

    @Deprecated
    public void deleteTaskDefine(String var1, boolean var2) throws JQException;

    public DesignTaskDefine queryTaskDefine(String var1);

    public DesignTaskDefine queryTaskDefineByCode(String var1);

    public DesignTaskDefine queryTaskDefineByFilePrefix(String var1);

    public DesignTaskDefine queryTaskDefineByTaskTitle(String var1);

    public List<DesignTaskDefine> getAllTaskDefines();

    public List<DesignTaskDefine> getAllReportTaskDefines();

    public List<DesignTaskDefine> getAllTaskDefinesByType(TaskType var1);

    public int countTask();

    public DesignTaskGroupDefine createTaskGroup();

    public String insertTaskGroupDefine(DesignTaskGroupDefine var1);

    public void updateTaskGroupDefine(DesignTaskGroupDefine var1);

    public void deleteTaskGroupDefine(String var1);

    public DesignTaskGroupDefine queryTaskGroupDefine(String var1);

    public List<DesignTaskGroupDefine> getChildTaskGroups(String var1, boolean var2);

    public List<DesignTaskGroupDefine> getAllTaskGroup();

    public List<DesignTaskGroupDefine> getGroupByTask(String var1);

    public List<DesignTaskDefine> getAllTasksInGroup(String var1, boolean var2);

    public void exchangeTaskGroup(String var1, String var2);

    public void addTaskToGroup(String var1, String var2);

    public void removeTaskFromGroup(String var1, String var2);

    public DesignFormSchemeDefine createFormSchemeDefine();

    public String insertFormSchemeDefine(DesignFormSchemeDefine var1);

    public void updateFormSchemeDefine(DesignFormSchemeDefine var1);

    public void deleteFormSchemeDefine(String var1) throws JQException;

    @Deprecated
    public void deleteFormSchemeDefine(String var1, boolean var2) throws JQException;

    public List<DesignFormSchemeDefine> queryFormSchemeByTask(String var1) throws JQException;

    public DesignFormSchemeDefine queryFormSchemeDefine(String var1);

    public DesignFormSchemeDefine queryFormSchemeDefineByFilePrefix(String var1);

    public DesignFormSchemeDefine queryFormSchemeDefineByTaskPrefix(String var1);

    public DesignFormDefine createFormDefine();

    public String insertFormDefine(DesignFormDefine var1) throws JQException;

    public String insertFormDefine(DesignFormDefine var1, int var2) throws JQException;

    @Deprecated
    public int addNewFormDefine(DesignFormDefine var1, String var2) throws JQException;

    public void updateFormDefine(DesignFormDefine var1);

    @Deprecated
    public void updateFormDefine(DesignFormDefine var1, int var2);

    public void deleteFormDefine(String var1) throws Exception;

    @Deprecated
    public void deleteFormDefine(String var1, boolean var2) throws Exception;

    @Deprecated
    public void deleteFormDefine(String var1, boolean var2, boolean var3, boolean var4) throws Exception;

    @Deprecated
    public void deleteAllFromDefines();

    @Deprecated
    public DesignFormDefine queryFormById(String var1);

    public DesignFormDefine queryFormAndExtAttribute(String var1, int var2);

    @Deprecated
    public DesignFormDefine queryFormByIdWithoutFormData(String var1);

    @Deprecated
    public DesignFormDefine queryFormByLanguageType(String var1, int var2);

    @Deprecated
    public DesignFormDefine queryFormByCodeInFormScheme(String var1, String var2);

    public DesignFormDefine querySoftFormDefineByCodeInFormScheme(String var1, String var2);

    public List<DesignFormDefine> queryFormsByTypeInScheme(String var1, FormType var2) throws JQException;

    @Deprecated
    public List<DesignFormDefine> queryAllFormDefinesByTask(String var1);

    public List<DesignFormDefine> queryAllSoftFormDefinesByTask(String var1);

    @Deprecated
    public List<DesignFormDefine> queryAllFormDefinesByFormScheme(String var1);

    public List<DesignFormDefine> queryAllSoftFormDefinesByFormScheme(String var1);

    @Deprecated
    public List<DesignFormDefine> queryAllFormDefines();

    public void setReportDataToForm(String var1, byte[] var2);

    public void setReportDataToFormByLanguage(String var1, byte[] var2, int var3);

    public byte[] getFillGuide(String var1);

    public void setFillGuide(String var1, byte[] var2);

    public byte[] getFrontScript(String var1);

    public void setFrontScript(String var1, byte[] var2);

    public byte[] getSurveyData(String var1);

    public void setSurveyData(String var1, byte[] var2);

    public byte[] getRegionSurveyData(String var1);

    public void setRegionSurveyData(String var1, byte[] var2);

    public void deleteRegionSurveyData(String var1);

    public byte[] getReportDataFromForm(String var1, int var2);

    public Map<Integer, byte[]> getReportDataFromForms(String var1);

    @Deprecated
    public List<DesignFormDefine> getAllFormDefinesWithoutBinaryData();

    @Deprecated
    public List<DesignFormDefine> getAllFormDefinesInTaskWithoutBinaryData(String var1);

    @Deprecated
    public List<DesignFormDefine> getAllFormDefinesInFormSchemeWithoutBinaryData(String var1);

    @Deprecated
    public List<DesignFormDefine> getAllFormsInGroupWithoutBinaryData(String var1);

    public List<DesignFormDefine> queryAllSoftFormDefinesInGroup(String var1);

    @Deprecated
    public DesignFormDefine queryFormDefineByCodeInFormSchemeWithoutBinaryData(String var1, String var2) throws JQException;

    @Deprecated
    public List<DesignFormDefine> queryFormDefineByCodeWithoutBinaryData(String var1);

    @Deprecated
    public DesignFormDefine queryFormDefineByIdWithoutBinaryData(String var1);

    public DesignFormDefine querySoftFormDefine(String var1);

    public DesignFormGroupDefine createFormGroup();

    public String insertFormGroup(DesignFormGroupDefine var1);

    public int addNewFormGroupToScheme(DesignFormGroupDefine var1, String var2);

    public void updateFormGroup(DesignFormGroupDefine var1);

    public void deleteFormGroup(String var1);

    @Deprecated
    public void deleteFormGroup(String var1, boolean var2);

    public DesignFormGroupDefine queryFormGroup(String var1);

    public List<DesignFormGroupDefine> queryFormGroupByTitleInFormScheme(String var1, String var2);

    public List<DesignFormGroupDefine> queryRootGroupsByFormScheme(String var1);

    public List<DesignFormGroupDefine> getFormGroupsByFormId(String var1);

    public List<DesignFormGroupLink> getFormGroupLinksByFormId(String var1);

    public List<DesignFormGroupLink> getFormGroupLinksByGroupId(String var1);

    public DesignFormGroupLink getFormGroupLinksByFormIdAndGroupId(String var1, String var2) throws Exception;

    public void updateDesignFormGroupLink(DesignFormGroupLink var1) throws Exception;

    @Deprecated
    public List<DesignFormGroupDefine> getChildFormGroups(String var1);

    public void addFormToGroup(String var1, String var2);

    public void removeFormFromGroup(String var1, String var2);

    @Deprecated
    public List<DesignFormDefine> getAllFormsInGroup(String var1, boolean var2);

    @Deprecated
    public List<DesignFormDefine> getAllFormsInGroupLazy(String var1, boolean var2);

    @Deprecated
    public List<DesignFormDefine> getAllFormsInGroupWithoutBinaryData(String var1, boolean var2);

    public void exchangeFormGroup(String var1, String var2);

    public List<DesignFormGroupDefine> getAllFormGroups();

    public int countAllForm();

    public DesignDataRegionDefine createDataRegionDefine();

    public String insertDataRegionDefine(DesignDataRegionDefine var1);

    public void updateDataRegionDefine(DesignDataRegionDefine var1);

    @Deprecated
    public void deleteDataRegionDefine(String var1, boolean var2) throws Exception;

    public void deleteDataRegionDefine(String var1) throws Exception;

    public DesignDataRegionDefine queryDataRegionDefine(String var1);

    public List<DesignDataRegionDefine> getAllRegions();

    public DesignDataRegionDefine getDataRegion(String var1, String var2);

    public List<DesignDataRegionDefine> getAllRegionsInForm(String var1);

    public List<String> insertDataRegionDefines(DesignDataRegionDefine[] var1);

    public void updateDataRegionDefines(DesignDataRegionDefine[] var1);

    public void deleteDataRegionDefines(String[] var1, boolean var2);

    public List<DesignDataRegionDefine> createDataRegionDefines(int var1);

    public DesignRegionSettingDefine createRegionSetting();

    public String addRegionSetting(DesignRegionSettingDefine var1);

    public DesignRegionSettingDefine getRegionSetting(String var1);

    public void updateRegionSetting(DesignRegionSettingDefine var1);

    public void removeRegionSetting(String var1);

    public DesignDataLinkDefine createDataLinkDefine();

    public String insertDataLinkDefine(DesignDataLinkDefine var1);

    public void updateDataLinkDefine(DesignDataLinkDefine var1);

    public void deleteDataLinkDefine(String var1);

    public void deleteDataLinkByRegionId(String var1) throws Exception;

    public DesignDataLinkDefine queryDataLinkDefine(String var1);

    public List<DesignDataLinkDefine> getAllLinksInForm(String var1);

    public List<DesignDataLinkDefine> getAllLinksInRegion(String var1);

    public List<DesignFieldDefine> getAllFieldsByLinksInRegion(String var1) throws JQException;

    public List<DesignFieldDefine> getAllFieldsByLinksInForm(String var1) throws JQException;

    public List<DesignTableDefine> getAllTableDefineInRegion(String var1);

    public List<DesignTableDefine> queryAllTableDefineInRegion(String var1, boolean var2) throws JQException;

    public DesignDataLinkDefine queryDataLinkDefine(String var1, int var2, int var3);

    public List<DesignDataLinkDefine> getReferencedDataLinkByField(DesignFieldDefine var1);

    public List<String> insertDataLinkDefines(DesignDataLinkDefine[] var1);

    public void updateDataLinkDefines(DesignDataLinkDefine[] var1);

    public void deleteDataLinkDefines(String[] var1);

    public List<DesignDataLinkDefine> createDataLinkDefines(int var1);

    public void deleteDataLinkDefinesByFieldKey(String var1);

    public void deleteDataLinkDefinesByFieldKeys(String[] var1);

    public List<DesignDataLinkDefine> getAllLinks();

    @Deprecated
    public String addEnumLinkage(String var1, DesignEnumLinkageSettingDefine var2);

    @Deprecated
    public DesignEnumLinkageSettingDefine getEnumLinkage(String var1);

    @Deprecated
    public List<DesignEnumLinkageSettingDefine> getEnumLinkages(String[] var1);

    @Deprecated
    public void updateEnumLinkage(DesignEnumLinkageSettingDefine var1);

    @Deprecated
    public void removeEnumLinkage(String var1);

    @Deprecated
    public void removeEnumLinkage(String[] var1);

    public DesignTaskLinkDefine createTaskLinkDefine();

    public String insertTaskLinkDefine(DesignTaskLinkDefine var1);

    public List<String> insertTaskLinkDefines(List<DesignTaskLinkDefine> var1);

    public void deleteTaskLinkDefine(String var1);

    public void deleteTaskLinkDefineByCurrentFormScheme(String var1);

    public DesignTaskLinkDefine queryDesignByKey(String var1);

    public void deleteTaskLinkDefineByCurrentFormSchemeAndNum(String var1, String var2);

    public void updateTaskLinkDefine(DesignTaskLinkDefine var1);

    public void updateTaskLinkDefines(List<DesignTaskLinkDefine> var1);

    public List<DesignTaskLinkDefine> queryLinksByCurrentFormScheme(String var1);

    public List<DesignTaskLinkDefine> queryLinksByRelatedTaskCode(String var1);

    public DesignTaskLinkDefine queryLinkByCurrentFormSchemeAndNum(String var1, String var2);

    public String getFormSchemeEntity(String var1) throws JQException;

    @Deprecated
    public DesignEntityLinkageDefine createEntityLinkageDefine();

    @Deprecated
    public String insertDesignerEntityeLinkageDefine(DesignEntityLinkageDefine var1);

    @Deprecated
    public DesignEntityLinkageDefine queryDesignEntityLinkageDefineByKey(String var1);

    @Deprecated
    public void updateEntityLinkageDefine(DesignEntityLinkageDefine var1);

    public List<DesignFormGroupDefine> queryAllGroupsByFormScheme(String var1) throws JQException;

    public DesignDataLinkDefine queryDataLinkDefineByColRow(String var1, int var2, int var3);

    public DesignDataLinkDefine queryDataLinkDefineByUniquecode(String var1, String var2);

    public List<DesignDataLinkDefine> getLinksInFormByField(String var1, String var2);

    public DesignFormSchemeDefine getFormschemeByCode(String var1) throws Exception;

    public DesignTaskFlowsDefine queryFlowDefine(String var1) throws Exception;

    public List<DesignCaliberGroupLink> getCaliberGroupByCaliberKey(String var1) throws Exception;

    public void updateCaliberLink(DesignCaliberGroupLink var1);

    public void deleteCaliberLink(DesignCaliberGroupLink var1);

    public List<DesignFormSchemeDefine> queryAllFormSchemeDefine();

    public List<DesignFormSchemeDefine> queryFormSchemeDefines(String[] var1) throws Exception;

    public DesignAnalysisFormParamDefine queryAnalysisFormParamDefine(String var1) throws Exception;

    public void updataAnalysisFormParamDefine(String var1, DesignAnalysisFormParamDefine var2) throws Exception;

    public void deleteAnalysisFormParamDefine(String var1) throws Exception;

    public DesignAnalysisSchemeParamDefine queryAnalysisSchemeParamDefine(String var1) throws Exception;

    public void updataAnalysisSchemeParamDefine(String var1, DesignAnalysisSchemeParamDefine var2) throws Exception;

    public void deleteAnalysisSchemeParamDefine(String var1) throws Exception;

    public boolean enableAnalysisScheme(String var1) throws Exception;

    public List<DesignTaskDefine> checkTaskNameAvailable(String var1, String var2) throws Exception;

    public List<FormulaVariDefine> queryAllFormulaVariable(String var1);

    public DesignAnalysisFormGroupDefine queryAnalysisFormGroupDefine(String var1) throws Exception;

    public void deleteAnalysisFormGroupDefine(String var1) throws Exception;

    public void updataAnalysisFormGroupDefine(String var1, DesignAnalysisFormGroupDefine var2) throws Exception;

    public List<DesignSchemePeriodLinkDefine> querySchemePeriodLinkByScheme(String var1) throws Exception;

    public List<DesignSchemePeriodLinkDefine> querySchemePeriodLinkByTask(String var1) throws Exception;

    public DesignSchemePeriodLinkDefine querySchemePeriodLinkByPeriodAndScheme(String var1, String var2) throws Exception;

    public DesignSchemePeriodLinkDefine querySchemePeriodLinkByPeriodAndTask(String var1, String var2) throws Exception;

    public void deleteSchemePeriodLinkByScheme(String var1) throws Exception;

    public void deleteSchemePeriodLinkByTask(String var1) throws Exception;

    public void deleteSchemePeriodLink(List<DesignSchemePeriodLinkDefine> var1) throws Exception;

    public void inserSchemePeriodLink(List<DesignSchemePeriodLinkDefine> var1) throws Exception;

    public List<DesignDataLinkMappingDefine> queryDataLinkMappingByFormKey(String var1);

    public List<DataLinkMappingVO> getDataLinkMappingVO(String var1);

    public void saveOrUpdateDataLinkMapping(String var1, List<DataLinkMappingVO> var2) throws Exception;

    public void deleteDataLinkMapping(String var1) throws Exception;

    public DesignDataLinkMappingDefine createDataLinkMappingDefine();

    public void insertDataLinkMappingDefine(DesignDataLinkMappingDefine var1) throws JQException;

    public void updateDataLinkMappingDefine(DesignDataLinkMappingDefine var1) throws JQException;

    public void insertDataLinkMappingDefines(DesignDataLinkMappingDefine[] var1) throws JQException;

    public void updateDataLinkMappingDefines(DesignDataLinkMappingDefine[] var1) throws JQException;

    public DesignReportTemplateDefine createReportTemplateDefine();

    public List<DesignReportTemplateDefine> getReportTemplateByTask(String var1);

    public List<DesignReportTemplateDefine> getReportTemplateByScheme(String var1);

    public void insertReportTemplate(DesignReportTemplateDefine var1, String var2, InputStream var3) throws JQException;

    public void updateReportTemplate(DesignReportTemplateDefine var1) throws JQException;

    public void updateReportTemplate(String var1, String var2, String var3, InputStream var4) throws JQException;

    public void deleteReportTemplate(String ... var1) throws JQException;

    public void deleteReportTemplateByScheme(String var1) throws JQException;

    public void getReportTemplateFile(String var1, OutputStream var2);

    public List<DesignFormDefine> getSimpleFormDefines(List<String> var1);

    public List<DesignFormGroupLink> getFormGroupLinks(List<String> var1);

    public List<DesignReportTagDefine> queryAllTagsByRptKey(String var1);

    public void deleteTagByKeys(List<String> var1) throws JQException;

    public void insertTags(List<DesignReportTagDefine> var1) throws JQException;

    public void deleteTagsByRptKey(String var1) throws JQException;

    public void saveTag(DesignReportTagDefine var1) throws JQException;

    public List<DesignReportTagDefine> filterCustomTagsInRpt(InputStream var1, String var2) throws JQException;

    public TransformReportDefine exportReport(String var1);

    public void importReport(TransformReportDefine var1, Boolean var2) throws JQException;

    public void deleteReport(String var1) throws JQException;

    public List<DesignTaskGroupLink> getGroupLinkByTaskKey(String var1);

    public List<DesignTaskGroupLink> getGroupLink();

    public List<DesignTaskGroupLink> getGroupLinkByGroupKey(String var1);

    public DesignDimensionFilter createDesignDimensionFilter();

    public DesignDimensionFilter getDimensionFilterByTaskKey(String var1, String var2);

    public List<DesignDimensionFilter> getDimensionFilterByTaskKey(String var1);

    public void updateDimensionFilter(DesignDimensionFilter var1);

    public void insertDimensionFilter(DesignDimensionFilter var1) throws JQException;

    public void insertDimensionFilters(List<DesignDimensionFilter> var1) throws JQException;

    public void deleteDimensionFilter(String var1);

    public void saveDimensionFiltersByTaskKey(String var1, List<DesignDimensionFilter> var2);
}

