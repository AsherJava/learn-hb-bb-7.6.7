/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.mapping2.dao;

import com.jiuqi.nr.mapping2.bean.PeriodMapping;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class PeriodMappingDao {
    private static final String TABLENAME = "NVWA_MAPPING_NR_PERIOD";
    private static final String KEY = "MP_KEY";
    private static final String MAPPINGSCHEME = "MP_MAPPINGSCHEME";
    private static final String PERIOD = "MP_PERIOD";
    private static final String MAPPING = "MP_MAPPING";
    private static final String PERIOD_MAPPING;
    private static final Function<ResultSet, PeriodMapping> ENTITY_READER_PERIOD_MAPPING;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add(List<PeriodMapping> ps) {
        if (CollectionUtils.isEmpty(ps)) {
            return;
        }
        String SQL_INSERT = String.format("INSERT INTO %s (%s) VALUES (?, ?, ?, ?)", TABLENAME, PERIOD_MAPPING);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (PeriodMapping p : ps) {
            Object[] param = new Object[]{p.getKey(), p.getMsKey(), p.getPeriod(), p.getMapping()};
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

    public List<PeriodMapping> findByMS(String mskey) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ? order by %s", PERIOD_MAPPING, TABLENAME, MAPPINGSCHEME, PERIOD);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_PERIOD_MAPPING.apply(rs), new Object[]{mskey});
    }

    static {
        StringBuilder builder = new StringBuilder();
        PERIOD_MAPPING = builder.append(KEY).append(",").append(MAPPINGSCHEME).append(",").append(PERIOD).append(",").append(MAPPING).toString();
        ENTITY_READER_PERIOD_MAPPING = rs -> {
            PeriodMapping period = new PeriodMapping();
            int index = 1;
            try {
                period.setKey(rs.getString(index++));
                period.setMsKey(rs.getString(index++));
                period.setPeriod(rs.getString(index++));
                period.setMapping(rs.getString(index));
            }
            catch (SQLException e) {
                throw new RuntimeException("read NR_MAPPING_PERIOD error.", e);
            }
            return period;
        };
    }
}

