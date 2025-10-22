/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import java.util.Calendar;
import java.util.List;

public class WeekDay
extends Function
implements IReportFunction {
    private static final long serialVersionUID = 6879722402804779502L;

    public WeekDay() {
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
        int result = p0.get(7);
        return result;
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 3;
    }

    public String name() {
        return "WeekDay";
    }

    public String title() {
        return "\u8fd4\u56de\u6307\u5b9a\u65e5\u671f\u662f\u5468\u51e0";
    }

    public boolean support(Language lang) {
        return lang != Language.EXCEL && lang != Language.SQL;
    }
}

