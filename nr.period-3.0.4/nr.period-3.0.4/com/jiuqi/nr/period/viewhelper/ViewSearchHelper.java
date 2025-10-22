/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.period.viewhelper;

import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.viewhelper.define.GradeViewDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ViewSearchHelper {
    private static final Logger logger = LoggerFactory.getLogger(ViewSearchHelper.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PeriodEngineService periodEngineService;
    private static final String VIEW_TABLE = "SYS_ENTITYVIEWDEFINE";
    private static final String VIEW_KEY = "EV_KEY";
    private static final String VIEW_ENTITY_KEY = "EV_ENTITY_ID";
    private static final String VIEW_TABLE_KEY = "EV_TABLE_KEY";
    private static final String SELECT_SQL = "SELECT * ";

    public GradeViewDefine searchByKey(String key) throws Exception {
        StringBuffer sql = this.initSql();
        sql.append(" WHERE EV_KEY = '" + key + "'");
        List queryForList = this.jdbcTemplate.queryForList(sql.toString());
        List<GradeViewDefine> list = this.transData(queryForList);
        return list.size() == 0 ? null : list.get(0);
    }

    public List<GradeViewDefine> searchByEntityKey(String key) throws Exception {
        StringBuffer sql = this.initSql();
        sql.append(" WHERE EV_ENTITY_ID = '" + key + "'");
        List queryForList = this.jdbcTemplate.queryForList(sql.toString());
        return this.transData(queryForList);
    }

    public List<GradeViewDefine> searchByTableKey(String key) throws Exception {
        StringBuffer sql = this.initSql();
        sql.append(" WHERE EV_TABLE_KEY = '" + key + "'");
        List queryForList = this.jdbcTemplate.queryForList(sql.toString());
        return this.transData(queryForList);
    }

    private StringBuffer initSql() {
        StringBuffer sql = new StringBuffer(SELECT_SQL);
        sql.append(" FROM ").append(VIEW_TABLE);
        return sql;
    }

    private List<GradeViewDefine> transData(List<Map<String, Object>> queryForList) throws Exception {
        ArrayList<GradeViewDefine> list = new ArrayList<GradeViewDefine>();
        for (Map<String, Object> dataMap : queryForList) {
            String ev_key = dataMap.get(VIEW_KEY).toString();
            String entity_key = null != dataMap.get(VIEW_ENTITY_KEY) ? dataMap.get(VIEW_ENTITY_KEY).toString() : "";
            String table_key = null != dataMap.get(VIEW_TABLE_KEY) ? dataMap.get(VIEW_TABLE_KEY).toString() : "";
            list.add(new GradeViewDefine(ev_key, entity_key, table_key));
        }
        return list;
    }

    public boolean isPeriodView(String viewKey) {
        GradeViewDefine gradeViewDefine = null;
        try {
            gradeViewDefine = this.searchByKey(viewKey);
            if (null != gradeViewDefine && this.periodEngineService.getPeriodAdapter().isPeriodEntity(gradeViewDefine.getEntityKey())) {
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void insertRunTimeView(GradeViewDefine gradeViewDefine) throws Exception {
        String insertSql = "INSERT INTO SYS_ENTITYVIEWDEFINE(EV_KEY,EV_ENTITY_ID,EV_TABLE_KEY) VALUES( '%s' , '%s' , '%s' )";
        this.jdbcTemplate.update(String.format(insertSql, gradeViewDefine.getKey(), gradeViewDefine.getEntityKey(), gradeViewDefine.getTableKey()));
        String insertSqlDes = "INSERT INTO DES_SYS_ENTITYVIEWDEFINE(EV_KEY,EV_ENTITY_ID,EV_TABLE_KEY) VALUES( '%s' , '%s' , '%s' )";
        this.jdbcTemplate.update(String.format(insertSqlDes, gradeViewDefine.getKey(), gradeViewDefine.getEntityKey(), gradeViewDefine.getTableKey()));
    }

    public void updateRunTimeView(GradeViewDefine gradeViewDefine) throws Exception {
        String insertSql = "UPDATE SYS_ENTITYVIEWDEFINE SET EV_ENTITY_ID= '%s', EV_TABLE_KEY= '%s' WHERE EV_KEY= '%s'";
        this.jdbcTemplate.update(String.format(insertSql, gradeViewDefine.getEntityKey(), gradeViewDefine.getTableKey(), gradeViewDefine.getKey()));
        String insertSqlDes = "UPDATE DES_SYS_ENTITYVIEWDEFINE SET EV_ENTITY_ID= '%s', EV_TABLE_KEY= '%s' WHERE EV_KEY= '%s'";
        this.jdbcTemplate.update(String.format(insertSqlDes, gradeViewDefine.getEntityKey(), gradeViewDefine.getTableKey(), gradeViewDefine.getKey()));
    }
}

