/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.asynctask.AsyncTaskFuture
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.dataservice.core.asynctask;

import com.jiuqi.nr.common.asynctask.AsyncTaskFuture;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;
import org.springframework.util.Assert;

public interface IAsyncTaskFeture<R> {
    default public Optional<String> getResult(String taskId) {
        Assert.notNull((Object)taskId, "taskId is must not be null!");
        AsyncTaskFuture taskFeture = this.getAsyncTaskFeture();
        if (taskFeture != null) {
            return Optional.ofNullable(taskFeture.getResult(taskId));
        }
        return Optional.empty();
    }

    default public Optional<R> getDetail(String taskId) {
        Assert.notNull((Object)taskId, "taskId is must not be null!");
        AsyncTaskFuture taskFeture = this.getAsyncTaskFeture();
        if (taskFeture != null) {
            Object detail = taskFeture.getDetail(taskId, this.getParamterClass());
            return Optional.ofNullable(detail);
        }
        return Optional.empty();
    }

    default public AsyncTaskInfo getTaskInfo(String taskId) {
        Assert.notNull((Object)taskId, "taskId is must not be null!");
        AsyncTaskFuture taskFeture = this.getAsyncTaskFeture();
        if (taskFeture != null) {
            return taskFeture.getTaskInfo(taskId);
        }
        return new AsyncTaskInfo();
    }

    default public AsyncTaskFuture getAsyncTaskFeture() {
        return (AsyncTaskFuture)BeanUtil.getBean(AsyncTaskFuture.class);
    }

    default public Class<? extends R> getParamterClass() {
        return (Class)((ParameterizedType)((Class)this.getClass().getGenericInterfaces()[0]).getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }
}

