/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package nr.single.map.data.internal.service;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;
import nr.single.map.data.bean.FormGroupData;
import nr.single.map.data.bean.FormSchemeData;
import nr.single.map.data.bean.TaskData;
import nr.single.map.data.service.SingleParamService;
import nr.single.map.data.util.SingleParamProvider;
import org.springframework.stereotype.Service;

@Service
public class SingleParamServiceImpl
implements SingleParamService {
    @Override
    public List<FormGroupData> getRuntimeFormList(JtableContext jtableContext) {
        return null;
    }

    @Override
    public List<TaskData> getRuntimeTaskList() {
        return this.getSingleParamProvider().getRuntimeTaskList();
    }

    @Override
    public TaskData getRuntimeTaskByKey(String taskKey) {
        return this.getSingleParamProvider().getRuntimeTaskByKey(taskKey);
    }

    @Override
    public List<FormSchemeData> runtimeFormSchemeList(String taskKey) {
        return this.getSingleParamProvider().getRuntimeFormSchemeList(taskKey);
    }

    private SingleParamProvider getSingleParamProvider() {
        return new SingleParamProvider();
    }
}

