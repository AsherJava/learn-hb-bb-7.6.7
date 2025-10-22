/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.node.builder.BBLXUnitNodeBuilder
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider
 */
package com.jiuqi.nr.batch.summary.service.ext.unittree;

import com.jiuiqi.nr.unit.treebase.node.builder.BBLXUnitNodeBuilder;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider;

public class FSTreeNodeBuilder
extends BBLXUnitNodeBuilder {
    private FormSchemeDefine formSchemeDefine;

    public FSTreeNodeBuilder(FormSchemeDefine formSchemeDefine, IUnitTreeNodeBuilder baseNodeBuilder, IconSourceProvider iconProvider, IEntityRefer referBBLXEntity) {
        super(baseNodeBuilder, iconProvider, referBBLXEntity);
        this.formSchemeDefine = formSchemeDefine;
    }

    public ITree<IBaseNodeData> buildTreeNode(IEntityRow row) {
        ITree node = super.buildTreeNode(row);
        ((IBaseNodeData)node.getData()).put("batchGatherSchemeCode", (Object)this.formSchemeDefine.getKey());
        ((IBaseNodeData)node.getData()).put("nodeType", (Object)"UNIT");
        node.setDisabled(this.isGroupNode((IBaseNodeData)node.getData()));
        return node;
    }

    private boolean isGroupNode(IBaseNodeData data) {
        return this.formSchemeDefine.getKey().equals(data.getKey());
    }
}

