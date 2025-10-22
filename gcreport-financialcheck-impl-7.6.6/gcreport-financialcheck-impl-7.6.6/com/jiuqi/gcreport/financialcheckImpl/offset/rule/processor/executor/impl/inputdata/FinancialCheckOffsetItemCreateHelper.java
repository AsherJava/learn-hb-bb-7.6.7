/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.OffsetVchrItemNumberUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.ManagementDimensionCacheService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum
 *  com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckDimSrcTypeEnum
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckFetchConfig
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckFetchConfig$Item
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO
 *  com.jiuqi.gcreport.unionrule.enums.FetchTypeEnum
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.rule.processor.executor.impl.inputdata;

import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.OffsetVchrItemNumberUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.ManagementDimensionCacheService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.service.GcFinancialCheckFormulaEvalService;
import com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum;
import com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckDimSrcTypeEnum;
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckFetchConfig;
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.FetchTypeEnum;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class FinancialCheckOffsetItemCreateHelper {
    private final FinancialCheckRuleDTO rule;
    private final GcCalcArgmentsDTO calcArgments;
    private boolean manualOffset = false;
    private boolean batchManualOffset = false;
    private final GcFinancialCheckFormulaEvalService formulaEvalService;
    private FinancialCheckFetchConfig fetchConfig;
    private Map<String, List<GcFcRuleUnOffsetDataDTO>> recordMap;
    private String offsetGroupId;
    private String mrecid;
    private List<GcFcRuleUnOffsetDataDTO> unOffsetDataS;
    private List<FinancialCheckFetchConfig.Item> debitBalanceFetchNumSettingItems;
    private List<FinancialCheckFetchConfig.Item> creditBalanceFetchNumSettingItems;
    private static final String ALLLIST = "ALL";
    private static final double ERRORRANGE = 0.001;

    public static FinancialCheckOffsetItemCreateHelper newInstance(FinancialCheckRuleDTO rule, GcCalcArgmentsDTO calcArgments) {
        FinancialCheckOffsetItemCreateHelper offsetItemCreateHelper = new FinancialCheckOffsetItemCreateHelper(rule, calcArgments);
        return offsetItemCreateHelper;
    }

    public static FinancialCheckOffsetItemCreateHelper newBatchManualInstance(FinancialCheckRuleDTO rule, GcCalcArgmentsDTO calcArgments) {
        FinancialCheckOffsetItemCreateHelper offsetItemCreateHelper = new FinancialCheckOffsetItemCreateHelper(rule, calcArgments);
        offsetItemCreateHelper.batchManualOffset = true;
        offsetItemCreateHelper.manualOffset = true;
        return offsetItemCreateHelper;
    }

    private FinancialCheckOffsetItemCreateHelper(FinancialCheckRuleDTO rule, GcCalcArgmentsDTO calcArgments) {
        this.rule = rule;
        this.calcArgments = calcArgments;
        this.formulaEvalService = (GcFinancialCheckFormulaEvalService)SpringContextUtils.getBean(GcFinancialCheckFormulaEvalService.class);
        this.unOffsetDataS = new ArrayList<GcFcRuleUnOffsetDataDTO>();
    }

    CreateResult createOffsetItemByFetchConfig(List<GcFcRuleUnOffsetDataDTO> unOffsetDataS, FinancialCheckFetchConfig fetchConfig, String offsetGroupId) {
        String filterFormula;
        this.fetchConfig = fetchConfig;
        this.offsetGroupId = offsetGroupId;
        this.mrecid = UUIDOrderUtils.newUUIDStr();
        this.recordMap = this.groupByDc(unOffsetDataS);
        CreateResult createResult = new CreateResult();
        String string = filterFormula = this.manualOffset ? fetchConfig.getManualFilterFormula() : fetchConfig.getFilterFormula();
        if (!this.checkByFilterFormula(unOffsetDataS, filterFormula)) {
            createResult.setFilterFormulaSuccess(false);
            return createResult;
        }
        createResult.setFilterFormulaSuccess(true);
        List<List<GcOffSetVchrItemDTO>> debitAndCreditOffsetItems = this.doCreateNonBalanceOffsetItemByFetchConfig();
        List<GcOffSetVchrItemDTO> debitOffsetItems = debitAndCreditOffsetItems.get(0);
        List<GcOffSetVchrItemDTO> creditOffsetItems = debitAndCreditOffsetItems.get(1);
        double debitAndCreditDiff = this.getDebitAndCreditDiff(debitOffsetItems, creditOffsetItems);
        List<GcOffSetVchrItemDTO> balanceOffsetItems = null;
        if (Math.abs(debitAndCreditDiff) > 0.001) {
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
        createResult.setNoDiff(Math.abs(debitAndCreditDiff) <= 0.001 || !CollectionUtils.isEmpty(balanceOffsetItems));
        createResult.setUnOffsetDataS(this.unOffsetDataS.stream().distinct().collect(Collectors.toList()));
        return createResult;
    }

    private boolean checkByFilterFormula(List<GcFcRuleUnOffsetDataDTO> list, String formula) {
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        if (ObjectUtils.isEmpty(formula)) {
            return true;
        }
        DimensionValueSet ds = DimensionUtils.generateDimSet((Object)this.calcArgments.getOrgId(), (Object)this.calcArgments.getPeriodStr(), (Object)this.calcArgments.getCurrency(), (Object)this.calcArgments.getOrgType(), (String)this.calcArgments.getSelectAdjustCode(), (String)this.calcArgments.getTaskId());
        return this.formulaEvalService.checkSumUnOffsetData(ds, formula, list);
    }

    private Map<String, List<GcFcRuleUnOffsetDataDTO>> groupByDc(Collection<GcFcRuleUnOffsetDataDTO> unOffsetDataS) {
        if (CollectionUtils.isEmpty(unOffsetDataS)) {
            return null;
        }
        HashMap<String, List<GcFcRuleUnOffsetDataDTO>> group = new HashMap<String, List<GcFcRuleUnOffsetDataDTO>>(16);
        ArrayList debitList = new ArrayList();
        ArrayList creditList = new ArrayList();
        group.put(OrientEnum.D.getCode(), debitList);
        group.put(OrientEnum.C.getCode(), creditList);
        group.put(ALLLIST, new ArrayList<GcFcRuleUnOffsetDataDTO>(unOffsetDataS));
        unOffsetDataS.forEach(record -> {
            if (OrientEnum.D.getValue().equals(record.getDc())) {
                debitList.add(record);
            } else if (OrientEnum.C.getValue().equals(record.getDc())) {
                creditList.add(record);
            }
        });
        return group;
    }

    private List<List<GcOffSetVchrItemDTO>> doCreateNonBalanceOffsetItemByFetchConfig() {
        ArrayList<List<GcOffSetVchrItemDTO>> offetItems = new ArrayList<List<GcOffSetVchrItemDTO>>();
        ArrayList<FinancialCheckFetchConfig.Item> debitFetchNumSettingItems = this.fetchConfig.getDebitConfigList() == null ? new ArrayList<FinancialCheckFetchConfig.Item>() : new ArrayList(this.fetchConfig.getDebitConfigList());
        this.debitBalanceFetchNumSettingItems = this.removeBalanceFetchNumSetting(debitFetchNumSettingItems);
        ArrayList<FinancialCheckFetchConfig.Item> creditFetchNumSettingItems = this.fetchConfig.getCreditConfigList() == null ? new ArrayList<FinancialCheckFetchConfig.Item>() : new ArrayList(this.fetchConfig.getCreditConfigList());
        this.creditBalanceFetchNumSettingItems = this.removeBalanceFetchNumSetting(creditFetchNumSettingItems);
        List<GcOffSetVchrItemDTO> debitOffsetItems = this.createOffsetItemByFetchNumSettingItems(debitFetchNumSettingItems, OrientEnum.D);
        List<GcOffSetVchrItemDTO> creditOffsetItems = this.createOffsetItemByFetchNumSettingItems(creditFetchNumSettingItems, OrientEnum.C);
        offetItems.add(debitOffsetItems);
        offetItems.add(creditOffsetItems);
        return offetItems;
    }

    private List<FinancialCheckFetchConfig.Item> removeBalanceFetchNumSetting(List<FinancialCheckFetchConfig.Item> fetchNumSettingItems) {
        List<FinancialCheckFetchConfig.Item> items = fetchNumSettingItems.stream().filter(FinancialCheckOffsetItemCreateHelper::isBalanceSetting).collect(Collectors.toList());
        fetchNumSettingItems.removeAll(items);
        return items;
    }

    private static boolean isBalanceSetting(FinancialCheckFetchConfig.Item item) {
        String formula = item.getFetchFormula();
        return StringUtils.hasLength(formula) && formula.toUpperCase().contains("PHS()");
    }

    private List<GcOffSetVchrItemDTO> createOffsetItemByFetchNumSettingItems(List<FinancialCheckFetchConfig.Item> fetchNumSettingItems, OrientEnum createOrient) {
        List<GcFcRuleUnOffsetDataDTO> allInputItems = this.recordMap.get(ALLLIST);
        ArrayList<GcOffSetVchrItemDTO> offsetVchrItems = new ArrayList<GcOffSetVchrItemDTO>();
        if (CollectionUtils.isEmpty(fetchNumSettingItems) || CollectionUtils.isEmpty(allInputItems)) {
            return offsetVchrItems;
        }
        fetchNumSettingItems.forEach(fetchNumSettingItem -> {
            FetchTypeEnum fetchType = fetchNumSettingItem.getFetchType();
            List<GcFcRuleUnOffsetDataDTO> srcTraList = this.getListForTra(fetchType);
            List<GcFcRuleUnOffsetDataDTO> srcFormualList = this.getListForFormual(fetchType);
            if (CollectionUtils.isEmpty(srcTraList) && CollectionUtils.isEmpty(srcFormualList)) {
                return;
            }
            if (fetchType == FetchTypeEnum.DEBIT_SUM || fetchType == FetchTypeEnum.CREDIT_SUM || fetchType == FetchTypeEnum.SUM) {
                FetchResult fetchResult = this.fetchSum(srcFormualList, (FinancialCheckFetchConfig.Item)fetchNumSettingItem);
                if (!fetchResult.needCreateOffsetItem) {
                    return;
                }
                List<GcOffSetVchrItemDTO> items = this.createSumOffsetItems((FinancialCheckFetchConfig.Item)fetchNumSettingItem, fetchResult.amt, createOrient);
                if (!CollectionUtils.isEmpty(items)) {
                    offsetVchrItems.addAll(items);
                    this.unOffsetDataS.addAll(srcFormualList);
                }
            } else {
                if (CollectionUtils.isEmpty(srcTraList)) {
                    return;
                }
                srcTraList.forEach(item -> {
                    FetchResult fetchResult = this.fetchItem((GcFcRuleUnOffsetDataDTO)item, (FinancialCheckFetchConfig.Item)fetchNumSettingItem, srcFormualList);
                    if (!fetchResult.needCreateOffsetItem) {
                        return;
                    }
                    GcOffSetVchrItemDTO offsetVchrItem = this.createOffsetItem((GcFcRuleUnOffsetDataDTO)item, fetchResult.amt, createOrient, (FinancialCheckFetchConfig.Item)fetchNumSettingItem);
                    if (offsetVchrItem != null) {
                        offsetVchrItems.add(offsetVchrItem);
                        this.unOffsetDataS.add((GcFcRuleUnOffsetDataDTO)item);
                    }
                });
            }
        });
        return offsetVchrItems;
    }

    private List<GcFcRuleUnOffsetDataDTO> getListForTra(FetchTypeEnum fetchType) {
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

    private List<GcFcRuleUnOffsetDataDTO> getListForFormual(FetchTypeEnum fetchType) {
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

    private FetchResult fetchSum(List<GcFcRuleUnOffsetDataDTO> srcList, FinancialCheckFetchConfig.Item fetchNumSettingItem) {
        FetchResult fetchResult = new FetchResult();
        if (CollectionUtils.isEmpty(srcList)) {
            fetchResult.needCreateOffsetItem = false;
            return fetchResult;
        }
        String formula = fetchNumSettingItem.getFetchFormula();
        String subjectCode = fetchNumSettingItem.getSubjectCode();
        if (ObjectUtils.isEmpty(formula)) {
            double amtSum = 0.0;
            for (GcFcRuleUnOffsetDataDTO inputItem : srcList) {
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
        DimensionValueSet dimensionValueSet = DimensionUtils.generateDimSet((Object)this.calcArgments.getOrgId(), (Object)this.calcArgments.getPeriodStr(), (Object)this.calcArgments.getCurrency(), (Object)this.calcArgments.getOrgType(), (String)this.calcArgments.getSelectAdjustCode(), (String)this.calcArgments.getTaskId());
        fetchResult.amt = this.formulaEvalService.evaluateSumUnOffsetData(dimensionValueSet, formula, srcList);
        fetchResult.needCreateOffsetItem = fetchResult.amt != 0.0;
        return fetchResult;
    }

    private FetchResult fetchItem(GcFcRuleUnOffsetDataDTO unOffsetData, FinancialCheckFetchConfig.Item fetchNumSettingItem, List<GcFcRuleUnOffsetDataDTO> inputDatas) {
        FetchResult result = new FetchResult();
        String formula = fetchNumSettingItem.getFetchFormula();
        if (ObjectUtils.isEmpty(formula)) {
            if (unOffsetData.getSubjectCode().equals(fetchNumSettingItem.getSubjectCode())) {
                result.needCreateOffsetItem = true;
                result.amt = unOffsetData.getAmt();
                return result;
            }
            result.needCreateOffsetItem = false;
        } else {
            DimensionValueSet dimensionValueSet = DimensionUtils.generateDimSet((Object)this.calcArgments.getOrgId(), (Object)this.calcArgments.getPeriodStr(), (Object)this.calcArgments.getCurrency(), (Object)this.calcArgments.getOrgType(), (String)this.calcArgments.getSelectAdjustCode(), (String)this.calcArgments.getTaskId());
            result.amt = this.formulaEvalService.evaluateMxUnOffsetData(dimensionValueSet, formula, unOffsetData, inputDatas);
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

    private List<GcOffSetVchrItemDTO> doCreateBalanceOffsetItem(List<FinancialCheckFetchConfig.Item> balanceFetchNumSettingItems, double debitAndCreditDiff, OrientEnum createOrient) {
        double balanceAmt;
        if (CollectionUtils.isEmpty(balanceFetchNumSettingItems)) {
            return null;
        }
        double d = balanceAmt = createOrient == OrientEnum.D ? -debitAndCreditDiff : debitAndCreditDiff;
        if (balanceAmt == 0.0) {
            return null;
        }
        FinancialCheckFetchConfig.Item balanceFetchItem = this.getMatchedBalanceItem(balanceFetchNumSettingItems);
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

    private List<GcOffSetVchrItemDTO> createSumOffsetItems(FinancialCheckFetchConfig.Item fetchItem, double amt, OrientEnum createOrient) {
        FinancialCheckDimSrcTypeEnum dimSrcType = fetchItem.getDimSrcType();
        ArrayList<GcOffSetVchrItemDTO> offsetItems = new ArrayList<GcOffSetVchrItemDTO>();
        if (ObjectUtils.isEmpty(dimSrcType) || FinancialCheckDimSrcTypeEnum.NONE.equals((Object)dimSrcType) || FinancialCheckDimSrcTypeEnum.ITEM.equals((Object)dimSrcType)) {
            List<GcFcRuleUnOffsetDataDTO> srcFormualList = this.getListForFormual(fetchItem.getFetchType());
            GcFcRuleUnOffsetDataDTO srcInputItem = CollectionUtils.isEmpty((Collection)this.recordMap.get(createOrient.getCode())) ? srcFormualList.get(0) : this.recordMap.get(createOrient.getCode()).get(0);
            GcOffSetVchrItemDTO offsetVchrItem = this.createOffsetItem(srcInputItem, amt, createOrient, fetchItem);
            offsetItems.add(offsetVchrItem);
            return offsetItems;
        }
        if (FinancialCheckDimSrcTypeEnum.DEBIT.equals((Object)dimSrcType) || FinancialCheckDimSrcTypeEnum.CREDIT.equals((Object)dimSrcType)) {
            FetchTypeEnum fetchType = FinancialCheckDimSrcTypeEnum.toFetchType((FinancialCheckDimSrcTypeEnum)dimSrcType);
            List<GcFcRuleUnOffsetDataDTO> srcFormualList = this.getListForFormual(fetchType);
            if (CollectionUtils.isEmpty(srcFormualList)) {
                return offsetItems;
            }
            GcFcRuleUnOffsetDataDTO srcInputItem = srcFormualList.get(0);
            GcOffSetVchrItemDTO offsetVchrItem = this.createOffsetItem(srcInputItem, amt, createOrient, fetchItem);
            offsetItems.add(offsetVchrItem);
            return offsetItems;
        }
        FetchTypeEnum fetchType = FinancialCheckDimSrcTypeEnum.toFetchType((FinancialCheckDimSrcTypeEnum)dimSrcType);
        List<GcFcRuleUnOffsetDataDTO> srcFormualList = this.getListForFormual(fetchType);
        if (CollectionUtils.isEmpty(srcFormualList)) {
            return offsetItems;
        }
        double srcSum = this.getSrcSum(dimSrcType);
        double distributionedAmt = 0.0;
        for (int index = 0; index < srcFormualList.size(); ++index) {
            double offsetAmt;
            GcFcRuleUnOffsetDataDTO srcInputItem = srcFormualList.get(index);
            if (index == srcFormualList.size() - 1) {
                offsetAmt = OffsetVchrItemNumberUtils.round((double)(amt - distributionedAmt));
            } else {
                offsetAmt = OffsetVchrItemNumberUtils.round((double)(amt * srcInputItem.getAmt() / srcSum));
                distributionedAmt += offsetAmt;
            }
            GcOffSetVchrItemDTO offsetVchrItem = this.createOffsetItem(srcInputItem, offsetAmt, createOrient, fetchItem);
            offsetItems.add(offsetVchrItem);
        }
        return offsetItems;
    }

    private FinancialCheckFetchConfig.Item getMatchedBalanceItem(List<FinancialCheckFetchConfig.Item> balanceFetchNumSettingItems) {
        double amtNotZero = 1.0;
        for (FinancialCheckFetchConfig.Item balanceFetchNumSettingItem : balanceFetchNumSettingItems) {
            DimensionValueSet dimensionValueSet;
            double result;
            String formula = balanceFetchNumSettingItem.getFetchFormula();
            if (ObjectUtils.isEmpty(formula)) {
                return null;
            }
            List<GcFcRuleUnOffsetDataDTO> srcFormualList = this.getListForFormual(balanceFetchNumSettingItem.getFetchType());
            if (CollectionUtils.isEmpty(srcFormualList) || NumberUtils.isZreo((Double)(result = this.formulaEvalService.evaluateUnOffsetDataPHS(dimensionValueSet = DimensionUtils.generateDimSet((Object)this.calcArgments.getOrgId(), (Object)this.calcArgments.getPeriodStr(), (Object)this.calcArgments.getCurrency(), (Object)this.calcArgments.getOrgType(), (String)this.calcArgments.getSelectAdjustCode(), (String)this.calcArgments.getTaskId()), formula, srcFormualList, amtNotZero)))) continue;
            return balanceFetchNumSettingItem;
        }
        return null;
    }

    private double getSrcSum(FinancialCheckDimSrcTypeEnum dimSrcType) {
        double srcSum = 0.0;
        List<GcFcRuleUnOffsetDataDTO> inputItems = this.recordMap.get(ALLLIST);
        for (GcFcRuleUnOffsetDataDTO inputItem : inputItems) {
            boolean needSum = FinancialCheckDimSrcTypeEnum.ALL_RATE.equals((Object)dimSrcType) || FinancialCheckDimSrcTypeEnum.DEBIT_RATE.equals((Object)dimSrcType) && inputItem.getDc() == 1 || FinancialCheckDimSrcTypeEnum.CREDIT_RATE.equals((Object)dimSrcType) && inputItem.getDc() == -1;
            if (!needSum) continue;
            srcSum += inputItem.getAmt().doubleValue();
        }
        return OffsetVchrItemNumberUtils.round((double)srcSum);
    }

    private GcOffSetVchrItemDTO createOffsetItem(GcFcRuleUnOffsetDataDTO srcUnOffsetData, Double amt, OrientEnum createOrient, FinancialCheckFetchConfig.Item fetchItem) {
        String oppUnitId;
        String unitId;
        if (srcUnOffsetData == null) {
            return null;
        }
        GcOffSetVchrItemDTO offsetVchrItem = new GcOffSetVchrItemDTO();
        offsetVchrItem.setId(UUID.randomUUID().toString());
        offsetVchrItem.setRecver(Long.valueOf(0L));
        offsetVchrItem.setmRecid(this.mrecid);
        offsetVchrItem.setAcctYear(this.calcArgments.getAcctYear());
        offsetVchrItem.setAcctPeriod(this.calcArgments.getAcctPeriod());
        offsetVchrItem.setDefaultPeriod(this.calcArgments.getPeriodStr());
        ConsolidatedTaskService taskCacheService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
        assert (taskCacheService != null);
        String reportSystemId = taskCacheService.getSystemIdBySchemeId(this.calcArgments.getSchemeId(), this.calcArgments.getPeriodStr());
        if (reportSystemId == null) {
            return null;
        }
        if (FetchTypeEnum.isSum((FetchTypeEnum)fetchItem.getFetchType())) {
            if (createOrient.getValue().equals(srcUnOffsetData.getDc())) {
                unitId = srcUnOffsetData.getUnitId();
                oppUnitId = srcUnOffsetData.getOppUnitId();
            } else {
                unitId = srcUnOffsetData.getOppUnitId();
                oppUnitId = srcUnOffsetData.getUnitId();
            }
        } else {
            unitId = srcUnOffsetData.getUnitId();
            oppUnitId = srcUnOffsetData.getOppUnitId();
        }
        offsetVchrItem.setSchemeId(this.calcArgments.getSchemeId());
        offsetVchrItem.setTaskId(this.calcArgments.getTaskId());
        offsetVchrItem.setSystemId(reportSystemId);
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
        offsetVchrItem.setElmMode(Integer.valueOf(this.batchManualOffset ? OffsetElmModeEnum.BATCH_MANUAL_ITEM.getValue() : (this.manualOffset ? OffsetElmModeEnum.MANUAL_ITEM.getValue() : OffsetElmModeEnum.AUTO_ITEM.getValue())));
        offsetVchrItem.setRuleId(this.rule.getId());
        offsetVchrItem.setChkState(this.rule.isChecked() ? CheckStateEnum.CHECKED.getCode() : CheckStateEnum.UNCHECKED.getCode());
        offsetVchrItem.setCreateTime(new Date());
        offsetVchrItem.setOffSetSrcType(OffSetSrcTypeEnum.CONSOLIDATE);
        offsetVchrItem.setSrcId(FetchTypeEnum.isSum((FetchTypeEnum)fetchItem.getFetchType()) ? null : srcUnOffsetData.getId());
        offsetVchrItem.setGcBusinessTypeCode(FinancialCheckOffsetItemCreateHelper.getBussinessTypeId(this.fetchConfig, (AbstractUnionRule)this.rule));
        offsetVchrItem.setFetchSetGroupId(this.fetchConfig == null ? null : this.fetchConfig.getFetchSetGroupId());
        offsetVchrItem.setSrcOffsetGroupId(this.offsetGroupId);
        offsetVchrItem.setSelectAdjustCode(this.calcArgments.getSelectAdjustCode());
        offsetVchrItem.addFieldValue("ADJUST", (Object)this.calcArgments.getSelectAdjustCode());
        offsetVchrItem.setOrgType(GCOrgTypeEnum.NONE.getCode());
        offsetVchrItem.setOrient(createOrient);
        if (FinancialCheckDimSrcTypeEnum.NONE.equals((Object)fetchItem.getDimSrcType())) {
            return offsetVchrItem;
        }
        FinancialCheckOffsetItemCreateHelper.setDimValues(offsetVchrItem, srcUnOffsetData);
        return offsetVchrItem;
    }

    private static String getBussinessTypeId(FinancialCheckFetchConfig fetchNumSetting, AbstractUnionRule rule) {
        if (fetchNumSetting == null || ObjectUtils.isEmpty(fetchNumSetting.getBusinessTypeCode())) {
            return rule.getBusinessTypeCode();
        }
        return fetchNumSetting.getBusinessTypeCode();
    }

    private static void setDimValues(GcOffSetVchrItemDTO offsetVchrItem, GcFcRuleUnOffsetDataDTO refInputItem) {
        ManagementDimensionCacheService managementDimensionCacheService = (ManagementDimensionCacheService)SpringContextUtils.getBean(ManagementDimensionCacheService.class);
        assert (managementDimensionCacheService != null);
        List managementDims = managementDimensionCacheService.getOptionalManagementDims();
        if (CollectionUtils.isEmpty(managementDims)) {
            return;
        }
        managementDims.forEach(managementDim -> FinancialCheckOffsetItemCreateHelper.setOffsetVchrItemFieldValue(offsetVchrItem, refInputItem, managementDim.getCode().toUpperCase()));
    }

    private static void setOffsetVchrItemFieldValue(GcOffSetVchrItemDTO offsetVchrItem, GcFcRuleUnOffsetDataDTO refInputItem, String fieldName) {
        offsetVchrItem.addFieldValue(fieldName, refInputItem.getFieldValue("PROJECTTITLE".equals(fieldName) ? "PROJECTCODE" : fieldName));
        try {
            Optional<PropertyDescriptor> matchedProperty = Stream.of(Introspector.getBeanInfo(GcOffSetVchrItemDTO.class).getPropertyDescriptors()).filter(property -> fieldName.equals(property.getName().toUpperCase())).findAny();
            if (matchedProperty.isPresent()) {
                matchedProperty.get().getWriteMethod().invoke(offsetVchrItem, refInputItem.getFieldValue("PROJECTTITLE".equals(fieldName) ? "PROJECTCODE" : fieldName));
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
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
        private List<GcFcRuleUnOffsetDataDTO> unOffsetDataS;

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

        public List<GcFcRuleUnOffsetDataDTO> getUnOffsetDataS() {
            return this.unOffsetDataS;
        }

        public void setUnOffsetDataS(List<GcFcRuleUnOffsetDataDTO> unOffsetDataS) {
            this.unOffsetDataS = unOffsetDataS;
        }
    }
}

