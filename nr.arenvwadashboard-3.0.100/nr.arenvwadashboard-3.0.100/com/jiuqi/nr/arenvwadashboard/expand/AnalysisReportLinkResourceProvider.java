/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.analysisreport.internal.AnalysisTemp
 *  com.jiuqi.nr.analysisreport.service.SaveAnalysis
 *  com.jiuqi.nr.designer.web.rest.vo.ReturnObject
 *  com.jiuqi.nvwa.datav.dashboard.adapter.DashboardAdapterException
 *  com.jiuqi.nvwa.link.provider.ILinkResourceProvider
 *  com.jiuqi.nvwa.link.provider.ResourceAppConfig
 *  com.jiuqi.nvwa.link.provider.ResourceAppInfo
 *  com.jiuqi.nvwa.link.provider.ResourceNode
 *  com.jiuqi.nvwa.link.provider.SearchItem
 *  org.json.JSONObject
 */
package com.jiuqi.nr.arenvwadashboard.expand;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.analysisreport.internal.AnalysisTemp;
import com.jiuqi.nr.analysisreport.service.SaveAnalysis;
import com.jiuqi.nr.designer.web.rest.vo.ReturnObject;
import com.jiuqi.nvwa.datav.dashboard.adapter.DashboardAdapterException;
import com.jiuqi.nvwa.link.provider.ILinkResourceProvider;
import com.jiuqi.nvwa.link.provider.ResourceAppConfig;
import com.jiuqi.nvwa.link.provider.ResourceAppInfo;
import com.jiuqi.nvwa.link.provider.ResourceNode;
import com.jiuqi.nvwa.link.provider.SearchItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class AnalysisReportLinkResourceProvider
implements ILinkResourceProvider {
    private static final Logger logger = LoggerFactory.getLogger(AnalysisReportLinkResourceProvider.class);
    private static final String TYPE = "com.jiuqi.nr.analysisreport";
    private static final String TITLE = "\u5206\u6790\u62a5\u544a";
    @Autowired
    private SaveAnalysis saveAnalysis;

    public String getType() {
        return TYPE;
    }

    public String getTitle() {
        return TITLE;
    }

    public double getOrder() {
        return 11.0;
    }

    public String getIcon() {
        return null;
    }

    public ResourceAppInfo getAppInfo(String resourceId, String extData) {
        return new ResourceAppInfo("@nr", "dashboard-analysisreport", "");
    }

    public List<ResourceNode> getChildNodes(String resourceId, String extData) {
        if (StringUtils.isEmpty((String)resourceId)) {
            return this.buildRootNode();
        }
        try {
            if (StringUtils.equals((String)"root", (String)resourceId)) {
                resourceId = "0";
            }
            return this.convertResourceNodes(this.getTreeChild(resourceId));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    private List<ResourceNode> convertResourceNodes(List<AnalysisTemp> nodes) throws Exception {
        ArrayList<ResourceNode> treeNodes = new ArrayList<ResourceNode>(nodes.size());
        for (AnalysisTemp analysisTemp : nodes) {
            ResourceNode node = new ResourceNode();
            node.setId(analysisTemp.getKey());
            node.setTitle(analysisTemp.getTitle());
            if (CollectionUtils.isEmpty(this.getTreeChild(analysisTemp.getKey()))) {
                node.setLinkResource(true);
                node.setLeaf(true);
            } else {
                node.setLeaf(false);
            }
            node.setIcon("");
            treeNodes.add(node);
        }
        return treeNodes;
    }

    public List<AnalysisTemp> getTreeChild(String parent) throws Exception {
        ReturnObject returnObject = this.saveAnalysis.getListByGroupKey(parent);
        if (returnObject.isSuccess()) {
            List analysisTempList = (List)returnObject.getObj();
            return analysisTempList;
        }
        return null;
    }

    private List<ResourceNode> buildRootNode() {
        ResourceNode node = new ResourceNode();
        node.setId("AnalysisReportWidget");
        node.setTitle(TITLE);
        node.setLeaf(false);
        node.setIcon("");
        return Collections.singletonList(node);
    }

    public List<String> getPaths(String resourceId, String extData) {
        try {
            return this.getPathKeys(resourceId);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public ResourceAppConfig buildAppConfig(String resourceId, String extData, String linkMsg) {
        ResourceAppConfig resourceAppConfig = new ResourceAppConfig();
        JSONObject configJson = new JSONObject();
        JSONObject customConfigJson = new JSONObject();
        customConfigJson.put("guid", (Object)resourceId);
        customConfigJson.put("message", (Object)linkMsg);
        customConfigJson.put("extData", (Object)extData);
        configJson.put("customConfig", (Object)customConfigJson);
        resourceAppConfig.setConfig(configJson.toString());
        return resourceAppConfig;
    }

    public List<SearchItem> search(String key) {
        try {
            return this.convertSearchResults(this.saveAnalysis.fuzzyQuery(key));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<SearchItem> convertSearchResults(List<AnalysisTemp> nodes) throws DashboardAdapterException {
        ArrayList<SearchItem> results = new ArrayList<SearchItem>();
        for (AnalysisTemp node : nodes) {
            if (node.getAntype().equals("group")) continue;
            SearchItem item = new SearchItem();
            item.setKey(node.getKey());
            item.setTitle(node.getTitle());
            item.setKeyPaths(Arrays.asList(this.getPathTitle(node.getKey()).split(" / ")));
            item.setTitlePaths(this.getPathKeys(node.getKey()));
            results.add(item);
        }
        return results;
    }

    public String getPathTitle(String key) {
        try {
            return TITLE + this.saveAnalysis.getModelPathByKey(key, "title");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public ResourceNode getResource(String resourceId, String extData) {
        if (StringUtils.isEmpty((String)resourceId)) {
            return null;
        }
        try {
            Object res = this.saveAnalysis.getListByKey(resourceId).getObj();
            if (res != null) {
                Map result = (Map)res;
                AnalysisTemp analysisTemp = (AnalysisTemp)result.get("result");
                List<ResourceNode> resourceNodes = this.convertResourceNodes(Arrays.asList(analysisTemp));
                return resourceNodes.get(0);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
}

