/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
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
import com.jiuqi.nr.attachment.transfer.dto.GenerateParamDTO;
import com.jiuqi.nr.attachment.transfer.dto.GenerateStatusDTO;
import com.jiuqi.nr.attachment.transfer.dto.RecordsQueryDTO;
import com.jiuqi.nr.attachment.transfer.exception.ExecuteException;
import com.jiuqi.nr.attachment.transfer.log.AttachmentLogHelper;
import com.jiuqi.nr.attachment.transfer.service.IAttachmentService;
import com.jiuqi.nr.attachment.transfer.service.IGenerateTaskService;
import com.jiuqi.nr.attachment.transfer.service.IWorkSpaceService;
import com.jiuqi.nr.attachment.transfer.vo.GenerateParamVO;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.Collections;
import java.util.List;
import nr.single.map.configurations.bean.SingleConfigInfo;
import nr.single.map.configurations.service.MappingConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/v1/attachment-transfer/generate"})
public class GenerateTaskController {
    @Autowired
    private IAttachmentService attachmentService;
    @Autowired
    private IGenerateTaskService generateTaskService;
    @Autowired
    private IWorkSpaceService workSpaceService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private MappingConfigService mappingConfigService;

    @PostMapping(value={"/start"})
    public boolean start(@RequestBody GenerateParamVO generateParamVO) throws JQException {
        AttachmentLogHelper.exportInfo("\u5f00\u59cb\u5bfc\u51fa", generateParamVO);
        try {
            return this.attachmentService.generateAttachment(GenerateParamDTO.getVOInstance(generateParamVO));
        }
        catch (Exception e) {
            AttachmentLogHelper.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)ExecuteException.FAILED_START, e.getMessage());
        }
    }

    @GetMapping(value={"/pause"})
    public boolean pause() throws JQException {
        AttachmentLogHelper.exportInfo("\u6267\u884c\u6682\u505c");
        try {
            return this.attachmentService.pausedGenerate();
        }
        catch (Exception e) {
            AttachmentLogHelper.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)ExecuteException.FAILED_PAUSE, e.getMessage());
        }
    }

    @GetMapping(value={"/resume"})
    public void resume() throws JQException {
        AttachmentLogHelper.exportInfo("\u6267\u884c\u6062\u590d");
        try {
            this.attachmentService.restartGenerate();
        }
        catch (Exception e) {
            AttachmentLogHelper.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)ExecuteException.FAILED_RESUME, e.getMessage());
        }
    }

    @GetMapping(value={"/reset"})
    public void reset() throws JQException {
        AttachmentLogHelper.exportInfo("\u6267\u884c\u91cd\u7f6e");
        try {
            this.attachmentService.resetGenerate();
        }
        catch (Exception e) {
            AttachmentLogHelper.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)ExecuteException.FAILED_RESET, e.getMessage());
        }
    }

    @GetMapping(value={"/cleanAndNext"})
    public void cleanAndNext() throws JQException {
        AttachmentLogHelper.exportInfo("\u6267\u884c\u6e05\u7406\u5e76\u7ee7\u7eed");
        try {
            this.attachmentService.cleanAndNext();
        }
        catch (Exception e) {
            AttachmentLogHelper.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)ExecuteException.FAILED_CLEAN, e.getMessage());
        }
    }

    @PostMapping(value={"/cleanFiles"})
    public void cleanFiles(@RequestBody List<String> keys) throws JQException {
        AttachmentLogHelper.exportInfo("\u6279\u91cf\u5220\u9664\u6587\u4ef6");
        try {
            this.attachmentService.cleanRecords(keys);
        }
        catch (Exception e) {
            AttachmentLogHelper.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)ExecuteException.FAILED_CLEAN, e.getMessage());
        }
    }

    @GetMapping(value={"/executeFailed"})
    public void executeFailed() throws JQException {
        AttachmentLogHelper.exportInfo("\u6267\u884c\u91cd\u65b0\u751f\u6210\u9519\u8bef\u6570\u636e");
        try {
            this.attachmentService.rebuildFailed();
        }
        catch (Exception e) {
            AttachmentLogHelper.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)ExecuteException.FAILED_EXE, e.getMessage());
        }
    }

    @GetMapping(value={"/rebuild/{key}"})
    public void rebuild(@PathVariable String key) throws JQException {
        AttachmentLogHelper.exportInfo("\u6267\u884c\u751f\u6210\u9519\u8bef\u6570\u636e", key);
        try {
            this.attachmentService.rebuild(key);
        }
        catch (Exception e) {
            AttachmentLogHelper.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)ExecuteException.FAILED_EXE, e.getMessage());
        }
    }

    @GetMapping(value={"/getConfig"})
    public GenerateParamVO getConfig() {
        GenerateParamDTO param = this.generateTaskService.getParam();
        if (param == null) {
            return null;
        }
        GenerateParamVO instance = GenerateParamVO.getInstance(param);
        TaskDefine task = this.runTimeViewController.getTask(instance.getTaskKey());
        instance.setTaskTitle(task.getTitle());
        SingleConfigInfo mapping = this.mappingConfigService.getMappingByKey(instance.getMapping());
        instance.setMappingTitle(mapping.getConfigName());
        instance.setSchemeKey(mapping.getSchemeKey());
        return instance;
    }

    @PostMapping(value={"/refresh"})
    public GenerateStatusDTO queryRecords(@RequestBody RecordsQueryDTO recordsQueryDTO) {
        return this.generateTaskService.queryRecords(recordsQueryDTO);
    }

    @GetMapping(value={"/listFile"})
    public List<FileInfoDTO> listFile() throws JQException {
        try {
            return Collections.emptyList();
        }
        catch (Exception e) {
            AttachmentLogHelper.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)ExecuteException.FAILED_EXE, e.getMessage());
        }
    }

    @GetMapping(value={"/cleanFile"})
    public int cleanFile() throws JQException {
        AttachmentLogHelper.exportInfo("\u6e05\u7406\u7a7a\u95f4");
        try {
            return this.workSpaceService.cleanFile(1);
        }
        catch (Exception e) {
            AttachmentLogHelper.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)ExecuteException.FAILED_EXE, e.getMessage());
        }
    }

    @GetMapping(value={"/checkError/{key}"})
    public String checkError(@PathVariable String key) throws JQException {
        try {
            return this.generateTaskService.queryError(key);
        }
        catch (Exception e) {
            AttachmentLogHelper.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)ExecuteException.FAILED_RESET, e.getMessage());
        }
    }
}

