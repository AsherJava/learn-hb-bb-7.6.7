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
import com.jiuqi.nr.form.selector.context.IFormQueryHelper;
import com.jiuqi.nr.form.selector.tree.IReportFormTreeProvider;
import com.jiuqi.nr.form.selector.tree.IReportTreeNode;
import com.jiuqi.nr.form.selector.tree.impl.ReportFormNode;
import com.jiuqi.nr.form.selector.tree.impl.ReportFormTreeData;
import com.jiuqi.nr.form.selector.tree.impl.ReportGroupNode;
import java.util.ArrayList;
import java.util.List;

public class ReportFormTreeDataWithFormula
extends ReportFormTreeData {
    public static final String SOURCE_ID = "formula";
    private IFormQueryHelper formQueryHelper;

    public ReportFormTreeDataWithFormula(IFormQueryHelper formQueryHelper, IReportFormTreeProvider provider, List<String> checkList) {
        super(provider, checkList);
        this.formQueryHelper = formQueryHelper;
    }

    @Override
    public List<ITree<IReportTreeNode>> getRoots(String formSchemeKey) {
        ArrayList<ITree<IReportTreeNode>> roots = new ArrayList<ITree<IReportTreeNode>>();
        roots.add(this.createTotalFormGroupNode());
        return roots;
    }

    @Override
    public List<ITree<IReportTreeNode>> getChildren(String groupKey) {
        ArrayList<ITree<IReportTreeNode>> children = new ArrayList<ITree<IReportTreeNode>>();
        if ("L8ZMDT3I".equals(groupKey)) {
            children.add(this.createBetweenFormGroupNode());
            List<FormGroupDefine> groups = this.provider.getGroups(this.formQueryHelper.getFormSchemeKey());
            if (groups != null) {
                groups.forEach(group -> children.add(this.createGroupNode((FormGroupDefine)group)));
            }
        } else {
            List<FormDefine> forms = this.provider.getForms(groupKey);
            if (forms != null) {
                forms.forEach(form -> children.add(this.createFormNode((FormDefine)form)));
            }
        }
        return children;
    }

    private ITree<IReportTreeNode> createTotalFormGroupNode() {
        ReportGroupNode totalFormGroupData = new ReportGroupNode();
        totalFormGroupData.setKey("L8ZMDT3I");
        totalFormGroupData.setCode("L8ZMDT3I");
        totalFormGroupData.setTitle("\u6240\u6709\u516c\u5f0f");
        totalFormGroupData.setType("form_with_all");
        ITree totalFormNode = new ITree((INode)totalFormGroupData);
        totalFormNode.setExpanded(true);
        return totalFormNode;
    }

    private ITree<IReportTreeNode> createBetweenFormGroupNode() {
        ReportFormNode betweenFormData = new ReportFormNode();
        betweenFormData.setKey("00000000-0000-0000-0000-000000000000");
        betweenFormData.setCode("L8ZMFAGT");
        betweenFormData.setTitle("\u8868\u95f4\u516c\u5f0f");
        betweenFormData.setType("form_with_between");
        ITree betweenFormNode = new ITree((INode)betweenFormData);
        betweenFormNode.setChecked(this.isChecked(betweenFormData.getKey()));
        betweenFormNode.setLeaf(true);
        return betweenFormNode;
    }
}

