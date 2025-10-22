/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.cell.CellExcpetion
 *  com.jiuqi.bi.syntax.cell.CompatiblePosition
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.cell.Region
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.dafafill.formula;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.cell.CellExcpetion;
import com.jiuqi.bi.syntax.cell.CompatiblePosition;
import com.jiuqi.bi.syntax.cell.IWorksheet;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.cell.Region;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dafafill.formula.DatafillCellNode;
import com.jiuqi.nr.dafafill.formula.DatafillFormulaContext;
import com.jiuqi.nr.dafafill.model.QueryField;
import java.util.List;
import java.util.Map;

public class DataFillWorksheet
implements IWorksheet {
    public String name;

    public IASTNode findCell(IContext var1, Token token, Position var3, int var4) throws CellExcpetion {
        boolean formulaCheck;
        DatafillFormulaContext context = (DatafillFormulaContext)var1;
        String text = token.text();
        QueryField queryField = context.getFullQueryFieldMap().get(text);
        if (null == queryField) {
            queryField = context.getQueryFieldMap().get(text);
        }
        if (formulaCheck = context.isFormulaCheck()) {
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

    public IASTNode findCell(IContext var1, Token var2, CompatiblePosition var3, int var4, List<IASTNode> var5) throws CellExcpetion {
        throw new CellExcpetion("\u4e0d\u652f\u6301\u884c\u5217\u5750\u6807\u6a21\u5f0f\u3002");
    }

    public IASTNode findRegion(IContext var1, Token var2, Region var3, int var4) throws CellExcpetion {
        throw new CellExcpetion("\u4e0d\u652f\u6301\u533a\u57df\u6a21\u5f0f\u3002");
    }

    public String name() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

