/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeNode
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.core.VisitorResult
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 */
package com.jiuqi.nr.datascheme.internal.tree.des;

import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.core.DataSchemeNode;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.core.VisitorResult;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.internal.tree.TypeDeter;
import com.jiuqi.nr.entity.model.IEntityDefine;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public abstract class BaseBuildTree {
    protected static final String[] DIM_PUB = new String[]{"DIM_PUB", "DIM_PUB", "\u516c\u5171\u7ef4\u5ea6\u5206\u7ec4"};
    protected static final int TABLE = NodeType.FMDM_TABLE.getValue() | NodeType.MUL_DIM_TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.TABLE.getValue() | NodeType.ACCOUNT_TABLE.getValue() | NodeType.MD_INFO.getValue();
    protected static final int FIELD = NodeType.FIELD_ZB.getValue() | NodeType.FIELD.getValue() | NodeType.TABLE_DIM.getValue();

    protected abstract IEntityDefine getEntityDefineByDimKey(String var1);

    public VisitorResult deter(int type, NodeType nodeType) {
        if (NodeType.SCHEME_GROUP.getValue() >= nodeType.getValue() && (type & NodeType.SCHEME.getValue()) != 0) {
            return VisitorResult.CONTINUE;
        }
        if (NodeType.SCHEME.getValue() >= nodeType.getValue() && (type & (NodeType.GROUP.getValue() | NodeType.TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.MD_INFO.getValue() | NodeType.MUL_DIM_TABLE.getValue() | NodeType.ACCOUNT_TABLE.getValue())) != 0) {
            return VisitorResult.CONTINUE;
        }
        if (NodeType.GROUP.getValue() >= nodeType.getValue() && (type & (NodeType.TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.MUL_DIM_TABLE.getValue() | NodeType.MD_INFO.getValue() | NodeType.ACCOUNT_TABLE.getValue())) != 0) {
            return VisitorResult.CONTINUE;
        }
        if (NodeType.MUL_DIM_TABLE.getValue() >= nodeType.getValue() && (type & (NodeType.FIELD_ZB.getValue() | NodeType.FIELD.getValue())) != 0) {
            return VisitorResult.CONTINUE;
        }
        return null;
    }

    public <DM extends DesignDataDimension> void dimNode(SchemeNode<Void> ele, List<DM> dims, List<ITree<DataSchemeNode>> value) {
        DesignDataDimension unit = null;
        ArrayList<DesignDataDimension> scopes = new ArrayList<DesignDataDimension>();
        for (DesignDataDimension dim : dims) {
            DimensionType dimensionType = dim.getDimensionType();
            if (DimensionType.UNIT == dimensionType) {
                unit = dim;
                continue;
            }
            if (DimensionType.UNIT_SCOPE != dimensionType) continue;
            scopes.add(dim);
        }
        if (unit == null) {
            throw new SchemeDataException("\u672a\u627e\u5230\u4e3b\u7ef4\u5ea6");
        }
        DataSchemeNodeDTO groupNode = new DataSchemeNodeDTO(DIM_PUB[0], DIM_PUB[1], DIM_PUB[2], NodeType.GROUP.getValue(), ele.getKey());
        ITree group = new ITree((INode)groupNode);
        group.setIcons(NodeIconGetter.getIconByType(NodeType.GROUP));
        value.add((ITree<DataSchemeNode>)group);
        ArrayList<ITree<DataSchemeNode>> children = new ArrayList<ITree<DataSchemeNode>>();
        group.setChildren(children);
        if (scopes.isEmpty()) {
            this.buildUnit(ele, unit, children);
        } else {
            for (DesignDataDimension scope : scopes) {
                this.buildUnit(ele, scope, children);
            }
        }
    }

    private void buildUnit(SchemeNode<Void> ele, DesignDataDimension unit, List<ITree<DataSchemeNode>> children) {
        String dimKey = unit.getDimKey();
        IEntityDefine define = this.getEntityDefineByDimKey(dimKey);
        if (define == null) {
            return;
        }
        DataSchemeNodeDTO dimNode0 = new DataSchemeNodeDTO(dimKey, define.getCode(), define.getTitle(), NodeType.FMDM_TABLE.getValue(), ele.getKey());
        ITree dimNode = new ITree((INode)dimNode0);
        dimNode.setIcons(NodeIconGetter.getIconByType(dimNode0.getType()));
        dimNode.setLeaf(true);
        children.add((ITree<DataSchemeNode>)dimNode);
    }

    protected boolean canRead(DataScheme scheme) {
        return TypeDeter.canRead(scheme);
    }

    protected boolean canRead(DataGroup group) {
        return TypeDeter.canRead(group);
    }
}

