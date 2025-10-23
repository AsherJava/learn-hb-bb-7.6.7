/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 */
package com.jiuqi.nr.task.service;

import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nr.task.dto.TaskGroupDTO;
import com.jiuqi.nr.task.web.vo.ResourcePath;
import com.jiuqi.nr.task.web.vo.ResourceSearchVO;
import com.jiuqi.nr.task.web.vo.TaskGroupTreeNode;
import java.util.List;

public interface ITaskGroupService {
    public String insertTaskGroup(TaskGroupDTO var1);

    public boolean checkTaskGroup(TaskGroupDTO var1);

    public void deleteTaskGroup(String var1);

    public void updateTaskGroup(TaskGroupDTO var1);

    public void changeTaskGroupOrder(String var1, int var2);

    public TaskGroupDTO queryTaskGroup(String var1);

    public List<UITreeNode<TaskGroupTreeNode>> listChildTaskGroup(String var1);

    public List<ResourceSearchVO> fuzzySearchTaskGroup(String var1);

    public List<TaskGroupDTO> queryTaskGroupByGroupId(String var1, boolean var2);

    public List<UITreeNode<TaskGroupTreeNode>> getRootGroupTree();

    public List<UITreeNode<TaskGroupTreeNode>> getAllTaskGroupTree();

    public List<TaskGroupDTO> getGroupByTask(String var1);

    public List<UITreeNode<TaskGroupTreeNode>> locationTaskGroup(String var1);

    public List<UITreeNode<TaskGroupTreeNode>> locationByTask(String var1);

    public List<ResourcePath> getResourcePath(String var1);
}

