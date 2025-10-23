/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.entity.component.tree.vo.TreeNode
 *  com.jiuqi.nr.entity.component.tree.vo.TreeParam
 *  com.jiuqi.nr.task.api.tree.TreeData
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.task.service;

import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.entity.component.tree.vo.TreeNode;
import com.jiuqi.nr.entity.component.tree.vo.TreeParam;
import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nr.task.dto.FormSchemeStatusDTO;
import com.jiuqi.nr.task.dto.TaskDetailDTO;
import com.jiuqi.nr.task.dto.taskOrderMoveDTO;
import com.jiuqi.nr.task.web.vo.DwTaskOptionVO;
import com.jiuqi.nr.task.web.vo.TaskItemVO;
import com.jiuqi.nr.task.web.vo.TaskParamVO;
import com.jiuqi.nr.task.web.vo.TaskPublishInfo;
import com.jiuqi.nr.task.web.vo.TaskResourceSearchVO;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public interface ITaskService {
    public TaskParamVO initTask(String var1);

    public TaskItemVO insertTask(TaskDetailDTO var1);

    public String deleteTask(String var1);

    public TaskParamVO getTask(String var1);

    public TaskItemVO updateTask(TaskDetailDTO var1);

    public List<TaskItemVO> queryTask(String var1);

    public void moveTask(taskOrderMoveDTO var1);

    public void changeTaskGroup(String var1, List<String> var2);

    public void checkTaskCode(String var1, String var2);

    public List<TaskResourceSearchVO> fuzzySearchTask(String var1);

    public boolean checkTask(String var1);

    public List<UITreeNode<TreeData>> treeDataSelect(String var1, String var2);

    public List<ITree<TreeNode>> unitTreeInit(TreeParam var1);

    public List<ITree<TreeNode>> unitTreeChildren(TreeParam var1);

    public List<ITree<TreeNode>> locationTreeNode(TreeParam var1);

    public boolean checkTaskTitle(TaskParamVO var1);

    public DwTaskOptionVO getDwTaskOption(String var1);

    public void saveDwTaskOption(DwTaskOptionVO var1) throws Exception;

    public FormSchemeStatusDTO queryTaskStatus(String var1);

    public TaskPublishInfo queryTaskPublishInfo(String var1);

    public void exportPublishError(String var1, HttpServletResponse var2);
}

