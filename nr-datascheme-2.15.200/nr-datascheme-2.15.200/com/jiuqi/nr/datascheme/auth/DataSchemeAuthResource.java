/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nvwa.authority.extend.DefaultResourceCategory
 *  com.jiuqi.nvwa.authority.privilege.PrivilegeDefinition
 *  com.jiuqi.nvwa.authority.privilege.PrivilegeDefinitionItem
 *  com.jiuqi.nvwa.authority.resource.Resource
 *  com.jiuqi.nvwa.authority.resource.ResourceGroupItem
 *  com.jiuqi.nvwa.authority.resource.ResourceItem
 *  com.jiuqi.nvwa.authority.resource.ResourceSearchResult
 *  com.jiuqi.nvwa.authority.util.AuthorityConst$AuthzRightAreaPlan
 *  com.jiuqi.nvwa.authority.util.AuthorityConst$Category_Type
 *  com.jiuqi.nvwa.authority.vo.GranteeInfo
 */
package com.jiuqi.nr.datascheme.auth;

import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.auth.DataSchemeAuthResourceType;
import com.jiuqi.nr.datascheme.auth.DataSchemePathProvider;
import com.jiuqi.nr.datascheme.common.Consts;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import com.jiuqi.nvwa.authority.extend.DefaultResourceCategory;
import com.jiuqi.nvwa.authority.privilege.PrivilegeDefinition;
import com.jiuqi.nvwa.authority.privilege.PrivilegeDefinitionItem;
import com.jiuqi.nvwa.authority.resource.Resource;
import com.jiuqi.nvwa.authority.resource.ResourceGroupItem;
import com.jiuqi.nvwa.authority.resource.ResourceItem;
import com.jiuqi.nvwa.authority.resource.ResourceSearchResult;
import com.jiuqi.nvwa.authority.util.AuthorityConst;
import com.jiuqi.nvwa.authority.vo.GranteeInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class DataSchemeAuthResource
extends DefaultResourceCategory {
    private static final long serialVersionUID = 2868491707156030870L;
    @Autowired
    private IDesignDataSchemeService iDesignDataSchemeService;
    @Autowired
    private DataSchemePathProvider dataSchemePathProvider;

    public String getId() {
        return "datascheme-auth-resource-category";
    }

    public String getTitle() {
        return "\u6570\u636e\u65b9\u6848";
    }

    public String getGroupTitle() {
        return "\u62a5\u8868";
    }

    public int getSeq() {
        return 0;
    }

    public boolean isSupportReject() {
        return true;
    }

    public AuthorityConst.AuthzRightAreaPlan getAuthRightAreaPlan() {
        return AuthorityConst.AuthzRightAreaPlan.All;
    }

    public AuthorityConst.Category_Type getCategoryType() {
        return AuthorityConst.Category_Type.NORMAL;
    }

    public List<String> getBasePrivilegeIds() {
        return Collections.singletonList("datascheme_auth_resource_read");
    }

    public List<PrivilegeDefinition> getPrivilegeDefinition(GranteeInfo granteeInfo) {
        ArrayList<PrivilegeDefinition> list = new ArrayList<PrivilegeDefinition>();
        PrivilegeDefinitionItem item = new PrivilegeDefinitionItem();
        item.setPrivilegeId("22222222-1111-1111-1111-222222222222");
        item.setPrivilegeTitle("\u540c\u4e0a\u7ea7");
        list.add((PrivilegeDefinition)item);
        item = new PrivilegeDefinitionItem();
        item.setPrivilegeId("datascheme_auth_resource_read");
        item.setPrivilegeTitle("\u8bbf\u95ee");
        list.add((PrivilegeDefinition)item);
        item = new PrivilegeDefinitionItem();
        item.setPrivilegeId("datascheme_auth_resource_write");
        item.setPrivilegeTitle("\u7f16\u8f91");
        list.add((PrivilegeDefinition)item);
        return list;
    }

    public List<Resource> getRootResources(GranteeInfo granteeInfo) {
        ArrayList<Resource> resources = new ArrayList<Resource>();
        resources.add((Resource)DataSchemeAuthResource.createResource(Consts.getDataSchemeRootGroup()));
        resources.add((Resource)DataSchemeAuthResource.createResource(Consts.getQueryDataSchemeRootGroup()));
        return resources;
    }

    public List<Resource> getChildResources(String resourceGroupId, GranteeInfo granteeInfo, Object param) {
        String groupId = DataSchemeAuthResourceType.DATA_SCHEME_GROUP.toObjectId(resourceGroupId);
        return this.getResources(groupId);
    }

    private List<Resource> getResources(String groupId) {
        List dataSchemes;
        ArrayList<Resource> resources = new ArrayList<Resource>();
        List schemeGroups = this.iDesignDataSchemeService.getDataGroupByParent(groupId);
        if (!CollectionUtils.isEmpty(schemeGroups)) {
            for (DesignDataGroup group : schemeGroups) {
                resources.add((Resource)DataSchemeAuthResource.createResource((DataGroup)group));
            }
        }
        if (!CollectionUtils.isEmpty(dataSchemes = this.iDesignDataSchemeService.getDataSchemeByParent(groupId))) {
            for (DesignDataScheme scheme : dataSchemes) {
                resources.add((Resource)DataSchemeAuthResource.createResource((DataScheme)scheme));
            }
        }
        return resources;
    }

    protected static ResourceGroupItem createResource() {
        ResourceGroupItem item = ResourceGroupItem.createResourceGroupItem((String)DataSchemeAuthResourceType.DATA_SCHEME_GROUP.toResourceId("00000000-0000-0000-0000-000000000000"), (String)"\u5168\u90e8\u6570\u636e\u65b9\u6848", (boolean)true);
        item.setIcons(NodeIconGetter.getIconByType(NodeType.SCHEME_GROUP));
        return item;
    }

    protected static ResourceGroupItem createResource(DataGroup group) {
        ResourceGroupItem item = ResourceGroupItem.createResourceGroupItem((String)DataSchemeAuthResourceType.DATA_SCHEME_GROUP.toResourceId(group.getKey()), (String)group.getTitle(), (boolean)true);
        item.setIcons(NodeIconGetter.getIconByType(NodeType.SCHEME_GROUP));
        return item;
    }

    protected static ResourceItem createResource(DataScheme scheme) {
        ResourceItem item = ResourceItem.createResourceItem((String)DataSchemeAuthResourceType.DATA_SCHEME.toResourceId(scheme.getKey()), (String)scheme.getTitle(), (Object)true);
        item.setIcons(NodeIconGetter.getIconByType(NodeType.SCHEME));
        return item;
    }

    public boolean enableSearch() {
        return true;
    }

    public List<ResourceSearchResult> searchResource(String fuzzyTitle, String key) {
        ArrayList<ResourceSearchResult> results = new ArrayList<ResourceSearchResult>();
        if (StringUtils.hasText(key)) {
            results.add(new ResourceSearchResult(key, this.getResourceTitle(key), this.getResourcePath(key)));
        } else if (StringUtils.hasText(fuzzyTitle)) {
            // empty if block
        }
        return results;
    }

    private String getResourceTitle(String key) {
        DataSchemeAuthResourceType parseFrom = DataSchemeAuthResourceType.parseFrom(key);
        String objectId = parseFrom.toObjectId(key);
        String title = null;
        switch (parseFrom) {
            case DATA_SCHEME_GROUP: {
                if ("00000000-0000-0000-0000-000000000000".equals(objectId)) {
                    title = "\u5168\u90e8\u6570\u636e\u65b9\u6848";
                    break;
                }
                if ("00000000-0000-0000-0000-111111111111".equals(objectId)) {
                    title = "\u5168\u90e8\u67e5\u8be2\u6570\u636e\u65b9\u6848";
                    break;
                }
                DesignDataGroup group = this.iDesignDataSchemeService.getDataGroup(objectId);
                title = group.getTitle();
                break;
            }
            case DATA_SCHEME: {
                DesignDataScheme scheme = this.iDesignDataSchemeService.getDataScheme(objectId);
                title = scheme.getTitle();
                break;
            }
        }
        return title;
    }

    public List<String> getResourcePath(String key) {
        ArrayList<String> path = new ArrayList<String>();
        path.add(0, key);
        Resource parent = this.dataSchemePathProvider.getParent(key);
        while (null != parent) {
            path.add(0, parent.getId());
            parent = this.dataSchemePathProvider.getParent(parent.getId());
        }
        return path;
    }
}

