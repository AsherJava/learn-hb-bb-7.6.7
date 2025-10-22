/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.cache.formula.IFunctionCache
 *  com.jiuqi.gcreport.common.nr.function.IGcFunction
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.engine.util.ReportExpressionEvalUtil
 *  com.jiuqi.nr.data.engine.util.ReportFormulaParseUtil
 */
package com.jiuqi.gcreport.calculate.formula.service.impl.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.cache.formula.IFunctionCache;
import com.jiuqi.gcreport.common.nr.function.IGcFunction;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.engine.util.ReportExpressionEvalUtil;
import com.jiuqi.nr.data.engine.util.ReportFormulaParseUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GetMergeUnitValueFunction
extends Function
implements IGcFunction,
IFunctionCache {
    private static final Logger logger = LoggerFactory.getLogger(GetMergeUnitValueFunction.class);
    private static ThreadLocal<Map<ArrayKey, Object>> functionCacheKey2ResultMapLocal = new ThreadLocal();

    public GetMergeUnitValueFunction() {
        this.parameters().add(new Parameter("unitCode", 6, "\u672c\u65b9\u5355\u4f4d\u5b57\u6bb5", false));
        this.parameters().add(new Parameter("oppUnitCode", 6, "\u5bf9\u65b9\u5355\u4f4d\u5b57\u6bb5", false));
        this.parameters().add(new Parameter("filed", 6, "\u6307\u6807\u4ee3\u7801\u6216\u8005\u5355\u4f4d\u7684\u5b57\u6bb5", false));
    }

    public String name() {
        return "GetMergeUnitValue";
    }

    public String title() {
        return "\u83b7\u53d6\u672c\u5bf9\u65b9\u5355\u4f4d\u7684\u5171\u540c\u4e0a\u7ea7\u5355\u4f4d\u7684\u5b57\u6bb5\u503c\u6216\u8005\u6307\u6807\u503c";
    }

    public int getResultType(IContext context, List<IASTNode> list) throws SyntaxException {
        return 0;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> parameters) throws SyntaxException {
        String param = (String)parameters.get(2).evaluate(null);
        QueryContext queryContext = (QueryContext)iContext;
        GcReportExceutorContext executorContext = (GcReportExceutorContext)queryContext.getExeContext();
        DefaultTableEntity masterData = executorContext.getData();
        String[] split = param.split("\\[");
        DimensionValueSet masterKeys = queryContext.getMasterKeys();
        Object result = null;
        if (StringUtils.isEmpty((String)split[0])) {
            param = param.substring(param.indexOf("[") + 1, param.indexOf("]"));
            String unitField = (String)parameters.get(0).evaluate(null);
            String oppUnitField = (String)parameters.get(1).evaluate(null);
            String unitcode = (String)masterData.getFields().get(unitField.toUpperCase());
            String oppUnitCode = (String)masterData.getFields().get(oppUnitField.toUpperCase());
            Integer acctYear = (Integer)masterData.getFields().get("ACCTYEAR");
            YearPeriodObject yp = new YearPeriodObject(acctYear.intValue(), 12);
            String orgType = null == masterKeys.getValue("MD_GCORGTYPE") ? "MD_ORG_CORPORATE" : (String)masterKeys.getValue("MD_GCORGTYPE");
            GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            GcOrgCacheVO unitVo = orgTool.getOrgByCode(unitcode);
            GcOrgCacheVO oppUnitVo = orgTool.getOrgByCode(oppUnitCode);
            if (null == unitVo || null == oppUnitVo) {
                return null;
            }
            GcOrgCacheVO commonUnit = orgTool.getCommonUnit(unitVo, oppUnitVo);
            Map fields = commonUnit.getFields();
            result = fields.get(param.toUpperCase());
        } else {
            try {
                ArrayKey functionCacheKey = this.getFunctionCacheKey(parameters, masterKeys);
                Map<ArrayKey, Object> functionCacheKey2ValueMap = functionCacheKey2ResultMapLocal.get();
                if (null != functionCacheKey2ValueMap && functionCacheKey2ValueMap.containsKey(functionCacheKey)) {
                    Object cacheResult = functionCacheKey2ValueMap.get(functionCacheKey);
                    return cacheResult;
                }
                ReportFormulaParseUtil reportFormulaParseUtil = (ReportFormulaParseUtil)SpringContextUtils.getBean(ReportFormulaParseUtil.class);
                ReportExpressionEvalUtil reportExpressionEvalUtil = (ReportExpressionEvalUtil)SpringContextUtils.getBean(ReportExpressionEvalUtil.class);
                IExpression iExpression = reportFormulaParseUtil.parseFormula(queryContext.getExeContext(), param);
                AbstractData zbValue = reportExpressionEvalUtil.eval(queryContext.getExeContext(), (IASTNode)iExpression, masterKeys);
                result = zbValue.getAsObject();
                if (null != functionCacheKey2ValueMap) {
                    functionCacheKey2ValueMap.put(functionCacheKey, result);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }

    private ArrayKey getFunctionCacheKey(List<IASTNode> parameters, DimensionValueSet masterKeys) throws SyntaxException {
        String zbCode = (String)parameters.get(2).evaluate(null);
        ArrayList<String> keys = new ArrayList<String>();
        keys.add(zbCode);
        Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)masterKeys);
        dimensionSet.forEach((dimName, dimValue) -> keys.add(dimName + "@" + dimValue.getValue()));
        ArrayKey arrayKey = new ArrayKey(keys);
        return arrayKey;
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)0)).append("\uff1b").append("\u83b7\u53d6\u672c\u5bf9\u65b9\u5355\u4f4d\u7684\u5171\u540c\u4e0a\u7ea7\u5355\u4f4d\u7684\u5b57\u6bb5\u503c\u6216\u8005\u6307\u6807\u503c").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b1\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u53d6\u5408\u5e76\u5355\u4f4d\u7684\u5e01\u522b\u503c\u3002 ").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GetMergeUnitValue(\"unitCode\",\"oppUnitCode\",\"[CURRENCYID]\")").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b2\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u53d6\u6570\u636e\u5f55\u5165\u4e2d\u5408\u5e76\u5355\u4f4d\u7684\u6307\u6807\u503c\u3002 ").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("CommParentsValue(\"unitCode\",\"oppUnitCode\",\"ZCOX_YB01[E2]\")").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }

    public void enableCache() {
        functionCacheKey2ResultMapLocal.set(new HashMap());
    }

    public void releaseCache() {
        functionCacheKey2ResultMapLocal.remove();
    }
}

