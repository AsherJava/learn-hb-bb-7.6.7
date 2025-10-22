/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 */
package com.jiuqi.nr.form.selector.tree.impl;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.form.selector.tree.IReportFormTreeData;
import com.jiuqi.nr.form.selector.tree.IReportFormTreeProvider;
import com.jiuqi.nr.form.selector.tree.IReportTreeNode;
import com.jiuqi.nr.form.selector.tree.impl.ReportFormNode;
import com.jiuqi.nr.form.selector.tree.impl.ReportGroupNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ReportFormTreeData
implements IReportFormTreeData {
    protected List<String> checkList;
    protected IReportFormTreeProvider provider;

    public ReportFormTreeData(IReportFormTreeProvider provider, List<String> checkList) {
        this.provider = provider;
        this.checkList = checkList;
    }

    @Override
    public List<ITree<IReportTreeNode>> getRoots(String formSchemeKey) {
        ArrayList<ITree<IReportTreeNode>> roots = new ArrayList<ITree<IReportTreeNode>>();
        List<FormGroupDefine> groups = this.provider.getGroups(formSchemeKey);
        if (groups != null) {
            groups.forEach(group -> this.addRoot((List<ITree<IReportTreeNode>>)roots, (FormGroupDefine)group));
        }
        return roots;
    }

    public void addRoot(List<ITree<IReportTreeNode>> roots, FormGroupDefine group) {
        if (this.getChildren(group.getKey()) != null && this.getChildren(group.getKey()).size() > 0) {
            roots.add(this.createGroupNode(group));
        }
    }

    @Override
    public List<ITree<IReportTreeNode>> getChildren(String groupKey) {
        ArrayList<ITree<IReportTreeNode>> children = new ArrayList<ITree<IReportTreeNode>>();
        List<FormDefine> forms = this.provider.getForms(groupKey);
        if (forms != null) {
            forms.forEach(form -> children.add(this.createFormNode((FormDefine)form)));
        }
        return children;
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

    protected ITree<IReportTreeNode> createGroupNode(FormGroupDefine group) {
        ReportGroupNode groupData = ReportGroupNode.assignGroupNode(group);
        ITree groupNode = new ITree((INode)groupData);
        groupNode.setExpanded(true);
        return groupNode;
    }

    protected ITree<IReportTreeNode> createFormNode(FormDefine form) {
        ReportFormNode formData = ReportFormNode.assignFormNode(form);
        ITree formNode = new ITree((INode)formData);
        formNode.setChecked(this.isChecked(formData.getKey()));
        formNode.setLeaf(true);
        formNode.setIcons(new String[]{"#icon-_TCYbiaoge"});
        return formNode;
    }

    protected boolean isChecked(String key) {
        return this.checkList != null && this.checkList.contains(key);
    }
}

