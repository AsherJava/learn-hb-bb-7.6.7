/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.service.impl;

import com.jiuqi.nr.summary.api.SummaryReport;
import com.jiuqi.nr.summary.api.SummarySolution;
import com.jiuqi.nr.summary.api.SummarySolutionGroup;
import com.jiuqi.nr.summary.internal.dao.ISummarySolutionDao;
import com.jiuqi.nr.summary.internal.dao.ISummarySolutionGroupDao;
import com.jiuqi.nr.summary.internal.dao.impl.DesignSummaryReportDaoImpl;
import com.jiuqi.nr.summary.internal.entity.DesignSummaryReportDO;
import com.jiuqi.nr.summary.internal.entity.SummarySolutionDO;
import com.jiuqi.nr.summary.internal.entity.SummarySolutionGroupDO;
import com.jiuqi.nr.summary.manage.provider.ResourceDirNodeProvider;
import com.jiuqi.nr.summary.manage.provider.ResourceNodeProvider;
import com.jiuqi.nr.summary.service.ISummarySearchService;
import com.jiuqi.nr.summary.utils.SummarySearchUtil;
import com.jiuqi.nr.summary.vo.ResourceNode;
import com.jiuqi.nr.summary.vo.TreeNode;
import com.jiuqi.nr.summary.vo.search.SummarySearchItem;
import com.jiuqi.nr.summary.vo.search.SummarySearchPosition;
import com.jiuqi.nr.summary.vo.search.SummarySearchPositionRequestParam;
import com.jiuqi.nr.summary.vo.search.SummarySearchRequestParam;
import com.jiuqi.nr.summary.vo.search.SummarySearchResultType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class SummarySearchServiceImpl
implements ISummarySearchService {
    @Autowired
    private ISummarySolutionGroupDao solutionGroupDao;
    @Autowired
    private ISummarySolutionDao solutionDao;
    @Autowired
    private DesignSummaryReportDaoImpl summaryReportDao;
    @Autowired
    private ResourceNodeProvider resourceNodeProvider;
    @Autowired
    private ResourceDirNodeProvider resourceDirNodeProvider;
    @Autowired
    private SummarySearchUtil summarySearchUtil;

    @Override
    public List<SummarySearchItem> search(SummarySearchRequestParam searchReqParam) {
        ArrayList<SummarySearchItem> searchItems = new ArrayList<SummarySearchItem>();
        HashMap<String, SummarySolutionGroup> groupCaches = new HashMap<String, SummarySolutionGroup>();
        HashMap<String, SummarySolution> solutionCaches = new HashMap<String, SummarySolution>();
        HashMap<String, SummaryReport> reportCaches = new HashMap<String, SummaryReport>();
        if (searchReqParam.isWithGroup()) {
            List<SummarySolutionGroup> soluGroups = this.solutionGroupDao.fuzzyQuery(searchReqParam.getKeywords()).stream().collect(Collectors.toList());
            searchItems.addAll(this.summarySearchUtil.buildSoluGroupSearchItems(soluGroups, groupCaches));
        }
        if (searchReqParam.isWithSolution()) {
            List<SummarySolution> solus = this.solutionDao.fuzzyQuery(searchReqParam.getKeywords()).stream().collect(Collectors.toList());
            searchItems.addAll(this.summarySearchUtil.buildSoluSearchItems(solus, groupCaches, solutionCaches));
        }
        if (searchReqParam.isWithReport()) {
            List<SummaryReport> reports = this.summaryReportDao.fuzzyQuery(searchReqParam.getKeywords()).stream().collect(Collectors.toList());
            searchItems.addAll(this.summarySearchUtil.buildReportSearchItems(reports, groupCaches, solutionCaches, reportCaches));
        }
        return searchItems;
    }

    @Override
    public SummarySearchPosition position(SummarySearchPositionRequestParam positionParam) {
        SummarySearchPosition positionResult = new SummarySearchPosition();
        SummarySearchResultType type = positionParam.getType();
        if (type.equals((Object)SummarySearchResultType.SUMMARY_REPORT)) {
            this.doSummaryReportPosition(positionResult, positionParam);
        } else if (type.equals((Object)SummarySearchResultType.SUMMARY_SOLUTION_GROUP)) {
            this.doSummarySolutionGroupPosition(positionResult, positionParam);
        } else {
            this.doSummarySolutionPosition(positionResult, positionParam);
        }
        return positionResult;
    }

    private void doSummaryReportPosition(SummarySearchPosition positionResult, SummarySearchPositionRequestParam positionParam) {
        String dataKey = positionParam.getKey();
        ArrayList<String> path = new ArrayList<String>();
        DesignSummaryReportDO reportDO = (DesignSummaryReportDO)this.summaryReportDao.getByKey(dataKey, false);
        String solutionKey = reportDO.getSummarySolutionKey();
        List<ResourceNode> resourceNodes = this.resourceNodeProvider.getNodes(1, solutionKey);
        for (ResourceNode node : resourceNodes) {
            if (!node.getKey().equals(dataKey)) continue;
            node.setChecked(true);
            node.setHighlight(true);
        }
        positionResult.setResourceNodes(resourceNodes);
        path.add(solutionKey);
        SummarySolutionDO solutionDO = this.solutionDao.getByKey(solutionKey, false);
        String groupKey = solutionDO.getGroup();
        while (StringUtils.hasLength(groupKey)) {
            path.add(groupKey);
            SummarySolutionGroupDO groupDO = this.solutionGroupDao.getByKey(groupKey);
            groupKey = groupDO.getParent();
        }
        Collections.reverse(path);
        List<TreeNode> roots = this.resourceDirNodeProvider.getRoots();
        TreeNode treeNode = roots.get(0);
        List<TreeNode> children = treeNode.getChildren();
        Iterator iterator = path.iterator();
        while (iterator.hasNext()) {
            String key = (String)iterator.next();
            if (CollectionUtils.isEmpty(children)) continue;
            for (TreeNode child : children) {
                String childKey = child.getKey();
                if (!childKey.equals(key)) continue;
                child.setExpand(true);
                children = this.resourceDirNodeProvider.getChilds(childKey);
                child.setChildren(children.isEmpty() ? null : children);
                if (iterator.hasNext()) continue;
                child.setSelected(true);
                positionResult.setSelectedTreeNode(child);
            }
        }
        positionResult.setTreeNode(treeNode);
    }

    private void doSummarySolutionPosition(SummarySearchPosition positionResult, SummarySearchPositionRequestParam positionParam) {
    }

    private void doSummarySolutionGroupPosition(SummarySearchPosition positionResult, SummarySearchPositionRequestParam positionParam) {
    }
}

