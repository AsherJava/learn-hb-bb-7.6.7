/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
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
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 */
package com.jiuqi.nr.datascheme.internal.tree.des;

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
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
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
import com.jiuqi.nr.datascheme.internal.tree.DesInterestTypeDeter;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class InterestSpecifiedTree
implements ReverseSchemeNodeVisitor<Void> {
    protected final IEntityMetaService entityMetaService;
    protected final PeriodEngineService periodEngineService;
    protected final int interestType;
    protected NodeFilter nodeFilter;
    protected final String specifiedKey;
    private String desTableKey;
    protected IDataTableRelDao<DesignDataTableRelDO> iDataTableRelDao;
    protected IDataTableDao<DesignDataTableDO> iDataTableDao;
    protected IDataFieldDao<DesignDataFieldDO> dataFieldDao;
    protected List<ITree<DataSchemeNode>> values;

    public InterestSpecifiedTree(IEntityMetaService entityMetaService, PeriodEngineService periodEngineService, int interestType, String specifiedKey) {
        this.entityMetaService = entityMetaService;
        this.periodEngineService = periodEngineService;
        this.interestType = interestType;
        this.specifiedKey = specifiedKey;
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
        List<ITree<DataSchemeNode>> children = DesInterestTypeDeter.buildDims(dims, this.nodeFilter, this.interestType, this.entityMetaService, this.periodEngineService);
        children.addAll(DesInterestTypeDeter.buildGroup(dataGroups, this.nodeFilter, r -> this.determine(ele, (ITree<DataSchemeNode>)r)));
        if (this.iDataTableRelDao != null) {
            DesignDataTable specSubTable = null;
            Iterator<DT> tableItr = dataTables.iterator();
            while (tableItr.hasNext()) {
                DesignDataTable next = (DesignDataTable)tableItr.next();
                if (next.getDataTableType() != DataTableType.SUB_TABLE) continue;
                if (next.getKey().equals(this.specifiedKey)) {
                    specSubTable = next;
                }
                tableItr.remove();
            }
            Consumer<ITree> customLeaf = node -> {
                boolean leafByTable = DesInterestTypeDeter.isLeafByTable(NodeType.valueOf((int)((DataSchemeNode)node.getData()).getType()), this.interestType);
                if (leafByTable && ((DataSchemeNode)node.getData()).getType() == NodeType.DETAIL_TABLE.getValue()) {
                    leafByTable = this.iDataTableRelDao.getByDesTable(node.getKey()).isEmpty();
                }
                node.setLeaf(leafByTable);
            };
            children.addAll(DesInterestTypeDeter.buildTable(dataTables, this.interestType, this.nodeFilter, customLeaf.andThen(r -> this.determine(ele, (ITree<DataSchemeNode>)r))));
            this.subTableSpec(children, specSubTable);
        } else {
            children.addAll(DesInterestTypeDeter.buildTable(dataTables, this.interestType, this.nodeFilter, r -> this.determine(ele, (ITree<DataSchemeNode>)r)));
        }
        this.values = children;
        return null;
    }

    private <DT extends DesignDataTable> void subTableSpec(List<ITree<DataSchemeNode>> children, DT specifSubTable) {
        if (specifSubTable != null && this.iDataTableRelDao != null) {
            boolean hasField = DesInterestTypeDeter.isLeafByTable(DataTableType.SUB_TABLE, this.interestType);
            DesignDataTableRelDO relDO = this.iDataTableRelDao.getBySrcTable(specifSubTable.getKey());
            if (relDO == null) {
                throw new RuntimeException("\u5b9a\u4f4d\u8282\u70b9\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570\uff1a" + specifSubTable);
            }
            String desTableKey = relDO.getDesTableKey();
            for (ITree<DataSchemeNode> child : children) {
                if (!child.getKey().equals(desTableKey)) continue;
                child.setExpanded(true);
                ArrayList<Object> fc = new ArrayList<Object>();
                List<DesignDataTableRelDO> rel = this.iDataTableRelDao.getByDesTable(child.getKey());
                if (!rel.isEmpty()) {
                    List<DesignDataTableDO> subTables = this.iDataTableDao.batchGet(rel.stream().map(DataTableRelDO::getSrcTableKey).collect(Collectors.toList()));
                    for (DesignDataTableDO subTable : subTables) {
                        DataTableNodeDTO node0 = new DataTableNodeDTO(subTable);
                        ITree subTableNode = new ITree((INode)node0);
                        if (subTable.getKey().equals(specifSubTable.getKey())) {
                            subTableNode.setSelected(true);
                            subTableNode.setLeaf(hasField);
                        }
                        fc.add(subTableNode);
                    }
                }
                if (!hasField) {
                    List<DesignDataFieldDO> fields = this.dataFieldDao.getByTable(desTableKey);
                    fc.addAll(DesInterestTypeDeter.buildField(fields, this.nodeFilter, null));
                }
                child.setChildren(fc);
                child.setLeaf(fc.isEmpty());
                break;
            }
        }
    }

    public <DG extends DesignDataGroup, DS extends DesignDataScheme> Void visitGoScNode(SchemeNode<Void> ele, List<DG> dataGroups, List<DS> dataSchemes) {
        ArrayList<ITree<DataSchemeNode>> children = new ArrayList<ITree<DataSchemeNode>>();
        children.addAll(DesInterestTypeDeter.buildGroup(dataGroups, this.nodeFilter, r -> this.determine(ele, (ITree<DataSchemeNode>)r)));
        children.addAll(DesInterestTypeDeter.buildScheme(dataSchemes, this.interestType, this.nodeFilter, r -> this.determine(ele, (ITree<DataSchemeNode>)r)));
        this.values = children;
        return null;
    }

    public Void visitRootNode(SchemeNode<Void> parent) {
        DataSchemeNodeDTO dataSchemeNodeVO = null;
        if ("00000000-0000-0000-0000-000000000000".equals(parent.getKey())) {
            dataSchemeNodeVO = new DataSchemeNodeDTO();
            dataSchemeNodeVO.setKey("00000000-0000-0000-0000-000000000000");
            dataSchemeNodeVO.setType(NodeType.SCHEME_GROUP.getValue());
            dataSchemeNodeVO.setCode("root");
            dataSchemeNodeVO.setTitle("\u5168\u90e8\u6570\u636e\u65b9\u6848");
        } else if ("00000000-0000-0000-0000-111111111111".equals(parent.getKey())) {
            dataSchemeNodeVO = new DataSchemeNodeDTO();
            dataSchemeNodeVO.setKey("00000000-0000-0000-0000-111111111111");
            dataSchemeNodeVO.setType(NodeType.SCHEME_GROUP.getValue());
            dataSchemeNodeVO.setCode("query_root");
            dataSchemeNodeVO.setTitle("\u5168\u90e8\u67e5\u8be2\u6570\u636e\u65b9\u6848");
        }
        ITree node = new ITree((INode)dataSchemeNodeVO);
        node.setIcons(NodeIconGetter.getIconByType(NodeType.SCHEME_GROUP));
        node.setExpanded(true);
        node.setSelected(false);
        ArrayList<ITree<DataSchemeNode>> values = new ArrayList<ITree<DataSchemeNode>>();
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
        if ((ele.getType() & NodeType.SCHEME.getValue()) != 0) {
            return VisitorResult.TERMINATE;
        }
        if ((this.interestType & ele.getType()) == 0) {
            return VisitorResult.TERMINATE;
        }
        return null;
    }

    public <DF extends DesignDataField> Void visitFieldNode(SchemeNode<Void> ele, List<DF> fields) {
        ArrayList<ITree<DataSchemeNode>> children = new ArrayList<ITree<DataSchemeNode>>();
        if (this.iDataTableRelDao != null && !CollectionUtils.isEmpty(fields)) {
            String dataTableKey = ((DesignDataField)fields.get(0)).getDataTableKey();
            List<DesignDataTableRelDO> rel = this.iDataTableRelDao.getByDesTable(dataTableKey);
            if (!CollectionUtils.isEmpty(rel)) {
                List<DesignDataTableDO> subTables = this.iDataTableDao.batchGet(rel.stream().map(DataTableRelDO::getSrcTableKey).collect(Collectors.toList()));
                Consumer<ITree> consumer = node -> {
                    DataSchemeNode data = (DataSchemeNode)node.getData();
                    ((DataSchemeNodeDTO)data).setParentKey(dataTableKey);
                };
                children.addAll(DesInterestTypeDeter.buildTable(subTables, this.interestType, this.nodeFilter, consumer.andThen(r -> this.determine(ele, (ITree<DataSchemeNode>)r))));
                children.addAll(DesInterestTypeDeter.buildField(fields, this.nodeFilter, r -> this.determine(ele, (ITree<DataSchemeNode>)r)));
                this.values = children;
                return null;
            }
            DesignDataTableRelDO cRel = this.iDataTableRelDao.getBySrcTable(dataTableKey);
            if (cRel != null) {
                this.desTableKey = cRel.getDesTableKey();
                List<DesignDataTableRelDO> desRel = this.iDataTableRelDao.getByDesTable(this.desTableKey);
                if (!CollectionUtils.isEmpty(desRel)) {
                    List<DesignDataTableDO> subTables = this.iDataTableDao.batchGet(desRel.stream().map(DataTableRelDO::getSrcTableKey).collect(Collectors.toList()));
                    Consumer<ITree> consumer = node -> {
                        if (node.getKey().equals(dataTableKey)) {
                            node.setExpanded(true);
                            node.setChildren(DesInterestTypeDeter.buildField(fields, this.nodeFilter, r -> this.determine(ele, (ITree<DataSchemeNode>)r)));
                        }
                        DataSchemeNode data = (DataSchemeNode)node.getData();
                        ((DataSchemeNodeDTO)data).setParentKey(this.desTableKey);
                    };
                    children.addAll(DesInterestTypeDeter.buildTable(subTables, this.interestType, this.nodeFilter, consumer.andThen(r -> this.determine(ele, (ITree<DataSchemeNode>)r))));
                    List<DesignDataFieldDO> zFields = this.dataFieldDao.getByTable(this.desTableKey);
                    children.addAll(DesInterestTypeDeter.buildField(zFields, this.nodeFilter, r -> this.determine(ele, (ITree<DataSchemeNode>)r)));
                    this.values = children;
                    return null;
                }
            }
        }
        children.addAll(DesInterestTypeDeter.buildField(fields, this.nodeFilter, r -> this.determine(ele, (ITree<DataSchemeNode>)r)));
        this.values = children;
        return null;
    }

    protected void determine(SchemeNode<Void> ele, ITree<DataSchemeNode> groupNode) {
        if (groupNode.getKey().equals(this.specifiedKey)) {
            groupNode.setSelected(true);
        } else if (ele.getKey().equals(groupNode.getKey())) {
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

    public List<ITree<DataSchemeNode>> getValues() {
        return this.values;
    }

    public void setNodeFilter(NodeFilter nodeFilter) {
        this.nodeFilter = nodeFilter;
    }
}

