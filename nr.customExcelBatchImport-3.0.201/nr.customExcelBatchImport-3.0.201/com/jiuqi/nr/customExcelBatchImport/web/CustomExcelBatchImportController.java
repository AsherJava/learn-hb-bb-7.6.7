/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.customExcelBatchImport.web;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.customExcelBatchImport.bean.CustomExcelOptionInfo;
import com.jiuqi.nr.customExcelBatchImport.bean.CustomExcelReturnInfo;
import com.jiuqi.nr.customExcelBatchImport.bean.ErrorInfo;
import com.jiuqi.nr.customExcelBatchImport.service.ICustomExcelBatchImportService;
import com.jiuqi.nr.customExcelBatchImport.service.impl.CustomExcelBatchImportExecutor;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/customExcelBatchImport"})
public class CustomExcelBatchImportController {
    private static final Logger logger = LoggerFactory.getLogger(CustomExcelBatchImportController.class);
    @Resource
    private ICustomExcelBatchImportService customExcelBatchImportService;
    @Resource
    private HttpServletResponse response;
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @RequestMapping(value={"/queryTemplateFileList"}, method={RequestMethod.POST})
    @ApiOperation(value="\u83b7\u53d6\u6a21\u677f\u6587\u6863\u5217\u8868")
    public List<FileInfo> getTemplateFileList(String taskKey, String periodInfo) {
        return this.customExcelBatchImportService.getTemplateFileList(taskKey, periodInfo);
    }

    @RequestMapping(value={"/coverTemplateFile"}, method={RequestMethod.POST})
    @ApiOperation(value="\u8986\u76d6\u6a21\u677f\u6587\u6863")
    public List<List<ErrorInfo>> coverTemplateFile(@Valid @RequestBody CustomExcelOptionInfo customExcelOptionInfo) {
        return this.customExcelBatchImportService.coverTemplateFile(customExcelOptionInfo.getTaskKey(), customExcelOptionInfo.getPeriodInfo(), customExcelOptionInfo.getFileKey());
    }

    @RequestMapping(value={"/downloadTempleFiles"}, method={RequestMethod.POST})
    @ApiOperation(value="\u6a21\u677f\u4e0b\u8f7d")
    public void downloadTempleFiles(@Valid @RequestBody CustomExcelOptionInfo customExcelOptionInfo) {
        this.customExcelBatchImportService.downloadTempleFiles(customExcelOptionInfo.getTaskKey(), customExcelOptionInfo.getPeriodInfo(), customExcelOptionInfo.getFileKey(), this.response);
    }

    @RequestMapping(value={"/downExampleNoEnableManage"}, method={RequestMethod.POST})
    @ApiOperation(value="\u4e0b\u8f7d\u6a21\u677f\u793a\u4f8b")
    public CustomExcelReturnInfo downExampleNoEnableManage(@Valid @RequestBody CustomExcelOptionInfo customExcelOptionInfo) {
        return this.customExcelBatchImportService.downExampleNoEnableManage(customExcelOptionInfo.getTaskKey(), customExcelOptionInfo.getPeriodInfo());
    }

    @RequestMapping(value={"/downExampleEnableManage"}, method={RequestMethod.POST})
    @ApiOperation(value="\u4e0b\u8f7d\u6a21\u677f\u793a\u4f8b")
    public void downExampleEnableManage(@Valid @RequestBody CustomExcelOptionInfo customExcelOptionInfo) {
        this.customExcelBatchImportService.downExampleEnableManage(customExcelOptionInfo.getTaskKey(), customExcelOptionInfo.getPeriodInfo(), this.response);
    }

    @RequestMapping(value={"/deleteFile"}, method={RequestMethod.POST})
    @ApiOperation(value="\u5220\u9664\u6a21\u677f")
    public ReturnInfo deleteFile(@Valid @RequestBody CustomExcelOptionInfo customExcelOptionInfo) throws Exception {
        return this.customExcelBatchImportService.deleteTemplate(customExcelOptionInfo.getFileKey());
    }

    @RequestMapping(value={"/customExcelImport"}, method={RequestMethod.POST})
    @ApiOperation(value="\u5bfc\u5165\u6570\u636e")
    public AsyncTaskInfo customExcelImport(@Valid @RequestBody CustomExcelOptionInfo customExcelOptionInfo) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(customExcelOptionInfo.getTaskKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)customExcelOptionInfo));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new CustomExcelBatchImportExecutor());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }
}

