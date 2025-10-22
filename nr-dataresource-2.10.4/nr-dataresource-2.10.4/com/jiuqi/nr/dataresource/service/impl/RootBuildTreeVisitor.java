/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 */
package com.jiuqi.nr.dataresource.service.impl;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.dataresource.DataResourceDefine;
import com.jiuqi.nr.dataresource.DataResourceDefineGroup;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.ResourceNode;
import com.jiuqi.nr.dataresource.authority.DataResourceAuthorityService;
import com.jiuqi.nr.dataresource.common.ResourceNodeIconGetter;
import com.jiuqi.nr.dataresource.dto.DataResourceNodeDTO;
import com.jiuqi.nr.dataresource.service.IDataResourceDefineGroupService;
import com.jiuqi.nr.dataresource.service.IDataResourceService;
import com.jiuqi.nr.dataresource.service.impl.BuildTreeVisitor;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RootBuildTreeVisitor
extends BuildTreeVisitor {
    private boolean child;
    private IDataResourceDefineGroupService groupService;
    private SystemIdentityService systemIdentityService = (SystemIdentityService)SpringBeanUtils.getBean(SystemIdentityService.class);

    public RootBuildTreeVisitor(IDataResourceService resourceService, DataResourceAuthorityService auth, IDataResourceDefineGroupService groupService) {
        super(resourceService, auth);
        this.groupService = groupService;
    }

    public void setChild(boolean child) {
        this.child = child;
    }

    @Override
    public Void visitRootIsDefineNode(DataResourceDefine define) {
        return null;
    }

    @Override
    public Void visitRootIsGroupNode(DataResourceDefineGroup group) {
        if (!this.child && "00000000-0000-0000-0000-000000000000".equals(group.getKey())) {
            DataResourceNodeDTO dto = new DataResourceNodeDTO(group);
            dto.setCanWrite(this.systemIdentityService.isAdmin());
            this.root = new ITree((INode)dto);
            this.root.setIcons(ResourceNodeIconGetter.getIconByType(NodeType.TREE_GROUP));
            this.root.setExpanded(true);
            this.root.setSelected(true);
        }
        return null;
    }

    @Override
    public <DG extends DataResourceDefineGroup, DR extends DataResourceDefine> Map<String, Void> visitGroupNode(ResourceNode<Void> ele, List<DG> groups, List<DR> resourceDefines) {
        ArrayList<ITree> values = new ArrayList<ITree>();
        for (DataResourceDefineGroup dataGroup : groups) {
            if (!this.auth.canRead(dataGroup.getKey(), NodeType.TREE_GROUP.getValue())) continue;
            DataResourceNodeDTO nodeDTO = new DataResourceNodeDTO(dataGroup);
            nodeDTO.setCanWrite(this.auth.canWrite(dataGroup.getKey(), NodeType.TREE_GROUP.getValue()));
            ITree node = new ITree((INode)nodeDTO);
            node.setIcons(ResourceNodeIconGetter.getIconByType(nodeDTO.getType()));
            List<DataResourceDefineGroup> children = this.groupService.getByParentKey(node.getKey());
            node.setLeaf(children.isEmpty());
            values.add(node);
        }
        if (this.root != null) {
            this.root.setChildren(values);
            this.root.setLeaf(values.isEmpty());
            this.value.add(this.root);
        } else {
            this.value = values;
        }
        this.ok = true;
        return null;
    }
}

