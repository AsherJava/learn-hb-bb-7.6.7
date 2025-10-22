/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl
 *  com.jiuqi.gcreport.calculate.util.GcCalcAmtCheckUtil
 *  com.jiuqi.gcreport.calculate.util.ListMatch
 *  com.jiuqi.gcreport.calculate.util.ListMatch$CallBack
 *  com.jiuqi.gcreport.calculate.util.ListMatch$CompareResult
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.inputdata.dto.InputRuleFilterCondition
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleFetchConfig
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleFetchConfig$Item
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO
 *  com.jiuqi.gcreport.unionrule.enums.FetchTypeEnum
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.enums.ToleranceTypeEnum
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.inputdata.flexible.processor.executor.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl;
import com.jiuqi.gcreport.calculate.util.GcCalcAmtCheckUtil;
import com.jiuqi.gcreport.calculate.util.ListMatch;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.dto.InputRuleFilterCondition;
import com.jiuqi.gcreport.inputdata.flexible.cache.FlexRuleCacheService;
import com.jiuqi.gcreport.inputdata.flexible.processor.executor.RuleChecker;
import com.jiuqi.gcreport.inputdata.flexible.processor.executor.impl.AbstractInputDataRuleExecutorImpl;
import com.jiuqi.gcreport.inputdata.flexible.processor.executor.impl.OffsetItemCreateHelper;
import com.jiuqi.gcreport.inputdata.flexible.processor.executor.impl.SortOffsetHelper;
import com.jiuqi.gcreport.inputdata.flexible.utils.CorporateConvertUtils;
import com.jiuqi.gcreport.inputdata.flexible.utils.RuleMappingImplUtils;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.enums.InputDataCheckStateEnum;
import com.jiuqi.gcreport.inputdata.inputdata.enums.InputDataCheckTypeEnum;
import com.jiuqi.gcreport.inputdata.inputdata.enums.ReportOffsetStateEnum;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO;
import com.jiuqi.gcreport.unionrule.dto.FlexibleFetchConfig;
import com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.FetchTypeEnum;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.enums.ToleranceTypeEnum;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class FlexibleRuleExecutorImpl
extends AbstractInputDataRuleExecutorImpl {
    private FlexibleRuleDTO flexRule;
    private Boolean hasAssociatedSubjectSetting;
    private final FlexRuleCacheService flexRuleCacheService = (FlexRuleCacheService)SpringContextUtils.getBean(FlexRuleCacheService.class);
    private final DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
    private final ConsolidatedSubjectService consolidatedSubjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class);
    private final InputDataNameProvider inputDataNameProvider = (InputDataNameProvider)SpringContextUtils.getBean(InputDataNameProvider.class);
    private Logger logger = LoggerFactory.getLogger(FlexibleRuleExecutorImpl.class);
    private Map<String, List<GcOffSetVchrItemDTO>> alreadyItemsGroupByOffSetGroupId = new HashMap<String, List<GcOffSetVchrItemDTO>>();
    private Map<String, Set<String>> allChildrenCodesGroupByParentCode = new HashMap<String, Set<String>>();
    private List<String> groupFieldList;
    private boolean groupContainUnitAndOppUnit;
    private boolean groupContainDc;
    private List<String> numberFields;
    private final double ERRORRANGE = 0.001;
    private List<ColumnModelDefine> offsetGroupingFields;
    private int matchedNum;
    private String curCondGroupKey;
    boolean originalExistToleranceFlag = false;
    private Map<String, List<InputDataEO>> inputDataCheckedGroupByCheckId = new HashMap<String, List<InputDataEO>>();

    @Override
    protected boolean canRealTimeOffset(AbstractUnionRule rule) {
        if (!(rule instanceof FlexibleRuleDTO)) {
            return false;
        }
        assert (this.flexRuleCacheService != null);
        this.flexRule = this.flexRuleCacheService.getFlexRule((FlexibleRuleDTO)rule);
        if (this.isManualBatchOffset) {
            FlexibleRuleDTO flexibleRule = (FlexibleRuleDTO)this.manualBatchOffsetRule;
            this.flexRule.setProportionOffsetDiffFlag(Boolean.valueOf(false));
            this.flexRule.setToleranceType(ToleranceTypeEnum.BY_MONEY);
            this.flexRule.setOffsetType(flexibleRule.getOffsetType());
            this.flexRule.setToleranceRange(flexibleRule.getToleranceRange());
            this.flexRule.setUnilateralOffsetFlag(flexibleRule.getUnilateralOffsetFlag());
            this.flexRule.setOffsetGroupingField(flexibleRule.getOffsetGroupingField());
            this.flexRule.setDiffSubjectCode(flexibleRule.getDiffSubjectCode());
            this.flexRule.setEnableToleranceFlag(Boolean.valueOf(true));
        }
        assert (this.flexRule != null);
        if (Boolean.FALSE.equals(this.flexRule.getCheckOffsetFlag()) && this.realTimeOffsetOptionFlag && Boolean.FALSE.equals(this.flexRule.getRealTimeOffsetFlag())) {
            return false;
        }
        List fetchConfigList = this.flexRule.getFetchConfigList();
        if (CollectionUtils.isEmpty(fetchConfigList)) {
            return false;
        }
        this.initGroupInfo();
        return true;
    }

    @Override
    protected boolean mustRelUnit() {
        return this.groupContainUnitAndOppUnit;
    }

    private void initGroupInfo() {
        this.groupFieldList = this.flexRule.getOffsetGroupingField();
        if (CollectionUtils.isEmpty(this.groupFieldList)) {
            this.groupFieldList = new ArrayList<String>();
        }
        if (!this.groupFieldList.contains("MDCODE")) {
            this.groupFieldList.add("MDCODE");
        }
        if (!this.groupFieldList.contains("OPPUNITID")) {
            this.groupFieldList.add("OPPUNITID");
        }
        this.groupFieldList = this.groupFieldList.stream().map(String::toUpperCase).collect(Collectors.toList());
        this.groupContainUnitAndOppUnit = true;
        this.groupContainDc = this.groupFieldList.contains("DC");
    }

    @Override
    protected void doOffset(List<InputDataEO> inputItems) {
        super.doOffset(inputItems);
        if (CollectionUtils.isEmpty(inputItems)) {
            return;
        }
        boolean isRealTimeOffsetFlag = this.isRealTimeOffset();
        if (!isRealTimeOffsetFlag) {
            return;
        }
        List<String> ids = inputItems.stream().map(InputDataEO::getId).collect(Collectors.toList());
        List<InputDataEO> unOffsetItems = this.inputService.queryByIds(ids, inputItems.get(0).getTaskId());
        if (CollectionUtils.isEmpty(unOffsetItems)) {
            return;
        }
        this.listConsolidatedSubjectBySystemId();
        List<InputDataEO> unOffsetInputDatas = unOffsetItems.stream().filter(item -> ReportOffsetStateEnum.NOTOFFSET.getValue().equals(item.getOffsetState())).collect(Collectors.toList());
        if (this.flexRule.getCorporateOffsetFlag().booleanValue()) {
            unOffsetInputDatas.forEach(CorporateConvertUtils::convertToOffsetUnit);
        }
        Map<String, List<InputDataEO>> group = this.groupByOffsetCondition(unOffsetInputDatas);
        if (this.flexRule.getEnableToleranceFlag().booleanValue()) {
            this.originalExistToleranceFlag = true;
        }
        group.forEach((condGroupKey, sameGroupList) -> this.doOffsetGroupByOffsetCondition((String)condGroupKey, (List<InputDataEO>)sameGroupList));
    }

    @Override
    protected void doCheckOffset(List<InputDataEO> inputItems) {
        super.doCheckOffset(inputItems);
        List<InputDataEO> unCheckDatas = inputItems.stream().filter(item -> InputDataCheckStateEnum.NOTCHECK.getValue().equals(item.getCheckState())).collect(Collectors.toList());
        if (this.flexRule.getCorporateOffsetFlag().booleanValue()) {
            unCheckDatas.forEach(CorporateConvertUtils::convertToOffsetUnit);
        }
        Map<String, List<InputDataEO>> group = this.groupByOffsetCondition(unCheckDatas);
        HashMap<String, List<InputDataEO>> unCheckedResult = new HashMap<String, List<InputDataEO>>();
        group.forEach((condGroupKey, sameGroupList) -> {
            this.doReconciliationOffset((List<InputDataEO>)sameGroupList);
            if (!CollectionUtils.isEmpty(sameGroupList)) {
                unCheckedResult.put((String)condGroupKey, (List<InputDataEO>)sameGroupList);
            }
        });
        this.saveCheckItems();
        Map<String, List<InputDataEO>> checkedAndUnOffsetInputData = this.getCheckedInputData(inputItems);
        this.doTryOffset(checkedAndUnOffsetInputData, unCheckedResult);
    }

    private void saveCheckItems() {
        if (this.inputDataCheckedGroupByCheckId.isEmpty()) {
            return;
        }
        ArrayList<InputDataEO> items = new ArrayList<InputDataEO>();
        for (List<InputDataEO> inputDataItems : this.inputDataCheckedGroupByCheckId.values()) {
            items.addAll(inputDataItems);
        }
        this.inputDataCheckService.saveInputDataCheckInfo(((InputDataEO)((Object)items.get(0))).getTaskId(), ((InputDataEO)((Object)items.get(0))).getPeriod(), items);
    }

    private Map<String, List<InputDataEO>> getCheckedInputData(List<InputDataEO> inputItems) {
        HashMap<String, List<InputDataEO>> unOffsetDataGroupByCheckGroupId = new HashMap<String, List<InputDataEO>>();
        if (CollectionUtils.isEmpty(inputItems)) {
            return unOffsetDataGroupByCheckGroupId;
        }
        for (InputDataEO inputData : inputItems) {
            if (!InputDataCheckStateEnum.CHECK.getValue().equals(inputData.getCheckState()) || !ReportOffsetStateEnum.NOTOFFSET.getValue().equals(inputData.getOffsetState())) continue;
            String checkGroupId = inputData.getCheckGroupId();
            List inputDataItems = unOffsetDataGroupByCheckGroupId.computeIfAbsent(checkGroupId, k -> new ArrayList());
            inputDataItems.add(inputData);
        }
        return unOffsetDataGroupByCheckGroupId;
    }

    private void doTryOffset(Map<String, List<InputDataEO>> checkedInputData, Map<String, List<InputDataEO>> unCheckedItems) {
        this.isCheckOffset = true;
        for (List<InputDataEO> inputDataItems : checkedInputData.values()) {
            this.doOffset(inputDataItems);
        }
        this.isCheckOffset = false;
        if (unCheckedItems.isEmpty()) {
            return;
        }
        boolean unCheckOffsetFlag = this.flexRule.getUnCheckOffsetFlag();
        if (unCheckOffsetFlag && !this.realTimeOffsetOptionFlag) {
            for (List<InputDataEO> inputDataItems : unCheckedItems.values()) {
                this.doOffset(inputDataItems);
            }
        }
    }

    private void doOffsetGroupByOffsetCondition(String condGroupKey, List<InputDataEO> sameGroupList) {
        if (this.realTimeOffsetMonitor != null) {
            this.realTimeOffsetMonitor.beginCondGroupOffset(this.curRelGroupKey, condGroupKey);
            int debitNum = (int)sameGroupList.stream().filter(inputItem -> inputItem.getDc() == OrientEnum.D.getValue()).count();
            int creditNum = sameGroupList.size() - debitNum;
            this.realTimeOffsetMonitor.setCondGroupOffsetNum(condGroupKey, debitNum, creditNum);
            this.matchedNum = 0;
            this.curCondGroupKey = condGroupKey;
        }
        HashMap<String, List<InputDataEO>> recordOffsetGroup = new HashMap<String, List<InputDataEO>>(16);
        recordOffsetGroup.put(UUIDUtils.emptyUUIDStr(), sameGroupList);
        if (sameGroupList.size() > 1 && this.originalExistToleranceFlag) {
            this.flexRule.setEnableToleranceFlag(Boolean.valueOf(false));
            this.doSameGroupOffset(recordOffsetGroup);
            if (recordOffsetGroup.containsKey(UUIDUtils.emptyUUIDStr())) {
                HashMap<String, List<InputDataEO>> recordOffsetGroupTolerance = new HashMap<String, List<InputDataEO>>(16);
                recordOffsetGroupTolerance.put(UUIDUtils.emptyUUIDStr(), (List<InputDataEO>)recordOffsetGroup.get(UUIDUtils.emptyUUIDStr()));
                this.flexRule.setEnableToleranceFlag(Boolean.valueOf(true));
                this.doSameGroupOffset(recordOffsetGroupTolerance);
            }
        } else {
            this.doSameGroupOffset(recordOffsetGroup);
        }
        if (this.realTimeOffsetMonitor != null) {
            this.realTimeOffsetMonitor.finishCondGroupOffset(condGroupKey, this.matchedNum);
        }
    }

    public List<OffsetResult> reltxnCanOffsetItems(FinancialCheckRuleDTO rule, List<InputDataEO> uncheckedList, GcCalcEnvContext env, String conSystemId) {
        if (CollectionUtils.isEmpty(uncheckedList)) {
            return null;
        }
        Map<String, InputRuleFilterCondition> conditions = this.getFilter(uncheckedList);
        if (CollectionUtils.isEmpty(conditions)) {
            return null;
        }
        this.processEnv = env;
        this.reportSystemId = conSystemId;
        FlexibleRuleDTO flexibleRule = FlexibleRuleExecutorImpl.convertFlexibleRule((AbstractUnionRule)rule);
        assert (this.flexRuleCacheService != null);
        this.flexRule = this.flexRuleCacheService.getFlexRule(flexibleRule);
        this.flexRule.setUnilateralOffsetFlag(Boolean.valueOf(true));
        List flexibleFetchConfigs = this.flexRule.getFetchConfigList();
        if (CollectionUtils.isEmpty(flexibleFetchConfigs)) {
            return null;
        }
        assert (this.consolidatedSubjectService != null);
        this.initGroupInfo();
        if (!this.mustRelUnit()) {
            return null;
        }
        Map<String, List<InputDataEO>> group = this.groupByOffsetCondition(uncheckedList);
        ArrayList<OffsetResult> OffsetResults = new ArrayList<OffsetResult>();
        group.forEach((condGroupKey, sameGroupList) -> {
            for (FlexibleFetchConfig flexibleFetchConfig : flexibleFetchConfigs) {
                String offsetGroupId;
                OffsetResult offsetResult = this.tryOffsetByFetchConfig((List<InputDataEO>)sameGroupList, flexibleFetchConfig, offsetGroupId = UUIDUtils.newUUIDStr());
                if (!offsetResult.matched) continue;
                offsetResult.getOffsetVchr().getItems().forEach(offSetVchrItem -> offSetVchrItem.setOffSetSrcType(rule.isChecked() ? OffSetSrcTypeEnum.FINANCIAL_CHECK : OffSetSrcTypeEnum.FINANCIAL_CHECK_NOCHECK));
                OffsetResults.add(offsetResult);
            }
        });
        return OffsetResults;
    }

    @Override
    protected String getRecordKey(InputDataEO inputItem) {
        String unitId = inputItem.getConvertOffsetOrgFlag() != false ? inputItem.getOffsetOrgCode() : inputItem.getUnitId();
        String oppUnitId = inputItem.getConvertOffsetOrgFlag() != false ? inputItem.getOffsetOppUnitId() : inputItem.getOppUnitId();
        StringBuilder keyBuf = new StringBuilder();
        if (this.groupContainUnitAndOppUnit && !this.groupContainDc) {
            keyBuf.append(this.getUnitComb(unitId, oppUnitId));
        } else if (this.groupContainUnitAndOppUnit) {
            String noSortUnitComb;
            String sortUnitComb = this.getUnitComb(unitId, oppUnitId);
            if (sortUnitComb.compareTo(noSortUnitComb = unitId + "," + oppUnitId) == 0) {
                keyBuf.append(sortUnitComb).append(inputItem.getDc());
            } else {
                keyBuf.append(sortUnitComb).append(inputItem.getDc() * -1);
            }
        }
        keyBuf.append(inputItem.getReportSystemId()).append(inputItem.getCurrency()).append(inputItem.getPeriod());
        this.offsetGroupingFields = this.getInputDataAllField(inputItem.getTaskId());
        this.numberFields = new ArrayList<String>();
        ArrayList offsetGroupingNumberFields = new ArrayList();
        this.groupFieldList.forEach(groupField -> {
            boolean isProcessedField;
            String fieldCode = groupField.toUpperCase();
            boolean isNumberField = this.offsetGroupingFields.stream().anyMatch(column -> column.getCode().equalsIgnoreCase(fieldCode) && ColumnModelType.BIGDECIMAL.equals((Object)column.getColumnType()) || ColumnModelType.DOUBLE.equals((Object)column.getColumnType()));
            if (isNumberField) {
                offsetGroupingNumberFields.add(fieldCode);
                return;
            }
            boolean bl = isProcessedField = "DC".equals(fieldCode) || this.groupContainUnitAndOppUnit && ("MDCODE".equalsIgnoreCase(fieldCode) || "OPPUNITID".equals(fieldCode));
            if (!isProcessedField) {
                keyBuf.append(inputItem.getFieldValue(fieldCode));
            }
        });
        if (this.flexRule.getCheckOffsetFlag().booleanValue()) {
            if (CollectionUtils.isEmpty(offsetGroupingNumberFields)) {
                this.numberFields.add("AMT");
            } else {
                this.numberFields.addAll(offsetGroupingNumberFields);
            }
        }
        return keyBuf.toString();
    }

    private void doSameGroupOffset(Map<String, List<InputDataEO>> recordOffsetGroup) {
        ArrayList unOffsetInputData = new ArrayList();
        List fetchConfigList = this.flexRule.getFetchConfigList();
        for (int index = 0; index < fetchConfigList.size(); ++index) {
            FlexibleFetchConfig fetchConfig = (FlexibleFetchConfig)fetchConfigList.get(index);
            recordOffsetGroup.forEach((offsetGroupId, value) -> {
                if (UUIDUtils.emptyUUIDStr().equals(offsetGroupId)) {
                    return;
                }
                this.doOffsetByFetchConfig((List<InputDataEO>)value, fetchConfig, (String)offsetGroupId);
            });
            if (recordOffsetGroup.containsKey(UUIDUtils.emptyUUIDStr())) {
                boolean hasNewMatched;
                Map<String, List<InputDataEO>> newRecordOffsetGroup = this.calMerge(recordOffsetGroup.get(UUIDUtils.emptyUUIDStr()), fetchConfig);
                boolean bl = hasNewMatched = !CollectionUtils.isEmpty(newRecordOffsetGroup) && (newRecordOffsetGroup.size() != 1 || !newRecordOffsetGroup.containsKey(UUIDUtils.emptyUUIDStr()));
                if (hasNewMatched) {
                    unOffsetInputData.addAll(recordOffsetGroup.get(UUIDUtils.emptyUUIDStr()));
                    recordOffsetGroup.remove(UUIDUtils.emptyUUIDStr());
                    recordOffsetGroup.putAll(newRecordOffsetGroup);
                }
                String offsetGroupId2 = UUIDUtils.newUUIDStr();
                if (!this.isManualBatchOffset && !this.isCheckOffset && recordOffsetGroup.containsKey(UUIDUtils.emptyUUIDStr())) {
                    this.flexRule.setEnableToleranceFlag(Boolean.valueOf(this.originalExistToleranceFlag));
                    if (this.doOffsetByFetchConfig(recordOffsetGroup.get(UUIDUtils.emptyUUIDStr()), fetchConfig, offsetGroupId2)) {
                        recordOffsetGroup.put(offsetGroupId2, recordOffsetGroup.get(UUIDUtils.emptyUUIDStr()));
                        unOffsetInputData.addAll(recordOffsetGroup.get(UUIDUtils.emptyUUIDStr()));
                        recordOffsetGroup.remove(UUIDUtils.emptyUUIDStr());
                    }
                }
            }
            if (index != 0 || !Boolean.TRUE.equals(this.flexRule.getReconciliationOffsetFlag()) || !recordOffsetGroup.containsKey(UUIDUtils.emptyUUIDStr())) continue;
            if (recordOffsetGroup.size() == 1) {
                return;
            }
            unOffsetInputData.addAll(recordOffsetGroup.get(UUIDUtils.emptyUUIDStr()));
            recordOffsetGroup.remove(UUIDUtils.emptyUUIDStr());
        }
        HashSet<String> inputDataIds = new HashSet<String>();
        HashSet inputDataOffsetIds = new HashSet();
        ArrayList<InputDataEO> inputDataItems = new ArrayList<InputDataEO>();
        for (List<InputDataEO> offsetInputData : recordOffsetGroup.values()) {
            if (CollectionUtils.isEmpty(offsetInputData)) continue;
            inputDataOffsetIds.addAll(offsetInputData.stream().map(InputDataEO::getId).collect(Collectors.toSet()));
        }
        for (InputDataEO inputData : unOffsetInputData) {
            if (inputDataIds.contains(inputData.getId()) || inputDataOffsetIds.contains(inputData.getId())) continue;
            inputDataIds.add(inputData.getId());
            inputDataItems.add(inputData);
        }
        if (!CollectionUtils.isEmpty(inputDataItems)) {
            recordOffsetGroup.put(UUIDUtils.emptyUUIDStr(), inputDataItems);
        }
    }

    private boolean doOffsetByFetchConfig(List<InputDataEO> inputItems, FlexibleFetchConfig fetchConfig, String offsetGroupId) {
        OffsetResult offsetResult = this.tryOffsetByFetchConfig(inputItems, fetchConfig, offsetGroupId);
        if (!offsetResult.matched) {
            return false;
        }
        if (this.isManualBatchOffset) {
            offsetResult.offsetVchr.setConsFormulaCalcType("manualFlag");
        } else {
            offsetResult.offsetVchr.setConsFormulaCalcType("autoFlag");
        }
        if (this.realTimeOffsetOptionFlag) {
            FlexibleRuleExecutorImpl flexibleRuleExecutorImpl = this;
            flexibleRuleExecutorImpl.offsetedNum = flexibleRuleExecutorImpl.offsetedNum + this.inputService.doOffset(offsetResult.offsetedInputItems, offsetResult.offsetVchr);
        } else {
            FlexibleRuleExecutorImpl flexibleRuleExecutorImpl = this;
            flexibleRuleExecutorImpl.offsetedNum = flexibleRuleExecutorImpl.offsetedNum + this.inputService.doOffsetNewTran(offsetResult.offsetedInputItems, offsetResult.offsetVchr);
        }
        this.pullOffSetVchrItems(offsetGroupId, offsetResult.offsetVchr.getItems());
        return true;
    }

    private OffsetResult tryOffsetByFetchConfig(List<InputDataEO> inputItems, FlexibleFetchConfig fetchConfig, String offsetGroupId) {
        boolean canOffset;
        OffsetResult offsetResult = new OffsetResult();
        if (StringUtils.hasText(this.checkByOptions(inputItems))) {
            return offsetResult;
        }
        OffsetItemCreateHelper offsetItemCreateHelper = this.getOffsetItemCreateHelper();
        OffsetItemCreateHelper.CreateResult createResult = offsetItemCreateHelper.createOffsetItemByFetchConfig(inputItems, fetchConfig, offsetGroupId, this.alreadyItemsGroupByOffSetGroupId.get(offsetGroupId));
        boolean bl = canOffset = !CollectionUtils.isEmpty(createResult.getOffsetItems()) && (createResult.isNoDiff() || GcCalcAmtCheckUtil.checkAndDistributionAmt((AbstractUnionRule)this.flexRule, createResult.getOffsetItems()));
        if (canOffset) {
            offsetResult.offsetedInputItems = this.setOffsetInfo(inputItems, createResult.getOffsetItems(), fetchConfig, offsetGroupId, createResult.isNoDiff());
            GcOffSetVchrDTO offsetVchr = new GcOffSetVchrDTO();
            ArrayList<GcOffSetVchrItemDTO> items = new ArrayList<GcOffSetVchrItemDTO>(createResult.getOffsetItems());
            offsetVchr.setItems(items);
            offsetResult.offsetVchr = offsetVchr;
            offsetResult.matched = true;
            return offsetResult;
        }
        return offsetResult;
    }

    private OffsetItemCreateHelper getOffsetItemCreateHelper() {
        OffsetItemCreateHelper offsetItemCreateHelper = this.isManualBatchOffset ? OffsetItemCreateHelper.newFlexBatchManualInstance(this.flexRule, this.processEnv.getCalcArgments(), this.realTimeOffsetOptionFlag) : OffsetItemCreateHelper.newFlexInstance(this.flexRule, this.processEnv.getCalcArgments(), this.realTimeOffsetOptionFlag);
        return offsetItemCreateHelper;
    }

    private Map<String, List<InputDataEO>> calMerge(List<InputDataEO> recordMap, FlexibleFetchConfig fetchConfig) {
        SortOffsetCheckResult sortOffsetCheckResult = this.checkCanSortOffsetByFetchConfig(fetchConfig);
        if (this.realTimeOffsetMonitor != null) {
            this.realTimeOffsetMonitor.setCondGroupSortOrder(this.curCondGroupKey, sortOffsetCheckResult.canSortOffset);
        }
        if (sortOffsetCheckResult.canSortOffset && !this.isManualBatchOffset && Boolean.TRUE.equals(this.flexRule.getOneToOneOffsetFlag())) {
            return this.sortOffset(recordMap, fetchConfig, sortOffsetCheckResult.balanceOffset);
        }
        return this.noSortOffset(recordMap, fetchConfig);
    }

    private SortOffsetCheckResult checkCanSortOffsetByFetchConfig(FlexibleFetchConfig fetchConfig) {
        boolean hasConfigFilter = StringUtils.hasText(fetchConfig.getFilterFormula());
        SortOffsetCheckResult debitSortOffsetCheckResult = this.checkCanSortOffsetByFetchItems(fetchConfig.getDebitConfigList(), hasConfigFilter);
        if (!debitSortOffsetCheckResult.canSortOffset) {
            return debitSortOffsetCheckResult;
        }
        SortOffsetCheckResult creditSortOffsetCheckResult = this.checkCanSortOffsetByFetchItems(fetchConfig.getCreditConfigList(), hasConfigFilter);
        if (!creditSortOffsetCheckResult.canSortOffset) {
            return creditSortOffsetCheckResult;
        }
        SortOffsetCheckResult sortOffsetCheckResult = new SortOffsetCheckResult();
        sortOffsetCheckResult.canSortOffset = !this.isCheckOffset;
        sortOffsetCheckResult.balanceOffset = debitSortOffsetCheckResult.balanceOffset || creditSortOffsetCheckResult.balanceOffset;
        return sortOffsetCheckResult;
    }

    private SortOffsetCheckResult checkCanSortOffsetByFetchItems(List<FlexibleFetchConfig.Item> fetchItems, boolean hasConfigFilter) {
        SortOffsetCheckResult sortOffsetCheckResult = new SortOffsetCheckResult();
        if (CollectionUtils.isEmpty(fetchItems)) {
            return sortOffsetCheckResult;
        }
        sortOffsetCheckResult.canSortOffset = fetchItems.stream().allMatch(fetchItem -> {
            SortOffsetCheckResult itemSortOffsetCheckResult = this.checkCanSortOffsetByFetchItem((FlexibleFetchConfig.Item)fetchItem, hasConfigFilter);
            if (!itemSortOffsetCheckResult.canSortOffset) {
                return false;
            }
            if (itemSortOffsetCheckResult.balanceOffset) {
                sortOffsetCheckResult.balanceOffset = true;
            }
            return true;
        });
        return sortOffsetCheckResult;
    }

    private SortOffsetCheckResult checkCanSortOffsetByFetchItem(FlexibleFetchConfig.Item fetchItem, boolean hasConfigFilter) {
        SortOffsetCheckResult sortOffsetCheckResult = new SortOffsetCheckResult();
        if (hasConfigFilter && (StringUtils.hasText(fetchItem.getFetchFormula()) || FetchTypeEnum.isSum((FetchTypeEnum)fetchItem.getFetchType()))) {
            return sortOffsetCheckResult;
        }
        String formula = fetchItem.getFetchFormula();
        if (StringUtils.hasText(formula) && ((formula = formula.toUpperCase()).contains("SUM") || formula.contains("AVG"))) {
            return sortOffsetCheckResult;
        }
        if (FetchTypeEnum.isSum((FetchTypeEnum)fetchItem.getFetchType())) {
            boolean balance = OffsetItemCreateHelper.isBalanceSetting(fetchItem);
            if (!balance) {
                return sortOffsetCheckResult;
            }
            sortOffsetCheckResult.balanceOffset = true;
        }
        sortOffsetCheckResult.canSortOffset = true;
        return sortOffsetCheckResult;
    }

    @Override
    protected String checkByOptions(Collection<InputDataEO> list) {
        if (CollectionUtils.isEmpty(this.flexRule.getFetchConfigList())) {
            Object[] args = new String[]{this.flexRule.getLocalizedName()};
            return GcI18nUtil.getMessage((String)"gc.calculate.flexiblerule.rulenotfetchsettingsmsg", (Object[])args);
        }
        return super.checkByOptions(list);
    }

    private String getInputUnitId(List<InputDataEO> inputItems) {
        return null;
    }

    private List<InputDataEO> setOffsetInfo(Collection<InputDataEO> inputItemsList, List<GcOffSetVchrItemDTO> offsetVchrList, FlexibleFetchConfig fetchNumSetting, String offsetGroupId, boolean noDiff) {
        if (CollectionUtils.isEmpty(inputItemsList)) {
            return Collections.emptyList();
        }
        Set<String> offsetedSubjectCodes = this.getOffsetedSubjectCodes(fetchNumSetting);
        Map<String, List<InputDataEO>> inputItemMap = this.groupInputDataBySubject(inputItemsList);
        Map<Object, Object> offsetAmtMap = new HashMap(16);
        if (!noDiff) {
            boolean offsetHasDiff = false;
            for (GcOffSetVchrItemDTO gcOffSetVchrItemDTO : offsetVchrList) {
                if ((gcOffSetVchrItemDTO.getDiffd() == null || gcOffSetVchrItemDTO.getDiffd() == 0.0) && (gcOffSetVchrItemDTO.getDiffc() == null || gcOffSetVchrItemDTO.getDiffc() == 0.0)) continue;
                offsetHasDiff = true;
                break;
            }
            if (offsetHasDiff) {
                offsetAmtMap = this.getOffsetAmtGroupBySubject(offsetVchrList);
            }
        }
        ArrayList<InputDataEO> offsetedInputDataList = new ArrayList<InputDataEO>();
        for (Map.Entry entry : inputItemMap.entrySet()) {
            String subjectCode = (String)entry.getKey();
            if (!RuleTypeEnum.FINANCIAL_CHECK.getCode().equals(this.flexRule.getRuleType()) && !offsetedSubjectCodes.contains(subjectCode)) continue;
            List inputItems = (List)entry.getValue();
            offsetedInputDataList.addAll(inputItems);
            BigDecimal offsetAmt = BigDecimal.ZERO;
            boolean hasOffsetItem = false;
            if (offsetAmtMap.containsKey(subjectCode)) {
                offsetAmt = (BigDecimal)offsetAmtMap.get(subjectCode);
                hasOffsetItem = true;
            }
            this.setOffsetInfo(inputItems, offsetAmt, hasOffsetItem, offsetGroupId);
        }
        return offsetedInputDataList;
    }

    private void setOffsetInfo(List<InputDataEO> inputItems, BigDecimal offsetAmtSum, boolean hasOffsetItem, String offsetGroupId) {
        boolean needNotAllocateDiff;
        if (CollectionUtils.isEmpty(inputItems)) {
            return;
        }
        BigDecimal distributionAmt = BigDecimal.ZERO;
        ContextUser loginUser = NpContextHolder.getContext().getUser();
        String loginUserName = loginUser == null ? "system" : loginUser.getName();
        for (InputDataEO inputItem : inputItems) {
            Double reportOffsetAmt = inputItem.getAmt();
            inputItem.setOffsetAmt(reportOffsetAmt);
            this.setOffsetInfo(inputItem, offsetGroupId, loginUserName);
            if (inputItem.getDiffAmt() == null) {
                inputItem.setDiffAmt(0.0);
            }
            if (inputItem.getDc() == OrientEnum.D.getValue()) {
                distributionAmt = distributionAmt.add(BigDecimal.valueOf(reportOffsetAmt));
                continue;
            }
            distributionAmt = distributionAmt.subtract(BigDecimal.valueOf(reportOffsetAmt));
        }
        BigDecimal unDistributionAmt = offsetAmtSum.subtract(distributionAmt);
        boolean bl = needNotAllocateDiff = BigDecimal.ZERO.compareTo(offsetAmtSum) == 0 && !hasOffsetItem || BigDecimal.ZERO.compareTo(unDistributionAmt) == 0;
        if (needNotAllocateDiff) {
            return;
        }
        InputDataEO lastInputItem = inputItems.get(0);
        BigDecimal reportDiffAmt = unDistributionAmt;
        if (lastInputItem.getDc() == OrientEnum.D.getValue()) {
            reportDiffAmt = unDistributionAmt.negate();
        }
        lastInputItem.setDiffAmt(BigDecimal.valueOf(lastInputItem.getDiffAmt()).add(reportDiffAmt).doubleValue());
        lastInputItem.setOffsetAmt(BigDecimal.valueOf(lastInputItem.getAmt()).subtract(BigDecimal.valueOf(lastInputItem.getDiffAmt())).doubleValue());
    }

    private void setOffsetInfo(InputDataEO inputItem, String offsetGroupId, String loginUserName) {
        inputItem.setOffsetTime(new Date());
        inputItem.setOffsetGroupId(offsetGroupId);
        inputItem.setOffsetState(ReportOffsetStateEnum.OFFSET.getValue());
        inputItem.setOffsetPerson(loginUserName);
    }

    private Map<String, BigDecimal> getOffsetAmtGroupBySubject(List<GcOffSetVchrItemDTO> offsetItems) {
        HashMap<String, BigDecimal> subjectOffsetItemMap = new HashMap<String, BigDecimal>(16);
        if (CollectionUtils.isEmpty(offsetItems)) {
            return subjectOffsetItemMap;
        }
        offsetItems.forEach(offsetItem -> {
            BigDecimal offsetAmt = (BigDecimal)subjectOffsetItemMap.get(offsetItem.getSubjectCode());
            if (offsetAmt == null) {
                offsetAmt = BigDecimal.ZERO;
            }
            offsetAmt = offsetAmt.add(BigDecimal.valueOf(offsetItem.getOffSetDebit())).subtract(BigDecimal.valueOf(offsetItem.getOffSetCredit()));
            subjectOffsetItemMap.put(offsetItem.getSubjectCode(), offsetAmt);
        });
        return subjectOffsetItemMap;
    }

    private Map<String, List<InputDataEO>> groupInputDataBySubject(Collection<InputDataEO> inputItemsList) {
        if (CollectionUtils.isEmpty(inputItemsList)) {
            return Collections.emptyMap();
        }
        return inputItemsList.stream().filter(inputItem -> ReportOffsetStateEnum.NOTOFFSET.getValue().equals(inputItem.getOffsetState())).collect(Collectors.groupingBy(InputDataEO::getSubjectCode));
    }

    private Set<String> getOffsetedSubjectCodes(FlexibleFetchConfig fetchNumSetting) {
        if (!this.hasAssociatedSubjectSetting()) {
            HashSet<String> offsetedSubjectCodes = new HashSet<String>();
            offsetedSubjectCodes.addAll(this.flexRule.getSrcDebitAllChildrenCodes());
            offsetedSubjectCodes.addAll(this.flexRule.getSrcCreditAllChildrenCodes());
            return offsetedSubjectCodes;
        }
        Set<String> offsetedSubjectCodes = this.getAllChildrenCodesByRangeContainsSelf(fetchNumSetting.getAssociatedSubject());
        if (!CollectionUtils.isEmpty(offsetedSubjectCodes)) {
            return offsetedSubjectCodes;
        }
        List<String> srcSubjectCodes = this.getAllFetchSubjectCodes(fetchNumSetting);
        offsetedSubjectCodes = this.getAllChildrenCodesByRangeContainsSelf(srcSubjectCodes);
        return offsetedSubjectCodes;
    }

    private Set<String> getAllChildrenCodesByRangeContainsSelf(List<String> subjectCodes) {
        if (CollectionUtils.isEmpty(subjectCodes)) {
            return Collections.emptySet();
        }
        if (CollectionUtils.isEmpty(this.allChildrenCodesGroupByParentCode)) {
            return Collections.emptySet();
        }
        return Stream.concat(subjectCodes.stream().map(code -> this.getChildrenCodesByConsolidatedSubjects((String)code)).flatMap(Collection::stream), subjectCodes.stream()).collect(Collectors.toSet());
    }

    private Set<String> getChildrenCodesByConsolidatedSubjects(String code) {
        Set<String> childrenCodes = this.allChildrenCodesGroupByParentCode.get(code);
        if (CollectionUtils.isEmpty(childrenCodes)) {
            HashSet<String> result = new HashSet<String>(16);
            result.add(code);
            return result;
        }
        return childrenCodes;
    }

    private boolean hasAssociatedSubjectSetting() {
        if (this.hasAssociatedSubjectSetting != null) {
            return this.hasAssociatedSubjectSetting;
        }
        List fetchConfigs = this.flexRule.getFetchConfigList();
        if (CollectionUtils.isEmpty(fetchConfigs)) {
            this.hasAssociatedSubjectSetting = Boolean.FALSE;
            return false;
        }
        this.hasAssociatedSubjectSetting = fetchConfigs.stream().anyMatch(fetchConfig -> !CollectionUtils.isEmpty(fetchConfig.getAssociatedSubject()));
        return this.hasAssociatedSubjectSetting;
    }

    private List<String> getAllFetchSubjectCodes(FlexibleFetchConfig fetchNumSetting) {
        List creditConfigList;
        ArrayList<String> srcSubjectCodes = new ArrayList<String>();
        List debitConfigList = fetchNumSetting.getDebitConfigList();
        if (!CollectionUtils.isEmpty(debitConfigList)) {
            debitConfigList.forEach(fetchNumSettingItem -> srcSubjectCodes.add(fetchNumSettingItem.getSubjectCode()));
        }
        if (!CollectionUtils.isEmpty(creditConfigList = fetchNumSetting.getCreditConfigList())) {
            creditConfigList.forEach(fetchNumSettingItem -> srcSubjectCodes.add(fetchNumSettingItem.getSubjectCode()));
        }
        return srcSubjectCodes;
    }

    @Override
    protected boolean checkAndSetRule(AbstractUnionRule rule) {
        if (!(rule instanceof FlexibleRuleDTO)) {
            return false;
        }
        assert (this.flexRuleCacheService != null);
        this.flexRule = this.flexRuleCacheService.getFlexRule((FlexibleRuleDTO)rule);
        List fetchConfigList = this.flexRule.getFetchConfigList();
        this.initGroupInfo();
        return !CollectionUtils.isEmpty(fetchConfigList);
    }

    @Override
    protected boolean hasBalanceFormulaSetting(AbstractUnionRule rule) {
        assert (this.flexRuleCacheService != null);
        FlexibleRuleDTO flexRule = this.flexRuleCacheService.getFlexRule((FlexibleRuleDTO)rule);
        assert (flexRule != null);
        List fetchConfigList = flexRule.getFetchConfigList();
        if (CollectionUtils.isEmpty(fetchConfigList)) {
            return false;
        }
        ArrayList fetchItems = new ArrayList();
        for (FlexibleFetchConfig flexibleFetchConfig : fetchConfigList) {
            fetchItems.addAll(flexibleFetchConfig.getCreditConfigList());
            fetchItems.addAll(flexibleFetchConfig.getDebitConfigList());
        }
        if (CollectionUtils.isEmpty(fetchItems)) {
            return false;
        }
        for (FlexibleFetchConfig.Item item : fetchItems) {
            if (!OffsetItemCreateHelper.isBalanceSetting(item)) continue;
            return true;
        }
        return false;
    }

    private Map<String, List<InputDataEO>> noSortOffset(List<InputDataEO> inputItems, FlexibleFetchConfig fetchConfig) {
        HashMap<String, List<InputDataEO>> newRecordOffsetGroup = new HashMap<String, List<InputDataEO>>(16);
        String offsetGroupId = UUIDUtils.newUUIDStr();
        boolean offsetSuccess = this.doOffsetByFetchConfig(inputItems, fetchConfig, offsetGroupId);
        if (offsetSuccess) {
            newRecordOffsetGroup.put(offsetGroupId, inputItems);
            return newRecordOffsetGroup;
        }
        newRecordOffsetGroup.put(UUIDUtils.emptyUUIDStr(), inputItems);
        if (!Boolean.TRUE.equals(this.flexRule.getOneToOneOffsetFlag()) || this.isCheckOffset) {
            return newRecordOffsetGroup;
        }
        Map<String, List<InputDataEO>> offsetedItems = this.calMergeOneToOne(inputItems, fetchConfig);
        if (!CollectionUtils.isEmpty(offsetedItems)) {
            newRecordOffsetGroup.putAll(offsetedItems);
            offsetedItems.forEach((key, value) -> inputItems.removeAll((Collection<?>)value));
            if (CollectionUtils.isEmpty(inputItems)) {
                newRecordOffsetGroup.remove(UUIDUtils.emptyUUIDStr());
            }
        }
        return newRecordOffsetGroup;
    }

    private List<InputDataEO> doOneToOneOffset(InputDataEO inputOffsetItem, List<InputDataEO> sameGroupList, FlexibleFetchConfig fetchConfig) {
        if (CollectionUtils.isEmpty(sameGroupList) || ReportOffsetStateEnum.OFFSET.getValue().equals(inputOffsetItem.getOffsetState())) {
            return null;
        }
        ArrayList<InputDataEO> list = new ArrayList<InputDataEO>();
        boolean offsetSuccess = false;
        for (InputDataEO otherInputOffsetItem : sameGroupList) {
            list.clear();
            if (otherInputOffsetItem.getId().equals(inputOffsetItem.getId())) break;
            list.add(inputOffsetItem);
            list.add(otherInputOffsetItem);
            String offsetGroupId = UUIDUtils.newUUIDStr();
            offsetSuccess = this.doOffsetByFetchConfig(list, fetchConfig, offsetGroupId);
            if (!offsetSuccess) continue;
            break;
        }
        if (offsetSuccess) {
            return list;
        }
        return null;
    }

    private Map<String, List<InputDataEO>> sortOffset(List<InputDataEO> inputItems, FlexibleFetchConfig fetchConfig, boolean balanceOffset) {
        HashMap<String, List<InputDataEO>> recordOffsetGroup = new HashMap<String, List<InputDataEO>>(16);
        String offsetGroupId = UUIDUtils.newUUIDStr();
        recordOffsetGroup.put(UUIDUtils.emptyUUIDStr(), inputItems);
        String mrecid = UUIDOrderUtils.newUUIDStr();
        OffsetItemCreateHelper offsetItemCreateHelper = this.getOffsetItemCreateHelper();
        List<List<GcOffSetVchrItemDTO>> debitAndCreditOffsetItems = offsetItemCreateHelper.createNonBalanceOffsetItemByFetchConfig(inputItems, fetchConfig, offsetGroupId, mrecid);
        debitAndCreditOffsetItems.set(0, debitAndCreditOffsetItems.get(0));
        debitAndCreditOffsetItems.set(1, debitAndCreditOffsetItems.get(1));
        if (CollectionUtils.isEmpty(debitAndCreditOffsetItems)) {
            return recordOffsetGroup;
        }
        SortOffsetHelper sortOffsetHelper = SortOffsetHelper.newInstance(inputItems, debitAndCreditOffsetItems.get(0), debitAndCreditOffsetItems.get(1), (AbstractUnionRule)this.flexRule, this.getCheckAfterMatched(fetchConfig), this.getMatchResultCunsumer(fetchConfig, recordOffsetGroup, inputItems)).setMaxOffsetNum(this.maxOffsetNum);
        BiFunction<Collection<GcOffSetVchrItemDTO>, Collection<GcOffSetVchrItemDTO>, List<GcOffSetVchrItemDTO>> balanceCreator = null;
        if (balanceOffset) {
            balanceCreator = this.getBalanceCreator(fetchConfig, inputItems.stream().collect(Collectors.toMap(InputDataEO::getId, i -> i)), offsetItemCreateHelper);
            sortOffsetHelper.setBalanceCreator(balanceCreator);
        }
        this.matchedNum += sortOffsetHelper.offset();
        if (Boolean.TRUE.equals(this.flexRule.getUnilateralOffsetFlag())) {
            this.doUnilateralOffset(debitAndCreditOffsetItems, inputItems, recordOffsetGroup, fetchConfig, balanceCreator);
        }
        return recordOffsetGroup;
    }

    private Predicate<List<InputDataEO>> getCheckAfterMatched(FlexibleFetchConfig fetchConfig) {
        return checkedInputItems -> {
            if (StringUtils.hasLength(this.checkByOptions((Collection<InputDataEO>)checkedInputItems))) {
                return false;
            }
            OffsetItemCreateHelper offsetItemCreateHelper = this.getOffsetItemCreateHelper();
            return offsetItemCreateHelper.checkByFilterFormula((List<InputDataEO>)checkedInputItems, fetchConfig.getFilterFormula());
        };
    }

    private BiConsumer<Collection<InputDataEO>, List<GcOffSetVchrItemDTO>> getMatchResultCunsumer(FlexibleFetchConfig fetchConfig, Map<String, List<InputDataEO>> recordOffsetGroup, List<InputDataEO> inputItems) {
        return (offsetedInputItems, offsetItems) -> {
            String offsetGroupId = ((GcOffSetVchrItemDTO)offsetItems.get(0)).getSrcOffsetGroupId();
            recordOffsetGroup.put(offsetGroupId, new ArrayList(offsetedInputItems));
            if (CollectionUtils.isEmpty(inputItems)) {
                recordOffsetGroup.remove(UUIDUtils.emptyUUIDStr());
            } else {
                recordOffsetGroup.put(UUIDUtils.emptyUUIDStr(), inputItems);
            }
            boolean noDiff = GcCalcAmtCheckUtil.distributionAmt((AbstractUnionRule)this.flexRule, (Collection)offsetItems);
            List<InputDataEO> offsetedList = this.setOffsetInfo((Collection<InputDataEO>)offsetedInputItems, (List<GcOffSetVchrItemDTO>)offsetItems, fetchConfig, offsetGroupId, noDiff);
            GcOffSetVchrDTO offsetVchr = new GcOffSetVchrDTO();
            offsetVchr.setItems(offsetItems);
            if (this.isManualBatchOffset) {
                offsetVchr.setConsFormulaCalcType("manualFlag");
            } else {
                offsetVchr.setConsFormulaCalcType("autoFlag");
            }
            FlexibleRuleExecutorImpl flexibleRuleExecutorImpl = this;
            flexibleRuleExecutorImpl.offsetedNum = flexibleRuleExecutorImpl.offsetedNum + this.inputService.doOffset(offsetedList, offsetVchr);
            this.pullOffSetVchrItems(offsetGroupId, offsetVchr.getItems());
        };
    }

    private BiFunction<Collection<GcOffSetVchrItemDTO>, Collection<GcOffSetVchrItemDTO>, List<GcOffSetVchrItemDTO>> getBalanceCreator(FlexibleFetchConfig fetchConfig, Map<String, InputDataEO> inputItemGroupById, OffsetItemCreateHelper offsetItemCreateHelper) {
        return (offsetItems1, offsetItems2) -> {
            double dibitAndOffsetDiff;
            List<FlexibleFetchConfig.Item> debitBalanceFetchItems = this.filterBalanceFetchItems(fetchConfig.getDebitConfigList());
            List<InputDataEO> inputItems = Stream.concat(offsetItems1.stream(), offsetItems2.stream()).map(GcOffSetVchrItemDTO::getSrcId).distinct().map(inputItemGroupById::get).filter(Objects::nonNull).collect(Collectors.toList());
            List<GcOffSetVchrItemDTO> balanceVchrItem = offsetItemCreateHelper.createBalanceOffsetItem(inputItems, fetchConfig, debitBalanceFetchItems, dibitAndOffsetDiff = Stream.concat(offsetItems1.stream(), offsetItems2.stream()).map(offsetItem -> offsetItem.getDebit() - offsetItem.getCredit()).reduce(0.0, Double::sum).doubleValue(), OrientEnum.D);
            if (!CollectionUtils.isEmpty(balanceVchrItem)) {
                return balanceVchrItem;
            }
            List<FlexibleFetchConfig.Item> creditBalanceFetchItems = this.filterBalanceFetchItems(fetchConfig.getCreditConfigList());
            balanceVchrItem = offsetItemCreateHelper.createBalanceOffsetItem(inputItems, fetchConfig, creditBalanceFetchItems, dibitAndOffsetDiff, OrientEnum.C);
            return balanceVchrItem;
        };
    }

    private List<FlexibleFetchConfig.Item> filterBalanceFetchItems(List<FlexibleFetchConfig.Item> fetchItems) {
        return fetchItems.stream().filter(OffsetItemCreateHelper::isBalanceSetting).collect(Collectors.toList());
    }

    private void doUnilateralOffset(List<List<GcOffSetVchrItemDTO>> debitAndCreditOffsetItems, List<InputDataEO> inputItems, Map<String, List<InputDataEO>> recordOffsetGroup, FlexibleFetchConfig fetchConfig, BiFunction<Collection<GcOffSetVchrItemDTO>, Collection<GcOffSetVchrItemDTO>, List<GcOffSetVchrItemDTO>> balanceCreator) {
        SortOffsetHelper sortOffsetHelper;
        if (!CollectionUtils.isEmpty((Collection)debitAndCreditOffsetItems.get(0))) {
            List<List<GcOffSetVchrItemDTO>> debitRecordOffsetGroup = this.groupByAmtSign(debitAndCreditOffsetItems.get(0));
            sortOffsetHelper = SortOffsetHelper.newInstance(inputItems, debitRecordOffsetGroup.get(0), debitRecordOffsetGroup.get(1), (AbstractUnionRule)this.flexRule, this.getCheckAfterMatched(fetchConfig), this.getMatchResultCunsumer(fetchConfig, recordOffsetGroup, inputItems)).setUnilateralOffsetItems(debitAndCreditOffsetItems.get(0));
            sortOffsetHelper.setBalanceCreator(balanceCreator);
            this.matchedNum += sortOffsetHelper.offset();
        }
        if (!CollectionUtils.isEmpty((Collection)debitAndCreditOffsetItems.get(1))) {
            List<List<GcOffSetVchrItemDTO>> creditRecordOffsetGroup = this.groupByAmtSign(debitAndCreditOffsetItems.get(1));
            sortOffsetHelper = SortOffsetHelper.newInstance(inputItems, creditRecordOffsetGroup.get(0), creditRecordOffsetGroup.get(1), (AbstractUnionRule)this.flexRule, checkedInputItems -> ObjectUtils.isEmpty(this.checkByOptions((Collection<InputDataEO>)checkedInputItems)), this.getMatchResultCunsumer(fetchConfig, recordOffsetGroup, inputItems)).setUnilateralOffsetItems(debitAndCreditOffsetItems.get(1));
            sortOffsetHelper.setBalanceCreator(balanceCreator);
            this.matchedNum += sortOffsetHelper.offset();
        }
    }

    private Map<String, List<InputDataEO>> calMergeOneToOne(List<InputDataEO> inputItems, FlexibleFetchConfig fetchConfig) {
        ArrayList<InputDataEO> otherItems = new ArrayList<InputDataEO>(inputItems);
        HashMap<String, List<InputDataEO>> allOffsetedItems = new HashMap<String, List<InputDataEO>>(16);
        inputItems.forEach(inputItem -> {
            ++this.matchedNum;
            otherItems.remove(inputItem);
            List<InputDataEO> offsetedItems = this.doOneToOneOffset((InputDataEO)((Object)inputItem), (List<InputDataEO>)otherItems, fetchConfig);
            if (!CollectionUtils.isEmpty(offsetedItems)) {
                otherItems.removeAll(offsetedItems);
                allOffsetedItems.put(offsetedItems.get(0).getOffsetGroupId(), offsetedItems);
            }
        });
        return allOffsetedItems;
    }

    @Override
    AbstractUnionRule getRule() {
        return this.flexRule;
    }

    public List<GcOffSetVchrItemDTO> createReltxnAutoOffsetItems(FlexibleRuleDTO rule, List<InputDataEO> inputItems, GcCalcArgmentsDTO arg) {
        if (!RuleMappingImplUtils.checkRelTxnApplicableCondition(inputItems, (AbstractUnionRule)rule, arg)) {
            return null;
        }
        rule.setGeneratePHSFlag(Boolean.valueOf(true));
        OffsetItemCreateHelper offsetItemCreateHelper = OffsetItemCreateHelper.newFlexInstance(rule, arg, this.realTimeOffsetOptionFlag);
        return this.createManualOffsetItems(rule, inputItems, arg, offsetItemCreateHelper);
    }

    public List<GcOffSetVchrItemDTO> createReltxnManualOffsetItems(FlexibleRuleDTO rule, List<InputDataEO> inputItems, GcCalcArgmentsDTO arg) {
        rule.setGeneratePHSFlag(Boolean.valueOf(true));
        OffsetItemCreateHelper offsetItemCreateHelper = OffsetItemCreateHelper.newFlexManualInstance(rule, arg, this.realTimeOffsetOptionFlag);
        return this.createManualOffsetItems(rule, inputItems, arg, offsetItemCreateHelper);
    }

    public List<GcOffSetVchrItemDTO> createManualOffsetItems(FlexibleRuleDTO rule, List<InputDataEO> inputItems, GcCalcArgmentsDTO arg) {
        OffsetItemCreateHelper offsetItemCreateHelper = OffsetItemCreateHelper.newFlexManualInstance(rule, arg, this.realTimeOffsetOptionFlag);
        return this.createManualOffsetItems(rule, inputItems, arg, offsetItemCreateHelper);
    }

    private List<GcOffSetVchrItemDTO> createManualOffsetItems(FlexibleRuleDTO rule, List<InputDataEO> inputItems, GcCalcArgmentsDTO arg, OffsetItemCreateHelper offsetItemCreateHelper) {
        this.processEnv = new GcCalcEnvContextImpl(UUIDUtils.newUUIDStr());
        this.reportSystemId = this.taskCacheService.getSystemIdBySchemeId(arg.getSchemeId(), arg.getPeriodStr());
        if (this.reportSystemId == null) {
            return Collections.emptyList();
        }
        ((GcCalcEnvContextImpl)this.processEnv).setCalcArgments(arg);
        assert (this.flexRuleCacheService != null);
        this.flexRule = this.flexRuleCacheService.getFlexRule(rule);
        List fetchConfigList = this.flexRule.getFetchConfigList();
        this.recalcAmt(inputItems);
        String offsetGroupId = UUIDOrderUtils.newUUIDStr();
        ArrayList<GcOffSetVchrItemDTO> offsetItems = new ArrayList<GcOffSetVchrItemDTO>();
        for (FlexibleFetchConfig fetchConfig : fetchConfigList) {
            OffsetItemCreateHelper.CreateResult createResult = offsetItemCreateHelper.createOffsetItemByFetchConfig(inputItems, fetchConfig, offsetGroupId, this.alreadyItemsGroupByOffSetGroupId.get(offsetGroupId));
            if (CollectionUtils.isEmpty(createResult.getOffsetItems())) continue;
            offsetItems.addAll(createResult.getOffsetItems());
        }
        return offsetItems;
    }

    @Override
    protected boolean canUnilateralOffset() {
        return Boolean.TRUE.equals(this.flexRule.getUnilateralOffsetFlag());
    }

    @Override
    protected boolean canBothDc() {
        return !this.groupFieldList.contains("DC");
    }

    @Override
    public String canOffset(List<InputDataEO> inputItems, boolean realTimeOffset, AbstractUnionRule rule, String orgType) {
        assert (this.flexRuleCacheService != null);
        this.flexRule = this.flexRuleCacheService.getFlexRule((FlexibleRuleDTO)rule);
        return super.canOffset(inputItems, realTimeOffset, rule, orgType);
    }

    @Override
    protected String checkAmt(List<InputDataEO> inputItems) {
        return this.checkOffSetAmt(inputItems);
    }

    private String checkOffSetAmt(List<InputDataEO> inputItems) {
        StringBuilder errMsg = new StringBuilder(GcI18nUtil.getMessage((String)"gc.calculate.flexiblerule.fetchsettingsmatchfailmsg"));
        for (int index = 0; index < this.flexRule.getFetchConfigList().size(); ++index) {
            FlexibleFetchConfig fetchConfig = (FlexibleFetchConfig)this.flexRule.getFetchConfigList().get(index);
            OffsetItemCreateHelper offsetItemCreateHelper = this.getOffsetItemCreateHelper();
            OffsetItemCreateHelper.CreateResult createResult = offsetItemCreateHelper.createOffsetItemByFetchConfig(inputItems, fetchConfig, null, new ArrayList<GcOffSetVchrItemDTO>());
            boolean amtMatched = createResult.isNoDiff();
            if (CollectionUtils.isEmpty(createResult.getOffsetItems())) {
                amtMatched = false;
            } else if (!amtMatched) {
                BigDecimal debitSum = BigDecimal.ZERO;
                BigDecimal creditSum = BigDecimal.ZERO;
                for (GcOffSetVchrItemDTO record : createResult.getOffsetItems()) {
                    debitSum = NumberUtils.add((BigDecimal)debitSum, (BigDecimal[])new BigDecimal[]{BigDecimal.valueOf(record.getDebit())});
                    creditSum = NumberUtils.add((BigDecimal)creditSum, (BigDecimal[])new BigDecimal[]{BigDecimal.valueOf(record.getCredit())});
                }
                amtMatched = GcCalcAmtCheckUtil.checkAmt((AbstractUnionRule)this.flexRule, (BigDecimal)debitSum, (BigDecimal)creditSum, createResult.getOffsetItems());
            }
            if (!amtMatched && index == 0 && Boolean.TRUE.equals(this.flexRule.getReconciliationOffsetFlag())) {
                return GcI18nUtil.getMessage((String)"gc.calculate.flexiblerule.firstfetchsettingmsg") + (createResult.isFilterFormulaSuccess() ? GcI18nUtil.getMessage((String)"gc.calculate.flexiblerule.amtmatchfailmsg") : GcI18nUtil.getMessage((String)"gc.calculate.flexiblerule.filtermatchfailmsg"));
            }
            if (!amtMatched) {
                String msg = createResult.isFilterFormulaSuccess() ? GcI18nUtil.getMessage((String)"gc.calculate.flexiblerule.amtmatchfailmsg") : GcI18nUtil.getMessage((String)"gc.calculate.flexiblerule.filtermatchfailmsg");
                Object[] args = new String[]{String.valueOf(index + 1), msg};
                errMsg.append(GcI18nUtil.getMessage((String)"gc.calculate.flexiblerule.notamtmatchedmsg", (Object[])args));
            }
            if (!amtMatched) continue;
            return "";
        }
        return errMsg.toString();
    }

    private String checkReconciliationOffsetAmt(List<InputDataEO> inputItems) {
        List<InputDataEO> creditInputDataItems;
        List<InputDataEO> debitInputDataItems = inputItems.stream().filter(inputItem -> inputItem.getDc() == OrientEnum.D.getValue()).collect(Collectors.toList());
        boolean amtCheckFlag = this.checkNumberFieldsAmt(debitInputDataItems, creditInputDataItems = inputItems.stream().filter(inputItem -> inputItem.getDc() == OrientEnum.C.getValue()).collect(Collectors.toList()));
        if (!amtCheckFlag) {
            Map<String, String> fieldDeines = this.offsetGroupingFields.stream().collect(Collectors.toMap(ColumnModelDefine::getName, column -> ObjectUtils.isEmpty(column.getTitle()) ? column.getName() : column.getTitle()));
            StringBuilder fieldDefineTitle = new StringBuilder();
            for (String code : this.numberFields) {
                fieldDefineTitle.append(fieldDeines.get(code)).append("\u3001");
            }
            Object[] args = new String[]{fieldDefineTitle.substring(0, fieldDefineTitle.length() - 1)};
            return GcI18nUtil.getMessage((String)"gc.calculate.flexiblerule.rulematchedfailmsg", (Object[])args);
        }
        return this.checkOffSetAmt(inputItems);
    }

    private boolean checkNumberFieldsAmt(List<InputDataEO> debitOffsetItems, List<InputDataEO> creditOffsetItems) {
        double debitSum = 0.0;
        double creditSum = 0.0;
        for (InputDataEO record : debitOffsetItems) {
            for (String fieldName : this.numberFields) {
                if (record.getFieldValue(fieldName) == null) continue;
                debitSum += ((Double)record.getFieldValue(fieldName)).doubleValue();
            }
        }
        for (InputDataEO record : creditOffsetItems) {
            for (String fieldName : this.numberFields) {
                if (record.getFieldValue(fieldName) == null) continue;
                creditSum += ((Double)record.getFieldValue(fieldName)).doubleValue();
            }
        }
        return Math.abs(debitSum - creditSum) <= 0.001;
    }

    public static RuleChecker getRuleCheckInstance(AbstractUnionRule rule) {
        FlexibleRuleExecutorImpl executorImpl = new FlexibleRuleExecutorImpl();
        executorImpl.flexRule = FlexibleRuleExecutorImpl.convertFlexibleRule(rule);
        executorImpl.initGroupInfo();
        return executorImpl;
    }

    public static FlexibleRuleDTO convertFlexibleRule(AbstractUnionRule rule) {
        if (RuleTypeEnum.FLEXIBLE.getCode().equals(rule.getRuleType())) {
            return (FlexibleRuleDTO)rule;
        }
        FinancialCheckRuleDTO financialCheckRule = (FinancialCheckRuleDTO)rule;
        FlexibleRuleDTO flexibleRule = new FlexibleRuleDTO();
        BeanUtils.copyProperties(rule, flexibleRule);
        List fetchConfigList = financialCheckRule.getFetchConfigList().stream().map(financialCheckFetchConfig -> {
            FlexibleFetchConfig flexibleFetchConfig = new FlexibleFetchConfig();
            BeanUtils.copyProperties(financialCheckFetchConfig, flexibleFetchConfig);
            List debitConfigList = financialCheckFetchConfig.getDebitConfigList().stream().map(item -> {
                FlexibleFetchConfig.Item flexibleItem = new FlexibleFetchConfig.Item();
                BeanUtils.copyProperties(item, flexibleItem);
                return flexibleItem;
            }).collect(Collectors.toList());
            List crebitConfigList = financialCheckFetchConfig.getCreditConfigList().stream().map(item -> {
                FlexibleFetchConfig.Item flexibleItem = new FlexibleFetchConfig.Item();
                BeanUtils.copyProperties(item, flexibleItem);
                return flexibleItem;
            }).collect(Collectors.toList());
            flexibleFetchConfig.setDebitConfigList(debitConfigList);
            flexibleFetchConfig.setCreditConfigList(crebitConfigList);
            return flexibleFetchConfig;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(fetchConfigList)) {
            return null;
        }
        flexibleRule.setFetchConfigList(fetchConfigList);
        return flexibleRule;
    }

    private void doReconciliationOffset(List<InputDataEO> checkItems) {
        List<InputDataEO> debitInputDataItems = checkItems.stream().filter(inputItem -> inputItem.getDc() == OrientEnum.D.getValue()).collect(Collectors.toList());
        List<InputDataEO> creditInputDataItems = checkItems.stream().filter(inputItem -> inputItem.getDc() == OrientEnum.C.getValue()).collect(Collectors.toList());
        this.doReconciliationOffset(debitInputDataItems, creditInputDataItems, checkItems, false);
        if (Boolean.TRUE.equals(this.flexRule.getUnilateralOffsetFlag()) && !CollectionUtils.isEmpty(checkItems)) {
            this.doUnilateralReconciliationOffset(checkItems);
        }
        if (!CollectionUtils.isEmpty(checkItems)) {
            this.doSumReconciliationOffset(checkItems);
        }
    }

    public void doSumReconciliationOffset(List<InputDataEO> checkItems) {
        List<InputDataEO> creditInputDataItems;
        List<InputDataEO> debitInputDataItems = checkItems.stream().filter(inputItem -> inputItem.getDc() == OrientEnum.D.getValue()).collect(Collectors.toList());
        boolean matched = this.checkNumberFieldsAmt(debitInputDataItems, creditInputDataItems = checkItems.stream().filter(inputItem -> inputItem.getDc() == OrientEnum.C.getValue()).collect(Collectors.toList()));
        if (matched) {
            checkItems.clear();
            ArrayList<InputDataEO> inputDataItems = new ArrayList<InputDataEO>();
            inputDataItems.addAll(debitInputDataItems);
            inputDataItems.addAll(creditInputDataItems);
            String checkGroupId = UUIDUtils.newUUIDStr();
            this.setCheckInputDataItems(inputDataItems, checkGroupId);
            this.inputDataCheckedGroupByCheckId.put(checkGroupId, inputDataItems);
        }
    }

    private void doReconciliationOffset(List<InputDataEO> debitOffsetItems, List<InputDataEO> creditOffsetItems, final List<InputDataEO> inputItems, final boolean unilateralOffsetFlag) {
        this.matchedNum += new ListMatch(debitOffsetItems, creditOffsetItems, (ListMatch.CallBack)new ListMatch.CallBack<InputDataEO>(){

            public int getMaxCount() {
                return unilateralOffsetFlag ? 1 : FlexibleRuleExecutorImpl.this.maxOffsetNum;
            }

            public double getValue(InputDataEO o) {
                double amtSum = 0.0;
                for (String fieldName : FlexibleRuleExecutorImpl.this.numberFields) {
                    if (o.getFieldValue(fieldName) == null) continue;
                    amtSum += ((Double)o.getFieldValue(fieldName)).doubleValue();
                }
                if (unilateralOffsetFlag) {
                    return amtSum > 0.0 ? amtSum : -amtSum;
                }
                return amtSum;
            }

            public ListMatch.CompareResult<InputDataEO> equals(double v1, double v2, Collection<InputDataEO> o1, Collection<InputDataEO> o2) {
                ListMatch.CompareResult compareResult = new ListMatch.CompareResult();
                if (Math.abs(v1 - v2) > 0.001) {
                    return compareResult;
                }
                compareResult.setEqual(true);
                return compareResult;
            }

            public void accept(Collection<InputDataEO> set1, Collection<InputDataEO> set2, List<InputDataEO> balanceItem) {
                inputItems.removeAll(set1);
                inputItems.removeAll(set2);
                ArrayList<InputDataEO> inputDataItems = new ArrayList<InputDataEO>();
                inputDataItems.addAll(set1);
                inputDataItems.addAll(set2);
                String checkGroupId = UUIDUtils.newUUIDStr();
                FlexibleRuleExecutorImpl.this.setCheckInputDataItems(inputDataItems, checkGroupId);
                FlexibleRuleExecutorImpl.this.inputDataCheckedGroupByCheckId.put(checkGroupId, inputDataItems);
            }
        }).match();
    }

    private void setCheckInputDataItems(List<InputDataEO> checkInputItems, String checkGroupId) {
        if (CollectionUtils.isEmpty(checkInputItems)) {
            return;
        }
        Date currentTime = new Date();
        String userName = NpContextHolder.getContext().getUserName();
        checkInputItems.forEach(inputData -> {
            inputData.setCheckGroupId(checkGroupId);
            inputData.setCheckState(InputDataCheckStateEnum.CHECK.getValue());
            inputData.setCheckType(InputDataCheckTypeEnum.AUTO_ITEM.getValue());
            if (inputData.getUnCheckAmt() == null) {
                inputData.setCheckAmt(0.0);
            } else {
                inputData.setCheckAmt(inputData.getUnCheckAmt());
            }
            inputData.setCheckTime(currentTime);
            inputData.setCreateUser(userName);
        });
    }

    private void doUnilateralReconciliationOffset(List<InputDataEO> inputItems) {
        ArrayList<InputDataEO> debitInputDataItems = new ArrayList<InputDataEO>();
        ArrayList<InputDataEO> creditInputDataItems = new ArrayList<InputDataEO>();
        inputItems.forEach(inputItem -> {
            if (inputItem.getDc() == OrientEnum.D.getValue()) {
                debitInputDataItems.add((InputDataEO)((Object)inputItem));
            } else if (inputItem.getDc() == OrientEnum.C.getValue()) {
                creditInputDataItems.add((InputDataEO)((Object)inputItem));
            }
        });
        this.doOffsetGroupInputData(debitInputDataItems, inputItems);
        this.doOffsetGroupInputData(creditInputDataItems, inputItems);
    }

    private void doOffsetGroupInputData(List<InputDataEO> offsetInputDataItems, List<InputDataEO> inputItems) {
        if (CollectionUtils.isEmpty(offsetInputDataItems)) {
            return;
        }
        List<List<InputDataEO>> offsetGroupInputData = this.groupByAmtSignInputdata(offsetInputDataItems);
        if (!CollectionUtils.isEmpty((Collection)offsetGroupInputData.get(0)) && !CollectionUtils.isEmpty((Collection)offsetGroupInputData.get(1))) {
            this.doReconciliationOffset(offsetGroupInputData.get(0), offsetGroupInputData.get(1), inputItems, true);
        }
    }

    private List<ColumnModelDefine> getInputDataAllField(String taskId) {
        try {
            assert (this.dataModelService != null);
            assert (this.inputDataNameProvider != null);
            String tableName = null == taskId ? "GC_INPUTDATATEMPLATE" : this.inputDataNameProvider.getTableNameByTaskId(taskId);
            TableModelDefine tableDefine = this.dataModelService.getTableModelDefineByName(tableName);
            return this.dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
        }
        catch (Exception e) {
            this.logger.error("\u83b7\u53d6\u5185\u90e8\u8868\u5b57\u6bb5\u4fe1\u606f\u5f02\u5e38", e);
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.flexiblerule.inputdatafieldsexceptionmsg"), (Throwable)e);
        }
    }

    private void pullOffSetVchrItems(String ofsetGroupId, List<GcOffSetVchrItemDTO> offSetVchrItems) {
        if (CollectionUtils.isEmpty(offSetVchrItems)) {
            return;
        }
        List offsetItems = this.alreadyItemsGroupByOffSetGroupId.computeIfAbsent(ofsetGroupId, item -> new ArrayList());
        offsetItems.addAll(offSetVchrItems);
    }

    private void listConsolidatedSubjectBySystemId() {
        List subjectEOList = this.consolidatedSubjectService.listAllSubjectsBySystemId(this.reportSystemId);
        this.allChildrenCodesGroupByParentCode = subjectEOList.stream().filter(item -> !StringUtils.isEmpty(item.getParentCode())).collect(Collectors.groupingBy(ConsolidatedSubjectEO::getParentCode, Collectors.mapping(ConsolidatedSubjectEO::getCode, Collectors.toSet())));
    }

    private boolean isRealTimeOffset() {
        boolean isRealTimeOffset;
        if (!this.realTimeOffsetOptionFlag || !this.flexRule.getCheckOffsetFlag().booleanValue()) {
            return true;
        }
        Object maxAmountOfRealTimeOffset = this.optionService.getOptionItem(this.reportSystemId, "maxAmountOfRealTimeOffset");
        if (maxAmountOfRealTimeOffset == null) {
            return false;
        }
        int maxOffsetAmount = Integer.valueOf(String.valueOf(maxAmountOfRealTimeOffset));
        boolean bl = isRealTimeOffset = maxOffsetAmount > 0;
        return isRealTimeOffset && Boolean.TRUE.equals(this.flexRule.getRealTimeOffsetFlag());
    }

    static class SortOffsetCheckResult {
        boolean canSortOffset;
        boolean balanceOffset;

        SortOffsetCheckResult() {
        }
    }

    public static class OffsetResult {
        private boolean matched;
        private List<InputDataEO> offsetedInputItems;
        private GcOffSetVchrDTO offsetVchr;

        public List<InputDataEO> getOffsetedInputItems() {
            return this.offsetedInputItems;
        }

        public GcOffSetVchrDTO getOffsetVchr() {
            return this.offsetVchr;
        }
    }
}

