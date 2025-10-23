/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.utils;

import com.jiuqi.nr.summary.api.SummaryReport;
import com.jiuqi.nr.summary.api.SummarySolution;
import com.jiuqi.nr.summary.api.SummarySolutionGroup;
import com.jiuqi.nr.summary.internal.dao.ISummarySolutionDao;
import com.jiuqi.nr.summary.internal.dao.ISummarySolutionGroupDao;
import com.jiuqi.nr.summary.internal.dao.impl.DesignSummaryReportDaoImpl;
import com.jiuqi.nr.summary.internal.entity.SummarySolutionDO;
import com.jiuqi.nr.summary.internal.entity.SummarySolutionGroupDO;
import com.jiuqi.nr.summary.vo.search.SummarySearchItem;
import com.jiuqi.nr.summary.vo.search.SummarySearchResultType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SummarySearchUtil {
    @Autowired
    private ISummarySolutionGroupDao solutionGroupDao;
    @Autowired
    private ISummarySolutionDao solutionDao;
    @Autowired
    private DesignSummaryReportDaoImpl summaryReportDao;

    public List<SummarySearchItem> buildSoluGroupSearchItems(List<SummarySolutionGroup> soluGroups, Map<String, SummarySolutionGroup> groupCaches) {
        ArrayList<SummarySearchItem> searchItems = new ArrayList<SummarySearchItem>();
        soluGroups.forEach(soluGroup -> {
            groupCaches.put(soluGroup.getKey(), (SummarySolutionGroup)soluGroup);
            SummarySearchItem searchItem = this.buildSoluGroupSearchItem((SummarySolutionGroup)soluGroup);
            ArrayList<String> pathIds = new ArrayList<String>();
            StringBuilder path = new StringBuilder();
            pathIds.add(0, searchItem.getKey());
            path.insert(0, "/" + searchItem.getTitle());
            String parent = soluGroup.getParent();
            while (parent != null) {
                if (!groupCaches.containsKey(parent)) {
                    SummarySolutionGroupDO parentGroupDO = this.solutionGroupDao.getByKey(parent);
                    groupCaches.put(parentGroupDO.getKey(), parentGroupDO);
                }
                SummarySolutionGroup parentGroup = (SummarySolutionGroup)groupCaches.get(parent);
                pathIds.add(0, parentGroup.getKey());
                path.insert(0, "/" + parentGroup.getTitle());
                parent = parentGroup.getParent();
            }
            searchItem.setPathIds(pathIds);
            path.delete(0, 1);
            searchItem.setPath(path.toString());
            searchItems.add(searchItem);
        });
        return searchItems;
    }

    public List<SummarySearchItem> buildSoluSearchItems(List<SummarySolution> solus, Map<String, SummarySolutionGroup> groupCaches, Map<String, SummarySolution> solutionCaches) {
        ArrayList<SummarySearchItem> searchItems = new ArrayList<SummarySearchItem>();
        solus.forEach(solution -> {
            solutionCaches.put(solution.getKey(), (SummarySolution)solution);
            SummarySearchItem searchItem = this.buildSoluSearchItem((SummarySolution)solution);
            ArrayList<String> pathIds = new ArrayList<String>();
            StringBuilder path = new StringBuilder();
            pathIds.add(0, searchItem.getKey());
            path.insert(0, "/" + searchItem.getTitle());
            String parent = solution.getGroup();
            while (parent != null) {
                if (!groupCaches.containsKey(parent)) {
                    SummarySolutionGroupDO parentGroupDO = this.solutionGroupDao.getByKey(parent);
                    groupCaches.put(parentGroupDO.getKey(), parentGroupDO);
                }
                SummarySolutionGroup parentGroup = (SummarySolutionGroup)groupCaches.get(parent);
                pathIds.add(0, parentGroup.getKey());
                path.insert(0, "/" + parentGroup.getTitle());
                parent = parentGroup.getParent();
            }
            searchItem.setPathIds(pathIds);
            path.delete(0, 1);
            searchItem.setPath(path.toString());
            searchItems.add(searchItem);
        });
        return searchItems;
    }

    public List<SummarySearchItem> buildReportSearchItems(List<SummaryReport> reports, Map<String, SummarySolutionGroup> groupCaches, Map<String, SummarySolution> solutionCaches, Map<String, SummaryReport> reportCaches) {
        ArrayList<SummarySearchItem> searchItems = new ArrayList<SummarySearchItem>();
        reports.forEach(report -> {
            reportCaches.put(report.getKey(), (SummaryReport)report);
            SummarySearchItem searchItem = this.buildReportSearchItem((SummaryReport)report);
            ArrayList<String> pathIds = new ArrayList<String>();
            StringBuilder path = new StringBuilder();
            pathIds.add(0, searchItem.getKey());
            path.insert(0, "/" + searchItem.getTitle());
            String solutionKey = report.getSummarySolutionKey();
            if (!solutionCaches.containsKey(solutionKey)) {
                SummarySolutionDO parentSolutionDO = this.solutionDao.getByKey(solutionKey, false);
                solutionCaches.put(parentSolutionDO.getKey(), parentSolutionDO);
            }
            SummarySolution summarySolution = (SummarySolution)solutionCaches.get(solutionKey);
            pathIds.add(0, summarySolution.getKey());
            path.insert(0, "/" + summarySolution.getTitle());
            String parent = summarySolution.getGroup();
            while (parent != null) {
                if (!groupCaches.containsKey(parent)) {
                    SummarySolutionGroupDO parentGroupDO = this.solutionGroupDao.getByKey(parent);
                    groupCaches.put(parentGroupDO.getKey(), parentGroupDO);
                }
                SummarySolutionGroup parentGroup = (SummarySolutionGroup)groupCaches.get(parent);
                pathIds.add(0, parentGroup.getKey());
                path.insert(0, "/" + parentGroup.getTitle());
                parent = parentGroup.getParent();
            }
            searchItem.setPathIds(pathIds);
            path.delete(0, 1);
            searchItem.setPath(path.toString());
            searchItems.add(searchItem);
        });
        return searchItems;
    }

    public SummarySearchItem buildSoluGroupSearchItem(SummarySolutionGroup soluGroup) {
        SummarySearchItem searchItem = new SummarySearchItem();
        searchItem.setKey(soluGroup.getKey());
        searchItem.setTitle(soluGroup.getTitle());
        ArrayList<SummarySearchResultType> types = new ArrayList<SummarySearchResultType>();
        types.add(SummarySearchResultType.SUMMARY_SOLUTION_GROUP);
        searchItem.setType(types);
        return searchItem;
    }

    public SummarySearchItem buildSoluSearchItem(SummarySolution solution) {
        SummarySearchItem searchItem = new SummarySearchItem();
        searchItem.setKey(solution.getKey());
        searchItem.setTitle(solution.getTitle());
        ArrayList<SummarySearchResultType> types = new ArrayList<SummarySearchResultType>();
        types.add(SummarySearchResultType.SUMMARY_SOLUTION);
        searchItem.setType(types);
        return searchItem;
    }

    public SummarySearchItem buildReportSearchItem(SummaryReport report) {
        SummarySearchItem searchItem = new SummarySearchItem();
        searchItem.setKey(report.getKey());
        searchItem.setTitle(report.getTitle());
        ArrayList<SummarySearchResultType> types = new ArrayList<SummarySearchResultType>();
        types.add(SummarySearchResultType.SUMMARY_REPORT);
        searchItem.setType(types);
        return searchItem;
    }
}

