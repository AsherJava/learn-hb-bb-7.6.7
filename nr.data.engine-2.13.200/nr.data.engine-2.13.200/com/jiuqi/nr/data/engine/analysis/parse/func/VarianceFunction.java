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
 *  com.jiuqi.np.dataengine.executors.StatUnit
 */
package com.jiuqi.nr.data.engine.analysis.parse.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.executors.StatUnit;
import com.jiuqi.nr.data.engine.analysis.parse.func.VarianceUnit;
import com.jiuqi.nr.data.engine.summary.parse.func.IStatistic;
import java.util.List;

public class VarianceFunction
extends Function
implements IStatistic {
    private static final long serialVersionUID = 2741554547558679660L;

    public VarianceFunction() {
        this.parameters().add(new Parameter("data", 0, "\u8981\u7edf\u8ba1\u7684\u6307\u6807"));
    }

    public String name() {
        return "Variance";
    }

    public String title() {
        return "\u65b9\u5dee";
    }

    public int getResultType(IContext paramIContext, List<IASTNode> paramList) throws SyntaxException {
        return 11;
    }

    public boolean support(Language lang) {
        return lang == Language.EXPLAIN || lang == Language.FORMULA;
    }

    public String category() {
        return "\u7edf\u8ba1\u51fd\u6570";
    }

    public Object evalute(IContext paramIContext, List<IASTNode> paramList) throws SyntaxException {
        return paramList.get(0).evaluate(paramIContext);
    }

    @Override
    public StatUnit createStatUnit(IContext paramIContext, List<IASTNode> paramList) throws SyntaxException {
        return new VarianceUnit();
    }
}

