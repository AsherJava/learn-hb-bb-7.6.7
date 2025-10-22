/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.fileupload.service.FileUploadOssService
 *  com.jiuqi.nr.fileupload.util.FileUploadUtils
 */
package com.jiuqi.nr.io.asynctask.executor;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.fileupload.service.FileUploadOssService;
import com.jiuqi.nr.fileupload.util.FileUploadUtils;
import com.jiuqi.nr.io.asynctask.bean.CSVFileImportInfo;
import com.jiuqi.nr.io.asynctask.bean.RegionDataSetContext;
import com.jiuqi.nr.io.common.ExtConstants;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.service.MultistageUnitReplace;
import com.jiuqi.nr.io.service.impl.CSVFileImportServiceImpl;
import com.jiuqi.nr.io.service.impl.IoQualifierImpl;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RealTimeJob(group="ASYNCTASK_IMPORTCSV", groupTitle="CSV\u5bfc\u5165", subject="\u62a5\u8868", tags={"\u957f\u4efb\u52a1"})
public class CSVFileImportAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final Logger log = LoggerFactory.getLogger(CSVFileImportAsyncTaskExecutor.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        File file;
        File pathFile;
        block29: {
            CSVFileImportServiceImpl csvFileImportAsyncService = (CSVFileImportServiceImpl)BeanUtil.getBean(CSVFileImportServiceImpl.class);
            FileUploadOssService fileUploadOssService = (FileUploadOssService)BeanUtil.getBean(FileUploadOssService.class);
            String taskId = jobContext.getInstanceId();
            AbstractRealTimeJob job = jobContext.getRealTimeJob();
            Map params = job.getParams();
            RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, AsynctaskPoolType.ASYNCTASK_BATCHCOPY.getName(), jobContext);
            String fileKey = null;
            pathFile = null;
            file = null;
            try {
                if (Objects.nonNull(params) && Objects.nonNull(this.getArgs())) {
                    CSVFileImportInfo csvFileImportInfo = (CSVFileImportInfo)SimpleParamConverter.SerializationUtils.deserialize((String)this.getArgs());
                    fileKey = csvFileImportInfo.getFileKey();
                    RegionDataSetContext regionDataSetContext = csvFileImportInfo.getRegionDataSetContext();
                    TableContext tableContext = this.buildTableContext(regionDataSetContext);
                    String fileName = csvFileImportInfo.getFileName();
                    byte[] fileBytes = fileUploadOssService.downloadFileByteFormTemp(fileKey);
                    String path = ExtConstants.UPLOADDIR + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + OrderGenerator.newOrder() + ExtConstants.FILE_SEPARATOR;
                    pathFile = new File(FilenameUtils.normalize(path));
                    if (!pathFile.exists() && !pathFile.mkdirs()) {
                        log.error("\u521b\u5efa\u4e34\u65f6\u76ee\u5f55\u5931\u8d25\uff01\u6587\u4ef6\u8def\u5f84\uff1a{}", (Object)pathFile.getAbsolutePath());
                    }
                    file = new File(FilenameUtils.normalize(pathFile.getPath() + ExtConstants.FILE_SEPARATOR + fileName));
                    try (FileOutputStream fos = new FileOutputStream(file);){
                        fos.write(fileBytes);
                    }
                    catch (IOException e) {
                        log.error("\u4e0b\u8f7d\u4fdd\u5b58\u4e34\u65f6\u6587\u4ef6\u51fa\u9519{}", (Object)e.getMessage(), (Object)e);
                    }
                    csvFileImportAsyncService.doFileDataImport(file, tableContext, regionDataSetContext.getFormKey(), regionDataSetContext.getRegionKey(), (AsyncTaskMonitor)asyncTaskMonitor);
                    if (asyncTaskMonitor.isCancel()) {
                        asyncTaskMonitor.canceled("\u5bfc\u5165\u53d6\u6d88\uff01", (Object)"\u5bfc\u5165\u53d6\u6d88\uff01");
                    }
                }
                if (fileKey == null) break block29;
            }
            catch (Exception e) {
                block30: {
                    try {
                        asyncTaskMonitor.error("\u5bfc\u5165\u51fa\u9519\uff01", (Throwable)e);
                        log.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
                        if (fileKey == null) break block30;
                    }
                    catch (Throwable throwable) {
                        if (fileKey != null) {
                            FileUploadUtils.fileDelete(fileKey);
                        }
                        if (file != null && !file.delete()) {
                            log.error("\u4e34\u65f6\u6587\u4ef6\u5220\u9664\u5931\u8d25\uff01\u6587\u4ef6\u8def\u5f84\uff1a{}", (Object)file.getAbsolutePath());
                        }
                        if (pathFile != null && !pathFile.delete()) {
                            log.error("\u4e34\u65f6\u6587\u4ef6\u76ee\u5f55\u5220\u9664\u5931\u8d25\uff01\u6587\u4ef6\u8def\u5f84\uff1a{}", (Object)pathFile.getAbsolutePath());
                        }
                        throw throwable;
                    }
                    FileUploadUtils.fileDelete((String)fileKey);
                }
                if (file != null && !file.delete()) {
                    log.error("\u4e34\u65f6\u6587\u4ef6\u5220\u9664\u5931\u8d25\uff01\u6587\u4ef6\u8def\u5f84\uff1a{}", (Object)file.getAbsolutePath());
                }
                if (pathFile != null && !pathFile.delete()) {
                    log.error("\u4e34\u65f6\u6587\u4ef6\u76ee\u5f55\u5220\u9664\u5931\u8d25\uff01\u6587\u4ef6\u8def\u5f84\uff1a{}", (Object)pathFile.getAbsolutePath());
                }
            }
            FileUploadUtils.fileDelete(fileKey);
        }
        if (file != null && !file.delete()) {
            log.error("\u4e34\u65f6\u6587\u4ef6\u5220\u9664\u5931\u8d25\uff01\u6587\u4ef6\u8def\u5f84\uff1a{}", (Object)file.getAbsolutePath());
        }
        if (pathFile != null && !pathFile.delete()) {
            log.error("\u4e34\u65f6\u6587\u4ef6\u76ee\u5f55\u5220\u9664\u5931\u8d25\uff01\u6587\u4ef6\u8def\u5f84\uff1a{}", (Object)pathFile.getAbsolutePath());
        }
    }

    private TableContext buildTableContext(RegionDataSetContext regionDataSetContext) {
        TableContext tableContext = new TableContext();
        if (regionDataSetContext != null) {
            tableContext.setTaskKey(regionDataSetContext.getTaskKey());
            tableContext.setFormSchemeKey(regionDataSetContext.getFormSchemeKey());
            tableContext.setFormKey(regionDataSetContext.getFormKey());
            tableContext.setDimensionSet(regionDataSetContext.getDimensionSetMap() == null ? null : DimensionValueSetUtil.getDimensionValueSet(regionDataSetContext.getDimensionSetMap()));
            tableContext.setDimensionSetRange(regionDataSetContext.getDimensionSetRangeMap() == null ? null : DimensionValueSetUtil.getDimensionValueSet(regionDataSetContext.getDimensionSetRangeMap()));
            tableContext.setOptType(regionDataSetContext.getOptType());
            tableContext.setFileType(regionDataSetContext.getFileType());
            tableContext.setSplit(regionDataSetContext.getSplit());
            tableContext.setAttachment(regionDataSetContext.isAttachment());
            tableContext.setAttachmentArea(regionDataSetContext.getAttachmentArea());
            tableContext.setExpEntryFields(regionDataSetContext.getExpEntryFields());
            tableContext.setExpEnumFields(regionDataSetContext.getExpEnumFields());
            tableContext.setFloatImpOpt(regionDataSetContext.getFloatImpOpt());
            tableContext.setPwd(regionDataSetContext.getPwd());
            tableContext.setExportBizkeyorder(regionDataSetContext.isExportBizkeyorder());
            tableContext.setImportBizkeyorder(regionDataSetContext.isImportBizkeyorder());
            tableContext.setValidEntityExist(regionDataSetContext.isValidEntityExist());
            IoQualifierImpl ioQualifier = new IoQualifierImpl();
            tableContext.setIoQualifier(ioQualifier);
            tableContext.setReturnBizKeyValue(regionDataSetContext.isReturnBizKeyValue());
            tableContext.setDataLineIndex(regionDataSetContext.getDataLineIndex());
            MultistageUnitReplace multistageUnitReplace = null;
            try {
                multistageUnitReplace = (MultistageUnitReplace)BeanUtil.getBean(MultistageUnitReplace.class);
            }
            catch (Exception e) {
                log.info("\u7cfb\u7edf\u4e2d\u4e0d\u5b58\u5728MultistageUnitReplace\u7684\u5b9e\u73b0\u7c7b\uff01");
            }
            tableContext.setMultistageUnitReplace(multistageUnitReplace);
            tableContext.setMultistageEliminateBizKey(regionDataSetContext.isMultistageEliminateBizKey());
            tableContext.setVariables(regionDataSetContext.getVariables());
            tableContext.setCsvRange(regionDataSetContext.getCsvRange());
            tableContext.setNewFileGroup(regionDataSetContext.isNewFileGroup());
            tableContext.setMeasure(regionDataSetContext.getMeasure());
            tableContext.setDecimal(regionDataSetContext.getDecimal());
            tableContext.setCheckType(regionDataSetContext.getCheckType());
            tableContext.setSortFields(regionDataSetContext.getSortFields());
            tableContext.setOrdered(regionDataSetContext.isOrdered());
        }
        return tableContext;
    }
}

