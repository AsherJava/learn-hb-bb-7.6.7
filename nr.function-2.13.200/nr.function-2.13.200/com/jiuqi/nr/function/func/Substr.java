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
import com.jiuqi.np.dataengine.parse.IReportFunction;
import java.util.List;

public final class Substr
extends Function
implements IReportFunction {
    private static final long serialVersionUID = 9011961945408989609L;

    public Substr() {
        this.parameters().add(new Parameter("text", 6, "\u6587\u672c"));
        this.parameters().add(new Parameter("start", 3, "\u8d77\u59cb\u4f4d\u7f6e"));
        this.parameters().add(new Parameter("length", 3, "\u622a\u53d6\u957f\u5ea6", true));
    }

    public String category() {
        return "\u6587\u672c\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object obj = parameters.get(0).evaluate(context);
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof String)) {
            throw new SyntaxException("Substr()\u7684\u7b2c\u4e00\u4e2a\u53c2\u6570\u5fc5\u987b\u4e3a\u5b57\u7b26\u4e32\u7c7b\u578b");
        }
        String text = (String)obj;
        obj = parameters.get(1).evaluate(context);
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof Number)) {
            throw new SyntaxException("Substr()\u7684\u7b2c\u4e8c\u4e2a\u53c2\u6570\u5fc5\u987b\u4e3a\u6570\u503c\u7c7b\u578b");
        }
        Number start = (Number)obj;
        if (start.intValue() < 0) {
            throw new SyntaxException("Substr()\u7684\u7b2c\u4e8c\u4e2a\u53c2\u6570\u503c\u4e0d\u80fd\u4e3a\u8d1f\u503c");
        }
        Number length = text.length() - start.intValue();
        if (parameters.size() == 3) {
            obj = parameters.get(2).evaluate(context);
            if (obj == null) {
                return null;
            }
            if (!(obj instanceof Number)) {
                throw new SyntaxException("Substr()\u7684\u7b2c\u4e09\u4e2a\u53c2\u6570\u5fc5\u987b\u4e3a\u6570\u503c\u7c7b\u578b");
            }
            length = (Number)obj;
            if (length.intValue() < 0) {
                throw new SyntaxException("Substr()\u7684\u7b2c\u4e09\u4e2a\u53c2\u6570\u503c\u4e0d\u80fd\u4e3a\u8d1f\u503c");
            }
        }
        return Substr.callFunction(text, start.intValue(), length.intValue());
    }

    public static String callFunction(String text, int start, int length) {
        if (length <= text.length() - start) {
            return text.substring(start, start + length);
        }
        return String.valueOf(text);
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 6;
    }

    public String name() {
        return "Substr";
    }

    public String[] aliases() {
        return new String[]{"SUBSTRING"};
    }

    public String title() {
        return "\u622a\u53d6\u5b57\u7b26\u4e32";
    }

    protected void toExplain(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        parameters.get(0).interpret(context, buffer, Language.EXPLAIN, info);
        buffer.append("\u7684\u7b2c");
        parameters.get(1).interpret(context, buffer, Language.EXPLAIN, info);
        buffer.append("\u4f4d");
        if (parameters.size() > 2) {
            buffer.append("\u5f00\u59cb\u622a\u53d6");
            parameters.get(2).interpret(context, buffer, Language.EXPLAIN, info);
            buffer.append("\u4f4d");
        } else {
            buffer.append("\u5230\u672b\u5c3e");
        }
    }

    public boolean support(Language lang) {
        return lang != Language.EXCEL;
    }

    protected void toSQL(IContext context, List<IASTNode> children, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        if (info.isDatabase(new String[]{"ORACLE", "DM", "KINGBASE"})) {
            buffer.append("SUBSTR(");
            boolean flag = false;
            for (IASTNode p : children) {
                if (flag) {
                    buffer.append(',');
                } else {
                    flag = true;
                }
                p.interpret(context, buffer, Language.SQL, (Object)info);
            }
            buffer.append(")");
        } else {
            super.toSQL(context, children, buffer, info);
        }
    }
}

