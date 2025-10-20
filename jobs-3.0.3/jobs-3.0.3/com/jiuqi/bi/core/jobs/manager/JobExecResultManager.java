/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager
 *  com.jiuqi.bi.util.Guid
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.core.jobs.manager;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.bean.JobExecResultBean;
import com.jiuqi.bi.core.jobs.bean.JobInstanceBean;
import com.jiuqi.bi.core.jobs.dao.JobExecResultDao;
import com.jiuqi.bi.core.jobs.manager.JobOperationManager;
import com.jiuqi.bi.core.jobs.oss.AbstractJobResult;
import com.jiuqi.bi.core.jobs.oss.JobByteResult;
import com.jiuqi.bi.core.jobs.oss.JobFileResult;
import com.jiuqi.bi.core.jobs.oss.OSSManager;
import com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager;
import com.jiuqi.bi.util.Guid;
import com.jiuqi.bi.util.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JobExecResultManager {
    private JobOperationManager jobOperationManager = new JobOperationManager();

    public List<JobExecResultBean> saveResultFile(String instanceId, List<AbstractJobResult> jobResults) throws JobsException {
        if (StringUtils.isEmpty((String)instanceId)) {
            throw new JobsException("\u4efb\u52a1\u5b9e\u4f8bid\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (jobResults == null) {
            return new ArrayList<JobExecResultBean>();
        }
        ArrayList<JobExecResultBean> fileBeans = new ArrayList<JobExecResultBean>();
        try {
            for (AbstractJobResult jobResult : jobResults) {
                String key = Guid.newGuid();
                long byteSize = 0L;
                if (jobResult instanceof JobFileResult) {
                    String filePath = ((JobFileResult)jobResult).getFilePath();
                    File file = new File(filePath);
                    byteSize = file.length();
                    try (FileInputStream inputStream = new FileInputStream(file);){
                        OSSManager.getInstance().upload(key, inputStream, byteSize);
                    }
                } else if (jobResult instanceof JobByteResult) {
                    byte[] bytes = ((JobByteResult)jobResult).getBytes();
                    byteSize = bytes.length;
                    try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);){
                        OSSManager.getInstance().upload(key, inputStream, byteSize);
                    }
                } else {
                    throw new JobsException("\u672a\u77e5\u7684AbstractJobResult\u7c7b\u578b");
                }
                JobExecResultBean fileBean = new JobExecResultBean(key, instanceId, OSSManager.getInstance().getBucketId(), jobResult);
                fileBean.setByteSize(byteSize);
                fileBeans.add(fileBean);
            }
            this.add(fileBeans);
        }
        catch (Exception e) {
            for (JobExecResultBean fileBean : fileBeans) {
                OSSManager.getInstance().delete(fileBean.getGuid());
            }
            throw new JobsException("\u4e0a\u4f20\u6587\u4ef6\u5931\u8d25\uff0c\u5f00\u59cb\u5220\u9664\u5df2\u4e0a\u4f20\u6587\u4ef6:" + e.getMessage(), e);
        }
        return fileBeans;
    }

    public void add(List<JobExecResultBean> beans) throws JobsException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            JobExecResultDao.insert(conn, beans);
        }
        catch (SQLException throwables) {
            throw new JobsException(throwables);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public JobExecResultBean getResultByGuid(String resultGuid) throws JobsException {
        JobExecResultBean jobExecResultBean;
        Connection conn = GlobalConnectionProviderManager.getConnection();
        try {
            jobExecResultBean = JobExecResultDao.getResultByGuid(conn, resultGuid);
        }
        catch (Throwable throwable) {
            try {
                conn.close();
                throw throwable;
            }
            catch (SQLException throwables) {
                throw new JobsException(throwables);
            }
        }
        conn.close();
        return jobExecResultBean;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<JobExecResultBean> getAllResultByRootJobInstance(String rootInsGuid) throws JobsException {
        Map<String, List<JobExecResultBean>> resultMap;
        List<JobInstanceBean> instances = this.jobOperationManager.getAllSubInstanceByRootInstanceId(rootInsGuid);
        ArrayList<String> allInsranceId = new ArrayList<String>();
        allInsranceId.add(rootInsGuid);
        for (JobInstanceBean instance : instances) {
            allInsranceId.add(instance.getInstanceId());
        }
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            resultMap = JobExecResultDao.getResultByInstanceIds(conn, allInsranceId);
        }
        catch (SQLException throwables) {
            throw new JobsException("\u6839\u636e\u4efb\u52a1\u7684\u6839\u5b9e\u4f8bGuid\u83b7\u53d6\u4efb\u52a1\u7684\u6240\u6709\u6267\u884c\u7ed3\u679c\u5931\u8d25", throwables);
        }
        Iterator<Map.Entry<String, List<JobExecResultBean>>> iterator = resultMap.entrySet().iterator();
        ArrayList<JobExecResultBean> result = new ArrayList<JobExecResultBean>();
        while (iterator.hasNext()) {
            Map.Entry<String, List<JobExecResultBean>> entry = iterator.next();
            result.addAll((Collection<JobExecResultBean>)entry.getValue());
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<JobExecResultBean> getResultListByInstanceIds(List<String> instanceIds) throws JobsException {
        List<JobExecResultBean> list;
        Connection conn = GlobalConnectionProviderManager.getConnection();
        try {
            list = JobExecResultDao.getResultListByInstanceIds(conn, instanceIds);
        }
        catch (Throwable throwable) {
            try {
                conn.close();
                throw throwable;
            }
            catch (SQLException throwables) {
                throw new JobsException(throwables);
            }
        }
        conn.close();
        return list;
    }

    public void deleteByInstanceIds(List<String> instanceIds) throws JobsException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            JobExecResultDao.deleteByInstanceIds(conn, instanceIds);
        }
        catch (SQLException throwables) {
            throw new JobsException(throwables);
        }
    }

    public void deleteNoExistInInstanceTableJob() throws JobsException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            JobExecResultDao.deleteNoExistInInstanceTableJob(conn);
        }
        catch (SQLException throwables) {
            throw new JobsException(throwables);
        }
    }
}

