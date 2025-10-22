/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.RowCallbackHandler
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.datascheme.internal.dao.impl;

import com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class DataFieldDeployInfoDaoImpl
extends BaseDao {
    private final String updateSql = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=? and %s=? and %s=?", "NR_DATASCHEME_DEPLOY_INFO", "DS_DT_KEY", "DS_TABLE_NAME", "DS_FIELD_NAME", "DS_VERSION", "DS_UPDATE_TIME", "DS_DS_KEY", "DS_DF_KEY", "DS_CM_KEY");
    private final String deleteSql = String.format("DELETE FROM %s WHERE %s=? and %s=? and %s=?", "NR_DATASCHEME_DEPLOY_INFO", "DS_DS_KEY", "DS_DF_KEY", "DS_CM_KEY");
    private final String tableInfoSql = String.format("SELECT %s, %s FROM %s GROUP BY %s, %s ", "DS_SDT_KEY", "DS_TM_KEY", "NR_DATASCHEME_DEPLOY_INFO", "DS_SDT_KEY", "DS_TM_KEY");
    private final String deleteByTableModelSql = String.format("DELETE FROM %s WHERE %s=?", "NR_DATASCHEME_DEPLOY_INFO", "DS_TM_KEY");
    private final String deleteByDataFieldSql = String.format("DELETE FROM %s WHERE %s=?", "NR_DATASCHEME_DEPLOY_INFO", "DS_DF_KEY");
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Class<DataFieldDeployInfoDO> getClz() {
        return DataFieldDeployInfoDO.class;
    }

    public List<DataFieldDeployInfoDO> getAll() {
        return super.list(this.getClz());
    }

    public Map<String, String> getTableModelInfo() {
        final HashMap<String, String> tableModleInfoMap = new HashMap<String, String>();
        this.jdbcTemplate.query(this.tableInfoSql, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                tableModleInfoMap.put(rs.getString(2), rs.getString(1));
            }
        });
        return tableModleInfoMap;
    }

    public List<DataFieldDeployInfoDO> getByDataSchemeKey(String dataSchemeKey) {
        return super.list(new String[]{"DS_DS_KEY"}, (Object[])new String[]{dataSchemeKey}, this.getClz());
    }

    public List<DataFieldDeployInfoDO> getByDataTableKey(String dataTableKey) {
        return super.list(new String[]{"DS_DT_KEY"}, (Object[])new String[]{dataTableKey}, this.getClz());
    }

    public List<DataFieldDeployInfoDO> getByDataTableKeys(List<String> dataTableKeys) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("dataTableKeys", dataTableKeys);
        RowMapper<DataFieldDeployInfoDO> rowMapper = super.getRowMapper(this.getClz());
        String sql = this.selectSQL + " where " + "DS_DT_KEY" + " in (:dataTableKeys) ";
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    public List<DataFieldDeployInfoDO> getByDataTableName(String tableName) {
        return super.list(new String[]{"DS_TABLE_NAME"}, (Object[])new String[]{tableName}, this.getClz());
    }

    public List<DataFieldDeployInfoDO> getByTableModelKey(String tableModelKey) {
        return super.list(new String[]{"DS_TM_KEY"}, (Object[])new String[]{tableModelKey}, this.getClz());
    }

    public DataFieldDeployInfoDO getByColumnModelKey(String columnModelKey) {
        String whereSql = String.format(" %s=? ", "DS_CM_KEY");
        return super.getBy(whereSql, new String[]{columnModelKey}, this.getClz());
    }

    public List<DataFieldDeployInfoDO> getByDataFieldKeys(String[] dataFieldKeys) {
        if (null == dataFieldKeys || 0 == dataFieldKeys.length) {
            return Collections.emptyList();
        }
        StringBuilder whereSql = new StringBuilder();
        whereSql.append("DS_DF_KEY").append(" in (?");
        for (int i = 1; i < dataFieldKeys.length; ++i) {
            whereSql.append(",?");
        }
        whereSql.append(")");
        return super.list(whereSql.toString(), (Object[])dataFieldKeys, this.getClz());
    }

    public List<DataFieldDeployInfoDO> getByColumnModelKeys(String[] keys) {
        if (null == keys || 0 == keys.length) {
            return Collections.emptyList();
        }
        StringBuilder whereSql = new StringBuilder();
        whereSql.append("DS_CM_KEY").append(" in (?");
        for (int i = 1; i < keys.length; ++i) {
            whereSql.append(",?");
        }
        whereSql.append(")");
        return super.list(whereSql.toString(), (Object[])keys, this.getClz());
    }

    public List<String> getTableNames(String ... dataTableKeys) {
        if (null == dataTableKeys || 0 == dataTableKeys.length) {
            return Collections.emptyList();
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append("DS_TABLE_NAME").append(" FROM ").append("NR_DATASCHEME_DEPLOY_INFO").append(" INNER JOIN ").append("NR_DATASCHEME_FIELD").append(" ON ").append("DS_DF_KEY").append(" = ").append("DF_KEY").append(" INNER JOIN ").append("NR_DATASCHEME_TABLE").append(" ON ").append("DT_KEY").append(" = ").append("DF_DT_KEY").append(" AND ").append("DT_KEY").append(" IN (?");
        for (int i = 1; i < dataTableKeys.length; ++i) {
            sql.append(",?");
        }
        sql.append(") GROUP BY ").append("DS_TABLE_NAME");
        return super.queryForList(sql.toString(), dataTableKeys, String.class);
    }

    public List<String> getTableModelKeys(String dataSchemeKey) {
        if (!StringUtils.hasLength(dataSchemeKey)) {
            return Collections.emptyList();
        }
        String sql = String.format("SELECT %s FROM %s WHERE %s = ? GROUP BY %s ", "DS_TM_KEY", "NR_DATASCHEME_DEPLOY_INFO", "DS_DS_KEY", "DS_TM_KEY");
        return (List)this.jdbcTemplate.query(sql, pss -> pss.setString(1, dataSchemeKey), rs -> {
            ArrayList<String> result = new ArrayList<String>();
            while (rs.next()) {
                result.add(rs.getString(1));
            }
            return result;
        });
    }

    public void insert(DataFieldDeployInfoDO deployInfo) {
        if (null == deployInfo) {
            return;
        }
        super.insert(deployInfo);
    }

    public void insert(DataFieldDeployInfoDO[] deployInfos) {
        if (null == deployInfos || 0 == deployInfos.length) {
            return;
        }
        super.insert(deployInfos);
    }

    public void update(DataFieldDeployInfoDO deployInfo) {
        if (null == deployInfo) {
            return;
        }
        this.jdbcTemplate.update(this.updateSql, new Object[]{deployInfo.getDataTableKey(), deployInfo.getTableName(), deployInfo.getFieldName(), deployInfo.getVersion(), Timestamp.from(Instant.now()), deployInfo.getDataSchemeKey(), deployInfo.getDataFieldKey(), deployInfo.getColumnModelKey()});
    }

    public void update(DataFieldDeployInfoDO[] deployInfos) {
        if (null == deployInfos || 0 == deployInfos.length) {
            return;
        }
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>(deployInfos.length);
        for (int i = 0; i < deployInfos.length; ++i) {
            batchArgs.add(new Object[]{deployInfos[i].getDataTableKey(), deployInfos[i].getTableName(), deployInfos[i].getFieldName(), deployInfos[i].getVersion(), Timestamp.from(Instant.now()), deployInfos[i].getDataSchemeKey(), deployInfos[i].getDataFieldKey(), deployInfos[i].getColumnModelKey()});
        }
        this.jdbcTemplate.batchUpdate(this.updateSql, batchArgs);
    }

    public void delete(DataFieldDeployInfoDO deployInfo) {
        if (null == deployInfo) {
            return;
        }
        this.jdbcTemplate.update(this.deleteSql, new Object[]{deployInfo.getDataSchemeKey(), deployInfo.getDataFieldKey(), deployInfo.getColumnModelKey()});
    }

    public void delete(DataFieldDeployInfoDO[] deployInfos) {
        if (null == deployInfos || 0 == deployInfos.length) {
            return;
        }
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>(deployInfos.length);
        for (int i = 0; i < deployInfos.length; ++i) {
            batchArgs.add(new Object[]{deployInfos[i].getDataSchemeKey(), deployInfos[i].getDataFieldKey(), deployInfos[i].getColumnModelKey()});
        }
        this.jdbcTemplate.batchUpdate(this.deleteSql, batchArgs);
    }

    public void deleteByTableModel(String tableModelKey) {
        this.jdbcTemplate.update(this.deleteByTableModelSql, new Object[]{tableModelKey});
    }

    public void deleteByDataFields(String[] dataFieldKeys) {
        if (null == dataFieldKeys || 0 == dataFieldKeys.length) {
            return;
        }
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>(dataFieldKeys.length);
        for (String dataFieldKey : dataFieldKeys) {
            batchArgs.add(new Object[]{dataFieldKey});
        }
        this.jdbcTemplate.batchUpdate(this.deleteByDataFieldSql, batchArgs);
    }
}

