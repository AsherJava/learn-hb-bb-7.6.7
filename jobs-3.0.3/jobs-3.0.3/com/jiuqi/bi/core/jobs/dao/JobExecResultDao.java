/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Html
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.core.jobs.dao;

import com.jiuqi.bi.core.jobs.bean.JobExecResultBean;
import com.jiuqi.bi.util.Html;
import com.jiuqi.bi.util.StringUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobExecResultDao {
    private static final String T_NAME = "BI_JOBS_EXEC_RESULT";
    private static final String F_GUID = "BJER_GUID";
    private static final String F_INSTANCEID = "BJI_INSTANCEID";
    private static final String F_BUCKET = "BJER_BUCKET";
    private static final String F_FILENAME = "BJER_FILENAME";
    private static final String F_EXT_NAME = "BJER_EXT_NAME";
    private static final String F_USERGUID = "BJER_USERGUID";
    private static final String F_BYTE_SIZE = "BJER_BYTE_SIZE";
    private static final String F_EXPIRE_TIME = "BJER_EXPIRE_TIME";
    private static final String F_DESCRIPTION = "BJER_DESCRIPTION";
    private static final String SQL_INSERT = " INSERT INTO BI_JOBS_EXEC_RESULT ( BJER_GUID , BJI_INSTANCEID , BJER_BUCKET , BJER_FILENAME , BJER_EXT_NAME , BJER_USERGUID , BJER_BYTE_SIZE , BJER_EXPIRE_TIME , BJER_DESCRIPTION ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
    private static final String SQL_SELECT = " SELECT BJER_GUID , BJI_INSTANCEID , BJER_BUCKET , BJER_FILENAME , BJER_EXT_NAME , BJER_USERGUID , BJER_BYTE_SIZE , BJER_EXPIRE_TIME , BJER_DESCRIPTION FROM BI_JOBS_EXEC_RESULT";
    private static final String SQL_SELECT_BY_GUID = " SELECT BJER_GUID , BJI_INSTANCEID , BJER_BUCKET , BJER_FILENAME , BJER_EXT_NAME , BJER_USERGUID , BJER_BYTE_SIZE , BJER_EXPIRE_TIME , BJER_DESCRIPTION FROM BI_JOBS_EXEC_RESULT WHERE BJER_GUID = ? ";
    private static final String SQL_SELECT_BY_INSTANCE_IDS = " SELECT BJER_GUID , BJI_INSTANCEID , BJER_BUCKET , BJER_FILENAME , BJER_EXT_NAME , BJER_USERGUID , BJER_BYTE_SIZE , BJER_EXPIRE_TIME , BJER_DESCRIPTION FROM BI_JOBS_EXEC_RESULT WHERE BJI_INSTANCEID IN ( ? ) ";
    private static final String SQL_DELETE_BY_INSTANCE_IDS = " DELETE FROM BI_JOBS_EXEC_RESULT WHERE BJI_INSTANCEID IN ( ? ) ";
    private static final String SQL_DELETE_NO_EXIST_IN_INSTANCE_TABLE = " DELETE FROM BI_JOBS_EXEC_RESULT WHERE BJER_GUID IN ( SELECT BJER_GUID FROM ( SELECT R.BJER_GUID,I.BJI_INSTANCEID SOURCE_ID FROM BI_JOBS_EXEC_RESULT R LEFT JOIN BI_JOBS_INSTANCES I ON R.BJI_INSTANCEID = I.BJI_INSTANCEID WHERE I.BJI_INSTANCEID IS NULL ) T ) ";

    public static void insert(Connection conn, List<JobExecResultBean> beans) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SQL_INSERT);){
            for (JobExecResultBean bean : beans) {
                ps.setString(1, bean.getGuid());
                ps.setString(2, bean.getInstanceId());
                ps.setString(3, bean.getBucket());
                ps.setString(4, bean.getFileName());
                ps.setString(5, bean.getExtName());
                ps.setString(6, bean.getUserGuid());
                ps.setLong(7, bean.getByteSize());
                ps.setLong(8, bean.getExpireTime());
                ps.setString(9, bean.getDescription());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public static JobExecResultBean getResultByGuid(Connection conn, String resultGuid) throws SQLException {
        JobExecResultBean resultBean = null;
        if (StringUtils.isEmpty((String)resultGuid)) {
            return resultBean;
        }
        try (PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_GUID);){
            ps.setString(1, resultGuid);
            try (ResultSet rs = ps.executeQuery();){
                while (rs.next()) {
                    resultBean = JobExecResultDao.fillBean(rs);
                }
            }
        }
        return resultBean;
    }

    public static List<JobExecResultBean> getResultListByInstanceIds(Connection conn, List<String> instanceIds) throws SQLException {
        ArrayList<JobExecResultBean> resultBeans = new ArrayList<JobExecResultBean>();
        Map<String, List<JobExecResultBean>> resultMap = JobExecResultDao.getResultByInstanceIds(conn, instanceIds);
        for (Map.Entry<String, List<JobExecResultBean>> entry : resultMap.entrySet()) {
            resultBeans.addAll((Collection<JobExecResultBean>)entry.getValue());
        }
        return resultBeans;
    }

    public static Map<String, List<JobExecResultBean>> getResultByInstanceIds(Connection conn, List<String> instanceIds) throws SQLException {
        HashMap<String, List<JobExecResultBean>> resultMap = new HashMap<String, List<JobExecResultBean>>();
        if (instanceIds == null || instanceIds.isEmpty()) {
            return resultMap;
        }
        StringBuilder idBuilder = new StringBuilder();
        for (String instanceId : instanceIds) {
            instanceId = Html.cleanName((String)instanceId, (char[])new char[0]);
            if (idBuilder.length() > 0) {
                idBuilder.append(",");
            }
            idBuilder.append("'").append(instanceId).append("'");
            resultMap.put(instanceId, new ArrayList());
        }
        String sql = String.format(SQL_SELECT_BY_INSTANCE_IDS.replace("?", "%s"), idBuilder.toString());
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();){
            while (rs.next()) {
                JobExecResultBean execResultBean = JobExecResultDao.fillBean(rs);
                ((List)resultMap.get(execResultBean.getInstanceId())).add(execResultBean);
            }
        }
        return resultMap;
    }

    public static void deleteByInstanceIds(Connection conn, List<String> instanceIds) throws SQLException {
        if (instanceIds == null || instanceIds.isEmpty()) {
            return;
        }
        StringBuilder idBuilder = new StringBuilder();
        for (String instanceId : instanceIds) {
            if (idBuilder.length() > 0) {
                idBuilder.append(",");
            }
            instanceId = Html.cleanName((String)instanceId, (char[])new char[0]);
            idBuilder.append("'").append(instanceId).append("'");
        }
        String sql = String.format(SQL_DELETE_BY_INSTANCE_IDS.replace("?", "%s"), idBuilder.toString());
        try (PreparedStatement ps = conn.prepareStatement(sql);){
            ps.execute();
        }
    }

    public static void deleteNoExistInInstanceTableJob(Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SQL_DELETE_NO_EXIST_IN_INSTANCE_TABLE);){
            ps.execute();
        }
    }

    private static JobExecResultBean fillBean(ResultSet rs) throws SQLException {
        JobExecResultBean bean = new JobExecResultBean();
        bean.setGuid(rs.getString(F_GUID));
        bean.setInstanceId(rs.getString(F_INSTANCEID));
        bean.setBucket(rs.getString(F_BUCKET));
        bean.setFileName(rs.getString(F_FILENAME));
        bean.setExtName(rs.getString(F_EXT_NAME));
        bean.setUserGuid(rs.getString(F_USERGUID));
        bean.setByteSize(rs.getLong(F_BYTE_SIZE));
        bean.setExpireTime(rs.getLong(F_EXPIRE_TIME));
        bean.setDescription(rs.getString(F_DESCRIPTION));
        return bean;
    }
}

