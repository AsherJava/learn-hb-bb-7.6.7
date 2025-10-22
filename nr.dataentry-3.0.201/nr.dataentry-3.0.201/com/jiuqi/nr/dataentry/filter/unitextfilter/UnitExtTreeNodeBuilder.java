/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.dataentity_ext.dto.EntityDataType
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.itreebase.node.BaseNodeDataImpl
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider
 *  com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig
 */
package com.jiuqi.nr.dataentry.filter.unitextfilter;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.provider.IUnitTreeEntityRowProvider;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeNodeBuilder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.dataentity_ext.dto.EntityDataType;
import com.jiuqi.nr.dataentry.filter.unitextfilter.EntityRowExt;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.itreebase.node.BaseNodeDataImpl;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider;
import com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig;
import java.util.List;

public class UnitExtTreeNodeBuilder
implements IUnitTreeNodeBuilder {
    protected IconSourceProvider iconProvider;
    protected IUnitTreeEntityRowProvider dimRowProvider;
    protected UnitTreeSystemConfig unitTreeSystemConfig;
    protected List<String> checklist;
    protected IUnitTreeContext ctx;
    public static final String KEY_OF_BBLX_CODE = "bblxCode";
    protected Boolean firstNode;

    public UnitExtTreeNodeBuilder(IUnitTreeEntityRowProvider dimRowProvider, IconSourceProvider iconProvider, List<String> checklist, IUnitTreeContext ctx) {
        this.iconProvider = iconProvider;
        this.dimRowProvider = dimRowProvider;
        this.unitTreeSystemConfig = (UnitTreeSystemConfig)SpringBeanUtils.getBean(UnitTreeSystemConfig.class);
        this.checklist = checklist;
        this.firstNode = true;
        this.ctx = ctx;
    }

    public ITree<IBaseNodeData> buildTreeNode(IEntityRow row) {
        EntityRowExt entityRowExt;
        IBaseNodeData data = this.implData(row);
        ITree node = new ITree((INode)data);
        node.setLeaf(row.isLeaf());
        node.setIcons(new String[]{this.iconProvider.getDefaultIconKey()});
        if (this.checklist != null && this.checklist.contains(node.getKey())) {
            node.setChecked(true);
        }
        if (this.firstNode.booleanValue()) {
            node.setSelected(true);
            this.firstNode = false;
        }
        if ((entityRowExt = (EntityRowExt)row).getType() != null && entityRowExt.getType().getCode() == EntityDataType.EXIST_DISABLE.getCode()) {
            node.setDisabled(true);
        } else if (entityRowExt.getType() != null && entityRowExt.getType().getCode() == EntityDataType.NOT_EXIST.getCode() && (!this.ctx.getCustomVariable().toMap().containsKey("showNewUnit") || this.ctx.getCustomVariable().toMap().get("showNewUnit").equals("1"))) {
            node.setTitle(node.getTitle() + "[\u65b0\u589e]");
        }
        return node;
    }

    protected IBaseNodeData implData(IEntityRow row) {
        BaseNodeDataImpl data = new BaseNodeDataImpl();
        data.setPath(row.getParentsEntityKeyDataPath());
        data.setKey(row.getEntityKeyData());
        data.setCode(row.getCode());
        data.setTitle(row.getTitle());
        return data;
    }
}

