/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.bi.core.jobs.bean.JobInstanceBean
 *  com.jiuqi.bi.core.jobs.manager.JobOperationManager
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJobManager
 *  com.jiuqi.bi.core.jobs.realtime.bean.RealTimeJobBean
 *  com.jiuqi.bi.core.jobs.realtime.core.RealTimeJobFactory
 *  com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobRunner
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.AsyncTaskArgsparser
 *  com.jiuqi.np.asynctask.AsyncTaskBufferQueue
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskManagerPub
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.AsyncTaskTypeCollecter
 *  com.jiuqi.np.asynctask.NpAsyncTaskExecutor
 *  com.jiuqi.np.asynctask.NpContextParam
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.event.PublishTaskEvent
 *  com.jiuqi.np.asynctask.exception.NpAsyncTaskExecption
 *  com.jiuqi.np.asynctask.exception.OutOfQueueException
 *  com.jiuqi.np.asynctask.exception.TaskExsitsException
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextOrganization
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.log.comm.LogTraceIDUtil
 *  com.jiuqi.nr.common.util.JsonUtil
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.context.request.RequestAttributes
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
 */
package com.jiuqi.np.asynctask.impl.service;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.bean.JobInstanceBean;
import com.jiuqi.bi.core.jobs.manager.JobOperationManager;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJobManager;
import com.jiuqi.bi.core.jobs.realtime.bean.RealTimeJobBean;
import com.jiuqi.bi.core.jobs.realtime.core.RealTimeJobFactory;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobRunner;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.AsyncTaskArgsparser;
import com.jiuqi.np.asynctask.AsyncTaskBufferQueue;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskManagerPub;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.AsyncTaskTypeCollecter;
import com.jiuqi.np.asynctask.NpAsyncTaskExecutor;
import com.jiuqi.np.asynctask.NpContextParam;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.dao.AsyncTaskDao;
import com.jiuqi.np.asynctask.event.PublishTaskEvent;
import com.jiuqi.np.asynctask.exception.NpAsyncTaskExecption;
import com.jiuqi.np.asynctask.exception.OutOfQueueException;
import com.jiuqi.np.asynctask.exception.TaskExsitsException;
import com.jiuqi.np.asynctask.impl.AsyncTaskImpl;
import com.jiuqi.np.asynctask.impl.NpContextParamImpl;
import com.jiuqi.np.asynctask.impl.SimpleAsyncTaskMonitor;
import com.jiuqi.np.asynctask.impl.service.AsyncTaskExecutorDispatcherImpl;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextOrganization;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.log.comm.LogTraceIDUtil;
import com.jiuqi.nr.common.util.JsonUtil;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class AsyncTaskManagerImpl
implements AsyncTaskManager,
ApplicationEventPublisherAware {
    @Autowired
    private AsyncTaskDao dao;
    @Autowired
    private AsyncTaskBufferQueue bufferQueue;
    @Autowired
    private AsyncTaskTypeCollecter collecter;
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private AsyncTaskManagerPub publishPub;
    @Autowired(required=false)
    private AsyncTaskArgsparser asyncTaskArgsparser;
    @Value(value="${jiuqi.np.asynctask.nr-asynctask-frame.enable:false}")
    private boolean enableOldFrame;
    private final String SERVE_ID;
    private static String IMMEDIATELY_THTREADPOOL_NAME = "IMMEDIATELY_ASYNCTASKPOOL";
    private ThreadPoolExecutor THTREADPOOL_EXECUTOR;
    private final Logger logger = LoggerFactory.getLogger(AsyncTaskManagerImpl.class);
    private static final String IP_HEADER_FORWARDED_FOR = "X-Forwarded-For";
    private static final String IP_HEADER_REMOTE_ADDR = "X-Real-IP";
    private static final String TASKKEY = "taskkey";
    private static final String FORMSCHEMEKEY = "formschemekey";
    private static final String NULLTASKKEY = "NULLTASKKEY";
    private static final String NULLFROMSCHEME = "NULLFROMSCHEME";

    public AsyncTaskManagerImpl(String serveId, Integer coreSize, Integer MaxSize, Integer blockingQueueSize) {
        this.SERVE_ID = serveId;
        if (this.enableOldFrame) {
            this.THTREADPOOL_EXECUTOR = new ThreadPoolExecutor(coreSize, MaxSize, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(blockingQueueSize), new AsyncTaskExecutorDispatcherImpl.NpAsyncTaskThreadFactory(IMMEDIATELY_THTREADPOOL_NAME), new ThreadPoolExecutor.CallerRunsPolicy());
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    public String publishTask(Object args, String taskPoolType) throws NpAsyncTaskExecption {
        return this.publishTaskWithDependImpl(args, taskPoolType, null);
    }

    public String publishTask(NpRealTimeTaskInfo npRealTimeTaskInfo) throws NpAsyncTaskExecption {
        try {
            RealTimeJob realTimeJob = npRealTimeTaskInfo.getAbstractRealTimeJob().getClass().getAnnotation(RealTimeJob.class);
            return this.publishRealTimeTask(npRealTimeTaskInfo, realTimeJob.groupTitle(), false, false);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return null;
        }
    }

    public String publishTask(NpRealTimeTaskInfo npRealTimeTaskInfo, String taskPoolType) throws NpAsyncTaskExecption {
        try {
            return this.publishTask(npRealTimeTaskInfo);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return null;
        }
    }

    public String publishTaskWithDepend(@RequestBody Object args, String taskPoolType, String dependedTaskId) throws NpAsyncTaskExecption {
        return this.publishTaskWithDependImpl(args, taskPoolType, dependedTaskId);
    }

    public String publishRealTimeTask(NpRealTimeTaskInfo npRealTimeTaskInfo, String taskPoolType, boolean isUniqueTask, boolean immediately) throws InstantiationException, IllegalAccessException, JobsException {
        String taskId;
        String taskType;
        AbstractRealTimeJob abstractRealTimeJob = npRealTimeTaskInfo.getAbstractRealTimeJob();
        Map params = abstractRealTimeJob.getParams();
        this.setContextToRealTimeParams(params);
        params.put("NR_ARGS", npRealTimeTaskInfo.getArgs());
        abstractRealTimeJob.setParams(params);
        abstractRealTimeJob.setQueryField1(npRealTimeTaskInfo.getTaskKey());
        abstractRealTimeJob.setQueryField2(npRealTimeTaskInfo.getFormSchemeKey());
        abstractRealTimeJob.setTitle(taskPoolType);
        abstractRealTimeJob.setUserGuid(NpContextHolder.getContext().getUserId());
        abstractRealTimeJob.setUserName(NpContextHolder.getContext().getUserName());
        if (!isUniqueTask && immediately) {
            taskType = "\u7acb\u5373\u6267\u884c";
            taskId = ImmediatelyJobRunner.getInstance().commit(abstractRealTimeJob);
        } else {
            taskType = "\u8c03\u5ea6\u6267\u884c";
            taskId = RealTimeJobManager.getInstance().commit(abstractRealTimeJob);
            if (taskId != null) {
                JobInstanceBean jobInstanceBean = RealTimeJobManager.getInstance().getMonitorManager().getJobInstanceByGuid(taskId);
                this.dao.insertTaskCompleteFlag(taskId, jobInstanceBean.getStarttime());
            }
        }
        this.logger.debug("\u53d1\u5e03\u201c{}\u201d\u7684\u5f02\u6b65\u4efb\u52a1\u6210\u529f\uff01\u4efb\u52a1\u53c2\u6570\u5982\u4e0b\uff1a\n\u5f02\u6b65\u4efb\u52a1\u7c7b\u578b\uff1a{}\uff1b\u5f02\u6b65\u4efb\u52a1ID\uff1a{}\uff1b\u4efb\u52a1key\uff1a{}\uff1b\u62a5\u8868\u65b9\u6848key\uff1a{}\u3002", taskType, taskPoolType, taskId, npRealTimeTaskInfo.getTaskKey(), npRealTimeTaskInfo.getFormSchemeKey());
        return taskId;
    }

    public String publishTaskWithDependImpl(Object args, String taskPoolType, String dependedTaskId) throws NpAsyncTaskExecption {
        return this.publishTaskImpl(args, taskPoolType, dependedTaskId, null);
    }

    public String publishTaskImpl(Object args, String taskPoolType, String dependedTaskId, String dimensionIdentity) throws NpAsyncTaskExecption {
        if (!this.enableOldFrame) {
            throw new NpAsyncTaskExecption("\u5f53\u524d\u672a\u542f\u7528\u65e7\u7248\u672c\u5f02\u6b65\u4efb\u52a1\u6846\u67b6\uff01");
        }
        AsyncTaskImpl task = new AsyncTaskImpl();
        if (this.getFreeQueueSize(taskPoolType) == 0) {
            throw new OutOfQueueException("\u6392\u961f\u961f\u5217\u4e0d\u8db3\uff01");
        }
        String taskId = "nr-" + UUID.randomUUID();
        Map keyMap = Objects.nonNull(this.asyncTaskArgsparser) ? this.asyncTaskArgsparser.getTaskKeyAndFormSchemeKey(args) : new HashMap();
        String taskKey = (String)keyMap.get(TASKKEY);
        String formSchemeKey = (String)keyMap.get(FORMSCHEMEKEY);
        task.setTaskId(taskId);
        task.setTaskKey(taskKey == null ? NULLTASKKEY : taskKey);
        task.setFormSchemeKey(formSchemeKey == null ? NULLFROMSCHEME : formSchemeKey);
        task.setArgs(args);
        task.setTaskPoolType(taskPoolType);
        Instant currentTime = Instant.now();
        task.setCreateTime(currentTime);
        task.setWaitingTime(currentTime);
        task.setCreateUserId(NpContextHolder.getContext().getUserId());
        task.setState(TaskState.WAITING);
        task.setDependTaskId(dependedTaskId);
        task.setDimensionIdentify(dimensionIdentity);
        task.setContext(this.getContextParam());
        this.dao.insert(task);
        ArrayList<AsyncTaskImpl> list = new ArrayList<AsyncTaskImpl>();
        list.add(task);
        PublishTaskEvent event = new PublishTaskEvent();
        event.setTaskPoolType(taskPoolType);
        event.setTaskList(list);
        this.eventPublisher.publishEvent((ApplicationEvent)event);
        return taskId;
    }

    public String publishAndExecuteTask(Object args, String taskPoolType) throws NpAsyncTaskExecption {
        return this.publishAndExecuteTaskProxy(args, taskPoolType, null);
    }

    public String publishAndExecuteTaskProxy(Object args, String taskPoolType, String dependedTaskId) throws NpAsyncTaskExecption {
        if (!this.enableOldFrame) {
            throw new NpAsyncTaskExecption("\u5f53\u524d\u672a\u542f\u7528\u65e7\u7248\u672c\u5f02\u6b65\u4efb\u52a1\u6846\u67b6\uff01");
        }
        AsyncTaskImpl task = new AsyncTaskImpl();
        String taskId = "nr-" + UUID.randomUUID();
        Map keyMap = Objects.nonNull(this.asyncTaskArgsparser) ? this.asyncTaskArgsparser.getTaskKeyAndFormSchemeKey(args) : new HashMap();
        String taskKey = (String)keyMap.get(TASKKEY);
        String formSchemeKey = (String)keyMap.get(FORMSCHEMEKEY);
        task.setTaskId(taskId);
        task.setTaskKey(taskKey == null ? NULLTASKKEY : taskKey);
        task.setFormSchemeKey(formSchemeKey == null ? NULLFROMSCHEME : formSchemeKey);
        task.setArgs(args);
        task.setTaskPoolType(taskPoolType);
        Instant currentTime = Instant.now();
        task.setCreateTime(currentTime);
        task.setWaitingTime(currentTime);
        task.setProcessTime(currentTime);
        task.setEffectTime(currentTime);
        task.setEffectTimeLong(System.currentTimeMillis());
        task.setCreateUserId(NpContextHolder.getContext().getUserId());
        task.setState(TaskState.PROCESSING);
        task.setDependTaskId(dependedTaskId);
        task.setContext(this.getContextParam());
        task.setServeId(this.SERVE_ID);
        this.dao.insert(task);
        this.runExecutor(task);
        return taskId;
    }

    private void runExecutor(AsyncTask task) throws NpAsyncTaskExecption {
        String taskPoolType = task.getTaskPoolType();
        String taskId = task.getTaskId();
        NpContextParam context = (NpContextParam)task.getContext();
        NpAsyncTaskExecutor executor = this.collecter.getExecutorByType(taskPoolType);
        if (null == executor) {
            this.dao.updateErrorInfo(taskId, "\u4efb\u52a1\u6c60\"" + taskPoolType + "\"\u65e0\u6267\u884c\u5668", null, null);
        } else {
            try {
                this.THTREADPOOL_EXECUTOR.execute(() -> {
                    try {
                        NpContextHolder.setContext((NpContext)this.getNpContext(context));
                        SimpleAsyncTaskMonitor monitor = new SimpleAsyncTaskMonitor(this.dao, this.eventPublisher, taskId, taskPoolType);
                        this.logger.trace("\u3010\u5f02\u6b65\u4efb\u52a1\u6267\u884c\u3011: " + taskPoolType + "_" + taskId);
                        executor.execute(task.getArgs(), (AsyncTaskMonitor)monitor);
                        if (!monitor.isFinish()) {
                            monitor.finish("ASYNC_FORCE_FINISH", null);
                        }
                    }
                    catch (Throwable e) {
                        this.logger.error(taskPoolType + "_" + taskId + "\u5f02\u6b65\u4efb\u52a1\u4efb\u52a1\u6267\u884c\u9519\u8bef: " + e.getMessage(), e);
                        this.dao.updateErrorInfo(taskId, taskPoolType + "_" + taskId + "\u5f02\u6b65\u4efb\u52a1\u4efb\u52a1\u6267\u884c\u9519\u8bef: " + e.getMessage(), e, null);
                    }
                    finally {
                        NpContextHolder.clearContext();
                        DsContextHolder.clearContext();
                    }
                });
            }
            catch (Throwable e) {
                this.dao.updateErrorInfo(taskId, taskPoolType + "_" + taskId + "\u5f02\u6b65\u4efb\u52a1\u4efb\u52a1\u6267\u884c\u9519\u8bef: " + e.getMessage(), e, null);
            }
        }
    }

    public String publishToSplitQueueProxy(Object args, String taskPoolType, String dependedTaskId) {
        if (!this.enableOldFrame) {
            throw new NpAsyncTaskExecption("\u5f53\u524d\u672a\u542f\u7528\u65e7\u7248\u672c\u5f02\u6b65\u4efb\u52a1\u6846\u67b6\uff01");
        }
        AsyncTaskImpl task = new AsyncTaskImpl();
        String taskId = "nr-" + UUID.randomUUID();
        Map keyMap = Objects.nonNull(this.asyncTaskArgsparser) ? this.asyncTaskArgsparser.getTaskKeyAndFormSchemeKey(args) : new HashMap();
        String taskKey = (String)keyMap.get(TASKKEY);
        String formSchemeKey = (String)keyMap.get(FORMSCHEMEKEY);
        task.setTaskId(taskId);
        task.setTaskKey(taskKey == null ? NULLTASKKEY : taskKey);
        task.setFormSchemeKey(formSchemeKey == null ? NULLFROMSCHEME : formSchemeKey);
        task.setArgs(args);
        task.setTaskPoolType(taskPoolType);
        Instant currentTime = Instant.now();
        task.setCreateTime(currentTime);
        task.setWaitingTime(currentTime);
        task.setProcessTime(currentTime);
        task.setCreateUserId(NpContextHolder.getContext().getUserId());
        task.setState(TaskState.PROCESSING);
        task.setDependTaskId(dependedTaskId);
        task.setContext(this.getContextParam());
        this.dao.insert(task);
        this.bufferQueue.publish("np_asynctask_split_queue", taskId, taskPoolType, task.getPriority());
        return taskId;
    }

    public String publishToSplitQueue(Object args, String taskPoolType) throws NpAsyncTaskExecption {
        return this.publishToSplitQueueProxy(args, taskPoolType, null);
    }

    private NpContext getNpContext(NpContextParam contextParam) {
        NpContextImpl context = (NpContextImpl)NpContextHolder.createEmptyContext();
        context.setTenant(contextParam.getTenant());
        context.setIdentity(contextParam.getContextIdentity());
        context.setUser(contextParam.getContextUser());
        Locale local = contextParam.getLocal();
        if (local == null) {
            local = LocaleContextHolder.getLocale();
        }
        String ip = contextParam.getIp();
        context.setIp(ip);
        context.setLocale(local);
        context.setOrganization(contextParam.getOrganization());
        LogTraceIDUtil.setLogTraceId((String)contextParam.getTraceId());
        DsContext dsContext = contextParam.getDsContext();
        DsContextHolder.setDsContext((DsContext)dsContext);
        Map extensionMap = contextParam.getExtensionMap();
        if (extensionMap != null && extensionMap.size() > 0) {
            for (Map.Entry extensionItem : extensionMap.entrySet()) {
                final ContextExtension contextExtension = context.getExtension((String)extensionItem.getKey());
                Consumer<Map.Entry<String, Object>> consumer = new Consumer<Map.Entry<String, Object>>(){

                    @Override
                    public void accept(Map.Entry<String, Object> item) {
                        Serializable seriaValue = (Serializable)item.getValue();
                        contextExtension.put(item.getKey(), seriaValue);
                    }
                };
                ((ContextExtension)extensionItem.getValue()).apply((Consumer)consumer);
            }
        }
        return context;
    }

    public TaskState queryTaskState(String taskId) {
        return this.publishPub.queryTaskState(taskId);
    }

    public Integer queryLocation(String taskId) {
        return this.publishPub.queryLocation(taskId);
    }

    public Integer queryLocation(AsyncTask asyncTask) {
        return this.publishPub.queryLocation(asyncTask);
    }

    public Double queryProcess(String taskId) {
        return this.publishPub.queryProcess(taskId);
    }

    public String queryResult(String taskId) {
        return this.publishPub.queryResult(taskId);
    }

    public Object queryDetail(String taskId) {
        return this.publishPub.queryDetail(taskId);
    }

    public String queryDetailString(String taskId) {
        return this.publishPub.queryDetailString(taskId);
    }

    public Serializable querySerializableDetail(String taskId) throws NpAsyncTaskExecption {
        try {
            return (Serializable)this.publishPub.queryDetail(taskId);
        }
        catch (Exception e) {
            this.logger.error("\u67e5\u8be2\u53ef\u5e8f\u5217\u5316\u5f02\u6b65\u4efb\u52a1detail\u5931\u8d25\uff1a{}", (Object)e.getMessage(), (Object)e);
            throw new NpAsyncTaskExecption("\u67e5\u8be2\u53ef\u5e8f\u5217\u5316\u5f02\u6b65\u4efb\u52a1detail\u5931\u8d25\uff1a" + e.getMessage(), (Throwable)e);
        }
    }

    public String queryArgs(String taskId) {
        return this.publishPub.queryArgs(taskId);
    }

    public AsyncTask queryTask(String taskId) {
        return this.publishPub.queryTask(taskId);
    }

    public AsyncTask querySimpleTask(String taskId) {
        return this.publishPub.querySimpleTask(taskId);
    }

    public Map<String, AsyncTask> batchQuerySimpleTask(List<String> taskIds) {
        return this.dao.batchQuerySimpleTasks(taskIds);
    }

    public List<AsyncTask> queryTaskToDo() {
        return this.publishPub.queryTaskToDo();
    }

    public List<AsyncTask> queryTaskToDoWithoutClob() {
        return this.publishPub.queryTaskToDoWithoutClob();
    }

    public int completeTask(String taskId) {
        return this.publishPub.completeTask(taskId);
    }

    public void cancelTask(String taskId) {
        this.publishPub.cancelTask(taskId);
    }

    private NpContextParam getContextParam() {
        String ip;
        Locale local;
        ContextUser user;
        NpContext npContext = NpContextHolder.getContext();
        NpContextParamImpl context = new NpContextParamImpl();
        String userTenant = npContext.getTenant();
        context.setTenant(userTenant);
        context.setLoginDate(npContext.getLoginDate());
        ContextIdentity identity = npContext.getIdentity();
        if (identity != null) {
            context.setContextIdentity(identity);
        }
        if ((user = npContext.getUser()) != null) {
            context.setContextUser(user);
        }
        if ((local = npContext.getLocale()) != null) {
            context.setLocale(local);
        }
        if (StringUtils.isEmpty((String)(ip = npContext.getIp()))) {
            RequestAttributes requestAttr = RequestContextHolder.getRequestAttributes();
            ip = AsyncTaskManagerImpl.getClientIpAddress(requestAttr);
        }
        context.setIp(ip);
        ContextOrganization orginzation = npContext.getOrganization();
        if (orginzation != null) {
            context.setOrganization(orginzation);
        }
        DsContext dsContext = DsContextHolder.getDsContext();
        context.setDsContext(dsContext);
        context.setTraceId(LogTraceIDUtil.getLogTraceId());
        final HashMap<String, ContextExtension> extensionMap = new HashMap<String, ContextExtension>();
        Consumer<Map.Entry<String, ContextExtension>> consumer = new Consumer<Map.Entry<String, ContextExtension>>(){

            @Override
            public void accept(Map.Entry<String, ContextExtension> extension) {
                extensionMap.put(extension.getKey(), extension.getValue());
            }
        };
        npContext.applyExtensions((Consumer)consumer);
        if (extensionMap.size() > 0) {
            context.setExtensionMap(extensionMap);
        }
        return context;
    }

    private static String getClientIpAddress(RequestAttributes requestAttr) {
        if (requestAttr == null || requestAttr != null && !(requestAttr instanceof ServletRequestAttributes)) {
            return DistributionManager.getInstance().getIp().replace("-", "@");
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes)requestAttr;
        HttpServletRequest request = attributes.getRequest();
        String requestHeader = request.getHeader(IP_HEADER_FORWARDED_FOR);
        if (StringUtils.isNotEmpty((String)requestHeader) && !"unknown".equalsIgnoreCase(requestHeader)) {
            int index = requestHeader.indexOf(",");
            if (index != -1) {
                return requestHeader.substring(0, index);
            }
            return requestHeader;
        }
        requestHeader = request.getHeader(IP_HEADER_REMOTE_ADDR);
        if (StringUtils.isNotEmpty((String)requestHeader) && !"unknown".equalsIgnoreCase(requestHeader)) {
            return requestHeader;
        }
        return request.getRemoteAddr();
    }

    private Integer getFreeQueueSize(String taskPoolType) {
        Integer queueSize = this.collecter.getQueueSize(taskPoolType);
        if (null == queueSize) {
            throw new NpAsyncTaskExecption("\u4efb\u52a1\u6c60\"" + taskPoolType + "\"\u65e0\u6267\u884c\u5668");
        }
        if (queueSize < 0) {
            return -1;
        }
        List<AsyncTask> taskList = this.dao.queryByTaskPool(taskPoolType, TaskState.WAITING);
        return Math.max(0, queueSize - taskList.size());
    }

    public String publishUniqueTask(Object args, String taskPoolType, String dimensionIdentity) {
        if (!this.enableOldFrame) {
            throw new NpAsyncTaskExecption("\u5f53\u524d\u672a\u542f\u7528\u65e7\u7248\u672c\u5f02\u6b65\u4efb\u52a1\u6846\u67b6\uff01");
        }
        if (StringUtils.isEmpty((String)dimensionIdentity)) {
            return this.publishTask(args, taskPoolType);
        }
        String taskId = this.dao.isUniqueTaskExsits(taskPoolType, dimensionIdentity);
        if (!StringUtils.isEmpty((String)taskId)) {
            throw new TaskExsitsException(taskId, "\u5f53\u524d\u4efb\u52a1\u6b63\u5728\u6267\u884c\u4e2d\uff0c\u8bf7\u7b49\u5f85\u3002");
        }
        return this.publishUniqueProxy(args, taskPoolType, dimensionIdentity);
    }

    public String publishUniqueTask(NpRealTimeTaskInfo npRealTimeTaskInfo, String taskPoolType, String dimensionIdentity) {
        if (StringUtils.isEmpty((String)dimensionIdentity)) {
            return this.publishTask(npRealTimeTaskInfo, taskPoolType);
        }
        AbstractRealTimeJob abstractRealTimeJob = npRealTimeTaskInfo.getAbstractRealTimeJob();
        List linkSources = abstractRealTimeJob.getLinkSource();
        linkSources.add(dimensionIdentity);
        try {
            JobOperationManager jobOperationManager = new JobOperationManager();
            boolean isTaskRunning = jobOperationManager.isLinkSourcesRunning(abstractRealTimeJob.getLinkSource());
            if (isTaskRunning) {
                throw new TaskExsitsException("taskID", "\u5f53\u524d\u4efb\u52a1\u6b63\u5728\u6267\u884c\u4e2d\uff0c\u8bf7\u7b49\u5f85\u3002");
            }
            return this.publishRealTimeTask(npRealTimeTaskInfo, taskPoolType, true, false);
        }
        catch (TaskExsitsException t) {
            this.logger.error(t.getMessage(), t);
            throw new TaskExsitsException(t.getMessage(), (Throwable)t);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return null;
        }
    }

    public String publishUniqueProxy(Object args, String taskPoolType, String dimensionIdentity) {
        return this.publishTaskImpl(args, taskPoolType, null, dimensionIdentity);
    }

    public Map<String, Object> queryDetails(List<String> taskIds) {
        return this.publishPub.queryDetails(taskIds);
    }

    public Map<String, String> queryDetailStrings(List<String> taskIds) {
        return this.publishPub.queryDetailStrings(taskIds);
    }

    public String queryTaskKey(String taskId) {
        return this.dao.queryTaskKey(taskId);
    }

    public String queryFormSchemeKey(String taskId) {
        return this.dao.queryFormSchemeKey(taskId);
    }

    public List<RealTimeJobBean> getCancelableTasks() {
        List tasks = RealTimeJobFactory.getInstance().getRealTimeJobList();
        if (!tasks.isEmpty()) {
            return tasks.stream().filter(RealTimeJobBean::isCancellable).collect(Collectors.toList());
        }
        return new ArrayList<RealTimeJobBean>();
    }

    private void setContextToRealTimeParams(Map<String, String> params) {
        NpContext npContext = NpContextHolder.getContext();
        params.put("NR_CONTEXT_TENANT", npContext.getTenant());
        ContextUser user = npContext.getUser();
        params.put("NR_CONTEXT_USER", user != null ? JsonUtil.objectToJson((Object)user) : null);
        ContextIdentity identity = npContext.getIdentity();
        params.put("NR_CONTEXT_IDENTITY", identity != null ? JsonUtil.objectToJson((Object)identity) : null);
        ContextOrganization orginzation = npContext.getOrganization();
        params.put("NR_CONTEXT_ORGANIZATION", orginzation != null ? JsonUtil.objectToJson((Object)orginzation) : null);
        Locale local = npContext.getLocale();
        params.put("NR_CONTEXT_LOCALE", local != null ? JsonUtil.objectToJson((Object)local) : null);
        String ip = npContext.getIp();
        if (StringUtils.isEmpty((String)ip)) {
            ip = AsyncTaskManagerImpl.getClientIpAddress(RequestContextHolder.getRequestAttributes());
        }
        params.put("NR_CONTEXT_IP", ip);
        Date loginDate = npContext.getLoginDate();
        params.put("NR_CONTEXT_LOGINDATE", loginDate != null ? JsonUtil.objectToJson((Object)loginDate) : null);
        params.put("NR_CONTEXT_TRACEID", LogTraceIDUtil.getLogTraceId());
        HashMap extensionMap = new HashMap();
        Consumer<Map.Entry> consumer = extension -> {
            ContextExtension cfr_ignored_0 = (ContextExtension)extensionMap.put(extension.getKey(), extension.getValue());
        };
        npContext.applyExtensions(consumer);
        if (!extensionMap.isEmpty()) {
            for (Map.Entry entry : extensionMap.entrySet()) {
                params.put("NR_CONTEXT_EXTENSION_" + (String)entry.getKey(), SimpleParamConverter.SerializationUtils.serializeToString(entry.getValue()));
            }
        }
    }
}

