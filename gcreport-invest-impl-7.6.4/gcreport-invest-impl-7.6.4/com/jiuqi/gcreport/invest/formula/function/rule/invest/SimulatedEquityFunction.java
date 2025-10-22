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
 *  com.jiuqi.common.base.util.DoubleKeyMap
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.nr.function.IGcFunction
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.unionrule.dto.AbstractInvestmentRule
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.DirectInvestmentDTO
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.gcreport.invest.formula.function.rule.invest;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DoubleKeyMap;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.nr.function.IGcFunction;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.unionrule.dto.AbstractInvestmentRule;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.DirectInvestmentDTO;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class SimulatedEquityFunction
extends Function
implements IGcFunction {
    private static final long serialVersionUID = 1L;
    public final String FUNCTION_NAME = "GETMNQYFTZ";
    private static transient ThreadLocal<DoubleKeyMap> unitCode2SubjectCode2OffsetValueHasInitLocal = new ThreadLocal();
    private static transient ThreadLocal<DoubleKeyMap> unitCode2SubjectCode2OffsetValueNoInitLocal = new ThreadLocal();
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;

    public SimulatedEquityFunction() {
        this.parameters().add(new Parameter("subjectCode", 6, "\u79d1\u76ee\u4ee3\u7801"));
        this.parameters().add(new Parameter("statisticInitEntry", 1, "\u5305\u542b\u6765\u81ea0\u671f\u7684\u6570\u636e\uff0c\u7f3a\u7701\u5305\u542b", true));
    }

    public String name() {
        return "GETMNQYFTZ";
    }

    public String title() {
        return "\u53d6\u6a21\u62df\u6743\u76ca\u6cd5\u8c03\u6574\u5206\u5f55\u79d1\u76ee\u6570\u636e";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
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
        String periodStr = (String)dimensionValueSet.getValue("DATATIME");
        String schemeId = executorContext.getSchemeId();
        String subjectCode = (String)parameters.get(0).evaluate(null);
        boolean statisticInitEntry = parameters.size() < 2 || !Boolean.FALSE.equals(parameters.get(1).evaluate(null));
        double result = 0.0;
        if (masterData != null) {
            HashSet<String> allSubjectCodes = new HashSet<String>(32);
            HashSet<String> creditSubjectCodes = new HashSet<String>(32);
            this.initSubjectCodes(schemeId, periodStr, subjectCode, allSubjectCodes, creditSubjectCodes);
            if (allSubjectCodes.isEmpty()) {
                return result;
            }
            Set<Integer> initOffSetSrcType = this.initOffsetSrcType();
            List<GcOffSetVchrItemAdjustEO> adjustEOs = this.queryData(executorContext, periodStr, dimensionValueSet, masterData, allSubjectCodes, statisticInitEntry);
            for (GcOffSetVchrItemAdjustEO record : adjustEOs) {
                Double offSetCredit = record.getOffSetCredit();
                Double offSetDebit = record.getOffSetDebit();
                if (!statisticInitEntry && initOffSetSrcType.contains(record.getOffSetSrcType())) continue;
                result += NumberUtils.sub((Double)offSetDebit, (Double)offSetCredit);
            }
            if (creditSubjectCodes.contains(subjectCode)) {
                result *= -1.0;
            }
        }
        return result;
    }

    private Set<Integer> initOffsetSrcType() {
        return OffSetSrcTypeEnum.getAllInitOffSetSrcTypeValue();
    }

    private List<ConsolidatedSubjectEO> initSubjectCodes(String schemeId, String periodStr, String subjectCode, Set<String> allSubjectCodes, Set<String> creditSubjectCodes) {
        ConsolidatedSubjectService subjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class);
        String reportSystemId = ((ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class)).getSystemIdBySchemeId(schemeId, periodStr);
        if (null == reportSystemId) {
            return new ArrayList<ConsolidatedSubjectEO>();
        }
        ConsolidatedSubjectEO subjectEo = subjectService.getSubjectByCode(reportSystemId, subjectCode);
        if (null == subjectEo) {
            return new ArrayList<ConsolidatedSubjectEO>();
        }
        ArrayList<ConsolidatedSubjectEO> allSubSubjectEos = new ArrayList<ConsolidatedSubjectEO>();
        allSubSubjectEos.addAll(subjectService.listAllChildrenSubjects(reportSystemId, subjectCode));
        allSubSubjectEos.add(subjectEo);
        for (ConsolidatedSubjectEO subject : allSubSubjectEos) {
            if (subject.getOrient() == OrientEnum.C.getValue()) {
                creditSubjectCodes.add(subject.getCode());
            }
            allSubjectCodes.add(subject.getCode());
        }
        return allSubSubjectEos;
    }

    private List<GcOffSetVchrItemAdjustEO> queryData(GcReportExceutorContext exceutorContext, String period, DimensionValueSet dimensionValueSet, DefaultTableEntity data, Set<String> allSubjectCodes, boolean statisticInitEntry) {
        DoubleKeyMap unitCode2SubjectCode2OffsetValue;
        String periodString = (String)dimensionValueSet.getValue("DATATIME");
        if (null == periodString) {
            periodString = (String)dimensionValueSet.getValue("DATATIME");
        }
        String currencyCode = (String)dimensionValueSet.getValue("MD_CURRENCY");
        String investUnit = StringUtils.toViewString((Object)data.getFieldValue("UNITCODE"));
        String investedUnit = StringUtils.toViewString((Object)data.getFieldValue("INVESTEDUNIT"));
        String orgType = (String)dimensionValueSet.getValue("MD_GCORGTYPE");
        Assert.notNull((Object)exceutorContext.getTaskId(), "\u4efb\u52a1ID\u4e0d\u80fd\u4e3a\u7a7a");
        String reportSystem = this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(exceutorContext.getTaskId(), period);
        List<String> ruleTypes = Arrays.asList(RuleTypeEnum.DIRECT_INVESTMENT.getCode(), RuleTypeEnum.INDIRECT_INVESTMENT.getCode());
        List rules = ((UnionRuleService)SpringContextUtils.getBean(UnionRuleService.class)).selectAllRuleListByReportSystemAndRuleTypes(reportSystem, ruleTypes);
        Set<String> ruleIds = rules.stream().filter(AbstractUnionRule::getStartFlag).filter(rule -> ((AbstractInvestmentRule)rule).getEquityLawAdjustFlag()).map(AbstractUnionRule::getId).collect(Collectors.toSet());
        DoubleKeyMap doubleKeyMap = unitCode2SubjectCode2OffsetValue = statisticInitEntry ? unitCode2SubjectCode2OffsetValueHasInitLocal.get() : unitCode2SubjectCode2OffsetValueNoInitLocal.get();
        if (null != unitCode2SubjectCode2OffsetValue) {
            if (unitCode2SubjectCode2OffsetValue.isEmpty()) {
                if (CollectionUtils.isEmpty(ruleIds)) {
                    return new ArrayList<GcOffSetVchrItemAdjustEO>();
                }
                this.loadCacheUnitCode2SubjectCode2OffsetValue(exceutorContext.getTaskId(), periodString, currencyCode, ruleIds, orgType, (String)dimensionValueSet.getValue("ADJUST"));
            }
            return this.loadDataFromCache((DoubleKeyMap<String, String, Double>)unitCode2SubjectCode2OffsetValue, investUnit, investedUnit, allSubjectCodes);
        }
        if (CollectionUtils.isEmpty(ruleIds)) {
            return new ArrayList<GcOffSetVchrItemAdjustEO>();
        }
        return this.loadFromDbNoCache(exceutorContext.getTaskId(), investUnit, investedUnit, periodString, currencyCode, allSubjectCodes, ruleIds, statisticInitEntry, orgType, (String)dimensionValueSet.getValue("ADJUST"));
    }

    private Set<String> getResultIds(List<UnionRuleVO> ruleVOS) {
        HashSet<String> resultIds = new HashSet<String>();
        for (UnionRuleVO rule : ruleVOS) {
            AbstractInvestmentRule investmentRule;
            String jsonString;
            if (!CollectionUtils.isEmpty((Collection)rule.getChildren())) {
                resultIds.addAll(this.getResultIds(rule.getChildren()));
            }
            if (StringUtils.isEmpty((String)(jsonString = rule.getJsonString())) || (investmentRule = (AbstractInvestmentRule)JsonUtils.readValue((String)jsonString, DirectInvestmentDTO.class)) == null || !investmentRule.getEquityLawAdjustFlag().booleanValue()) continue;
            resultIds.add(rule.getId());
        }
        return resultIds;
    }

    private List<GcOffSetVchrItemAdjustEO> loadFromDbNoCache(String taskId, String investUnit, String investedUnit, String periodString, String currencyCode, Set<String> allSubjectCodes, Set<String> ruleIds, boolean statisticInitEntry, String orgType, String adjust) {
        String systemId = this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(taskId, periodString);
        Set<Integer> initOffSetSrcType = this.initOffsetSrcType();
        ArrayList<GcOffSetVchrItemAdjustEO> result = new ArrayList<GcOffSetVchrItemAdjustEO>();
        List<String> orgTypes = Arrays.asList("NONE", orgType);
        ArrayList<String> columnNamesList = new ArrayList<String>(Arrays.asList("systemId", "DATATIME", "unitId", "oppunitid", "subjectCode", "offSetCurr", "RULEID", "MD_GCORGTYPE"));
        ArrayList<Object> columnValueList = new ArrayList<Object>(Arrays.asList(systemId, periodString, investUnit, investedUnit, allSubjectCodes, currencyCode, ruleIds, orgTypes));
        if (DimensionUtils.isExistAdjust((String)taskId)) {
            columnNamesList.add("ADJUST");
            columnValueList.add(adjust);
        }
        List unitResult = ((GcOffSetAppOffsetService)SpringContextUtils.getBean(GcOffSetAppOffsetService.class)).listOffsetRecordsByWhere(columnNamesList.toArray(new String[0]), columnValueList.toArray());
        columnNamesList = new ArrayList<String>(Arrays.asList("systemId", "DATATIME", "unitId", "oppunitid", "subjectCode", "offSetCurr", "RULEID", "MD_GCORGTYPE"));
        columnValueList = new ArrayList<Object>(Arrays.asList(systemId, periodString, investedUnit, investUnit, allSubjectCodes, currencyCode, ruleIds, orgTypes));
        if (DimensionUtils.isExistAdjust((String)taskId)) {
            columnNamesList.add("ADJUST");
            columnValueList.add(adjust);
        }
        List oppUnitResult = ((GcOffSetAppOffsetService)SpringContextUtils.getBean(GcOffSetAppOffsetService.class)).listOffsetRecordsByWhere(columnNamesList.toArray(new String[0]), columnValueList.toArray());
        if (statisticInitEntry) {
            result.addAll(unitResult);
            result.addAll(oppUnitResult);
        } else {
            this.filterAndAddValue(result, unitResult, initOffSetSrcType);
            this.filterAndAddValue(result, oppUnitResult, initOffSetSrcType);
        }
        return result;
    }

    private void filterAndAddValue(List<GcOffSetVchrItemAdjustEO> result, List<GcOffSetVchrItemAdjustEO> oppUnitResult, Set<Integer> initOffSetSrcType) {
        for (GcOffSetVchrItemAdjustEO adjustEO : oppUnitResult) {
            if (initOffSetSrcType.contains(adjustEO.getOffSetSrcType())) continue;
            result.add(adjustEO);
        }
    }

    private List<GcOffSetVchrItemAdjustEO> loadDataFromCache(DoubleKeyMap<String, String, Double> unitCode2SubjectCode2OffsetValue, String investUnit, String investedUnit, Set<String> allSubjectCodes) {
        ArrayList<GcOffSetVchrItemAdjustEO> result = new ArrayList<GcOffSetVchrItemAdjustEO>();
        String cacheUnitKey = this.getCacheUnitKey(investUnit, investedUnit);
        for (String subjectCode : allSubjectCodes) {
            Double subjectCode2OffsetValue = (Double)unitCode2SubjectCode2OffsetValue.get((Object)cacheUnitKey, (Object)subjectCode);
            if (null == subjectCode2OffsetValue) continue;
            GcOffSetVchrItemAdjustEO adjustEO = new GcOffSetVchrItemAdjustEO();
            adjustEO.setUnitId(investUnit);
            adjustEO.setOppUnitId(investedUnit);
            adjustEO.setSubjectCode(subjectCode);
            adjustEO.setOffSetDebit(subjectCode2OffsetValue);
            adjustEO.setOffSetCredit(Double.valueOf(0.0));
            result.add(adjustEO);
        }
        return result;
    }

    private String getCacheUnitKey(String investUnit, String investedUnit) {
        if (investUnit.compareTo(investedUnit) > 0) {
            return investedUnit + "|" + investUnit;
        }
        return investUnit + "|" + investedUnit;
    }

    private void loadCacheUnitCode2SubjectCode2OffsetValue(String taskId, String periodString, String currencyCode, Set<String> ruleIds, String orgType, String adjust) {
        QueryParamsVO queryParamsVO = new QueryParamsVO();
        queryParamsVO.setPeriodStr(periodString);
        queryParamsVO.setCurrency(currencyCode);
        queryParamsVO.setTaskId(taskId);
        ArrayList<String> ruleIdList = new ArrayList<String>();
        ruleIdList.addAll(ruleIds);
        queryParamsVO.setRules(ruleIdList);
        queryParamsVO.setOrgType(orgType);
        queryParamsVO.setSelectAdjustCode(adjust);
        List adjustEOS = ((GcOffSetAppOffsetService)SpringContextUtils.getBean(GcOffSetAppOffsetService.class)).listFairValueFuncRecords(queryParamsVO);
        this.addValue(adjustEOS);
    }

    private void addValue(List<GcOffSetVchrItemAdjustEO> adjustEOS) {
        Set<Integer> initOffSetSrcType = this.initOffsetSrcType();
        DoubleKeyMap unitCode2SubjectCode2OffsetValueHasInit = unitCode2SubjectCode2OffsetValueHasInitLocal.get();
        DoubleKeyMap unitCode2SubjectCode2OffsetValueNoInit = unitCode2SubjectCode2OffsetValueNoInitLocal.get();
        for (GcOffSetVchrItemAdjustEO adjustEO : adjustEOS) {
            String cacheUnitKey = this.getCacheUnitKey(adjustEO.getUnitId(), adjustEO.getOppUnitId());
            Double offSetValue = NumberUtils.sub((Double)adjustEO.getOffSetDebit(), (Double)adjustEO.getOffSetCredit());
            if (adjustEO.getUnitId().equals(adjustEO.getOppUnitId())) {
                if (!initOffSetSrcType.contains(adjustEO.getOffSetSrcType())) {
                    this.addValue((DoubleKeyMap<String, String, Double>)unitCode2SubjectCode2OffsetValueNoInit, cacheUnitKey, adjustEO.getSubjectCode(), offSetValue);
                }
                this.addValue((DoubleKeyMap<String, String, Double>)unitCode2SubjectCode2OffsetValueHasInit, cacheUnitKey, adjustEO.getSubjectCode(), offSetValue);
                continue;
            }
            if (!initOffSetSrcType.contains(adjustEO.getOffSetSrcType())) {
                this.addValue((DoubleKeyMap<String, String, Double>)unitCode2SubjectCode2OffsetValueNoInit, cacheUnitKey, adjustEO.getSubjectCode(), offSetValue);
            }
            this.addValue((DoubleKeyMap<String, String, Double>)unitCode2SubjectCode2OffsetValueHasInit, cacheUnitKey, adjustEO.getSubjectCode(), offSetValue);
        }
        this.fillValueIfEmpty((DoubleKeyMap<String, String, Double>)unitCode2SubjectCode2OffsetValueHasInit, (DoubleKeyMap<String, String, Double>)unitCode2SubjectCode2OffsetValueNoInit);
    }

    private void fillValueIfEmpty(DoubleKeyMap<String, String, Double> unitCode2SubjectCode2OffsetValueHasInit, DoubleKeyMap<String, String, Double> unitCode2SubjectCode2OffsetValueNoInit) {
        if (unitCode2SubjectCode2OffsetValueHasInit.isEmpty()) {
            unitCode2SubjectCode2OffsetValueHasInit.put((Object)UUIDUtils.emptyUUIDStr(), (Object)"", (Object)0.0);
        }
        if (unitCode2SubjectCode2OffsetValueNoInit.isEmpty()) {
            unitCode2SubjectCode2OffsetValueNoInit.put((Object)UUIDUtils.emptyUUIDStr(), (Object)"", (Object)0.0);
        }
    }

    private void addValue(DoubleKeyMap<String, String, Double> unitCode2SubjectCode2OffsetValue, String cacheUnitKey, String subjectCode, Double offSetValue) {
        Double oldValue = (Double)unitCode2SubjectCode2OffsetValue.get((Object)cacheUnitKey, (Object)subjectCode);
        if (null == offSetValue) {
            offSetValue = 0.0;
        }
        if (null != oldValue) {
            offSetValue = offSetValue + oldValue;
        }
        unitCode2SubjectCode2OffsetValue.put((Object)cacheUnitKey, (Object)subjectCode, (Object)offSetValue);
    }

    public static void enableCache() {
        unitCode2SubjectCode2OffsetValueHasInitLocal.set(new DoubleKeyMap());
        unitCode2SubjectCode2OffsetValueNoInitLocal.set(new DoubleKeyMap());
    }

    public static void releaseCache() {
        unitCode2SubjectCode2OffsetValueHasInitLocal.remove();
        unitCode2SubjectCode2OffsetValueNoInitLocal.remove();
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append("\uff1b").append("\u6a21\u62df\u6743\u76ca\u6cd5\u8c03\u6574\u5206\u5f55\u79d1\u76ee\u6570\u636e").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u53d6\u67d0\u79d1\u76ee\u7684\u6a21\u62df\u6743\u76ca\u6cd5\u89c4\u5219\u751f\u6210\u7684\u62b5\u9500\u6570\u3002 ").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GETMNQYFTZ('1231')").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }
}

