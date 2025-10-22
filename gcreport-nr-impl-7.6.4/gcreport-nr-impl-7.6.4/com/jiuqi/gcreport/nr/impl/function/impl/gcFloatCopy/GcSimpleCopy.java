/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.gcreport.nr.impl.function.impl.gcFloatCopy;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.gcreport.nr.impl.function.NrFunction;
import com.jiuqi.gcreport.nr.impl.function.impl.gcFloatCopy.GcSimpleCopyExecutor;
import com.jiuqi.gcreport.nr.impl.function.impl.gcFloatCopy.GcSimpleCopyParaParser;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GcSimpleCopy
extends NrFunction {
    private static final long serialVersionUID = 3565622721625053981L;

    public GcSimpleCopy() {
        this.parameters().add(new Parameter("reportName", 6, "\u6e90\u8868\u5355\u4ee3\u7801\uff0c\u53ef\u901a\u8fc7@\u522b\u540d\u6765\u6307\u5b9a\u8de8\u4efb\u52a1"));
        this.parameters().add(new Parameter("clearDestBeforeCopy", 1, "\u590d\u5236\u524d\u662f\u5426\u6e05\u9664\u76ee\u6807\u6d6e\u52a8\u884c\u7684\u6570\u636e"));
        this.parameters().add(new Parameter("periodOffset", 3, "\u65f6\u671f\u504f\u79fb\u91cf"));
        this.parameters().add(new Parameter("srcRelaExp", 6, "\u6e90\u6d6e\u52a8\u884c\u6307\u6807\u5217\u8868"));
        this.parameters().add(new Parameter("destRelaExp", 6, "\u76ee\u6807\u6d6e\u52a8\u884c\u6307\u6807\u5217\u8868"));
        this.parameters().add(new Parameter("srcFilter", 6, "\u6e90\u6d6e\u52a8\u884c\u8fc7\u6ee4\u6761\u4ef6"));
        this.parameters().add(new Parameter("assignExp", 6, "\u4fee\u6539\u65f6\u8d4b\u503c\u8868\u8fbe\u5f0f(\u8bbe\u7f6e\u590d\u5236\u6620\u5c04\u89c4\u5219,A[AF]=B[BF]<\u4eceA[AF]\u590d\u5236\u5230B[BF]\u5b57\u6bb5>;field<\u4ece\u5b57\u6bb5\u590d\u5236\u5230\u65b0\u8bb0\u5f55\u76f8\u540c\u5b57\u6bb5>)"));
        this.parameters().add(new Parameter("insertExp", 6, "\u65b0\u589e\u884c\u8d4b\u503c\u8868\u8fbe\u5f0f(\u8bbe\u7f6e\u590d\u5236\u6620\u5c04\u89c4\u5219,A[AF]=B[BF]<\u4eceA[AF]\u590d\u5236\u5230B[BF]\u5b57\u6bb5>;field<\u4ece\u5b57\u6bb5\u590d\u5236\u5230\u65b0\u8bb0\u5f55\u76f8\u540c\u5b57\u6bb5>)"));
        this.parameters().add(new Parameter("orgCode", 6, "\u6307\u5b9a\u5355\u4f4d,\u53ef\u7701\u7565", true));
        this.parameters().add(new Parameter("period", 6, "\u6307\u5b9a\u65f6\u671f\uff0c\u53ef\u7701\u7565", true));
    }

    public String name() {
        return "GcFloatCopy";
    }

    public String title() {
        return "\u5185\u90e8\u8868\u548c\u6d6e\u52a8\u884c\u4e4b\u95f4\u6570\u636e\u62f7\u8d1d";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 0;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext qContext = (QueryContext)context;
        try {
            GcSimpleCopyParaParser parser = new GcSimpleCopyParaParser(qContext, parameters);
            if (!parser.isValid()) {
                return false;
            }
            return new GcSimpleCopyExecutor(parser).execute(qContext);
        }
        catch (Exception e) {
            throw new SyntaxException((Throwable)e);
        }
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)0)).append("\uff1a").append(DataType.toString((int)1)).append("\uff1b").append("\u6267\u884c\u662f\u5426\u6210\u529f").append(StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u8de8\u4efb\u52a1\u62f7\u8d1d\u6d6e\u52a8\u884c\u6570\u636e ").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GCFLOATCOPY(\"L8ZCPPP3_F2@1\",").append(StringUtils.LINE_SEPARATOR);
        buffer.append("false,").append(StringUtils.LINE_SEPARATOR);
        buffer.append("0,").append(StringUtils.LINE_SEPARATOR);
        buffer.append("\"L8ZCPPP3_F2_F2[L8ZCPPP3_F2_F2_001];L8ZCPPP3_F2_F2[L8ZCPPP3_F2_F2_002]\",").append(StringUtils.LINE_SEPARATOR);
        buffer.append("\"L8ZNTPIS_F2[L8ZNTPIS_F2_001];L8ZNTPIS_F2[L8ZNTPIS_F2_002]\",").append(StringUtils.LINE_SEPARATOR);
        buffer.append("\"L8ZCPPP3_F2_F2[L8ZCPPP3_F2_F2_002]=\\\"10310101\\\"\",").append(StringUtils.LINE_SEPARATOR);
        buffer.append("\"L8ZCPPP3_F2_F2[L8ZCPPP3_F2_F2_001]=L8ZNTPIS_F2[L8ZNTPIS_F2_001];L8ZCPPP3_F2_F2[L8ZCPPP3_F2_F2_002]=L8ZNTPIS_F2[L8ZNTPIS_F2_002]\",").append(StringUtils.LINE_SEPARATOR);
        buffer.append("\"L8ZCPPP3_F2_F2[L8ZCPPP3_F2_F2_001]=L8ZNTPIS_F2[L8ZNTPIS_F2_001];L8ZCPPP3_F2_F2[L8ZCPPP3_F2_F2_002]=L8ZNTPIS_F2[L8ZNTPIS_F2_002]\",").append(StringUtils.LINE_SEPARATOR);
        buffer.append("\"1066833\",").append(StringUtils.LINE_SEPARATOR);
        buffer.append("\"2022Y0001\")").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u5907\u6ce8: ").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("1.\"@\"\u7b26\u53f7\u540e\u53ea\u652f\u6301\u6570\u5b57; ").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("2.\u8f6c\u6362\u6307\u6807\u4e4b\u95f4\u4f7f\u7528\";\"\u9694\u79bb; ").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("3.\u6307\u5b9a\u65f6\u671f\u65f6\u9700\u89c4\u8303\u53c2\u6570\u683c\u5f0f,\u59822022Y0001\u30012022N0001").append(StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }
}

