/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.Pattern
 */
package com.jiuqi.gcreport.consolidatedsystem.vo.option;

import com.jiuqi.gcreport.consolidatedsystem.vo.option.CarryOverRelateTaskScheme;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.CrossTaskCalcVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.DeferredIncomeTax;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ReclassifyOtherInfoVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ReclassifySubjectMappingVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ReduceReclassifySubjectMappingVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.PrimaryWorkpaperTypeVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.Pattern;
import org.springframework.util.CollectionUtils;

public class ConsolidatedOptionVO {
    private String undistributedProfitSubjectCode = null;
    private String intermediateSubjectCode = null;
    private List<String> managementAccountingFieldCodes = new ArrayList<String>();
    @Pattern(regexp="[1,0]", message="\u201c\u662f\u5426\u663e\u793a\u679a\u4e3e\u5b57\u5178\u4ee3\u7801 \u201d\u6570\u636e\u6709\u8bef\u3002")
    private @Pattern(regexp="[1,0]", message="\u201c\u662f\u5426\u663e\u793a\u679a\u4e3e\u5b57\u5178\u4ee3\u7801 \u201d\u6570\u636e\u6709\u8bef\u3002") String showDictCode = "0";
    @Pattern(regexp="[1,0]", message="\u201c\u662f\u5426\u663e\u793a\u672c\u7ea7-\u5168\u90e8\u9875\u7b7e \u201d\u6570\u636e\u6709\u8bef\u3002")
    private @Pattern(regexp="[1,0]", message="\u201c\u662f\u5426\u663e\u793a\u672c\u7ea7-\u5168\u90e8\u9875\u7b7e \u201d\u6570\u636e\u6709\u8bef\u3002") String showSumTab = "1";
    @Pattern(regexp="[1,0]", message="\u201c\u5e76\u8d26\u5355\u4f4d\u95f4\u4e0d\u6267\u884c\u5408\u5e76\u62b5\u9500 \u201d\u6570\u636e\u6709\u8bef\u3002")
    private @Pattern(regexp="[1,0]", message="\u201c\u5e76\u8d26\u5355\u4f4d\u95f4\u4e0d\u6267\u884c\u5408\u5e76\u62b5\u9500 \u201d\u6570\u636e\u6709\u8bef\u3002") String mergeAccountUnitNotOffset = "1";
    private String throughoutOffsetSubject = "";
    private Integer maxAmountOfRealTimeOffset = Integer.MAX_VALUE;
    private Integer maxOffsetNum = 3;
    private Integer overTimeMinute = 60;
    private List<String> managementDimension = new ArrayList<String>();
    private List<String> carryOverSubjectCodes = new ArrayList<String>();
    private String carryOverUndisProfitSubjectCode = "";
    private List<String> carryOverRuleIds = new ArrayList<String>();
    private List<String> carryOverSumRuleIds = new ArrayList<String>();
    private Map<String, Map<String, String>> carryOverSubjectCodeMapping = new HashMap<String, Map<String, String>>();
    private List<CarryOverRelateTaskScheme> carryOverRelateTaskSchemes = new ArrayList<CarryOverRelateTaskScheme>();
    private DeferredIncomeTax diTax = new DeferredIncomeTax();
    private Boolean enableFloatBalance = false;
    private Boolean monthlyIncrement = false;
    private Boolean realTimeConversion = false;
    private Boolean allowConversionBWB = false;
    private String realTimeConversionExecutorName;
    private List<String> finishCalcRewriteRuleIds = new ArrayList<String>();
    private List<String> monthlyIncrementRuleIds = new ArrayList<String>();
    private Boolean carryOverConformRuleAdjustOffsets = false;
    private List<Map<String, String>> fairValueAdjustShowColumns = new ArrayList<Map<String, String>>();
    private List<Map<String, Object>> fairValueAdjustShowSubjects = new ArrayList<Map<String, Object>>();
    private List<Map<String, String>> directInvestShowColumns = new ArrayList<Map<String, String>>();
    private List<Map<String, Object>> directInvestShowSubjects = new ArrayList<Map<String, Object>>();
    private List<Map<String, String>> indirectInvestShowColumns = new ArrayList<Map<String, String>>();
    private List<Map<String, Object>> indirectInvestShowSubjects = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> fewShareholderShowSubjects = new ArrayList<Map<String, Object>>();
    private List<PrimaryWorkpaperTypeVO> primaryWorkpaperTypes = new ArrayList<PrimaryWorkpaperTypeVO>();
    private List<ReclassifySubjectMappingVO> reclassifySubjectMappings = new ArrayList<ReclassifySubjectMappingVO>();
    private List<ReduceReclassifySubjectMappingVO> reduceReclassifySubjectMappings = new ArrayList<ReduceReclassifySubjectMappingVO>();
    private ReclassifyOtherInfoVO reclassifyOtherInfo = new ReclassifyOtherInfoVO();
    private ReclassifyOtherInfoVO reduceReclassifyOtherInfo = new ReclassifyOtherInfoVO();
    private List<String> offsetSpecialUnitType;
    private List<String> carryOverSumColumns = new ArrayList<String>();
    private List<CrossTaskCalcVO> crossTaskCalcSets = new ArrayList<CrossTaskCalcVO>();
    private String sameCtrlExtractPeriodOffset = "0";
    private Boolean enableFinishCalcDataSum = true;
    private String fdtzCalcMode = "monthlyEnd";
    private Integer subjectSummaryLevel = 2;

    public List<String> getManagementAccountingFieldCodes() {
        return this.managementAccountingFieldCodes;
    }

    public void setManagementAccountingFieldCodes(List<String> managementAccountingFieldCodes) {
        this.managementAccountingFieldCodes = managementAccountingFieldCodes;
    }

    public String getUndistributedProfitSubjectCode() {
        return this.undistributedProfitSubjectCode;
    }

    public void setUndistributedProfitSubjectCode(String undistributedProfitSubjectCode) {
        this.undistributedProfitSubjectCode = undistributedProfitSubjectCode;
    }

    public String getIntermediateSubjectCode() {
        return this.intermediateSubjectCode;
    }

    public void setIntermediateSubjectCode(String intermediateSubjectCode) {
        this.intermediateSubjectCode = intermediateSubjectCode;
    }

    public String getMergeAccountUnitNotOffset() {
        return this.mergeAccountUnitNotOffset;
    }

    public void setMergeAccountUnitNotOffset(String mergeAccountUnitNotOffset) {
        this.mergeAccountUnitNotOffset = mergeAccountUnitNotOffset;
    }

    public String getThroughoutOffsetSubject() {
        return this.throughoutOffsetSubject;
    }

    public void setThroughoutOffsetSubject(String throughoutOffsetSubject) {
        this.throughoutOffsetSubject = throughoutOffsetSubject;
    }

    public Integer getMaxAmountOfRealTimeOffset() {
        return this.maxAmountOfRealTimeOffset;
    }

    public void setMaxAmountOfRealTimeOffset(Integer maxAmountOfRealTimeOffset) {
        this.maxAmountOfRealTimeOffset = maxAmountOfRealTimeOffset;
    }

    public Integer getMaxOffsetNum() {
        return this.maxOffsetNum;
    }

    public void setMaxOffsetNum(Integer maxOffsetNum) {
        this.maxOffsetNum = maxOffsetNum;
    }

    public Integer getOverTimeMinute() {
        return this.overTimeMinute;
    }

    public void setOverTimeMinute(Integer overTimeMinute) {
        this.overTimeMinute = overTimeMinute;
    }

    public List<String> getManagementDimension() {
        return this.managementDimension;
    }

    public void setManagementDimension(List<String> managementDimension) {
        this.managementDimension = managementDimension;
    }

    public String getCarryOverUndisProfitSubjectCode() {
        return this.carryOverUndisProfitSubjectCode;
    }

    public void setCarryOverUndisProfitSubjectCode(String carryOverUndisProfitSubjectCode) {
        this.carryOverUndisProfitSubjectCode = carryOverUndisProfitSubjectCode;
    }

    public List<String> getCarryOverRuleIds() {
        return this.carryOverRuleIds;
    }

    public void setCarryOverRuleIds(List<String> carryOverRuleIds) {
        this.carryOverRuleIds = carryOverRuleIds;
    }

    public List<String> getCarryOverSubjectCodes() {
        return this.carryOverSubjectCodes;
    }

    public void setCarryOverSubjectCodes(List<String> carryOverSubjectCodes) {
        this.carryOverSubjectCodes = carryOverSubjectCodes;
    }

    public Map<String, Map<String, String>> getCarryOverSubjectCodeMapping() {
        return this.carryOverSubjectCodeMapping;
    }

    public Map<String, String> getCarryOverSubjectCodeMappingByDestSystemId(String destSystemId) {
        HashMap<String, String> curr2ConvertedCodeMap = new HashMap<String, String>();
        this.carryOverSubjectCodeMapping.forEach((currSubjectCode, desSystemId2SubjectCodeMap) -> {
            if (CollectionUtils.isEmpty(desSystemId2SubjectCodeMap) || !desSystemId2SubjectCodeMap.containsKey(destSystemId)) {
                return;
            }
            curr2ConvertedCodeMap.put((String)currSubjectCode, (String)desSystemId2SubjectCodeMap.get(destSystemId));
        });
        return curr2ConvertedCodeMap;
    }

    public void setCarryOverSubjectCodeMapping(Map<String, Map<String, String>> carryOverSubjectCodeMapping) {
        this.carryOverSubjectCodeMapping = carryOverSubjectCodeMapping;
    }

    public List<CarryOverRelateTaskScheme> getCarryOverRelateTaskSchemes() {
        return this.carryOverRelateTaskSchemes;
    }

    public void setCarryOverRelateTaskSchemes(List<CarryOverRelateTaskScheme> carryOverRelateTaskSchemes) {
        this.carryOverRelateTaskSchemes = carryOverRelateTaskSchemes;
    }

    public String getShowDictCode() {
        return this.showDictCode;
    }

    public void setShowDictCode(String showDictCode) {
        this.showDictCode = showDictCode;
    }

    public String getShowSumTab() {
        return this.showSumTab;
    }

    public void setShowSumTab(String showSumTab) {
        this.showSumTab = showSumTab;
    }

    public DeferredIncomeTax getDiTax() {
        return this.diTax;
    }

    public void setDiTax(DeferredIncomeTax diTax) {
        this.diTax = diTax;
    }

    public Boolean getEnableFloatBalance() {
        return this.enableFloatBalance;
    }

    public void setEnableFloatBalance(Boolean enableFloatBalance) {
        this.enableFloatBalance = enableFloatBalance;
    }

    public List<String> getFinishCalcRewriteRuleIds() {
        return this.finishCalcRewriteRuleIds;
    }

    public void setFinishCalcRewriteRuleIds(List<String> finishCalcRewriteRuleIds) {
        this.finishCalcRewriteRuleIds = finishCalcRewriteRuleIds;
    }

    public Boolean getMonthlyIncrement() {
        return this.monthlyIncrement;
    }

    public void setMonthlyIncrement(Boolean monthlyIncrement) {
        this.monthlyIncrement = monthlyIncrement;
    }

    public Boolean getRealTimeConversion() {
        return this.realTimeConversion;
    }

    public void setRealTimeConversion(Boolean realTimeConversion) {
        this.realTimeConversion = realTimeConversion;
    }

    public Boolean getAllowConversionBWB() {
        return this.allowConversionBWB;
    }

    public void setAllowConversionBWB(Boolean allowConversionBWB) {
        this.allowConversionBWB = allowConversionBWB;
    }

    public Boolean getCarryOverConformRuleAdjustOffsets() {
        return this.carryOverConformRuleAdjustOffsets;
    }

    public void setCarryOverConformRuleAdjustOffsets(Boolean carryOverConformRuleAdjustOffsets) {
        this.carryOverConformRuleAdjustOffsets = carryOverConformRuleAdjustOffsets;
    }

    public List<String> getOffsetSpecialUnitType() {
        return this.offsetSpecialUnitType;
    }

    public void setOffsetSpecialUnitType(List<String> offsetSpecialUnitType) {
        this.offsetSpecialUnitType = offsetSpecialUnitType;
    }

    public List<String> getMonthlyIncrementRuleIds() {
        return this.monthlyIncrementRuleIds;
    }

    public void setMonthlyIncrementRuleIds(List<String> monthlyIncrementRuleIds) {
        this.monthlyIncrementRuleIds = monthlyIncrementRuleIds;
    }

    public List<Map<String, String>> getFairValueAdjustShowColumns() {
        return this.fairValueAdjustShowColumns;
    }

    public void setFairValueAdjustShowColumns(List<Map<String, String>> fairValueAdjustShowColumns) {
        this.fairValueAdjustShowColumns = fairValueAdjustShowColumns;
    }

    public List<Map<String, String>> getDirectInvestShowColumns() {
        return this.directInvestShowColumns;
    }

    public void setDirectInvestShowColumns(List<Map<String, String>> directInvestShowColumns) {
        this.directInvestShowColumns = directInvestShowColumns;
    }

    public List<Map<String, String>> getIndirectInvestShowColumns() {
        return this.indirectInvestShowColumns;
    }

    public void setIndirectInvestShowColumns(List<Map<String, String>> indirectInvestShowColumns) {
        this.indirectInvestShowColumns = indirectInvestShowColumns;
    }

    public List<Map<String, Object>> getFairValueAdjustShowSubjects() {
        return this.fairValueAdjustShowSubjects;
    }

    public void setFairValueAdjustShowSubjects(List<Map<String, Object>> fairValueAdjustShowSubjects) {
        this.fairValueAdjustShowSubjects = fairValueAdjustShowSubjects;
    }

    public List<Map<String, Object>> getDirectInvestShowSubjects() {
        return this.directInvestShowSubjects;
    }

    public void setDirectInvestShowSubjects(List<Map<String, Object>> directInvestShowSubjects) {
        this.directInvestShowSubjects = directInvestShowSubjects;
    }

    public List<Map<String, Object>> getIndirectInvestShowSubjects() {
        return this.indirectInvestShowSubjects;
    }

    public void setIndirectInvestShowSubjects(List<Map<String, Object>> indirectInvestShowSubjects) {
        this.indirectInvestShowSubjects = indirectInvestShowSubjects;
    }

    public List<Map<String, Object>> getFewShareholderShowSubjects() {
        return this.fewShareholderShowSubjects;
    }

    public void setFewShareholderShowSubjects(List<Map<String, Object>> fewShareholderShowSubjects) {
        this.fewShareholderShowSubjects = fewShareholderShowSubjects;
    }

    public List<PrimaryWorkpaperTypeVO> getPrimaryWorkpaperTypes() {
        return this.primaryWorkpaperTypes;
    }

    public void setPrimaryWorkpaperTypes(List<PrimaryWorkpaperTypeVO> primaryWorkpaperTypes) {
        this.primaryWorkpaperTypes = primaryWorkpaperTypes;
    }

    public List<ReclassifySubjectMappingVO> getReclassifySubjectMappings() {
        return this.reclassifySubjectMappings;
    }

    public void setReclassifySubjectMappings(List<ReclassifySubjectMappingVO> reclassifySubjectMappings) {
        this.reclassifySubjectMappings = reclassifySubjectMappings;
    }

    public List<ReduceReclassifySubjectMappingVO> getReduceReclassifySubjectMappings() {
        return this.reduceReclassifySubjectMappings;
    }

    public void setReduceReclassifySubjectMappings(List<ReduceReclassifySubjectMappingVO> reduceReclassifySubjectMappings) {
        this.reduceReclassifySubjectMappings = reduceReclassifySubjectMappings;
    }

    public ReclassifyOtherInfoVO getReclassifyOtherInfo() {
        if (null == this.reclassifyOtherInfo) {
            this.reclassifyOtherInfo = new ReclassifyOtherInfoVO();
        }
        return this.reclassifyOtherInfo;
    }

    public void setReclassifyOtherInfo(ReclassifyOtherInfoVO reclassifyOtherInfo) {
        this.reclassifyOtherInfo = reclassifyOtherInfo;
    }

    public ReclassifyOtherInfoVO getReduceReclassifyOtherInfo() {
        if (null == this.reduceReclassifyOtherInfo) {
            this.reduceReclassifyOtherInfo = new ReclassifyOtherInfoVO();
        }
        return this.reduceReclassifyOtherInfo;
    }

    public void setReduceReclassifyOtherInfo(ReclassifyOtherInfoVO reduceReclassifyOtherInfo) {
        this.reduceReclassifyOtherInfo = reduceReclassifyOtherInfo;
    }

    public List<CrossTaskCalcVO> getCrossTaskCalcSets() {
        return this.crossTaskCalcSets;
    }

    public void setCrossTaskCalcSets(List<CrossTaskCalcVO> crossTaskCalcSets) {
        this.crossTaskCalcSets = crossTaskCalcSets;
    }

    public String getRealTimeConversionExecutorName() {
        return this.realTimeConversionExecutorName;
    }

    public void setRealTimeConversionExecutorName(String realTimeConversionExecutorName) {
        this.realTimeConversionExecutorName = realTimeConversionExecutorName;
    }

    public List<String> getCarryOverSumColumns() {
        return this.carryOverSumColumns;
    }

    public void setCarryOverSumColumns(List<String> carryOverSumColumns) {
        this.carryOverSumColumns = carryOverSumColumns;
    }

    public List<String> getCarryOverSumRuleIds() {
        return this.carryOverSumRuleIds;
    }

    public void setCarryOverSumRuleIds(List<String> carryOverSumRuleIds) {
        this.carryOverSumRuleIds = carryOverSumRuleIds;
    }

    public String getSameCtrlExtractPeriodOffset() {
        return this.sameCtrlExtractPeriodOffset;
    }

    public void setSameCtrlExtractPeriodOffset(String sameCtrlExtractPeriodOffset) {
        this.sameCtrlExtractPeriodOffset = sameCtrlExtractPeriodOffset;
    }

    public String getFdtzCalcMode() {
        return this.fdtzCalcMode;
    }

    public void setFdtzCalcMode(String fdtzCalcMode) {
        this.fdtzCalcMode = fdtzCalcMode;
    }

    public Boolean getEnableFinishCalcDataSum() {
        return this.enableFinishCalcDataSum;
    }

    public void setEnableFinishCalcDataSum(Boolean enableFinishCalcDataSum) {
        this.enableFinishCalcDataSum = enableFinishCalcDataSum;
    }

    public Integer getSubjectSummaryLevel() {
        return this.subjectSummaryLevel;
    }

    public void setSubjectSummaryLevel(Integer subjectSummaryLevel) {
        this.subjectSummaryLevel = subjectSummaryLevel;
    }
}

