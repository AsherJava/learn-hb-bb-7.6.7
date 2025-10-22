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

public final class Value
extends Function
implements IReportFunction {
    private static final long serialVersionUID = 3758095211416327526L;

    public Value() {
        this.parameters().add(new Parameter("text", 6, "\u6587\u672c"));
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
            throw new SyntaxException("Value()\u7684\u53c2\u6570\u5fc5\u987b\u4e3a\u5b57\u7b26\u4e32\u7c7b\u578b");
        }
        String s = ((String)obj).trim();
        try {
            if (s == null || s.length() == 0) {
                return null;
            }
            return Double.valueOf(s);
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 3;
    }

    public String name() {
        return "Value";
    }

    public String title() {
        return "\u5c06\u6587\u672c\u8f6c\u5316\u4e3a\u6570\u503c";
    }

    public boolean support(Language lang) {
        return true;
    }

    protected void toSQL(IContext context, List<IASTNode> children, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        if (info.isDatabase(new String[]{"MYSQL", "GBASE"})) {
            buffer.append("CAST(");
            children.get(0).interpret(context, buffer, Language.SQL, (Object)info);
            buffer.append(" AS DECIMAL)");
        } else if (info.isDatabase(new String[]{"SQLSERVER"})) {
            buffer.append("CAST(");
            children.get(0).interpret(context, buffer, Language.SQL, (Object)info);
            buffer.append(" AS FLOAT)");
        } else if (info.isDatabase(new String[]{"ORACLE", "DM", "KINGBASE", "KINGBASE8"})) {
            buffer.append("TO_NUMBER(");
            children.get(0).interpret(context, buffer, Language.SQL, (Object)info);
            buffer.append(")");
        } else if (info.isDatabase(new String[]{"HANA"})) {
            buffer.append("TO_DOUBLE(");
            children.get(0).interpret(context, buffer, Language.SQL, (Object)info);
            buffer.append(")");
        } else if (info.isDatabase(new String[]{"DB2"})) {
            buffer.append("DOUBLE(");
            children.get(0).interpret(context, buffer, Language.SQL, (Object)info);
            buffer.append(")");
        } else {
            throw new InterpretException("\u6570\u636e\u5e93\u7c7b\u578b\u4e0d\u88ab\u652f\u6301");
        }
    }

    protected void toExcel(IContext context, List<IASTNode> children, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append("value(");
        children.get(0).interpret(context, buffer, Language.SQL, info);
        buffer.append(")");
    }
}

