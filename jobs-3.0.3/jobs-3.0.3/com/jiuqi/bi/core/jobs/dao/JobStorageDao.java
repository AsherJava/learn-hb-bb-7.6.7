/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.core.jobs.dao;

import com.jiuqi.bi.core.jobs.bean.JobInfo;
import com.jiuqi.bi.core.jobs.bean.JobInstanceBean;
import com.jiuqi.bi.core.jobs.certification.CertificationInfo;
import com.jiuqi.bi.core.jobs.core.SchedulerManager;
import com.jiuqi.bi.core.jobs.model.AbstractScheduleMethod;
import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.bi.core.jobs.model.JobScheduleModel;
import com.jiuqi.bi.util.StringUtils;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class JobStorageDao {
    public static final String T_NAME = "BI_JOBS";
    public static final String F_JOBGUID = "BJ_JOBGUID";
    public static final String F_JOBTITLE = "BJ_JOBTITLE";
    public static final String F_JOBCATEGORY = "BJ_JOBCATEGORY";
    public static final String F_DESC = "BJ_DESC";
    public static final String F_ENABLE = "BJ_ENABLE";
    public static final String F_STARTTIME = "BJ_STARTTIME";
    public static final String F_ENDTIME = "BJ_ENDTIME";
    public static final String F_SCHEDULEINFO = "BJ_SCHEDULEINFO";
    public static final String F_FOLDER = "BJ_FOLDER";
    public static final String F_LASTMODIFYTIME = "BJ_LASTMODIFYTIME";
    public static final String F_SCHEDULETYPE = "BJ_SCHEDULETYPE";
    public static final String F_SCHEDULESUMMARY = "BJ_SCHEDULESUMMARY";
    public static final String F_USER = "BJ_USER";
    public static final String F_CERTIFICATION = "BJ_CERTIFICATION";
    public static final String F_CONFIG = "BJ_CONFIG";
    private static final String SELECT_JOB;
    private static final String SELECT_JOB_BY_TITLE = "SELECT BJ_FOLDER, BJ_JOBGUID, BJ_JOBCATEGORY, BJ_DESC, BJ_ENABLE, BJ_STARTTIME, BJ_ENDTIME, BJ_SCHEDULEINFO, BJ_LASTMODIFYTIME, BJ_SCHEDULETYPE, BJ_SCHEDULESUMMARY, BJ_USER, BJ_CERTIFICATION, BJ_CONFIG FROM BI_JOBS WHERE BJ_JOBTITLE = ? ";
    private static final String SELECT_JOBS_INCLUDE_STATE = "SELECT  T1.BJ_JOBGUID,  T1.BJ_JOBTITLE,  T1.BJ_JOBCATEGORY,  T1.BJ_DESC,  T1.BJ_ENABLE,  T1.BJ_STARTTIME,  T1.BJ_ENDTIME,  T1.BJ_SCHEDULEINFO,  T1.BJ_FOLDER,  T1.BJ_LASTMODIFYTIME,  T3.BJI_INSTANCEID ,  T3.BJI_STATE ,  T3.BJI_PROGRESS ,  T3.BJI_STARTTIME ,   T3.BJI_ENDTIME,   T3.BJI_RESULT,   T3.BJI_INSTITLE   FROM BI_JOBS T1  LEFT JOIN  (  SELECT BJI_JOBGUID JOBGUID, MAX(BJI_STARTTIME) STARTTIME  FROM BI_JOBS_INSTANCES  WHERE BJI_PARENT_INSTID IS NULL  GROUP BY BJI_JOBGUID  ) T2 ON T1.BJ_JOBGUID = T2.JOBGUID LEFT JOIN (SELECT * FROM BI_JOBS_INSTANCES T4  WHERE T4.BJI_STATE = 0 ) T3  ON T2.JOBGUID = T3.BJI_JOBGUID AND T2.STARTTIME = T3.BJI_STARTTIME  WHERE T1.BJ_JOBGUID IN ( ? )  ORDER BY BJ_JOBTITLE ";
    private static final String SELECT_JOBS_BY_FOLDER = "SELECT BJ_JOBGUID, BJ_JOBTITLE, BJ_JOBCATEGORY, BJ_DESC, BJ_ENABLE, BJ_STARTTIME, BJ_ENDTIME, BJ_SCHEDULEINFO, BJ_LASTMODIFYTIME, BJ_SCHEDULETYPE, BJ_SCHEDULESUMMARY , BJ_USER, BJ_CERTIFICATION, BJ_CONFIG FROM BI_JOBS WHERE BJ_FOLDER = ?  ORDER BY BJ_JOBTITLE ASC ";
    private static final String SELECT_JOB_COUNT_BY_FOLDER = "SELECT COUNT(*)   FROM BI_JOBS WHERE BJ_FOLDER = ? ";
    private static final String DELETE_JOB = "DELETE  FROM BI_JOBS WHERE BJ_JOBGUID = ? ";
    private static final String INSERT_JOB = "INSERT INTO BI_JOBS ( BJ_JOBGUID , BJ_JOBTITLE , BJ_JOBCATEGORY , BJ_DESC , BJ_ENABLE , BJ_STARTTIME , BJ_ENDTIME , BJ_SCHEDULEINFO , BJ_FOLDER , BJ_LASTMODIFYTIME , BJ_SCHEDULETYPE , BJ_SCHEDULESUMMARY , BJ_USER, BJ_CERTIFICATION, BJ_CONFIG )  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_JOB_TITLE = "UPDATE BI_JOBS SET BJ_JOBTITLE = ?  WHERE BJ_JOBGUID = ? ";
    private static final String UPDATE_JOB_BASEINFO = " UPDATE BI_JOBS SET BJ_JOBTITLE = ? , BJ_FOLDER = ? , BJ_DESC = ? , BJ_USER = ? , BJ_CERTIFICATION = ? , BJ_CONFIG = ? WHERE BJ_JOBGUID = ? ";
    private static final String UPDATE_JOB_SCHEDULE_CONF = "UPDATE BI_JOBS SET BJ_STARTTIME = ? , BJ_ENDTIME = ? , BJ_SCHEDULEINFO = ? , BJ_LASTMODIFYTIME = ? , BJ_SCHEDULETYPE = ? , BJ_SCHEDULESUMMARY = ?  WHERE BJ_JOBGUID = ? ";
    private static final String UPDATE_JOB_ENABLE = "UPDATE BI_JOBS SET BJ_ENABLE = ?  WHERE BJ_JOBGUID = ? ";
    private static final String UPDATE_JOB_CONFIG = "UPDATE BI_JOBS SET BJ_CONFIG = ?  WHERE BJ_JOBGUID = ? ";
    private static final String EXIST_JOB_WITH_TITLE = "SELECT * FROM BI_JOBS WHERE BJ_JOBTITLE = ? ";
    private static final String SELECT_JOB_BY_CATEGORY_ID = "SELECT BJ_FOLDER, BJ_JOBGUID, BJ_JOBCATEGORY, BJ_DESC, BJ_ENABLE, BJ_STARTTIME, BJ_ENDTIME, BJ_SCHEDULEINFO, BJ_LASTMODIFYTIME, BJ_SCHEDULETYPE, BJ_SCHEDULESUMMARY, BJ_USER, BJ_CERTIFICATION, BJ_CONFIG FROM BI_JOBS WHERE BJ_JOBCATEGORY = ? ";

    private JobStorageDao() {
    }

    public static JobScheduleModel selectJob(Connection conn, String jobGuid) throws SQLException {
        JobScheduleModel jobModel;
        block14: {
            jobModel = new JobScheduleModel();
            try (PreparedStatement ps = conn.prepareStatement(SELECT_JOB);){
                ps.setString(1, jobGuid);
                try (ResultSet rs = ps.executeQuery();){
                    if (rs.next()) {
                        jobModel.setGuid(jobGuid);
                        jobModel.setTitle(rs.getString(2));
                        JobStorageDao.innerSelectJob(jobModel, rs);
                        jobModel.setLastExecuteTime(rs.getLong("PREV_FIRE_TIME"));
                        jobModel.setNextExecuteTime(rs.getLong("NEXT_FIRE_TIME"));
                        break block14;
                    }
                    JobScheduleModel jobScheduleModel = null;
                    return jobScheduleModel;
                }
            }
        }
        return jobModel;
    }

    public static List<JobInfo> selectJobsIncludeState(Connection conn, List<String> jobGuids) throws SQLException {
        ArrayList<JobInfo> jobInfos = new ArrayList<JobInfo>();
        if (jobGuids == null || jobGuids.isEmpty()) {
            return jobInfos;
        }
        HashSet<String> set = new HashSet<String>();
        String sql = String.format(SELECT_JOBS_INCLUDE_STATE.replace("?", "%s"), JobStorageDao.generateString(jobGuids));
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();){
            while (rs.next()) {
                JobModel jobModel = new JobModel();
                jobModel.setGuid(rs.getString(1));
                jobModel.setTitle(rs.getString(2));
                jobModel.setCategory(rs.getString(3));
                jobModel.setDesc(rs.getString(4));
                jobModel.setEnable(rs.getInt(5) == 1);
                jobModel.setStartTime(rs.getLong(6));
                jobModel.setEndTime(rs.getLong(7));
                if (rs.getBytes(8) != null) {
                    try {
                        String scheduleInfo = new String(rs.getBytes(8), StandardCharsets.UTF_8);
                        if (StringUtils.isNotEmpty((String)scheduleInfo)) {
                            JSONObject scheduleJson = new JSONObject(scheduleInfo);
                            jobModel.setScheduleMethod(AbstractScheduleMethod.loadMethod(scheduleJson));
                        }
                    }
                    catch (JSONException e) {
                        throw new SQLException("string\u8f6cjson\u5931\u8d25: " + e.getMessage());
                    }
                }
                jobModel.setFolderGuid(rs.getString(9));
                jobModel.setLastModifyTime(rs.getLong(10));
                JobInstanceBean instance = null;
                if (StringUtils.isNotEmpty((String)rs.getString(11))) {
                    if (set.contains(jobModel.getGuid())) continue;
                    set.add(jobModel.getGuid());
                    instance = new JobInstanceBean();
                    instance.setInstanceId(rs.getString(11));
                    instance.setState(rs.getInt(12));
                    instance.setProgress(rs.getInt(13));
                    instance.setStarttime(rs.getLong(14));
                    instance.setEndtime(rs.getLong(15));
                    instance.setResult(rs.getInt(16));
                    instance.setInstanceName(rs.getString(17));
                }
                JobInfo jobInfo = new JobInfo(jobModel, instance);
                jobInfos.add(jobInfo);
            }
        }
        return jobInfos;
    }

    private static String generateString(List<String> jobGuids) {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (String guid : jobGuids) {
            if (i > 0) {
                builder.append(",");
            }
            builder.append("'").append(guid).append("'");
            ++i;
        }
        return builder.toString();
    }

    public static List<JobModel> selectJobsByFolder(Connection conn, String folderGuid) throws SQLException {
        ArrayList<JobModel> jobModels = new ArrayList<JobModel>();
        try (PreparedStatement ps = conn.prepareStatement(SELECT_JOBS_BY_FOLDER);){
            ps.setString(1, folderGuid);
            try (ResultSet rs = ps.executeQuery();){
                while (rs.next()) {
                    JobModel jobModel = new JobModel();
                    jobModel.setGuid(rs.getString(1));
                    jobModel.setTitle(rs.getString(2));
                    jobModel.setCategory(rs.getString(3));
                    jobModel.setDesc(rs.getString(4));
                    jobModel.setEnable(rs.getInt(5) == 1);
                    jobModel.setStartTime(rs.getLong(6));
                    jobModel.setEndTime(rs.getLong(7));
                    byte[] data = rs.getBytes(8);
                    if (data != null) {
                        try {
                            String scheduleInfo = new String(data, StandardCharsets.UTF_8);
                            if (StringUtils.isNotEmpty((String)scheduleInfo)) {
                                JSONObject scheduleJson = new JSONObject(scheduleInfo);
                                jobModel.setScheduleMethod(AbstractScheduleMethod.loadMethod(scheduleJson));
                            }
                        }
                        catch (JSONException e) {
                            throw new SQLException("string\u8f6cjson\u5931\u8d25", e);
                        }
                    }
                    jobModel.setFolderGuid(folderGuid);
                    jobModel.setLastModifyTime(rs.getLong(9));
                    jobModel.setUser(rs.getString(12));
                    String cert = rs.getString(13);
                    if (StringUtils.isNotEmpty((String)cert)) {
                        jobModel.setCertification(new CertificationInfo().fromJson(new JSONObject(cert)));
                    }
                    if ((data = rs.getBytes(14)) != null) {
                        String extendedConfig = new String(data, StandardCharsets.UTF_8);
                        jobModel.setExtendedConfig(extendedConfig);
                    }
                    jobModels.add(jobModel);
                }
            }
        }
        return jobModels;
    }

    public static int selectJobCountByFolder(Connection conn, String folderGuid) throws SQLException {
        int count = 0;
        try (PreparedStatement ps = conn.prepareStatement(SELECT_JOB_COUNT_BY_FOLDER);){
            ps.setString(1, folderGuid);
            try (ResultSet rs = ps.executeQuery();){
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        }
        return count;
    }

    public static void deleteJob(Connection conn, String jobGuid) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(DELETE_JOB);){
            ps.setString(1, jobGuid);
            ps.execute();
        }
    }

    public static void insert(Connection conn, JobModel job) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(INSERT_JOB);){
            ps.setString(1, job.getGuid());
            ps.setString(2, job.getTitle());
            ps.setString(3, job.getCategory());
            ps.setString(4, job.getDesc());
            ps.setInt(5, job.isEnable() ? 1 : 0);
            ps.setLong(6, job.getStartTime());
            ps.setLong(7, job.getEndTime());
            AbstractScheduleMethod scheduleMethod = job.getScheduleMethod();
            byte[] scheduleInfo = null;
            if (scheduleMethod != null) {
                JSONObject scheduleJson = new JSONObject();
                scheduleMethod.toJson(scheduleJson);
                scheduleInfo = scheduleJson.toString().getBytes(StandardCharsets.UTF_8);
                String scheduleType = scheduleMethod.getName();
                ps.setString(11, scheduleType);
                String summary = scheduleMethod.generateText();
                ps.setString(12, summary);
            }
            ps.setBytes(8, scheduleInfo);
            ps.setString(9, job.getFolderGuid());
            ps.setLong(10, job.getLastModifyTime());
            ps.setString(13, job.getUser());
            ps.setString(14, job.getCertification() == null ? null : job.getCertification().toJson().toString());
            ps.setBytes(15, job.getExtendedConfig() == null ? new byte[]{} : job.getExtendedConfig().getBytes(StandardCharsets.UTF_8));
            ps.execute();
        }
        catch (JSONException e) {
            throw new SQLException(e.getMessage(), e);
        }
    }

    public static void updateJobTitle(Connection conn, String jobGuid, String title) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(UPDATE_JOB_TITLE);){
            ps.setString(1, title);
            ps.setString(2, jobGuid);
            ps.execute();
        }
    }

    public static void updateJobBaseinfo(Connection conn, JobModel job) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(UPDATE_JOB_BASEINFO);){
            ps.setString(1, job.getTitle());
            ps.setString(2, job.getFolderGuid());
            ps.setString(3, job.getDesc());
            ps.setString(4, job.getUser());
            ps.setString(5, job.getCertification() == null ? null : job.getCertification().toJson().toString());
            ps.setBytes(6, job.getExtendedConfig() == null ? new byte[]{} : job.getExtendedConfig().getBytes(StandardCharsets.UTF_8));
            ps.setString(7, job.getGuid());
            ps.execute();
        }
    }

    public static void updateJobScheduleConf(Connection conn, JobModel job) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(UPDATE_JOB_SCHEDULE_CONF);){
            ps.setLong(1, job.getStartTime());
            ps.setLong(2, job.getEndTime());
            AbstractScheduleMethod scheduleMethod = job.getScheduleMethod();
            byte[] scheduleInfo = null;
            if (scheduleMethod != null) {
                JSONObject scheduleJson = new JSONObject();
                scheduleMethod.toJson(scheduleJson);
                scheduleInfo = scheduleJson.toString().getBytes(StandardCharsets.UTF_8);
                String scheduleType = scheduleMethod.getName();
                ps.setString(5, scheduleType);
                String summary = scheduleMethod.generateText();
                ps.setString(6, summary);
            } else {
                ps.setString(5, null);
                ps.setString(6, null);
            }
            ps.setBytes(3, scheduleInfo);
            ps.setLong(4, System.currentTimeMillis());
            ps.setString(7, job.getGuid());
            ps.execute();
        }
        catch (JSONException e) {
            throw new SQLException(e);
        }
    }

    public static void updateJobEnable(Connection conn, String jobGuid, boolean enable) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(UPDATE_JOB_ENABLE);){
            ps.setInt(1, enable ? 1 : 0);
            ps.setString(2, jobGuid);
            ps.execute();
        }
    }

    public static void updateJobConfig(Connection conn, String jobGuid, String config) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(UPDATE_JOB_CONFIG);){
            ps.setBytes(1, StringUtils.isEmpty((String)config) ? new byte[]{} : config.getBytes(StandardCharsets.UTF_8));
            ps.setString(2, jobGuid);
            ps.execute();
        }
    }

    public static boolean isExistJobTitle(Connection conn, String title) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(EXIST_JOB_WITH_TITLE);){
            boolean bl;
            block12: {
                ps.setString(1, title);
                ResultSet rs = ps.executeQuery();
                try {
                    bl = rs.next();
                    if (rs == null) break block12;
                }
                catch (Throwable throwable) {
                    if (rs != null) {
                        try {
                            rs.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                rs.close();
            }
            return bl;
        }
    }

    public static JobModel selectJobByTitle(Connection conn, String jobTitle) throws SQLException {
        JobModel jobModel;
        block14: {
            jobModel = new JobModel();
            try (PreparedStatement ps = conn.prepareStatement(SELECT_JOB_BY_TITLE);){
                ps.setString(1, jobTitle);
                try (ResultSet rs = ps.executeQuery();){
                    if (rs.next()) {
                        jobModel.setGuid(rs.getString(2));
                        jobModel.setTitle(jobTitle);
                        JobStorageDao.innerSelectJob(jobModel, rs);
                        break block14;
                    }
                    JobModel jobModel2 = null;
                    return jobModel2;
                }
            }
        }
        return jobModel;
    }

    public static List<JobModel> selectJobByCategoryId(Connection conn, String CategoryId) throws SQLException {
        ArrayList<JobModel> jobModels = new ArrayList<JobModel>();
        try (PreparedStatement ps = conn.prepareStatement(SELECT_JOB_BY_CATEGORY_ID);){
            ps.setString(1, CategoryId);
            try (ResultSet rs = ps.executeQuery();){
                while (rs.next()) {
                    JobModel jobModel = new JobModel();
                    jobModel.setGuid(rs.getString(2));
                    jobModel.setTitle(CategoryId);
                    JobStorageDao.innerSelectJob(jobModel, rs);
                    jobModels.add(jobModel);
                }
            }
        }
        return jobModels;
    }

    private static void innerSelectJob(JobModel jobModel, ResultSet rs) throws SQLException {
        jobModel.setCategory(rs.getString(3));
        jobModel.setDesc(rs.getString(4));
        jobModel.setEnable(rs.getInt(5) == 1);
        jobModel.setStartTime(rs.getLong(6));
        jobModel.setEndTime(rs.getLong(7));
        byte[] data = rs.getBytes(8);
        if (data != null) {
            try {
                String scheduleInfo = new String(data, StandardCharsets.UTF_8);
                if (StringUtils.isNotEmpty((String)scheduleInfo)) {
                    JSONObject scheduleJson = new JSONObject(scheduleInfo);
                    jobModel.setScheduleMethod(AbstractScheduleMethod.loadMethod(scheduleJson));
                }
            }
            catch (JSONException e) {
                throw new SQLException("string\u8f6cjson\u5931\u8d25", e);
            }
        }
        jobModel.setFolderGuid(rs.getString(1));
        jobModel.setLastModifyTime(rs.getLong(9));
        jobModel.setUser(rs.getString(12));
        String cert = rs.getString(13);
        if (StringUtils.isNotEmpty((String)cert)) {
            jobModel.setCertification(new CertificationInfo().fromJson(new JSONObject(cert)));
        }
        if ((data = rs.getBytes(14)) != null) {
            String extendedConfig = new String(data, StandardCharsets.UTF_8);
            jobModel.setExtendedConfig(extendedConfig);
        }
    }

    static {
        StringBuilder stringBuilder = new StringBuilder().append("SELECT BJ_FOLDER, BJ_JOBTITLE, BJ_JOBCATEGORY, BJ_DESC, BJ_ENABLE, BJ_STARTTIME, BJ_ENDTIME, BJ_SCHEDULEINFO, BJ_LASTMODIFYTIME, BJ_SCHEDULETYPE, BJ_SCHEDULESUMMARY, BJ_USER, BJ_CERTIFICATION, BJ_CONFIG, PREV_FIRE_TIME, NEXT_FIRE_TIME FROM BI_JOBS j LEFT JOIN ");
        SchedulerManager.getInstance();
        SELECT_JOB = stringBuilder.append(SchedulerManager.getTablePrefix()).append("TRIGGERS t ON  j.BJ_JOBGUID = t.JOB_NAME WHERE ").append(F_JOBGUID).append(" = ? ").toString();
    }
}

