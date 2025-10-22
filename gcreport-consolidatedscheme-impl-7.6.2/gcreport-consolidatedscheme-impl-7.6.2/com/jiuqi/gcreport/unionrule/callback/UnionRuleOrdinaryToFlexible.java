/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleFetchConfig
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleFetchConfig$Item
 *  com.jiuqi.gcreport.unionrule.enums.FetchTypeEnum
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.unionrule.callback;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.unionrule.dto.FlexibleFetchConfig;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.enums.FetchTypeEnum;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UnionRuleOrdinaryToFlexible
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(UnionRuleOrdinaryToFlexible.class);

    public void execute(DataSource dataSource) throws Exception {
        List ordinaryRules = EntNativeSqlDefaultDao.getInstance().selectMap(" select *  from GC_UNIONRULE WHERE RULETYPE = 'ORDINARY' ", new Object[0]);
        if (CollectionUtils.isEmpty((Collection)ordinaryRules)) {
            return;
        }
        HashMap<String, List<ConsolidatedSubjectEO>> consolidateSubjects = new HashMap<String, List<ConsolidatedSubjectEO>>();
        for (Map ordinaryRuleMap : ordinaryRules) {
            try {
                UnionRuleEO ordinaryRule = (UnionRuleEO)((Object)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)ordinaryRuleMap), UnionRuleEO.class));
                ((UnionRuleOrdinaryToFlexible)SpringBeanUtils.getBean(UnionRuleOrdinaryToFlexible.class)).disposeOneRule(ordinaryRule, consolidateSubjects);
            }
            catch (Exception e) {
                logger.error("\u666e\u901a\u89c4\u5219[{}]\u5347\u7ea7\u7075\u6d3b\u89c4\u5219\u5931\u8d25,\u5931\u8d25\u539f\u56e0\u4e3a[{}]", ordinaryRuleMap.get("id"), (Object)e.getMessage());
            }
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void disposeOneRule(UnionRuleEO ordinaryRule, Map<String, List<ConsolidatedSubjectEO>> consolidateSubjects) {
        List<ConsolidatedSubjectEO> subjects;
        String jsonString = ordinaryRule.getJsonString();
        Map ordinaryRuleJsonMap = (Map)JsonUtils.readValue((String)jsonString, Map.class);
        HashMap<String, Object> flexibleMap = new HashMap<String, Object>();
        flexibleMap.put("offsetLevelCondition", "");
        Object bothDCFlag = ordinaryRuleJsonMap.get("bothDCFlag");
        if (Objects.nonNull(bothDCFlag) && ((Boolean)bothDCFlag).booleanValue()) {
            flexibleMap.put("offsetGroupingField", Arrays.asList("MDCODE", "OPPUNITID"));
        } else {
            flexibleMap.put("offsetGroupingField", Arrays.asList("MDCODE", "OPPUNITID", "DC"));
        }
        flexibleMap.put("realTimeOffsetFlag", true);
        flexibleMap.put("reconciliationOffsetFlag", false);
        flexibleMap.put("oneToOneOffsetFlag", true);
        flexibleMap.put("unilateralOffsetFlag", ordinaryRuleJsonMap.get("unilateralOffsetFlag"));
        flexibleMap.put("proportionOffsetDiffFlag", false);
        flexibleMap.put("generatePHSFlag", false);
        ArrayList<FlexibleFetchConfig> fetchConfigList = new ArrayList<FlexibleFetchConfig>();
        FlexibleFetchConfig flexibleFetchConfig = new FlexibleFetchConfig();
        String fetchSetGroupId = UUIDUtils.newUUIDStr();
        flexibleFetchConfig.setFetchSetGroupId(fetchSetGroupId);
        flexibleFetchConfig.setDescription(ordinaryRule.getTitle());
        flexibleFetchConfig.setFilterFormula("");
        flexibleFetchConfig.setAssociatedSubject(new ArrayList());
        flexibleFetchConfig.setBusinessTypeCode("");
        flexibleFetchConfig.setManualFilterFormula("");
        if (consolidateSubjects.containsKey(ordinaryRule.getId())) {
            subjects = consolidateSubjects.get(ordinaryRule.getId());
        } else {
            subjects = ((ConsolidatedSubjectService)SpringBeanUtils.getBean(ConsolidatedSubjectService.class)).listAllSubjectsBySystemId(ordinaryRule.getReportSystem());
            consolidateSubjects.put(ordinaryRule.getId(), subjects);
        }
        List debitItemList = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(ordinaryRuleJsonMap.get("debitItemList")), ArrayList.class);
        if (!CollectionUtils.isEmpty((Collection)debitItemList)) {
            ArrayList<String> debitItemSubject = new ArrayList<String>();
            ArrayList debitFetchConfigItems = new ArrayList();
            debitItemList.forEach(debitItem -> {
                String subject = (String)debitItem.get("subjectCode");
                FlexibleFetchConfig.Item item = new FlexibleFetchConfig.Item();
                item.setFetchFormula("");
                item.setFetchType(FetchTypeEnum.ALL_DETAIL);
                item.setSubjectCode(subject);
                debitFetchConfigItems.add(item);
                debitItemSubject.add(subject);
            });
            flexibleFetchConfig.setDebitConfigList(debitFetchConfigItems);
            Set<String> baseDataCodeOnlyParent = ((ConsolidatedSubjectService)SpringBeanUtils.getBean(ConsolidatedSubjectService.class)).filterByExcludeChild(subjects, debitItemSubject);
            flexibleMap.put("debitItemList", new ArrayList<String>(baseDataCodeOnlyParent));
        } else {
            flexibleFetchConfig.setDebitConfigList(new ArrayList());
            flexibleMap.put("debitItemList", new ArrayList());
        }
        List creditItemList = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(ordinaryRuleJsonMap.get("creditItemList")), ArrayList.class);
        if (!CollectionUtils.isEmpty((Collection)creditItemList)) {
            ArrayList<String> creditItemSubject = new ArrayList<String>();
            ArrayList creditFetchConfigItems = new ArrayList();
            creditItemList.forEach(creditItem -> {
                String subject = (String)creditItem.get("subjectCode");
                FlexibleFetchConfig.Item item = new FlexibleFetchConfig.Item();
                item.setFetchFormula("");
                item.setFetchType(FetchTypeEnum.ALL_DETAIL);
                item.setSubjectCode(subject);
                creditFetchConfigItems.add(item);
                creditItemSubject.add(subject);
            });
            flexibleFetchConfig.setCreditConfigList(creditFetchConfigItems);
            Set<String> baseDataCodeOnlyParent = ((ConsolidatedSubjectService)SpringBeanUtils.getBean(ConsolidatedSubjectService.class)).filterByExcludeChild(subjects, creditItemSubject);
            flexibleMap.put("creditItemList", new ArrayList<String>(baseDataCodeOnlyParent));
        } else {
            flexibleFetchConfig.setCreditConfigList(new ArrayList());
            flexibleMap.put("creditItemList", new ArrayList());
        }
        fetchConfigList.add(flexibleFetchConfig);
        flexibleMap.put("fetchConfigList", fetchConfigList);
        String updateRuleSql = " update GC_UNIONRULE set RULETYPE= 'FLEXIBLE', JSONSTRING=?  where id = ? ";
        EntNativeSqlDefaultDao.getInstance().execute(updateRuleSql, new Object[]{JsonUtils.writeValueAsString(flexibleMap), ordinaryRule.getId()});
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u89c4\u5219", (String)("\u4fee\u6539-" + ordinaryRule.getReportSystem() + "\u5408\u5e76\u4f53\u7cfb-" + ordinaryRule.getTitle() + "\u5408\u5e76\u89c4\u5219"), (String)("\u4fee\u6539\u540e:\n" + JsonUtils.writeValueAsString((Object)((Object)ordinaryRule))));
        String updateOffsetSql = " update GC_OFFSETVCHRITEM set FETCHSETGROUPID = ? where RULEID = ? ";
        EntNativeSqlDefaultDao.getInstance().execute(updateOffsetSql, new Object[]{fetchSetGroupId, ordinaryRule.getId()});
    }
}

