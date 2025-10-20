/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.aidocaudit.util;

import com.jiuqi.gcreport.aidocaudit.dto.RuleItemTreeDTO;
import com.jiuqi.gcreport.aidocaudit.eo.AidocauditRuleItemEO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class BuildTreeUtil {
    private BuildTreeUtil() {
        throw new UnsupportedOperationException("\u8be5\u7c7b\u4e0d\u5141\u8bb8\u5b9e\u4f8b\u5316");
    }

    public static List<RuleItemTreeDTO> buildRuleItemTree(List<AidocauditRuleItemEO> ruleitems) {
        String ordinal;
        List ruleItemTreeDTOs = ruleitems.stream().map(BuildTreeUtil::convertToRuleItemTreeDTO).collect(Collectors.toList());
        HashMap<String, List> parentMap = new HashMap<String, List>();
        for (RuleItemTreeDTO dto2 : ruleItemTreeDTOs) {
            ordinal = dto2.getOrdinal();
            String parentOrdinal = ordinal.length() > 3 ? ordinal.substring(0, ordinal.length() - 3) : null;
            if (parentOrdinal == null) continue;
            parentMap.computeIfAbsent(parentOrdinal, k -> new ArrayList()).add(dto2);
        }
        for (RuleItemTreeDTO dto2 : ruleItemTreeDTOs) {
            ordinal = dto2.getOrdinal();
            List children = (List)parentMap.get(ordinal);
            if (children == null) continue;
            dto2.setChildren(children);
        }
        return ruleItemTreeDTOs.stream().filter(dto -> dto.getOrdinal().length() == 3).collect(Collectors.toList());
    }

    private static RuleItemTreeDTO convertToRuleItemTreeDTO(AidocauditRuleItemEO ruleitem) {
        RuleItemTreeDTO dto = new RuleItemTreeDTO();
        dto.setId(ruleitem.getId());
        dto.setRuleId(ruleitem.getRuleId());
        dto.setScoreItemId(ruleitem.getScoreItemId());
        dto.setScoreItemName(ruleitem.getScoreItemName());
        dto.setFullScore(ruleitem.getFullScore());
        dto.setParagraphTitle(ruleitem.getParagraphTitle());
        dto.setParentScoreItemId(ruleitem.getParentScoreItemId());
        dto.setPrompt(ruleitem.getPrompt());
        dto.setOrdinal(ruleitem.getOrdinal());
        dto.setCreateTime(ruleitem.getCreateTime());
        dto.setCreateUser(ruleitem.getCreateUser());
        dto.setUpdateTime(ruleitem.getUpdateTime());
        dto.setUpdateUser(ruleitem.getUpdateUser());
        return dto;
    }
}

