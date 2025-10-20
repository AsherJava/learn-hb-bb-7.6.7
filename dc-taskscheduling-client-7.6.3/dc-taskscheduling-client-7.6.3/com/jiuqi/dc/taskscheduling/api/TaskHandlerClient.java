/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.taskscheduling.log.impl.data.TaskCountQueryDTO
 *  com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO
 *  com.jiuqi.nvwa.remote.event.RemoteSeviceChangeEvent
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.dc.taskscheduling.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.taskscheduling.api.vo.TaskHandlerVO;
import com.jiuqi.dc.taskscheduling.api.vo.TaskParamVO;
import com.jiuqi.dc.taskscheduling.core.data.SqlExecuteLogDTO;
import com.jiuqi.dc.taskscheduling.core.data.TaskEnableQueryDTO;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg;
import com.jiuqi.dc.taskscheduling.log.impl.data.TaskCountQueryDTO;
import com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO;
import com.jiuqi.nvwa.remote.event.RemoteSeviceChangeEvent;
import java.util.Date;
import java.util.List;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.dc.taskscheduling.api.TaskHandlerClient", name="${custom.service-name.datacenter:datacenter-service}", url="${custom.service-url.datacenter:}", primary=false)
@RefreshScope
public interface TaskHandlerClient {
    public static final String API_PATH = "/api/datacenter/v1/taskhandler/anon";

    @GetMapping(value={"/api/datacenter/v1/taskhandler/anon/regist"})
    public BusinessResponseEntity<String> regist();

    @GetMapping(value={"/api/datacenter/v1/taskhandler/anon/refresh"})
    public BusinessResponseEntity<String> refresh(RemoteSeviceChangeEvent var1);

    @GetMapping(value={"/api/datacenter/v1/taskhandler/anon/listHandler"})
    public BusinessResponseEntity<List<TaskHandlerVO>> getHandlerList();

    @PostMapping(value={"/api/datacenter/v1/taskhandler/anon/getHandleParams/{taskName}"})
    public BusinessResponseEntity<Object> getHandleParams(@PathVariable(name="taskName", required=true) String var1, @RequestBody String var2);

    @PostMapping(value={"/api/datacenter/v1/taskhandler/anon/handleTask/{taskName}"})
    public BusinessResponseEntity<TaskHandleResult> handleTask(@PathVariable(name="taskName", required=true) String var1, @RequestBody TaskHandleMsg var2);

    @PostMapping(value={"/api/datacenter/v1/taskhandler/anon/afterSubtasksHandle/{taskName}"})
    public BusinessResponseEntity<String> afterSubtasksHandle(@PathVariable(name="taskName", required=true) String var1, @RequestBody String var2);

    @PostMapping(value={"/api/datacenter/v1/taskhandler/anon/startTask/{taskName}"})
    public BusinessResponseEntity<String> startTask(@PathVariable(name="taskName", required=true) String var1, @RequestBody String var2);

    @PostMapping(value={"/api/datacenter/v1/taskhandler/anon/startTaskWithExtInfo/{taskName}"})
    public BusinessResponseEntity<String> startTaskWithExtInfo(@PathVariable(name="taskName", required=true) String var1, @RequestBody TaskParamVO var2);

    @PostMapping(value={"/api/datacenter/v1/taskhandler/anon/startSubTask/{taskName}"})
    public BusinessResponseEntity<List<String>> startSubTask(@PathVariable(name="taskName", required=true) String var1, @RequestBody TaskHandleMsg var2);

    @PostMapping(value={"/api/datacenter/v1/taskhandler/anon/waitTaskFinished/{runnerId}"})
    public void waitTaskFinished(@PathVariable(name="runnerId", required=true) String var1);

    @GetMapping(value={"/api/datacenter/v1/taskhandler/anon/getTaskProgress/{runnerId}"})
    public BusinessResponseEntity<Double> getTaskProgress(@PathVariable(name="runnerId", required=true) String var1);

    @PostMapping(value={"/api/datacenter/v1/taskhandler/anon/queryTaskLog"})
    public BusinessResponseEntity<List<LogManagerDetailVO>> queryTaskLog(@RequestBody TaskCountQueryDTO var1);

    @PostMapping(value={"/api/datacenter/v1/taskhandler/anon/enable"})
    public BusinessResponseEntity<Boolean> enable(@RequestBody TaskEnableQueryDTO var1);

    @PostMapping(value={"/api/datacenter/v1/taskhandler/anon/countTask"})
    public BusinessResponseEntity<Integer> countTask(@RequestBody TaskCountQueryDTO var1);

    @PostMapping(value={"/api/datacenter/v1/taskhandler/anon/recordSql"})
    @Async(value="sql-record-executor")
    public void recordSql(@RequestBody SqlExecuteLogDTO var1);

    @PostMapping(value={"/api/datacenter/v1/taskhandler/anon/recordSqlEndTime/{sqlLogId}"})
    @Async(value="sql-record-executor")
    public void recordSqlEndTime(@PathVariable(name="sqlLogId") String var1, @RequestBody Date var2);

    @PostMapping(value={"/api/datacenter/v1/taskhandler/anon/check/binding"})
    public boolean isBinding(@RequestBody String var1);
}

