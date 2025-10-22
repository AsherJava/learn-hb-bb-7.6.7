/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.np.dataengine.definitions.DataDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.node.ExpressionUtils
 *  com.jiuqi.np.dataengine.node.RestrictInfo
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.data.engine.summary.parse;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.np.dataengine.definitions.DataDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.node.RestrictInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.data.engine.summary.parse.SumContext;
import com.jiuqi.nr.data.engine.summary.parse.SumNode;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.List;

public class SumNodeProvider
implements IReportDynamicNodeProvider {
    public IASTNode find(IContext paramIContext, Token paramToken, String fieldCode) throws DynamicNodeException {
        SumContext context = (SumContext)paramIContext;
        return this.findSpec(context, paramToken, context.getMainTable(), fieldCode, null);
    }

    public IASTNode findSpecial(IContext context, Token token, String fieldCode) throws DynamicNodeException {
        SumContext sumContext = (SumContext)context;
        for (String tableCode : sumContext.getEntityTableNames()) {
            IASTNode node = this.findSpec(sumContext, token, tableCode, fieldCode, null);
            if (node == null) continue;
            return node;
        }
        return this.find(context, token, fieldCode);
    }

    public IASTNode find(IContext paramIContext, Token paramToken, List<String> paramList) throws DynamicNodeException {
        if (paramList.size() == 2) {
            String tableCode = paramList.get(0);
            String fieldCode = paramList.get(1);
            return this.findSpec(paramIContext, paramToken, tableCode, fieldCode, null);
        }
        return null;
    }

    public IASTNode findSpec(IContext context, Token token, String tableCode, String fieldCode, List<IASTNode> specs) throws DynamicNodeException {
        SumContext sumContext = (SumContext)context;
        try {
            DataDefinitionsCache dataDefinitionsCache = sumContext.getExecutorContext().getCache().getDataDefinitionsCache();
            tableCode = tableCode.toUpperCase();
            fieldCode = fieldCode.toUpperCase();
            FieldDefine fieldDefine = dataDefinitionsCache.parseSearchField(tableCode, fieldCode, sumContext.getMainTable());
            if (fieldDefine != null) {
                DataModelDefinitionsCache dataModelDefinitionsCache = sumContext.getExecutorContext().getCache().getDataModelDefinitionsCache();
                ColumnModelDefine columnModel = dataModelDefinitionsCache.getColumnModel(fieldDefine);
                TableModelDefine tableModel = dataModelDefinitionsCache.getTableModel(columnModel);
                SumNode node = this.createNode(token, sumContext, columnModel, tableModel.getName());
                if (specs != null) {
                    QueryContext qContext = new QueryContext(sumContext.getExecutorContext(), null, null);
                    RestrictInfo restrictInfo = ExpressionUtils.parseRestrictInfo((QueryContext)qContext, null, null, specs);
                    if (restrictInfo.statKind > 0) {
                        node.setStatistic(restrictInfo.conditionNode, restrictInfo.statKind);
                    }
                }
                return node;
            }
        }
        catch (ParseException e) {
            throw new DynamicNodeException(e.getMessage(), (Throwable)e);
        }
        return null;
    }

    public IASTNode findRestrict(IContext paramIContext, Token paramToken, List<String> paramList, List<IASTNode> paramList1) throws DynamicNodeException {
        if (paramList.size() == 2) {
            String tableName = paramList.get(0);
            String fieldName = paramList.get(1);
            return this.findSpec(paramIContext, paramToken, tableName, fieldName, paramList1);
        }
        return null;
    }

    private SumNode createNode(Token paramToken, SumContext context, ColumnModelDefine columnModel, String tableName) {
        SumNode node = new SumNode(paramToken, columnModel);
        context.addNode(node, tableName);
        return node;
    }

    public IASTNode findSpec(IContext arg0, Token arg1, String arg2, String arg3) throws DynamicNodeException {
        return null;
    }
}

