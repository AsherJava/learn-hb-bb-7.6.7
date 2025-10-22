/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.util.JsonUtils
 */
package com.jiuqi.gcreport.inputdata.flexible.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.util.JsonUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.util.ObjectUtils;

public class FlexibleRuleJsonStringEO2VOHelper {
    private String jsonStr;
    private String reportSystemId;
    private GcBaseDataCenterTool subjectTool;

    private FlexibleRuleJsonStringEO2VOHelper() {
    }

    public static FlexibleRuleJsonStringEO2VOHelper newInstance(String jsonStr, String reportSystemId) {
        FlexibleRuleJsonStringEO2VOHelper helper = new FlexibleRuleJsonStringEO2VOHelper();
        helper.subjectTool = GcBaseDataCenterTool.getInstance();
        helper.jsonStr = jsonStr;
        helper.reportSystemId = reportSystemId;
        return helper;
    }

    public String convertJsonStringWhenEO2VO() {
        ArrayList creditItemList = new ArrayList();
        ArrayList debitItemList = new ArrayList();
        Map mapJson = (Map)JsonUtils.readValue((String)this.jsonStr, (TypeReference)new TypeReference<Map<String, Object>>(){});
        List debitItemListCodes = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(mapJson.get("debitItemList")), (TypeReference)new TypeReference<List<String>>(){});
        List creditItemListCodes = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(mapJson.get("creditItemList")), (TypeReference)new TypeReference<List<String>>(){});
        debitItemListCodes.forEach(code -> {
            GcBaseDataVO gcBaseDataVO = this.subjectTool.convertGcBaseDataVO(this.subjectTool.queryBasedataByObjCode("MD_GCSUBJECT", GcBaseDataCenterTool.combiningObjectCode((String)code, (String[])new String[]{this.reportSystemId})));
            if (!Objects.isNull(gcBaseDataVO)) {
                debitItemList.add(gcBaseDataVO);
            }
        });
        creditItemListCodes.forEach(code -> {
            GcBaseDataVO gcBaseDataVO = this.subjectTool.convertGcBaseDataVO(this.subjectTool.queryBasedataByObjCode("MD_GCSUBJECT", GcBaseDataCenterTool.combiningObjectCode((String)code, (String[])new String[]{this.reportSystemId})));
            if (!Objects.isNull(gcBaseDataVO)) {
                creditItemList.add(gcBaseDataVO);
            }
        });
        mapJson.put("debitItemList", debitItemList);
        mapJson.put("creditItemList", creditItemList);
        String fetchConfig = JsonUtils.writeValueAsString(mapJson.get("fetchConfigList"));
        List oldFetchConfigList = (List)JsonUtils.readValue((String)fetchConfig, (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
        List<Map<String, Object>> newFetchConfigList = this.handleAllFetchSetting(oldFetchConfigList);
        mapJson.put("fetchConfigList", newFetchConfigList);
        return JsonUtils.writeValueAsString((Object)mapJson);
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
        String jsonStringBusinessTypeCode = (String)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)oldFetchConfig.get("businessTypeCode")), String.class);
        if (!StringUtils.isEmpty((String)jsonStringBusinessTypeCode)) {
            GcBaseDataVO businessType = GcBaseDataCenterTool.getInstance().convertGcBaseDataVO(GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_GCBUSINESSTYPE", jsonStringBusinessTypeCode));
            oldFetchConfig.put("businessTypeCode", businessType);
        }
        ArrayList<GcBaseDataVO> associatedSubjectListBase = new ArrayList<GcBaseDataVO>();
        String associatedSubjectString = "associatedSubject";
        if (!org.springframework.util.StringUtils.isEmpty(oldFetchConfig.get(associatedSubjectString))) {
            List associatedSubjectList = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)oldFetchConfig.get("associatedSubject")), (TypeReference)new TypeReference<List<String>>(){});
            for (int i = 0; i < associatedSubjectList.size(); ++i) {
                GcBaseDataVO associatedSubject = GcBaseDataCenterTool.getInstance().convertGcBaseDataVO(GcBaseDataCenterTool.getInstance().queryBasedataByObjCode("MD_GCSUBJECT", GcBaseDataCenterTool.combiningObjectCode((String)((String)associatedSubjectList.get(i)), (String[])new String[]{this.reportSystemId})));
                associatedSubjectListBase.add(associatedSubject);
            }
        }
        oldFetchConfig.put("associatedSubject", associatedSubjectListBase);
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
        String oldSubjectCode = (String)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)detailedSetting.get("subjectCode")), String.class);
        detailedSetting.put("subjectCode", this.subjectTool.convertGcBaseDataVO(this.subjectTool.queryBasedataByObjCode("MD_GCSUBJECT", GcBaseDataCenterTool.combiningObjectCode((String)oldSubjectCode, (String[])new String[]{this.reportSystemId}))));
        if (detailedSetting.containsKey("fetchType") && detailedSetting.containsKey("dimSrcType") && Objects.nonNull(detailedSetting.get("fetchType")) && ((String)detailedSetting.get("fetchType")).endsWith("DETAIL") && ObjectUtils.isEmpty(detailedSetting.get("dimSrcType"))) {
            detailedSetting.put("dimSrcType", "ITEM");
        }
        return detailedSetting;
    }
}

