/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package nr.single.map.data.service;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;
import nr.single.map.data.bean.FormGroupData;
import nr.single.map.data.bean.FormSchemeData;
import nr.single.map.data.bean.TaskData;

public interface SingleParamService {
    public List<FormGroupData> getRuntimeFormList(JtableContext var1);

    public List<TaskData> getRuntimeTaskList();

    public TaskData getRuntimeTaskByKey(String var1);

    public List<FormSchemeData> runtimeFormSchemeList(String var1);
}

