/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.IDimensionFilter
 */
package com.jiuqi.nr.definition.api;

import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.IDimensionFilter;
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
import java.util.List;

public interface IRunTimeViewController {
    public TaskStream getTaskStream(String var1);

    public TaskDefine getTask(String var1);

    public TaskListStream listAllTaskStream();

    public List<TaskDefine> listAllTask();

    public TaskListStream listTaskByTypeStream(TaskType var1);

    public List<TaskDefine> listTaskByType(TaskType var1);

    public TaskStream getTaskByCodeStream(String var1);

    public TaskDefine getTaskByCode(String var1);

    public TaskStream getTaskByFilePrefixStream(String var1);

    public TaskDefine getTaskByFilePrefix(String var1);

    public TaskListStream listTaskByTaskGroupStream(String var1);

    public List<TaskDefine> listTaskByTaskGroup(String var1);

    public List<TaskDefine> listTaskByDataScheme(String var1);

    public List<TaskDefine> listTaskByCaliber(String var1);

    public TaskGroupDefine getTaskGroup(String var1);

    public TaskGroupStream getTaskGroupStream(String var1);

    public List<TaskGroupDefine> listTaskGroupByParentGroup(String var1);

    public TaskGroupListStream listTaskGroupByParentGroupStream(String var1);

    public List<TaskGroupDefine> listTaskGroupByTask(String var1);

    public List<TaskGroupDefine> listAllTaskGroup();

    public FormSchemeListStream listFormSchemeByTaskStream(String var1);

    public List<FormSchemeDefine> listAllFormScheme();

    public List<FormSchemeDefine> listFormSchemeByTask(String var1);

    public FormSchemeStream getFormSchemeStream(String var1);

    public FormSchemeDefine getFormScheme(String var1);

    public FormSchemeStream getFormSchemeByCodeStream(String var1);

    public FormSchemeDefine getFormSchemeByCode(String var1);

    public FormGroupStream getFormGroupStream(String var1, String var2);

    public FormGroupDefine getFormGroup(String var1, String var2);

    public FormGroupListStream listFormGroupByFormSchemeStream(String var1);

    public List<FormGroupDefine> listFormGroupByFormScheme(String var1);

    public FormGroupListStream listFormGroupByFormStream(String var1, String var2);

    public List<FormGroupDefine> listFormGroupByForm(String var1, String var2);

    public FormStream getFormStream(String var1, String var2);

    public FormDefine getForm(String var1, String var2);

    public FormStream listFormByCodeAndFormSchemeStream(String var1, String var2);

    public FormDefine listFormByCodeAndFormScheme(String var1, String var2);

    public FormListStream listFormByFormSchemeStream(String var1);

    public List<FormDefine> listFormByFormScheme(String var1);

    public FormStream getFmdmFormByFormSchemeStream(String var1);

    public FormDefine getFmdmFormByFormScheme(String var1);

    public FormListStream listFormByGroupStream(String var1, String var2);

    public List<FormDefine> listFormByGroup(String var1, String var2);

    public BigDataDefine getFormStyle(String var1, String var2);

    public DataRegionDefine getDataRegion(String var1, String var2);

    public DataRegionDefine getDataRegion(String var1, String var2, String var3);

    public List<DataRegionDefine> listDataRegionByForm(String var1, String var2);

    public RegionSettingStream getRegionSettingByRegionStream(String var1, String var2);

    public RegionSettingDefine getRegionSettingByRegion(String var1, String var2);

    public AttachmentObj getLinkFileSetting(String var1, String var2);

    public DataLinkDefine getDataLink(String var1, String var2);

    public List<DataLinkDefine> listDataLinkByForm(String var1, String var2);

    public List<DataLinkDefine> listDataLinkByDataRegion(String var1, String var2);

    public DataLinkDefine getDataLinkByFormAndPos(String var1, int var2, int var3, String var4);

    public DataLinkDefine getDataLinkByFormAndColRow(String var1, int var2, int var3, String var4);

    public DataLinkDefine getDataLinkByFormAndUniquecode(String var1, String var2, String var3);

    public List<String> listFieldKeyByDataRegion(String var1, String var2);

    public List<String> listFieldKeyByForm(String var1, String var2);

    public List<TaskLinkDefine> listTaskLinkByFormScheme(String var1);

    public List<SchemePeriodLinkDefine> listSchemePeriodLinkByFormScheme(String var1);

    public List<SchemePeriodLinkDefine> listSchemePeriodLinkByTask(String var1);

    public SchemePeriodLinkDefine getSchemePeriodLinkByPeriodAndFormScheme(String var1, String var2);

    public SchemePeriodLinkDefine getSchemePeriodLinkByPeriodAndTask(String var1, String var2);

    public List<DataLinkMappingDefine> listDataLinkMappingByForm(String var1, String var2);

    public IDimensionFilter getDimensionFilterBySchemeAndEntity(String var1, String var2);

    public List<IDimensionFilter> listDimensionFilterByScheme(String var1);

    public List<EntityViewDefine> listDimensionViews(String var1);

    public EntityViewDefine getDimensionView(String var1, String var2);

    public EntityViewDefine getViewByTaskDefineKey(String var1);

    public EntityViewDefine getViewByFormSchemeKey(String var1);

    public EntityViewDefine getViewByLinkAndFormScheme(String var1, String var2);

    public List<FormFoldingDefine> listFormFoldingByFormKey(String var1);

    public TaskOrgLinkStream getTaskOrgLinkStream(String var1);

    public TaskOrgLinkDefine getTaskOrgLink(String var1);

    public TaskOrgLinkListStream listTaskOrgLinkStreamByTask(String var1);

    public List<TaskOrgLinkDefine> listTaskOrgLinkByTask(String var1);

    public TaskOrgLinkStream getTaskOrgLinkStreamByTaskAndEntity(String var1, String var2);

    public TaskOrgLinkDefine getTaskOrgLinkByTaskAndEntity(String var1, String var2);
}

