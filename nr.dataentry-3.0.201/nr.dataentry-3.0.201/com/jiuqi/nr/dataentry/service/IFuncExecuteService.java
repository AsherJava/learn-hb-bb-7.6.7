/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.FieldQueryInfo
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataentry.bean.BatchCheckExportInfo;
import com.jiuqi.nr.dataentry.bean.BatchCheckParam;
import com.jiuqi.nr.dataentry.bean.DataEntityReturnInfo;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nr.dataentry.bean.DataEntryInitParam;
import com.jiuqi.nr.dataentry.bean.DataentryEntityQueryInfo;
import com.jiuqi.nr.dataentry.bean.ExportData;
import com.jiuqi.nr.dataentry.bean.ExportParam;
import com.jiuqi.nr.dataentry.bean.FormSchemeResult;
import com.jiuqi.nr.dataentry.bean.FormsQueryResult;
import com.jiuqi.nr.dataentry.bean.FuncExecResult;
import com.jiuqi.nr.dataentry.bean.InitLinkParam;
import com.jiuqi.nr.dataentry.bean.SearchFieldItem;
import com.jiuqi.nr.dataentry.paramInfo.FormGroupData;
import com.jiuqi.nr.dataentry.paramInfo.FormsParam;
import com.jiuqi.nr.dataentry.tree.FormTree;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.FieldQueryInfo;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestBody;

public interface IFuncExecuteService {
    public FuncExecResult getDataEntryEnv(DataEntryInitParam var1) throws Exception;

    public FormSchemeDefine queryFormScheme(String var1, String var2);

    public FormsQueryResult getForms(FormsParam var1);

    public FormsQueryResult getFormsWithoutAuth(FormsParam var1);

    public boolean fmdmExist(String var1);

    public List<DataEntityReturnInfo> queryEntityData(@RequestBody DataentryEntityQueryInfo var1);

    public FormsQueryResult getFormsReadAble(@RequestBody DataEntryContext var1);

    public FormTree getFormTree(List<FormGroupData> var1);

    public void exportAsync(ExportParam var1, AsyncTaskMonitor var2);

    public ExportData export(ExportParam var1) throws IOException;

    public ExportData checkResultExport(BatchCheckExportInfo var1);

    public List<SearchFieldItem> searchFieldsInForm(FieldQueryInfo var1);

    public String getFMDMFormKey(String var1, String var2);

    public List<TaskDefine> getRuntimeTasks();

    @Deprecated
    public boolean getUnitWriteFormDataPerm(JtableContext var1);

    public FormTree getAllForms(String var1);

    public boolean haveTask(DataEntryInitParam var1);

    public Map<String, Object> getSysParam(String var1);

    public BatchCheckParam getBatchCheckParam(JtableContext var1);

    public List<FormSchemeResult> getFormSchemeData(DataEntryInitParam var1);

    public Map<String, Object> queryInitLinkData(InitLinkParam var1);
}

