/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.dafafill.formula;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dafafill.formula.DatafillCellNode;
import com.jiuqi.nr.dafafill.formula.DatafillFormulaContext;
import com.jiuqi.nr.dafafill.model.QueryField;
import java.util.List;
import java.util.Map;

public class DatafillDynamicNodeProvider
implements IDynamicNodeProvider {
    public IASTNode find(IContext var1, Token token, String refName) throws DynamicNodeException {
        DatafillFormulaContext context = (DatafillFormulaContext)var1;
        String text = token.text();
        QueryField queryField = context.getFullQueryFieldMap().get(text);
        if (null == queryField) {
            queryField = context.getQueryFieldMap().get(text);
        }
        boolean formulaCheck = context.isFormulaCheck();
        if (queryField == null) {
            throw new DynamicNodeException(text + "\u672a\u627e\u5230\u5bf9\u5e94\u5df2\u9009\u5b57\u6bb5");
        }
        if (formulaCheck) {
            DatafillCellNode cellNode = new DatafillCellNode(token, context.getCurWorksheet(), new Position(1, 1), null, queryField);
            return cellNode;
        }
        Map<DimensionValueSet, Position> dimensionPositionMap = context.getDimensionPositionMap();
        DimensionValueSet rowColDimenson = context.getCellContext().getRowColDimenson();
        DimensionValueSet zbDimensionValueSet = new DimensionValueSet(rowColDimenson);
        zbDimensionValueSet.setValue("ZB", (Object)queryField.getId());
        Position p = dimensionPositionMap.get(zbDimensionValueSet);
        DatafillCellNode cellNode = new DatafillCellNode(token, context.getCurWorksheet(), p, zbDimensionValueSet, queryField);
        return cellNode;
    }

    public IASTNode find(IContext context, Token token, List<String> objPath) throws DynamicNodeException {
        return this.find(context, token, "");
    }

    public IASTNode findSpec(IContext context, Token token, String refName, String spec) throws DynamicNodeException {
        throw new DynamicNodeException("\u4e0d\u652f\u6301");
    }

    public IASTNode findRestrict(IContext context, Token token, List<String> objPath, List<IASTNode> restrictItems) throws DynamicNodeException {
        throw new DynamicNodeException("\u4e0d\u652f\u6301");
    }
}

