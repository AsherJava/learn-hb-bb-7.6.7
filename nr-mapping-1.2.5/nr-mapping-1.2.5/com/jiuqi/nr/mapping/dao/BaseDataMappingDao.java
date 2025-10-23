/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.mapping.dao;

import com.jiuqi.nr.mapping.bean.BaseDataMapping;
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
public class BaseDataMappingDao {
    private static final String TABLENAME = "NR_MAPPING_BASEDATA";
    private static final String KEY = "MB_KEY";
    private static final String MAPPINGSCHEME = "MB_MAPPINGSCHEME";
    private static final String BASEDATACODE = "MB_BASEDATA_CODE";
    private static final String MAPPINGCODE = "MB_MAPPING_CODE";
    private static final String MAPPINGTITLE = "MB_MAPPING_TITLE";
    private static final String BASEDATA_MAPPING;
    private static final Function<ResultSet, BaseDataMapping> ENTITY_READER_BASEDATA_MAPPING;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<BaseDataMapping> findByMS(String mskey) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ? ", BASEDATA_MAPPING, TABLENAME, MAPPINGSCHEME);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_BASEDATA_MAPPING.apply(rs), new Object[]{mskey});
    }

    public void batchAdd(List<BaseDataMapping> bds) {
        if (CollectionUtils.isEmpty(bds)) {
            return;
        }
        String SQL_INSERT = String.format("INSERT INTO %s (%s) VALUES (?, ?, ?, ?, ?)", TABLENAME, BASEDATA_MAPPING);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (BaseDataMapping bd : bds) {
            Object[] param = new Object[]{bd.getKey(), bd.getMsKey(), bd.getBaseDataCode(), bd.getMappingCode(), bd.getMappingTitle()};
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
        BASEDATA_MAPPING = builder.append(KEY).append(",").append(MAPPINGSCHEME).append(",").append(BASEDATACODE).append(",").append(MAPPINGCODE).append(",").append(MAPPINGTITLE).toString();
        ENTITY_READER_BASEDATA_MAPPING = rs -> {
            BaseDataMapping baseData = new BaseDataMapping();
            int index = 1;
            try {
                baseData.setKey(rs.getString(index++));
                baseData.setMsKey(rs.getString(index++));
                baseData.setBaseDataCode(rs.getString(index++));
                baseData.setMappingCode(rs.getString(index++));
                baseData.setMappingTitle(rs.getString(index++));
            }
            catch (SQLException e) {
                throw new RuntimeException("read NR_MAPPING_BASEDATA error.", e);
            }
            return baseData;
        };
    }
}

