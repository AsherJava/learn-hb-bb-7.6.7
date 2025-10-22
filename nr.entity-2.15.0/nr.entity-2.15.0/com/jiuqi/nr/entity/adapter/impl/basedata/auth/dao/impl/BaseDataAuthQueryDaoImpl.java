/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 */
package com.jiuqi.nr.entity.adapter.impl.basedata.auth.dao.impl;

import com.jiuqi.nr.entity.adapter.impl.basedata.auth.dao.BaseDataAuthQueryDao;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class BaseDataAuthQueryDaoImpl
implements BaseDataAuthQueryDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Set<String> getUserId(Set<String> userName) {
        HashSet userId = new HashSet();
        StringBuilder builder = new StringBuilder("SELECT ID FROM NP_USER ");
        if (userName != null && !userName.isEmpty()) {
            builder.append("WHERE NAME IN (:NAMES)");
            HashMap<String, Set<String>> params = new HashMap<String, Set<String>>();
            params.put("NAMES", userName);
            NamedParameterJdbcTemplate nameJdbc = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
            return (Set)nameJdbc.query(builder.toString(), params, x -> {
                while (x.next()) {
                    userId.add(x.getString("ID"));
                }
                return userId;
            });
        }
        return (Set)this.jdbcTemplate.query(builder.toString(), rs -> {
            while (rs.next()) {
                userId.add(rs.getString("ID"));
            }
            return userId;
        });
    }

    @Override
    public Set<String> getRelateUserIDByOrg(Collection<String> orgCode) {
        if (orgCode == null || orgCode.isEmpty()) {
            return Collections.emptySet();
        }
        String sql = "SELECT ID FROM NP_USER WHERE ORG_CODE IN (:ordCode)";
        NamedParameterJdbcTemplate nameJdbc = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        HashMap<String, Collection<String>> params = new HashMap<String, Collection<String>>();
        params.put("ordCode", orgCode);
        return (Set)nameJdbc.query(sql, params, rs -> {
            HashSet<String> res = new HashSet<String>();
            while (rs.next()) {
                res.add(rs.getString("ID"));
            }
            return res;
        });
    }
}

