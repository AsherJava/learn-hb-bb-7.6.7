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
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.gcreport.common.nr.function.INrFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.gcreport.samecontrol.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.gcreport.common.nr.function.INrFunction;
import com.jiuqi.gcreport.samecontrol.function.SameCtrlZbValueExecutionProcessor;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SameCtrlLastYearAdjustOrgChangFunction
extends Function
implements INrFunction {
    public SameCtrlLastYearAdjustOrgChangFunction() {
        this.parameters().add(new Parameter("lastYearSamePeriodZbCode", 6, "\u4e0a\u5e74\u540c\u671f\u6570\u6307\u6807"));
    }

    public String name() {
        return "SNTZDWBD";
    }

    public String title() {
        return "\u4e0a\u5e74\u540c\u671f\u6570\u8c03\u6574\u8868\u4e2d\u5355\u4f4d\u8303\u56f4\u53d8\u52a8\u6570\u516c\u5f0f\u51fd\u6570";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) {
        return 3;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> list) throws SyntaxException {
        QueryContext queryContext = (QueryContext)iContext;
        String lastYearSamePeriodZbCode = (String)list.get(0).evaluate(iContext);
        return SameCtrlZbValueExecutionProcessor.calResult(queryContext, lastYearSamePeriodZbCode);
    }

    double calResult(QueryContext queryContext, String lastYearSamePeriodZbCode) {
        return SameCtrlZbValueExecutionProcessor.calResult(queryContext, lastYearSamePeriodZbCode);
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder stringBuilder = new StringBuilder(supperDescription);
        stringBuilder.append("\u8fd4\u56de\u503c\uff1a").append(StringUtils.LINE_SEPARATOR);
        stringBuilder.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append("\uff1b").append("\u975e\u5dee\u989d\u5355\u4f4d\uff0c\u8fd4\u56de0; \u5dee\u989d\u5355\u4f4d\uff0c\u8fd4\u56de\u8fd4\u56de\u5bf9\u5e94\u5408\u5e76\u5355\u4f4d\u5168\u5e74\u540c\u63a7\u6536\u8d2d\u53d8\u52a8\u5355\u4f4d\u7684\u5f53\u671f\u53c2\u65701\uff08\u4e0a\u5e74\u540c\u671f\u6570\u6307\u6807\uff09\u6307\u6807\u6570\u636e-\u5bf9\u5e94\u5408\u5e76\u5355\u4f4d\u662f\u5171\u540c\u4e0a\u7ea7\u7684\u5168\u5e74\u540c\u63a7\u53d8\u52a8\u5355\u4f4d\u7684\u5f53\u671f\u53c2\u65701\uff08\u4e0a\u5e74\u540c\u671f\u6570\u6307\u6807\uff09\u6307\u6807\u6570\u636e.").append(StringUtils.LINE_SEPARATOR);
        stringBuilder.append("\u793a\u4f8b\uff1a").append(StringUtils.LINE_SEPARATOR);
        stringBuilder.append("    ").append("\u573a\u666f\uff1a").append(StringUtils.LINE_SEPARATOR);
        stringBuilder.append("    ").append("    ").append("\u540c\u63a7\u3001\u975e\u540c\u63a7\u5904\u7406\u4ece\u53d8\u52a8\u5355\u4f4d\u53d6\u6570\u7684\u6307\u6807\u4ee3\u7801\u3002").append(StringUtils.LINE_SEPARATOR);
        stringBuilder.append("    ").append("\u516c\u5f0f\uff1a").append(StringUtils.LINE_SEPARATOR);
        stringBuilder.append("    ").append("    ").append("SNTZDWBD('ZCOX_YB01[A01]')").append(StringUtils.LINE_SEPARATOR);
        stringBuilder.append("    ").append("    ").append("SNTZDWBD('\u62a5\u8868\u6807\u8bc6[2,1]')").append(StringUtils.LINE_SEPARATOR);
        return stringBuilder.toString();
    }
}

