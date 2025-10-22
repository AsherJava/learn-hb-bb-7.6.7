/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.BSGroupService
 *  com.jiuqi.nr.batch.summary.service.BSSchemeService
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryGroup
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 */
package com.jiuqi.bi.dataset.report.provider;

import com.jiuqi.bi.dataset.report.bean.SummaryTreeNode;
import com.jiuqi.nr.batch.summary.service.BSGroupService;
import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryGroup;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class SummaryTreeProvider {
    @Autowired
    private BSGroupService bsGroupService;
    @Autowired
    private BSSchemeService bsSchemeService;

    public List<ITree<SummaryTreeNode>> getChildrenByGroup(String taskKey, String groupKey) {
        ArrayList<ITree<SummaryTreeNode>> treeNodeList = new ArrayList<ITree<SummaryTreeNode>>();
        List childrenGroups = this.bsGroupService.findChildGroups(taskKey, groupKey);
        List childrenSchemes = this.bsSchemeService.findChildSchemeByGroup(taskKey, groupKey);
        if (!CollectionUtils.isEmpty(childrenGroups)) {
            childrenGroups.forEach(group -> treeNodeList.add(this.buildTreeNodeFromGroup((SummaryGroup)group)));
        }
        if (!CollectionUtils.isEmpty(childrenSchemes)) {
            childrenSchemes.forEach(scheme -> treeNodeList.add(this.buildTreeNodeFromScheme((SummaryScheme)scheme)));
        }
        return treeNodeList;
    }

    public String getSummarySchemeTitle(String taskKey, String schemeCode) {
        return this.bsSchemeService.findScheme(taskKey, schemeCode).getTitle();
    }

    private ITree<SummaryTreeNode> buildTreeNodeFromGroup(SummaryGroup summaryGroup) {
        ITree treeNode = new ITree((INode)new SummaryTreeNode(summaryGroup));
        treeNode.setIcons("#icon16_DH_A_NW_gongnengfenzushouqi");
        treeNode.setDisabled(true);
        return treeNode;
    }

    private ITree<SummaryTreeNode> buildTreeNodeFromScheme(SummaryScheme summaryScheme) {
        ITree treeNode = new ITree((INode)new SummaryTreeNode(summaryScheme));
        treeNode.setIcons("#icon-_GJZzidingyihuizong");
        treeNode.setLeaf(true);
        return treeNode;
    }
}

