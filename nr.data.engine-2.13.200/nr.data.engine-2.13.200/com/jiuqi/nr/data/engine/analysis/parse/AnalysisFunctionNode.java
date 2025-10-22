/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.executors.StatUnit
 */
package com.jiuqi.nr.data.engine.analysis.parse;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.StatUnit;
import com.jiuqi.nr.data.engine.analysis.exe.AnalysisContext;
import com.jiuqi.nr.data.engine.summary.parse.func.IStatistic;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalysisFunctionNode
extends FunctionNode {
    private static final Logger logger = LoggerFactory.getLogger(AnalysisFunctionNode.class);
    private static final long serialVersionUID = -2768828222248241734L;
    public static final LongAdder nextUniqueNumber = new LongAdder();
    private long uniqueNum;

    public AnalysisFunctionNode(Token token, IFunction function, List<IASTNode> params) {
        super(token, function, params);
        nextUniqueNumber.increment();
        this.uniqueNum = nextUniqueNumber.longValue();
    }

    public Object evaluate(IContext context) throws SyntaxException {
        try {
            Object value = super.evaluate(context);
            if (context instanceof AnalysisContext) {
                int type;
                AnalysisContext aContext = (AnalysisContext)context;
                StatUnit statUnit = aContext.getStatUnitMap().get(this.uniqueNum);
                if (statUnit == null) {
                    IStatistic statFunc = (IStatistic)this.function;
                    statUnit = statFunc.createStatUnit(context, this.parameters);
                    aContext.getStatUnitMap().put(this.uniqueNum, statUnit);
                }
                if ((type = this.getType(context)) == 11) {
                    statUnit.statistic(AbstractData.valueOf((Object)value, (int)((IASTNode)this.parameters.get(0)).getType(context)));
                } else {
                    statUnit.statistic(AbstractData.valueOf((Object)value, (int)type));
                }
                return statUnit.getResult().getAsObject();
            }
            return value;
        }
        catch (DataTypeException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}

