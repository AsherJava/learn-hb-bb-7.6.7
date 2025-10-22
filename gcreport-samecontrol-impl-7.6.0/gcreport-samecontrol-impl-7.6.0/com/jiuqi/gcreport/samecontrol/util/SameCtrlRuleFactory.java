/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.samecontrol.dto.samectrlrule.AbstractCommonRule
 *  com.jiuqi.gcreport.samecontrol.dto.samectrlrule.DirectInvestmentDTO
 *  com.jiuqi.gcreport.samecontrol.dto.samectrlrule.DisposerInvestmentDTO
 *  com.jiuqi.gcreport.samecontrol.dto.samectrlrule.SameCtrlGroupRuleDTO
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlRuleTypeEnum
 */
package com.jiuqi.gcreport.samecontrol.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.samecontrol.dto.samectrlrule.AbstractCommonRule;
import com.jiuqi.gcreport.samecontrol.dto.samectrlrule.DirectInvestmentDTO;
import com.jiuqi.gcreport.samecontrol.dto.samectrlrule.DisposerInvestmentDTO;
import com.jiuqi.gcreport.samecontrol.dto.samectrlrule.SameCtrlGroupRuleDTO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlRuleEO;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlRuleTypeEnum;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.BeanUtils;

public class SameCtrlRuleFactory {
    public static AbstractCommonRule convertDTO(SameCtrlRuleEO sameCtrlRuleEO) {
        AbstractCommonRule rule;
        String jsonString = sameCtrlRuleEO.getJsonString();
        if (!Objects.equals(1, sameCtrlRuleEO.getLeafFlag())) {
            SameCtrlGroupRuleDTO rule2 = new SameCtrlGroupRuleDTO();
            BeanUtils.copyProperties((Object)sameCtrlRuleEO, rule2);
            rule2.setLeafFlag(Boolean.valueOf(Objects.equals(sameCtrlRuleEO.getLeafFlag(), 1)));
            rule2.setStartFlag(Boolean.valueOf(Objects.equals(sameCtrlRuleEO.getStartFlag(), 1)));
            rule2.setRuleType(null);
            return rule2;
        }
        SameCtrlRuleTypeEnum ruleType = SameCtrlRuleTypeEnum.codeOf((String)sameCtrlRuleEO.getRuleType());
        if (SameCtrlRuleTypeEnum.DIRECT_INVESTMENT.equals((Object)ruleType)) {
            rule = (AbstractCommonRule)JsonUtils.readValue((String)jsonString, DirectInvestmentDTO.class);
        } else if (SameCtrlRuleTypeEnum.DISPOSER_INVESTMENT.equals((Object)ruleType)) {
            rule = (AbstractCommonRule)JsonUtils.readValue((String)jsonString, DisposerInvestmentDTO.class);
        } else {
            throw new BusinessRuntimeException("\u65e0\u6548\u7684\u89c4\u5219\u7c7b\u578b");
        }
        assert (rule != null);
        BeanUtils.copyProperties((Object)sameCtrlRuleEO, rule);
        rule.setLeafFlag(Boolean.valueOf(Objects.equals(sameCtrlRuleEO.getLeafFlag(), 1)));
        rule.setStartFlag(Boolean.valueOf(Objects.equals(sameCtrlRuleEO.getStartFlag(), 1)));
        rule.setRuleType(ruleType);
        return rule;
    }

    public static SameCtrlRuleEO convertEO(AbstractCommonRule abstractCommonRule) {
        SameCtrlRuleEO ruleEO = new SameCtrlRuleEO();
        if (!Boolean.TRUE.equals(abstractCommonRule.getLeafFlag())) {
            BeanUtils.copyProperties(abstractCommonRule, (Object)ruleEO);
            ruleEO.setLeafFlag(Boolean.TRUE.equals(abstractCommonRule.getLeafFlag()) ? 1 : 0);
            ruleEO.setStartFlag(Boolean.TRUE.equals(abstractCommonRule.getStartFlag()) ? 1 : 0);
            ruleEO.setRuleType("");
            return ruleEO;
        }
        HashMap<String, List> map = new HashMap<String, List>(16);
        String ruleType = abstractCommonRule.getRuleType().getCode();
        if (SameCtrlRuleTypeEnum.DIRECT_INVESTMENT.getCode().equals(ruleType)) {
            DirectInvestmentDTO directInvestmentDTO = new DirectInvestmentDTO();
            BeanUtils.copyProperties(abstractCommonRule, directInvestmentDTO);
            map.put("debitItemList", directInvestmentDTO.getDebitItemList());
            map.put("creditItemList", directInvestmentDTO.getCreditItemList());
            ruleEO.setJsonString(JsonUtils.writeValueAsString(map));
            ruleEO.setRuleType(ruleType);
        }
        if (SameCtrlRuleTypeEnum.DISPOSER_INVESTMENT.getCode().equals(ruleType)) {
            DisposerInvestmentDTO disposerInvestmentDTO = new DisposerInvestmentDTO();
            BeanUtils.copyProperties(abstractCommonRule, disposerInvestmentDTO);
            map.put("debitItemList", disposerInvestmentDTO.getDebitItemList());
            map.put("creditItemList", disposerInvestmentDTO.getCreditItemList());
            ruleEO.setJsonString(JsonUtils.writeValueAsString(map));
            ruleEO.setRuleType(ruleType);
        }
        return ruleEO;
    }
}

