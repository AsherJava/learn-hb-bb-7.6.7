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
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
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
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public final class LDollar
extends Function
implements IReportFunction {
    private static final Logger logger = LogFactory.getLogger(LDollar.class);
    private static final long serialVersionUID = 8931778238597600614L;

    public LDollar() {
        this.parameters().add(new Parameter("text", 6, "\u6587\u672c"));
        this.parameters().add(new Parameter("length", 3, "\u957f\u5ea6"));
    }

    public static final String callFunction(String text, int length) {
        try {
            byte[] bytes = text.getBytes("gbk");
            if (bytes.length < length) {
                length = bytes.length;
            }
            byte[] newBytes = Arrays.copyOf(bytes, length);
            return new String(newBytes, "gbk");
        }
        catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), (Throwable)e);
            return null;
        }
    }

    public String name() {
        return "L$";
    }

    public String title() {
        return "\u622a\u53d6\u5b57\u7b26\u4e32\u7684\u5de6\u4e32(\u6309\u5b57\u8282\u8ba1\u6570\uff0c\u6c49\u5b57\u7b97\u4e24\u4e2a\u5b57\u8282)";
    }

    protected void toExplain(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        parameters.get(0).interpret(context, buffer, Language.EXPLAIN, info);
        buffer.append("\u524d");
        parameters.get(1).interpret(context, buffer, Language.EXPLAIN, info);
        buffer.append("\u4f4d\u5b57\u8282");
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 6;
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
            throw new SyntaxException("Left()\u7684\u7b2c\u4e00\u4e2a\u53c2\u6570\u5fc5\u987b\u4e3a\u5b57\u7b26\u4e32\u7c7b\u578b");
        }
        String text = (String)obj;
        obj = parameters.get(1).evaluate(context);
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof Number)) {
            throw new SyntaxException("Left()\u7684\u7b2c\u4e8c\u4e2a\u53c2\u6570\u5fc5\u987b\u4e3a\u6570\u503c\u7c7b\u578b");
        }
        Number length = (Number)obj;
        if (length.intValue() < 0) {
            length = 0;
        }
        return LDollar.callFunction(text, length.intValue());
    }

    public boolean support(Language lang) {
        return lang != Language.EXCEL;
    }

    protected void toSQL(IContext context, List<IASTNode> children, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        if (info.isDatabase(new String[]{"ORACLE", "DM", "KINGBASE"})) {
            buffer.append("SUBSTRB(");
            children.get(0).interpret(context, buffer, Language.SQL, (Object)info);
            buffer.append(", 1, ");
            children.get(1).interpret(context, buffer, Language.SQL, (Object)info);
            buffer.append(")");
        } else {
            super.toSQL(context, children, buffer, info);
        }
    }
}

