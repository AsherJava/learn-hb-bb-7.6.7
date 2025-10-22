/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 */
package nr.midstore2.design.vo;

import com.jiuqi.nr.common.itree.ITree;
import java.util.List;
import nr.midstore2.design.vo.MistoreTaskVO;
import nr.midstore2.design.vo.TaskTreeNodeVO;

public class TaskListVO {
    private List<ITree<TaskTreeNodeVO>> taskTree;
    private MistoreTaskVO task;

    public List<ITree<TaskTreeNodeVO>> getTaskTree() {
        return this.taskTree;
    }

    public void setTaskTree(List<ITree<TaskTreeNodeVO>> taskTree) {
        this.taskTree = taskTree;
    }

    public MistoreTaskVO getTask() {
        return this.task;
    }

    public void setTask(MistoreTaskVO task) {
        this.task = task;
    }
}

