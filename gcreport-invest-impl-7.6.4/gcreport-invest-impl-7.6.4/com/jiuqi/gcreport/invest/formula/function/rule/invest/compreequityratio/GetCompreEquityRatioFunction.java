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
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.gcreport.common.cache.formula.IFunctionCache
 *  com.jiuqi.gcreport.common.nr.function.IGcFunction
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.gcreport.invest.formula.function.rule.invest.compreequityratio;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.cache.formula.IFunctionCache;
import com.jiuqi.gcreport.common.nr.function.IGcFunction;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.invest.formula.function.rule.invest.compreequityratio.InvestCacheCalculator;
import com.jiuqi.gcreport.invest.formula.function.rule.invest.compreequityratio.InvestCalculator;
import com.jiuqi.gcreport.invest.formula.function.rule.invest.compreequityratio.InvestFunctionException;
import com.jiuqi.gcreport.invest.investbill.dao.InvestBillDao;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GetCompreEquityRatioFunction
extends Function
implements IFunctionCache,
IGcFunction {
    private static final long serialVersionUID = 1L;
    public final String FUNCTION_NAME = "GetCompreEquityRatio";
    private transient Logger logger = LoggerFactory.getLogger(GetCompreEquityRatioFunction.class);
    private static transient ThreadLocal<InvestCacheCalculator> investCalculatorThreadLocal = new ThreadLocal();

    public String name() {
        return "GetCompreEquityRatio";
    }

    public String title() {
        return "\u5f53\u524d\u5408\u5e76\u5c42\u7ea7\u5bf9\u6295\u8d44\u5355\u4f4d\u7efc\u5408\u6301\u80a1\u6bd4\u4f8b\u51fd\u6570";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 3;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext queryContext = (QueryContext)context;
        GcReportExceutorContext executorContext = (GcReportExceutorContext)queryContext.getExeContext();
        DefaultTableEntity masterData = executorContext.getData();
        DimensionValueSet dimensionValueSet = queryContext.getMasterKeys();
        String mergeOrgCode = executorContext.getOrgId();
        String periodString = (String)dimensionValueSet.getValue("DATATIME");
        YearPeriodDO periodUtil = YearPeriodUtil.transform(null, (String)periodString);
        String baseUnitCode = this.getBaseUnit(periodUtil, mergeOrgCode);
        String unitCode = (String)masterData.getFieldValue("UNITCODE");
        if (StringUtils.isEmpty((String)baseUnitCode)) {
            return 0.0;
        }
        if (baseUnitCode.equals(unitCode)) {
            return 100.0;
        }
        try {
            List<Map<String, Object>> records = this.queryData(periodUtil, mergeOrgCode);
            InvestCalculator investCalculator = this.getInvestCalculator(records);
            BigDecimal rate = BigDecimal.valueOf(investCalculator.getRate(unitCode));
            String cycleUnitCode = this.getCycleUnitCode(records, unitCode);
            if (!StringUtils.isEmpty((String)cycleUnitCode)) {
                BigDecimal cycleInvestRate = this.getCycleInvestRate(baseUnitCode, unitCode, records, cycleUnitCode);
                rate = rate.add(cycleInvestRate);
            }
            this.logger.info("[{}]\u7efc\u5408\u6301\u80a1\u6bd4\u4f8b\u8ba1\u7b97\u5b8c\u6210\uff1a[{}] ", (Object)unitCode, (Object)rate);
            return rate.setScale(16, 4).doubleValue() * 100.0;
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return 0.0;
        }
    }

    private BigDecimal getCycleInvestRate(String baseUnitCode, String unitCode, List<Map<String, Object>> records, String cycleUnitCode) {
        Map<String, Object> base2CurrentUnitRecord = null;
        Map<String, Object> currentUnit2CycleUnitRecord = null;
        Map<String, Object> cycleUnit2CurrentUnitRecord = null;
        for (Map<String, Object> record : records) {
            if (baseUnitCode.equals(record.get("UNITCODE")) && unitCode.equals(record.get("INVESTEDUNIT"))) {
                base2CurrentUnitRecord = record;
            }
            if (unitCode.equals(record.get("UNITCODE")) && cycleUnitCode.equals(record.get("INVESTEDUNIT"))) {
                currentUnit2CycleUnitRecord = record;
            }
            if (!cycleUnitCode.equals(record.get("UNITCODE")) || !unitCode.equals(record.get("INVESTEDUNIT"))) continue;
            cycleUnit2CurrentUnitRecord = record;
        }
        if (base2CurrentUnitRecord == null) {
            return BigDecimal.ZERO;
        }
        if (currentUnit2CycleUnitRecord == null) {
            return BigDecimal.ZERO;
        }
        if (cycleUnit2CurrentUnitRecord == null) {
            return BigDecimal.ZERO;
        }
        Double baseEquityRatio = ConverterUtils.getAsDouble(base2CurrentUnitRecord.get("ENDEQUITYRATIO")) / 100.0;
        Double currentEquityRatio = ConverterUtils.getAsDouble(currentUnit2CycleUnitRecord.get("ENDEQUITYRATIO")) / 100.0;
        Double cycleEquityRatio = ConverterUtils.getAsDouble(cycleUnit2CurrentUnitRecord.get("ENDEQUITYRATIO")) / 100.0;
        BigDecimal cycleRatio = BigDecimal.valueOf(baseEquityRatio).multiply(BigDecimal.valueOf(currentEquityRatio)).multiply(BigDecimal.valueOf(cycleEquityRatio));
        this.logger.info("[{}]\u5355\u4f4d\u5b58\u5728\u5faa\u73af\u6295\u8d44\u8def\u5f84\uff0c\u5faa\u73af\u6295\u8d44\u8def\u5f84\u4fe1\u606f\uff1a[{}-->\u3010\u6301\u80a1\u6bd4\u4f8b{}\u3011{}-->\u3010\u6301\u80a1\u6bd4\u4f8b{}\u3011{}-->\u3010\u6301\u80a1\u6bd4\u4f8b{}\u3011{}],\u6b64\u8def\u5f84\u6301\u80a1\u6bd4\u4f8b\uff1a[{}]", unitCode, baseUnitCode, baseEquityRatio, unitCode, currentEquityRatio, cycleUnitCode, cycleEquityRatio, unitCode, cycleRatio);
        return cycleRatio;
    }

    private String getCycleUnitCode(List<Map<String, Object>> records, String unitCode) {
        if (CollectionUtils.isEmpty(records)) {
            return null;
        }
        HashSet<String> unitCodeInvestSet = new HashSet<String>();
        HashSet<String> unitCodeInvestedSet = new HashSet<String>();
        for (Map<String, Object> record : records) {
            Object investUnitObj = record.get("UNITCODE");
            if (investUnitObj == null) continue;
            String investUnit = investUnitObj.toString();
            Object investedUnitObj = record.get("INVESTEDUNIT");
            if (investedUnitObj == null) continue;
            String investedUnit = investedUnitObj.toString();
            if (unitCode.equals(investedUnit)) {
                if (unitCodeInvestSet.contains(investUnit)) {
                    return investUnit;
                }
                unitCodeInvestedSet.add(investUnit);
                continue;
            }
            if (!unitCode.equals(investUnit)) continue;
            if (unitCodeInvestedSet.contains(investedUnit)) {
                return investedUnit;
            }
            unitCodeInvestSet.add(investedUnit);
        }
        return null;
    }

    private String getBaseUnit(YearPeriodDO periodUtil, String mergeOrgCode) {
        if (null == investCalculatorThreadLocal.get()) {
            return this.getBaseUnitFromOrgTool(periodUtil, mergeOrgCode);
        }
        InvestCacheCalculator cacheCalculator = investCalculatorThreadLocal.get();
        if (StringUtils.isEmpty((String)cacheCalculator.getBaseUnitCode())) {
            String baseUnitCode = this.getBaseUnitFromOrgTool(periodUtil, mergeOrgCode);
            cacheCalculator.setBaseUnitCode(baseUnitCode);
        }
        if (cacheCalculator.isDoCalcFail()) {
            return null;
        }
        return cacheCalculator.getBaseUnitCode();
    }

    private String getBaseUnitFromOrgTool(YearPeriodDO periodUtil, String mergeOrgCode) {
        YearPeriodObject yp = new YearPeriodObject(null, periodUtil.toString());
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)"MD_ORG_CORPORATE", (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        String baseUnitCode = orgTool.getDeepestBaseUnitId(mergeOrgCode);
        return baseUnitCode;
    }

    private InvestCalculator getInvestCalculator(List<Map<String, Object>> records) throws SyntaxException {
        try {
            if (null == investCalculatorThreadLocal.get()) {
                return this.getInvestCalculatorFromDb(records);
            }
            InvestCacheCalculator cacheCalculator = investCalculatorThreadLocal.get();
            if (null == cacheCalculator.getInvestCalculator()) {
                InvestCalculator investCalculator = this.getInvestCalculatorFromDb(records);
                cacheCalculator.setInvestCalculator(investCalculator);
            }
            return cacheCalculator.getInvestCalculator();
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw new SyntaxException((Throwable)e);
        }
    }

    private InvestCalculator getInvestCalculatorFromDb(List<Map<String, Object>> records) throws InvestFunctionException {
        InvestCalculator calc = new InvestCalculator();
        for (Map<String, Object> record : records) {
            String unitCode = (String)record.get("UNITCODE");
            String oppUnitCode = (String)record.get("INVESTEDUNIT");
            Double endEquityRatio = ConverterUtils.getAsDouble((Object)record.get("ENDEQUITYRATIO"));
            if (null == endEquityRatio) continue;
            calc.addInvest(unitCode, oppUnitCode, endEquityRatio / 100.0);
        }
        return calc;
    }

    private List<Map<String, Object>> queryData(YearPeriodDO periodUtil, String mergeOrgCode) {
        HashMap<String, Object> params = new HashMap<String, Object>(16);
        params.put("pageSize", -1);
        params.put("pageNum", -1);
        params.put("mergeUnit", mergeOrgCode);
        params.put("acctYear", String.valueOf(periodUtil.getYear()));
        List<Map<String, Object>> records = ((InvestBillDao)SpringContextUtils.getBean(InvestBillDao.class)).listInvestBillsByPaging(params);
        return records;
    }

    public void enableCache() {
        investCalculatorThreadLocal.set(new InvestCacheCalculator());
    }

    public void releaseCache() {
        investCalculatorThreadLocal.remove();
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append("\uff1b").append("\u7efc\u5408\u6301\u80a1\u6bd4\u4f8b").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u53d6\u5408\u5e76\u5c42\u7ea7\u5bf9\u6295\u8d44\u5355\u4f4d\u7efc\u5408\u6301\u80a1\u6bd4\u4f8b").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GetCompreEquityRatio()").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }
}

