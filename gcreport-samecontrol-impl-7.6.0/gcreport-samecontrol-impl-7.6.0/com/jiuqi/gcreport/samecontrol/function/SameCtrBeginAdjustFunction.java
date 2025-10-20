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
 *  com.jiuqi.common.base.util.SpringContextUtils
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
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.common.nr.function.INrFunction;
import com.jiuqi.gcreport.samecontrol.function.SameCtrBeginAdjustOffsetFunction;
import com.jiuqi.gcreport.samecontrol.function.SameCtrlBeginAdjustLastYearEndFunction;
import com.jiuqi.gcreport.samecontrol.function.SameCtrlBeginAdjustOrgChangFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SameCtrBeginAdjustFunction
extends Function
implements INrFunction {
    public SameCtrBeginAdjustFunction() {
        this.parameters().add(new Parameter("originalFormula", 6, "\u539f12\u6708\u6570\u516c\u5f0f"));
        this.parameters().add(new Parameter("lastYearEndZbCode", 6, "\u4e0a\u5e74\u5e74\u672b\u6570\u6307\u6807"));
        this.parameters().add(new Parameter("subjectCode", 6, "\u79d1\u76ee\u4ee3\u7801", true));
    }

    public String name() {
        return "QCTZ";
    }

    public String title() {
        return "\u540c\u63a7\u3001\u975e\u540c\u63a7\u8c03\u6574\u671f\u521d\u6570\u8fd0\u7b97\u516c\u5f0f\u51fd\u6570";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) {
        return 3;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Double evalute(IContext iContext, List<IASTNode> list) throws SyntaxException {
        String originalFormula = (String)list.get(0).evaluate(iContext);
        String lastYearEndZbCode = (String)list.get(1).evaluate(iContext);
        String subjectExpression = list.size() > 2 ? (String)list.get(2).evaluate(iContext) : null;
        SameCtrBeginAdjustOffsetFunction beginAdjustOffsetFunction = new SameCtrBeginAdjustOffsetFunction();
        SameCtrlBeginAdjustLastYearEndFunction beginAdjustLastYearEndFieldFunction = (SameCtrlBeginAdjustLastYearEndFunction)((Object)SpringContextUtils.getBean(SameCtrlBeginAdjustLastYearEndFunction.class));
        SameCtrlBeginAdjustOrgChangFunction beginAdjustOrgChangFunction = (SameCtrlBeginAdjustOrgChangFunction)((Object)SpringContextUtils.getBean(SameCtrlBeginAdjustOrgChangFunction.class));
        double result = 0.0;
        result += beginAdjustLastYearEndFieldFunction.calResult((QueryContext)iContext, originalFormula, lastYearEndZbCode);
        result += beginAdjustOrgChangFunction.calResult((QueryContext)iContext, lastYearEndZbCode);
        return result += beginAdjustOffsetFunction.calcResult((QueryContext)iContext, subjectExpression);
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append("\uff1b").append("\u540c\u63a7\u3001\u975e\u540c\u63a7\u8c03\u6574\u671f\u521d\u6570").append(StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u539f12\u6708\u6570\u516c\u5f0f\u4e3axxx,\u4e0a\u5e74\u5e74\u672b\u6570\u6307\u6807\u4e3axx,\u5408\u5e76\u79d1\u76ee\u4ee3\u7801\u4e3a1231\u65f6\u7684\u6570\u636e\u7ed3\u679c\u3002 ").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("QCTZ('xxx','xx','1231')").append(StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }
}

