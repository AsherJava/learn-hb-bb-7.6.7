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

public final class Len
extends Function
implements IReportFunction {
    private static final Logger logger = LogFactory.getLogger(Len.class);
    private static final long serialVersionUID = -5437710826060264488L;

    public Len() {
        this.parameters().add(new Parameter("text", 6, "\u5b57\u7b26\u4e32\u503c"));
    }

    public static final int callFunction(String text) {
        try {
            return text.getBytes("gbk").length;
        }
        catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), (Throwable)e);
            return 0;
        }
    }

    public String name() {
        return "Len";
    }

    public String title() {
        return "\u6c42\u5b57\u7b26\u4e32\u957f\u5ea6(\u6309\u5b57\u8282\u8ba1\u6570\uff0c\u6c49\u5b57\u7b97\u4e24\u4e2a\u5b57\u8282)";
    }

    protected void toExplain(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        parameters.get(0).interpret(context, buffer, Language.EXPLAIN, info);
        buffer.append("\u7684\u5b57\u8282\u957f\u5ea6");
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
            return 0;
        }
        if (!(obj instanceof String)) {
            throw new SyntaxException("Length()\u7684\u53c2\u6570\u5fc5\u987b\u4e3a\u5b57\u7b26\u4e32\u7c7b\u578b");
        }
        return Len.callFunction((String)obj);
    }

    public boolean support(Language lang) {
        return lang != Language.EXCEL;
    }

    protected void toSQL(IContext context, List<IASTNode> children, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        buffer.append("LENGTHB(");
        children.get(0).interpret(context, buffer, Language.SQL, (Object)info);
        buffer.append(")");
    }
}

