/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.enumcheck.dao;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.enumcheck.dao.EnumCheckRes;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class EnumCheckResDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(String id, String schemeKey, String data) {
        String sql = "INSERT INTO SYS_ENUMCHECK_RECORD (ID_,SCHEME_KEY_, DATA_, CREATED_BY_, CREATED_AT_) VALUES (?, ?, ?, ?, ?)";
        String userId = NpContextHolder.getContext().getUserId();
        this.jdbcTemplate.update(sql, new Object[]{id, schemeKey, data, userId, Timestamp.from(Instant.now())});
    }

    public void delete(String id) {
        String sql = "DELETE FROM SYS_ENUMCHECK_RECORD WHERE ID_ = ?";
        this.jdbcTemplate.update(sql, new Object[]{id});
    }

    public void update(String id, String schemeKey, String data) {
        String createdBy = NpContextHolder.getContext().getUserId();
        String sql = "UPDATE SYS_ENUMCHECK_RECORD SET SCHEME_KEY_ = ?, DATA_ = ?, CREATED_BY_ = ? WHERE ID_ = ?";
        this.jdbcTemplate.update(sql, new Object[]{schemeKey, data, createdBy, id});
    }

    public EnumCheckRes findById(String id) {
        String sql = "SELECT * FROM SYS_ENUMCHECK_RECORD WHERE ID_ = ?";
        return (EnumCheckRes)this.jdbcTemplate.queryForObject(sql, new Object[]{id}, (RowMapper)new EnumCheckResRowMapper());
    }

    public List<EnumCheckRes> findByCreatedAt(Timestamp createdAt) {
        String sql = "SELECT ID_,SCHEME_KEY_ FROM SYS_ENUMCHECK_RECORD WHERE CREATED_AT_ < ?";
        return this.jdbcTemplate.query(sql, (RowMapper)new EnumCheckDeleteInfoRowMapper(), new Object[]{createdAt});
    }

    public List<EnumCheckRes> findBySchemeKey(String schemeKey) {
        String sql = "SELECT ID_,SCHEME_KEY_ FROM SYS_ENUMCHECK_RECORD WHERE SCHEME_KEY_ = ?";
        return this.jdbcTemplate.query(sql, (RowMapper)new EnumCheckDeleteInfoRowMapper(), new Object[]{schemeKey});
    }

    public void deleteByCreatedAt(Timestamp createdAt) {
        String sql = "DELETE FROM SYS_ENUMCHECK_RECORD WHERE CREATED_AT_ < ?";
        this.jdbcTemplate.update(sql, new Object[]{createdAt});
    }

    public void deleteBySchemeKey(String schemeKey) {
        String sql = "DELETE FROM SYS_ENUMCHECK_RECORD WHERE SCHEME_KEY_ = ?";
        this.jdbcTemplate.update(sql, new Object[]{schemeKey});
    }

    private static class EnumCheckDeleteInfoRowMapper
    implements RowMapper<EnumCheckRes> {
        private EnumCheckDeleteInfoRowMapper() {
        }

        public EnumCheckRes mapRow(ResultSet rs, int rowNum) throws SQLException {
            EnumCheckRes enumCheckRes = new EnumCheckRes();
            enumCheckRes.setId(rs.getString("ID_"));
            enumCheckRes.setSchemeKey(rs.getString("SCHEME_KEY_"));
            return enumCheckRes;
        }
    }

    private static class EnumCheckResRowMapper
    implements RowMapper<EnumCheckRes> {
        private EnumCheckResRowMapper() {
        }

        public EnumCheckRes mapRow(ResultSet rs, int rowNum) throws SQLException {
            EnumCheckRes inenumCheckResCheckRes = new EnumCheckRes();
            inenumCheckResCheckRes.setId(rs.getString("ID_"));
            inenumCheckResCheckRes.setSchemeKey(rs.getString("SCHEME_KEY_"));
            inenumCheckResCheckRes.setCreatedAt(rs.getTimestamp("CREATED_AT_"));
            inenumCheckResCheckRes.setData(rs.getString("DATA_"));
            inenumCheckResCheckRes.setCreatedBy(rs.getString("CREATED_BY_"));
            return inenumCheckResCheckRes;
        }
    }
}

