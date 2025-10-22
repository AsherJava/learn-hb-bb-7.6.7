/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.BSGroupService
 *  com.jiuqi.nr.batch.summary.service.BSSchemeService
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryGroup
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.batch.summary.storage.utils.DateUtils
 *  com.jiuqi.nvwa.resourceview.query.IResourceDataProvider
 *  com.jiuqi.nvwa.resourceview.query.NodeType
 *  com.jiuqi.nvwa.resourceview.query.ResourceData
 *  com.jiuqi.nvwa.resourceview.query.ResourceGroup
 */
package com.jiuqi.nr.batch.summary.web.app;

import com.jiuqi.nr.batch.summary.service.BSGroupService;
import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryGroup;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.batch.summary.storage.utils.DateUtils;
import com.jiuqi.nvwa.resourceview.query.IResourceDataProvider;
import com.jiuqi.nvwa.resourceview.query.NodeType;
import com.jiuqi.nvwa.resourceview.query.ResourceData;
import com.jiuqi.nvwa.resourceview.query.ResourceGroup;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SummarySchemeResourceDataProvider
implements IResourceDataProvider {
    private final String taskKey;
    private final BSGroupService groupService;
    private final BSSchemeService schemeService;

    public SummarySchemeResourceDataProvider(String taskKey, BSGroupService groupService, BSSchemeService schemeService) {
        this.taskKey = taskKey;
        this.groupService = groupService;
        this.schemeService = schemeService;
    }

    public List<ResourceGroup> getGroupsForTree(String parentId) {
        return this.groupService.findChildGroups(this.taskKey, parentId).stream().map(this::translateSummaryGroup).collect(Collectors.toList());
    }

    public List<ResourceGroup> getRootGroupsForTree() {
        return this.groupService.findChildGroups(this.taskKey, "00000000-0000-0000-0000-000000000000").stream().map(this::translateSummaryGroup).collect(Collectors.toList());
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
        return this.schemeService.findChildSchemeByGroup(this.taskKey, groupId).stream().map(this::translateSummaryScheme).collect(Collectors.toList());
    }

    public List<ResourceData> getRootResourceDatasForTable() {
        return this.schemeService.findChildSchemeByGroup(this.taskKey, "00000000-0000-0000-0000-000000000000").stream().map(this::translateSummaryScheme).collect(Collectors.toList());
    }

    public ResourceGroup getGroup(String groupId) {
        return this.translateSummaryGroup(this.groupService.findGroup(groupId));
    }

    public ResourceData getResourceData(String resourceId) {
        return this.translateSummaryScheme(this.schemeService.findScheme(resourceId));
    }

    public boolean hasResourceRootGroup() {
        return false;
    }

    private ResourceGroup translateSummaryGroup(SummaryGroup group) {
        ResourceGroup rg = new ResourceGroup();
        rg.setId(group.getKey());
        rg.setName("");
        rg.setTitle(group.getTitle());
        rg.setType(NodeType.NODE_GROUP);
        rg.setGroup(group.getParent());
        rg.setModifyTime(DateUtils.dateToString((Date)group.getUpdateTime(), (String)"yyyy-MM-dd HH:mm:ss"));
        rg.setIcon("#icon16_DH_A_NW_gongnengfenzushouqi");
        return rg;
    }

    private ResourceData translateSummaryScheme(SummaryScheme scheme) {
        ResourceData rd = new ResourceData();
        rd.setId(scheme.getKey());
        rd.setName(scheme.getCode());
        rd.setTitle(scheme.getTitle());
        rd.setType(NodeType.NODE_DATA);
        rd.setResourceType("com.jiuqi.nr.batch.summary.web.app.scheme.resource.type");
        rd.setGroup(scheme.getGroup());
        rd.setModifyTime(DateUtils.dateToString((Date)scheme.getUpdateTime(), (String)"yyyy-MM-dd HH:mm:ss"));
        rd.setIcon("#icon-_GJZzidingyihuizong");
        return rd;
    }
}

