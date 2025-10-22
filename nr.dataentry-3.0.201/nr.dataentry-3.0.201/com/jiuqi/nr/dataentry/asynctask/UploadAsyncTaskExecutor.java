/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nr.common.exception.NrCommonException
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.fileupload.service.FileUploadOssService
 */
package com.jiuqi.nr.dataentry.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.common.exception.NrCommonException;
import com.jiuqi.nr.dataentry.bean.AsyncUploadParam;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.dataentry.service.IUploadService;
import com.jiuqi.nr.dataentry.util.BatchExportConsts;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.fileupload.service.FileUploadOssService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_UPLOADFILE", cancellable=true, rollback=false, groupTitle="\u5bfc\u5165", subject="\u62a5\u8868", tags={"\u957f\u4efb\u52a1"})
public class UploadAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(UploadAsyncTaskExecutor.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void executeWithNpContext(JobContext jobContext) {
        block45: {
            String errorInfo = "task_error_info";
            String cancelInfo = "task_cancel_info";
            IUploadService uploadService = (IUploadService)BeanUtil.getBean(IUploadService.class);
            FileService fileService = (FileService)BeanUtil.getBean(FileService.class);
            NpApplication npApplication = (NpApplication)BeanUtil.getBean(NpApplication.class);
            FileUploadOssService fileUploadOssService = (FileUploadOssService)BeanUtil.getBean(FileUploadOssService.class);
            String taskId = jobContext.getInstanceId();
            AbstractRealTimeJob job = jobContext.getRealTimeJob();
            Map params = job.getParams();
            RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_UPLOADFILE.getName(), jobContext);
            String fileInfoKey = "";
            AsyncUploadParam asyncUploadParam = (AsyncUploadParam)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")));
            UploadParam param = asyncUploadParam.getParam();
            File file = null;
            File pathFile = null;
            try {
                if (StringUtils.isNotEmpty((String)param.getFilePath())) {
                    PathUtils.validatePathManipulation((String)param.getFilePath());
                    pathFile = new File(param.getFilePath());
                    uploadService.uploadAsync((AsyncTaskMonitor)asyncTaskMonitor, param, asyncUploadParam.getSuffix(), pathFile);
                    break block45;
                }
                SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                String fileLocation = sfDate.format(new Date()) + OrderGenerator.newOrder();
                String path = BatchExportConsts.UPLOADDIR + BatchExportConsts.SEPARATOR + fileLocation + BatchExportConsts.SEPARATOR;
                pathFile = new File(path);
                if (!pathFile.exists()) {
                    pathFile.mkdirs();
                }
                if (StringUtils.isNotEmpty((String)param.getFileKeyOfSOss())) {
                    file = new File(pathFile.getPath() + BatchExportConsts.SEPARATOR + param.getFileNameInfo());
                    try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
                        fileUploadOssService.downloadFileFormTemp(param.getFileKeyOfSOss(), (OutputStream)fileOutputStream);
                        param.setFileLocation(fileLocation);
                        uploadService.uploadAsync((AsyncTaskMonitor)asyncTaskMonitor, param, asyncUploadParam.getSuffix(), file);
                        fileInfoKey = param.getFileKeyOfSOss();
                        break block45;
                    }
                }
                FileInfo fileInfo = fileService.tempArea().getInfo(param.getFileKey());
                file = new File(pathFile.getPath() + BatchExportConsts.SEPARATOR + fileInfo.getName());
                try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
                    fileService.tempArea().download(fileInfo.getKey(), (OutputStream)fileOutputStream);
                    param.setFileLocation(fileLocation);
                    uploadService.uploadAsync((AsyncTaskMonitor)asyncTaskMonitor, param, asyncUploadParam.getSuffix(), file);
                    fileInfoKey = fileInfo.getKey();
                }
            }
            catch (NrCommonException nrCommonException) {
                asyncTaskMonitor.error(errorInfo, (Throwable)nrCommonException);
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + nrCommonException.getMessage(), nrCommonException);
            }
            catch (Exception e) {
                asyncTaskMonitor.error(errorInfo, (Throwable)e);
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            finally {
                String finalFileInfoKey = fileInfoKey;
                if (StringUtils.isNotEmpty((String)finalFileInfoKey) && StringUtils.isEmpty((String)param.getFileKeyOfSOss())) {
                    npApplication.asyncRun(() -> {
                        try {
                            fileService.tempArea().delete(finalFileInfoKey, Boolean.valueOf(false));
                        }
                        catch (Exception e) {
                            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                        }
                    });
                }
                try {
                    if (!StringUtils.isNotEmpty((String)param.getFilePath()) && file != null && file.exists()) {
                        file.delete();
                    }
                    if (!StringUtils.isNotEmpty((String)param.getFilePath()) && !pathFile.delete()) {
                        logger.info("\u6587\u4ef6\u5220\u9664\u5931\u8d25");
                    }
                }
                catch (Exception e) {
                    asyncTaskMonitor.error(errorInfo, (Throwable)e);
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
    }

    public String getTaskPoolType() {
        return AsynctaskPoolType.ASYNCTASK_UPLOADFILE.getName();
    }
}

