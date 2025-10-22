/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Authorization
 *  com.jiuqi.np.authz2.privilege.Authority
 *  com.jiuqi.np.authz2.privilege.Privilege
 *  com.jiuqi.np.authz2.privilege.PrivilegeType
 *  com.jiuqi.np.authz2.privilege.service.AuthorizationService
 *  com.jiuqi.np.authz2.privilege.service.PrivilegeMetaService
 *  com.jiuqi.np.authz2.privilege.service.PrivilegeService
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.common.resource.NrPrivilegeAuthority
 *  com.jiuqi.nr.common.resource.NrResource
 *  com.jiuqi.nr.common.resource.NrResourceCategory
 *  com.jiuqi.nr.common.resource.bean.NrAuthzRightAreaPlan
 *  com.jiuqi.nr.common.resource.bean.NrPrivilegeAuthorityItem
 *  com.jiuqi.nr.common.resource.bean.NrResourceGroupItem
 *  com.jiuqi.nr.common.resource.bean.NrResourceItem
 *  com.jiuqi.nr.common.resource.i18n.PrivilegeI18NService
 *  com.jiuqi.nvwa.authority.extend.DefaultResourceCategory
 *  com.jiuqi.nvwa.authority.privilege.PrivilegeDefinition
 *  com.jiuqi.nvwa.authority.privilege.PrivilegeDefinitionItem
 *  com.jiuqi.nvwa.authority.resource.Resource
 *  com.jiuqi.nvwa.authority.resource.ResourceGroupItem
 *  com.jiuqi.nvwa.authority.resource.ResourceSearchResult
 *  com.jiuqi.nvwa.authority.util.AuthorityConst$Category_Type
 *  com.jiuqi.nvwa.authority.vo.GranteeInfo
 */
package com.jiuqi.nr.dataresource.authority;

import com.jiuqi.np.authz2.Authorization;
import com.jiuqi.np.authz2.privilege.Authority;
import com.jiuqi.np.authz2.privilege.Privilege;
import com.jiuqi.np.authz2.privilege.PrivilegeType;
import com.jiuqi.np.authz2.privilege.service.AuthorizationService;
import com.jiuqi.np.authz2.privilege.service.PrivilegeMetaService;
import com.jiuqi.np.authz2.privilege.service.PrivilegeService;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.resource.NrPrivilegeAuthority;
import com.jiuqi.nr.common.resource.NrResource;
import com.jiuqi.nr.common.resource.NrResourceCategory;
import com.jiuqi.nr.common.resource.bean.NrAuthzRightAreaPlan;
import com.jiuqi.nr.common.resource.bean.NrPrivilegeAuthorityItem;
import com.jiuqi.nr.common.resource.bean.NrResourceGroupItem;
import com.jiuqi.nr.common.resource.bean.NrResourceItem;
import com.jiuqi.nr.common.resource.i18n.PrivilegeI18NService;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.ResourceNode;
import com.jiuqi.nr.dataresource.authority.Util;
import com.jiuqi.nr.dataresource.dao.IDataResourceDao;
import com.jiuqi.nr.dataresource.dao.IDataResourceDefineDao;
import com.jiuqi.nr.dataresource.dao.IDataResourceDefineGroupDao;
import com.jiuqi.nr.dataresource.entity.DataResourceDO;
import com.jiuqi.nr.dataresource.entity.ResourceTreeDO;
import com.jiuqi.nr.dataresource.entity.ResourceTreeGroup;
import com.jiuqi.nr.dataresource.loader.DataResourceLevelLoader;
import com.jiuqi.nr.dataresource.service.IDataResourceService;
import com.jiuqi.nr.dataresource.service.impl.AuthRootBuildTreeVisitor;
import com.jiuqi.nvwa.authority.extend.DefaultResourceCategory;
import com.jiuqi.nvwa.authority.privilege.PrivilegeDefinition;
import com.jiuqi.nvwa.authority.privilege.PrivilegeDefinitionItem;
import com.jiuqi.nvwa.authority.resource.Resource;
import com.jiuqi.nvwa.authority.resource.ResourceGroupItem;
import com.jiuqi.nvwa.authority.resource.ResourceSearchResult;
import com.jiuqi.nvwa.authority.util.AuthorityConst;
import com.jiuqi.nvwa.authority.vo.GranteeInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DataResourceResourceCategory
extends DefaultResourceCategory
implements NrResourceCategory {
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private PrivilegeI18NService privilegeI18NService;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private PrivilegeMetaService privilegeMetaService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private DataResourceLevelLoader loader;
    @Autowired
    private IDataResourceService resourceService;
    @Autowired
    private IDataResourceDefineGroupDao groupDao;
    @Autowired
    private IDataResourceDefineDao defineDao;
    @Autowired
    private IDataResourceDao resourceDao;
    private static final List<String> all = Arrays.asList("DataResource_read", "DataResource_write");
    private static final List<String> onlyRead = Arrays.asList("DataResource_read");

    public int getPrivilegeType() {
        return PrivilegeType.OBJECT_INHERIT.getValue();
    }

    public List<NrPrivilegeAuthority> getPrivilegeAuthority() {
        return null;
    }

    public String getId() {
        return "DataResourceTreeResourceCategoryImpl_ID";
    }

    public String getTitle() {
        return "\u6307\u6807\u89c6\u56fe";
    }

    public String getPluginName() {
        return "auth-plugin";
    }

    public AuthorityConst.Category_Type getCategoryType() {
        return AuthorityConst.Category_Type.CUSTOM;
    }

    public int getSeq() {
        return 100;
    }

    public List<NrResource> getRoot(String granteeId, int privilegeType, Object param) {
        return this.buildChildren(granteeId, "00000000-0000-0000-0000-000000000000", NodeType.TREE_GROUP.getValue());
    }

    public List<NrResource> getChild(String resourceGroupId, String granteeId, Object param) {
        String[] arr = Util.splitResourceId(resourceGroupId);
        return this.buildChildren(granteeId, arr[1], Integer.valueOf(arr[0]));
    }

    public List<PrivilegeDefinition> getPrivilegeDefinition(GranteeInfo granteeInfo) {
        ArrayList<PrivilegeDefinition> list = new ArrayList<PrivilegeDefinition>(10);
        PrivilegeDefinitionItem item = new PrivilegeDefinitionItem();
        item.setPrivilegeId("22222222-1111-1111-1111-222222222222");
        item.setPrivilegeTitle("\u540c\u4e0a\u7ea7");
        list.add((PrivilegeDefinition)item);
        if (granteeInfo == null || !granteeInfo.isFromAudit().booleanValue()) {
            PrivilegeDefinitionItem item1 = new PrivilegeDefinitionItem();
            item1.setPrivilegeId("22222222-3333-3333-3333-222222222222");
            item1.setPrivilegeTitle("\u6388\u6743");
            list.add((PrivilegeDefinition)item1);
        }
        PrivilegeDefinitionItem read = new PrivilegeDefinitionItem();
        read.setPrivilegeId("DataResource_read");
        read.setPrivilegeTitle("\u8bbf\u95ee");
        list.add((PrivilegeDefinition)read);
        PrivilegeDefinitionItem write = new PrivilegeDefinitionItem();
        write.setPrivilegeId("DataResource_write");
        write.setPrivilegeTitle("\u7f16\u8f91");
        list.add((PrivilegeDefinition)write);
        return list;
    }

    private List<NrPrivilegeAuthority> getPrivilegeAuthoritys(String granteeId, List<String> privilegeIds, String resource) {
        if (!StringUtils.hasText(granteeId)) {
            return null;
        }
        ArrayList<NrPrivilegeAuthority> nrPrivilegeAuthorities = new ArrayList<NrPrivilegeAuthority>();
        NrPrivilegeAuthorityItem nrPrivilegeAuthorityItemSuper = new NrPrivilegeAuthorityItem();
        nrPrivilegeAuthorities.add((NrPrivilegeAuthority)nrPrivilegeAuthorityItemSuper);
        Authority delegateAuthority = null;
        Boolean delegate = null;
        boolean accInhert = true;
        boolean isRole = this.roleService.exists(granteeId);
        for (String privilegeId : privilegeIds) {
            Authority authorityAcc;
            boolean inhert = false;
            if ("22222222-2222-2222-2222-222222222222".equals(privilegeId) || "22222222-1111-1111-1111-222222222222".equals(privilegeId)) continue;
            boolean edit = this.privilegeService.hasAuth(privilegeId, NpContextHolder.getContext().getIdentityId(), (Object)resource);
            Privilege privilege = this.privilegeMetaService.getPrivilege(privilegeId);
            String privilegeTitle = this.privilegeI18NService.getTitleByPrivilege(privilege);
            String name = privilege.getName();
            if (isRole) {
                Optional query = this.authorizationService.query(privilegeId, granteeId, resource);
                if (query.isPresent()) {
                    authorityAcc = ((Authorization)query.get()).getAccessAuthority();
                    if (delegate == null) {
                        delegateAuthority = ((Authorization)query.get()).getDelegateAuthority();
                        delegate = false;
                    }
                    accInhert = false;
                } else {
                    authorityAcc = this.privilegeService.getAuthority(privilegeId, granteeId, (Object)resource);
                    if (delegate == null) {
                        delegateAuthority = this.privilegeService.getDelegateAuthority(privilegeId, granteeId, (Object)resource);
                        delegate = true;
                    }
                    inhert = true;
                }
            } else {
                Authority authorityByIdentityCale = this.privilegeService.getAuthority(privilegeId, granteeId, (Object)resource);
                Authority authorityByIdentity = null;
                Optional query = this.authorizationService.query(privilegeId, granteeId, resource);
                if (query.isPresent()) {
                    authorityByIdentity = ((Authorization)query.get()).getAccessAuthority();
                    accInhert = false;
                }
                authorityAcc = authorityByIdentityCale;
                boolean bl = inhert = !authorityByIdentityCale.equals((Object)authorityByIdentity);
                if (delegate == null) {
                    delegateAuthority = this.privilegeService.getDelegateAuthority(privilegeId, granteeId, (Object)resource);
                    delegate = inhert;
                }
            }
            nrPrivilegeAuthorities.add((NrPrivilegeAuthority)new NrPrivilegeAuthorityItem(privilegeId, name, privilegeTitle, authorityAcc, Boolean.valueOf(inhert), Boolean.valueOf(!edit)));
        }
        nrPrivilegeAuthorityItemSuper.setPrivilegeId("22222222-1111-1111-1111-222222222222");
        nrPrivilegeAuthorityItemSuper.setPrivilegeTitle("\u540c\u4e0a\u7ea7");
        nrPrivilegeAuthorityItemSuper.setPrivilegeName("superior");
        if (accInhert) {
            nrPrivilegeAuthorityItemSuper.setAuthority(Authority.ALLOW);
        } else {
            nrPrivilegeAuthorityItemSuper.setAuthority(Authority.UNKNOW);
        }
        nrPrivilegeAuthorityItemSuper.setReadOnly(Boolean.valueOf(false));
        return nrPrivilegeAuthorities;
    }

    private List<NrResource> buildChildren(String granteeId, String dataSourceId, int type) {
        ArrayList<NrResource> list = new ArrayList<NrResource>();
        AuthRootBuildTreeVisitor build = new AuthRootBuildTreeVisitor(this.privilegeService, this.resourceService);
        this.loader.walkDataResourceTree(new ResourceNode(dataSourceId, type), build);
        List<Resource> resources = build.getValue();
        List<String> privileges = NodeType.TREE_GROUP.getValue() == type ? all : onlyRead;
        for (Resource r : resources) {
            List<NrPrivilegeAuthority> nrPrivilegeAuthorities = this.getPrivilegeAuthoritys(granteeId, privileges, r.getId());
            if (r instanceof ResourceGroupItem) {
                list.add((NrResource)NrResourceGroupItem.create((String)r.getId(), (String)r.getTitle(), (int)this.getPrivilegeType(), privileges, nrPrivilegeAuthorities, (int)NrAuthzRightAreaPlan.ALL.getValue()));
                continue;
            }
            list.add((NrResource)new NrResourceItem(r.getId(), r.getTitle(), this.getPrivilegeType(), privileges, nrPrivilegeAuthorities, NrAuthzRightAreaPlan.ALL.getValue()));
        }
        return list;
    }

    public List<ResourceSearchResult> searchResource(String fuzzyTitle, String key) {
        ArrayList<ResourceSearchResult> results = new ArrayList<ResourceSearchResult>();
        if (StringUtils.hasText(key)) {
            results.add(new ResourceSearchResult(key, this.getResourceTitle(key), this.getPath(key)));
        }
        return results;
    }

    private String getResourceTitle(String resourceId) {
        String[] arr = Util.splitResourceId(resourceId);
        NodeType nodeType = NodeType.valueOf(Integer.parseInt(arr[0]));
        String dataSourceId = arr[1];
        if ("00000000-0000-0000-0000-000000000000".equals(dataSourceId)) {
            return "\u6307\u6807\u89c6\u56fe";
        }
        switch (nodeType) {
            case TREE_GROUP: {
                ResourceTreeGroup group = (ResourceTreeGroup)this.groupDao.get(dataSourceId);
                return group != null ? group.getTitle() : "";
            }
            case TREE: {
                ResourceTreeDO tree = (ResourceTreeDO)this.defineDao.get(dataSourceId);
                return tree != null ? tree.getTitle() : "";
            }
            case RESOURCE_GROUP: {
                DataResourceDO treeInnerGroup = (DataResourceDO)this.resourceDao.get(dataSourceId);
                return treeInnerGroup != null ? treeInnerGroup.getTitle() : "";
            }
        }
        return "";
    }

    private List<String> getPath(String key) {
        List<String> list = this.getParent(key, new ArrayList<String>());
        list.add(key);
        return list;
    }

    public List<String> getParent(String resourceId, List<String> pathList) {
        String[] arr = Util.splitResourceId(resourceId);
        NodeType nodeType = NodeType.valueOf(Integer.parseInt(arr[0]));
        String dataSourceId = arr[1];
        if ("00000000-0000-0000-0000-000000000000".equals(dataSourceId)) {
            return pathList;
        }
        switch (nodeType) {
            case TREE_GROUP: {
                ResourceTreeGroup group = (ResourceTreeGroup)this.groupDao.get(dataSourceId);
                if (group == null) break;
                this.handleTreeGroup(pathList, group.getParentKey());
                break;
            }
            case TREE: {
                ResourceTreeDO tree = (ResourceTreeDO)this.defineDao.get(dataSourceId);
                if (tree == null) break;
                this.handleTreeGroup(pathList, tree.getGroupKey());
                break;
            }
            case RESOURCE_GROUP: {
                DataResourceDO pTreeInnerGroup;
                DataResourceDO treeInnerGroup = (DataResourceDO)this.resourceDao.get(dataSourceId);
                if (treeInnerGroup == null) break;
                if (StringUtils.hasText(treeInnerGroup.getParentKey()) && (pTreeInnerGroup = (DataResourceDO)this.resourceDao.get(treeInnerGroup.getParentKey())) != null) {
                    pathList.add(0, Util.getResourceIdByType(pTreeInnerGroup.getKey(), NodeType.RESOURCE_GROUP.getValue()));
                    break;
                }
                ResourceTreeDO pTree = (ResourceTreeDO)this.defineDao.get(treeInnerGroup.getResourceDefineKey());
                pathList.add(0, Util.getResourceIdByType(pTree.getKey(), NodeType.TREE.getValue()));
            }
        }
        return this.getParent(pathList.get(0), pathList);
    }

    private void handleTreeGroup(List<String> pathList, String groupKey) {
        ResourceTreeGroup pGroup = (ResourceTreeGroup)this.groupDao.get(groupKey);
        String parent = "00000000-0000-0000-0000-000000000000";
        if (pGroup != null) {
            parent = pGroup.getKey();
        }
        pathList.add(0, Util.getResourceIdByType(parent, NodeType.TREE_GROUP.getValue()));
    }
}

