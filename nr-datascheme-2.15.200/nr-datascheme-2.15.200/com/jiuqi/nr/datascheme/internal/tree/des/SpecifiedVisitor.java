/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeNode
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.core.VisitorResult
 *  com.jiuqi.nr.datascheme.api.loader.des.ReverseSchemeNodeVisitor
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.datascheme.internal.tree.des;

import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.DataSchemeNode;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.core.VisitorResult;
import com.jiuqi.nr.datascheme.api.loader.des.ReverseSchemeNodeVisitor;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableRelDao;
import com.jiuqi.nr.datascheme.internal.entity.DataTableRelDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableRelDO;
import com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.internal.tree.DataTableNodeDTO;
import com.jiuqi.nr.datascheme.internal.tree.des.BaseBuildTree;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

@Deprecated
public class SpecifiedVisitor
extends BaseBuildTree
implements ReverseSchemeNodeVisitor<Void> {
    protected List<ITree<DataSchemeNode>> children = Collections.emptyList();
    protected final String specifiedKey;
    protected List<ITree<DataSchemeNode>> values;
    protected final NodeType nodeType;
    protected final boolean root;
    protected boolean ok;
    protected static final int UN_SHOW = DataFieldKind.PUBLIC_FIELD_DIM.getValue() | DataFieldKind.BUILT_IN_FIELD.getValue();
    private IEntityMetaService entityMetaService;
    protected IDataTableRelDao<DesignDataTableRelDO> iDataTableRelDao;
    protected IDataTableDao<DesignDataTableDO> iDataTableDao;
    protected IDataFieldDao<DesignDataFieldDO> dataFieldDao;

    public List<ITree<DataSchemeNode>> getValues() {
        return this.values;
    }

    public SpecifiedVisitor(String specifiedKey, boolean root, NodeType nodeType) {
        this.specifiedKey = specifiedKey;
        this.root = root;
        this.nodeType = nodeType;
    }

    public void setDataTableRelDao(IDataTableRelDao<DesignDataTableRelDO> iDataTableRelDao) {
        this.iDataTableRelDao = iDataTableRelDao;
    }

    public void setDataTableDao(IDataTableDao<DesignDataTableDO> iDataTableDao) {
        this.iDataTableDao = iDataTableDao;
    }

    public void setDataFieldDao(IDataFieldDao<DesignDataFieldDO> dataFieldDao) {
        this.dataFieldDao = dataFieldDao;
    }

    public <DG extends DesignDataGroup, DT extends DesignDataTable, DM extends DesignDataDimension> Void visitGoTaNode(SchemeNode<Void> ele, List<DG> dataGroups, List<DT> dataTables, List<DM> dims) {
        ArrayList<ITree<DataSchemeNode>> children = new ArrayList<ITree<DataSchemeNode>>();
        if (!this.root && !dims.isEmpty()) {
            super.dimNode(ele, dims, children);
        }
        for (DesignDataGroup dataGroup : dataGroups) {
            DataSchemeNodeDTO node0 = new DataSchemeNodeDTO(dataGroup);
            ITree groupNode = new ITree((INode)node0);
            children.add((ITree<DataSchemeNode>)groupNode);
            if (!this.root && dataGroup.getParentKey() == null) {
                this.ok = true;
            }
            int type = node0.getType();
            groupNode.setIcons(NodeIconGetter.getIconByType(NodeType.valueOf((int)type)));
            this.determine(ele, (ITree<DataSchemeNode>)groupNode);
        }
        if (this.nodeType.getValue() >= NodeType.FMDM_TABLE.getValue()) {
            DesignDataTable specifSubTable = null;
            for (DesignDataTable dataTable : dataTables) {
                if (null == dataTable.getDataTableType()) continue;
                if (dataTable.getDataTableType() == DataTableType.SUB_TABLE) {
                    if (!ele.getKey().equals(dataTable.getKey())) continue;
                    specifSubTable = dataTable;
                    continue;
                }
                DataTableNodeDTO node0 = new DataTableNodeDTO(dataTable);
                ITree tableNode = new ITree((INode)node0);
                tableNode.setLeaf((FIELD & this.nodeType.getValue()) == 0);
                if (tableNode.isLeaf() && dataTable.getDataTableType() == DataTableType.DETAIL && this.iDataTableRelDao != null) {
                    boolean empty = this.iDataTableRelDao.getByDesTable(dataTable.getKey()).isEmpty();
                    tableNode.setLeaf(tableNode.isLeaf() && empty);
                }
                int type = node0.getType();
                tableNode.setIcons(NodeIconGetter.getIconByType(NodeType.valueOf((int)type)));
                children.add((ITree<DataSchemeNode>)tableNode);
                if (!this.root && dataTable.getDataGroupKey() == null) {
                    this.ok = true;
                }
                this.determine(ele, (ITree<DataSchemeNode>)tableNode);
            }
            this.subTableSpec(children, specifSubTable);
        }
        if (this.ok) {
            this.values = children;
        } else {
            this.children = children;
        }
        return null;
    }

    private <DT extends DesignDataTable> void subTableSpec(List<ITree<DataSchemeNode>> children, DT specifSubTable) {
        boolean hasField;
        boolean bl = hasField = this.nodeType.getValue() >= NodeType.FIELD_ZB.getValue();
        if (specifSubTable != null && this.iDataTableRelDao != null) {
            DesignDataTableRelDO relDO = this.iDataTableRelDao.getBySrcTable(specifSubTable.getKey());
            if (relDO == null) {
                throw new RuntimeException("\u5b9a\u4f4d\u8282\u70b9\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570\uff1a" + specifSubTable);
            }
            String desTableKey = relDO.getDesTableKey();
            for (ITree<DataSchemeNode> child : children) {
                if (!child.getKey().equals(desTableKey)) continue;
                child.setExpanded(true);
                ArrayList<ITree<DataSchemeNode>> fc = new ArrayList<ITree<DataSchemeNode>>();
                List<DesignDataTableRelDO> rel = this.iDataTableRelDao.getByDesTable(child.getKey());
                if (!rel.isEmpty()) {
                    List<DesignDataTableDO> subTables = this.iDataTableDao.batchGet(rel.stream().map(DataTableRelDO::getSrcTableKey).collect(Collectors.toList()));
                    for (DesignDataTableDO subTable : subTables) {
                        DataTableNodeDTO node0 = new DataTableNodeDTO(subTable);
                        ITree subTableNode = new ITree((INode)node0);
                        if (subTable.getKey().equals(specifSubTable.getKey())) {
                            subTableNode.setSelected(true);
                            subTableNode.setLeaf(!hasField);
                            subTableNode.setIcons(NodeIconGetter.getIconByType(NodeType.DETAIL_TABLE));
                        }
                        fc.add((ITree<DataSchemeNode>)subTableNode);
                    }
                }
                if (hasField) {
                    List<DesignDataFieldDO> fields = this.dataFieldDao.getByTable(desTableKey);
                    this.buildFields(null, fields, fc);
                }
                child.setChildren(fc);
                child.setLeaf(fc.isEmpty());
                break;
            }
        }
    }

    public <DG extends DesignDataGroup, DS extends DesignDataScheme> Void visitGoScNode(SchemeNode<Void> ele, List<DG> dataGroups, List<DS> dataSchemes) {
        int type;
        DataSchemeNodeDTO node0;
        ArrayList<ITree<DataSchemeNode>> children = new ArrayList<ITree<DataSchemeNode>>();
        for (DesignDataGroup dataGroup : dataGroups) {
            if (!this.canRead((DataGroup)dataGroup)) continue;
            node0 = new DataSchemeNodeDTO(dataGroup);
            ITree groupNode = new ITree((INode)node0);
            type = node0.getType();
            groupNode.setIcons(NodeIconGetter.getIconByType(NodeType.valueOf((int)type)));
            this.determine(ele, (ITree<DataSchemeNode>)groupNode);
            children.add((ITree<DataSchemeNode>)groupNode);
        }
        if (NodeType.SCHEME.getValue() <= this.nodeType.getValue()) {
            for (DesignDataScheme dataScheme : dataSchemes) {
                if (!this.canRead((DataScheme)dataScheme)) continue;
                node0 = new DataSchemeNodeDTO(dataScheme);
                ITree schemeNode = new ITree((INode)node0);
                schemeNode.setLeaf(NodeType.SCHEME.getValue() == this.nodeType.getValue());
                type = node0.getType();
                schemeNode.setIcons(NodeIconGetter.getIconByType(NodeType.valueOf((int)type)));
                this.determine(ele, (ITree<DataSchemeNode>)schemeNode);
                children.add((ITree<DataSchemeNode>)schemeNode);
            }
        }
        this.children = children;
        return null;
    }

    public Void visitRootNode(SchemeNode<Void> parent) {
        ArrayList<ITree<DataSchemeNode>> values = new ArrayList<ITree<DataSchemeNode>>();
        DataSchemeNodeDTO dataSchemeNodeDTO = null;
        if ("00000000-0000-0000-0000-111111111111".equals(parent.getKey())) {
            dataSchemeNodeDTO = new DataSchemeNodeDTO();
            dataSchemeNodeDTO.setKey("00000000-0000-0000-0000-111111111111");
            dataSchemeNodeDTO.setType(NodeType.SCHEME_GROUP.getValue());
            dataSchemeNodeDTO.setCode("query_root");
            dataSchemeNodeDTO.setTitle("\u5168\u90e8\u67e5\u8be2\u6570\u636e\u65b9\u6848");
        } else {
            dataSchemeNodeDTO = new DataSchemeNodeDTO();
            dataSchemeNodeDTO.setKey("00000000-0000-0000-0000-000000000000");
            dataSchemeNodeDTO.setType(NodeType.SCHEME_GROUP.getValue());
            dataSchemeNodeDTO.setCode("root");
            dataSchemeNodeDTO.setTitle("\u5168\u90e8\u6570\u636e\u65b9\u6848");
        }
        ITree node = new ITree((INode)dataSchemeNodeDTO);
        int type = dataSchemeNodeDTO.getType();
        node.setIcons(NodeIconGetter.getIconByType(NodeType.valueOf((int)type)));
        node.setExpanded(true);
        if (this.children.isEmpty()) {
            node.setLeaf(true);
        } else {
            node.setChildren(this.children);
        }
        values.add(node);
        this.values = values;
        return null;
    }

    protected void determine(SchemeNode<Void> ele, ITree<DataSchemeNode> groupNode) {
        if (groupNode.getKey().equals(this.specifiedKey)) {
            groupNode.setSelected(true);
        } else if (ele != null && ele.getKey().equals(groupNode.getKey())) {
            groupNode.setExpanded(true);
            if (!this.children.isEmpty()) {
                groupNode.setChildren(this.children);
            }
        }
    }

    public VisitorResult preVisitNode(SchemeNode<Void> ele) {
        String key = ele.getKey();
        if (this.specifiedKey.equals(key)) {
            return null;
        }
        if (this.ok) {
            return VisitorResult.TERMINATE;
        }
        if (!this.root && (ele.getType() & NodeType.SCHEME.getValue()) != 0) {
            return VisitorResult.TERMINATE;
        }
        return super.deter(ele.getType(), this.nodeType);
    }

    public <DF extends DesignDataField> Void visitFieldNode(SchemeNode<Void> ele, List<DF> fields) {
        String dataTableKey;
        List<DesignDataTableRelDO> rel;
        ArrayList<ITree<DataSchemeNode>> children = new ArrayList<ITree<DataSchemeNode>>();
        if (this.iDataTableRelDao != null && !CollectionUtils.isEmpty(fields) && !CollectionUtils.isEmpty(rel = this.iDataTableRelDao.getByDesTable(dataTableKey = ((DesignDataField)fields.get(0)).getDataTableKey()))) {
            List<DesignDataTableDO> subTables = this.iDataTableDao.batchGet(rel.stream().map(DataTableRelDO::getSrcTableKey).collect(Collectors.toList()));
            for (DesignDataTableDO table : subTables) {
                DataTableNodeDTO node0 = new DataTableNodeDTO(table);
                node0.setParentKey(dataTableKey);
                ITree node = new ITree((INode)node0);
                node.setIcons(NodeIconGetter.getIconByType(node0.getType()));
                children.add((ITree<DataSchemeNode>)node);
            }
        }
        this.buildFields(ele, fields, children);
        this.children = children;
        return null;
    }

    private <DF extends DesignDataField> void buildFields(SchemeNode<Void> ele, List<DF> fields, List<ITree<DataSchemeNode>> children) {
        for (DesignDataField field : fields) {
            DataFieldKind dataFieldKind = field.getDataFieldKind();
            if (dataFieldKind == null || (dataFieldKind.getValue() & UN_SHOW) != 0) continue;
            DataSchemeNodeDTO node0 = new DataSchemeNodeDTO(field);
            ITree fieldNode = new ITree((INode)node0);
            fieldNode.setLeaf(true);
            this.determine(ele, (ITree<DataSchemeNode>)fieldNode);
            int type = node0.getType();
            fieldNode.setIcons(NodeIconGetter.getIconByType(NodeType.valueOf((int)type)));
            children.add((ITree<DataSchemeNode>)fieldNode);
        }
    }

    @Override
    protected IEntityDefine getEntityDefineByDimKey(String dimKey) {
        return this.entityMetaService.queryEntity(dimKey);
    }

    public void setEntityMetaService(IEntityMetaService entityMetaService) {
        this.entityMetaService = entityMetaService;
    }
}

