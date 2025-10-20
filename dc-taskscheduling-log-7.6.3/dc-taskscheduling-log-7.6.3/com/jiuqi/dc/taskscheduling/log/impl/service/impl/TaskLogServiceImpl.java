/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.excel.EasyExcel
 *  com.alibaba.excel.ExcelWriter
 *  com.alibaba.excel.write.metadata.WriteSheet
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.utils.Pair
 *  javax.annotation.Resource
 *  org.jetbrains.annotations.NotNull
 *  org.springframework.transaction.annotation.Propagation
 */
package com.jiuqi.dc.taskscheduling.log.impl.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.utils.Pair;
import com.jiuqi.dc.taskscheduling.log.impl.dao.TaskLogDao;
import com.jiuqi.dc.taskscheduling.log.impl.data.ArchiveParam;
import com.jiuqi.dc.taskscheduling.log.impl.data.TaskCountQueryDTO;
import com.jiuqi.dc.taskscheduling.log.impl.domain.SqlExecuteArchiveLogDO;
import com.jiuqi.dc.taskscheduling.log.impl.domain.SqlInfoLogDO;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskItemLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.enums.ArchiveMode;
import com.jiuqi.dc.taskscheduling.log.impl.enums.ArchiveType;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DimType;
import com.jiuqi.dc.taskscheduling.log.impl.service.TaskLogService;
import com.jiuqi.dc.taskscheduling.log.impl.util.TaskInfoUtil;
import com.jiuqi.dc.taskscheduling.log.impl.util.TaskSchedulingServerUtil;
import com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

@Service
public class TaskLogServiceImpl
implements TaskLogService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TaskLogDao taskLogDao;
    @Autowired
    private TaskLogServiceImpl taskLogService;
    @Value(value="${spring.application.name}")
    private String currAppName;
    @Resource
    private TaskSchedulingServerUtil serverUtil;

    @Override
    @OuterTransaction(propagation=Propagation.REQUIRES_NEW)
    public void insertTaskLogs(DcTaskLogEO taskInfo, List<DcTaskItemLogEO> taskItemList) {
        this.taskLogDao.insertTaskLogs(taskInfo);
        this.taskLogDao.insertTaskItems(taskItemList);
    }

    @Override
    public void insertSuccessTaskAndItemLogs(String runnerId, String taskType, String resultLog, Date startTime) {
        this.insertTaskAndItemLogs(runnerId, taskType, resultLog, startTime, DataHandleState.SUCCESS.getState());
    }

    @Override
    public void insertFailureTaskAndItemLogs(String runnerId, String taskType, String resultLog, Date startTime) {
        this.insertTaskAndItemLogs(runnerId, taskType, resultLog, startTime, DataHandleState.FAILURE.getState());
    }

    private void insertTaskAndItemLogs(String runnerId, String taskType, String resultLog, Date startTime, int executeState) {
        Date endTime = DateUtils.now();
        DcTaskLogEO taskInfo = TaskInfoUtil.createTaskInfo(taskType);
        taskInfo.setId(runnerId);
        taskInfo.setResultLog(resultLog);
        taskInfo.setEndTime(endTime);
        ArrayList<DcTaskItemLogEO> taskItemList = new ArrayList<DcTaskItemLogEO>();
        DcTaskItemLogEO taskItem = TaskInfoUtil.createTaskItemInfo(taskType, UUIDUtils.newHalfGUIDStr(), UUIDUtils.emptyHalfGUIDStr(), DimType.UNITCODE, null, null, runnerId);
        taskItem.setResultLog(resultLog);
        taskItem.setExecuteState(executeState);
        taskItem.setStartTime(startTime);
        taskItem.setEndTime(endTime);
        taskItem.setExecuteAppName(this.currAppName);
        taskItemList.add(taskItem);
        this.insertTaskLogs(taskInfo, taskItemList);
    }

    @Override
    @OuterTransaction(propagation=Propagation.REQUIRES_NEW)
    public void insertTaskItemLogs(List<DcTaskItemLogEO> taskItemList) {
        this.taskLogDao.insertTaskItems(taskItemList);
    }

    @Override
    @OuterTransaction(propagation=Propagation.REQUIRES_NEW)
    public void updateTaskLogSourceTypeAndExtFields(String id, int sourceType, Map<String, Object> fieldValues) {
        if (fieldValues == null || fieldValues.isEmpty()) {
            fieldValues = new HashMap<String, Object>(1);
        }
        fieldValues.put("SOURCETYPE", String.valueOf(sourceType));
        this.taskLogDao.updateTaskLog(id, fieldValues);
    }

    @Override
    @OuterTransaction(propagation=Propagation.REQUIRES_NEW)
    public void updateTaskLogExtFields(String id, Map<String, Object> fieldValues) {
        if (fieldValues == null || fieldValues.isEmpty()) {
            return;
        }
        this.taskLogDao.updateTaskLog(id, fieldValues);
    }

    @Override
    @OuterTransaction(propagation=Propagation.REQUIRES_NEW)
    public void updateTaskResult(String id, String resultLog) {
        this.taskLogDao.updateTaskResult(id, resultLog);
    }

    @Override
    @OuterTransaction(propagation=Propagation.REQUIRES_NEW)
    public void updateTaskItemStartTimeById(String itemId, String queueName) {
        this.taskLogDao.updateTaskItemStartById(itemId, queueName, this.currAppName, this.serverUtil.getServerIp());
    }

    @Override
    @OuterTransaction(propagation=Propagation.REQUIRES_NEW)
    public void updateTaskItemProgress(String id, double progress, String log) {
        this.taskLogDao.updateTaskItemProgress(id, progress, log);
    }

    @Override
    @OuterTransaction(propagation=Propagation.REQUIRES_NEW)
    public void updateTaskItemResultById(String itemId, DataHandleState handleState, String resultLog) {
        this.taskLogDao.updateTaskItemResultById(itemId, handleState, resultLog);
    }

    @Override
    public Integer getUnFinishTaskItemCount(String runnerId) {
        return this.taskLogDao.getStateCountByRunnerId(runnerId, CollectionUtils.newArrayList((Object[])new DataHandleState[]{DataHandleState.UNEXECUTE, DataHandleState.EXECUTING}));
    }

    @Override
    public Integer getStateCountByRunnerId(String runnerId, DataHandleState handleState) {
        return this.taskLogDao.getStateCountByRunnerId(runnerId, handleState);
    }

    @Override
    @Deprecated
    public List<String> listTaskItemDimCode(String runnerId, DataHandleState handleState) {
        return this.taskLogDao.listTaskItemDimCode(runnerId, handleState);
    }

    @Override
    @OuterTransaction
    public void cancelTask(String runnerId) {
        this.taskLogDao.cancelTask(runnerId);
    }

    @Override
    public boolean isCancel(String id) {
        return this.taskLogDao.isCancel(id);
    }

    @Override
    public Double getTaskProgressByRunnerId(String runnerId) {
        return this.taskLogDao.getTaskProgressByRunnerId(runnerId);
    }

    @Override
    public Integer getStateCountByRunnerIdAndDimCode(String runnerId, String handlerName, String dimCode, DataHandleState handleState) {
        return this.taskLogDao.getStateCountByRunnerIdAndDimCode(runnerId, handlerName, dimCode, handleState);
    }

    @Override
    public String logArchive(ArchiveParam archiveParam) throws Exception {
        Object taskInfoItemWriter;
        ArchiveType archiveType = Optional.ofNullable(archiveParam).map(e -> ArchiveType.fromCode(e.getArchiveType())).orElseThrow(() -> new BusinessRuntimeException("\u9884\u5904\u7406\u65e5\u5fd7\u5f52\u6863\u7c7b\u578b\u4e3a\u7a7a,\u8bf7\u68c0\u67e5"));
        ArchiveMode archiveMode = Optional.ofNullable(archiveParam).map(e -> ArchiveMode.fromCode(e.getArchiveMode())).orElseThrow(() -> new BusinessRuntimeException("\u9884\u5904\u7406\u65e5\u5fd7\u5f52\u6863\u6a21\u5f0f\u4e3a\u7a7a,\u8bf7\u68c0\u67e5"));
        Date archiveEndDate = DateUtils.shifting((Date)DateUtils.now(), (int)archiveType.getType(), (int)(archiveType.getValue() * -1));
        List<ArchiveParam> archiveDimList = this.taskLogDao.getArchiveDimList(archiveEndDate);
        if (CollectionUtils.isEmpty(archiveDimList)) {
            return "\u6ca1\u6709\u5f85\u5f52\u6863\u7684\u65e5\u5fd7.\n";
        }
        HashMap logTableMap = CollectionUtils.newHashMap();
        logTableMap.put("GC_LOG_SQLEXECUTE", new Pair(SqlExecuteArchiveLogDO.class, (Object)"LOGID"));
        if (ArchiveMode.ONLY_CLEAR.equals((Object)archiveMode)) {
            for (ArchiveParam archiveDim : archiveDimList) {
                this.taskLogService.archiveByDim(archiveDim, logTableMap);
            }
            return "\u5f85\u5f52\u6863\u7684\u65e5\u5fd7\u5df2\u6e05\u9664";
        }
        String path = System.getProperty("user.dir") + File.separator + "logs" + File.separator + "taskLog" + File.separator;
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        HashMap excelWriterMap = CollectionUtils.newHashMap();
        try (ExcelWriter taskInfoWriter = EasyExcel.write((String)(path + "GC_LOG_TASKINFO" + ".csv"), DcTaskLogEO.class).build();){
            taskInfoItemWriter = EasyExcel.write((String)(path + "GC_LOG_TASKITEMINFO" + ".csv"), DcTaskLogEO.class).build();
            Object object = null;
            try (ExcelWriter taskSqlInfoWriter2 = EasyExcel.write((String)(path + "GC_LOG_SQLINFO" + ".csv"), SqlInfoLogDO.class).build();
                 ExcelWriter taskSqlExcuteWriter = EasyExcel.write((String)(path + "GC_LOG_SQLEXECUTE" + ".csv"), SqlExecuteArchiveLogDO.class).build();){
                excelWriterMap.put("GC_LOG_TASKINFO", taskInfoWriter);
                excelWriterMap.put("GC_LOG_TASKITEMINFO", taskInfoItemWriter);
                excelWriterMap.put("GC_LOG_SQLINFO", taskSqlInfoWriter2);
                excelWriterMap.put("GC_LOG_SQLEXECUTE", taskSqlExcuteWriter);
                for (ArchiveParam archiveDim : archiveDimList) {
                    this.taskLogService.archiveByDim(archiveDim, logTableMap, excelWriterMap);
                }
            }
            catch (Throwable taskSqlInfoWriter2) {
                object = taskSqlInfoWriter2;
                throw taskSqlInfoWriter2;
            }
            finally {
                if (taskInfoItemWriter != null) {
                    if (object != null) {
                        try {
                            taskInfoItemWriter.close();
                        }
                        catch (Throwable taskSqlInfoWriter2) {
                            ((Throwable)object).addSuppressed(taskSqlInfoWriter2);
                        }
                    } else {
                        taskInfoItemWriter.close();
                    }
                }
            }
        }
        catch (Exception e2) {
            this.logger.error("\u5199\u5165\u5931\u8d25\uff01\uff01\uff01");
        }
        String fileName = DateUtils.nowTimeStr((String)"yyyyMMddHHmmssSSS") + "-taskLog.zip";
        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(path + fileName));
            taskInfoItemWriter = null;
            try {
                for (String csvFileName : excelWriterMap.keySet()) {
                    File file = new File(path + csvFileName + ".csv");
                    zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
                    Files.copy(file.toPath(), zipOutputStream);
                }
                zipOutputStream.closeEntry();
            }
            catch (Throwable throwable) {
                taskInfoItemWriter = throwable;
                throw throwable;
            }
            finally {
                if (zipOutputStream != null) {
                    if (taskInfoItemWriter != null) {
                        try {
                            zipOutputStream.close();
                        }
                        catch (Throwable throwable) {
                            ((Throwable)taskInfoItemWriter).addSuppressed(throwable);
                        }
                    } else {
                        zipOutputStream.close();
                    }
                }
            }
        }
        catch (Exception e3) {
            this.logger.error("\u4efb\u52a1\u65e5\u5fd7\u5f52\u6863\u5931\u8d25\u3002", e3);
            throw e3;
        }
        finally {
            for (String csvFileName : excelWriterMap.keySet()) {
                File csvTempFile = new File(path + csvFileName + ".csv");
                csvTempFile.delete();
            }
        }
        return "CSV\u6587\u4ef6\u5df2\u5bfc\u51fa\u5e76\u6253\u5305\u6210\u538b\u7f29\u6587\u4ef6\u3002\u6587\u4ef6\u8def\u5f84\uff1a" + path + ", \u6587\u4ef6\u540d\u79f0\uff1a\n" + fileName;
    }

    @Override
    public Integer countTask(TaskCountQueryDTO queryParam) {
        Assert.isNotEmpty((String)queryParam.getRunnerId(), (String)"\u4efb\u52a1id\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        return this.taskLogDao.countTask(queryParam);
    }

    @OuterTransaction(propagation=Propagation.REQUIRES_NEW)
    @NotNull
    public void archiveByDim(ArchiveParam archiveDim, Map<String, Pair<Class, String>> logTableMap, Map<String, ExcelWriter> excelWriterMap) {
        this.taskLogDao.prepareTaskInfoTemp(archiveDim.getArchivePeriod());
        WriteSheet writeSheet = EasyExcel.writerSheet((String)"").build();
        excelWriterMap.get("GC_LOG_TASKINFO").write(this.taskLogDao.getTaskInfoArchiveLog(archiveDim.getArchivePeriod()), writeSheet);
        this.taskLogDao.deleteTaskInfoLog(archiveDim.getArchivePeriod());
        excelWriterMap.get("GC_LOG_TASKITEMINFO").write(this.taskLogDao.getArchiveLog("GC_LOG_TASKITEMINFO", DcTaskItemLogEO.class, "RUNNERID", "RUNNERID"), writeSheet);
        this.taskLogDao.prepareTaskItemInfoTemp();
        this.taskLogDao.deleteArchiveLog("GC_LOG_TASKITEMINFO", "RUNNERID", "RUNNERID");
        this.taskLogDao.prepareSqlExcuteInfoTemp();
        for (Map.Entry<String, Pair<Class, String>> entry : logTableMap.entrySet()) {
            excelWriterMap.get(entry.getKey()).write(this.taskLogDao.getArchiveLog(entry.getKey(), (Class)entry.getValue().getFirst(), (String)entry.getValue().getSecond(), "LOGID"), writeSheet);
            this.taskLogDao.deleteArchiveLog(entry.getKey(), (String)entry.getValue().getSecond(), "LOGID");
        }
        excelWriterMap.get("GC_LOG_SQLINFO").write(this.taskLogDao.getArchiveLog("GC_LOG_SQLINFO", SqlInfoLogDO.class, "ID", "SQLINFOID"), writeSheet);
        this.taskLogDao.clearTemp();
    }

    @OuterTransaction(propagation=Propagation.REQUIRES_NEW)
    @NotNull
    public void archiveByDim(ArchiveParam archiveDim, Map<String, Pair<Class, String>> logTableMap) {
        this.taskLogDao.prepareTaskInfoTemp(archiveDim.getArchivePeriod());
        this.taskLogDao.deleteTaskInfoLog(archiveDim.getArchivePeriod());
        this.taskLogDao.prepareTaskItemInfoTemp();
        this.taskLogDao.deleteArchiveLog("GC_LOG_TASKITEMINFO", "RUNNERID", "RUNNERID");
        this.taskLogDao.prepareSqlExcuteInfoTemp();
        for (Map.Entry<String, Pair<Class, String>> entry : logTableMap.entrySet()) {
            this.taskLogDao.deleteArchiveLog(entry.getKey(), (String)entry.getValue().getSecond(), "LOGID");
        }
        this.taskLogDao.clearTemp();
    }

    @Override
    public List<LogManagerDetailVO> queryTaskLog(TaskCountQueryDTO queryDto) {
        Assert.isNotNull((Object)queryDto);
        Assert.isNotEmpty((String)queryDto.getRunnerId());
        return this.taskLogDao.queryTaskLog(queryDto);
    }
}

