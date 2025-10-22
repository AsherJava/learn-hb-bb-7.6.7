/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.nr.data.common.logger.DataImportLogger
 *  com.jiuqi.nr.data.common.logger.DataIoLoggerFactory
 *  com.jiuqi.nr.data.common.logger.LoggerPartInfo
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  javax.annotation.Resource
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.io.service.impl;

import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.nr.data.common.logger.DataImportLogger;
import com.jiuqi.nr.data.common.logger.DataIoLoggerFactory;
import com.jiuqi.nr.data.common.logger.LoggerPartInfo;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.io.common.ExtConstants;
import com.jiuqi.nr.io.config.NrIoProperties;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.output.ImportInformations;
import com.jiuqi.nr.io.service.DataFileImportService;
import com.jiuqi.nr.io.service.FileImportService;
import com.jiuqi.nr.io.util.FileUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Resource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DataFileImportServiceImpl
implements DataFileImportService {
    private static final Logger log = LoggerFactory.getLogger(DataFileImportServiceImpl.class);
    public static final String MODULE_CSVFILE_UPLOAD = "\u6570\u636e\u670d\u52a1-\u591a\u7ea7\u90e8\u7f72CSV\u5bfc\u5165";
    @Resource
    private Map<String, FileImportService> fileImportService;
    @Autowired
    private NrIoProperties nrIoProperties;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private DataIoLoggerFactory dataIoLoggerFactory;

    @Override
    public List<Map<String, Object>> dataImport(TableContext param, MultipartFile file, AsyncTaskMonitor monitor) {
        ArrayList<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
        if (null == param.getDimensionSet()) {
            throw new RuntimeException("\u53c2\u6570\u4e3b\u4f53\u4e3a\u7a7a\uff0c\u4e0d\u80fd\u8fdb\u884c\u5bfc\u5165,\u8bf7\u68c0\u67e5\u5165\u53c2.");
        }
        File destFile = null;
        String path = ExtConstants.ROOTPATH + "/" + UUID.randomUUID().toString() + "/";
        try {
            destFile = FileUtil.createIfNotExists(FilenameUtils.normalize(path + file.getOriginalFilename()));
            file.transferTo(destFile);
            file = null;
        }
        catch (IOException | IllegalStateException e) {
            log.info("\u4fdd\u5b58\u4e34\u65f6\u6587\u4ef6\u51fa\u9519{}", e);
        }
        String fileName = destFile.getName();
        String[] split = fileName.split("\\.");
        String suffix = split[split.length - 1];
        String tmpPath = path + 1;
        if ("zip".equalsIgnoreCase(suffix)) {
            FileUtil.unZip(destFile, tmpPath);
        } else {
            try {
                FileUtils.copyFile(destFile, new File(FilenameUtils.normalize(tmpPath + "/" + destFile.getName())));
                FileUtils.forceDelete(destFile);
            }
            catch (IOException e) {
                log.info("\u79fb\u52a8\u6587\u4ef6\u51fa\u9519{}", e);
            }
        }
        List<File> files = FileUtil.getFiles(FilenameUtils.normalize(tmpPath), "Attament");
        FileImportService fileService = null;
        fileService = param.getFileType().equals(".json") ? this.fileImportService.get("JsonFileImportServiceImpl") : this.fileImportService.get("TxtFileImportServiceImpl");
        if (null != monitor) {
            monitor.progressAndMessage(0.3, "");
        }
        DimensionCombination dimensionCombination = new DimensionCombinationBuilder(param.getDimensionSet()).getCombination();
        DataImportLogger dataImportLogger = this.dataIoLoggerFactory.getDataImportLogger(MODULE_CSVFILE_UPLOAD, param.getFormSchemeKey(), dimensionCombination);
        dataImportLogger.startImport();
        for (File item : files) {
            if (item.getName().contains("_ROWDATAS") || item.getName().equalsIgnoreCase("successInfo.json") || item.getName().equalsIgnoreCase("errorInfo.json")) continue;
            Map<Object, Object> results = new HashMap();
            try {
                results = fileService.dealFileData(item, param, dataImportLogger);
                response.add(results);
                LoggerPartInfo loggerPartInfo = (LoggerPartInfo)results.get("log_info");
                this.recordLogPartInfo(loggerPartInfo, dataImportLogger);
            }
            catch (Exception e) {
                log.info("\u6570\u636e\u5bfc\u5165\u51fa\u9519\uff1a{}", (Object)e.getMessage(), (Object)e);
            }
            if (null == monitor) continue;
            monitor.progressAndMessage(0.7 / (double)files.size(), "");
        }
        FileUtil.deleteFiles(FilenameUtils.normalize(destFile.getPath().replace(destFile.getName(), "")));
        dataImportLogger.finishImport();
        return response;
    }

    @Override
    public List<Map<String, Object>> dataImportFile(TableContext param, File file, AsyncTaskMonitor monitor) {
        return this.dataImportFile(param, file, monitor, false);
    }

    @Override
    public List<Map<String, Object>> dataImportFile(TableContext param, File file, AsyncTaskMonitor monitor, boolean deleteFile) {
        ArrayList<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
        if (null == param.getDimensionSet()) {
            throw new RuntimeException("\u53c2\u6570\u4e3b\u4f53\u4e3a\u7a7a\uff0c\u4e0d\u80fd\u8fdb\u884c\u5bfc\u5165,\u8bf7\u68c0\u67e5\u5165\u53c2.");
        }
        String path = file.getPath();
        String fileName = file.getName();
        String[] split = fileName.split("\\.");
        String suffix = split[split.length - 1];
        String tmpPath = path + UUID.randomUUID().toString() + 1;
        if ("zip".equalsIgnoreCase(suffix)) {
            FileUtil.unZip(file, tmpPath);
        } else {
            try {
                if (!deleteFile) {
                    tmpPath = file.getAbsolutePath();
                } else {
                    FileUtils.copyFile(file, new File(FilenameUtils.normalize(tmpPath + "/" + file.getName())));
                    FileUtils.forceDelete(file);
                }
            }
            catch (IOException e) {
                log.info("\u79fb\u52a8\u6587\u4ef6\u51fa\u9519{}", e);
            }
        }
        List<File> files = FileUtil.getFiles(FilenameUtils.normalize(tmpPath), "Attament");
        FileImportService fileService = null;
        fileService = param.getFileType().equals(".json") ? this.fileImportService.get("JsonFileImportServiceImpl") : (param.getFileType().equals(".csv") ? this.fileImportService.get("CSVFileImportServiceImpl") : this.fileImportService.get("TxtFileImportServiceImpl"));
        if (null != monitor) {
            monitor.progressAndMessage(0.3, "");
        }
        if (null == files || files.isEmpty()) {
            HashMap<String, Object> results = new HashMap<String, Object>();
            results.put("msg", "error");
            ImportInformations error = new ImportInformations(null, null, null, String.format("\u538b\u7f29\u6587\u4ef6\u4e2d\u6ca1\u6709\u4efb\u4f55txt\u683c\u5f0f\u6570\u636e", new Object[0]), "");
            ArrayList<ImportInformations> errorInfo = new ArrayList<ImportInformations>();
            errorInfo.add(error);
            results.put("errorInfo", errorInfo);
            results.put("msg", "error");
            response.add(results);
        }
        DimensionCombination dimensionCombination = new DimensionCombinationBuilder(param.getDimensionSet()).getCombination();
        DataImportLogger dataImportLogger = this.dataIoLoggerFactory.getDataImportLogger(MODULE_CSVFILE_UPLOAD, param.getFormSchemeKey(), dimensionCombination);
        dataImportLogger.startImport();
        int splicThreadNum = this.nrIoProperties.getImpThreadSize();
        boolean multiThread = param.getFileType().equals(".csv") && splicThreadNum > 1 && param.getTempTable() == null;
        int availableThreadNum = splicThreadNum;
        ArrayList<String> taskIdList = new ArrayList<String>();
        if (files != null) {
            for (File item : files) {
                String taskId;
                Map<Object, Object> results;
                if (item.getName().contains("_ROWDATAS") || item.getName().equalsIgnoreCase("successInfo.json") || item.getName().equalsIgnoreCase("errorInfo.json")) continue;
                if (!item.getName().toLowerCase().contains(param.getFileType())) {
                    results = new HashMap<String, String>();
                    results.put("msg", "error");
                    ImportInformations error = new ImportInformations(null, null, null, String.format("\u6587\u4ef6 %s \u4e0d\u662f\u89c4\u8303\u7684%s\u683c\u5f0f\u6570\u636e", item.getName(), param.getFileType()), "");
                    ArrayList<ImportInformations> errorInfo = new ArrayList<ImportInformations>();
                    errorInfo.add(error);
                    results.put("errorInfo", errorInfo);
                    results.put("msg", "error");
                    response.add(results);
                    continue;
                }
                if (!multiThread) {
                    try {
                        results = fileService.dealFileData(item, param, dataImportLogger);
                        response.add(results);
                        LoggerPartInfo loggerPartInfo = (LoggerPartInfo)results.get("log_info");
                        this.recordLogPartInfo(loggerPartInfo, dataImportLogger);
                    }
                    catch (Exception e) {
                        log.info("\u6570\u636e\u5bfc\u5165\u51fa\u9519\uff1a{}", (Object)e.getMessage(), (Object)e);
                    }
                } else if (availableThreadNum > 0) {
                    try {
                        results = fileService.dealFileData(item, param, dataImportLogger);
                        if (results != null && StringUtils.hasText(taskId = (String)results.get("taskId"))) {
                            --availableThreadNum;
                            taskIdList.add(taskId);
                        }
                        response.add(results);
                    }
                    catch (Exception e) {
                        log.info("\u6570\u636e\u5bfc\u5165\u51fa\u9519\uff1a{}", (Object)e.getMessage(), (Object)e);
                    }
                } else {
                    this.waitAvailableThread(taskIdList, monitor, 0.7 / (double)files.size(), splicThreadNum, 1, response, dataImportLogger);
                    try {
                        results = fileService.dealFileData(item, param, dataImportLogger);
                        if (results != null && StringUtils.hasText(taskId = (String)results.get("taskId"))) {
                            --availableThreadNum;
                            taskIdList.add(taskId);
                        }
                        response.add(results);
                    }
                    catch (Exception e) {
                        log.info("\u6570\u636e\u5bfc\u5165\u51fa\u9519\uff1a{}", (Object)e.getMessage(), (Object)e);
                    }
                }
                if (null == monitor || multiThread) continue;
                monitor.progressAndMessage(monitor.getLastProgress() + 0.7 / (double)files.size(), "");
            }
        }
        if (multiThread) {
            this.waitAvailableThread(taskIdList, monitor, 0.7 / (double)files.size(), splicThreadNum, splicThreadNum, response, dataImportLogger);
        }
        if (!deleteFile) {
            FileUtil.deleteFiles(FilenameUtils.normalize(file.getPath().replace(file.getName(), "")));
        }
        dataImportLogger.finishImport();
        return response;
    }

    private void waitAvailableThread(List<String> taskIdList, AsyncTaskMonitor parentMonitor, double stepProgress, int allThreadNum, int needThreadNum, List<Map<String, Object>> response, DataImportLogger dataImportLogger) {
        while (allThreadNum - taskIdList.size() < needThreadNum) {
            Map<String, AsyncTask> tasks = this.queryTasks(taskIdList);
            for (Map.Entry<String, AsyncTask> entry : tasks.entrySet()) {
                String taskId = entry.getKey();
                AsyncTask asyncTask = entry.getValue();
                if (asyncTask.getState().equals((Object)TaskState.WAITING) || asyncTask.getState().equals((Object)TaskState.PROCESSING)) continue;
                if (asyncTask.getState().equals((Object)TaskState.FINISHED) || asyncTask.getState().equals((Object)TaskState.CANCELED) || asyncTask.getState().equals((Object)TaskState.ERROR)) {
                    if (parentMonitor != null) {
                        parentMonitor.progressAndMessage(parentMonitor.getLastProgress() + stepProgress, "");
                    }
                    if (asyncTask.getState().equals((Object)TaskState.FINISHED)) {
                        Map results = (Map)this.asyncTaskManager.queryDetail(taskId);
                        response.add(results);
                        if (results != null) {
                            LoggerPartInfo loggerPartInfo = (LoggerPartInfo)results.get("log_info");
                            this.recordLogPartInfo(loggerPartInfo, dataImportLogger);
                        }
                    }
                }
                taskIdList.remove(taskId);
            }
            try {
                Thread.sleep(3000L);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private Map<String, AsyncTask> queryTasks(List<String> taskIds) {
        AsyncTaskManager taskManager = (AsyncTaskManager)BeanUtils.getBean(AsyncTaskManager.class);
        HashMap<String, AsyncTask> tasks = new HashMap<String, AsyncTask>();
        for (String taskId : taskIds) {
            AsyncTask asyncTask = taskManager.querySimpleTask(taskId);
            tasks.put(taskId, asyncTask);
        }
        return tasks;
    }

    private void recordLogPartInfo(LoggerPartInfo loggerPartInfo, DataImportLogger dataImportLogger) {
        Set errorMessages;
        if (loggerPartInfo == null || dataImportLogger == null) {
            return;
        }
        Set unitCodes = loggerPartInfo.getUnitCodes();
        Set reportCodes = loggerPartInfo.getReportCodes();
        if (!unitCodes.isEmpty() && !reportCodes.isEmpty()) {
            try {
                for (String unitCode : unitCodes) {
                    for (String tableCode : reportCodes) {
                        dataImportLogger.addTableToUnit(unitCode, tableCode);
                    }
                }
            }
            catch (Exception e) {
                log.error("\u7ec4\u88c5\u7cfb\u7edf\u65e5\u5fd7\u53c2\u6570\u8fc7\u7a0b\u4e2d\u53d1\u751f\u9519\u8bef {}", (Object)e.getMessage(), (Object)e);
            }
        }
        if (!(errorMessages = loggerPartInfo.getErrorMessages()).isEmpty()) {
            errorMessages.forEach(arg_0 -> ((DataImportLogger)dataImportLogger).importError(arg_0));
        }
    }
}

