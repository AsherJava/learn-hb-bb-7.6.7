/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException
 *  com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode
 *  com.jiuqi.nvwa.dataanalyze.service.ResourceTreeNodeService
 */
package com.jiuqi.nr.designer.reporttag.helper;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.designer.reporttag.rest.vo.QuickReportNode;
import com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException;
import com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode;
import com.jiuqi.nvwa.dataanalyze.service.ResourceTreeNodeService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class QuickReportHelper {
    private static final Logger logger = LoggerFactory.getLogger(QuickReportHelper.class);
    @Autowired
    private ResourceTreeNodeService resourceTreeNodeService;

    public List<ITree<QuickReportNode>> getChildren(String parent) {
        List<String> types = QuickReportHelper.getTypes();
        ArrayList<ITree<QuickReportNode>> result = new ArrayList<ITree<QuickReportNode>>();
        try {
            List children = this.resourceTreeNodeService.getChildren(parent, types);
            children.forEach(o -> result.add(this.getQuickReportTreeNode((ResourceTreeNode)o)));
        }
        catch (DataAnalyzeResourceException e) {
            logger.error("\u5206\u6790\u8868\u8d44\u6e90\u83b7\u53d6\u5f02\u5e38:" + e.getMessage(), e);
        }
        return result;
    }

    private static List<String> getTypes() {
        ArrayList<String> types = new ArrayList<String>();
        types.add("com.jiuqi.nvwa.quickreport.business");
        return types;
    }

    private ITree<QuickReportNode> getQuickReportTreeNode(ResourceTreeNode resourceTreeNode) {
        QuickReportNode quickReportNode = new QuickReportNode(resourceTreeNode);
        ITree node = new ITree((INode)quickReportNode);
        node.setLeaf(quickReportNode.getNodeType() == QuickReportNode.NodeType.QUICK_REPORT);
        node.setNoDrop(true);
        node.setNoDrag(false);
        node.setIcons(new String[]{quickReportNode.getIcon()});
        return node;
    }

    public List<ITree<QuickReportNode>> locate(String guid) {
        List<ITree<QuickReportNode>> rootChildren = this.getRootChildren();
        try {
            if (StringUtils.hasText(guid)) {
                List path = this.resourceTreeNodeService.getPath(guid);
                this.appendPathNode(rootChildren, path, guid);
            }
        }
        catch (DataAnalyzeResourceException e) {
            logger.error("\u5206\u6790\u8868\u8282\u70b9\u8def\u5f84\u83b7\u53d6\u5f02\u5e38:" + e.getMessage(), e);
        }
        return rootChildren;
    }

    private void appendPathNode(List<ITree<QuickReportNode>> rootChildren, List<ResourceTreeNode> path, String target) {
        if (!CollectionUtils.isEmpty(path)) {
            List<ITree<QuickReportNode>> cur = rootChildren;
            for (ResourceTreeNode resourceTreeNode : path) {
                cur = this.loadNext(cur, resourceTreeNode.getGuid(), target);
            }
        }
    }

    private List<ITree<QuickReportNode>> loadNext(List<ITree<QuickReportNode>> cur, String curPathNode, String target) {
        List<ITree<QuickReportNode>> children = this.getChildren(curPathNode);
        children.forEach(o -> {
            if (o.getKey().equals(target)) {
                o.setSelected(true);
            }
        });
        for (ITree<QuickReportNode> c : cur) {
            if (!c.getKey().equals(curPathNode)) continue;
            c.setExpanded(true);
            c.setChildren(children);
            return children;
        }
        return cur;
    }

    private List<ITree<QuickReportNode>> getRootChildren() {
        return this.getChildren("root");
    }

    public List<QuickReportNode> getAllNodes() {
        ArrayList<QuickReportNode> result = new ArrayList<QuickReportNode>();
        try {
            List allResourceTreeNodes = this.resourceTreeNodeService.getAllResourceTreeNodes(QuickReportHelper.getTypes());
            allResourceTreeNodes.forEach(o -> result.add(new QuickReportNode((ResourceTreeNode)o)));
        }
        catch (DataAnalyzeResourceException e) {
            logger.error("\u83b7\u53d6\u6240\u6709\u5206\u6790\u8868\u8282\u70b9\u6570\u636e\u5f02\u5e38:" + e.getMessage(), e);
        }
        return result;
    }
}

