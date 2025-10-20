/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.impl.RunTimeBigDataTable;
import com.jiuqi.nr.definition.util.DefinitionUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class RunTimeBigDataTableDao
extends BaseDao {
    private static String ATTR_KEY = "key";
    private static String ATTR_CODE = "code";
    private static String ATTR_lAND = "lang";
    public static int DEFAULT_lAND = 1;
    public static String DEFAULT_VERSION = "1.0";
    private Class<RunTimeBigDataTable> implClass = RunTimeBigDataTable.class;
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public RunTimeBigDataTable queryigDataDefine(String Key2, String code, int Lang) {
        List defines = this.list(new String[]{ATTR_KEY, ATTR_CODE, ATTR_lAND}, new Object[]{Key2, code, Lang}, this.implClass);
        if (null != defines && defines.size() > 0) {
            return (RunTimeBigDataTable)defines.get(0);
        }
        return null;
    }

    public RunTimeBigDataTable queryigDataDefine(String key, String code) {
        return this.queryigDataDefine(key, code, DEFAULT_lAND);
    }

    public List<RunTimeBigDataTable> queryigDataDefine(String code) {
        return this.queryigDataDefine(code, DEFAULT_lAND);
    }

    public List<RunTimeBigDataTable> queryigDataDefine(String code, int Lang) {
        return this.list(new String[]{ATTR_CODE, ATTR_lAND}, new Object[]{code, Lang}, this.implClass);
    }

    public void deleteBigData(String key, String code, int lang) throws Exception {
        this.deleteBy(new String[]{ATTR_KEY, ATTR_CODE, ATTR_lAND}, new Object[]{key, code, lang});
    }

    public void deleteBigData(String Key2, String code) throws Exception {
        this.deleteBy(new String[]{ATTR_KEY, ATTR_CODE, ATTR_lAND}, new Object[]{Key2, code, DEFAULT_lAND});
    }

    public void updateData(RunTimeBigDataTable data) throws Exception {
        this.update(data, new String[]{ATTR_KEY, ATTR_CODE, ATTR_lAND}, new Object[]{data.getKey(), data.getCode(), data.getLang()});
    }

    public List<RunTimeBigDataTable> queryBigDataDefine(List<String> keys, String code) {
        return DefinitionUtils.limitExe(keys, subKeys -> this.listByKeys((List<String>)subKeys, code, DEFAULT_lAND));
    }

    public List<RunTimeBigDataTable> queryBigDataDefine(List<String> keys) {
        return DefinitionUtils.limitExe(keys, subKeys -> this.listByKeys((List<String>)subKeys, null, DEFAULT_lAND));
    }

    private List<RunTimeBigDataTable> listByKeys(List<String> keys, String code, int lang) {
        String sql;
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        if (null == code) {
            sql = "SELECT D.* FROM NR_PARAM_BIGDATATABLE D WHERE D.BD_LANG = :lang AND BD_KEY IN (:keys) ";
        } else {
            sql = "SELECT D.* FROM NR_PARAM_BIGDATATABLE D WHERE D.BD_CODE = :code AND D.BD_LANG = :lang AND BD_KEY IN (:keys) ";
            sqlParameterSource.addValue("code", (Object)code);
        }
        sqlParameterSource.addValue("keys", keys);
        sqlParameterSource.addValue("lang", (Object)lang);
        RowMapper rowMapper = (rs, rowNum) -> {
            RunTimeBigDataTable bigData = new RunTimeBigDataTable();
            this.readRecord(rs, bigData);
            return bigData;
        };
        this.buildMethod();
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }

    public List<RunTimeBigDataTable> queryLinkAttr(String formSchemeKey) {
        String sql = "SELECT T.* FROM NR_PARAM_BIGDATATABLE T LEFT JOIN NR_PARAM_DATALINK L ON T.BD_KEY = L.DL_KEY LEFT JOIN NR_PARAM_DATAREGION R ON L.DL_REGION_KEY = R.DR_KEY LEFT JOIN NR_PARAM_FORM F ON R.DR_FORM_KEY = F.FM_KEY WHERE T.BD_CODE = ? AND T.BD_LANG = ? AND F.FM_FORMSCHEME = ?";
        RowMapper rowMapper = (rs, rowNum) -> {
            RunTimeBigDataTable bigData = new RunTimeBigDataTable();
            this.readRecord(rs, bigData);
            return bigData;
        };
        this.buildMethod();
        return this.jdbcTemplate.query(sql, rowMapper, new Object[]{"ATTACHMENT", DEFAULT_lAND, formSchemeKey});
    }

    public List<RunTimeBigDataTable> queryLinkAttr(List<String> regionKeys, String code) {
        return DefinitionUtils.limitExe(regionKeys, subKeys -> this.queryLinkAttr((List<String>)subKeys, code, DEFAULT_lAND));
    }

    public List<RunTimeBigDataTable> queryLinkAttr(List<String> regionKeys, String code, int lang) {
        String sql = "SELECT D.* FROM NR_PARAM_BIGDATATABLE D INNER JOIN NR_PARAM_DATALINK L ON D.BD_KEY = L.DL_KEY WHERE D.BD_CODE=:code AND D.BD_LANG=:lang AND L.DL_REGION_KEY IN (:regionKeys) ";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("regionKeys", regionKeys);
        sqlParameterSource.addValue("code", (Object)code);
        sqlParameterSource.addValue("lang", (Object)lang);
        RowMapper rowMapper = (rs, rowNum) -> {
            RunTimeBigDataTable bigData = new RunTimeBigDataTable();
            this.readRecord(rs, bigData);
            return bigData;
        };
        this.buildMethod();
        return this.namedParameterJdbcTemplate.query(sql, (SqlParameterSource)sqlParameterSource, rowMapper);
    }
}

