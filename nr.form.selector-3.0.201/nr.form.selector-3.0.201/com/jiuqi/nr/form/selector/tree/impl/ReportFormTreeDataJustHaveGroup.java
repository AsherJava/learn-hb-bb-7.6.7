/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 */
package com.jiuqi.nr.form.selector.tree.impl;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.form.selector.context.IFormQueryHelper;
import com.jiuqi.nr.form.selector.tree.IReportFormTreeData;
import com.jiuqi.nr.form.selector.tree.IReportFormTreeProvider;
import com.jiuqi.nr.form.selector.tree.IReportTreeNode;
import com.jiuqi.nr.form.selector.tree.impl.ReportGroupNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ReportFormTreeDataJustHaveGroup
implements IReportFormTreeData {
    public static final String SOURCE_ID = "just-have-group";
    protected IReportFormTreeProvider provider;
    List<String> checkList;

    public ReportFormTreeDataJustHaveGroup(IFormQueryHelper formQueryHelper, IReportFormTreeProvider provider, List<String> checkList) {
        this.provider = provider;
        this.checkList = checkList;
    }

    @Override
    public List<ITree<IReportTreeNode>> getRoots(String formSchemeKey) {
        ArrayList<ITree<IReportTreeNode>> roots = new ArrayList<ITree<IReportTreeNode>>();
        List<FormGroupDefine> groups = this.provider.getGroups(formSchemeKey);
        groups.forEach(group -> roots.add(this.createGroupNode((FormGroupDefine)group)));
        return roots;
    }

    protected ITree<IReportTreeNode> createGroupNode(FormGroupDefine group) {
        ReportGroupNode groupData = ReportGroupNode.assignGroupNode(group);
        ITree groupNode = new ITree((INode)groupData);
        groupNode.setExpanded(true);
        groupNode.setLeaf(true);
        groupNode.setChecked(this.isChecked(group.getKey()));
        return groupNode;
    }

    @Override
    public List<ITree<IReportTreeNode>> getChildren(String groupKey) {
        return new ArrayList<ITree<IReportTreeNode>>();
    }

    @Override
    public List<ITree<IReportTreeNode>> getTree(String formSchemeKey) {
        List<ITree<IReportTreeNode>> tree = this.getRoots(formSchemeKey);
        Stack<ITree<IReportTreeNode>> stack = new Stack<ITree<IReportTreeNode>>();
        this.reverseAdd(stack, tree);
        while (!stack.isEmpty()) {
            ITree<IReportTreeNode> node = stack.pop();
            List<ITree<IReportTreeNode>> children = this.getChildren(node.getKey());
            if (children == null || children.isEmpty()) continue;
            node.setChildren(children);
            this.reverseAdd(stack, children);
        }
        return tree;
    }

    private void reverseAdd(Stack<ITree<IReportTreeNode>> stack, List<ITree<IReportTreeNode>> nodes) {
        for (int i = nodes.size() - 1; i >= 0; --i) {
            stack.push(nodes.get(i));
        }
    }

    protected boolean isChecked(String key) {
        return this.checkList != null && this.checkList.contains(key);
    }
}

