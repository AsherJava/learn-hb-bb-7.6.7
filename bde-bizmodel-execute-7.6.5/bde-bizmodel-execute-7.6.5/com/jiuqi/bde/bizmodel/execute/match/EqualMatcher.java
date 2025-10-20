/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.match.BaseDataMatchCondi
 *  com.jiuqi.bde.bizmodel.define.match.FilterRule
 *  com.jiuqi.bde.bizmodel.define.match.IBaseDataMatcher
 *  com.jiuqi.bde.common.constant.MatchRuleEnum
 */
package com.jiuqi.bde.bizmodel.execute.match;

import com.jiuqi.bde.bizmodel.define.match.BaseDataMatchCondi;
import com.jiuqi.bde.bizmodel.define.match.FilterRule;
import com.jiuqi.bde.bizmodel.define.match.IBaseDataMatcher;
import com.jiuqi.bde.bizmodel.execute.util.FilterRuleUtils;
import com.jiuqi.bde.common.constant.MatchRuleEnum;
import java.util.StringJoiner;
import org.springframework.stereotype.Component;

@Component
public class EqualMatcher
implements IBaseDataMatcher {
    public String getMatchCode() {
        return MatchRuleEnum.EQ.getCode();
    }

    public boolean match(BaseDataMatchCondi condi) {
        return FilterRuleUtils.matchEqual(condi.getFilterRule(), condi.getDataCode());
    }

    public String getMatchSql(BaseDataMatchCondi baseDataMatchCondi, String fieldName) {
        StringBuilder sql = new StringBuilder();
        FilterRule filterRule = baseDataMatchCondi.getFilterRule();
        if (filterRule.getParamValues() == null) {
            return " ";
        }
        switch (filterRule.getParamTypeEnum()) {
            case SINGLE: {
                sql.append(" AND ").append(fieldName).append("='").append(filterRule.getParamValues()[0]).append("' ");
                return sql.toString();
            }
            case MULTIVALUED: {
                StringJoiner stringJoiner = new StringJoiner(",");
                for (String value : filterRule.getParamValues()) {
                    stringJoiner.add("'" + value + "'");
                }
                sql.append(" AND ").append(fieldName).append(" IN(").append(stringJoiner).append(") ");
                return sql.toString();
            }
            case RANGE: {
                sql.append(" AND ").append(fieldName).append(">='").append(filterRule.getParamValues()[0]).append("' AND ").append(fieldName).append("<='").append(filterRule.getParamValues()[1]).append("ZZ").append("' ");
                return sql.toString();
            }
        }
        return " ";
    }

    public String getNoMatchSql(BaseDataMatchCondi baseDataMatchCondi, String fieldName) {
        StringBuilder sql = new StringBuilder();
        FilterRule filterRule = baseDataMatchCondi.getFilterRule();
        if (filterRule.getParamValues() == null) {
            return " ";
        }
        switch (filterRule.getParamTypeEnum()) {
            case SINGLE: {
                sql.append(" AND ").append(fieldName).append("<>'").append(filterRule.getParamValues()[0]).append("' ");
                return sql.toString();
            }
            case MULTIVALUED: {
                StringJoiner stringJoiner = new StringJoiner(",");
                for (String value : filterRule.getParamValues()) {
                    stringJoiner.add("'" + value + "'");
                }
                sql.append(" AND ").append(fieldName).append(" NOT IN(").append(stringJoiner).append(") ");
                return sql.toString();
            }
            case RANGE: {
                sql.append(" AND ").append(fieldName).append("<'").append(filterRule.getParamValues()[0]).append("' AND ").append(fieldName).append(">'").append(filterRule.getParamValues()[1]).append("' ");
                return sql.toString();
            }
        }
        return " ";
    }
}

