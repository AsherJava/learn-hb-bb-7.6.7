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
 *  com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
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
import com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableRelDao;
import com.jiuqi.nr.datascheme.internal.entity.DataTableRelDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableRelDO;
import com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.internal.tree.DataTableNodeDTO;
import com.jiuqi.nr.datascheme.internal.tree.des.BaseBuildTree;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Deprecated
public class BuildTreeVisitor
extends BaseBuildTree
implements SchemeNodeVisitor<Void> {
    protected final NodeType nodeType;
    protected List<ITree<DataSchemeNode>> value = new ArrayList<ITree<DataSchemeNode>>();
    protected ITree<DataSchemeNode> node;
    protected boolean root;
    protected boolean ok;
    protected boolean checkbox;
    protected int checkboxOptional = -1;
    protected boolean children;
    protected NodeFilter filter;
    protected final int UN_SHOW = DataFieldKind.PUBLIC_FIELD_DIM.getValue() | DataFieldKind.BUILT_IN_FIELD.getValue();
    protected IEntityMetaService entityMetaService;
    protected IDataTableRelDao<DesignDataTableRelDO> iDataTableRelDao;
    protected IDataTableDao<DesignDataTableDO> iDataTableDao;

    public void setDataTableRelDao(IDataTableRelDao<DesignDataTableRelDO> iDataTableRelDao) {
        this.iDataTableRelDao = iDataTableRelDao;
    }

    public void setDataTableDao(IDataTableDao<DesignDataTableDO> iDataTableDao) {
        this.iDataTableDao = iDataTableDao;
    }

    public VisitorResult preVisitNode(SchemeNode<Void> ele) {
        if (this.ok) {
            return VisitorResult.TERMINATE;
        }
        if (this.filter != null && !this.filter.test(ele)) {
            return VisitorResult.CONTINUE;
        }
        boolean root = "00000000-0000-0000-0000-000000000000".equals(ele.getKey());
        if (root && !this.children) {
            DataSchemeNodeDTO dataSchemeNodeVO = new DataSchemeNodeDTO();
            dataSchemeNodeVO.setKey("00000000-0000-0000-0000-000000000000");
            dataSchemeNodeVO.setType(NodeType.SCHEME_GROUP.getValue());
            dataSchemeNodeVO.setCode("root");
            dataSchemeNodeVO.setTitle("\u5168\u90e8\u6570\u636e\u65b9\u6848");
            ITree node = new ITree((INode)dataSchemeNodeVO);
            node.setExpanded(true);
            node.setSelected(true);
            node.setIcons(NodeIconGetter.getIconByType(NodeType.SCHEME_GROUP));
            this.determineCheck(node, NodeType.SCHEME_GROUP.getValue());
            this.value.add((ITree<DataSchemeNode>)node);
            this.node = node;
            return null;
        }
        return super.deter(ele.getType(), this.nodeType);
    }

    protected void determineCheck(ITree<?> node, int nodeType) {
        if (this.checkbox && this.checkboxOptional != -1 && (nodeType & this.checkboxOptional) == 0) {
            node.setDisabled(true);
        }
    }

    public Void visitRootIsSchemeNode(DesignDataScheme scheme) {
        if (this.root) {
            return null;
        }
        if (!this.children) {
            DataSchemeNodeDTO dataSchemeNodeVO = new DataSchemeNodeDTO(scheme);
            ITree node = new ITree((INode)dataSchemeNodeVO);
            node.setExpanded(true);
            node.setSelected(true);
            node.setIcons(NodeIconGetter.getIconByType(NodeType.SCHEME));
            this.value.add((ITree<DataSchemeNode>)node);
            this.node = node;
        }
        return null;
    }

    public Void visitRootIsGroupNode(DesignDataGroup group) {
        return null;
    }

    public Void visitRootIsTableNode(DesignDataTable table) {
        return null;
    }

    public <DG extends DesignDataGroup, DT extends DesignDataTable, DM extends DesignDataDimension> Map<String, Void> visitSchemeNode(SchemeNode<Void> ele, List<DG> dataGroups, List<DT> dataTables, List<DM> dims) {
        ArrayList<ITree<DataSchemeNode>> value = new ArrayList<ITree<DataSchemeNode>>();
        if (!this.root && !dims.isEmpty()) {
            super.dimNode(ele, dims, value);
        }
        List<ITree<DataSchemeNode>> list = this.getGroupAndTableTreeList(dataGroups, dataTables);
        value.addAll(list);
        if (this.node != null) {
            this.node.setChildren(value);
            if (value.isEmpty()) {
                this.node.setLeaf(true);
            }
        } else {
            this.value = value;
        }
        this.ok = true;
        return null;
    }

    public <DG extends DesignDataGroup, DT extends DesignDataTable> Map<String, Void> visitGroupNode(SchemeNode<Void> ele, List<DG> dataGroups, List<DT> dataTables) {
        this.value = this.getGroupAndTableTreeList(dataGroups, dataTables);
        this.ok = true;
        return null;
    }

    protected <DG extends DesignDataGroup, DT extends DesignDataTable> List<ITree<DataSchemeNode>> getGroupAndTableTreeList(List<DG> dataGroups, List<DT> dataTables) {
        List<ITree<DataSchemeNode>> value = this.getGroupTreeList(dataGroups);
        if (this.nodeType.getValue() < NodeType.ACCOUNT_TABLE.getValue()) {
            this.ok = true;
            return value;
        }
        for (DesignDataTable dataTable : dataTables) {
            DataTableType dataTableType = dataTable.getDataTableType();
            if (null == dataTableType || this.iDataTableRelDao != null && dataTableType == DataTableType.SUB_TABLE) continue;
            DataTableNodeDTO node0 = new DataTableNodeDTO(dataTable);
            ITree node = new ITree((INode)node0);
            node.setLeaf((FIELD & this.nodeType.getValue()) == 0);
            if (this.iDataTableRelDao != null && dataTableType == DataTableType.DETAIL && node.isLeaf()) {
                boolean noSubTable = this.iDataTableRelDao.getByDesTable(dataTable.getKey()).isEmpty();
                node.setLeaf(noSubTable);
            }
            int type = node0.getType();
            node.setIcons(NodeIconGetter.getIconByType(type));
            value.add((ITree<DataSchemeNode>)node);
            this.determineCheck(node, node0.getType());
        }
        this.ok = true;
        return value;
    }

    public <DF extends DesignDataField> void visitTableNode(SchemeNode<Void> ele, List<DF> dataFields) {
        ArrayList<ITree<DataSchemeNode>> value = new ArrayList<ITree<DataSchemeNode>>();
        if (this.iDataTableRelDao != null) {
            List<DesignDataTableRelDO> rel = this.iDataTableRelDao.getByDesTable(ele.getKey());
            if (!rel.isEmpty()) {
                List<DesignDataTableDO> subTables = this.iDataTableDao.batchGet(rel.stream().map(DataTableRelDO::getSrcTableKey).collect(Collectors.toList()));
                for (DesignDataTableDO subTable : subTables) {
                    DataTableNodeDTO node0 = new DataTableNodeDTO(subTable);
                    node0.setParentKey(ele.getKey());
                    ITree node = new ITree((INode)node0);
                    node.setLeaf(true);
                    int type = node0.getType();
                    node.setIcons(NodeIconGetter.getIconByType(type));
                    value.add((ITree<DataSchemeNode>)node);
                    this.determineCheck(node, node0.getType());
                }
            }
            this.value = value;
        }
        if (NodeType.FMDM_TABLE.getValue() >= this.nodeType.getValue()) {
            this.ok = true;
            return;
        }
        for (DesignDataField dataField : dataFields) {
            DataFieldKind dataFieldKind = dataField.getDataFieldKind();
            if (dataFieldKind == null || (this.UN_SHOW & dataFieldKind.getValue()) != 0) continue;
            DataSchemeNodeDTO node0 = new DataSchemeNodeDTO(dataField);
            ITree node = new ITree((INode)node0);
            int type = node0.getType();
            node.setIcons(NodeIconGetter.getIconByType(type));
            this.determineCheck(node, type);
            node.setLeaf(true);
            value.add((ITree<DataSchemeNode>)node);
        }
        this.value = value;
        this.ok = true;
    }

    public <DA extends ColumnModelDefine> void visitDimNode(SchemeNode<Void> ele, List<DA> attributes) {
    }

    public <DG extends DesignDataGroup, DS extends DesignDataScheme> Map<String, Void> visitSchemeGroupNode(SchemeNode<Void> next, List<DG> groups, List<DS> schemes) {
        if (groups.isEmpty() && schemes.isEmpty()) {
            if (this.node != null) {
                this.node.setLeaf(true);
            }
            return null;
        }
        List<ITree<DataSchemeNode>> value = this.getGroupTreeList(groups);
        if (this.nodeType.getValue() >= NodeType.SCHEME.getValue()) {
            for (DesignDataScheme scheme : schemes) {
                if (!this.canRead((DataScheme)scheme)) continue;
                DataSchemeNodeDTO node0 = new DataSchemeNodeDTO(scheme);
                if (this.filter != null && !this.filter.test((Object)node0)) continue;
                ITree schemeNode = new ITree((INode)node0);
                schemeNode.setLeaf(this.nodeType.getValue() == NodeType.SCHEME.getValue());
                int type = node0.getType();
                schemeNode.setIcons(NodeIconGetter.getIconByType(type));
                this.determineCheck(schemeNode, type);
                value.add((ITree<DataSchemeNode>)schemeNode);
            }
        }
        if (this.node != null) {
            this.node.setChildren(value);
        } else {
            this.value = value;
        }
        this.ok = true;
        return null;
    }

    protected <DG extends DesignDataGroup> List<ITree<DataSchemeNode>> getGroupTreeList(List<DG> groups) {
        ArrayList<ITree<DataSchemeNode>> value = new ArrayList<ITree<DataSchemeNode>>();
        for (DesignDataGroup group : groups) {
            if (!this.canRead((DataGroup)group)) continue;
            DataSchemeNodeDTO node0 = new DataSchemeNodeDTO(group);
            if (this.filter != null && !this.filter.test((Object)node0)) continue;
            ITree node = new ITree((INode)node0);
            int type = node0.getType();
            node.setIcons(NodeIconGetter.getIconByType(type));
            value.add((ITree<DataSchemeNode>)node);
            this.determineCheck(node, type);
        }
        return value;
    }

    public List<ITree<DataSchemeNode>> getValue() {
        return this.value;
    }

    public BuildTreeVisitor(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public void setCheckboxOptional(int checkboxOptional) {
        this.checkboxOptional = checkboxOptional;
    }

    public void setChildren(boolean b) {
        this.children = b;
    }

    public void setEntityMetaService(IEntityMetaService entityMetaService) {
        this.entityMetaService = entityMetaService;
    }

    public void setFilter(NodeFilter filter) {
        this.filter = filter;
    }

    @Override
    protected IEntityDefine getEntityDefineByDimKey(String dimKey) {
        return this.entityMetaService.queryEntity(dimKey);
    }
}

