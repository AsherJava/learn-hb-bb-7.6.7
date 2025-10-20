/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.format.IDataFormator
 *  com.jiuqi.bi.syntax.format.IFormatable
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.parser.function;

import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataNode;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFormulaInfo;
import com.jiuqi.bi.quickreport.engine.parser.function.Q_ExpandingFunction;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.format.IDataFormator;
import com.jiuqi.bi.syntax.format.IFormatable;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;
import java.text.DecimalFormat;
import java.util.List;

public final class Q_RankOf
extends Q_ExpandingFunction
implements IFormatable {
    private static final long serialVersionUID = 1L;

    public Q_RankOf() {
        super("rankItem", "\u8981\u6392\u540d\u7684\u7ef4\u5ea6\u5b57\u6bb5\u6216\u4e3b\u63a7\u5355\u5143\u683c");
    }

    public String name() {
        return "Q_RankOf";
    }

    public String title() {
        return "\u83b7\u53d6\u5df2\u6392\u5e8f\u4e3b\u63a7\u5355\u5143\u683c\u6216\u7ef4\u5ea6\u5bf9\u5e94\u7684\u6392\u540d\u503c";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 3;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        AxisDataNode rankData = this.findMasterData((ReportContext)context);
        if (rankData == null || rankData.getRank() < 1) {
            throw new SyntaxException("\u7a0b\u5e8f\u5904\u7406\u9519\u8bef\uff0c\u65e0\u6cd5\u8ba1\u7b97\u6392\u540d\u503c");
        }
        return rankData.getRank();
    }

    @Override
    protected void toFormula(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        if (info instanceof DSFormulaInfo) {
            throw new InterpretException("\u6392\u540d\u51fd\u6570\u65e0\u6cd5\u5e94\u7528\u4e8e\u8fc7\u6ee4\u5904\u7406\u3002");
        }
        super.toFormula(context, parameters, buffer, info);
    }

    public IDataFormator getDataFormator(IContext context) throws SyntaxException {
        return cxt -> new DecimalFormat("#,##0");
    }
}

