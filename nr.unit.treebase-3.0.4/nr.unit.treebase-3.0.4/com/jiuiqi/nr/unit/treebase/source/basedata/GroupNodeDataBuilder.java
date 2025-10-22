/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.itreebase.node.BaseNodeDataImpl
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.nodeicon.IconCategory
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider
 */
package com.jiuiqi.nr.unit.treebase.source.basedata;

import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeGroupField;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuiqi.nr.unit.treebase.source.basedata.IGroupDataRow;
import com.jiuiqi.nr.unit.treebase.source.basedata.IGroupNodeDataBuilder;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.nodeicon.IconCategory;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider;
import java.util.List;

public class GroupNodeDataBuilder
implements IGroupNodeDataBuilder {
    private static String level1IconKey = "level1GroupIcon";
    private static String level2IconKey = "level2GroupIcon";
    private String level1GroupKey;
    private String level2GroupKey;
    private IBaseNodeData actionNode;
    private IconSourceProvider iconProvider;
    private IUnitTreeNodeBuilder dimNodeBuilder;

    public GroupNodeDataBuilder(IUnitTreeNodeBuilder dimNodeBuilder, IconSourceProvider iconProvider, UnitTreeGroupField groupField, IBaseNodeData actionNode) {
        this.dimNodeBuilder = dimNodeBuilder;
        this.iconProvider = iconProvider;
        this.actionNode = actionNode;
        this.init(groupField);
    }

    @Override
    public void beforeCreateITreeNode(List<IEntityRow> rows) {
        this.dimNodeBuilder.beforeCreateITreeNode(rows);
    }

    @Override
    public ITree<IBaseNodeData> buildTreeNode(IEntityRow row) {
        ITree<IBaseNodeData> node = this.dimNodeBuilder.buildTreeNode(row);
        node.setSelected(node.getKey().equals(this.actionNode.getKey()));
        ((IBaseNodeData)node.getData()).put("nodeType", (Object)"node@Dim");
        return node;
    }

    @Override
    public ITree<IBaseNodeData> buildTreeNode(IGroupDataRow row) {
        if ("node@Dim".equals(row.getRowType())) {
            return this.buildTreeNode(row.getData());
        }
        IBaseNodeData dataImpl = this.getDataImpl(row);
        ITree node = new ITree((INode)dataImpl);
        node.setIcons(this.getIcons(row.getReferEntityId()));
        node.setLeaf(row.isLeaf());
        node.setExpanded(true);
        node.setDisabled(true);
        return node;
    }

    private IBaseNodeData getDataImpl(IGroupDataRow dataRow) {
        BaseNodeDataImpl data = new BaseNodeDataImpl();
        data.setKey(dataRow.getEntityKeyData());
        data.setCode(dataRow.getCode());
        data.setTitle(dataRow.getTitle());
        data.put("nodeType", (Object)dataRow.getRowType());
        return data;
    }

    private String[] getIcons(String nodeType) {
        if (nodeType.equals(this.level1GroupKey)) {
            return new String[]{this.iconProvider.getIconKey(IconCategory.NODE_ICONS, level1IconKey)};
        }
        if (nodeType.equals(this.level2GroupKey)) {
            return new String[]{this.iconProvider.getIconKey(IconCategory.NODE_ICONS, level2IconKey)};
        }
        return new String[]{this.iconProvider.getDefaultIconKey()};
    }

    private void init(UnitTreeGroupField groupField) {
        UnitTreeGroupField parentGroupField = groupField.getParentGroupField();
        if (parentGroupField != null) {
            this.level2GroupKey = groupField.getReferEntityId();
            this.level1GroupKey = parentGroupField.getReferEntityId();
        } else {
            this.level1GroupKey = groupField.getReferEntityId();
        }
    }
}

