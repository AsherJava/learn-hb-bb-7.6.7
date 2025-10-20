/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Role
 *  com.jiuqi.np.authz2.privilege.Authority
 *  com.jiuqi.np.authz2.privilege.AuthzType
 *  com.jiuqi.np.authz2.privilege.Privilege
 *  com.jiuqi.np.authz2.privilege.PrivilegeType
 *  com.jiuqi.np.authz2.privilege.service.AuthorizationService
 *  com.jiuqi.np.authz2.privilege.service.PrivilegeMetaService
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nvwa.authority.resource.ResourceSearchResult
 *  javax.validation.constraints.NotEmpty
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.nr.common.resource.service;

import com.jiuqi.np.authz2.Role;
import com.jiuqi.np.authz2.privilege.Authority;
import com.jiuqi.np.authz2.privilege.AuthzType;
import com.jiuqi.np.authz2.privilege.Privilege;
import com.jiuqi.np.authz2.privilege.PrivilegeType;
import com.jiuqi.np.authz2.privilege.service.AuthorizationService;
import com.jiuqi.np.authz2.privilege.service.PrivilegeMetaService;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.common.resource.NrPrivilegeAuthority;
import com.jiuqi.nr.common.resource.NrResource;
import com.jiuqi.nr.common.resource.NrResourceCategory;
import com.jiuqi.nr.common.resource.NrResourceGroup;
import com.jiuqi.nr.common.resource.authority.IAuthMgr;
import com.jiuqi.nr.common.resource.authority.RoleAuthority;
import com.jiuqi.nr.common.resource.authority.RoleCheckType;
import com.jiuqi.nr.common.resource.bean.NrResourceGroupItem;
import com.jiuqi.nr.common.resource.bean.NrResourceItem;
import com.jiuqi.nr.common.resource.bean.PermissionStatusData;
import com.jiuqi.nr.common.resource.bean.PrivilegeWebImpl;
import com.jiuqi.nr.common.resource.bean.ResourceWebImpl;
import com.jiuqi.nr.common.resource.exception.AuthErrorEnum;
import com.jiuqi.nr.common.resource.service.IAuthService;
import com.jiuqi.nr.common.resource.service.ResourceService;
import com.jiuqi.nvwa.authority.resource.ResourceSearchResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class AuthServiceImpl
implements IAuthService {
    private final String VIRTUAL_ID = "0000-0000-0000-000000000000";
    private final String V_GROUP = "V_GROUP_";
    private static final int ROOT_LEVEL = 2;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private RoleAuthority roleAuthority;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private IAuthMgr iAuthMgr;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private PrivilegeMetaService privilegeMetaService;

    @Override
    public List<ITree<ResourceWebImpl>> getRootNode(String categoryId, String granteeId, boolean lazyQueryAuth, Object param) {
        NrResourceCategory nrResourceCategory = this.resourceService.getResourceCategory(categoryId);
        Assert.notNull((Object)nrResourceCategory, "\u53c2\u6570\u9519\u8bef");
        List<NrResource> nrResources = this.resourceService.getRootResources(categoryId, granteeId, param);
        if (!lazyQueryAuth) {
            boolean hasAuth = false;
            if (!CollectionUtils.isEmpty(nrResources)) {
                for (NrResource nrResource : nrResources) {
                    List<NrPrivilegeAuthority> privilegeAuthority = nrResource.getPrivilegeAuthority();
                    if (CollectionUtils.isEmpty(privilegeAuthority)) continue;
                    hasAuth = true;
                    break;
                }
                if (!hasAuth) {
                    nrResources = this.resourceService.getResourcesAuthority(categoryId, nrResources, granteeId, param);
                }
            }
        }
        NrResourceGroupItem rootResource = NrResourceGroupItem.create("V_GROUP_0000-0000-0000-000000000000", nrResourceCategory.getTitle(), nrResourceCategory.getPrivilegeType(), null, null, nrResourceCategory.getNrAuthRightAreaPlan(), null);
        ResourceWebImpl rootWeb = new ResourceWebImpl(rootResource);
        rootWeb.setCategoryId(nrResourceCategory.getId());
        ITree<ResourceWebImpl> rootTree = new ITree<ResourceWebImpl>(rootWeb);
        rootTree.setLeaf(false);
        ArrayList<ITree<ResourceWebImpl>> nodes = new ArrayList<ITree<ResourceWebImpl>>();
        nodes.add(rootTree);
        ArrayList<ITree<ITree<ResourceWebImpl>>> childes = new ArrayList<ITree<ITree<ResourceWebImpl>>>();
        rootTree.setChildren(childes);
        for (NrResource nrResource : nrResources) {
            if (nrResource instanceof NrResourceGroup) {
                NrResourceGroup resourceGroup = (NrResourceGroup)nrResource;
                ResourceWebImpl resourceWebImpl = new ResourceWebImpl(resourceGroup);
                resourceWebImpl.setParam(nrResource.getParam());
                resourceWebImpl.setCategoryId(categoryId);
                resourceWebImpl.setAuthRightAreaPlan(resourceGroup.getAuthRightAreaPlan());
                resourceWebImpl.setPrivilegeAuthorities(nrResource.getPrivilegeAuthority());
                ITree<ResourceWebImpl> child = new ITree<ResourceWebImpl>(resourceWebImpl);
                child.setLeaf(false);
                childes.add(child);
                continue;
            }
            ResourceWebImpl resourceWebImpl = new ResourceWebImpl(nrResource);
            resourceWebImpl.setCategoryId(categoryId);
            resourceWebImpl.setPrivilegeAuthorities(nrResource.getPrivilegeAuthority());
            resourceWebImpl.setAuthRightAreaPlan(nrResource.getAuthRightAreaPlan());
            ITree<ResourceWebImpl> child = new ITree<ResourceWebImpl>(resourceWebImpl);
            child.setLeaf(true);
            childes.add(child);
        }
        return nodes;
    }

    @Override
    public List<ITree<ResourceWebImpl>> getChildNode(ResourceWebImpl impl, boolean lazyQueryAuth) {
        ArrayList<ITree<ResourceWebImpl>> nodes = new ArrayList<ITree<ResourceWebImpl>>();
        String categoryId = impl.getCategoryId();
        String ownerId = impl.getOwnerId();
        String key = impl.getKey();
        List<NrResource> nrResources = key.equals("V_GROUP_0000-0000-0000-000000000000") ? this.resourceService.getRootResources(categoryId, ownerId, impl.getParam()) : this.resourceService.getChildResources(categoryId, key, ownerId, impl.getParam());
        if (!lazyQueryAuth) {
            boolean hasAuth = false;
            if (!CollectionUtils.isEmpty(nrResources)) {
                for (NrResource nrResource : nrResources) {
                    List<NrPrivilegeAuthority> privilegeAuthority = nrResource.getPrivilegeAuthority();
                    if (CollectionUtils.isEmpty(privilegeAuthority)) continue;
                    hasAuth = true;
                    break;
                }
                if (!hasAuth) {
                    nrResources = this.resourceService.getResourcesAuthority(categoryId, nrResources, ownerId, impl.getParam());
                }
            }
        }
        for (NrResource nrResource : nrResources) {
            if (nrResource instanceof NrResourceGroup) {
                NrResourceGroup resourceGroup = (NrResourceGroup)nrResource;
                ResourceWebImpl resourceWebImpl = new ResourceWebImpl(resourceGroup);
                resourceWebImpl.setParam(nrResource.getParam());
                resourceWebImpl.setCategoryId(categoryId);
                resourceWebImpl.setAuthRightAreaPlan(resourceGroup.getAuthRightAreaPlan());
                resourceWebImpl.setPrivilegeAuthorities(nrResource.getPrivilegeAuthority());
                ITree<ResourceWebImpl> child = new ITree<ResourceWebImpl>(resourceWebImpl);
                child.setLeaf(false);
                nodes.add(child);
                continue;
            }
            ResourceWebImpl resourceWebImpl = new ResourceWebImpl(nrResource);
            resourceWebImpl.setCategoryId(categoryId);
            resourceWebImpl.setPrivilegeAuthorities(nrResource.getPrivilegeAuthority());
            resourceWebImpl.setAuthRightAreaPlan(nrResource.getAuthRightAreaPlan());
            ITree<ResourceWebImpl> child = new ITree<ResourceWebImpl>(resourceWebImpl);
            child.setLeaf(true);
            nodes.add(child);
        }
        return nodes;
    }

    @Override
    public List<ITree<ResourceWebImpl>> getRootNode(@NotNull String categoryId, @NotNull String granteeId, Object param) {
        return this.getRootNode(categoryId, granteeId, false, param);
    }

    @Override
    public List<ITree<ResourceWebImpl>> getChildNode(ResourceWebImpl impl) {
        return this.getChildNode(impl, false);
    }

    @Override
    public List<ITree<ResourceWebImpl>> getSelectedNode(@NotNull @NotEmpty List<ResourceWebImpl> impl) throws JQException {
        if (impl.size() == 1 && impl.get(0).getFromAudit()) {
            ResourceWebImpl selectNode = impl.get(0);
            NrResourceCategory nrResourceCategory = this.resourceService.getResourceCategory(selectNode.getCategoryId());
            List resourceSearchResults = nrResourceCategory.searchResource(null, selectNode.getKey());
            if (!CollectionUtils.isEmpty(resourceSearchResults)) {
                ArrayList<ResourceWebImpl> parentNodes = new ArrayList<ResourceWebImpl>();
                for (String parent : ((ResourceSearchResult)resourceSearchResults.get(0)).getPath()) {
                    ResourceWebImpl parentNode = new ResourceWebImpl();
                    parentNode.setKey(parent);
                    parentNode.setOwnerId(selectNode.getOwnerId());
                    parentNode.setCategoryId(selectNode.getCategoryId());
                    parentNode.setParam(selectNode.getParam());
                    parentNodes.add(parentNode);
                }
                return this.locateTree(parentNodes);
            }
            return Collections.emptyList();
        }
        return this.locateTree(impl);
    }

    private List<ITree<ResourceWebImpl>> locateTree(List<ResourceWebImpl> impl) throws JQException {
        if (impl.size() < 2) {
            ResourceWebImpl vNode = impl.get(0);
            return this.getRootNode(vNode.getCategoryId(), vNode.getOwnerId(), vNode.getParam());
        }
        ResourceWebImpl root = impl.get(1);
        String ownerId = root.getOwnerId();
        String categoryId = root.getCategoryId();
        if (!StringUtils.hasLength(ownerId) || !StringUtils.hasLength(categoryId)) {
            throw new IllegalArgumentException("\u5165\u53c2\u9519\u8bef");
        }
        List<ITree<ResourceWebImpl>> vRootNodes = this.getRootNode(categoryId, ownerId, root.getParam());
        ITree<ResourceWebImpl> resourceWebITree = vRootNodes.get(0);
        List<ITree<ResourceWebImpl>> rootNodes = resourceWebITree.getChildren();
        int loop = 0;
        block0: for (ResourceWebImpl path : impl) {
            Optional<ITree> rootNode;
            ++loop;
            if (path.getKey().contains("V_GROUP_")) continue;
            List childNode = null;
            if (loop != impl.size()) {
                childNode = this.getChildNode(path);
            }
            if ((rootNode = rootNodes.stream().filter(n -> n.getKey().equals(root.getKey())).findFirst()).isPresent()) {
                ITree resourceRootNode = rootNode.get();
                Iterator iterator = resourceRootNode.iterator(ITree.traverPloy.BREADTH_FIRST);
                while (iterator.hasNext()) {
                    ITree next = iterator.next();
                    if (!((ResourceWebImpl)next.getData()).getKey().equals(path.getKey())) continue;
                    if (loop == impl.size()) {
                        next.setSelected(true);
                        continue block0;
                    }
                    next.setChildren(childNode);
                    next.setExpanded(true);
                    continue block0;
                }
                continue;
            }
            throw new JQException((ErrorEnum)AuthErrorEnum.AUTH, "\u53c2\u6570\u9519\u8bef,\u65e0\u6cd5\u5b9a\u4f4d\u6811\u5f62");
        }
        ArrayList<ITree<ResourceWebImpl>> resp = new ArrayList<ITree<ResourceWebImpl>>();
        resp.add(resourceWebITree);
        return resp;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void savePrivilege(PrivilegeWebImpl impl) throws JQException {
        String ownerId = impl.getOwnerId();
        Map<String, Map<String, Authority>> resource = impl.getAuthority();
        Boolean isRole = impl.getIsRole();
        Boolean isDuty = impl.getDuty();
        String resCategoryId = impl.getResCategoryId();
        Map<String, Integer> resourceMapPrivilegeType = impl.getResourceMapPrivilegeType();
        Assert.notNull((Object)ownerId, "ownerId must be notNull");
        Assert.notNull((Object)resCategoryId, "resCategoryId must be notNull");
        Assert.notEmpty(resource, "resource must be notEmpty");
        Assert.notEmpty(resourceMapPrivilegeType, "resourceMapPrivilegeType must be notEmpty");
        StringBuilder log = new StringBuilder();
        PermissionStatusData authLog = new PermissionStatusData();
        authLog.setResourceIds(new ArrayList<String>(impl.getAuthority().keySet()));
        authLog.setResCategoryId(resCategoryId);
        if (isRole.booleanValue()) {
            String roleTitle;
            Role role = this.roleService.get(ownerId).orElse(null);
            String string = roleTitle = role != null ? role.getTitle() : "";
            if (this.roleAuthority.canMgrAuth(ownerId)) {
                this.beforeAuthorization(authLog, ownerId, isDuty);
                this.savePrivilege(resource, resourceMapPrivilegeType, ownerId, resCategoryId);
                this.afterAuthorization(authLog, ownerId, isDuty);
                String msg = this.toLogMessage(authLog);
                String logMsg = "\u4fee\u6539\u3010" + roleTitle + "\u3011\u89d2\u8272\u3010" + ownerId + "\u3011\r\n" + msg;
                LogHelper.info((String)"\u89d2\u8272\u7ba1\u7406", (String)"\u8bbe\u7f6e\u89d2\u8272\u6743\u9650", (String)logMsg);
                return;
            }
            log.append("\u89d2\u8272\u540d\u79f0").append(":").append(roleTitle).append(",").append(AuthErrorEnum.AUTH_ROLE_005.getMessage());
            LogHelper.warn((String)"\u89d2\u8272\u7ba1\u7406", (String)"\u8bbe\u7f6e\u89d2\u8272\u6743\u9650", (String)log.toString());
            throw new JQException((ErrorEnum)AuthErrorEnum.AUTH_ROLE_005);
        }
        User user = this.userService.get(ownerId);
        String userName = user != null ? user.getName() : "";
        try {
            ArrayList<String> userIds = new ArrayList<String>();
            userIds.add(ownerId);
            this.iAuthMgr.checkPermissionUserIds(userIds, RoleCheckType.ALL);
            this.beforeAuthorization(authLog, ownerId, isDuty);
            this.savePrivilege(resource, resourceMapPrivilegeType, ownerId, resCategoryId);
            this.afterAuthorization(authLog, ownerId, isDuty);
            String msg = this.toLogMessage(authLog);
            String logMsg = "\u4fee\u6539\u3010" + userName + "\u3011\u7528\u6237\u3010" + ownerId + "\u3011\r\n" + msg;
            LogHelper.info((String)LogModuleEnum.NRUSER.getTitle(), (String)"\u8bbe\u7f6e\u7528\u6237\u6743\u9650", (String)logMsg);
            return;
        }
        catch (Exception e) {
            log.append("\u7528\u6237\u540d\u79f0").append(":").append(userName).append(",").append(AuthErrorEnum.AUTH_ROLE_005.getMessage());
            LogHelper.warn((String)LogModuleEnum.NRUSER.getTitle(), (String)"\u8bbe\u7f6e\u7528\u6237\u6743\u9650", (String)log.toString());
            throw e;
        }
    }

    private String toLogMessage(PermissionStatusData authLog) {
        Map<String, Map<String, Authority>> oldPermissionStatus = authLog.getOldPermissionStatus();
        if (CollectionUtils.isEmpty(oldPermissionStatus)) {
            return "\u6210\u529f";
        }
        Map<String, Map<String, Authority>> permissionStatus = authLog.getPermissionStatus();
        if (CollectionUtils.isEmpty(permissionStatus)) {
            return "\u6210\u529f";
        }
        StringBuilder logBuilder = new StringBuilder();
        for (Map.Entry<String, Map<String, Authority>> oldStatus : oldPermissionStatus.entrySet()) {
            NrResource resource;
            String resourceId = oldStatus.getKey();
            Map<String, Authority> oldPrStatus = oldStatus.getValue();
            Map<String, Authority> prStatus = permissionStatus.get(resourceId);
            if (prStatus == null || (resource = authLog.getResource(resourceId)) == null) continue;
            boolean isLog = true;
            boolean updated = false;
            ArrayList<String> oldStatusMsg = new ArrayList<String>();
            ArrayList<String> statusMsg = new ArrayList<String>();
            for (Map.Entry<String, Authority> oldPrStat : oldPrStatus.entrySet()) {
                String privilegeTitle;
                String privilegeId = oldPrStat.getKey();
                if (privilegeId.equals("22222222-1111-1111-1111-222222222222")) continue;
                if (privilegeId.equals("22222222-2222-2222-2222-222222222222")) {
                    privilegeTitle = "\u6388\u6743";
                } else {
                    Privilege privilege = this.privilegeMetaService.getPrivilege(privilegeId);
                    String string = privilegeTitle = privilege == null ? null : privilege.getTitle();
                }
                if (privilegeTitle == null) {
                    isLog = false;
                    continue;
                }
                Authority privilegeAuthority = oldPrStat.getValue();
                String authorityLogTitle = this.getAuthorityLogTitle(privilegeAuthority);
                oldStatusMsg.add(authorityLogTitle + privilegeTitle);
                Authority authority = prStatus.get(privilegeId);
                if (authority == null) {
                    isLog = false;
                    continue;
                }
                String authorityStatus = this.getAuthorityLogTitle(authority);
                statusMsg.add(authorityStatus + privilegeTitle);
                if (updated || authorityStatus.equals(authorityLogTitle)) continue;
                updated = true;
            }
            if (!isLog || !updated) continue;
            logBuilder.append(resource.getTitle()).append(" \u3010").append(resourceId).append("\u3011").append(" \u7531 ").append(String.join((CharSequence)"|", oldStatusMsg)).append(" \u8bbe\u7f6e\u4e3a ").append(String.join((CharSequence)"|", statusMsg)).append("\r\n");
        }
        if (logBuilder.length() == 0) {
            logBuilder.append("\u672a\u53d1\u751f\u6539\u53d8\uff01");
        }
        return logBuilder.toString();
    }

    private String getAuthorityLogTitle(Authority authority) {
        if (authority == Authority.ALLOW) {
            return "\u5141\u8bb8";
        }
        return "\u7981\u6b62";
    }

    private void beforeAuthorization(PermissionStatusData authLog, String grantId, Boolean isDuty) {
        List<String> resourceIds = authLog.getResourceIds();
        String resCategoryId = authLog.getResCategoryId();
        LinkedHashMap<String, Map<String, Authority>> oldPermissionStatus = new LinkedHashMap<String, Map<String, Authority>>();
        authLog.setOldPermissionStatus(oldPermissionStatus);
        List<NrResource> authorityStatus = this.resourceService.getResourcesAuthorityById(resCategoryId, resourceIds, grantId, isDuty);
        for (NrResource nrStatus : authorityStatus) {
            LinkedHashMap<String, Authority> prStatus = new LinkedHashMap<String, Authority>();
            oldPermissionStatus.put(nrStatus.getId(), prStatus);
            List<NrPrivilegeAuthority> privilegeAuthority = nrStatus.getPrivilegeAuthority();
            for (NrPrivilegeAuthority nrPrivilegeAuthority : privilegeAuthority) {
                prStatus.put(nrPrivilegeAuthority.getPrivilegeId(), nrPrivilegeAuthority.getAuthority());
            }
        }
    }

    private void afterAuthorization(PermissionStatusData authLog, String grantId, Boolean isDuty) {
        List<String> resourceIds = authLog.getResourceIds();
        String resCategoryId = authLog.getResCategoryId();
        HashMap<String, Map<String, Authority>> permissionStatus = new HashMap<String, Map<String, Authority>>();
        authLog.setPermissionStatus(permissionStatus);
        List<NrResource> authorityStatus = this.resourceService.getResourcesAuthorityById(resCategoryId, resourceIds, grantId, isDuty);
        for (NrResource nrStatus : authorityStatus) {
            HashMap<String, Authority> prStatus = new HashMap<String, Authority>();
            permissionStatus.put(nrStatus.getId(), prStatus);
            authLog.addResource(nrStatus.getId(), nrStatus);
            List<NrPrivilegeAuthority> privilegeAuthority = nrStatus.getPrivilegeAuthority();
            for (NrPrivilegeAuthority nrPrivilegeAuthority : privilegeAuthority) {
                prStatus.put(nrPrivilegeAuthority.getPrivilegeId(), nrPrivilegeAuthority.getAuthority());
            }
        }
    }

    @Override
    public List<ResourceWebImpl> getPrivilege(List<ResourceWebImpl> impl) {
        String resourceCategoryId = null;
        String ownerId = null;
        ArrayList<NrResource> nrResources = new ArrayList<NrResource>();
        for (ResourceWebImpl webItem : impl) {
            NrResourceItem resourceItem = new NrResourceItem(webItem.getKey(), webItem.getTitle(), webItem.getPrivilegeType(), webItem.getPrivilegeIds(), null, webItem.getAuthRightAreaPlan(), webItem.getParam());
            if (resourceCategoryId == null) {
                resourceCategoryId = webItem.getCategoryId();
            }
            if (ownerId == null) {
                ownerId = webItem.getOwnerId();
            }
            nrResources.add(resourceItem);
        }
        List<NrResource> resourcesAuthority = this.resourceService.getResourcesAuthority(resourceCategoryId, nrResources, ownerId, impl.get(0).getParam());
        ArrayList<ResourceWebImpl> resourceWebs = new ArrayList<ResourceWebImpl>();
        for (NrResource nrResource : resourcesAuthority) {
            ResourceWebImpl resourceWebImpl = new ResourceWebImpl(nrResource);
            resourceWebImpl.setCategoryId(resourceWebImpl.getCategoryId());
            resourceWebImpl.setPrivilegeAuthorities(nrResource.getPrivilegeAuthority());
            resourceWebImpl.setAuthRightAreaPlan(nrResource.getAuthRightAreaPlan());
            resourceWebs.add(resourceWebImpl);
        }
        return resourceWebs;
    }

    private void savePrivilege(Map<String, Map<String, Authority>> resource, Map<String, Integer> resourceMapPrivilegeType, String ownerId, String resCategoryId) {
        for (Map.Entry<String, Map<String, Authority>> resourceEntry : resource.entrySet()) {
            String resourceId = resourceEntry.getKey();
            Integer privilegeType = resourceMapPrivilegeType.get(resourceId);
            boolean isSaveEntityPrivilege = privilegeType.intValue() == PrivilegeType.OBJECT.getValue();
            Map<String, Authority> privilegeIdMapAuthTypes = resourceEntry.getValue();
            boolean superior = this.isRowSuperior(privilegeIdMapAuthTypes);
            Authority gant = this.isRowGant(privilegeIdMapAuthTypes);
            for (Map.Entry<String, Authority> privilegeIdMapAuthType : privilegeIdMapAuthTypes.entrySet()) {
                String privilegeId = privilegeIdMapAuthType.getKey();
                Authority authority = privilegeIdMapAuthType.getValue();
                if (superior) {
                    this.authorizationService.revoke(privilegeId, ownerId, resourceId);
                    continue;
                }
                Authority saveGant = gant;
                if (isSaveEntityPrivilege) {
                    if (Authority.NONE.equals((Object)saveGant)) {
                        saveGant = Authority.UNKNOW;
                    }
                    if (Authority.NONE.equals((Object)authority)) {
                        authority = Authority.UNKNOW;
                    }
                    if (Authority.UNKNOW.equals((Object)authority) || Authority.REJECT.equals((Object)authority)) {
                        if (Authority.ALLOW.equals((Object)saveGant)) {
                            saveGant = Authority.UNKNOW;
                        }
                        if (authority.equals((Object)saveGant)) {
                            if (authority.equals((Object)Authority.UNKNOW)) {
                                this.authorizationService.revoke(privilegeId, ownerId, resourceId);
                                continue;
                            }
                            this.authorizationService.grant(privilegeId, ownerId, this.getOperator(), resourceId, privilegeType.intValue(), authority);
                            continue;
                        }
                        this.authorizationService.grant(privilegeId, ownerId, this.getOperator(), resourceId, privilegeType.intValue(), authority, AuthzType.ACCESS);
                        this.authorizationService.grant(privilegeId, ownerId, this.getOperator(), resourceId, privilegeType.intValue(), saveGant, AuthzType.DELEGATE);
                        continue;
                    }
                } else {
                    if (Authority.UNKNOW.equals((Object)saveGant)) {
                        saveGant = Authority.NONE;
                    }
                    if (Authority.UNKNOW.equals((Object)authority)) {
                        authority = Authority.NONE;
                    }
                    if (Authority.REJECT.equals((Object)authority) || Authority.NONE.equals((Object)authority)) {
                        if (Authority.ALLOW.equals((Object)saveGant)) {
                            saveGant = Authority.NONE;
                        }
                        if (authority.equals((Object)saveGant)) {
                            this.authorizationService.grant(privilegeId, ownerId, this.getOperator(), resourceId, privilegeType.intValue(), authority);
                            continue;
                        }
                        this.authorizationService.grant(privilegeId, ownerId, this.getOperator(), resourceId, privilegeType.intValue(), authority, AuthzType.ACCESS);
                        this.authorizationService.grant(privilegeId, ownerId, this.getOperator(), resourceId, privilegeType.intValue(), saveGant, AuthzType.DELEGATE);
                        continue;
                    }
                }
                if (!Authority.ALLOW.equals((Object)authority)) continue;
                if (authority.equals((Object)saveGant)) {
                    this.authorizationService.grant(privilegeId, ownerId, this.getOperator(), resourceId, privilegeType.intValue(), authority);
                    continue;
                }
                this.authorizationService.grant(privilegeId, ownerId, this.getOperator(), resourceId, privilegeType.intValue(), authority, AuthzType.ACCESS);
                this.authorizationService.grant(privilegeId, ownerId, this.getOperator(), resourceId, privilegeType.intValue(), saveGant, AuthzType.DELEGATE);
            }
        }
    }

    private String getOperator() {
        return NpContextHolder.getContext().getIdentityId();
    }

    private boolean isRowSuperior(Map<String, Authority> privilegeIdMapAuthTypes) {
        Authority authority = privilegeIdMapAuthTypes.get("22222222-1111-1111-1111-222222222222");
        privilegeIdMapAuthTypes.remove("22222222-1111-1111-1111-222222222222");
        return Authority.ALLOW.equals((Object)authority);
    }

    private Authority isRowGant(Map<String, Authority> privilegeIdMapAuthTypes) {
        Authority authority = privilegeIdMapAuthTypes.get("22222222-2222-2222-2222-222222222222");
        privilegeIdMapAuthTypes.remove("22222222-2222-2222-2222-222222222222");
        if (authority == null) {
            authority = Authority.UNKNOW;
        }
        return authority;
    }

    private static class ResLog {
        private boolean empty = true;
        private final Map<String, StringBuilder> resourceLog = new HashMap<String, StringBuilder>();

        private ResLog() {
        }

        public StringBuilder getLog(String resourceId) {
            StringBuilder logBuilder = this.resourceLog.get(resourceId);
            if (logBuilder == null) {
                logBuilder = new StringBuilder("\u8d44\u6e90\u3010").append(resourceId).append("\u3011\uff0c");
                this.resourceLog.put(resourceId, logBuilder);
                this.empty = false;
            }
            return logBuilder;
        }

        public boolean isEmpty() {
            return this.empty;
        }
    }
}

