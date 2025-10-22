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
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.nr.impl.function.NrFunction
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 */
package com.jiuqi.gcreport.conversion.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.conversion.conversionrate.service.ConversionRateService;
import com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemTaskDao;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemTaskEO;
import com.jiuqi.gcreport.nr.impl.function.NrFunction;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RateCurrencyFunction
extends NrFunction {
    private static final long serialVersionUID = 1923322597865316895L;
    private static final String GETHL = "GETHL";
    @Autowired
    private ConversionSystemTaskDao taskSchemeDao;
    @Autowired
    private ConversionRateService rateService;

    public RateCurrencyFunction() {
        this.parameters().add(new Parameter("sourcrZb", 6, "\u539f\u5e01\u5e01\u79cd\u6307\u6807"));
        this.parameters().add(new Parameter("targetZb", 6, "\u76ee\u6807\u5e01\u79cd\u6307\u6807\u6216\u4ee3\u7801"));
        this.parameters().add(new Parameter("rateType", 6, "\u6c47\u7387\u7c7b\u578b"));
    }

    public String name() {
        return GETHL;
    }

    public String title() {
        return "\u6c47\u7387\u516c\u5f0f\uff0c\u53d6\u503c\u4e3a\u7a7a\u65f6\u9ed8\u8ba4\u4e3a0";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 3;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> parameters) throws SyntaxException {
        try {
            AbstractData targetZB;
            String rateType;
            String targetZBCode;
            String sourcrZBCode;
            QueryContext queryContext = (QueryContext)iContext;
            ReportFmlExecEnvironment exceutorContext = (ReportFmlExecEnvironment)queryContext.getExeContext().getEnv();
            ConversionSystemTaskEO taskSchemeEO = this.getConversionSystemTaskEO(exceutorContext.getTaskDefine().getKey(), exceutorContext.getFormSchemeKey());
            Map dims = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)queryContext.getRowKey());
            String unitId = ((DimensionValue)dims.get("MD_ORG")).getValue();
            if (StringUtils.isEmpty((String)unitId)) {
                throw new BusinessRuntimeException("\u6298\u7b97\u6c47\u7387\u516c\u5f0f\u83b7\u53d6\u672c\u65b9\u5355\u4f4d\u4e3a\u7a7a");
            }
            try {
                sourcrZBCode = (String)parameters.get(0).evaluate((IContext)queryContext);
                targetZBCode = (String)parameters.get(1).evaluate((IContext)queryContext);
                rateType = (String)parameters.get(2).evaluate((IContext)queryContext);
            }
            catch (SyntaxException e) {
                throw new BusinessRuntimeException("\u83b7\u53d6GETHL\u516c\u5f0f\u53c2\u6570\u9519\u8bef\u3002", (Throwable)e);
            }
            DimensionValueSet ds = this.calcDimensionValueSet(queryContext.getRowKey(), parameters, unitId);
            String sourcrValue = "";
            String targetValue = "";
            AbstractData sourcrZB = NrTool.getZbValue((DimensionValueSet)ds, (String)sourcrZBCode);
            String regex = "(.+)\\[(.+)\\]";
            targetValue = targetZBCode.matches(regex) ? ((targetZB = NrTool.getZbValue((DimensionValueSet)ds, (String)targetZBCode)) == null ? null : targetZB.getAsString()) : targetZBCode;
            String string = sourcrValue = sourcrZB == null ? null : sourcrZB.getAsString();
            if (StringUtils.isEmpty((String)sourcrValue) || StringUtils.isEmpty((String)targetValue)) {
                return BigDecimal.ONE;
            }
            Map<String, BigDecimal> rateValueMap = this.rateService.getRateInfos(taskSchemeEO == null ? null : taskSchemeEO.getRateSchemeCode(), exceutorContext.getFormSchemeKey(), sourcrValue, targetValue, queryContext.getPeriodWrapper().toString());
            if (rateValueMap == null || rateValueMap.size() == 0) {
                return 0.0;
            }
            BigDecimal rateValue = rateValueMap.get(rateType);
            if (rateValue == null) {
                return 0.0;
            }
            return rateValue;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
    }

    private DimensionValueSet calcDimensionValueSet(DimensionValueSet olDimensionValueSet, List<IASTNode> parameters, String unitId) throws Exception {
        DimensionValueSet ds = new DimensionValueSet(olDimensionValueSet);
        ds.setValue("MD_ORG", (Object)unitId);
        String periodString = (String)ds.getValue("DATATIME");
        PeriodWrapper periodWrapper = new PeriodWrapper(periodString);
        ds.setValue("DATATIME", (Object)periodWrapper.toString());
        if (parameters.size() <= 1) {
            return ds;
        }
        String offsetTerm = (String)parameters.get(1).evaluate(null);
        if (!"-1".equalsIgnoreCase(offsetTerm) && !"-1N".equalsIgnoreCase(offsetTerm)) {
            return ds;
        }
        if ("-1".equalsIgnoreCase(offsetTerm)) {
            periodWrapper.priorPeriod();
        } else {
            periodWrapper.setYear(periodWrapper.getYear() - 1);
        }
        ds.setValue("DATATIME", (Object)periodWrapper.toString());
        return ds;
    }

    private ConversionSystemTaskEO getConversionSystemTaskEO(String taskId, String schemeId) {
        ConversionSystemTaskEO taskSchemeEO = this.taskSchemeDao.queryByTaskAndScheme(taskId, schemeId);
        if (taskSchemeEO == null) {
            taskSchemeEO = new ConversionSystemTaskEO();
            taskSchemeEO.setTaskId(taskId);
            taskSchemeEO.setSchemeId(schemeId);
        }
        return taskSchemeEO;
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a\uff08\u53d6\u503c\u4e3a\u7a7a\u65f6\u9ed8\u8ba4\u4e3a0\uff09").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append("\uff1b").append("\u5185\u90e8\u8868\u6bcf\u884c\u6570\u636e\u7684\u539f\u5e01\u5e01\u79cd\u548c\u6298\u7b97\u540e\u76ee\u6807\u5e01\u79cd\u5bf9\u5e94\u7684\u6c47\u7387\u503c\uff0c").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u83b7\u53d6\u5185\u90e8\u8868\u6bcf\u884c\u6570\u636e\u7684\u539f\u5e01\u5e01\u79cd\u548c\u6298\u7b97\u540e\u76ee\u6807\u5e01\u79cd\u5bf9\u5e94\u7684\u6c47\u7387\u3002 ").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GETHL(\"[sourcrzb]\",\"[targetzb]\",\"HLLX\")").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\u503c\uff1a\uff08\u53d6\u503c\u4e3a\u7a7a\u65f6\u9ed8\u8ba4\u4e3a0\uff09").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("0.05").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }
}

