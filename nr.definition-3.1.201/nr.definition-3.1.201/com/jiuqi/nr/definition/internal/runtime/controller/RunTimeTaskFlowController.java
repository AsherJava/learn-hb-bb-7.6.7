/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.controller;

import com.jiuqi.nr.definition.controller.IRunTimeTaskFlowController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.dao.RunTimeBigDataTableDao;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.internal.impl.RunTimeBigDataTable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Deprecated
public class RunTimeTaskFlowController
implements IRunTimeTaskFlowController {
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private RunTimeBigDataTableDao bigDataDao;

    @Override
    public List<TaskDefine> getEnableFlowTaskDefines() {
        List<TaskDefine> allTaskDefines = this.runTimeAuthViewController.getAllTaskDefines();
        ArrayList<TaskDefine> result = new ArrayList<TaskDefine>();
        for (TaskDefine define : allTaskDefines) {
            TaskFlowsDefine flowsDefine = define.getFlowsSetting();
            if (null == flowsDefine || FlowsType.NOSTARTUP == flowsDefine.getFlowsType()) continue;
            result.add(define);
        }
        return result;
    }

    private Map<String, TaskFlowsDefine> getAllFlowsDefine() {
        List<RunTimeBigDataTable> flowDatas = this.bigDataDao.queryigDataDefine("FLOWSETTING");
        LinkedHashMap<String, TaskFlowsDefine> flowSettings = new LinkedHashMap<String, TaskFlowsDefine>(flowDatas.size());
        for (RunTimeBigDataTable bigData : flowDatas) {
            flowSettings.put(bigData.getKey(), DesignTaskFlowsDefine.bytesToTaskFlowsData(bigData.getData()));
        }
        return flowSettings;
    }
}

