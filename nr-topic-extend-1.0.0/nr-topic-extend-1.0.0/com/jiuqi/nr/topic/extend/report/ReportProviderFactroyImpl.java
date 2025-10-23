/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.topicnavigator.TopicNavigatorException
 *  com.jiuqi.nvwa.topicnavigator.adapter.resource.ITopicNavResourceContext
 *  com.jiuqi.nvwa.topicnavigator.adapter.resource.ITopicNavigatorResourceProvider
 *  com.jiuqi.nvwa.topicnavigator.adapter.resource.ITopicNavigatorResourceProviderFactory
 *  com.jiuqi.nvwa.topicnavigator.adapter.resource.TopicNavResourceItem
 */
package com.jiuqi.nr.topic.extend.report;

import com.jiuqi.nr.topic.extend.report.TaskTreeService;
import com.jiuqi.nvwa.topicnavigator.TopicNavigatorException;
import com.jiuqi.nvwa.topicnavigator.adapter.resource.ITopicNavResourceContext;
import com.jiuqi.nvwa.topicnavigator.adapter.resource.ITopicNavigatorResourceProvider;
import com.jiuqi.nvwa.topicnavigator.adapter.resource.ITopicNavigatorResourceProviderFactory;
import com.jiuqi.nvwa.topicnavigator.adapter.resource.TopicNavResourceItem;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportProviderFactroyImpl
implements ITopicNavigatorResourceProviderFactory {
    private static final Logger logger = LoggerFactory.getLogger(ReportProviderFactroyImpl.class);
    public static final String NR_TOPIC_MODULEID = "NR_TOPIC_MODULEID";
    @Autowired
    TaskTreeService service;

    public String getModuleId() {
        return NR_TOPIC_MODULEID;
    }

    public String getModuleTitle() {
        return "NR\u62a5\u8868";
    }

    public int order() {
        return 30;
    }

    public String getModuleIcon() {
        return "nr-iconfont icon-16_GJ_A_NR_hangshuxing";
    }

    public List<TopicNavResourceItem> getChildren(String pGuid, List<String> resourceTypeList, ITopicNavResourceContext context) throws TopicNavigatorException {
        if (NR_TOPIC_MODULEID.equals(pGuid)) {
            return this.service.getRootList();
        }
        return this.service.getByParent(pGuid);
    }

    public TopicNavResourceItem getResource(String resourceGuid, String resourceType, ITopicNavResourceContext context) throws TopicNavigatorException {
        return this.service.getResource(resourceGuid);
    }

    public List<TopicNavResourceItem> searchResourceList(String searchText, List<String> resourceTypeList, ITopicNavResourceContext context) throws TopicNavigatorException {
        return this.service.searchForm(searchText);
    }

    public List<String> getPaths(String resourceGuid, String resourceType, ITopicNavResourceContext context) throws TopicNavigatorException {
        List<String> paths = this.service.getPaths(resourceGuid);
        paths.add(NR_TOPIC_MODULEID);
        Collections.reverse(paths);
        return paths;
    }

    public TopicNavResourceItem getResourceByName(String resourceName, ITopicNavigatorResourceProvider provider, ITopicNavResourceContext context) throws TopicNavigatorException {
        return this.service.getResourceByName(resourceName);
    }

    public boolean canAccess(TopicNavResourceItem resourceItem, ITopicNavResourceContext context) {
        return this.service.hasAuth(resourceItem.getGuid());
    }

    public boolean canUserSecretAccess(TopicNavResourceItem resourceItem, ITopicNavResourceContext context) {
        return true;
    }
}

