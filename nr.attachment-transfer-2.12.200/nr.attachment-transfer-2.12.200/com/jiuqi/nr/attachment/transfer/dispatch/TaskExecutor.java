/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.attachment.transfer.dispatch;

import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.attachment.transfer.check.DiskSpaceCheck;
import com.jiuqi.nr.attachment.transfer.check.MemorySpaceCheck;
import com.jiuqi.nr.attachment.transfer.check.ResourceCheckFactory;
import com.jiuqi.nr.attachment.transfer.context.DefaultTaskContext;
import com.jiuqi.nr.attachment.transfer.context.TaskContext;
import com.jiuqi.nr.attachment.transfer.dao.IGenerateRecordDao;
import com.jiuqi.nr.attachment.transfer.dao.IGenerateTaskDao;
import com.jiuqi.nr.attachment.transfer.dao.IImportRecordDao;
import com.jiuqi.nr.attachment.transfer.dao.IImportTaskDao;
import com.jiuqi.nr.attachment.transfer.dispatch.TaskDaemonThread;
import com.jiuqi.nr.attachment.transfer.dispatch.TaskMonitorThread;
import com.jiuqi.nr.attachment.transfer.dispatch.TaskScheduler;
import com.jiuqi.nr.attachment.transfer.domain.GenerateTaskDO;
import com.jiuqi.nr.attachment.transfer.dto.AttachmentRecordDTO;
import com.jiuqi.nr.attachment.transfer.dto.GenerateParamDTO;
import com.jiuqi.nr.attachment.transfer.dto.ImportParamDTO;
import com.jiuqi.nr.attachment.transfer.dto.ImportRecordDTO;
import com.jiuqi.nr.attachment.transfer.dto.WorkSpaceDTO;
import com.jiuqi.nr.attachment.transfer.monitor.DefaultTaskMonitor;
import com.jiuqi.nr.attachment.transfer.monitor.GenerateStatusModifier;
import com.jiuqi.nr.attachment.transfer.monitor.GenerateTaskModifier;
import com.jiuqi.nr.attachment.transfer.monitor.ImportStatusModifier;
import com.jiuqi.nr.attachment.transfer.monitor.ImportTaskModifier;
import com.jiuqi.nr.attachment.transfer.service.IWorkSpaceService;
import com.jiuqi.nr.attachment.transfer.task.AbstractTaskProvider;
import com.jiuqi.nr.attachment.transfer.task.GenerateTask;
import com.jiuqi.nr.attachment.transfer.task.GeneratorAttachmentTask;
import com.jiuqi.nr.attachment.transfer.task.ImportAttachmentTask;
import com.jiuqi.nr.attachment.transfer.task.ImportTask;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskExecutor {
    private volatile TaskDaemonThread taskDaemonThread;
    private volatile TaskMonitorThread taskMonitorThread;
    @Autowired
    private IWorkSpaceService workSpaceService;
    @Autowired
    private IGenerateRecordDao generateRecordDao;
    @Autowired
    private IImportRecordDao importRecordDao;
    @Autowired
    private IGenerateTaskDao generateTaskDao;
    @Autowired
    private IImportTaskDao importTaskDao;

    public void startGenerator(GenerateParamDTO generateParamDTO, List<AttachmentRecordDTO> taskList) {
        WorkSpaceDTO workSpaceDTO = this.getWorkSpaceDTO(generateParamDTO);
        GeneratorAttachmentTask task = new GeneratorAttachmentTask(generateParamDTO, taskList);
        ResourceCheckFactory checkFactory = new ResourceCheckFactory();
        checkFactory.buildDiskChecker(new DiskSpaceCheck(generateParamDTO.getRealFilePath(), generateParamDTO.getSpaceSize()));
        checkFactory.buildMemoryChecker(new MemorySpaceCheck(5L));
        String serverId = DistributionManager.getInstance().self().getName();
        GenerateTaskDO query = this.generateTaskDao.query(serverId);
        DefaultTaskMonitor defaultTaskMonitor = new DefaultTaskMonitor(query.getKey());
        GenerateTaskModifier taskModifier = new GenerateTaskModifier(this.generateTaskDao);
        defaultTaskMonitor.setStatusModifier(taskModifier);
        GenerateStatusModifier modifier = new GenerateStatusModifier(this.generateRecordDao);
        DefaultTaskContext defaultTaskContext = new DefaultTaskContext(defaultTaskMonitor, NpContextHolder.getContext(), checkFactory, modifier);
        this.startTask(task, defaultTaskContext, workSpaceDTO.getThread());
    }

    private WorkSpaceDTO getWorkSpaceDTO(GenerateParamDTO generateParamDTO) {
        WorkSpaceDTO workSpaceDTO = this.workSpaceService.getConfig(1);
        generateParamDTO.setFilePath(workSpaceDTO.getFilePath());
        generateParamDTO.setSpaceSize(workSpaceDTO.getSpaceSize());
        return workSpaceDTO;
    }

    public void startImport(ImportParamDTO importParamDTO, List<ImportRecordDTO> importRecords, int thread) {
        ImportAttachmentTask task = new ImportAttachmentTask(importParamDTO, importRecords);
        ResourceCheckFactory checkFactory = new ResourceCheckFactory();
        checkFactory.buildMemoryChecker(new MemorySpaceCheck(5L));
        DefaultTaskMonitor taskMonitor = new DefaultTaskMonitor(importParamDTO.getHex());
        ImportTaskModifier importModifier = new ImportTaskModifier(this.importTaskDao);
        taskMonitor.setStatusModifier(importModifier);
        ImportStatusModifier modifier = new ImportStatusModifier(this.importRecordDao);
        DefaultTaskContext defaultTaskContext = new DefaultTaskContext(taskMonitor, NpContextHolder.getContext(), checkFactory, modifier);
        this.startTask(task, defaultTaskContext, thread);
    }

    public boolean pause() {
        if (this.taskMonitorThread == null) {
            throw new RuntimeException("\u4efb\u52a1\u4e0d\u5b58\u5728\uff0c\u8bf7\u91cd\u65b0\u5c1d\u8bd5");
        }
        return this.taskMonitorThread.pauseThread();
    }

    public boolean restart() {
        if (this.taskMonitorThread == null) {
            throw new RuntimeException("\u4efb\u52a1\u5df2\u88ab\u5173\u95ed\uff0c\u8bf7\u5237\u65b0\u754c\u9762\u540e\u91cd\u8bd5");
        }
        return this.taskMonitorThread.resumeThread();
    }

    public boolean isAlive() {
        return this.taskMonitorThread != null && this.taskMonitorThread != null && this.taskMonitorThread.isRunning();
    }

    public void stopGenerator() {
        if (this.taskMonitorThread != null) {
            this.taskMonitorThread.stopThread(true, true, true);
        }
    }

    public void rebuild(GenerateParamDTO generateParamDTO, List<AttachmentRecordDTO> records) {
        if (this.taskDaemonThread != null) {
            this.getWorkSpaceDTO(generateParamDTO);
            for (AttachmentRecordDTO record : records) {
                this.taskDaemonThread.getTaskProvider().append(new GenerateTask(record, generateParamDTO));
            }
        } else {
            this.startGenerator(generateParamDTO, records);
        }
    }

    public void reLoad(ImportParamDTO importParamDTO, List<ImportRecordDTO> records, int thread) {
        if (this.taskDaemonThread != null && this.taskMonitorThread.isRunning()) {
            for (ImportRecordDTO record : records) {
                this.taskDaemonThread.getTaskProvider().append(new ImportTask(record, importParamDTO));
            }
        } else {
            this.startImport(importParamDTO, records, thread);
        }
    }

    public void startTask(AbstractTaskProvider taskProvider, TaskContext taskContext, int threadNum) {
        TaskScheduler taskScheduler = new TaskScheduler(threadNum);
        if (this.taskDaemonThread != null && this.taskDaemonThread.isRunning()) {
            throw new RuntimeException("\u4efb\u52a1\u6267\u884c\u4e2d\uff0c\u8bf7\u52ff\u91cd\u590d\u6267\u884c");
        }
        if (this.taskMonitorThread != null && this.taskMonitorThread.isRunning()) {
            throw new RuntimeException("\u4efb\u52a1\u6267\u884c\u4e2d\uff0c\u8bf7\u52ff\u91cd\u590d\u6267\u884c");
        }
        this.taskDaemonThread = new TaskDaemonThread(taskScheduler, taskProvider, taskContext);
        this.taskMonitorThread = new TaskMonitorThread(taskScheduler, taskContext);
        this.taskDaemonThread.start();
        this.taskMonitorThread.start();
    }
}

