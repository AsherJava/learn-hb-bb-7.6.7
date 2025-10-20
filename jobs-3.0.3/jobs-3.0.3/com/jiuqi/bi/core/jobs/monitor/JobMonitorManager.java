/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONObject
 */
package com.jiuqi.bi.core.jobs.monitor;

import com.jiuqi.bi.core.jobs.BaseFactory;
import com.jiuqi.bi.core.jobs.IJobCache;
import com.jiuqi.bi.core.jobs.JobCacheProviderManager;
import com.jiuqi.bi.core.jobs.JobFactoryManager;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.base.BaseJobFactoryManager;
import com.jiuqi.bi.core.jobs.bean.JobInstanceBean;
import com.jiuqi.bi.core.jobs.core.SchedulerCommitPool;
import com.jiuqi.bi.core.jobs.dao.JobInstanceDetailDAO;
import com.jiuqi.bi.core.jobs.dao.JobInstancesDAO;
import com.jiuqi.bi.core.jobs.monitor.PageContext;
import com.jiuqi.bi.core.jobs.monitor.State;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager;
import com.jiuqi.bi.util.StringUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobMonitorManager {
    public static final Logger LOGGER = LoggerFactory.getLogger(JobMonitorManager.class);
    private String categoryId = null;

    public JobMonitorManager(BaseFactory factory) {
        this.categoryId = factory.getJobCategoryId();
    }

    public JobMonitorManager(String categoryId) {
        this.categoryId = categoryId;
    }

    private JobMonitorManager() {
    }

    public static JobMonitorManager getInstance(String categoryId) {
        JobMonitorManager monitorManager = JobFactoryManager.getInstance().getMonitorManager(categoryId);
        if (monitorManager == null) {
            monitorManager = BaseJobFactoryManager.getInstance().getMonitorManager(categoryId);
        }
        if (monitorManager == null) {
            monitorManager = new JobMonitorManager();
            monitorManager.categoryId = categoryId;
        }
        return monitorManager;
    }

    public String getJobRunningInstanceID(String jobId) throws JobsException {
        JobInstanceBean bean = this.getLastJobInstance(jobId);
        if (bean == null || bean.getState() == State.FINISHED.getValue()) {
            return null;
        }
        return bean.getInstanceId();
    }

    public JobInstanceBean getLastJobInstance(String jobId) throws JobsException {
        JobInstanceBean jobInstanceBean;
        block8: {
            Connection conn = GlobalConnectionProviderManager.getConnection();
            try {
                jobInstanceBean = JobInstancesDAO.queryLastInstance(conn, jobId, this.categoryId);
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
                    throw new JobsException(e.getMessage(), e);
                }
            }
            conn.close();
        }
        return jobInstanceBean;
    }

    public List<JobInstanceBean> getUnfinishedJobInstances() throws JobsException {
        List<JobInstanceBean> list;
        block8: {
            Connection conn = GlobalConnectionProviderManager.getConnection();
            try {
                list = JobInstancesDAO.getUnfinishedInstances(conn);
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
                    throw new JobsException(e.getMessage(), e);
                }
            }
            conn.close();
        }
        return list;
    }

    public int getNumsBeforeTimeUseCache(long starttime, State state) throws JobsException {
        return this.getNumsBeforeTimeUseCache(starttime, state, 20);
    }

    public int getNumsBeforeTimeUseCache(long starttime, State state, int useSqlNum) throws JobsException {
        int n;
        block20: {
            int nums;
            Instant instant = Instant.ofEpochMilli(starttime);
            ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
            long minuteTimestamp = zonedDateTime.withSecond(0).withNano(0).toInstant().toEpochMilli();
            IJobCache cache = JobCacheProviderManager.getInstance().getJobLocationCache();
            String key = minuteTimestamp + "_" + state.getValue();
            Object numsOject = cache.get(key);
            if (numsOject != null && (nums = ((Integer)numsOject).intValue()) > useSqlNum) {
                String machineName = DistributionManager.getInstance().getMachineName();
                Object log = cache.get(machineName);
                if (log == null) {
                    cache.put(machineName, 1);
                    int mainTriggerCount = SchedulerCommitPool.getInstance().getMainTriggerCount();
                    int subJobTriggerCount = SchedulerCommitPool.getInstance().getSubJobTriggerCount();
                    int realTriggerCount = SchedulerCommitPool.getInstance().getRealTimeJobTriggerCount();
                    int queueSize = SchedulerCommitPool.getInstance().getQueueSize();
                    int waitTriggerCount = 0;
                    Map<Object, Object> firedTriggersCount = new HashMap();
                    try (Connection conn = GlobalConnectionProviderManager.getConnection();){
                        waitTriggerCount = JobInstancesDAO.getTriggersCount(conn);
                        firedTriggersCount = JobInstancesDAO.getFiredTriggersCount(conn);
                    }
                    StringBuilder builder = new StringBuilder();
                    if (null != firedTriggersCount) {
                        for (String string : firedTriggersCount.keySet()) {
                            int nodeNums = (Integer)firedTriggersCount.get(string);
                            builder.append(string).append(":").append(nodeNums).append(",");
                        }
                    }
                    LOGGER.warn("{} \u670d\u52a1\u6392\u961f\u6570\u91cf\u5df2\u7ecf\u8fbe\u5230\u8b66\u6212\u503c\uff1a{},\u5f53\u524d\u670d\u52a1\u5f85\u63d0\u4ea4\u7684mainTrigger:{},subTrigger:{},realTimeTrigger:{},\u961f\u5217\u957f\u5ea6:{},waitTrigger:{},friedTrigger:{}", machineName, nums, mainTriggerCount, subJobTriggerCount, realTriggerCount, queueSize, waitTriggerCount, builder.toString());
                }
                return nums;
            }
            Connection conn = GlobalConnectionProviderManager.getConnection();
            try {
                int numsBeforeTime = JobInstancesDAO.getNumsBeforeTime(conn, starttime, state);
                if (numsBeforeTime > useSqlNum) {
                    cache.put(key, numsBeforeTime);
                }
                n = numsBeforeTime;
                if (conn == null) break block20;
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
                    throw new JobsException(e.getMessage(), e);
                }
            }
            conn.close();
        }
        return n;
    }

    @Deprecated
    public int getNumsBeforeTime(long starttime, State state) throws JobsException {
        int n;
        block8: {
            Connection conn = GlobalConnectionProviderManager.getConnection();
            try {
                n = JobInstancesDAO.getNumsBeforeTime(conn, starttime, state);
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
                    throw new JobsException(e.getMessage(), e);
                }
            }
            conn.close();
        }
        return n;
    }

    public static String getInstanceDetail(String instanceId) throws JobsException {
        String string;
        block8: {
            Connection conn = GlobalConnectionProviderManager.getConnection();
            try {
                string = JobInstanceDetailDAO.selectInstanceDetail(conn, instanceId);
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
                    throw new JobsException("\u6dfb\u52a0\u4efb\u52a1\u8be6\u60c5\u5931\u8d25", e);
                }
            }
            conn.close();
        }
        return string;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Map<String, String> getInstanceParams(String instanceId) throws JobsException {
        HashMap<String, String> map = new HashMap<String, String>();
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            String s = JobInstanceDetailDAO.selectInstanceParams(conn, instanceId);
            if (s == null) {
                HashMap<String, String> hashMap2 = map;
                return hashMap2;
            }
            JSONObject jsonObject = new JSONObject(s);
            for (String string : jsonObject.keySet()) {
                map.put(string, jsonObject.optString(string));
            }
            HashMap<String, String> hashMap = map;
            return hashMap;
        }
        catch (SQLException e) {
            throw new JobsException("\u6dfb\u52a0\u4efb\u52a1\u8be6\u60c5\u5931\u8d25", e);
        }
    }

    public JobInstanceBean getJobInstanceByGuid(String instanceGuid) throws JobsException {
        return this.getJobInstanceByGuid(instanceGuid, false);
    }

    public JobInstanceBean getJobInstanceByGuid(String instanceGuid, boolean simple) throws JobsException {
        JobInstanceBean jobInstanceBean;
        block10: {
            JobInstanceBean instance = (JobInstanceBean)JobCacheProviderManager.getInstance().getJobInstanceBeanCache().get(instanceGuid);
            if (null != instance) {
                return instance;
            }
            simple = false;
            Connection conn = GlobalConnectionProviderManager.getConnection();
            try {
                instance = JobInstancesDAO.queryInstanceByGuid(conn, instanceGuid, simple);
                if (null != instance) {
                    JobCacheProviderManager.getInstance().getJobInstanceBeanCache().put(instanceGuid, instance);
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
                    throw new JobsException(e.getMessage(), e);
                }
            }
            conn.close();
        }
        return jobInstanceBean;
    }

    public Map<String, JobInstanceBean> getJobInstanceByGuid(List<String> instanceGuids, boolean simple) throws JobsException {
        try {
            HashMap<String, JobInstanceBean> result = new HashMap<String, JobInstanceBean>();
            ArrayList<String> olds = new ArrayList<String>();
            olds.addAll(instanceGuids);
            Map<String, Object> map = JobCacheProviderManager.getInstance().getJobInstanceBeanCache().gets(olds);
            for (String key : map.keySet()) {
                result.put(key, (JobInstanceBean)map.get(key));
            }
            if (map.size() == instanceGuids.size()) {
                return result;
            }
            Iterator iterator = olds.iterator();
            while (iterator.hasNext()) {
                String key;
                key = (String)iterator.next();
                if (!map.containsKey(key) || map.get(key) == null) continue;
                iterator.remove();
            }
            if (!olds.isEmpty()) {
                simple = false;
                try (Connection conn = GlobalConnectionProviderManager.getConnection();){
                    Map<String, JobInstanceBean> stringJobInstanceBeanMap = JobInstancesDAO.queryInstanceByGuid(conn, olds, simple);
                    for (String key : stringJobInstanceBeanMap.keySet()) {
                        JobCacheProviderManager.getInstance().getJobInstanceBeanCache().put(key, stringJobInstanceBeanMap.get(key));
                        result.put(key, stringJobInstanceBeanMap.get(key));
                    }
                }
            }
            return result;
        }
        catch (SQLException e) {
            throw new JobsException(e.getMessage(), e);
        }
    }

    public List<JobInstanceBean> getJobInstanceHistories(String jobId, PageContext pc) throws JobsException {
        List<JobInstanceBean> list;
        block8: {
            Connection conn = GlobalConnectionProviderManager.getConnection();
            try {
                list = JobInstancesDAO.queryAllInstance(conn, jobId, this.categoryId, pc.getStart(), pc.getEnd());
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
                    throw new JobsException(e.getMessage(), e);
                }
            }
            conn.close();
        }
        return list;
    }

    public List<JobInstanceBean> getJobInstanceHistoriesByFolder(String folder, PageContext pc, String filter) throws JobsException {
        List<JobInstanceBean> list;
        block8: {
            Connection conn = GlobalConnectionProviderManager.getConnection();
            try {
                list = JobInstancesDAO.queryAllInstanceByFolder(conn, folder, pc.getStart(), pc.getEnd(), filter);
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
                    throw new JobsException(e.getMessage(), e);
                }
            }
            conn.close();
        }
        return list;
    }

    public int getFolderJobInstanceHistoriesCount(String folder, String filter) throws JobsException {
        int n;
        block8: {
            Connection conn = GlobalConnectionProviderManager.getConnection();
            try {
                n = JobInstancesDAO.getAllInstanceByFolderCount(conn, folder, filter);
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
                    throw new JobsException(e.getMessage(), e);
                }
            }
            conn.close();
        }
        return n;
    }

    public int getJobInstanceHistoryiesCount(String jobId) throws JobsException {
        int n;
        block8: {
            Connection conn = GlobalConnectionProviderManager.getConnection();
            try {
                n = JobInstancesDAO.getAllInstanceCount(conn, jobId, this.categoryId);
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
                    throw new JobsException(e.getMessage(), e);
                }
            }
            conn.close();
        }
        return n;
    }

    public int getSubJobCount(String instanceId) throws JobsException {
        int n;
        block8: {
            Connection conn = GlobalConnectionProviderManager.getConnection();
            try {
                n = JobInstancesDAO.getSubInstanceCount(conn, instanceId);
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
                    throw new JobsException(e.getMessage(), e);
                }
            }
            conn.close();
        }
        return n;
    }

    public static List<JobInstanceBean> getSubJobInstance(String instanceId) throws JobsException {
        List<JobInstanceBean> list;
        block8: {
            Connection conn = GlobalConnectionProviderManager.getConnection();
            try {
                list = JobInstancesDAO.getSubInstance(conn, instanceId);
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
                    throw new JobsException(e.getMessage(), e);
                }
            }
            conn.close();
        }
        return list;
    }

    public JobInstanceBean getParentJobInstance(String instanceId) throws JobsException {
        JobInstanceBean parentInstance = new JobInstanceBean();
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            JobInstanceBean instanceBean = JobInstancesDAO.getInstance(conn, instanceId);
            if (StringUtils.isNotEmpty((String)instanceBean.getParentInstanceId())) {
                parentInstance = JobInstancesDAO.getInstance(conn, instanceBean.getParentInstanceId());
            }
        }
        catch (SQLException e) {
            throw new JobsException(e.getMessage(), e);
        }
        return parentInstance;
    }
}

