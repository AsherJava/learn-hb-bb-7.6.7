/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 */
package com.jiuqi.nr.dafafill.async;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.dafafill.exception.DataFillRuntimeException;
import com.jiuqi.nr.dafafill.model.AsyncUploadInfo;
import com.jiuqi.nr.dafafill.service.IDataFillUploadService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

@RealTimeJob(group="ASYNCTASK_DATAFILL_UPLOADFILE", groupTitle="\u81ea\u5b9a\u4e49\u5f55\u5165\u4e0a\u4f20\u6587\u4ef6")
public class DataFillUploadAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DataFillUploadAsyncTaskExecutor.class);
    public static final String SEPARATOR = File.separator;
    public static final String ROOT_LOCATION = System.getProperty(FilenameUtils.normalize("java.io.tmpdir"));
    public static final String UPLOADDIR = ROOT_LOCATION + SEPARATOR + ".nr" + SEPARATOR + "AppData" + SEPARATOR + "datafillupload";

    private IDataFillUploadService getIDataFillUploadService(String suffix) {
        ApplicationContext applicationContext = SpringBeanUtils.getApplicationContext();
        Collection<IDataFillUploadService> dataFillUploadServices = applicationContext.getBeansOfType(IDataFillUploadService.class).values();
        Optional<IDataFillUploadService> findFirst = dataFillUploadServices.stream().filter(e -> e.accept(suffix)).findFirst();
        if (findFirst.isPresent()) {
            return findFirst.get();
        }
        throw new DataFillRuntimeException("suffix:" + suffix + ";not found IDataFillUploadExportService");
    }

    public String getTaskPoolType() {
        return "ASYNCTASK_DATAFILL_UPLOADFILE";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute(JobContext jobContext) {
        File tempFile = null;
        FileService fileService = (FileService)BeanUtil.getBean(FileService.class);
        Map args = jobContext.getRealTimeJob().getParams();
        String arg = (String)args.get("NR_ARGS");
        Object deserialize = SimpleParamConverter.SerializationUtils.deserialize((String)arg);
        if (deserialize instanceof AsyncUploadInfo) {
            AsyncUploadInfo asyncUploadInfo = (AsyncUploadInfo)deserialize;
            RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(jobContext.getInstanceId(), this.getTaskPoolType(), jobContext);
            SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String fileLocation = sfDate.format(new Date()) + OrderGenerator.newOrder();
            String path = UPLOADDIR + SEPARATOR + fileLocation + SEPARATOR;
            try {
                PathUtils.validatePathManipulation((String)path);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            File pathFile = new File(path);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            String dataentryUploadArea = "DATA_FILL";
            FileInfo fileInfo = fileService.area(dataentryUploadArea).getInfo(asyncUploadInfo.getFileKey());
            String temp = pathFile.getPath() + SEPARATOR + fileInfo.getName();
            try {
                PathUtils.validatePathManipulation((String)temp);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            tempFile = new File(temp);
            try (FileOutputStream fileOutputStream = new FileOutputStream(tempFile);){
                fileService.area(dataentryUploadArea).download(fileInfo.getKey(), (OutputStream)fileOutputStream);
                IDataFillUploadService dataFillUploadService = this.getIDataFillUploadService(asyncUploadInfo.getSuffix());
                dataFillUploadService.upload((AsyncTaskMonitor)asyncTaskMonitor, asyncUploadInfo, tempFile);
            }
            catch (Exception e) {
                asyncTaskMonitor.error("nr.dataFill.taskFail", (Throwable)e);
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            finally {
                if (null != tempFile) {
                    tempFile.delete();
                }
            }
        }
    }
}

