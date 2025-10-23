/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.extension.ILogGenerator
 *  com.jiuqi.bi.core.jobs.extension.ILogGenerator$LogItem
 *  com.jiuqi.bi.core.jobs.extension.ILogGenerator$LogItemDetail
 *  com.jiuqi.bi.core.jobs.extension.ILogGenerator$LogItemHandle
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeLogManager
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncThreadExecutor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.bpm.repair.web;

import com.jiuqi.bi.core.jobs.extension.ILogGenerator;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeLogManager;
import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncThreadExecutor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.bpm.repair.scheme.BpmRepairScheme;
import com.jiuqi.nr.bpm.repair.service.BpmRepairSchemeManager;
import com.jiuqi.nr.bpm.repair.service.BpmRepairToolsWebCheckService;
import com.jiuqi.nr.bpm.repair.service.BpmRepairToolsWebService;
import com.jiuqi.nr.bpm.repair.web.param.BpmRepairSchemeInfo;
import com.jiuqi.nr.bpm.repair.web.param.BpmRepairToolsParam;
import com.jiuqi.nr.bpm.repair.web.param.PeriodParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/nr/workflow/repair-tools"})
@Api(tags={"\u6d41\u7a0b\u6570\u636e\u4fee\u590d\u5de5\u5177-API"})
public class BpmRepairToolsWebController {
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private AsyncThreadExecutor asyncThreadExecutor;
    @Autowired
    private BpmRepairSchemeManager schemeManager;
    @Autowired
    private BpmRepairToolsWebService service;
    @Autowired
    private BpmRepairToolsWebCheckService checkService;

    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u6240\u6709\u4fee\u590d\u65b9\u6848")
    @GetMapping(value={"/get-all-schemes"})
    public List<BpmRepairSchemeInfo> getAllSchemes() {
        return this.schemeManager.getAllSchemes();
    }

    @ResponseBody
    @ApiOperation(value="\u6267\u884c\u4fee\u590d")
    @PostMapping(value={"/execute"})
    public Map<String, Object> executeRepairing(@RequestBody BpmRepairToolsParam env) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        if (env.isValidEnv()) {
            BpmRepairScheme repairScheme = this.schemeManager.getSchemeByCode(env.getScheme());
            if (repairScheme != null && repairScheme.getExecutor() != null) {
                NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
                npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)env));
                npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)repairScheme.getExecutor());
                result.put("asyncTaskId", this.asyncTaskManager.publishTask(npRealTimeTaskInfo));
            } else {
                result.put("error", "\u4fee\u590d\u65b9\u6848\u3010" + env.getScheme() + "\u3011\u672a\u5b9e\u73b0");
            }
        } else {
            result.put("error", "\u4e0d\u5b8c\u6574\u7684\u53c2\u6570\uff01\uff01\uff01\u65e0\u6cd5\u6267\u884c\u4fee\u590d\uff01\uff01\uff01");
        }
        return result;
    }

    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u6267\u884c\u8fdb\u5ea6\u4fe1\u606f")
    @GetMapping(value={"/get-progress-info"})
    public Map<String, Object> getProgressInfo(@RequestParam(name="asyncTaskId") String asyncTaskId) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        AsyncTask asyncTask = this.asyncTaskManager.queryTask(asyncTaskId);
        if (asyncTask != null) {
            TaskState state = asyncTask.getState();
            Double process = asyncTask.getProcess() * 100.0;
            String message = asyncTask.getResult();
            result.put("state", state);
            result.put("process", process);
            result.put("message", message);
        } else {
            result.put("state", TaskState.WAITING);
            result.put("process", 0);
            result.put("message", "");
        }
        return result;
    }

    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u8be6\u7ec6\u7684\u65e5\u5fd7\u4fe1\u606f")
    @GetMapping(value={"/get-log-detail"})
    public String getLogDetail(@RequestParam(name="asyncTaskId") String asyncTaskId) {
        final StringBuilder logDetail = new StringBuilder();
        ILogGenerator logGenerator = RealTimeLogManager.getInstance().getLogGenerator();
        try {
            logGenerator.iterateAllLogs(asyncTaskId, false, new ILogGenerator.LogItemHandle(){

                public void handleLogItem(ILogGenerator.LogItem item, ILogGenerator.LogItemDetail detail) throws Exception {
                    logDetail.append(item.getMessage()).append("\n");
                }
            });
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return logDetail.toString();
    }

    @ResponseBody
    @ApiOperation(value="\u4e0b\u8f7d\u65e5\u5fd7")
    @GetMapping(value={"/download-log"})
    public void downloadLogDetail(@RequestParam(name="asyncTaskId") String asyncTaskId, HttpServletResponse response) {
        ILogGenerator logGenerator = RealTimeLogManager.getInstance().getLogGenerator();
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/octet-stream");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        try (ServletOutputStream outStr = response.getOutputStream();
             final BufferedOutputStream buff = new BufferedOutputStream((OutputStream)outStr);){
            logGenerator.iterateAllLogs(asyncTaskId, false, new ILogGenerator.LogItemHandle(){

                public void handleLogItem(ILogGenerator.LogItem item, ILogGenerator.LogItemDetail detail) throws Exception {
                    buff.write((item.getMessage() + "\n").getBytes(StandardCharsets.UTF_8));
                }
            });
            buff.flush();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseBody
    @ApiOperation(value="\u52a0\u8f7d\u4efb\u52a1\u4e0b\u7684\u65f6\u671f\u7ec4\u4ef6\u53c2\u6570")
    @GetMapping(value={"/get-period-param"})
    public PeriodParam getPeriodParam(@RequestParam(name="taskId") String taskId) {
        try {
            return this.service.getPeriodParam(taskId);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseBody
    @ApiOperation(value="\u83b7\u53d6\u9519\u8bef\u8bb0\u5f55")
    @PostMapping(value={"/get-error-records"})
    public String getErrorRecords(@RequestBody BpmRepairToolsParam env) {
        return this.checkService.getErrorRecords(env);
    }
}

