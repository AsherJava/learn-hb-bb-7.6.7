/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.RuntimeDataSchemeNode
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.core.VisitorResult
 *  com.jiuqi.nr.datascheme.api.loader.run.RuntimeSchemeVisitor
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.datascheme.internal.tree.run;

import com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.RuntimeDataSchemeNode;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.core.VisitorResult;
import com.jiuqi.nr.datascheme.api.loader.run.RuntimeSchemeVisitor;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableRelDao;
import com.jiuqi.nr.datascheme.internal.entity.DataTableDO;
import com.jiuqi.nr.datascheme.internal.entity.DataTableRelDO;
import com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.internal.tree.TypeDeter;
import com.jiuqi.nr.datascheme.web.param.ZbSchemeNodeFilter;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class DataResourceTree
implements RuntimeSchemeVisitor<Void> {
    private static final Logger logger = LoggerFactory.getLogger(DataResourceTree.class);
    private List<ITree<RuntimeDataSchemeNode>> value = new ArrayList<ITree<RuntimeDataSchemeNode>>();
    private ITree<RuntimeDataSchemeNode> node;
    private boolean ok;
    private int checkboxOptional = -1;
    private boolean children;
    private static final int DIM_TYPE = 1;
    private final IEntityMetaService entityMetaService;
    private final PeriodEngineService periodEngineService;
    private IDataTableRelDao<DataTableRelDO> iDataTableRelDao;
    private IDataTableDao<DataTableDO> iDataTableDao;
    private final NodeFilter filter;
    private final int unShow = DataFieldKind.PUBLIC_FIELD_DIM.getValue() | DataFieldKind.BUILT_IN_FIELD.getValue();

    public DataResourceTree(IEntityMetaService entityMetaService, PeriodEngineService periodEngineService, NodeFilter filter) {
        this.entityMetaService = entityMetaService;
        this.periodEngineService = periodEngineService;
        this.filter = filter;
    }

    public void setDataTableRelDao(IDataTableRelDao<DataTableRelDO> iDataTableRelDao) {
        this.iDataTableRelDao = iDataTableRelDao;
    }

    public void setDataTableDao(IDataTableDao<DataTableDO> iDataTableDao) {
        this.iDataTableDao = iDataTableDao;
    }

    public VisitorResult preVisitNode(SchemeNode<Void> ele) {
        if (this.ok) {
            return VisitorResult.TERMINATE;
        }
        RuntimeDataSchemeNodeDTO dataSchemeNodeVO = null;
        if ("00000000-0000-0000-0000-000000000000".equals(ele.getKey())) {
            dataSchemeNodeVO = new RuntimeDataSchemeNodeDTO();
            dataSchemeNodeVO.setKey("00000000-0000-0000-0000-000000000000");
            dataSchemeNodeVO.setType(NodeType.SCHEME_GROUP.getValue());
            dataSchemeNodeVO.setCode("root");
            dataSchemeNodeVO.setTitle("\u5168\u90e8\u6570\u636e\u65b9\u6848");
        } else if ("00000000-0000-0000-0000-111111111111".equals(ele.getKey())) {
            dataSchemeNodeVO = new RuntimeDataSchemeNodeDTO();
            dataSchemeNodeVO.setKey("00000000-0000-0000-0000-111111111111");
            dataSchemeNodeVO.setType(NodeType.SCHEME_GROUP.getValue());
            dataSchemeNodeVO.setCode("query_root");
            dataSchemeNodeVO.setTitle("\u5168\u90e8\u67e5\u8be2\u6570\u636e\u65b9\u6848");
        }
        if (!this.children && null != dataSchemeNodeVO) {
            ITree node = new ITree((INode)dataSchemeNodeVO);
            node.setExpanded(true);
            node.setSelected(true);
            this.determineCheck(node, NodeType.SCHEME_GROUP.getValue());
            node.setIcons(NodeIconGetter.getIconByType(NodeType.SCHEME_GROUP));
            this.value.add((ITree<RuntimeDataSchemeNode>)node);
            this.node = node;
        }
        return null;
    }

    private void determineCheck(ITree<?> node, int nodeType) {
        if (this.checkboxOptional != -1 && (nodeType & this.checkboxOptional) == 0) {
            node.setDisabled(true);
        }
    }

    public Void visitRootIsSchemeNode(DataScheme scheme) {
        return null;
    }

    public Void visitRootIsGroupNode(DataGroup group) {
        return null;
    }

    public Void visitRootIsTableNode(DataTable table) {
        return null;
    }

    public <DG extends DataGroup, DT extends DataTable, DM extends DataDimension> Map<String, Void> visitSchemeNode(SchemeNode<Void> ele, List<DG> dataGroups, List<DT> dataTables, List<DM> dims) {
        ArrayList<ITree<RuntimeDataSchemeNode>> value = new ArrayList<ITree<RuntimeDataSchemeNode>>();
        ITree unit = null;
        ITree period = null;
        ArrayList<ITree> scopes = new ArrayList<ITree>();
        ArrayList<ITree> dimsOther = new ArrayList<ITree>();
        dims.removeIf(x -> AdjustUtils.isAdjust(x.getDimKey()));
        for (DataDimension dim : dims) {
            String dimKey = dim.getDimKey();
            DimensionType dimensionType = dim.getDimensionType();
            try {
                ITree node;
                if (dimensionType == DimensionType.PERIOD) {
                    IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
                    IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(dimKey);
                    RuntimeDataSchemeNodeDTO node0 = new RuntimeDataSchemeNodeDTO(periodEntity, ele.getKey());
                    period = node = new ITree((INode)node0);
                    node.setIcons(NodeIconGetter.getIconByType(NodeType.DIM));
                    if (this.filter != null && !this.filter.test((Object)node0)) {
                        continue;
                    }
                } else {
                    IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(dimKey);
                    RuntimeDataSchemeNodeDTO node0 = new RuntimeDataSchemeNodeDTO(iEntityDefine, ele.getKey());
                    node = new ITree((INode)node0);
                    node.setIcons(NodeIconGetter.getIconByType(NodeType.DIM));
                    if (this.filter != null && !this.filter.test((Object)node0)) continue;
                    if (dimensionType == DimensionType.UNIT_SCOPE) {
                        scopes.add(node);
                    }
                    if (dimensionType == DimensionType.UNIT) {
                        unit = node;
                    }
                    if (dimensionType == DimensionType.DIMENSION) {
                        dimsOther.add(node);
                    }
                }
                node.setLeaf(true);
            }
            catch (Exception e) {
                logger.error(" \u5b9e\u4f53 " + dimKey + " \u672a\u627e\u5230\u6216\u5df2\u4e22\u5931", e);
            }
        }
        if (scopes.isEmpty()) {
            if (unit != null) {
                value.add(unit);
            }
        } else {
            value.addAll(scopes);
        }
        if (period != null) {
            value.add(period);
        }
        value.addAll(dimsOther);
        value.addAll(this.getGroupAndTableTreeList(dataGroups, dataTables));
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

    public <DA extends ColumnModelDefine> void visitDimNode(SchemeNode<Void> ele, List<DA> attributes) {
    }

    public <DG extends DataGroup, DT extends DataTable> Map<String, Void> visitGroupNode(SchemeNode<Void> ele, List<DG> dataGroups, List<DT> dataTables) {
        this.value = this.getGroupAndTableTreeList(dataGroups, dataTables);
        this.ok = true;
        return null;
    }

    private <DG extends DataGroup, DT extends DataTable> List<ITree<RuntimeDataSchemeNode>> getGroupAndTableTreeList(List<DG> dataGroups, List<DT> dataTables) {
        List<ITree<RuntimeDataSchemeNode>> value = this.getGroupTreeList(dataGroups);
        for (DataTable dataTable : dataTables) {
            if (dataTable.getDataTableType() == null || this.iDataTableRelDao != null && dataTable.getDataTableType() == DataTableType.SUB_TABLE) continue;
            RuntimeDataSchemeNodeDTO node0 = new RuntimeDataSchemeNodeDTO(dataTable);
            ITree node = new ITree((INode)node0);
            value.add((ITree<RuntimeDataSchemeNode>)node);
            this.determineCheck(node, node0.getType());
            node.setIcons(NodeIconGetter.getIconByType(node0.getType()));
        }
        this.ok = true;
        return value;
    }

    public <DF extends DataField> void visitTableNode(SchemeNode<Void> ele, List<DF> dataFields) {
        ArrayList<ITree<RuntimeDataSchemeNode>> value = new ArrayList<ITree<RuntimeDataSchemeNode>>();
        if (this.iDataTableRelDao != null) {
            String key = ele.getKey();
            if (ele.getType() == NodeType.DETAIL_TABLE.getValue()) {
                List<DataTableRelDO> subTable = this.iDataTableRelDao.getByDesTable(key);
                ArrayList<String> subIds = new ArrayList<String>();
                for (DataTableRelDO dataTableRelDO : subTable) {
                    String srcTableKey = dataTableRelDO.getSrcTableKey();
                    subIds.add(srcTableKey);
                }
                if (!subIds.isEmpty()) {
                    List<DataTableDO> subTables = this.iDataTableDao.batchGet(subIds);
                    for (DataTableDO table : subTables) {
                        RuntimeDataSchemeNodeDTO node0 = new RuntimeDataSchemeNodeDTO(table);
                        node0.setParentKey(key);
                        ITree node = new ITree((INode)node0);
                        value.add((ITree<RuntimeDataSchemeNode>)node);
                        this.determineCheck(node, node0.getType());
                        node.setIcons(NodeIconGetter.getIconByType(node0.getType()));
                    }
                }
            }
        }
        ZbSchemeNodeFilter zbSchemeNodeFilter = null;
        if (this.filter instanceof ZbSchemeNodeFilter) {
            zbSchemeNodeFilter = (ZbSchemeNodeFilter)this.filter;
        }
        for (DataField dataField : dataFields) {
            IEntityDefine entityDefine;
            String refDataEntityKey;
            DataFieldKind dataFieldKind = dataField.getDataFieldKind();
            if (dataFieldKind == null || (this.unShow & dataFieldKind.getValue()) != 0) continue;
            RuntimeDataSchemeNodeDTO runtimeDataSchemeNodeDTO = new RuntimeDataSchemeNodeDTO(dataField);
            if (zbSchemeNodeFilter != null) {
                zbSchemeNodeFilter.filterByPeriod(dataField.getDataSchemeKey(), runtimeDataSchemeNodeDTO);
            }
            if (StringUtils.hasText(refDataEntityKey = dataField.getRefDataEntityKey()) && (entityDefine = this.entityMetaService.queryEntity(refDataEntityKey)) != null && dataFieldKind == DataFieldKind.TABLE_FIELD_DIM) {
                runtimeDataSchemeNodeDTO.setTableDim(true);
                runtimeDataSchemeNodeDTO.setDataTableKey(dataField.getDataTableKey());
            }
            ITree node = new ITree((INode)runtimeDataSchemeNodeDTO);
            this.determineCheck(node, runtimeDataSchemeNodeDTO.getType());
            node.setLeaf(true);
            node.setIcons(NodeIconGetter.getIconByType(runtimeDataSchemeNodeDTO.getType()));
            value.add((ITree<RuntimeDataSchemeNode>)node);
        }
        this.value = value;
        this.ok = true;
    }

    public <DG extends DataGroup, DS extends DataScheme> Map<String, Void> visitSchemeGroupNode(SchemeNode<Void> next, List<DG> groups, List<DS> schemes) {
        if (groups.isEmpty() && schemes.isEmpty()) {
            if (this.node != null) {
                this.node.setLeaf(true);
            }
            return null;
        }
        List<ITree<RuntimeDataSchemeNode>> value = this.getGroupTreeList(groups);
        for (DataScheme scheme : schemes) {
            if (!TypeDeter.canRead(scheme)) continue;
            RuntimeDataSchemeNodeDTO node0 = new RuntimeDataSchemeNodeDTO(scheme);
            if (this.filter != null && !this.filter.test((Object)node0)) continue;
            ITree schemeNode = new ITree((INode)node0);
            this.determineCheck(schemeNode, node0.getType());
            schemeNode.setIcons(NodeIconGetter.getIconByType(node0.getType()));
            value.add((ITree<RuntimeDataSchemeNode>)schemeNode);
        }
        if (this.node != null) {
            this.node.setChildren(value);
        } else {
            this.value = value;
        }
        this.ok = true;
        return null;
    }

    private <DG extends DataGroup> List<ITree<RuntimeDataSchemeNode>> getGroupTreeList(List<DG> groups) {
        ArrayList<ITree<RuntimeDataSchemeNode>> value = new ArrayList<ITree<RuntimeDataSchemeNode>>();
        for (DataGroup group : groups) {
            if (!TypeDeter.canRead(group)) continue;
            RuntimeDataSchemeNodeDTO node0 = new RuntimeDataSchemeNodeDTO(group);
            if (this.filter != null && !this.filter.test((Object)node0)) continue;
            ITree node = new ITree((INode)node0);
            value.add((ITree<RuntimeDataSchemeNode>)node);
            node.setIcons(NodeIconGetter.getIconByType(node0.getType()));
            this.determineCheck(node, node0.getType());
        }
        return value;
    }

    public List<ITree<RuntimeDataSchemeNode>> getValue() {
        return this.value;
    }

    public void setCheckboxOptional(int checkboxOptional) {
        this.checkboxOptional = checkboxOptional;
    }

    public void setChildren(boolean children) {
        this.children = children;
    }
}

