/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DataTableRel
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.RuntimeDataSchemeNode
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.core.VisitorResult
 *  com.jiuqi.nr.datascheme.api.loader.run.RuntimeSchemeVisitor
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.datascheme.internal.tree.run;

import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DataTableRel;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.RuntimeDataSchemeNode;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.core.VisitorResult;
import com.jiuqi.nr.datascheme.api.loader.run.RuntimeSchemeVisitor;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableRelDao;
import com.jiuqi.nr.datascheme.internal.entity.DataTableDO;
import com.jiuqi.nr.datascheme.internal.entity.DataTableRelDO;
import com.jiuqi.nr.datascheme.internal.tree.InterestTypeDeter;
import com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class RunInterestTreeVisitor
implements RuntimeSchemeVisitor<Void> {
    private NodeFilter nodeFilter;
    protected List<ITree<RuntimeDataSchemeNode>> value = new ArrayList<ITree<RuntimeDataSchemeNode>>();
    private ITree<RuntimeDataSchemeNode> root;
    private boolean complete;
    protected boolean children;
    private final int interestType;
    private final IEntityMetaService entityMetaService;
    private final PeriodEngineService periodEngineService;
    private IDataTableRelDao<DataTableRelDO> iDataTableRelDao;
    private IDataTableDao<DataTableDO> iDataTableDao;

    public RunInterestTreeVisitor(NodeFilter nodeFilter, int interestType, IEntityMetaService entityMetaService, PeriodEngineService periodEngineService) {
        this.nodeFilter = nodeFilter;
        this.interestType = interestType;
        this.entityMetaService = entityMetaService;
        this.periodEngineService = periodEngineService;
    }

    public void setDataTableRelDao(IDataTableRelDao<DataTableRelDO> iDataTableRelDao) {
        this.iDataTableRelDao = iDataTableRelDao;
    }

    public void setDataTableDao(IDataTableDao<DataTableDO> iDataTableDao) {
        this.iDataTableDao = iDataTableDao;
    }

    public void setChildren(boolean children) {
        this.children = children;
    }

    public void setNodeFilter(NodeFilter nodeFilter) {
        this.nodeFilter = nodeFilter;
    }

    public List<ITree<RuntimeDataSchemeNode>> getValue() {
        return this.value;
    }

    public VisitorResult preVisitNode(SchemeNode<Void> ele) {
        if (this.complete) {
            return VisitorResult.TERMINATE;
        }
        if (this.nodeFilter != null && !this.nodeFilter.test(ele)) {
            return VisitorResult.CONTINUE;
        }
        if ((ele.getType() & this.interestType) == 0) {
            return VisitorResult.CONTINUE;
        }
        return null;
    }

    public Void visitRootIsSchemeNode(DataScheme scheme) {
        if (!this.children) {
            RuntimeDataSchemeNodeDTO root = new RuntimeDataSchemeNodeDTO(scheme);
            ITree tree = new ITree((INode)root);
            tree.setExpanded(true);
            tree.setSelected(true);
            this.value.add((ITree<RuntimeDataSchemeNode>)tree);
            this.root = tree;
        }
        return null;
    }

    public Void visitRootIsGroupNode(DataGroup group) {
        if (!this.children) {
            RuntimeDataSchemeNodeDTO root = new RuntimeDataSchemeNodeDTO(group);
            ITree tree = new ITree((INode)root);
            tree.setSelected(true);
            tree.setExpanded(true);
            this.value.add((ITree<RuntimeDataSchemeNode>)tree);
            this.root = tree;
        }
        return null;
    }

    public Void visitRootIsTableNode(DataTable table) {
        return null;
    }

    public <DG extends DataGroup, DS extends DataScheme> Map<String, Void> visitSchemeGroupNode(SchemeNode<Void> next, List<DG> groups, List<DS> schemes) {
        if (groups.isEmpty() && schemes.isEmpty()) {
            if (this.root != null) {
                this.root.setLeaf(true);
                this.complete = true;
            }
            return null;
        }
        ArrayList<ITree<RuntimeDataSchemeNode>> value = new ArrayList<ITree<RuntimeDataSchemeNode>>();
        value.addAll(this.buildGroup(groups));
        value.addAll(this.buildSchema(schemes));
        if (this.root != null) {
            this.root.setChildren(value);
        } else {
            this.value = value;
        }
        this.complete = true;
        return null;
    }

    public <DG extends DataGroup, DT extends DataTable, DM extends DataDimension> Map<String, Void> visitSchemeNode(SchemeNode<Void> ele, List<DG> dataGroups, List<DT> dataTables, List<DM> dims) {
        ArrayList<ITree<RuntimeDataSchemeNode>> value = new ArrayList<ITree<RuntimeDataSchemeNode>>();
        value.addAll(this.buildDims(dims));
        value.addAll(this.buildGroup(dataGroups));
        value.addAll(this.buildTable(dataTables));
        if (this.root != null) {
            this.root.setChildren(value);
            if (value.isEmpty()) {
                this.root.setLeaf(true);
            }
        } else {
            this.value = value;
        }
        this.complete = true;
        return null;
    }

    public <DA extends ColumnModelDefine> void visitDimNode(SchemeNode<Void> ele, List<DA> attributes) {
        ArrayList<ITree<RuntimeDataSchemeNode>> value = new ArrayList<ITree<RuntimeDataSchemeNode>>();
        String dimKey = ele.getKey();
        if (attributes != null) {
            for (ColumnModelDefine attribute : attributes) {
                RuntimeDataSchemeNodeDTO node0 = new RuntimeDataSchemeNodeDTO(attribute, dimKey);
                if (this.nodeFilter != null && !this.nodeFilter.test((Object)node0)) continue;
                ITree node = new ITree((INode)node0);
                node.setIcons(NodeIconGetter.getIconByType(NodeType.valueOf((int)node0.getType())));
                node.setLeaf(true);
                value.add((ITree<RuntimeDataSchemeNode>)node);
            }
        }
        this.complete = true;
        this.value = value;
    }

    public <DG extends DataGroup, DT extends DataTable> Map<String, Void> visitGroupNode(SchemeNode<Void> ele, List<DG> dataGroups, List<DT> dataTables) {
        ArrayList<ITree<RuntimeDataSchemeNode>> value = new ArrayList<ITree<RuntimeDataSchemeNode>>();
        value.addAll(this.buildGroup(dataGroups));
        value.addAll(this.buildTable(dataTables));
        this.value = value;
        this.complete = true;
        return null;
    }

    public <DF extends DataField> void visitTableNode(SchemeNode<Void> ele, List<DF> dataFields) {
        List<DataTableRelDO> rel;
        if (this.iDataTableRelDao != null && !CollectionUtils.isEmpty(rel = this.iDataTableRelDao.getByDesTable(ele.getKey()))) {
            List<DataTableDO> subTables = this.iDataTableDao.batchGet(rel.stream().map(DataTableRel::getSrcTableKey).collect(Collectors.toList()));
            Consumer<ITree<RuntimeDataSchemeNode>> consumer = node -> {
                RuntimeDataSchemeNode data = (RuntimeDataSchemeNode)node.getData();
                ((RuntimeDataSchemeNodeDTO)data).setParentKey(ele.getKey());
            };
            this.value.addAll(InterestTypeDeter.buildTable(subTables, this.interestType, this.nodeFilter, consumer));
        }
        this.value.addAll(InterestTypeDeter.buildField(dataFields, this.nodeFilter, null));
        this.complete = true;
    }

    private <DM extends DataDimension> List<ITree<RuntimeDataSchemeNode>> buildDims(List<DM> dims) {
        return InterestTypeDeter.buildDims(dims, this.nodeFilter, this.interestType, this.entityMetaService, this.periodEngineService);
    }

    private <DG extends DataGroup> List<ITree<RuntimeDataSchemeNode>> buildGroup(List<DG> groups) {
        return InterestTypeDeter.buildGroup(groups, this.nodeFilter, null);
    }

    private <DS extends DataScheme> List<ITree<RuntimeDataSchemeNode>> buildSchema(List<DS> schema) {
        return InterestTypeDeter.buildScheme(schema, this.interestType, this.nodeFilter, null);
    }

    private <DT extends DataTable> List<ITree<RuntimeDataSchemeNode>> buildTable(List<DT> dataTables) {
        Consumer<ITree> customLeaf = null;
        if (this.iDataTableRelDao != null) {
            dataTables = dataTables.stream().filter(r -> r.getDataTableType() != DataTableType.SUB_TABLE).collect(Collectors.toList());
            customLeaf = node -> {
                boolean subTable = this.iDataTableRelDao.getByDesTable(node.getKey()).isEmpty();
                node.setLeaf(subTable && InterestTypeDeter.isLeafByTable(NodeType.valueOf((int)((RuntimeDataSchemeNode)node.getData()).getType()), this.interestType));
            };
        }
        return InterestTypeDeter.buildTable(dataTables, this.interestType, this.nodeFilter, customLeaf);
    }
}

