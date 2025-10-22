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
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.gcreport.nr.impl.function.impl.floatcopyT;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.gcreport.nr.impl.function.NrFunction;
import com.jiuqi.gcreport.nr.impl.function.impl.floatcopyT.CrossTaskFloatCopyExecutor;
import com.jiuqi.gcreport.nr.impl.function.impl.floatcopyT.CrossTaskFloatCopyParaParser;
import com.jiuqi.gcreport.nr.impl.function.impl.util.CrossTaskFunctionUtil;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CrossTaskFloatCopyFunction
extends NrFunction
implements IReportFunction {
    private static final transient Logger logger = LoggerFactory.getLogger(CrossTaskFloatCopyFunction.class);
    private static final long serialVersionUID = 3565622721625053981L;

    public CrossTaskFloatCopyFunction() {
        this.parameters().add(new Parameter("srcRelaExp", 6, "\u6e90\u6d6e\u52a8\u884c\u5173\u8054\u7684\u8868\u8fbe\u5f0f"));
        this.parameters().add(new Parameter("destRelaExp", 6, "\u76ee\u6807\u6d6e\u52a8\u884c\u5173\u8054\u7684\u8868\u8fbe\u5f0f"));
        this.parameters().add(new Parameter("srcFilter", 6, "\u6e90\u6d6e\u52a8\u884c\u8fc7\u6ee4\u6761\u4ef6"));
        this.parameters().add(new Parameter("assignExp", 6, "\u8d4b\u503c\u8868\u8fbe\u5f0f"));
        this.parameters().add(new Parameter("clearDestBeforeCopy", 1, "\u590d\u5236\u524d\u662f\u5426\u6e05\u9664\u76ee\u6807\u6d6e\u52a8\u884c\u7684\u6570\u636e"));
        this.parameters().add(new Parameter("srcPeriodType", 6, "\u6e90\u6d6e\u52a8\u884c\u65f6\u671f\u7c7b\u578b"));
        this.parameters().add(new Parameter("convertZbs", 6, "\u6839\u636e\u540d\u79f0\u8f6c\u6362\u6307\u6807"));
        this.parameters().add(new Parameter("periodOffset", 3, "\u65f6\u671f\u504f\u79fb\u91cf", true));
        this.parameters().add(new Parameter("period", 3, "\u6307\u5b9a\u65f6\u671f", true));
        this.parameters().add(new Parameter("orgCode", 6, "\u6307\u5b9a\u5355\u4f4d", true));
    }

    public String name() {
        return "FloatCopyT";
    }

    public String title() {
        return "\u8de8\u4efb\u52a1\u62f7\u8d1d\u6d6e\u52a8\u884c\u6570\u636e";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 0;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (parameters.size() < 9) {
            return super.validate(context, parameters);
        }
        CrossTaskFunctionUtil.valiatePeriodParamter((QueryContext)context, parameters.get(0), parameters.get(8));
        return super.validate(context, parameters);
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext qContext = (QueryContext)context;
        try {
            CrossTaskFloatCopyParaParser parser = new CrossTaskFloatCopyParaParser(qContext, parameters);
            if (!parser.isValid()) {
                return false;
            }
            return new CrossTaskFloatCopyExecutor(parser, this).execute(qContext, parameters);
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
        buffer.append("    ").append("    ").append("FloatCopyT(\"BB005[2,25]@21\",\"[2,100]\",\"\",\"[2,25]=[2,100];[2,1]=[2,1];[2,2]=[2,2];\",TRUE,\"N\",\"[2,25];[2,1]\")").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u5907\u6ce8: ").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("1.\"@\"\u7b26\u53f7\u540e\u53ea\u652f\u6301\u6570\u5b57; ").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("2.\u65f6\u671f\u7c7b\u578b\u4f7f\u7528\u76ee\u6807\u65b9\u6848\u7684\u65f6\u671f\u7c7b\u578b\uff0c\u4f8b:\u6708\u62a5\u53d6\u5e74\u62a5\uff0c\u4f7f\u7528\u5e74\u62a5\u7684\u65f6\u671f\u7c7b\u578bN;(\u5176\u4ed6\u65f6\u671f\u7c7b\u578b:\u6708\u62a5(Y),\u5b63\u62a5(J),\u534a\u5e74\u62a5(H)) ").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("3.\u8f6c\u6362\u6307\u6807\u4e4b\u95f4\u4f7f\u7528\";\"\u9694\u79bb; ").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("4.\u4e1a\u52a1\u4e3b\u952e\u5217\u6570\u636e\u4e0d\u80fd\u4e3a\u7a7a; ").append(StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }
}

