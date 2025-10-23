/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.multcheck2.dao;

import com.jiuqi.nr.multcheck2.bean.MultcheckSchemeOrg;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MultcheckSchemeOrgDao {
    private static final String TABLE_NAME = "NR_MULTCHECK_SCHEME_ORG";
    private static final String KEY = "MSO_KEY";
    private static final String SCHEME = "MS_KEY";
    private static final String ORG = "MSO_ORG";
    private static final String MULTCHECK_SCHEME_ORG;
    private static final Function<ResultSet, MultcheckSchemeOrg> ENTITY_READER_MULTCHECK_SCHEME_ORG;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void batchAdd(List<MultcheckSchemeOrg> items) {
        String SQL_INSERT = String.format("INSERT INTO %s (%s) VALUES (?, ?, ?)", TABLE_NAME, MULTCHECK_SCHEME_ORG);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (MultcheckSchemeOrg item : items) {
            Object[] param = new Object[]{item.getKey(), item.getScheme(), item.getOrg()};
            args.add(param);
        }
        this.jdbcTemplate.batchUpdate(SQL_INSERT, args);
    }

    public void deleteByScheme(String scheme) {
        String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, SCHEME);
        this.jdbcTemplate.update(SQL_DELETE, new Object[]{scheme});
    }

    public List<MultcheckSchemeOrg> getByScheme(String scheme) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", MULTCHECK_SCHEME_ORG, TABLE_NAME, SCHEME);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_MULTCHECK_SCHEME_ORG.apply(rs), new Object[]{scheme});
    }

    public int getCountByScheme(String scheme) {
        String SQL_QUERY = String.format("SELECT COUNT(*) FROM %s WHERE %s = ?", TABLE_NAME, SCHEME);
        return (Integer)this.jdbcTemplate.queryForObject(SQL_QUERY, Integer.class, new Object[]{scheme});
    }

    static {
        StringBuilder builder = new StringBuilder();
        MULTCHECK_SCHEME_ORG = builder.append(KEY).append(",").append(SCHEME).append(",").append(ORG).toString();
        ENTITY_READER_MULTCHECK_SCHEME_ORG = rs -> {
            MultcheckSchemeOrg mso = new MultcheckSchemeOrg();
            int index = 1;
            try {
                mso.setKey(rs.getString(index++));
                mso.setScheme(rs.getString(index++));
                mso.setOrg(rs.getString(index++));
            }
            catch (SQLException e) {
                throw new RuntimeException("read multcheckItem error.", e);
            }
            return mso;
        };
    }
}

