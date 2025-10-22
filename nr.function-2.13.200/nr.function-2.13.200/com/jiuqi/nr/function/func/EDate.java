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

public class EDate
extends Function
implements IReportFunction {
    private static final long serialVersionUID = 2359865846049855737L;

    public EDate() {
        this.parameters().add(new Parameter("date", 2, "\u65e5\u671f"));
        this.parameters().add(new Parameter("month", 3, "\u4e4b\u524d\uff08\u8d1f\uff09\u6216\u4e4b\u540e\uff08\u6b63\uff09\u7684\u6708\u6570"));
    }

    public String name() {
        return "EDate";
    }

    public String title() {
        return "\u8fd4\u56de\u6307\u5b9a\u65e5\u671f\uff08Start_Date\uff09\u4e4b\u524d\u6216\u8005\u4e4b\u540e\u6307\u5b9a\u6708\u6570\u5bf9\u5e94\u5f97\u65e5\u671f";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 2;
    }

    public String category() {
        return "\u65e5\u671f\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object obj = parameters.get(0).evaluate(context);
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof Calendar)) {
            throw new SyntaxException("EDate()\u51fd\u6570\u7684\u7b2c\u4e00\u4e2a\u53c2\u6570\u5fc5\u987b\u4e3a\u65e5\u671f\u7c7b\u578b");
        }
        Calendar calendar = (Calendar)obj;
        Calendar date = Calendar.getInstance();
        date.set(calendar.get(1), calendar.get(2), calendar.get(5));
        obj = parameters.get(1).evaluate(context);
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof Number)) {
            throw new SyntaxException("EDate()\u51fd\u6570\u7684\u7b2c\u4e8c\u4e2a\u53c2\u6570\u5fc5\u987b\u4e3a\u6570\u503c\u7c7b\u578b");
        }
        Number month = (Number)obj;
        date.add(2, month.intValue());
        return date;
    }

    public boolean support(Language lang) {
        return lang != Language.EXCEL && lang != Language.JavaScript;
    }

    protected void toSQL(IContext context, List<IASTNode> children, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        buffer.append("add_months(");
        children.get(0).interpret(context, buffer, Language.SQL, (Object)info);
        buffer.append(", ");
        children.get(1).interpret(context, buffer, Language.SQL, (Object)info);
        buffer.append(")");
    }
}

