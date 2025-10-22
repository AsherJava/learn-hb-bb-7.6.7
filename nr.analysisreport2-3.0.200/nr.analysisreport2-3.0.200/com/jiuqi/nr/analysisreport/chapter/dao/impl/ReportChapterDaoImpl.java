/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.BeanParaException
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.internal.db.BaseDao
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.analysisreport.chapter.dao.impl;

import com.jiuqi.np.definition.exception.BeanParaException;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.analysisreport.chapter.bean.ReportChapterDefine;
import com.jiuqi.nr.analysisreport.chapter.dao.IChapterDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class ReportChapterDaoImpl
extends BaseDao
implements IChapterDao {
    @Autowired
    private NamedParameterJdbcTemplate npJdbcTemplate;
    private Class<ReportChapterDefine> implClass = ReportChapterDefine.class;
    public static final String IDS = "ids";

    public Class<?> getClz() {
        return this.implClass;
    }

    @Override
    public int inseartChapter(ReportChapterDefine reportChapterDefine) throws Exception {
        return this.insert(reportChapterDefine);
    }

    @Override
    public boolean updateChapter(ReportChapterDefine reportChapterDefine) throws Exception {
        return this.update(reportChapterDefine) == 1;
    }

    @Override
    public ReportChapterDefine getBykey(String key) {
        return (ReportChapterDefine)this.getByKey(key, this.implClass);
    }

    @Override
    public int[] batchDeleteByKeys(String[] keys) throws DBParaException {
        return this.delete(keys);
    }

    @Override
    public List<ReportChapterDefine> getChapterByModuleId(String moduleId) {
        return super.list(new String[]{"arcAtKey"}, new Object[]{moduleId}, ReportChapterDefine.class);
    }

    @Override
    public void delete(String key) throws DBParaException {
        super.delete((Object)key);
    }

    @Override
    public int deleteByFiled(String[] fieldNames, Object[] args) throws BeanParaException, DBParaException {
        return super.deleteBy(fieldNames, args);
    }

    @Override
    public Integer checkChapterTitleRepeat(String moduleId, String parentId, String chaptername) {
        String whereSql = "%s='%s' AND %s='%s'";
        whereSql = String.format(whereSql, "ARC_AT_KEY", moduleId, "ARC_NAME", chaptername);
        return super.count(whereSql);
    }

    @Override
    public List<ReportChapterDefine> list(List<String> arcKeys) throws Exception {
        String sql = "SELECT  %s,%s,%s,%s,%s,%s FROM %s WHERE %s IN (:ids)";
        sql = String.format(sql, "ARC_KEY", "ARC_AT_KEY", "ARC_NAME", "ARC_PARENT", "ARC_ORDER", "ARC_TYPE_SPEED", "NR_B_AR_CHAPTER", "ARC_KEY");
        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue(IDS, arcKeys);
        return this.npJdbcTemplate.query(sql, (SqlParameterSource)parameter, (RowMapper)new BeanPropertyRowMapper(ReportChapterDefine.class));
    }

    @Override
    public int clearTemplateArcData(String templateKey) {
        String sql = "UPDATE %s SET %s = NULL, %s = NULL WHERE %s = ?";
        sql = String.format(sql, "NR_B_AR_CHAPTER", "ARC_DATA", "ARC_CATALOG", "ARC_AT_KEY");
        return this.jdbcTemplate.update(sql, new Object[]{templateKey});
    }

    @Override
    public List<ReportChapterDefine> listOnlyArcNameAndArcKey(String key) {
        String sql = "select %s,%s,%s from %s where %s=? order by %s ";
        sql = String.format(sql, "ARC_KEY", "ARC_NAME", "ARC_TYPE_SPEED", "NR_B_AR_CHAPTER", "ARC_AT_KEY", "ARC_ORDER");
        return this.jdbcTemplate.query(sql, new Object[]{key}, (RowMapper)new RowMapper<ReportChapterDefine>(){

            public ReportChapterDefine mapRow(ResultSet rs, int rowNum) throws SQLException {
                ReportChapterDefine reportChapterDefine = new ReportChapterDefine();
                reportChapterDefine.setArcKey(rs.getString("ARC_KEY"));
                reportChapterDefine.setArcName(rs.getString("ARC_NAME"));
                Object object = rs.getObject("ARC_TYPE_SPEED");
                reportChapterDefine.setTypeSpeed(object == null ? null : Integer.valueOf(object.toString()));
                return reportChapterDefine;
            }
        });
    }

    @Override
    public boolean checkGenCatalogCompleted(String templateKey) {
        String sql = "SELECT COUNT(1) FROM NR_B_AR_CHAPTER WHERE ARC_KEY=? AND ARC_CATALOG_UPDATETIME!= ARC_UPDATETIME";
        return (Integer)super.queryForObject(sql, new Object[]{templateKey}, Integer.TYPE) == 0;
    }

    @Override
    public void batchInsert(ReportChapterDefine[] reportChapterDefines) throws DBParaException {
        super.insert((Object[])reportChapterDefines);
    }
}

