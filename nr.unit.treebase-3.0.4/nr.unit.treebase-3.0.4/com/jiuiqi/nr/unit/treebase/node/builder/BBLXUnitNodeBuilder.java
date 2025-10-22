/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.nodeicon.IconCategory
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider
 */
package com.jiuiqi.nr.unit.treebase.node.builder;

import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.nodeicon.IconCategory;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider;

public class BBLXUnitNodeBuilder
implements IUnitTreeNodeBuilder {
    public static final String KEY_OF_BBLX_CODE = "bblxCode";
    protected IconSourceProvider iconProvider;
    protected IUnitTreeNodeBuilder baseNodeBuilder;
    protected IEntityRefer referBBLXEntity;

    public BBLXUnitNodeBuilder(IUnitTreeNodeBuilder baseNodeBuilder, IconSourceProvider iconProvider, IEntityRefer referBBLXEntity) {
        this.baseNodeBuilder = baseNodeBuilder;
        this.iconProvider = iconProvider;
        this.referBBLXEntity = referBBLXEntity;
    }

    @Override
    public ITree<IBaseNodeData> buildTreeNode(IEntityRow row) {
        ITree<IBaseNodeData> node = this.baseNodeBuilder.buildTreeNode(row);
        IBaseNodeData data = (IBaseNodeData)node.getData();
        String codeOfBBLX = row.getAsString(this.referBBLXEntity.getOwnField());
        data.put(KEY_OF_BBLX_CODE, (Object)codeOfBBLX);
        node.setIcons(new String[]{this.iconProvider.getIconKey(IconCategory.NODE_ICONS, codeOfBBLX)});
        return node;
    }
}

