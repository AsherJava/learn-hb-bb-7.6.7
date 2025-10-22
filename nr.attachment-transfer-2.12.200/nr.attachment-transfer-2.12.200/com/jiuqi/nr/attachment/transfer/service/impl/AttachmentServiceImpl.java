/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.attachment.transfer.service.impl;

import com.jiuqi.nr.attachment.transfer.common.Constant;
import com.jiuqi.nr.attachment.transfer.common.Utils;
import com.jiuqi.nr.attachment.transfer.dispatch.TaskExecutor;
import com.jiuqi.nr.attachment.transfer.domain.AttachmentRecordDO;
import com.jiuqi.nr.attachment.transfer.dto.AttachmentRecordDTO;
import com.jiuqi.nr.attachment.transfer.dto.GenerateParamDTO;
import com.jiuqi.nr.attachment.transfer.dto.ImportParamDTO;
import com.jiuqi.nr.attachment.transfer.dto.ImportRecordDTO;
import com.jiuqi.nr.attachment.transfer.dto.RecordsQueryDTO;
import com.jiuqi.nr.attachment.transfer.dto.WorkSpaceDTO;
import com.jiuqi.nr.attachment.transfer.service.IAttachmentService;
import com.jiuqi.nr.attachment.transfer.service.IGenerateTaskService;
import com.jiuqi.nr.attachment.transfer.service.IImportTaskService;
import com.jiuqi.nr.attachment.transfer.service.IWorkSpaceService;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
public class AttachmentServiceImpl
implements IAttachmentService {
    private static final Logger log = LoggerFactory.getLogger(AttachmentServiceImpl.class);
    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private IGenerateTaskService generateTaskService;
    @Autowired
    private IImportTaskService importTaskService;
    @Autowired
    private IWorkSpaceService workSpaceService;

    @Override
    @Transactional
    public boolean generateAttachment(GenerateParamDTO generateParamDTO) {
        Assert.notNull((Object)generateParamDTO.getTaskKey(), "\u4efb\u52a1\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)generateParamDTO.getPeriod(), "\u65f6\u671f\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)generateParamDTO.getMapping(), "\u6620\u5c04\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a");
        boolean alive = this.taskExecutor.isAlive();
        if (alive) {
            throw new RuntimeException("\u4efb\u52a1\u8fd0\u884c\u4e2d\uff0c\u8bf7\u5148\u6682\u505c\u4efb\u52a1");
        }
        boolean existed = this.generateTaskService.existRunningTask();
        if (existed) {
            return this.restartGenerate();
        }
        List<AttachmentRecordDTO> records = this.generateTaskService.initGenerateInfo(generateParamDTO);
        if (CollectionUtils.isEmpty(records)) {
            return false;
        }
        List<AttachmentRecordDTO> taskList = records.stream().filter(e -> e.getStatus() == Constant.GenerateStatus.NONE.getStatus()).collect(Collectors.toList());
        this.taskExecutor.startGenerator(generateParamDTO, taskList);
        return false;
    }

    @Override
    public boolean pausedGenerate() {
        boolean paused = this.taskExecutor.pause();
        if (!paused) {
            throw new RuntimeException("\u4efb\u52a1\u6682\u505c\u5931\u8d25\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5");
        }
        return true;
    }

    @Override
    public boolean rebuildFailed() {
        List<AttachmentRecordDTO> recordDTOS = this.generateTaskService.listRecordsByStatus(Constant.GenerateStatus.FAIL.getStatus());
        GenerateParamDTO param = this.generateTaskService.getParam();
        this.taskExecutor.rebuild(param, recordDTOS);
        return false;
    }

    @Override
    public boolean rebuild(String key) {
        GenerateParamDTO param = this.generateTaskService.getParam();
        AttachmentRecordDTO record = this.generateTaskService.queryRecord(key);
        if (record == null) {
            return false;
        }
        ArrayList<AttachmentRecordDTO> records = new ArrayList<AttachmentRecordDTO>(1);
        records.add(record);
        this.taskExecutor.rebuild(param, records);
        return false;
    }

    @Override
    public boolean restartGenerate() {
        boolean alive = this.taskExecutor.isAlive();
        if (alive) {
            return this.taskExecutor.restart();
        }
        List<AttachmentRecordDTO> exeRecords = this.generateTaskService.listRecordsByStatus(Constant.GenerateStatus.NONE.getStatus(), Constant.GenerateStatus.READY.getStatus(), Constant.GenerateStatus.DOING.getStatus(), Constant.GenerateStatus.CANCEL.getStatus());
        GenerateParamDTO param = this.generateTaskService.getParam();
        if (param == null) {
            throw new RuntimeException("\u67e5\u8be2\u4e0d\u5230\u4efb\u52a1\uff0c\u8bf7\u91cd\u65b0\u914d\u7f6e\u53c2\u6570");
        }
        this.taskExecutor.startGenerator(param, exeRecords);
        return true;
    }

    @Override
    public boolean cleanAndNext() {
        List<AttachmentRecordDO> finishedRecords = this.generateTaskService.cleanRecords();
        if (!CollectionUtils.isEmpty(finishedRecords)) {
            this.cleanWorkSpace(finishedRecords);
        }
        return this.restartGenerate();
    }

    @Override
    public boolean cleanRecords(List<String> keys) {
        List<AttachmentRecordDO> finishedRecords = this.generateTaskService.cleanRecords(keys);
        if (!CollectionUtils.isEmpty(finishedRecords)) {
            this.cleanWorkSpace(finishedRecords);
        }
        return true;
    }

    @Override
    public boolean resetGenerate() {
        this.taskExecutor.stopGenerator();
        this.generateTaskService.reset();
        this.workSpaceService.cleanFile(1);
        return true;
    }

    @Override
    @Transactional
    public boolean importAttachment(ImportParamDTO importParamDTO) {
        Assert.notNull((Object)importParamDTO.getTaskKey(), "\u4efb\u52a1\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)importParamDTO.getPeriod(), "\u65f6\u671f\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)importParamDTO.getMapping(), "\u6620\u5c04\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a");
        WorkSpaceDTO workSpaceDTO = this.workSpaceService.getConfig(2);
        importParamDTO.setFilePath(workSpaceDTO.getFilePath());
        importParamDTO.setSpaceSize(workSpaceDTO.getSpaceSize());
        List<ImportRecordDTO> importRecords = this.importTaskService.initImportInfo(importParamDTO);
        this.taskExecutor.startImport(importParamDTO, importRecords, workSpaceDTO.getThread());
        return true;
    }

    @Override
    public boolean reloadFailed() {
        ImportParamDTO importParamDTO = this.importTaskService.getParam();
        if (importParamDTO == null) {
            throw new RuntimeException("\u8bf7\u91cd\u65b0\u914d\u7f6e\u5bfc\u5165\u53c2\u6570");
        }
        WorkSpaceDTO workSpaceDTO = this.workSpaceService.getConfig(2);
        importParamDTO.setFilePath(workSpaceDTO.getFilePath());
        importParamDTO.setSpaceSize(workSpaceDTO.getSpaceSize());
        RecordsQueryDTO queryDTO = new RecordsQueryDTO();
        queryDTO.setStatus(Constant.ImportStatus.FAIL.getStatus());
        List<ImportRecordDTO> recordDTOS = this.importTaskService.listRecords(queryDTO);
        this.taskExecutor.reLoad(importParamDTO, recordDTOS, workSpaceDTO.getThread());
        return true;
    }

    @Override
    public boolean resetImport() {
        this.taskExecutor.stopGenerator();
        this.importTaskService.reset();
        this.workSpaceService.cleanFile(2);
        return false;
    }

    @Override
    public boolean reload(String key) {
        ImportParamDTO importParamDTO = this.importTaskService.getParam();
        WorkSpaceDTO workSpaceDTO = this.workSpaceService.getConfig(2);
        importParamDTO.setFilePath(workSpaceDTO.getFilePath());
        importParamDTO.setSpaceSize(workSpaceDTO.getSpaceSize());
        ArrayList<ImportRecordDTO> recordDTOS = new ArrayList<ImportRecordDTO>();
        ImportRecordDTO recordDTO = this.importTaskService.queryRecord(key);
        if (recordDTO == null) {
            return false;
        }
        recordDTOS.add(recordDTO);
        this.taskExecutor.reLoad(importParamDTO, recordDTOS, workSpaceDTO.getThread());
        return false;
    }

    @Override
    public boolean pausedImport() {
        this.taskExecutor.pause();
        return false;
    }

    @Override
    public boolean restartImport() {
        if (this.taskExecutor.isAlive()) {
            return this.taskExecutor.restart();
        }
        ImportParamDTO importParamDTO = this.importTaskService.getParam();
        WorkSpaceDTO workSpaceDTO = this.workSpaceService.getConfig(2);
        importParamDTO.setFilePath(workSpaceDTO.getFilePath());
        importParamDTO.setSpaceSize(workSpaceDTO.getSpaceSize());
        List<ImportRecordDTO> recordDTOS = this.importTaskService.listNeedResume();
        this.taskExecutor.startImport(importParamDTO, recordDTOS, workSpaceDTO.getThread());
        return false;
    }

    private void cleanWorkSpace(List<AttachmentRecordDO> finishedRecords) {
        WorkSpaceDTO config = this.workSpaceService.getConfig(1);
        String filePath = config.getFilePath();
        for (AttachmentRecordDO record : finishedRecords) {
            try {
                Utils.cleanFile(filePath + File.separator + "JIOEXPORT" + File.separator + record.getFileName());
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}

