/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.nr.datascheme.api.core.CalResult
 *  com.jiuqi.nvwa.definition.common.ProgressItem
 */
package com.jiuqi.nr.datascheme.internal.job;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.nr.datascheme.api.core.CalResult;
import com.jiuqi.nr.datascheme.internal.dao.IDataSchemeCalResultDao;
import com.jiuqi.nr.datascheme.internal.entity.DataSchemeCalResultDO;
import com.jiuqi.nvwa.definition.common.ProgressItem;
import java.time.Instant;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalZbAsyncTaskMonitor
implements AsyncTaskMonitor {
    public static final String TASK_ID = "NR_ASYNC_TASK_DATA_SCHEME_CAL_ZB";
    public static final String TASK_POOL_TASK = "DATA_SCHEME_CAL_ZB";
    private static final Logger log = LoggerFactory.getLogger(CalZbAsyncTaskMonitor.class);
    private NedisCache cache;
    private String dataSchemeKey;
    private String id;
    private IDataSchemeCalResultDao dataSchemeCalResultDao;

    public CalZbAsyncTaskMonitor() {
    }

    public CalZbAsyncTaskMonitor(String dataSchemeKey, NedisCache cache, IDataSchemeCalResultDao dataSchemeCalResultDao) {
        this.dataSchemeKey = dataSchemeKey;
        this.cache = cache;
        this.dataSchemeCalResultDao = dataSchemeCalResultDao;
        this.initProgress();
    }

    private void initProgress() {
        if (this.cache != null) {
            this.id = UUID.randomUUID().toString();
            this.cache.evict(this.id);
            ProgressItem progressItem = new ProgressItem();
            progressItem.setProgressId(this.id);
            progressItem.addStepTitle("\u51c6\u5907");
            progressItem.addStepTitle("\u5904\u7406\u6307\u6807");
            progressItem.addStepTitle("\u5b8c\u6210");
            progressItem.setMessage("\u5f00\u59cb");
            progressItem.setCurrentStep(0);
            progressItem.setCurrentProgess(0);
            this.cache.put(this.id, (Object)progressItem);
        }
    }

    public String getId() {
        return this.id;
    }

    public String getTaskId() {
        return TASK_ID;
    }

    public String getTaskPoolTask() {
        return TASK_POOL_TASK;
    }

    public void progressAndMessage(double progress, String message) {
        log.debug("\u5904\u7406\u8ba1\u7b97\u6307\u6807\u8fdb\u5ea6: {}, {}", (Object)progress, (Object)message);
        if (this.cache == null) {
            return;
        }
        ProgressItem progressItem = (ProgressItem)this.cache.get(this.id, ProgressItem.class);
        if (progressItem != null) {
            progressItem.setMessage(message);
            if (progress < 0.5) {
                progressItem.setCurrentStep(0);
            } else if (progress < 1.0) {
                progressItem.setCurrentStep(1);
                progressItem.setMessage("\u6b63\u5728\u5904\u7406");
            } else {
                progressItem.setCurrentStep(2);
                progressItem.setFinished(true);
                progressItem.setMessage("\u5904\u7406\u5b8c\u6210");
                this.finish("\u6210\u529f", message);
            }
            this.cache.put(this.id, (Object)progressItem);
        }
    }

    public boolean isCancel() {
        return false;
    }

    public void finish(String result, Object detail) {
        if (this.dataSchemeCalResultDao != null) {
            DataSchemeCalResultDO calResult = new DataSchemeCalResultDO();
            calResult.setKey(UUID.randomUUID().toString());
            calResult.setCalResult(CalResult.SUCCESS);
            calResult.setDataSchemeKey(this.dataSchemeKey);
            calResult.setUpdateTime(Instant.now());
            calResult.setMessage(result);
            try {
                this.dataSchemeCalResultDao.saveResult(calResult);
            }
            catch (Exception e) {
                log.error("\u4fdd\u5b58\u8ba1\u7b97\u7ed3\u679c\u5931\u8d25", e);
            }
        }
    }

    public void canceling(String result, Object detail) {
    }

    public void canceled(String result, Object detail) {
    }

    public void error(String result, Throwable t) {
        ProgressItem progressItem;
        if (this.cache != null && (progressItem = (ProgressItem)this.cache.get(this.dataSchemeKey, ProgressItem.class)) != null) {
            progressItem.setMessage(result);
            progressItem.setFailed(true);
        }
        if (this.dataSchemeCalResultDao != null) {
            DataSchemeCalResultDO calResult = new DataSchemeCalResultDO();
            calResult.setKey(UUID.randomUUID().toString());
            calResult.setCalResult(CalResult.FAIL);
            calResult.setDataSchemeKey(this.dataSchemeKey);
            calResult.setUpdateTime(Instant.now());
            calResult.setMessage("\u5931\u8d25");
            try {
                this.dataSchemeCalResultDao.saveResult(calResult);
            }
            catch (Exception e) {
                log.error("\u4fdd\u5b58\u8ba1\u7b97\u7ed3\u679c\u5931\u8d25", e);
            }
        }
    }

    public boolean isFinish() {
        return false;
    }
}

