/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.BSGroupService
 *  com.jiuqi.nr.batch.summary.service.BSSchemeService
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryGroup
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nvwa.resourceview.query.NodeType
 *  com.jiuqi.nvwa.resourceview.search.IResourceSearcher
 *  com.jiuqi.nvwa.resourceview.search.SearchResult
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.batch.gather.gzw.web.app;

import com.jiuqi.nr.batch.gather.gzw.web.app.context.BatchGatherGZWContextData;
import com.jiuqi.nr.batch.summary.service.BSGroupService;
import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryGroup;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nvwa.resourceview.query.NodeType;
import com.jiuqi.nvwa.resourceview.search.IResourceSearcher;
import com.jiuqi.nvwa.resourceview.search.SearchResult;
import com.jiuqi.util.StringUtils;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class BatchGatherSchemeResourceGZWSearcher
implements IResourceSearcher {
    private BSGroupService groupService;
    private BSSchemeService schemeService;
    private BatchGatherGZWContextData contextData;
    private List<TreeNode> tree = new ArrayList<TreeNode>();

    public BatchGatherSchemeResourceGZWSearcher(BatchGatherGZWContextData contextData, BSGroupService groupService, BSSchemeService schemeService) {
        this.contextData = contextData;
        this.groupService = groupService;
        this.schemeService = schemeService;
        this.init();
    }

    public List<SearchResult> search(String keyword) {
        ArrayList<SearchResult> rs = new ArrayList<SearchResult>();
        if (StringUtils.isNotEmpty((String)keyword)) {
            ArrayDeque nodeQue = new ArrayDeque();
            this.tree.forEach(nodeQue::addLast);
            while (!nodeQue.isEmpty()) {
                TreeNode node = (TreeNode)nodeQue.pollFirst();
                node.children.forEach(nodeQue::addLast);
                if (!node.nodeType.equals("scheme") || !this.isMatch(node.data, keyword)) continue;
                rs.add(node.getPathNode());
            }
        }
        return rs;
    }

    private void init() {
        String taskKey = this.contextData.getTaskId();
        HashMap<String, TreeNode> nodeMap = new HashMap<String, TreeNode>();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        List<TreeNode> childNodes = this.assignGroup2TreeNodes(this.groupService.findChildGroups(taskKey, "00000000-0000-0000-0000-000000000000"), stack, nodeMap);
        this.tree.addAll(childNodes);
        childNodes = this.assignScheme2TreeNodes(this.schemeService.findChildSchemeByGroup(taskKey, "00000000-0000-0000-0000-000000000000"), stack, nodeMap);
        this.tree.addAll(childNodes);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (!node.nodeType.equals("Group")) continue;
            childNodes = this.assignGroup2TreeNodes(this.groupService.findChildGroups(taskKey, node.key), stack, nodeMap);
            node.appendChildren(childNodes);
            childNodes = this.assignScheme2TreeNodes(this.schemeService.findChildSchemeByGroup(taskKey, node.key), stack, nodeMap);
            node.appendChildren(childNodes);
        }
    }

    private SearchResult assign2SearchResult(SummaryScheme scheme) {
        SearchResult result = new SearchResult();
        result.setName(scheme.getCode());
        result.setTitle(scheme.getTitle());
        result.setType(NodeType.NODE_DATA);
        result.setIcon("#icon-_GJZzidingyihuizong");
        result.setResTypeId("com.jiuqi.nr.batch.gather.gzw.web.app.scheme.resource.type");
        return result;
    }

    private SearchResult assign2SearchResult(SummaryGroup group) {
        SearchResult result = new SearchResult();
        result.setName("");
        result.setTitle(group.getTitle());
        result.setIcon("#icon16_DH_A_NW_gongnengfenzushouqi");
        result.setType(NodeType.NODE_GROUP);
        return result;
    }

    private TreeNode assign2TreeNode(SummaryScheme scheme) {
        TreeNode treeNode = new TreeNode(scheme.getKey(), this.assign2SearchResult(scheme));
        treeNode.setNodeType("scheme");
        return treeNode;
    }

    private TreeNode assign2TreeNode(SummaryGroup group) {
        TreeNode treeNode = new TreeNode(group.getKey(), this.assign2SearchResult(group));
        treeNode.setNodeType("Group");
        return treeNode;
    }

    private List<TreeNode> assignGroup2TreeNodes(List<SummaryGroup> groups, Stack<TreeNode> stack, Map<String, TreeNode> nodeMap) {
        List<TreeNode> treeNodes = groups.stream().map(this::assign2TreeNode).collect(Collectors.toList());
        treeNodes.forEach(stack::push);
        treeNodes.forEach(n -> nodeMap.put(((TreeNode)n).key, (TreeNode)n));
        return treeNodes;
    }

    private List<TreeNode> assignScheme2TreeNodes(List<SummaryScheme> schemes, Stack<TreeNode> stack, Map<String, TreeNode> nodeMap) {
        List<TreeNode> treeNodes = schemes.stream().map(this::assign2TreeNode).collect(Collectors.toList());
        treeNodes.forEach(stack::push);
        treeNodes.forEach(n -> nodeMap.put(((TreeNode)n).key, (TreeNode)n));
        return treeNodes;
    }

    private boolean isMatch(SearchResult record, String keyword) {
        return record.getName().contains(keyword.toUpperCase()) || record.getTitle().contains(keyword.toUpperCase());
    }

    private static class TreeNode {
        private String key;
        private SearchResult data;
        private TreeNode parent;
        private String nodeType;
        private List<TreeNode> children = new ArrayList<TreeNode>(0);

        public TreeNode(String key, SearchResult data) {
            this.key = key;
            this.data = data;
        }

        public SearchResult getPathNode() {
            this.data.setPathIds(this.getPathIds());
            this.data.setPathTitles(this.getPathTitle());
            return this.data;
        }

        public List<String> getPathIds() {
            TreeNode currParent = this.parent;
            ArrayList<String> ids = new ArrayList<String>();
            ids.add(this.key);
            while (currParent != null) {
                ids.add(0, currParent.key);
                currParent = currParent.parent;
            }
            return ids;
        }

        public List<String> getPathTitle() {
            ArrayList<String> titles = new ArrayList<String>();
            TreeNode currParent = this.parent;
            titles.add(this.data.getTitle());
            while (currParent != null) {
                titles.add(0, currParent.data.getTitle());
                currParent = currParent.parent;
            }
            return titles;
        }

        public void appendChildren(List<TreeNode> children) {
            children.forEach(n -> n.setParent(this));
            this.children.addAll(children);
        }

        public void setNodeType(String nodeType) {
            this.nodeType = nodeType;
        }

        public void setParent(TreeNode parent) {
            this.parent = parent;
        }
    }
}

