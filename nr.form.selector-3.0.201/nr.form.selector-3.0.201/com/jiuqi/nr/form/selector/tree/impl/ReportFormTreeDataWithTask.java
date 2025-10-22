/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.form.selector.tree.impl;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.form.selector.context.IFormQueryHelper;
import com.jiuqi.nr.form.selector.tree.IReportFormTreeProvider;
import com.jiuqi.nr.form.selector.tree.IReportTreeNode;
import com.jiuqi.nr.form.selector.tree.impl.ReportFormTreeData;
import com.jiuqi.nr.form.selector.tree.impl.ReportTreeNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportFormTreeDataWithTask
extends ReportFormTreeData {
    public static final String SOURCE_ID = "task";
    private IFormQueryHelper formQueryHelper;
    private IRunTimeViewController runTimeViewController;
    private Map<String, String> typeMap = new HashMap<String, String>();

    public ReportFormTreeDataWithTask(IRunTimeViewController runTimeViewController, IFormQueryHelper formQueryHelper, IReportFormTreeProvider provider, List<String> checkList) {
        super(provider, checkList);
        this.formQueryHelper = formQueryHelper;
        this.runTimeViewController = runTimeViewController;
    }

    @Override
    public List<ITree<IReportTreeNode>> getRoots(String noneKey) {
        List<FormSchemeDefine> formSchemeDefines = this.getFormSchemes();
        return formSchemeDefines.stream().map(this::createSchemeNode).collect(Collectors.toList());
    }

    @Override
    public List<ITree<IReportTreeNode>> getChildren(String parentKey) {
        if ("scheme".equals(this.typeMap.get(parentKey))) {
            List<FormGroupDefine> groups = this.provider.getGroups(parentKey);
            return groups.stream().map(this::createGroupNode).collect(Collectors.toList());
        }
        if ("group".equals(this.typeMap.get(parentKey))) {
            List<FormDefine> forms = this.provider.getForms(parentKey);
            return forms.stream().map(this::createFormNode).collect(Collectors.toList());
        }
        return new ArrayList<ITree<IReportTreeNode>>();
    }

    @Override
    protected ITree<IReportTreeNode> createGroupNode(FormGroupDefine group) {
        this.typeMap.put(group.getKey(), "group");
        return super.createGroupNode(group);
    }

    private ITree<IReportTreeNode> createSchemeNode(FormSchemeDefine scheme) {
        ReportTreeNode data = new ReportTreeNode();
        data.setKey(scheme.getKey());
        data.setCode(scheme.getFormSchemeCode());
        data.setTitle(scheme.getTitle());
        data.setType("scheme");
        ITree node = new ITree((INode)data);
        node.setLeaf(false);
        node.setDisabled(true);
        node.setExpanded(true);
        this.typeMap.put(data.getKey(), "scheme");
        return node;
    }

    private List<FormSchemeDefine> getFormSchemes() {
        try {
            return this.runTimeViewController.queryFormSchemeByTask(this.formQueryHelper.getTaskKey());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

