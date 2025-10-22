/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.dataentry.bean.ExecuteTaskParam
 *  com.jiuqi.nr.jtable.annotation.JLoggable
 *  com.jiuqi.nr.todo.entity.TodoVO
 *  com.jiuqi.nr.workflow2.todo.entityimpl.PeriodItem
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.mobile.approval.client;

import com.jiuqi.gcreport.mobile.approval.vo.MobileTodoParamInfo;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.dataentry.bean.ExecuteTaskParam;
import com.jiuqi.nr.jtable.annotation.JLoggable;
import com.jiuqi.nr.todo.entity.TodoVO;
import com.jiuqi.nr.workflow2.todo.entityimpl.PeriodItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.mobile.approval.client.MobileTodoClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@Api(value="/api/v1/mobile/todo", tags={"\u79fb\u52a8\u7aef\u5f85\u529e\u4e8b\u9879\u5ba2\u6237\u7aef"})
public interface MobileTodoClient {
    @PostMapping(value={"/api/v1/mobile/todo/queryTodoDataInfo"})
    @ApiOperation(value=" \u67e5\u8be2\u5f85\u529e\u8be6\u60c5\u6570\u636e\u4fe1\u606f")
    public TodoVO queryTodoDataInfo(@RequestBody MobileTodoParamInfo var1);

    @GetMapping(value={"/api/v1/mobile/todo/queryPeriodList"})
    @ApiOperation(value=" \u67e5\u8be2\u5f85\u529e\u4e8b\u9879\u65f6\u671f\u4fe1\u606f\u5217\u8868")
    public List<PeriodItem> queryTodoDataInfo(@RequestParam(value="taskKey") String var1);

    @GetMapping(value={"/api/v1/mobile/todo/queryMobileTodoAllowTaskInfo"})
    @ApiOperation(value=" \u67e5\u8be2\u6709\u6743\u9650\u7684\u4efb\u52a1\u5217\u8868")
    public List<Map<String, Object>> queryMobileTodoAllowTaskInfo();

    @JLoggable(value="\u6267\u884c\u4e0a\u62a5")
    @PostMapping(value={"/api/v1/mobile/todo/actions"})
    @ApiOperation(value="\u4e0a\u62a5\u6d41\u7a0b\uff1a\u6267\u884c\u4e0a\u62a5\u6d41\u7a0b\u52a8\u4f5c")
    public AsyncTaskInfo executeTask(@RequestBody ExecuteTaskParam var1);
}

