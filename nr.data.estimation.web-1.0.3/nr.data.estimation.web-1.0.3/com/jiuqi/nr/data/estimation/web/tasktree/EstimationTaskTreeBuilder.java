/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.itree.ITreeBreadthFirstIterator
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.data.estimation.web.tasktree;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.itree.ITreeBreadthFirstIterator;
import com.jiuqi.nr.data.estimation.web.tasktree.ETaskNodeData;
import com.jiuqi.nr.data.estimation.web.tasktree.EstimationTaskTreeNode;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EstimationTaskTreeBuilder {
    private IRunTimeViewController runTimeViewController;

    public EstimationTaskTreeBuilder(IRunTimeViewController runTimeViewController) {
        this.runTimeViewController = runTimeViewController;
    }

    public List<ITree<EstimationTaskTreeNode>> getTree() {
        List<ITree<EstimationTaskTreeNode>> tree = this.getRoots();
        ITreeBreadthFirstIterator iterator = new ITreeBreadthFirstIterator(tree);
        while (iterator.hasNext()) {
            ITree target = iterator.next();
            List<ITree<EstimationTaskTreeNode>> children = this.getChildren((EstimationTaskTreeNode)target.getData());
            if (children == null || children.isEmpty()) continue;
            target.setChildren(children);
        }
        return tree;
    }

    public List<ITree<EstimationTaskTreeNode>> getRoots() {
        List allTaskDefines = this.runTimeViewController.getAllTaskDefines();
        return allTaskDefines.stream().map(task -> {
            ITree node = new ITree((INode)this.createTaskData((TaskDefine)task));
            node.setLeaf(false);
            return node;
        }).collect(Collectors.toList());
    }

    public List<ITree<EstimationTaskTreeNode>> getChildren(EstimationTaskTreeNode parent) {
        if ("task".equals(parent.getNodeType())) {
            try {
                List schemeDefines = this.runTimeViewController.queryFormSchemeByTask(parent.getKey());
                return schemeDefines.stream().map(formSchemeDefine -> {
                    ITree node = new ITree((INode)this.createFormSchemeData((FormSchemeDefine)formSchemeDefine));
                    node.setLeaf(true);
                    return node;
                }).collect(Collectors.toList());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return new ArrayList<ITree<EstimationTaskTreeNode>>();
    }

    private ETaskNodeData createTaskData(TaskDefine taskDefine) {
        ETaskNodeData node = new ETaskNodeData();
        node.setKey(taskDefine.getKey());
        node.setCode(taskDefine.getTaskCode());
        node.setTitle(taskDefine.getTitle());
        node.setNodeType("task");
        return node;
    }

    private ETaskNodeData createFormSchemeData(FormSchemeDefine formScheme) {
        ETaskNodeData node = new ETaskNodeData();
        node.setKey(formScheme.getKey());
        node.setCode(formScheme.getFormSchemeCode());
        node.setTitle(formScheme.getTitle());
        node.setNodeType("formScheme");
        return node;
    }
}

