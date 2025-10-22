/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.dto.GcCalcRuleExecuteStateDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.service.GcCalcService
 *  com.jiuqi.gcreport.calculate.util.GcCalcAmtCheckUtil
 *  com.jiuqi.gcreport.calculate.util.PeriodUtils
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.NumberUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.event.GcCalcExecuteSameOffsetItemEvent
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.PublicValueAdjustmentRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.PublicValueAdjustmentRuleDTO$Item
 *  com.jiuqi.gcreport.unionrule.enums.InvestmentUnitEnum
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.period.PeriodWrapper
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.invest.calculate.rule.processor.executor;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.dto.GcCalcRuleExecuteStateDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.service.GcCalcService;
import com.jiuqi.gcreport.calculate.util.GcCalcAmtCheckUtil;
import com.jiuqi.gcreport.calculate.util.PeriodUtils;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.NumberUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.invest.formula.service.GcBillFormulaEvalService;
import com.jiuqi.gcreport.invest.investbill.service.FairValueBillService;
import com.jiuqi.gcreport.invest.investbill.service.InvestBillService;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.event.GcCalcExecuteSameOffsetItemEvent;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.PublicValueAdjustmentRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.InvestmentUnitEnum;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.period.PeriodWrapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GcCalcFvchBillRuleExecutor {
    private Logger logger = LoggerFactory.getLogger(GcCalcFvchBillRuleExecutor.class);
    @Autowired
    private GcOffSetAppOffsetService adjustService;
    @Autowired
    private GcBillFormulaEvalService billFormulaEvalService;
    @Autowired
    private FairValueBillService fairValueBillService;
    @Autowired
    private InvestBillService investBillService;
    @Autowired
    private GcCalcService gcCalcService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Transactional(propagation=Propagation.NESTED, rollbackFor={Exception.class})
    public void calMerge(AbstractUnionRule rule, GcCalcEnvContext env) {
        this.doExecute(env, rule);
        if (env.getCalcArgments().getPreCalcFlag().get()) {
            throw new BusinessRuntimeException("\u5408\u5e76\u8ba1\u7b97\u9884\u6267\u884c\u901a\u8fc7\u629b\u5f02\u5e38\u7684\u65b9\u5f0f\u6765\u8fdb\u884c\u4e0d\u63d0\u4ea4\u4e8b\u52a1\u64cd\u4f5c");
        }
    }

    private void doExecute(GcCalcEnvContext env, AbstractUnionRule rule) {
        ((GcCalcRuleExecuteStateDTO)env.getRuleStateMap().get(rule.getId())).addCreateOffsetItemCountValue(Integer.valueOf(0));
        if (!(rule instanceof PublicValueAdjustmentRuleDTO)) {
            return;
        }
        PublicValueAdjustmentRuleDTO publicValueAdjustmentRule = (PublicValueAdjustmentRuleDTO)rule;
        GcCalcArgmentsDTO arg = env.getCalcArgments();
        this.gcCalcService.deleteAutoOffsetEntrysByRule(rule.getId(), arg);
        this.calMerge(publicValueAdjustmentRule, env);
        this.executeInvest(env);
    }

    private void executeInvest(GcCalcEnvContext env) {
        QueryParamsDTO queryParamsDTO = new QueryParamsDTO();
        queryParamsDTO.setTaskId(env.getCalcArgments().getTaskId());
        queryParamsDTO.setCurrency(env.getCalcArgments().getCurrency());
        queryParamsDTO.setSchemeId(env.getCalcArgments().getSchemeId());
        queryParamsDTO.setPeriodStr(env.getCalcArgments().getPeriodStr());
        queryParamsDTO.setOrgId(env.getCalcArgments().getOrgId());
        queryParamsDTO.setOrgType(env.getCalcArgments().getOrgType());
        this.applicationEventPublisher.publishEvent((ApplicationEvent)new GcCalcExecuteSameOffsetItemEvent((Object)this, queryParamsDTO));
    }

    private void calMerge(PublicValueAdjustmentRuleDTO publicValueAdjustmentRule, GcCalcEnvContext env) {
        Map<String, List<DefaultTableEntity>> mFvcData;
        if (CollectionUtils.isEmpty((Collection)publicValueAdjustmentRule.getCreditItemList()) || CollectionUtils.isEmpty((Collection)publicValueAdjustmentRule.getDebitItemList())) {
            return;
        }
        GcCalcArgmentsDTO arg = env.getCalcArgments();
        YearPeriodObject yp = new YearPeriodObject(null, env.getCalcArgments().getPeriodStr());
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)env.getCalcArgments().getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        List<Object> investMast = new ArrayList<DefaultTableEntity>();
        HashSet<String> investUnitSet = new HashSet<String>();
        HashSet<String> investedUnitSet = new HashSet();
        HashMap<String, String> fvchSrcId2IdMap = new HashMap<String, String>();
        if (arg.getPreCalcFlag().get()) {
            String billCode = (String)arg.getExtendInfo().get("BILLCODE");
            DefaultTableEntity investBillEntity = InvestBillTool.getEntityByBillCode((String)billCode, (String)"GC_INVESTBILL");
            if (investBillEntity == null) {
                return;
            }
            investMast.add(investBillEntity);
            List<DefaultTableEntity> fvchSubItems = this.fairValueBillService.getFvchItemsByMasterSrcId((String)investBillEntity.getFieldValue("SRCID"), arg.getAcctYear());
            mFvcData = fvchSubItems.stream().collect(Collectors.groupingBy(item -> (String)item.getFieldValue("INVESTEDUNIT")));
            for (DefaultTableEntity item2 : fvchSubItems) {
                investUnitSet.add((String)item2.getFieldValue("UNITCODE"));
                investedUnitSet.add((String)item2.getFieldValue("INVESTEDUNIT"));
            }
        } else {
            investedUnitSet = this.getUnits(arg, orgTool);
            investUnitSet = new HashSet();
            mFvcData = this.fairValueBillService.getFvchItemBills(arg.getAcctYear(), investUnitSet, fvchSrcId2IdMap);
            int period = PeriodUtils.getMonth((int)arg.getAcctYear(), (int)arg.getPeriodType(), (int)arg.getAcctPeriod());
            investMast = this.investBillService.getMastByInvestAndInvestedUnit(investUnitSet, investedUnitSet, arg.getAcctYear(), period);
        }
        HashSet disposedFvchId = new HashSet();
        investMast = investMast.stream().filter(item -> {
            if (null == item.getFieldValue("DISPOSEDATE")) {
                return true;
            }
            disposedFvchId.add(fvchSrcId2IdMap.get(item.getFieldValue("SRCID")));
            return false;
        }).collect(Collectors.toList());
        HashMap investMastMap = new HashMap();
        if (investMast != null) {
            investMast.forEach(item -> {
                String investUnit = String.valueOf(item.getFieldValue("UNITCODE"));
                String investedUnit = String.valueOf(item.getFieldValue("INVESTEDUNIT"));
                investMastMap.put(investUnit + "|" + investedUnit, item);
            });
        }
        investedUnitSet.forEach(unitId -> {
            List<DefaultTableEntity> fvcData = (List<DefaultTableEntity>)mFvcData.get(unitId);
            if (!CollectionUtils.isEmpty(fvcData = this.filterFvcData(arg, fvcData, orgTool, investMastMap, disposedFvchId))) {
                for (DefaultTableEntity fvc : fvcData) {
                    this.doOffset(fvc, publicValueAdjustmentRule, env, investMastMap);
                }
            }
        });
    }

    private void doOffset(DefaultTableEntity fvc, PublicValueAdjustmentRuleDTO publicValueAdjustmentRule, GcCalcEnvContext env, Map<String, DefaultTableEntity> investMastMap) {
        List<GcOffSetVchrItemDTO> offsetItems = this.getOffsetItems(fvc, publicValueAdjustmentRule, env, investMastMap);
        if (offsetItems.size() < 2 && !env.getCalcArgments().getPreCalcFlag().get()) {
            return;
        }
        if (GcCalcAmtCheckUtil.checkAndDistributionAmt((AbstractUnionRule)publicValueAdjustmentRule, offsetItems) || env.getCalcArgments().getPreCalcFlag().get()) {
            GcOffSetVchrDTO offSetItemDTO = new GcOffSetVchrDTO();
            offSetItemDTO.setItems(offsetItems);
            offSetItemDTO.setConsFormulaCalcType("autoFlag");
            this.adjustService.save(offSetItemDTO);
            List items = offSetItemDTO.getItems();
            ((GcCalcRuleExecuteStateDTO)env.getRuleStateMap().get(publicValueAdjustmentRule.getId())).addCreateOffsetItemCountValue(Integer.valueOf(CollectionUtils.isEmpty((Collection)items) ? 0 : items.size()));
            if (env.getCalcArgments().getPreCalcFlag().get()) {
                env.getCalcContextExpandVariableCenter().getPreCalcOffSetItems().addAll(items);
            }
        } else {
            this.logger.debug("\u516c\u5141\u4ef7\u503c\u8c03\u6574\u89c4\u5219\u5408\u5e76\u8ba1\u7b97\u672a\u6838\u5bf9\u6210\u529f. \u5355\u4f4d\uff1a" + env.getCalcArgments().getOrgId() + " \u65f6\u671f\uff1a" + env.getCalcArgments().getPeriodStr());
        }
    }

    private List<GcOffSetVchrItemDTO> getOffsetItems(DefaultTableEntity fvc, PublicValueAdjustmentRuleDTO publicValueAdjustmentRule, GcCalcEnvContext env, Map<String, DefaultTableEntity> investMastMap) {
        String offsetGroupId = fvc.getId();
        List debitItemList = publicValueAdjustmentRule.getDebitItemList();
        List creditItemList = publicValueAdjustmentRule.getCreditItemList();
        List<GcOffSetVchrItemDTO> offsets = this.createOffsets(offsetGroupId, fvc, debitItemList, creditItemList, publicValueAdjustmentRule, env, investMastMap);
        return offsets;
    }

    private Set<String> getUnits(GcCalcArgmentsDTO arg, GcOrgCenterService tool) {
        HashSet<String> unitIds = new HashSet<String>();
        List childrenOrg = tool.getOrgChildrenTree(arg.getOrgId());
        childrenOrg.forEach(org -> {
            String baseUnitId;
            String string = baseUnitId = org.isLeaf() ? null : tool.getDeepestBaseUnitId(org.getId());
            if (baseUnitId != null) {
                unitIds.add(baseUnitId);
            } else {
                unitIds.add(org.getId());
            }
        });
        return unitIds;
    }

    private List<GcOffSetVchrItemDTO> createOffsets(String offsetGroupId, DefaultTableEntity fvc, List<PublicValueAdjustmentRuleDTO.Item> debitItemList, List<PublicValueAdjustmentRuleDTO.Item> creditItemList, PublicValueAdjustmentRuleDTO publicValueAdjustmentRule, GcCalcEnvContext env, Map<String, DefaultTableEntity> investMastMap) {
        List<Object> parents;
        Optional<BaseDataVO> filted;
        String assetType;
        DimensionValueSet dset;
        ArrayList<GcOffSetVchrItemDTO> offsets = new ArrayList<GcOffSetVchrItemDTO>();
        ArrayList<GcOffSetVchrItemDTO> offsets_d = new ArrayList<GcOffSetVchrItemDTO>();
        ArrayList<GcOffSetVchrItemDTO> offsets_c = new ArrayList<GcOffSetVchrItemDTO>();
        GcOffSetVchrItemDTO phsOffset = null;
        List<PublicValueAdjustmentRuleDTO.Item> phsRuleItem_d = this.getPublicValueAdjustmentRuleDTOItemWithPHS(debitItemList);
        List<PublicValueAdjustmentRuleDTO.Item> phsRuleItem_c = this.getPublicValueAdjustmentRuleDTOItemWithPHS(creditItemList);
        debitItemList = debitItemList.stream().filter(one -> StringUtils.isEmpty((String)one.getFetchFormula()) || !one.getFetchFormula().contains("PHS")).collect(Collectors.toList());
        creditItemList = creditItemList.stream().filter(one -> StringUtils.isEmpty((String)one.getFetchFormula()) || !one.getFetchFormula().contains("PHS")).collect(Collectors.toList());
        GcCalcArgmentsDTO arg = env.getCalcArgments();
        if (!this.isAllowCal(arg, dset = DimensionUtils.generateDimSet((Object)arg.getOrgId(), (Object)arg.getPeriodStr(), (Object)arg.getCurrency(), (Object)arg.getOrgType(), (String)arg.getSelectAdjustCode(), (String)arg.getTaskId()), fvc, publicValueAdjustmentRule.getRuleCondition())) {
            return new ArrayList<GcOffSetVchrItemDTO>();
        }
        List baseData = GcBaseDataCenterTool.getInstance().queryBasedataItemsVO("MD_ASSETTYPE");
        Assert.isTrue((!CollectionUtils.isEmpty((Collection)baseData) ? 1 : 0) != 0, (String)GcI18nUtil.getMessage((String)"gc.calculate.bill.assets.rule.assets.enum.empty"), (Object[])new Object[0]);
        for (PublicValueAdjustmentRuleDTO.Item item : debitItemList) {
            GcOffSetVchrItemDTO offsetItem_d;
            assetType = (String)fvc.getFieldValue("ASSETTYPE");
            filted = baseData.stream().filter(datum -> datum.getCode().equals(assetType)).findAny();
            if (!filted.isPresent()) continue;
            BaseDataVO datum2 = filted.get();
            List<Object> list = parents = datum2.getParents() == null ? new ArrayList() : Arrays.asList(datum2.getParents());
            if (!datum2.getCode().equals(item.getAssetTypeCode()) && !parents.contains(item.getAssetTypeCode()) || (offsetItem_d = this.createOffsetItem(env, investMastMap, offsetGroupId, fvc, item, OrientEnum.D, publicValueAdjustmentRule, arg)).getDebit() == 0.0 && offsetItem_d.getCredit() == 0.0 && !arg.getPreCalcFlag().get()) continue;
            offsets_d.add(offsetItem_d);
        }
        for (PublicValueAdjustmentRuleDTO.Item item : creditItemList) {
            GcOffSetVchrItemDTO offsetItem_c;
            assetType = (String)fvc.getFieldValue("ASSETTYPE");
            filted = baseData.stream().filter(datum -> datum.getCode().equals(assetType)).findAny();
            if (!filted.isPresent()) continue;
            BaseDataVO datum2 = filted.get();
            List<Object> list = parents = datum2.getParents() == null ? new ArrayList() : Arrays.asList(datum2.getParents());
            if (!datum2.getCode().equals(item.getAssetTypeCode()) && !parents.contains(item.getAssetTypeCode()) || (offsetItem_c = this.createOffsetItem(env, investMastMap, offsetGroupId, fvc, item, OrientEnum.C, publicValueAdjustmentRule, arg)).getDebit() == 0.0 && offsetItem_c.getCredit() == 0.0 && !arg.getPreCalcFlag().get()) continue;
            offsets_c.add(offsetItem_c);
        }
        if (phsOffset == null) {
            phsOffset = this.createOffsetItemWithPHS(offsets_d, offsets_c, phsRuleItem_d, phsRuleItem_c, fvc, offsetGroupId, publicValueAdjustmentRule, env, investMastMap);
        }
        offsets.addAll(offsets_d);
        offsets.addAll(offsets_c);
        if (phsOffset != null) {
            offsets.add(phsOffset);
        }
        if (offsets.size() < 2) {
            new ArrayList();
        }
        return offsets;
    }

    private boolean isAllowCal(GcCalcArgmentsDTO arg, DimensionValueSet dset, DefaultTableEntity fvc, String ruleCondition) {
        return this.billFormulaEvalService.checkFvchBillData(arg, dset, ruleCondition, fvc);
    }

    private GcOffSetVchrItemDTO createOffsetItem(GcCalcEnvContext env, Map<String, DefaultTableEntity> investMastMap, String offsetGroupId, DefaultTableEntity fvcData, PublicValueAdjustmentRuleDTO.Item item, OrientEnum orient, PublicValueAdjustmentRuleDTO publicValueAdjustmentRule, GcCalcArgmentsDTO calcArgmentsDTO) {
        String defaultDimOrgId;
        GcOffSetVchrItemDTO offsetVchrItem = new GcOffSetVchrItemDTO();
        offsetVchrItem.setId(UUID.randomUUID().toString());
        offsetVchrItem.setRecver(Long.valueOf(0L));
        offsetVchrItem.setOrgType(GCOrgTypeEnum.NONE.getCode());
        offsetVchrItem.setAcctYear(calcArgmentsDTO.getAcctYear());
        offsetVchrItem.setAcctPeriod(calcArgmentsDTO.getAcctPeriod());
        offsetVchrItem.setDefaultPeriod(calcArgmentsDTO.getPeriodStr());
        offsetVchrItem.setSrcOffsetGroupId(offsetGroupId);
        offsetVchrItem.setTaskId(calcArgmentsDTO.getTaskId());
        offsetVchrItem.setSchemeId(calcArgmentsDTO.getSchemeId());
        offsetVchrItem.setSubjectCode(item.getSubjectCode());
        offsetVchrItem.setSortOrder(Double.valueOf(0.0));
        offsetVchrItem.setOffSetCurr(calcArgmentsDTO.getCurrency());
        offsetVchrItem.setElmMode(Integer.valueOf(OffsetElmModeEnum.AUTO_ITEM.getValue()));
        offsetVchrItem.setOffSetSrcType(OffSetSrcTypeEnum.FAIRVALUE_ADJ);
        offsetVchrItem.setRuleId(publicValueAdjustmentRule.getId());
        offsetVchrItem.setCreateTime(new Date());
        offsetVchrItem.setGcBusinessTypeCode(publicValueAdjustmentRule.getBusinessTypeCode());
        offsetVchrItem.addFieldValue("ASSETTITLE", fvcData.getFieldValue("ASSETTITLE"));
        String investedUnit = (String)fvcData.getFieldValue("INVESTEDUNIT");
        String investUnit = (String)fvcData.getFieldValue("UNITCODE");
        if (InvestmentUnitEnum.INVESTED_UNIT == item.getInvestmentUnit()) {
            offsetVchrItem.setUnitId(investedUnit);
            offsetVchrItem.setOppUnitId(investUnit);
            defaultDimOrgId = investedUnit;
        } else {
            offsetVchrItem.setUnitId(investUnit);
            offsetVchrItem.setOppUnitId(investedUnit);
            defaultDimOrgId = investUnit;
        }
        DimensionValueSet dset = DimensionUtils.generateDimSet((Object)fvcData.getFieldValue("INVESTEDUNIT"), (Object)calcArgmentsDTO.getPeriodStr(), (Object)calcArgmentsDTO.getCurrency(), (Object)calcArgmentsDTO.getOrgType(), (String)calcArgmentsDTO.getSelectAdjustCode(), (String)calcArgmentsDTO.getTaskId());
        Assert.isFalse((boolean)"PHS".equalsIgnoreCase(item.getFetchFormula()), (String)GcI18nUtil.getMessage((String)"gc.calculate.bill.phs.error"), (Object[])new Object[0]);
        if (StringUtils.isEmpty((String)item.getFetchFormula())) {
            offsetVchrItem.setDebit(Double.valueOf(0.0));
            offsetVchrItem.setCredit(Double.valueOf(0.0));
        } else {
            if (orient == OrientEnum.D) {
                offsetVchrItem.setDebit(Double.valueOf(this.billFormulaEvalService.evaluateFvchBillData(env, dset, item.getFetchFormula(), fvcData)));
                offsetVchrItem.setCredit(Double.valueOf(0.0));
            }
            if (orient == OrientEnum.C) {
                offsetVchrItem.setDebit(Double.valueOf(0.0));
                offsetVchrItem.setCredit(Double.valueOf(this.billFormulaEvalService.evaluateFvchBillData(env, dset, item.getFetchFormula(), fvcData)));
            }
        }
        offsetVchrItem.setOffSetDebit(offsetVchrItem.getDebit());
        offsetVchrItem.setOffSetCredit(offsetVchrItem.getCredit());
        offsetVchrItem.setDiffd(Double.valueOf(0.0));
        offsetVchrItem.setDiffc(Double.valueOf(0.0));
        this.initDimensions(dset, offsetVchrItem, fvcData, item, env, investUnit, investedUnit, defaultDimOrgId, investMastMap);
        offsetVchrItem.setSelectAdjustCode(calcArgmentsDTO.getSelectAdjustCode());
        return offsetVchrItem;
    }

    private String getDimValueByDimCodeAndOrg(GcCalcEnvContext env, String dimOrgId, String dimCode) {
        if (dimOrgId == null) {
            return null;
        }
        GcOrgCenterService orgCenterService = env.getCalcContextExpandVariableCenter().getOrgCenterService();
        GcOrgCacheVO dimOrg = orgCenterService.getOrgByCode(dimOrgId);
        if (dimOrg == null) {
            return null;
        }
        Object dimValue = dimOrg.getBaseFieldValue(dimCode);
        if (dimValue == null) {
            return null;
        }
        return dimValue.toString();
    }

    private void initDimensions(DimensionValueSet dimensionValueSet, GcOffSetVchrItemDTO offSetVchrItemDTO, DefaultTableEntity fvcData, PublicValueAdjustmentRuleDTO.Item item, GcCalcEnvContext env, String investUnit, String investedUnit, String defaultDimOrgId, Map<String, DefaultTableEntity> investMastMap) {
        Map dimensions = item.getDimensions();
        if (dimensions == null) {
            return;
        }
        for (String dimKey : dimensions.keySet()) {
            if (dimKey.contains("customizeFormula")) continue;
            String dimValueSource = (String)dimensions.get(dimKey);
            if (InvestmentUnitEnum.INVESTMENT_UNIT.getCode().equalsIgnoreCase(dimValueSource)) {
                offSetVchrItemDTO.addFieldValue(dimKey, (Object)this.getDimValueByDimCodeAndOrg(env, investUnit, dimKey));
                continue;
            }
            if (InvestmentUnitEnum.INVESTED_UNIT.getCode().equalsIgnoreCase(dimValueSource)) {
                offSetVchrItemDTO.addFieldValue(dimKey, (Object)this.getDimValueByDimCodeAndOrg(env, investedUnit, dimKey));
                continue;
            }
            if ("ACCOUNT".equals(dimValueSource)) {
                Object fieldObj = fvcData.getFieldValue(dimKey);
                if (fieldObj != null) {
                    offSetVchrItemDTO.addFieldValue(dimKey, fieldObj);
                    continue;
                }
                DefaultTableEntity investMast = investMastMap.get(investUnit + "|" + investedUnit);
                if (investMast == null) continue;
                offSetVchrItemDTO.addFieldValue(dimKey, investMast.getFieldValue(dimKey));
                continue;
            }
            if ("customizeFormula".equals(dimValueSource)) {
                if (StringUtils.isEmpty((String)((String)dimensions.get(dimKey + "_customizeFormula")))) {
                    offSetVchrItemDTO.addFieldValue(dimKey, null);
                    continue;
                }
                AbstractData data = this.billFormulaEvalService.evaluateFvchBillAbstractData(env, dimensionValueSet, (String)dimensions.get(dimKey + "_customizeFormula"), fvcData);
                offSetVchrItemDTO.addFieldValue(dimKey, data.getAsObject());
                continue;
            }
            offSetVchrItemDTO.addFieldValue(dimKey, (Object)this.getDimValueByDimCodeAndOrg(env, defaultDimOrgId, dimKey));
        }
    }

    private List<PublicValueAdjustmentRuleDTO.Item> getPublicValueAdjustmentRuleDTOItemWithPHS(List<PublicValueAdjustmentRuleDTO.Item> itemList) {
        if (CollectionUtils.isEmpty(itemList)) {
            return null;
        }
        ArrayList result = itemList.stream().filter(one -> !StringUtils.isEmpty((String)one.getFetchFormula()) && one.getFetchFormula().contains("PHS")).collect(Collectors.toList());
        return result == null ? new ArrayList() : result;
    }

    private GcOffSetVchrItemDTO createOffsetItemWithPHS(Collection<GcOffSetVchrItemDTO> offsets_d, Collection<GcOffSetVchrItemDTO> offsets_c, List<PublicValueAdjustmentRuleDTO.Item> phsRuleItem_d, List<PublicValueAdjustmentRuleDTO.Item> phsRuleItem_c, DefaultTableEntity fvc, String offsetGroupId, PublicValueAdjustmentRuleDTO publicValueAdjustmentRule, GcCalcEnvContext env, Map<String, DefaultTableEntity> investMastMap) {
        GcOffSetVchrItemDTO offsetItem = null;
        GcCalcArgmentsDTO calcArgments = env.getCalcArgments();
        for (PublicValueAdjustmentRuleDTO.Item fetchFormula : phsRuleItem_d) {
            double debit = this.calcPHSDiff(offsets_d, offsets_c, OrientEnum.D);
            env.getCalcContextExpandVariableCenter().setPhsValue(Double.valueOf(debit));
            offsetItem = this.createOffsetItem(env, investMastMap, offsetGroupId, fvc, fetchFormula, OrientEnum.D, publicValueAdjustmentRule, calcArgments);
            if (NumberUtils.isZreo((Double)offsetItem.getOffSetDebit())) continue;
            offsetItem.setMemo(GcI18nUtil.getMessage((String)"gc.calculate.bill.phs.memo"));
            return offsetItem;
        }
        for (PublicValueAdjustmentRuleDTO.Item fetchFormula : phsRuleItem_c) {
            double crebit = this.calcPHSDiff(offsets_d, offsets_c, OrientEnum.C);
            env.getCalcContextExpandVariableCenter().setPhsValue(Double.valueOf(crebit));
            offsetItem = this.createOffsetItem(env, investMastMap, offsetGroupId, fvc, fetchFormula, OrientEnum.C, publicValueAdjustmentRule, calcArgments);
            if (NumberUtils.isZreo((Double)offsetItem.getOffSetDebit()) && !calcArgments.getPreCalcFlag().get()) continue;
            offsetItem.setMemo(GcI18nUtil.getMessage((String)"gc.calculate.bill.phs.memo"));
            return offsetItem;
        }
        if (offsetItem != null) {
            offsetItem.setMemo(GcI18nUtil.getMessage((String)"gc.calculate.bill.phs.memo"));
        }
        return offsetItem;
    }

    private double calcPHSDiff(Collection<GcOffSetVchrItemDTO> offsets_d, Collection<GcOffSetVchrItemDTO> offsets_c, OrientEnum orient) {
        double m_debit_sum = offsets_d.stream().mapToDouble(GcOffSetVchrItemDTO::getDebit).sum();
        double m_crebit_sum = offsets_c.stream().mapToDouble(GcOffSetVchrItemDTO::getCredit).sum();
        BigDecimal debit_sum = new BigDecimal(m_debit_sum);
        BigDecimal crebit_sum = new BigDecimal(m_crebit_sum);
        if (orient == OrientEnum.D) {
            return crebit_sum.subtract(debit_sum).doubleValue();
        }
        if (orient == OrientEnum.C) {
            return debit_sum.subtract(crebit_sum).doubleValue();
        }
        return 0.0;
    }

    private Calendar getCalendar(PeriodWrapper periodWrapper) {
        Calendar calendar = GregorianCalendar.getInstance();
        switch (periodWrapper.getType()) {
            case 1: {
                calendar.set(periodWrapper.getYear(), 11, 1);
                break;
            }
            case 2: {
                calendar.set(periodWrapper.getYear(), periodWrapper.getPeriod() * 6 - 1, 1);
                break;
            }
            case 3: {
                calendar.set(periodWrapper.getYear(), periodWrapper.getPeriod() * 3 - 1, 1);
                break;
            }
            case 4: {
                calendar.set(periodWrapper.getYear(), periodWrapper.getPeriod() - 1, 1);
            }
        }
        return calendar;
    }

    private List<DefaultTableEntity> filterFvcData(GcCalcArgmentsDTO arg, List<DefaultTableEntity> fvcData, GcOrgCenterService tool, Map<String, DefaultTableEntity> investMastMap, Set<String> disposedFvchId) {
        if (CollectionUtils.isEmpty(fvcData)) {
            return null;
        }
        int acctYear = arg.getAcctYear();
        int acctPeriod = arg.getAcctPeriod();
        int periodType = arg.getPeriodType();
        PeriodWrapper pw = new PeriodWrapper(acctYear, periodType, acctPeriod);
        Calendar argCalendar = this.getCalendar(pw);
        argCalendar.add(2, 1);
        argCalendar.set(argCalendar.get(1), argCalendar.get(2), 1, 0, 0, 0);
        argCalendar.set(14, 0);
        return fvcData.stream().filter(datum -> {
            Date value = (Date)datum.getFieldValue("BIZDATE");
            if (value != null) {
                Calendar appendTime = GregorianCalendar.getInstance();
                appendTime.setTime(value);
                if (!appendTime.before(argCalendar)) {
                    return false;
                }
            }
            GcOrgCacheVO investUnit = tool.getOrgByCode((String)datum.getFieldValue("UNITCODE"));
            GcOrgCacheVO investUnited = tool.getOrgByCode((String)datum.getFieldValue("INVESTEDUNIT"));
            if (investUnit == null || investUnited == null) {
                return false;
            }
            GcOrgCacheVO commonUnit = tool.getCommonUnit(investUnit, investUnited);
            if (commonUnit == null || !Objects.equals(commonUnit.getId(), arg.getOrgId())) {
                return false;
            }
            if (disposedFvchId.contains(datum.getFieldValue("MASTERID"))) {
                return false;
            }
            String investUnitCode = (String)datum.getFieldValue("UNITCODE");
            String investUnitedCode = (String)datum.getFieldValue("INVESTEDUNIT");
            DefaultTableEntity investBill = (DefaultTableEntity)investMastMap.get(investUnitCode + "|" + investUnitedCode);
            if (null == investBill) {
                return false;
            }
            datum.addFieldValue("CURRENCYCODE", investBill.getFieldValue("CURRENCYCODE"));
            return true;
        }).collect(Collectors.toList());
    }

    private boolean isDispose(String periodStr, String investUnitCode, String investedUnitCode) {
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        Date calcDate = yp.formatYP().getEndDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(calcDate);
        int currentMonth = calendar.get(2);
        int period = PeriodUtils.getMonth((int)yp.getYear(), (int)yp.getType(), (int)yp.getPeriod());
        String[] columns = new String[]{"UNITCODE", "INVESTEDUNIT", "ACCTYEAR", "PERIOD"};
        List<Map<String, Object>> curPeriodInvestList = this.investBillService.listByWhere(columns, new Object[]{investUnitCode, investedUnitCode, yp.formatYP().getYear(), period});
        if (CollectionUtils.isEmpty(curPeriodInvestList)) {
            return true;
        }
        Map<String, Object> investData = curPeriodInvestList.get(0);
        Date disposeDate = (Date)investData.get("DISPOSEDATE");
        if (disposeDate == null) {
            return false;
        }
        calendar.setTime(disposeDate);
        int disposeMonth = calendar.get(2);
        return disposeMonth <= currentMonth;
    }
}

