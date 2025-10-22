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
import com.jiuqi.nr.dataresource.loader.ResourceNodeVisitor;
import com.jiuqi.nr.dataresource.service.IDataResourceService;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.VisitorResult;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class BuildTreeVisitor
implements ResourceNodeVisitor<Void> {
    protected List<ITree<DataResourceNode>> value = new ArrayList<ITree<DataResourceNode>>();
    protected ITree<DataResourceNode> root;
    protected boolean ok;
    protected final IDataResourceService resourceService;
    protected final DataResourceAuthorityService auth;
    protected static final int DIM_G = NodeType.TABLE_DIM_GROUP.getValue() | NodeType.DIM_GROUP.getValue();

    public BuildTreeVisitor(IDataResourceService resourceService, DataResourceAuthorityService auth) {
        this.resourceService = resourceService;
        this.auth = auth;
    }

    @Override
    public VisitorResult preVisitNode(ResourceNode<Void> ele) {
        if (this.ok) {
            return VisitorResult.TERMINATE;
        }
        return null;
    }

    @Override
    public Void visitRootIsDefineNode(DataResourceDefine define) {
        DataResourceNodeDTO nodeDTO = new DataResourceNodeDTO(define);
        nodeDTO.setCanWrite(this.auth.canWrite(define.getKey(), NodeType.TREE.getValue()));
        this.root = new ITree((INode)nodeDTO);
        this.root.setIcons(ResourceNodeIconGetter.getIconByType(nodeDTO.getType()));
        this.root.setExpanded(true);
        this.root.setSelected(true);
        return null;
    }

    @Override
    public Void visitRootIsGroupNode(DataResourceDefineGroup group) {
        return null;
    }

    @Override
    public <DG extends DataResource> Map<String, Void> visitResourceDefineNode(ResourceNode<Void> ele, List<DG> dataGroups) {
        List<ITree<DataResourceNode>> values = this.buildGroup(dataGroups, null);
        this.root.setChildren(values);
        this.value.add(this.root);
        this.ok = true;
        return null;
    }

    @Override
    public <DG extends DataResource, DA extends DimAttribute, DF extends DataField> Map<String, Void> visitResourceNode(ResourceNode<Void> ele, List<DG> dataGroups, List<DA> dimAttributes, List<DF> dataFields) {
        if ((ele.getType() & DIM_G) != 0) {
            List<DataResource> dimGroups = this.resourceService.getByParentKey(ele.getKey());
            this.value = this.buildGroup(dimGroups, true);
        } else {
            this.value = this.buildGroup(dataGroups, null);
        }
        this.ok = true;
        return null;
    }

    private <DG extends DataResource> List<ITree<DataResourceNode>> buildGroup(List<DG> dataGroups, Boolean leaf) {
        ArrayList<ITree<DataResourceNode>> values = new ArrayList<ITree<DataResourceNode>>();
        for (DataResource dataGroup : dataGroups) {
            if ((dataGroup.getResourceKind().getValue() == DataResourceKind.RESOURCE_GROUP.getValue() || dataGroup.getResourceKind().getValue() == DataResourceKind.MD_INFO.getValue()) && !this.auth.canRead(dataGroup.getKey(), NodeType.RESOURCE_GROUP.getValue())) continue;
            DataResourceNodeDTO nodeDTO = new DataResourceNodeDTO(dataGroup);
            if (dataGroup.getResourceKind().getValue() == DataResourceKind.MD_INFO.getValue()) {
                nodeDTO.setCanWrite(false);
            } else {
                nodeDTO.setCanWrite(this.auth.canWrite(dataGroup.getResourceDefineKey(), NodeType.TREE.getValue()));
            }
            ITree tree = new ITree((INode)nodeDTO);
            tree.setIcons(ResourceNodeIconGetter.getIconByType(nodeDTO.getType()));
            if (leaf == null) {
                List<DataResource> children = this.resourceService.getByParentKey(dataGroup.getKey());
                tree.setLeaf(children.isEmpty());
            } else {
                tree.setLeaf(leaf.booleanValue());
            }
            values.add((ITree<DataResourceNode>)tree);
        }
        values.sort(Comparator.comparing(ITree::getData));
        return values;
    }

    @Override
    public <DG extends DataResourceDefineGroup, DR extends DataResourceDefine> Map<String, Void> visitGroupNode(ResourceNode<Void> ele, List<DG> groups, List<DR> resourceDefines) {
        return null;
    }

    public List<ITree<DataResourceNode>> getValue() {
        return this.value;
    }

    public void setValue(List<ITree<DataResourceNode>> value) {
        this.value = value;
    }
}

