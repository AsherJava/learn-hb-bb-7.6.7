/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionException
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager
 *  com.jiuqi.bi.core.nodekeeper.Node
 *  com.jiuqi.bi.util.Html
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONObject
 */
package com.jiuqi.bi.core.jobs.manager;

import com.jiuqi.bi.core.jobs.JobCacheProviderManager;
import com.jiuqi.bi.core.jobs.JobFactoryManager;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.bean.JobExecResultBean;
import com.jiuqi.bi.core.jobs.bean.JobInstanceBean;
import com.jiuqi.bi.core.jobs.dao.JobInstanceDetailDAO;
import com.jiuqi.bi.core.jobs.dao.JobInstancesDAO;
import com.jiuqi.bi.core.jobs.dao.JobLinkSourceDao;
import com.jiuqi.bi.core.jobs.dao.JobRunningDao;
import com.jiuqi.bi.core.jobs.manager.JobExecResultManager;
import com.jiuqi.bi.core.jobs.monitor.JobGroupByInfo;
import com.jiuqi.bi.core.jobs.monitor.JobKind;
import com.jiuqi.bi.core.jobs.monitor.JobType;
import com.jiuqi.bi.core.jobs.monitor.State;
import com.jiuqi.bi.core.jobs.oss.OSSManager;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.core.RealTimeJobFactory;
import com.jiuqi.bi.core.jobs.realtime.imdiately.IImmediatelyJobStorageMiddleware;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobManager;
import com.jiuqi.bi.core.nodekeeper.DistributionException;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager;
import com.jiuqi.bi.core.nodekeeper.Node;
import com.jiuqi.bi.util.Html;
import com.jiuqi.bi.util.StringUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobOperationManager {
    private static final Logger logger = LoggerFactory.getLogger(JobOperationManager.class);
    private long lastCommitTime;
    private static final long COMMIT_PROGRESS_INTERVAL = 2000L;

    public void mainJobStart(String instanceId, String jobId, String userguid, String username, String jobTitle, String quartzInstance, String categoryId, String categoryTitle, boolean backstage, List<String> linkSources, String queryField1, String queryField2) throws Exception {
        Node self = DistributionManager.getInstance().getSelfNode();
        this.insertInstance(instanceId, jobId, userguid, username, State.RUNNING, self.getName(), jobTitle, quartzInstance, JobType.SCHEDULED_JOB, categoryId, categoryTitle, backstage, linkSources, queryField1, queryField2);
    }

    public void mainJobAdded(String instanceId, String jobId, String userguid, String username, String jobTitle, JobType jobType, String categoryId, String categoryTitle, boolean backstage, List<String> bindingSource, String queryField1, String queryField2) throws Exception {
        this.insertInstance(instanceId, jobId, userguid, username, State.WAITING, "", jobTitle, "", jobType, categoryId, categoryTitle, backstage, bindingSource, queryField1, queryField2);
    }

    public void mainRealTimeJobAdded(String instanceId, String jobId, String userguid, String username, String jobTitle, JobType jobType, String categoryId, String categoryTitle, boolean backstage, List<String> bindingSource, String queryField1, String queryField2) throws Exception {
        this.insertInstance(instanceId, jobId, userguid, username, State.WAITING, "", jobTitle, "", jobType, categoryId, categoryTitle, backstage, -1, bindingSource, queryField1, queryField2);
    }

    public void mainJobFired(String instanceId, String quartzInstance) throws Exception {
        this.jobFired(instanceId, quartzInstance);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void jobFired(String instanceId, String quartzInstance) throws Exception {
        Node self = DistributionManager.getInstance().getSelfNode();
        long execTime = System.currentTimeMillis();
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            JobInstanceBean bean;
            JobInstancesDAO.updateInstanceStateAndNodeNameAndExecTime(conn, instanceId, State.RUNNING.getValue(), self.getName(), quartzInstance, execTime);
            if (JobCacheProviderManager.getInstance().getJobInstanceBeanCache().exist(instanceId) && (bean = (JobInstanceBean)JobCacheProviderManager.getInstance().getJobInstanceBeanCache().get(instanceId)) != null) {
                bean.setState(State.RUNNING.getValue());
                bean.setNode(self.getName());
                bean.setQuartzInstance(quartzInstance);
                bean.setExecStartTime(execTime);
                JobCacheProviderManager.getInstance().getJobInstanceBeanCache().put(instanceId, bean);
            }
        }
    }

    private void insertInstance(String instanceId, String jobId, String userguid, String username, State state, String nodeName, String jobTitle, String quartzInstance, JobType jobType, String categoryId, String categoryTitle, boolean backstage, List<String> linkSources, String queryField1, String queryField2) throws Exception {
        this.insertInstance(instanceId, jobId, userguid, username, state, nodeName, jobTitle, quartzInstance, jobType, categoryId, categoryTitle, backstage, 0, linkSources, queryField1, queryField2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void insertInstance(String instanceId, String jobId, String userguid, String username, State state, String nodeName, String jobTitle, String quartzInstance, JobType jobType, String categoryId, String categoryTitle, boolean backstage, int level, List<String> linkSources, String queryField1, String queryField2) throws Exception {
        Node self = DistributionManager.getInstance().getSelfNode();
        JobInstanceBean bean = new JobInstanceBean();
        bean.setInstanceId(instanceId);
        bean.setParentInstanceId(null);
        bean.setRootInstanceId(instanceId);
        bean.setLevel(level);
        bean.setProgress(0);
        bean.setPrompt("");
        bean.setState(state.getValue());
        bean.setJobType(jobType.getValue());
        bean.setUserguid(StringUtils.isEmpty((String)userguid) ? "_system_" : userguid);
        bean.setUsername(StringUtils.isEmpty((String)username) ? "_system_" : username);
        bean.setStarttime(System.currentTimeMillis());
        bean.setEndtime(-1L);
        bean.setJobId(jobId);
        bean.setResult(1);
        bean.setResultMessage("");
        bean.setNode(nodeName);
        bean.setInstanceName(jobTitle);
        bean.setQuartzInstance(quartzInstance);
        bean.setCategoryId(categoryId);
        bean.setCategoryTitle(categoryTitle);
        bean.setBackstage(backstage);
        bean.setQueryField1(queryField1);
        bean.setQueryField2(queryField2);
        bean.setPublishNode(self.getName());
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            conn.setAutoCommit(false);
            try {
                JobInstancesDAO.insertInstance(conn, bean);
                JobLinkSourceDao.addJobSource(conn, instanceId, linkSources);
                conn.commit();
            }
            catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateJobInstanceTitle(String title, String instanceId) throws SQLException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            JobInstanceBean bean;
            JobInstancesDAO.updateInstanceTitle(conn, instanceId, title);
            if (JobCacheProviderManager.getInstance().getJobInstanceBeanCache().exist(instanceId) && (bean = (JobInstanceBean)JobCacheProviderManager.getInstance().getJobInstanceBeanCache().get(instanceId)) != null) {
                bean.setInstanceName(title);
                JobCacheProviderManager.getInstance().getJobInstanceBeanCache().put(instanceId, bean);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateJobStage(String instanceId, int stage) throws SQLException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            JobInstancesDAO.updateInstanceExecuteStage(conn, instanceId, stage);
            JobCacheProviderManager.getInstance().getJobInstanceBeanCache().remove(instanceId);
        }
    }

    public void subJobAdded(String instanceId, String parentInstanceId, String rootInstanceId, String jobId, String jobTitle, String userguid, String username, List<String> linkSource) throws SQLException {
        this.subJobAdded(instanceId, parentInstanceId, rootInstanceId, jobId, jobTitle, userguid, username, 1, linkSource);
    }

    public void subRealTimeJobAdded(String instanceId, String parentInstanceId, String rootInstanceId, String jobId, String jobTitle, String userguid, String username, List<String> linkSource) throws SQLException {
        this.subJobAdded(instanceId, parentInstanceId, rootInstanceId, jobId, jobTitle, userguid, username, -2, linkSource);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void subJobAdded(String instanceId, String parentInstanceId, String rootInstanceId, String jobId, String jobTitle, String userguid, String username, int level, List<String> linkSource) throws SQLException {
        String self = null;
        try {
            self = DistributionManager.getInstance().getSelfNode().getName();
        }
        catch (DistributionException e) {
            throw new RuntimeException(e);
        }
        JobInstanceBean bean = new JobInstanceBean();
        bean.setInstanceId(instanceId);
        bean.setParentInstanceId(parentInstanceId);
        bean.setRootInstanceId(rootInstanceId);
        bean.setLevel(level);
        bean.setProgress(0);
        bean.setPrompt("");
        bean.setState(State.WAITING.getValue());
        bean.setJobType(JobType.SUB_JOB.getValue());
        bean.setUserguid(StringUtils.isEmpty((String)userguid) ? "_system_" : userguid);
        bean.setUsername(StringUtils.isEmpty((String)username) ? "_system_" : username);
        bean.setStarttime(System.currentTimeMillis());
        bean.setEndtime(-1L);
        bean.setJobId(jobId);
        bean.setResult(1);
        bean.setResultMessage("");
        bean.setNode(null);
        bean.setInstanceName(jobTitle);
        bean.setPublishNode(self);
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            conn.setAutoCommit(false);
            try {
                JobInstancesDAO.insertInstance(conn, bean);
                JobLinkSourceDao.addJobSource(conn, instanceId, linkSource);
                conn.commit();
            }
            catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public void subJobFired(String instanceId, String quartzInstance) throws Exception {
        this.jobFired(instanceId, quartzInstance);
    }

    public void subJobExcepted(String instanceId, String exceptMessage) throws SQLException {
        this.jobExcepted(instanceId, exceptMessage);
    }

    public void jobExcepted(String instanceId, String exceptMessage) throws SQLException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            JobInstancesDAO.updateInstanceState(conn, instanceId, State.FINISHED.getValue(), 4, exceptMessage, System.currentTimeMillis());
            JobCacheProviderManager.getInstance().getJobInstanceBeanCache().remove(instanceId);
        }
    }

    public void jobTimeout(String instanceId, String exceptMessage) throws SQLException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            JobInstancesDAO.updateInstanceState(conn, instanceId, State.FINISHED.getValue(), 5, exceptMessage, System.currentTimeMillis());
            JobCacheProviderManager.getInstance().getJobInstanceBeanCache().remove(instanceId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void jobFinished(String instanceId, int jobResult, String resultMessage) throws SQLException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            conn.setAutoCommit(false);
            try {
                JobInstancesDAO.updateInstanceState(conn, instanceId, State.FINISHED.getValue(), jobResult, resultMessage, System.currentTimeMillis());
                JobInstancesDAO.updateInstanceProgress(conn, instanceId, 100);
                conn.commit();
                JobCacheProviderManager.getInstance().getJobInstanceBeanCache().remove(instanceId);
            }
            catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public void jobCanceled(String instanceId) throws SQLException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            JobInstancesDAO.updateInstanceState(conn, instanceId, State.FINISHED.getValue(), 2, "\u4efb\u52a1\u53d6\u6d88", System.currentTimeMillis());
            JobCacheProviderManager.getInstance().getJobInstanceBeanCache().remove(instanceId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateJobProgress(String instanceId, int progress, String prompt) throws SQLException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            if (JobCacheProviderManager.getInstance().getJobInstanceBeanCache().exist(instanceId)) {
                long now;
                JobInstanceBean bean = (JobInstanceBean)JobCacheProviderManager.getInstance().getJobInstanceBeanCache().get(instanceId);
                if (bean != null) {
                    bean.setProgress(progress);
                    bean.setPrompt(prompt);
                    JobCacheProviderManager.getInstance().getJobInstanceBeanCache().put(instanceId, bean);
                }
                if ((now = System.currentTimeMillis()) - this.lastCommitTime > 2000L || progress > 95) {
                    JobInstancesDAO.updateInstanceProgress(conn, instanceId, progress, prompt);
                    this.lastCommitTime = now;
                }
            } else {
                JobInstancesDAO.updateInstanceProgress(conn, instanceId, progress, prompt);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateJobProgress(String instanceId, int progress) throws SQLException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            if (JobCacheProviderManager.getInstance().getJobInstanceBeanCache().exist(instanceId)) {
                long now;
                JobInstanceBean bean = (JobInstanceBean)JobCacheProviderManager.getInstance().getJobInstanceBeanCache().get(instanceId);
                if (bean != null) {
                    bean.setProgress(progress);
                    JobCacheProviderManager.getInstance().getJobInstanceBeanCache().put(instanceId, bean);
                }
                if ((now = System.currentTimeMillis()) - this.lastCommitTime > 2000L || progress > 95) {
                    JobInstancesDAO.updateInstanceProgress(conn, instanceId, progress);
                    this.lastCommitTime = now;
                }
            } else {
                JobInstancesDAO.updateInstanceProgress(conn, instanceId, progress);
            }
        }
    }

    public void cancelJob(String instanceId) throws SQLException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            JobInstancesDAO.cancelInstance(conn, instanceId);
            JobCacheProviderManager.getInstance().getJobInstanceBeanCache().remove(instanceId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateJobBackstage(String instanceId, boolean backstage) throws SQLException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            JobInstancesDAO.updateInstanceBackstage(conn, instanceId, backstage);
            JobCacheProviderManager.getInstance().getJobInstanceBeanCache().remove(instanceId);
        }
    }

    public void terminateAllUnfinishedInstance() throws Exception {
        List allNode = DistributionManager.getInstance().getAllNode();
        Node self = DistributionManager.getInstance().self();
        ArrayList<Node> aliveNodes = new ArrayList<Node>();
        for (Node node : allNode) {
            if (!node.isAlive() || node.getName().equals(self.getName())) continue;
            aliveNodes.add(node);
        }
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            if (aliveNodes.isEmpty()) {
                JobInstancesDAO.setUnfinishedInstanceToTerminated(conn);
                JobRunningDao.deleteAll(conn);
            } else {
                JobInstancesDAO.setUnfinishedInstanceToTerminated(conn, self.getName());
                JobRunningDao.deleteByNode(conn, self.getName());
            }
        }
    }

    public int getJobInstanceCount(String jobTitle, List<String> categorys, List<State> states, List<String> execNodes, String execUserTitle, String execUserGuid, Long startTime, Long endTime) throws JobsException {
        return this.getJobInstanceCount(jobTitle, categorys, states, execNodes, execUserTitle, execUserGuid, startTime, endTime, null, null, null);
    }

    public int getJobInstanceCount(String jobTitle, List<String> categorys, List<State> states, List<String> execNodes, String execUserTitle, String execUserGuid, Long startTime, Long endTime, String filter) throws JobsException {
        return this.getJobInstanceCount(jobTitle, categorys, states, execNodes, execUserTitle, execUserGuid, startTime, endTime, null, null, filter);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getJobInstanceCount(String jobTitle, List<String> categorys, List<State> states, List<String> execNodes, String execUserTitle, String execUserGuid, Long startTime, Long endTime, String queryField1, String queryField2, String filter) throws JobsException {
        int n;
        if (categorys == null || categorys.size() == 0) {
            categorys = JobFactoryManager.getInstance().getCategoryIds();
        }
        Connection conn = GlobalConnectionProviderManager.getConnection();
        try {
            n = JobInstancesDAO.queryInstanceCount(conn, jobTitle, categorys, states, execNodes, execUserTitle, execUserGuid, startTime, endTime, queryField1, queryField2, filter);
        }
        catch (Throwable throwable) {
            try {
                conn.close();
                throw throwable;
            }
            catch (SQLException e) {
                throw new JobsException("\u6839\u636e\u6761\u4ef6\u67e5\u8be2\u4efb\u52a1\u5b9e\u4f8b\u603b\u6761\u6570\u5931\u8d25", e);
            }
        }
        conn.close();
        return n;
    }

    public List<JobInstanceBean> getJobInstances(String jobTitle, List<String> categorys, List<State> states, List<String> execNodes, String execUserTitle, String execUserGuid, Long startTime, Long endTime, int start, int end) throws JobsException {
        return this.getJobInstances(jobTitle, categorys, states, execNodes, execUserTitle, execUserGuid, startTime, endTime, start, end, null, null, null);
    }

    public List<JobInstanceBean> getJobInstances(String jobTitle, List<String> categorys, List<State> states, List<String> execNodes, String execUserTitle, String execUserGuid, Long startTime, Long endTime, int start, int end, String filter) throws JobsException {
        return this.getJobInstances(jobTitle, categorys, states, execNodes, execUserTitle, execUserGuid, startTime, endTime, start, end, null, null, filter);
    }

    public List<JobInstanceBean> getJobInstances(String jobTitle, List<String> categorys, List<State> states, List<String> execNodes, String execUserTitle, String execUserGuid, Long startTime, Long endTime, int start, int end, String queryField1, String queryField2, String filter) throws JobsException {
        List<JobInstanceBean> list;
        block9: {
            if (categorys == null || categorys.isEmpty()) {
                categorys = JobFactoryManager.getInstance().getCategoryIds();
            }
            Connection conn = GlobalConnectionProviderManager.getConnection();
            try {
                List<JobInstanceBean> instanceBeans = JobInstancesDAO.queryInstances(conn, jobTitle, categorys, states, execNodes, execUserTitle, execUserGuid, startTime, endTime, start, end, queryField1, queryField2, filter);
                list = this.getSubJobInstanceBeans(conn, instanceBeans);
                if (conn == null) break block9;
            }
            catch (Throwable throwable) {
                try {
                    if (conn != null) {
                        try {
                            conn.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (SQLException e) {
                    throw new JobsException(e);
                }
            }
            conn.close();
        }
        return list;
    }

    private List<JobInstanceBean> getRealtimeJobInstances(String jobTitle, List<String> categorys, List<State> states, List<String> execNodes, String execUserTitle, String execUserGuid, Long startTime, Long endTime, int start, int end, String queryField1, String queryField2, String filter) throws JobsException {
        List<JobInstanceBean> list;
        block8: {
            Connection conn = GlobalConnectionProviderManager.getConnection();
            try {
                List<JobInstanceBean> instanceBeans = JobInstancesDAO.queryRealtimeInstances(conn, jobTitle, categorys, states, execNodes, execUserTitle, execUserGuid, startTime, endTime, start, end, queryField1, queryField2, filter);
                list = this.getSubJobInstanceBeans(conn, instanceBeans);
                if (conn == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if (conn != null) {
                        try {
                            conn.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (JobsException | SQLException e) {
                    throw new JobsException(e);
                }
            }
            conn.close();
        }
        return list;
    }

    public List<JobInstanceBean> getJobInstances(List<String> instanceIDS) throws JobsException {
        List<JobInstanceBean> list;
        block8: {
            Connection conn = GlobalConnectionProviderManager.getConnection();
            try {
                List<JobInstanceBean> instanceBeans = JobInstancesDAO.queryInstancesByInstanceIds(conn, instanceIDS);
                list = this.getSubJobInstanceBeans(conn, instanceBeans);
                if (conn == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if (conn != null) {
                        try {
                            conn.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (SQLException e) {
                    throw new JobsException(e);
                }
            }
            conn.close();
        }
        return list;
    }

    private List<JobInstanceBean> getSubJobInstanceBeans(Connection conn, List<JobInstanceBean> instanceBeans) throws SQLException, JobsException {
        if (instanceBeans != null && !instanceBeans.isEmpty()) {
            ArrayList<String> instanceIds = new ArrayList<String>();
            for (JobInstanceBean instanceBean : instanceBeans) {
                instanceIds.add(instanceBean.getInstanceId());
            }
            Map<String, List<JobInstanceBean>> subMap = JobInstancesDAO.getAllSubInstanceByParentInstanceId(conn, instanceIds);
            JobExecResultManager jobExecResultManager = new JobExecResultManager();
            List<JobExecResultBean> resultListByInstanceIds = jobExecResultManager.getResultListByInstanceIds(instanceIds);
            for (JobExecResultBean execResultBean : resultListByInstanceIds) {
                boolean exist = OSSManager.getInstance().exist(execResultBean.getGuid());
                execResultBean.setExist(exist);
            }
            for (JobInstanceBean instanceBean : instanceBeans) {
                instanceBean.setChildrenInstance(subMap.get(instanceBean.getInstanceId()));
                ArrayList<JobExecResultBean> execResultBeans = new ArrayList<JobExecResultBean>();
                for (JobExecResultBean execResultBean : resultListByInstanceIds) {
                    if (!instanceBean.getInstanceId().equals(execResultBean.getInstanceId())) continue;
                    execResultBeans.add(execResultBean);
                }
                instanceBean.setExecResults(execResultBeans);
            }
        }
        return instanceBeans;
    }

    public int getRealTimeJobInstanceCount(String jobTitle, List<String> groups, List<State> states, List<String> execNodes, String execUserTitle, String execUserGuid, Long startTime, Long endTime) throws JobsException {
        return this.getRealtimeJobInstanceCount(jobTitle, this.getCategoryIdByGroupId(groups), states, execNodes, execUserTitle, execUserGuid, startTime, endTime, null, null, null);
    }

    public int getRealTimeJobInstanceCount(String jobTitle, List<String> groups, List<State> states, List<String> execNodes, String execUserTitle, String execUserGuid, Long startTime, Long endTime, String filter) throws JobsException {
        return this.getRealtimeJobInstanceCount(jobTitle, this.getCategoryIdByGroupId(groups), states, execNodes, execUserTitle, execUserGuid, startTime, endTime, null, null, filter);
    }

    public int getRealTimeJobInstanceCount(String jobTitle, List<String> groups, List<State> states, List<String> execNodes, String execUserTitle, String execUserGuid, Long startTime, Long endTime, String queryField1, String queryField2, String filter) throws JobsException {
        return this.getRealtimeJobInstanceCount(jobTitle, this.getCategoryIdByGroupId(groups), states, execNodes, execUserTitle, execUserGuid, startTime, endTime, queryField1, queryField2, filter);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int getRealtimeJobInstanceCount(String jobTitle, List<String> categorys, List<State> states, List<String> execNodes, String execUserTitle, String execUserGuid, Long startTime, Long endTime, String queryField1, String queryField2, String filter) throws JobsException {
        int n;
        if (categorys == null || categorys.isEmpty()) {
            categorys = JobFactoryManager.getInstance().getCategoryIds();
        }
        Connection conn = GlobalConnectionProviderManager.getConnection();
        try {
            n = JobInstancesDAO.queryRealtimeInstanceCount(conn, jobTitle, categorys, states, execNodes, execUserTitle, execUserGuid, startTime, endTime, queryField1, queryField2, filter);
        }
        catch (Throwable throwable) {
            try {
                conn.close();
                throw throwable;
            }
            catch (SQLException e) {
                throw new JobsException("\u6839\u636e\u6761\u4ef6\u67e5\u8be2\u4efb\u52a1\u5b9e\u4f8b\u603b\u6761\u6570\u5931\u8d25", e);
            }
        }
        conn.close();
        return n;
    }

    public List<JobInstanceBean> getRealTimeJobInstances(String jobTitle, List<String> groups, List<State> states, List<String> execNodes, String execUserTitle, String execUserGuid, Long startTime, Long endTime, int start, int end) throws JobsException {
        return this.getRealTimeJobInstances(jobTitle, groups, states, execNodes, execUserTitle, execUserGuid, startTime, endTime, start, end, null, null, null);
    }

    public List<JobInstanceBean> getRealTimeJobInstances(String jobTitle, List<String> groups, List<State> states, List<String> execNodes, String execUserTitle, String execUserGuid, Long startTime, Long endTime, int start, int end, String filter) throws JobsException {
        return this.getRealTimeJobInstances(jobTitle, groups, states, execNodes, execUserTitle, execUserGuid, startTime, endTime, start, end, null, null, filter);
    }

    public List<JobInstanceBean> getRealTimeJobInstances(String jobTitle, List<String> groups, List<State> states, List<String> execNodes, String execUserTitle, String execUserGuid, Long startTime, Long endTime, int start, int end, String queryField1, String queryField2, String filter) throws JobsException {
        List<JobInstanceBean> instanceBeans = this.getRealtimeJobInstances(jobTitle, this.getCategoryIdByGroupId(groups), states, execNodes, execUserTitle, execUserGuid, startTime, endTime, start, end, queryField1, queryField2, filter);
        for (JobInstanceBean instanceBean : instanceBeans) {
            instanceBean.setGroupId(RealTimeJobFactory.getRealTimeJobGroupByCategoryId(instanceBean.getCategoryId()));
            instanceBean.setCategoryId("com.jiuqi.bi.jobs.realtime");
        }
        return instanceBeans;
    }

    public List<JobInstanceBean> getRealTimeJobInstances(List<String> instanceIDS) throws JobsException {
        List<JobInstanceBean> instanceBeans = this.getJobInstances(instanceIDS);
        for (JobInstanceBean instanceBean : instanceBeans) {
            instanceBean.setGroupId(RealTimeJobFactory.getRealTimeJobGroupByCategoryId(instanceBean.getCategoryId()));
            instanceBean.setCategoryId("com.jiuqi.bi.jobs.realtime");
        }
        return instanceBeans;
    }

    private List<String> getCategoryIdByGroupId(List<String> groups) {
        if (groups == null || groups.size() == 0) {
            groups = RealTimeJobFactory.getInstance().getGroupIds();
        }
        ArrayList<String> categoryIds = new ArrayList<String>();
        for (String group : groups) {
            categoryIds.add(RealTimeJobFactory.getRealTimeJobCategoryId(group));
        }
        return categoryIds;
    }

    public JobInstanceBean getInstanceById(String instanceId) throws JobsException {
        JobInstanceBean jobInstanceBean;
        block10: {
            JobInstanceBean instance = (JobInstanceBean)JobCacheProviderManager.getInstance().getJobInstanceBeanCache().get(instanceId);
            if (null != instance) {
                return instance;
            }
            Connection conn = GlobalConnectionProviderManager.getConnection();
            try {
                instance = JobInstancesDAO.queryInstanceByGuid(conn, instanceId);
                if (null != instance) {
                    JobCacheProviderManager.getInstance().getJobInstanceBeanCache().put(instanceId, instance);
                }
                jobInstanceBean = instance;
                if (conn == null) break block10;
            }
            catch (Throwable throwable) {
                try {
                    if (conn != null) {
                        try {
                            conn.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (SQLException e) {
                    throw new JobsException("\u67e5\u8be2\u4efb\u52a1\u5b9e\u4f8b\u51fa\u9519", e);
                }
            }
            conn.close();
        }
        return jobInstanceBean;
    }

    public List<JobInstanceBean> getAllSubInstanceByRootInstanceId(String rootInstanceId) throws JobsException {
        List<JobInstanceBean> list;
        block8: {
            rootInstanceId = Html.cleanName((String)rootInstanceId, (char[])new char[0]);
            Connection conn = GlobalConnectionProviderManager.getConnection();
            try {
                list = JobInstancesDAO.getAllSubInstanceByRootInstanceId(conn, rootInstanceId);
                if (conn == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if (conn != null) {
                        try {
                            conn.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (SQLException e) {
                    throw new JobsException("\u6839\u636erootInstanceId\u67e5\u8be2\u6240\u6709\u5b50\u4efb\u52a1\u5931\u8d25", e);
                }
            }
            conn.close();
        }
        return list;
    }

    public void delete(List<String> instanceIds) throws JobsException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            JobInstancesDAO.delete(conn, instanceIds);
            JobLinkSourceDao.deleteJobSource(conn, instanceIds);
            JobInstanceDetailDAO.deleteInstanceDetail(conn, instanceIds);
            JobCacheProviderManager.getInstance().getJobInstanceBeanCache().removes(instanceIds);
        }
        catch (SQLException e) {
            throw new JobsException("\u5220\u9664\u5b9e\u4f8b\u5931\u8d25", e);
        }
    }

    public void deleteNoExistJobInstance() throws JobsException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            JobInstanceDetailDAO.deleteNoSourceDetail(conn);
        }
        catch (SQLException e) {
            throw new JobsException("\u5220\u9664\u5b9e\u4f8b\u5931\u8d25", e);
        }
    }

    public List<JobInstanceBean> getAllSubInstanceListByParentInstanceId(List<String> parentInstanceId) throws JobsException {
        List<JobInstanceBean> list;
        block8: {
            Connection conn = GlobalConnectionProviderManager.getConnection();
            try {
                list = JobInstancesDAO.getAllSubInstanceListByParentInstanceId(conn, parentInstanceId);
                if (conn == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if (conn != null) {
                        try {
                            conn.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (SQLException e) {
                    throw new JobsException("\u6839\u636eFIELD_PARENT_INSTANCEID\u67e5\u8be2\u6240\u6709\u5b50\u4efb\u52a1\u5931\u8d25", e);
                }
            }
            conn.close();
        }
        return list;
    }

    public List<JobInstanceBean> getAllSubInstanceListByRootInstanceIds(List<String> rootInstanceIds) throws JobsException {
        List<JobInstanceBean> list;
        block8: {
            Connection conn = GlobalConnectionProviderManager.getConnection();
            try {
                list = JobInstancesDAO.getAllSubInstanceListByRootInstanceIds(conn, rootInstanceIds);
                if (conn == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if (conn != null) {
                        try {
                            conn.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (SQLException e) {
                    throw new JobsException("\u6839\u636erootInstanceIds\u67e5\u8be2\u6240\u6709\u5b50\u4efb\u52a1\u5931\u8d25", e);
                }
            }
            conn.close();
        }
        return list;
    }

    public boolean instanceStateIsCanceled(String instanceId, String jobTitle) throws JobsException {
        JobInstanceBean instanceBean;
        try {
            instanceBean = this.getInstanceById(instanceId);
        }
        catch (JobsException e) {
            throw new JobsException("\u67e5\u8be2\u4efb\u52a1\u5b9e\u4f8b[" + jobTitle + "]\u5931\u8d25", e);
        }
        if (instanceBean == null) {
            throw new JobsException("\u5373\u65f6\u4efb\u52a1[" + jobTitle + "]\u8fd0\u884c\u5931\u8d25\uff0cinstance\u5b9e\u4f8b\u4e3a\u7a7a");
        }
        return instanceBean.getResult() == 2;
    }

    public boolean isLinkSourcesRunning(List<String> linkSource) throws JobsException {
        boolean bl;
        block8: {
            Connection conn = GlobalConnectionProviderManager.getConnection();
            try {
                bl = JobLinkSourceDao.isLinkSourcesRunning(conn, linkSource);
                if (conn == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if (conn != null) {
                        try {
                            conn.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (SQLException e) {
                    throw new JobsException("\u5220\u9664\u5b9e\u4f8b\u5931\u8d25", e);
                }
            }
            conn.close();
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getExpireInstanceCount(String userGuid, JobKind type, long expireTimePoint) throws JobsException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            if (type == JobKind.SCHEDULED_JOB) {
                int n = JobInstancesDAO.getExpireScheduleInstanceCount(conn, JobType.SCHEDULED_JOB, userGuid, expireTimePoint);
                return n;
            }
            if (type == JobKind.MANUAL_SCHEDULED_JOB) {
                int n = JobInstancesDAO.getExpireScheduleInstanceCount(conn, JobType.MANUAL_JOB, userGuid, expireTimePoint);
                return n;
            }
            if (type == JobKind.REALTIME_JOB) {
                int n = JobInstancesDAO.getExpireRealtimeInstanceCount(conn, userGuid, expireTimePoint);
                return n;
            }
            if (type == JobKind.SIMPLE_BACKEND_JOB) {
                int n = JobInstancesDAO.getExpireSimpleInstanceCount(conn, userGuid, expireTimePoint);
                return n;
            }
            if (type == JobKind.REMOTE_JOB) {
                int n = JobInstancesDAO.getExpireRemoteInstanceCount(conn, userGuid, expireTimePoint);
                return n;
            }
            int n = 0;
            return n;
        }
        catch (SQLException e) {
            throw new JobsException("\u67e5\u8be2n\u6761\u7528\u6237\u8fc7\u671f\u7684\u4efb\u52a1\u5b9e\u4f8b\u6761\u6570\u5931\u8d25", e);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<JobInstanceBean> getExpireInstance(int topN, String userGuid, JobKind type, long expireTimePoint) throws JobsException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            if (type == JobKind.SCHEDULED_JOB) {
                List<JobInstanceBean> list = JobInstancesDAO.getExpireScheduleInstance(conn, JobType.SCHEDULED_JOB, topN, userGuid, expireTimePoint);
                return list;
            }
            if (type == JobKind.MANUAL_SCHEDULED_JOB) {
                List<JobInstanceBean> list = JobInstancesDAO.getExpireScheduleInstance(conn, JobType.MANUAL_JOB, topN, userGuid, expireTimePoint);
                return list;
            }
            if (type == JobKind.REALTIME_JOB) {
                List<JobInstanceBean> list = JobInstancesDAO.getExpireRealtimeInstance(conn, topN, userGuid, expireTimePoint);
                return list;
            }
            if (type == JobKind.SIMPLE_BACKEND_JOB) {
                List<JobInstanceBean> list = JobInstancesDAO.getExpireSimpleInstance(conn, topN, userGuid, expireTimePoint);
                return list;
            }
            if (type == JobKind.REMOTE_JOB) {
                List<JobInstanceBean> list = JobInstancesDAO.getExpireRemoteInstance(conn, topN, userGuid, expireTimePoint);
                return list;
            }
            ArrayList<JobInstanceBean> arrayList = new ArrayList<JobInstanceBean>();
            return arrayList;
        }
        catch (SQLException e) {
            throw new JobsException("\u67e5\u8be2n\u6761\u7528\u6237\u8fc7\u671f\u7684\u4efb\u52a1\u5b9e\u4f8b\u5931\u8d25", e);
        }
    }

    public void setInstanceDetail(String instanceId, String detail) throws JobsException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            JobInstanceDetailDAO.updateInstanceDetail(conn, instanceId, detail);
        }
        catch (SQLException e) {
            throw new JobsException("\u6dfb\u52a0\u4efb\u52a1\u8be6\u60c5\u5931\u8d25", e);
        }
    }

    public void setInstanceParams(String instanceId, Map<String, String> params) throws JobsException {
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            jsonObject.put(entry.getKey(), (Object)entry.getValue());
        }
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            JobInstanceDetailDAO.addInstanceDetailWithParams(conn, instanceId, jsonObject.toString());
        }
        catch (SQLException e) {
            throw new JobsException("\u6dfb\u52a0\u4efb\u52a1\u8be6\u60c5\u5931\u8d25", e);
        }
    }

    public void insertRunning(String instanceId, int jobType, String jobGroup) throws JobsException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            JobRunningDao.insert(conn, instanceId, jobType, jobGroup);
        }
        catch (SQLException e) {
            throw new JobsException("\u6dfb\u52a0\u4efb\u52a1\u8fd0\u884c\u5931\u8d25", e);
        }
    }

    public void deleteRunning(String instanceId) throws JobsException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            JobRunningDao.delete(conn, instanceId);
        }
        catch (SQLException e) {
            throw new JobsException("\u5220\u9664\u4efb\u52a1\u8fd0\u884c\u5931\u8d25", e);
        }
    }

    public int selectRunningCount(int jobType, String jobGroup) throws JobsException {
        int n;
        block8: {
            Connection conn = GlobalConnectionProviderManager.getConnection();
            try {
                n = JobRunningDao.selectCount(conn, jobType, jobGroup);
                if (conn == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if (conn != null) {
                        try {
                            conn.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (SQLException e) {
                    throw new JobsException("\u67e5\u8be2\u4efb\u52a1\u8fd0\u884c\u5931\u8d25", e);
                }
            }
            conn.close();
        }
        return n;
    }

    public int selectFiredTriggersRunningCount(String sql, String jobGroup, String schedName) throws JobsException {
        int n;
        block8: {
            Connection conn = GlobalConnectionProviderManager.getConnection();
            try {
                n = JobRunningDao.selectFiredTriggersRunningCount(conn, sql, schedName, "com.jiuqi.bi.jobs.realtime." + jobGroup);
                if (conn == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if (conn != null) {
                        try {
                            conn.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (SQLException e) {
                    throw new JobsException("\u67e5\u8be2\u4efb\u52a1\u8fd0\u884c\u5931\u8d25", e);
                }
            }
            conn.close();
        }
        return n;
    }

    public boolean allowRealtimeRunning(String jobGroup) {
        return this.allowRealtimeRunning(jobGroup, 0);
    }

    public boolean allowRealtimeRunning(String jobGroup, int offset) {
        return this.allowRealtimeRunning(jobGroup, offset, null, null);
    }

    public boolean allowRealtimeRunning(String jobGroup, int offset, String sql, String schedName) {
        IImmediatelyJobStorageMiddleware storageMiddleware = ImmediatelyJobManager.getInstance().getStorageMiddleware();
        int maxConcurrentJobCount = storageMiddleware.getMaxConcurrentJobCount(jobGroup);
        int i = offset;
        try {
            i += this.selectRunningCount(JobType.REALTIME_JOB.getValue(), jobGroup);
        }
        catch (JobsException e) {
            logger.error(e.getMessage(), e);
        }
        if (maxConcurrentJobCount > 0) {
            if (i < maxConcurrentJobCount && sql != null) {
                try {
                    int running = this.selectFiredTriggersRunningCount(sql, jobGroup, schedName);
                    return running < maxConcurrentJobCount;
                }
                catch (Exception e) {
                    logger.error("\u67e5\u8be2\u6846\u67b6\u70b9\u706b\u8868\u62a5\u9519 " + sql, e);
                }
            }
            return i < maxConcurrentJobCount;
        }
        Class<AbstractRealTimeJob> realTimeJobClazzByGroup = RealTimeJobFactory.getInstance().getRealTimeJobClazzByGroup(jobGroup);
        if (realTimeJobClazzByGroup == null) {
            return true;
        }
        int maxConcurrency = realTimeJobClazzByGroup.getAnnotation(RealTimeJob.class).defaultMaxConcurrency();
        if (maxConcurrency > 0) {
            return i < maxConcurrency;
        }
        return true;
    }

    public List<JobGroupByInfo> getAllRunningJobInfo() throws JobsException {
        List<JobGroupByInfo> list;
        block9: {
            Connection conn = GlobalConnectionProviderManager.getConnection();
            try {
                List<String> groups = RealTimeJobFactory.getInstance().getGroupIds();
                ArrayList<String> categorys = new ArrayList<String>();
                for (String group : groups) {
                    categorys.add(RealTimeJobFactory.getRealTimeJobCategoryId(group));
                }
                list = JobInstancesDAO.getAllJobGroupByInfo(conn, categorys);
                if (conn == null) break block9;
            }
            catch (Throwable throwable) {
                try {
                    if (conn != null) {
                        try {
                            conn.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (SQLException e) {
                    throw new JobsException("\u67e5\u8be2\u4efb\u52a1\u5206\u7ec4\u6570\u91cf\u5931\u8d25", e);
                }
            }
            conn.close();
        }
        return list;
    }

    public List<JobInstanceBean> getLastJobInfo() throws JobsException {
        List<JobInstanceBean> list;
        block9: {
            Connection conn = GlobalConnectionProviderManager.getConnection();
            try {
                List<String> groups = RealTimeJobFactory.getInstance().getGroupIds();
                ArrayList<String> categorys = new ArrayList<String>();
                for (String group : groups) {
                    categorys.add(RealTimeJobFactory.getRealTimeJobCategoryId(group));
                }
                list = JobInstancesDAO.getLastJobInfo(conn, categorys);
                if (conn == null) break block9;
            }
            catch (Throwable throwable) {
                try {
                    if (conn != null) {
                        try {
                            conn.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (SQLException e) {
                    throw new JobsException("\u67e5\u8be2\u4efb\u52a1\u5206\u7ec4\u6570\u91cf\u5931\u8d25", e);
                }
            }
            conn.close();
        }
        return list;
    }
}

