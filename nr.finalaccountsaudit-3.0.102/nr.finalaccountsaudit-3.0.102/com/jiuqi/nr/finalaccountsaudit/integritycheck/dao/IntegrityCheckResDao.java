/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.finalaccountsaudit.integritycheck.dao;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.finalaccountsaudit.integritycheck.dao.IntegrityCheckRes;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class IntegrityCheckResDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(String id, String schemeKey, String data) {
        String sql = "INSERT INTO SYS_INTEGRITY_RECORD (ID_,SCHEME_KEY_, DATA_, CREATED_BY_, CREATED_AT_) VALUES (?, ?, ?, ?, ?)";
        String userId = NpContextHolder.getContext().getUserId();
        this.jdbcTemplate.update(sql, new Object[]{id, schemeKey, data, userId, Timestamp.from(Instant.now())});
    }

    public void delete(String id) {
        String sql = "DELETE FROM SYS_INTEGRITY_RECORD WHERE ID_ = ?";
        this.jdbcTemplate.update(sql, new Object[]{id});
    }

    public void update(String id, String schemeKey, String data) {
        String createdBy = NpContextHolder.getContext().getUserId();
        String sql = "UPDATE SYS_INTEGRITY_RECORD SET SCHEME_KEY_ = ?, DATA_ = ?, CREATED_BY_ = ? WHERE ID_ = ?";
        this.jdbcTemplate.update(sql, new Object[]{schemeKey, data, createdBy, id});
    }

    public IntegrityCheckRes findById(String id) {
        String sql = "SELECT * FROM SYS_INTEGRITY_RECORD WHERE ID_ = ?";
        return (IntegrityCheckRes)this.jdbcTemplate.queryForObject(sql, new Object[]{id}, (RowMapper)new IntegrityCheckResRowMapper());
    }

    public void deleteByCreatedAt(Timestamp createdAt) {
        String sql = "DELETE FROM SYS_INTEGRITY_RECORD WHERE CREATED_AT_ < ?";
        this.jdbcTemplate.update(sql, new Object[]{createdAt});
    }

    private static class IntegrityCheckResRowMapper
    implements RowMapper<IntegrityCheckRes> {
        private IntegrityCheckResRowMapper() {
        }

        public IntegrityCheckRes mapRow(ResultSet rs, int rowNum) throws SQLException {
            IntegrityCheckRes integrityCheckRes = new IntegrityCheckRes();
            integrityCheckRes.setId(rs.getString("ID_"));
            integrityCheckRes.setSchemeKey(rs.getString("SCHEME_KEY_"));
            integrityCheckRes.setCreatedAt(rs.getTimestamp("CREATED_AT_"));
            integrityCheckRes.setData(rs.getString("DATA_"));
            integrityCheckRes.setCreatedBy(rs.getString("CREATED_BY_"));
            return integrityCheckRes;
        }
    }
}

