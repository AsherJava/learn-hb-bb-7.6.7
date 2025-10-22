/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 */
package com.jiuqi.nr.system.check.service.impl;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.system.check.model.response.tree.FormTreeNode;
import com.jiuqi.nr.system.check.service.SCFormTreeService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SCFormTreeServiceImpl
implements SCFormTreeService {
    @Autowired
    private IRunTimeViewController iRunTimeViewController;

    private ITree<FormTreeNode> getTreeNode(FormGroupDefine formGroupDefine) {
        ITree node = new ITree((INode)new FormTreeNode(formGroupDefine));
        node.setLeaf(false);
        node.setNoDrag(true);
        node.setNoDrop(true);
        return node;
    }

    private ITree<FormTreeNode> getTreeNode(FormDefine formDefine, String formGroupKey) {
        ITree node = new ITree((INode)new FormTreeNode(formDefine, formGroupKey));
        node.setLeaf(true);
        node.setNoDrag(true);
        node.setNoDrop(true);
        return node;
    }

    @Override
    public List<ITree<FormTreeNode>> createFormTree(String schemeKey) throws Exception {
        ArrayList<ITree<FormTreeNode>> formTree = new ArrayList<ITree<FormTreeNode>>();
        List formGroupDefines = this.iRunTimeViewController.getAllFormGroupsInFormScheme(schemeKey);
        if (formGroupDefines != null) {
            for (FormGroupDefine formGroupDefine : formGroupDefines) {
                String formGroupKey = formGroupDefine.getKey();
                ITree<FormTreeNode> formTreeGroupNode = this.getTreeNode(formGroupDefine);
                List formDefines = this.iRunTimeViewController.getAllFormsInGroup(formGroupKey);
                if (formDefines != null) {
                    ArrayList<ITree<FormTreeNode>> formGroupChildNodes = new ArrayList<ITree<FormTreeNode>>();
                    for (FormDefine formDefine : formDefines) {
                        ITree<FormTreeNode> formTreeNode = this.getTreeNode(formDefine, formGroupKey);
                        formGroupChildNodes.add(formTreeNode);
                    }
                    formTreeGroupNode.setChildren(formGroupChildNodes);
                }
                formTree.add(formTreeGroupNode);
            }
        }
        return formTree;
    }
}

