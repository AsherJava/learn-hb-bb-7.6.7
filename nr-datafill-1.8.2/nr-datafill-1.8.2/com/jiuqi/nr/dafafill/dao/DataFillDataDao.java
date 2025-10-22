/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.dafafill.dao;

import com.jiuqi.nr.dafafill.entity.DataFillData;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Repository
public class DataFillDataDao {
    private static final String TABLE_NAME = "NR_DATAFILL_MODEL";
    private static final String ID = "DFM_ID";
    private static final String DFDID = "DFD_ID";
    private static final String LANGUAGE = "DFM_LANGUAGE";
    private static final String DATA = "DFM_DATA";
    private static final String QUERY_F_DATAFILL_DATA;
    private static final Function<ResultSet, DataFillData> ENTITY_READER_DATAFILL_DATA;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add(DataFillData data) {
        String SQL_ADD = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES ( ?, ?, ?, ?)", TABLE_NAME, ID, DFDID, LANGUAGE, DATA);
        this.jdbcTemplate.update(SQL_ADD, new Object[]{data.getId(), data.getDefinitionId(), data.getLanguage(), new String(data.getData(), StandardCharsets.UTF_8)});
    }

    public void modify(DataFillData data) {
        String SQL_MODIFY = String.format("UPDATE %s SET %s=? WHERE %s = ? and %s = ? ", TABLE_NAME, DATA, DFDID, LANGUAGE);
        this.jdbcTemplate.update(SQL_MODIFY, new Object[]{new String(data.getData(), StandardCharsets.UTF_8), data.getDefinitionId(), data.getLanguage()});
    }

    public DataFillData findByDefinition(String definitionId, String language) {
        String QUERY_BY_DEF_LAN = String.format("SELECT %s FROM %s WHERE %s = ? and %s = ?", QUERY_F_DATAFILL_DATA, TABLE_NAME, DFDID, LANGUAGE);
        return (DataFillData)this.jdbcTemplate.query(QUERY_BY_DEF_LAN, rs -> {
            if (rs.next()) {
                return ENTITY_READER_DATAFILL_DATA.apply(rs);
            }
            return null;
        }, new Object[]{definitionId, language});
    }

    public void deleteByDefinitionId(String definitionId) {
        String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, DFDID);
        this.jdbcTemplate.update(SQL_DELETE, new Object[]{definitionId});
    }

    public void batchDeleteByDefinitionIds(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        String SQL_DELETE_ = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, DFDID);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (String id : ids) {
            Object[] param = new Object[]{id};
            args.add(param);
        }
        this.jdbcTemplate.batchUpdate(SQL_DELETE_, args);
    }

    static {
        StringBuilder builder = new StringBuilder();
        QUERY_F_DATAFILL_DATA = builder.append(ID).append(",").append(DFDID).append(",").append(LANGUAGE).append(",").append(DATA).toString();
        ENTITY_READER_DATAFILL_DATA = rs -> {
            DataFillData data = new DataFillData();
            int index = 1;
            try {
                data.setId(rs.getString(index++));
                data.setDefinitionId(rs.getString(index++));
                data.setLanguage(rs.getString(index++));
                String dataStr = rs.getString(index++);
                if (StringUtils.hasText(dataStr)) {
                    data.setData(dataStr.getBytes(StandardCharsets.UTF_8));
                }
            }
            catch (SQLException e) {
                throw new RuntimeException("read ZBQueryGroup group error.", e);
            }
            return data;
        };
    }
}

