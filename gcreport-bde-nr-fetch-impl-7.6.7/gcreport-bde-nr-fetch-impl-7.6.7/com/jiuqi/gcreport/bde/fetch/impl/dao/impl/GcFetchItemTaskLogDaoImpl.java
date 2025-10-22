/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.bde.fetch.impl.dao.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.bde.fetch.impl.common.ExecuteStateEnum;
import com.jiuqi.gcreport.bde.fetch.impl.dao.GcFetchItemTaskLogDao;
import com.jiuqi.gcreport.bde.fetch.impl.entity.GcFetchItemTaskLogEO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GcFetchItemTaskLogDaoImpl
implements GcFetchItemTaskLogDao {
    private static final String FILED_STRING = " id,fetchTaskId,formId,regionId,executeState,process,processTime,resultContent,fetchstatus ";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public GcFetchItemTaskLogEO getItemTask(String fetchTaskId, String formId, String regionId) {
        StringBuffer sql = new StringBuffer();
        sql.append("  select ").append(FILED_STRING).append("\n");
        sql.append("    from ").append("GC_FETCH_ITEMTASKLOG").append(" T \n");
        sql.append("   where T.FETCHTASKID = ? and T.FORMID = ?  and T.REGIONID = ?\n");
        return (GcFetchItemTaskLogEO)this.jdbcTemplate.query(sql.toString(), rs -> {
            if (rs.next()) {
                return this.getGcFetchItemTaskLogEO(rs);
            }
            return null;
        }, new Object[]{fetchTaskId, formId, regionId});
    }

    private GcFetchItemTaskLogEO getGcFetchItemTaskLogEO(ResultSet rs) throws SQLException {
        GcFetchItemTaskLogEO gcFetchItemTaskLogEO = new GcFetchItemTaskLogEO();
        gcFetchItemTaskLogEO.setId(rs.getString(1));
        gcFetchItemTaskLogEO.setFetchTaskId(rs.getString(2));
        gcFetchItemTaskLogEO.setFormId(rs.getString(3));
        gcFetchItemTaskLogEO.setRegionId(rs.getString(4));
        gcFetchItemTaskLogEO.setExecuteState(rs.getString(5));
        gcFetchItemTaskLogEO.setProcess(rs.getDouble(6));
        gcFetchItemTaskLogEO.setProcessTime(rs.getDate(7));
        gcFetchItemTaskLogEO.setResultContent(rs.getString(8));
        gcFetchItemTaskLogEO.setFetchStatus(rs.getInt(9));
        return gcFetchItemTaskLogEO;
    }

    @Override
    public Double queryProcess(String fetchTaskId) {
        StringBuffer sql = new StringBuffer();
        sql.append("  select sum(T.PROCESS) as PROCESS \n");
        sql.append("    from ").append("GC_FETCH_ITEMTASKLOG").append(" T \n");
        sql.append("   where T.FETCHTASKID = ? \n");
        return (Double)this.jdbcTemplate.query(sql.toString(), rs -> {
            if (!rs.next()) {
                return 0.0;
            }
            return rs.getDouble(1);
        }, new Object[]{fetchTaskId});
    }

    @Override
    public String getLastFetchInfo(String fetchTaskId) {
        StringBuffer sql = new StringBuffer();
        sql.append("  select T.RESULTCONTENT as RESULTCONTENT \n");
        sql.append("    from ").append("GC_FETCH_ITEMTASKLOG").append(" T \n");
        sql.append("   where T.FETCHTASKID = ? \n");
        sql.append("   order by T.PROCESSTIME desc \n");
        return (String)this.jdbcTemplate.query(sql.toString(), rs -> {
            if (!rs.next()) {
                return null;
            }
            return rs.getString(1);
        }, new Object[]{fetchTaskId});
    }

    @Override
    public List<GcFetchItemTaskLogEO> getErrorItemTaskList(String fetchTaskId) {
        StringBuffer sql = new StringBuffer();
        sql.append("  select ").append(FILED_STRING).append(" \n");
        sql.append("    from ").append("GC_FETCH_ITEMTASKLOG").append(" T \n");
        sql.append("   where T.FETCHTASKID = ? \n");
        sql.append("   and T.executeState = ? \n");
        return this.jdbcTemplate.query(sql.toString(), (rs, row) -> this.getGcFetchItemTaskLogEO(rs), new Object[]{fetchTaskId, ExecuteStateEnum.ERROR.getCode()});
    }

    @Override
    public void update(GcFetchItemTaskLogEO itemTaskLog) {
        StringBuffer sql = new StringBuffer();
        sql.append(" update ").append("GC_FETCH_ITEMTASKLOG").append("\n");
        sql.append("    set fetchTaskId=?,formId=?,executeState=?,process=?,processTime=?,resultContent=?,fetchstatus=? \n");
        sql.append("  where id = ? \n");
        this.jdbcTemplate.update(sql.toString(), new Object[]{itemTaskLog.getFetchTaskId(), itemTaskLog.getFormId(), itemTaskLog.getExecuteState(), itemTaskLog.getProcess(), itemTaskLog.getProcessTime(), itemTaskLog.getResultContent(), itemTaskLog.getFetchStatus(), itemTaskLog.getId()});
    }

    @Override
    public void saveAll(List<GcFetchItemTaskLogEO> itemTaskLogList) {
        StringBuffer sql = new StringBuffer();
        sql.append(" insert into  ").append("GC_FETCH_ITEMTASKLOG").append("\n");
        sql.append("(").append(FILED_STRING).append(")").append("\n");
        sql.append("values(?,?,?,?,?,?,?,?,?)");
        ArrayList<Object[]> argsList = new ArrayList<Object[]>();
        for (GcFetchItemTaskLogEO itemTaskLog : itemTaskLogList) {
            if (StringUtils.isEmpty((String)itemTaskLog.getId())) {
                itemTaskLog.setId(UUIDUtils.newHalfGUIDStr());
            }
            Object[] args = new Object[]{itemTaskLog.getId(), itemTaskLog.getFetchTaskId(), itemTaskLog.getFormId(), itemTaskLog.getRegionId(), itemTaskLog.getExecuteState(), itemTaskLog.getProcess(), itemTaskLog.getProcessTime(), itemTaskLog.getResultContent(), itemTaskLog.getFetchStatus()};
            argsList.add(args);
        }
        this.jdbcTemplate.batchUpdate(sql.toString(), argsList);
    }

    @Override
    public void updateErrorStateByFetchId(String fetchTaskId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" update ").append("GC_FETCH_ITEMTASKLOG").append("\n");
        sql.append("    set executeState='ERROR',process=1,resultContent='\u53d6\u6570\u8d85\u65f6',processTime=? \n");
        sql.append("  where fetchTaskId = ? and process=0 \n");
        this.jdbcTemplate.update(sql.toString(), new Object[]{new Date(), fetchTaskId});
    }

    @Override
    public int updateFetchStatus(String fetchTaskId, List<String> formKeys) {
        Assert.isNotEmpty(formKeys);
        String sql = "UPDATE GC_FETCH_ITEMTASKLOG SET FETCHSTATUS = 1 WHERE 1=1 AND FETCHTASKID = ? AND %1$s AND FETCHSTATUS = 0 ";
        return this.jdbcTemplate.update(String.format(sql, SqlBuildUtil.getStrInCondi((String)"FORMID", formKeys)), new Object[]{fetchTaskId});
    }
}

