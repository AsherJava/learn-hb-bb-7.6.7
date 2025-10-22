/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.nr.data.engine.summary.parse.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.List;

public class StartWithFunction
extends Function {
    private static final long serialVersionUID = -4562771098865625223L;

    public StartWithFunction() {
        this.parameters().add(new Parameter("text", 0, "\u5f85\u67e5\u627e\u7684\u5185\u5bb9"));
        this.parameters().add(new Parameter("prefix", 0, "\u524d\u7f00"));
    }

    public String name() {
        return "StartWith";
    }

    public String title() {
        return "\u4ee5\u67d0\u4e2a\u5b57\u7b26\u4e32\u4e3a\u524d\u7f00";
    }

    public int getResultType(IContext paramIContext, List<IASTNode> paramList) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u903b\u8f91\u51fd\u6570";
    }

    public Object evalute(IContext paramIContext, List<IASTNode> paramList) throws SyntaxException {
        Object text = paramList.get(0).evaluate(paramIContext);
        Object prefix = paramList.get(1).evaluate(paramIContext);
        if (text == null || prefix == null) {
            return false;
        }
        return text.toString().startsWith(prefix.toString());
    }

    protected void toSQL(IContext paramIContext, List<IASTNode> paramList, StringBuilder paramStringBuilder, ISQLInfo paramISQLInfo) throws InterpretException {
        try {
            String prefix = paramList.get(1).evaluate(paramIContext).toString();
            if (prefix.length() == 0) {
                paramStringBuilder.append("1=1");
                return;
            }
            paramStringBuilder.append(paramList.get(0).interpret(paramIContext, Language.SQL, (Object)paramISQLInfo));
            paramStringBuilder.append(" like '");
            paramStringBuilder.append(prefix);
            paramStringBuilder.append("%'");
        }
        catch (SyntaxException e) {
            throw new InterpretException(e.getMessage(), (Throwable)e);
        }
    }
}

