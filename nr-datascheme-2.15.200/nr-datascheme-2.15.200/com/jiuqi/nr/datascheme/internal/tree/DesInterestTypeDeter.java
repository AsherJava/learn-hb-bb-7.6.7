/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeNode
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.datascheme.internal.tree;

import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.DataSchemeNode;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.internal.tree.DataTableNodeDTO;
import com.jiuqi.nr.datascheme.internal.tree.TypeDeter;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public final class DesInterestTypeDeter
extends TypeDeter {
    private static final Logger logger = LoggerFactory.getLogger(DesInterestTypeDeter.class);
    public static final int UN_SHOW = DataFieldKind.PUBLIC_FIELD_DIM.getValue() | DataFieldKind.BUILT_IN_FIELD.getValue();

    public static <DG extends DesignDataGroup> List<ITree<DataSchemeNode>> buildGroup(List<DG> groups, NodeFilter nodeFilter, Consumer<ITree<DataSchemeNode>> consumer) {
        if (CollectionUtils.isEmpty(groups)) {
            return new ArrayList<ITree<DataSchemeNode>>();
        }
        ArrayList<ITree<DataSchemeNode>> value = new ArrayList<ITree<DataSchemeNode>>();
        for (DesignDataGroup group : groups) {
            if (!DesInterestTypeDeter.canRead((DataGroup)group)) continue;
            DataSchemeNodeDTO node0 = new DataSchemeNodeDTO(group);
            if (nodeFilter != null && !nodeFilter.test((Object)node0)) continue;
            ITree node = new ITree((INode)node0);
            if (consumer != null) {
                consumer.accept((ITree<DataSchemeNode>)node);
            }
            node.setIcons(NodeIconGetter.getIconByType(node0.getType()));
            value.add((ITree<DataSchemeNode>)node);
        }
        return value;
    }

    public static <DS extends DesignDataScheme> List<ITree<DataSchemeNode>> buildScheme(List<DS> schemes, int interestType, NodeFilter nodeFilter, Consumer<ITree<DataSchemeNode>> consumer) {
        if (CollectionUtils.isEmpty(schemes)) {
            return new ArrayList<ITree<DataSchemeNode>>();
        }
        ArrayList<ITree<DataSchemeNode>> value = new ArrayList<ITree<DataSchemeNode>>();
        for (DesignDataScheme scheme : schemes) {
            if (!DesInterestTypeDeter.canRead((DataScheme)scheme)) continue;
            DataSchemeNodeDTO node0 = new DataSchemeNodeDTO(scheme);
            if (nodeFilter != null && !nodeFilter.test((Object)node0)) continue;
            ITree schemeNode = new ITree((INode)node0);
            schemeNode.setLeaf((interestType & (NodeType.GROUP.getValue() | NodeType.TABLE.getValue() | NodeType.ACCOUNT_TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.MUL_DIM_TABLE.getValue() | NodeType.MD_INFO.getValue())) == 0);
            schemeNode.setIcons(NodeIconGetter.getIconByType(node0.getType()));
            value.add((ITree<DataSchemeNode>)schemeNode);
            if (consumer == null) continue;
            consumer.accept((ITree<DataSchemeNode>)schemeNode);
        }
        return value;
    }

    public static <DT extends DesignDataTable> List<ITree<DataSchemeNode>> buildTable(List<DT> tables, int interestType, NodeFilter nodeFilter, Consumer<ITree<DataSchemeNode>> consumer) {
        if (CollectionUtils.isEmpty(tables)) {
            return new ArrayList<ITree<DataSchemeNode>>();
        }
        ArrayList<ITree<DataSchemeNode>> value = new ArrayList<ITree<DataSchemeNode>>();
        for (DesignDataTable table : tables) {
            DataTableNodeDTO node0 = new DataTableNodeDTO(table);
            if (nodeFilter != null && !nodeFilter.test((Object)node0)) continue;
            ITree schemeNode = new ITree((INode)node0);
            schemeNode.setLeaf(DesInterestTypeDeter.isLeafByTable((DataTable)table, interestType));
            schemeNode.setIcons(NodeIconGetter.getIconByType(node0.getType()));
            if (consumer != null) {
                consumer.accept((ITree<DataSchemeNode>)schemeNode);
            }
            value.add((ITree<DataSchemeNode>)schemeNode);
        }
        return value;
    }

    public static <DF extends DesignDataField> List<ITree<DataSchemeNode>> buildField(List<DF> dataFields, NodeFilter nodeFilter, Consumer<ITree<DataSchemeNode>> consumer) {
        ArrayList<ITree<DataSchemeNode>> value = new ArrayList<ITree<DataSchemeNode>>();
        for (DesignDataField dataField : dataFields) {
            DataFieldKind dataFieldKind = dataField.getDataFieldKind();
            if (dataFieldKind == null || (UN_SHOW & dataFieldKind.getValue()) != 0) continue;
            DataSchemeNodeDTO node0 = new DataSchemeNodeDTO(dataField);
            if (nodeFilter != null && !nodeFilter.test((Object)node0)) continue;
            ITree node = new ITree((INode)node0);
            int type = node0.getType();
            node.setIcons(NodeIconGetter.getIconByType(NodeType.valueOf((int)type)));
            node.setLeaf(true);
            if (consumer != null) {
                consumer.accept((ITree<DataSchemeNode>)node);
            }
            value.add((ITree<DataSchemeNode>)node);
        }
        return value;
    }

    public static <DM extends DataDimension> List<ITree<DataSchemeNode>> buildDims(List<DM> dims, NodeFilter nodeFilter, int interestType, IEntityMetaService entityMetaService, PeriodEngineService periodEngineService) {
        ArrayList<ITree<DataSchemeNode>> list = new ArrayList<ITree<DataSchemeNode>>();
        if (dims != null) {
            for (DataDimension dim : dims) {
                ITree node;
                DataSchemeNodeDTO node0;
                String schemeKey = dim.getDataSchemeKey();
                String dimKey = dim.getDimKey();
                DimensionType dimensionType = dim.getDimensionType();
                if (dimensionType == DimensionType.PERIOD) {
                    try {
                        IPeriodEntityAdapter periodAdapter = periodEngineService.getPeriodAdapter();
                        IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(dimKey);
                        node0 = new DataSchemeNodeDTO(periodEntity, schemeKey);
                    }
                    catch (Exception e) {
                        logger.error(" \u5b9e\u4f53 " + dimKey + " \u672a\u627e\u5230\u6216\u5df2\u4e22\u5931", e);
                        continue;
                    }
                    if (nodeFilter != null && !nodeFilter.test((Object)node0)) continue;
                    node = new ITree((INode)node0);
                } else {
                    try {
                        IEntityDefine iEntityDefine = entityMetaService.queryEntity(dimKey);
                        node0 = new DataSchemeNodeDTO(iEntityDefine, schemeKey);
                    }
                    catch (Exception e) {
                        logger.error(" \u5b9e\u4f53 " + dimKey + " \u672a\u627e\u5230\u6216\u5df2\u4e22\u5931", e);
                        continue;
                    }
                    if (nodeFilter != null && !nodeFilter.test((Object)node0)) continue;
                    node = new ITree((INode)node0);
                }
                node.setLeaf((interestType & NodeType.ENTITY_ATTRIBUTE.getValue()) == 0);
                node.setIcons(NodeIconGetter.getIconByType(NodeType.DIM));
                list.add((ITree<DataSchemeNode>)node);
            }
        }
        return list;
    }
}

