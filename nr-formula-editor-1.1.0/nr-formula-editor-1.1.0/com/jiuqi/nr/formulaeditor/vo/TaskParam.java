/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formulaeditor.vo;

import com.jiuqi.nr.formulaeditor.vo.EditorNodeData;
import com.jiuqi.nr.formulaeditor.vo.ITree;
import com.jiuqi.nr.formulaeditor.vo.TaskData;
import java.util.List;

public class TaskParam {
    private List<ITree<EditorNodeData>> tree;
    private List<TaskData> tasks;

    public List<ITree<EditorNodeData>> getTree() {
        return this.tree;
    }

    public void setTree(List<ITree<EditorNodeData>> tree) {
        this.tree = tree;
    }

    public List<TaskData> getTasks() {
        return this.tasks;
    }

    public void setTasks(List<TaskData> tasks) {
        this.tasks = tasks;
    }
}

