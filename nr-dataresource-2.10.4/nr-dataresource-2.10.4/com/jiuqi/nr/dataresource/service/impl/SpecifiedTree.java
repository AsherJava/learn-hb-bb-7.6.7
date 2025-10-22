/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.core.VisitorResult
 */
package com.jiuqi.nr.dataresource.service.impl;

import com.jiuqi.nr.dataresource.DataResource;
import com.jiuqi.nr.dataresource.DataResourceDefine;
import com.jiuqi.nr.dataresource.DataResourceDefineGroup;
import com.jiuqi.nr.dataresource.DataResourceKind;
import com.jiuqi.nr.dataresource.DataResourceNode;
import com.jiuqi.nr.dataresource.DimAttribute;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.ResourceNode;
import com.jiuqi.nr.dataresource.authority.DataResourceAuthorityService;
import com.jiuqi.nr.dataresource.common.ResourceNodeIconGetter;
import com.jiuqi.nr.dataresource.dto.DataResourceNodeDTO;
import com.jiuqi.nr.dataresource.loader.ReverseDataResourceNodeVisitor;
import com.jiuqi.nr.dataresource.service.IDataResourceService;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.VisitorResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SpecifiedTree
implements ReverseDataResourceNodeVisitor<Void> {
    private final String specifiedKey;
    private List<ITree<DataResourceNode>> values = Collections.emptyList();
    protected IDataResourceService resourceService;
    protected DataResourceAuthorityService auth;

    @Override
    public VisitorResult preVisitNode(ResourceNode<Void> ele) {
        if (ele.getType() == NodeType.TREE.getValue()) {
            return VisitorResult.TERMINATE;
        }
        return null;
    }

    @Override
    public Void visitRootNode(ResourceNode<Void> parent) {
        return null;
    }

    @Override
    public <DR extends DataResource, DA extends DimAttribute, DF extends DataField> Void visitGroupNode(ResourceNode<Void> ele, List<DR> dataGroups, List<DA> dimAttributes, List<DF> dataFields) {
        ArrayList<ITree<DataResourceNode>> children = new ArrayList<ITree<DataResourceNode>>();
        for (DataResource dataGroup : dataGroups) {
            if ((dataGroup.getResourceKind().getValue() == DataResourceKind.RESOURCE_GROUP.getValue() || dataGroup.getResourceKind().getValue() == DataResourceKind.MD_INFO.getValue()) && !this.auth.canRead(dataGroup.getKey(), NodeType.RESOURCE_GROUP.getValue())) continue;
            DataResourceNodeDTO node = new DataResourceNodeDTO(dataGroup);
            if (dataGroup.getResourceKind().getValue() == DataResourceKind.MD_INFO.getValue()) {
                node.setCanWrite(false);
            } else {
                node.setCanWrite(this.auth.canWrite(dataGroup.getResourceDefineKey(), NodeType.TREE.getValue()));
            }
            ITree iTreeNode = new ITree((INode)node);
            iTreeNode.setIcons(ResourceNodeIconGetter.getIconByType(node.getType()));
            children.add((ITree<DataResourceNode>)iTreeNode);
            this.determine(ele, (ITree<DataResourceNode>)iTreeNode);
        }
        this.values = children;
        this.values.sort(Comparator.comparing(ITree::getData));
        return null;
    }

    private void determine(ResourceNode<Void> ele, ITree<DataResourceNode> groupNode) {
        if (groupNode.getKey().equals(this.specifiedKey)) {
            groupNode.setSelected(true);
            List<DataResource> child = this.resourceService.getByParentKey(groupNode.getKey());
            groupNode.setLeaf(child.isEmpty());
        } else if (ele.getKey().equals(groupNode.getKey())) {
            groupNode.setExpanded(true);
            if (!this.values.isEmpty()) {
                groupNode.setChildren(this.values);
            }
        } else {
            List<DataResource> child = this.resourceService.getByParentKey(groupNode.getKey());
            groupNode.setLeaf(child.isEmpty());
        }
    }

    @Override
    public <DD extends DataResourceDefine, DG extends DataResourceDefineGroup> Void visitGoDeNode(ResourceNode<Void> ele, List<DG> dataGroups, List<DD> dataDefines) {
        return null;
    }

    public SpecifiedTree(String specifiedKey) {
        this.specifiedKey = specifiedKey;
    }

    public void setResourceService(IDataResourceService resourceService, DataResourceAuthorityService auth) {
        this.resourceService = resourceService;
        this.auth = auth;
    }

    public List<ITree<DataResourceNode>> getValues() {
        return this.values;
    }
}

