/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.analysisreport.dao;

import com.jiuqi.nr.analysisreport.dao.impl.AnalysisReportDao;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportDefine;
import com.jiuqi.nr.analysisreport.internal.AnalysisReportDefineImpl;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class AnalysisReportDefineDao
extends AnalysisReportDao {
    @Autowired
    private NamedParameterJdbcTemplate npJdbcTemplate;
    private static String COLUMN_AT_KEY = "key";
    private static String COLUMN_AG_KEY = "groupKey";
    private static String COLUMN_AT_TITLE = "title";
    private static String COLUMN_AT_DESCRIPTION = "description";
    private Class<AnalysisReportDefineImpl> implClass = AnalysisReportDefineImpl.class;

    @Override
    public Class<?> getClz() {
        return this.implClass;
    }

    public List<AnalysisReportDefine> getList() throws Exception {
        return this.list(this.implClass);
    }

    public List<AnalysisReportDefine> getListByGroupKey(String groupKey) throws Exception {
        return this.list(new String[]{COLUMN_AG_KEY}, new Object[]{groupKey}, this.implClass);
    }

    public AnalysisReportDefine getListByKey(String key) throws Exception {
        List<AnalysisReportDefineImpl> list = this.list(new String[]{COLUMN_AT_KEY}, new Object[]{key}, this.implClass);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public int insertModel(AnalysisReportDefine analysisReportDefine) throws Exception {
        return this.insert(analysisReportDefine);
    }

    public int updateModel(AnalysisReportDefine analysisReportDefine) throws Exception {
        return this.update(analysisReportDefine);
    }

    public int deleteModel(String key) throws Exception {
        return this.deleteBy(new String[]{COLUMN_AT_KEY}, new Object[]{key});
    }

    public List<AnalysisReportDefine> fuzzyQuery(String keywords) {
        String sql = "select %s,%s,%s from %s where %s like ? ";
        sql = String.format(sql, "at_key", "ag_key", "at_title", "sys_analytemplate", "at_title");
        return this.jdbcTemplate.query(sql, ps -> ps.setString(1, "%" + keywords + "%"), this.rowMapper());
    }

    public RowMapper<AnalysisReportDefine> rowMapper() {
        return (rs, rowNum) -> {
            AnalysisReportDefineImpl reportDO = new AnalysisReportDefineImpl();
            reportDO.setKey(rs.getString("at_key"));
            reportDO.setGroupKey(rs.getString("ag_key"));
            reportDO.setTitle(rs.getString("at_title"));
            return reportDO;
        };
    }

    public List<AnalysisReportDefineImpl> getList(List<String> atKeys) {
        String sql = "SELECT AT_KEY,AT_TITLE FROM SYS_ANALYTEMPLATE WHERE AT_KEY IN (:ids)";
        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("ids", atKeys);
        return this.npJdbcTemplate.queryForList(sql, (SqlParameterSource)parameter, AnalysisReportDefineImpl.class);
    }

    public void updateTemplateLastModifiedTime(String key, Date updateDataTime) {
        String sql = "update %s set %s=?  where %s=?";
        sql = String.format(sql, "sys_analytemplate", "at_updatetime", "at_key");
        this.jdbcTemplate.update(sql, new Object[]{updateDataTime, key});
    }

    public Date getCatalogUpdateTime(String key) {
        String sql = "select %s from %s where %s=?";
        sql = String.format(sql, "AT_CATALOG_UPDATETIME", "sys_analytemplate", "at_key");
        Object[] args = new Object[]{key};
        int[] argTypes = new int[]{12};
        return (Date)this.jdbcTemplate.queryForObject(sql, args, argTypes, Date.class);
    }
}

