/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataTableRel
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
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.datascheme.internal.tree.des;

import com.jiuqi.nr.datascheme.api.DataTableRel;
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
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableRelDao;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableRelDO;
import com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.internal.tree.DesInterestTypeDeter;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class InterestTreeVisitor
implements SchemeNodeVisitor<Void> {
    private final IEntityMetaService entityMetaService;
    private final PeriodEngineService periodEngineService;
    private final int interestType;
    protected List<ITree<DataSchemeNode>> value = new ArrayList<ITree<DataSchemeNode>>();
    protected ITree<DataSchemeNode> node;
    protected boolean ok;
    protected boolean children;
    protected NodeFilter nodeFilter;
    protected IDataTableRelDao<DesignDataTableRelDO> iDataTableRelDao;
    protected IDataTableDao<DesignDataTableDO> iDataTableDao;
    protected IDataFieldDao<DesignDataFieldDO> dataFieldDao;

    public InterestTreeVisitor(IEntityMetaService entityMetaService, PeriodEngineService periodEngineService, int interestType) {
        this.entityMetaService = entityMetaService;
        this.periodEngineService = periodEngineService;
        this.interestType = interestType;
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

    public VisitorResult preVisitNode(SchemeNode<Void> ele) {
        if (this.ok) {
            return VisitorResult.TERMINATE;
        }
        if (this.nodeFilter != null && !this.nodeFilter.test(ele)) {
            return VisitorResult.CONTINUE;
        }
        if (!this.children) {
            DataSchemeNodeDTO dataSchemeNodeVO = null;
            if ("00000000-0000-0000-0000-000000000000".equals(ele.getKey())) {
                dataSchemeNodeVO = new DataSchemeNodeDTO();
                dataSchemeNodeVO = new DataSchemeNodeDTO();
                dataSchemeNodeVO.setKey("00000000-0000-0000-0000-000000000000");
                dataSchemeNodeVO.setType(NodeType.SCHEME_GROUP.getValue());
                dataSchemeNodeVO.setCode("root");
                dataSchemeNodeVO.setTitle("\u5168\u90e8\u6570\u636e\u65b9\u6848");
            } else if ("00000000-0000-0000-0000-111111111111".equals(ele.getKey())) {
                dataSchemeNodeVO = new DataSchemeNodeDTO();
                dataSchemeNodeVO = new DataSchemeNodeDTO();
                dataSchemeNodeVO.setKey("00000000-0000-0000-0000-111111111111");
                dataSchemeNodeVO.setType(NodeType.SCHEME_GROUP.getValue());
                dataSchemeNodeVO.setCode("query_root");
                dataSchemeNodeVO.setTitle("\u5168\u90e8\u67e5\u8be2\u6570\u636e\u65b9\u6848");
            }
            if (null != dataSchemeNodeVO) {
                ITree node = new ITree((INode)dataSchemeNodeVO);
                node.setIcons(NodeIconGetter.getIconByType(NodeType.SCHEME_GROUP));
                node.setExpanded(true);
                node.setSelected(true);
                this.value.add((ITree<DataSchemeNode>)node);
                this.node = node;
                return null;
            }
        }
        if ((ele.getType() & this.interestType) == 0) {
            return VisitorResult.CONTINUE;
        }
        return null;
    }

    public Void visitRootIsSchemeNode(DesignDataScheme scheme) {
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

    public <DG extends DesignDataGroup, DS extends DesignDataScheme> Map<String, Void> visitSchemeGroupNode(SchemeNode<Void> next, List<DG> groups, List<DS> schemes) {
        List<ITree<DataSchemeNode>> iTrees = DesInterestTypeDeter.buildGroup(groups, this.nodeFilter, null);
        iTrees.addAll(DesInterestTypeDeter.buildScheme(schemes, this.interestType, this.nodeFilter, null));
        if (this.node != null) {
            this.node.setChildren(iTrees);
            if (this.value.isEmpty()) {
                this.node.setLeaf(true);
            }
        } else {
            this.value = iTrees;
        }
        this.ok = true;
        return null;
    }

    public <DG extends DesignDataGroup, DT extends DesignDataTable, DM extends DesignDataDimension> Map<String, Void> visitSchemeNode(SchemeNode<Void> ele, List<DG> dataGroups, List<DT> dataTables, List<DM> dims) {
        ArrayList<ITree<DataSchemeNode>> value = new ArrayList<ITree<DataSchemeNode>>(DesInterestTypeDeter.buildDims(dims, this.nodeFilter, this.interestType, this.entityMetaService, this.periodEngineService));
        value.addAll(DesInterestTypeDeter.buildGroup(dataGroups, this.nodeFilter, null));
        value.addAll(this.buildTable(dataTables));
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
        ArrayList<ITree<DataSchemeNode>> value = new ArrayList<ITree<DataSchemeNode>>();
        String dimKey = ele.getKey();
        if (attributes != null) {
            for (ColumnModelDefine attribute : attributes) {
                DataSchemeNodeDTO node0 = new DataSchemeNodeDTO(attribute, dimKey);
                if (this.nodeFilter != null && !this.nodeFilter.test((Object)node0)) continue;
                ITree node = new ITree((INode)node0);
                node.setIcons(NodeIconGetter.getIconByType(NodeType.valueOf((int)node0.getType())));
                node.setLeaf(true);
                value.add((ITree<DataSchemeNode>)node);
            }
        }
        this.value = value;
    }

    public <DG extends DesignDataGroup, DT extends DesignDataTable> Map<String, Void> visitGroupNode(SchemeNode<Void> ele, List<DG> dataGroups, List<DT> dataTables) {
        List<ITree<DataSchemeNode>> value = DesInterestTypeDeter.buildGroup(dataGroups, this.nodeFilter, null);
        value.addAll(this.buildTable(dataTables));
        this.value = value;
        this.ok = true;
        return null;
    }

    public <DF extends DesignDataField> void visitTableNode(SchemeNode<Void> ele, List<DF> dataFields) {
        List<DesignDataTableRelDO> rel;
        if (this.iDataTableRelDao != null && !CollectionUtils.isEmpty(rel = this.iDataTableRelDao.getByDesTable(ele.getKey()))) {
            List<DesignDataTableDO> subTables = this.iDataTableDao.batchGet(rel.stream().map(DataTableRel::getSrcTableKey).collect(Collectors.toList()));
            Consumer<ITree<DataSchemeNode>> consumer = node -> {
                DataSchemeNode data = (DataSchemeNode)node.getData();
                ((DataSchemeNodeDTO)data).setParentKey(ele.getKey());
            };
            this.value.addAll(DesInterestTypeDeter.buildTable(subTables, this.interestType, this.nodeFilter, consumer));
        }
        this.value.addAll(DesInterestTypeDeter.buildField(dataFields, this.nodeFilter, null));
        this.ok = true;
    }

    private <DT extends DesignDataTable> List<ITree<DataSchemeNode>> buildTable(List<DT> dataTables) {
        Consumer<ITree> customLeaf = null;
        if (this.iDataTableRelDao != null) {
            dataTables = dataTables.stream().filter(r -> r.getDataTableType() != DataTableType.SUB_TABLE).collect(Collectors.toList());
            customLeaf = node -> {
                boolean leafByTable = DesInterestTypeDeter.isLeafByTable(NodeType.valueOf((int)((DataSchemeNode)node.getData()).getType()), this.interestType);
                if (leafByTable && ((DataSchemeNode)node.getData()).getType() == NodeType.DETAIL_TABLE.getValue()) {
                    leafByTable = this.iDataTableRelDao.getByDesTable(node.getKey()).isEmpty();
                }
                node.setLeaf(leafByTable);
            };
        }
        return DesInterestTypeDeter.buildTable(dataTables, this.interestType, this.nodeFilter, customLeaf);
    }

    public List<ITree<DataSchemeNode>> getValue() {
        return this.value;
    }

    public void setChildren(boolean children) {
        this.children = children;
    }

    public void setNodeFilter(NodeFilter nodeFilter) {
        this.nodeFilter = nodeFilter;
    }
}

