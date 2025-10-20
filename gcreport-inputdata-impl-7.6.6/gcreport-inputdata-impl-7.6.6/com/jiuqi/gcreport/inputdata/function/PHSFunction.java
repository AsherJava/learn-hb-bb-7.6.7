/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.gcreport.common.nr.function.IGcFunction
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.gcreport.inputdata.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.nr.function.IGcFunction;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PHSFunction
extends Function
implements IGcFunction {
    private static final long serialVersionUID = 1L;
    private final String FUNCTION_NAME = "PHS";

    public String name() {
        return "PHS";
    }

    public String title() {
        return "\u5e73\u8861\u6570";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 3;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        ExecutorContext executorContext = ((QueryContext)context).getExeContext();
        if (executorContext instanceof GcReportExceutorContext) {
            Double phsValue = ((GcReportExceutorContext)executorContext).getPhsValue();
            return phsValue;
        }
        return 0.0;
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)0)).append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u5e73\u8861\u6570").append(StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u6c42\u5e73\u8861\u6570\u503c ").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("PHS()").append(StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }
}

