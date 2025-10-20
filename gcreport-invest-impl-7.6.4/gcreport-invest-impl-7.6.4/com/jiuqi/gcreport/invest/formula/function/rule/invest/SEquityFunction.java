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
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.gcreport.common.cache.formula.IFunctionCache
 *  com.jiuqi.gcreport.common.nr.function.IGcFunction
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskCacheService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.monthcalcscheme.vo.MonthCalcZbMappingVO
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
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodConsts
 */
package com.jiuqi.gcreport.invest.formula.function.rule.invest;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.cache.formula.IFunctionCache;
import com.jiuqi.gcreport.common.nr.function.IGcFunction;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskCacheService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.invest.investbill.enums.ChangeTypeEnum;
import com.jiuqi.gcreport.invest.monthcalcscheme.service.MonthCalcZbMappingService;
import com.jiuqi.gcreport.monthcalcscheme.vo.MonthCalcZbMappingVO;
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
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodConsts;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SEquityFunction
extends Function
implements IFunctionCache,
IGcFunction {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(SEquityFunction.class);
    @Autowired
    private MonthCalcZbMappingService monthCalcZbMappingService;
    @Autowired
    private ConsolidatedOptionService optionService;
    @Autowired
    private ConsolidatedTaskCacheService consolidatedTaskCacheService;
    private static transient ThreadLocal<Map<String, Object>> zb2ZbMappingVOLocal = new ThreadLocal();
    private static transient ThreadLocal<Map<String, String>> changeScenario2ChangeTypThreadLocal = new ThreadLocal();
    public static final String FUNCTION_NAME = "SEquity";

    public SEquityFunction() {
        this.parameters().add(new Parameter("zbCode", 6, "\u6307\u6807\u6807\u8bc6"));
        this.parameters().add(new Parameter("oneSubSumScale", 1, "true\u4e3a1-(\u7d2f\u8ba1\u6bd4\u4f8b/100)\uff0cfalse\u4e3a\u7d2f\u8ba1\u6bd4\u4f8b\uff0c\u9ed8\u8ba4false", true));
        this.parameters().add(new Parameter("minusThePreviousYear", 1, "true\u4e3a\u51cf\u53bb\u4e0a\u5e7412\u6708\u4efd\u6570\u636e\uff0cfalse\u4e3a\u4e0d\u51cf\u53bb\uff0c\u9ed8\u8ba4false", true));
    }

    public String name() {
        return FUNCTION_NAME;
    }

    public String title() {
        return "\u5206\u6bb5\u6295\u8d44\u51fd\u6570";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 0;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    private Map<Object, List<DefaultTableEntity>> getMonth2ItemsMap(List<DefaultTableEntity> currYearOrderItems) {
        Map<Object, List<DefaultTableEntity>> month2InvestItemsMap = currYearOrderItems.stream().collect(Collectors.groupingBy(item -> {
            Date date = (Date)item.getFieldValue("CHANGEDATE");
            return DateUtils.getDateFieldValue((Date)date, (int)2);
        }));
        return month2InvestItemsMap;
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext queryContext = (QueryContext)context;
        GcReportExceutorContext executorContext = (GcReportExceutorContext)queryContext.getExeContext();
        try {
            String zbCode = (String)parameters.get(0).evaluate(null);
            FieldDefine fieldDefine = NrTool.queryFieldDefineByZbCode((String)zbCode);
            if (null == fieldDefine) {
                throw new SyntaxException("\u6307\u6807\u4e0d\u5b58\u5728");
            }
        }
        catch (Exception e) {
            throw new SyntaxException("\u83b7\u53d6\u6307\u6807\u4ee3\u7801\u5bf9\u5e94\u7684\u5b57\u6bb5\u5f02\u5e38:", (Throwable)e);
        }
        return super.validate(context, parameters);
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext queryContext = (QueryContext)context;
        GcReportExceutorContext executorContext = (GcReportExceutorContext)queryContext.getExeContext();
        String zbCode = (String)parameters.get(0).evaluate(null);
        DimensionValueSet ds = this.getDimensionSet(queryContext);
        this.initZb2ZbMappingVoMap(executorContext);
        DefaultTableEntity masterData = executorContext.getData();
        List<DefaultTableEntity> currYearOrderItems = this.getCurrYearAndMonthItems(masterData, executorContext.getItems(), (String)ds.getValue("DATATIME"));
        Map<Object, List<DefaultTableEntity>> month2InvestItemsMap = this.getMonth2ItemsMap(currYearOrderItems);
        Set<String> changeTypeSet = this.getChangeTypeSet(currYearOrderItems);
        String allChangType = changeTypeSet.stream().collect(Collectors.joining(","));
        boolean oneSubSumScale = parameters.size() > 1 ? (Boolean)parameters.get(1).evaluate(null) : false;
        boolean minusThePreviousYear = parameters.size() > 2 ? (Boolean)parameters.get(2).evaluate(null) : false;
        String calcMode = this.getCalcMode(executorContext.getSchemeId(), (String)ds.getValue("DATATIME"));
        HashMap<String, Object> penetrateReturnMap = new HashMap<String, Object>();
        penetrateReturnMap.put("calcProcessDatas", new ArrayList());
        double returnAmt = 0.0;
        if (allChangType.equals(ChangeTypeEnum.CONTROLPOWER_UNCHANGED.getCode()) || CollectionUtils.isEmpty(changeTypeSet)) {
            this.appendDescription(penetrateReturnMap, ChangeTypeEnum.CONTROLPOWER_UNCHANGED.getCode(), calcMode, ds, zbCode, currYearOrderItems);
            returnAmt = this.controlUnChange(zbCode, masterData, currYearOrderItems, month2InvestItemsMap, ds, null, null, calcMode, oneSubSumScale, minusThePreviousYear, penetrateReturnMap);
        }
        if (allChangType.equals(ChangeTypeEnum.SC_CHANGE.getCode()) || allChangType.equals(ChangeTypeEnum.SC_CHANGE.getCode() + "," + ChangeTypeEnum.CONTROLPOWER_UNCHANGED.getCode()) || allChangType.equals(ChangeTypeEnum.CONTROLPOWER_UNCHANGED.getCode() + "," + ChangeTypeEnum.SC_CHANGE.getCode())) {
            this.appendDescription(penetrateReturnMap, ChangeTypeEnum.SC_CHANGE.getCode(), calcMode, ds, zbCode, currYearOrderItems);
            returnAmt = this.scChange(zbCode, masterData, currYearOrderItems, month2InvestItemsMap, ds, null, calcMode, oneSubSumScale, minusThePreviousYear, penetrateReturnMap);
        }
        if (allChangType.equals(ChangeTypeEnum.UNSC_ACQUISITION.getCode()) || allChangType.equals(ChangeTypeEnum.UNSC_ACQUISITION.getCode() + "," + ChangeTypeEnum.CONTROLPOWER_UNCHANGED.getCode()) || allChangType.equals(ChangeTypeEnum.CONTROLPOWER_UNCHANGED.getCode() + "," + ChangeTypeEnum.UNSC_ACQUISITION.getCode())) {
            this.appendDescription(penetrateReturnMap, ChangeTypeEnum.UNSC_ACQUISITION.getCode(), calcMode, ds, zbCode, currYearOrderItems);
            returnAmt = this.unScAcquisition(zbCode, masterData, currYearOrderItems, month2InvestItemsMap, ds, null, calcMode, oneSubSumScale, minusThePreviousYear, penetrateReturnMap);
        }
        if (allChangType.equals(ChangeTypeEnum.UNSC_DISPOSE.getCode()) || allChangType.equals(ChangeTypeEnum.UNSC_DISPOSE.getCode() + "," + ChangeTypeEnum.CONTROLPOWER_UNCHANGED.getCode()) || allChangType.equals(ChangeTypeEnum.CONTROLPOWER_UNCHANGED.getCode() + "," + ChangeTypeEnum.UNSC_DISPOSE.getCode())) {
            this.appendDescription(penetrateReturnMap, ChangeTypeEnum.UNSC_DISPOSE.getCode(), calcMode, ds, zbCode, currYearOrderItems);
            returnAmt = this.unScDispose(zbCode, masterData, currYearOrderItems, month2InvestItemsMap, ds, null, calcMode, oneSubSumScale, minusThePreviousYear, penetrateReturnMap);
        }
        if (allChangType.equals(ChangeTypeEnum.SC_CHANGE.getCode() + "," + ChangeTypeEnum.UNSC_DISPOSE.getCode()) || allChangType.equals(ChangeTypeEnum.CONTROLPOWER_UNCHANGED.getCode() + "," + ChangeTypeEnum.SC_CHANGE.getCode() + "," + ChangeTypeEnum.UNSC_DISPOSE.getCode())) {
            this.appendDescription(penetrateReturnMap, ChangeTypeEnum.SC_CHANGE.getCode() + "," + ChangeTypeEnum.UNSC_DISPOSE.getCode(), calcMode, ds, zbCode, currYearOrderItems);
            returnAmt = this.scChangeAndUnScDispose(zbCode, masterData, currYearOrderItems, month2InvestItemsMap, ds, calcMode, oneSubSumScale, minusThePreviousYear, penetrateReturnMap);
        }
        if (allChangType.equals(ChangeTypeEnum.UNSC_ACQUISITION.getCode() + "," + ChangeTypeEnum.UNSC_DISPOSE.getCode()) || allChangType.equals(ChangeTypeEnum.CONTROLPOWER_UNCHANGED.getCode() + "," + ChangeTypeEnum.UNSC_ACQUISITION.getCode() + "," + ChangeTypeEnum.UNSC_DISPOSE.getCode())) {
            this.appendDescription(penetrateReturnMap, ChangeTypeEnum.UNSC_ACQUISITION.getCode() + ChangeTypeEnum.UNSC_DISPOSE.getCode(), calcMode, ds, zbCode, currYearOrderItems);
            returnAmt = this.unScAcquisitionAndUnScDispose(zbCode, masterData, currYearOrderItems, month2InvestItemsMap, ds, calcMode, oneSubSumScale, minusThePreviousYear, penetrateReturnMap);
        }
        penetrateReturnMap.put("amt", returnAmt);
        return ConverterUtils.getAsBooleanValue((Object)ds.getValue("sequityPenetrateFlag")) ? penetrateReturnMap : Double.valueOf(returnAmt);
    }

    private String getCalcMode(String schemeId, String periodStr) {
        String reportSystemId = ((ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class)).getSystemIdBySchemeId(schemeId, periodStr);
        ConsolidatedOptionVO consolidatedOptionVO = this.optionService.getOptionData(reportSystemId);
        String calcMode = StringUtils.isEmpty((String)consolidatedOptionVO.getFdtzCalcMode()) ? "monthlyEnd" : consolidatedOptionVO.getFdtzCalcMode();
        return calcMode;
    }

    private void initZb2ZbMappingVoMap(GcReportExceutorContext executorContext) {
        Map<String, Object> monthCalcZb2MappingVOMap = zb2ZbMappingVOLocal.get();
        if (null == monthCalcZb2MappingVOMap || monthCalcZb2MappingVOMap.isEmpty()) {
            zb2ZbMappingVOLocal.set(this.monthCalcZbMappingService.getZbMappingCache(executorContext.getTaskId(), executorContext.getPeriodType()));
        }
    }

    private void initChangeScenario2ChangeTypeMap() {
        GcBaseDataCenterTool baseDataTool = GcBaseDataCenterTool.getInstance();
        List baseDataList = baseDataTool.queryBasedataItems("MD_CHANGERATIO");
        Map<String, String> code2ChangeTypeMap = baseDataList.stream().collect(Collectors.toMap(GcBaseData::getCode, gcBaseData -> {
            String changetype = (String)gcBaseData.getFieldVal("CHANGETYPE");
            if (null == changetype) {
                return "01";
            }
            return (String)gcBaseData.getFieldVal("CHANGETYPE");
        }));
        changeScenario2ChangeTypThreadLocal.set(code2ChangeTypeMap);
    }

    private double controlUnChange(String zbCode, DefaultTableEntity masterData, List<DefaultTableEntity> currYearItems, Map<Object, List<DefaultTableEntity>> month2InvestItemsMap, DimensionValueSet ds, Integer beginMonth, Integer endMonth, String calcMode, boolean oneSubSumScale, boolean minusThePreviousYear, Map<String, Object> penetrateReturnMap) {
        DimensionValueSet dsTemp = new DimensionValueSet(ds);
        String periodString = ConverterUtils.getAsString((Object)dsTemp.getValue("DATATIME"));
        YearPeriodDO periodUtil = YearPeriodUtil.transform(null, (String)periodString);
        beginMonth = null == ConverterUtils.getAsInteger((Object)beginMonth) ? 1 : beginMonth;
        endMonth = null == ConverterUtils.getAsInteger((Object)endMonth) ? periodUtil.getEndDate().getMonth() + 1 : endMonth;
        double currTotalEquityRatio = (Double)masterData.getFieldValue("BEGINEQUITYRATIO") / 100.0;
        double sumAmt = 0.0;
        double currMonthZbValue = 0.0;
        double lastMonthZbValue = 0.0;
        if (beginMonth != 1) {
            lastMonthZbValue = this.getCurrMonthZbValue(zbCode, dsTemp, periodUtil, beginMonth - 1);
            currTotalEquityRatio = this.getEquityRatio(masterData, currYearItems, calcMode.equals("monthlyBegin") ? beginMonth - 1 : beginMonth - 2);
        } else {
            YearPeriodDO periodUtilTemp = YearPeriodUtil.transform(null, (String)(periodUtil.getYear() - 1 + periodUtil.getFormatValue().substring(4)));
            lastMonthZbValue = minusThePreviousYear ? this.getCurrMonthZbValue(zbCode, dsTemp, periodUtilTemp, 12) : 0.0;
        }
        for (int i = beginMonth.intValue(); i <= endMonth; ++i) {
            currMonthZbValue = this.getCurrMonthZbValue(zbCode, dsTemp, periodUtil, i);
            currTotalEquityRatio = this.addCurrMonthEquityRatio(month2InvestItemsMap, calcMode, currTotalEquityRatio, i);
            double calcAmt = NumberUtils.mul((double)NumberUtils.sub((double)currMonthZbValue, (double)lastMonthZbValue), (double)(oneSubSumScale ? 1.0 - currTotalEquityRatio : currTotalEquityRatio));
            sumAmt = NumberUtils.sum((double)sumAmt, (double)calcAmt);
            this.appendPenetrateInfo(oneSubSumScale ? NumberUtils.sub((double)1.0, (double)currTotalEquityRatio) : currTotalEquityRatio, calcAmt, currMonthZbValue, lastMonthZbValue, penetrateReturnMap, i, ConverterUtils.getAsBooleanValue((Object)ds.getValue("sequityPenetrateFlag")));
            lastMonthZbValue = currMonthZbValue;
        }
        return sumAmt;
    }

    private double getCurrMonthZbValue(String zbCode, DimensionValueSet dsTemp, YearPeriodDO periodUtil, int i) {
        char typeCode = periodUtil.getTypeCode();
        String periodStr = String.format("%04d%c%04d", periodUtil.getYear() % 10000, Character.valueOf((char)PeriodConsts.typeToCode((int)4)), i % 10000);
        Map<String, Object> zb2ZbMappingVO = zb2ZbMappingVOLocal.get();
        MonthCalcZbMappingVO monthCalcZbMappingVO = (MonthCalcZbMappingVO)zb2ZbMappingVO.get(zbCode);
        if (null == monthCalcZbMappingVO) {
            return this.getZbValue(zbCode, dsTemp, periodStr);
        }
        if (typeCode != (char)PeriodConsts.typeToCode((int)4)) {
            String string = zbCode = StringUtils.isEmpty((String)monthCalcZbMappingVO.getZb_Y()) ? zbCode : monthCalcZbMappingVO.getZb_Y();
        }
        if (typeCode == (char)PeriodConsts.typeToCode((int)1) && i % 12 == 0) {
            if (!CollectionUtils.isEmpty((Collection)monthCalcZbMappingVO.getZb_N())) {
                List zbNList = monthCalcZbMappingVO.getZb_N();
                for (String zbN : zbNList) {
                    if (!zbN.contains((String)dsTemp.getValue("TASKID"))) continue;
                    zbCode = zbN.split(":")[1];
                    break;
                }
                periodUtil = YearPeriodUtil.transform(null, (int)periodUtil.getYear(), (int)1, (int)(i / 12));
                periodStr = periodUtil.getFormatValue();
            }
        } else if (typeCode == (char)PeriodConsts.typeToCode((int)2) || typeCode == (char)PeriodConsts.typeToCode((int)1) && i % 6 == 0 && !StringUtils.isEmpty((String)monthCalcZbMappingVO.getZb_H())) {
            String zbh = monthCalcZbMappingVO.getZb_H();
            periodUtil = YearPeriodUtil.transform(null, (int)periodUtil.getYear(), (int)2, (int)(i / 6));
            periodStr = periodUtil.getFormatValue();
            zbCode = zbh;
        } else if ((typeCode == (char)PeriodConsts.typeToCode((int)3) || typeCode == (char)PeriodConsts.typeToCode((int)2) || typeCode == (char)PeriodConsts.typeToCode((int)1) && i % 3 == 0) && !StringUtils.isEmpty((String)monthCalcZbMappingVO.getZb_J())) {
            String zbJ = monthCalcZbMappingVO.getZb_J();
            periodUtil = YearPeriodUtil.transform(null, (int)periodUtil.getYear(), (int)3, (int)(i / 3));
            periodStr = periodUtil.getFormatValue();
            zbCode = zbJ;
        }
        return this.getZbValue(zbCode, dsTemp, periodStr);
    }

    private Double getZbValue(String zbCode, DimensionValueSet dsTemp, String periodStr) {
        dsTemp.setValue("DATATIME", (Object)periodStr);
        try {
            AbstractData zbValue = NrTool.getZbValue((DimensionValueSet)dsTemp, (String)zbCode);
            return null == zbValue ? 0.0 : zbValue.getAsFloat();
        }
        catch (Exception e) {
            logger.error("SEquity\u51fd\u6570\u4e2d\u83b7\u53d6\u6307\u6807\u6570\u636e \u51fa\u73b0\u5f02\u5e38" + e.getMessage(), e);
            return 0.0;
        }
    }

    private double scChange(String zbCode, DefaultTableEntity masterData, List<DefaultTableEntity> currYearItems, Map<Object, List<DefaultTableEntity>> month2InvestItemsMap, DimensionValueSet ds, Integer scChangeMonth, String calcMode, boolean oneSubSumScale, boolean minusThePreviousYear, Map<String, Object> penetrateReturnMap) {
        DimensionValueSet dsTemp = new DimensionValueSet(ds);
        boolean onylScChageCalc = null != ConverterUtils.getAsInteger((Object)scChangeMonth);
        scChangeMonth = null == ConverterUtils.getAsInteger((Object)scChangeMonth) ? this.getChangeMonth(currYearItems, ChangeTypeEnum.SC_CHANGE.getCode()) : scChangeMonth.intValue();
        double scChangeMonthEquityRatio = this.getEquityRatio(masterData, currYearItems, scChangeMonth);
        scChangeMonthEquityRatio = oneSubSumScale ? NumberUtils.sub((double)1.0, (double)scChangeMonthEquityRatio) : scChangeMonthEquityRatio;
        double sumAmt = 0.0;
        double currMonthZbValue = 0.0;
        double lastMonthZbValue = 0.0;
        YearPeriodDO periodUtil = YearPeriodUtil.transform(null, (String)ConverterUtils.getAsString((Object)dsTemp.getValue("DATATIME")));
        for (int i = 1; i <= scChangeMonth; ++i) {
            currMonthZbValue = this.getCurrMonthZbValue(zbCode, dsTemp, periodUtil, i);
            double calcAmt = NumberUtils.mul((double)NumberUtils.sub((double)currMonthZbValue, (double)lastMonthZbValue), (double)scChangeMonthEquityRatio);
            sumAmt = NumberUtils.sum((double)sumAmt, (double)calcAmt);
            this.appendPenetrateInfo(scChangeMonthEquityRatio, calcAmt, currMonthZbValue, lastMonthZbValue, penetrateReturnMap, i, ConverterUtils.getAsBooleanValue((Object)ds.getValue("sequityPenetrateFlag")));
            lastMonthZbValue = currMonthZbValue;
        }
        if (onylScChageCalc) {
            return sumAmt;
        }
        double amt = this.controlUnChange(zbCode, masterData, currYearItems, month2InvestItemsMap, ds, scChangeMonth + 1, null, calcMode, oneSubSumScale, minusThePreviousYear, penetrateReturnMap);
        return NumberUtils.sum((double)sumAmt, (double)amt);
    }

    private int getChangeMonth(List<DefaultTableEntity> currYearItems, String chageType) {
        Map<String, String> changeScenario2ChangeTypMap = changeScenario2ChangeTypThreadLocal.get();
        List fiterItems = currYearItems.stream().filter(item -> {
            String changeScenario = (String)item.getFieldValue("CHANGESCENARIO");
            String changeTypeTemp = (String)changeScenario2ChangeTypMap.get(changeScenario);
            return chageType.equals(changeTypeTemp);
        }).collect(Collectors.toList());
        Date date = (Date)((DefaultTableEntity)fiterItems.get(0)).getFieldValue("CHANGEDATE");
        int scChangeMonth = DateUtils.getDateFieldValue((Date)date, (int)2);
        return scChangeMonth;
    }

    private double unScAcquisition(String zbCode, DefaultTableEntity masterData, List<DefaultTableEntity> currYearItems, Map<Object, List<DefaultTableEntity>> month2InvestItemsMap, DimensionValueSet ds, Integer endMonth, String calcMode, boolean oneSubSumScale, boolean minusThePreviousYear, Map<String, Object> penetrateReturnMap) {
        DimensionValueSet dsTemp = new DimensionValueSet(ds);
        String periodString = ConverterUtils.getAsString((Object)dsTemp.getValue("DATATIME"));
        YearPeriodDO periodUtil = YearPeriodUtil.transform(null, (String)periodString);
        int unScAcquisitionMonth = this.getChangeMonth(currYearItems, ChangeTypeEnum.UNSC_ACQUISITION.getCode());
        double unScAcquisitionMonthEquityRatio = this.getEquityRatio(masterData, currYearItems, unScAcquisitionMonth);
        unScAcquisitionMonthEquityRatio = oneSubSumScale ? NumberUtils.sub((double)1.0, (double)unScAcquisitionMonthEquityRatio) : unScAcquisitionMonthEquityRatio;
        double acquisitionMonthZbVal = this.getCurrMonthZbValue(zbCode, dsTemp, periodUtil, unScAcquisitionMonth);
        double acquisitionLastMonthZbVal = this.getCurrMonthZbValue(zbCode, dsTemp, periodUtil, unScAcquisitionMonth - 1);
        double sumAmt = NumberUtils.mul((double)NumberUtils.sub((double)acquisitionMonthZbVal, (double)acquisitionLastMonthZbVal), (double)unScAcquisitionMonthEquityRatio);
        this.appendPenetrateInfo(unScAcquisitionMonthEquityRatio, sumAmt, acquisitionMonthZbVal, acquisitionLastMonthZbVal, penetrateReturnMap, unScAcquisitionMonth, ConverterUtils.getAsBooleanValue((Object)ds.getValue("sequityPenetrateFlag")));
        double amt = this.controlUnChange(zbCode, masterData, currYearItems, month2InvestItemsMap, ds, unScAcquisitionMonth + 1, endMonth, calcMode, oneSubSumScale, minusThePreviousYear, penetrateReturnMap);
        return NumberUtils.sum((double)sumAmt, (double)amt);
    }

    private double unScDispose(String zbCode, DefaultTableEntity masterData, List<DefaultTableEntity> currYearItems, Map<Object, List<DefaultTableEntity>> month2InvestItemsMap, DimensionValueSet ds, Integer beginMonth, String calcMode, boolean oneSubSumScale, boolean minusThePreviousYear, Map<String, Object> penetrateReturnMap) {
        int unScDisposeMonth = this.getChangeMonth(currYearItems, ChangeTypeEnum.UNSC_DISPOSE.getCode());
        if (null == beginMonth) {
            beginMonth = 1;
        }
        double sumAmt = this.controlUnChange(zbCode, masterData, currYearItems, month2InvestItemsMap, ds, beginMonth, unScDisposeMonth - 1, calcMode, oneSubSumScale, minusThePreviousYear, penetrateReturnMap);
        double disposeMonthAmt = this.getDisposeMonthAmt(zbCode, masterData, currYearItems, ds, unScDisposeMonth, penetrateReturnMap);
        return NumberUtils.sum((double)sumAmt, (double)disposeMonthAmt);
    }

    private double getDisposeMonthAmt(String zbCode, DefaultTableEntity masterData, List<DefaultTableEntity> currYearItems, DimensionValueSet ds, int disposeMonth, Map<String, Object> penetrateReturnMap) {
        double unScAcquisitionMonthEquityRatio = this.getEquityRatio(masterData, currYearItems, disposeMonth - 1);
        DimensionValueSet dsTemp = new DimensionValueSet(ds);
        String periodString = ConverterUtils.getAsString((Object)dsTemp.getValue("DATATIME"));
        YearPeriodDO periodUtil = YearPeriodUtil.transform(null, (String)periodString);
        double disposeMonthZbVal = this.getCurrMonthZbValue(zbCode, dsTemp, periodUtil, disposeMonth);
        double disposeLastMonthZbVal = this.getCurrMonthZbValue(zbCode, dsTemp, periodUtil, disposeMonth - 1);
        double disposeMonthAmt = NumberUtils.mul((double)NumberUtils.sub((double)disposeMonthZbVal, (double)disposeLastMonthZbVal), (double)unScAcquisitionMonthEquityRatio);
        this.appendPenetrateInfo(unScAcquisitionMonthEquityRatio, disposeMonthAmt, disposeMonthZbVal, disposeLastMonthZbVal, penetrateReturnMap, disposeMonth, ConverterUtils.getAsBooleanValue((Object)ds.getValue("sequityPenetrateFlag")));
        return disposeMonthAmt;
    }

    private double scChangeAndUnScDispose(String zbCode, DefaultTableEntity masterData, List<DefaultTableEntity> currYearItems, Map<Object, List<DefaultTableEntity>> month2InvestItemsMap, DimensionValueSet ds, String calcMode, boolean oneSubSumScale, boolean minusThePreviousYear, Map<String, Object> penetrateReturnMap) {
        int scChangeMonth = this.getChangeMonth(currYearItems, ChangeTypeEnum.SC_CHANGE.getCode());
        double sumAmt = this.scChange(zbCode, masterData, currYearItems, month2InvestItemsMap, ds, scChangeMonth, calcMode, oneSubSumScale, minusThePreviousYear, penetrateReturnMap);
        double amt = this.unScDispose(zbCode, masterData, currYearItems, month2InvestItemsMap, ds, scChangeMonth + 1, calcMode, oneSubSumScale, minusThePreviousYear, penetrateReturnMap);
        return NumberUtils.sum((double)sumAmt, (double)amt);
    }

    private double unScAcquisitionAndUnScDispose(String zbCode, DefaultTableEntity masterData, List<DefaultTableEntity> currYearItems, Map<Object, List<DefaultTableEntity>> month2InvestItemsMap, DimensionValueSet ds, String calcMode, boolean oneSubSumScale, boolean minusThePreviousYear, Map<String, Object> penetrateReturnMap) {
        int unScDisposeMonth = this.getChangeMonth(currYearItems, ChangeTypeEnum.UNSC_DISPOSE.getCode());
        double sumAmt = this.unScAcquisition(zbCode, masterData, currYearItems, month2InvestItemsMap, ds, unScDisposeMonth - 1, calcMode, oneSubSumScale, minusThePreviousYear, penetrateReturnMap);
        double disposeMonthAmt = this.getDisposeMonthAmt(zbCode, masterData, currYearItems, ds, unScDisposeMonth, penetrateReturnMap);
        return NumberUtils.sum((double)sumAmt, (double)disposeMonthAmt);
    }

    private double getEquityRatio(DefaultTableEntity masterData, List<DefaultTableEntity> currYearItems, int changMonth) {
        DefaultTableEntity item;
        Date changeDate;
        double equityRatio = (Double)masterData.getFieldValue("BEGINEQUITYRATIO") / 100.0;
        Iterator<DefaultTableEntity> iterator = currYearItems.iterator();
        while (iterator.hasNext() && changMonth >= DateUtils.getDateFieldValue((Date)(changeDate = (Date)(item = iterator.next()).getFieldValue("CHANGEDATE")), (int)2)) {
            Object changeRatioObj = item.getFieldValue("CHANGERATIO");
            double changeRatio = changeRatioObj == null ? 0.0 : (Double)changeRatioObj / 100.0;
            equityRatio = NumberUtils.sum((double)equityRatio, (double)changeRatio);
        }
        return equityRatio;
    }

    private double addCurrMonthEquityRatio(Map<Object, List<DefaultTableEntity>> month2InvestItemsMap, String calcMode, double currTotalEquityRatio, int i) {
        List<DefaultTableEntity> subItems = month2InvestItemsMap.get(calcMode.equals("monthlyBegin") ? i : i - 1);
        if (!CollectionUtils.isEmpty(subItems)) {
            for (DefaultTableEntity item : subItems) {
                Object changeRatioObj = item.getFieldValue("CHANGERATIO");
                double changeRatio = changeRatioObj == null ? 0.0 : (Double)changeRatioObj / 100.0;
                currTotalEquityRatio = NumberUtils.sum((double)currTotalEquityRatio, (double)changeRatio);
            }
        }
        return currTotalEquityRatio;
    }

    private void appendDescription(Map<String, Object> returnMap, String changeType, String calcMode, DimensionValueSet ds, String zbCode, List<DefaultTableEntity> currYearOrderItems) {
        int unScDisposeMonth;
        int unScAcquisitionMonth;
        int i;
        int scChangeMonth;
        if (!ConverterUtils.getAsBooleanValue((Object)ds.getValue("sequityPenetrateFlag"))) {
            return;
        }
        StringBuffer calcProcessDescription = new StringBuffer();
        FieldDefine fieldDefine = NrTool.queryFieldDefineByZbCode((String)zbCode);
        String zbTitle = fieldDefine.getTitle();
        returnMap.put("zbTitle", zbTitle);
        String periodString = (String)ds.getValue("DATATIME");
        YearPeriodObject yp = new YearPeriodObject(null, periodString);
        int period = yp.getPeriod();
        if (changeType.equals(ChangeTypeEnum.CONTROLPOWER_UNCHANGED.getCode())) {
            calcProcessDescription = calcMode.equals("monthlyEnd") ? calcProcessDescription.append("1\u6708'").append(zbTitle).append("'*\u4e3b\u8868\u671f\u521d\u80a1\u6743\u6bd4\u4f8b") : calcProcessDescription.append("1\u6708'").append(zbTitle).append("'*1\u6708\u80a1\u6743\u6bd4\u4f8b");
            for (int i2 = 2; i2 <= period; ++i2) {
                calcProcessDescription.append(String.format(" + (%1$s\u6708'%2$s'- %3$s\u6708'%2$s') * %4$s\u6708\u80a1\u6743\u6bd4\u4f8b", i2, zbTitle, i2 - 1, calcMode.equals("monthlyEnd") ? i2 - 1 : i2));
            }
        }
        if (changeType.equals(ChangeTypeEnum.SC_CHANGE.getCode())) {
            scChangeMonth = this.getChangeMonth(currYearOrderItems, ChangeTypeEnum.SC_CHANGE.getCode());
            calcProcessDescription.append("1\u6708'").append(zbTitle).append("'*").append(scChangeMonth).append("\u6708\u80a1\u6743\u6bd4\u4f8b");
            for (i = 2; i <= scChangeMonth; ++i) {
                calcProcessDescription.append(String.format("+(%1$s\u6708'%2$s'- %3$s\u6708'%2$s') * %4$s\u6708\u80a1\u6743\u6bd4\u4f8b", i, zbTitle, i - 1, scChangeMonth));
            }
            for (i = scChangeMonth + 1; i < period; ++i) {
                calcProcessDescription.append(String.format(" + (%1$s\u6708'%2$s'- %3$s\u6708'%2$s') * %4$s\u6708\u80a1\u6743\u6bd4\u4f8b", i, zbTitle, i - 1, calcMode.equals("monthlyEnd") ? i - 1 : i));
            }
        }
        if (changeType.equals(ChangeTypeEnum.UNSC_ACQUISITION.getCode())) {
            unScAcquisitionMonth = this.getChangeMonth(currYearOrderItems, ChangeTypeEnum.UNSC_ACQUISITION.getCode());
            calcProcessDescription.append(String.format("+ (%1$s\u6708'%2$s'- %3$s\u6708'%2$s') * %1$s\u6708\u80a1\u6743\u6bd4\u4f8b", unScAcquisitionMonth, zbTitle, unScAcquisitionMonth - 1));
            for (i = 2; i <= period; ++i) {
                calcProcessDescription.append(String.format("+(%1$s\u6708'%2$s'- %3$s\u6708'%2$s') * %4$s\u6708\u80a1\u6743\u6bd4\u4f8b", i, zbTitle, i - 1, calcMode.equals("monthlyEnd") ? i - 1 : i));
            }
        }
        if (changeType.equals(ChangeTypeEnum.UNSC_DISPOSE.getCode())) {
            int unScDisposeMonth2 = this.getChangeMonth(currYearOrderItems, ChangeTypeEnum.UNSC_DISPOSE.getCode());
            calcProcessDescription = calcMode.equals("monthlyEnd") ? calcProcessDescription.append("1\u6708'").append(zbTitle).append("'*\u4e3b\u8868\u671f\u521d\u80a1\u6743\u6bd4\u4f8b") : calcProcessDescription.append("1\u6708'").append(zbTitle).append("'*1\u6708\u80a1\u6743\u6bd4\u4f8b");
            for (i = 2; i < unScDisposeMonth2; ++i) {
                calcProcessDescription.append(String.format(" + (%1$s\u6708'%2$s'- %3$s\u6708'%2$s') * %4$s\u6708\u80a1\u6743\u6bd4\u4f8b", i, zbTitle, i - 1, calcMode.equals("monthlyEnd") ? i - 1 : i));
            }
            calcProcessDescription.append(String.format(" + (%1$s\u6708'%2$s'- %3$s\u6708'%2$s') * %3$s\u6708\u80a1\u6743\u6bd4\u4f8b", unScDisposeMonth2, zbTitle, unScDisposeMonth2 - 1));
        }
        if (changeType.equals(ChangeTypeEnum.SC_CHANGE.getCode() + "," + ChangeTypeEnum.UNSC_DISPOSE.getCode())) {
            scChangeMonth = this.getChangeMonth(currYearOrderItems, ChangeTypeEnum.SC_CHANGE.getCode());
            calcProcessDescription.append("1\u6708'").append(zbTitle).append("'*").append(scChangeMonth).append("\u6708\u80a1\u6743\u6bd4\u4f8b");
            for (i = 2; i <= scChangeMonth; ++i) {
                calcProcessDescription.append(String.format(" + (%1$s\u6708'%2$s'- %3$s\u6708'%2$s') * %4$s\u6708\u80a1\u6743\u6bd4\u4f8b", i, zbTitle, i - 1, scChangeMonth));
            }
            unScDisposeMonth = this.getChangeMonth(currYearOrderItems, ChangeTypeEnum.UNSC_DISPOSE.getCode());
            for (int i3 = scChangeMonth + 1; i3 < unScDisposeMonth; ++i3) {
                calcProcessDescription.append(String.format(" + (%1$s\u6708'%2$s'- %3$s\u6708'%2$s') * %4$s\u6708\u80a1\u6743\u6bd4\u4f8b", i3, zbTitle, i3 - 1, calcMode.equals("monthlyEnd") ? i3 - 1 : i3));
            }
            calcProcessDescription.append(String.format(" + (%1$s\u6708'%2$s'- %3$s\u6708'%2$s') * %3$s\u6708\u80a1\u6743\u6bd4\u4f8b", unScDisposeMonth, zbTitle, unScDisposeMonth - 1));
        }
        if (changeType.equals(ChangeTypeEnum.UNSC_ACQUISITION.getCode() + "," + ChangeTypeEnum.UNSC_DISPOSE.getCode())) {
            unScAcquisitionMonth = this.getChangeMonth(currYearOrderItems, ChangeTypeEnum.UNSC_ACQUISITION.getCode());
            calcProcessDescription.append(String.format(" + (%1$s\u6708'%2$s'- %3$s\u6708'%2$s') * %1$s\u6708\u80a1\u6743\u6bd4\u4f8b", unScAcquisitionMonth, zbTitle, unScAcquisitionMonth - 1));
            for (i = 2; i <= period; ++i) {
                calcProcessDescription.append(String.format(" + (%1$s\u6708'%2$s'- %3$s\u6708'%2$s') * %4$s\u6708\u80a1\u6743\u6bd4\u4f8b", i, zbTitle, i - 1, calcMode.equals("monthlyEnd") ? i - 1 : i));
            }
            unScDisposeMonth = this.getChangeMonth(currYearOrderItems, ChangeTypeEnum.UNSC_DISPOSE.getCode());
            calcProcessDescription.append(String.format(" + (%1$s\u6708'%2$s'- %3$s\u6708'%2$s') * %3$s\u6708\u80a1\u6743\u6bd4\u4f8b", unScDisposeMonth, zbTitle, unScDisposeMonth - 1));
        }
        returnMap.put("calcProcessDes", calcProcessDescription.toString());
    }

    private void appendPenetrateInfo(double currTotalEquityRatio, double sumAmt, double currMonthZbValue, double lastMonthZbValue, Map<String, Object> penetrateReturnMap, int i, boolean sequityPenetrateFlag) {
        if (sequityPenetrateFlag) {
            LinkedHashMap<String, Object> calcProcessData = new LinkedHashMap<String, Object>();
            calcProcessData.put("month", i);
            calcProcessData.put("zbValue", NumberUtils.doubleToString((double)currMonthZbValue));
            calcProcessData.put("equityRatio", NumberUtils.doubleToString((double)(currTotalEquityRatio * 100.0)) + "%");
            calcProcessData.put("calcProcess", "(" + NumberUtils.doubleToString((double)currMonthZbValue) + "-" + NumberUtils.doubleToString((double)lastMonthZbValue) + ")*" + NumberUtils.doubleToString((double)(currTotalEquityRatio * 100.0)) + "%");
            calcProcessData.put("calcAmt", NumberUtils.doubleToString((double)sumAmt));
            List calcProcessDataList = (List)penetrateReturnMap.get("calcProcessDatas");
            calcProcessDataList.add(calcProcessData);
        }
    }

    private Set<String> getChangeTypeSet(List<DefaultTableEntity> currYearItems) {
        if (null == changeScenario2ChangeTypThreadLocal.get() || changeScenario2ChangeTypThreadLocal.get().size() == 0) {
            this.initChangeScenario2ChangeTypeMap();
        }
        Map<String, String> code2ChangeTypeMap = changeScenario2ChangeTypThreadLocal.get();
        Set<String> changeTypeSet = currYearItems.stream().map(item -> (String)code2ChangeTypeMap.get(item.getFieldValue("CHANGESCENARIO"))).collect(Collectors.toSet());
        changeTypeSet.removeIf(Objects::isNull);
        return changeTypeSet;
    }

    private List<DefaultTableEntity> getCurrYearAndMonthItems(DefaultTableEntity masterData, List<DefaultTableEntity> items, String periodStr) {
        YearPeriodDO periodUtil = YearPeriodUtil.transform(null, (String)periodStr);
        int calcMonth = periodUtil.getEndDate().getMonth() + 1;
        String masterYearStr = ConverterUtils.getAsString((Object)masterData.getFieldValue("ACCTYEAR"));
        if (StringUtils.isEmpty((String)masterYearStr)) {
            return new ArrayList<DefaultTableEntity>();
        }
        Integer masterYear = ConverterUtils.getAsInteger((Object)masterYearStr);
        Calendar calendar = Calendar.getInstance();
        ArrayList<DefaultTableEntity> currYearItems = new ArrayList<DefaultTableEntity>(16);
        if (!CollectionUtils.isEmpty(items)) {
            items.forEach(item -> {
                if (null == item.getFieldValue("CHANGEDATE")) {
                    return;
                }
                calendar.setTime((Date)item.getFieldValue("CHANGEDATE"));
                int itemYear = calendar.get(1);
                int itemMonth = calendar.get(2) + 1;
                if (masterYear == itemYear && itemMonth <= calcMonth) {
                    currYearItems.add((DefaultTableEntity)item);
                }
            });
        }
        this.sortItems(currYearItems);
        return currYearItems;
    }

    private DimensionValueSet getDimensionSet(QueryContext queryContext) {
        GcReportExceutorContext executorContext = (GcReportExceutorContext)queryContext.getExeContext();
        DefaultTableEntity masterData = executorContext.getData();
        DimensionValueSet ds = new DimensionValueSet(queryContext.getCurrentMasterKey());
        String unitId = this.getUnit(masterData, ds);
        ds.setValue("MD_ORG", (Object)unitId);
        ds.setValue("TASKID", (Object)executorContext.getTaskId());
        return ds;
    }

    private String getUnit(DefaultTableEntity data, DimensionValueSet ds) {
        String unitId;
        String periodString;
        YearPeriodObject yp;
        String orgTypeId = String.valueOf(ds.getValue("MD_GCORGTYPE"));
        GcOrgCenterService orgCenterService = GcOrgPublicTool.getInstance((String)orgTypeId, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)(yp = new YearPeriodObject(null, periodString = (String)ds.getValue("DATATIME"))));
        GcOrgCacheVO hbOrg = orgCenterService.getUnionUnitByGrade(unitId = StringUtils.toViewString((Object)data.getFieldValue("INVESTEDUNIT")));
        if (null != hbOrg) {
            unitId = hbOrg.getId();
        }
        return unitId;
    }

    private List sortItems(List<DefaultTableEntity> items) {
        if (CollectionUtils.isEmpty(items)) {
            return new ArrayList();
        }
        items.sort((o1, o2) -> {
            Object o2ChangeDate = o2.getFieldValue("CHANGEDATE");
            if (o2ChangeDate == null) {
                return -1;
            }
            Object o1ChangeDate = o1.getFieldValue("CHANGEDATE");
            if (o1ChangeDate == null) {
                return 1;
            }
            return ((Date)o1ChangeDate).compareTo((Date)o2ChangeDate);
        });
        return items;
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append("\uff1b").append("\u8ba1\u7b97\u7ed3\u679c").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u5c11\u6570\u80a1\u4e1c\u635f\u76ca\u62b5\u9500-\u5206\u6bb5\u6295\u8d44").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("SEquity('ZCOX_YB02[B43]',true)").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }

    public void enableCache() {
        zb2ZbMappingVOLocal.set(new HashMap());
        changeScenario2ChangeTypThreadLocal.set(new HashMap());
    }

    public void releaseCache() {
        zb2ZbMappingVOLocal.remove();
        changeScenario2ChangeTypThreadLocal.remove();
    }
}

