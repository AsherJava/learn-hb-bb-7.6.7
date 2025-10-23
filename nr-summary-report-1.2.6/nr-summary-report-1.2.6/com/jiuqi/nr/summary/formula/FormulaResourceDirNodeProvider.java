/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.formula;

import com.jiuqi.nr.summary.api.service.IDesignSummarySolutionService;
import com.jiuqi.nr.summary.model.report.SummaryReport;
import com.jiuqi.nr.summary.vo.CustomNodeData;
import com.jiuqi.nr.summary.vo.NodeType;
import com.jiuqi.nr.summary.vo.TreeNode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormulaResourceDirNodeProvider {
    @Autowired
    private IDesignSummarySolutionService designSolutionService;

    public List<TreeNode> getChilds(String parentKey) {
        ArrayList<TreeNode> nodes = new ArrayList<TreeNode>();
        nodes.addAll(this.getSummaryReports(parentKey));
        return nodes;
    }

    private List<TreeNode> getSummaryReports(String solutionKey) {
        return this.designSolutionService.getSummaryReportsBySolution(solutionKey).stream().map(this::buildSummaryReportNode).collect(Collectors.toList());
    }

    private TreeNode buildSummaryReportNode(SummaryReport summaryReport) {
        TreeNode treeNode = new TreeNode(summaryReport.getKey(), summaryReport.getTitle());
        CustomNodeData nodeData = new CustomNodeData(NodeType.SUMMARY_REPORT);
        nodeData.setKey(summaryReport.getKey());
        nodeData.setTitle(summaryReport.getTitle());
        nodeData.setCode(summaryReport.getName());
        nodeData.setParentTitle(summaryReport.getSolution());
        treeNode.setData(nodeData);
        treeNode.setLeaf(true);
        return treeNode;
    }
}

