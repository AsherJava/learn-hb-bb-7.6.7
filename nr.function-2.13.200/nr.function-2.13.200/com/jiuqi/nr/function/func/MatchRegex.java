/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import java.util.List;

public final class MatchRegex
extends Function
implements IReportFunction {
    private static final long serialVersionUID = -6479446524002949358L;

    public MatchRegex() {
        this.parameters().add(new Parameter("text", 6, "\u5b57\u7b26\u4e32"));
        this.parameters().add(new Parameter("regexp", 6, "\u6b63\u5219\u8868\u8fbe\u5f0f"));
    }

    public static boolean matchRegex(String s, String regexp) {
        boolean match = false;
        if (s == null || s == null) {
            return false;
        }
        match = s.matches(regexp);
        return match;
    }

    public static final boolean callFunction(String text, String regexp) {
        return MatchRegex.matchRegex(text, regexp);
    }

    public static final boolean isNullResult(boolean text) {
        return text;
    }

    public String name() {
        return "MatchRegex";
    }

    public String title() {
        return "\u662f\u5426\u7b26\u5408\u6b63\u5219\u8868\u8fbe\u5f0f";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u6587\u672c\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object p0 = parameters.get(0).evaluate(context);
        Object p1 = parameters.get(1).evaluate(context);
        return MatchRegex.matchRegex((String)p0, (String)p1);
    }

    public boolean support(Language lang) {
        return lang == Language.JavaScript || super.support(lang);
    }
}

