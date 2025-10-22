/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.service.InvestBillInitialMergeCalcCache
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.init.dao.GcOffSetVchrItemInitDao
 *  com.jiuqi.gcreport.offsetitem.init.entity.GcOffSetVchrItemInitEO
 *  com.jiuqi.gcreport.offsetitem.init.vo.OffsetItemInitQueryParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 */
package com.jiuqi.gcreport.invest.calculate.rule.processor.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.service.InvestBillInitialMergeCalcCache;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.invest.calculate.rule.dispatcher.executor.GcCalcInvestBillRuleDispatcherExecutor;
import com.jiuqi.gcreport.invest.calculate.rule.processor.executor.GcCalcInvestBillRuleMonthlyExecutor;
import com.jiuqi.gcreport.invest.investbill.dao.InvestBillDao;
import com.jiuqi.gcreport.invest.investbill.dto.GcInvestBillGroupDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.init.dao.GcOffSetVchrItemInitDao;
import com.jiuqi.gcreport.offsetitem.init.entity.GcOffSetVchrItemInitEO;
import com.jiuqi.gcreport.offsetitem.init.vo.OffsetItemInitQueryParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvestBillInitialMergeCalcCacheImpl
implements InvestBillInitialMergeCalcCache {
    private Logger logger = LoggerFactory.getLogger(InvestBillInitialMergeCalcCacheImpl.class);
    private static final String CACHE_KEY_ALL_INIT = "ALLINIT";
    private static final String CACHE_KEY_PRIOR_MAST = "PRIORMAST";
    private final ReadWriteLock read_write_lock = new ReentrantReadWriteLock();
    @Autowired
    private GcCalcInvestBillRuleDispatcherExecutor investBillExecutor;
    @Autowired
    private InvestBillDao investBillDao;
    private Map<String, SoftReference<Map<String, Set<String>>>> initialMergeCache = new ConcurrentHashMap<String, SoftReference<Map<String, Set<String>>>>();

    public Set<String> getInitialMergeCache(GcCalcArgmentsDTO calcArgments) {
        return (Set)MapUtils.getVal(this.getCacheMap(calcArgments), (Object)CACHE_KEY_ALL_INIT, Collections.emptySet());
    }

    public Set<String> getPriorMastIdSetCache(GcCalcArgmentsDTO calcArgments) {
        return (Set)MapUtils.getVal(this.getCacheMap(calcArgments), (Object)CACHE_KEY_PRIOR_MAST, Collections.emptySet());
    }

    private Map<String, Set<String>> getCacheMap(GcCalcArgmentsDTO calcArgments) {
        Map<String, Set<String>> investMastIdCacheMap;
        String sn = calcArgments.getSn();
        SoftReference<Map<String, Set<String>>> setWeakReference = this.initialMergeCache.get(sn);
        if (setWeakReference == null) {
            investMastIdCacheMap = this.initCacheBySn(calcArgments);
        } else {
            investMastIdCacheMap = setWeakReference.get();
            if (investMastIdCacheMap == null) {
                investMastIdCacheMap = this.initCacheBySn(calcArgments);
            }
        }
        this.checkRemoveNullValue();
        return investMastIdCacheMap;
    }

    public void removeCacheBySn(String sn) {
        this.read_write_lock.readLock().lock();
        try {
            this.initialMergeCache.remove(sn);
        }
        finally {
            this.read_write_lock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void checkRemoveNullValue() {
        this.read_write_lock.readLock().lock();
        try {
            for (String sn : this.initialMergeCache.keySet()) {
                SoftReference<Map<String, Set<String>>> setWeakReference = this.initialMergeCache.get(sn);
                if (setWeakReference == null) {
                    this.initialMergeCache.remove(sn);
                    continue;
                }
                Map<String, Set<String>> cacheMap = setWeakReference.get();
                if (cacheMap != null) continue;
                this.initialMergeCache.remove(sn);
            }
        }
        finally {
            this.read_write_lock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Map<String, Set<String>> initCacheBySn(GcCalcArgmentsDTO calcArgments) {
        InvestBillInitialMergeCalcCacheImpl investBillInitialMergeCalcCacheImpl = this;
        synchronized (investBillInitialMergeCalcCacheImpl) {
            if (this.initialMergeCache.get(calcArgments.getSn()) != null && this.initialMergeCache.get(calcArgments.getSn()).get() != null) {
                return this.initialMergeCache.get(calcArgments.getSn()).get();
            }
            Map<String, Set<String>> cacheMap = this.getInitialMergeMastIdSet(calcArgments);
            SoftReference<Map<String, Set<String>>> setWeakReference = new SoftReference<Map<String, Set<String>>>(cacheMap);
            this.initialMergeCache.put(calcArgments.getSn(), setWeakReference);
            return cacheMap;
        }
    }

    private Map<String, Set<String>> getInitialMergeMastIdSet(GcCalcArgmentsDTO calcArgments) {
        GcCalcArgmentsDTO calcArgmentsCopy = new GcCalcArgmentsDTO();
        BeanUtils.copyProperties(calcArgments, calcArgmentsCopy);
        HashMap<String, Set<String>> cacheMap = new HashMap<String, Set<String>>();
        String hbOrgId = calcArgmentsCopy.getOrgId();
        YearPeriodObject yp = new YearPeriodObject(null, calcArgmentsCopy.getPeriodStr());
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)calcArgmentsCopy.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO hbOrg = orgTool.getOrgByCode(hbOrgId);
        String baseUnitId = hbOrg.getBaseUnitId();
        if (baseUnitId == null) {
            return cacheMap;
        }
        List childOrgs = orgTool.getOrgChildrenTree(hbOrgId);
        if (childOrgs == null || childOrgs.size() == 0) {
            return cacheMap;
        }
        Map<Boolean, List<GcInvestBillGroupDTO>> investmentDatasGroup = this.investBillExecutor.getInvestmentDatasGroupMap(calcArgmentsCopy, orgTool, hbOrg, baseUnitId, childOrgs);
        ArrayList<GcInvestBillGroupDTO> investmentList = new ArrayList<GcInvestBillGroupDTO>();
        if (!CollectionUtils.isEmpty((Collection)investmentDatasGroup.get(Boolean.TRUE))) {
            investmentList.addAll((Collection)investmentDatasGroup.get(Boolean.TRUE));
        }
        if (!CollectionUtils.isEmpty((Collection)investmentDatasGroup.get(Boolean.FALSE))) {
            investmentList.addAll((Collection)investmentDatasGroup.get(Boolean.FALSE));
        }
        if (CollectionUtils.isEmpty(investmentList)) {
            return cacheMap;
        }
        Set<String> ruleIdSet = this.getRuleChangeScenarioList(calcArgmentsCopy);
        if (ruleIdSet.size() == 0) {
            return cacheMap;
        }
        ArrayList<String> initialMergeRules = new ArrayList<String>(ruleIdSet);
        List<GcOffSetVchrItemInitEO> content = this.getGcOffSetVchrItemInitEOS(calcArgments, initialMergeRules);
        Set<String> initialMergeMastIdSetByInitOffset = this.getInitialMergeMastIdSetByInitOffset(investmentList, content);
        if (calcArgmentsCopy.getPeriodStr().endsWith("01")) {
            cacheMap.put(CACHE_KEY_ALL_INIT, initialMergeMastIdSetByInitOffset);
            cacheMap.put(CACHE_KEY_PRIOR_MAST, Collections.EMPTY_SET);
            return cacheMap;
        }
        List<GcOffSetVchrItemAdjustEO> offSetVchrItemAdjustEOS = ((GcCalcInvestBillRuleMonthlyExecutor)SpringContextUtils.getBean(GcCalcInvestBillRuleMonthlyExecutor.class)).getPriorGcOffSetVchrItemAdjustEOS(calcArgmentsCopy, initialMergeRules);
        if (CollectionUtils.isEmpty(offSetVchrItemAdjustEOS)) {
            cacheMap.put(CACHE_KEY_ALL_INIT, initialMergeMastIdSetByInitOffset);
            cacheMap.put(CACHE_KEY_PRIOR_MAST, Collections.EMPTY_SET);
            return cacheMap;
        }
        Set<String> investSrcIdSet = offSetVchrItemAdjustEOS.stream().map(GcOffSetVchrItemAdjustEO::getSrcOffsetGroupId).collect(Collectors.toSet());
        Set<String> priorMastIdSet = this.investBillDao.listInvestIdsBySrcIdsAndExactPeriod(investSrcIdSet, yp.getPeriod());
        HashSet<String> result = new HashSet<String>();
        for (String initialMergeMastId : initialMergeMastIdSetByInitOffset) {
            if (priorMastIdSet.contains(initialMergeMastId)) continue;
            result.add(initialMergeMastId);
        }
        cacheMap.put(CACHE_KEY_ALL_INIT, result);
        cacheMap.put(CACHE_KEY_PRIOR_MAST, priorMastIdSet);
        return cacheMap;
    }

    private List<GcOffSetVchrItemInitEO> getGcOffSetVchrItemInitEOS(GcCalcArgmentsDTO calcArgments, List<String> initialMergeRules) {
        OffsetItemInitQueryParamsVO queryParamsVO = new OffsetItemInitQueryParamsVO();
        BeanUtils.copyProperties(calcArgments, queryParamsVO);
        queryParamsVO.setOrgId(calcArgments.getOrgId());
        queryParamsVO.setRules(initialMergeRules);
        ConsolidatedTaskVO consolidatedTaskVO = ((ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class)).getTaskBySchemeId(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        if (consolidatedTaskVO != null) {
            queryParamsVO.setSystemId(consolidatedTaskVO.getSystemId());
            queryParamsVO.setTaskId(null);
        }
        Pagination page = ((GcOffSetVchrItemInitDao)SpringContextUtils.getBean(GcOffSetVchrItemInitDao.class)).queryOffsetingEntryEO(queryParamsVO);
        List content = page.getContent();
        return content;
    }

    private Set<String> getInitialMergeMastIdSetByInitOffset(List<GcInvestBillGroupDTO> directInvestmentGroups, List<GcOffSetVchrItemInitEO> content) {
        HashSet<String> cacheUnitKeySet = new HashSet<String>();
        for (GcOffSetVchrItemInitEO offSetVchrItemInitEO : content) {
            cacheUnitKeySet.add(this.getCacheUnitKey(offSetVchrItemInitEO.getUnitId(), offSetVchrItemInitEO.getOppUnitId()));
        }
        HashSet<String> result = new HashSet<String>();
        for (GcInvestBillGroupDTO dto : directInvestmentGroups) {
            String investedUnit;
            String investUnit = String.valueOf(dto.getMaster().getFieldValue("UNITCODE"));
            if (cacheUnitKeySet.contains(this.getCacheUnitKey(investUnit, investedUnit = String.valueOf(dto.getMaster().getFieldValue("INVESTEDUNIT"))))) continue;
            result.add(dto.getMaster().getId());
        }
        return result;
    }

    private Set<String> getInitialMergeMastIdSet(List<GcInvestBillGroupDTO> directInvestmentGroups, Set<String> priorPeriodMastIdSet) {
        HashSet<String> result = new HashSet<String>();
        for (GcInvestBillGroupDTO dto : directInvestmentGroups) {
            if (priorPeriodMastIdSet.contains(dto.getMaster().getId())) continue;
            result.add(dto.getMaster().getId());
        }
        return result;
    }

    private boolean verifyRuleIsInitialMerge(Map<String, Set<String>> ruleId2InitialMergeDictCodeSetMap, Date startPeriod, Date endPeriod, Set<String> priorPeriodMastIdSet, GcInvestBillGroupDTO dto, List<DefaultTableEntity> items) {
        for (String ruleId : ruleId2InitialMergeDictCodeSetMap.keySet()) {
            Set<String> initialMergeSet;
            ArrayList<DefaultTableEntity> itemList;
            boolean hasCalc = priorPeriodMastIdSet.contains(dto.getMaster().getId());
            if (!this.isInitialMerge(startPeriod, endPeriod, items, hasCalc, itemList = new ArrayList<DefaultTableEntity>(), initialMergeSet = ruleId2InitialMergeDictCodeSetMap.get(ruleId))) continue;
            String investUnit = String.valueOf(dto.getMaster().getFieldValue("UNITCODE"));
            String investedUnit = String.valueOf(dto.getMaster().getFieldValue("INVESTEDUNIT"));
            this.logger.info("\u53f0\u8d26\u3010\u6295\u8d44\u5355\u4f4d:{},\u88ab\u6295\u8d44\u5355\u4f4d:{}\u3011\uff0c\u7b26\u5408\u89c4\u5219\u3010{}\u3011\u4e2d\u7684\u521d\u6b21\u5408\u5e76\u573a\u666f\u3002", investUnit, investedUnit, ruleId);
            return true;
        }
        return false;
    }

    private boolean isInitialMerge(Date startPeriod, Date endPeriod, List<DefaultTableEntity> items, boolean hasCalc, List<DefaultTableEntity> itemList, Set<String> initialMergeSet) {
        for (DefaultTableEntity item : items) {
            Date changeDate;
            Object changeDateObj;
            Object changeScenarioObj = item.getFieldValue("CHANGESCENARIO");
            if (!initialMergeSet.contains(changeScenarioObj) || (changeDateObj = item.getFieldValue("CHANGEDATE")) == null || DateUtils.getYearOfDate((Date)(changeDate = (Date)changeDateObj)) != DateUtils.getYearOfDate((Date)startPeriod) || changeDate.compareTo(endPeriod) > 0 || changeDate.compareTo(startPeriod) < 0 && hasCalc) continue;
            return true;
        }
        return false;
    }

    private Set<String> getRuleChangeScenarioList(GcCalcArgmentsDTO calcArgments) {
        HashSet<String> ruleIdSet = new HashSet<String>(16);
        ArrayList<String> ruleTypeCodes = new ArrayList<String>();
        ruleTypeCodes.add(RuleTypeEnum.DIRECT_INVESTMENT.getCode());
        ruleTypeCodes.add(RuleTypeEnum.DIRECT_INVESTMENT_SEGMENT.getCode());
        ruleTypeCodes.add(RuleTypeEnum.INDIRECT_INVESTMENT.getCode());
        ruleTypeCodes.add(RuleTypeEnum.INDIRECT_INVESTMENT_SEGMENT.getCode());
        ruleTypeCodes.add(RuleTypeEnum.PUBLIC_VALUE_ADJUSTMENT.getCode());
        List rules = ((UnionRuleService)SpringContextUtils.getBean(UnionRuleService.class)).selectRuleListBySchemeIdAndRuleTypes(calcArgments.getSchemeId(), calcArgments.getPeriodStr(), ruleTypeCodes);
        for (AbstractUnionRule rule : rules) {
            ruleIdSet.add(rule.getId());
        }
        return ruleIdSet;
    }

    private String getCacheUnitKey(String investUnit, String investedUnit) {
        if (investUnit.compareTo(investedUnit) > 0) {
            return investedUnit + "|" + investUnit;
        }
        return investUnit + "|" + investedUnit;
    }
}

