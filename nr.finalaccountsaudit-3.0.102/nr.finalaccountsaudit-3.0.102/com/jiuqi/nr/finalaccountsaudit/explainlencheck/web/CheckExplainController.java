/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.definition.impl.controller.DataDefinitionRuntimeController2
 *  com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.jtable.annotation.JLoggable
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.finalaccountsaudit.explainlencheck.web;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.definition.impl.controller.DataDefinitionRuntimeController2;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.finalaccountsaudit.common.AsynctaskPoolType;
import com.jiuqi.nr.finalaccountsaudit.explainlencheck.asynctask.ExplainInfoCheckAsyncTaskExecutor;
import com.jiuqi.nr.finalaccountsaudit.explainlencheck.bean.ExplainInfoCheckParam;
import com.jiuqi.nr.finalaccountsaudit.explainlencheck.bean.ExplainInfoCheckResultItem;
import com.jiuqi.nr.finalaccountsaudit.explainlencheck.bean.QueryExplainCheckResultInfo;
import com.jiuqi.nr.finalaccountsaudit.explainlencheck.service.ExplainInfoCheckService;
import com.jiuqi.nr.jtable.annotation.JLoggable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags={"\u51fa\u9519\u8bf4\u660e\u957f\u5ea6\u68c0\u67e5"})
@RequestMapping(value={"api/v1/finalaccount"})
public class CheckExplainController {
    @Autowired
    CommonUtil commonUtil;
    @Autowired
    DataDefinitionRuntimeController2 dataDefinitionRuntimeController;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private ExplainInfoCheckService explainInfoCheckService;

    @RequestMapping(value={"/explainInfoLenCheck"}, method={RequestMethod.POST})
    @ApiOperation(value="\u7efc\u5408\u5ba1\u6838\u4e00\u952e\u5ba1\u6838\u65f6\u8c03\u7528\u7684\u51fa\u9519\u8bf4\u660e\u957f\u5ea6\u68c0\u67e5")
    public AsyncTaskInfo explainInfoLenCheck(@RequestBody ExplainInfoCheckParam param) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(param.getContext().getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(param.getContext().getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)param));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new ExplainInfoCheckAsyncTaskExecutor());
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo, AsynctaskPoolType.ASYNCTASK_EXPLAININFOCHECK.getName());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @JLoggable(value="\u67e5\u8be2\u51fa\u9519\u8bf4\u660e\u68c0\u67e5\u7ed3\u679c\u5206\u7ec4\u4fe1\u606f")
    @PostMapping(value={"/queryErrorCheckResultGroup"})
    @ApiOperation(value="\u67e5\u8be2\u51fa\u9519\u8bf4\u660e\u68c0\u67e5\u7ed3\u679c\u5206\u7ec4\u4fe1\u606f", notes="\u67e5\u8be2\u51fa\u9519\u8bf4\u660e\u68c0\u67e5\u7ed3\u679c\u5206\u7ec4\u4fe1\u606f")
    public List<ExplainInfoCheckResultItem> QueryExplainInfoCheckResult(@RequestBody QueryExplainCheckResultInfo info) throws SQLException {
        return this.explainInfoCheckService.QueryExplainInfoCheckResult(info);
    }
}

