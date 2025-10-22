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

public final class IsDigit
extends Function
implements IReportFunction {
    private static final long serialVersionUID = -6719045326856149051L;

    public IsDigit() {
        this.parameters().add(new Parameter("text", 6, "\u5b57\u7b26\u4e32"));
    }

    public String name() {
        return "IsDigit";
    }

    public String title() {
        return "\u662f\u6570\u5b57";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u6587\u672c\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        String str = (String)parameters.get(0).evaluate(context);
        if (str == null) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public boolean support(Language lang) {
        return lang == Language.JavaScript || super.support(lang);
    }
}

