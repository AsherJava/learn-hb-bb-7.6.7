/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskTypeCollecter
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  org.quartz.Job
 *  org.quartz.JobExecutionContext
 *  org.quartz.JobExecutionException
 */
package com.jiuqi.np.asynctask.impl.service;

import com.jiuqi.np.asynctask.AsyncTaskTypeCollecter;
import com.jiuqi.np.asynctask.dao.AsyncTaskDao;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class DeleteHistoryDataPlanTaskRunner
implements Job {
    private Logger logger = LogFactory.getLogger(this.getClass());
    @Autowired
    private AsyncTaskDao dao;
    @Autowired
    private AsyncTaskTypeCollecter collecter;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        this.logger.info("\u5f00\u59cb\u6267\u884c\u5220\u9664\u5f02\u6b65\u4efb\u52a1\u5386\u53f2\u6570\u636e");
        long currentTimeMillis = System.currentTimeMillis();
        try {
            List byTypeTasks = this.collecter.getClearDataByTypeTasks();
            List byTaskIdTasks = this.collecter.getClearDataByTaskIdTasks();
            if (byTypeTasks.size() > 0) {
                for (String taskType : byTypeTasks) {
                    this.collecter.getExecutorByType(taskType).cleanHistoryData();
                }
            }
            if (byTaskIdTasks.size() > 0) {
                for (String taskType : byTaskIdTasks) {
                    List<String> taskIds = this.dao.queryTaskIdsToClear(taskType, currentTimeMillis);
                    if (null == taskIds) continue;
                    this.collecter.getExecutorByType(taskType).cleanHistoryDataByTaskId(taskIds);
                }
            }
            this.dao.insertHistoryData(currentTimeMillis);
            this.dao.deleteHistoryData(currentTimeMillis);
            this.dao.deleteHistoryTableData(currentTimeMillis);
        }
        catch (Exception e) {
            this.logger.info("\u5220\u9664\u5f02\u6b65\u4efb\u52a1\u5386\u53f2\u6570\u636e\u9519\u8bef\uff1a" + e.getMessage());
        }
        this.logger.info("\u5220\u9664\u5f02\u6b65\u4efb\u52a1\u5386\u53f2\u6570\u636e\u6267\u884c\u5b8c\u6bd5");
    }
}

