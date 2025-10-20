/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.core.jobs.simpleschedule.dao;

import com.jiuqi.bi.core.jobs.certification.CertificationInfo;
import com.jiuqi.bi.core.jobs.model.AbstractScheduleMethod;
import com.jiuqi.bi.core.jobs.simpleschedule.bean.SimpleJobBean;
import com.jiuqi.bi.core.jobs.simpleschedule.bean.SimpleJobExecuteType;
import com.jiuqi.bi.util.StringUtils;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONException;
import org.json.JSONObject;

public class SimpleJobDao {
    private static final String T_TABLE = "BI_SIMPLE_JOBS";
    private static final String F_GUID = "BSJ_GUID";
    private static final String F_TITLE = "BSJ_TITLE";
    private static final String F_DESC = "BSJ_DESC";
    private static final String F_ENABLE = "BSJ_ENABLE";
    private static final String F_SCHEDULEINFO = "BSJ_SCHEDULEINFO";
    private static final String F_FOLDER = "BSJ_FOLDER";
    private static final String F_USER = "BSJ_USER";
    private static final String F_MODIFYTIME = "BSJ_MODIFYTIME";
    private static final String F_JOBCATEGORY = "BSJ_JOBCATEGORY";
    private static final String F_STARTTIME = "BSJ_STARTTIME";
    private static final String F_ENDTIME = "BSJ_ENDTIME";
    private static final String F_SCHEDULETYPE = "BSJ_SCHEDULETYPE";
    private static final String F_SCHEDULESUMMARY = "BSJ_SCHEDULESUMMARY";
    private static final String F_CERTIFICATION = "BSJ_CERTIFICATION";
    private static final String F_CONFIG = "BSJ_CONFIG";
    private static final String F_GROUPID = "BSJ_GROUPID";
    private static final String F_EXECUTETYPE = "BSJ_EXECUTETYPE";
    private static final String F_FINISHED = "BSJ_FINISHED";
    private static final String SQL_INSERT = "INSERT INTO BI_SIMPLE_JOBS(BSJ_GUID ,BSJ_TITLE ,BSJ_DESC ,BSJ_ENABLE ,BSJ_SCHEDULEINFO ,BSJ_SCHEDULESUMMARY ,BSJ_SCHEDULETYPE ,BSJ_FOLDER ,BSJ_USER ,BSJ_MODIFYTIME ,BSJ_JOBCATEGORY ,BSJ_STARTTIME ,BSJ_ENDTIME ,BSJ_CERTIFICATION ,BSJ_CONFIG ,BSJ_GROUPID ,BSJ_EXECUTETYPE ,BSJ_FINISHED) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SELECT_JOB = "SELECT BSJ_FOLDER, BSJ_TITLE, BSJ_JOBCATEGORY, BSJ_DESC, BSJ_ENABLE, BSJ_STARTTIME, BSJ_ENDTIME, BSJ_SCHEDULEINFO, BSJ_SCHEDULETYPE, BSJ_SCHEDULESUMMARY, BSJ_USER, BSJ_CERTIFICATION, BSJ_CONFIG, BSJ_GROUPID, BSJ_EXECUTETYPE FROM BI_SIMPLE_JOBS WHERE BSJ_GUID=?";
    private static final String UPDATE_FINISH = "UPDATE BI_SIMPLE_JOBS SET BSJ_FINISHED=1 WHERE BSJ_GUID=? AND BSJ_EXECUTETYPE=?";
    private static final String SQL_DELETE = "DELETE FROM BI_SIMPLE_JOBS WHERE BSJ_GUID=?";

    private SimpleJobDao() {
    }

    public static void addBean(Connection conn, SimpleJobBean bean) throws Exception {
        if (conn == null || bean == null) {
            return;
        }
        try (PreparedStatement pst = conn.prepareStatement(SQL_INSERT);){
            int index = 1;
            pst.setString(index++, bean.getGuid());
            pst.setString(index++, bean.getTitle());
            pst.setString(index++, bean.getDesc());
            pst.setInt(index++, bean.isEnable() ? 1 : 0);
            AbstractScheduleMethod scheduleMethod = bean.getScheduleMethod();
            byte[] scheduleInfo = null;
            if (scheduleMethod != null) {
                JSONObject scheduleJson = new JSONObject();
                scheduleMethod.toJson(scheduleJson);
                scheduleInfo = scheduleJson.toString().getBytes(StandardCharsets.UTF_8);
                pst.setBytes(index++, scheduleInfo);
                String scheduleType = scheduleMethod.getName();
                pst.setString(index++, scheduleType);
                String summary = scheduleMethod.generateText();
                pst.setString(index++, summary);
            }
            pst.setString(index++, bean.getFolderGuid());
            pst.setString(index++, bean.getUser());
            pst.setLong(index++, bean.getLastModifyTime());
            pst.setString(index++, bean.getCategory());
            pst.setLong(index++, bean.getStartTime());
            pst.setLong(index++, bean.getEndTime());
            pst.setString(index++, bean.getCertification() == null ? null : bean.getCertification().toJson().toString());
            pst.setBytes(index++, bean.getExtendedConfig() == null ? new byte[]{} : bean.getExtendedConfig().getBytes(StandardCharsets.UTF_8));
            pst.setString(index++, bean.getGroupId());
            pst.setString(index++, bean.getExecuteType());
            pst.setInt(index++, bean.isFinished() ? 1 : 0);
            pst.execute();
        }
    }

    public static SimpleJobBean queryBean(Connection conn, String guid) throws SQLException {
        block20: {
            if (conn == null || StringUtils.isEmpty((String)guid)) {
                return null;
            }
            try (PreparedStatement ps = conn.prepareStatement(SELECT_JOB);){
                ps.setString(1, guid);
                try (ResultSet rs = ps.executeQuery();){
                    if (!rs.next()) break block20;
                    SimpleJobBean bean = new SimpleJobBean();
                    bean.setGuid(guid);
                    bean.setTitle(rs.getString(2));
                    bean.setCategory(rs.getString(3));
                    bean.setDesc(rs.getString(4));
                    bean.setEnable(rs.getInt(5) == 1);
                    bean.setStartTime(rs.getLong(6));
                    bean.setEndTime(rs.getLong(7));
                    byte[] data = rs.getBytes(8);
                    if (data != null) {
                        try {
                            String scheduleInfo = new String(data, StandardCharsets.UTF_8);
                            if (StringUtils.isNotEmpty((String)scheduleInfo)) {
                                JSONObject scheduleJson = new JSONObject(scheduleInfo);
                                bean.setScheduleMethod(AbstractScheduleMethod.loadMethod(scheduleJson));
                            }
                        }
                        catch (JSONException e) {
                            throw new SQLException("string\u8f6cjson\u5931\u8d25", e);
                        }
                    }
                    bean.setFolderGuid(rs.getString(1));
                    bean.setUser(rs.getString(11));
                    String cert = rs.getString(12);
                    if (StringUtils.isNotEmpty((String)cert)) {
                        bean.setCertification(new CertificationInfo().fromJson(new JSONObject(cert)));
                    }
                    if ((data = rs.getBytes(13)) != null) {
                        String extendedConfig = new String(data, StandardCharsets.UTF_8);
                        bean.setExtendedConfig(extendedConfig);
                    }
                    bean.setGroupId(rs.getString(14));
                    bean.setExecuteType(rs.getString(15));
                    SimpleJobBean simpleJobBean = bean;
                    return simpleJobBean;
                }
            }
        }
        return null;
    }

    public static void updateFinished(Connection conn, String jobId, SimpleJobExecuteType executeType) throws SQLException {
        if (conn == null) {
            return;
        }
        try (PreparedStatement ps = conn.prepareStatement(UPDATE_FINISH);){
            ps.setString(1, jobId);
            ps.setString(2, executeType.getName());
            ps.executeUpdate();
        }
    }

    public static void deleteBean(Connection conn, String jobId) throws SQLException {
        if (conn == null || StringUtils.isEmpty((String)jobId)) {
            return;
        }
        try (PreparedStatement ps = conn.prepareStatement(SQL_DELETE);){
            ps.setString(1, jobId);
            ps.executeUpdate();
        }
    }
}

