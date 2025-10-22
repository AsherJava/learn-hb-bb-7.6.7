/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.data.engine.util.ReportExpressionEvalUtil
 *  com.jiuqi.nr.data.engine.util.ReportFormulaParseUtil
 */
package com.jiuqi.gcreport.samecontrol.function;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.data.engine.util.ReportExpressionEvalUtil;
import com.jiuqi.nr.data.engine.util.ReportFormulaParseUtil;
import java.math.BigDecimal;
import java.util.List;

public class SameCtrlFormulaExecutionTool {
    public static double exaluateFormulaAmt(QueryContext queryContext, DimensionValueSet dimensionValueSet, String formula) throws Exception {
        if (!StringUtils.isEmpty((String)formula)) {
            ReportFormulaParseUtil reportFormulaParseUtil = (ReportFormulaParseUtil)SpringContextUtils.getBean(ReportFormulaParseUtil.class);
            ReportExpressionEvalUtil reportExpressionEvalUtil = (ReportExpressionEvalUtil)SpringContextUtils.getBean(ReportExpressionEvalUtil.class);
            IExpression iExpression = reportFormulaParseUtil.parseFormula(queryContext.getExeContext(), formula);
            AbstractData abstractData = reportExpressionEvalUtil.eval(queryContext.getExeContext(), (IASTNode)iExpression, dimensionValueSet);
            return SameCtrlFormulaExecutionTool.getDoubleValue(abstractData);
        }
        return 0.0;
    }

    public static double evaluateSameCtrlZbValue(QueryContext queryContext, List<String> codeList, DimensionValueSet dimensionValueSet, String lastYearSamePeriodZbCode) throws Exception {
        double result = 0.0;
        for (String code : codeList) {
            DimensionValueSet dimensionValueSetNewCode = new DimensionValueSet(dimensionValueSet);
            dimensionValueSetNewCode.setValue("MD_ORG", (Object)code);
            result += SameCtrlFormulaExecutionTool.exaluateFormulaAmt(queryContext, dimensionValueSetNewCode, lastYearSamePeriodZbCode);
        }
        return result;
    }

    private static double getDoubleValue(AbstractData data) {
        if (data != null) {
            BigDecimal bigDecimal = data.getAsCurrency();
            bigDecimal = bigDecimal == null ? BigDecimal.ZERO : bigDecimal;
            double roundValue = NumberUtils.round((double)bigDecimal.doubleValue());
            return roundValue;
        }
        return 0.0;
    }
}

