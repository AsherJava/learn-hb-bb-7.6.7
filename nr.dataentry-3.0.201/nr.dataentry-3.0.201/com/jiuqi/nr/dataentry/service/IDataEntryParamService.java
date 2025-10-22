/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.nr.dataentry.paramInfo.FormGroupData;
import com.jiuqi.nr.dataentry.paramInfo.FormSchemeData;
import com.jiuqi.nr.dataentry.paramInfo.TaskData;
import com.jiuqi.nr.dataentry.paramInfo.TaskGroupParam;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;

public interface IDataEntryParamService {
    public List<FormGroupData> getRuntimeFormList(JtableContext var1);

    public List<FormGroupData> getRuntimeFMDM(JtableContext var1);

    public List<TaskData> getRuntimeTaskList();

    public TaskData getRuntimeTaskByKey(String var1);

    public List<FormSchemeData> runtimeFormSchemeList(String var1) throws Exception;

    public List<TaskData> getRuntimeTaskListByGroupKey(TaskGroupParam var1);
}

