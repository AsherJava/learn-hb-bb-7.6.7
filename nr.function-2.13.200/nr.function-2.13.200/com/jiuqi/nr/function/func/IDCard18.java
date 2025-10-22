/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.function.IJavaScriptFunction
 *  com.jiuqi.bi.util.IDCard
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.function.IJavaScriptFunction;
import com.jiuqi.bi.util.IDCard;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import java.util.List;

public final class IDCard18
extends Function
implements IJavaScriptFunction,
IReportFunction {
    private static final long serialVersionUID = 7302875836177217284L;

    public IDCard18() {
        this.parameters().add(new Parameter("idcard", 6, "\u8eab\u4efd\u8bc1\u53f7\u7801"));
    }

    public static final String callFunction(String idcard) {
        return IDCard.IDCard18((String)idcard);
    }

    public static final boolean isNullResult(boolean idcard) {
        return idcard;
    }

    public String name() {
        return "IDCard18";
    }

    public String title() {
        return "\u8f6c\u6362\u621018\u4f4d\u8eab\u4efd\u8bc1";
    }

    protected void toExplain(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append("\u5c06");
        parameters.get(0).interpret(context, buffer, Language.EXPLAIN, info);
        buffer.append("\u8eab\u4efd\u8bc1\u8f6c\u6362\u621018\u4f4d");
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u4fe1\u606f\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object p0 = parameters.get(0).evaluate(context);
        if (p0 == null) {
            return null;
        }
        String idcard = (String)p0;
        return IDCard.IDCard18((String)idcard);
    }

    public boolean support(Language lang) {
        return lang == Language.JavaScript || super.support(lang);
    }

    public String toScriptName() {
        return "IDCard";
    }
}

