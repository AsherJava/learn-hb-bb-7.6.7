/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.google.common.collect.Lists
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlRuleTypeEnum
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlrule.SameCtrlRuleVO
 */
package com.jiuqi.gcreport.samecontrol.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlRuleEO;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlRuleTypeEnum;
import com.jiuqi.gcreport.samecontrol.vo.samectrlrule.SameCtrlRuleVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.BeanUtils;

public class SameCtrlRuleVOFactory {
    private ConsolidatedSubjectService subjectService;
    private SameCtrlRuleEO unionRuleEntity;
    private boolean noSetting;

    private SameCtrlRuleVOFactory(SameCtrlRuleEO unionRuleEntity) {
        this.unionRuleEntity = unionRuleEntity;
    }

    public static SameCtrlRuleVO newInstanceByEntity(SameCtrlRuleEO unionRuleEntity) {
        SameCtrlRuleVOFactory sameCtrlRuleVOFactory = new SameCtrlRuleVOFactory(unionRuleEntity);
        return sameCtrlRuleVOFactory.convert();
    }

    public static SameCtrlRuleVO newNoSettingInstanceByEntity(SameCtrlRuleEO unionRuleEntity) {
        SameCtrlRuleVOFactory sameCtrlRuleVOFactory = new SameCtrlRuleVOFactory(unionRuleEntity);
        sameCtrlRuleVOFactory.noSetting = true;
        return sameCtrlRuleVOFactory.convert();
    }

    private SameCtrlRuleVO convert() {
        if (Objects.isNull((Object)this.unionRuleEntity)) {
            return null;
        }
        SameCtrlRuleVO ruleVO = new SameCtrlRuleVO();
        BeanUtils.copyProperties((Object)this.unionRuleEntity, ruleVO);
        ruleVO.setLeafFlag(Boolean.valueOf(Objects.equals(this.unionRuleEntity.getLeafFlag(), 1)));
        ruleVO.setStartFlag(Boolean.valueOf(Objects.equals(this.unionRuleEntity.getStartFlag(), 1)));
        if (!Objects.equals(1, this.unionRuleEntity.getLeafFlag())) {
            if ("root".equalsIgnoreCase(this.unionRuleEntity.getDataType())) {
                ruleVO.setDataType("root");
                ruleVO.setDataTypeDescription("\u6839\u8282\u70b9");
            } else if ("group".equalsIgnoreCase(this.unionRuleEntity.getDataType())) {
                ruleVO.setDataType("group");
                ruleVO.setDataTypeDescription("\u5206\u7ec4");
            }
        } else {
            if (!this.noSetting) {
                ruleVO.setDataTypeDescription(Objects.requireNonNull(SameCtrlRuleTypeEnum.codeOf((String)this.unionRuleEntity.getRuleType())).getName());
                ruleVO.setJsonString(this.jsonStringCodeToBaseDataVo());
                ruleVO.setBusinessTypeCode(GcBaseDataCenterTool.getInstance().convertGcBaseDataVO(GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_GCBUSINESSTYPE", this.unionRuleEntity.getBusinessTypeCode())));
            }
            ruleVO.setRuleType(SameCtrlRuleTypeEnum.codeOf((String)this.unionRuleEntity.getRuleType()));
        }
        ruleVO.setChildren((List)Lists.newArrayList());
        return ruleVO;
    }

    private String jsonStringCodeToBaseDataVo() {
        this.subjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class);
        return this.handleJsonStringCodeToBaseDataVo();
    }

    private String handleJsonStringCodeToBaseDataVo() {
        Map fetchConfig = (Map)JsonUtils.readValue((String)this.unionRuleEntity.getJsonString(), (TypeReference)new TypeReference<Map<String, Object>>(){});
        return JsonUtils.writeValueAsString(this.handleOneFetchSetting(fetchConfig));
    }

    private Map<String, Object> handleOneFetchSetting(Map<String, Object> oldFetchConfig) {
        List oldDebitConfigLists = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)oldFetchConfig.get("debitItemList")), (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
        List oldCreditConfigLists = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)oldFetchConfig.get("creditItemList")), (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
        List<Map<String, Object>> newDebitConfigAllDetailedSetting = this.handleAllDetailedSetting(oldDebitConfigLists);
        List<Map<String, Object>> newCreditConfigAllDetailedSetting = this.handleAllDetailedSetting(oldCreditConfigLists);
        oldFetchConfig.put("debitItemList", newDebitConfigAllDetailedSetting);
        oldFetchConfig.put("creditItemList", newCreditConfigAllDetailedSetting);
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
        detailedSetting.put("subjectCode", this.subjectService.getSubjectByCode(this.unionRuleEntity.getReportSystem(), oldSubjectCode));
        return detailedSetting;
    }
}

