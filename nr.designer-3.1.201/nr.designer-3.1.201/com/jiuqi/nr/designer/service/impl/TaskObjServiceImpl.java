/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 */
package com.jiuqi.nr.designer.service.impl;

import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.designer.service.ITaskObjService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskObjServiceImpl
implements ITaskObjService {
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    private static final Logger logger = LoggerFactory.getLogger(TaskObjServiceImpl.class);

    @Override
    public boolean taskIsPublish(String taskKey) throws Exception {
        TaskDefine taskDefine = this.runTimeAuthViewController.queryTaskDefine(taskKey);
        return taskDefine != null;
    }
}

