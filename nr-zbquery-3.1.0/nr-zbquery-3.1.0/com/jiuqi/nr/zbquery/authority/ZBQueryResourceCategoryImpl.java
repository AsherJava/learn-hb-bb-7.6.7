/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.authority.extend.DefaultAuthQueryService
 *  com.jiuqi.nvwa.authority.extend.DefaultResourceCategory
 *  com.jiuqi.nvwa.authority.privilege.PrivilegeDefinition
 *  com.jiuqi.nvwa.authority.privilege.PrivilegeDefinitionItem
 *  com.jiuqi.nvwa.authority.resource.Resource
 *  com.jiuqi.nvwa.authority.resource.ResourceGroupItem
 *  com.jiuqi.nvwa.authority.resource.ResourceItem
 *  com.jiuqi.nvwa.authority.util.AuthorityConst$AuthzRightAreaPlan
 *  com.jiuqi.nvwa.authority.util.AuthorityConst$Category_Type
 *  com.jiuqi.nvwa.authority.vo.GranteeInfo
 */
package com.jiuqi.nr.zbquery.authority;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.zbquery.bean.ZBQueryGroup;
import com.jiuqi.nr.zbquery.bean.ZBQueryInfo;
import com.jiuqi.nr.zbquery.common.ZBQueryResourceConst;
import com.jiuqi.nr.zbquery.service.ZBQueryGroupService;
import com.jiuqi.nr.zbquery.service.ZBQueryInfoService;
import com.jiuqi.nvwa.authority.extend.DefaultAuthQueryService;
import com.jiuqi.nvwa.authority.extend.DefaultResourceCategory;
import com.jiuqi.nvwa.authority.privilege.PrivilegeDefinition;
import com.jiuqi.nvwa.authority.privilege.PrivilegeDefinitionItem;
import com.jiuqi.nvwa.authority.resource.Resource;
import com.jiuqi.nvwa.authority.resource.ResourceGroupItem;
import com.jiuqi.nvwa.authority.resource.ResourceItem;
import com.jiuqi.nvwa.authority.util.AuthorityConst;
import com.jiuqi.nvwa.authority.vo.GranteeInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

@Deprecated
public class ZBQueryResourceCategoryImpl
extends DefaultResourceCategory {
    private static final long serialVersionUID = -3564839741445156629L;
    @Autowired
    private DefaultAuthQueryService defaultAuthQueryService;
    @Autowired
    private ZBQueryGroupService zBQueryGroupService;
    @Autowired
    private ZBQueryInfoService zBQueryInfoService;

    public String getId() {
        return "ZBQUERYRESOURCE_ID";
    }

    public String getTitle() {
        return "\u6307\u6807\u7efc\u5408\u67e5\u8be2";
    }

    public String getGroupTitle() {
        return "\u62a5\u8868";
    }

    public int getSeq() {
        return 27;
    }

    public AuthorityConst.AuthzRightAreaPlan getAuthRightAreaPlan() {
        return AuthorityConst.AuthzRightAreaPlan.All;
    }

    public AuthorityConst.Category_Type getCategoryType() {
        return AuthorityConst.Category_Type.NORMAL;
    }

    public List<String> getPrivilegeIds() {
        return Arrays.asList("22222222-1111-1111-1111-222222222222", "ZBQueryResource_read", "ZBQueryResource_edit", "22222222-2222-2222-2222-222222222222");
    }

    public List<PrivilegeDefinition> getPrivilegeDefinition() {
        ArrayList<PrivilegeDefinition> list = new ArrayList<PrivilegeDefinition>();
        PrivilegeDefinitionItem superItem = new PrivilegeDefinitionItem();
        superItem.setPrivilegeId("22222222-1111-1111-1111-222222222222");
        superItem.setPrivilegeTitle("\u540c\u4e0a\u7ea7");
        PrivilegeDefinitionItem grandDefinition = new PrivilegeDefinitionItem();
        grandDefinition.setPrivilegeId("22222222-2222-2222-2222-222222222222");
        grandDefinition.setPrivilegeTitle("\u6388\u6743");
        PrivilegeDefinitionItem read = new PrivilegeDefinitionItem();
        read.setPrivilegeId("ZBQueryResource_read");
        read.setPrivilegeTitle("\u8bbf\u95ee");
        PrivilegeDefinitionItem edit = new PrivilegeDefinitionItem();
        edit.setPrivilegeId("ZBQueryResource_edit");
        edit.setPrivilegeTitle("\u7f16\u8f91");
        list.add((PrivilegeDefinition)superItem);
        list.add((PrivilegeDefinition)read);
        list.add((PrivilegeDefinition)edit);
        list.add((PrivilegeDefinition)grandDefinition);
        return list;
    }

    public List<String> getBasePrivilegeIds() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("ZBQueryResource_read");
        return list;
    }

    public List<Resource> getRootResources(GranteeInfo granteeInfo) {
        ArrayList<Resource> list = new ArrayList<Resource>();
        ResourceGroupItem virtualGroup = ResourceGroupItem.createResourceGroupItem((String)"ZBQUERYRESOURCE_G_00000000-0000-0000-0000-000000000000", (String)ZBQueryResourceConst.VIRTUAL_RESOURCE_TITLE, (boolean)true, (boolean)true);
        list.add((Resource)virtualGroup);
        List<ZBQueryGroup> rootZBQueryGroup = this.zBQueryGroupService.getQueryGroupChildren(null);
        if (!CollectionUtils.isEmpty(rootZBQueryGroup)) {
            String resourceId = null;
            for (ZBQueryGroup group : rootZBQueryGroup) {
                resourceId = "ZBQUERYRESOURCE_G_" + group.getId();
                boolean hasDelegate = this.defaultAuthQueryService.hasDelegateAuth("ZBQueryResource_read", NpContextHolder.getContext().getIdentityId(), (Object)resourceId);
                if (!hasDelegate) continue;
                list.add((Resource)ResourceGroupItem.createResourceGroupItem((String)resourceId, (String)group.getTitle(), (boolean)true));
            }
        }
        return list;
    }

    public List<Resource> getChildResources(String resourceGroupId, GranteeInfo granteeInfo) {
        List<ZBQueryInfo> zbQueryInfo;
        ArrayList<Resource> list = new ArrayList<Resource>();
        String id = resourceGroupId.substring("ZBQUERYRESOURCE_G_".length());
        List<ZBQueryGroup> zbQueryGroupByGroup = this.zBQueryGroupService.getQueryGroupChildren(id);
        String resourceId = null;
        if (!CollectionUtils.isEmpty(zbQueryGroupByGroup)) {
            for (ZBQueryGroup group : zbQueryGroupByGroup) {
                resourceId = "ZBQUERYRESOURCE_G_" + group.getId();
                boolean hasDelegate = this.defaultAuthQueryService.hasDelegateAuth("ZBQueryResource_read", NpContextHolder.getContext().getIdentityId(), (Object)resourceId);
                if (!hasDelegate) continue;
                list.add((Resource)ResourceGroupItem.createResourceGroupItem((String)resourceId, (String)group.getTitle(), (boolean)true));
            }
        }
        if (!CollectionUtils.isEmpty(zbQueryInfo = this.zBQueryInfoService.getQueryInfoByGroup(id))) {
            for (ZBQueryInfo info : zbQueryInfo) {
                resourceId = "ZBQUERYRESOURCE_I_" + info.getId();
                boolean hasDelegate = this.defaultAuthQueryService.hasDelegateAuth("ZBQueryResource_read", NpContextHolder.getContext().getIdentityId(), (Object)resourceId);
                if (!hasDelegate) continue;
                list.add((Resource)ResourceItem.createResourceItem((String)resourceId, (String)info.getTitle()));
            }
        }
        return list;
    }
}

