/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.dispatch.core.ITaskContext
 *  com.jiuqi.nvwa.dispatch.core.TaskContextBuilder
 *  com.jiuqi.nvwa.dispatch.core.TaskException
 *  com.jiuqi.nvwa.dispatch.core.submit.ITaskDispatcher
 */
package com.jiuqi.nvwa.datav.dashboard.engine.cache;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.datav.dashboard.engine.cache.ICacheDashboardRenderProvider;
import com.jiuqi.nvwa.dispatch.core.ITaskContext;
import com.jiuqi.nvwa.dispatch.core.TaskContextBuilder;
import com.jiuqi.nvwa.dispatch.core.TaskException;
import com.jiuqi.nvwa.dispatch.core.submit.ITaskDispatcher;

public class DashboardRenderCacheManager {
    public static final String DISPATCH_TASK_TYPE = "dashboard-render-cache";
    private static final DashboardRenderCacheManager instance = new DashboardRenderCacheManager();

    private DashboardRenderCacheManager() {
    }

    public static DashboardRenderCacheManager getInstance() {
        return instance;
    }

    public ICacheDashboardRenderProvider getCacheProvider(String sessionId, boolean create) throws TaskException {
        ITaskContext taskContext;
        if (StringUtils.isEmpty((String)sessionId)) {
            throw new TaskException("sessionId\u4e0d\u80fd\u4e3a\u7a7a");
        }
        ITaskDispatcher dispatcher = (ITaskDispatcher)SpringBeanUtils.getBean(ITaskDispatcher.class);
        ICacheDashboardRenderProvider cache = (ICacheDashboardRenderProvider)dispatcher.findService(taskContext = new TaskContextBuilder().taskType(DISPATCH_TASK_TYPE).taskId(this.buildTaskId(sessionId)).build(), ICacheDashboardRenderProvider.class);
        if (cache == null && create) {
            return (ICacheDashboardRenderProvider)dispatcher.createService(taskContext, ICacheDashboardRenderProvider.class);
        }
        return cache;
    }

    public void removeDashboardRenderCache(String sessionId) throws TaskException {
        ITaskDispatcher dispatcher = (ITaskDispatcher)SpringBeanUtils.getBean(ITaskDispatcher.class);
        ITaskContext taskContext = new TaskContextBuilder().taskType(DISPATCH_TASK_TYPE).taskId(this.buildTaskId(sessionId)).build();
        dispatcher.destoryService(taskContext);
    }

    private String buildTaskId(String sessionId) throws TaskException {
        if (StringUtils.isEmpty((String)sessionId)) {
            throw new TaskException("sessionId\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String userId = NpContextHolder.getContext().getUserId();
        if (StringUtils.isEmpty((String)userId)) {
            throw new TaskException("\u7528\u6237\u4fe1\u606f\u4e0d\u80fd\u4e3a\u7a7a");
        }
        return userId + ":" + sessionId;
    }
}

