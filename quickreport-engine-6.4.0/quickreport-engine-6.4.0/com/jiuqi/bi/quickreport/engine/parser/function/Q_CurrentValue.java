/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.grid.GridHelper
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.parser.function;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.build.CellValue;
import com.jiuqi.bi.quickreport.engine.context.EvalCellInfo;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorksheet;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.grid.GridHelper;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.List;

public final class Q_CurrentValue
extends Function {
    private static final long serialVersionUID = 1L;
    public static final String FUNCTION_NAME = "Q_CurrentValue";

    public String name() {
        return FUNCTION_NAME;
    }

    public String[] aliases() {
        return new String[]{"Q_CurValue"};
    }

    public String title() {
        return "\u53d6\u5f53\u524d\u5355\u5143\u683c\u503c";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 0;
    }

    public String category() {
        return "\u5206\u6790\u8868\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return this.getCurrentValue((ReportContext)context);
    }

    private Object getCurrentValue(ReportContext context) throws SyntaxException {
        EvalCellInfo cellInfo = context.getCurrentCell();
        if (cellInfo == null) {
            throw new SyntaxException("\u65e0\u6cd5\u83b7\u53d6\u5f53\u524d\u5904\u7406\u5355\u5143\u683c\u4fe1\u606f\u3002");
        }
        EngineWorksheet worksheet = (EngineWorksheet)context.getWorkbook().find(context, cellInfo.getSheetName());
        if (worksheet == null) {
            throw new SyntaxException("\u67e5\u627e\u9875\u7b7e\u201c" + cellInfo.getSheetName() + "\u201d\u4e0d\u5b58\u5728\u3002");
        }
        GridData grid = worksheet.getResultGrid().getGridData();
        CellValue cellValue = (CellValue)grid.getObj(cellInfo.getPosition().col(), cellInfo.getPosition().row());
        if (cellValue == null) {
            return GridHelper.readCellValue((GridData)grid, (int)cellInfo.getPosition().col(), (int)cellInfo.getPosition().row());
        }
        return cellValue.value;
    }
}

