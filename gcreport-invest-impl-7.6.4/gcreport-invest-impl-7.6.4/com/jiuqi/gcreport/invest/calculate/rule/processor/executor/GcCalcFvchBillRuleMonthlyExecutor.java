/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.dto.GcCalcRuleExecuteStateDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.service.GcCalcService
 *  com.jiuqi.gcreport.calculate.service.InvestBillInitialMergeCalcCache
 *  com.jiuqi.gcreport.calculate.util.GcCalcAmtCheckUtil
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.NumberUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.samecontrol.dao.SameCtrlOffSetItemDao
 *  com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgOrgEO
 *  com.jiuqi.gcreport.samecontrol.entity.SameCtrlOffSetItemEO
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlSrcTypeEnum
 *  com.jiuqi.gcreport.samecontrol.service.SameCtrlChgOrgService
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.PublicValueAdjustmentRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.PublicValueAdjustmentRuleDTO$Item
 *  com.jiuqi.gcreport.unionrule.enums.InvestmentUnitEnum
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.invest.calculate.rule.processor.executor;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.dto.GcCalcRuleExecuteStateDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.service.GcCalcService;
import com.jiuqi.gcreport.calculate.service.InvestBillInitialMergeCalcCache;
import com.jiuqi.gcreport.calculate.util.GcCalcAmtCheckUtil;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.NumberUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.invest.calculate.rule.processor.executor.GcCalcInvestBillRuleMonthlyExecutor;
import com.jiuqi.gcreport.invest.formula.service.GcBillFormulaEvalService;
import com.jiuqi.gcreport.invest.investbill.dto.GcInvestBillGroupDTO;
import com.jiuqi.gcreport.invest.investbill.service.FairValueBillService;
import com.jiuqi.gcreport.invest.investbill.service.InvestBillService;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlOffSetItemDao;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgOrgEO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlOffSetItemEO;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlSrcTypeEnum;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlChgOrgService;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.PublicValueAdjustmentRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.InvestmentUnitEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
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
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GcCalcFvchBillRuleMonthlyExecutor {
    private Logger logger = LoggerFactory.getLogger(GcCalcFvchBillRuleMonthlyExecutor.class);
    @Autowired
    private GcOffSetAppOffsetService adjustService;
    @Autowired
    private GcBillFormulaEvalService billFormulaEvalService;
    @Autowired
    private FairValueBillService fairValueBillService;
    @Autowired
    private InvestBillService investBillService;
    @Autowired
    private SameCtrlChgOrgService sameCtrlChgOrgService;
    @Autowired
    private SameCtrlOffSetItemDao sameCtrlOffSetItemDao;
    @Autowired
    private UnionRuleService unionRuleService;
    @Autowired
    private GcCalcService gcCalcService;
    private static final String SQL_GET_INVESTMENT_ITEM_DATAEOS = " select %1$s \n from GC_INVESTBILLITEM ei  where %2$s ";

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
        this.gcCalcService.deleteAutoOffsetEntrysByRule(rule.getId(), env.getCalcArgments());
        SameCtrlOffsetCond cond = this.getSameCtrlOffsetCond(env.getCalcArgments());
        this.sameCtrlOffSetItemDao.deleteByRuleAndSrcType(cond, Arrays.asList(rule.getLocalizedName()), Arrays.asList(SameCtrlSrcTypeEnum.END_INVEST_CALC.getCode()));
        ((GcCalcInvestBillRuleMonthlyExecutor)SpringContextUtils.getBean(GcCalcInvestBillRuleMonthlyExecutor.class)).priorPeriodHandler(env, rule);
        this.calMerge(publicValueAdjustmentRule, env, cond);
    }

    private SameCtrlOffsetCond getSameCtrlOffsetCond(GcCalcArgmentsDTO calcArgments) {
        SameCtrlOffsetCond cond = new SameCtrlOffsetCond();
        BeanUtils.copyProperties(calcArgments, cond);
        cond.setMergeUnitCode(calcArgments.getOrgId());
        cond.setPeriodStr(calcArgments.getPeriodStr());
        cond.setOrgType(calcArgments.getOrgType());
        cond.setTaskId(calcArgments.getTaskId());
        cond.setSchemeId(calcArgments.getSchemeId());
        return cond;
    }

    private void calMerge(PublicValueAdjustmentRuleDTO publicValueAdjustmentRule, GcCalcEnvContext env, SameCtrlOffsetCond cond) {
        Map<String, List<DefaultTableEntity>> mFvcData;
        if (CollectionUtils.isEmpty((Collection)publicValueAdjustmentRule.getCreditItemList()) || CollectionUtils.isEmpty((Collection)publicValueAdjustmentRule.getDebitItemList())) {
            return;
        }
        GcCalcArgmentsDTO arg = env.getCalcArgments();
        YearPeriodObject yp = new YearPeriodObject(null, env.getCalcArgments().getPeriodStr());
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)env.getCalcArgments().getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        String priorPeriodStr = this.getPriorPeriod(arg.getPeriodStr());
        YearPeriodObject prioryp = new YearPeriodObject(null, priorPeriodStr);
        GcOrgCenterService priorTool = GcOrgPublicTool.getInstance((String)env.getCalcArgments().getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)prioryp);
        List<Object> investBillGroupDTOs = new ArrayList<GcInvestBillGroupDTO>();
        HashSet<String> investUnitSet = new HashSet<String>();
        Set<Object> investedUnitSet = new HashSet();
        HashMap<String, String> fvchSrcId2IdMap = new HashMap<String, String>();
        if (arg.getPreCalcFlag().get()) {
            String billCode = (String)arg.getExtendInfo().get("BILLCODE");
            DefaultTableEntity investBillEntity = InvestBillTool.getEntityByBillCode((String)billCode, (String)"GC_INVESTBILL");
            if (investBillEntity == null) {
                return;
            }
            GcInvestBillGroupDTO gcInvestBillGroupDTO = new GcInvestBillGroupDTO(investBillEntity, InvestBillTool.listItemByMasterId(Arrays.asList(investBillEntity.getId()), (String)"GC_INVESTBILLITEM"));
            investBillGroupDTOs.add(gcInvestBillGroupDTO);
            List<DefaultTableEntity> fvchSubItems = this.fairValueBillService.getFvchItemsByMasterSrcId((String)investBillEntity.getFieldValue("SRCID"), arg.getAcctYear());
            mFvcData = fvchSubItems.stream().collect(Collectors.groupingBy(item -> (String)item.getFieldValue("INVESTEDUNIT")));
            for (DefaultTableEntity item2 : fvchSubItems) {
                investUnitSet.add((String)item2.getFieldValue("UNITCODE"));
                investedUnitSet.add((String)item2.getFieldValue("INVESTEDUNIT"));
            }
        } else {
            investedUnitSet = this.getUnits(arg, orgTool);
            investedUnitSet.addAll(this.getUnits(arg, priorTool));
            investUnitSet = new HashSet();
            int acctYear = arg.getAcctYear();
            mFvcData = this.fairValueBillService.getFvchItemBills(acctYear, investUnitSet, fvchSrcId2IdMap);
            investBillGroupDTOs = this.getGcDnaGenericEntities(investedUnitSet, investUnitSet, acctYear, arg.getAcctPeriod());
            if (investBillGroupDTOs == null) {
                return;
            }
        }
        HashSet disposedFvchId = new HashSet();
        investBillGroupDTOs = investBillGroupDTOs.stream().filter(item -> {
            if (null == item.getMaster().getFieldValue("DISPOSEDATE")) {
                return true;
            }
            disposedFvchId.add(fvchSrcId2IdMap.get(item.getMaster().getFieldValue("SRCID")));
            return false;
        }).collect(Collectors.toList());
        HashMap investMastMap = new HashMap(32);
        HashMap investUnitMap = new HashMap(32);
        investBillGroupDTOs.forEach(item -> {
            String investUnit = String.valueOf(item.getMaster().getFieldValue("UNITCODE"));
            String investedUnit = String.valueOf(item.getMaster().getFieldValue("INVESTEDUNIT"));
            investMastMap.put(investUnit + "|" + investedUnit, item.getMaster());
            investUnitMap.put(investUnit + "|" + investedUnit, item);
        });
        Set initialMergeInvestMastIdCache = ((InvestBillInitialMergeCalcCache)SpringContextUtils.getBean(InvestBillInitialMergeCalcCache.class)).getInitialMergeCache(arg);
        HashMap ruleId2TitleMap = new HashMap();
        List rules = this.unionRuleService.selectRuleListBySchemeIdAndRuleTypes(arg.getSchemeId(), arg.getPeriodStr(), null);
        rules.forEach(item -> ruleId2TitleMap.put(item.getId(), item.getLocalizedName()));
        investedUnitSet.forEach(unitId -> {
            List<DefaultTableEntity> fvcData = (List<DefaultTableEntity>)mFvcData.get(unitId);
            if (!CollectionUtils.isEmpty(fvcData = this.filterFvcData(arg, fvcData, orgTool, initialMergeInvestMastIdCache, publicValueAdjustmentRule, investUnitMap, disposedFvchId))) {
                for (DefaultTableEntity fvc : fvcData) {
                    DefaultTableEntity fvcCopy = this.copyGcDnaGenericEntity(fvc);
                    boolean isCurrentMergeLevel = this.isCurrentMergeLevel(arg, yp, fvcCopy);
                    this.dealWithUnitReplace(publicValueAdjustmentRule, isCurrentMergeLevel, fvcCopy);
                    this.doOffset(fvcCopy, publicValueAdjustmentRule, env, investMastMap, isCurrentMergeLevel, cond, ruleId2TitleMap);
                }
            }
        });
    }

    private boolean isCurrentMergeLevel(GcCalcArgmentsDTO calcArgments, YearPeriodObject yp, DefaultTableEntity fvcCopy) {
        GcOrgCenterService currentInstance = GcOrgPublicTool.getInstance((String)calcArgments.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        String investUnit = String.valueOf(fvcCopy.getFieldValue("UNITCODE"));
        String investedUnit = String.valueOf(fvcCopy.getFieldValue("INVESTEDUNIT"));
        boolean isCurrentMergeLevel = this.checkMergeLevel(calcArgments, currentInstance, investUnit, investedUnit);
        return isCurrentMergeLevel;
    }

    private DefaultTableEntity copyGcDnaGenericEntity(DefaultTableEntity source) {
        if (source == null) {
            return null;
        }
        DefaultTableEntity copy = new DefaultTableEntity();
        BeanUtils.copyProperties(source, copy);
        Map fields = source.getFields();
        if (fields == null || fields.size() == 0) {
            return copy;
        }
        HashMap copyFields = new HashMap(fields.size());
        copyFields.putAll(fields);
        copy.resetFields(copyFields);
        return copy;
    }

    private void dealWithUnitReplace(PublicValueAdjustmentRuleDTO rule, boolean isCurrentMergeLevel, DefaultTableEntity fvcCopy) {
        Object sameCtrlChgOrgEO;
        String investedUnit = String.valueOf(fvcCopy.getFieldValue("INVESTEDUNIT"));
        if (!isCurrentMergeLevel && !CollectionUtils.isEmpty((Collection)rule.getDealWith()) && (sameCtrlChgOrgEO = fvcCopy.getFieldValue("VIRTUALUNIT")) != null) {
            SameCtrlChgOrgEO sameCtrlChgOrg = (SameCtrlChgOrgEO)sameCtrlChgOrgEO;
            fvcCopy.addFieldValue("ORIGINAL_INVESTEDUNIT", (Object)investedUnit);
            fvcCopy.addFieldValue("INVESTEDUNIT", (Object)sameCtrlChgOrg.getVirtualCode());
        }
    }

    private List<GcInvestBillGroupDTO> getGcDnaGenericEntities(Set<String> investedUnitSet, Set<String> investUnitSet, int acctYear, int period) {
        List<DefaultTableEntity> investMast = this.investBillService.getMastByInvestAndInvestedUnit(investUnitSet, investedUnitSet, acctYear, period);
        if (investMast == null) {
            return null;
        }
        Map<String, DefaultTableEntity> investmentMap = investMast.stream().collect(Collectors.toMap(DefaultTableEntity::getId, GcInvestBillEO -> GcInvestBillEO));
        List<DefaultTableEntity> investmentItemDatas = this.getInvestmentItemDatas(investmentMap.keySet());
        Map<String, List<DefaultTableEntity>> investmentItemMap = null;
        if (investmentItemDatas != null && investmentItemDatas.size() > 0) {
            investmentItemMap = investmentItemDatas.stream().collect(Collectors.groupingBy(o -> String.valueOf(o.getFieldValue("MASTERID"))));
        }
        Map<String, List<DefaultTableEntity>> finalInvestmentItemMap = investmentItemMap;
        ArrayList<GcInvestBillGroupDTO> groups = new ArrayList<GcInvestBillGroupDTO>();
        investmentMap.entrySet().stream().forEach(entry -> {
            String id = (String)entry.getKey();
            DefaultTableEntity master = (DefaultTableEntity)entry.getValue();
            List items = null;
            if (finalInvestmentItemMap != null) {
                items = (List)finalInvestmentItemMap.get(id);
            }
            GcInvestBillGroupDTO gcInvestmentGroup = new GcInvestBillGroupDTO(master, items, true);
            groups.add(gcInvestmentGroup);
        });
        return groups;
    }

    private void doOffset(DefaultTableEntity fvc, PublicValueAdjustmentRuleDTO publicValueAdjustmentRule, GcCalcEnvContext env, Map<String, DefaultTableEntity> investMastMap, boolean isCurrentMergeLevel, SameCtrlOffsetCond cond, Map<String, String> ruleId2TitleMap) {
        List<GcOffSetVchrItemDTO> offsetItems = this.getOffsetItems(fvc, publicValueAdjustmentRule, env, investMastMap);
        if (offsetItems.size() < 2 && !env.getCalcArgments().getPreCalcFlag().get()) {
            return;
        }
        if (!GcCalcAmtCheckUtil.checkAndDistributionAmt((AbstractUnionRule)publicValueAdjustmentRule, offsetItems) && !env.getCalcArgments().getPreCalcFlag().get()) {
            this.logger.debug("\u516c\u5141\u4ef7\u503c\u8c03\u6574\u89c4\u5219\u5408\u5e76\u8ba1\u7b97\u672a\u6838\u5bf9\u6210\u529f. \u5355\u4f4d\uff1a" + env.getCalcArgments().getOrgId() + " \u65f6\u671f\uff1a" + env.getCalcArgments().getPeriodStr());
            return;
        }
        if (isCurrentMergeLevel) {
            GcOffSetVchrDTO offSetItemDTO = new GcOffSetVchrDTO();
            offSetItemDTO.setItems(offsetItems);
            offSetItemDTO.setConsFormulaCalcType("autoFlag");
            this.adjustService.save(offSetItemDTO);
            return;
        }
        Object sameCtrlChgOrgEO = fvc.getFieldValue("VIRTUALUNIT");
        if (sameCtrlChgOrgEO == null) {
            return;
        }
        SameCtrlChgOrgEO sameCtrlChgOrg = (SameCtrlChgOrgEO)sameCtrlChgOrgEO;
        SameCtrlOffsetCond copyCond = this.initSameCtrlOffsetCondBySameCtrlChgOrgEO(sameCtrlChgOrg, cond);
        this.sameCtrlOffSetItemDao.saveAll(this.convertOffsetItemToSameCtrlList(offsetItems, copyCond, ruleId2TitleMap));
        ((GcCalcRuleExecuteStateDTO)env.getRuleStateMap().get(publicValueAdjustmentRule.getId())).addCreateOffsetItemCountValue(Integer.valueOf(CollectionUtils.isEmpty(offsetItems) ? 0 : offsetItems.size()));
        if (env.getCalcArgments().getPreCalcFlag().get()) {
            env.getCalcContextExpandVariableCenter().getPreCalcOffSetItems().addAll(offsetItems);
        }
    }

    private List<SameCtrlOffSetItemEO> convertOffsetItemToSameCtrlList(List<GcOffSetVchrItemDTO> itemDTOList, SameCtrlOffsetCond cond, Map<String, String> ruleId2TitleMap) {
        String mRecid = UUIDOrderUtils.newUUIDStr();
        ArrayList<SameCtrlOffSetItemEO> sameCtrlOffSetItemList = new ArrayList<SameCtrlOffSetItemEO>();
        double sumOffsetValue = 0.0;
        for (GcOffSetVchrItemDTO item : itemDTOList) {
            SameCtrlOffSetItemEO sameCtrlOffSetItemEO = new SameCtrlOffSetItemEO();
            BeanUtils.copyProperties(item, sameCtrlOffSetItemEO);
            sameCtrlOffSetItemEO.setId(UUIDOrderUtils.newUUIDStr());
            sameCtrlOffSetItemEO.setSrcId(item.getId());
            sameCtrlOffSetItemEO.setmRecid(mRecid);
            sameCtrlOffSetItemEO.setCreateTime(Calendar.getInstance().getTime());
            sameCtrlOffSetItemEO.setElmMode(Integer.valueOf(OffsetElmModeEnum.AUTO_ITEM.getValue()));
            sameCtrlOffSetItemEO.setDefaultPeriod(cond.getPeriodStr());
            sameCtrlOffSetItemEO.setTaskId(cond.getTaskId());
            sameCtrlOffSetItemEO.setSchemeId(cond.getSchemeId());
            sameCtrlOffSetItemEO.setUnitCode(item.getUnitId());
            sameCtrlOffSetItemEO.setOppUnitCode(item.getOppUnitId());
            YearPeriodObject yp = new YearPeriodObject(null, cond.getPeriodStr());
            GcOrgCenterService orgCenterTool = GcOrgPublicTool.getInstance((String)cond.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            GcOrgCacheVO mergeUnitVo = orgCenterTool.getOrgByCode(cond.getMergeUnitCode());
            sameCtrlOffSetItemEO.setInputUnitParents(mergeUnitVo.getParentStr());
            sameCtrlOffSetItemEO.setSameParentCode(cond.getSameParentCode());
            sameCtrlOffSetItemEO.setUnitChangeYear(Integer.valueOf(DateUtils.getYearOfDate((Date)cond.getChangeDate())));
            sameCtrlOffSetItemEO.setSameCtrlChgId(cond.getSameCtrlChgId());
            sameCtrlOffSetItemEO.setSameCtrlSrcType(SameCtrlSrcTypeEnum.END_INVEST_CALC.getCode());
            sameCtrlOffSetItemEO.setOrgType(cond.getOrgType());
            sameCtrlOffSetItemEO.setRuleTitle(ruleId2TitleMap.get(item.getRuleId()));
            sameCtrlOffSetItemEO.setOrient(item.getOrient().getValue());
            sameCtrlOffSetItemEO.setInputUnitCode(cond.getMergeUnitCode());
            int orient = sameCtrlOffSetItemEO.getOrient();
            if (orient == OrientEnum.D.getValue()) {
                sameCtrlOffSetItemEO.setOffSetDebit(item.getOffSetDebit());
            }
            if (orient == OrientEnum.C.getValue()) {
                sameCtrlOffSetItemEO.setOffSetCredit(item.getOffSetCredit());
            }
            this.sameCtrlOffSetItemDao.checkItemDTO(sameCtrlOffSetItemEO);
            sumOffsetValue = com.jiuqi.common.base.util.NumberUtils.sum((double)sumOffsetValue, (double)com.jiuqi.common.base.util.NumberUtils.sub((Double)sameCtrlOffSetItemEO.getOffSetDebit(), (Double)sameCtrlOffSetItemEO.getOffSetCredit()));
            sameCtrlOffSetItemList.add(sameCtrlOffSetItemEO);
        }
        return sameCtrlOffSetItemList;
    }

    private SameCtrlOffsetCond initSameCtrlOffsetCondBySameCtrlChgOrgEO(SameCtrlChgOrgEO sameCtrlChgOrgEO, SameCtrlOffsetCond cond) {
        SameCtrlOffsetCond copyCond = new SameCtrlOffsetCond();
        BeanUtils.copyProperties(cond, copyCond);
        copyCond.setSameParentCode(sameCtrlChgOrgEO.getSameParentCode());
        copyCond.setChangeDate(sameCtrlChgOrgEO.getChangeDate());
        copyCond.setSameCtrlChgId(sameCtrlChgOrgEO.getId());
        return copyCond;
    }

    private List<GcOffSetVchrItemDTO> getOffsetItems(DefaultTableEntity fvc, PublicValueAdjustmentRuleDTO publicValueAdjustmentRule, GcCalcEnvContext env, Map<String, DefaultTableEntity> investMastMap) {
        List debitItemList = publicValueAdjustmentRule.getDebitItemList();
        List creditItemList = publicValueAdjustmentRule.getCreditItemList();
        List<GcOffSetVchrItemDTO> offsets = this.createOffsets(fvc, debitItemList, creditItemList, publicValueAdjustmentRule, env, investMastMap);
        return offsets;
    }

    private Set<String> getUnits(GcCalcArgmentsDTO arg, GcOrgCenterService tool) {
        HashSet<String> unitIds = new HashSet<String>();
        GcOrgCacheVO orgByCode = tool.getOrgByCode(arg.getOrgId());
        if (orgByCode == null) {
            return unitIds;
        }
        List childrenOrg = tool.getOrgChildrenTree(arg.getOrgId());
        if (CollectionUtils.isEmpty((Collection)childrenOrg)) {
            return unitIds;
        }
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

    private List<GcOffSetVchrItemDTO> createOffsets(DefaultTableEntity fvc, List<PublicValueAdjustmentRuleDTO.Item> debitItemList, List<PublicValueAdjustmentRuleDTO.Item> creditItemList, PublicValueAdjustmentRuleDTO publicValueAdjustmentRule, GcCalcEnvContext env, Map<String, DefaultTableEntity> investMastMap) {
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
            if (!datum2.getCode().equals(item.getAssetTypeCode()) && !parents.contains(item.getAssetTypeCode()) || (offsetItem_d = this.createOffsetItem(env, investMastMap, fvc, item, OrientEnum.D, publicValueAdjustmentRule, arg)).getDebit() == 0.0 && offsetItem_d.getCredit() == 0.0 && !arg.getPreCalcFlag().get()) continue;
            offsets_d.add(offsetItem_d);
        }
        for (PublicValueAdjustmentRuleDTO.Item item : creditItemList) {
            GcOffSetVchrItemDTO offsetItem_c;
            assetType = (String)fvc.getFieldValue("ASSETTYPE");
            filted = baseData.stream().filter(datum -> datum.getCode().equals(assetType)).findAny();
            if (!filted.isPresent()) continue;
            BaseDataVO datum2 = filted.get();
            List<Object> list = parents = datum2.getParents() == null ? new ArrayList() : Arrays.asList(datum2.getParents());
            if (!datum2.getCode().equals(item.getAssetTypeCode()) && !parents.contains(item.getAssetTypeCode()) || (offsetItem_c = this.createOffsetItem(env, investMastMap, fvc, item, OrientEnum.C, publicValueAdjustmentRule, arg)).getDebit() == 0.0 && offsetItem_c.getCredit() == 0.0 && !arg.getPreCalcFlag().get()) continue;
            offsets_c.add(offsetItem_c);
        }
        if (phsOffset == null) {
            phsOffset = this.createOffsetItemWithPHS(offsets_d, offsets_c, phsRuleItem_d, phsRuleItem_c, fvc, publicValueAdjustmentRule, env, investMastMap);
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

    private GcOffSetVchrItemDTO createOffsetItem(GcCalcEnvContext env, Map<String, DefaultTableEntity> investMastMap, DefaultTableEntity fvcData, PublicValueAdjustmentRuleDTO.Item item, OrientEnum orient, PublicValueAdjustmentRuleDTO publicValueAdjustmentRule, GcCalcArgmentsDTO calcArgmentsDTO) {
        String defaultDimOrgId;
        GcOffSetVchrItemDTO offsetVchrItem = new GcOffSetVchrItemDTO();
        offsetVchrItem.setId(UUID.randomUUID().toString());
        offsetVchrItem.setRecver(Long.valueOf(0L));
        offsetVchrItem.setOrgType(GCOrgTypeEnum.NONE.getCode());
        offsetVchrItem.setAcctYear(calcArgmentsDTO.getAcctYear());
        offsetVchrItem.setAcctPeriod(calcArgmentsDTO.getAcctPeriod());
        offsetVchrItem.setDefaultPeriod(calcArgmentsDTO.getPeriodStr());
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
        String investUnit = (String)fvcData.getFieldValue("UNITCODE");
        String investedUnit = String.valueOf(fvcData.getFieldValue("INVESTEDUNIT"));
        String virtualInvestedUnit = this.getInvestedUnitByRule(fvcData);
        Object originalInvestedUnit = fvcData.getFieldValue("ORIGINAL_INVESTEDUNIT");
        DefaultTableEntity investData = investMastMap.get(investUnit + "|" + (originalInvestedUnit != null ? originalInvestedUnit.toString() : investedUnit));
        String offsetGroupId = fvcData.getId();
        offsetVchrItem.setSrcOffsetGroupId(offsetGroupId);
        if (InvestmentUnitEnum.INVESTED_UNIT == item.getInvestmentUnit()) {
            offsetVchrItem.setUnitId(virtualInvestedUnit);
            offsetVchrItem.setOppUnitId(investUnit);
            defaultDimOrgId = investedUnit;
        } else {
            offsetVchrItem.setUnitId(investUnit);
            offsetVchrItem.setOppUnitId(virtualInvestedUnit);
            defaultDimOrgId = investUnit;
        }
        this.initDimensions(dset, offsetVchrItem, fvcData, item, env, investUnit, investedUnit, defaultDimOrgId, investData);
        offsetVchrItem.setSelectAdjustCode(env.getCalcArgments().getSelectAdjustCode());
        return offsetVchrItem;
    }

    private String getInvestedUnitByRule(DefaultTableEntity fvcData) {
        String investedUnit = String.valueOf(fvcData.getFieldValue("INVESTEDUNIT"));
        Object sameCtrlChgOrgEO = fvcData.getFieldValue("VIRTUALUNIT");
        if (sameCtrlChgOrgEO == null) {
            return investedUnit;
        }
        SameCtrlChgOrgEO sameCtrlChgOrg = (SameCtrlChgOrgEO)sameCtrlChgOrgEO;
        return sameCtrlChgOrg.getChangedCode();
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

    private void initDimensions(DimensionValueSet dimensionValueSet, GcOffSetVchrItemDTO offSetVchrItemDTO, DefaultTableEntity fvcData, PublicValueAdjustmentRuleDTO.Item item, GcCalcEnvContext env, String investUnit, String investedUnit, String defaultDimOrgId, DefaultTableEntity investMast) {
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

    private GcOffSetVchrItemDTO createOffsetItemWithPHS(Collection<GcOffSetVchrItemDTO> offsets_d, Collection<GcOffSetVchrItemDTO> offsets_c, List<PublicValueAdjustmentRuleDTO.Item> phsRuleItem_d, List<PublicValueAdjustmentRuleDTO.Item> phsRuleItem_c, DefaultTableEntity fvc, PublicValueAdjustmentRuleDTO publicValueAdjustmentRule, GcCalcEnvContext env, Map<String, DefaultTableEntity> investMastMap) {
        GcOffSetVchrItemDTO offsetItem = null;
        for (PublicValueAdjustmentRuleDTO.Item fetchFormula : phsRuleItem_d) {
            double debit = this.calcPHSDiff(offsets_d, offsets_c, OrientEnum.D);
            env.getCalcContextExpandVariableCenter().setPhsValue(Double.valueOf(debit));
            offsetItem = this.createOffsetItem(env, investMastMap, fvc, fetchFormula, OrientEnum.D, publicValueAdjustmentRule, env.getCalcArgments());
            if (NumberUtils.isZreo((Double)offsetItem.getOffSetDebit())) continue;
            offsetItem.setMemo(GcI18nUtil.getMessage((String)"gc.calculate.bill.phs.memo"));
            return offsetItem;
        }
        for (PublicValueAdjustmentRuleDTO.Item fetchFormula : phsRuleItem_c) {
            double crebit = this.calcPHSDiff(offsets_d, offsets_c, OrientEnum.C);
            env.getCalcContextExpandVariableCenter().setPhsValue(Double.valueOf(crebit));
            offsetItem = this.createOffsetItem(env, investMastMap, fvc, fetchFormula, OrientEnum.C, publicValueAdjustmentRule, env.getCalcArgments());
            if (NumberUtils.isZreo((Double)offsetItem.getOffSetDebit()) && !env.getCalcArgments().getPreCalcFlag().get()) continue;
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

    private List<DefaultTableEntity> filterFvcData(GcCalcArgmentsDTO arg, List<DefaultTableEntity> fvcData, GcOrgCenterService tool, Set<String> initialMergeInvestMastIdCache, PublicValueAdjustmentRuleDTO rule, Map<String, GcInvestBillGroupDTO> investUnitMap, Set<String> disposedFvchId) {
        Date[] periodDateRegion;
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
        List initialMerge = rule.getInitialMerge();
        List goingConcern = rule.getGoingConcern();
        List dealWith = rule.getDealWith();
        PeriodWrapper periodWrapper = new PeriodWrapper(arg.getPeriodStr());
        DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
        try {
            periodDateRegion = defaultPeriodAdapter.getPeriodDateRegion(periodWrapper);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.bill.invest.rule.scope.error") + e.getMessage(), (Throwable)e);
        }
        Date startPeriod = periodDateRegion[0];
        Date endPeriod = periodDateRegion[1];
        List sameCtrlChgOrgEOList = Collections.emptyList();
        Map unit2SamCtrlChgOrgMap = sameCtrlChgOrgEOList.stream().collect(Collectors.toMap(SameCtrlChgOrgEO::getChangedCode, Function.identity(), (v1, v2) -> v2));
        return fvcData.stream().filter(datum -> {
            if (disposedFvchId.contains(datum.getFieldValue("MASTERID"))) {
                return false;
            }
            String investUnitCode = (String)datum.getFieldValue("UNITCODE");
            String investUnitedCode = (String)datum.getFieldValue("INVESTEDUNIT");
            Calendar appendTime = GregorianCalendar.getInstance();
            Object bizDate = datum.getFieldValue("BIZDATE");
            if (bizDate == null) {
                throw new BusinessRuntimeException(String.format("\u6295\u8d44\u5355\u4f4d[%1s]\uff0c\u88ab\u6295\u8d44\u5355\u4f4d[%2s]\u5bf9\u5e94\u7684\u516c\u5141\u53f0\u8d26\u7684\u589e\u52a0\u65e5\u671f\u5b57\u6bb5\u4e0d\u80fd\u4e3a\u7a7a", investUnitCode, investUnitedCode));
            }
            appendTime.setTime((Date)bizDate);
            if (!appendTime.before(argCalendar)) {
                return false;
            }
            GcInvestBillGroupDTO investBill = (GcInvestBillGroupDTO)investUnitMap.get(investUnitCode + "|" + investUnitedCode);
            datum.addFieldValue("CURRENCYCODE", investBill.getMaster().getFieldValue("CURRENCYCODE"));
            if (null == investBill) {
                return false;
            }
            String mastId = investBill.getMaster().getId();
            if (!this.checkMergeLevel(arg, tool, investUnitCode, investUnitedCode)) {
                SameCtrlChgOrgEO sameCtrlChgOrgEO = this.getVirtualUnit(unit2SamCtrlChgOrgMap, investUnitedCode);
                if (sameCtrlChgOrgEO != null) {
                    investUnitedCode = sameCtrlChgOrgEO.getVirtualCode();
                    datum.addFieldValue("VIRTUALUNIT", (Object)sameCtrlChgOrgEO);
                    investBill.getMaster().addFieldValue("VIRTUALUNIT", (Object)sameCtrlChgOrgEO);
                }
                if (!this.checkMergeLevel(arg, tool, investUnitCode, investUnitedCode)) {
                    return false;
                }
            }
            return !this.monthlyIncrementFilterHander(initialMergeInvestMastIdCache, initialMerge, goingConcern, dealWith, startPeriod, endPeriod, investBill, mastId);
        }).collect(Collectors.toList());
    }

    private boolean monthlyIncrementFilterHander(Set<String> initialMergeInvestMastIdCache, List<String> initialMerge, List<String> goingConcern, List<String> dealWith, Date startPeriod, Date endPeriod, GcInvestBillGroupDTO investBill, String mastId) {
        if (!CollectionUtils.isEmpty(initialMerge) && !this.conformInitialMerge(investBill, initialMerge, startPeriod, endPeriod, initialMergeInvestMastIdCache)) {
            return true;
        }
        return !CollectionUtils.isEmpty(goingConcern) ? !this.conformChangeScenario(investBill, goingConcern, startPeriod, endPeriod, false) : !CollectionUtils.isEmpty(dealWith) && !this.conformChangeScenario(investBill, dealWith, startPeriod, endPeriod, true);
    }

    private boolean conformChangeScenario(GcInvestBillGroupDTO investBill, List<String> changeScenarioList, Date startPeriod, Date endPeriod, boolean isDealWith) {
        HashSet<String> changeScenarioSet = new HashSet<String>(changeScenarioList);
        List<DefaultTableEntity> items = investBill.getItems();
        Object sameCtrlChgOrgEO = investBill.getMaster().getFieldValue("VIRTUALUNIT");
        if (CollectionUtils.isEmpty(items)) {
            return false;
        }
        for (DefaultTableEntity item : items) {
            Date changeDate;
            Object changeScenarioObj = item.getFieldValue("CHANGESCENARIO");
            if (!changeScenarioSet.contains(changeScenarioObj)) continue;
            Object changeDateObj = item.getFieldValue("CHANGEDATE");
            if (isDealWith) {
                if (sameCtrlChgOrgEO != null) {
                    SameCtrlChgOrgEO sameCtrlChgOrg = (SameCtrlChgOrgEO)sameCtrlChgOrgEO;
                    changeDateObj = sameCtrlChgOrg.getChangeDate();
                } else {
                    changeDateObj = null;
                }
            }
            if (changeDateObj == null || (changeDate = (Date)changeDateObj).compareTo(startPeriod) < 0 || changeDate.compareTo(endPeriod) > 0) continue;
            return true;
        }
        return false;
    }

    private boolean conformInitialMerge(GcInvestBillGroupDTO investBill, List<String> initialMerge, Date startPeriod, Date endPeriod, Set<String> initialMergeInvestMastIdSet) {
        HashSet<String> initialMergeSet = new HashSet<String>(initialMerge);
        List<DefaultTableEntity> items = investBill.getItems();
        if (CollectionUtils.isEmpty(items)) {
            return false;
        }
        boolean hasCalc = !initialMergeInvestMastIdSet.contains(investBill.getMaster().getId());
        for (DefaultTableEntity item : items) {
            Date changeDate;
            Object changeDateObj;
            Object changeScenarioObj = item.getFieldValue("CHANGESCENARIO");
            if (!initialMergeSet.contains(changeScenarioObj) || (changeDateObj = item.getFieldValue("CHANGEDATE")) == null || DateUtils.getYearOfDate((Date)(changeDate = (Date)changeDateObj)) != DateUtils.getYearOfDate((Date)startPeriod) || changeDate.compareTo(endPeriod) > 0 || changeDate.compareTo(startPeriod) < 0 && hasCalc) continue;
            return true;
        }
        return false;
    }

    private boolean checkMergeLevel(GcCalcArgmentsDTO arg, GcOrgCenterService tool, String investUnitCode, String investUnitedCode) {
        GcOrgCacheVO investUnit = tool.getOrgByCode(investUnitCode);
        GcOrgCacheVO investUnited = tool.getOrgByCode(investUnitedCode);
        if (investUnit == null || investUnited == null) {
            return false;
        }
        GcOrgCacheVO commonUnit = tool.getCommonUnit(investUnit, investUnited);
        return commonUnit != null && Objects.equals(commonUnit.getId(), arg.getOrgId());
    }

    private SameCtrlChgOrgEO getVirtualUnit(Map<String, SameCtrlChgOrgEO> unit2SamCtrlChgOrgMap, String investedUnit) {
        if (StringUtils.isEmpty((String)investedUnit)) {
            return null;
        }
        return unit2SamCtrlChgOrgMap.get(investedUnit);
    }

    private String getPriorPeriod(String periodStr) {
        PeriodWrapper periodWrapper = new PeriodWrapper(periodStr);
        DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
        defaultPeriodAdapter.priorPeriod(periodWrapper);
        return periodWrapper.toString();
    }

    private List<DefaultTableEntity> getInvestmentItemDatas(Set<String> investmentIds) {
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_INVESTBILLITEM", (String)"ei");
        String condiSql = SqlUtils.getConditionOfIdsUseOr(investmentIds, (String)"ei.masterId");
        String formatSQL = String.format(SQL_GET_INVESTMENT_ITEM_DATAEOS, columns, condiSql);
        return InvestBillTool.queryBySql((String)formatSQL, (Object[])new Object[0]);
    }
}

