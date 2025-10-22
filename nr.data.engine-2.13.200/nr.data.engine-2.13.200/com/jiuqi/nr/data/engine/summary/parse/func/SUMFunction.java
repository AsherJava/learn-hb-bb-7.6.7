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

public class SUMFunction
extends Function
implements IStatistic {
    private static final long serialVersionUID = -3979907463488409689L;

    public SUMFunction() {
        this.parameters().add(new Parameter("data", 0, "\u8981\u7edf\u8ba1\u7684\u6307\u6807"));
    }

    public String name() {
        return "SUM";
    }

    public String title() {
        return "\u7d2f\u8ba1";
    }

    public int getResultType(IContext paramIContext, List<IASTNode> paramList) throws SyntaxException {
        int type = paramList.get(0).getType(paramIContext);
        if (type == 1) {
            type = 3;
        }
        return type;
    }

    public boolean support(Language lang) {
        return true;
    }

    public String category() {
        return "\u7edf\u8ba1\u51fd\u6570";
    }

    public Object evalute(IContext paramIContext, List<IASTNode> paramList) throws SyntaxException {
        int type = paramList.get(0).getType(paramIContext);
        Object value = paramList.get(0).evaluate(paramIContext);
        if (type == 1) {
            return (Boolean)value != false ? -1 : 0;
        }
        return value;
    }

    protected void toSQL(IContext paramIContext, List<IASTNode> paramList, StringBuilder paramStringBuilder, ISQLInfo paramISQLInfo) throws InterpretException {
        paramStringBuilder.append("SUM(");
        paramStringBuilder.append(paramList.get(0).interpret(paramIContext, Language.SQL, (Object)paramISQLInfo));
        paramStringBuilder.append(")");
    }

    @Override
    public StatUnit createStatUnit(IContext paramIContext, List<IASTNode> paramList) throws SyntaxException {
        return StatItem.createStatUnit((int)1, (int)this.getResultType(paramIContext, paramList));
    }
}

