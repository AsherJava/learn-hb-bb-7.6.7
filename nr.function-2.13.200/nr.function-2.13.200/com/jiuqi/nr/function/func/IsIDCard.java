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
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.nr.function.util.IdCardUtil;
import java.util.List;

public final class IsIDCard
extends Function
implements IReportFunction {
    private static final long serialVersionUID = -2591161031362406492L;

    public IsIDCard() {
        this.parameters().add(new Parameter("idcard", 6, "\u8eab\u4efd\u8bc1\u53f7\u7801"));
    }

    public static final boolean callFunction(String idcard) {
        return idcard != null && IdCardUtil.isValidatedAllIdcard(idcard);
    }

    public static final boolean isNullResult(boolean idcard) {
        return idcard;
    }

    public String name() {
        return "IsIDCard";
    }

    public String title() {
        return "\u662f\u5408\u6cd5\u8eab\u4efd\u8bc1";
    }

    protected void toExplain(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        parameters.get(0).interpret(context, buffer, Language.EXPLAIN, info);
        buffer.append("\u662f\u5408\u6cd5\u8eab\u4efd\u8bc1\u53f7");
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u4fe1\u606f\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object p0 = parameters.get(0).evaluate(context);
        if (p0 == null) {
            return false;
        }
        String idcard = (String)p0;
        return IdCardUtil.isValidatedAllIdcard(idcard);
    }

    public boolean support(Language lang) {
        return lang == Language.JavaScript || super.support(lang);
    }
}

