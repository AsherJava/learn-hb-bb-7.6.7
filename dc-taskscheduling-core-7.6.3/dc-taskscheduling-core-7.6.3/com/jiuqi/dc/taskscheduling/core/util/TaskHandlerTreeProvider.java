/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.taskscheduling.api.vo.TaskHandlerVO
 */
package com.jiuqi.dc.taskscheduling.core.util;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.taskscheduling.api.vo.TaskHandlerVO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskHandlerTreeProvider {
    private static final TaskHandlerVO root = new TaskHandlerVO("-", "-", null);
    private List<TaskHandlerVO> handlers;

    public TaskHandlerTreeProvider(List<TaskHandlerVO> handlers) {
        this.handlers = handlers;
    }

    public TaskHandlerVO getRoot() {
        return root;
    }

    public List<TaskHandlerVO> getTopHandler() {
        ArrayList<TaskHandlerVO> rootHandler = new ArrayList<TaskHandlerVO>();
        for (TaskHandlerVO handler : this.handlers) {
            if (!StringUtils.isEmpty((String)handler.getPreTask())) continue;
            rootHandler.add(handler);
        }
        return rootHandler;
    }

    public List<TaskHandlerVO> getChildren(TaskHandlerVO parent) {
        ArrayList<TaskHandlerVO> children = new ArrayList<TaskHandlerVO>();
        for (TaskHandlerVO handler : this.handlers) {
            if (StringUtils.isEmpty((String)handler.getPreTask()) || !Arrays.asList(handler.getPreTask().split(";")).contains(parent.getName())) continue;
            children.add(handler);
        }
        return children;
    }

    public boolean hasChildren(TaskHandlerVO parent) {
        return !this.getChildren(parent).isEmpty();
    }
}

