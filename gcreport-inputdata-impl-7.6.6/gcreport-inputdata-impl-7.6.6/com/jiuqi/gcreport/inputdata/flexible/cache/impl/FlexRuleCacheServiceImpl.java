/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleFetchConfig
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleFetchConfig$Item
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.RuleChangeEvent
 *  com.jiuqi.gcreport.unionrule.dto.RuleChangeEvent$RuleChangedInfo
 *  com.jiuqi.gcreport.unionrule.enums.FetchTypeEnum
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.np.cache.NedisCacheManager
 */
package com.jiuqi.gcreport.inputdata.flexible.cache.impl;

import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.inputdata.flexible.cache.FlexRuleCacheService;
import com.jiuqi.gcreport.unionrule.dto.FlexibleFetchConfig;
import com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO;
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
public class FlexRuleCacheServiceImpl
implements FlexRuleCacheService,
ApplicationListener<RuleChangeEvent> {
    private NedisCacheManager cacheManger;
    private ConsolidatedSubjectService consolidatedSubjectService;
    private Logger logger = LoggerFactory.getLogger(FlexRuleCacheServiceImpl.class);

    public FlexRuleCacheServiceImpl(NedisCacheManager cacheManger, ConsolidatedSubjectService consolidatedSubjectService) {
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
    public FlexibleRuleDTO getFlexRule(FlexibleRuleDTO rule) {
        if (rule == null || StringUtils.isEmpty(rule.getId())) {
            return null;
        }
        return this.copyFlexibleRuleDTO((FlexibleRuleDTO)this.cacheManger.getCache("gcreport:flexRule").get(rule.getId(), () -> this.valueLoader(rule)));
    }

    private FlexibleRuleDTO valueLoader(FlexibleRuleDTO rule) {
        try {
            return this.analysisRule(rule);
        }
        catch (RuntimeException e) {
            this.logger.error("\u7075\u6d3b\u89c4\u5219\u201c{}\u201d\u7f13\u5b58\u52a0\u8f7d\u5f02\u5e38", (Object)rule.getLocalizedName(), (Object)e);
            throw e;
        }
    }

    private FlexibleRuleDTO analysisRule(FlexibleRuleDTO rule) {
        if (CollectionUtils.isEmpty(rule.getFetchConfigList())) {
            return rule;
        }
        rule.getFetchConfigList().forEach(fetchConfig -> this.analysisFetchConfig((FlexibleFetchConfig)fetchConfig, rule.getReportSystem()));
        return rule;
    }

    private void analysisFetchConfig(FlexibleFetchConfig fetchConfig, String reportSystemId) {
        List<FlexibleFetchConfig.Item> debitItems = this.analysisItems(fetchConfig.getDebitConfigList(), reportSystemId);
        List<FlexibleFetchConfig.Item> creditItems = this.analysisItems(fetchConfig.getCreditConfigList(), reportSystemId);
        fetchConfig.setDebitConfigList(debitItems);
        fetchConfig.setCreditConfigList(creditItems);
    }

    private List<FlexibleFetchConfig.Item> analysisItems(Collection<FlexibleFetchConfig.Item> srcItems, String reportSystemId) {
        if (CollectionUtils.isEmpty(srcItems)) {
            return Collections.emptyList();
        }
        ArrayList<FlexibleFetchConfig.Item> analysisResult = new ArrayList<FlexibleFetchConfig.Item>();
        srcItems.forEach(item -> this.analysisItem((List<FlexibleFetchConfig.Item>)analysisResult, (FlexibleFetchConfig.Item)item, reportSystemId));
        return analysisResult;
    }

    private void analysisItem(List<FlexibleFetchConfig.Item> analysisResult, FlexibleFetchConfig.Item fetchNumSettingItem, String reportSystemId) {
        analysisResult.add(fetchNumSettingItem);
        String formula = fetchNumSettingItem.getFetchFormula();
        if (StringUtils.isEmpty(formula)) {
            boolean isDetailFetchType;
            FetchTypeEnum fetchType = fetchNumSettingItem.getFetchType();
            boolean bl = isDetailFetchType = fetchType == FetchTypeEnum.ALL_DETAIL || fetchType == FetchTypeEnum.DEBIT_DETAIL || fetchType == FetchTypeEnum.CREDIT_DETAIL;
            if (!isDetailFetchType) {
                return;
            }
            this.appendNoFormulaChildrenFetch(analysisResult, fetchNumSettingItem, reportSystemId);
        } else {
            this.appenSpecialFormulaChildrenFetch(analysisResult, fetchNumSettingItem, reportSystemId);
        }
    }

    private void appendNoFormulaChildrenFetch(List<FlexibleFetchConfig.Item> analysisResult, FlexibleFetchConfig.Item fetchNumSettingItem, String reportSystemId) {
        Set childrenSubjectCodes = this.consolidatedSubjectService.listAllChildrenCodes(fetchNumSettingItem.getSubjectCode(), reportSystemId);
        if (CollectionUtils.isEmpty(childrenSubjectCodes)) {
            return;
        }
        childrenSubjectCodes.forEach(subjectCode -> {
            FlexibleFetchConfig.Item item = new FlexibleFetchConfig.Item();
            item.setFetchType(fetchNumSettingItem.getFetchType());
            item.setSubjectCode(subjectCode);
            item.setDimensions(fetchNumSettingItem.getDimensions());
            analysisResult.add(item);
        });
    }

    private void appenSpecialFormulaChildrenFetch(List<FlexibleFetchConfig.Item> analysisResult, FlexibleFetchConfig.Item fetchNumSettingItem, String reportSystemId) {
        String splitToChChild;
        String formula = fetchNumSettingItem.getFetchFormula();
        if (!formula.contains(splitToChChild = "\"CHILDACCOUNT\"")) {
            return;
        }
        Set childrenSubjectCodes = this.consolidatedSubjectService.listAllChildrenCodes(fetchNumSettingItem.getSubjectCode(), reportSystemId);
        if (CollectionUtils.isEmpty(childrenSubjectCodes)) {
            return;
        }
        String subjectCodeFirstValue = (String)childrenSubjectCodes.stream().findFirst().get();
        fetchNumSettingItem.setFetchFormula(formula.replace(splitToChChild, "\"" + subjectCodeFirstValue + "\""));
        fetchNumSettingItem.setSubjectCode(subjectCodeFirstValue);
        childrenSubjectCodes.remove(subjectCodeFirstValue);
        if (CollectionUtils.isEmpty(childrenSubjectCodes)) {
            return;
        }
        childrenSubjectCodes.forEach(subjectCode -> {
            Set childrens = this.consolidatedSubjectService.listAllChildrenCodes(subjectCode, reportSystemId);
            if (!CollectionUtils.isEmpty(childrens)) {
                return;
            }
            FlexibleFetchConfig.Item item = new FlexibleFetchConfig.Item();
            item.setFetchType(fetchNumSettingItem.getFetchType());
            item.setSubjectCode(subjectCode);
            item.setFetchFormula(formula.replace(splitToChChild, "\"" + subjectCode + "\""));
            item.setDimensions(fetchNumSettingItem.getDimensions());
            analysisResult.add(item);
        });
    }

    private FlexibleRuleDTO copyFlexibleRuleDTO(FlexibleRuleDTO flexibleRuleCacheDTO) {
        FlexibleRuleDTO flexibleRuleDTO = new FlexibleRuleDTO();
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
            flexibleRuleCacheDTO.getFetchConfigList().forEach(flexibleFetchConfigCache -> flexibleFetchConfigs.add(this.copyFlexibleFetchConfig((FlexibleFetchConfig)flexibleFetchConfigCache)));
        }
        flexibleRuleDTO.setFetchConfigList(flexibleFetchConfigs);
        if (!CollectionUtils.isEmpty(flexibleRuleCacheDTO.getOffsetGroupingField())) {
            ArrayList offsetGroupingField = new ArrayList(flexibleRuleCacheDTO.getOffsetGroupingField());
            flexibleRuleDTO.setOffsetGroupingField(offsetGroupingField);
        }
        return flexibleRuleDTO;
    }

    private FlexibleFetchConfig copyFlexibleFetchConfig(FlexibleFetchConfig flexibleFetchConfigCache) {
        FlexibleFetchConfig flexibleFetchConfig = new FlexibleFetchConfig();
        BeanUtils.copyProperties(flexibleFetchConfigCache, flexibleFetchConfig);
        if (!CollectionUtils.isEmpty(flexibleFetchConfigCache.getAssociatedSubject())) {
            ArrayList associatedSubject = new ArrayList(flexibleFetchConfigCache.getAssociatedSubject());
            flexibleFetchConfig.setAssociatedSubject(associatedSubject);
        }
        ArrayList debitConfigList = new ArrayList();
        if (!CollectionUtils.isEmpty(flexibleFetchConfigCache.getDebitConfigList())) {
            flexibleFetchConfigCache.getDebitConfigList().forEach(itemCache -> debitConfigList.add(this.copyFlexibleFetchConfigItem((FlexibleFetchConfig.Item)itemCache)));
        }
        flexibleFetchConfig.setDebitConfigList(debitConfigList);
        ArrayList creditConfigList = new ArrayList();
        if (!CollectionUtils.isEmpty(flexibleFetchConfigCache.getCreditConfigList())) {
            flexibleFetchConfigCache.getCreditConfigList().forEach(itemCache -> creditConfigList.add(this.copyFlexibleFetchConfigItem((FlexibleFetchConfig.Item)itemCache)));
        }
        flexibleFetchConfig.setCreditConfigList(creditConfigList);
        return flexibleFetchConfig;
    }

    private FlexibleFetchConfig.Item copyFlexibleFetchConfigItem(FlexibleFetchConfig.Item itemCache) {
        FlexibleFetchConfig.Item item = new FlexibleFetchConfig.Item();
        BeanUtils.copyProperties(itemCache, item);
        return item;
    }
}

