/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.util.JsonUtils
 */
package com.jiuqi.gcreport.asset.calculate.rule.base.fixedAssets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.util.JsonUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FixedAssetsRuleJsonStringEO2VOHelper {
    private String jsonStr;
    private String reportSystemId;
    private final String fixedAssetsType = "CUSTOMIZE";

    private FixedAssetsRuleJsonStringEO2VOHelper() {
    }

    public static FixedAssetsRuleJsonStringEO2VOHelper newInstance(String jsonStr, String reportSystemId) {
        FixedAssetsRuleJsonStringEO2VOHelper helper = new FixedAssetsRuleJsonStringEO2VOHelper();
        helper.jsonStr = jsonStr;
        helper.reportSystemId = reportSystemId;
        return helper;
    }

    public String convertJsonStringWhenEO2VO() {
        Map<String, Object> fetchConfig = (Map<String, Object>)JsonUtils.readValue((String)this.jsonStr, (TypeReference)new TypeReference<Map<String, Object>>(){});
        fetchConfig.put("debitConfigList", fetchConfig.get("debitItemList"));
        fetchConfig.put("creditConfigList", fetchConfig.get("creditItemList"));
        ArrayList<Map<String, Object>> oldFetchConfigList = new ArrayList<Map<String, Object>>();
        oldFetchConfigList.add(fetchConfig);
        List<Map<String, Object>> newFetchConfigList = this.handleAllFetchSetting(oldFetchConfigList);
        fetchConfig = newFetchConfigList.get(0);
        fetchConfig.put("debitItemList", fetchConfig.get("debitConfigList"));
        fetchConfig.put("creditItemList", fetchConfig.get("creditConfigList"));
        fetchConfig.remove("debitConfigList");
        fetchConfig.remove("creditConfigList");
        return JsonUtils.writeValueAsString(fetchConfig);
    }

    private List<Map<String, Object>> handleAllFetchSetting(List<Map<String, Object>> oldFetchConfigList) {
        ArrayList<Map<String, Object>> newFetchConfigList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < oldFetchConfigList.size(); ++i) {
            Map<String, Object> oldFetchConfig = oldFetchConfigList.get(i);
            Map<String, Object> oneFetchSetting = this.handleOneFetchSetting(oldFetchConfig);
            newFetchConfigList.add(oneFetchSetting);
        }
        return newFetchConfigList;
    }

    private Map<String, Object> handleOneFetchSetting(Map<String, Object> oldFetchConfig) {
        List oldDebitConfigLists = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)oldFetchConfig.get("debitConfigList")), (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
        List oldCreditConfigLists = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)oldFetchConfig.get("creditConfigList")), (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
        List<Map<String, Object>> newDebitConfigAllDetailedSetting = this.handleAllDetailedSetting(oldDebitConfigLists);
        List<Map<String, Object>> newCreditConfigAllDetailedSetting = this.handleAllDetailedSetting(oldCreditConfigLists);
        oldFetchConfig.put("debitConfigList", newDebitConfigAllDetailedSetting);
        oldFetchConfig.put("creditConfigList", newCreditConfigAllDetailedSetting);
        return oldFetchConfig;
    }

    private List<Map<String, Object>> handleAllDetailedSetting(List<Map<String, Object>> oldDebitOrCreditConfigLists) {
        ArrayList<Map<String, Object>> newDebitOrCreditConfigLists = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < oldDebitOrCreditConfigLists.size(); ++i) {
            Map<String, Object> oldOneDetailedSetting = oldDebitOrCreditConfigLists.get(i);
            Map<String, Object> newOneDetailedSetting = this.handleOneDetailedSetting(oldOneDetailedSetting);
            newDebitOrCreditConfigLists.add(newOneDetailedSetting);
        }
        return newDebitOrCreditConfigLists;
    }

    private Map<String, Object> handleOneDetailedSetting(Map<String, Object> detailedSetting) {
        if (!"CUSTOMIZE".equals(detailedSetting.get("type"))) {
            detailedSetting.put("subjectCode", detailedSetting.get("subjectCode"));
            return detailedSetting;
        }
        String oldSubjectCode = (String)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)detailedSetting.get("subjectCode")), String.class);
        GcBaseDataCenterTool subjectTool = GcBaseDataCenterTool.getInstance();
        detailedSetting.put("subjectCode", subjectTool.convertGcBaseDataVO(subjectTool.queryBasedataByObjCode("MD_GCSUBJECT", GcBaseDataCenterTool.combiningObjectCode((String)oldSubjectCode, (String[])new String[]{this.reportSystemId}))));
        return detailedSetting;
    }
}

