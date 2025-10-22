/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import java.util.Arrays;
import java.util.List;

public final class Split
extends Function
implements IReportFunction {
    private static final long serialVersionUID = -1811482478845878106L;

    public Split() {
        this.parameters().add(new Parameter("text", 6, "\u6587\u672c"));
        this.parameters().add(new Parameter("regex", 6, "\u5206\u9694\u7b26,\u53ef\u7f3a\u7701,\u9ed8\u8ba4\u4f7f\u7528;", true));
    }

    public String category() {
        return "\u6587\u672c\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object p1 = parameters.get(0).evaluate(context);
        if (p1 == null) {
            return null;
        }
        if (!(p1 instanceof String)) {
            throw new SyntaxException("split()\u7684\u7b2c\u4e00\u4e2a\u53c2\u6570\u5fc5\u987b\u4e3a\u5b57\u7b26\u4e32\u7c7b\u578b");
        }
        String text = (String)p1;
        String regex = ";";
        if (parameters.size() > 1) {
            Object p2 = parameters.get(1).evaluate(context);
            if (p2 != null && !(p2 instanceof String)) {
                throw new SyntaxException("split()\u7684\u7b2c\u4e8c\u4e2a\u53c2\u6570\u5fc5\u987b\u4e3a\u5b57\u7b26\u4e32\u7c7b\u578b");
            }
            regex = (String)p2;
        }
        return new ArrayData(6, Arrays.asList(text.split(regex)));
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 11;
    }

    public String name() {
        return "split";
    }

    public String title() {
        return "\u628a\u5b57\u7b26\u4e32\u5206\u9694\u4e3a\u6570\u7ec4";
    }
}

