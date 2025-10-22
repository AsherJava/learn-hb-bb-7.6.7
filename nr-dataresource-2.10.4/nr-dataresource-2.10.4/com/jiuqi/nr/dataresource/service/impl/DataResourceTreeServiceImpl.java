/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 */
package com.jiuqi.nr.dataresource.service.impl;

import com.jiuqi.nr.dataresource.DataResource;
import com.jiuqi.nr.dataresource.DataResourceDefine;
import com.jiuqi.nr.dataresource.DataResourceKind;
import com.jiuqi.nr.dataresource.DataResourceNode;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.ResourceNode;
import com.jiuqi.nr.dataresource.TreeSearchQuery;
import com.jiuqi.nr.dataresource.authority.DataResourceAuthorityService;
import com.jiuqi.nr.dataresource.common.ResourceNodeIconGetter;
import com.jiuqi.nr.dataresource.dto.DataResourceNodeDTO;
import com.jiuqi.nr.dataresource.loader.DataResourceLevelLoader;
import com.jiuqi.nr.dataresource.service.IDataResourceDefineGroupService;
import com.jiuqi.nr.dataresource.service.IDataResourceDefineService;
import com.jiuqi.nr.dataresource.service.IDataResourceService;
import com.jiuqi.nr.dataresource.service.IDataResourceTreeService;
import com.jiuqi.nr.dataresource.service.impl.BuildTreeVisitor;
import com.jiuqi.nr.dataresource.service.impl.RootBuildTreeVisitor;
import com.jiuqi.nr.dataresource.service.impl.SpecifiedGroupTree;
import com.jiuqi.nr.dataresource.service.impl.SpecifiedTree;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class DataResourceTreeServiceImpl
implements IDataResourceTreeService<DataResourceNode> {
    @Autowired
    private IDataResourceDefineService defineService;
    @Autowired
    private IDataResourceService resourceService;
    @Autowired
    private DataResourceLevelLoader levelLoader;
    @Autowired
    private DataResourceAuthorityService auth;
    @Autowired
    private IDataResourceDefineGroupService groupService;

    @Override
    public List<ITree<DataResourceNode>> getRootTree(NodeType leaf, String defineKey) {
        Assert.notNull((Object)defineKey, "defineKey must not be null");
        BuildTreeVisitor visitor = new BuildTreeVisitor(this.resourceService, this.auth);
        this.levelLoader.walkDataResourceTree(new ResourceNode(defineKey, NodeType.TREE.getValue()), visitor);
        return visitor.getValue();
    }

    @Override
    public List<ITree<DataResourceNode>> getChildTree(NodeType leaf, DataResourceNode parent) {
        Assert.notNull((Object)parent, "parent must not be null");
        int type = parent.getType();
        if (NodeType.DIM_FMDM_GROUP.getValue() == type) {
            return Collections.emptyList();
        }
        BuildTreeVisitor visitor = new BuildTreeVisitor(this.resourceService, this.auth);
        this.levelLoader.walkDataResourceTree(new ResourceNode(parent.getKey(), type), visitor);
        return visitor.getValue();
    }

    @Override
    public List<ITree<DataResourceNode>> getSpecifiedTree(NodeType leaf, DataResourceNode node, String defineKey) {
        if (node == null || node.getKey() == null) {
            return this.getRootTree(leaf, defineKey);
        }
        Assert.notNull((Object)defineKey, "defineKey must not be null");
        int type = node.getType();
        if (NodeType.TREE.getValue() == type) {
            return this.getRootTree(leaf, defineKey);
        }
        if (((NodeType.RESOURCE_GROUP.getValue() | NodeType.MD_INFO.getValue() | NodeType.DIM_GROUP.getValue() | NodeType.TABLE_DIM_GROUP.getValue()) & type) == 0) {
            throw new IllegalArgumentException("\u4e0d\u652f\u6301\u8282\u70b9\u5b9a\u4f4d");
        }
        SpecifiedTree specifiedTree = new SpecifiedTree(node.getKey());
        specifiedTree.setResourceService(this.resourceService, this.auth);
        this.levelLoader.reverseDataResourceTree(new ResourceNode(node.getKey(), node.getType()), specifiedTree);
        ArrayList<ITree<DataResourceNode>> values = new ArrayList<ITree<DataResourceNode>>();
        DataResourceDefine dataResourceDefine = this.defineService.get(defineKey);
        DataResourceNodeDTO nodeDTO = new DataResourceNodeDTO(dataResourceDefine);
        nodeDTO.setCanWrite(this.auth.canWrite(defineKey, NodeType.TREE.getValue()));
        ITree root = new ITree((INode)nodeDTO);
        root.setExpanded(true);
        root.setIcons(ResourceNodeIconGetter.getIconByType(NodeType.TREE));
        values.add(root);
        root.setChildren(specifiedTree.getValues());
        return values;
    }

    @Override
    public List<DataResourceNode> search(TreeSearchQuery searchQuery) {
        DataResourceDefine dataResourceDefine;
        Assert.notNull((Object)searchQuery, "searchQuery must not be null");
        int searchType = searchQuery.getSearchType();
        String keyword = searchQuery.getKeyword();
        Assert.notNull((Object)keyword, "keyword must not be null");
        String defineKey = searchQuery.getDefineKey();
        Assert.notNull((Object)defineKey, "defineKey must not be null");
        ArrayList<DataResourceNode> search = new ArrayList<DataResourceNode>();
        if ((NodeType.TREE.getValue() & searchType) != 0 && (dataResourceDefine = this.defineService.get(defineKey)).getTitle().contains(keyword) && this.auth.canRead(dataResourceDefine.getKey(), NodeType.TREE.getValue())) {
            search.add(new DataResourceNodeDTO(dataResourceDefine));
        }
        if (((NodeType.RESOURCE_GROUP.getValue() | NodeType.DIM_GROUP.getValue() | NodeType.TABLE_DIM_GROUP.getValue()) & searchType) != 0) {
            List<DataResource> dataResources = this.resourceService.searchBy(defineKey, keyword);
            for (DataResource dataResource : dataResources) {
                if (dataResource.getResourceKind() == DataResourceKind.RESOURCE_GROUP) {
                    if (!this.auth.canRead(dataResource.getKey(), NodeType.RESOURCE_GROUP.getValue())) continue;
                    search.add(new DataResourceNodeDTO(dataResource));
                    continue;
                }
                search.add(new DataResourceNodeDTO(dataResource));
            }
        }
        return search;
    }

    @Override
    public List<ITree<DataResourceNode>> getRootTree(NodeType leafType) {
        RootBuildTreeVisitor visitor = new RootBuildTreeVisitor(this.resourceService, this.auth, this.groupService);
        this.levelLoader.walkDataResourceTree(new ResourceNode("00000000-0000-0000-0000-000000000000", NodeType.TREE_GROUP.getValue()), visitor);
        return visitor.getValue();
    }

    @Override
    public List<ITree<DataResourceNode>> getGroupChildTree(NodeType leafType, DataResourceNode parent) {
        Assert.notNull((Object)parent, "parent must not be null");
        RootBuildTreeVisitor visitor = new RootBuildTreeVisitor(this.resourceService, this.auth, this.groupService);
        visitor.setChild(true);
        this.levelLoader.walkDataResourceTree(new ResourceNode(parent.getKey(), parent.getType()), visitor);
        return visitor.getValue();
    }

    @Override
    public List<ITree<DataResourceNode>> getGroupSpecifiedTree(NodeType leaf, DataResourceNode node) {
        if (node == null || "00000000-0000-0000-0000-000000000000".equals(node.getKey())) {
            return this.getRootTree(leaf);
        }
        int type = node.getType();
        if ((NodeType.TREE_GROUP.getValue() & type) == 0) {
            throw new IllegalArgumentException("\u4e0d\u652f\u6301\u8282\u70b9\u5b9a\u4f4d");
        }
        SpecifiedGroupTree specifiedGroupTree = new SpecifiedGroupTree(node.getKey());
        specifiedGroupTree.setResourceService(this.groupService, this.auth);
        this.levelLoader.reverseDataResourceTree(new ResourceNode(node.getKey(), node.getType()), specifiedGroupTree);
        return specifiedGroupTree.getValues();
    }
}

