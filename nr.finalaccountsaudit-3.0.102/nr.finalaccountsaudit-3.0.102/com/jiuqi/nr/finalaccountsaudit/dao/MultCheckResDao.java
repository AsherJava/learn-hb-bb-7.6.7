/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.finalaccountsaudit.dao;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.finalaccountsaudit.dao.MultCheckRes;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MultCheckResDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(String id, String data) {
        String sql = "INSERT INTO SYS_MULTCHECK_RES (ID_, DATA_, CREATED_BY_, CREATED_AT_) VALUES (?, ?, ?, ?)";
        String userId = NpContextHolder.getContext().getUserId();
        this.jdbcTemplate.update(sql, new Object[]{id, data, userId, Timestamp.from(Instant.now())});
    }

    public void delete(String id) {
        String sql = "DELETE FROM SYS_MULTCHECK_RES WHERE ID_ = ?";
        this.jdbcTemplate.update(sql, new Object[]{id});
    }

    public void update(String id, String data) {
        String createdBy = NpContextHolder.getContext().getUserId();
        String sql = "UPDATE SYS_MULTCHECK_RES SET DATA_ = ?, CREATED_BY_ = ? WHERE ID_ = ?";
        this.jdbcTemplate.update(sql, new Object[]{data, createdBy, id});
    }

    public MultCheckRes findById(String id) {
        String sql = "SELECT * FROM SYS_MULTCHECK_RES WHERE ID_ = ?";
        return (MultCheckRes)this.jdbcTemplate.queryForObject(sql, new Object[]{id}, (RowMapper)new SysMultCheckResRowMapper());
    }

    public void deleteByCreatedAt(Date createdAt) {
        String sql = "DELETE FROM SYS_MULTCHECK_RES WHERE CREATED_AT_ < ?";
        createdAt = new Timestamp(createdAt.getTime());
        this.jdbcTemplate.update(sql, new Object[]{createdAt});
    }

    private static class SysMultCheckResRowMapper
    implements RowMapper<MultCheckRes> {
        private SysMultCheckResRowMapper() {
        }

        public MultCheckRes mapRow(ResultSet rs, int rowNum) throws SQLException {
            MultCheckRes sysMultCheckRes = new MultCheckRes();
            sysMultCheckRes.setId(rs.getString("ID_"));
            sysMultCheckRes.setCreatedAt(rs.getTimestamp("CREATED_AT_"));
            sysMultCheckRes.setData(rs.getString("DATA_"));
            sysMultCheckRes.setCreatedBy(rs.getString("CREATED_BY_"));
            return sysMultCheckRes;
        }
    }
}

