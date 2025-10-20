/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO
 *  com.jiuqi.gcreport.unionrule.enums.OffsetTypeEnum
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.enums.ToleranceTypeEnum
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.gcreport.unionrule.upgrade;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.enums.OffsetTypeEnum;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.enums.ToleranceTypeEnum;
import com.jiuqi.gcreport.unionrule.util.UnionRuleConverter;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class FlexibleRuleOneToOneOffsetUpgrade
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        EntNativeSqlDefaultDao unionRuleDao = EntNativeSqlDefaultDao.newInstance((String)"GC_UNIONRULE", UnionRuleEO.class);
        List unionRuleList = unionRuleDao.selectEntity("SELECT * FROM GC_UNIONRULE", new Object[0]);
        if (CollectionUtils.isEmpty((Collection)unionRuleList)) {
            return;
        }
        unionRuleList.forEach(unionRule -> {
            String ruleType = unionRule.getRuleType();
            if ("root".equalsIgnoreCase(ruleType) || "group".equalsIgnoreCase(ruleType)) {
                return;
            }
            RuleTypeEnum ruleTypeEnum = RuleTypeEnum.codeOf((String)ruleType);
            if (RuleTypeEnum.FLEXIBLE.equals((Object)ruleTypeEnum)) {
                String jsonString = unionRule.getJsonString();
                if (StringUtils.isEmpty((String)jsonString)) {
                    return;
                }
                FlexibleRuleDTO flexibleRuleDTO = (FlexibleRuleDTO)JsonUtils.readValue((String)jsonString, FlexibleRuleDTO.class);
                this.copyProperties((UnionRuleEO)((Object)unionRule), (AbstractUnionRule)flexibleRuleDTO, ruleType);
                flexibleRuleDTO.setOneToOneOffsetFlag(Boolean.valueOf(true));
                this.updateRule((EntNativeSqlDefaultDao<UnionRuleEO>)unionRuleDao, flexibleRuleDTO);
            }
        });
    }

    private void updateRule(EntNativeSqlDefaultDao<UnionRuleEO> unionRuleDao, FlexibleRuleDTO flexibleRuleDTO) {
        UnionRuleEO updateUnionRuleEO = UnionRuleConverter.convertUnionRuleDTOToEO((AbstractUnionRule)flexibleRuleDTO);
        BeanUtils.copyProperties(flexibleRuleDTO, (Object)updateUnionRuleEO);
        updateUnionRuleEO.setLeafFlag(Boolean.TRUE.equals(flexibleRuleDTO.getLeafFlag()) ? 1 : 0);
        updateUnionRuleEO.setStartFlag(Boolean.TRUE.equals(flexibleRuleDTO.getStartFlag()) ? 1 : 0);
        updateUnionRuleEO.setInitTypeFlag(Boolean.TRUE.equals(flexibleRuleDTO.getInitTypeFlag()) ? 1 : 0);
        updateUnionRuleEO.setEnableToleranceFlag(Boolean.TRUE.equals(flexibleRuleDTO.getEnableToleranceFlag()) ? 1 : 0);
        if (flexibleRuleDTO.getOffsetType() != null) {
            String offsetType = flexibleRuleDTO.getOffsetType().getCode();
            updateUnionRuleEO.setOffsetType(offsetType);
        }
        if (flexibleRuleDTO.getToleranceType() != null) {
            String toleranceType = flexibleRuleDTO.getToleranceType().getCode();
            updateUnionRuleEO.setToleranceType(toleranceType);
        }
        unionRuleDao.update((BaseEntity)updateUnionRuleEO);
    }

    private void copyProperties(UnionRuleEO unionRuleEO, AbstractUnionRule rule, String ruleType) {
        BeanUtils.copyProperties((Object)unionRuleEO, rule);
        rule.setLeafFlag(Boolean.valueOf(Objects.equals(unionRuleEO.getLeafFlag(), 1)));
        rule.setStartFlag(Boolean.valueOf(Objects.equals(unionRuleEO.getStartFlag(), 1)));
        rule.setInitTypeFlag(Boolean.valueOf(Objects.equals(unionRuleEO.getInitTypeFlag(), 1)));
        rule.setEnableToleranceFlag(Boolean.valueOf(Objects.equals(unionRuleEO.getEnableToleranceFlag(), 1)));
        rule.setRuleType(ruleType);
        if (!StringUtils.isEmpty((String)unionRuleEO.getOffsetType())) {
            rule.setOffsetType(OffsetTypeEnum.codeOf((String)unionRuleEO.getOffsetType()));
        }
        if (!StringUtils.isEmpty((String)unionRuleEO.getToleranceType())) {
            rule.setToleranceType(ToleranceTypeEnum.codeOf((String)unionRuleEO.getToleranceType()));
        }
    }
}

