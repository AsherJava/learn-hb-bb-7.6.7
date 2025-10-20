/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.tree.BaseTreeNode
 *  com.jiuqi.dc.taskscheduling.api.vo.TaskHandlerVO
 */
package com.jiuqi.dc.taskscheduling.core.data;

import com.jiuqi.dc.base.common.tree.BaseTreeNode;
import com.jiuqi.dc.taskscheduling.api.vo.TaskHandlerVO;
import java.util.ArrayList;

public class TaskHandlerTreeNode
extends BaseTreeNode {
    private TaskHandlerVO taskHandler;

    public TaskHandlerTreeNode() {
    }

    public TaskHandlerTreeNode(TaskHandlerVO taskHandler) {
        this.taskHandler = taskHandler;
        super.setCode(taskHandler.getName());
        super.setChildren(new ArrayList());
    }

    public TaskHandlerVO getTaskHandler() {
        return this.taskHandler;
    }

    public void setTaskHandler(TaskHandlerVO taskHandler) {
        this.taskHandler = taskHandler;
    }
}

