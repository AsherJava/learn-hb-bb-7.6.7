/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.function;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.expression.DSFieldNode;
import com.jiuqi.bi.dataset.function.DSFunction;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.List;

public class Lag
extends DSFunction {
    private static final long serialVersionUID = -2793554453186861730L;

    public Lag() {
        this.parameters().add(new Parameter("attribute", 0, "\u504f\u79fb\u7ef4\u5c5e\u6027"));
        this.parameters().add(new Parameter("offset", 3, "\u504f\u79fb\u91cf"));
    }

    @Override
    public Object evalute(IContext context, List<IASTNode> paramNodes, BIDataSet filterDs) throws SyntaxException {
        throw new SyntaxException("DS_LAG\u51fd\u6570\u4e0d\u80fd\u72ec\u7acb\u6267\u884c\uff0c\u53ea\u80fd\u4f5c\u4e3a\u8fc7\u6ee4\u6761\u4ef6\u88ab\u4f7f\u7528");
    }

    @Override
    public boolean isInfiniteParameter() {
        return false;
    }

    public String name() {
        return "LAG";
    }

    public String[] aliases() {
        return new String[]{"DS_LAG"};
    }

    public String title() {
        return "\u83b7\u53d6\u6307\u5b9a\u7684\u65f6\u671f\u7ef4\u5ea6\u504f\u79fb\u540e\u7684\u503c";
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (parameters.size() == 0) {
            throw new SyntaxException("\u51fd\u6570\u53c2\u6570\u4e2a\u6570\u4e0d\u5339\u914d");
        }
        IASTNode node = parameters.get(0);
        if (!(node instanceof DSFieldNode)) {
            throw new SyntaxException("\u51fd\u6570\u53c2\u6570\u9519\u8bef\uff0c\u8282\u70b9\u3010" + node.interpret(context, Language.FORMULA, null) + "\u3011\u4e0d\u662f\u4e00\u4e2a\u6570\u636e\u96c6\u5b57\u6bb5\u8282\u70b9");
        }
        return super.validate(context, parameters);
    }

    public int getResultType(IContext context, List<IASTNode> paramNodes) throws SyntaxException {
        return 1;
    }
}

