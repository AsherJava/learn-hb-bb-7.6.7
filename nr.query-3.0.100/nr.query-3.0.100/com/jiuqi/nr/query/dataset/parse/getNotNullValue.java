/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.nr.query.dataset.parse;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.Iterator;
import java.util.List;

public final class getNotNullValue
extends Function {
    private static final long serialVersionUID = 425370342614348893L;

    public getNotNullValue() {
        this.parameters().add(new Parameter("value", 0, "\u503c"));
    }

    public String category() {
        return "\u903b\u8f91\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        IASTNode p;
        Object value = null;
        Iterator<IASTNode> iterator = parameters.iterator();
        while (iterator.hasNext() && (value = (p = iterator.next()).evaluate(context)) == null) {
        }
        return value;
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 6;
    }

    public boolean isInfiniteParameter() {
        return true;
    }

    public String name() {
        return "getNotNullValue";
    }

    public String title() {
        return "\u83b7\u53d6\u975e\u7a7a\u503c";
    }
}

