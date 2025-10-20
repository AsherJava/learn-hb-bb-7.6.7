/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager
 *  com.jiuqi.bi.util.Guid
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.core.jobs.manager;

import com.jiuqi.bi.core.jobs.IJobFactory;
import com.jiuqi.bi.core.jobs.JobFactoryManager;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.bean.JobInfo;
import com.jiuqi.bi.core.jobs.core.bridge.AbstractJobBridge;
import com.jiuqi.bi.core.jobs.core.bridge.JobBridgeFactory;
import com.jiuqi.bi.core.jobs.dao.JobParameterDao;
import com.jiuqi.bi.core.jobs.dao.JobStorageDao;
import com.jiuqi.bi.core.jobs.extension.JobListenerContext;
import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.bi.core.jobs.model.JobParameter;
import com.jiuqi.bi.core.jobs.model.JobScheduleModel;
import com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager;
import com.jiuqi.bi.util.Guid;
import com.jiuqi.bi.util.StringUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobStorageManager {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public JobModel getJob(String jobGuid) throws JobsException {
        JobScheduleModel jobModel;
        if (StringUtils.isEmpty((String)jobGuid)) {
            return null;
        }
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            jobModel = JobStorageDao.selectJob(conn, jobGuid);
            if (jobModel != null) {
                jobModel.getParameters().addAll(JobParameterDao.selectByJobGuid(conn, jobGuid));
            }
        }
        catch (SQLException e) {
            throw new JobsException(e.getMessage(), e);
        }
        return jobModel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public JobModel getJobByTitle(String jobTitle) throws JobsException {
        JobModel jobModel = new JobModel();
        if (StringUtils.isEmpty((String)jobTitle)) {
            return null;
        }
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            jobModel = JobStorageDao.selectJobByTitle(conn, jobTitle);
            if (jobModel != null) {
                jobModel.getParameters().addAll(JobParameterDao.selectByJobGuid(conn, jobModel.getGuid()));
            }
        }
        catch (SQLException e) {
            throw new JobsException(e.getMessage(), e);
        }
        return jobModel;
    }

    public List<JobModel> getJobByCategoryId(String categoryId) throws JobsException {
        List<JobModel> jobModels;
        if (StringUtils.isEmpty((String)categoryId)) {
            return null;
        }
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            jobModels = JobStorageDao.selectJobByCategoryId(conn, categoryId);
            if (!jobModels.isEmpty()) {
                for (JobModel jobModel : jobModels) {
                    jobModel.getParameters().addAll(JobParameterDao.selectByJobGuid(conn, jobModel.getGuid()));
                }
            }
        }
        catch (SQLException e) {
            throw new JobsException(e.getMessage(), e);
        }
        return jobModels;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<JobInfo> getJobsIncludeState(List<String> jobGuids) throws JobsException {
        List<JobInfo> JobInfos = new ArrayList<JobInfo>();
        if (jobGuids == null || jobGuids.size() == 0) {
            return JobInfos;
        }
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            JobInfos = JobStorageDao.selectJobsIncludeState(conn, jobGuids);
        }
        catch (SQLException e) {
            throw new JobsException(e.getMessage(), e);
        }
        return JobInfos;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<JobModel> getJobsByFolder(String folderGuid) throws JobsException {
        List<JobModel> jobModels = new ArrayList<JobModel>();
        if (StringUtils.isEmpty((String)folderGuid)) {
            return jobModels;
        }
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            jobModels = JobStorageDao.selectJobsByFolder(conn, folderGuid);
        }
        catch (SQLException e) {
            throw new JobsException(e.getMessage(), e);
        }
        return jobModels;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getJobCountByFolder(String folderGuid) throws JobsException {
        int count = 0;
        if (StringUtils.isEmpty((String)folderGuid)) {
            return count;
        }
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            count = JobStorageDao.selectJobCountByFolder(conn, folderGuid);
        }
        catch (SQLException e) {
            throw new JobsException(e.getMessage(), e);
        }
        return count;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void deleteJob(String jobGuid, String categoryId) throws JobsException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            JobModel job = this.getJob(jobGuid);
            if (job == null) {
                throw new JobsException("\u4efb\u52a1\u4e0d\u5b58\u5728");
            }
            IJobFactory jobFactory = JobFactoryManager.getInstance().getJobFactory(categoryId, true);
            if (jobFactory == null) {
                // empty if block
            }
            conn.setAutoCommit(false);
            JobListenerContext listenerContext = new JobListenerContext(jobGuid, categoryId);
            try {
                if (jobFactory != null) {
                    jobFactory.beforeJobDelete(listenerContext);
                }
                JobStorageDao.deleteJob(conn, jobGuid);
                JobParameterDao.deleteParameters(conn, jobGuid);
                if (jobFactory != null) {
                    jobFactory.afterJobDelete(listenerContext);
                }
                AbstractJobBridge jobBridge = JobBridgeFactory.getInstance().getDefaultBridge();
                jobBridge.unscheduleJob(jobGuid, job.getCategory());
                conn.commit();
            }
            catch (Exception e) {
                conn.rollback();
                throw e;
            }
        }
        catch (Exception e) {
            throw new JobsException(e.getMessage(), e);
        }
    }

    public JobModel addJob(JobModel job) throws JobsException {
        if (job == null) {
            throw new JobsException("job\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (StringUtils.isEmpty((String)job.getTitle()) || StringUtils.isEmpty((String)job.getCategory())) {
            throw new JobsException("job\u7684title\u548ccategory\u5747\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (StringUtils.isEmpty((String)job.getGuid())) {
            job.setGuid(Guid.newGuid());
        }
        if (job.getLastModifyTime() == 0L) {
            job.setLastModifyTime(System.currentTimeMillis());
        }
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            conn.setAutoCommit(false);
            try {
                JobStorageDao.insert(conn, job);
                JobParameterDao.insertParameters(conn, job.getGuid(), job.getParameters());
                AbstractJobBridge jobBridge = JobBridgeFactory.getInstance().getDefaultBridge();
                jobBridge.scheduleJob(job);
                if (!job.isEnable()) {
                    jobBridge.disableJob(job.getGuid(), job.getCategory());
                }
                conn.commit();
            }
            catch (Exception e) {
                conn.rollback();
                throw e;
            }
        }
        catch (Exception e) {
            throw new JobsException(e.getMessage(), e);
        }
        return job;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateJobTitle(String jobGuid, String title) throws JobsException {
        if (StringUtils.isEmpty((String)jobGuid) || StringUtils.isEmpty((String)title)) {
            throw new JobsException("jobGuid\u3001title\u5747\u4e0d\u80fd\u4e3a\u7a7a");
        }
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            JobStorageDao.updateJobTitle(conn, jobGuid, title);
        }
        catch (SQLException e) {
            throw new JobsException(e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateJobBaseinfo(JobModel job) throws JobsException {
        if (job == null || StringUtils.isEmpty((String)job.getGuid()) || StringUtils.isEmpty((String)job.getTitle())) {
            throw new JobsException("job\u3001jobGuid\u3001title\u5747\u4e0d\u80fd\u4e3a\u7a7a");
        }
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            conn.setAutoCommit(false);
            try {
                JobParameterDao.deleteParameters(conn, job.getGuid());
                JobStorageDao.updateJobBaseinfo(conn, job);
                JobParameterDao.insertParameters(conn, job.getGuid(), job.getParameters());
            }
            catch (Exception e) {
                conn.rollback();
                throw e;
            }
            finally {
                conn.setAutoCommit(true);
            }
        }
        catch (Exception e) {
            throw new JobsException(e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateJobConfig(String jobId, String config) throws JobsException {
        if (StringUtils.isEmpty((String)jobId)) {
            throw new JobsException("jobId\u4e0d\u80fd\u4e3a\u7a7a");
        }
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            JobStorageDao.updateJobConfig(conn, jobId, config);
        }
        catch (SQLException e) {
            throw new JobsException(e.getMessage(), e);
        }
    }

    public void updateJobScheduleConf(JobModel job) throws JobsException {
        if (job == null || StringUtils.isEmpty((String)job.getGuid())) {
            throw new JobsException("job\u3001jobGuid\u5747\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (StringUtils.isEmpty((String)job.getCategory())) {
            JobModel job2 = this.getJob(job.getGuid());
            job.setCategory(job2.getCategory());
            job.setEnable(job2.isEnable());
        }
        if (job.isEnable()) {
            AbstractJobBridge jobBridge = JobBridgeFactory.getInstance().getDefaultBridge();
            jobBridge.scheduleJob(job);
        }
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            JobStorageDao.updateJobScheduleConf(conn, job);
        }
        catch (Exception e) {
            throw new JobsException(e.getMessage(), e);
        }
    }

    public void updateJobEnable(String jobGuid, boolean enable) throws JobsException {
        if (StringUtils.isEmpty((String)jobGuid)) {
            throw new JobsException("jobGuid\u4e0d\u80fd\u4e3a\u7a7a");
        }
        JobModel job = this.getJob(jobGuid);
        if (job == null) {
            throw new JobsException("job\u4e0d\u5b58\u5728");
        }
        AbstractJobBridge jobBridge = JobBridgeFactory.getInstance().getDefaultBridge();
        if (enable) {
            jobBridge.scheduleJob(job);
            jobBridge.enableJob(job.getGuid(), job.getCategory());
        } else {
            jobBridge.disableJob(job.getGuid(), job.getCategory());
        }
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            JobStorageDao.updateJobEnable(conn, jobGuid, enable);
        }
        catch (Exception e) {
            throw new JobsException(e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isExistJobTitle(String title) throws JobsException {
        boolean bl;
        if (StringUtils.isEmpty((String)title)) {
            throw new JobsException("\u6807\u9898\u4e0d\u80fd\u4e3a\u7a7a");
        }
        Connection conn = GlobalConnectionProviderManager.getConnection();
        try {
            bl = JobStorageDao.isExistJobTitle(conn, title);
        }
        catch (Throwable throwable) {
            try {
                conn.close();
                throw throwable;
            }
            catch (SQLException e) {
                throw new JobsException(e.getMessage(), e);
            }
        }
        conn.close();
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateJobParameter(String jobGuid, List<JobParameter> parameters) throws JobsException {
        if (StringUtils.isEmpty((String)jobGuid) || parameters == null) {
            throw new JobsException("jobGuid\u3001parameters\u5747\u4e0d\u80fd\u4e3a\u7a7a");
        }
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            conn.setAutoCommit(false);
            try {
                JobParameterDao.deleteParameters(conn, jobGuid);
                JobParameterDao.insertParameters(conn, jobGuid, parameters);
                conn.commit();
            }
            catch (Exception e) {
                conn.rollback();
                throw e;
            }
            finally {
                conn.setAutoCommit(true);
            }
        }
        catch (Exception e) {
            throw new JobsException(e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<JobParameter> getJobParameter(String jobGuid) throws JobsException {
        List<JobParameter> list;
        if (StringUtils.isEmpty((String)jobGuid)) {
            throw new JobsException("\u4efb\u52a1\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a");
        }
        Connection conn = GlobalConnectionProviderManager.getConnection();
        try {
            list = JobParameterDao.selectByJobGuid(conn, jobGuid);
        }
        catch (Throwable throwable) {
            try {
                conn.close();
                throw throwable;
            }
            catch (SQLException e) {
                throw new JobsException(e.getMessage(), e);
            }
        }
        conn.close();
        return list;
    }
}

