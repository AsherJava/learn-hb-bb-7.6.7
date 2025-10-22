/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.dafafill.service.impl;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.dafafill.async.DataFillExportAsyncTaskExecutor;
import com.jiuqi.nr.dafafill.async.DataFillUploadAsyncTaskExecutor;
import com.jiuqi.nr.dafafill.model.AsyncUploadInfo;
import com.jiuqi.nr.dafafill.model.DataFillDataQueryInfo;
import com.jiuqi.nr.dafafill.service.IDataFillDataEnvImportExportService;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class DataFillDataEnvImportExportServiceImpl
implements IDataFillDataEnvImportExportService {
    private static Logger logger = LoggerFactory.getLogger(DataFillDataEnvImportExportServiceImpl.class);
    @Autowired
    private FileService fileService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @Override
    public AsyncTaskInfo export(DataFillDataQueryInfo queryInfo) {
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        NpRealTimeTaskInfo taskInfo = new NpRealTimeTaskInfo();
        taskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)queryInfo));
        taskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new DataFillExportAsyncTaskExecutor());
        String asyncTaskId = this.asyncTaskManager.publishTask(taskInfo, "AT_DATAFILLEXPORT");
        asyncTaskInfo.setDetail((Object)"");
        asyncTaskInfo.setId(asyncTaskId);
        asyncTaskInfo.setProcess(Double.valueOf(0.0));
        asyncTaskInfo.setResult("");
        asyncTaskInfo.setState(TaskState.PROCESSING);
        return asyncTaskInfo;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public AsyncTaskInfo importData(DataFillDataQueryInfo queryInfo, MultipartFile multipartFile) {
        String taskId = UUID.randomUUID().toString();
        String dataentryUploadArea = "DATA_FILL";
        String fileName = multipartFile.getOriginalFilename();
        String[] split = fileName.split("\\.");
        String suffix = split[split.length - 1];
        if (suffix.equals("et")) {
            suffix = "xlsx";
        }
        FileInfo fileInfo = null;
        try (InputStream inputStream = multipartFile.getInputStream();){
            FileAreaService fileAreaService = this.fileService.area(dataentryUploadArea);
            long fileSizeByte = fileAreaService.getAreaConfig().getMaxFileSize();
            double fileSizeM = (double)fileSizeByte / 1048576.0;
            if (fileSizeByte < multipartFile.getSize()) {
                AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
                asyncTaskInfo.setId(taskId);
                asyncTaskInfo.setProcess(Double.valueOf(1.0));
                asyncTaskInfo.setResult("");
                asyncTaskInfo.setDetail((Object)("\u6587\u4ef6\u5927\u5c0f\u5927\u4e8e\u914d\u7f6e\u503c" + fileSizeM + "M!!!"));
                asyncTaskInfo.setState(TaskState.ERROR);
                AsyncTaskInfo asyncTaskInfo2 = asyncTaskInfo;
                return asyncTaskInfo2;
            }
            fileInfo = fileAreaService.upload(fileName, inputStream);
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
            asyncTaskInfo.setId(taskId);
            asyncTaskInfo.setProcess(Double.valueOf(1.0));
            asyncTaskInfo.setResult("");
            asyncTaskInfo.setDetail((Object)e.getMessage());
            asyncTaskInfo.setState(TaskState.ERROR);
            return asyncTaskInfo;
        }
        AsyncUploadInfo asyncUploadInfo = new AsyncUploadInfo();
        asyncUploadInfo.setQueryInfo(queryInfo);
        asyncUploadInfo.setFileKey(fileInfo.getKey());
        asyncUploadInfo.setSuffix(suffix);
        NpRealTimeTaskInfo taskInfo = new NpRealTimeTaskInfo();
        taskInfo.setTaskKey(taskId);
        taskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)asyncUploadInfo));
        taskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new DataFillUploadAsyncTaskExecutor());
        String asyncTaskId = this.asyncTaskManager.publishTask(taskInfo, "ASYNCTASK_DATAFILL_UPLOADFILE");
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asyncTaskId);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }
}

