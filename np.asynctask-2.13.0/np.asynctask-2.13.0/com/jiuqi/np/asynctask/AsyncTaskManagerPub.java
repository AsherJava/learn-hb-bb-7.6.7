/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.np.asynctask;

import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.exception.NpAsyncTaskExecption;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface AsyncTaskManagerPub {
    @PostMapping(value={"/asynctask/manager/publishTaskWithDepend"}, consumes={"application/json;charset=UTF-8"}, produces={"application/json;charset=UTF-8"})
    public String publishTaskWithDepend(@RequestBody byte[] var1, @RequestParam(value="taskPoolType") String var2) throws NpAsyncTaskExecption;

    @PostMapping(value={"/asynctask/manager/publishTaskWithDependImpl"}, consumes={"application/json;charset=UTF-8"}, produces={"application/json;charset=UTF-8"})
    public String publishTaskWithDependImpl(@RequestBody byte[] var1, @RequestParam(value="taskPoolType") String var2, @RequestParam(value="dependedTaskId") String var3) throws NpAsyncTaskExecption;

    @PostMapping(value={"/asynctask/manager/publishAndExecuteTask"}, consumes={"application/json;charset=UTF-8"}, produces={"application/json;charset=UTF-8"})
    public String publishAndExecuteTask(@RequestBody byte[] var1, @RequestParam(value="taskPoolType") String var2) throws NpAsyncTaskExecption;

    @PostMapping(value={"/asynctask/manager/publishToSplitQueue"}, consumes={"application/json;charset=UTF-8"}, produces={"application/json;charset=UTF-8"})
    public String publishToSplitQueue(@RequestBody byte[] var1, @RequestParam(value="taskPoolType") String var2) throws NpAsyncTaskExecption;

    @PostMapping(value={"/asynctask/manager/cancelTask"})
    public void cancelTask(@RequestParam(value="taskId") String var1);

    @PostMapping(value={"/asynctask/manager/queryTaskState"})
    public TaskState queryTaskState(@RequestParam(value="taskId") String var1);

    @PostMapping(value={"/asynctask/manager/queryLocation"})
    public Integer queryLocation(@RequestParam(value="taskId") String var1);

    @PostMapping(value={"/asynctask/manager/queryAsyncTaskLocation"})
    public Integer queryLocation(@RequestBody AsyncTask var1);

    @PostMapping(value={"/asynctask/manager/queryProcess"})
    public Double queryProcess(@RequestParam(value="taskId") String var1);

    @PostMapping(value={"/asynctask/manager/queryResult"})
    public String queryResult(@RequestParam(value="taskId") String var1);

    @PostMapping(value={"/asynctask/manager/queryDetail"})
    public Object queryDetail(@RequestParam(value="taskId") String var1);

    @PostMapping(value={"/asynctask/manager/queryDetailString"})
    public String queryDetailString(@RequestParam(value="taskId") String var1);

    @PostMapping(value={"/asynctask/manager/queryArgs"})
    public String queryArgs(@RequestParam(value="taskId") String var1);

    @PostMapping(value={"/asynctask/manager/queryTask"})
    public AsyncTask queryTask(@RequestParam(value="taskId") String var1);

    @PostMapping(value={"/asynctask/manager/queryTaskProcess"})
    public AsyncTask querySimpleTask(@RequestParam(value="taskId") String var1);

    @GetMapping(value={"/asynctask/manager/queryTaskToDo"})
    public List<AsyncTask> queryTaskToDo();

    @GetMapping(value={"/asynctask/manager/queryTaskToDoWithoutClob"})
    public List<AsyncTask> queryTaskToDoWithoutClob();

    @PostMapping(value={"/asynctask/manager/publishUniqueTask"}, consumes={"application/json;charset=UTF-8"}, produces={"application/json;charset=UTF-8"})
    public String publishUniqueTask(byte[] var1, String var2, String var3);

    @PostMapping(value={"/asynctask/manager/completeTask"})
    public int completeTask(String var1);

    @PostMapping(value={"/asynctask/manager/queryDetails"})
    public Map<String, Object> queryDetails(List<String> var1);

    @PostMapping(value={"/asynctask/manager/queryDetailStrings"})
    public Map<String, String> queryDetailStrings(List<String> var1);
}

