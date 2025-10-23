/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.definition.progress.ProgressItem
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.task.api.aop.TaskLog
 *  com.jiuqi.nr.task.api.task.TaskCache
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.task.form.controller;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.definition.progress.ProgressItem;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.task.api.aop.TaskLog;
import com.jiuqi.nr.task.api.task.TaskCache;
import com.jiuqi.nr.task.form.common.ExceptionEnum;
import com.jiuqi.nr.task.form.dto.FormExportDTO;
import com.jiuqi.nr.task.form.dto.FormImportDTO;
import com.jiuqi.nr.task.form.executor.FormExportExecutor;
import com.jiuqi.nr.task.form.formio.IFormExportService;
import com.jiuqi.nr.task.form.formio.IFormImportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@JQRestController
@RequestMapping(value={"/api/v1/form-io/"})
@Api(tags={"\u8868\u6837\u5bfc\u5165\u5bfc\u51fa"})
public class FormStyleIOController {
    @Autowired
    private IFormExportService formExportService;
    @Autowired
    private IFormImportService formImportService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private TaskCache taskCache;

    @ApiOperation(value="\u8868\u6837\u5f02\u6b65\u5bfc\u51fa")
    @PostMapping(value={"excel/export_async"})
    public String exportAsync(@RequestBody FormExportDTO exportDTO) throws JQException {
        try {
            this.checkTask(exportDTO.getTaskKey());
            NpRealTimeTaskInfo realtimeJob = new NpRealTimeTaskInfo();
            realtimeJob.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)exportDTO));
            realtimeJob.setAbstractRealTimeJob((AbstractRealTimeJob)new FormExportExecutor());
            String taskId = this.asyncTaskManager.publishTask(realtimeJob);
            this.taskCache.addAsyncTak(taskId);
            return taskId;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_0001, e.getMessage());
        }
    }

    @ApiOperation(value="\u5bfc\u51fa\u540e\u7684\u8868\u6837\u4e0b\u8f7d")
    @PostMapping(value={"excel/download"})
    public void download(@RequestBody FormExportDTO exportDTO, HttpServletResponse response) throws JQException {
        try {
            this.formExportService.download(exportDTO, response);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_0002, e.getMessage());
        }
    }

    @ApiOperation(value="\u8868\u6837\u6587\u4ef6\u4e0a\u4f20")
    @PostMapping(value={"excel/file/upload"})
    public String excelUpload(@RequestParam(value="file") MultipartFile file) throws JQException {
        String fileKey = "";
        try {
            fileKey = this.formImportService.excelUpload(file);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_0003, e.getMessage());
        }
        return fileKey;
    }

    @ApiOperation(value="\u8868\u6837\u5bfc\u5165")
    @PostMapping(value={"excel/import"})
    @TaskLog(operation="\u8868\u6837\u5bfc\u5165")
    public void FormImport(@RequestBody FormImportDTO formImportDTO) throws JQException {
        try {
            if (Boolean.TRUE.equals(formImportDTO.isSaveImportData())) {
                this.formImportService.saveImportData(formImportDTO);
                return;
            }
            this.formImportService.formImport(formImportDTO);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_0004, e.getMessage());
        }
    }

    @ApiOperation(value="\u8868\u6837\u5bfc\u5165\u8fdb\u5ea6")
    @GetMapping(value={"excel/import/progress/{progressID}"})
    public ProgressItem getProgress(@PathVariable String progressID) {
        return this.formImportService.getProgress(progressID);
    }

    private void checkTask(String taskKey) throws JQException {
        DesignTaskDefine taskDefine = this.designTimeViewController.getTask(taskKey);
        if (taskDefine == null) {
            throw new JQException((ErrorEnum)ExceptionEnum.EXCEPTION_ENUM_0004);
        }
    }
}

