/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.mapping.dao;

import com.jiuqi.nr.mapping.bean.UnitMapping;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class UnitMappingDao {
    private static final String TABLENAME = "NR_MAPPING_ENTITY";
    private static final String KEY = "ME_KEY";
    private static final String MAPPINGSCHEME = "ME_MAPPINGSCHEME";
    private static final String UNITCODE = "ME_UNIT_CODE";
    private static final String MAPPING = "ME_MAPPING";
    private static final String ORDER = "ME_ORDER";
    private static final String UNIT_MAPPING;
    private static final Function<ResultSet, UnitMapping> ENTITY_READER_UNIT_MAPPING;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<UnitMapping> findByMS(String mskey) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ? order by %s", UNIT_MAPPING, TABLENAME, MAPPINGSCHEME, ORDER);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_UNIT_MAPPING.apply(rs), new Object[]{mskey});
    }

    public void add(List<UnitMapping> mappings) {
        if (CollectionUtils.isEmpty(mappings)) {
            return;
        }
        String SQL_INSERT = String.format("INSERT INTO %s (%s) VALUES (?, ?, ?, ?,?)", TABLENAME, UNIT_MAPPING);
        LinkedList<Object[]> args = new LinkedList<Object[]>();
        for (UnitMapping p : mappings) {
            Object[] param = new Object[]{p.getKey(), p.getMsKey(), p.getUnitCode(), p.getMapping(), p.getOrder()};
            args.add(param);
        }
        this.jdbcTemplate.batchUpdate(SQL_INSERT, args);
    }

    public void deleteByMS(String msKey) {
        String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLENAME, MAPPINGSCHEME);
        this.jdbcTemplate.update(SQL_DELETE, new Object[]{msKey});
    }

    public void batchDeleteByMS(List<String> keys) {
        String SQL_BATCH_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLENAME, MAPPINGSCHEME);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (String key : keys) {
            Object[] param = new Object[]{key};
            args.add(param);
        }
        this.jdbcTemplate.batchUpdate(SQL_BATCH_DELETE, args);
    }

    static {
        StringBuilder builder = new StringBuilder();
        UNIT_MAPPING = builder.append(KEY).append(",").append(MAPPINGSCHEME).append(",").append(UNITCODE).append(",").append(MAPPING).append(",").append(ORDER).toString();
        ENTITY_READER_UNIT_MAPPING = rs -> {
            UnitMapping unit = new UnitMapping();
            int index = 1;
            try {
                unit.setKey(rs.getString(index++));
                unit.setMsKey(rs.getString(index++));
                unit.setUnitCode(rs.getString(index++));
                unit.setMapping(rs.getString(index++));
                unit.setOrder(rs.getInt(index++));
            }
            catch (SQLException e) {
                throw new RuntimeException("read NR_MAPPING_ENTITY error.", e);
            }
            return unit;
        };
    }
}

