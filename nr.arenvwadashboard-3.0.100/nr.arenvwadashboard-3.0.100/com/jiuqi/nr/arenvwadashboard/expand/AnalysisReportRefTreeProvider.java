/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.analysisreport.internal.AnalysisTemp
 *  com.jiuqi.nr.analysisreport.service.SaveAnalysis
 *  com.jiuqi.nr.designer.web.rest.vo.ReturnObject
 *  com.jiuqi.nvwa.datav.dashboard.adapter.DashboardAdapterException
 *  com.jiuqi.nvwa.datav.dashboard.domain.RefTreeNode
 *  com.jiuqi.nvwa.datav.dashboard.domain.SearchResult
 *  com.jiuqi.nvwa.datav.dashboard.engine.DashboardContext
 *  com.jiuqi.nvwa.datav.dashboard.enums.RefType
 *  com.jiuqi.nvwa.datav.dashboard.exception.DashboardException
 *  com.jiuqi.nvwa.datav.dashboard.provider.IRefTreeProvider
 */
package com.jiuqi.nr.arenvwadashboard.expand;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.analysisreport.internal.AnalysisTemp;
import com.jiuqi.nr.analysisreport.service.SaveAnalysis;
import com.jiuqi.nr.designer.web.rest.vo.ReturnObject;
import com.jiuqi.nvwa.datav.dashboard.adapter.DashboardAdapterException;
import com.jiuqi.nvwa.datav.dashboard.domain.RefTreeNode;
import com.jiuqi.nvwa.datav.dashboard.domain.SearchResult;
import com.jiuqi.nvwa.datav.dashboard.engine.DashboardContext;
import com.jiuqi.nvwa.datav.dashboard.enums.RefType;
import com.jiuqi.nvwa.datav.dashboard.exception.DashboardException;
import com.jiuqi.nvwa.datav.dashboard.provider.IRefTreeProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class AnalysisReportRefTreeProvider
implements IRefTreeProvider {
    private static final Logger logger = LoggerFactory.getLogger(AnalysisReportRefTreeProvider.class);
    @Autowired
    private SaveAnalysis saveAnalysis;

    public String getProviderType() {
        return RefType.EXTERNAL.name();
    }

    public List<RefTreeNode> getTreeNode(String parent, DashboardContext dashboardContext) throws DashboardException {
        if (StringUtils.isEmpty((String)parent)) {
            return this.buildRootNode();
        }
        try {
            if (StringUtils.equals((String)"AnalysisReportWidget", (String)parent)) {
                parent = "0";
            }
            return this.convertRefTreeNodes(this.getTreeChild(parent));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public List<String> getNodePath(String refResourceId, DashboardContext dashboardContext) throws DashboardException {
        try {
            return this.getPathKeys(refResourceId);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public String getNodePathTitle(String refResourceId, DashboardContext dashboardContext) throws DashboardException {
        return this.getPathTitle(refResourceId);
    }

    public List<SearchResult> searchNodes(String filter, DashboardContext dashboardContext) throws DashboardException {
        try {
            return this.convertSearchResults(this.saveAnalysis.fuzzyQuery(filter));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<AnalysisTemp> getTreeChild(String parent) throws Exception {
        ReturnObject returnObject = this.saveAnalysis.getListByGroupKey(parent);
        if (returnObject.isSuccess()) {
            List analysisTempList = (List)returnObject.getObj();
            return analysisTempList;
        }
        return null;
    }

    private List<RefTreeNode> convertRefTreeNodes(List<AnalysisTemp> nodes) throws Exception {
        ArrayList<RefTreeNode> treeNodes = new ArrayList<RefTreeNode>(nodes.size());
        for (AnalysisTemp analysisTemp : nodes) {
            RefTreeNode node = new RefTreeNode();
            node.setId(analysisTemp.getKey());
            node.setName(analysisTemp.getTitle());
            node.setTitle(analysisTemp.getTitle());
            node.setResourceType("com.jiuqi.nr.arenvwadashboard");
            if (CollectionUtils.isEmpty(this.getTreeChild(analysisTemp.getKey()))) {
                node.setParent(false);
            } else {
                node.setParent(true);
            }
            node.setType("AnalysisReportWidget");
            node.setIcon("");
            treeNodes.add(node);
        }
        return treeNodes;
    }

    public String getPathTitle(String key) {
        try {
            return "\u5206\u6790\u62a5\u544a" + this.saveAnalysis.getModelPathByKey(key, "title");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public List<String> getPathKeys(String key) {
        try {
            String analysisModelFullPathKeyByKey = this.saveAnalysis.getModelPathByKey(key, "KEY");
            if (StringUtils.isNotEmpty((String)analysisModelFullPathKeyByKey)) {
                return Arrays.asList(analysisModelFullPathKeyByKey.split("/"));
            }
            return null;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    private List<SearchResult> convertSearchResults(List<AnalysisTemp> nodes) throws DashboardAdapterException {
        ArrayList<SearchResult> results = new ArrayList<SearchResult>();
        for (AnalysisTemp node : nodes) {
            if (node.getAntype().equals("group")) continue;
            SearchResult searchResult = new SearchResult();
            searchResult.setId(node.getKey());
            searchResult.setName("");
            searchResult.setTitle(node.getTitle());
            searchResult.setIcon("");
            searchResult.setType("AnalysisReportWidget");
            searchResult.setTypeTitle("\u5206\u6790\u62a5\u544a");
            searchResult.setResourceType(RefType.ANALYZE.name());
            searchResult.setPaths(this.getPathTitle(node.getKey()));
            searchResult.setPathGuids(this.getPathKeys(node.getKey()));
            results.add(searchResult);
        }
        return results;
    }

    private List<RefTreeNode> buildRootNode() {
        RefTreeNode node = new RefTreeNode();
        node.setId("AnalysisReportWidget");
        node.setName("AnalysisReportWidget");
        node.setTitle("\u5206\u6790\u62a5\u544a");
        node.setParent(true);
        node.setType("AnalysisReportWidget");
        node.setResourceType(RefType.EXTERNAL.name());
        node.setIcon("");
        return Collections.singletonList(node);
    }
}

