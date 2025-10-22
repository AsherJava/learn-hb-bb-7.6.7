/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 */
package com.jiuqi.nr.function.func.deprecated;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import java.util.List;

public final class ToGuid
extends Function
implements IReportFunction {
    private static final long serialVersionUID = -1811482478845878106L;

    public ToGuid() {
        this.parameters().add(new Parameter("text", 6, "\u6587\u672c"));
    }

    public String category() {
        return "\u6587\u672c\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object obj = parameters.get(0).evaluate(context);
        return obj;
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 6;
    }

    public String name() {
        return "toGuid";
    }

    public String title() {
        return "\u517c\u5bb9JQR\u51fd\u6570\uff0c\u539f\u503c\u8fd4\u56de";
    }

    public boolean isDeprecated() {
        return true;
    }
}

