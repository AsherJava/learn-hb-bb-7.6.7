/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.nodeicon.IconCategory
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider
 *  com.jiuqi.nr.state.common.StateConst
 *  com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils
 */
package com.jiuiqi.nr.unit.treebase.node.builder;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.node.state.TerminalStateManagement;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.nodeicon.IconCategory;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider;
import com.jiuqi.nr.state.common.StateConst;
import com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TerminalStateNodeDataBuilder
implements IUnitTreeNodeBuilder {
    private static final String KEY_OF_END_FILL_STATE = "endFillState";
    private final IUnitTreeContext context;
    private final IconSourceProvider iconProvider;
    private final IUnitTreeNodeBuilder baseNodeBuilder;
    private DimensionValueSet dimValueSet;
    private Map<DimensionValueSet, StateConst> statusMap;

    public TerminalStateNodeDataBuilder(IUnitTreeContext context, IUnitTreeNodeBuilder baseNodeBuilder, IconSourceProvider iconProvider) {
        this.context = context;
        this.iconProvider = iconProvider;
        this.baseNodeBuilder = baseNodeBuilder;
    }

    @Override
    public void beforeCreateITreeNode(List<IEntityRow> rows) {
        this.baseNodeBuilder.beforeCreateITreeNode(rows);
        TerminalStateManagement terminalStateMgr = (TerminalStateManagement)SpringBeanUtils.getBean(TerminalStateManagement.class);
        this.dimValueSet = terminalStateMgr.buildDimValueSet(this.context);
        this.statusMap = terminalStateMgr.batchTerminalState(this.context, rows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList()));
    }

    @Override
    public ITree<IBaseNodeData> buildTreeNode(IEntityRow row) {
        ITree<IBaseNodeData> node = this.baseNodeBuilder.buildTreeNode(row);
        StateConst stateConst = this.readStateConst(node.getKey());
        if (stateConst != null) {
            ((IBaseNodeData)node.getData()).put(KEY_OF_END_FILL_STATE, (Object)stateConst.toString());
            this.setNodeIcon(node, stateConst.toString());
        }
        return node;
    }

    public StateConst readStateConst(String rowKey) {
        if (null != this.statusMap) {
            String mainDimName = this.context.getEntityDefine().getDimensionName();
            this.dimValueSet.setValue(mainDimName, (Object)rowKey);
            StateConst state = this.statusMap.get(this.dimValueSet);
            return state == null ? StateConst.STARTFILL : state;
        }
        return null;
    }

    private void setNodeIcon(ITree<IBaseNodeData> node, String stateCode) {
        String statusIconKey = this.iconProvider.getIconKey(IconCategory.STATUS_ICONS, stateCode);
        if (null != statusIconKey) {
            node.setIcons((String[])JavaBeanUtils.appendElement((Object[])node.getIcons(), (Object[])new String[]{statusIconKey}));
        }
    }
}

