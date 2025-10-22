/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.finalaccountsaudit.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ObserverDao {
    @Autowired
    JdbcTemplate jdbcTpl;

    public boolean tableExists(String tableName) {
        String sql = String.format("select count(1) from %s", tableName);
        try {
            this.jdbcTpl.query(sql, (RowMapper)new RowMapper<Object>(){

                public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return true;
                }
            });
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}

