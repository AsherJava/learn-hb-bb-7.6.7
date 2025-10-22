/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.function.Statistics
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.function.Statistics;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Stdev
extends Statistics
implements IReportFunction {
    private static final long serialVersionUID = 2210203755703563613L;

    public String name() {
        return "STDEV";
    }

    public String title() {
        return "\u8ba1\u7b97\u57fa\u4e8e\u7ed9\u5b9a\u6837\u672c\u7684\u6807\u51c6\u504f\u5dee\uff08\u5ffd\u7565\u975e\u6570\u503c\u578b\u6307\u6807\uff09";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 3;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        boolean isNull = true;
        ArrayList<Number> valList = new ArrayList<Number>();
        Iterator<IASTNode> var6 = parameters.iterator();
        block4: while (true) {
            if (!var6.hasNext()) {
                if (isNull) {
                    return null;
                }
                return this.stdDev(valList.toArray(new Number[1]));
            }
            IASTNode p = var6.next();
            Object pVal = p.evaluate(context);
            if (pVal == null) continue;
            int pType = this.typeOf(context, p, pVal);
            switch (pType) {
                case 3: 
                case 10: {
                    isNull = false;
                    valList.add((Number)pVal);
                    break;
                }
                case 11: {
                    ArrayData arr = (ArrayData)pVal;
                    for (Object item : arr) {
                        if (item == null || !(item instanceof Number)) continue;
                        isNull = false;
                        valList.add((Double)item);
                    }
                    continue block4;
                }
            }
        }
    }

    public boolean support(Language lang) {
        return lang == Language.FORMULA || lang == Language.EXPLAIN || lang == Language.EXCEL;
    }

    private Double stdDev(Number[] values) {
        double avg = this.avg(values);
        double sum = 0.0;
        for (Number value : values) {
            sum += Math.pow(value.doubleValue() - avg, 2.0);
        }
        if (values.length < 2) {
            return null;
        }
        double variance = sum / (double)(values.length - 1);
        return Math.sqrt(variance);
    }

    private double avg(Number[] values) {
        double sum = 0.0;
        for (Number value : values) {
            sum += value.doubleValue();
        }
        return sum / (double)values.length;
    }
}

