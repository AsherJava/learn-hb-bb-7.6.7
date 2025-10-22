/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.gcreport.common.nr.function.IGcFunction
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.gcreport.calculate.formula.service.impl.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.nr.function.IGcFunction;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GetChangeDataFunction
extends Function
implements IGcFunction {
    private static final long serialVersionUID = 1L;
    private final Logger logger = LoggerFactory.getLogger(((Object)((Object)this)).getClass());
    public final String FUNCTION_NAME = "GetChangeData";
    private static final String FN_INVESTED_UNIT = "INVESTEDUNIT";
    public static final String FN_UNITCODE = "UNITCODE";

    public GetChangeDataFunction() {
        this.parameters().add(new Parameter("zbcode", 6, "\u6307\u6807\u4ee3\u7801"));
    }

    public String name() {
        return "GetChangeData";
    }

    public String title() {
        return "\u83b7\u53d6\u88ab\u6295\u8d44\u5355\u4f4d\u6307\u6807\u7684\u53d8\u52a8\u6570";
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
            GcReportExceutorContext exceutorContext = (GcReportExceutorContext)queryContext.getExeContext();
            DefaultTableEntity investData = exceutorContext.getData();
            Object fieldValue = investData.getFieldValue(FN_INVESTED_UNIT);
            if (fieldValue == null) {
                return 0.0;
            }
            String investedUnit = fieldValue.toString();
            try {
                return this.getChangeDataZbNumber(queryContext.getCurrentMasterKey(), parameters, investedUnit, exceutorContext);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("\u83b7\u53d6\u88ab\u6295\u8d44\u5355\u4f4d\u6307\u6807\u7684\u53d8\u52a8\u6570\u5f02\u5e38\uff01", (Throwable)e);
            }
        }
        return 0.0;
    }

    private Double getChangeDataZbNumber(DimensionValueSet olDimensionValueSet, List<IASTNode> parameters, String investedUnit, GcReportExceutorContext exceutorContext) throws Exception {
        AbstractData priorData;
        String zbCode = ConverterUtils.getAsString((Object)parameters.get(0).evaluate(null));
        DefaultTableEntity investData = exceutorContext.getData();
        List items = exceutorContext.getItems();
        DimensionValueSet ds = new DimensionValueSet(olDimensionValueSet);
        String unitCode = ConverterUtils.getAsString((Object)investData.getFieldValue(FN_UNITCODE));
        StringBuilder logInfo = new StringBuilder("\n\u83b7\u53d6\u88ab\u6295\u8d44\u5355\u4f4d\u6307\u6807\u7684\u53d8\u52a8\u6570\uff1a\u6295\u8d44\u5355\u4f4d[" + unitCode + "],\u88ab\u6295\u8d44\u5355\u4f4d[" + investedUnit + "],\u6307\u6807\u4ee3\u7801[" + zbCode + "]");
        String periodString = this.getPeriodString(items, ds, logInfo);
        logInfo.append("\u53d6\u6570\u7b2c\u4e00\u4e2a\u65f6\u671f[" + periodString + "]\n");
        String orgTypeId = String.valueOf(ds.getValue("MD_GCORGTYPE"));
        YearPeriodObject yp = new YearPeriodObject(null, periodString);
        investedUnit = this.getInvestedUnit(investedUnit, ds, orgTypeId, yp, logInfo);
        PeriodWrapper periodWrapper = new PeriodWrapper(periodString);
        ds.setValue("DATATIME", (Object)periodWrapper.toString());
        AbstractData abstractData = NrTool.getZbValue((DimensionValueSet)ds, (String)zbCode);
        Double currentZbData = null == abstractData ? 0.0 : abstractData.getAsFloat();
        logInfo.append("\u65f6\u671f[" + periodString + "]\u7684\u6307\u6807\u6570\u4e3a[" + NumberUtils.doubleToString((Double)currentZbData) + "]\n");
        if (periodString.endsWith("01")) {
            this.logger.info(logInfo.append("\u65f6\u671f\u4e3a\u7b2c\u4e00\u671f\uff0c\u76f4\u63a5\u8fd4\u56de\u6307\u6807\u6570\n").toString());
            return currentZbData;
        }
        if (this.changeDataIsOneMonth(items)) {
            this.logger.info(logInfo.append("\u5b50\u8868\u53d8\u52a8\u671f\u4e3a1\u6708\uff0c\u76f4\u63a5\u8fd4\u56de\u6307\u6807\u6570\n").toString());
            return currentZbData;
        }
        DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
        defaultPeriodAdapter.modifyPeriod(periodWrapper, this.getModifyCount(items, yp.formatYP().getEndDate()));
        ds.setValue("DATATIME", (Object)periodWrapper.toString());
        logInfo.append("\u83b7\u53d6\u53d8\u52a8\u6570\uff0c\u505a\u51cf\u6cd5\u7684\u65f6\u671f\u4e3a[" + periodWrapper + "]\n");
        investedUnit = this.getSrcInvestedUnit(investData, investedUnit, logInfo);
        ds.setValue("MD_ORG", (Object)investedUnit);
        GcOrgCacheVO investedUnitVO = GcOrgPublicTool.getInstance((String)orgTypeId, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)new YearPeriodObject(null, periodWrapper.toString())).getOrgByCode(investedUnit);
        if (investedUnitVO != null && !StringUtils.isEmpty((String)investedUnitVO.getOrgTypeId())) {
            ds.setValue("MD_GCORGTYPE", (Object)investedUnitVO.getOrgTypeId());
        }
        Double priorZbData = null == (priorData = NrTool.getZbValue((DimensionValueSet)ds, (String)zbCode)) ? 0.0 : priorData.getAsFloat();
        Double result = currentZbData - priorZbData;
        logInfo.append("\u65f6\u671f[" + periodWrapper + "]\u7684\u6307\u6807\u6570\u4e3a[" + NumberUtils.doubleToString((Double)priorZbData) + "]\u3002\u51fd\u6570\u8ba1\u7b97\u7ed3\u679c\u4e3a[" + NumberUtils.doubleToString((Double)result) + "]\n");
        this.logger.info(logInfo.toString());
        return result;
    }

    private boolean changeDataIsOneMonth(List<DefaultTableEntity> items) {
        if (CollectionUtils.isEmpty(items)) {
            return false;
        }
        Object changeDate = items.get(0).getFieldValue("CHANGEDATE");
        if (changeDate == null) {
            return false;
        }
        int changeDateMonth = DateUtils.getDateFieldValue((Date)((Date)changeDate), (int)2);
        return 1 == changeDateMonth;
    }

    private String getSrcInvestedUnit(DefaultTableEntity investData, String investedUnit, StringBuilder logInfo) {
        String srcInvestedunit = ConverterUtils.getAsString((Object)investData.getFieldValue("SRC_INVESTEDUNIT"));
        if (StringUtils.isEmpty((String)srcInvestedunit)) {
            return investedUnit;
        }
        logInfo.append("\u573a\u666f\u4e3a\u5904\u7f6e\u573a\u666f\uff0c\u7528\u53d8\u52a8\u540e\u5355\u4f4d\u51cf\u6e90\u6295\u8d44\u5355\u4f4d[" + srcInvestedunit + "]\u6307\u6807\u6570\n");
        return srcInvestedunit;
    }

    private String getInvestedUnit(String investedUnit, DimensionValueSet ds, String orgTypeId, YearPeriodObject yp, StringBuilder logInfo) {
        GcOrgCenterService orgCenterService = GcOrgPublicTool.getInstance((String)orgTypeId, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO hbOrg = orgCenterService.getUnionUnitByGrade(investedUnit);
        if (null != hbOrg) {
            logInfo.append("\u88ab\u6295\u8d44\u5355\u4f4d\u4e3a\u672c\u90e8\u5355\u4f4d\uff0c\u53d6\u5bf9\u5e94\u5408\u5e76\u5355\u4f4d[" + hbOrg.getId() + "]\u7684\u6570\u636e\n");
            investedUnit = hbOrg.getId();
        }
        ds.setValue("MD_ORG", (Object)investedUnit);
        GcOrgCacheVO investedUnitVO = orgCenterService.getOrgByCode(investedUnit);
        if (investedUnitVO != null && !StringUtils.isEmpty((String)investedUnitVO.getOrgTypeId())) {
            ds.setValue("MD_GCORGTYPE", (Object)investedUnitVO.getOrgTypeId());
        }
        return investedUnit;
    }

    private String getPeriodString(List<DefaultTableEntity> items, DimensionValueSet ds, StringBuilder logInfo) {
        String periodString = (String)ds.getValue("DATATIME");
        logInfo.append("\u5f53\u524d\u65f6\u671f[" + periodString + "]\n");
        periodString = this.goingConcernDealWith(items, periodString, logInfo);
        return periodString;
    }

    private String goingConcernDealWith(List<DefaultTableEntity> items, String periodString, StringBuilder logInfo) {
        if (CollectionUtils.isEmpty(items)) {
            return periodString;
        }
        DefaultTableEntity item = items.get(0);
        if (item == null) {
            logInfo.append("\u5f53\u524d\u6295\u8d44\u53f0\u8d26\u65e0\u5b50\u8868\u4fe1\u606f\uff0c\u4f7f\u7528\u5408\u5e76\u8ba1\u7b97\u6708\u51cf\u4e0a\u6708\n");
            return periodString;
        }
        if (!this.isGoingOrInitial(item, logInfo)) {
            return periodString;
        }
        Object changeDateObj = item.getFieldValue("CHANGEDATE");
        if (changeDateObj == null) {
            logInfo.append("\u53d8\u52a8\u65f6\u671f\u4e3a\u7a7a\uff0c\u4f7f\u7528\u5408\u5e76\u8ba1\u7b97\u6708\u51cf\u4e0a\u6708\n");
            return periodString;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime((Date)changeDateObj);
        PeriodWrapper periodWrapper = new PeriodWrapper(periodString);
        YearPeriodDO yearPeriodDO = YearPeriodUtil.transform(null, (int)periodWrapper.getType(), (Calendar)calendar);
        return yearPeriodDO.toString();
    }

    private boolean isGoingOrInitial(DefaultTableEntity item, StringBuilder logInfo) {
        Object goingConcernFlag = item.getFieldValue("GOINGCONCERNFLAG");
        if (goingConcernFlag != null && Boolean.TRUE.equals(goingConcernFlag)) {
            logInfo.append("\u5f53\u524d\u6570\u636e\u4e3a\u6301\u7eed\u573a\u666f:\u4f7f\u7528\u53d8\u52a8\u6708\u51cf\u4e0a\u6708\n");
            return true;
        }
        Object initialFlag = item.getFieldValue("INITIALFLAG");
        if (initialFlag != null && Boolean.TRUE.equals(initialFlag)) {
            logInfo.append("\u5f53\u524d\u6570\u636e\u4e3a\u521d\u6b21\u573a\u666f:\u4f7f\u7528\u53d8\u52a8\u6708\u51cf\u4e0a\u6708\n");
            return true;
        }
        logInfo.append("\u5f53\u524d\u6570\u636e\u4e3a\u666e\u901a\u573a\u666f:\u4f7f\u7528\u5408\u5e76\u8ba1\u7b97\u6708\u51cf\u4e0a\u6708\n");
        return false;
    }

    private int getModifyCount(List<DefaultTableEntity> items, Date endDate) {
        int endDateMonth;
        if (CollectionUtils.isEmpty(items)) {
            return -1;
        }
        DefaultTableEntity item = items.get(0);
        if (item == null) {
            return -1;
        }
        Object changeDateObj = item.getFieldValue("CHANGEDATE");
        if (changeDateObj == null) {
            return -1;
        }
        int changeDateMonth = DateUtils.getDateFieldValue((Date)((Date)changeDateObj), (int)2);
        if (changeDateMonth >= (endDateMonth = DateUtils.getDateFieldValue((Date)endDate, (int)2))) {
            return -1;
        }
        return changeDateMonth - endDateMonth - 1;
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append("\uff1b").append("\u6307\u6807\u6570\u503c").append(StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u53d6\u6307\u6807\u672c\u671f\u53d8\u52a8\u6570\u503c ").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GetChangeData('ZCOX_YB01[A06]')").append(StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }
}

