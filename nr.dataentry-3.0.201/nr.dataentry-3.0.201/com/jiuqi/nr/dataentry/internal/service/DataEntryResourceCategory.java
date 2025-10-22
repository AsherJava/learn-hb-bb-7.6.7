/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.auth.DataSchemeAuthResourceType
 *  com.jiuqi.nr.datascheme.common.NodeIconGetter
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskGroupDefine
 *  com.jiuqi.nvwa.authority.extend.DefaultResourceCategory
 *  com.jiuqi.nvwa.authority.extend.ResourceCategory
 *  com.jiuqi.nvwa.authority.extend.ResourceCategoryFilter
 *  com.jiuqi.nvwa.authority.privilege.PrivilegeDefinition
 *  com.jiuqi.nvwa.authority.privilege.PrivilegeDefinitionItem
 *  com.jiuqi.nvwa.authority.resource.Resource
 *  com.jiuqi.nvwa.authority.resource.ResourceGroupItem
 *  com.jiuqi.nvwa.authority.resource.ResourceItem
 *  com.jiuqi.nvwa.authority.util.AuthorityConst$AuthzRightAreaPlan
 *  com.jiuqi.nvwa.authority.util.AuthorityConst$Category_Type
 *  com.jiuqi.nvwa.authority.vo.GranteeInfo
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.nr.dataentry.bean.AuthorityOptions;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.auth.DataSchemeAuthResourceType;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nvwa.authority.extend.DefaultResourceCategory;
import com.jiuqi.nvwa.authority.extend.ResourceCategory;
import com.jiuqi.nvwa.authority.extend.ResourceCategoryFilter;
import com.jiuqi.nvwa.authority.privilege.PrivilegeDefinition;
import com.jiuqi.nvwa.authority.privilege.PrivilegeDefinitionItem;
import com.jiuqi.nvwa.authority.resource.Resource;
import com.jiuqi.nvwa.authority.resource.ResourceGroupItem;
import com.jiuqi.nvwa.authority.resource.ResourceItem;
import com.jiuqi.nvwa.authority.util.AuthorityConst;
import com.jiuqi.nvwa.authority.vo.GranteeInfo;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class DataEntryResourceCategory
extends DefaultResourceCategory
implements ResourceCategoryFilter {
    private static final long serialVersionUID = -6341017304556981776L;
    public static final String DATAENTRYSOURCEOPTIONSID = "TaskResourceCategory-4A6C8C5C300143B6AA8CCE4DC888224D";
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;

    public String getId() {
        return DATAENTRYSOURCEOPTIONSID;
    }

    public String getTitle() {
        return "\u6570\u636e\u64cd\u4f5c\u6743\u9650";
    }

    public String getGroupTitle() {
        return "\u62a5\u8868";
    }

    public int getSeq() {
        return 0;
    }

    public AuthorityConst.AuthzRightAreaPlan getAuthRightAreaPlan() {
        return AuthorityConst.AuthzRightAreaPlan.All;
    }

    public AuthorityConst.Category_Type getCategoryType() {
        return AuthorityConst.Category_Type.NORMAL;
    }

    public List<PrivilegeDefinition> getPrivilegeDefinition(GranteeInfo granteeInfo) {
        AuthorityOptions[] values;
        ArrayList<PrivilegeDefinition> list = new ArrayList<PrivilegeDefinition>();
        for (AuthorityOptions value : values = AuthorityOptions.values()) {
            PrivilegeDefinitionItem item = new PrivilegeDefinitionItem();
            item.setPrivilegeId(value.getId());
            item.setPrivilegeTitle(value.getTitle());
            list.add((PrivilegeDefinition)item);
        }
        return list;
    }

    public List<Resource> getRootResources(GranteeInfo granteeInfo) {
        return this.getResources("");
    }

    public List<Resource> getChildResources(String resourceGroupId, GranteeInfo granteeInfo, Object param) {
        String groupId = DataSchemeAuthResourceType.DATA_SCHEME_GROUP.toObjectId(resourceGroupId);
        return this.getResources(groupId);
    }

    private List<Resource> getResources(String groupId) {
        ArrayList<Resource> resources;
        block8: {
            List allGroup;
            List allTasks;
            List tasks;
            block7: {
                resources = new ArrayList<Resource>();
                tasks = this.iRunTimeViewController.getAllRunTimeTasksInGroup(groupId);
                allTasks = this.iRunTimeViewController.getAllTaskDefines();
                allGroup = this.iRunTimeViewController.getAllTaskGroup();
                this.iRunTimeViewController.getChildTaskGroups(groupId, true);
                if (!CollectionUtils.isEmpty(tasks)) {
                    for (TaskDefine task : tasks) {
                        resources.add((Resource)DataEntryResourceCategory.createResource(task));
                    }
                }
                if (!groupId.equals("")) break block7;
                if (!CollectionUtils.isEmpty(allGroup)) {
                    for (TaskGroupDefine scheme : allGroup) {
                        resources.add((Resource)DataEntryResourceCategory.createResource(scheme));
                    }
                }
                if (CollectionUtils.isEmpty(allTasks)) break block8;
                for (TaskDefine task : allTasks) {
                    List group = this.iRunTimeViewController.getGroupByTask(task.getKey());
                    if (!group.isEmpty()) continue;
                    resources.add((Resource)DataEntryResourceCategory.createResource(task));
                }
                break block8;
            }
            if (CollectionUtils.isEmpty(tasks) && CollectionUtils.isEmpty(allGroup) && !CollectionUtils.isEmpty(allTasks)) {
                for (TaskGroupDefine scheme : allTasks) {
                    resources.add((Resource)DataEntryResourceCategory.createResource((TaskDefine)scheme));
                }
            }
        }
        return resources;
    }

    protected static ResourceGroupItem createResource(TaskGroupDefine group) {
        ResourceGroupItem item = ResourceGroupItem.createResourceGroupItem((String)DataSchemeAuthResourceType.DATA_SCHEME_GROUP.toResourceId(group.getKey()), (String)group.getTitle(), (boolean)true);
        item.setIcons(NodeIconGetter.getIconByType((NodeType)NodeType.SCHEME_GROUP));
        return item;
    }

    protected static ResourceItem createResource(TaskDefine scheme) {
        ResourceItem item = ResourceItem.createResourceItem((String)DataSchemeAuthResourceType.DATA_SCHEME.toResourceId(scheme.getKey()), (String)scheme.getTitle(), (Object)true);
        item.setIcons(NodeIconGetter.getIconByType((NodeType)NodeType.SCHEME));
        return item;
    }

    public void doFilter(GranteeInfo granteeInfo, List<ResourceCategory> resourceCategorys) {
        String string = this.iNvwaSystemOptionService.get("nr-data-entry-group", "IS_ENABLE_DATA_AUTH_OPTIONS");
        if (string.equals("") || string.equals("0")) {
            Iterator<ResourceCategory> it = resourceCategorys.iterator();
            while (it.hasNext()) {
                ResourceCategory next = it.next();
                if (!next.getId().equals(this.getId())) continue;
                it.remove();
                break;
            }
        }
    }
}

