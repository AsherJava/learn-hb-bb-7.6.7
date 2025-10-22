/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.formula.GcAbstractData
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.OffsetVchrItemNumberUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.ManagementDimensionCacheService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.enums.CheckStateEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.transfer.service.TransferService
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleFetchConfig
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleFetchConfig$Item
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO
 *  com.jiuqi.gcreport.unionrule.enums.FetchTypeEnum
 *  com.jiuqi.gcreport.unionrule.enums.FlexibleDimSrcTypeEnum
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 */
package com.jiuqi.gcreport.inputdata.flexible.processor.executor.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.formula.GcAbstractData;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.OffsetVchrItemNumberUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.ManagementDimensionCacheService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.inputdata.formula.GcInputDataFormulaEvalService;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.enums.CheckStateEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.transfer.service.TransferService;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FlexibleFetchConfig;
import com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.FetchTypeEnum;
import com.jiuqi.gcreport.unionrule.enums.FlexibleDimSrcTypeEnum;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

class OffsetItemCreateHelper {
    private final AbstractUnionRule rule;
    private final GcCalcArgmentsDTO calcArgments;
    private boolean manualOffset = false;
    private boolean batchManualOffset = false;
    private final boolean canCreateBalance;
    private final GcInputDataFormulaEvalService inputDataFormulaEvalService;
    private FlexibleFetchConfig fetchConfig;
    private Map<String, List<InputDataEO>> recordMap;
    private String offsetGroupId;
    private String mrecid;
    private String orgType;
    private final String CUSTOMIZE_FORMULA = "_CUSTOMIZE_FORMULA";
    private List<FlexibleFetchConfig.Item> debitBalanceFetchNumSettingItems;
    private List<FlexibleFetchConfig.Item> creditBalanceFetchNumSettingItems;
    private List<FlexibleFetchConfig.Item> debitSubjectAllocationFetchNumSettingItems;
    private List<FlexibleFetchConfig.Item> creditSubjectAllocationFetchNumSettingItems;
    private List<GcOffSetVchrItemDTO> alreadyOffSetVchrItems;
    private static final String ALLLIST = "ALL";
    private static final double ERRORRANGE = 0.001;

    static OffsetItemCreateHelper newFlexInstance(FlexibleRuleDTO rule, GcCalcArgmentsDTO calcArgments, boolean realTimeOffsetOptionFlag) {
        boolean canCreateBalance = !realTimeOffsetOptionFlag || Boolean.TRUE.equals(rule.getGeneratePHSFlag());
        OffsetItemCreateHelper offsetItemCreateHelper = new OffsetItemCreateHelper((AbstractUnionRule)rule, calcArgments, canCreateBalance);
        return offsetItemCreateHelper;
    }

    static OffsetItemCreateHelper newFlexManualInstance(FlexibleRuleDTO rule, GcCalcArgmentsDTO calcArgments, boolean realTimeOffsetOptionFlag) {
        boolean canCreateBalance = !realTimeOffsetOptionFlag || Boolean.TRUE.equals(rule.getGeneratePHSFlag());
        OffsetItemCreateHelper offsetItemCreateHelper = new OffsetItemCreateHelper((AbstractUnionRule)rule, calcArgments, canCreateBalance);
        offsetItemCreateHelper.manualOffset = true;
        return offsetItemCreateHelper;
    }

    static OffsetItemCreateHelper newFlexBatchManualInstance(FlexibleRuleDTO rule, GcCalcArgmentsDTO calcArgments, boolean realTimeOffsetOptionFlag) {
        boolean canCreateBalance = !realTimeOffsetOptionFlag || Boolean.TRUE.equals(rule.getGeneratePHSFlag());
        OffsetItemCreateHelper offsetItemCreateHelper = new OffsetItemCreateHelper((AbstractUnionRule)rule, calcArgments, canCreateBalance);
        offsetItemCreateHelper.batchManualOffset = true;
        offsetItemCreateHelper.manualOffset = true;
        return offsetItemCreateHelper;
    }

    private OffsetItemCreateHelper(AbstractUnionRule rule, GcCalcArgmentsDTO calcArgments, boolean canCreateBalance) {
        this.rule = rule;
        this.calcArgments = calcArgments;
        this.canCreateBalance = canCreateBalance;
        this.inputDataFormulaEvalService = (GcInputDataFormulaEvalService)SpringContextUtils.getBean(GcInputDataFormulaEvalService.class);
        this.orgType = this.getCommonUnitOrgType();
    }

    CreateResult createOffsetItemByFetchConfig(List<InputDataEO> inputItems, FlexibleFetchConfig fetchConfig, String offsetGroupId, List<GcOffSetVchrItemDTO> alreadyOffSetVchrItems) {
        String filterFormula;
        this.recordMap = this.groupByDc(inputItems);
        this.fetchConfig = fetchConfig;
        this.offsetGroupId = offsetGroupId;
        this.mrecid = UUIDOrderUtils.newUUIDStr();
        this.alreadyOffSetVchrItems = alreadyOffSetVchrItems;
        CreateResult createResult = new CreateResult();
        String string = filterFormula = this.manualOffset ? fetchConfig.getManualFilterFormula() : fetchConfig.getFilterFormula();
        if (!this.checkByFilterFormula(inputItems, filterFormula)) {
            createResult.setFilterFormulaSuccess(false);
            return createResult;
        }
        createResult.setFilterFormulaSuccess(true);
        List<List<GcOffSetVchrItemDTO>> debitAndCreditOffsetItems = this.doCreateNonBalanceOffsetItemByFetchConfig();
        List<GcOffSetVchrItemDTO> debitOffsetItems = debitAndCreditOffsetItems.get(0);
        List<GcOffSetVchrItemDTO> creditOffsetItems = debitAndCreditOffsetItems.get(1);
        double debitAndCreditDiff = this.getDebitAndCreditDiff(debitOffsetItems, creditOffsetItems);
        List<GcOffSetVchrItemDTO> subjectAllocationOffsetItems = this.getSubjectAllocationOffsetItems(debitOffsetItems, creditOffsetItems, debitAndCreditDiff);
        if (!CollectionUtils.isEmpty(subjectAllocationOffsetItems)) {
            debitAndCreditDiff = this.getDebitAndCreditDiff(debitOffsetItems, creditOffsetItems);
        }
        List<GcOffSetVchrItemDTO> balanceOffsetItems = null;
        if (Math.abs(debitAndCreditDiff) > 0.001 && this.canCreateBalance) {
            balanceOffsetItems = this.doCreateBalanceOffsetItem(this.debitBalanceFetchNumSettingItems, debitAndCreditDiff, OrientEnum.D);
            if (!CollectionUtils.isEmpty(balanceOffsetItems)) {
                debitOffsetItems.addAll(balanceOffsetItems);
            } else {
                balanceOffsetItems = this.doCreateBalanceOffsetItem(this.creditBalanceFetchNumSettingItems, debitAndCreditDiff, OrientEnum.C);
                if (!CollectionUtils.isEmpty(balanceOffsetItems)) {
                    creditOffsetItems.addAll(balanceOffsetItems);
                }
            }
        }
        ArrayList<GcOffSetVchrItemDTO> offsetItems = new ArrayList<GcOffSetVchrItemDTO>();
        offsetItems.addAll(debitOffsetItems);
        offsetItems.addAll(creditOffsetItems);
        createResult.setOffsetItems(offsetItems);
        createResult.setNoDiff(Math.abs(debitAndCreditDiff) <= 0.001 || !CollectionUtils.isEmpty(balanceOffsetItems) || !CollectionUtils.isEmpty(subjectAllocationOffsetItems));
        return createResult;
    }

    boolean checkByFilterFormula(List<InputDataEO> list, String formula) {
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        if (ObjectUtils.isEmpty(formula)) {
            return true;
        }
        return this.inputDataFormulaEvalService.checkSumInputData(DimensionUtils.generateDimSet((Object)this.calcArgments.getOrgId(), (Object)this.calcArgments.getPeriodStr(), (Object)this.calcArgments.getCurrency(), (Object)this.orgType, (String)this.calcArgments.getSelectAdjustCode(), (String)this.calcArgments.getTaskId()), formula, list);
    }

    private Map<String, List<InputDataEO>> groupByDc(Collection<InputDataEO> inputItems) {
        if (CollectionUtils.isEmpty(inputItems)) {
            return null;
        }
        HashMap<String, List<InputDataEO>> group = new HashMap<String, List<InputDataEO>>(16);
        ArrayList debitList = new ArrayList();
        ArrayList creditList = new ArrayList();
        group.put(OrientEnum.D.getCode(), debitList);
        group.put(OrientEnum.C.getCode(), creditList);
        group.put(ALLLIST, new ArrayList<InputDataEO>(inputItems));
        if (CollectionUtils.isEmpty(inputItems)) {
            return group;
        }
        inputItems.forEach(record -> {
            if (record.getDc() == OrientEnum.D.getValue()) {
                debitList.add(record);
            } else if (record.getDc() == OrientEnum.C.getValue()) {
                creditList.add(record);
            }
        });
        return group;
    }

    List<List<GcOffSetVchrItemDTO>> createNonBalanceOffsetItemByFetchConfig(List<InputDataEO> inputItems, FlexibleFetchConfig fetchConfig, String offsetGroupId, String mrecid) {
        this.recordMap = this.groupByDc(inputItems);
        this.fetchConfig = fetchConfig;
        this.offsetGroupId = offsetGroupId;
        this.mrecid = mrecid;
        return this.doCreateNonBalanceOffsetItemByFetchConfig();
    }

    private List<List<GcOffSetVchrItemDTO>> doCreateNonBalanceOffsetItemByFetchConfig() {
        ArrayList<List<GcOffSetVchrItemDTO>> offetItems = new ArrayList<List<GcOffSetVchrItemDTO>>();
        ArrayList<FlexibleFetchConfig.Item> debitFetchNumSettingItems = this.fetchConfig.getDebitConfigList() == null ? new ArrayList<FlexibleFetchConfig.Item>() : new ArrayList(this.fetchConfig.getDebitConfigList());
        this.debitBalanceFetchNumSettingItems = this.removeBalanceFetchNumSetting(debitFetchNumSettingItems);
        ArrayList<FlexibleFetchConfig.Item> creditFetchNumSettingItems = this.fetchConfig.getCreditConfigList() == null ? new ArrayList<FlexibleFetchConfig.Item>() : new ArrayList(this.fetchConfig.getCreditConfigList());
        this.creditBalanceFetchNumSettingItems = this.removeBalanceFetchNumSetting(creditFetchNumSettingItems);
        this.debitSubjectAllocationFetchNumSettingItems = this.removeSubjectAllocationFetchNumSetting(debitFetchNumSettingItems);
        this.creditSubjectAllocationFetchNumSettingItems = this.removeSubjectAllocationFetchNumSetting(creditFetchNumSettingItems);
        List<GcOffSetVchrItemDTO> debitOffsetItems = this.createOffsetItemByFetchNumSettingItems(debitFetchNumSettingItems, OrientEnum.D);
        List<GcOffSetVchrItemDTO> creditOffsetItems = this.createOffsetItemByFetchNumSettingItems(creditFetchNumSettingItems, OrientEnum.C);
        offetItems.add(debitOffsetItems);
        offetItems.add(creditOffsetItems);
        return offetItems;
    }

    private List<FlexibleFetchConfig.Item> removeBalanceFetchNumSetting(List<FlexibleFetchConfig.Item> fetchNumSettingItems) {
        List<FlexibleFetchConfig.Item> items = fetchNumSettingItems.stream().filter(OffsetItemCreateHelper::isBalanceSetting).collect(Collectors.toList());
        fetchNumSettingItems.removeAll(items);
        return items;
    }

    static boolean isBalanceSetting(FlexibleFetchConfig.Item item) {
        String formula = item.getFetchFormula();
        return StringUtils.hasLength(formula) && formula.toUpperCase().contains("PHS()");
    }

    private List<FlexibleFetchConfig.Item> removeSubjectAllocationFetchNumSetting(List<FlexibleFetchConfig.Item> fetchNumSettingItems) {
        List<FlexibleFetchConfig.Item> items = fetchNumSettingItems.stream().filter(OffsetItemCreateHelper::isSubjectAllocationFormulaSetting).collect(Collectors.toList());
        fetchNumSettingItems.removeAll(items);
        return items;
    }

    static boolean isSubjectAllocationFormulaSetting(FlexibleFetchConfig.Item item) {
        String formula = item.getFetchFormula();
        return StringUtils.hasLength(formula) && formula.toUpperCase().contains("SUBJECTALLOCATION");
    }

    private List<GcOffSetVchrItemDTO> createOffsetItemByFetchNumSettingItems(List<FlexibleFetchConfig.Item> fetchNumSettingItems, OrientEnum createOrient) {
        List<InputDataEO> allInputItems = this.recordMap.get(ALLLIST);
        ArrayList<GcOffSetVchrItemDTO> offsetVchrItems = new ArrayList<GcOffSetVchrItemDTO>();
        if (CollectionUtils.isEmpty(fetchNumSettingItems) || CollectionUtils.isEmpty(allInputItems)) {
            return offsetVchrItems;
        }
        fetchNumSettingItems.forEach(fetchNumSettingItem -> {
            FetchTypeEnum fetchType = fetchNumSettingItem.getFetchType();
            List<InputDataEO> srcTraList = this.getListForTra(fetchType);
            List<InputDataEO> srcFormualList = this.getListForFormual(fetchType);
            if (CollectionUtils.isEmpty(srcTraList) && CollectionUtils.isEmpty(srcFormualList)) {
                return;
            }
            if (fetchType == FetchTypeEnum.DEBIT_SUM || fetchType == FetchTypeEnum.CREDIT_SUM || fetchType == FetchTypeEnum.SUM) {
                FetchResult fetchResult = this.fetchSum(srcFormualList, (FlexibleFetchConfig.Item)fetchNumSettingItem);
                if (!fetchResult.needCreateOffsetItem) {
                    return;
                }
                List<GcOffSetVchrItemDTO> items = this.createSumOffsetItems((FlexibleFetchConfig.Item)fetchNumSettingItem, fetchResult.amt, createOrient);
                if (!CollectionUtils.isEmpty(items)) {
                    offsetVchrItems.addAll(items);
                }
            } else {
                if (CollectionUtils.isEmpty(srcTraList)) {
                    return;
                }
                srcTraList.forEach(inputItem -> {
                    FetchResult fetchResult = this.fetchItem((InputDataEO)((Object)((Object)inputItem)), (FlexibleFetchConfig.Item)fetchNumSettingItem, srcFormualList);
                    if (!fetchResult.needCreateOffsetItem) {
                        return;
                    }
                    GcOffSetVchrItemDTO offsetVchrItem = this.createOffsetItem((InputDataEO)((Object)((Object)inputItem)), fetchResult.amt, createOrient, (FlexibleFetchConfig.Item)fetchNumSettingItem);
                    if (offsetVchrItem != null) {
                        this.setOffsetItemDimFieldValue(this.getDimensions(fetchNumSettingItem.getDimensions()), offsetVchrItem, (InputDataEO)((Object)((Object)inputItem)), srcFormualList);
                        offsetVchrItems.add(offsetVchrItem);
                    }
                });
            }
        });
        return offsetVchrItems;
    }

    private List<InputDataEO> getListForTra(FetchTypeEnum fetchType) {
        if (fetchType == FetchTypeEnum.DEBIT_DETAIL) {
            return this.recordMap.get(OrientEnum.D.getCode());
        }
        if (fetchType == FetchTypeEnum.CREDIT_DETAIL) {
            return this.recordMap.get(OrientEnum.C.getCode());
        }
        if (fetchType == FetchTypeEnum.ALL_DETAIL) {
            return this.recordMap.get(ALLLIST);
        }
        return Collections.emptyList();
    }

    private List<InputDataEO> getListForFormual(FetchTypeEnum fetchType) {
        if (fetchType == FetchTypeEnum.DEBIT_SUM) {
            return this.recordMap.get(OrientEnum.D.getCode());
        }
        if (fetchType == FetchTypeEnum.CREDIT_SUM) {
            return this.recordMap.get(OrientEnum.C.getCode());
        }
        if (fetchType == FetchTypeEnum.ALL_DETAIL || fetchType == FetchTypeEnum.SUM || fetchType == FetchTypeEnum.DEBIT_DETAIL || fetchType == FetchTypeEnum.CREDIT_DETAIL) {
            return this.recordMap.get(ALLLIST);
        }
        return Collections.emptyList();
    }

    private FetchResult fetchSum(List<InputDataEO> srcList, FlexibleFetchConfig.Item fetchNumSettingItem) {
        FetchResult fetchResult = new FetchResult();
        if (CollectionUtils.isEmpty(srcList)) {
            fetchResult.needCreateOffsetItem = false;
            return fetchResult;
        }
        String formula = fetchNumSettingItem.getFetchFormula();
        String subjectCode = fetchNumSettingItem.getSubjectCode();
        if (ObjectUtils.isEmpty(formula)) {
            double amtSum = 0.0;
            for (InputDataEO inputItem : srcList) {
                if (!subjectCode.equals(inputItem.getSubjectCode())) continue;
                amtSum += inputItem.getAmt().doubleValue();
            }
            if (Math.abs(amtSum) <= 0.001) {
                fetchResult.needCreateOffsetItem = false;
                return fetchResult;
            }
            fetchResult.needCreateOffsetItem = true;
            fetchResult.amt = amtSum;
            return fetchResult;
        }
        DimensionValueSet dimensionValueSet = DimensionUtils.generateDimSet((Object)this.calcArgments.getOrgId(), (Object)this.calcArgments.getPeriodStr(), (Object)this.calcArgments.getCurrency(), (Object)this.orgType, (String)this.calcArgments.getSelectAdjustCode(), (String)this.calcArgments.getTaskId());
        fetchResult.amt = this.inputDataFormulaEvalService.evaluateSumInputData(dimensionValueSet, formula, this.listInputData(formula, srcList), this.alreadyOffSetVchrItems);
        fetchResult.needCreateOffsetItem = fetchResult.amt != 0.0;
        return fetchResult;
    }

    private FetchResult fetchItem(InputDataEO inputDataItem, FlexibleFetchConfig.Item fetchNumSettingItem, List<InputDataEO> inputDatas) {
        FetchResult result = new FetchResult();
        String formula = fetchNumSettingItem.getFetchFormula();
        if (ObjectUtils.isEmpty(formula)) {
            if (inputDataItem.getSubjectCode().equals(fetchNumSettingItem.getSubjectCode())) {
                result.needCreateOffsetItem = true;
                result.amt = inputDataItem.getAmt();
                return result;
            }
            result.needCreateOffsetItem = false;
        } else {
            DimensionValueSet dimensionValueSet = DimensionUtils.generateDimSet((Object)this.calcArgments.getOrgId(), (Object)this.calcArgments.getPeriodStr(), (Object)this.calcArgments.getCurrency(), (Object)this.orgType, (String)this.calcArgments.getSelectAdjustCode(), (String)this.calcArgments.getTaskId());
            result.amt = this.inputDataFormulaEvalService.evaluateMxInputData(dimensionValueSet, formula, inputDataItem, this.listInputData(formula, inputDatas), this.alreadyOffSetVchrItems);
            result.needCreateOffsetItem = result.amt != 0.0;
        }
        return result;
    }

    private double getDebitAndCreditDiff(List<GcOffSetVchrItemDTO> debitList, List<GcOffSetVchrItemDTO> creditList) {
        double diff = 0.0;
        for (GcOffSetVchrItemDTO gcOffSetVchrItemDTO : debitList) {
            diff += gcOffSetVchrItemDTO.getDebit().doubleValue();
        }
        for (GcOffSetVchrItemDTO gcOffSetVchrItemDTO : creditList) {
            diff -= gcOffSetVchrItemDTO.getCredit().doubleValue();
        }
        return diff;
    }

    List<GcOffSetVchrItemDTO> createBalanceOffsetItem(List<InputDataEO> inputItems, FlexibleFetchConfig fetchConfig, List<FlexibleFetchConfig.Item> balanceFetchNumSettingItems, double srcDebitCreditDiff, OrientEnum createOrient) {
        this.recordMap = this.groupByDc(inputItems);
        this.fetchConfig = fetchConfig;
        this.offsetGroupId = "";
        this.mrecid = "";
        return this.doCreateBalanceOffsetItem(balanceFetchNumSettingItems, srcDebitCreditDiff, createOrient);
    }

    private List<GcOffSetVchrItemDTO> doCreateBalanceOffsetItem(List<FlexibleFetchConfig.Item> balanceFetchNumSettingItems, double debitAndCreditDiff, OrientEnum createOrient) {
        if (CollectionUtils.isEmpty(balanceFetchNumSettingItems)) {
            return null;
        }
        double balanceAmt = this.getBalanceAmt(debitAndCreditDiff, createOrient);
        if (balanceAmt == 0.0) {
            return null;
        }
        FlexibleFetchConfig.Item balanceFetchItem = this.getMatchedBalanceItem(balanceFetchNumSettingItems, debitAndCreditDiff);
        if (balanceFetchItem == null) {
            return null;
        }
        List<GcOffSetVchrItemDTO> offsetItems = this.createSumOffsetItems(balanceFetchItem, balanceAmt, createOrient);
        offsetItems.forEach(offsetItem -> {
            offsetItem.setOffSetSrcType(OffSetSrcTypeEnum.PHS);
            offsetItem.setMemo("\u5e73\u8861\u6570");
        });
        return offsetItems;
    }

    private List<GcOffSetVchrItemDTO> createSumOffsetItems(FlexibleFetchConfig.Item fetchItem, double amt, OrientEnum createOrient) {
        List<InputDataEO> srcFormualList;
        Map<String, String> dimensions = this.getDimensions(fetchItem.getDimensions());
        ArrayList<GcOffSetVchrItemDTO> offsetItems = new ArrayList<GcOffSetVchrItemDTO>();
        List<InputDataEO> list = srcFormualList = CollectionUtils.isEmpty((Collection)this.recordMap.get(createOrient.getCode())) ? this.getListForFormual(fetchItem.getFetchType()) : this.recordMap.get(createOrient.getCode());
        if (CollectionUtils.isEmpty(srcFormualList)) {
            return offsetItems;
        }
        InputDataEO srcInputItem = srcFormualList.get(0);
        if (CollectionUtils.isEmpty(dimensions)) {
            GcOffSetVchrItemDTO offsetVchrItem = this.createOffsetItem(srcInputItem, amt, createOrient, fetchItem);
            offsetItems.add(offsetVchrItem);
            return offsetItems;
        }
        if (FetchTypeEnum.isSum((FetchTypeEnum)fetchItem.getFetchType())) {
            if (!CollectionUtils.isEmpty(dimensions)) {
                ArrayList<List<InputDataEO>> inputDatas = new ArrayList<List<InputDataEO>>();
                ArrayList<String> dimNames = new ArrayList<String>();
                boolean isExistRate = this.isExistRate(dimensions, fetchItem.getFetchType(), inputDatas, dimNames);
                if (isExistRate) {
                    this.createOffsetIteBmyAmtRate(inputDatas, dimNames, dimensions, fetchItem, amt, 1.0, createOrient, offsetItems, 0, new HashMap<String, Object>());
                    return offsetItems;
                }
                GcOffSetVchrItemDTO offsetVchrItem = this.createOffsetItem(srcInputItem, amt, createOrient, fetchItem);
                for (String dimName : dimensions.keySet()) {
                    FetchTypeEnum fetchType;
                    List<InputDataEO> listForFormual;
                    String dimValue = dimensions.get(dimName);
                    if (!StringUtils.hasText(dimName) || dimName.endsWith("_CUSTOMIZE_FORMULA") || !StringUtils.hasText(dimValue)) continue;
                    FlexibleDimSrcTypeEnum dimSrcTypeEnum = FlexibleDimSrcTypeEnum.valueOf((String)dimValue);
                    if (Objects.nonNull(dimSrcTypeEnum) && FlexibleDimSrcTypeEnum.CUSTOMIZE_FORMULA.name().equals(dimValue)) {
                        String dimFormula = dimensions.get(dimName + "_CUSTOMIZE_FORMULA");
                        if (!StringUtils.hasText(dimFormula)) continue;
                        DimensionValueSet dimensionValueSet = DimensionUtils.generateDimSet((Object)this.calcArgments.getOrgId(), (Object)this.calcArgments.getPeriodStr(), (Object)this.calcArgments.getCurrency(), (Object)this.orgType, (String)this.calcArgments.getSelectAdjustCode(), (String)this.calcArgments.getTaskId());
                        AbstractData abstractData = this.inputDataFormulaEvalService.evaluateMxInputAbstractData(dimensionValueSet, dimFormula, srcInputItem, srcFormualList);
                        Object fieldValue = null;
                        fieldValue = abstractData.dataType == 3 ? Double.valueOf(GcAbstractData.getDoubleValue((AbstractData)abstractData)) : GcAbstractData.getStringValue((AbstractData)abstractData);
                        OffsetItemCreateHelper.setOffsetVchrItemFieldValue(offsetVchrItem, dimName, fieldValue);
                        continue;
                    }
                    if (FlexibleDimSrcTypeEnum.NONE.name().equals(dimValue) || CollectionUtils.isEmpty(listForFormual = this.getListForFormual(fetchType = Objects.nonNull(dimSrcTypeEnum) ? FlexibleDimSrcTypeEnum.toFetchType((FlexibleDimSrcTypeEnum)dimSrcTypeEnum) : fetchItem.getFetchType()))) continue;
                    OffsetItemCreateHelper.setOffsetVchrItemFieldValue(offsetVchrItem, dimName, listForFormual.get(0).getFieldValue(dimName.toUpperCase()));
                }
                offsetItems.add(offsetVchrItem);
            }
        } else {
            GcOffSetVchrItemDTO offsetVchrItem = this.createOffsetItem(srcInputItem, amt, createOrient, fetchItem);
            this.setOffsetItemDimFieldValue(dimensions, offsetVchrItem, srcInputItem, srcFormualList);
            offsetItems.add(offsetVchrItem);
        }
        return offsetItems;
    }

    private void createOffsetIteBmyAmtRate(List<List<InputDataEO>> inputDatas, List<String> dimNames, Map<String, String> dimensions, FlexibleFetchConfig.Item fetchItem, double amt, double offsetAmt, OrientEnum createOrient, List<GcOffSetVchrItemDTO> offsetItems, int index, Map<String, Object> dimFieldValue) {
        List<InputDataEO> inputDataItems = inputDatas.get(index);
        double sumAmt = this.getSrcSum(inputDataItems);
        if (sumAmt == 0.0) {
            return;
        }
        for (InputDataEO inputData : inputDataItems) {
            String dimName = dimNames.get(index);
            String dimValue = dimensions.get(dimName);
            FlexibleDimSrcTypeEnum dimSrcTypeEnum = FlexibleDimSrcTypeEnum.valueOf((String)dimValue);
            Object fieldValue = null;
            if (Objects.nonNull(dimSrcTypeEnum) && FlexibleDimSrcTypeEnum.CUSTOMIZE_FORMULA.name().equals(dimValue)) {
                String dimFormula = dimensions.get(dimName + "_CUSTOMIZE_FORMULA");
                if (StringUtils.hasText(dimFormula)) {
                    DimensionValueSet dimensionValueSet = DimensionUtils.generateDimSet((Object)this.calcArgments.getOrgId(), (Object)this.calcArgments.getPeriodStr(), (Object)this.calcArgments.getCurrency(), (Object)this.orgType, (String)this.calcArgments.getSelectAdjustCode(), (String)this.calcArgments.getTaskId());
                    AbstractData abstractData = this.inputDataFormulaEvalService.evaluateMxInputAbstractData(dimensionValueSet, dimFormula, inputData, inputDataItems);
                    fieldValue = abstractData.dataType == 3 ? Double.valueOf(GcAbstractData.getDoubleValue((AbstractData)abstractData)) : GcAbstractData.getStringValue((AbstractData)abstractData);
                }
            } else if (!FlexibleDimSrcTypeEnum.NONE.name().equals(dimValue)) {
                fieldValue = inputData.getFieldValue(dimName.toUpperCase());
            }
            dimFieldValue.put(dimName, fieldValue);
            double newOffsetAmt = offsetAmt * inputData.getAmt() / sumAmt;
            if (dimNames.size() == index + 1) {
                GcOffSetVchrItemDTO offsetVchrItem = this.createOffsetItem(inputData, OffsetVchrItemNumberUtils.round((double)(newOffsetAmt * amt)), createOrient, fetchItem);
                for (String dim : dimFieldValue.keySet()) {
                    OffsetItemCreateHelper.setOffsetVchrItemFieldValue(offsetVchrItem, dim, dimFieldValue.get(dim));
                }
                offsetItems.add(offsetVchrItem);
            }
            if (index + 1 >= dimNames.size()) continue;
            this.createOffsetIteBmyAmtRate(inputDatas, dimNames, dimensions, fetchItem, amt, newOffsetAmt, createOrient, offsetItems, index + 1, dimFieldValue);
        }
    }

    private void setOffsetItemDimFieldValue(Map<String, String> dimensions, GcOffSetVchrItemDTO offsetVchrItem, InputDataEO srcInputItem, List<InputDataEO> srcInputDatas) {
        if (CollectionUtils.isEmpty(dimensions)) {
            return;
        }
        for (String dimName : dimensions.keySet()) {
            if (!StringUtils.hasText(dimName) || dimName.endsWith("_CUSTOMIZE_FORMULA")) continue;
            String dimValue = dimensions.get(dimName);
            if (StringUtils.hasText(dimValue) && FlexibleDimSrcTypeEnum.CUSTOMIZE_FORMULA.name().equals(dimValue)) {
                String dimFormula = dimensions.get(dimName + "_CUSTOMIZE_FORMULA");
                if (!StringUtils.hasText(dimFormula)) continue;
                DimensionValueSet dimensionValueSet = DimensionUtils.generateDimSet((Object)this.calcArgments.getOrgId(), (Object)this.calcArgments.getPeriodStr(), (Object)this.calcArgments.getCurrency(), (Object)this.orgType, (String)this.calcArgments.getSelectAdjustCode(), (String)this.calcArgments.getTaskId());
                AbstractData abstractData = this.inputDataFormulaEvalService.evaluateMxInputAbstractData(dimensionValueSet, dimFormula, srcInputItem, srcInputDatas);
                Object fieldValue = null;
                if (Objects.nonNull(abstractData)) {
                    fieldValue = abstractData.dataType == 3 ? Double.valueOf(GcAbstractData.getDoubleValue((AbstractData)abstractData)) : GcAbstractData.getStringValue((AbstractData)abstractData);
                }
                OffsetItemCreateHelper.setOffsetVchrItemFieldValue(offsetVchrItem, dimName, fieldValue);
                continue;
            }
            if (FlexibleDimSrcTypeEnum.NONE.name().equals(dimValue)) continue;
            OffsetItemCreateHelper.setOffsetVchrItemFieldValueByInputData(offsetVchrItem, srcInputItem, dimName.toUpperCase());
        }
    }

    private boolean isExistRate(Map<String, String> dimensions, FetchTypeEnum fetchTypeEnum, List<List<InputDataEO>> inputDatas, List<String> dimNames) {
        boolean isExistRate = false;
        for (String dimName : dimensions.keySet()) {
            List<InputDataEO> inputDataItems;
            FlexibleDimSrcTypeEnum dimSrcTypeEnum;
            String dimValue = dimensions.get(dimName);
            if (!StringUtils.hasText(dimName) || dimName.endsWith("_CUSTOMIZE_FORMULA") || !StringUtils.hasText(dimValue) || null == (dimSrcTypeEnum = FlexibleDimSrcTypeEnum.valueOf((String)dimValue)) || FlexibleDimSrcTypeEnum.NONE.equals((Object)dimSrcTypeEnum)) continue;
            if (FlexibleDimSrcTypeEnum.CREDIT_RATE.equals((Object)dimSrcTypeEnum) || FlexibleDimSrcTypeEnum.DEBIT_RATE.equals((Object)dimSrcTypeEnum) || FlexibleDimSrcTypeEnum.ALL_RATE.equals((Object)dimSrcTypeEnum)) {
                isExistRate = true;
            }
            if (CollectionUtils.isEmpty(inputDataItems = this.listInputDataItems(dimSrcTypeEnum, fetchTypeEnum))) continue;
            dimNames.add(dimName);
            inputDatas.add(inputDataItems);
        }
        return isExistRate;
    }

    private Map<String, String> getDimensions(Map<String, String> dimensions) {
        if (CollectionUtils.isEmpty(dimensions)) {
            TransferService transferService = (TransferService)SpringContextUtils.getBean(TransferService.class);
            ConsolidatedTaskService taskService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
            assert (taskService != null);
            String reportSystemId = taskService.getSystemIdBySchemeId(this.calcArgments.getSchemeId(), this.calcArgments.getPeriodStr());
            if (reportSystemId == null) {
                return null;
            }
            String path = "unionRule_" + reportSystemId;
            List dimColumns = transferService.getSelectColumnsByPath(path, true);
            if (CollectionUtils.isEmpty(dimColumns)) {
                return null;
            }
            dimensions = new HashMap<String, String>();
            for (String dim : dimColumns) {
                dimensions.put(dim, null);
            }
            return dimensions;
        }
        return dimensions;
    }

    private FlexibleFetchConfig.Item getMatchedBalanceItem(List<FlexibleFetchConfig.Item> balanceFetchNumSettingItems, double phsValue) {
        for (FlexibleFetchConfig.Item balanceFetchNumSettingItem : balanceFetchNumSettingItems) {
            DimensionValueSet dimensionValueSet;
            double result;
            String formula = balanceFetchNumSettingItem.getFetchFormula();
            if (ObjectUtils.isEmpty(formula)) {
                return null;
            }
            List<InputDataEO> srcFormualList = this.getListForFormual(balanceFetchNumSettingItem.getFetchType());
            if (CollectionUtils.isEmpty(srcFormualList) || NumberUtils.isZreo((Double)(result = this.inputDataFormulaEvalService.evaluateInputDataPHS(dimensionValueSet = DimensionUtils.generateDimSet((Object)this.calcArgments.getOrgId(), (Object)this.calcArgments.getPeriodStr(), (Object)this.calcArgments.getCurrency(), (Object)this.orgType, (String)this.calcArgments.getSelectAdjustCode(), (String)this.calcArgments.getTaskId()), formula, srcFormualList, phsValue)))) continue;
            return balanceFetchNumSettingItem;
        }
        return null;
    }

    private double getSrcSum(List<InputDataEO> inputItems) {
        double srcSum = 0.0;
        for (InputDataEO inputItem : inputItems) {
            srcSum += inputItem.getAmt().doubleValue();
        }
        return OffsetVchrItemNumberUtils.round((double)srcSum);
    }

    private List<InputDataEO> listInputDataItems(FlexibleDimSrcTypeEnum dimSrcTypeEnum, FetchTypeEnum fetchType) {
        ArrayList<InputDataEO> inputDataItems = new ArrayList<InputDataEO>();
        if (null == dimSrcTypeEnum || FlexibleDimSrcTypeEnum.CUSTOMIZE_FORMULA.equals((Object)dimSrcTypeEnum)) {
            List<InputDataEO> listForFormual = this.getListForFormual(fetchType);
            if (!CollectionUtils.isEmpty(listForFormual)) {
                inputDataItems.add(listForFormual.get(0));
            }
        } else {
            FetchTypeEnum fetchTypeEnum = FlexibleDimSrcTypeEnum.toFetchType((FlexibleDimSrcTypeEnum)dimSrcTypeEnum);
            List<InputDataEO> listForFormual = this.getListForFormual(fetchTypeEnum);
            if (CollectionUtils.isEmpty(listForFormual)) {
                return inputDataItems;
            }
            if (FlexibleDimSrcTypeEnum.DEBIT.equals((Object)dimSrcTypeEnum) || FlexibleDimSrcTypeEnum.CREDIT.equals((Object)dimSrcTypeEnum)) {
                inputDataItems.add(listForFormual.get(0));
            } else if (FlexibleDimSrcTypeEnum.ALL_RATE.equals((Object)dimSrcTypeEnum) || FlexibleDimSrcTypeEnum.DEBIT_RATE.equals((Object)dimSrcTypeEnum) || FlexibleDimSrcTypeEnum.CREDIT_RATE.equals((Object)dimSrcTypeEnum)) {
                inputDataItems.addAll(listForFormual);
            }
        }
        return inputDataItems;
    }

    private double getBalanceAmt(double debitAndCreditDiff, OrientEnum createOrient) {
        return createOrient == OrientEnum.D ? -debitAndCreditDiff : debitAndCreditDiff;
    }

    GcOffSetVchrItemDTO createOffsetItem(InputDataEO srcInputItem, Double amt, OrientEnum createOrient, FlexibleFetchConfig.Item fetchItem) {
        String oppUnitId;
        String unitId;
        String convertedOppUnitId;
        if (srcInputItem == null) {
            return null;
        }
        GcOffSetVchrItemDTO offsetVchrItem = new GcOffSetVchrItemDTO();
        offsetVchrItem.setId(UUID.randomUUID().toString());
        offsetVchrItem.setRecver(Long.valueOf(0L));
        offsetVchrItem.setmRecid(this.mrecid);
        offsetVchrItem.setAcctYear(this.calcArgments.getAcctYear());
        offsetVchrItem.setAcctPeriod(this.calcArgments.getAcctPeriod());
        offsetVchrItem.setDefaultPeriod(this.calcArgments.getPeriodStr());
        ConsolidatedTaskService taskService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
        assert (taskService != null);
        String reportSystemId = taskService.getSystemIdBySchemeId(this.calcArgments.getSchemeId(), this.calcArgments.getPeriodStr());
        if (reportSystemId == null) {
            return null;
        }
        String convertedUnitId = srcInputItem.getConvertOffsetOrgFlag() != false ? srcInputItem.getOffsetOrgCode() : srcInputItem.getUnitId();
        String string = convertedOppUnitId = srcInputItem.getConvertOffsetOrgFlag() != false ? srcInputItem.getOffsetOppUnitId() : srcInputItem.getOppUnitId();
        if (FetchTypeEnum.isSum((FetchTypeEnum)fetchItem.getFetchType())) {
            if (!FetchTypeEnum.SUM.equals((Object)fetchItem.getFetchType())) {
                if (srcInputItem.getDc() == OrientEnum.D.getValue() && FetchTypeEnum.DEBIT_SUM.equals((Object)fetchItem.getFetchType()) || srcInputItem.getDc() == OrientEnum.C.getValue() && FetchTypeEnum.CREDIT_SUM.equals((Object)fetchItem.getFetchType())) {
                    unitId = convertedUnitId;
                    oppUnitId = convertedOppUnitId;
                } else {
                    unitId = convertedOppUnitId;
                    oppUnitId = convertedUnitId;
                }
            } else if (srcInputItem.getDc() == createOrient.getValue()) {
                unitId = convertedUnitId;
                oppUnitId = convertedOppUnitId;
            } else {
                unitId = convertedOppUnitId;
                oppUnitId = convertedUnitId;
            }
        } else {
            unitId = convertedUnitId;
            oppUnitId = convertedOppUnitId;
        }
        offsetVchrItem.setSchemeId(this.calcArgments.getSchemeId());
        offsetVchrItem.setTaskId(this.calcArgments.getTaskId());
        offsetVchrItem.setSystemId(reportSystemId);
        offsetVchrItem.setFormId(srcInputItem.getFormId());
        offsetVchrItem.setUnitId(unitId);
        offsetVchrItem.setOppUnitId(oppUnitId);
        offsetVchrItem.setSubjectCode(fetchItem.getSubjectCode());
        offsetVchrItem.setSortOrder(Double.valueOf(0.0));
        offsetVchrItem.setDebit(Double.valueOf(createOrient == OrientEnum.D ? OffsetVchrItemNumberUtils.round((Double)amt) : 0.0));
        offsetVchrItem.setCredit(Double.valueOf(createOrient == OrientEnum.C ? OffsetVchrItemNumberUtils.round((Double)amt) : 0.0));
        offsetVchrItem.setOffSetCurr(this.calcArgments.getCurrency());
        offsetVchrItem.setOffSetDebit(offsetVchrItem.getDebit());
        offsetVchrItem.setOffSetCredit(offsetVchrItem.getCredit());
        offsetVchrItem.setDiffd(Double.valueOf(0.0));
        offsetVchrItem.setDiffc(Double.valueOf(0.0));
        offsetVchrItem.setElmMode(Integer.valueOf(OffsetElmModeEnum.AUTO_ITEM.getValue()));
        offsetVchrItem.setChkState("1".equals(srcInputItem.getCheckState()) ? CheckStateEnum.CHECKED.getCode() : CheckStateEnum.UNCHECKED.getCode());
        offsetVchrItem.setRuleId(this.rule.getId());
        offsetVchrItem.setCreateTime(new Date());
        offsetVchrItem.setOffSetSrcType(OffSetSrcTypeEnum.CONSOLIDATE);
        offsetVchrItem.setSrcId(FetchTypeEnum.isSum((FetchTypeEnum)fetchItem.getFetchType()) ? null : srcInputItem.getId());
        offsetVchrItem.setGcBusinessTypeCode(OffsetItemCreateHelper.getBussinessTypeId(this.fetchConfig, this.rule));
        offsetVchrItem.setFetchSetGroupId(this.fetchConfig == null ? null : this.fetchConfig.getFetchSetGroupId());
        offsetVchrItem.setSrcOffsetGroupId(this.offsetGroupId);
        offsetVchrItem.setSelectAdjustCode(this.calcArgments.getSelectAdjustCode());
        offsetVchrItem.addFieldValue("ADJUST", (Object)this.calcArgments.getSelectAdjustCode());
        offsetVchrItem.setOrgType(GCOrgTypeEnum.NONE.getCode());
        offsetVchrItem.setOrient(createOrient);
        if (this.manualOffset) {
            offsetVchrItem.setElmMode(Integer.valueOf(OffsetElmModeEnum.MANUAL_ITEM.getValue()));
        }
        if (this.batchManualOffset) {
            offsetVchrItem.setElmMode(Integer.valueOf(OffsetElmModeEnum.BATCH_MANUAL_ITEM.getValue()));
        }
        if (FetchTypeEnum.isSum((FetchTypeEnum)fetchItem.getFetchType()) && CollectionUtils.isEmpty(fetchItem.getDimensions()) && RuleTypeEnum.RELATE_TRANSACTIONS.getCode().equals(this.rule.getRuleType())) {
            return offsetVchrItem;
        }
        OffsetItemCreateHelper.setDimValues(offsetVchrItem, srcInputItem);
        return offsetVchrItem;
    }

    private static String getBussinessTypeId(FlexibleFetchConfig fetchNumSetting, AbstractUnionRule rule) {
        if (fetchNumSetting == null || ObjectUtils.isEmpty(fetchNumSetting.getBusinessTypeCode())) {
            return rule.getBusinessTypeCode();
        }
        return fetchNumSetting.getBusinessTypeCode();
    }

    private static void setDimValues(GcOffSetVchrItemDTO offsetVchrItem, InputDataEO refInputItem) {
        ManagementDimensionCacheService managementDimensionCacheService = (ManagementDimensionCacheService)SpringContextUtils.getBean(ManagementDimensionCacheService.class);
        assert (managementDimensionCacheService != null);
        List managementDims = managementDimensionCacheService.getOptionalManagementDims();
        if (CollectionUtils.isEmpty(managementDims)) {
            return;
        }
        managementDims.forEach(managementDim -> OffsetItemCreateHelper.setOffsetVchrItemFieldValueByInputData(offsetVchrItem, refInputItem, managementDim.getCode().toUpperCase()));
    }

    private static void setOffsetVchrItemFieldValueByInputData(GcOffSetVchrItemDTO offsetVchrItem, InputDataEO refInputItem, String fieldName) {
        Object fieldValue = refInputItem.getFieldValue("PROJECTTITLE".equals(fieldName) ? "PROJECTCODE" : fieldName);
        OffsetItemCreateHelper.setOffsetVchrItemFieldValue(offsetVchrItem, fieldName, fieldValue);
    }

    private static void setOffsetVchrItemFieldValue(GcOffSetVchrItemDTO offsetVchrItem, String fieldName, Object fieldValue) {
        offsetVchrItem.addFieldValue(fieldName, fieldValue);
        try {
            Optional<PropertyDescriptor> matchedProperty = Stream.of(Introspector.getBeanInfo(GcOffSetVchrItemDTO.class).getPropertyDescriptors()).filter(property -> fieldName.equals(property.getName().toUpperCase())).findAny();
            if (matchedProperty.isPresent()) {
                matchedProperty.get().getWriteMethod().invoke(offsetVchrItem, fieldValue);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private List<GcOffSetVchrItemDTO> getSubjectAllocationOffsetItems(List<GcOffSetVchrItemDTO> debitOffsetItems, List<GcOffSetVchrItemDTO> creditOffsetItems, double debitAndCreditDiff) {
        List<GcOffSetVchrItemDTO> subjectAllocationOffsetItems = null;
        if (Math.abs(debitAndCreditDiff) > 0.001) {
            subjectAllocationOffsetItems = this.doCreateSubjectAllocationOffsetItems(this.debitSubjectAllocationFetchNumSettingItems, debitAndCreditDiff, OrientEnum.D);
            if (!CollectionUtils.isEmpty(subjectAllocationOffsetItems)) {
                debitOffsetItems.addAll(subjectAllocationOffsetItems);
            } else {
                subjectAllocationOffsetItems = this.doCreateSubjectAllocationOffsetItems(this.creditSubjectAllocationFetchNumSettingItems, debitAndCreditDiff, OrientEnum.C);
                if (!CollectionUtils.isEmpty(subjectAllocationOffsetItems)) {
                    creditOffsetItems.addAll(subjectAllocationOffsetItems);
                }
            }
        }
        return subjectAllocationOffsetItems;
    }

    private List<GcOffSetVchrItemDTO> doCreateSubjectAllocationOffsetItems(List<FlexibleFetchConfig.Item> subjectAllocationFetchNumSettingItems, double debitAndCreditDiff, OrientEnum createOrient) {
        if (CollectionUtils.isEmpty(subjectAllocationFetchNumSettingItems)) {
            return null;
        }
        double subjectAllocationAmt = this.getBalanceAmt(debitAndCreditDiff, createOrient);
        if (subjectAllocationAmt == 0.0) {
            return null;
        }
        HashMap<String, Double> subjectAllocationValues = new HashMap<String, Double>();
        FlexibleFetchConfig.Item subjectAllocationFetchItem = this.getMatchedSubjectAllocationItem(subjectAllocationFetchNumSettingItems, subjectAllocationValues, createOrient);
        if (subjectAllocationFetchItem == null) {
            return null;
        }
        ArrayList<GcOffSetVchrItemDTO> offSetVchrItems = new ArrayList<GcOffSetVchrItemDTO>();
        int i = 0;
        for (Map.Entry entry : subjectAllocationValues.entrySet()) {
            String subjectCode = (String)entry.getKey();
            Double subjectAllocationValue = NumberUtils.parseDouble(entry.getValue());
            ++i;
            if (!StringUtils.hasText(subjectCode) || !Objects.nonNull(subjectAllocationValue)) continue;
            subjectAllocationFetchItem.setSubjectCode(subjectCode);
            double rateAmtValue = subjectAllocationAmt * subjectAllocationValue;
            if (Math.abs(rateAmtValue) <= 0.001) continue;
            if (subjectAllocationValues.size() == i) {
                rateAmtValue = this.getDiffAmtValue(offSetVchrItems, subjectAllocationAmt, createOrient);
            }
            List<GcOffSetVchrItemDTO> offsetItems = this.createSumOffsetItems(subjectAllocationFetchItem, rateAmtValue, createOrient);
            offsetItems.forEach(offsetItem -> {
                offsetItem.setOffSetSrcType(OffSetSrcTypeEnum.KMFP);
                offsetItem.setMemo("\u79d1\u76ee\u5206\u914d");
            });
            offSetVchrItems.addAll(offsetItems);
        }
        return offSetVchrItems;
    }

    private FlexibleFetchConfig.Item getMatchedSubjectAllocationItem(List<FlexibleFetchConfig.Item> subjectAllocationFetchNumSettingItems, Map<String, Double> subjectAllocationValues, OrientEnum createOrient) {
        for (FlexibleFetchConfig.Item subjectAllocationFetchNumSettingItem : subjectAllocationFetchNumSettingItems) {
            DimensionValueSet dimensionValueSet;
            String result;
            String formula = subjectAllocationFetchNumSettingItem.getFetchFormula();
            if (ObjectUtils.isEmpty(formula)) {
                return null;
            }
            List<InputDataEO> srcFormualList = this.recordMap.get(ALLLIST);
            if (CollectionUtils.isEmpty(srcFormualList) || !StringUtils.hasText(result = this.inputDataFormulaEvalService.evaluateInputDataSubjectAllocation(dimensionValueSet = DimensionUtils.generateDimSet((Object)this.calcArgments.getOrgId(), (Object)this.calcArgments.getPeriodStr(), (Object)this.calcArgments.getCurrency(), (Object)this.orgType, (String)this.calcArgments.getSelectAdjustCode(), (String)this.calcArgments.getTaskId()), formula, srcFormualList))) continue;
            Map resultObj = (Map)JsonUtils.readValue((String)result, (TypeReference)new TypeReference<Map<String, Object>>(){});
            Map rateValue = (Map)resultObj.get("rateValue");
            subjectAllocationValues.putAll(rateValue);
            FlexibleFetchConfig.Item subjectAllocationItem = new FlexibleFetchConfig.Item();
            BeanUtils.copyProperties(subjectAllocationFetchNumSettingItem, subjectAllocationItem);
            return subjectAllocationItem;
        }
        return null;
    }

    private List<InputDataEO> listInputData(String fetchFormula, List<InputDataEO> srcList) {
        if (StringUtils.hasText(fetchFormula) && fetchFormula.toUpperCase().contains("MLL") || fetchFormula.toUpperCase().contains("SUBJECTALLOCATION")) {
            return this.recordMap.get(ALLLIST);
        }
        return srcList;
    }

    private double getDiffAmtValue(List<GcOffSetVchrItemDTO> offSetVchrItems, double subjectAllocationAmt, OrientEnum createOrient) {
        double sumRateAmtValue = 0.0;
        for (GcOffSetVchrItemDTO item : offSetVchrItems) {
            if (OrientEnum.C.equals((Object)createOrient)) {
                sumRateAmtValue += item.getCredit().doubleValue();
                continue;
            }
            sumRateAmtValue += item.getDebit().doubleValue();
        }
        return OffsetVchrItemNumberUtils.round((double)(subjectAllocationAmt - sumRateAmtValue));
    }

    private String getCommonUnitOrgType() {
        YearPeriodObject yp = new YearPeriodObject(this.calcArgments.getSchemeId(), this.calcArgments.getPeriodStr());
        String orgType = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)this.calcArgments.getTaskId());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO gcOrgCacheVO = tool.getOrgByCode(this.calcArgments.getOrgId());
        return gcOrgCacheVO.getOrgTypeId();
    }

    private static class FetchResult {
        private Double amt = 0.0;
        private boolean needCreateOffsetItem = false;

        private FetchResult() {
        }
    }

    public static class CreateResult {
        private List<GcOffSetVchrItemDTO> offsetItems;
        private boolean filterFormulaSuccess;
        private boolean noDiff;

        private CreateResult() {
        }

        public List<GcOffSetVchrItemDTO> getOffsetItems() {
            return this.offsetItems;
        }

        public void setOffsetItems(List<GcOffSetVchrItemDTO> offsetItems) {
            this.offsetItems = offsetItems;
        }

        boolean isFilterFormulaSuccess() {
            return this.filterFormulaSuccess;
        }

        private void setFilterFormulaSuccess(boolean filterFormulaSuccess) {
            this.filterFormulaSuccess = filterFormulaSuccess;
        }

        boolean isNoDiff() {
            return this.noDiff;
        }

        private void setNoDiff(boolean noDiff) {
            this.noDiff = noDiff;
        }
    }
}

