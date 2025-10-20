/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.aidocaudit.dao.impl;

import com.jiuqi.gcreport.aidocaudit.dao.IAidocauditResultDao;
import com.jiuqi.gcreport.aidocaudit.dto.AuditParamDTO;
import com.jiuqi.gcreport.aidocaudit.eo.AidocauditResultEO;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AidocauditResultDaoImpl
extends GcDbSqlGenericDAO<AidocauditResultEO, String>
implements IAidocauditResultDao {
    private static final String SELECT_TEMPLATE = "SELECT %s FROM ";
    private static final String MDCODE_FIELD = "t.MDCODE";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public AidocauditResultDaoImpl() {
        super(AidocauditResultEO.class);
    }

    @Override
    public List<AidocauditResultEO> queryByTempIdAndBusiness(String ruleId, String orgId, String period, String taskId) {
        String sqlTemplate = "SELECT %s FROM GC_AIDOCAUDIT_RESULT t WHERE t.RULEID = ? AND t.MDCODE = ? AND t.DATATIME = ? AND t.TASKID = ?";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_AIDOCAUDIT_RESULT", (String)"t");
        String finalSql = String.format("SELECT %s FROM GC_AIDOCAUDIT_RESULT t WHERE t.RULEID = ? AND t.MDCODE = ? AND t.DATATIME = ? AND t.TASKID = ?", columns);
        return this.jdbcTemplate.query(finalSql, (rs, rowNum) -> this.resultSetMapping(rs), new Object[]{ruleId, orgId, period, taskId});
    }

    @Override
    public List<AidocauditResultEO> queryByOrgIds(String taskId, String ruleId, String period, List<String> orgIds) {
        String sqlTemplate = "SELECT %s FROM GC_AIDOCAUDIT_RESULT t WHERE t.TASKID = ? AND t.RULEID = ? AND t.DATATIME = ? AND " + SqlUtils.getConditionOfIdsUseOr(orgIds, (String)MDCODE_FIELD);
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_AIDOCAUDIT_RESULT", (String)"t");
        String finalSql = String.format(sqlTemplate, columns);
        return this.jdbcTemplate.query(finalSql, (rs, rowNum) -> this.resultSetMapping(rs), new Object[]{taskId, ruleId, period});
    }

    @Override
    public List<AidocauditResultEO> queryByOrgIdsLimit(String taskId, String ruleId, String period, List<String> orgIds, int i) {
        String sqlTemplate = "SELECT %s FROM GC_AIDOCAUDIT_RESULT t WHERE t.TASKID = ? AND t.RULEID = ? AND t.DATATIME = ? AND " + SqlUtils.getConditionOfIdsUseOr(orgIds, (String)MDCODE_FIELD) + "ORDER BY t.SCORE ASC ";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_AIDOCAUDIT_RESULT", (String)"t");
        String finalSql = String.format(sqlTemplate, columns);
        return this.selectEntityByPaging(finalSql, 0, i, new Object[]{taskId, ruleId, period});
    }

    @Override
    public List<AidocauditResultEO> getUnitDetailByPage(AuditParamDTO param) {
        String sqlTemplate = "SELECT %s FROM GC_AIDOCAUDIT_RESULT t WHERE t.TASKID = ? AND t.RULEID = ? AND t.DATATIME = ? AND " + SqlUtils.getConditionOfIdsUseOr(param.getOrgIds(), (String)MDCODE_FIELD);
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_AIDOCAUDIT_RESULT", (String)"t");
        String finalSql = String.format(sqlTemplate, columns);
        Integer pageNum = param.getCurrentPage();
        Integer pageSize = param.getPageSize();
        return this.selectEntityByPaging(finalSql, (pageNum - 1) * pageSize, pageNum * pageSize, new Object[]{param.getTaskId(), param.getRuleId(), param.getDataTime()});
    }

    @Override
    public List<AidocauditResultEO> queryByRuleIdsAndOrgIds(String taskId, List<String> ruleIds, String period, List<String> orgIds) {
        String sqlTemplate = "SELECT %s FROM GC_AIDOCAUDIT_RESULT t WHERE T.TASKID = ? AND " + SqlUtils.getConditionOfIdsUseOr(ruleIds, (String)"t.RULEID") + " AND t.DATATIME = ? AND " + SqlUtils.getConditionOfIdsUseOr(orgIds, (String)MDCODE_FIELD);
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_AIDOCAUDIT_RESULT", (String)"t");
        String finalSql = String.format(sqlTemplate, columns);
        return this.selectEntity(finalSql, new Object[]{taskId, period});
    }

    private AidocauditResultEO resultSetMapping(ResultSet rs) throws SQLException {
        AidocauditResultEO result = new AidocauditResultEO();
        result.setId(rs.getString("ID"));
        result.setMdCode(rs.getString("MDCODE"));
        result.setDataTime(rs.getString("DATATIME"));
        result.setTaskId(rs.getString("TASKID"));
        result.setZbCode(rs.getString("ZBCODE"));
        result.setAttachmentId(rs.getString("attachmentID"));
        result.setRuleId(rs.getString("RULEID"));
        result.setScore(rs.getBigDecimal("SCORE"));
        result.setRuleNum(rs.getInt("RULENUM"));
        result.setRuleMatchNum(rs.getInt("RULEMATCHNUM"));
        result.setRuleUnmatchNum(rs.getInt("RULEUNMATCHNUM"));
        result.setRuleSuspectMatchNum(rs.getInt("RULESUSPECTMATCHNUM"));
        result.setCreateTime(rs.getTimestamp("CREATETIME"));
        result.setCreateUser(rs.getString("CREATEUSER"));
        return result;
    }
}

