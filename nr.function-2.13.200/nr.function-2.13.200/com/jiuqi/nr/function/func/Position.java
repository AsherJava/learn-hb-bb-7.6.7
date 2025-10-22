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

public final class Position
extends Function
implements IReportFunction {
    private static final long serialVersionUID = -493651136089706030L;

    public Position() {
        this.parameters().add(new Parameter("subStr", 6, "\u5b50\u4e32"));
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
        int index = text.indexOf(subStr);
        index = index < 0 ? 0 : index + 1;
        return (double)index;
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 3;
    }

    public String name() {
        return "Position";
    }

    public String title() {
        return "\u67e5\u627e\u5b57\u7b26\u4e32\u5728\u6587\u672c\u4e2d\u51fa\u73b0\u7684\u4f4d\u7f6e(\u6309\u5b57\u8282\u8ba1\u6570\uff0c\u6c49\u5b57\u7b97\u4e24\u4e2a\u5b57\u8282)";
    }

    public boolean support(Language lang) {
        return lang != Language.EXCEL;
    }

    protected String nameOf(Language lang, Object info) throws InterpretException {
        if (lang == Language.SQL) {
            ISQLInfo sqlInfo = (ISQLInfo)info;
            if (sqlInfo.isDatabase(new String[]{"SQLSERVER"})) {
                return "CHARINDEX";
            }
            if (sqlInfo.isDatabase(new String[]{"MYSQL", "ORACLE", "DM", "KINGBASE", "KINGBASE8", "GBASE"})) {
                return "INSTR";
            }
            if (sqlInfo.isDatabase(new String[]{"HANA"})) {
                return "LOCATE";
            }
            if (sqlInfo.isDatabase(new String[]{"DB2"})) {
                return "POSSTR";
            }
        }
        return super.nameOf(lang, info);
    }

    protected void toExplain(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        parameters.get(0).interpret(context, buffer, Language.EXPLAIN, info);
        buffer.append("\u5728\u5b57\u7b26\u4e32");
        parameters.get(1).interpret(context, buffer, Language.EXPLAIN, info);
        buffer.append("\u7684\u4f4d\u7f6e");
    }

    protected void toSQL(IContext context, List<IASTNode> children, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        buffer.append(this.nameOf(Language.SQL, info)).append("(");
        children.get(1).interpret(context, buffer, Language.SQL, (Object)info);
        buffer.append(",");
        children.get(0).interpret(context, buffer, Language.SQL, (Object)info);
        buffer.append(")");
    }

    protected void toMDX(IContext context, List<IASTNode> children, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append(this.name()).append("(");
        children.get(0).interpret(context, buffer, Language.JQMDX, info);
        buffer.append(",");
        children.get(1).interpret(context, buffer, Language.JQMDX, info);
        buffer.append(")");
        buffer.append("-1");
    }
}

