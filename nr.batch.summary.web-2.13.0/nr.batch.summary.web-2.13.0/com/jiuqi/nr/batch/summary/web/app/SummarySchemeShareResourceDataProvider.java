/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.BSShareService
 *  com.jiuqi.nr.batch.summary.storage.entity.ShareSummaryScheme
 *  com.jiuqi.nr.batch.summary.storage.utils.DateUtils
 *  com.jiuqi.nvwa.resourceview.query.IResourceDataProvider
 *  com.jiuqi.nvwa.resourceview.query.NodeType
 *  com.jiuqi.nvwa.resourceview.query.ResourceData
 *  com.jiuqi.nvwa.resourceview.query.ResourceGroup
 */
package com.jiuqi.nr.batch.summary.web.app;

import com.jiuqi.nr.batch.summary.service.BSShareService;
import com.jiuqi.nr.batch.summary.storage.entity.ShareSummaryScheme;
import com.jiuqi.nr.batch.summary.storage.utils.DateUtils;
import com.jiuqi.nvwa.resourceview.query.IResourceDataProvider;
import com.jiuqi.nvwa.resourceview.query.NodeType;
import com.jiuqi.nvwa.resourceview.query.ResourceData;
import com.jiuqi.nvwa.resourceview.query.ResourceGroup;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SummarySchemeShareResourceDataProvider
implements IResourceDataProvider {
    private final String taskKey;
    private final BSShareService shareService;

    public SummarySchemeShareResourceDataProvider(String taskKey, BSShareService shareService) {
        this.taskKey = taskKey;
        this.shareService = shareService;
    }

    public List<ResourceGroup> getGroupsForTree(String parentId) {
        return new ArrayList<ResourceGroup>();
    }

    public List<ResourceGroup> getRootGroupsForTree() {
        return new ArrayList<ResourceGroup>();
    }

    public List<ResourceData> getResourceDatasForTree(String groupId) {
        return new ArrayList<ResourceData>();
    }

    public List<ResourceData> getRootResourceDatasForTree() {
        return new ArrayList<ResourceData>();
    }

    public List<ResourceGroup> getGroupsForTable(String parentId) {
        return this.getGroupsForTree(parentId);
    }

    public List<ResourceGroup> getRootGroupsForTable() {
        return this.getRootGroupsForTree();
    }

    public List<ResourceData> getResourceDatasForTable(String groupId) {
        return this.shareService.findChildSchemeByGroup(this.taskKey, groupId).stream().map(this::translateShareSummaryScheme).collect(Collectors.toList());
    }

    public List<ResourceData> getRootResourceDatasForTable() {
        return this.shareService.findChildSchemeByGroup(this.taskKey).stream().map(this::translateShareSummaryScheme).collect(Collectors.toList());
    }

    public ResourceGroup getGroup(String groupId) {
        return null;
    }

    public ResourceData getResourceData(String resourceId) {
        return this.translateShareSummaryScheme(this.shareService.findScheme(resourceId, 0));
    }

    public boolean hasResourceRootGroup() {
        return false;
    }

    private ResourceData translateShareSummaryScheme(ShareSummaryScheme scheme) {
        ResourceData rd = new ResourceData();
        rd.setId(scheme.getCode());
        rd.setName(scheme.getToUser());
        rd.setTitle(scheme.getTitle());
        rd.setType(NodeType.NODE_DATA);
        rd.setResourceType("com.jiuqi.nr.batch.summary.web.app.scheme.resource.type.share");
        rd.setGroup(null);
        rd.setModifyTime(DateUtils.dateToString((Date)scheme.getShareTime(), (String)"yyyy-MM-dd HH:mm:ss"));
        rd.setIcon("#icon-_GJZzidingyihuizong");
        return rd;
    }
}

