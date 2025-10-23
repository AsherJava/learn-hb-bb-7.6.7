/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.mapping2.dao;

import com.jiuqi.nr.mapping2.bean.ZBMapping;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class ZBMappingDao {
    private static final String TABLENAME = "NVWA_MAPPING_NR_ZB";
    private static final String KEY = "MZ_KEY";
    private static final String MAPPINGSCHEME = "MZ_MAPPINGSCHEME";
    private static final String FORM = "MZ_FORM_CODE";
    private static final String TABLE = "MZ_TABLE_CODE";
    private static final String REGION = "MZ_REGION_CODE";
    private static final String ZB = "MZ_ZB_CODE";
    private static final String MAPPING = "MZ_MAPPING";
    private static final String ZB_MAPPING;
    private static final Function<ResultSet, ZBMapping> ENTITY_READER_ZB_MAPPING;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void batchAdd(List<ZBMapping> zbMappings) {
        if (CollectionUtils.isEmpty(zbMappings)) {
            return;
        }
        String SQL_INSERT = String.format("INSERT INTO %s (%s) VALUES (?, ?, ?, ?, ?, ?, ?)", TABLENAME, ZB_MAPPING);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (ZBMapping zb : zbMappings) {
            Object[] param = new Object[]{zb.getKey(), zb.getMsKey(), zb.getForm(), zb.getTable(), zb.getRegionCode(), zb.getZbCode(), zb.getMapping()};
            args.add(param);
        }
        this.jdbcTemplate.batchUpdate(SQL_INSERT, args);
    }

    public void deleteByMS(String msKey) {
        String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLENAME, MAPPINGSCHEME);
        this.jdbcTemplate.update(SQL_DELETE, new Object[]{msKey});
    }

    public void batchDeleteByKey(List<String> keys) {
        String SQL_BATCH_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLENAME, KEY);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (String key : keys) {
            Object[] param = new Object[]{key};
            args.add(param);
        }
        this.jdbcTemplate.batchUpdate(SQL_BATCH_DELETE, args);
    }

    public void deleteByMSAndForm(String msKey, String formCode) {
        String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ? and %s = ?", TABLENAME, MAPPINGSCHEME, FORM);
        this.jdbcTemplate.update(SQL_DELETE, new Object[]{msKey, formCode});
    }

    public List<ZBMapping> findByMS(String mskey) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ? order by %s", ZB_MAPPING, TABLENAME, MAPPINGSCHEME, FORM);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_ZB_MAPPING.apply(rs), new Object[]{mskey});
    }

    public List<ZBMapping> findByMSAndForm(String mskey, String formCode) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ? AND %s = ?", ZB_MAPPING, TABLENAME, MAPPINGSCHEME, FORM);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_ZB_MAPPING.apply(rs), new Object[]{mskey, formCode});
    }

    public List<ZBMapping> findByMSAndMapping(String mskey, String mapping) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ? AND %s = ?", ZB_MAPPING, TABLENAME, MAPPINGSCHEME, MAPPING);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_ZB_MAPPING.apply(rs), new Object[]{mskey, mapping});
    }

    public List<ZBMapping> findByMSAndMapping(String mskey, List<String> mappings) {
        if (CollectionUtils.isEmpty(mappings)) {
            return new ArrayList<ZBMapping>();
        }
        String placeholders = mappings.stream().map(m -> "?").collect(Collectors.joining(","));
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ? AND %s IN (%s)", ZB_MAPPING, TABLENAME, MAPPINGSCHEME, MAPPING, placeholders);
        ArrayList<String> params = new ArrayList<String>();
        params.add(mskey);
        params.addAll(mappings);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_ZB_MAPPING.apply(rs), params.toArray());
    }

    static {
        StringBuilder builder = new StringBuilder();
        ZB_MAPPING = builder.append(KEY).append(",").append(MAPPINGSCHEME).append(",").append(FORM).append(",").append(TABLE).append(",").append(REGION).append(",").append(ZB).append(",").append(MAPPING).toString();
        ENTITY_READER_ZB_MAPPING = rs -> {
            ZBMapping zb = new ZBMapping();
            int index = 1;
            try {
                zb.setKey(rs.getString(index++));
                zb.setMsKey(rs.getString(index++));
                zb.setForm(rs.getString(index++));
                zb.setTable(rs.getString(index++));
                zb.setRegionCode(rs.getString(index++));
                zb.setZbCode(rs.getString(index++));
                zb.setMapping(rs.getString(index++));
            }
            catch (SQLException e) {
                throw new RuntimeException("read NVWA_MAPPING_NR_ZB error.", e);
            }
            return zb;
        };
    }
}

