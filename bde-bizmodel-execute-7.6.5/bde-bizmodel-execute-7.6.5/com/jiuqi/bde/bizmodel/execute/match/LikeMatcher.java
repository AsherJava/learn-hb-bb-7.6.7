/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.match.BaseDataMatchCondi
 *  com.jiuqi.bde.bizmodel.define.match.FilterRule
 *  com.jiuqi.bde.bizmodel.define.match.IBaseDataMatcher
 *  com.jiuqi.bde.common.constant.MatchRuleEnum
 *  com.jiuqi.dc.base.common.utils.SqlUtil
 */
package com.jiuqi.bde.bizmodel.execute.match;

import com.jiuqi.bde.bizmodel.define.match.BaseDataMatchCondi;
import com.jiuqi.bde.bizmodel.define.match.FilterRule;
import com.jiuqi.bde.bizmodel.define.match.IBaseDataMatcher;
import com.jiuqi.bde.bizmodel.execute.util.FilterRuleUtils;
import com.jiuqi.bde.common.constant.MatchRuleEnum;
import com.jiuqi.dc.base.common.utils.SqlUtil;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component
public class LikeMatcher
implements IBaseDataMatcher {
    public String getMatchCode() {
        return MatchRuleEnum.LIKE.getCode();
    }

    public boolean match(BaseDataMatchCondi condi) {
        return FilterRuleUtils.matchVague(condi.getFilterRule(), condi.getDataCode());
    }

    public String getMatchSql(BaseDataMatchCondi baseDataMatchCondi, String fieldName) {
        StringBuilder sql = new StringBuilder();
        FilterRule filterRule = baseDataMatchCondi.getFilterRule();
        if (filterRule.getParamValues() == null) {
            return " ";
        }
        switch (filterRule.getParamTypeEnum()) {
            case SINGLE: {
                sql.append(" AND ").append(fieldName).append(" LIKE '").append(filterRule.getParamValues()[0]).append("%%%%' ");
                return sql.toString();
            }
            case MULTIVALUED: {
                ArrayList<String> likeSqlList = new ArrayList<String>();
                for (String value : filterRule.getParamValues()) {
                    likeSqlList.add(String.format(" %1$s LIKE '" + value + "%%%%' ", fieldName));
                }
                return SqlUtil.concatCondi(likeSqlList, (boolean)false);
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
                sql.append(" AND ").append(fieldName).append(" NOT LIKE '").append(filterRule.getParamValues()[0]).append("%%%%' ");
                return sql.toString();
            }
            case MULTIVALUED: {
                ArrayList<String> likeSqlList = new ArrayList<String>();
                for (String value : filterRule.getParamValues()) {
                    likeSqlList.add(" FLOATRESULT.%1$s NOT LIKE '" + value + "%%%%' ");
                }
                return SqlUtil.concatCondi(likeSqlList, (boolean)false);
            }
            case RANGE: {
                sql.append(" AND ").append(fieldName).append("<'").append(filterRule.getParamValues()[0]).append("' AND ").append(fieldName).append(">'").append(filterRule.getParamValues()[1]).append("' ");
                return sql.toString();
            }
        }
        return " ";
    }
}

