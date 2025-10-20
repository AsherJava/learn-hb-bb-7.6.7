/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.gcreport.common.nr.function.IGcFunction
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.gcreport.invest.formula.function.rule.invest;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.nr.function.IGcFunction;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.invest.investbill.dao.InvestBillDao;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GetBeginMonthBBFunction
extends Function
implements IGcFunction {
    private static final long serialVersionUID = 1L;
    public final String FUNCTION_NAME = "GetBeginMonthBB";

    public String name() {
        return "GetBeginMonthBB";
    }

    public String title() {
        return "\u6295\u8d44\u89c4\u5219\u83b7\u53d6\u6708\u521d\u80a1\u6743\u6bd4\u4f8b\u51fd\u6570";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 3;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Double evalute(IContext context, List<IASTNode> parameters) {
        QueryContext queryContext = (QueryContext)context;
        if (queryContext.getExeContext() instanceof GcReportExceutorContext) {
            Date[] periodDateRegion;
            GcReportExceutorContext exceutorContext = (GcReportExceutorContext)queryContext.getExeContext();
            DefaultTableEntity investData = exceutorContext.getData();
            Object idObj = investData.getFieldValue("ID");
            Object endEquityRatioObj = investData.getFieldValue("ENDEQUITYRATIO");
            Double endEquityRatio = endEquityRatioObj == null ? 0.0 : ConverterUtils.getAsDouble((Object)endEquityRatioObj);
            if (idObj == null) {
                return endEquityRatio;
            }
            InvestBillDao investBillDao = (InvestBillDao)SpringContextUtils.getBean(InvestBillDao.class);
            List<DefaultTableEntity> investmentItems = investBillDao.getInvestmentItemsByMastId(idObj.toString());
            if (CollectionUtils.isEmpty(investmentItems)) {
                return endEquityRatio;
            }
            DimensionValueSet ds = queryContext.getCurrentMasterKey();
            String periodString = (String)ds.getValue("DATATIME");
            if (null == periodString) {
                periodString = (String)ds.getValue("DATATIME");
            }
            PeriodWrapper periodWrapper = new PeriodWrapper(periodString);
            DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
            try {
                periodDateRegion = defaultPeriodAdapter.getPeriodDateRegion(periodWrapper);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("\u53d6\u6708\u521d\u80a1\u6743\u6bd4\u4f8b\u51fd\u6570\u51fa\u73b0\u5f02\u5e38\uff1a", (Throwable)e);
            }
            Date startPeriod = periodDateRegion[0];
            Date endPeriod = periodDateRegion[1];
            if (startPeriod == null || endPeriod == null) {
                return endEquityRatio;
            }
            for (DefaultTableEntity item : investmentItems) {
                Object changeRatioObj;
                Date changeDate;
                Object changeDateObj = item.getFieldValue("CHANGEDATE");
                if (changeDateObj == null || (changeDate = (Date)changeDateObj).compareTo(startPeriod) < 0 || changeDate.compareTo(endPeriod) > 0 || (changeRatioObj = item.getFieldValue("CHANGERATIO")) == null) continue;
                endEquityRatio = endEquityRatio - (Double)changeRatioObj;
            }
            return endEquityRatio;
        }
        return 0.0;
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append("\uff1b").append("\u6708\u521d\u80a1\u6743\u6bd4\u4f8b").append(StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u6295\u8d44\u89c4\u5219\u83b7\u53d6\u6708\u521d\u80a1\u6743\u6bd4\u4f8b\u51fd\u6570 ").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GetBeginMonthBB()").append(StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }
}

