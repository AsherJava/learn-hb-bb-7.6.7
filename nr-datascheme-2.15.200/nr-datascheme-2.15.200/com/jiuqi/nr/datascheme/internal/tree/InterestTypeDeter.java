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
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.internal.tree.TypeDeter;
import com.jiuqi.nr.datascheme.web.param.ZbSchemeNodeFilter;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public final class InterestTypeDeter
extends TypeDeter {
    private static final Logger logger = LoggerFactory.getLogger(InterestTypeDeter.class);
    public static final int UN_SHOW = DataFieldKind.PUBLIC_FIELD_DIM.getValue() | DataFieldKind.BUILT_IN_FIELD.getValue();

    public static <DG extends DataGroup> List<ITree<RuntimeDataSchemeNode>> buildGroup(List<DG> groups, NodeFilter nodeFilter, Consumer<ITree<RuntimeDataSchemeNode>> consumer) {
        if (CollectionUtils.isEmpty(groups)) {
            return Collections.emptyList();
        }
        ArrayList<ITree<RuntimeDataSchemeNode>> value = new ArrayList<ITree<RuntimeDataSchemeNode>>();
        for (DataGroup group : groups) {
            if (!InterestTypeDeter.canRead(group)) continue;
            RuntimeDataSchemeNodeDTO node0 = new RuntimeDataSchemeNodeDTO(group);
            if (nodeFilter != null && !nodeFilter.test((Object)node0)) continue;
            ITree node = new ITree((INode)node0);
            if (consumer != null) {
                consumer.accept((ITree<RuntimeDataSchemeNode>)node);
            }
            node.setIcons(NodeIconGetter.getIconByType(node0.getType()));
            value.add((ITree<RuntimeDataSchemeNode>)node);
        }
        return value;
    }

    public static <DS extends DataScheme> List<ITree<RuntimeDataSchemeNode>> buildScheme(List<DS> schemes, int interestType, NodeFilter nodeFilter, Consumer<ITree<RuntimeDataSchemeNode>> consumer) {
        if (CollectionUtils.isEmpty(schemes)) {
            return Collections.emptyList();
        }
        ArrayList<ITree<RuntimeDataSchemeNode>> value = new ArrayList<ITree<RuntimeDataSchemeNode>>();
        for (DataScheme scheme : schemes) {
            if (!InterestTypeDeter.canRead(scheme)) continue;
            RuntimeDataSchemeNodeDTO node0 = new RuntimeDataSchemeNodeDTO(scheme);
            if (nodeFilter != null && !nodeFilter.test((Object)node0)) continue;
            ITree schemeNode = new ITree((INode)node0);
            schemeNode.setLeaf((interestType & (NodeType.GROUP.getValue() | NodeType.TABLE.getValue() | NodeType.ACCOUNT_TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.MUL_DIM_TABLE.getValue() | NodeType.MD_INFO.getValue())) == 0);
            schemeNode.setIcons(NodeIconGetter.getIconByType(node0.getType()));
            value.add((ITree<RuntimeDataSchemeNode>)schemeNode);
            if (consumer == null) continue;
            consumer.accept((ITree<RuntimeDataSchemeNode>)schemeNode);
        }
        return value;
    }

    public static <DT extends DataTable> List<ITree<RuntimeDataSchemeNode>> buildTable(List<DT> tables, int interestType, NodeFilter nodeFilter, Consumer<ITree<RuntimeDataSchemeNode>> consumer) {
        ArrayList<ITree<RuntimeDataSchemeNode>> value = new ArrayList<ITree<RuntimeDataSchemeNode>>();
        if (CollectionUtils.isEmpty(tables)) {
            return value;
        }
        for (DataTable table : tables) {
            RuntimeDataSchemeNodeDTO node0 = new RuntimeDataSchemeNodeDTO(table);
            if (nodeFilter != null && !nodeFilter.test((Object)node0)) continue;
            ITree schemeNode = new ITree((INode)node0);
            schemeNode.setLeaf(InterestTypeDeter.isLeafByTable(table, interestType));
            schemeNode.setIcons(NodeIconGetter.getIconByType(node0.getType()));
            if (consumer != null) {
                consumer.accept((ITree<RuntimeDataSchemeNode>)schemeNode);
            }
            value.add((ITree<RuntimeDataSchemeNode>)schemeNode);
        }
        return value;
    }

    public static <DF extends DataField> List<ITree<RuntimeDataSchemeNode>> buildField(List<DF> dataFields, NodeFilter nodeFilter, Consumer<ITree<RuntimeDataSchemeNode>> consumer) {
        ArrayList<ITree<RuntimeDataSchemeNode>> value = new ArrayList<ITree<RuntimeDataSchemeNode>>();
        for (DataField dataField : dataFields) {
            DataFieldKind dataFieldKind = dataField.getDataFieldKind();
            if (dataFieldKind == null || (UN_SHOW & dataFieldKind.getValue()) != 0) continue;
            RuntimeDataSchemeNodeDTO node0 = new RuntimeDataSchemeNodeDTO(dataField);
            if (nodeFilter != null) {
                if (!nodeFilter.test((Object)node0)) continue;
                if (nodeFilter instanceof ZbSchemeNodeFilter) {
                    ((ZbSchemeNodeFilter)nodeFilter).filterByPeriod(dataField.getDataSchemeKey(), node0);
                }
            }
            ITree node = new ITree((INode)node0);
            int type = node0.getType();
            node.setIcons(NodeIconGetter.getIconByType(NodeType.valueOf((int)type)));
            node.setLeaf(true);
            if (consumer != null) {
                consumer.accept((ITree<RuntimeDataSchemeNode>)node);
            }
            value.add((ITree<RuntimeDataSchemeNode>)node);
        }
        return value;
    }

    public static <DM extends DataDimension> List<ITree<RuntimeDataSchemeNode>> buildDims(List<DM> dims, NodeFilter nodeFilter, int interestType, IEntityMetaService entityMetaService, PeriodEngineService periodEngineService) {
        ArrayList<ITree<RuntimeDataSchemeNode>> list = new ArrayList<ITree<RuntimeDataSchemeNode>>();
        if (dims != null) {
            for (DataDimension dim : dims) {
                ITree node;
                RuntimeDataSchemeNodeDTO node0;
                if (AdjustUtils.isAdjust(dim.getDimKey()).booleanValue()) continue;
                String schemeKey = dim.getDataSchemeKey();
                String dimKey = dim.getDimKey();
                DimensionType dimensionType = dim.getDimensionType();
                if (dimensionType == DimensionType.PERIOD) {
                    try {
                        IPeriodEntityAdapter periodAdapter = periodEngineService.getPeriodAdapter();
                        IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(dimKey);
                        node0 = new RuntimeDataSchemeNodeDTO(periodEntity, schemeKey);
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
                        node0 = new RuntimeDataSchemeNodeDTO(iEntityDefine, schemeKey);
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
                list.add((ITree<RuntimeDataSchemeNode>)node);
            }
        }
        return list;
    }
}

