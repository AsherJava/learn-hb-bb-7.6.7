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

public final class ChineseDigitUnit
extends Function
implements IReportFunction {
    private static final long serialVersionUID = 1873001492629793293L;

    public ChineseDigitUnit() {
        this.parameters().add(new Parameter("digitValue", 3, "\u5355\u4f4d\u6570\u503c"));
    }

    public static final String callFunction(double value) {
        return ChineseNumberFormat.toChineseDigitUnit((double)value);
    }

    public static final String callFunction(BigDecimal value) {
        return ChineseNumberFormat.toChineseDigitUnit((double)value.doubleValue());
    }

    public static final boolean isNullResult(boolean value) {
        return value;
    }

    public String name() {
        return "ChineseDigitUnit";
    }

    public String title() {
        return "\u8ba1\u7b97\u4e2d\u6587\u6570\u503c\u5355\u4f4d";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u6587\u672c\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object p0 = parameters.get(0).evaluate(context);
        if (p0 == null) {
            return null;
        }
        String result = null;
        if (p0 instanceof Integer) {
            result = ChineseNumberFormat.toChineseDigitUnit((long)((Long)p0));
        } else if (p0 instanceof Number) {
            result = ChineseNumberFormat.toChineseDigitUnit((double)((Number)p0).doubleValue());
        }
        return result;
    }
}

