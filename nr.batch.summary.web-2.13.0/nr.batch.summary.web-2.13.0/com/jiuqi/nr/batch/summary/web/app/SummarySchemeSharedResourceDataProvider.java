/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.BSShareService
 *  com.jiuqi.nr.batch.summary.storage.entity.ShareSummaryGroup
 *  com.jiuqi.nr.batch.summary.storage.entity.ShareSummaryScheme
 *  com.jiuqi.nr.batch.summary.storage.utils.DateUtils
 *  com.jiuqi.nvwa.resourceview.query.IResourceDataProvider
 *  com.jiuqi.nvwa.resourceview.query.NodeType
 *  com.jiuqi.nvwa.resourceview.query.ResourceData
 *  com.jiuqi.nvwa.resourceview.query.ResourceGroup
 */
package com.jiuqi.nr.batch.summary.web.app;

import com.jiuqi.nr.batch.summary.service.BSShareService;
import com.jiuqi.nr.batch.summary.storage.entity.ShareSummaryGroup;
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

public class SummarySchemeSharedResourceDataProvider
implements IResourceDataProvider {
    private final String taskKey;
    private final BSShareService shareService;

    public SummarySchemeSharedResourceDataProvider(String taskKey, BSShareService shareService) {
        this.taskKey = taskKey;
        this.shareService = shareService;
    }

    public List<ResourceGroup> getGroupsForTree(String parentId) {
        return this.shareService.findChildGroups(this.taskKey, parentId).stream().map(this::translateShareSummaryGroup).collect(Collectors.toList());
    }

    public List<ResourceGroup> getRootGroupsForTree() {
        return this.shareService.findChildGroups(this.taskKey, "00000000-0000-0000-0000-000000000000").stream().map(this::translateShareSummaryGroup).collect(Collectors.toList());
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
        return this.shareService.findChildSchemeByGroup(this.taskKey, "00000000-0000-0000-0000-000000000000").stream().map(this::translateShareSummaryScheme).collect(Collectors.toList());
    }

    public ResourceGroup getGroup(String groupId) {
        return this.translateShareSummaryGroup(this.shareService.findGroup(groupId));
    }

    public ResourceData getResourceData(String resourceId) {
        return this.translateShareSummaryScheme(this.shareService.findScheme(resourceId, 0));
    }

    public boolean hasResourceRootGroup() {
        return false;
    }

    private ResourceGroup translateShareSummaryGroup(ShareSummaryGroup group) {
        ResourceGroup rg = new ResourceGroup();
        rg.setId(group.getCode());
        rg.setName(group.getTitle());
        rg.setTitle(group.getTitle());
        rg.setType(NodeType.NODE_GROUP);
        rg.setGroup(group.getParent());
        rg.setIcon("#icon16_DH_A_NW_gongnengfenzushouqi");
        return rg;
    }

    private ResourceData translateShareSummaryScheme(ShareSummaryScheme scheme) {
        ResourceData rd = new ResourceData();
        rd.setId(scheme.getCode());
        rd.setName(scheme.getFromUser());
        rd.setTitle(scheme.getTitle());
        rd.setType(NodeType.NODE_DATA);
        rd.setResourceType("com.jiuqi.nr.batch.summary.web.app.scheme.resource.type.shared");
        rd.setGroup(scheme.getFromUser());
        rd.setModifyTime(DateUtils.dateToString((Date)scheme.getShareTime(), (String)"yyyy-MM-dd HH:mm:ss"));
        rd.setIcon("#icon-_GJZzidingyihuizong");
        return rd;
    }
}

