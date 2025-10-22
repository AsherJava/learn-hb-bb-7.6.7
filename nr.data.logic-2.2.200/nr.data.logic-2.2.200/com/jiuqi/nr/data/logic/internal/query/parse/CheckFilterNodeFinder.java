/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.common.DataDefinitionsCache
 *  com.jiuqi.nvwa.dataengine.common.QueryContext
 *  com.jiuqi.nvwa.dataengine.parse.ColumnNodeFinder
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.data.logic.internal.query.parse;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.common.DataDefinitionsCache;
import com.jiuqi.nvwa.dataengine.common.QueryContext;
import com.jiuqi.nvwa.dataengine.parse.ColumnNodeFinder;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public class CheckFilterNodeFinder
extends ColumnNodeFinder {
    private String dwGroupName;

    protected IASTNode find(QueryContext qContext, Token token, String groupName, String refName) throws DynamicNodeException {
        if (groupName == null) {
            groupName = this.dwGroupName;
        }
        groupName = groupName.toUpperCase();
        refName = refName.toUpperCase();
        try {
            DataAccessContext accessContext = qContext.getAccessContext();
            DataDefinitionsCache dataDefinitionsCache = accessContext.getDataDefinitionsCache();
            ColumnModelDefine columnModel = dataDefinitionsCache.parseSearchColumn(groupName, refName, qContext.getMainTableName());
            if (columnModel != null) {
                return CheckFilterNodeFinder.createNode((DataAccessContext)accessContext, (ColumnModelDefine)columnModel);
            }
            columnModel = dataDefinitionsCache.parseSearchColumn(this.dwGroupName, refName, this.dwGroupName);
            if (columnModel != null) {
                return CheckFilterNodeFinder.createNode((DataAccessContext)accessContext, (ColumnModelDefine)columnModel);
            }
        }
        catch (Exception e) {
            throw new DynamicNodeException(e.getMessage(), (Throwable)e);
        }
        return null;
    }

    public void setDwGroupName(String dwGroupName) {
        this.dwGroupName = dwGroupName;
    }
}

