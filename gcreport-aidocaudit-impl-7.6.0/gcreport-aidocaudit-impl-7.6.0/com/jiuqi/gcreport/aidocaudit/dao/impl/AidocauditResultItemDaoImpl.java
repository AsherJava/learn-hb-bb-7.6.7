/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.aidocaudit.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.aidocaudit.dao.IAidocauditResultItemDao;
import com.jiuqi.gcreport.aidocaudit.dto.ResultDetailDTO;
import com.jiuqi.gcreport.aidocaudit.dto.ResultItemAndRuleNameDTO;
import com.jiuqi.gcreport.aidocaudit.dto.ResultitemOrderDTO;
import com.jiuqi.gcreport.aidocaudit.eo.AidocauditResultitemEO;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AidocauditResultItemDaoImpl
extends GcDbSqlGenericDAO<AidocauditResultitemEO, String>
implements IAidocauditResultItemDao {
    private static final String FULLSCORE = "FULLSCORE";
    private static final String SCOREBASIS = "SCOREBASIS";
    private static final String RESULTID = "RESULTID";
    private static final String SCORE = "SCORE";
    private static final String RULEITEMID = "RULEITEMID";
    private static final String SCOREITEMNAME = "SCOREITEMNAME";
    private static final String RULEITEM_JOIN_SQL = "LEFT JOIN GC_AIDOCAUDIT_RULEITEM m ON t.RULEITEMID = m.ID ";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public AidocauditResultItemDaoImpl() {
        super(AidocauditResultitemEO.class);
    }

    @Override
    public List<AidocauditResultitemEO> queryByResultIds(List<String> resultIds) {
        String sql = "SELECT %1$s FROM GC_AIDOCAUDIT_RESULTITEM t WHERE " + SqlUtils.getConditionOfIdsUseOr(resultIds, (String)"t.RESULTID");
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_AIDOCAUDIT_RESULTITEM", (String)"t");
        String formatSQL = String.format(sql, columns);
        return this.selectEntity(formatSQL, new Object[0]);
    }

    @Override
    public List<ResultItemAndRuleNameDTO> queryOrgQuestionStatus(List<String> resultIds) {
        String sql = "SELECT %1$s, m.SCOREITEMNAME as SCOREITEMNAME FROM GC_AIDOCAUDIT_RESULTITEM t LEFT JOIN GC_AIDOCAUDIT_RULEITEM m ON t.RULEITEMID = m.ID WHERE m.PARENTSCOREITEMID IS NULL AND t.SCORE < t.FULLSCORE AND " + SqlUtils.getConditionOfIdsUseOr(resultIds, (String)"t.RESULTID");
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_AIDOCAUDIT_RESULTITEM", (String)"t");
        String formatSQL = String.format(sql, columns);
        return this.jdbcTemplate.query(formatSQL, (rs, rowNum) -> this.resultSetMapping(rs));
    }

    @Override
    public List<ResultitemOrderDTO> queryDataByOrder(String resultId) {
        String sql = "SELECT %1$s,m.ORDINAL,m.SCOREITEMID,m.PARENTSCOREITEMID,m.PARAGRAPHTITLE,m.SCOREITEMNAME FROM GC_AIDOCAUDIT_RESULTITEM t LEFT JOIN GC_AIDOCAUDIT_RULEITEM m ON t.RULEITEMID = m.ID WHERE t.RESULTID = ? ORDER BY m.ORDINAL ASC";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_AIDOCAUDIT_RESULTITEM", (String)"t");
        String formatSQL = String.format("SELECT %1$s,m.ORDINAL,m.SCOREITEMID,m.PARENTSCOREITEMID,m.PARAGRAPHTITLE,m.SCOREITEMNAME FROM GC_AIDOCAUDIT_RESULTITEM t LEFT JOIN GC_AIDOCAUDIT_RULEITEM m ON t.RULEITEMID = m.ID WHERE t.RESULTID = ? ORDER BY m.ORDINAL ASC", columns);
        return this.jdbcTemplate.query(formatSQL, (rs, rowNum) -> this.resultDTOSetMapping(rs), new Object[]{resultId});
    }

    @Override
    public List<ResultDetailDTO> queryRuleAuditResultDetail(List<String> resultIds, String ruleItemId, Boolean isParent) {
        if (CollectionUtils.isEmpty(resultIds)) {
            return Collections.emptyList();
        }
        String sql = "SELECT t.ID AS RESULTITEMID, r.MDCODE AS MDCODE, t.SCORE AS SCORE, t.FULLSCORE AS FULLSCORE,t.SCOREBASIS AS SCOREBASIS, m.ID AS RULEITEMID, m.SCOREITEMNAME AS SCOREITEMNAME ,r.ATTACHMENTID as ATTACHMENTID,r.ID as RESULTID FROM GC_AIDOCAUDIT_RESULTITEM t LEFT JOIN GC_AIDOCAUDIT_RESULT r ON t.RESULTID = r.ID LEFT JOIN GC_AIDOCAUDIT_RULEITEM m ON t.RULEITEMID = m.ID WHERE " + SqlUtils.getConditionOfIdsUseOr(resultIds, (String)"t.RESULTID ");
        StringBuilder sqlBuffer = new StringBuilder(sql);
        if (isParent != null && isParent.booleanValue()) {
            sqlBuffer.append("AND m.PARENTSCOREITEMID = ? ");
        } else {
            sqlBuffer.append("AND m.ID = ? ");
        }
        sqlBuffer.append("ORDER BY r.MDCODE,m.ORDINAL ASC");
        String formatSQL = sqlBuffer.toString();
        return this.jdbcTemplate.query(formatSQL, this::resultDetailDTOMapping, new Object[]{ruleItemId});
    }

    private ResultDetailDTO resultDetailDTOMapping(ResultSet rs, int rowNum) throws SQLException {
        ResultDetailDTO dto = new ResultDetailDTO();
        dto.setResultItemId(rs.getString("RESULTITEMID"));
        dto.setMdCode(rs.getString("MDCODE"));
        dto.setScore(rs.getBigDecimal(SCORE));
        dto.setFullScore(rs.getBigDecimal(FULLSCORE));
        dto.setScoreBasis(rs.getString(SCOREBASIS));
        dto.setRuleItemId(rs.getString(RULEITEMID));
        dto.setRuleItemName(rs.getString(SCOREITEMNAME));
        dto.setAttachmentId(rs.getString("ATTACHMENTID"));
        dto.setResultId(rs.getString(RESULTID));
        return dto;
    }

    private ResultItemAndRuleNameDTO resultSetMapping(ResultSet rs) throws SQLException {
        ResultItemAndRuleNameDTO item = new ResultItemAndRuleNameDTO();
        item.setId(rs.getString("ID"));
        item.setResultId(rs.getString(RESULTID));
        item.setScore(rs.getBigDecimal(SCORE));
        item.setScoreBasis(rs.getString(SCOREBASIS));
        item.setFullScore(rs.getBigDecimal(FULLSCORE));
        item.setRuleItemId(rs.getString(RULEITEMID));
        item.setRuleItemName(rs.getString(SCOREITEMNAME));
        return item;
    }

    private ResultitemOrderDTO resultDTOSetMapping(ResultSet rs) throws SQLException {
        ResultitemOrderDTO item = new ResultitemOrderDTO();
        item.setId(rs.getString("ID"));
        item.setResultId(rs.getString(RESULTID));
        item.setScore(rs.getBigDecimal(SCORE));
        item.setScoreBasis(rs.getString(SCOREBASIS));
        item.setFullScore(rs.getBigDecimal(FULLSCORE));
        item.setRuleItemId(rs.getString(RULEITEMID));
        item.setRuleItemName(rs.getString(SCOREITEMNAME));
        item.setOrdinal(rs.getString("ORDINAL"));
        item.setParentScoreItemId(rs.getString("PARENTSCOREITEMID"));
        item.setParagraphTitle(rs.getString("PARAGRAPHTITLE"));
        return item;
    }
}

