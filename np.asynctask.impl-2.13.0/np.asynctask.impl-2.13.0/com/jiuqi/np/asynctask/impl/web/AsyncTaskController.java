/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Html
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskQueryInfo
 *  com.jiuqi.nr.common.asynctask.entity.BatchAsyncTaskQueryInfo
 *  com.jiuqi.nr.common.exception.AsyncTaskErrorException
 *  com.jiuqi.nr.common.exception.ExceptionCodeCost
 *  com.jiuqi.nr.common.exception.NotFoundAsyncTaskException
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.np.asynctask.impl.web;

import com.jiuqi.bi.util.Html;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.dao.AsyncTaskDao;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskQueryInfo;
import com.jiuqi.nr.common.asynctask.entity.BatchAsyncTaskQueryInfo;
import com.jiuqi.nr.common.exception.AsyncTaskErrorException;
import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.exception.NotFoundAsyncTaskException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags={"\u4efb\u52a1\u8fdb\u5ea6\u7ec4\u4ef6"})
public class AsyncTaskController {
    @Resource
    AsyncTaskManager asyncTaskManager;
    @Autowired
    private AsyncTaskDao dao;
    private static final String OVERTIME = "overtime";
    private static final String SIMPLE_QUERY_URL = "/api/asynctask/simpleQuery?asynTaskID=";
    private static final String QUERY_DETAIL_URL = "/api/asynctask/query_detail?asynTaskID=";
    private static final String QUERY_URL = "/api/asynctask/query?asynTaskID=";

    @ApiOperation(value="\u83b7\u53d6\u5f02\u6b65\u4efb\u52a1")
    @RequestMapping(value={"/api/asynctask/query"}, method={RequestMethod.GET})
    public AsyncTaskInfo queryAsyncTask(@Valid AsyncTaskQueryInfo asyncTaskQueryInfo) {
        if (StringUtils.isNotEmpty((String)asyncTaskQueryInfo.getAsynTaskID())) {
            String asynTaskID = asyncTaskQueryInfo.getAsynTaskID();
            AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
            asyncTaskInfo.setId(asynTaskID);
            AsyncTask queryTask = this.asyncTaskManager.querySimpleTask(asynTaskID);
            if (null == queryTask) {
                throw new NotFoundAsyncTaskException(ExceptionCodeCost.NOTFOUND_ASYNCTASK_IDISNULL, null);
            }
            TaskState state = queryTask.getState();
            asyncTaskInfo.setState(state);
            asyncTaskInfo.setResult(TaskState.OVERTIME.equals((Object)state) ? OVERTIME : queryTask.getResult());
            asyncTaskInfo.setProcess(queryTask.getProcess());
            asyncTaskInfo.setDetail(queryTask.getDetail());
            asyncTaskInfo.setUrl(QUERY_URL);
            if (state == TaskState.WAITING) {
                asyncTaskInfo.setLocation(Integer.valueOf("immediately".equals(queryTask.getPublishType()) ? 0 : this.asyncTaskManager.queryLocation(queryTask)));
            } else if (state == TaskState.FINISHED || state == TaskState.ERROR) {
                asyncTaskInfo.setUrl(QUERY_DETAIL_URL);
                Object queryDetail = this.asyncTaskManager.queryDetail(asyncTaskQueryInfo.getAsynTaskID());
                asyncTaskInfo.setDetail(queryDetail);
            }
            return asyncTaskInfo;
        }
        throw new NotFoundAsyncTaskException(ExceptionCodeCost.NOTFOUND_ASYNCTASK_IDISNULL, null);
    }

    @ApiOperation(value="\u83b7\u53d6\u5f02\u6b65\u4efb\u52a1")
    @RequestMapping(value={"/api/asynctask/simpleQuery"}, method={RequestMethod.GET})
    public AsyncTaskInfo simpleQueryAsyncTask(@Valid AsyncTaskQueryInfo asyncTaskQueryInfo) {
        if (StringUtils.isNotEmpty((String)asyncTaskQueryInfo.getAsynTaskID())) {
            String asynTaskID = asyncTaskQueryInfo.getAsynTaskID();
            AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
            asyncTaskInfo.setId(asynTaskID);
            AsyncTask queryTask = this.asyncTaskManager.querySimpleTask(asynTaskID);
            if (null == queryTask) {
                throw new NotFoundAsyncTaskException(ExceptionCodeCost.NOTFOUND_ASYNCTASK_IDISNULL, null);
            }
            TaskState state = queryTask.getState();
            asyncTaskInfo.setState(state);
            asyncTaskInfo.setResult(TaskState.OVERTIME.equals((Object)state) ? OVERTIME : queryTask.getResult());
            asyncTaskInfo.setProcess(queryTask.getProcess());
            asyncTaskInfo.setUrl(SIMPLE_QUERY_URL);
            if (state == TaskState.WAITING) {
                asyncTaskInfo.setLocation(Integer.valueOf("immediately".equals(queryTask.getPublishType()) ? 0 : this.asyncTaskManager.queryLocation(queryTask)));
            } else if (state == TaskState.FINISHED || state == TaskState.ERROR) {
                asyncTaskInfo.setUrl(QUERY_DETAIL_URL);
            }
            return asyncTaskInfo;
        }
        throw new NotFoundAsyncTaskException(ExceptionCodeCost.NOTFOUND_ASYNCTASK_IDISNULL, null);
    }

    @ApiOperation(value="\u6279\u91cf\u83b7\u53d6\u5f02\u6b65\u4efb\u52a1")
    @RequestMapping(value={"/api/asynctask/batchSimpleQuery"}, method={RequestMethod.POST})
    public Map<String, AsyncTaskInfo> batchSimpleQuery(@RequestBody BatchAsyncTaskQueryInfo batchAsyncTaskQueryInfo) {
        HashMap<String, AsyncTaskInfo> asyncTaskInfoMap = new HashMap<String, AsyncTaskInfo>();
        List queryInfos = new ArrayList();
        if (batchAsyncTaskQueryInfo != null) {
            queryInfos = batchAsyncTaskQueryInfo.getAsyncTaskInfos();
        }
        if (queryInfos != null && !queryInfos.isEmpty()) {
            Map queryInfoMap = queryInfos.stream().collect(HashMap::new, (m, v) -> m.put(v.getAsynTaskID(), v), HashMap::putAll);
            ArrayList asyncTaskIDs = new ArrayList(queryInfoMap.keySet());
            Map asyncTasks = this.asyncTaskManager.batchQuerySimpleTask(asyncTaskIDs);
            for (String taskID : asyncTaskIDs) {
                AsyncTaskQueryInfo queryInfo = (AsyncTaskQueryInfo)queryInfoMap.get(taskID);
                AsyncTask asyncTask = (AsyncTask)asyncTasks.get(taskID);
                if (null == asyncTask) {
                    throw new NotFoundAsyncTaskException(ExceptionCodeCost.NOTFOUND_ASYNCTASK_IDISNULL, null);
                }
                AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
                asyncTaskInfo.setId(taskID);
                TaskState state = asyncTask.getState();
                asyncTaskInfo.setState(state);
                asyncTaskInfo.setResult(TaskState.OVERTIME.equals((Object)state) ? OVERTIME : asyncTask.getResult());
                asyncTaskInfo.setProcess(asyncTask.getProcess());
                asyncTaskInfo.setUrl(queryInfo.isQueryDetail() ? QUERY_URL : SIMPLE_QUERY_URL);
                if (state == TaskState.WAITING) {
                    asyncTaskInfo.setLocation(Integer.valueOf("immediately".equals(asyncTask.getPublishType()) ? 0 : this.asyncTaskManager.queryLocation(asyncTask)));
                } else if (state == TaskState.FINISHED || state == TaskState.ERROR) {
                    asyncTaskInfo.setUrl(QUERY_DETAIL_URL);
                    if (queryInfo.isQueryDetail()) {
                        Object queryDetail = this.asyncTaskManager.queryDetail(taskID);
                        asyncTaskInfo.setDetail(queryDetail);
                    }
                }
                asyncTaskInfoMap.put(taskID, asyncTaskInfo);
            }
            return asyncTaskInfoMap;
        }
        throw new NotFoundAsyncTaskException(ExceptionCodeCost.NOTFOUND_ASYNCTASK_IDISNULL, null);
    }

    @ApiOperation(value="\u67e5\u8be2\u5f02\u6b65\u4efb\u52a1\u7ed3\u679c")
    @RequestMapping(value={"/api/asynctask/getTaskDetail"}, method={RequestMethod.GET})
    public Serializable getTaskDetail(@Valid AsyncTaskQueryInfo asyncTaskQueryInfo) {
        Serializable detail;
        try {
            detail = this.asyncTaskManager.querySerializableDetail(asyncTaskQueryInfo.getAsynTaskID());
        }
        catch (Exception e) {
            throw new AsyncTaskErrorException(ExceptionCodeCost.ERROR_ASYNCTASK, null);
        }
        return detail;
    }

    @ApiOperation(value="\u53d6\u6d88\u5f02\u6b65\u4efb\u52a1")
    @RequestMapping(value={"/api/asynctask/cancel"}, method={RequestMethod.GET})
    public AsyncTaskInfo cancelAsyncTask(@Valid AsyncTaskQueryInfo asyncTaskQueryInfo) {
        if (StringUtils.isNotEmpty((String)asyncTaskQueryInfo.getAsynTaskID())) {
            String asynTaskID = asyncTaskQueryInfo.getAsynTaskID();
            this.asyncTaskManager.cancelTask(asynTaskID);
            AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
            AsyncTask queryTask = this.asyncTaskManager.queryTask(asynTaskID);
            asyncTaskInfo.setId(asynTaskID);
            asyncTaskInfo.setProcess(queryTask.getProcess());
            asyncTaskInfo.setState(queryTask.getState());
            return asyncTaskInfo;
        }
        throw new NotFoundAsyncTaskException(ExceptionCodeCost.NOTFOUND_ASYNCTASK_IDISNULL, null);
    }

    @ApiOperation(value="\u67e5\u8be2\u5f02\u6b65\u4efb\u52a1\u7ed3\u679c")
    @RequestMapping(value={"/api/asynctask/query_detail"}, method={RequestMethod.GET})
    public String queryAsyncTaskDetail(@Valid AsyncTaskQueryInfo asyncTaskQueryInfo) {
        Object queryDetail = this.asyncTaskManager.queryDetail(asyncTaskQueryInfo.getAsynTaskID());
        if (null != queryDetail) {
            return Html.cleanUrlXSS((String)queryDetail.toString());
        }
        return null;
    }

    @ApiOperation(value="\u67e5\u8be2\u5f02\u6b65\u4efb\u52a1\u8be6\u7ec6\u4fe1\u606f")
    @RequestMapping(value={"/api/query_asynctask_detail/{taskId}"}, method={RequestMethod.GET})
    public AsyncTask queryAsyncTaskDetail(@PathVariable(value="taskId") String taskId) {
        if (!StringUtils.isEmpty((String)taskId)) {
            return this.dao.query(taskId);
        }
        return null;
    }
}

