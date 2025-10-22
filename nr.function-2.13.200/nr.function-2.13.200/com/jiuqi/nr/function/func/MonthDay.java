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
import java.util.Calendar;
import java.util.List;

public class MonthDay
extends Function
implements IReportFunction {
    private static final long serialVersionUID = 6879722402804779502L;

    public MonthDay() {
        this.parameters().add(new Parameter("date", 2, "\u65e5\u671f\u503c"));
    }

    public String category() {
        return "\u65e5\u671f\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Calendar p0 = (Calendar)parameters.get(0).evaluate(context);
        if (p0 == null) {
            return null;
        }
        int result = p0.get(5);
        return result;
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 3;
    }

    public String name() {
        return "MonthDay";
    }

    public String title() {
        return "\u8fd4\u56de\u6307\u5b9a\u6708\u4efd\u4e2d\u7684\u5929\u6570";
    }

    public boolean support(Language lang) {
        return lang != Language.EXCEL;
    }

    public String[] aliases() {
        return new String[]{"Day"};
    }

    protected void toSQL(IContext context, List<IASTNode> children, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        if (info.isDatabase(new String[]{"MYSQL"})) {
            buffer.append(" DAYOFMONTH(");
            children.get(0).interpret(context, buffer, Language.SQL, (Object)info);
            buffer.append(") ");
        } else if (info.isDatabase(new String[]{"ORACLE"})) {
            buffer.append(" extract(day from ");
            children.get(0).interpret(context, buffer, Language.SQL, (Object)info);
            buffer.append(") ");
        } else if (info.isDatabase(new String[]{"SQLSERVER"})) {
            buffer.append(" DAY(");
            children.get(0).interpret(context, buffer, Language.SQL, (Object)info);
            buffer.append(") ");
        } else {
            throw new InterpretException("MonthDay()\u51fd\u6570\u9047\u5230\u5c1a\u672a\u652f\u6301\u7684\u6570\u636e\u5e93\u7c7b\u578b\uff1a" + info.getDatabase().getTitle());
        }
    }
}

