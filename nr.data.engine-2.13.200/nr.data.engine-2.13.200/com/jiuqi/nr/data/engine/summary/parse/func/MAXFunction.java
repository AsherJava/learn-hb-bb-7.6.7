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
 *  com.jiuqi.np.dataengine.executors.StatItem
 *  com.jiuqi.np.dataengine.executors.StatUnit
 */
package com.jiuqi.nr.data.engine.summary.parse.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.executors.StatUnit;
import com.jiuqi.nr.data.engine.summary.parse.func.IStatistic;
import java.util.List;

public class MAXFunction
extends Function
implements IStatistic {
    private static final long serialVersionUID = -3196668252608939610L;

    public MAXFunction() {
        this.parameters().add(new Parameter("data", 0, "\u8981\u7edf\u8ba1\u7684\u6307\u6807"));
    }

    public String name() {
        return "MAX";
    }

    public String title() {
        return "\u6700\u5927\u503c";
    }

    public String[] aliases() {
        return new String[]{"LAST"};
    }

    public int getResultType(IContext paramIContext, List<IASTNode> paramList) throws SyntaxException {
        return paramList.get(0).getType(paramIContext);
    }

    public boolean support(Language lang) {
        return true;
    }

    public String category() {
        return "\u7edf\u8ba1\u51fd\u6570";
    }

    public Object evalute(IContext paramIContext, List<IASTNode> paramList) throws SyntaxException {
        return paramList.get(0).evaluate(paramIContext);
    }

    protected void toSQL(IContext paramIContext, List<IASTNode> paramList, StringBuilder paramStringBuilder, ISQLInfo paramISQLInfo) throws InterpretException {
        paramStringBuilder.append("MAX(");
        paramStringBuilder.append(paramList.get(0).interpret(paramIContext, Language.SQL, (Object)paramISQLInfo));
        paramStringBuilder.append(")");
    }

    @Override
    public StatUnit createStatUnit(IContext paramIContext, List<IASTNode> paramList) throws SyntaxException {
        return StatItem.createStatUnit((int)4, (int)paramList.get(0).getType(paramIContext));
    }
}

