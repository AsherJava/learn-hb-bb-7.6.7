/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  org.springframework.dao.EmptyResultDataAccessException
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.aidocaudit.dao.impl;

import com.jiuqi.gcreport.aidocaudit.dao.IAidocauditRuleDao;
import com.jiuqi.gcreport.aidocaudit.eo.AidocauditRuleEO;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Repository
public class AidocauditRuleDaoImpl
extends GcDbSqlGenericDAO<AidocauditRuleEO, String>
implements IAidocauditRuleDao {
    private static final String SELECT_TEMPLATE = "SELECT %1$s FROM ";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public AidocauditRuleDaoImpl() {
        super(AidocauditRuleEO.class);
    }

    @Override
    public List<AidocauditRuleEO> queryListByIds(List<String> ruleIds) {
        if (CollectionUtils.isEmpty(ruleIds)) {
            return new ArrayList<AidocauditRuleEO>();
        }
        String sql = "SELECT %1$s FROM GC_AIDOCAUDIT_RULE t where " + SqlUtils.getConditionOfIdsUseOr(ruleIds, (String)"t.ID");
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_AIDOCAUDIT_RULE", (String)"t");
        String formatSQL = String.format(sql, columns);
        return this.selectEntity(formatSQL, new Object[0]);
    }

    @Override
    public AidocauditRuleEO getByScoreTmplId(String tempId) {
        if (!StringUtils.hasText(tempId)) {
            return new AidocauditRuleEO();
        }
        String sql = "SELECT %1$s FROM GC_AIDOCAUDIT_RULE t where t.SCORETMPLID = ?";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_AIDOCAUDIT_RULE", (String)"t");
        String formatSQL = String.format("SELECT %1$s FROM GC_AIDOCAUDIT_RULE t where t.SCORETMPLID = ?", columns);
        try {
            return (AidocauditRuleEO)((Object)this.jdbcTemplate.queryForObject(formatSQL, (rs, rowNum) -> {
                AidocauditRuleEO rule = new AidocauditRuleEO();
                rule.setId(rs.getString("ID"));
                rule.setRuleName(rs.getString("RULENAME"));
                rule.setRuleAttachmentId(rs.getString("RULEATTACHMENTID"));
                rule.setRuleAttachmentName(rs.getString("RULEATTACHMENTNAME"));
                rule.setAchmentZbCode(rs.getString("ACHMENTZBCODE"));
                rule.setScoreTmplId(rs.getString("SCORETMPLID"));
                rule.setRuleCount(rs.getInt("RULECOUNT"));
                rule.setTotalScore(rs.getBigDecimal("TOTALSCORE"));
                rule.setCreateTime(rs.getTimestamp("CREATETIME"));
                rule.setCreateUser(rs.getString("CREATEUSER"));
                rule.setUpdateTime(rs.getTimestamp("UPDATETIME"));
                rule.setUpdateUser(rs.getString("UPDATEUSER"));
                return rule;
            }, new Object[]{tempId}));
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<AidocauditRuleEO> list(Integer ruleType) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT %1$s FROM GC_AIDOCAUDIT_RULE t ");
        if (ruleType != null) {
            sqlBuilder.append(" WHERE t.RULESTATUS = 2 AND t.RULETYPE = ").append(ruleType);
        }
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_AIDOCAUDIT_RULE", (String)"t");
        String formatSQL = String.format(sqlBuilder.toString(), columns);
        sqlBuilder.append(" ORDER BY t.CREATEUSER DESC ");
        return this.selectEntity(formatSQL, new Object[0]);
    }

    @Override
    public List<AidocauditRuleEO> queryByRuleAttachIdAndStatus(String ruleAttachId) {
        String sql = "SELECT %1$s FROM GC_AIDOCAUDIT_RULE t where t.RULEATTACHMENTID = ?";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_AIDOCAUDIT_RULE", (String)"t");
        String formatSQL = String.format("SELECT %1$s FROM GC_AIDOCAUDIT_RULE t where t.RULEATTACHMENTID = ?", columns);
        return this.selectEntity(formatSQL, new Object[]{ruleAttachId});
    }
}

