/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 */
package com.jiuqi.gcreport.invest.calculate.rule.base.fvch;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PublicValueAdjustmentRuleJsonStringVO2EOHelper {
    private UnionRuleVO unionRuleVO;

    private PublicValueAdjustmentRuleJsonStringVO2EOHelper() {
    }

    public static PublicValueAdjustmentRuleJsonStringVO2EOHelper newInstance(UnionRuleVO unionRuleVO) {
        PublicValueAdjustmentRuleJsonStringVO2EOHelper helper = new PublicValueAdjustmentRuleJsonStringVO2EOHelper();
        helper.unionRuleVO = unionRuleVO;
        return helper;
    }

    public String convertJsonStringWhenVO2EO() {
        Map<String, Object> fetchConfig = (Map<String, Object>)JsonUtils.readValue((String)this.unionRuleVO.getJsonString(), (TypeReference)new TypeReference<Map<String, Object>>(){});
        fetchConfig.put("debitConfigList", fetchConfig.get("debitItemList"));
        fetchConfig.put("creditConfigList", fetchConfig.get("creditItemList"));
        ArrayList<Map<String, Object>> oldFetchConfigList = new ArrayList<Map<String, Object>>();
        oldFetchConfigList.add(fetchConfig);
        List<Map<String, Object>> newFetchConfigList = this.handleSaveAllFetchSetting(oldFetchConfigList);
        fetchConfig = newFetchConfigList.get(0);
        fetchConfig.put("debitItemList", fetchConfig.get("debitConfigList"));
        fetchConfig.put("creditItemList", fetchConfig.get("creditConfigList"));
        fetchConfig.remove("debitConfigList");
        fetchConfig.remove("creditConfigList");
        return JsonUtils.writeValueAsString(fetchConfig);
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
        if (Objects.isNull(subject)) {
            throw new BusinessRuntimeException(debitOrCredit + "\u7b2c" + rowDebitOrCreditIndex + "\u884c\u201c" + debitOrCredit + "\u79d1\u76ee\u201d\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        }
        detailedSetting.put("subjectCode", subject.getCode());
        return detailedSetting;
    }
}

