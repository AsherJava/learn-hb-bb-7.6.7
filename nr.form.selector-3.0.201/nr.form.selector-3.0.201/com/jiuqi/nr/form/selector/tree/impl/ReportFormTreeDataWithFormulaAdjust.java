/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 */
package com.jiuqi.nr.form.selector.tree.impl;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.form.selector.context.IFormQueryHelper;
import com.jiuqi.nr.form.selector.tree.IReportFormTreeProvider;
import com.jiuqi.nr.form.selector.tree.IReportTreeNode;
import com.jiuqi.nr.form.selector.tree.impl.ReportFormNode;
import com.jiuqi.nr.form.selector.tree.impl.ReportFormTreeDataWithFormula;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.env.Environment;

public class ReportFormTreeDataWithFormulaAdjust
extends ReportFormTreeDataWithFormula {
    private IFormQueryHelper formQueryHelper;

    public ReportFormTreeDataWithFormulaAdjust(IFormQueryHelper formQueryHelper, IReportFormTreeProvider provider, List<String> checkList) {
        super(formQueryHelper, provider, checkList);
        this.formQueryHelper = formQueryHelper;
    }

    @Override
    public List<ITree<IReportTreeNode>> getChildren(String groupKey) {
        ArrayList<ITree<IReportTreeNode>> children = new ArrayList<ITree<IReportTreeNode>>();
        Environment environment = (Environment)SpringBeanUtils.getBean(Environment.class);
        String isOpenBetweenformula = environment.getProperty("jiuqi.nr.dataentry.between-formula");
        if (isOpenBetweenformula == null) {
            isOpenBetweenformula = "false";
        }
        if ("L8ZMDT3I".equals(groupKey)) {
            List<FormGroupDefine> groups;
            if (isOpenBetweenformula.equals("true")) {
                children.add(this.createBetweenFormGroupNode());
            }
            if ((groups = this.provider.getGroups(this.formQueryHelper.getFormSchemeKey())) != null) {
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

