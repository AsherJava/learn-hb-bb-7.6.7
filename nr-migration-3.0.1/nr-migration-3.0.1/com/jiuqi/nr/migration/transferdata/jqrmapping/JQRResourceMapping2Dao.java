/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.migration.transferdata.jqrmapping;

import com.jiuqi.nr.migration.transferdata.jqrmapping.JQRResourceMapping2DO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class JQRResourceMapping2Dao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String TABLENAME = "NR_MAPPING_JQR_CUSTOM";
    private static final String KEY = "MJ_KEY";
    private static final String MAPPING_SCHEME = "MJ_MAPPING_SCHEME";
    private static final String DESC = "MJ_DESC";
    private static final String NRCODE = "MJ_NR_CODE";
    private static final String JQRMAPPING = "MJ_MAPPING";
    private static final String JQRCUSTOM_MAPPING;
    private static final Function<ResultSet, JQRResourceMapping2DO> ENTITY_READER_PERIOD_MAPPING;

    public void add(List<JQRResourceMapping2DO> ps) {
        if (CollectionUtils.isEmpty(ps)) {
            return;
        }
        String SQL_INSERT = String.format("INSERT INTO %s (%s) VALUES (?, ?, ?, ?, ?)", TABLENAME, JQRCUSTOM_MAPPING);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (JQRResourceMapping2DO p : ps) {
            Object[] param = new Object[]{UUID.randomUUID().toString(), p.getMappingSchemeKey(), p.getDesc(), p.getNrCode(), p.getJqrMapping()};
            args.add(param);
        }
        this.jdbcTemplate.batchUpdate(SQL_INSERT, args);
    }

    public void delete(String mskey) {
        String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLENAME, MAPPING_SCHEME);
        this.jdbcTemplate.update(SQL_DELETE, new Object[]{mskey});
    }

    public List<JQRResourceMapping2DO> findByMS(String key) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", JQRCUSTOM_MAPPING, TABLENAME, MAPPING_SCHEME);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_PERIOD_MAPPING.apply(rs), new Object[]{key});
    }

    public void updateJqrMapping(List<JQRResourceMapping2DO> ps) {
        if (CollectionUtils.isEmpty(ps)) {
            return;
        }
        String SQL_UPDATE = String.format("UPDATE %s SET %s = ? WHERE %s = ? AND %s = ?", TABLENAME, JQRMAPPING, KEY, NRCODE);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (JQRResourceMapping2DO p : ps) {
            Object[] param = new Object[]{p.getJqrMapping(), p.getKey(), p.getNrCode()};
            args.add(param);
        }
        this.jdbcTemplate.batchUpdate(SQL_UPDATE, args);
    }

    static {
        StringBuilder builder = new StringBuilder();
        JQRCUSTOM_MAPPING = builder.append(KEY).append(",").append(MAPPING_SCHEME).append(",").append(DESC).append(",").append(NRCODE).append(",").append(JQRMAPPING).toString();
        ENTITY_READER_PERIOD_MAPPING = rs -> {
            JQRResourceMapping2DO jqrCustomMapping = new JQRResourceMapping2DO();
            try {
                jqrCustomMapping.setKey(rs.getString(1));
                jqrCustomMapping.setMappingSchemeKey(rs.getString(2));
                jqrCustomMapping.setDesc(rs.getString(3));
                jqrCustomMapping.setNrCode(rs.getString(4));
                jqrCustomMapping.setJqrMapping(rs.getString(5));
            }
            catch (SQLException e) {
                throw new RuntimeException("read NR_MAPPING_JQR_CUSTOM error.", e);
            }
            return jqrCustomMapping;
        };
    }
}

