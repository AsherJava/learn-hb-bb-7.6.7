/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlRuleTypeEnum
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlrule.SameCtrlRuleVO
 */
package com.jiuqi.gcreport.samecontrol.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlRuleEO;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlRuleTypeEnum;
import com.jiuqi.gcreport.samecontrol.vo.samectrlrule.SameCtrlRuleVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.BeanUtils;

public class SameCtrlRuleEOFactory {
    private SameCtrlRuleVO sameCtrlRuleVO;

    private SameCtrlRuleEOFactory(SameCtrlRuleVO sameCtrlRuleVO) {
        this.sameCtrlRuleVO = sameCtrlRuleVO;
    }

    public static SameCtrlRuleEO newInstanceByVO(SameCtrlRuleVO sameCtrlRuleVO) {
        SameCtrlRuleEOFactory sameCtrlRuleEOFactory = new SameCtrlRuleEOFactory(sameCtrlRuleVO);
        return sameCtrlRuleEOFactory.convert();
    }

    private SameCtrlRuleEO convert() {
        SameCtrlRuleEO ruleEO = new SameCtrlRuleEO();
        if (!"root".equalsIgnoreCase(this.sameCtrlRuleVO.getDataType()) && !"group".equalsIgnoreCase(this.sameCtrlRuleVO.getDataType())) {
            this.sameCtrlRuleVO.setJsonString(this.jsonStringBaseDataVoToCode());
        }
        BeanUtils.copyProperties(this.sameCtrlRuleVO, (Object)ruleEO);
        ruleEO.setLeafFlag(Boolean.TRUE.equals(this.sameCtrlRuleVO.getLeafFlag()) ? 1 : 0);
        ruleEO.setStartFlag(Boolean.TRUE.equals(this.sameCtrlRuleVO.getLeafFlag()) ? 1 : 0);
        if (!Objects.isNull(this.sameCtrlRuleVO.getRuleType())) {
            ruleEO.setRuleType(this.sameCtrlRuleVO.getRuleType().getCode());
            ruleEO.setDataType(this.sameCtrlRuleVO.getRuleType().getCode());
        } else {
            ruleEO.setRuleType("");
        }
        ruleEO.setBusinessTypeCode(this.sameCtrlRuleVO.getBusinessTypeCode().getCode());
        return ruleEO;
    }

    private String jsonStringBaseDataVoToCode() {
        return this.handleSaveJsonStringBaseDataVoToCode();
    }

    private String handleSaveJsonStringBaseDataVoToCode() {
        Map fetchConfig = (Map)JsonUtils.readValue((String)this.sameCtrlRuleVO.getJsonString(), (TypeReference)new TypeReference<Map<String, Object>>(){});
        return JsonUtils.writeValueAsString(this.handleSaveOneFetchSetting(fetchConfig));
    }

    private Map<String, Object> handleSaveOneFetchSetting(Map<String, Object> oldFetchConfig) {
        List oldDebitConfigLists = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)oldFetchConfig.get("debitItemList")), (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
        List oldCreditConfigLists = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)oldFetchConfig.get("creditItemList")), (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
        String debit = "\u501f\u65b9";
        String credit = "\u8d37\u65b9";
        List<Map<String, Object>> newDebitConfigAllDetailedSetting = this.handleSaveAllDetailedSetting(oldDebitConfigLists, debit);
        List<Map<String, Object>> newCreditConfigAllDetailedSetting = this.handleSaveAllDetailedSetting(oldCreditConfigLists, credit);
        oldFetchConfig.put("debitItemList", newDebitConfigAllDetailedSetting);
        oldFetchConfig.put("creditItemList", newCreditConfigAllDetailedSetting);
        return oldFetchConfig;
    }

    private List<Map<String, Object>> handleSaveAllDetailedSetting(List<Map<String, Object>> oldDebitOrCreditConfigLists, String debitOrCredit) {
        ArrayList<Map<String, Object>> newDebitOrCreditConfigLists = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < oldDebitOrCreditConfigLists.size(); ++i) {
            Map<String, Object> oldOneDetailedSetting = oldDebitOrCreditConfigLists.get(i);
            Map<String, Object> newOneDetailedSetting = this.handleSaveOneDetailedSetting(oldOneDetailedSetting, debitOrCredit, i + 1);
            newDebitOrCreditConfigLists.add(newOneDetailedSetting);
        }
        return newDebitOrCreditConfigLists;
    }

    private Map<String, Object> handleSaveOneDetailedSetting(Map<String, Object> detailedSetting, String debitOrCredit, int rowDebitOrCreditIndex) {
        String oldSubjectCodeString = JsonUtils.writeValueAsString((Object)detailedSetting.get("subjectCode"));
        GcBaseDataVO subject = (GcBaseDataVO)JsonUtils.readValue((String)oldSubjectCodeString, (TypeReference)new TypeReference<GcBaseDataVO>(){});
        if ((SameCtrlRuleTypeEnum.DIRECT_INVESTMENT.equals((Object)this.sameCtrlRuleVO.getRuleType()) || SameCtrlRuleTypeEnum.DISPOSER_INVESTMENT.equals((Object)this.sameCtrlRuleVO.getRuleType())) && Objects.isNull(subject)) {
            throw new BusinessRuntimeException(debitOrCredit + "\u7b2c" + rowDebitOrCreditIndex + "\u884c\u201c" + debitOrCredit + "\u79d1\u76ee\u201d\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        }
        detailedSetting.put("subjectCode", subject.getCode());
        return detailedSetting;
    }
}

