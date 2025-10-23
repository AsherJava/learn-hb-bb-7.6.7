/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bsp.contentcheckrules.beans.CheckGroupingTreeVO
 *  com.jiuqi.bsp.contentcheckrules.beans.CheckGroupingVO
 *  com.jiuqi.bsp.contentcheckrules.service.CheckGroupingService
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.multcheck2.service.impl;

import com.jiuqi.bsp.contentcheckrules.beans.CheckGroupingTreeVO;
import com.jiuqi.bsp.contentcheckrules.beans.CheckGroupingVO;
import com.jiuqi.bsp.contentcheckrules.service.CheckGroupingService;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.multcheck2.service.IMCRuleService;
import com.jiuqi.nr.multcheck2.web.vo.OrgTreeNode;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class MCRuleServiceImple
implements IMCRuleService {
    @Autowired
    private CheckGroupingService groupService;

    @Override
    public List<ITree<OrgTreeNode>> buildRuleGroupTree() {
        ArrayList<ITree<OrgTreeNode>> tree = new ArrayList<ITree<OrgTreeNode>>();
        List allGroups = this.groupService.getGroupingTree();
        OrgTreeNode root = new OrgTreeNode();
        root.setKey("-");
        root.setCode("-");
        root.setTitle("\u89c4\u5219\u5206\u7ec4");
        ITree rootNode = new ITree((INode)root);
        ArrayList<ITree<OrgTreeNode>> children = new ArrayList<ITree<OrgTreeNode>>();
        rootNode.setChildren(children);
        rootNode.setIcons(new String[]{"#icon14_SHU_A_NW_tubiaokezhankai"});
        rootNode.setExpanded(true);
        this.buildTree(children, allGroups);
        tree.add(rootNode);
        return tree;
    }

    private void buildTree(List<ITree<OrgTreeNode>> children, List<CheckGroupingTreeVO> allGroups) {
        if (CollectionUtils.isEmpty(allGroups)) {
            return;
        }
        for (CheckGroupingTreeVO group : allGroups) {
            OrgTreeNode node = new OrgTreeNode();
            node.setKey(group.getKey());
            node.setCode(group.getCode());
            node.setTitle(group.getTitle());
            ITree treeNode = new ITree((INode)node);
            treeNode.setIcons(new String[]{"#icon14_SHU_A_NW_tubiaokezhankai"});
            List childrenGroup = group.getCheckGroupingTreeVOList();
            if (!CollectionUtils.isEmpty(childrenGroup)) {
                ArrayList<ITree<OrgTreeNode>> childrenNode = new ArrayList<ITree<OrgTreeNode>>();
                treeNode.setChildren(childrenNode);
                this.buildTree(childrenNode, childrenGroup);
            } else {
                treeNode.setLeaf(true);
            }
            children.add((ITree<OrgTreeNode>)treeNode);
        }
    }

    @Override
    public CheckGroupingVO getRuleGroupByKey(String key) {
        return this.groupService.getCheckGrouping(key);
    }
}

