/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.itree.ITreeBreadthFirstIterator
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.source.ITreeNodeProvider
 */
package com.jiuqi.nr.unit.uselector.source.impl;

import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.itree.ITreeBreadthFirstIterator;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.source.ITreeNodeProvider;
import java.util.List;

public class USelectorTreeNodeProvider
implements ITreeNodeProvider {
    private final List<String> checklist;
    protected ITreeNodeProvider baseProvider;

    public USelectorTreeNodeProvider(ITreeNodeProvider baseProvider, List<String> checklist) {
        this.checklist = checklist;
        this.baseProvider = baseProvider;
    }

    public List<ITree<IBaseNodeData>> getRoots() {
        return this.batchSetCheckState(this.baseProvider.getRoots());
    }

    public List<ITree<IBaseNodeData>> getChildren(IBaseNodeData parentNode) {
        return this.batchSetCheckState(this.baseProvider.getChildren(parentNode));
    }

    public List<ITree<IBaseNodeData>> getTree() {
        return this.batchSetCheckState(this.baseProvider.getTree());
    }

    protected List<ITree<IBaseNodeData>> batchSetCheckState(List<ITree<IBaseNodeData>> tree) {
        ITreeBreadthFirstIterator iterator = new ITreeBreadthFirstIterator(tree);
        while (iterator.hasNext()) {
            ITree next = iterator.next();
            next.setChecked(this.isChecked((ITree<IBaseNodeData>)next));
        }
        return tree;
    }

    protected boolean isChecked(ITree<IBaseNodeData> node) {
        return this.checklist.contains(node.getKey());
    }
}

