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
 *  com.jiuqi.bi.syntax.reportparser.function.IJavaScriptFunction
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.function.IJavaScriptFunction;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import java.util.List;

public final class Length
extends Function
implements IJavaScriptFunction,
IReportFunction {
    private static final long serialVersionUID = 8374405472279825570L;

    public Length() {
        this.parameters().add(new Parameter("text", 6, "\u5b57\u7b26\u4e32\u503c"));
    }

    public String category() {
        return "\u6587\u672c\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object obj = parameters.get(0).evaluate(context);
        if (obj == null) {
            return 0;
        }
        if (!(obj instanceof String)) {
            throw new SyntaxException("Length()\u7684\u53c2\u6570\u5fc5\u987b\u4e3a\u5b57\u7b26\u4e32\u7c7b\u578b");
        }
        return ((String)obj).length();
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 3;
    }

    public String name() {
        return "Length";
    }

    public String title() {
        return "\u6c42\u5b57\u7b26\u4e32\u957f\u5ea6";
    }

    protected void toExplain(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        parameters.get(0).interpret(context, buffer, Language.EXPLAIN, info);
        buffer.append("\u7684\u957f\u5ea6");
    }

    public boolean support(Language lang) {
        return true;
    }

    protected void toSQL(IContext context, List<IASTNode> children, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        if (info.isDatabase(new String[]{"SQLSERVER"})) {
            buffer.append("LEN(");
            children.get(0).interpret(context, buffer, Language.SQL, (Object)info);
            buffer.append(")");
        } else {
            buffer.append("LENGTH(");
            children.get(0).interpret(context, buffer, Language.SQL, (Object)info);
            buffer.append(")");
        }
    }

    protected void toExcel(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append("LEN").append('(');
        boolean flag = false;
        for (IASTNode p : parameters) {
            if (flag) {
                buffer.append(',');
            } else {
                flag = true;
            }
            p.interpret(context, buffer, Language.EXCEL, info);
        }
        buffer.append(')');
    }

    public String toScriptName() {
        return "LenB";
    }
}

