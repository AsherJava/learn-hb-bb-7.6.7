/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  nr.single.map.configurations.bean.SingleConfigInfo
 *  nr.single.map.configurations.service.MappingConfigService
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.attachment.transfer.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.attachment.transfer.dto.FileInfoDTO;
import com.jiuqi.nr.attachment.transfer.dto.ImportParamDTO;
import com.jiuqi.nr.attachment.transfer.dto.ImportStatusDTO;
import com.jiuqi.nr.attachment.transfer.dto.RecordsQueryDTO;
import com.jiuqi.nr.attachment.transfer.exception.ExecuteException;
import com.jiuqi.nr.attachment.transfer.log.AttachmentLogHelper;
import com.jiuqi.nr.attachment.transfer.service.IAttachmentService;
import com.jiuqi.nr.attachment.transfer.service.IImportTaskService;
import com.jiuqi.nr.attachment.transfer.vo.ImportParamVO;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import nr.single.map.configurations.bean.SingleConfigInfo;
import nr.single.map.configurations.service.MappingConfigService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/v1/attachment-transfer/import"})
public class ImportTaskController {
    @Autowired
    private IAttachmentService attachmentService;
    @Autowired
    private IImportTaskService importTaskService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private MappingConfigService mappingConfigService;

    @PostMapping(value={"/start"})
    public boolean start(@RequestBody ImportParamVO importParamDTO) throws JQException {
        AttachmentLogHelper.importInfo("\u5f00\u59cb\u5bfc\u5165", importParamDTO);
        try {
            return this.attachmentService.importAttachment(ImportParamDTO.getVOInstance(importParamDTO));
        }
        catch (Exception e) {
            AttachmentLogHelper.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)ExecuteException.FAILED_START, e.getMessage());
        }
    }

    @GetMapping(value={"/pause"})
    public boolean pause() throws JQException {
        AttachmentLogHelper.importInfo("\u6682\u505c\u5bfc\u5165");
        try {
            return this.attachmentService.pausedImport();
        }
        catch (Exception e) {
            AttachmentLogHelper.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)ExecuteException.FAILED_PAUSE, e.getMessage());
        }
    }

    @GetMapping(value={"/resume"})
    public void resume() throws JQException {
        AttachmentLogHelper.importInfo("\u6062\u590d\u5bfc\u5165");
        try {
            this.attachmentService.restartImport();
        }
        catch (Exception e) {
            AttachmentLogHelper.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)ExecuteException.FAILED_RESUME, e.getMessage());
        }
    }

    @GetMapping(value={"/reset"})
    public void reset() throws JQException {
        AttachmentLogHelper.importInfo("\u91cd\u7f6e\u5bfc\u5165");
        try {
            this.attachmentService.resetImport();
        }
        catch (Exception e) {
            AttachmentLogHelper.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)ExecuteException.FAILED_RESET, e.getMessage());
        }
    }

    @GetMapping(value={"/reload/{key}"})
    public void rebuild(@PathVariable String key) throws JQException {
        AttachmentLogHelper.importInfo("\u91cd\u65b0\u5bfc\u5165\u9519\u8bef\u6570\u636e", key);
        try {
            this.attachmentService.reload(key);
        }
        catch (Exception e) {
            AttachmentLogHelper.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)ExecuteException.FAILED_RESET, e.getMessage());
        }
    }

    @GetMapping(value={"/checkError/{key}"})
    public String checkError(@PathVariable String key) throws JQException {
        try {
            return this.importTaskService.queryError(key);
        }
        catch (Exception e) {
            AttachmentLogHelper.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)ExecuteException.FAILED_RESET, e.getMessage());
        }
    }

    @GetMapping(value={"/reloadFailed"})
    public void executeFailed() throws JQException {
        AttachmentLogHelper.importInfo("\u91cd\u7f6e\u5168\u90e8\u9519\u8bef\u6570\u636e");
        try {
            this.attachmentService.reloadFailed();
        }
        catch (Exception e) {
            AttachmentLogHelper.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)ExecuteException.FAILED_EXE, e.getMessage());
        }
    }

    @GetMapping(value={"/getConfig"})
    public ImportParamVO getConfig() {
        ImportParamDTO param = this.importTaskService.getParam();
        if (param == null) {
            return null;
        }
        ImportParamVO paramVO = ImportParamVO.getInstance(param);
        TaskDefine task = this.runTimeViewController.getTask(paramVO.getTaskKey());
        paramVO.setTaskTitle(task.getTitle());
        SingleConfigInfo mapping = this.mappingConfigService.getMappingByKey(paramVO.getMapping());
        paramVO.setMappingTitle(mapping.getConfigName());
        paramVO.setSchemeKey(mapping.getSchemeKey());
        return paramVO;
    }

    @PostMapping(value={"/refresh"})
    public ImportStatusDTO refresh(@RequestBody RecordsQueryDTO recordsQueryDTO) {
        return this.importTaskService.queryRecords(recordsQueryDTO);
    }

    @GetMapping(value={"/listFile"})
    public List<FileInfoDTO> listFile() {
        return Collections.emptyList();
    }

    @PostMapping(value={"/export"})
    public void exportResult(HttpServletResponse res) {
        try (ServletOutputStream out = res.getOutputStream();){
            res.setCharacterEncoding("utf-8");
            res.setContentType("application/vnd.ms-excel");
            res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("\u6d4b\u8bd5\u6587\u4ef6", "UTF-8") + ".xlsx");
            XSSFWorkbook sheets = this.importTaskService.exportResult();
            sheets.write((OutputStream)out);
            out.flush();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

