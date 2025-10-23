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
 *  com.jiuqi.nr.datascheme.api.loader.run.RuntimeReverseSchemeVisitor
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 */
package com.jiuqi.nr.datascheme.internal.tree.run;

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
import com.jiuqi.nr.datascheme.api.loader.run.RuntimeReverseSchemeVisitor;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableRelDao;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DataTableDO;
import com.jiuqi.nr.datascheme.internal.entity.DataTableRelDO;
import com.jiuqi.nr.datascheme.internal.tree.InterestTypeDeter;
import com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class RunInterestReverseTreeVisitor
implements RuntimeReverseSchemeVisitor<Void> {
    private final IEntityMetaService entityMetaService;
    private final PeriodEngineService periodEngineService;
    private IDataTableRelDao<DataTableRelDO> iDataTableRelDao;
    private IDataTableDao<DataTableDO> iDataTableDao;
    protected IDataFieldDao<DataFieldDO> dataFieldDao;
    private String desTableKey;
    private final int interestType;
    protected NodeFilter nodeFilter;
    protected final String specifiedKey;
    private String eleKey;
    protected List<ITree<RuntimeDataSchemeNode>> values = new ArrayList<ITree<RuntimeDataSchemeNode>>();

    public void setDataTableRelDao(IDataTableRelDao<DataTableRelDO> iDataTableRelDao) {
        this.iDataTableRelDao = iDataTableRelDao;
    }

    public void setDataTableDao(IDataTableDao<DataTableDO> iDataTableDao) {
        this.iDataTableDao = iDataTableDao;
    }

    public void setDataFieldDao(IDataFieldDao<DataFieldDO> dataFieldDao) {
        this.dataFieldDao = dataFieldDao;
    }

    public RunInterestReverseTreeVisitor(IEntityMetaService entityMetaService, PeriodEngineService periodEngineService, int interestType, String specifiedKey) {
        this.entityMetaService = entityMetaService;
        this.periodEngineService = periodEngineService;
        this.interestType = interestType;
        this.specifiedKey = specifiedKey;
    }

    public Void visitRootNode(SchemeNode<Void> parent) {
        RuntimeDataSchemeNodeDTO dataSchemeNodeVO = null;
        if ("00000000-0000-0000-0000-000000000000".equals(parent.getKey())) {
            dataSchemeNodeVO = new RuntimeDataSchemeNodeDTO();
            dataSchemeNodeVO.setKey("00000000-0000-0000-0000-000000000000");
            dataSchemeNodeVO.setType(NodeType.SCHEME_GROUP.getValue());
            dataSchemeNodeVO.setCode("root");
            dataSchemeNodeVO.setTitle("\u5168\u90e8\u6570\u636e\u65b9\u6848");
        } else if ("00000000-0000-0000-0000-111111111111".equals(parent.getKey())) {
            dataSchemeNodeVO = new RuntimeDataSchemeNodeDTO();
            dataSchemeNodeVO.setKey("00000000-0000-0000-0000-111111111111");
            dataSchemeNodeVO.setType(NodeType.SCHEME_GROUP.getValue());
            dataSchemeNodeVO.setCode("query_root");
            dataSchemeNodeVO.setTitle("\u5168\u90e8\u67e5\u8be2\u6570\u636e\u65b9\u6848");
        }
        ITree node = new ITree((INode)dataSchemeNodeVO);
        node.setIcons(NodeIconGetter.getIconByType(NodeType.SCHEME_GROUP));
        node.setExpanded(true);
        node.setSelected(true);
        ArrayList<ITree<RuntimeDataSchemeNode>> values = new ArrayList<ITree<RuntimeDataSchemeNode>>();
        node.setChildren(this.values);
        values.add(node);
        this.values = values;
        return null;
    }

    public VisitorResult preVisitNode(SchemeNode<Void> ele) {
        String key = ele.getKey();
        if (this.specifiedKey.equals(key)) {
            return null;
        }
        if (this.nodeFilter != null && !this.nodeFilter.test(ele)) {
            return VisitorResult.TERMINATE;
        }
        if ((this.interestType & ele.getType()) == 0) {
            return VisitorResult.TERMINATE;
        }
        return null;
    }

    public <DG extends DataGroup, DT extends DataTable, DM extends DataDimension> Void visitGoTaNode(SchemeNode<Void> ele, List<DG> dataGroups, List<DT> dataTables, List<DM> dims) {
        ArrayList<ITree<RuntimeDataSchemeNode>> children = new ArrayList<ITree<RuntimeDataSchemeNode>>(InterestTypeDeter.buildDims(dims, this.nodeFilter, this.interestType, this.entityMetaService, this.periodEngineService));
        this.eleKey = ele.getKey();
        children.addAll(InterestTypeDeter.buildGroup(dataGroups, this.nodeFilter, this::determine));
        if (this.iDataTableRelDao != null) {
            DataTable specSubTable = null;
            for (DataTable dataTable : dataTables) {
                if (dataTable.getDataTableType() != DataTableType.SUB_TABLE || !dataTable.getKey().equals(this.specifiedKey)) continue;
                specSubTable = dataTable;
                break;
            }
            dataTables = dataTables.stream().filter(r -> r.getDataTableType() != DataTableType.SUB_TABLE).collect(Collectors.toList());
            Consumer<ITree> customLeaf = node -> {
                boolean leafByTable = InterestTypeDeter.isLeafByTable(NodeType.valueOf((int)((RuntimeDataSchemeNode)node.getData()).getType()), this.interestType);
                if (leafByTable && ((RuntimeDataSchemeNode)node.getData()).getType() == NodeType.DETAIL_TABLE.getValue()) {
                    leafByTable = this.iDataTableRelDao.getByDesTable(node.getKey()).isEmpty();
                }
                node.setLeaf(leafByTable);
            };
            children.addAll(InterestTypeDeter.buildTable(dataTables, this.interestType, this.nodeFilter, customLeaf.andThen(this::determine)));
            this.subTableSpec(children, specSubTable);
        } else {
            children.addAll(InterestTypeDeter.buildTable(dataTables, this.interestType, this.nodeFilter, this::determine));
        }
        this.eleKey = null;
        this.values = children;
        return null;
    }

    private <DT extends DataTable> void subTableSpec(List<ITree<RuntimeDataSchemeNode>> children, DT specifSubTable) {
        boolean hasField = InterestTypeDeter.isLeafByTable(DataTableType.SUB_TABLE, this.interestType);
        if (specifSubTable != null && this.iDataTableRelDao != null) {
            DataTableRelDO relDO = this.iDataTableRelDao.getBySrcTable(specifSubTable.getKey());
            if (relDO == null) {
                throw new RuntimeException("\u5b9a\u4f4d\u8282\u70b9\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570\uff1a" + specifSubTable);
            }
            String desTableKey = relDO.getDesTableKey();
            for (ITree<RuntimeDataSchemeNode> child : children) {
                if (!child.getKey().equals(desTableKey)) continue;
                child.setExpanded(true);
                ArrayList<Object> fc = new ArrayList<Object>();
                List<DataTableRelDO> rel = this.iDataTableRelDao.getByDesTable(child.getKey());
                if (!rel.isEmpty()) {
                    List<DataTableDO> subTables = this.iDataTableDao.batchGet(rel.stream().map(DataTableRelDO::getSrcTableKey).collect(Collectors.toList()));
                    for (DataTableDO subTable : subTables) {
                        RuntimeDataSchemeNodeDTO node0 = new RuntimeDataSchemeNodeDTO(subTable);
                        ITree subTableNode = new ITree((INode)node0);
                        if (subTable.getKey().equals(specifSubTable.getKey())) {
                            subTableNode.setSelected(true);
                            subTableNode.setLeaf(hasField);
                        }
                        fc.add(subTableNode);
                    }
                }
                if (!hasField) {
                    List<DataFieldDO> fields = this.dataFieldDao.getByTable(desTableKey);
                    fc.addAll(InterestTypeDeter.buildField(fields, this.nodeFilter, null));
                }
                child.setChildren(fc);
                break;
            }
        }
    }

    public <DG extends DataGroup, DS extends DataScheme> Void visitGoScNode(SchemeNode<Void> ele, List<DG> dataGroups, List<DS> dataSchemes) {
        ArrayList<ITree<RuntimeDataSchemeNode>> children = new ArrayList<ITree<RuntimeDataSchemeNode>>();
        this.eleKey = ele.getKey();
        children.addAll(InterestTypeDeter.buildGroup(dataGroups, this.nodeFilter, this::determine));
        children.addAll(InterestTypeDeter.buildScheme(dataSchemes, this.interestType, this.nodeFilter, this::determine));
        this.eleKey = null;
        this.values = children;
        return null;
    }

    public <DF extends DataField> Void visitFieldNode(SchemeNode<Void> ele, List<DF> fields) {
        this.eleKey = ele.getKey();
        ArrayList<ITree<RuntimeDataSchemeNode>> children = new ArrayList<ITree<RuntimeDataSchemeNode>>();
        if (this.iDataTableRelDao != null && !CollectionUtils.isEmpty(fields)) {
            DataTableRelDO cRel;
            String dataTableKey = ((DataField)fields.get(0)).getDataTableKey();
            List<DataTableRelDO> rel = this.iDataTableRelDao.getByDesTable(dataTableKey);
            if (!CollectionUtils.isEmpty(rel)) {
                List<DataTableDO> subTables = this.iDataTableDao.batchGet(rel.stream().map(DataTableRelDO::getSrcTableKey).collect(Collectors.toList()));
                Consumer<ITree> consumer = node -> {
                    RuntimeDataSchemeNode data = (RuntimeDataSchemeNode)node.getData();
                    ((RuntimeDataSchemeNodeDTO)data).setParentKey(dataTableKey);
                };
                children.addAll(InterestTypeDeter.buildTable(subTables, this.interestType, this.nodeFilter, consumer.andThen(this::determine)));
                children.addAll(InterestTypeDeter.buildField(fields, this.nodeFilter, this::determine));
            }
            if ((cRel = this.iDataTableRelDao.getBySrcTable(dataTableKey)) != null) {
                this.desTableKey = cRel.getDesTableKey();
                List<DataTableRelDO> desRel = this.iDataTableRelDao.getByDesTable(this.desTableKey);
                if (!CollectionUtils.isEmpty(desRel)) {
                    List<DataTableDO> subTables = this.iDataTableDao.batchGet(desRel.stream().map(DataTableRelDO::getSrcTableKey).collect(Collectors.toList()));
                    Consumer<ITree> consumer = node -> {
                        if (node.getKey().equals(dataTableKey)) {
                            node.setExpanded(true);
                            node.setChildren(InterestTypeDeter.buildField(fields, this.nodeFilter, this::determine));
                        }
                        RuntimeDataSchemeNode data = (RuntimeDataSchemeNode)node.getData();
                        ((RuntimeDataSchemeNodeDTO)data).setParentKey(this.desTableKey);
                    };
                    children.addAll(InterestTypeDeter.buildTable(subTables, this.interestType, this.nodeFilter, consumer.andThen(this::determine)));
                    List<DataFieldDO> zFields = this.dataFieldDao.getByTable(this.desTableKey);
                    children.addAll(InterestTypeDeter.buildField(zFields, this.nodeFilter, this::determine));
                }
            }
        }
        children.addAll(InterestTypeDeter.buildField(fields, this.nodeFilter, this::determine));
        this.values = children;
        this.eleKey = null;
        return null;
    }

    protected void determine(ITree<RuntimeDataSchemeNode> groupNode) {
        if (groupNode.getKey().equals(this.specifiedKey)) {
            groupNode.setSelected(true);
        } else if (groupNode.getKey().equals(this.eleKey)) {
            groupNode.setExpanded(true);
            if (!this.values.isEmpty()) {
                groupNode.setChildren(this.values);
            }
        } else if (groupNode.getKey().equals(this.desTableKey)) {
            groupNode.setExpanded(true);
            if (!this.values.isEmpty()) {
                groupNode.setChildren(this.values);
            }
        }
    }

    public List<ITree<RuntimeDataSchemeNode>> getValues() {
        return this.values;
    }

    public void setNodeFilter(NodeFilter nodeFilter) {
        this.nodeFilter = nodeFilter;
    }
}

