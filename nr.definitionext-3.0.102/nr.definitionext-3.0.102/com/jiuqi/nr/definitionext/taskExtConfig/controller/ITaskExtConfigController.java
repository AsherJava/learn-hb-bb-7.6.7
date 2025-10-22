/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definitionext.taskExtConfig.controller;

import com.jiuqi.nr.definitionext.taskExtConfig.model.ExtensionBasicModel;
import com.jiuqi.nr.definitionext.taskExtConfig.model.TaskExtConfigDefine;
import java.util.List;

public interface ITaskExtConfigController {
    public TaskExtConfigDefine initTaskExtConfigDefine(String var1, String var2, String var3) throws Exception;

    public Boolean insertTaskExtConfigDefine(TaskExtConfigDefine var1) throws Exception;

    public Boolean updateTaskExtConfigDefine(TaskExtConfigDefine var1) throws Exception;

    public Boolean deleteTaskExtConfigDefineByKey(String var1) throws Exception;

    public TaskExtConfigDefine getTaskExtConfigDefineByKey(String var1) throws Exception;

    public List<TaskExtConfigDefine> getAllTaskExtConfigDefine() throws Exception;

    public List<TaskExtConfigDefine> getTaskExtConfigDefineByType(String var1) throws Exception;

    public ExtensionBasicModel<?> getTaskExtConfigDefineBySchemakey(String var1, String var2, String var3) throws Exception;

    public Object getTaskExtConfigDefineBySchemakeyCache(String var1, String var2, String var3) throws Exception;
}

