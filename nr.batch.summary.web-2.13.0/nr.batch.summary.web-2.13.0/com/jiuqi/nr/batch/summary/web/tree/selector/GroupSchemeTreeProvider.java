/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.BSGroupService
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryGroup
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.itreebase.node.BaseNodeDataImpl
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nvwa.resourceview.query.NodeType
 *  com.jiuqi.nvwa.resourceview.query.ResourceNode
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batch.summary.web.tree.selector;

import com.jiuqi.nr.batch.summary.service.BSGroupService;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryGroup;
import com.jiuqi.nr.batch.summary.web.app.func.para.OpenGroupTreePagePara;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nvwa.resourceview.query.NodeType;
import com.jiuqi.nvwa.resourceview.query.ResourceNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class GroupSchemeTreeProvider {
    @Resource
    private BSGroupService groupService;

    public List<ITree<IBaseNodeData>> getGroupTree(OpenGroupTreePagePara loadPara) {
        ArrayList<ITree<IBaseNodeData>> tree = new ArrayList<ITree<IBaseNodeData>>();
        String taskKey = loadPara.getTaskId();
        Set<String> unCheckSet = this.getUnCheckSet(loadPara.getCheckTableRow());
        Stack<ITree<IBaseNodeData>> stack = new Stack<ITree<IBaseNodeData>>();
        ITree<IBaseNodeData> node = this.buildRootNode();
        List<ITree<IBaseNodeData>> childNodes = this.assignGroup2TreeNodes(this.groupService.findChildGroups(taskKey, "00000000-0000-0000-0000-000000000000"), stack, unCheckSet);
        node.setChildren(childNodes);
        tree.add(node);
        while (!stack.isEmpty()) {
            node = stack.pop();
            childNodes = this.assignGroup2TreeNodes(this.groupService.findChildGroups(taskKey, node.getKey()), stack, unCheckSet);
            node.setChildren(childNodes.stream().filter(n -> !unCheckSet.contains(n.getKey())).collect(Collectors.toList()));
            node.setLeaf(node.getChildren().isEmpty());
            node.setExpanded(true);
        }
        return tree;
    }

    private List<ITree<IBaseNodeData>> assignGroup2TreeNodes(List<SummaryGroup> groups, Stack<ITree<IBaseNodeData>> stack, Set<String> unCheckSet) {
        List filterGroup = groups.stream().filter(g -> !unCheckSet.contains(g.getKey())).collect(Collectors.toList());
        List<ITree<IBaseNodeData>> treeNodes = filterGroup.stream().map(this::buildNode).collect(Collectors.toList());
        treeNodes.forEach(stack::push);
        return treeNodes;
    }

    private ITree<IBaseNodeData> buildNode(SummaryGroup group) {
        BaseNodeDataImpl data = new BaseNodeDataImpl();
        data.setKey(group.getKey());
        data.setCode("-");
        data.setTitle(group.getTitle());
        ITree node = new ITree((INode)data);
        node.setIcons(new String[]{"#icon16_DH_A_NW_gongnengfenzushouqi"});
        return node;
    }

    private ITree<IBaseNodeData> buildRootNode() {
        BaseNodeDataImpl data = new BaseNodeDataImpl();
        data.setKey("00000000-0000-0000-0000-000000000000");
        data.setCode("00000000-0000-0000-0000-000000000000");
        data.setTitle("\u6211\u7684\u5206\u7c7b\u6c47\u603b\u65b9\u6848");
        ITree node = new ITree((INode)data);
        node.setIcons(new String[]{"#icon16_SHU_A_NW_yingyongfuwu"});
        node.setLeaf(false);
        node.setExpanded(true);
        return node;
    }

    private Set<String> getUnCheckSet(List<ResourceNode> checkTableRow) {
        return checkTableRow.stream().filter(this::isGroupNode).map(ResourceNode::getId).collect(Collectors.toSet());
    }

    private boolean isGroupNode(ResourceNode rsNode) {
        return NodeType.NODE_GROUP == rsNode.getType();
    }
}

