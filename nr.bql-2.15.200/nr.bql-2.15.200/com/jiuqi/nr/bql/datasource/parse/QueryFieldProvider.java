/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.model.FieldInfo
 *  com.jiuqi.bi.adhoc.model.TableInfo
 *  com.jiuqi.bi.adhoc.model.TableType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.nr.bql.datasource.parse;

import com.jiuqi.bi.adhoc.model.FieldInfo;
import com.jiuqi.bi.adhoc.model.TableInfo;
import com.jiuqi.bi.adhoc.model.TableType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.bql.datasource.QueryContext;
import com.jiuqi.nr.bql.datasource.parse.DataFieldNode;
import java.util.List;

public class QueryFieldProvider
implements IDynamicNodeProvider {
    public IASTNode find(IContext context, Token token, String refName) throws DynamicNodeException {
        QueryContext qContext = (QueryContext)context;
        return this.find(context, token, qContext.getDefaultTableName(), refName);
    }

    public IASTNode find(IContext context, Token token, List<String> objPath) throws DynamicNodeException {
        String itemName = null;
        String groupName = null;
        if (objPath.size() == 1) {
            itemName = objPath.get(0);
            return this.find(context, token, itemName);
        }
        if (objPath.size() == 2) {
            groupName = objPath.get(0);
            itemName = objPath.get(1);
            return this.find(context, token, groupName, itemName);
        }
        return null;
    }

    private IASTNode find(IContext context, Token token, String groupName, String itemName) {
        QueryContext qContext = (QueryContext)context;
        TableInfo tableInfo = qContext.getTableInfoMap().get(groupName);
        if (tableInfo == null) {
            return null;
        }
        FieldInfo fieldInfo = tableInfo.findField(itemName);
        String tableName = tableInfo.getPhysicalTableName();
        if (tableInfo.getType() == TableType.DIM) {
            tableName = qContext.getTableCode(tableInfo);
        }
        return new DataFieldNode(fieldInfo, tableName);
    }

    public IASTNode findRestrict(IContext context, Token token, List<String> arg2, List<IASTNode> arg3) throws DynamicNodeException {
        return null;
    }

    public IASTNode findSpec(IContext context, Token token, String arg2, String arg3) throws DynamicNodeException {
        return null;
    }
}

