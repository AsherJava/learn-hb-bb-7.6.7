/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.core.VisitorResult
 */
package com.jiuqi.nr.dataresource.service.impl;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.dataresource.DataResource;
import com.jiuqi.nr.dataresource.DataResourceDefine;
import com.jiuqi.nr.dataresource.DataResourceDefineGroup;
import com.jiuqi.nr.dataresource.DataResourceNode;
import com.jiuqi.nr.dataresource.DimAttribute;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.ResourceNode;
import com.jiuqi.nr.dataresource.authority.DataResourceAuthorityService;
import com.jiuqi.nr.dataresource.common.ResourceNodeIconGetter;
import com.jiuqi.nr.dataresource.dto.DataResourceNodeDTO;
import com.jiuqi.nr.dataresource.entity.ResourceTreeGroup;
import com.jiuqi.nr.dataresource.i18n.ResourceTreeI18Util;
import com.jiuqi.nr.dataresource.loader.ReverseDataResourceNodeVisitor;
import com.jiuqi.nr.dataresource.service.IDataResourceDefineGroupService;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.VisitorResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpecifiedGroupTree
implements ReverseDataResourceNodeVisitor<Void> {
    private final String specifiedKey;
    private IDataResourceDefineGroupService groupService;
    private DataResourceAuthorityService auth;
    private List<ITree<DataResourceNode>> values = Collections.emptyList();
    private SystemIdentityService systemIdentityService = (SystemIdentityService)SpringBeanUtils.getBean(SystemIdentityService.class);

    @Override
    public VisitorResult preVisitNode(ResourceNode<Void> ele) {
        return null;
    }

    @Override
    public Void visitRootNode(ResourceNode<Void> parent) {
        ResourceTreeGroup resourceTreeGroup = new ResourceTreeGroup();
        resourceTreeGroup.setKey("00000000-0000-0000-0000-000000000000");
        resourceTreeGroup.setTitle(ResourceTreeI18Util.getRootTitle());
        DataResourceNodeDTO node = new DataResourceNodeDTO(resourceTreeGroup);
        node.setCanWrite(this.systemIdentityService.isAdmin());
        ITree root = new ITree((INode)node);
        root.setIcons(ResourceNodeIconGetter.getIconByType(NodeType.TREE_GROUP));
        root.setExpanded(true);
        if (!this.values.isEmpty()) {
            root.setChildren(this.values);
        }
        this.values = new ArrayList<ITree<DataResourceNode>>();
        this.values.add((ITree<DataResourceNode>)root);
        return null;
    }

    @Override
    public <DR extends DataResource, DA extends DimAttribute, DF extends DataField> Void visitGroupNode(ResourceNode<Void> ele, List<DR> dataGroups, List<DA> dimAttributes, List<DF> dataFields) {
        return null;
    }

    private void determine(ResourceNode<Void> ele, ITree<DataResourceNode> groupNode) {
        if (groupNode.getKey().equals(this.specifiedKey)) {
            groupNode.setSelected(true);
            List<DataResourceDefineGroup> child = this.groupService.getByParentKey(groupNode.getKey());
            groupNode.setLeaf(child.isEmpty());
        } else if (ele.getKey().equals(groupNode.getKey())) {
            groupNode.setExpanded(true);
            if (!this.values.isEmpty()) {
                groupNode.setChildren(this.values);
            }
        } else {
            List<DataResourceDefineGroup> child = this.groupService.getByParentKey(groupNode.getKey());
            groupNode.setLeaf(child.isEmpty());
        }
    }

    @Override
    public <DD extends DataResourceDefine, DG extends DataResourceDefineGroup> Void visitGoDeNode(ResourceNode<Void> ele, List<DG> dataGroups, List<DD> dataDefines) {
        ArrayList<ITree<DataResourceNode>> children = new ArrayList<ITree<DataResourceNode>>();
        for (DataResourceDefineGroup dataGroup : dataGroups) {
            if (!this.auth.canRead(dataGroup.getKey(), NodeType.TREE_GROUP.getValue())) continue;
            DataResourceNodeDTO node = new DataResourceNodeDTO(dataGroup);
            node.setCanWrite(this.auth.canWrite(dataGroup.getKey(), NodeType.TREE_GROUP.getValue()));
            ITree iTreeNode = new ITree((INode)node);
            children.add((ITree<DataResourceNode>)iTreeNode);
            iTreeNode.setIcons(ResourceNodeIconGetter.getIconByType(node.getType()));
            this.determine(ele, (ITree<DataResourceNode>)iTreeNode);
        }
        this.values = children;
        return null;
    }

    public SpecifiedGroupTree(String specifiedKey) {
        this.specifiedKey = specifiedKey;
    }

    public void setResourceService(IDataResourceDefineGroupService groupService, DataResourceAuthorityService auth) {
        this.groupService = groupService;
        this.auth = auth;
    }

    public List<ITree<DataResourceNode>> getValues() {
        return this.values;
    }
}

