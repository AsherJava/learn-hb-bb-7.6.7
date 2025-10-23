/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.mapping.dao;

import com.jiuqi.nr.mapping.bean.UnitRule;
import com.jiuqi.nr.mapping.common.UnitRuleType;
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
public class UnitRuleDao {
    private static final String TABLENAME = "NR_MAPPING_ENTITY_RULE";
    private static final String KEY = "MR_KEY";
    private static final String MAPPINGSCHEME = "MR_MAPPINGSCHEME";
    private static final String TYPE = "MR_TYPE";
    private static final String EXPRESS = "MR_EXPRESS";
    private static final String UNIT_RULE;
    private static final Function<ResultSet, UnitRule> ENTITY_READER_UNIT_RULE;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<UnitRule> findByMS(String mskey) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", UNIT_RULE, TABLENAME, MAPPINGSCHEME);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_UNIT_RULE.apply(rs), new Object[]{mskey});
    }

    public void add(List<UnitRule> saveRules) {
        if (CollectionUtils.isEmpty(saveRules)) {
            return;
        }
        String SQL_INSERT = String.format("INSERT INTO %s (%s) VALUES (?, ?, ?, ?)", TABLENAME, UNIT_RULE);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (UnitRule r : saveRules) {
            Object[] param = new Object[]{r.getKey(), r.getMrKey(), r.getType().value(), r.getExpress()};
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
        UNIT_RULE = builder.append(KEY).append(",").append(MAPPINGSCHEME).append(",").append(TYPE).append(",").append(EXPRESS).toString();
        ENTITY_READER_UNIT_RULE = rs -> {
            UnitRule rule = new UnitRule();
            int index = 1;
            try {
                rule.setKey(rs.getString(index++));
                rule.setMrKey(rs.getString(index++));
                rule.setType(UnitRuleType.valueOf(rs.getInt(index++)));
                rule.setExpress(rs.getString(index++));
            }
            catch (SQLException e) {
                throw new RuntimeException("read NR_MAPPING_ENTITY_RULE error.", e);
            }
            return rule;
        };
    }
}

