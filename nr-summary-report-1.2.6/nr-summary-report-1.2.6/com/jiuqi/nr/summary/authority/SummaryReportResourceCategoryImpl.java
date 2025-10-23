/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.authority.extend.DefaultAuthQueryService
 *  com.jiuqi.nvwa.authority.extend.DefaultResourceCategory
 *  com.jiuqi.nvwa.authority.privilege.PrivilegeDefinition
 *  com.jiuqi.nvwa.authority.privilege.PrivilegeDefinitionItem
 *  com.jiuqi.nvwa.authority.resource.Resource
 *  com.jiuqi.nvwa.authority.resource.ResourceSearchResult
 *  com.jiuqi.nvwa.authority.util.AuthorityConst$AuthzRightAreaPlan
 *  com.jiuqi.nvwa.authority.util.AuthorityConst$Category_Type
 *  com.jiuqi.nvwa.authority.vo.GranteeInfo
 */
package com.jiuqi.nr.summary.authority;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.summary.api.SummaryReport;
import com.jiuqi.nr.summary.api.SummarySolution;
import com.jiuqi.nr.summary.api.SummarySolutionGroup;
import com.jiuqi.nr.summary.authority.SummaryReportAuthUtil;
import com.jiuqi.nr.summary.internal.dto.DesignSummaryReportDTO;
import com.jiuqi.nr.summary.internal.dto.SummarySolutionDTO;
import com.jiuqi.nr.summary.internal.service.IDesignSummaryReportService;
import com.jiuqi.nr.summary.internal.service.ISummarySolutionGroupService;
import com.jiuqi.nr.summary.internal.service.ISummarySolutionService;
import com.jiuqi.nr.summary.service.ISummarySearchService;
import com.jiuqi.nr.summary.utils.SummarySearchUtil;
import com.jiuqi.nr.summary.vo.search.SummarySearchItem;
import com.jiuqi.nr.summary.vo.search.SummarySearchRequestParam;
import com.jiuqi.nr.summary.vo.search.SummarySearchResultType;
import com.jiuqi.nvwa.authority.extend.DefaultAuthQueryService;
import com.jiuqi.nvwa.authority.extend.DefaultResourceCategory;
import com.jiuqi.nvwa.authority.privilege.PrivilegeDefinition;
import com.jiuqi.nvwa.authority.privilege.PrivilegeDefinitionItem;
import com.jiuqi.nvwa.authority.resource.Resource;
import com.jiuqi.nvwa.authority.resource.ResourceSearchResult;
import com.jiuqi.nvwa.authority.util.AuthorityConst;
import com.jiuqi.nvwa.authority.vo.GranteeInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SummaryReportResourceCategoryImpl
extends DefaultResourceCategory {
    private static final long serialVersionUID = -3297605305678096868L;
    @Autowired
    private DefaultAuthQueryService defaultAuthQueryService;
    @Autowired
    private ISummarySolutionGroupService summarySolutionGroupService;
    @Autowired
    private ISummarySolutionService summarySolutionService;
    @Autowired
    private IDesignSummaryReportService designSummaryReportService;
    @Autowired
    private ISummarySearchService summarySearchService;
    @Autowired
    private SummarySearchUtil summarySearchUtil;

    public String getId() {
        return "summaryreport-auth-resource-category";
    }

    public String getTitle() {
        return "\u81ea\u5b9a\u4e49\u6c47\u603b";
    }

    public String getGroupTitle() {
        return "\u62a5\u8868";
    }

    public AuthorityConst.AuthzRightAreaPlan getAuthRightAreaPlan() {
        return AuthorityConst.AuthzRightAreaPlan.All;
    }

    public AuthorityConst.Category_Type getCategoryType() {
        return AuthorityConst.Category_Type.NORMAL;
    }

    public List<String> getBasePrivilegeIds() {
        return Collections.singletonList("summaryreport_auth_resource_read");
    }

    public List<PrivilegeDefinition> getPrivilegeDefinition(GranteeInfo granteeInfo) {
        ArrayList<PrivilegeDefinition> list = new ArrayList<PrivilegeDefinition>();
        PrivilegeDefinitionItem item = new PrivilegeDefinitionItem();
        item.setPrivilegeId("22222222-1111-1111-1111-222222222222");
        item.setPrivilegeTitle("\u540c\u4e0a\u7ea7");
        list.add((PrivilegeDefinition)item);
        item = new PrivilegeDefinitionItem();
        item.setPrivilegeId("summaryreport_auth_resource_manage");
        item.setPrivilegeTitle("\u7ba1\u7406");
        list.add((PrivilegeDefinition)item);
        item = new PrivilegeDefinitionItem();
        item.setPrivilegeId("summaryreport_auth_resource_sum");
        item.setPrivilegeTitle("\u6c47\u603b");
        list.add((PrivilegeDefinition)item);
        item = new PrivilegeDefinitionItem();
        item.setPrivilegeId("summaryreport_auth_resource_read");
        item.setPrivilegeTitle("\u67e5\u770b");
        list.add((PrivilegeDefinition)item);
        return list;
    }

    public List<Resource> getRootResources(GranteeInfo granteeInfo) {
        return this.getResources(null);
    }

    public List<Resource> getChildResources(String resourceGroupId, GranteeInfo granteeInfo) {
        return this.getResources(resourceGroupId);
    }

    private List<Resource> getResources(String resourceGroupId) {
        ArrayList<Resource> list = new ArrayList<Resource>();
        String objectId = SummaryReportAuthUtil.toObjectId(resourceGroupId);
        if (StringUtils.hasLength(resourceGroupId) && SummaryReportAuthUtil.isSolution(resourceGroupId)) {
            this.designSummaryReportService.getSummaryReportsBySolution(objectId, false).forEach(report -> {
                if (this.hasReadAuth(SummaryReportAuthUtil.toResourceId(report))) {
                    list.add((Resource)SummaryReportAuthUtil.createResource(report));
                }
            });
        } else {
            this.summarySolutionGroupService.getSummarySolutionGroupsByGroup(objectId).forEach(solutionGroup -> {
                if (this.hasReadAuth(SummaryReportAuthUtil.toResourceId(solutionGroup))) {
                    list.add((Resource)SummaryReportAuthUtil.createResource(solutionGroup));
                }
            });
            this.summarySolutionService.getSummarySolutionsByGroup(objectId, false).forEach(solution -> {
                if (this.hasReadAuth(SummaryReportAuthUtil.toResourceId(solution))) {
                    list.add((Resource)SummaryReportAuthUtil.createResource(solution));
                }
            });
        }
        return list;
    }

    private boolean hasReadAuth(String resourceId) {
        return this.defaultAuthQueryService.hasDelegateAuth("summaryreport_auth_resource_read", NpContextHolder.getContext().getIdentityId(), (Object)resourceId);
    }

    public boolean isSupportReject() {
        return true;
    }

    public boolean enableSearch() {
        return true;
    }

    public List<ResourceSearchResult> searchResource(String fuzzyTitle, String key) {
        ArrayList<ResourceSearchResult> resultList = new ArrayList<ResourceSearchResult>();
        ArrayList<SummarySearchItem> searchItems = new ArrayList<SummarySearchItem>();
        if (StringUtils.hasLength(key)) {
            HashMap<String, SummarySolutionGroup> groupCaches = new HashMap<String, SummarySolutionGroup>();
            HashMap<String, SummarySolution> solutionCaches = new HashMap<String, SummarySolution>();
            HashMap<String, SummaryReport> reportCaches = new HashMap<String, SummaryReport>();
            String objectId = SummaryReportAuthUtil.toObjectId(key);
            if (SummaryReportAuthUtil.isSolution(key)) {
                SummarySolutionDTO summarySolution = this.summarySolutionService.getSummarySolutionByKey(objectId, false);
                searchItems.addAll(this.summarySearchUtil.buildSoluSearchItems(Arrays.asList(summarySolution), groupCaches, solutionCaches));
            } else if (SummaryReportAuthUtil.isReport(key)) {
                DesignSummaryReportDTO summaryReport = this.designSummaryReportService.getSummaryReportByKey(objectId, false);
                searchItems.addAll(this.summarySearchUtil.buildReportSearchItems(Arrays.asList(summaryReport), groupCaches, solutionCaches, reportCaches));
            } else {
                SummarySolutionGroup summarySolutionGroup = this.summarySolutionGroupService.getSummarySolutionGroupByKey(objectId);
                searchItems.addAll(this.summarySearchUtil.buildSoluGroupSearchItems(Arrays.asList(summarySolutionGroup), groupCaches));
            }
        } else {
            SummarySearchRequestParam searchReqParam = new SummarySearchRequestParam();
            searchReqParam.setKeywords(fuzzyTitle);
            searchReqParam.setWithGroup(true);
            searchReqParam.setWithSolution(true);
            searchReqParam.setWithReport(true);
            searchItems.addAll(this.summarySearchService.search(searchReqParam));
        }
        searchItems.forEach(searchItem -> resultList.add(this.Item2Result((SummarySearchItem)searchItem)));
        return resultList;
    }

    private ResourceSearchResult Item2Result(SummarySearchItem item) {
        ResourceSearchResult result = new ResourceSearchResult();
        String key = item.getKey();
        SummarySearchResultType type = item.getType().get(0);
        List<String> pathIds = item.getPathIds();
        ArrayList<String> path = new ArrayList<String>();
        if (type == SummarySearchResultType.SUMMARY_SOLUTION) {
            key = SummaryReportAuthUtil.toResourceIdForSolution(key);
            for (int i = 0; i < pathIds.size(); ++i) {
                String pathId = pathIds.get(i);
                if (i == pathIds.size() - 1) {
                    path.add(SummaryReportAuthUtil.toResourceIdForSolution(pathId));
                    continue;
                }
                path.add(SummaryReportAuthUtil.toResourceIdForGroup(pathId));
            }
        } else if (type == SummarySearchResultType.SUMMARY_REPORT) {
            key = SummaryReportAuthUtil.toResourceIdForReport(key);
            for (int i = 0; i < pathIds.size(); ++i) {
                String pathId = pathIds.get(i);
                if (i == pathIds.size() - 1) {
                    path.add(SummaryReportAuthUtil.toResourceIdForReport(pathId));
                    continue;
                }
                if (i == pathIds.size() - 2) {
                    path.add(SummaryReportAuthUtil.toResourceIdForSolution(pathId));
                    continue;
                }
                path.add(SummaryReportAuthUtil.toResourceIdForGroup(pathId));
            }
        } else {
            key = SummaryReportAuthUtil.toResourceIdForGroup(key);
            for (int i = 0; i < pathIds.size(); ++i) {
                String pathId = pathIds.get(i);
                path.add(SummaryReportAuthUtil.toResourceIdForGroup(pathId));
            }
        }
        result.setKey(key);
        result.setTitle(item.getTitle());
        result.setPath(path);
        return result;
    }
}

