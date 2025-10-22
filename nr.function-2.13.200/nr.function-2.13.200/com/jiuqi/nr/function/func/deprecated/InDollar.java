/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 */
package com.jiuqi.nr.function.func.deprecated;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import java.util.List;

public class InDollar
extends Function
implements IReportFunction {
    private static final long serialVersionUID = -1880150191086424717L;

    public InDollar() {
        this.parameters().add(new Parameter("value", 0, "\u6570\u503c"));
        this.parameters().add(new Parameter("list", 0, "\u5217\u8868"));
    }

    public String name() {
        return "In$";
    }

    public String title() {
        return "\u5728\u5217\u8868\u4e2d(\u4ec5\u7528\u4e8e\u517c\u5bb9JQR)";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public boolean isInfiniteParameter() {
        return true;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object value = parameters.get(0).evaluate(context);
        if (value == null) {
            return false;
        }
        for (int i = 1; i < parameters.size(); ++i) {
            if (DataType.compareObject((Object)value, (Object)parameters.get(i).evaluate(context)) != 0) continue;
            return true;
        }
        return false;
    }

    public boolean isDeprecated() {
        return true;
    }
}

