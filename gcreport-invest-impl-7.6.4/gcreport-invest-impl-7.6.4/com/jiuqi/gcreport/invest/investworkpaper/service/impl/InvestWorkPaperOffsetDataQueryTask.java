/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperQueryCondition
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperRowData
 *  com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperSettingVo$SettingData
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.unionrule.dto.AbstractInvestmentRule
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.PublicValueAdjustmentRuleDTO
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.jiuqi.gcreport.invest.investworkpaper.service.impl;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.invest.investworkpaper.enums.DataSourceEnum;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperQueryCondition;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperRowData;
import com.jiuqi.gcreport.investworkpaper.vo.InvestWorkPaperSettingVo;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.unionrule.dto.AbstractInvestmentRule;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.PublicValueAdjustmentRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class InvestWorkPaperOffsetDataQueryTask {
    static final String AMTORIENT = "AMTORIENT";
    static final String SUBJECTCODE = "SUBJECTCODE";
    @Autowired
    private UnionRuleService unionRuleService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskCacheService;
    @Autowired
    private GcOffSetItemAdjustCoreService offsetCoreService;
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;

    public List<InvestWorkPaperRowData> assembleOffsetData(List<Map<String, Object>> investBills, InvestWorkPaperSettingVo.SettingData settingData, InvestWorkPaperQueryCondition condition) {
        List rules;
        List<String> ruleIds = this.getRuleIds(settingData, condition);
        if (CollectionUtils.isEmpty(ruleIds)) {
            List<String> ruleTypes = Arrays.asList(RuleTypeEnum.DIRECT_INVESTMENT.getCode(), RuleTypeEnum.INDIRECT_INVESTMENT.getCode(), RuleTypeEnum.PUBLIC_VALUE_ADJUSTMENT.getCode());
            rules = this.unionRuleService.selectRuleListBySchemeIdAndRuleTypes(condition.getSchemeId(), condition.getPeriodStr(), ruleTypes);
            ruleIds = rules.stream().map(AbstractUnionRule::getId).collect(Collectors.toList());
        } else {
            rules = this.unionRuleService.selectUnionRuleDTOByIdList(ruleIds);
            List<String> finalRuleIds = ruleIds;
            rules = rules.stream().sorted(Comparator.comparingInt(rule -> finalRuleIds.indexOf(rule.getId()))).collect(Collectors.toList());
        }
        List<UnitAndMergeTypeKey> unitAndMergeTypeKeys = this.getUnitAndMergeTypeKeys(investBills);
        QueryParamsVO queryParamsVO = this.convertQueryParams(condition, ruleIds);
        Map<Object, List<Map<String, Object>>> ruleId2OffsetDatas = this.getRuleId2OffsetDatas(queryParamsVO);
        Map<String, String> subject2TitleMap = this.getSubject2TitleMap(queryParamsVO);
        ArrayList<InvestWorkPaperRowData> tableRowDatas = new ArrayList<InvestWorkPaperRowData>();
        for (AbstractUnionRule rule2 : rules) {
            String ruleType = rule2.getRuleType();
            List<Map<String, Object>> ruleOffsetDatas = ruleId2OffsetDatas.get(rule2.getId());
            if (CollectionUtils.isEmpty(ruleOffsetDatas)) continue;
            ruleOffsetDatas.forEach(offsetData -> offsetData.put(AMTORIENT, InvestWorkPaperOffsetDataQueryTask.getAmtOrient(offsetData)));
            List<String> ruleDebitSubjectOrderList = this.getDebitSubjectOrderList(rule2);
            List<String> ruleCreditSubjectOrderList = this.getCreditSubjectOrderList(rule2);
            ArrayList<String> ruleSubjectOrderList = new ArrayList<String>();
            ruleSubjectOrderList.addAll(ruleDebitSubjectOrderList);
            ruleSubjectOrderList.addAll(ruleCreditSubjectOrderList);
            ruleOffsetDatas.sort(InvestWorkPaperOffsetDataQueryTask.mapUniversalComparator(ruleDebitSubjectOrderList, ruleCreditSubjectOrderList, ruleSubjectOrderList));
            LinkedHashMap subjectCodeAndOrient2OffsetDatas = ruleOffsetDatas.stream().collect(Collectors.groupingBy(offsetData -> (String)offsetData.get(SUBJECTCODE) + "_" + offsetData.get(AMTORIENT), LinkedHashMap::new, Collectors.toList()));
            subjectCodeAndOrient2OffsetDatas.forEach((subjectCodeAndOrient, offsetDatas) -> {
                String subjectCode = subjectCodeAndOrient.split("_")[0];
                InvestWorkPaperRowData rowData = this.getRowData(subject2TitleMap, rule2, subjectCode, (List<Map<String, Object>>)offsetDatas);
                for (UnitAndMergeTypeKey key : unitAndMergeTypeKeys) {
                    String investedUnitUnderlineInvestUnit = key.getCombinedUnit();
                    List<Map<String, Object>> matchUnitOffsetData = this.getMatchUnitOffsetData((List<Map<String, Object>>)offsetDatas, investedUnitUnderlineInvestUnit);
                    if (CollectionUtils.isEmpty(matchUnitOffsetData)) continue;
                    this.appendRowData(rowData, investedUnitUnderlineInvestUnit, matchUnitOffsetData);
                }
                this.setNotCurrentLevelInvestOffsetData((List<Map<String, Object>>)offsetDatas, rowData);
                tableRowDatas.add(rowData);
            });
        }
        return tableRowDatas;
    }

    private void appendRowData(InvestWorkPaperRowData rowData, String investedUnitUnderlineInvestUnit, List<Map<String, Object>> matchUnitOffsetData) {
        BigDecimal debitValue = BigDecimal.ZERO;
        BigDecimal creditValue = BigDecimal.ZERO;
        if (!CollectionUtils.isEmpty(matchUnitOffsetData)) {
            for (Map<String, Object> offsetData : matchUnitOffsetData) {
                debitValue = debitValue.add(ConverterUtils.getAsBigDecimal((Object)offsetData.get("DEBITVALUE")));
                creditValue = creditValue.add(ConverterUtils.getAsBigDecimal((Object)offsetData.get("CREDITVALUE")));
            }
        }
        rowData.addDynamicField(investedUnitUnderlineInvestUnit, (Object)(debitValue.compareTo(BigDecimal.ZERO) == 0 ? NumberUtils.doubleToString((double)creditValue.doubleValue()) : NumberUtils.doubleToString((double)debitValue.doubleValue())));
    }

    @NotNull
    private List<Map<String, Object>> getMatchUnitOffsetData(List<Map<String, Object>> offsetDatas, String investedUnitUnderlineInvestUnit) {
        List<Map<String, Object>> matchUnitOffsetData = offsetDatas.stream().filter(offsetData -> {
            String unitId = (String)offsetData.get("UNITID");
            String oppUnitId = (String)offsetData.get("OPPUNITID");
            List<String> unitList = Arrays.asList(unitId + "_" + oppUnitId, oppUnitId + "_" + unitId);
            if (unitList.contains(investedUnitUnderlineInvestUnit)) {
                offsetData.put("matchInvest", true);
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        return matchUnitOffsetData;
    }

    @NotNull
    private List<String> getDebitSubjectOrderList(AbstractUnionRule rule) {
        if (rule instanceof AbstractInvestmentRule) {
            List debitItemList = ((AbstractInvestmentRule)rule).getDebitItemList();
            return debitItemList.stream().map(item -> item.getSubjectCode()).collect(Collectors.toList());
        }
        if (rule instanceof PublicValueAdjustmentRuleDTO) {
            List debitItemList = ((PublicValueAdjustmentRuleDTO)rule).getDebitItemList();
            return debitItemList.stream().map(item -> item.getSubjectCode()).collect(Collectors.toList());
        }
        return new ArrayList<String>();
    }

    @NotNull
    private List<String> getCreditSubjectOrderList(AbstractUnionRule rule) {
        if (rule instanceof AbstractInvestmentRule) {
            List creditItemList = ((AbstractInvestmentRule)rule).getCreditItemList();
            return creditItemList.stream().map(item -> item.getSubjectCode()).collect(Collectors.toList());
        }
        if (rule instanceof PublicValueAdjustmentRuleDTO) {
            List creditItemList = ((PublicValueAdjustmentRuleDTO)rule).getCreditItemList();
            return creditItemList.stream().map(item -> item.getSubjectCode()).collect(Collectors.toList());
        }
        return new ArrayList<String>();
    }

    @NotNull
    private InvestWorkPaperRowData getRowData(Map<String, String> subject2TitleMap, AbstractUnionRule rule, String subjectCode, List<Map<String, Object>> offsetDatas) {
        InvestWorkPaperRowData rowData = new InvestWorkPaperRowData();
        rowData.setDataSource(DataSourceEnum.OFFSETDATA.getCode());
        rowData.setDataSourceTitle(rule.getTitle());
        rowData.setOrientTitle(this.getAmtOrientTitle(offsetDatas.get(0)));
        rowData.setZbTitle(subject2TitleMap.get(subjectCode));
        rowData.setRuleId(rule.getId());
        return rowData;
    }

    private void setNotCurrentLevelInvestOffsetData(List<Map<String, Object>> offsetDatas, InvestWorkPaperRowData rowData) {
        List notMatchInvest = offsetDatas.stream().filter(offsetData -> !ConverterUtils.getAsBooleanValue(offsetData.get("matchInvest"), (boolean)false)).collect(Collectors.toList());
        BigDecimal debitValue = BigDecimal.ZERO;
        BigDecimal creditValue = BigDecimal.ZERO;
        if (!CollectionUtils.isEmpty(notMatchInvest)) {
            for (Map offsetData2 : notMatchInvest) {
                debitValue = debitValue.add(ConverterUtils.getAsBigDecimal(offsetData2.get("DEBITVALUE")));
                creditValue = creditValue.add(ConverterUtils.getAsBigDecimal(offsetData2.get("CREDITVALUE")));
            }
        }
        if (debitValue.compareTo(BigDecimal.ZERO) != 0 || creditValue.compareTo(BigDecimal.ZERO) != 0) {
            rowData.addDynamicField("notCurrentLevelInvestOffset", (Object)(debitValue.compareTo(BigDecimal.ZERO) == 0 ? NumberUtils.doubleToString((double)creditValue.doubleValue()) : NumberUtils.doubleToString((double)debitValue.doubleValue())));
        }
    }

    @NotNull
    private Map<String, String> getSubject2TitleMap(QueryParamsVO queryParamsVO) {
        List allSubjectEos = this.consolidatedSubjectService.listAllSubjectsBySystemId(queryParamsVO.getSystemId());
        Map<String, String> subject2TitleMap = allSubjectEos.stream().collect(Collectors.toMap(subjectEO -> subjectEO.getCode(), subjectEO -> subjectEO.getTitle()));
        return subject2TitleMap;
    }

    @NotNull
    private Map<Object, List<Map<String, Object>>> getRuleId2OffsetDatas(QueryParamsVO queryParamsVO) {
        List<Map<String, Object>> offsetDataList = this.sumOffsetValueGroupByRule(queryParamsVO);
        Map<Object, List<Map<String, Object>>> ruleId2OffsetDatas = offsetDataList.stream().collect(Collectors.groupingBy(item -> item.get("RULEID")));
        return ruleId2OffsetDatas;
    }

    @Nullable
    private List<String> getRuleIds(InvestWorkPaperSettingVo.SettingData settingData, InvestWorkPaperQueryCondition condition) {
        List ruleIds = condition.getRuleIds();
        if (CollectionUtils.isEmpty(ruleIds)) {
            List ruleSettings = settingData.getRuleSetting();
            if (ruleSettings == null) {
                return null;
            }
            ruleIds = ruleSettings.stream().map(ruleSetting -> ruleSetting.getRuleId()).collect(Collectors.toList());
        }
        return ruleIds;
    }

    private List<UnitAndMergeTypeKey> getUnitAndMergeTypeKeys(List<Map<String, Object>> investBills) {
        List<UnitAndMergeTypeKey> combinedUnitAndMergeTypeKeys = investBills.stream().map(investBill -> {
            String investedUnit = (String)investBill.get("INVESTEDUNIT_CODE");
            String investUnit = (String)investBill.get("UNITCODE_CODE");
            String mergeType = investBill.get("MERGETYPE_CODE").toString();
            return new UnitAndMergeTypeKey(investedUnit + "_" + investUnit, mergeType);
        }).collect(Collectors.toList());
        return combinedUnitAndMergeTypeKeys;
    }

    public QueryParamsVO convertQueryParams(InvestWorkPaperQueryCondition condition, List<String> ruleIds) {
        QueryParamsVO queryParamsVO = new QueryParamsVO();
        BeanUtils.copyProperties(condition, queryParamsVO);
        queryParamsVO.setOrgId(condition.getMergeUnitId());
        queryParamsVO.setRules(ruleIds);
        queryParamsVO.setSelectAdjustCode(condition.getSelectAdjustCode());
        String reportSystemId = this.consolidatedTaskCacheService.getSystemIdBySchemeId(condition.getSchemeId(), condition.getPeriodStr());
        queryParamsVO.setSystemId(reportSystemId);
        return queryParamsVO;
    }

    public List<Map<String, Object>> sumOffsetValueGroupByRule(QueryParamsVO queryParamsVO) {
        QueryParamsDTO queryParamsDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(queryParamsVO, queryParamsDTO);
        if (StringUtils.isEmpty((String)queryParamsDTO.getSelectAdjustCode())) {
            queryParamsDTO.setSelectAdjustCode("0");
        }
        String offsetValueStr = "\nsum(record.offset_DEBIT) as debitValue,sum(record.offset_Credit) as creditValue  ";
        String selectFields = " record.unitid,record.oppUnitId,record.ruleId,record.subjectCode,record.subjectOrient,record.offsetSrcType,record.adjust, " + offsetValueStr;
        String groupStr = " record.unitid,record.oppUnitId,record.ruleId,record.subjectCode,record.subjectOrient,record.offsetSrcType,record.adjust";
        return this.offsetCoreService.sumOffsetValueGroupBy(queryParamsDTO, selectFields, groupStr);
    }

    public static Comparator<Map<String, Object>> mapUniversalComparator(List<String> ruleDebiSubjectOrder, List<String> ruleCreditSubjectOrder, List<String> ruleSubjectOrderList) {
        Comparator comparator = (record1, record2) -> {
            boolean isRuleSubject2;
            Integer amtOrient1 = (Integer)record1.get(AMTORIENT);
            Integer amtOrient2 = (Integer)record2.get(AMTORIENT);
            int result = NumberUtils.compareInt((Integer)amtOrient2, (Integer)amtOrient1);
            if (result != 0) {
                return result;
            }
            String subjectCode1 = (String)record1.get(SUBJECTCODE);
            String subjectCode2 = (String)record2.get(SUBJECTCODE);
            boolean isRuleSubject1 = ruleDebiSubjectOrder.contains(subjectCode1) && OrientEnum.D.getValue().equals(amtOrient1) || ruleCreditSubjectOrder.contains(subjectCode1) && OrientEnum.C.getValue().equals(amtOrient1);
            boolean bl = isRuleSubject2 = ruleDebiSubjectOrder.contains(subjectCode2) && OrientEnum.D.getValue().equals(amtOrient2) || ruleCreditSubjectOrder.contains(subjectCode2) && OrientEnum.C.getValue().equals(amtOrient2);
            if (isRuleSubject1 && !isRuleSubject2) {
                return -1;
            }
            if (!isRuleSubject1 && isRuleSubject2) {
                return 1;
            }
            if (isRuleSubject1 && isRuleSubject2) {
                return Integer.compare(ruleSubjectOrderList.indexOf(subjectCode1), ruleSubjectOrderList.indexOf(subjectCode2));
            }
            return MapUtils.compareStr((Map)record1, (Map)record2, (Object)SUBJECTCODE);
        };
        return comparator;
    }

    private static Integer getAmtOrient(Map<String, Object> record) {
        Double offsetDebit = (Double)record.get("DEBITVALUE");
        Double offsetCredit = (Double)record.get("CREDITVALUE");
        if (offsetDebit != null && offsetDebit != 0.0) {
            return OrientEnum.D.getValue();
        }
        if (offsetCredit != null && offsetCredit != 0.0) {
            return OrientEnum.C.getValue();
        }
        Integer subjectOrient = (Integer)record.get("SUBJECTORIENT");
        return subjectOrient == null ? null : Integer.valueOf(-subjectOrient.intValue());
    }

    private String getAmtOrientTitle(Map<String, Object> record) {
        Double offsetDebit = (Double)record.get("DEBITVALUE");
        Double offsetCredit = (Double)record.get("CREDITVALUE");
        if (offsetDebit != null && offsetDebit != 0.0) {
            return OrientEnum.D.getTitle();
        }
        if (offsetCredit != null && offsetCredit != 0.0) {
            return OrientEnum.C.getTitle();
        }
        Integer subjectOrient = (Integer)record.get("SUBJECTORIENT");
        return subjectOrient == null ? null : OrientEnum.valueOf((Integer)(-subjectOrient.intValue())).getTitle();
    }

    private static class UnitAndMergeTypeKey {
        private final String combinedUnit;
        private final String mergeType;

        public UnitAndMergeTypeKey(String combinedUnit, String mergeType) {
            this.combinedUnit = combinedUnit;
            this.mergeType = mergeType;
        }

        public String getCombinedUnit() {
            return this.combinedUnit;
        }

        public String getMergeType() {
            return this.mergeType;
        }

        public String toString() {
            return this.combinedUnit + "|" + this.mergeType;
        }
    }
}

