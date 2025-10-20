/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IDimensionFilter
 */
package com.jiuqi.nr.definition.controller;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IDimensionFilter;
import com.jiuqi.nr.definition.common.DataLinkEditMode;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.TaskType;
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
import com.jiuqi.nr.definition.reportTag.InjectContext;
import com.jiuqi.nr.definition.util.AttachObj;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IRunTimeViewController {
    public TaskDefine queryTaskDefine(String var1);

    public List<TaskDefine> getAllTaskDefines();

    public List<TaskDefine> getAllReportTaskDefines();

    public List<TaskDefine> getAllTaskDefinesByType(TaskType var1);

    public TaskDefine queryTaskDefineByCode(String var1);

    public TaskDefine queryTaskDefineByFilePrefix(String var1);

    public List<TaskDefine> getAllRunTimeTasksInGroup(String var1);

    public TaskGroupDefine queryTaskGroupDefine(String var1);

    public List<TaskGroupDefine> getChildTaskGroups(String var1, boolean var2);

    public List<TaskGroupDefine> getGroupByTask(String var1);

    public List<TaskGroupDefine> getAllTaskGroup();

    public FormDefine queryFormById(String var1);

    public List<FormDefine> queryFormsById(List<String> var1);

    public FormDefine queryFormByCodeInScheme(String var1, String var2) throws Exception;

    public List<FormDefine> queryAllFormDefinesByTask(String var1);

    public List<FormDefine> queryAllFormDefinesByFormScheme(String var1);

    public List<String> queryAllFormKeysByFormScheme(String var1);

    public FormDefine queryFmdmFormDefineByFormScheme(String var1);

    public BigDataDefine getReportDataFromForm(String var1);

    public String getFillingGuideFromForm(String var1);

    public List<FormSchemeDefine> listAllFormScheme();

    public List<FormSchemeDefine> queryFormSchemeByTask(String var1) throws Exception;

    public List<FormSchemeDefine> queryFormSchemeByForm(String var1) throws Exception;

    public FormSchemeDefine getFormScheme(String var1);

    public FormGroupDefine queryFormGroup(String var1);

    public List<FormGroupDefine> queryRootGroupsByFormScheme(String var1);

    public List<FormGroupDefine> getFormGroupsByFormKey(String var1);

    public List<FormGroupDefine> getChildFormGroups(String var1);

    public List<FormDefine> getAllFormsInGroup(String var1) throws Exception;

    public List<FormDefine> getAllFormsInGroup(String var1, boolean var2) throws Exception;

    public int getFormsCountInGroup(String var1, boolean var2) throws Exception;

    public List<FormGroupDefine> getAllGroupsInGroup(String var1);

    public List<FormGroupDefine> getAllFormGroupsInFormScheme(String var1);

    public DataRegionDefine queryDataRegionDefine(String var1);

    public DataRegionDefine getDataRegion(String var1, String var2, String var3);

    public List<DataRegionDefine> getAllRegionsInForm(String var1);

    public RegionSettingDefine getRegionSetting(String var1);

    public DataLinkDefine queryDataLinkDefine(String var1);

    public List<DataLinkDefine> getAllLinksInForm(String var1);

    public List<DataLinkDefine> getAllLinksInRegion(String var1);

    public List<String> getFieldKeysInRegion(String var1);

    public List<String> getFieldKeysInForm(String var1);

    public DataLinkDefine queryDataLinkDefineByXY(String var1, int var2, int var3);

    public DataLinkDefine queryDataLinkDefineByColRow(String var1, int var2, int var3);

    public DataLinkDefine queryDataLinkDefineByUniquecode(String var1, String var2);

    public List<DataLinkDefine> queryDataLinkDefineByUniquecodes(String var1, Collection<String> var2);

    public void initFormCache(String var1);

    public void initTask(String var1) throws Exception;

    public List<DataLinkDefine> getLinksInFormByField(String var1, String var2);

    public List<DataLinkDefine> getLinksInRegionByField(String var1, String var2);

    public void initEntityFormCache(String var1) throws Exception;

    public FormSchemeDefine getFormschemeByCode(String var1);

    public List<TaskLinkDefine> queryLinksByCurrentFormScheme(String var1);

    public TaskLinkDefine queryTaskLinkByCurrentFormSchemeAndNumber(String var1, String var2);

    public EntityLinkageDefine queryEntityLinkageDefine(String var1);

    public List<FormSchemeDefine> queryFormSchemeByField(String var1) throws Exception;

    public String getFrontScriptFromForm(String var1);

    public String getSurveyDataFromForm(String var1);

    public AttachObj getAttachment(String var1) throws Exception;

    public List<FormSchemeDefine> queryFormSchemeByEntity(String var1) throws JQException;

    @Deprecated
    public List<RegionPartitionDefine> getRegionPartitionDefines(String var1);

    @Deprecated
    public List<String> getAllDimensionsInForm(String var1);

    @Deprecated
    public List<DataLinkDefine> getAllLinksInFormFilterBySelectorViewId(String var1, DataLinkEditMode var2);

    @Deprecated
    public List<TaskLinkDefine> queryLinksByCurrentTask(String var1);

    @Deprecated
    public TaskLinkDefine queryLinkByCurrentTaskAndNum(String var1, String var2);

    @Deprecated
    public FormDefine getLinkTaskForm(String var1, String var2, String var3);

    @Deprecated
    public EnumLinkageSettingDefine getEnumLinkage(String var1);

    @Deprecated
    public List<EnumLinkageSettingDefine> getEnumLinkages(String[] var1);

    @Deprecated
    public String getFormSchemeEntity(String var1) throws JQException;

    @Deprecated
    public String getFormEntity(String var1, String var2) throws JQException;

    @Deprecated
    public FormDefine queryEntityForm(String var1) throws Exception;

    @Deprecated
    public void initCache() throws Exception;

    public List<FormDefine> getAllFormsInGroupWithoutOrder(String var1) throws Exception;

    public List<FormDefine> getAllFormsInGroupWithoutOrder(String var1, boolean var2) throws Exception;

    public AnalysisFormParamDefine queryAnalysisFormParamDefine(String var1) throws Exception;

    public AnalysisSchemeParamDefine queryAnalysisSchemeParamDefine(String var1) throws Exception;

    public List<FormulaVariDefine> queryAllFormulaVariable(String var1);

    public AnalysisFormGroupDefine queryAnalysisFormGroupDefine(String var1) throws Exception;

    public FieldDefine queryFieldDefine(String var1) throws Exception;

    public List<SchemePeriodLinkDefine> querySchemePeriodLinkByScheme(String var1) throws Exception;

    public List<SchemePeriodLinkDefine> querySchemePeriodLinkBySchemeSort(String var1) throws Exception;

    public List<SchemePeriodLinkDefine> querySchemePeriodLinkByTask(String var1) throws Exception;

    public SchemePeriodLinkDefine querySchemePeriodLinkByPeriodAndScheme(String var1, String var2) throws Exception;

    public SchemePeriodLinkDefine querySchemePeriodLinkByPeriodAndTask(String var1, String var2) throws Exception;

    public List<DataLinkMappingDefine> queryDataLinkMapping(String var1);

    public ReportTemplateDefine getReportTemplate(String var1);

    public List<ReportTemplateDefine> getReportTemplateByTask(String var1);

    public List<ReportTemplateDefine> getReportTemplateByScheme(String var1);

    public byte[] getReportTemplateFile(String var1);

    public void getReportTemplateFile(String var1, OutputStream var2);

    public List<ReportTagDefine> queryAllTagsByRptKey(String var1);

    public byte[] injectReplyToRpt(byte[] var1, InjectContext var2);

    public EntityViewDefine getViewByTaskDefineKey(String var1);

    public EntityViewDefine getViewByFormSchemeKey(String var1);

    public EntityViewDefine getViewByLinkDefineKey(String var1);

    public List<IDimensionFilter> getDimensionFilter(String var1);

    public IDimensionFilter getDimensionFilter(String var1, String var2);

    public List<EntityViewDefine> listDimensionViewsByTask(String var1);

    public List<EntityViewDefine> listDimensionViewsByFormScheme(String var1);

    public EntityViewDefine getDimensionViewByFormSchemeAndEntity(String var1, String var2);

    public EntityViewDefine getDimensionViewByTaskAndEntity(String var1, String var2);

    @Deprecated
    public List<DataLinkDefine> getDataLinksByField(String var1) throws Exception;

    public Collection<String> getFormKeysByField(String var1);

    public Collection<FormFieldInfoDefine> getFormInfosByField(String var1);

    public List<FormFoldingDefine> listFormFoldingByFormKey(String var1);

    public List<DataLinkDefine> listDataLinkByFormAndLinkExp(String var1, String var2, String var3);

    default public DataLinkDefine getDataLinkByFormAndField(String formSchemeKey, String formKey, String fieldKey) {
        List<DataLinkDefine> dataLinkDefines = this.listDataLinkByFormAndLinkExp(formSchemeKey, formKey, fieldKey);
        if (null == dataLinkDefines) {
            return null;
        }
        return dataLinkDefines.stream().findFirst().orElse(null);
    }

    default public DataLinkDefine getDataLinkByFormAndEntityAttr(String formSchemeKey, String formKey, String entityAttrCode) {
        List<DataLinkDefine> dataLinkDefines = this.listDataLinkByFormAndLinkExp(formSchemeKey, formKey, entityAttrCode);
        if (null == dataLinkDefines) {
            return null;
        }
        return dataLinkDefines.stream().filter(l -> DataLinkType.DATA_LINK_TYPE_FMDM == l.getType()).findFirst().orElse(null);
    }

    public Set<String> listLinkExpressionByFormKey(String var1, String var2);
}

