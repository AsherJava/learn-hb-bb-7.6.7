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
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
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
import com.jiuqi.gcreport.samecontrol.function.SameCtrlFormulaExecutionTool;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlChgOrgService;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SameCtrlChgCurrentMonthFieldFunction
extends Function
implements INrFunction {
    @Autowired
    private SameCtrlChgOrgService sameCtrlChgOrgService;
    private static final transient Logger logger = LoggerFactory.getLogger(SameCtrlChgCurrentMonthFieldFunction.class);

    public SameCtrlChgCurrentMonthFieldFunction() {
        this.parameters().add(new Parameter("currentMonthZbCode", 6, "\u6307\u6807\u6216\u8005\u884c\u5217\u53f7"));
    }

    public String name() {
        return "CZTZ";
    }

    public String title() {
        return "\u540c\u63a7\u5355\u4f4d\u5f53\u671f\u6708\u62a5\u6307\u6807\u6570\u636e";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) {
        return 3;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> list) throws SyntaxException {
        QueryContext queryContext = (QueryContext)iContext;
        DimensionValueSet dimensionValueSet = queryContext.getMasterKeys();
        String periodSrt = (String)dimensionValueSet.getValue("DATATIME");
        String orgCode = (String)dimensionValueSet.getValue("MD_ORG");
        try {
            boolean isSameCtrlChgOrg = this.sameCtrlChgOrgService.contasinsChangeOrg(orgCode, periodSrt);
            if (isSameCtrlChgOrg) {
                String currentMonthZbCode = (String)list.get(0).evaluate(iContext);
                return SameCtrlFormulaExecutionTool.exaluateFormulaAmt(queryContext, dimensionValueSet, currentMonthZbCode);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return 0.0;
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder stringBuilder = new StringBuilder(supperDescription);
        stringBuilder.append("\u8fd4\u56de\u503c\uff1a").append(StringUtils.LINE_SEPARATOR);
        stringBuilder.append("    ").append(DataType.toExpression((int)0)).append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u540c\u63a7\u5355\u4f4d\u5f53\u671f\u6708\u62a5\u6307\u6807\u6570\u636e").append(StringUtils.LINE_SEPARATOR);
        stringBuilder.append("\u793a\u4f8b\uff1a").append(StringUtils.LINE_SEPARATOR);
        stringBuilder.append("    ").append("\u573a\u666f\uff1a").append(StringUtils.LINE_SEPARATOR);
        stringBuilder.append("    ").append("    ").append("\u5224\u65ad\u662f\u5426\u662f\u540c\u63a7\u53d8\u52a8\u5355\u4f4d\uff0c\u8fd4\u56de\u5f53\u671f\u6708\u62a5\u6307\u6807\u6570\u636e\uff0c\u5426\u5219\u8fd4\u56de0").append(StringUtils.LINE_SEPARATOR);
        stringBuilder.append("    ").append("\u516c\u5f0f\uff1a").append(StringUtils.LINE_SEPARATOR);
        stringBuilder.append("    ").append("    ").append("CZTZ('ZCOX_YB01[A01]')").append(StringUtils.LINE_SEPARATOR);
        stringBuilder.append("    ").append("    ").append("CZTZ('\u62a5\u8868\u6807\u8bc6[2,1]')").append(StringUtils.LINE_SEPARATOR);
        return stringBuilder.toString();
    }
}

