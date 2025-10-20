/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.IMultiInstance
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.parser.function;

import com.jiuqi.bi.quickreport.engine.build.CellValue;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataNode;
import com.jiuqi.bi.quickreport.engine.build.fragment.ExpandingCalcCell;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFormulaInfo;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorksheet;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.IMultiInstance;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.Collection;
import java.util.List;

public abstract class Q_ExpandingFunction
extends Function
implements IMultiInstance {
    private static final long serialVersionUID = 1L;
    private CellBindingInfo masterCell;

    public Q_ExpandingFunction(String paramName, String paramTitle) {
        this.parameters().add(new Parameter(paramName, 0, paramTitle));
    }

    public String category() {
        return "\u5206\u6790\u8868\u51fd\u6570";
    }

    public boolean support(Language lang) {
        return lang == Language.FORMULA || lang == Language.EXPLAIN;
    }

    protected void toFormula(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        if (info instanceof DSFormulaInfo) {
            throw new InterpretException("\u51fd\u6570" + this.name() + "()\u65e0\u6cd5\u7528\u4e8e\u6570\u636e\u5904\u7406");
        }
        super.toFormula(context, parameters, buffer, info);
    }

    public CellBindingInfo getMasterCell() {
        return this.masterCell;
    }

    public void setMasterCell(CellBindingInfo masterCell) {
        this.masterCell = masterCell;
    }

    protected AxisDataNode findMasterData(ReportContext context) throws SyntaxException {
        if (this.masterCell == null) {
            throw new SyntaxException("\u5f53\u524d\u73af\u5883\u65e0\u6cd5\u8ba1\u7b97\u6d6e\u52a8\u533a\u57df\u4fe1\u606f\uff0c\u4ec5\u5728\u6d6e\u52a8\u533a\u57df\u4e2d\u53ef\u4ee5\u4f7f\u7528\u8be5\u51fd\u6570\u3002");
        }
        if (context.getCurrentCell() == null) {
            return this.locateMasterData(context.getCurrentRestrictions());
        }
        EngineWorksheet worksheet = (EngineWorksheet)context.getWorkbook().activeWorksheet(context);
        Position curPos = context.getCurrentCell().getPosition();
        Object cellInfo = worksheet.getResultGrid().getGridData().getObj(curPos.col(), curPos.row());
        if (cellInfo instanceof ExpandingCalcCell) {
            return this.locateMasterData(((ExpandingCalcCell)cellInfo).getRestrictions());
        }
        if (cellInfo instanceof CellValue) {
            return this.locateMasterData(((CellValue)cellInfo)._restrictions);
        }
        return null;
    }

    private AxisDataNode locateMasterData(Collection<AxisDataNode> datas) {
        for (AxisDataNode data : datas) {
            if (data.getRegion() == null || data.getRegion().getMasterCell() != this.masterCell) continue;
            return data;
        }
        return null;
    }
}

