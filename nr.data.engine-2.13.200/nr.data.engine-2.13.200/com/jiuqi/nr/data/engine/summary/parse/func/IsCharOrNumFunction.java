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

public class IsCharOrNumFunction
extends Function {
    private static final long serialVersionUID = -4562771098865625223L;

    public IsCharOrNumFunction() {
        this.parameters().add(new Parameter("text", 0, "\u5f85\u5224\u65ad\u7684\u5185\u5bb9"));
    }

    public String name() {
        return "IsCharOrNum";
    }

    public String title() {
        return "\u5b57\u7b26\u4e32\u7531\u5b57\u6bcd\u548c\u6570\u636e\u7ec4\u6210";
    }

    public int getResultType(IContext paramIContext, List<IASTNode> paramList) throws SyntaxException {
        return 1;
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u903b\u8f91\u51fd\u6570";
    }

    public Object evalute(IContext paramIContext, List<IASTNode> paramList) throws SyntaxException {
        Object text = paramList.get(0).evaluate(paramIContext);
        if (text == null) {
            return false;
        }
        return text.toString().matches("^[A-Za-z0-9]*$");
    }

    public boolean support(Language lang) {
        return true;
    }

    protected void toSQL(IContext paramIContext, List<IASTNode> paramList, StringBuilder paramStringBuilder, ISQLInfo paramISQLInfo) throws InterpretException {
        paramStringBuilder.append("regexp_like(");
        paramStringBuilder.append(paramList.get(0).interpret(paramIContext, Language.SQL, (Object)paramISQLInfo));
        paramStringBuilder.append(",'^[A-Za-z0-9]*$')");
    }
}

