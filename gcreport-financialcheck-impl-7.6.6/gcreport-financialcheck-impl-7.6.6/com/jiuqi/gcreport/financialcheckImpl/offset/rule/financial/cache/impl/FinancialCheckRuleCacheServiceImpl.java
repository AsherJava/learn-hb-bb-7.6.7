/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckFetchConfig
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckFetchConfig$Item
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.RuleChangeEvent
 *  com.jiuqi.gcreport.unionrule.dto.RuleChangeEvent$RuleChangedInfo
 *  com.jiuqi.gcreport.unionrule.enums.FetchTypeEnum
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.np.cache.NedisCacheManager
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.rule.financial.cache.impl;

import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.financial.cache.FinancialCheckRuleCacheService;
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckFetchConfig;
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO;
import com.jiuqi.gcreport.unionrule.dto.RuleChangeEvent;
import com.jiuqi.gcreport.unionrule.enums.FetchTypeEnum;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.np.cache.NedisCacheManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class FinancialCheckRuleCacheServiceImpl
implements FinancialCheckRuleCacheService,
ApplicationListener<RuleChangeEvent> {
    private NedisCacheManager cacheManger;
    private ConsolidatedSubjectService consolidatedSubjectService;
    private Logger logger = LoggerFactory.getLogger(FinancialCheckRuleCacheServiceImpl.class);

    public FinancialCheckRuleCacheServiceImpl(NedisCacheManager cacheManger, ConsolidatedSubjectService consolidatedSubjectService) {
        this.cacheManger = cacheManger;
        this.consolidatedSubjectService = consolidatedSubjectService;
    }

    @Override
    public void onApplicationEvent(RuleChangeEvent event) {
        RuleChangeEvent.RuleChangedInfo ruleChangedInfo = event.getFlexRuleChangedInfo();
        if (ruleChangedInfo.getRuleType().equals(RuleTypeEnum.FLEXIBLE.getCode()) || ruleChangedInfo.getRuleType().equals(RuleTypeEnum.FINANCIAL_CHECK.getCode()) || ruleChangedInfo.getRuleType().equals(RuleTypeEnum.RELATE_TRANSACTIONS.getCode())) {
            this.cacheManger.getCache("gcreport:flexRule").evict(ruleChangedInfo.getId());
        }
    }

    @Override
    public FinancialCheckRuleDTO getRule(FinancialCheckRuleDTO rule) {
        if (rule == null || StringUtils.isEmpty(rule.getId())) {
            return null;
        }
        return this.copyFlexibleRuleDTO((FinancialCheckRuleDTO)this.cacheManger.getCache("gcreport:flexRule").get(rule.getId(), () -> this.valueLoader(rule)));
    }

    private FinancialCheckRuleDTO valueLoader(FinancialCheckRuleDTO rule) {
        try {
            return this.analysisRule(rule);
        }
        catch (RuntimeException e) {
            this.logger.error("\u5173\u8054\u4ea4\u6613\u89c4\u5219\u201c{}\u201d\u7f13\u5b58\u52a0\u8f7d\u5f02\u5e38", (Object)rule.getTitle(), (Object)e);
            throw e;
        }
    }

    private FinancialCheckRuleDTO analysisRule(FinancialCheckRuleDTO rule) {
        if (CollectionUtils.isEmpty(rule.getFetchConfigList())) {
            return rule;
        }
        rule.getFetchConfigList().forEach(fetchConfig -> this.analysisFetchConfig((FinancialCheckFetchConfig)fetchConfig, rule.getReportSystem()));
        return rule;
    }

    private void analysisFetchConfig(FinancialCheckFetchConfig fetchConfig, String reportSystemId) {
        List<FinancialCheckFetchConfig.Item> debitItems = this.analysisItems(fetchConfig.getDebitConfigList(), reportSystemId);
        List<FinancialCheckFetchConfig.Item> creditItems = this.analysisItems(fetchConfig.getCreditConfigList(), reportSystemId);
        fetchConfig.setDebitConfigList(debitItems);
        fetchConfig.setCreditConfigList(creditItems);
    }

    private List<FinancialCheckFetchConfig.Item> analysisItems(Collection<FinancialCheckFetchConfig.Item> srcItems, String reportSystemId) {
        if (CollectionUtils.isEmpty(srcItems)) {
            return Collections.emptyList();
        }
        ArrayList<FinancialCheckFetchConfig.Item> analysisResult = new ArrayList<FinancialCheckFetchConfig.Item>();
        srcItems.forEach(item -> this.analysisItem((List<FinancialCheckFetchConfig.Item>)analysisResult, (FinancialCheckFetchConfig.Item)item, reportSystemId));
        return analysisResult;
    }

    private void analysisItem(List<FinancialCheckFetchConfig.Item> analysisResult, FinancialCheckFetchConfig.Item fetchNumSettingItem, String reportSystemId) {
        boolean isDetailFetchType;
        analysisResult.add(fetchNumSettingItem);
        FetchTypeEnum fetchType = fetchNumSettingItem.getFetchType();
        boolean bl = isDetailFetchType = fetchType == FetchTypeEnum.ALL_DETAIL || fetchType == FetchTypeEnum.DEBIT_DETAIL || fetchType == FetchTypeEnum.CREDIT_DETAIL;
        if (!isDetailFetchType) {
            return;
        }
        String formula = fetchNumSettingItem.getFetchFormula();
        if (StringUtils.isEmpty(formula)) {
            this.appendNoFormulaChildrenFetch(analysisResult, fetchNumSettingItem, reportSystemId);
        } else {
            this.appenSpecialFormulaChildrenFetch(analysisResult, fetchNumSettingItem, reportSystemId);
        }
    }

    private void appendNoFormulaChildrenFetch(List<FinancialCheckFetchConfig.Item> analysisResult, FinancialCheckFetchConfig.Item fetchNumSettingItem, String reportSystemId) {
        Set childrenSubjectCodes = this.consolidatedSubjectService.listAllChildrenCodes(fetchNumSettingItem.getSubjectCode(), reportSystemId);
        if (CollectionUtils.isEmpty(childrenSubjectCodes)) {
            return;
        }
        childrenSubjectCodes.forEach(subjectCode -> {
            FinancialCheckFetchConfig.Item item = new FinancialCheckFetchConfig.Item();
            item.setFetchType(fetchNumSettingItem.getFetchType());
            item.setSubjectCode(subjectCode);
            item.setDimSrcType(fetchNumSettingItem.getDimSrcType());
            analysisResult.add(item);
        });
    }

    private void appenSpecialFormulaChildrenFetch(List<FinancialCheckFetchConfig.Item> analysisResult, FinancialCheckFetchConfig.Item fetchNumSettingItem, String reportSystemId) {
        String splitToChChild;
        String formula = fetchNumSettingItem.getFetchFormula();
        if (!formula.contains(splitToChChild = "\"CHILDACCOUNT\"")) {
            return;
        }
        Set childrenSubjectCodes = this.consolidatedSubjectService.listAllChildrenCodes(fetchNumSettingItem.getSubjectCode(), reportSystemId);
        if (CollectionUtils.isEmpty(childrenSubjectCodes)) {
            return;
        }
        fetchNumSettingItem.setFetchFormula(formula.replace(splitToChChild, "\"" + fetchNumSettingItem.getSubjectCode() + "\""));
        childrenSubjectCodes.forEach(subjectCode -> {
            FinancialCheckFetchConfig.Item item = new FinancialCheckFetchConfig.Item();
            item.setFetchType(fetchNumSettingItem.getFetchType());
            item.setSubjectCode(subjectCode);
            item.setFetchFormula(formula.replace(splitToChChild, "\"" + subjectCode + "\""));
            item.setDimSrcType(fetchNumSettingItem.getDimSrcType());
            analysisResult.add(item);
        });
    }

    private FinancialCheckRuleDTO copyFlexibleRuleDTO(FinancialCheckRuleDTO flexibleRuleCacheDTO) {
        FinancialCheckRuleDTO flexibleRuleDTO = new FinancialCheckRuleDTO();
        BeanUtils.copyProperties(flexibleRuleCacheDTO, flexibleRuleDTO);
        if (!CollectionUtils.isEmpty(flexibleRuleCacheDTO.getCreditItemList())) {
            ArrayList creditItemList = new ArrayList(flexibleRuleCacheDTO.getCreditItemList());
            flexibleRuleDTO.setCreditItemList(creditItemList);
        }
        if (!CollectionUtils.isEmpty(flexibleRuleCacheDTO.getDebitItemList())) {
            ArrayList debitItemList = new ArrayList(flexibleRuleCacheDTO.getDebitItemList());
            flexibleRuleDTO.setDebitItemList(debitItemList);
        }
        ArrayList flexibleFetchConfigs = new ArrayList();
        if (!CollectionUtils.isEmpty(flexibleRuleCacheDTO.getFetchConfigList())) {
            flexibleRuleCacheDTO.getFetchConfigList().forEach(flexibleFetchConfigCache -> flexibleFetchConfigs.add(this.copyFlexibleFetchConfig((FinancialCheckFetchConfig)flexibleFetchConfigCache)));
        }
        flexibleRuleDTO.setFetchConfigList(flexibleFetchConfigs);
        if (!CollectionUtils.isEmpty(flexibleRuleCacheDTO.getOffsetGroupingField())) {
            ArrayList offsetGroupingField = new ArrayList(flexibleRuleCacheDTO.getOffsetGroupingField());
            flexibleRuleDTO.setOffsetGroupingField(offsetGroupingField);
        }
        return flexibleRuleDTO;
    }

    private FinancialCheckFetchConfig copyFlexibleFetchConfig(FinancialCheckFetchConfig flexibleFetchConfigCache) {
        FinancialCheckFetchConfig flexibleFetchConfig = new FinancialCheckFetchConfig();
        BeanUtils.copyProperties(flexibleFetchConfigCache, flexibleFetchConfig);
        ArrayList debitConfigList = new ArrayList();
        if (!CollectionUtils.isEmpty(flexibleFetchConfigCache.getDebitConfigList())) {
            flexibleFetchConfigCache.getDebitConfigList().forEach(itemCache -> debitConfigList.add(this.copyFlexibleFetchConfigItem((FinancialCheckFetchConfig.Item)itemCache)));
        }
        flexibleFetchConfig.setDebitConfigList(debitConfigList);
        ArrayList creditConfigList = new ArrayList();
        if (!CollectionUtils.isEmpty(flexibleFetchConfigCache.getCreditConfigList())) {
            flexibleFetchConfigCache.getCreditConfigList().forEach(itemCache -> creditConfigList.add(this.copyFlexibleFetchConfigItem((FinancialCheckFetchConfig.Item)itemCache)));
        }
        flexibleFetchConfig.setCreditConfigList(creditConfigList);
        return flexibleFetchConfig;
    }

    private FinancialCheckFetchConfig.Item copyFlexibleFetchConfigItem(FinancialCheckFetchConfig.Item itemCache) {
        FinancialCheckFetchConfig.Item item = new FinancialCheckFetchConfig.Item();
        BeanUtils.copyProperties(itemCache, item);
        return item;
    }
}

