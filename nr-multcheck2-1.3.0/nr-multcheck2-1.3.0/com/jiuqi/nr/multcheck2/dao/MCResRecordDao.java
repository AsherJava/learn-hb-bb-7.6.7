/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.multcheck2.dao;

import com.jiuqi.nr.multcheck2.bean.MultcheckResRecord;
import com.jiuqi.nr.multcheck2.common.CheckSource;
import com.jiuqi.nr.multcheck2.common.SerializeUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Repository
@Deprecated
public class MCResRecordDao {
    private static final Logger logger = LoggerFactory.getLogger(MCResRecordDao.class);
    private static final String RECORD;
    private static final Function<ResultSet, MultcheckResRecord> ENTITY_READER_MULTCHECK_RECORD;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add(String tableName, MultcheckResRecord record, List<String> dims) {
        String dim = null;
        try {
            if (!CollectionUtils.isEmpty(record.getDims())) {
                dim = SerializeUtil.serializeToJson(record.getDims());
            }
        }
        catch (Exception e) {
            logger.error("\u7efc\u5408\u5ba1\u68382.0\u8f6c\u6362record\u7684\u60c5\u666f\uff1a\uff1a" + e.getMessage(), e);
        }
        String SQL_INSERT = String.format("INSERT INTO %s (%s) VALUES (?,?,?,?,?,?,?,?,?,?,?)", tableName, RECORD);
        this.jdbcTemplate.update(SQL_INSERT, new Object[]{record.getKey(), record.getTask(), record.getPeriod(), record.getSource().value(), record.getSchemeKey(), dim, record.getSuccess(), record.getFailed(), new Timestamp(record.getBegin().getTime()), new Timestamp(record.getEnd().getTime()), record.getUser()});
    }

    public MultcheckResRecord getRecordByKey(String tableName, String key, List<String> dims) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", RECORD, tableName, "MRR_KEY");
        return (MultcheckResRecord)this.jdbcTemplate.query(SQL_QUERY, rs -> {
            if (rs.next()) {
                MultcheckResRecord record = ENTITY_READER_MULTCHECK_RECORD.apply(rs);
                String dimClob = rs.getString("MRR_DIM");
                if (!CollectionUtils.isEmpty(dims) && StringUtils.hasText(dimClob)) {
                    try {
                        Map dimMap = SerializeUtil.deserializeFromJson(dimClob, Map.class);
                        record.setDims(dimMap);
                    }
                    catch (Exception e) {
                        logger.error("\u7efc\u5408\u5ba1\u68382.0\u8f6c\u6362record\u7684\u60c5\u666f\uff1a\uff1a" + e.getMessage(), e);
                    }
                }
                return record;
            }
            return null;
        }, new Object[]{key});
    }

    public List<String> getRecordByTaskPeriod(String tableName, String task, String period, String userName, CheckSource source) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ? AND %s = ? AND %s = ? AND %s = ? ORDER BY %s DESC", "MRR_KEY", tableName, "MRR_TASK", "MRR_PERIOD", "MRR_USER", "MRR_SOURCE", "MRR_END_TIME");
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> rs.getString(1), new Object[]{task, period, userName, source.value()});
    }

    public List<String> getRecordByTaskPeriodScheme(String tableName, String task, String period, String scheme, String userName, CheckSource source) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ? AND %s = ? AND %s = ? AND %s = ? AND %s = ? ORDER BY %s DESC", "MRR_KEY", tableName, "MRR_TASK", "MRR_PERIOD", "MRR_USER", "MRR_SOURCE", "MS_KEY", "MRR_END_TIME");
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> rs.getString(1), new Object[]{task, period, userName, source.value(), scheme});
    }

    public void cleanRecord(Date cleanDate, String tableName) {
        String sql = String.format("DELETE FROM %s WHERE %s < ?", tableName, "MRR_END_TIME");
        this.jdbcTemplate.update(sql, new Object[]{cleanDate});
    }

    public void cleanAllRecords(String tableName) {
        String sql = String.format("delete from %s where 1=1", tableName);
        this.jdbcTemplate.update(sql);
    }

    static {
        StringBuilder builder = new StringBuilder();
        RECORD = builder.append("MRR_KEY").append(",").append("MRR_TASK").append(",").append("MRR_PERIOD").append(",").append("MRR_SOURCE").append(",").append("MS_KEY").append(",").append("MRR_DIM").append(",").append("MRR_SUCCESS").append(",").append("MRR_FAILED").append(",").append("MRR_BEGIN_TIME").append(",").append("MRR_END_TIME").append(",").append("MRR_USER").toString();
        ENTITY_READER_MULTCHECK_RECORD = rs -> {
            MultcheckResRecord record = new MultcheckResRecord();
            try {
                record.setKey(rs.getString("MRR_KEY"));
                record.setTask(rs.getString("MRR_TASK"));
                record.setPeriod(rs.getString("MRR_PERIOD"));
                record.setSource(CheckSource.fromValue(rs.getInt("MRR_SOURCE")));
                record.setSchemeKey(rs.getString("MS_KEY"));
                record.setSuccess(rs.getInt("MRR_SUCCESS"));
                record.setFailed(rs.getInt("MRR_FAILED"));
                record.setBegin(rs.getTimestamp("MRR_BEGIN_TIME"));
                record.setEnd(rs.getTimestamp("MRR_END_TIME"));
                record.setUser(rs.getString("MRR_USER"));
            }
            catch (SQLException e) {
                throw new RuntimeException("read MultcheckResRecord error.", e);
            }
            return record;
        };
    }
}

