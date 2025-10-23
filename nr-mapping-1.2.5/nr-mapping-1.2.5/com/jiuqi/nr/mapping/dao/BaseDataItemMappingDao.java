/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.mapping.dao;

import com.jiuqi.nr.mapping.bean.BaseDataItemMapping;
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
public class BaseDataItemMappingDao {
    private static final String TABLENAME = "NR_MAPPING_BASEDATA_ITEM";
    private static final String KEY = "MBI_KEY";
    private static final String BASEDATACODE = "MBI_BASEDATA_CODE";
    private static final String MAPPINGSCHEME = "MBI_MAPPINGSCHEME";
    private static final String BASEITEMCODE = "MBI_BASEITEM_CODE";
    private static final String MAPPINGCODE = "MBI_MAPPING_CODE";
    private static final String MAPPINGTITLE = "MBI_MAPPING_TITLE";
    private static final String BASEDATA_ITEM_MAPPING;
    private static final Function<ResultSet, BaseDataItemMapping> ENTITY_READER_BASEDATA_ITEM_MAPPING;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<BaseDataItemMapping> findByMSAndBaseDataCode(String mskey, String baseDataCode) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ? and %s = ?", BASEDATA_ITEM_MAPPING, TABLENAME, MAPPINGSCHEME, BASEDATACODE);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_BASEDATA_ITEM_MAPPING.apply(rs), new Object[]{mskey, baseDataCode});
    }

    public List<BaseDataItemMapping> findByMS(String mskey) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ? ORDER BY %s", BASEDATA_ITEM_MAPPING, TABLENAME, MAPPINGSCHEME, BASEDATACODE);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_BASEDATA_ITEM_MAPPING.apply(rs), new Object[]{mskey});
    }

    public void batchAdd(List<BaseDataItemMapping> bdis) {
        if (CollectionUtils.isEmpty(bdis)) {
            return;
        }
        String SQL_INSERT = String.format("INSERT INTO %s (%s) VALUES (?, ?, ?, ?, ?, ?)", TABLENAME, BASEDATA_ITEM_MAPPING);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (BaseDataItemMapping bdi : bdis) {
            Object[] param = new Object[]{bdi.getKey(), bdi.getBaseDataCode(), bdi.getMsKey(), bdi.getBaseItemCode(), bdi.getMappingCode(), bdi.getMappingTitle()};
            args.add(param);
        }
        this.jdbcTemplate.batchUpdate(SQL_INSERT, args);
    }

    public void deleteByMSBaseData(String msKey, String baseDataCode) {
        String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ? and %s = ?", TABLENAME, MAPPINGSCHEME, BASEDATACODE);
        this.jdbcTemplate.update(SQL_DELETE, new Object[]{msKey, baseDataCode});
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
        BASEDATA_ITEM_MAPPING = builder.append(KEY).append(",").append(BASEDATACODE).append(",").append(MAPPINGSCHEME).append(",").append(BASEITEMCODE).append(",").append(MAPPINGCODE).append(",").append(MAPPINGTITLE).toString();
        ENTITY_READER_BASEDATA_ITEM_MAPPING = rs -> {
            BaseDataItemMapping item = new BaseDataItemMapping();
            int index = 1;
            try {
                item.setKey(rs.getString(index++));
                item.setBaseDataCode(rs.getString(index++));
                item.setMsKey(rs.getString(index++));
                item.setBaseItemCode(rs.getString(index++));
                item.setMappingCode(rs.getString(index++));
                item.setMappingTitle(rs.getString(index++));
            }
            catch (SQLException e) {
                throw new RuntimeException("read NR_MAPPING_BASEDATA_ITEM error.", e);
            }
            return item;
        };
    }
}

