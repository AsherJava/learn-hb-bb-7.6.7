/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.authority.extend.SuperInheritPathProvider
 *  com.jiuqi.nvwa.authority.resource.Resource
 */
package com.jiuqi.nr.summary.authority;

import com.jiuqi.nr.summary.api.SummarySolutionGroup;
import com.jiuqi.nr.summary.authority.SummaryReportAuthUtil;
import com.jiuqi.nr.summary.internal.dto.DesignSummaryReportDTO;
import com.jiuqi.nr.summary.internal.dto.SummarySolutionDTO;
import com.jiuqi.nr.summary.internal.service.IDesignSummaryReportService;
import com.jiuqi.nr.summary.internal.service.ISummarySolutionGroupService;
import com.jiuqi.nr.summary.internal.service.ISummarySolutionService;
import com.jiuqi.nvwa.authority.extend.SuperInheritPathProvider;
import com.jiuqi.nvwa.authority.resource.Resource;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
public class SummaryReportInheritPathProvider
implements SuperInheritPathProvider {
    @Autowired
    private ISummarySolutionGroupService summarySolutionGroupService;
    @Autowired
    private ISummarySolutionService summarySolutionService;
    @Autowired
    private IDesignSummaryReportService designSummaryReportService;

    public String getResourceCategoryId() {
        return "summaryreport-auth-resource-category";
    }

    public Resource getParent(Object resource) {
        String resourceId = null;
        if (resource instanceof Resource) {
            resourceId = ((Resource)resource).getId();
        } else if (resource instanceof String) {
            resourceId = (String)resource;
        }
        if (StringUtils.hasLength(resourceId)) {
            String objectId = SummaryReportAuthUtil.toObjectId(resourceId);
            if (SummaryReportAuthUtil.isReport(resourceId)) {
                SummarySolutionDTO solution;
                DesignSummaryReportDTO summaryReport = this.designSummaryReportService.getSummaryReportByKey(objectId, false);
                if (!ObjectUtils.isEmpty(summaryReport) && !ObjectUtils.isEmpty(solution = this.summarySolutionService.getSummarySolutionByKey(summaryReport.getSummarySolutionKey(), false))) {
                    return SummaryReportAuthUtil.createResource(solution);
                }
            } else if (SummaryReportAuthUtil.isSolution(resourceId)) {
                SummarySolutionGroup solutionGroup;
                SummarySolutionDTO solution = this.summarySolutionService.getSummarySolutionByKey(objectId, false);
                if (!ObjectUtils.isEmpty(solution) && StringUtils.hasLength(solution.getGroup()) && !ObjectUtils.isEmpty(solutionGroup = this.summarySolutionGroupService.getSummarySolutionGroupByKey(solution.getGroup()))) {
                    return SummaryReportAuthUtil.createResource(solutionGroup);
                }
            } else {
                SummarySolutionGroup parentSolutionGroup;
                SummarySolutionGroup solutionGroup = this.summarySolutionGroupService.getSummarySolutionGroupByKey(objectId);
                if (!ObjectUtils.isEmpty(solutionGroup) && StringUtils.hasLength(solutionGroup.getParent()) && !ObjectUtils.isEmpty(parentSolutionGroup = this.summarySolutionGroupService.getSummarySolutionGroupByKey(solutionGroup.getParent()))) {
                    return SummaryReportAuthUtil.createResource(parentSolutionGroup);
                }
            }
        }
        return null;
    }

    public Set<String> computeIfChildrens(String resourceId, Set<String> resourceIds) {
        return this.getChildren(resourceId, resourceIds);
    }

    private Set<String> getChildren(String resourceId, Set<String> resourceIds) {
        HashSet<String> res = new HashSet<String>();
        String objectId = SummaryReportAuthUtil.toObjectId(resourceId);
        if (StringUtils.hasLength(resourceId) && SummaryReportAuthUtil.isSolution(resourceId)) {
            this.designSummaryReportService.getSummaryReportsBySolution(objectId, false).forEach(report -> {
                String resourceId1 = SummaryReportAuthUtil.toResourceId(report);
                if (resourceIds.contains(resourceId1)) {
                    res.add(resourceId1);
                }
            });
        } else if (StringUtils.hasLength(resourceId) && SummaryReportAuthUtil.isGroup(resourceId)) {
            this.summarySolutionGroupService.getSummarySolutionGroupsByGroup(objectId).forEach(solutionGroup -> {
                String resourceId1 = SummaryReportAuthUtil.toResourceId(solutionGroup);
                if (resourceIds.contains(resourceId1)) {
                    res.add(resourceId1);
                    res.addAll(this.getChildren(resourceId1, resourceIds));
                }
            });
            this.summarySolutionService.getSummarySolutionsByGroup(objectId, false).forEach(solution -> {
                String resourceId1 = SummaryReportAuthUtil.toResourceId(solution);
                if (resourceIds.contains(resourceId1)) {
                    res.add(resourceId1);
                    res.addAll(this.getChildren(resourceId1, resourceIds));
                }
            });
        }
        return res;
    }
}

