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
import java.util.List;

public final class Pos
extends Function
implements IReportFunction {
    private static final Logger logger = LogFactory.getLogger(Pos.class);
    private static final long serialVersionUID = -4778258463214484480L;

    public Pos() {
        this.parameters().add(new Parameter("subStr", 6, "\u5b50\u4e32"));
        this.parameters().add(new Parameter("text", 6, "\u6587\u672c"));
    }

    public static final int callFunction(String substring, String totalstring) {
        try {
            byte[] substringBytes = substring.getBytes("gbk");
            byte[] totalstringBytes = totalstring.getBytes("gbk");
            for (int index = 0; index < totalstringBytes.length; ++index) {
                if (totalstringBytes[index] != substringBytes[0]) continue;
                boolean match = true;
                for (int i = 0; i < substringBytes.length; ++i) {
                    if (index + i < totalstringBytes.length && substringBytes[i] == totalstringBytes[index + i]) continue;
                    match = false;
                    break;
                }
                if (!match) continue;
                return index + 1;
            }
        }
        catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return 0;
    }

    public String name() {
        return "Pos";
    }

    public String title() {
        return "\u67e5\u627e\u5b57\u7b26\u4e32\u5728\u6587\u672c\u4e2d\u51fa\u73b0\u7684\u4f4d\u7f6e(\u6309\u5b57\u8282\u8ba1\u6570\uff0c\u6c49\u5b57\u7b97\u4e24\u4e2a\u5b57\u8282)";
    }

    protected void toExplain(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        parameters.get(0).interpret(context, buffer, Language.EXPLAIN, info);
        buffer.append("\u5728\u5b57\u7b26\u4e32");
        parameters.get(1).interpret(context, buffer, Language.EXPLAIN, info);
        buffer.append("\u7684\u5b57\u8282\u4f4d\u7f6e");
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 3;
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
            throw new SyntaxException("Pos()\u7684\u7b2c\u4e00\u4e2a\u53c2\u6570\u5fc5\u987b\u4e3a\u5b57\u7b26\u4e32\u7c7b\u578b");
        }
        String subStr = (String)obj;
        obj = parameters.get(1).evaluate(context);
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof String)) {
            throw new SyntaxException("Pos()\u7684\u7b2c\u4e8c\u4e2a\u53c2\u6570\u5fc5\u987b\u4e3a\u5b57\u7b26\u4e32\u7c7b\u578b");
        }
        String text = (String)obj;
        return Pos.callFunction(subStr, text);
    }

    public boolean support(Language lang) {
        return lang != Language.EXCEL;
    }

    protected void toSQL(IContext context, List<IASTNode> children, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        IASTNode subTextNode = children.get(0);
        IASTNode textNode = children.get(1);
        buffer.append("INSTRB(");
        textNode.interpret(context, buffer, Language.SQL, (Object)info);
        buffer.append(",");
        subTextNode.interpret(context, buffer, Language.SQL, (Object)info);
        buffer.append(")");
    }
}

