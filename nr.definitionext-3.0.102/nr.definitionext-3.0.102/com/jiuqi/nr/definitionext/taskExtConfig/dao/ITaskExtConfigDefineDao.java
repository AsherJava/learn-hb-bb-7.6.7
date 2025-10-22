/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.bean.FormsQueryResult
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.definitionext.taskExtConfig.dao;

import com.jiuqi.nr.dataentry.bean.FormsQueryResult;
import com.jiuqi.nr.definitionext.taskExtConfig.model.TaskExtConfigDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;

public interface ITaskExtConfigDefineDao {
    public Boolean insertTaskExtConfigDefine(TaskExtConfigDefine var1) throws Exception;

    public Boolean updateTaskExtConfigDefine(TaskExtConfigDefine var1) throws Exception;

    public Boolean deleteTaskExtConfigDefineById(String var1) throws Exception;

    public List<TaskExtConfigDefine> getAllTaskExtConfig() throws Exception;

    public TaskExtConfigDefine getTaskExtConfigDefinesByKey(String var1) throws Exception;

    public List<TaskExtConfigDefine> getTaskExtConfigDefinesByTask(TaskExtConfigDefine var1) throws Exception;

    public FormsQueryResult getGroupAllReplyInfo(JtableContext var1) throws Exception;
}

