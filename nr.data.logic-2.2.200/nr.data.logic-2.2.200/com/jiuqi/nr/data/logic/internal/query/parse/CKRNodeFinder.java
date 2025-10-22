/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.common.DataDefinitionsCache
 *  com.jiuqi.nvwa.dataengine.common.DimensionValueSet
 *  com.jiuqi.nvwa.dataengine.common.QueryField
 *  com.jiuqi.nvwa.dataengine.parse.RestrictColumnNode
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.data.logic.internal.query.parse;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.nr.data.logic.internal.query.parse.CKRNode;
import com.jiuqi.nr.data.logic.internal.query.parse.CKRQueryContext;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.common.DataDefinitionsCache;
import com.jiuqi.nvwa.dataengine.common.DimensionValueSet;
import com.jiuqi.nvwa.dataengine.common.QueryField;
import com.jiuqi.nvwa.dataengine.parse.RestrictColumnNode;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.List;

public class CKRNodeFinder
implements IReportDynamicNodeProvider {
    public IASTNode find(IContext context, Token token, String refName) throws DynamicNodeException {
        CKRQueryContext qContext = (CKRQueryContext)context;
        return this.find(qContext, token, qContext.getMainTableName(), refName);
    }

    protected IASTNode find(CKRQueryContext qContext, Token token, String groupName, String refName) throws DynamicNodeException {
        String dwGroupName = qContext.getDwGroupName();
        if (groupName == null) {
            groupName = dwGroupName;
        }
        groupName = groupName.toUpperCase();
        refName = refName.toUpperCase();
        try {
            DataDefinitionsCache dataDefinitionsCache = qContext.getDataDefinitionsCache();
            ColumnModelDefine columnModel = dataDefinitionsCache.parseSearchColumn(groupName, refName, qContext.getMainTableName());
            if (columnModel != null) {
                return CKRNodeFinder.createNode(columnModel);
            }
            columnModel = dataDefinitionsCache.parseSearchColumn(dwGroupName, refName, dwGroupName);
            if (columnModel != null) {
                return CKRNodeFinder.createNode(columnModel);
            }
        }
        catch (Exception e) {
            throw new DynamicNodeException(e.getMessage(), (Throwable)e);
        }
        return null;
    }

    public static CKRNode createNode(ColumnModelDefine columnModel) throws DynamicNodeException {
        if (columnModel != null) {
            return new CKRNode(columnModel);
        }
        return null;
    }

    public IASTNode find(IContext context, Token token, List<String> objPath) throws DynamicNodeException {
        CKRQueryContext qContext = (CKRQueryContext)context;
        if (objPath.size() == 1) {
            String itemName = objPath.get(0);
            return this.find((IContext)qContext, token, itemName);
        }
        if (objPath.size() == 2) {
            String groupName = objPath.get(0);
            String itemName = objPath.get(1);
            return this.find(qContext, token, groupName, itemName);
        }
        return null;
    }

    public IASTNode findSpec(IContext iContext, Token token, String s, String s1) throws DynamicNodeException {
        return null;
    }

    public IASTNode findRestrict(IContext context, Token token, List<String> objPath, List<IASTNode> restrictItems) throws DynamicNodeException {
        return null;
    }

    public IASTNode findSpecial(IContext context, Token token, String refName) throws DynamicNodeException {
        return this.find(context, token, refName);
    }

    public static RestrictColumnNode createRestrictNode(DataAccessContext aContext, ColumnModelDefine columnModel, PeriodModifier modifier, DimensionValueSet dimensionRestriction) throws DynamicNodeException {
        if (columnModel != null) {
            try {
                DataDefinitionsCache dataDefinitionsCache = aContext.getDataDefinitionsCache();
                QueryField queryField = dataDefinitionsCache.extractQueryField(aContext, columnModel, modifier, dimensionRestriction);
                return new RestrictColumnNode(queryField);
            }
            catch (Exception e) {
                throw new DynamicNodeException((Throwable)e);
            }
        }
        return null;
    }
}

