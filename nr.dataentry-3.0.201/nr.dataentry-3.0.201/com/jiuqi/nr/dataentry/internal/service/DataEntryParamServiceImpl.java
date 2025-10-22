/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.nr.dataentry.paramInfo.FormGroupData;
import com.jiuqi.nr.dataentry.paramInfo.FormSchemeData;
import com.jiuqi.nr.dataentry.paramInfo.TaskData;
import com.jiuqi.nr.dataentry.paramInfo.TaskGroupParam;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.dataentry.util.DataEntryParamProvider;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataEntryParamServiceImpl
implements IDataEntryParamService {
    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public List<FormGroupData> getRuntimeFormList(JtableContext jtableContext) {
        return this.getDataEntryParamProvider().getRuntimeFormList(jtableContext);
    }

    @Override
    public List<FormGroupData> getRuntimeFMDM(JtableContext context) {
        return this.getDataEntryParamProvider().getRuntimeFMDM(context);
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public List<TaskData> getRuntimeTaskList() {
        return this.getDataEntryParamProvider().getRuntimeTaskList();
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public TaskData getRuntimeTaskByKey(String taskKey) {
        return this.getDataEntryParamProvider().getRuntimeTaskByKey(taskKey);
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public List<FormSchemeData> runtimeFormSchemeList(String taskKey) throws Exception {
        return this.getDataEntryParamProvider().getRuntimeFormSchemeList(taskKey);
    }

    private DataEntryParamProvider getDataEntryParamProvider() {
        return new DataEntryParamProvider();
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public List<TaskData> getRuntimeTaskListByGroupKey(TaskGroupParam taskGroupParam) {
        return this.getDataEntryParamProvider().getRuntimeTaskByGroupKey(taskGroupParam);
    }
}

