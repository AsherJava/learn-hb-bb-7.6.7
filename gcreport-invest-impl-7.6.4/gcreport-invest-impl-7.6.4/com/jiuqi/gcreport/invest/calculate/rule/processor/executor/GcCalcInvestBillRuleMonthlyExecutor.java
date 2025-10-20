/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
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
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.impl.function.GcFormulaThreadContext
 *  com.jiuqi.gcreport.offsetitem.dao.impl.GcOffsetVchrQueryImpl
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.init.service.GcOffSetInitService
 *  com.jiuqi.gcreport.offsetitem.init.vo.OffsetItemInitQueryParamsVO
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
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
 *  com.jiuqi.gcreport.unionrule.dto.AbstractInvestmentRule
 *  com.jiuqi.gcreport.unionrule.dto.AbstractInvestmentRule$Item
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.enums.InvestmentUnitEnum
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 *  javax.validation.constraints.NotNull
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.invest.calculate.rule.processor.executor;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
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
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.invest.calculate.rule.processor.executor.GcCalcInvestBillRuleExecutor;
import com.jiuqi.gcreport.invest.formula.service.GcBillFormulaEvalService;
import com.jiuqi.gcreport.invest.investbill.dto.GcInvestBillGroupDTO;
import com.jiuqi.gcreport.nr.impl.function.GcFormulaThreadContext;
import com.jiuqi.gcreport.offsetitem.dao.impl.GcOffsetVchrQueryImpl;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.init.service.GcOffSetInitService;
import com.jiuqi.gcreport.offsetitem.init.vo.OffsetItemInitQueryParamsVO;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
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
import com.jiuqi.gcreport.unionrule.dto.AbstractInvestmentRule;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.enums.InvestmentUnitEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GcCalcInvestBillRuleMonthlyExecutor {
    private Logger logger = LoggerFactory.getLogger(GcCalcInvestBillRuleExecutor.class);
    @Autowired
    private GcOffSetAppOffsetService offSetItemAdjustService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private GcOffSetItemAdjustCoreService offsetCoreService;
    @Autowired
    private GcOffSetInitService gcOffSetInitService;
    @Autowired
    private GcOffSetAppOffsetService adjustingEntryService;
    @Autowired
    private SameCtrlOffSetItemDao sameCtrlOffSetItemDao;
    @Autowired
    private UnionRuleService unionRuleService;
    @Autowired
    private SameCtrlChgOrgService sameCtrlChgOrgService;
    @Autowired
    private GcCalcService gcCalcService;
    @Autowired
    private GcBillFormulaEvalService billFormulaEvalService;

    @Transactional(propagation=Propagation.NESTED, rollbackFor={Exception.class})
    public void execute(@NotNull AbstractInvestmentRule rule, GcCalcEnvContext env, List<GcInvestBillGroupDTO> datas) {
        this.doExecute(env, rule, datas);
        if (env.getCalcArgments().getPreCalcFlag().get()) {
            throw new BusinessRuntimeException("\u5408\u5e76\u8ba1\u7b97\u9884\u6267\u884c\u901a\u8fc7\u629b\u5f02\u5e38\u7684\u65b9\u5f0f\u6765\u8fdb\u884c\u4e0d\u63d0\u4ea4\u4e8b\u52a1\u64cd\u4f5c");
        }
    }

    private boolean doExecute(GcCalcEnvContext env, @NotNull AbstractInvestmentRule rule, List<GcInvestBillGroupDTO> datas) {
        Date[] periodDateRegion;
        ((GcCalcRuleExecuteStateDTO)env.getRuleStateMap().get(rule.getId())).addCreateOffsetItemCountValue(Integer.valueOf(0));
        GcCalcArgmentsDTO calcArgments = env.getCalcArgments();
        this.deleteHistoryAdjustOffSetItems(env, rule);
        SameCtrlOffsetCond cond = this.getSameCtrlOffsetCond(calcArgments);
        this.sameCtrlOffSetItemDao.deleteByRuleAndSrcType(cond, Arrays.asList(rule.getLocalizedName()), Arrays.asList(SameCtrlSrcTypeEnum.END_INVEST_CALC.getCode()));
        this.priorPeriodHandler(env, (AbstractUnionRule)rule);
        DimensionValueSet dset = DimensionUtils.generateDimSet(null, (Object)calcArgments.getPeriodStr(), (Object)calcArgments.getCurrency(), (Object)calcArgments.getOrgType(), (String)calcArgments.getSelectAdjustCode(), (String)calcArgments.getTaskId());
        List<GcInvestBillGroupDTO> acceptDatas = !StringUtils.isEmpty((String)rule.getRuleCondition()) ? datas.stream().filter(data -> {
            boolean isAccept = false;
            try {
                dset.setValue("MD_ORG", data.getMaster().getFieldValue("INVESTEDUNIT"));
                isAccept = this.billFormulaEvalService.checkInvestBillData(env, dset, rule.getRuleCondition(), (GcInvestBillGroupDTO)data);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.bill.invest.rule.condition.error", (Object[])new Object[]{rule.getLocalizedName(), rule.getRuleCondition()}) + e.getMessage());
            }
            if (!isAccept) {
                this.logger.info("\u5408\u5e76\u8ba1\u7b97\u6295\u8d44\u89c4\u5219[{}]\uff0c\u6295\u8d44\u6570\u636e[ID:{}]\u4e0d\u6ee1\u8db3\u9002\u7528\u6761\u4ef6[{}]\uff0c\u4e0d\u751f\u6210\u62b5\u9500\u5206\u5f55\u3002", rule.getLocalizedName(), data.getMaster().getId(), rule.getRuleCondition());
            }
            return isAccept;
        }).collect(Collectors.toList()) : datas;
        PeriodWrapper periodWrapper = new PeriodWrapper(calcArgments.getPeriodStr());
        DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
        try {
            periodDateRegion = defaultPeriodAdapter.getPeriodDateRegion(periodWrapper);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.bill.invest.rule.scope.error") + e.getMessage(), (Throwable)e);
        }
        Date startPeriod = periodDateRegion[0];
        Date endPeriod = periodDateRegion[1];
        Set initialMergeInvestMastIdCache = ((InvestBillInitialMergeCalcCache)SpringContextUtils.getBean(InvestBillInitialMergeCalcCache.class)).getInitialMergeCache(calcArgments);
        Set priorMastIdSetCache = ((InvestBillInitialMergeCalcCache)SpringContextUtils.getBean(InvestBillInitialMergeCalcCache.class)).getPriorMastIdSetCache(calcArgments);
        acceptDatas = this.monthlyIncrementFilterHander(acceptDatas, rule, startPeriod, endPeriod, initialMergeInvestMastIdCache, priorMastIdSetCache);
        List<GcOffSetVchrDTO> alloffsetDTOList = Collections.synchronizedList(new ArrayList());
        HashMap sameCtrlOffsetMap = new HashMap();
        try {
            GcFormulaThreadContext.enableCache();
            YearPeriodObject yp = new YearPeriodObject(null, calcArgments.getPeriodStr());
            Date calcDate = yp.formatYP().getEndDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(calcDate);
            int currentMonth = calendar.get(2);
            if (!CollectionUtils.isEmpty(acceptDatas)) {
                for (GcInvestBillGroupDTO acceptData : acceptDatas) {
                    acceptData = this.copyData(acceptData);
                    Object sameCtrlChgOrgEO = acceptData.getMaster().getFieldValue("VIRTUALUNIT");
                    boolean isCurrentMergeLevel = this.isCurrentMergeLevel(calcArgments, yp, acceptData);
                    this.dealWithUnitReplace(rule, isCurrentMergeLevel, acceptData);
                    if (this.isDispose(sameCtrlChgOrgEO, currentMonth, calendar)) continue;
                    int i = 0;
                    ArrayList<DefaultTableEntity> allItems = new ArrayList<DefaultTableEntity>(acceptData.getItems());
                    do {
                        GcOffSetVchrDTO itemDTO;
                        ArrayList<DefaultTableEntity> items = new ArrayList<DefaultTableEntity>();
                        if (!CollectionUtils.isEmpty(acceptData.getItems())) {
                            items.add((DefaultTableEntity)allItems.get(i++));
                            this.sortItems(((DefaultTableEntity)items.get(0)).getId(), acceptData.getItems());
                            this.sortItems(((DefaultTableEntity)items.get(0)).getId(), acceptData.getCurrYearItems());
                        }
                        if ((itemDTO = this.executeSingleAcceptData(rule, env, new GcInvestBillGroupDTO(acceptData.getMaster(), items), acceptData)) == null) continue;
                        if (sameCtrlChgOrgEO == null) {
                            alloffsetDTOList.add(itemDTO);
                            continue;
                        }
                        SameCtrlChgOrgEO sameCtrlChgOrg = (SameCtrlChgOrgEO)sameCtrlChgOrgEO;
                        if (!sameCtrlOffsetMap.containsKey(sameCtrlChgOrg)) {
                            sameCtrlOffsetMap.put(sameCtrlChgOrg, new ArrayList());
                        }
                        ((List)sameCtrlOffsetMap.get(sameCtrlChgOrg)).add(itemDTO);
                    } while (acceptData.getItems() != null && i < acceptData.getItems().size());
                }
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.bill.invest.rule.error", (Object[])new Object[]{rule.getLocalizedName()}) + e.getMessage(), (Throwable)e);
        }
        finally {
            GcFormulaThreadContext.releaseCache();
        }
        if (!CollectionUtils.isEmpty(alloffsetDTOList)) {
            alloffsetDTOList.forEach(offsetDTO -> offsetDTO.setConsFormulaCalcType("autoFlag"));
            this.offSetItemAdjustService.batchSave(alloffsetDTOList);
            alloffsetDTOList.forEach(gcOffSetVchrDTO -> {
                List items = gcOffSetVchrDTO.getItems();
                ((GcCalcRuleExecuteStateDTO)env.getRuleStateMap().get(rule.getId())).addCreateOffsetItemCountValue(Integer.valueOf(CollectionUtils.isEmpty((Collection)items) ? 0 : items.size()));
                if (env.getCalcArgments().getPreCalcFlag().get()) {
                    env.getCalcContextExpandVariableCenter().getPreCalcOffSetItems().addAll(items);
                }
            });
        }
        if (sameCtrlOffsetMap.size() == 0) {
            return true;
        }
        List rules = this.unionRuleService.selectRuleListBySchemeIdAndRuleTypes(calcArgments.getSchemeId(), calcArgments.getPeriodStr(), null);
        Map<String, String> ruleId2TitleMap = rules.stream().collect(Collectors.toMap(AbstractUnionRule::getId, AbstractUnionRule::getLocalizedName, (o1, o2) -> o2));
        ArrayList<SameCtrlOffSetItemEO> sameCtrlOffSetItemList = new ArrayList<SameCtrlOffSetItemEO>();
        for (SameCtrlChgOrgEO sameCtrlChgOrgEO : sameCtrlOffsetMap.keySet()) {
            List gcOffSetVchrDTOS = (List)sameCtrlOffsetMap.get(sameCtrlChgOrgEO);
            for (GcOffSetVchrDTO offSetVchrDTO : gcOffSetVchrDTOS) {
                SameCtrlOffsetCond copyCond = this.initSameCtrlOffsetCondBySameCtrlChgOrgEO(sameCtrlChgOrgEO, cond);
                sameCtrlOffSetItemList.addAll(this.convertOffsetItemToSameCtrlList(offSetVchrDTO, copyCond, ruleId2TitleMap));
            }
        }
        this.sameCtrlOffSetItemDao.saveAll(sameCtrlOffSetItemList);
        return false;
    }

    private List<SameCtrlOffSetItemEO> convertOffsetItemToSameCtrlList(GcOffSetVchrDTO group, SameCtrlOffsetCond cond, Map<String, String> ruleId2TitleMap) {
        String mRecid = UUIDOrderUtils.newUUIDStr();
        ArrayList<SameCtrlOffSetItemEO> sameCtrlOffSetItemList = new ArrayList<SameCtrlOffSetItemEO>();
        double sumOffsetValue = 0.0;
        for (GcOffSetVchrItemDTO item : group.getItems()) {
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

    private boolean isCurrentMergeLevel(GcCalcArgmentsDTO calcArgments, YearPeriodObject yp, GcInvestBillGroupDTO acceptData) {
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)calcArgments.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        String investUnit = String.valueOf(acceptData.getMaster().getFieldValue("UNITCODE"));
        String investedUnit = String.valueOf(acceptData.getMaster().getFieldValue("INVESTEDUNIT"));
        boolean isCurrentMergeLevel = this.checkMergeLevel(calcArgments, orgTool, investUnit, investedUnit);
        return isCurrentMergeLevel;
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

    private boolean isDispose(Object sameCtrlChgOrgEO, int currentMonth, Calendar calendar) {
        if (sameCtrlChgOrgEO == null) {
            return false;
        }
        SameCtrlChgOrgEO sameCtrlChgOrg = (SameCtrlChgOrgEO)sameCtrlChgOrgEO;
        calendar.setTime(sameCtrlChgOrg.getChangeDate());
        int disposeMonth = calendar.get(2);
        return disposeMonth < currentMonth;
    }

    private void dealWithUnitReplace(@NotNull AbstractInvestmentRule rule, boolean isCurrentMergeLevel, GcInvestBillGroupDTO acceptData) {
        if (!isCurrentMergeLevel && !CollectionUtils.isEmpty((Collection)rule.getDealWith())) {
            Object sameCtrlChgOrgEO = acceptData.getMaster().getFieldValue("VIRTUALUNIT");
            if (sameCtrlChgOrgEO == null) {
                return;
            }
            SameCtrlChgOrgEO sameCtrlChgOrg = (SameCtrlChgOrgEO)sameCtrlChgOrgEO;
            acceptData.getMaster().addFieldValue("SRC_INVESTEDUNIT", acceptData.getMaster().getFieldValue("INVESTEDUNIT"));
            acceptData.getMaster().addFieldValue("INVESTEDUNIT", (Object)sameCtrlChgOrg.getVirtualCode());
        }
    }

    private GcInvestBillGroupDTO copyData(GcInvestBillGroupDTO acceptData) {
        DefaultTableEntity masterCopy = this.copyGcDnaGenericEntity(acceptData.getMaster());
        ArrayList<DefaultTableEntity> itemsCopy = new ArrayList<DefaultTableEntity>();
        this.copyList(acceptData.getItems(), itemsCopy);
        return new GcInvestBillGroupDTO(masterCopy, itemsCopy, true);
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

    private void copyList(List<DefaultTableEntity> source, List<DefaultTableEntity> target) {
        if (CollectionUtils.isEmpty(source)) {
            return;
        }
        for (DefaultTableEntity item : source) {
            if (item == null) continue;
            target.add(this.copyGcDnaGenericEntity(item));
        }
    }

    private void sortItems(String id, List<DefaultTableEntity> items) {
        if (StringUtils.isEmpty((String)id)) {
            return;
        }
        if (CollectionUtils.isEmpty(items)) {
            return;
        }
        DefaultTableEntity current = null;
        for (int i = 0; i < items.size(); ++i) {
            DefaultTableEntity item = items.get(i);
            if (!id.equals(item.getId())) continue;
            current = item;
            items.remove(i);
            break;
        }
        if (current == null) {
            return;
        }
        items.add(0, current);
    }

    private List<GcInvestBillGroupDTO> monthlyIncrementFilterHander(List<GcInvestBillGroupDTO> acceptDatas, AbstractInvestmentRule rule, Date startPeriod, Date endPeriod, Set<String> initialMergeInvestMastIdSet, Set<String> priorMastIdSetCache) {
        if (CollectionUtils.isEmpty(acceptDatas)) {
            return acceptDatas;
        }
        List initialMerge = rule.getInitialMerge();
        List goingConcern = rule.getGoingConcern();
        List dealWith = rule.getDealWith();
        if (!CollectionUtils.isEmpty((Collection)initialMerge)) {
            return this.initialMergeFilter(acceptDatas, initialMerge, startPeriod, endPeriod, initialMergeInvestMastIdSet);
        }
        if (!CollectionUtils.isEmpty((Collection)goingConcern)) {
            return this.changeScenarioFilter(acceptDatas, goingConcern, priorMastIdSetCache, startPeriod, endPeriod);
        }
        if (!CollectionUtils.isEmpty((Collection)dealWith)) {
            return this.dealWithChangeScenarioFilter(acceptDatas, dealWith, startPeriod, endPeriod);
        }
        return this.acceptDataDefaultHandler(acceptDatas, startPeriod, initialMergeInvestMastIdSet, priorMastIdSetCache);
    }

    private List<GcInvestBillGroupDTO> filterInitialMergeInvestData(List<GcInvestBillGroupDTO> acceptDatas, Set<String> initialMergeInvestMastIdSet) {
        if (CollectionUtils.isEmpty(initialMergeInvestMastIdSet)) {
            return acceptDatas;
        }
        ArrayList<GcInvestBillGroupDTO> result = new ArrayList<GcInvestBillGroupDTO>();
        for (GcInvestBillGroupDTO dto : acceptDatas) {
            if (initialMergeInvestMastIdSet.contains(dto.getMaster().getId())) continue;
            result.add(dto);
        }
        return result;
    }

    private List<GcInvestBillGroupDTO> acceptDataDefaultHandler(List<GcInvestBillGroupDTO> acceptDatas, Date startPeriod, Set<String> initialMergeInvestMastIdSet, Set<String> priorMastIdSetCache) {
        ArrayList<GcInvestBillGroupDTO> result = new ArrayList<GcInvestBillGroupDTO>();
        for (GcInvestBillGroupDTO dto : acceptDatas) {
            if (this.isDealWithPeriod(dto, startPeriod)) continue;
            List<DefaultTableEntity> items = dto.getCurrYearItems();
            if (!initialMergeInvestMastIdSet.contains(dto.getMaster().getId())) {
                DefaultTableEntity copyMaster = this.copyGcDnaGenericEntity(dto.getMaster());
                result.add(new GcInvestBillGroupDTO(copyMaster, null, false));
                continue;
            }
            if (CollectionUtils.isEmpty(items)) {
                result.add(new GcInvestBillGroupDTO(dto.getMaster(), null, false));
                continue;
            }
            ArrayList<DefaultTableEntity> itemList = new ArrayList<DefaultTableEntity>();
            itemList.add(items.get(items.size() - 1));
            result.add(new GcInvestBillGroupDTO(dto.getMaster(), itemList, true));
        }
        return result;
    }

    private List<GcInvestBillGroupDTO> changeScenarioFilter(List<GcInvestBillGroupDTO> acceptDatas, List<String> changeScenarioList, Set<String> priorMastIdSetCache, Date startPeriod, Date endPeriod) {
        HashSet<String> changeScenarioSet = new HashSet<String>(changeScenarioList);
        ArrayList<GcInvestBillGroupDTO> result = new ArrayList<GcInvestBillGroupDTO>();
        for (GcInvestBillGroupDTO dto : acceptDatas) {
            List<DefaultTableEntity> items = dto.getCurrYearItems();
            if (CollectionUtils.isEmpty(items) || this.isDealWithPeriod(dto, startPeriod)) continue;
            ArrayList<DefaultTableEntity> itemList = new ArrayList<DefaultTableEntity>();
            for (DefaultTableEntity item : items) {
                Object changeDateObj;
                Object changeScenarioObj = item.getFieldValue("CHANGESCENARIO");
                if (!changeScenarioSet.contains(changeScenarioObj) || (changeDateObj = item.getFieldValue("CHANGEDATE")) == null) continue;
                Date changeDate = (Date)changeDateObj;
                if (priorMastIdSetCache.contains(dto.getMaster().getId()) && (changeDate.compareTo(startPeriod) < 0 || changeDate.compareTo(endPeriod) > 0)) continue;
                DefaultTableEntity copyItem = this.copyGcDnaGenericEntity(item);
                copyItem.addFieldValue("GOINGCONCERNFLAG", (Object)true);
                itemList.add(copyItem);
            }
            if (CollectionUtils.isEmpty(itemList)) continue;
            result.add(new GcInvestBillGroupDTO(dto.getMaster(), itemList, true));
        }
        return result;
    }

    private List<GcInvestBillGroupDTO> dealWithChangeScenarioFilter(List<GcInvestBillGroupDTO> acceptDatas, List<String> changeScenarioList, Date startPeriod, Date endPeriod) {
        HashSet<String> changeScenarioSet = new HashSet<String>(changeScenarioList);
        ArrayList<GcInvestBillGroupDTO> result = new ArrayList<GcInvestBillGroupDTO>();
        for (GcInvestBillGroupDTO dto : acceptDatas) {
            List<DefaultTableEntity> items = dto.getCurrYearItems();
            if (CollectionUtils.isEmpty(items)) continue;
            Object sameCtrlChgOrgEO = dto.getMaster().getFieldValue("VIRTUALUNIT");
            ArrayList<DefaultTableEntity> itemList = new ArrayList<DefaultTableEntity>();
            for (DefaultTableEntity item : items) {
                SameCtrlChgOrgEO sameCtrlChgOrg;
                Date changeDate;
                Object changeScenarioObj = item.getFieldValue("CHANGESCENARIO");
                if (!changeScenarioSet.contains(changeScenarioObj) || sameCtrlChgOrgEO == null || (changeDate = (sameCtrlChgOrg = (SameCtrlChgOrgEO)sameCtrlChgOrgEO).getChangeDate()).compareTo(startPeriod) < 0 || changeDate.compareTo(endPeriod) > 0) continue;
                itemList.add(item);
            }
            if (CollectionUtils.isEmpty(itemList)) continue;
            result.add(new GcInvestBillGroupDTO(dto.getMaster(), itemList, true));
        }
        return result;
    }

    private boolean isDealWithPeriod(GcInvestBillGroupDTO dto, Date startPeriod) {
        Object disposeDateObj = dto.getMaster().getFieldValue("DISPOSEDATE");
        if (disposeDateObj == null) {
            return false;
        }
        int changeMonth = DateUtils.getDateFieldValue((Date)((Date)disposeDateObj), (int)2);
        int currentMonth = DateUtils.getDateFieldValue((Date)startPeriod, (int)2);
        return currentMonth >= changeMonth;
    }

    public void priorPeriodHandler(GcCalcEnvContext env, @NotNull AbstractUnionRule rule) {
        GcCalcArgmentsDTO calcArgments = env.getCalcArgments();
        String reportSystemId = ((ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class)).getSystemIdBySchemeId(calcArgments.getSchemeId(), calcArgments.getPeriodStr());
        ConsolidatedOptionVO conOptionBySystemId = ((ConsolidatedOptionService)SpringContextUtils.getBean(ConsolidatedOptionService.class)).getOptionData(reportSystemId);
        if (!CollectionUtils.isEmpty((Collection)conOptionBySystemId.getMonthlyIncrementRuleIds()) && !conOptionBySystemId.getMonthlyIncrementRuleIds().contains(rule.getId())) {
            return;
        }
        if (calcArgments.getPeriodStr().endsWith("01")) {
            return;
        }
        this.copyPriorPeriodSameCtrlOffsetItem(env, rule);
        List sameCtrlChgOrgEOList = Collections.emptyList();
        Map unit2SamCtrlChgOrgMap = sameCtrlChgOrgEOList.stream().collect(Collectors.toMap(SameCtrlChgOrgEO::getChangedCode, Function.identity(), (v1, v2) -> v2));
        List rules = this.unionRuleService.selectRuleListBySchemeIdAndRuleTypes(calcArgments.getSchemeId(), calcArgments.getPeriodStr(), null);
        Map<String, String> ruleId2TitleMap = rules.stream().collect(Collectors.toMap(AbstractUnionRule::getId, AbstractUnionRule::getLocalizedName, (o1, o2) -> o2));
        List<GcOffSetVchrItemAdjustEO> priorDataAllList = this.getPriorGcOffSetVchrItemAdjustEOS(calcArgments, Arrays.asList(rule.getId()));
        ArrayList<GcOffSetVchrItemAdjustEO> offSetSaveDataList = new ArrayList<GcOffSetVchrItemAdjustEO>();
        ArrayList<SameCtrlOffSetItemEO> sameCtrlOffsetSaveList = new ArrayList<SameCtrlOffSetItemEO>();
        HashMap<String, String> mrecidMap = new HashMap<String, String>();
        Collections.reverse(priorDataAllList);
        block3: for (GcOffSetVchrItemAdjustEO eo : priorDataAllList) {
            SameCtrlChgOrgEO sameCtrlChgOrgEO;
            OffSetSrcTypeEnum oldSrcType = OffSetSrcTypeEnum.getEnumByValue((int)eo.getOffSetSrcType());
            if (oldSrcType == null) {
                eo.setOffSetSrcType(Integer.valueOf(OffSetSrcTypeEnum.COPY_OFFSET.getSrcTypeValue()));
            } else {
                switch (oldSrcType) {
                    case FAIRVALUE_ADJUST_ITEM_INIT: 
                    case EQUITY_METHOD_ADJUST_ITEM_INIT: 
                    case OFFSET_ITEM_INIT: 
                    case INVEST_OFFSET_ITEM_INIT: 
                    case INVENTORY_OFFSET_ITEM_INIT: 
                    case CARRY_OVER: 
                    case CARRY_OVER_FAIRVALUE: {
                        continue block3;
                    }
                }
                eo.setOffSetSrcType(Integer.valueOf(OffSetSrcTypeEnum.COPY_OFFSET.getSrcTypeValue()));
            }
            eo.setId(UUIDOrderUtils.newUUIDStr());
            int acctPeriod = ConverterUtils.getAsIntValue((Object)eo.getDefaultPeriod().substring(5, 9));
            if (StringUtils.isEmpty((String)eo.getMemo())) {
                eo.setMemo(acctPeriod + "\u671f\u6708\u7ed3");
            } else if (!eo.getMemo().contains("\u671f\u6708\u7ed3")) {
                eo.setMemo(acctPeriod + "\u671f\u6708\u7ed3\uff0c" + eo.getMemo());
            }
            eo.setDefaultPeriod(calcArgments.getPeriodStr());
            eo.setAdjust(calcArgments.getSelectAdjustCode());
            if (mrecidMap.containsKey(eo.getmRecid())) {
                eo.setmRecid((String)mrecidMap.get(eo.getmRecid()));
            } else {
                String newMrecid = env.getCalcContextExpandVariableCenter().getPreGernerateOffsetItemMRecid(rule.getId());
                mrecidMap.put(eo.getmRecid(), newMrecid);
                eo.setmRecid(newMrecid);
            }
            SameCtrlChgOrgEO sameCtrlChgOrgEO2 = sameCtrlChgOrgEO = unit2SamCtrlChgOrgMap.containsKey(eo.getUnitId()) ? (SameCtrlChgOrgEO)unit2SamCtrlChgOrgMap.get(eo.getUnitId()) : (SameCtrlChgOrgEO)unit2SamCtrlChgOrgMap.get(eo.getOppUnitId());
            if (sameCtrlChgOrgEO == null) {
                offSetSaveDataList.add(eo);
                continue;
            }
            int changeMonth = DateUtils.getDateFieldValue((Date)sameCtrlChgOrgEO.getChangeDate(), (int)2);
            YearPeriodObject yp = new YearPeriodObject(null, calcArgments.getPeriodStr());
            int currentMonth = DateUtils.getDateFieldValue((Date)yp.formatYP().getEndDate(), (int)2);
            if (currentMonth < changeMonth) {
                offSetSaveDataList.add(eo);
                continue;
            }
            sameCtrlOffsetSaveList.add(this.convertOffsetItemEOToSameCtrlEO(eo, calcArgments, sameCtrlChgOrgEO, ruleId2TitleMap));
        }
        if (!CollectionUtils.isEmpty(offSetSaveDataList)) {
            EntNativeSqlDefaultDao offsetDao = EntNativeSqlDefaultDao.newInstance((String)"GC_OFFSETVCHRITEM", GcOffSetVchrItemAdjustEO.class);
            offsetDao.addBatch(offSetSaveDataList);
        }
        if (!CollectionUtils.isEmpty(sameCtrlOffsetSaveList)) {
            this.sameCtrlOffSetItemDao.saveAll(sameCtrlOffsetSaveList);
        }
    }

    private SameCtrlOffSetItemEO convertOffsetItemEOToSameCtrlEO(GcOffSetVchrItemAdjustEO eo, GcCalcArgmentsDTO calcArgments, SameCtrlChgOrgEO sameCtrlChgOrgEO, Map<String, String> ruleId2TitleMap) {
        SameCtrlOffSetItemEO sameCtrlOffSetItemEO = new SameCtrlOffSetItemEO();
        BeanUtils.copyProperties(eo, sameCtrlOffSetItemEO);
        sameCtrlOffSetItemEO.setId(UUIDOrderUtils.newUUIDStr());
        sameCtrlOffSetItemEO.setSrcId(eo.getId());
        sameCtrlOffSetItemEO.setmRecid(eo.getmRecid());
        sameCtrlOffSetItemEO.setCreateTime(Calendar.getInstance().getTime());
        sameCtrlOffSetItemEO.setElmMode(Integer.valueOf(OffsetElmModeEnum.AUTO_ITEM.getValue()));
        sameCtrlOffSetItemEO.setDefaultPeriod(calcArgments.getPeriodStr());
        sameCtrlOffSetItemEO.setTaskId(calcArgments.getTaskId());
        sameCtrlOffSetItemEO.setSchemeId(calcArgments.getSchemeId());
        sameCtrlOffSetItemEO.setUnitCode(eo.getUnitId());
        sameCtrlOffSetItemEO.setOppUnitCode(eo.getOppUnitId());
        sameCtrlOffSetItemEO.setInputUnitCode(calcArgments.getOrgId());
        GcOrgCenterService orgCenterTool = GcOrgPublicTool.getInstance((String)calcArgments.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(null, calcArgments.getPeriodStr()));
        GcOrgCacheVO mergeUnitVo = orgCenterTool.getOrgByCode(calcArgments.getOrgId());
        sameCtrlOffSetItemEO.setInputUnitParents(mergeUnitVo.getParentStr());
        sameCtrlOffSetItemEO.setSameParentCode(sameCtrlChgOrgEO.getSameParentCode());
        sameCtrlOffSetItemEO.setUnitChangeYear(Integer.valueOf(DateUtils.getYearOfDate((Date)sameCtrlChgOrgEO.getChangeDate())));
        sameCtrlOffSetItemEO.setSameCtrlChgId(sameCtrlChgOrgEO.getId());
        sameCtrlOffSetItemEO.setSameCtrlSrcType(SameCtrlSrcTypeEnum.END_INVEST_CALC.getCode());
        sameCtrlOffSetItemEO.setOrgType(calcArgments.getOrgType());
        sameCtrlOffSetItemEO.setRuleTitle(ruleId2TitleMap.get(eo.getRuleId()));
        sameCtrlOffSetItemEO.setOrient(eo.getOrient());
        int orient = sameCtrlOffSetItemEO.getOrient();
        String offSetCurr = eo.getOffSetCurr();
        if (orient == OrientEnum.D.getValue()) {
            sameCtrlOffSetItemEO.setOffSetDebit((Double)eo.getFieldValue("DEBIT_" + offSetCurr));
        }
        if (orient == OrientEnum.C.getValue()) {
            sameCtrlOffSetItemEO.setOffSetCredit((Double)eo.getFieldValue("CREDIT_" + offSetCurr));
        }
        this.sameCtrlOffSetItemDao.checkItemDTO(sameCtrlOffSetItemEO);
        return sameCtrlOffSetItemEO;
    }

    private void copyPriorPeriodSameCtrlOffsetItem(GcCalcEnvContext env, AbstractUnionRule rule) {
        GcCalcArgmentsDTO calcArgments = env.getCalcArgments();
        SameCtrlOffsetCond cond = this.getSameCtrlOffsetCond(calcArgments);
        String priorPeriodStr = this.getPriorPeriod(calcArgments.getPeriodStr());
        cond.setPeriodStr(priorPeriodStr);
        List sameCtrlOffSetItemEOS = this.sameCtrlOffSetItemDao.listOffsetsByRuleAndSrcType(cond, Arrays.asList(rule.getLocalizedName()), Arrays.asList(SameCtrlSrcTypeEnum.END_INVEST_CALC.getCode()));
        ArrayList<SameCtrlOffSetItemEO> saveDataList = new ArrayList<SameCtrlOffSetItemEO>();
        HashMap<String, String> mrecidMap = new HashMap<String, String>();
        Integer period = Integer.valueOf(priorPeriodStr.substring(priorPeriodStr.length() - 2));
        Collections.reverse(sameCtrlOffSetItemEOS);
        for (SameCtrlOffSetItemEO eo : sameCtrlOffSetItemEOS) {
            eo.setId(UUIDOrderUtils.newUUIDStr());
            if (StringUtils.isEmpty((String)eo.getMemo())) {
                eo.setMemo(period + "\u671f\u6708\u7ed3");
            } else if (!eo.getMemo().contains("\u671f\u6708\u7ed3")) {
                eo.setMemo(period + "\u671f\u6708\u7ed3\uff0c" + eo.getMemo());
            }
            eo.setDefaultPeriod(calcArgments.getPeriodStr());
            if (mrecidMap.containsKey(eo.getmRecid())) {
                eo.setmRecid((String)mrecidMap.get(eo.getmRecid()));
            } else {
                String newMrecid = env.getCalcContextExpandVariableCenter().getPreGernerateOffsetItemMRecid(rule.getId());
                mrecidMap.put(eo.getmRecid(), newMrecid);
                eo.setmRecid(newMrecid);
            }
            saveDataList.add(eo);
        }
        this.sameCtrlOffSetItemDao.saveAll(saveDataList);
    }

    private Set<String> getChangeUnitSet(GcCalcArgmentsDTO calcArgments, GcOrgCenterService tool) {
        PeriodWrapper periodWrapper = new PeriodWrapper(calcArgments.getPeriodStr());
        int period = periodWrapper.getPeriod();
        Set<String> currentUnitSet = this.getUnits(calcArgments.getOrgId(), tool, null);
        HashSet<String> changeUnitSet = new HashSet<String>();
        for (int i = period - 1; i > 0; --i) {
            String tempPeriod = this.getPriorPeriod(calcArgments.getPeriodStr());
            YearPeriodObject yp = new YearPeriodObject(null, tempPeriod);
            GcOrgCenterService priorTool = GcOrgPublicTool.getInstance((String)calcArgments.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            Set<String> units = this.getUnits(calcArgments.getOrgId(), priorTool, currentUnitSet);
            changeUnitSet.addAll(units);
            currentUnitSet.addAll(units);
        }
        if (changeUnitSet.isEmpty()) {
            return null;
        }
        return changeUnitSet;
    }

    private String getCurrentMergeLLevelVirtualUnit(String comBaseUnit, GcOffSetVchrItemDTO gcOffSetVchrItemDTO, Map<String, String> unit2virtualUnitMapCache, GcOrgCenterService currentMangerInstance, GcOrgCenterService tool, GcCalcArgmentsDTO calcArgments) {
        String investedUnitId = comBaseUnit.equals(gcOffSetVchrItemDTO.getUnitId()) ? gcOffSetVchrItemDTO.getOppUnitId() : gcOffSetVchrItemDTO.getUnitId();
        String virtualUnit = this.getVirtualUnit(unit2virtualUnitMapCache, currentMangerInstance, investedUnitId);
        if (!this.checkMergeLevel(calcArgments, tool, comBaseUnit, virtualUnit)) {
            return null;
        }
        return virtualUnit;
    }

    private List<GcOffSetVchrDTO> getCarryOverGcOffsetGroupDTOs(QueryParamsVO queryParamsVO) {
        OffsetItemInitQueryParamsVO copyQueryParamsVO = new OffsetItemInitQueryParamsVO();
        BeanUtils.copyProperties(queryParamsVO, copyQueryParamsVO);
        ArrayList<Integer> offSetSrcTypes = new ArrayList<Integer>();
        offSetSrcTypes.add(OffSetSrcTypeEnum.CARRY_OVER.getSrcTypeValue());
        offSetSrcTypes.add(OffSetSrcTypeEnum.CARRY_OVER_FAIRVALUE.getSrcTypeValue());
        copyQueryParamsVO.setOffSetSrcTypes(offSetSrcTypes);
        return this.gcOffSetInitService.queryOffSetGroupDTOs(copyQueryParamsVO);
    }

    private List<GcOffSetVchrDTO> getInitGcOffsetGroupDTOs(QueryParamsVO queryParamsVO) {
        OffsetItemInitQueryParamsVO copyQueryParamsVO = new OffsetItemInitQueryParamsVO();
        BeanUtils.copyProperties(queryParamsVO, copyQueryParamsVO);
        this.setInitSrcTypes(copyQueryParamsVO);
        return this.gcOffSetInitService.queryOffSetGroupDTOs(copyQueryParamsVO);
    }

    private QueryParamsVO getQueryParamsVO(GcCalcArgmentsDTO calcArgments, List<String> rules, String comBaseUnit, Set<String> changeUnitSet) {
        QueryParamsVO queryParamsVO = new QueryParamsVO();
        BeanUtils.copyProperties(calcArgments, queryParamsVO);
        YearPeriodDO yp = new YearPeriodObject(null, calcArgments.getPeriodStr()).formatYP();
        String zeroPeriodStr = new YearPeriodObject(calcArgments.getSchemeId(), yp.getYear(), yp.getType(), 0).formatYP().toString();
        queryParamsVO.setPeriodStr(zeroPeriodStr);
        queryParamsVO.setRules(rules);
        ArrayList<String> unitList = new ArrayList<String>();
        unitList.add(comBaseUnit);
        ArrayList<String> oppUnitList = new ArrayList<String>(changeUnitSet);
        queryParamsVO.setUnitIdList(unitList);
        queryParamsVO.setOppUnitIdList(oppUnitList);
        ConsolidatedTaskVO consolidatedTaskVO = this.consolidatedTaskService.getTaskBySchemeId(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        if (consolidatedTaskVO != null) {
            queryParamsVO.setSystemId(consolidatedTaskVO.getSystemId());
            queryParamsVO.setTaskId(null);
        }
        return queryParamsVO;
    }

    private void setInitSrcTypes(OffsetItemInitQueryParamsVO queryParamsVO) {
        queryParamsVO.setOffSetSrcTypes((Collection)OffSetSrcTypeEnum.getCommonInitOffSetSrcTypeValue());
    }

    private void save(GcOffSetVchrDTO group, GcCalcArgmentsDTO calcArgments, Map<String, String> carryOverSubjectCodeMapping, String comBaseUnit, String virtualUnit) {
        group.setMrecid(UUIDOrderUtils.newUUIDStr());
        for (GcOffSetVchrItemDTO item : group.getItems()) {
            if (item.getDisableFlag().booleanValue()) {
                return;
            }
            item.setSrcId(item.getId());
            item.setId(UUID.randomUUID().toString());
            item.setmRecid(group.getMrecid());
            item.setCreateTime(Calendar.getInstance().getTime());
            item.setModifyTime(null);
            item.setElmMode(Integer.valueOf(OffsetElmModeEnum.AUTO_ITEM.getValue()));
            item.setTaskId(calcArgments.getTaskId());
            item.setSchemeId(calcArgments.getSchemeId());
            item.setAcctYear(calcArgments.getAcctYear());
            item.setAcctPeriod(calcArgments.getAcctPeriod());
            item.setDefaultPeriod(calcArgments.getPeriodStr());
            if (carryOverSubjectCodeMapping.containsKey(item.getSubjectCode())) {
                item.setSubjectCode(carryOverSubjectCodeMapping.get(item.getSubjectCode()));
            }
            if (comBaseUnit.equals(item.getUnitId())) {
                item.setOppUnitId(virtualUnit);
            } else {
                item.setUnitId(virtualUnit);
            }
            item.setMemo(calcArgments.getAcctYear() + OffSetSrcTypeEnum.CARRY_OVER.getSrcTypeName());
        }
        this.adjustingEntryService.save(group);
    }

    private void save(GcOffSetVchrDTO group, GcCalcArgmentsDTO calcArgments, String comBaseUnit, String virtualUnit) {
        group.setMrecid(UUIDOrderUtils.newUUIDStr());
        for (GcOffSetVchrItemDTO item : group.getItems()) {
            if (item.getDisableFlag().booleanValue()) {
                return;
            }
            item.setSrcId(item.getId());
            item.setId(UUID.randomUUID().toString());
            item.setmRecid(group.getMrecid());
            item.setCreateTime(Calendar.getInstance().getTime());
            item.setModifyTime(null);
            item.setElmMode(Integer.valueOf(OffsetElmModeEnum.AUTO_ITEM.getValue()));
            item.setAcctYear(calcArgments.getAcctYear());
            item.setAcctPeriod(calcArgments.getAcctPeriod());
            item.setDefaultPeriod(calcArgments.getPeriodStr());
            item.setTaskId(calcArgments.getTaskId());
            item.setSchemeId(calcArgments.getSchemeId());
            item.setMemo(item.getAcctYear() + "\u5e74\u521d\u521d\u59cb\u5316");
            if (comBaseUnit.equals(item.getUnitId())) {
                item.setOppUnitId(virtualUnit);
                continue;
            }
            item.setUnitId(virtualUnit);
        }
        this.adjustingEntryService.save(group);
    }

    private Set<String> getUnits(String comUnitId, GcOrgCenterService tool, Set<String> filterUnitSet) {
        HashSet<String> unitIds = new HashSet<String>();
        GcOrgCacheVO orgByCode = tool.getOrgByCode(comUnitId);
        if (orgByCode == null) {
            return unitIds;
        }
        List childrenOrg = tool.getOrgChildrenTree(comUnitId);
        if (CollectionUtils.isEmpty((Collection)childrenOrg)) {
            return unitIds;
        }
        for (GcOrgCacheVO org : childrenOrg) {
            String needAddUnitId;
            String baseUnitId = org.isLeaf() ? null : tool.getDeepestBaseUnitId(org.getId());
            String string = needAddUnitId = baseUnitId != null ? baseUnitId : org.getId();
            if (filterUnitSet != null && filterUnitSet.contains(needAddUnitId)) continue;
            unitIds.add(needAddUnitId);
        }
        return unitIds;
    }

    public List<GcOffSetVchrItemAdjustEO> getPriorGcOffSetVchrItemAdjustEOS(GcCalcArgmentsDTO calcArgments, List<String> rules) {
        String priorPeriodStr = this.getPriorPeriod(calcArgments.getPeriodStr());
        QueryParamsVO queryParamsVO = new QueryParamsVO();
        BeanUtils.copyProperties(calcArgments, queryParamsVO);
        queryParamsVO.setOrgId(calcArgments.getOrgId());
        queryParamsVO.setPeriodStr(priorPeriodStr);
        queryParamsVO.setAcctPeriod(Integer.valueOf(calcArgments.getAcctPeriod() - 1));
        ArrayList<Integer> elmModes = new ArrayList<Integer>();
        elmModes.add(OffsetElmModeEnum.AUTO_ITEM.getValue());
        queryParamsVO.setElmModes(elmModes);
        queryParamsVO.setRules(rules);
        Set<String> priorMrecidSet = this.queryMrecids(queryParamsVO, priorPeriodStr);
        if (CollectionUtils.isEmpty(priorMrecidSet)) {
            return Collections.emptyList();
        }
        List priorAdjustEOS = this.offsetCoreService.listByWhere(new String[]{"mrecid"}, new Object[]{priorMrecidSet});
        ArrayList<GcOffSetVchrItemAdjustEO> adjustEOS = new ArrayList<GcOffSetVchrItemAdjustEO>();
        if (CollectionUtils.isEmpty((Collection)priorAdjustEOS)) {
            return adjustEOS;
        }
        return priorAdjustEOS;
    }

    private void addPriorAdjustEO(GcCalcArgmentsDTO calcArgments, List<GcOffSetVchrItemAdjustEO> adjustEOS, List<GcOffSetVchrItemAdjustEO> priorAdjustEOS) {
        YearPeriodObject yp = new YearPeriodObject(null, calcArgments.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)calcArgments.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        HashMap<String, String> unit2virtualUnitMapCache = new HashMap<String, String>();
        for (GcOffSetVchrItemAdjustEO eo : priorAdjustEOS) {
            GcOrgCacheVO oppUnitOrg;
            String oppUnitCode;
            if (this.checkMergeLevel(calcArgments, tool, eo.getUnitId(), eo.getOppUnitId())) {
                adjustEOS.add(eo);
                continue;
            }
            String unitVirtualUnit = this.getVirtualUnit(unit2virtualUnitMapCache, tool, eo.getUnitId());
            String oppUnitVirtualUnit = this.getVirtualUnit(unit2virtualUnitMapCache, tool, eo.getOppUnitId());
            if (StringUtils.isEmpty((String)unitVirtualUnit) && StringUtils.isEmpty((String)oppUnitVirtualUnit)) continue;
            String unitCode = StringUtils.isEmpty((String)unitVirtualUnit) ? eo.getUnitId() : unitVirtualUnit;
            String string = oppUnitCode = StringUtils.isEmpty((String)oppUnitVirtualUnit) ? eo.getOppUnitId() : oppUnitVirtualUnit;
            GcOrgCacheVO unitOrg = tool.getOrgByCode(unitCode);
            GcOrgCacheVO firstSameParentOrg = tool.getCommonUnit(unitOrg, oppUnitOrg = tool.getOrgByCode(oppUnitCode));
            if (firstSameParentOrg == null || !firstSameParentOrg.getCode().equals(calcArgments.getOrgId())) continue;
            eo.setUnitId(unitCode);
            eo.setOppUnitId(oppUnitCode);
            adjustEOS.add(eo);
        }
    }

    private boolean checkMergeLevel(GcCalcArgmentsDTO arg, GcOrgCenterService tool, String unitCode, String oppUnitCode) {
        GcOrgCacheVO unit = tool.getOrgByCode(unitCode);
        GcOrgCacheVO oppUnit = tool.getOrgByCode(oppUnitCode);
        if (unit == null || oppUnit == null) {
            return false;
        }
        GcOrgCacheVO commonUnit = tool.getCommonUnit(unit, oppUnit);
        return commonUnit != null && Objects.equals(commonUnit.getId(), arg.getOrgId());
    }

    private String getVirtualUnit(Map<String, String> unit2virtualUnitMapCache, GcOrgCenterService currentMangerInstance, String unitId) {
        if (unit2virtualUnitMapCache.containsKey(unitId)) {
            return unit2virtualUnitMapCache.get(unitId);
        }
        GcOrgCacheVO vritualUnit = currentMangerInstance.getOrgByCode(unitId);
        if (vritualUnit == null || vritualUnit.getTypeFieldValue("DISPOSALVIRTUALUNITID") == null) {
            unit2virtualUnitMapCache.put(unitId, null);
            return null;
        }
        unit2virtualUnitMapCache.put(unitId, vritualUnit.getTypeFieldValue("DISPOSALVIRTUALUNITID").toString());
        return unit2virtualUnitMapCache.get(unitId);
    }

    private Set<String> queryMrecids(QueryParamsVO queryParamsVO, String unitTreePeriod) {
        GcOffsetVchrQueryImpl offsetVchrQuery = (GcOffsetVchrQueryImpl)SpringContextUtils.getBean(GcOffsetVchrQueryImpl.class);
        HashSet<String> mRecids = new HashSet<String>();
        String queryFields = "record.mrecid";
        YearPeriodObject yp = new YearPeriodObject(null, unitTreePeriod);
        GcOrgCenterService orgService = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        YearPeriodDO ypdo = yp.formatYP();
        String orgTable = orgService.getCurrOrgType().getTable();
        ArrayList<Object> params = new ArrayList<Object>();
        StringBuffer head = new StringBuffer(32);
        StringBuffer whereSql = new StringBuffer(32);
        Date date = ypdo.getEndDate();
        GcOrgCacheVO org = orgService.getOrgByCode(queryParamsVO.getOrgId());
        if (null == org || org.getParentStr() == null) {
            return mRecids;
        }
        String parentGuids = org.getParentStr();
        int gcParentsLength = org.getGcParentStr().length();
        this.initMergeUnitCondition(queryParamsVO, head, whereSql, params, parentGuids, gcParentsLength, date, offsetVchrQuery);
        QueryParamsDTO queryParamsDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(queryParamsVO, queryParamsDTO);
        offsetVchrQuery.initUnitCondition(queryParamsDTO, whereSql, orgService);
        offsetVchrQuery.initPeriodCondition(queryParamsDTO, params, whereSql);
        offsetVchrQuery.initOtherCondition(whereSql, queryParamsDTO);
        StringBuffer sql = new StringBuffer(512);
        sql.append("select ").append(queryFields).append(" from ").append("GC_OFFSETVCHRITEM").append("  record\n");
        sql.append("join ").append(orgTable).append("  bfUnitTable on (record.unitid = bfUnitTable.code)\n");
        sql.append("join ").append(orgTable).append("  dfUnitTable on (record.oppunitid = dfUnitTable.code)\n");
        sql.append(whereSql).append(" and record.DISABLEFLAG <> 1 ");
        sql.append("group by ").append(queryFields).append("\n");
        sql.append("order by record.mrecid desc\n");
        List rs = EntNativeSqlDefaultDao.getInstance().selectMap(sql.toString(), params);
        int count = rs.size();
        if (count < 1) {
            return mRecids;
        }
        mRecids.addAll(rs.stream().map(m -> String.valueOf(m.get("MRECID"))).collect(Collectors.toSet()));
        return mRecids;
    }

    public void initMergeUnitCondition(QueryParamsVO queryParamsVO, StringBuffer head, StringBuffer whereSql, List<Object> params, String parentGuids, int gcParentsLength, Date date, GcOffsetVchrQueryImpl offsetVchrQuery) {
        int len = gcParentsLength + GcOrgPublicTool.getInstance().getOrgCodeLength() + 1;
        whereSql.append("where (substr(bfUnitTable.gcparents, 1, ").append(len).append(" ) <> substr(dfUnitTable.gcparents, 1, ").append(len).append(")\n");
        whereSql.append("and bfUnitTable.parents like ?\n");
        whereSql.append("and dfUnitTable.parents like ?\n");
        params.add(parentGuids + "%");
        params.add(parentGuids + "%");
        String orgTypeId = queryParamsVO.getOrgType();
        String emptyUUID = "NONE";
        whereSql.append(" and record.md_gcorgtype in('" + emptyUUID + "',?)) \n");
        params.add(orgTypeId);
        offsetVchrQuery.initValidtimeCondition(head, whereSql, params, date);
        if (queryParamsVO.isDelete() && !"MD_ORG_CORPORATE".equals(queryParamsVO.getOrgType())) {
            whereSql.append(" and record.md_gcorgtype <> '").append(emptyUUID).append("'\n");
        }
    }

    private String getPriorPeriod(String periodStr) {
        PeriodWrapper periodWrapper = new PeriodWrapper(periodStr);
        DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
        defaultPeriodAdapter.priorPeriod(periodWrapper);
        return periodWrapper.toString();
    }

    private List<GcInvestBillGroupDTO> initialMergeFilter(List<GcInvestBillGroupDTO> acceptDatas, List<String> initialMerge, Date startPeriod, Date endPeriod, Set<String> initialMergeInvestMastIdSet) {
        HashSet<String> initialMergeSet = new HashSet<String>(initialMerge);
        ArrayList<GcInvestBillGroupDTO> result = new ArrayList<GcInvestBillGroupDTO>();
        for (GcInvestBillGroupDTO dto : acceptDatas) {
            List<DefaultTableEntity> items = dto.getCurrYearItems();
            if (CollectionUtils.isEmpty(items)) continue;
            boolean hasCalc = !initialMergeInvestMastIdSet.contains(dto.getMaster().getId());
            ArrayList<DefaultTableEntity> itemList = new ArrayList<DefaultTableEntity>();
            for (DefaultTableEntity item : items) {
                Date changeDate;
                Object changeDateObj;
                Object changeScenarioObj = item.getFieldValue("CHANGESCENARIO");
                if (!initialMergeSet.contains(changeScenarioObj) || (changeDateObj = item.getFieldValue("CHANGEDATE")) == null || DateUtils.getYearOfDate((Date)(changeDate = (Date)changeDateObj)) != DateUtils.getYearOfDate((Date)startPeriod) || changeDate.compareTo(endPeriod) > 0 || hasCalc) continue;
                DefaultTableEntity copyItem = this.copyGcDnaGenericEntity(item);
                copyItem.addFieldValue("INITIALFLAG", (Object)true);
                itemList.add(copyItem);
            }
            if (CollectionUtils.isEmpty(itemList)) continue;
            result.add(new GcInvestBillGroupDTO(dto.getMaster(), itemList, true));
        }
        return result;
    }

    private void deleteHistoryAdjustOffSetItems(GcCalcEnvContext env, @NotNull AbstractInvestmentRule rule) {
        GcCalcArgmentsDTO calcArgments = env.getCalcArgments();
        this.gcCalcService.deleteAutoOffsetEntrysByRule(rule.getId(), calcArgments);
    }

    private GcOffSetVchrDTO executeSingleAcceptData(AbstractInvestmentRule rule, GcCalcEnvContext env, GcInvestBillGroupDTO acceptData, GcInvestBillGroupDTO allItemsData) {
        boolean isCreateGcOffSetVchrDTO;
        GcOffSetVchrItemDTO phsRecord;
        GcOffSetVchrItemDTO offSetVchrItem;
        GcCalcArgmentsDTO calcArgments = env.getCalcArgments();
        DimensionValueSet dset = DimensionUtils.generateDimSet((Object)acceptData.getMaster().getFieldValue("INVESTEDUNIT"), (Object)calcArgments.getPeriodStr(), (Object)calcArgments.getCurrency(), (Object)calcArgments.getOrgType(), (String)calcArgments.getSelectAdjustCode(), (String)calcArgments.getTaskId());
        Double debitSum = 0.0;
        Double creditSum = 0.0;
        String preGernerateOffsetItemMRecid = env.getCalcContextExpandVariableCenter().getPreGernerateOffsetItemMRecid(rule.getId());
        GcOffSetVchrDTO offSetVchrDTO = new GcOffSetVchrDTO(preGernerateOffsetItemMRecid);
        ArrayList<GcOffSetVchrItemDTO> records = new ArrayList<GcOffSetVchrItemDTO>();
        ArrayList<AbstractInvestmentRule.Item> debitPhsFormulaList = new ArrayList<AbstractInvestmentRule.Item>();
        ArrayList<AbstractInvestmentRule.Item> creditPhsFormulaList = new ArrayList<AbstractInvestmentRule.Item>();
        for (AbstractInvestmentRule.Item item : rule.getDebitItemList()) {
            if (!StringUtils.isEmpty((String)item.getFetchFormula()) && item.getFetchFormula().contains("PHS")) {
                debitPhsFormulaList.add(item);
                continue;
            }
            offSetVchrItem = this.createNewOffSetVchrItem(offSetVchrDTO.getMrecid(), calcArgments, rule);
            this.fillOffSetVchrItem(offSetVchrItem, acceptData, item, dset, OrientEnum.D, env, allItemsData, rule);
            if (NumberUtils.isZreo((Double)offSetVchrItem.getOffSetDebit())) {
                this.logger.info("\u5408\u5e76\u8ba1\u7b97\u6295\u8d44\u89c4\u5219[{}]\uff0c\u6295\u8d44\u6570\u636e[ID:{}]\u5728\u89c4\u5219\u6761\u76ee[{}]\u4e2d\u53d6\u6570\u501f\u65b9\u62b5\u9500\u91d1\u989d\u4e3a0\uff0c\u4e0d\u751f\u6210\u62b5\u9500\u5206\u5f55\u3002", rule.getLocalizedName(), acceptData.getMaster().getId(), item.getFetchFormula());
                continue;
            }
            debitSum = NumberUtils.sum((Double)debitSum, (Double)offSetVchrItem.getOffSetDebit());
            records.add(offSetVchrItem);
        }
        for (AbstractInvestmentRule.Item item : rule.getCreditItemList()) {
            if (!StringUtils.isEmpty((String)item.getFetchFormula()) && item.getFetchFormula().contains("PHS")) {
                creditPhsFormulaList.add(item);
                continue;
            }
            offSetVchrItem = this.createNewOffSetVchrItem(offSetVchrDTO.getMrecid(), calcArgments, rule);
            this.fillOffSetVchrItem(offSetVchrItem, acceptData, item, dset, OrientEnum.C, env, allItemsData, rule);
            if (NumberUtils.isZreo((Double)offSetVchrItem.getOffSetCredit())) {
                this.logger.info("\u5408\u5e76\u8ba1\u7b97\u6295\u8d44\u89c4\u5219[{}]\uff0c\u6295\u8d44\u6570\u636e[ID:{}]\u5728\u89c4\u5219\u6761\u76ee[{}]\u4e2d\u53d6\u6570\u8d37\u65b9\u62b5\u9500\u91d1\u989d\u4e3a0\uff0c\u4e0d\u751f\u6210\u62b5\u9500\u5206\u5f55\u3002", rule.getLocalizedName(), acceptData.getMaster().getId(), item.getFetchFormula());
                continue;
            }
            creditSum = NumberUtils.sum((Double)creditSum, (Double)offSetVchrItem.getOffSetCredit());
            records.add(offSetVchrItem);
        }
        double diffValue = NumberUtils.sub((Double)debitSum, (Double)creditSum);
        if (!NumberUtils.isZreo((Double)diffValue) && null != (phsRecord = this.phsRecord(rule, env, acceptData, calcArgments, dset, offSetVchrDTO.getMrecid(), debitPhsFormulaList, creditPhsFormulaList, diffValue, allItemsData))) {
            records.add(phsRecord);
        }
        if (isCreateGcOffSetVchrDTO = GcCalcAmtCheckUtil.checkAndDistributionAmt((AbstractUnionRule)rule, records)) {
            offSetVchrDTO.setItems(records);
            return offSetVchrDTO;
        }
        this.logger.info("\u5408\u5e76\u8ba1\u7b97\u6295\u8d44\u89c4\u5219[{}]\uff0c\u6295\u8d44\u6570\u636e[ID:{}]\u4e0d\u6ee1\u8db3\u5bb9\u5dee\u7ea6\u675f\uff0c\u4e0d\u751f\u6210\u62b5\u9500\u5206\u5f55\u3002", (Object)rule.getLocalizedName(), (Object)acceptData.getMaster().getId());
        return null;
    }

    private GcOffSetVchrItemDTO phsRecord(AbstractInvestmentRule rule, GcCalcEnvContext env, GcInvestBillGroupDTO investBillGroupDTO, GcCalcArgmentsDTO calcArgments, DimensionValueSet dset, String mrecid, List<AbstractInvestmentRule.Item> debitPhsFormulaList, List<AbstractInvestmentRule.Item> creditPhsFormulaList, double diffValue, GcInvestBillGroupDTO allItemsData) {
        GcOffSetVchrItemDTO offSetVchrItem;
        for (AbstractInvestmentRule.Item fetchFormula : debitPhsFormulaList) {
            env.getCalcContextExpandVariableCenter().setPhsValue(Double.valueOf(-diffValue));
            offSetVchrItem = this.createNewOffSetVchrItem(mrecid, calcArgments, rule);
            this.fillOffSetVchrItem(offSetVchrItem, investBillGroupDTO, fetchFormula, dset, OrientEnum.D, env, allItemsData, rule);
            if (NumberUtils.isZreo((Double)offSetVchrItem.getOffSetDebit())) continue;
            offSetVchrItem.setMemo(GcI18nUtil.getMessage((String)"gc.calculate.bill.phs.memo"));
            return offSetVchrItem;
        }
        for (AbstractInvestmentRule.Item fetchFormula : creditPhsFormulaList) {
            env.getCalcContextExpandVariableCenter().setPhsValue(Double.valueOf(diffValue));
            offSetVchrItem = this.createNewOffSetVchrItem(mrecid, calcArgments, rule);
            this.fillOffSetVchrItem(offSetVchrItem, investBillGroupDTO, fetchFormula, dset, OrientEnum.C, env, allItemsData, rule);
            if (NumberUtils.isZreo((Double)offSetVchrItem.getOffSetCredit())) continue;
            offSetVchrItem.setMemo(GcI18nUtil.getMessage((String)"gc.calculate.bill.phs.memo"));
            return offSetVchrItem;
        }
        return null;
    }

    private void fillOffSetVchrItem(GcOffSetVchrItemDTO offSetVchrItemDTO, GcInvestBillGroupDTO investmentBillGroupDTO, AbstractInvestmentRule.Item item, DimensionValueSet dimensionValueSet, OrientEnum orientEnum, GcCalcEnvContext env, GcInvestBillGroupDTO allItemsData, AbstractInvestmentRule rule) {
        String defaultDimOrgId;
        String oppUnitId;
        String unitId;
        String subjectCode = item.getSubjectCode();
        String fetchFormula = item.getFetchFormula();
        offSetVchrItemDTO.setSortOrder(Double.valueOf(item.getSort() == null ? 0.0 : (double)item.getSort().intValue()));
        String srcId = ConverterUtils.getAsString((Object)investmentBillGroupDTO.getMaster().getFieldValue("SRCID"));
        if (StringUtils.isEmpty((String)srcId)) {
            offSetVchrItemDTO.setSrcOffsetGroupId(investmentBillGroupDTO.getMaster().getId());
        } else {
            offSetVchrItemDTO.setSrcOffsetGroupId(srcId);
        }
        offSetVchrItemDTO.setSubjectCode(subjectCode);
        if (StringUtils.isEmpty((String)fetchFormula)) {
            return;
        }
        Assert.isFalse((boolean)"PHS".equalsIgnoreCase(fetchFormula), (String)GcI18nUtil.getMessage((String)"gc.calculate.bill.phs.error"), (Object[])new Object[0]);
        double result = this.billFormulaEvalService.evaluateInvestBillData(env, dimensionValueSet, fetchFormula, allItemsData, null);
        if (orientEnum.equals((Object)OrientEnum.D)) {
            offSetVchrItemDTO.setOffSetDebit(Double.valueOf(result));
            offSetVchrItemDTO.setDebit(Double.valueOf(result));
        } else {
            offSetVchrItemDTO.setOffSetCredit(Double.valueOf(result));
            offSetVchrItemDTO.setCredit(Double.valueOf(result));
        }
        String investUnit = String.valueOf(investmentBillGroupDTO.getMaster().getFieldValue("UNITCODE"));
        String investedUnit = String.valueOf(investmentBillGroupDTO.getMaster().getFieldValue("INVESTEDUNIT"));
        String virtualInvestedUnit = this.getInvestedUnitByRule(investmentBillGroupDTO);
        if (InvestmentUnitEnum.INVESTMENT_UNIT.equals((Object)item.getInvestmentUnit())) {
            unitId = investUnit;
            oppUnitId = virtualInvestedUnit;
            defaultDimOrgId = investUnit;
        } else {
            unitId = virtualInvestedUnit;
            oppUnitId = investUnit;
            defaultDimOrgId = investedUnit;
        }
        this.initDimensions(dimensionValueSet, offSetVchrItemDTO, investmentBillGroupDTO, item, env, investUnit, investedUnit, defaultDimOrgId);
        offSetVchrItemDTO.setUnitId(unitId);
        offSetVchrItemDTO.setOppUnitId(oppUnitId);
    }

    private String getInvestedUnitByRule(GcInvestBillGroupDTO investmentBillGroupDTO) {
        String investedUnit = String.valueOf(investmentBillGroupDTO.getMaster().getFieldValue("INVESTEDUNIT"));
        Object sameCtrlChgOrgEO = investmentBillGroupDTO.getMaster().getFieldValue("VIRTUALUNIT");
        if (sameCtrlChgOrgEO == null) {
            return investedUnit;
        }
        SameCtrlChgOrgEO sameCtrlChgOrg = (SameCtrlChgOrgEO)sameCtrlChgOrgEO;
        return sameCtrlChgOrg.getChangedCode();
    }

    private void initDimensions(DimensionValueSet dimensionValueSet, GcOffSetVchrItemDTO offSetVchrItemDTO, GcInvestBillGroupDTO investmentBillGroupDTO, AbstractInvestmentRule.Item item, GcCalcEnvContext env, String investUnit, String investedUnit, String defaultDimOrgId) {
        Map dimensions = item.getDimensions();
        if (dimensions == null) {
            return;
        }
        for (String dimKey : dimensions.keySet()) {
            String dimValueSource = (String)dimensions.get(dimKey);
            if (dimKey.contains("customizeFormula")) continue;
            if (InvestmentUnitEnum.INVESTMENT_UNIT.getCode().equalsIgnoreCase(dimValueSource)) {
                offSetVchrItemDTO.addFieldValue(dimKey, (Object)this.getDimValueByDimCodeAndOrg(env, investUnit, dimKey));
                continue;
            }
            if (InvestmentUnitEnum.INVESTED_UNIT.getCode().equalsIgnoreCase(dimValueSource)) {
                offSetVchrItemDTO.addFieldValue(dimKey, (Object)this.getDimValueByDimCodeAndOrg(env, investedUnit, dimKey));
                continue;
            }
            if ("ACCOUNT".equals(dimValueSource)) {
                offSetVchrItemDTO.addFieldValue(dimKey, investmentBillGroupDTO.getMaster().getFieldValue(dimKey));
                continue;
            }
            if ("customizeFormula".equals(dimValueSource)) {
                if (StringUtils.isEmpty((String)((String)dimensions.get(dimKey + "_customizeFormula")))) {
                    offSetVchrItemDTO.addFieldValue(dimKey, null);
                    continue;
                }
                AbstractData data = this.billFormulaEvalService.getInvestBillData(env, dimensionValueSet, (String)dimensions.get(dimKey + "_customizeFormula"), investmentBillGroupDTO);
                offSetVchrItemDTO.addFieldValue(dimKey, data.getAsObject());
                continue;
            }
            offSetVchrItemDTO.addFieldValue(dimKey, (Object)this.getDimValueByDimCodeAndOrg(env, defaultDimOrgId, dimKey));
        }
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

    private GcOffSetVchrItemDTO createNewOffSetVchrItem(String mrecid, GcCalcArgmentsDTO calcArgments, AbstractInvestmentRule rule) {
        GcOffSetVchrItemDTO initOffSetVchrItem = new GcOffSetVchrItemDTO();
        initOffSetVchrItem.setmRecid(mrecid);
        initOffSetVchrItem.setDefaultPeriod(calcArgments.getPeriodStr());
        initOffSetVchrItem.setAcctPeriod(calcArgments.getAcctPeriod());
        initOffSetVchrItem.setAcctYear(calcArgments.getAcctYear());
        initOffSetVchrItem.setTaskId(calcArgments.getTaskId());
        initOffSetVchrItem.setSchemeId(calcArgments.getSchemeId());
        initOffSetVchrItem.setId(UUIDOrderUtils.newUUIDStr());
        initOffSetVchrItem.setElmMode(Integer.valueOf(OffsetElmModeEnum.AUTO_ITEM.getValue()));
        initOffSetVchrItem.setCreateTime(Calendar.getInstance().getTime());
        initOffSetVchrItem.setGcBusinessTypeCode(rule.getBusinessTypeCode());
        initOffSetVchrItem.setRuleId(rule.getId());
        initOffSetVchrItem.setOffSetSrcType(rule.getEquityLawAdjustFlag() != false ? OffSetSrcTypeEnum.EQUITY_METHOD_ADJ : OffSetSrcTypeEnum.CONSOLIDATE);
        initOffSetVchrItem.setOffSetCurr(calcArgments.getCurrency());
        initOffSetVchrItem.setDebit(Double.valueOf(0.0));
        initOffSetVchrItem.setCredit(Double.valueOf(0.0));
        initOffSetVchrItem.setOffSetDebit(Double.valueOf(0.0));
        initOffSetVchrItem.setOffSetCredit(Double.valueOf(0.0));
        initOffSetVchrItem.setBfOffSetDebit(Double.valueOf(0.0));
        initOffSetVchrItem.setBfOffSetCredit(Double.valueOf(0.0));
        initOffSetVchrItem.setDiffd(Double.valueOf(0.0));
        initOffSetVchrItem.setDiffc(Double.valueOf(0.0));
        initOffSetVchrItem.setOrgType(GCOrgTypeEnum.NONE.getCode());
        initOffSetVchrItem.setSelectAdjustCode(calcArgments.getSelectAdjustCode());
        return initOffSetVchrItem;
    }
}

