/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.ChineseNumberFormat
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.ChineseNumberFormat;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import java.math.BigDecimal;
import java.util.List;

public final class ChineseStr
extends Function
implements IReportFunction {
    private static final long serialVersionUID = 4479523540904032729L;

    public ChineseStr() {
        this.parameters().add(new Parameter("value", 3, "\u6570\u503c"));
        this.parameters().add(new Parameter("capital", 1, "\u662f\u5426\u5927\u5199"));
    }

    public static final String callFunction(double value, boolean useCaptical) {
        return new ChineseNumberFormat(useCaptical).format(value);
    }

    public static final String callFunction(BigDecimal value, boolean useCaptical) {
        return new ChineseNumberFormat(useCaptical).format(value.doubleValue());
    }

    public static final boolean isNullResult(boolean value, boolean useCaptical) {
        return value;
    }

    public String name() {
        return "ChineseStr";
    }

    public String title() {
        return "\u4e2d\u6587\u6570\u503c";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u6587\u672c\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object p0 = parameters.get(0).evaluate(context);
        Object p1 = parameters.get(1).evaluate(context);
        if (p0 == null) {
            return null;
        }
        boolean capital = p1 == null ? true : (Boolean)p1;
        ChineseNumberFormat fmt = new ChineseNumberFormat(capital);
        if (p0 instanceof Number) {
            return fmt.format((Object)((Number)p0));
        }
        return null;
    }
}

