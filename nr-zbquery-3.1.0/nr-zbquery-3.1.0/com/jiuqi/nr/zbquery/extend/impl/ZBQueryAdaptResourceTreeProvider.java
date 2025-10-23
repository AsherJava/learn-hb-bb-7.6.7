/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataresource.DataResourceDefine
 *  com.jiuqi.nr.dataresource.DataResourceNode
 *  com.jiuqi.nr.dataresource.DimAttribute
 *  com.jiuqi.nr.dataresource.NodeType
 *  com.jiuqi.nr.dataresource.dto.DataResourceNodeDTO
 *  com.jiuqi.nr.dataresource.service.IDataLinkService
 *  com.jiuqi.nr.dataresource.service.IDataResourceDefineService
 *  com.jiuqi.nr.dataresource.service.IDataResourceTreeService
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.common.NodeIconGetter
 *  com.jiuqi.nr.period.common.utils.BqlTimeDimUtils
 */
package com.jiuqi.nr.zbquery.extend.impl;

import com.jiuqi.nr.dataresource.DataResourceDefine;
import com.jiuqi.nr.dataresource.DataResourceNode;
import com.jiuqi.nr.dataresource.DimAttribute;
import com.jiuqi.nr.dataresource.dto.DataResourceNodeDTO;
import com.jiuqi.nr.dataresource.service.IDataLinkService;
import com.jiuqi.nr.dataresource.service.IDataResourceDefineService;
import com.jiuqi.nr.dataresource.service.IDataResourceTreeService;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import com.jiuqi.nr.period.common.utils.BqlTimeDimUtils;
import com.jiuqi.nr.zbquery.bean.impl.ResourceTableAdaptNode;
import com.jiuqi.nr.zbquery.bean.impl.ResourceTreeAdaptNode;
import com.jiuqi.nr.zbquery.extend.IZBQueryAdaptTreeProvider;
import com.jiuqi.nr.zbquery.rest.param.AdaptTreeParam;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ZBQueryAdaptResourceTreeProvider
implements IZBQueryAdaptTreeProvider {
    private static final String TYPE = "resourcetree";
    @Autowired
    private IDataResourceTreeService<DataResourceNode> treeService;
    @Autowired
    private IDataResourceDefineService defineService;
    @Autowired
    private IDataLinkService linkService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;

    @Override
    public String getTreeType() {
        return TYPE;
    }

    @Override
    public List<ITree<ResourceTreeAdaptNode>> getRoots(AdaptTreeParam param) {
        ArrayList<ITree<ResourceTreeAdaptNode>> adaptTreeNodeList = new ArrayList<ITree<ResourceTreeAdaptNode>>();
        List treeGroups = this.treeService.getRootTree(com.jiuqi.nr.dataresource.NodeType.TREE_GROUP);
        List treeDefines = this.defineService.getByGroupKey("00000000-0000-0000-0000-000000000000");
        if (!CollectionUtils.isEmpty(treeGroups)) {
            ITree allTreeGroup = (ITree)treeGroups.get(0);
            adaptTreeNodeList.add(this.buildAdaptTreeNode((ITree<DataResourceNode>)allTreeGroup));
        }
        if (!CollectionUtils.isEmpty(treeDefines)) {
            treeDefines.forEach(define -> ((ITree)adaptTreeNodeList.get(0)).appendChild(this.buildAdaptTreeNodeFromDefine((DataResourceDefine)define)));
        }
        return adaptTreeNodeList;
    }

    @Override
    public List<ITree<ResourceTreeAdaptNode>> getChildren(AdaptTreeParam param) {
        ArrayList<ITree<ResourceTreeAdaptNode>> adaptChildNodeList = new ArrayList<ITree<ResourceTreeAdaptNode>>();
        if (param.getNodeType() == com.jiuqi.nr.dataresource.NodeType.TREE_GROUP.getValue()) {
            this.buildChildrenOfTreeGroup(param.getParentKey(), adaptChildNodeList);
        } else if (param.getNodeType() == com.jiuqi.nr.dataresource.NodeType.TREE.getValue()) {
            this.buildChildrenOfTree(param.getParentKey(), adaptChildNodeList);
        } else if (param.getNodeType() == com.jiuqi.nr.dataresource.NodeType.RESOURCE_GROUP.getValue()) {
            this.buildChildrenOfResourceGroup(param.getParentKey(), adaptChildNodeList);
        }
        return adaptChildNodeList;
    }

    @Override
    public List<ResourceTableAdaptNode> getFields(AdaptTreeParam param) {
        ArrayList<ResourceTableAdaptNode> fields = new ArrayList<ResourceTableAdaptNode>();
        if (param.getNodeType() == com.jiuqi.nr.dataresource.NodeType.DIM_GROUP.getValue()) {
            this.buildFieldsOfDimGroup(param.getParentCode(), param.getParentKey(), fields);
        } else if (param.getNodeType() == com.jiuqi.nr.dataresource.NodeType.RESOURCE_GROUP.getValue()) {
            this.buildFieldsOfResourceGroup(param.getParentCode(), param.getParentKey(), fields);
        }
        return fields;
    }

    private void buildChildrenOfTreeGroup(String treeGroupKey, List<ITree<ResourceTreeAdaptNode>> adaptChildNodeList) {
        List treeGroups = this.treeService.getGroupChildTree(com.jiuqi.nr.dataresource.NodeType.TREE_GROUP, this.buildQueryNodeParam(treeGroupKey, com.jiuqi.nr.dataresource.NodeType.TREE_GROUP));
        List treeDefines = this.defineService.getByGroupKey(treeGroupKey);
        if (!CollectionUtils.isEmpty(treeGroups)) {
            treeGroups.forEach(treeGroup -> adaptChildNodeList.add(this.buildAdaptTreeNode((ITree<DataResourceNode>)treeGroup)));
        }
        if (!CollectionUtils.isEmpty(treeDefines)) {
            treeDefines.forEach(treeDefine -> adaptChildNodeList.add(this.buildAdaptTreeNodeFromDefine((DataResourceDefine)treeDefine)));
        }
    }

    private void buildChildrenOfTree(String treeKey, List<ITree<ResourceTreeAdaptNode>> adaptChildNodeList) {
        List childrenTree = this.treeService.getChildTree(null, this.buildQueryNodeParam(treeKey, com.jiuqi.nr.dataresource.NodeType.TREE));
        List childrenNode = ((ITree)childrenTree.get(0)).getChildren();
        ConcurrentHashMap schemePeriodDimMap = new ConcurrentHashMap();
        if (!CollectionUtils.isEmpty(childrenNode)) {
            childrenNode.forEach(c -> {
                ITree<ResourceTreeAdaptNode> adaptTreeNode = this.buildAdaptTreeNode((ITree<DataResourceNode>)c);
                adaptChildNodeList.add(adaptTreeNode);
                DataResourceNode resourceNode = (DataResourceNode)c.getData();
                if (resourceNode.getDataSchemeKey() != null && !schemePeriodDimMap.containsKey(resourceNode.getDataSchemeKey())) {
                    List dimensions = this.dataSchemeService.getDataSchemeDimension(resourceNode.getDataSchemeKey(), DimensionType.PERIOD);
                    schemePeriodDimMap.put(resourceNode.getDataSchemeKey(), dimensions.get(0));
                }
                if (resourceNode.getType() == com.jiuqi.nr.dataresource.NodeType.DIM_GROUP.getValue()) {
                    adaptTreeNode.setLeaf(true);
                    List dimAttrs = this.linkService.getDimAttributeByGroup(c.getKey());
                    if (!CollectionUtils.isEmpty(dimAttrs)) {
                        String dimKey = ((DimAttribute)dimAttrs.get(0)).getDimKey();
                        if (dimKey.endsWith("@ORG")) {
                            ((ResourceTreeAdaptNode)adaptTreeNode.getData()).setCode(dimKey.split("@")[0]);
                        } else {
                            DataDimension periodDim = (DataDimension)schemePeriodDimMap.get(resourceNode.getDataSchemeKey());
                            if (periodDim.getDimKey().equals(resourceNode.getDimKey())) {
                                ((ResourceTreeAdaptNode)adaptTreeNode.getData()).setCode(BqlTimeDimUtils.getBqlTimeDimTable((String)dimKey));
                            } else {
                                ((ResourceTreeAdaptNode)adaptTreeNode.getData()).setCode(dimKey);
                            }
                        }
                    }
                }
            });
        }
    }

    private void buildChildrenOfResourceGroup(String resourceGroupKey, List<ITree<ResourceTreeAdaptNode>> adaptChildNodeList) {
        List childrenTree = this.treeService.getChildTree(null, this.buildQueryNodeParam(resourceGroupKey, com.jiuqi.nr.dataresource.NodeType.RESOURCE_GROUP));
        ConcurrentHashMap schemePeriodDimMap = new ConcurrentHashMap();
        if (!CollectionUtils.isEmpty(childrenTree)) {
            childrenTree.forEach(c -> {
                ITree<ResourceTreeAdaptNode> adaptTreeNode = this.buildAdaptTreeNode((ITree<DataResourceNode>)c);
                adaptChildNodeList.add(adaptTreeNode);
                DataResourceNode resourceNode = (DataResourceNode)c.getData();
                if (resourceNode.getDataSchemeKey() != null && !schemePeriodDimMap.containsKey(resourceNode.getDataSchemeKey())) {
                    List dimensions = this.dataSchemeService.getDataSchemeDimension(resourceNode.getDataSchemeKey(), DimensionType.PERIOD);
                    schemePeriodDimMap.put(resourceNode.getDataSchemeKey(), dimensions.get(0));
                }
                if (((DataResourceNode)c.getData()).getType() == com.jiuqi.nr.dataresource.NodeType.DIM_GROUP.getValue()) {
                    adaptTreeNode.setLeaf(true);
                    List dimAttrs = this.linkService.getDimAttributeByGroup(c.getKey());
                    if (!CollectionUtils.isEmpty(dimAttrs)) {
                        String dimKey = ((DimAttribute)dimAttrs.get(0)).getDimKey();
                        if (dimKey.endsWith("@ORG")) {
                            ((ResourceTreeAdaptNode)adaptTreeNode.getData()).setCode(dimKey.split("@")[0]);
                        } else {
                            DataDimension periodDim = (DataDimension)schemePeriodDimMap.get(resourceNode.getDataSchemeKey());
                            if (periodDim.getDimKey().equals(resourceNode.getDimKey())) {
                                ((ResourceTreeAdaptNode)adaptTreeNode.getData()).setCode(BqlTimeDimUtils.getBqlTimeDimTable((String)dimKey));
                            } else {
                                ((ResourceTreeAdaptNode)adaptTreeNode.getData()).setCode(dimKey);
                            }
                        }
                    }
                } else {
                    adaptTreeNode.setLeaf(c.isLeaf());
                    List dataFields = this.linkService.getByGroup(c.getKey());
                    if (!CollectionUtils.isEmpty(dataFields)) {
                        String tableKey = ((DataField)dataFields.get(0)).getDataTableKey();
                        DataTable dataTable = this.dataSchemeService.getDataTable(tableKey);
                        adaptTreeNode.setCode(dataTable.getCode());
                        ((ResourceTreeAdaptNode)adaptTreeNode.getData()).setCode(dataTable.getCode());
                    }
                }
            });
        }
    }

    private void buildFieldsOfDimGroup(String parentCode, String dimGroupKey, List<ResourceTableAdaptNode> fields) {
        List dimAttrs = this.linkService.getDimAttributeByGroup(dimGroupKey);
        if (!CollectionUtils.isEmpty(dimAttrs)) {
            dimAttrs.forEach(attr -> {
                ResourceTableAdaptNode adaptTableNode = new ResourceTableAdaptNode((DimAttribute)attr);
                adaptTableNode.setTableCode(parentCode);
                fields.add(adaptTableNode);
            });
        }
    }

    private void buildFieldsOfResourceGroup(String parentCode, String resourceGroupKey, List<ResourceTableAdaptNode> fields) {
        List dataFields = this.linkService.getByGroup(resourceGroupKey);
        if (!CollectionUtils.isEmpty(dataFields)) {
            dataFields.forEach(field -> {
                ResourceTableAdaptNode adaptTableNode = new ResourceTableAdaptNode((DataField)field);
                adaptTableNode.setTableCode(parentCode);
                fields.add(adaptTableNode);
            });
        }
    }

    private DataResourceNode buildQueryNodeParam(String parentKey, com.jiuqi.nr.dataresource.NodeType type) {
        DataResourceNodeDTO nodeDTO = new DataResourceNodeDTO();
        nodeDTO.setKey(parentKey);
        nodeDTO.setType(type.getValue());
        return nodeDTO;
    }

    private ITree<ResourceTreeAdaptNode> buildAdaptTreeNode(ITree<DataResourceNode> node) {
        ITree adaptTreeNode = new ITree((INode)new ResourceTreeAdaptNode((DataResourceNode)node.getData()));
        this.setNodeIcon((ITree<ResourceTreeAdaptNode>)adaptTreeNode);
        List children = node.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            children.forEach(c -> adaptTreeNode.appendChild(this.buildAdaptTreeNode((ITree<DataResourceNode>)c)));
        }
        return adaptTreeNode;
    }

    private ITree<ResourceTreeAdaptNode> buildAdaptTreeNodeFromDefine(DataResourceDefine define) {
        ITree adaptTreeNode = new ITree((INode)new ResourceTreeAdaptNode(define));
        this.setNodeIcon((ITree<ResourceTreeAdaptNode>)adaptTreeNode);
        return adaptTreeNode;
    }

    private void setNodeIcon(ITree<ResourceTreeAdaptNode> node) {
        int nodeType = ((ResourceTreeAdaptNode)node.getData()).getNodeType();
        if (nodeType == com.jiuqi.nr.dataresource.NodeType.TREE.getValue() || nodeType == com.jiuqi.nr.dataresource.NodeType.TREE_GROUP.getValue() || nodeType == com.jiuqi.nr.dataresource.NodeType.RESOURCE_GROUP.getValue()) {
            node.setIcons(NodeIconGetter.getIconByType((NodeType)NodeType.GROUP));
        } else if (nodeType == com.jiuqi.nr.dataresource.NodeType.DIM_GROUP.getValue() || nodeType == com.jiuqi.nr.dataresource.NodeType.TABLE_DIM_GROUP.getValue()) {
            node.setIcons(NodeIconGetter.getIconByType((NodeType)NodeType.DIM));
        }
    }
}

