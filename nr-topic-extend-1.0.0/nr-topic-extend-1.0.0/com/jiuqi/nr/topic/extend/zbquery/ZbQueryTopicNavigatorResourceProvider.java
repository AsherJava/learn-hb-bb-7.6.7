/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.quickreport.adapter.IAuthorityJudge
 *  com.jiuqi.nvwa.topicnavigator.TopicNavigatorException
 *  com.jiuqi.nvwa.topicnavigator.adapter.resource.ITopicNavResourceContext
 *  com.jiuqi.nvwa.topicnavigator.adapter.resource.ITopicNavigatorResourceProvider
 *  com.jiuqi.nvwa.topicnavigator.adapter.resource.ITopicNavigatorResourceProviderAnnotation
 *  com.jiuqi.nvwa.topicnavigator.adapter.resource.TopicNavResourceParameter
 *  com.jiuqi.nvwa.topicnavigator.adapter.resource.config.TopicNavigatorResourceConfig
 *  org.json.JSONObject
 */
package com.jiuqi.nr.topic.extend.zbquery;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.topic.extend.zbquery.ZbQueryTopicConfig;
import com.jiuqi.nvwa.quickreport.adapter.IAuthorityJudge;
import com.jiuqi.nvwa.topicnavigator.TopicNavigatorException;
import com.jiuqi.nvwa.topicnavigator.adapter.resource.ITopicNavResourceContext;
import com.jiuqi.nvwa.topicnavigator.adapter.resource.ITopicNavigatorResourceProvider;
import com.jiuqi.nvwa.topicnavigator.adapter.resource.ITopicNavigatorResourceProviderAnnotation;
import com.jiuqi.nvwa.topicnavigator.adapter.resource.TopicNavResourceParameter;
import com.jiuqi.nvwa.topicnavigator.adapter.resource.config.TopicNavigatorResourceConfig;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ITopicNavigatorResourceProviderAnnotation(resourceProviderId="ZB_QUERY_TOPIC_NAV_RESOURCE_ID", resourceType="com.jiuqi.nr.zbquery.manage", resourceTypeTitle="\u6307\u6807\u67e5\u8be2\u6a21\u677f", resourceFactoryId="DATAV_ANALYSIS_MODULEID", transferFactoryId="DataAnalysisResourceCategory_ID", transferResType="com.jiuqi.nr.zbquery.manage")
public class ZbQueryTopicNavigatorResourceProvider
implements ITopicNavigatorResourceProvider {
    private static final Logger logger = LoggerFactory.getLogger(ZbQueryTopicNavigatorResourceProvider.class);
    public static final String ZB_QUERY_TOPIC_NAV_RESOURCE_ID = "ZB_QUERY_TOPIC_NAV_RESOURCE_ID";
    public static final String NVWA_ZBQUERY_RESTYPE_TITLE = "\u6307\u6807\u67e5\u8be2\u6a21\u677f";
    @Autowired
    private IAuthorityJudge authorityJudge;

    public String getId() {
        return ZB_QUERY_TOPIC_NAV_RESOURCE_ID;
    }

    public String getIcon(String guid, String type) {
        return "nr-iconfont icon-16_DH_A_NR_guoluchaxun";
    }

    public String getGuidByName(String resourceName, ITopicNavResourceContext context) throws TopicNavigatorException {
        return null;
    }

    public TopicNavigatorResourceConfig getResourceConfig(String resourceGuid, TopicNavResourceParameter extraParameter, ITopicNavResourceContext context) throws TopicNavigatorException {
        TopicNavigatorResourceConfig config = new TopicNavigatorResourceConfig();
        config.setProdLine("@nr");
        config.setExpose("ZBQUERY");
        config.setApp("nr-topic-extend-plugin");
        ZbQueryTopicConfig param = new ZbQueryTopicConfig();
        param.setResourceId(resourceGuid);
        param.setCanAccess(this.authorityJudge.canAccess(resourceGuid, NpContextHolder.getContext().getUserId()));
        config.setConfig(new JSONObject((Object)param));
        return config;
    }

    public boolean supportExportExcel() {
        return true;
    }

    public GridData getGridData(String resourceGuid, TopicNavResourceParameter extraParameter, ITopicNavResourceContext context) throws TopicNavigatorException {
        return null;
    }
}

