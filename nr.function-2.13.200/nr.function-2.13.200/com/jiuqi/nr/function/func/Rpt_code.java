/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.List;

public class Rpt_code
extends Function
implements IReportFunction {
    private static final long serialVersionUID = 1L;

    public String category() {
        return "\u5176\u4ed6";
    }

    public Object evalute(IContext context, List<IASTNode> arg1) throws SyntaxException {
        QueryContext qContext = (QueryContext)context;
        return qContext.getDefaultGroupName();
    }

    public int getResultType(IContext arg0, List<IASTNode> arg1) throws SyntaxException {
        return 6;
    }

    public String name() {
        return "rpt_code";
    }

    public String title() {
        return "\u83b7\u53d6\u5f53\u524d\u62a5\u8868\u6807\u8bc6";
    }

    protected void toExplain(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        parameters.get(0).interpret(context, buffer, Language.EXPLAIN, info);
        buffer.append("\u62a5\u8868\u7684\u6807\u8bc6");
    }
}

