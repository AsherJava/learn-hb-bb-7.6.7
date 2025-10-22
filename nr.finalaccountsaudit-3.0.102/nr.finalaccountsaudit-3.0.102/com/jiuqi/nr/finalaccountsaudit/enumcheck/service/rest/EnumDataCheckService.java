/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.jtable.annotation.JLoggable
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.finalaccountsaudit.enumcheck.service.rest;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.finalaccountsaudit.common.AsynctaskPoolType;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumCheckGroupInfo;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumCheckResultSaveInfo;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumDataCheckInfo;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumDataCheckResultItem;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumTableResultInfo;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.QueryEnumResultInfo;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.internal.service.EnumDataCheckAsyncTaskExecutor;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.service.IEnumDataCheckService;
import com.jiuqi.nr.jtable.annotation.JLoggable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.sql.SQLException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags={"\u679a\u4e3e\u5b57\u5178\u68c0\u67e5"})
@RequestMapping(value={"FinalAccEnumDataCheck/EnumDataCheck"})
public class EnumDataCheckService {
    @Autowired
    IEnumDataCheckService enumDataCheckService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @JLoggable(value="\u679a\u4e3e\u5b57\u5178\u68c0\u67e5")
    @PostMapping(value={"/Enum-check"})
    @ApiOperation(value="\u679a\u4e3e\u5b57\u5178\u68c0\u67e5", notes="\u679a\u4e3e\u5b57\u5178\u68c0\u67e5")
    public AsyncTaskInfo EnumCheck(@Valid @RequestBody EnumDataCheckInfo enumDataCheckInfo) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(enumDataCheckInfo.getContext().getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(enumDataCheckInfo.getContext().getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)((Object)enumDataCheckInfo)));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new EnumDataCheckAsyncTaskExecutor());
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo, AsynctaskPoolType.ASYNCTASK_ENUMDATACHECK.getName());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @JLoggable(value="\u67e5\u8be2\u679a\u4e3e\u5b57\u5178")
    @PostMapping(value={"/queryAllEnumTables"})
    @ApiOperation(value="\u67e5\u8be2\u679a\u4e3e\u5b57\u5178", notes="\u67e5\u8be2\u679a\u4e3e\u5b57\u5178")
    public EnumTableResultInfo queryAllEnumTables(@Valid @RequestBody EnumDataCheckInfo enumDataCheckInfo) {
        return this.enumDataCheckService.queryAllEnumTables(enumDataCheckInfo, null);
    }

    @JLoggable(value="\u4fdd\u5b58\u679a\u4e3e\u68c0\u67e5\u7ed3\u679c")
    @PostMapping(value={"/saveEnumCheckResults"})
    @ApiOperation(value="\u4fdd\u5b58\u679a\u4e3e\u68c0\u67e5\u7ed3\u679c", notes="\u4fdd\u5b58\u679a\u4e3e\u68c0\u67e5\u7ed3\u679c")
    public EnumCheckResultSaveInfo saveEnumCheckResults(@Valid @RequestBody QueryEnumResultInfo queryEnumResultInfo) throws SQLException {
        return this.enumDataCheckService.saveEnumCheckResults(queryEnumResultInfo);
    }

    @JLoggable(value="\u67e5\u8be2\u679a\u4e3e\u68c0\u67e5\u7ed3\u679c\u5206\u7ec4\u8be6\u60c5")
    @PostMapping(value={"/queryEnumCheckResults"})
    @ApiOperation(value="\u67e5\u8be2\u679a\u4e3e\u68c0\u67e5\u7ed3\u679c\u5206\u7ec4\u8be6\u60c5", notes="\u67e5\u8be2\u679a\u4e3e\u68c0\u67e5\u7ed3\u679c\u5206\u7ec4\u8be6\u60c5")
    public String queryEnumCheckResults(@Valid @RequestBody QueryEnumResultInfo queryEnumResultInfo) throws Exception {
        return this.enumDataCheckService.queryEnumCheckResults(queryEnumResultInfo);
    }

    @JLoggable(value="\u67e5\u8be2\u679a\u4e3e\u68c0\u67e5\u7ed3\u679c\u5206\u7ec4\u4fe1\u606f")
    @PostMapping(value={"/queryEnumCheckResultGroup"})
    @ApiOperation(value="\u67e5\u8be2\u679a\u4e3e\u68c0\u67e5\u7ed3\u679c\u5206\u7ec4\u4fe1\u606f", notes="\u67e5\u8be2\u679a\u4e3e\u68c0\u67e5\u7ed3\u679c\u5206\u7ec4\u4fe1\u606f")
    public EnumCheckGroupInfo queryEnumCheckResultGroup(@Valid @RequestBody QueryEnumResultInfo queryEnumResultInfo) throws Exception {
        return this.enumDataCheckService.queryEnumCheckResultGroup(queryEnumResultInfo);
    }

    @JLoggable(value="\u67e5\u8be2\u679a\u4e3e\u68c0\u67e5\u7ed3\u679c\u5bfc\u51fa\u6570\u636e")
    @PostMapping(value={"/queryEnumCheckResultExport"})
    @ApiOperation(value="\u67e5\u8be2\u679a\u4e3e\u68c0\u67e5\u7ed3\u679c\u5bfc\u51fa\u6570\u636e", notes="\u67e5\u8be2\u679a\u4e3e\u68c0\u67e5\u7ed3\u679c\u5bfc\u51fa\u6570\u636e")
    public List<EnumDataCheckResultItem> queryEnumCheckResultExport(@Valid @RequestBody QueryEnumResultInfo queryEnumResultInfo) throws Exception {
        return this.enumDataCheckService.queryEnumCheckResultExport(queryEnumResultInfo);
    }
}

