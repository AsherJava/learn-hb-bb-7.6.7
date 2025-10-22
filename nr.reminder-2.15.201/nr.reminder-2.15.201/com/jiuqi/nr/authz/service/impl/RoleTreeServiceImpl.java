/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Role
 *  com.jiuqi.np.authz2.RoleGroup
 *  com.jiuqi.np.authz2.RoleGroupDTO
 *  com.jiuqi.np.authz2.Traceable
 *  com.jiuqi.np.authz2.service.RoleGroupService
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.resource.authority.RoleAuthority
 *  javax.validation.constraints.NotEmpty
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.nr.authz.service.impl;

import com.jiuqi.np.authz2.Role;
import com.jiuqi.np.authz2.RoleGroup;
import com.jiuqi.np.authz2.RoleGroupDTO;
import com.jiuqi.np.authz2.Traceable;
import com.jiuqi.np.authz2.service.RoleGroupService;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.nr.authz.bean.RoleQueryParam;
import com.jiuqi.nr.authz.bean.RoleTreeNode;
import com.jiuqi.nr.authz.service.IRoleTreeService;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.resource.authority.RoleAuthority;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class RoleTreeServiceImpl
implements IRoleTreeService {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleGroupService roleGroupService;
    @Autowired
    private RoleAuthority roleAuthorityService;
    public static final String KEY_ALL_ROLE = "00000000-0000-0000-0000-000000000000";
    public static final String TITLE_ALL_ROLE = "\u5168\u90e8\u89d2\u8272";

    @Override
    public List<RoleTreeNode> getRolesByIds(@NotNull @NotEmpty List<String> roleIds) {
        List roleByIds = this.roleService.getByIds(roleIds);
        return roleByIds.stream().filter(Objects::nonNull).filter(role -> this.roleAuthorityService.canReadRole(role)).map(RoleTreeNode::new).collect(Collectors.toList());
    }

    @Override
    public List<ITree<RoleTreeNode>> getRootNode(boolean getGroup) {
        ArrayList<ITree<RoleTreeNode>> nodes = new ArrayList<ITree<RoleTreeNode>>();
        RoleTreeNode node = new RoleTreeNode();
        node.setKey("ffffffff-ffff-ffff-ffff-ffffffffffff");
        node.setTitle(TITLE_ALL_ROLE);
        node.setGroupFlag(true);
        ITree root = new ITree((INode)node);
        root.setExpanded(true);
        root.setSelected(true);
        nodes.add(root);
        List roleGroups = this.roleGroupService.getChildren(null);
        if (roleGroups != null && !roleGroups.isEmpty()) {
            roleGroups.sort(Comparator.comparing(Traceable::getCreateTime, Comparator.nullsLast(Instant::compareTo)));
        }
        RoleGroupDTO roleGroupDTO = new RoleGroupDTO();
        roleGroupDTO.setId(KEY_ALL_ROLE);
        roleGroupDTO.setName("\u9ed8\u8ba4\u5206\u7ec4");
        roleGroupDTO.setTitle("\u9ed8\u8ba4\u5206\u7ec4");
        roleGroups.add(0, roleGroupDTO);
        for (RoleGroup roleGroup : roleGroups) {
            List childrenId;
            if (!this.roleAuthorityService.canReadRoleGroup(roleGroup)) continue;
            RoleTreeNode group = new RoleTreeNode(roleGroup);
            ITree child = new ITree((INode)group);
            if (getGroup && CollectionUtils.isEmpty(childrenId = this.roleGroupService.getChildrenId(roleGroup.getId()))) {
                child.setLeaf(true);
            }
            root.appendChild(child);
        }
        return nodes;
    }

    @Override
    public List<ITree<RoleTreeNode>> getChildNode(@NotNull RoleTreeNode parent, boolean isGroup) {
        if (parent.getKey() == null) {
            return this.getRootNode(false);
        }
        ArrayList<ITree<RoleTreeNode>> nodes = new ArrayList<ITree<RoleTreeNode>>();
        if (parent.getGroupFlag().booleanValue()) {
            List groupList = this.roleGroupService.getChildren(parent.getKey());
            groupList.sort(Comparator.comparing(Traceable::getCreateTime, Comparator.nullsLast(Instant::compareTo)));
            for (RoleGroup roleGroup : groupList) {
                List childrenId;
                if (!this.roleAuthorityService.canReadRoleGroup(roleGroup)) continue;
                RoleTreeNode group = new RoleTreeNode(roleGroup);
                ITree node = new ITree((INode)group);
                if (isGroup && CollectionUtils.isEmpty(childrenId = this.roleGroupService.getChildrenId(roleGroup.getId()))) {
                    node.setLeaf(true);
                }
                nodes.add((ITree<RoleTreeNode>)node);
            }
            if (!isGroup) {
                List roleList = null;
                roleList = KEY_ALL_ROLE.equals(parent.getKey()) ? this.roleService.getByGroup(null) : this.roleService.getByGroup(parent.getKey());
                roleList.sort(Comparator.comparing(Traceable::getCreateTime, Comparator.nullsLast(Instant::compareTo)));
                for (Role role : roleList) {
                    if (!this.roleAuthorityService.canReadRole(role)) continue;
                    RoleTreeNode roleImpl = new RoleTreeNode(role);
                    ITree node = new ITree((INode)roleImpl);
                    node.setLeaf(true);
                    nodes.add((ITree<RoleTreeNode>)node);
                }
            }
        }
        return nodes;
    }

    @Override
    public List<ITree<RoleTreeNode>> getSearchTree(@NotNull RoleTreeNode node, boolean getGroup) {
        List<ITree<RoleTreeNode>> resRootNode = this.getRootNode(getGroup);
        resRootNode.get(0).setSelected(false);
        List<String> nodePath = this.getGroupPath(node);
        List<ITree<RoleTreeNode>> rootNode = resRootNode.get(0).getChildren();
        block0: for (int i = 0; i < nodePath.size(); ++i) {
            String uuid = nodePath.get(i);
            for (ITree iTree : rootNode) {
                if (!iTree.getKey().equals(uuid)) continue;
                iTree.setExpanded(true);
                if (i == nodePath.size() - 1) {
                    iTree.setSelected(true);
                    iTree.setChecked(true);
                    if (!((RoleTreeNode)iTree.getData()).getGroupFlag().booleanValue()) continue block0;
                }
                List<ITree<RoleTreeNode>> childNode = this.getChildNode((RoleTreeNode)iTree.getData(), getGroup);
                iTree.setChildren(childNode);
                rootNode = childNode;
                continue block0;
            }
        }
        return resRootNode;
    }

    @Override
    public List<RoleTreeNode> searchRoleByFuzzyQuery(@NotNull RoleQueryParam roleQueryParam) {
        ArrayList<RoleTreeNode> list = new ArrayList<RoleTreeNode>();
        Boolean searchCol = roleQueryParam.getSearchCol();
        ArrayList<Role> roleListByTitle = new ArrayList<Role>(this.roleService.fuzzyQueryByTitle(roleQueryParam.getKeyword()));
        if (searchCol == null) {
            List queryByName = this.roleService.fuzzyQueryByName(roleQueryParam.getKeyword());
            roleListByTitle.removeAll(queryByName);
            roleListByTitle.addAll(queryByName);
        }
        roleListByTitle.sort(Comparator.comparing(Traceable::getCreateTime, Comparator.nullsLast(Instant::compareTo)));
        for (Role role : roleListByTitle) {
            Optional<RoleGroupDTO> roleGroupInfo;
            if (!this.roleAuthorityService.canReadRole(role)) continue;
            String groupId = role.getGroupId();
            if (StringUtils.hasLength(groupId)) {
                roleGroupInfo = this.roleGroupService.get(groupId);
            } else {
                RoleGroupDTO moRen = new RoleGroupDTO();
                moRen.setId(KEY_ALL_ROLE);
                moRen.setName("\u9ed8\u8ba4\u5206\u7ec4");
                moRen.setTitle("\u9ed8\u8ba4\u5206\u7ec4");
                roleGroupInfo = Optional.of(moRen);
            }
            RoleTreeNode serData = new RoleTreeNode(role);
            list.add(serData);
            if (!roleGroupInfo.isPresent()) continue;
            if (roleQueryParam.isGroup()) {
                RoleTreeNode parent = new RoleTreeNode((RoleGroup)roleGroupInfo.get());
                serData.setParent(parent);
                continue;
            }
            String title = ((RoleGroup)roleGroupInfo.get()).getTitle() + "/[" + role.getName() + "]" + role.getTitle();
            serData.setTitle(title);
        }
        return list;
    }

    private List<String> getGroupPath(RoleTreeNode impl) {
        ArrayList<String> nodePath = new ArrayList<String>();
        nodePath.add(impl.getKey());
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

