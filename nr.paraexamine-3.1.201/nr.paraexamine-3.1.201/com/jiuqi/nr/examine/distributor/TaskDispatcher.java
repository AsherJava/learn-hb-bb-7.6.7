/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.examine.distributor;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.examine.common.ParaType;
import com.jiuqi.nr.examine.distributor.ExamineTaskDispatcher;
import com.jiuqi.nr.examine.task.ExamineTask;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskDispatcher
extends ExamineTaskDispatcher {
    private static final Logger logger = LoggerFactory.getLogger(TaskDispatcher.class);

    public TaskDispatcher(ExamineTask task) {
        super(task);
    }

    @Override
    protected List<ExamineTask> childrenTask() {
        ArrayList<ExamineTask> childrenTask = new ArrayList<ExamineTask>();
        String key = this.task.getKey();
        if (StringUtils.isEmpty((String)key)) {
            ExamineTask childTask = new ExamineTask(this.task);
            childTask.setParaType(ParaType.FORMSCHEME);
            childrenTask.add(childTask);
        } else {
            IDesignTimeViewController nrDesignController = this.task.getEnv().getNrDesignController();
            try {
                List schemes = nrDesignController.queryFormSchemeByTask(key);
                for (DesignFormSchemeDefine scheme : schemes) {
                    ExamineTask childTask = new ExamineTask(this.task);
                    childTask.setParaType(ParaType.FORMSCHEME);
                    childTask.setKey(scheme.getKey());
                    childrenTask.add(childTask);
                }
            }
            catch (JQException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return childrenTask;
    }
}

