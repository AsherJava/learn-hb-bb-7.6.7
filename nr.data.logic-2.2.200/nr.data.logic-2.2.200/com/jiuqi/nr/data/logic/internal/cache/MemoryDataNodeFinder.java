/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.query.result.ColumnInfo
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.node.DynamicDataNodeFinder
 */
package com.jiuqi.nr.data.logic.internal.cache;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.query.result.ColumnInfo;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.node.DynamicDataNodeFinder;
import com.jiuqi.nr.data.logic.internal.cache.MemoryDataContext;
import com.jiuqi.nr.data.logic.internal.cache.MemoryDataNode;
import java.util.List;

public class MemoryDataNodeFinder
extends DynamicDataNodeFinder {
    public IASTNode find(IContext context, Token token, String refName) throws DynamicNodeException {
        MemoryDataContext memoryDataContext = (MemoryDataContext)context;
        return this.find(memoryDataContext, token, memoryDataContext.getDefaultTableName(), refName);
    }

    public IASTNode find(IContext context, Token token, List<String> objPath) throws DynamicNodeException {
        MemoryDataContext memoryDataContext = (MemoryDataContext)context;
        if (objPath.size() == 1) {
            String itemName = objPath.get(0);
            return this.find((IContext)memoryDataContext, token, itemName);
        }
        if (objPath.size() == 2) {
            String groupName = objPath.get(0);
            String itemName = objPath.get(1);
            return this.find(memoryDataContext, token, groupName, itemName);
        }
        return null;
    }

    private IASTNode find(MemoryDataContext memoryDataContext, Token token, String groupName, String itemName) {
        String defaultTableName = memoryDataContext.getDefaultTableName();
        String columnName = groupName == null || defaultTableName.equals(groupName) ? itemName : (memoryDataContext.getDwTableName().equals(groupName) ? ("CODE".equals(itemName) ? "MDCODE" : itemName) : groupName);
        Column column = memoryDataContext.getMetadata().find(columnName);
        if (column == null) {
            return null;
        }
        return new MemoryDataNode((Column<ColumnInfo>)column);
    }

    public IASTNode findSpecial(IContext context, Token token, String refName) throws DynamicNodeException {
        return this.find(context, token, refName);
    }

    public IASTNode findRestrict(IContext context, Token token, List<String> objPath, List<IASTNode> restrictItems) throws DynamicNodeException {
        return null;
    }

    public IASTNode findSpec(IContext context, Token token, String refName, String spec) throws DynamicNodeException {
        return null;
    }
}

