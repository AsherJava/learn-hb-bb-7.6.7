/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.rule;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class FinancialCheckRuleJsonStringVO2EOHelper {
    private UnionRuleVO unionRuleVO;

    private FinancialCheckRuleJsonStringVO2EOHelper() {
    }

    public static FinancialCheckRuleJsonStringVO2EOHelper newInstance(UnionRuleVO unionRuleVO) {
        FinancialCheckRuleJsonStringVO2EOHelper helper = new FinancialCheckRuleJsonStringVO2EOHelper();
        helper.unionRuleVO = unionRuleVO;
        return helper;
    }

    public String convertJsonStringWhenVO2EO() {
        Map mapJson = (Map)JsonUtils.readValue((String)this.unionRuleVO.getJsonString(), (TypeReference)new TypeReference<Map<String, Object>>(){});
        List debitItemList = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(mapJson.get("debitItemList")), (TypeReference)new TypeReference<List<GcBaseDataVO>>(){});
        List creditItemList = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(mapJson.get("creditItemList")), (TypeReference)new TypeReference<List<GcBaseDataVO>>(){});
        List debitItemSelectedCodeList = debitItemList.stream().map(GcBaseDataVO::getCode).distinct().collect(Collectors.toList());
        List creditItemSelectedCodeList = creditItemList.stream().map(GcBaseDataVO::getCode).distinct().collect(Collectors.toList());
        ConsolidatedSubjectService consolidatedSubjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class);
        mapJson.put("debitItemList", consolidatedSubjectService.filterByExcludeChild(this.unionRuleVO.getReportSystem(), debitItemSelectedCodeList));
        mapJson.put("creditItemList", consolidatedSubjectService.filterByExcludeChild(this.unionRuleVO.getReportSystem(), creditItemSelectedCodeList));
        String fetchConfig = JsonUtils.writeValueAsString(mapJson.get("fetchConfigList"));
        List oldFetchConfigList = (List)JsonUtils.readValue((String)fetchConfig, (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
        List<Map<String, Object>> newFetchConfigList = this.handleSaveAllFetchSetting(oldFetchConfigList);
        mapJson.put("fetchConfigList", newFetchConfigList);
        return JsonUtils.writeValueAsString((Object)mapJson);
    }

    private List<Map<String, Object>> handleSaveAllFetchSetting(List<Map<String, Object>> oldFetchConfigList) {
        ArrayList<Map<String, Object>> newFetchConfigList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < oldFetchConfigList.size(); ++i) {
            Map<String, Object> oldFetchConfig = oldFetchConfigList.get(i);
            Map<String, Object> oneFetchSetting = this.handleSaveOneFetchSetting(oldFetchConfig, i + 1);
            newFetchConfigList.add(oneFetchSetting);
        }
        return newFetchConfigList;
    }

    private Map<String, Object> handleSaveOneFetchSetting(Map<String, Object> oldFetchConfig, int fetchConfigGroupIndex) {
        List oldDebitConfigLists = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)oldFetchConfig.get("debitConfigList")), (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
        List oldCreditConfigLists = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)oldFetchConfig.get("creditConfigList")), (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
        String businessTypeCode = "businessTypeCode";
        if (!org.springframework.util.StringUtils.isEmpty(oldFetchConfig.get(businessTypeCode))) {
            GcBaseDataVO baseDataVO = (GcBaseDataVO)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)oldFetchConfig.get("businessTypeCode")), (TypeReference)new TypeReference<GcBaseDataVO>(){});
            oldFetchConfig.put("businessTypeCode", baseDataVO.getCode());
        } else {
            oldFetchConfig.put("businessTypeCode", "");
        }
        String debit = "\u501f\u65b9";
        String credit = "\u8d37\u65b9";
        List<Map<String, Object>> newDebitConfigAllDetailedSetting = this.handleSaveAllDetailedSetting(oldDebitConfigLists, debit, fetchConfigGroupIndex);
        List<Map<String, Object>> newCreditConfigAllDetailedSetting = this.handleSaveAllDetailedSetting(oldCreditConfigLists, credit, fetchConfigGroupIndex);
        oldFetchConfig.put("debitConfigList", newDebitConfigAllDetailedSetting);
        oldFetchConfig.put("creditConfigList", newCreditConfigAllDetailedSetting);
        return oldFetchConfig;
    }

    private List<Map<String, Object>> handleSaveAllDetailedSetting(List<Map<String, Object>> oldDebitOrCreditConfigLists, String debitOrCredit, int fetchConfigGroupIndex) {
        ArrayList<Map<String, Object>> newDebitOrCreditConfigLists = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < oldDebitOrCreditConfigLists.size(); ++i) {
            Map<String, Object> oldOneDetailedSetting = oldDebitOrCreditConfigLists.get(i);
            Map<String, Object> newOneDetailedSetting = this.handleSaveOneDetailedSetting(oldOneDetailedSetting, debitOrCredit, fetchConfigGroupIndex, i + 1);
            newDebitOrCreditConfigLists.add(newOneDetailedSetting);
        }
        return newDebitOrCreditConfigLists;
    }

    private Map<String, Object> handleSaveOneDetailedSetting(Map<String, Object> detailedSetting, String debitOrCredit, int fetchConfigGroupIndex, int rowDebitOrCreditIndex) {
        String oldSubjectCodeString = JsonUtils.writeValueAsString((Object)detailedSetting.get("subjectCode"));
        GcBaseDataVO subject = (GcBaseDataVO)JsonUtils.readValue((String)oldSubjectCodeString, (TypeReference)new TypeReference<GcBaseDataVO>(){});
        String fetchType = "fetchType";
        if (detailedSetting.containsKey(fetchType) && Objects.isNull(detailedSetting.get(fetchType))) {
            throw new BusinessRuntimeException("\u53d6\u6570\u8bbe\u7f6e\u7b2c" + fetchConfigGroupIndex + "\u7ec4\uff0c" + debitOrCredit + "\u7b2c" + rowDebitOrCreditIndex + "\u884c\u201c\u53d6\u6570\u65b9\u5f0f\u201d\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        }
        if (Objects.isNull(subject) || StringUtils.isEmpty((String)subject.getCode())) {
            throw new BusinessRuntimeException("\u53d6\u6570\u8bbe\u7f6e\u7b2c" + fetchConfigGroupIndex + "\u7ec4\uff0c" + debitOrCredit + "\u7b2c" + rowDebitOrCreditIndex + "\u884c\u201c" + debitOrCredit + "\u79d1\u76ee\u201d\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        }
        detailedSetting.put("subjectCode", subject.getCode());
        return detailedSetting;
    }
}

