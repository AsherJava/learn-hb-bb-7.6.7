/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.analysisreport.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportGroupDefine;
import com.jiuqi.nr.analysisreport.internal.AnalysisReportGroupDefineImpl;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class AnalysisReportGroupDefineDao
extends BaseDao {
    private static String COLUMU_AG_KEY = "key";
    private static String COLUMU_AG_TITLE = "title";
    private static String COLUMU_AG_DESCRIPTION = "description";
    private static String COLUMU_AG_ORDER = "order";
    private static String COLUMU_AG_PARENT = "parentgroup";
    private Class<AnalysisReportGroupDefineImpl> implClass = AnalysisReportGroupDefineImpl.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public List<AnalysisReportGroupDefine> getGroupList() throws Exception {
        return this.list(this.implClass);
    }

    public AnalysisReportGroupDefine getGroupByKey(String key) throws Exception {
        List list = this.list(new String[]{COLUMU_AG_KEY}, new Object[]{key}, this.implClass);
        if (null != list && list.size() > 0) {
            return (AnalysisReportGroupDefine)list.get(0);
        }
        return null;
    }

    public List<AnalysisReportGroupDefine> getGroupByParent(String parent) throws Exception {
        List list = this.list(new String[]{COLUMU_AG_PARENT}, new Object[]{parent}, this.implClass);
        return list;
    }

    public int insertGroup(AnalysisReportGroupDefine analysisReportGroupDefine) throws Exception {
        return this.insert(analysisReportGroupDefine);
    }

    public int updateGroup(AnalysisReportGroupDefine analysisReportGroupDefine) throws Exception {
        return this.update(analysisReportGroupDefine);
    }

    public int deleteGroup(String key) throws Exception {
        return this.deleteBy(new String[]{COLUMU_AG_KEY}, new Object[]{key});
    }

    public List<AnalysisReportGroupDefine> fuzzyQuery(String keywords) {
        String sql = "select %s,%s,%s from %s where %s like ? ";
        sql = String.format(sql, "ag_key", "ag_parent", "ag_title", "sys_analytemplategroup", "ag_title");
        return this.jdbcTemplate.query(sql, ps -> ps.setString(1, "%" + keywords + "%"), this.rowMapper());
    }

    public RowMapper<AnalysisReportGroupDefine> rowMapper() {
        return (rs, rowNum) -> {
            AnalysisReportGroupDefineImpl groupDefine = new AnalysisReportGroupDefineImpl();
            groupDefine.setKey(rs.getString("ag_key"));
            groupDefine.setParentgroup(rs.getString("ag_parent"));
            groupDefine.setTitle(rs.getString("ag_title"));
            return groupDefine;
        };
    }
}

