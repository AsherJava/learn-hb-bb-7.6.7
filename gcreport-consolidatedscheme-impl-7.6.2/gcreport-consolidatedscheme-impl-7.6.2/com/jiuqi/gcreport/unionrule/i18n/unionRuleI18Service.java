/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.unionrule.constant.RuleConst
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 */
package com.jiuqi.gcreport.unionrule.i18n;

import com.jiuqi.gcreport.unionrule.base.UnionRuleManager;
import com.jiuqi.gcreport.unionrule.constant.RuleConst;
import com.jiuqi.gcreport.unionrule.dao.UnionRuleDao;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.util.UnionRuleUtils;
import com.jiuqi.gcreport.unionrule.util.UnionRuleVOFactory;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class unionRuleI18Service {
    @Autowired
    private UnionRuleDao unionRuleDao;

    public List<UnionRuleVO> loadRuleListBySystem(String reportSystemId) {
        List<UnionRuleEO> rootRule = this.unionRuleDao.findByParentIdAndReportSystem(RuleConst.ROOT_PARENT_ID, reportSystemId);
        if (CollectionUtils.isEmpty(rootRule)) {
            return Collections.emptyList();
        }
        ArrayList<UnionRuleVO> allRule = new ArrayList<UnionRuleVO>();
        UnionRuleEO rootRuleEO = rootRule.get(0);
        UnionRuleVO rootRuleVO = UnionRuleVOFactory.newNoSettingInstanceByEntity(rootRuleEO);
        allRule.add(rootRuleVO);
        this.findChildRules(rootRuleEO, allRule);
        return allRule;
    }

    private void findChildRules(UnionRuleEO parent, List<UnionRuleVO> allRule) {
        if (parent.getLeafFlag() == 1) {
            UnionRuleManager managerByRuleTypeCode = UnionRuleUtils.getManagerByRuleTypeCode(parent.getRuleType());
            if (Objects.isNull(managerByRuleTypeCode)) {
                return;
            }
            List<UnionRuleVO> unionRuleChildNodes = managerByRuleTypeCode.getRuleHandler().getUnionRuleChildNodes(parent);
            if (!CollectionUtils.isEmpty(unionRuleChildNodes)) {
                allRule.addAll(unionRuleChildNodes);
            }
            return;
        }
        List<UnionRuleEO> children = this.unionRuleDao.findByParentId(parent.getId());
        if (CollectionUtils.isEmpty(children)) {
            return;
        }
        List<UnionRuleEO> childRuleSByFilter = children.stream().filter(rule -> Objects.equals(1, rule.getStartFlag())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(childRuleSByFilter)) {
            return;
        }
        childRuleSByFilter.forEach(child -> {
            allRule.add(UnionRuleVOFactory.newNoSettingInstanceByEntity(child));
            this.findChildRules((UnionRuleEO)((Object)child), allRule);
        });
    }
}

