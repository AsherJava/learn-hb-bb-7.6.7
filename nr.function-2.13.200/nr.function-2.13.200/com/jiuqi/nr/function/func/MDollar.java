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

public final class MDollar
extends Function
implements IReportFunction {
    private static final Logger logger = LogFactory.getLogger(MDollar.class);
    private static final long serialVersionUID = -5294739385430292488L;

    public MDollar() {
        this.parameters().add(new Parameter("text", 6, "\u6587\u672c"));
        this.parameters().add(new Parameter("start", 3, "\u8d77\u59cb\u4f4d\u7f6e"));
        this.parameters().add(new Parameter("length", 3, "\u957f\u5ea6"));
    }

    public static final String callFunction(String text, int start, int length) {
        try {
            byte[] bytes = text.getBytes("gbk");
            if (start > bytes.length) {
                return null;
            }
            byte[] newBytes = Arrays.copyOfRange(bytes, start - 1, length + start - 1);
            return new String(newBytes, "gbk");
        }
        catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), (Throwable)e);
            return null;
        }
    }

    public String name() {
        return "M$";
    }

    public String title() {
        return "\u622a\u53d6\u5b57\u7b26\u4e32\u4e2d\u7684\u5b50\u4e32(\u6309\u5b57\u8282\u8ba1\u6570\uff0c\u6c49\u5b57\u7b97\u4e24\u4e2a\u5b57\u8282)";
    }

    protected void toExplain(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        parameters.get(0).interpret(context, buffer, Language.EXPLAIN, info);
        buffer.append("\u7684");
        String startStr = parameters.get(1).interpret(context, Language.EXPLAIN, info);
        int start = Integer.parseInt(startStr);
        String lenStr = parameters.get(2).interpret(context, Language.EXPLAIN, info);
        int len = Integer.parseInt(lenStr);
        if (start == 1 && len > 1) {
            buffer.append("\u5f00\u59cb\u4f4d\u7f6e");
        } else {
            buffer.append("\u7b2c").append(startStr).append("\u4f4d\u5b57\u8282");
        }
        if (len > 1) {
            buffer.append("\u5230\u7b2c").append(start + len - 1).append("\u4f4d\u5b57\u8282 ");
        }
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
            throw new SyntaxException("Mid()\u7684\u7b2c\u4e00\u4e2a\u53c2\u6570\u5fc5\u987b\u4e3a\u5b57\u7b26\u4e32\u7c7b\u578b");
        }
        String text = (String)obj;
        obj = parameters.get(1).evaluate(context);
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof Number)) {
            throw new SyntaxException("Mid()\u7684\u7b2c\u4e8c\u4e2a\u53c2\u6570\u5fc5\u987b\u4e3a\u6570\u503c\u7c7b\u578b");
        }
        Number start = (Number)obj;
        if (start.intValue() < 1) {
            start = 1;
        }
        if ((obj = parameters.get(2).evaluate(context)) == null) {
            return null;
        }
        if (!(obj instanceof Number)) {
            throw new SyntaxException("Mid()\u7684\u7b2c\u4e09\u4e2a\u53c2\u6570\u5fc5\u987b\u4e3a\u6570\u503c\u7c7b\u578b");
        }
        Number length = (Number)obj;
        if (length.intValue() < 0) {
            length = 0;
        }
        return MDollar.callFunction(text, start.intValue(), length.intValue());
    }

    public boolean support(Language lang) {
        return lang != Language.EXCEL;
    }

    protected String nameOf(Language lang, Object info) throws InterpretException {
        if (lang == Language.SQL) {
            ISQLInfo sqlInfo = (ISQLInfo)info;
            if (sqlInfo.isDatabase(new String[]{"MYSQL", "SQLSERVER", "HANA", "GBASE"})) {
                return "SUBSTRING";
            }
            if (sqlInfo.isDatabase(new String[]{"ORACLE", "DB2", "DM", "KINGBASE"})) {
                return "SUBSTR";
            }
        }
        return super.nameOf(lang, info);
    }

    protected void toSQL(IContext context, List<IASTNode> children, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        String name = this.nameOf(Language.SQL, info);
        buffer.append(name).append("(");
        children.get(0).interpret(context, buffer, Language.SQL, (Object)info);
        buffer.append(", ");
        children.get(1).interpret(context, buffer, Language.SQL, (Object)info);
        buffer.append(", ");
        children.get(2).interpret(context, buffer, Language.SQL, (Object)info);
        buffer.append(")");
    }
}

