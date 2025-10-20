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
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.nr.function.IGcFunction
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.nr.impl.function.GcReportSimpleExecutorContext
 *  com.jiuqi.gcreport.rate.impl.consts.RateTypeEnum
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.gcreport.conversion.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.nr.function.IGcFunction;
import com.jiuqi.gcreport.conversion.conversionrate.service.ConversionRateService;
import com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemTaskDao;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemTaskEO;
import com.jiuqi.gcreport.conversion.function.RateValueFunctionContext;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.nr.impl.function.GcReportSimpleExecutorContext;
import com.jiuqi.gcreport.rate.impl.consts.RateTypeEnum;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.stereotype.Component;

@Component
public class RateValueFunction
extends Function
implements IGcFunction {
    public RateValueFunction() {
        this.parameters().add(new Parameter("rateTypeCode", 6, "\u7b2c\u4e00\u4e2a\u53c2\u6570\u4e3a\u6c47\u7387\u7c7b\u578b\u7684\u7f16\u7801\uff0c\u53c2\u6570\u503c\u4e3aMD_RATETYPE\u57fa\u7840\u6570\u636e\u9879\u4ee3\u7801\u3002", false));
        this.parameters().add(new Parameter("yearValue", 6, "\u7b2c\u4e8c\u4e2a\u53c2\u6570\u4e3a\u5e74\u5ea6\uff0c\u53ef\u586b\u5199\u5185\u5bb9\uff1a\u5177\u4f53\u5e74\u5ea6\uff08\u53d6\u6307\u5b9a\u5e74\u5ea6\uff09\u3001\u504f\u79fb\u91cf-1\uff08\u53d6\u4e0a\u5e74\u5ea6\uff09\u3001\u4e0d\u586b\u5199\uff08\u53d6\u5f53\u524d\u5e74\u5ea6\uff09\u3002", true));
        this.parameters().add(new Parameter("periodValue", 6, "\u7b2c\u4e09\u4e2a\u53c2\u6570\u4e3a\u671f\u95f4\u503c\uff0c\u53ef\u586b\u5199\u5185\u5bb9\uff1a\u5177\u4f53\u671f\u95f4\uff08\u53d6\u6307\u5b9a\u671f\u95f4\uff09\u3001\u504f\u79fb\u91cf-1\uff08\u53d6\u4e0a\u4e00\u671f\uff09\u3001END\uff08\u53d6\u6700\u540e\u4e00\u671f\uff09\u3001\u4e0d\u586b\u5199\uff08\u53d6\u5f53\u524d\u65f6\u671f\uff09\u3002", true));
        this.parameters().add(new Parameter("afterCurrencyCode", 6, "\u7b2c\u56db\u4e2a\u53c2\u6570\u4e3a\u6298\u7b97\u540e\u5e01\u79cd", true));
    }

    public String name() {
        return "RateValue";
    }

    public String title() {
        return "\u83b7\u53d6\u5bf9\u5e94\u5e74\u5ea6\u5bf9\u5e94\u671f\u95f4\u5bf9\u5e94\u6298\u7b97\u6c47\u7387\u7c7b\u578b\u7684\u503c";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 3;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public BigDecimal evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        BigDecimal rateValue;
        QueryContext queryContext = (QueryContext)context;
        RateValueFunctionContext rateValueFunctionContext = this.getRateValueFunctionContext(queryContext);
        AtomicBoolean isSumAvgType = new AtomicBoolean(false);
        String rateTypeCode = (String)parameters.get(0).evaluate(null);
        PeriodWrapper periodWrapper = new PeriodWrapper(rateValueFunctionContext.getPeriodStr());
        this.changeYear(periodWrapper, parameters);
        this.changePeriod(periodWrapper, parameters, isSumAvgType);
        if (RateTypeEnum.NOTCONV.getCode().equalsIgnoreCase(rateTypeCode)) {
            return null;
        }
        ConversionRateService rateService = (ConversionRateService)SpringContextUtils.getBean(ConversionRateService.class);
        String rateSchemeCode = rateValueFunctionContext.getRateSchemeCode();
        String schemeId = rateValueFunctionContext.getSchemeId();
        String beforeCurrencyCode = rateValueFunctionContext.getBeforeCurrencyCode();
        String afterCurrencyCode = rateValueFunctionContext.getAfterCurrencyCode();
        String periodStr = periodWrapper.toString();
        if (parameters.size() == 4) {
            afterCurrencyCode = this.getAfterCurrencyCode(context, parameters);
        }
        if (org.springframework.util.StringUtils.isEmpty(beforeCurrencyCode) || org.springframework.util.StringUtils.isEmpty(afterCurrencyCode)) {
            return BigDecimal.ONE;
        }
        if (beforeCurrencyCode.equals(afterCurrencyCode)) {
            return BigDecimal.ONE;
        }
        if (isSumAvgType.get()) {
            rateValue = rateService.getSumAvgRateValueByRateTypeCode(rateSchemeCode, schemeId, beforeCurrencyCode, afterCurrencyCode, periodStr, rateTypeCode);
        } else {
            Map<String, BigDecimal> rateValueMap = rateService.getRateInfosByRateTypeCode(rateSchemeCode, schemeId, beforeCurrencyCode, afterCurrencyCode, periodStr, rateTypeCode);
            if (null == rateValueMap) {
                this.error(periodStr, beforeCurrencyCode, afterCurrencyCode);
            }
            rateValue = rateValueMap.get(rateTypeCode);
        }
        if (null == rateValue) {
            this.error(periodStr, beforeCurrencyCode, afterCurrencyCode);
        }
        return rateValue;
    }

    private String getAfterCurrencyCode(IContext context, List<IASTNode> parameters) throws SyntaxException {
        String result = (String)parameters.get(3).evaluate(context);
        if (org.springframework.util.StringUtils.isEmpty(result)) {
            return "CNY";
        }
        if (result.contains("GC_INVESTBILL")) {
            String field = result.substring(result.indexOf("["), result.indexOf("]"));
            QueryContext queryContext = (QueryContext)context;
            GcReportSimpleExecutorContext executorContext = (GcReportSimpleExecutorContext)queryContext.getExeContext();
            DefaultTableEntity masterData = executorContext.getData();
            return (String)masterData.getFields().get(field);
        }
        return result;
    }

    private void error(String periodStr, String beforeCurrencyCode, String afterCurrencyCode) {
        GcBaseData beforeCurrency = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CURRENCY", beforeCurrencyCode);
        Assert.isNotNull((Object)beforeCurrency, (String)("\u6298\u7b97\u524d\u5e01\u79cd\u4e0d\u5b58\u5728\uff1a" + beforeCurrencyCode), (Object[])new Object[0]);
        GcBaseData afterCurrency = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CURRENCY", afterCurrencyCode);
        Assert.isNotNull((Object)afterCurrency, (String)("\u6298\u7b97\u540e\u5e01\u79cd\u4e0d\u5b58\u5728\uff1a" + afterCurrencyCode), (Object[])new Object[0]);
        String periodTitle = new DefaultPeriodAdapter().getPeriodTitle(periodStr);
        String message = "\u65f6\u671f[" + periodTitle + "]\u4e0b\u672a\u8bbe\u7f6e\u6298\u7b97\u6c47\u7387\uff0c\u6298\u7b97\u524d\u5e01\u79cd" + beforeCurrencyCode + "|" + beforeCurrency.getTitle() + ",\u6298\u7b97\u540e\u5e01\u79cd" + afterCurrencyCode + "|" + afterCurrency.getTitle();
        throw new RuntimeException(message);
    }

    private RateValueFunctionContext getRateValueFunctionContext(QueryContext queryContext) {
        RateValueFunctionContext env;
        ExecutorContext exeContext = queryContext.getExeContext();
        Variable variable = this.getVariable(exeContext);
        if (null == variable) {
            GcReportSimpleExecutorContext executorContext = (GcReportSimpleExecutorContext)exeContext;
            env = this.getBillEnv(queryContext, executorContext);
        } else {
            try {
                env = (RateValueFunctionContext)variable.getVarValue((IContext)exeContext);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("\u6298\u7b97\u516c\u5f0fRateValueFunction\u4e0a\u4e0b\u6587\u83b7\u53d6\u6298\u7b97\u73af\u5883\u5f02\u5e38\u3002", (Throwable)e);
            }
        }
        return env;
    }

    private Variable getVariable(ExecutorContext exeContext) {
        VariableManager variableManager = exeContext.getVariableManager();
        if (null == variableManager) {
            return null;
        }
        Variable variable = variableManager.find("RateValueFunctionContext");
        return variable;
    }

    private RateValueFunctionContext getBillEnv(QueryContext queryContext, GcReportSimpleExecutorContext executorContext) {
        RateValueFunctionContext env = new RateValueFunctionContext();
        DefaultTableEntity master = executorContext.getData();
        env.setBeforeCurrencyCode((String)master.getFieldValue("CURRENCYCODE"));
        DimensionValueSet ds = queryContext.getCurrentMasterKey();
        env.setAfterCurrencyCode(String.valueOf(ds.getValue("MD_CURRENCY")));
        env.setPeriodStr(String.valueOf(ds.getValue("DATATIME")));
        env.setSchemeId(executorContext.getSchemeId());
        ConversionSystemTaskEO taskSchemeEO = ((ConversionSystemTaskDao)SpringContextUtils.getBean(ConversionSystemTaskDao.class)).queryByTaskAndScheme(executorContext.getTaskId(), env.getSchemeId());
        Assert.isNotNull((Object)((Object)taskSchemeEO), (String)"\u5f53\u524d\u4efb\u52a1\u3001\u62a5\u8868\u65b9\u6848\u672a\u914d\u7f6e\u6298\u7b97\u4f53\u7cfb", (Object[])new Object[0]);
        if (null == taskSchemeEO) {
            return null;
        }
        env.setRateSchemeCode(taskSchemeEO.getRateSchemeCode());
        return env;
    }

    private void changePeriod(PeriodWrapper periodWrapper, List<IASTNode> parameters, AtomicBoolean isSumAvgType) throws SyntaxException {
        if (parameters.size() < 3) {
            return;
        }
        String periodParam = (String)parameters.get(2).evaluate(null);
        if (org.springframework.util.StringUtils.isEmpty(periodParam)) {
            return;
        }
        if ("-1".equals(periodParam)) {
            DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
            defaultPeriodAdapter.modifyPeriod(periodWrapper, -1);
            return;
        }
        if ("END".equals(periodParam)) {
            periodWrapper.setYear(periodWrapper.getYear() + 1);
            periodWrapper.setPeriod(1);
            DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
            defaultPeriodAdapter.modifyPeriod(periodWrapper, -1);
            return;
        }
        if ("SUMAVG".equals(periodParam)) {
            isSumAvgType.set(true);
            return;
        }
        periodWrapper.setPeriod(Integer.valueOf(periodParam).intValue());
    }

    private void changeYear(PeriodWrapper periodWrapper, List<IASTNode> parameters) throws SyntaxException {
        if (parameters.size() < 2) {
            return;
        }
        String yearParam = (String)parameters.get(1).evaluate(null);
        if (org.springframework.util.StringUtils.isEmpty(yearParam)) {
            return;
        }
        if ("-1".equals(yearParam)) {
            DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
            defaultPeriodAdapter.modifyYear(periodWrapper, -1);
            return;
        }
        periodWrapper.setYear(Integer.valueOf(yearParam).intValue());
    }

    private int getPeriodType(String periodStr) {
        PeriodWrapper periodWrapper = new PeriodWrapper(periodStr);
        return periodWrapper.getType();
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        String rateType = (String)parameters.get(0).evaluate(null);
        if (StringUtils.isEmpty((String)rateType)) {
            throw new SyntaxException(parameters.get(0).getToken(), "RateValue\u51fd\u6570\u7b2c\u4e00\u4e2a\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a");
        }
        return super.validate(context, parameters);
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append("\uff1b").append("\u6307\u5b9a\u671f\u521d\u6c47\u7387\u3001\u671f\u672b\u6c47\u7387\u3001\u5e73\u5747\u6c47\u7387\u3001\u4e0a\u5e74\u5e73\u5747\u6c47\u7387\u3001\u5b9e\u65bd\u81ea\u5b9a\u4e49\u6c47\u7387\u7c7b\u578b\u7684\u6c47\u7387\u503c\uff0c").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u4e00\u3001\u6298\u7b97\u573a\u666f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u901a\u8fc7\u6c47\u7387\u516c\u5f0f\u83b7\u53d6\u6c47\u7387\u503c\u3002 ").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\u793a\u4f8b\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u6708\u5e73\u5747\u6c47\u7387\uff1a RateValue(\"01\",\"\",\"\")+RateValue(\"02\",\"\",\"\"))/2").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u7d2f\u52a0\u5e73\u5747\u6c47\u7387\uff1a RateValue(\"01\",\"\",\"SUMAVG\")").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u4e0a\u5e74\u671f\u672b\uff1a RateValue(\"01\",\"-1\",\"END\")").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u4e0a\u5e74\u540c\u671f\uff1a RateValue(\"01\",\"-1\",\"\")").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u4e0a\u671f\uff1a RateValue(\"01\",\"\",\"-1\")").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u6307\u5b9a\u5e74\u5ea6\u671f\u95f4\uff1a RateValue(\"01\",\"2020\",\"1\")").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\u503c\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("0.05").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u4e8c\u3001\u5408\u5e76\u8ba1\u7b97\u573a\u666f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u83b7\u53d6\u6e90\u5e01\u79cd\u4eba\u6c11\u5e01CNY\u3001\u76ee\u6807\u5e01\u79cd\u7f8e\u5143USD\u3001\u671f\u672b\u6c47\u738702\u5bf9\u5e94\u7684\u6c47\u7387\u503c").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("1\u3001\u53f0\u8d26\u5e01\u79cd\u5b57\u6bb5CURRENCYCODE\u5f55\u5165\u503c\u4eba\u6c11\u5e01CNY").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("2\u3001\u5408\u5e76\u8ba1\u7b97\u9009\u62e9\u5e01\u79cd\u7f8e\u5143USD").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("3\u3001\u516c\u5f0f\uff1aRateValue(\"02\",\"\",\"\")").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\u503c\uff1a").append("0.05").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }
}

