/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.nvwa.topicnavigator.TopicNavigatorException
 *  com.jiuqi.nvwa.topicnavigator.adapter.resource.ITopicNavResourceContext
 *  com.jiuqi.nvwa.topicnavigator.adapter.resource.ITopicNavigatorResourceProvider
 *  com.jiuqi.nvwa.topicnavigator.adapter.resource.ITopicNavigatorResourceProviderAnnotation
 *  com.jiuqi.nvwa.topicnavigator.adapter.resource.TopicNavResourceParameter
 *  com.jiuqi.nvwa.topicnavigator.adapter.resource.config.TopicNavigatorResourceConfig
 *  org.json.JSONObject
 */
package com.jiuqi.nr.topic.extend.report;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.nr.topic.extend.report.TaskTreeService;
import com.jiuqi.nr.topic.extend.report.bean.ResourceParam;
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
@ITopicNavigatorResourceProviderAnnotation(resourceProviderId="REPORT_TOPIC_NAV_RESOURCE_ID", resourceType="com.jiuqi.nr.topic.extend.report", resourceTypeTitle="\u57fa\u7840\u8868", resourceFactoryId="NR_TOPIC_MODULEID", transferFactoryId="", transferResType="")
public class ReportTopicNavigatorResourceProvider
implements ITopicNavigatorResourceProvider {
    private static final Logger logger = LoggerFactory.getLogger(ReportTopicNavigatorResourceProvider.class);
    protected static final String REPORT_TOPIC_NAV_RESOURCE_ID = "REPORT_TOPIC_NAV_RESOURCE_ID";
    protected static final String NVWA_REPORT_RESTYPE_TITLE = "\u57fa\u7840\u8868";
    protected static final String RESOURCE_TYPE = "com.jiuqi.nr.topic.extend.report";
    @Autowired
    TaskTreeService service;

    public String getId() {
        return REPORT_TOPIC_NAV_RESOURCE_ID;
    }

    public String getIcon(String guid, String type) {
        return "nr-iconfont icon-_TCYbiaoge";
    }

    public String getGuidByName(String resourceName, ITopicNavResourceContext context) throws TopicNavigatorException {
        return this.service.getFormGuidByName(resourceName);
    }

    public TopicNavigatorResourceConfig getResourceConfig(String resourceGuid, TopicNavResourceParameter extraParameter, ITopicNavResourceContext context) throws TopicNavigatorException {
        TopicNavigatorResourceConfig config = new TopicNavigatorResourceConfig();
        config.setProdLine("@nr");
        config.setExpose("REPORT");
        config.setApp("nr-topic-extend-plugin");
        ResourceParam param = new ResourceParam();
        param.setFormKey(resourceGuid);
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

