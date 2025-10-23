/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.manage.provider;

import com.jiuqi.nr.summary.api.service.IDesignSummarySolutionService;
import com.jiuqi.nr.summary.api.service.IRuntimeSummarySolutionService;
import com.jiuqi.nr.summary.model.group.SummarySolutionGroup;
import com.jiuqi.nr.summary.model.report.SummaryReport;
import com.jiuqi.nr.summary.model.soulution.SummarySolution;
import com.jiuqi.nr.summary.vo.CustomNodeData;
import com.jiuqi.nr.summary.vo.NodeType;
import com.jiuqi.nr.summary.vo.ResourceNode;
import com.jiuqi.nr.summary.vo.SummarySolutionNodeData;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResourceNodeProvider {
    @Autowired
    private IDesignSummarySolutionService designSolutionService;
    @Autowired
    private IRuntimeSummarySolutionService runtimeSolutionService;

    public List<ResourceNode> getNodes(int type, String parentKey) {
        ArrayList<ResourceNode> nodes = new ArrayList<ResourceNode>();
        if (type == 0) {
            List summarySolutionGroupNodes = this.designSolutionService.getSummarySolutionGroupByGroup(parentKey).stream().map(this::buildFromSummarySolutionGroup).sorted(Comparator.comparing(ResourceNode::getModifyTime)).collect(Collectors.toList());
            List summarySolutionNodes = this.designSolutionService.getSummarySolutionsByGroup(parentKey).stream().map(this::buildFromSummarySolution).sorted(Comparator.comparing(ResourceNode::getModifyTime)).collect(Collectors.toList());
            nodes.addAll(summarySolutionGroupNodes);
            nodes.addAll(summarySolutionNodes);
        } else {
            List runtimeReportKey = this.runtimeSolutionService.getSummaryReportDefinesBySolu(parentKey).stream().map(SummaryReport::getKey).collect(Collectors.toList());
            List summaryReportNodes = this.designSolutionService.getSummaryReportsBySolution(parentKey).stream().map(report -> this.buildFromSummaryReport((SummaryReport)report, runtimeReportKey)).collect(Collectors.toList());
            nodes.addAll(summaryReportNodes);
        }
        return nodes;
    }

    private ResourceNode buildFromSummarySolutionGroup(SummarySolutionGroup summarySolutionGroup) {
        CustomNodeData nodeData = new CustomNodeData(NodeType.SUMMARY_SOLUTION_GROUP);
        nodeData.setDeployed(true);
        ResourceNode resourceNode = new ResourceNode(summarySolutionGroup.getKey(), null, summarySolutionGroup.getTitle(), NodeType.SUMMARY_SOLUTION_GROUP, summarySolutionGroup.getModifyTime());
        resourceNode.setData(nodeData);
        return resourceNode;
    }

    private ResourceNode buildFromSummarySolution(SummarySolution summarySolution) {
        ResourceNode resourceNode = new ResourceNode(summarySolution.getKey(), summarySolution.getName(), summarySolution.getTitle(), NodeType.SUMMARY_SOLUTION, summarySolution.getModifyTime());
        SummarySolutionNodeData nodeData = new SummarySolutionNodeData(summarySolution.getMainTask());
        nodeData.setDeployed(true);
        resourceNode.setData(nodeData);
        return resourceNode;
    }

    private ResourceNode buildFromSummaryReport(SummaryReport summaryReport, List<String> runtimeReportKey) {
        CustomNodeData reportNodeData = new CustomNodeData(NodeType.SUMMARY_REPORT);
        reportNodeData.setDeployed(runtimeReportKey.contains(summaryReport.getKey()));
        ResourceNode resourceNode = new ResourceNode(summaryReport.getKey(), summaryReport.getName(), summaryReport.getTitle(), NodeType.SUMMARY_REPORT, summaryReport.getModifyTime());
        resourceNode.setOrder(summaryReport.getOrder());
        resourceNode.setData(reportNodeData);
        return resourceNode;
    }
}

