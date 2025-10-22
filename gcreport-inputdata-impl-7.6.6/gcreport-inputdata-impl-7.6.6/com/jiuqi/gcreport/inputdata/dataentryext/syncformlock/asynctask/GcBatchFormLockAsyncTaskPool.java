/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType
 *  com.jiuqi.np.asynctask.AsyncTaskPool
 *  com.jiuqi.np.sql.type.Convert
 *  com.jiuqi.nr.dataentry.asynctask.BatchFormLockAsyncTaskPool
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.gcreport.inputdata.dataentryext.syncformlock.asynctask;

import com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType;
import com.jiuqi.np.asynctask.AsyncTaskPool;
import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.nr.dataentry.asynctask.BatchFormLockAsyncTaskPool;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class GcBatchFormLockAsyncTaskPool
implements AsyncTaskPool {
    @Autowired(required=false)
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Autowired(required=false)
    private BatchFormLockAsyncTaskPool batchFormLockAsyncTaskPool;
    public static final String QUEUE_PREFIX = "QUEUE_";

    public String getType() {
        return GcAsyncTaskPoolType.ASYNCTASK_GC_BATCH_FORM_LOCK.getName();
    }

    public Integer getQueueSize() {
        try {
            return this.getQueueSizeByNvwaSystemOption();
        }
        catch (Exception e) {
            return 100;
        }
    }

    public Integer getParallelSize() {
        try {
            return this.getParallelSizeByNvwaSystemOption();
        }
        catch (Exception e) {
            return 10;
        }
    }

    public Boolean isConfig() {
        return true;
    }

    private Integer getQueueSizeByNvwaSystemOption() {
        String config;
        if (Objects.nonNull(this.iNvwaSystemOptionService) && Objects.nonNull(this.batchFormLockAsyncTaskPool) && this.batchFormLockAsyncTaskPool.isConfig().booleanValue() && StringUtils.hasLength(config = this.iNvwaSystemOptionService.get("async-task-queue-declare", QUEUE_PREFIX.concat(this.batchFormLockAsyncTaskPool.getType())))) {
            try {
                return Convert.toInt((String)config);
            }
            catch (Exception e) {
                return 100;
            }
        }
        return 100;
    }

    private Integer getParallelSizeByNvwaSystemOption() {
        String config;
        if (Objects.nonNull(this.iNvwaSystemOptionService) && Objects.nonNull(this.batchFormLockAsyncTaskPool) && this.batchFormLockAsyncTaskPool.isConfig().booleanValue() && StringUtils.hasLength(config = this.iNvwaSystemOptionService.get("async-task-parallel-declare", this.batchFormLockAsyncTaskPool.getType()))) {
            try {
                return Convert.toInt((String)config);
            }
            catch (Exception e) {
                return 10;
            }
        }
        return 10;
    }
}

