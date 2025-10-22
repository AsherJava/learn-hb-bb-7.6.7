/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.examine.distributor;

import com.jiuqi.nr.examine.common.ParaType;
import com.jiuqi.nr.examine.distributor.ExamineTaskDispatcher;
import com.jiuqi.nr.examine.task.ExamineTask;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class FormDispatcher
extends ExamineTaskDispatcher {
    public FormDispatcher(ExamineTask task) {
        super(task);
    }

    @Override
    protected List<ExamineTask> childrenTask() {
        ArrayList<ExamineTask> childrenTask = new ArrayList<ExamineTask>();
        String key = this.task.getKey();
        if (StringUtils.isEmpty((String)key)) {
            ExamineTask childTask = new ExamineTask(this.task);
            childTask = new ExamineTask(this.task);
            childTask.setParaType(ParaType.REGION);
            childrenTask.add(childTask);
        } else {
            List regions = this.task.getEnv().getNrDesignController().getAllRegionsInForm(key);
            regions.stream().forEach(region -> {
                ExamineTask childTask = new ExamineTask(this.task);
                childTask.setParaType(ParaType.REGION);
                childTask.setKey(region.getKey());
                childrenTask.add(childTask);
            });
        }
        return childrenTask;
    }
}

