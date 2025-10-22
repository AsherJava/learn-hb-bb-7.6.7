/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Role
 *  com.jiuqi.np.authz2.RoleGroup
 *  com.jiuqi.np.authz2.Traceable
 *  com.jiuqi.np.authz2.service.RoleGroupService
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.authz2.vo.RoleGroupV
 *  com.jiuqi.np.authz2.vo.RoleV
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.resource.authority.RoleAuthority
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.authz.service.impl;

import com.jiuqi.np.authz2.Role;
import com.jiuqi.np.authz2.RoleGroup;
import com.jiuqi.np.authz2.Traceable;
import com.jiuqi.np.authz2.service.RoleGroupService;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.authz2.vo.RoleGroupV;
import com.jiuqi.np.authz2.vo.RoleV;
import com.jiuqi.nr.authz.bean.RoleWebImpl;
import com.jiuqi.nr.authz.service.IRoleMgrService;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.resource.authority.RoleAuthority;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class RoleMgrServiceImpl
implements IRoleMgrService {
    @Resource
    private RoleGroupService roleGroupService;
    @Resource
    private RoleService roleService;
    @Resource
    private RoleAuthority roleAuthority;
    public static final String TITLE_UNKNOWNGROUP = "\u672a\u5206\u7ec4";
    public static final String NAME_UNKNOWNGROUP = "NO_GROUP";
    public static final String KEY_UNKNOWNGROUP = "ffffffff-0000-0000-0000-000000000000";
    public static final String KEY_ALLROLES = "00000000-0000-0000-0000-000000000000";
    public static final String TITLE_ALLROLES = "\u5168\u90e8\u89d2\u8272";

    @Override
    public List<RoleWebImpl> getChildren(RoleWebImpl roleImpl) {
        ArrayList<RoleWebImpl> list = new ArrayList<RoleWebImpl>();
        if (roleImpl.getGroupFlag().booleanValue()) {
            list.addAll(this.roleGroupService.getChildren(roleImpl.getParentId()).stream().filter(roleGroup -> this.roleAuthority.canReadRoleGroup(roleGroup.getId())).map(RoleWebImpl::new).collect(Collectors.toList()));
        } else {
            list.addAll(this.roleService.getByGroup(roleImpl.getParentId()).stream().filter(roleGroup -> this.roleAuthority.canReadRole(roleGroup.getId())).map(RoleWebImpl::new).collect(Collectors.toList()));
        }
        return list;
    }

    @Override
    public RoleWebImpl getRoleDetail(RoleWebImpl roleImpl) {
        if (roleImpl.getGroupFlag().booleanValue()) {
            Optional optional = this.roleGroupService.get(roleImpl.getId());
            if (optional.isPresent()) {
                return new RoleWebImpl((RoleGroup)optional.get());
            }
        } else {
            Optional optional = this.roleService.get(roleImpl.getId());
            if (optional.isPresent()) {
                return new RoleWebImpl((Role)optional.get());
            }
        }
        return null;
    }

    @Override
    public int getVisibleCount() {
        return this.getAllRoles().size();
    }

    @Override
    public List<RoleWebImpl> searchRoleByFuzzyQuery(RoleWebImpl impl) {
        ArrayList<RoleWebImpl> list = new ArrayList<RoleWebImpl>();
        ArrayList roleListByName = new ArrayList(this.roleService.fuzzyQueryByName(impl.getTitle()));
        List roleListByTitle = this.roleService.fuzzyQueryByTitle(impl.getTitle());
        roleListByName.removeAll(roleListByTitle);
        roleListByName.addAll(roleListByTitle);
        for (Role role : roleListByName) {
            if (!this.roleAuthority.canReadRole(role.getId())) continue;
            Optional roleGroupInfo = this.roleGroupService.get(role.getGroupId());
            RoleWebImpl serData = new RoleWebImpl(role);
            String title = null;
            if (roleGroupInfo.isPresent()) {
                title = ((RoleGroup)roleGroupInfo.get()).getTitle() + "/[" + role.getName() + "]" + role.getTitle();
            }
            serData.setTitle(title == null ? role.getTitle() : title);
            list.add(serData);
        }
        return list;
    }

    @Override
    public List<Role> getAllRoles() {
        return this.roleService.getAllRoles().stream().filter(role -> this.roleAuthority.canReadRole(role.getId())).collect(Collectors.toList());
    }

    @Override
    public List<Role> getRolesByIds(List<String> roleIds) {
        return this.roleService.getByIds(roleIds).stream().filter(role -> this.roleAuthority.canReadRole(role.getId())).collect(Collectors.toList());
    }

    @Override
    public List<ITree<RoleWebImpl>> getRootNode() {
        ArrayList<ITree<RoleWebImpl>> nodes = new ArrayList<ITree<RoleWebImpl>>();
        RoleWebImpl node = new RoleWebImpl();
        node.setKey(KEY_ALLROLES);
        node.setTitle(TITLE_ALLROLES);
        ITree root = new ITree((INode)node);
        root.setExpanded(true);
        nodes.add(root);
        RoleGroupV unKnownGroup = null;
        List roleGroups = this.roleGroupService.getChildren(null);
        if (roleGroups != null && !roleGroups.isEmpty()) {
            roleGroups.sort(Comparator.comparing(Traceable::getCreateTime, Comparator.nullsLast(Instant::compareTo)));
            for (RoleGroup roleGroup : roleGroups) {
                if (!this.roleAuthority.canReadRoleGroup(roleGroup.getId())) continue;
                RoleWebImpl group = new RoleWebImpl(roleGroup);
                ITree child = new ITree((INode)group);
                child.setExpanded(false);
                child.setLeaf(false);
                if (roleGroup.getTitle().equals(TITLE_UNKNOWNGROUP)) {
                    unKnownGroup = new RoleGroupV();
                    unKnownGroup.setId(roleGroup.getId());
                    unKnownGroup.setTitle(roleGroup.getTitle());
                    root.appendChild(0, child);
                    continue;
                }
                root.appendChild(child);
            }
        } else {
            Optional roleGroup = this.roleGroupService.get(KEY_UNKNOWNGROUP);
            unKnownGroup = new RoleGroupV();
            if (!roleGroup.isPresent()) {
                unKnownGroup.setTitle(TITLE_UNKNOWNGROUP);
                unKnownGroup.setName(NAME_UNKNOWNGROUP);
                unKnownGroup.setId(KEY_UNKNOWNGROUP);
                this.roleGroupService.create(unKnownGroup);
            } else {
                BeanUtils.copyProperties(roleGroup.get(), unKnownGroup);
            }
            RoleWebImpl unKnownNode = new RoleWebImpl((RoleGroup)unKnownGroup);
            ITree unKnownChild = new ITree((INode)unKnownNode);
            root.appendChild(unKnownChild);
        }
        if (unKnownGroup == null) {
            return nodes;
        }
        List roles = this.roleService.getByGroup(null);
        if (roles != null && !roles.isEmpty()) {
            for (Role role : roles) {
                if ("ffffffff-ffff-ffff-bbbb-ffffffffffff".equals(role.getId()) || "ffffffff-ffff-ffff-aaaa-ffffffffffff".equals(role.getId())) continue;
                RoleV roleV = new RoleV();
                BeanUtils.copyProperties(role, roleV);
                roleV.setGroupId(unKnownGroup.getId());
                roleV.setCreateTime(Instant.MIN);
                this.roleService.modify(roleV);
            }
        }
        return nodes;
    }

    @Override
    public List<ITree<RoleWebImpl>> getChildNode(RoleWebImpl impl) {
        if (impl.getId() == null) {
            return this.getRootNode();
        }
        ArrayList<ITree<RoleWebImpl>> nodes = new ArrayList<ITree<RoleWebImpl>>();
        if (impl.getGroupFlag().booleanValue()) {
            List groupList = this.roleGroupService.getChildren(impl.getId());
            groupList.sort(Comparator.comparing(Traceable::getCreateTime, Comparator.nullsLast(Instant::compareTo)));
            for (RoleGroup roleGroup : groupList) {
                if (!this.roleAuthority.canReadRoleGroup(roleGroup.getId())) continue;
                RoleWebImpl group = new RoleWebImpl(roleGroup);
                ITree node = new ITree((INode)group);
                node.setExpanded(false);
                node.setLeaf(false);
                nodes.add((ITree<RoleWebImpl>)node);
            }
            List roleList = this.roleService.getByGroup(impl.getId());
            roleList.sort(Comparator.comparing(Traceable::getCreateTime, Comparator.nullsLast(Instant::compareTo)));
            for (Role role : roleList) {
                if (!this.roleAuthority.canReadRole(role.getId())) continue;
                RoleWebImpl roleImpl = new RoleWebImpl(role);
                ITree node = new ITree((INode)roleImpl);
                node.setLeaf(true);
                nodes.add((ITree<RoleWebImpl>)node);
            }
        }
        return nodes;
    }

    @Override
    public List<ITree<RoleWebImpl>> getSearchTree(RoleWebImpl impl) {
        List<ITree<RoleWebImpl>> resRootNode = this.getRootNode();
        List<String> nodePath = this.getGroupPath(impl);
        List<ITree<RoleWebImpl>> rootNode = resRootNode.get(0).getChildren();
        block0: for (int i = 0; i < nodePath.size(); ++i) {
            String uuid = nodePath.get(i);
            for (ITree iTree : rootNode) {
                if (!iTree.getKey().equals(uuid)) continue;
                iTree.setExpanded(true);
                if (i == nodePath.size() - 1) {
                    iTree.setSelected(true);
                    if (!((RoleWebImpl)iTree.getData()).getGroupFlag().booleanValue()) continue block0;
                }
                List<ITree<RoleWebImpl>> childNode = this.getChildNode((RoleWebImpl)iTree.getData());
                iTree.setChildren(childNode);
                rootNode = childNode;
                continue block0;
            }
        }
        return resRootNode;
    }

    public List<String> getGroupPath(RoleWebImpl impl) {
        ArrayList<String> nodePath = new ArrayList<String>();
        nodePath.add(impl.getId());
        if (impl.getParentId() == null) {
            return nodePath;
        }
        Optional roleGroupInfo = this.roleGroupService.get(impl.getParentId());
        while (roleGroupInfo.isPresent()) {
            nodePath.add(((RoleGroup)roleGroupInfo.get()).getId());
            if (((RoleGroup)roleGroupInfo.get()).getParentId() == null) break;
            roleGroupInfo = this.roleGroupService.get(((RoleGroup)roleGroupInfo.get()).getParentId());
        }
        Collections.reverse(nodePath);
        return nodePath;
    }
}

