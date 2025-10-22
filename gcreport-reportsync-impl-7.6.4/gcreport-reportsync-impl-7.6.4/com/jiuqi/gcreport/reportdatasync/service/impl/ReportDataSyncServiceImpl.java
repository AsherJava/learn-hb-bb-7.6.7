/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.ThreadFactoryBuilder
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.zip.ZipHelper
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  com.jiuqi.common.expimp.progress.common.ProgressDataImpl
 *  com.jiuqi.common.expimp.progress.service.ProgressService
 *  com.jiuqi.common.reportsync.dto.ReportDataSyncServerInfoBase
 *  com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext
 *  com.jiuqi.common.reportsync.task.IReportSyncExportTask
 *  com.jiuqi.common.reportsync.task.IReportSyncImportTask
 *  com.jiuqi.common.reportsync.util.CommonReportUtil
 *  com.jiuqi.common.reportsync.vo.ReportDataSyncParams
 *  com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.service.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.zip.ZipHelper;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import com.jiuqi.common.expimp.progress.service.ProgressService;
import com.jiuqi.common.reportsync.dto.ReportDataSyncServerInfoBase;
import com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext;
import com.jiuqi.common.reportsync.task.IReportSyncExportTask;
import com.jiuqi.common.reportsync.task.IReportSyncImportTask;
import com.jiuqi.common.reportsync.util.CommonReportUtil;
import com.jiuqi.common.reportsync.vo.ReportDataSyncParams;
import com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums;
import com.jiuqi.gcreport.reportdatasync.properties.ReportDataSyncProperties;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncServerListService;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncService;
import com.jiuqi.gcreport.reportdatasync.task.IReportSyncAfterImportTask;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncUtil;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ReportDataSyncServiceImpl
implements ReportDataSyncService,
InitializingBean {
    private static Logger logger = LoggerFactory.getLogger(ReportDataSyncServiceImpl.class);
    @Autowired
    private ProgressService<ProgressData<Set>, Set> progressService;
    @Autowired(required=false)
    private List<IReportSyncExportTask> dataSyncExportTask = Collections.emptyList();
    @Autowired(required=false)
    private List<IReportSyncImportTask> dataSyncImportTasks = Collections.emptyList();
    @Autowired(required=false)
    private List<IReportSyncAfterImportTask> dataSyncAfterImportTasks = Collections.emptyList();
    private ThreadPoolExecutor exportExecutorService;
    private ThreadPoolExecutor importExecutorService;
    @Autowired
    private ReportDataSyncProperties dataSyncProperties;
    @Autowired
    private ReportDataSyncServerListService serverInfoService;

    /*
     * Exception decompiling
     */
    @Override
    public ByteArrayOutputStream export(ReportDataSyncParams dataSyncParam, String sn) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String upload(String sn) {
        ProgressDataImpl progressData = new ProgressDataImpl(sn, Collections.synchronizedSet(new LinkedHashSet()), "dataSync");
        this.progressService.createProgressData((ProgressData)progressData);
        progressData.setProgressValueAndRefresh(0.05);
        int size = this.dataSyncImportTasks.size();
        CountDownLatch countDownLatch = new CountDownLatch(size);
        String snFolderPath = ReportDataSyncUtil.getSnFolderPath(sn);
        ArrayList errorLogs = new ArrayList();
        try {
            double stepProgress = 0.9 / (double)this.dataSyncImportTasks.size();
            NpContext npContext = NpContextHolder.getContext();
            this.dataSyncImportTasks.stream().sorted(Comparator.comparingInt(IReportSyncImportTask::priority)).forEach(task -> this.importExecutorService.submit(() -> {
                try {
                    NpContextHolder.setContext((NpContext)npContext);
                    ((Set)progressData.getResult()).add(String.format("\u3010%1$s\u3011\u6b63\u5728\u5bfc\u5165", task.funcTitle()));
                    progressData.addProgressValueAndRefresh(0.0);
                    String filePath = CommonReportUtil.createNewPath((String)snFolderPath, (String)task.syncType().getCode());
                    File rootFolder = new File(filePath);
                    ReportSyncExportTaskContext reportSyncExportTaskContext = this.buirdExportContext(new ReportDataSyncParams(), rootFolder, (ProgressDataImpl<Set>)progressData);
                    Collection msgList = task.exec(reportSyncExportTaskContext);
                    this.logMsg((Set)progressData.getResult(), msgList, task.funcTitle());
                }
                catch (Exception e) {
                    try {
                        logger.error(e.getMessage(), e);
                        ((Set)progressData.getResult()).add(String.format("%1$s\u6570\u636e\u4e0a\u4f20\u5931\u8d25:%2$s", task.funcTitle(), e.getMessage()));
                    }
                    catch (Throwable throwable) {
                        ((Set)progressData.getResult()).remove(String.format("\u3010%1$s\u3011\u6b63\u5728\u5bfc\u5165", task.funcTitle()));
                        progressData.addProgressValueAndRefresh(stepProgress);
                        countDownLatch.countDown();
                        throw throwable;
                    }
                    ((Set)progressData.getResult()).remove(String.format("\u3010%1$s\u3011\u6b63\u5728\u5bfc\u5165", task.funcTitle()));
                    progressData.addProgressValueAndRefresh(stepProgress);
                    countDownLatch.countDown();
                }
                ((Set)progressData.getResult()).remove(String.format("\u3010%1$s\u3011\u6b63\u5728\u5bfc\u5165", task.funcTitle()));
                progressData.addProgressValueAndRefresh(stepProgress);
                countDownLatch.countDown();
            }));
            countDownLatch.await();
            this.dataSyncAfterImportTasks.forEach(task -> {
                String filePath = CommonReportUtil.createNewPath((String)snFolderPath, (String)task.syncType().getCode());
                File rootFolder = new File(filePath);
                task.afterImport(rootFolder);
            });
            progressData.setSuccessFlag(true);
        }
        catch (Exception e) {
            progressData.setSuccessFlag(false);
            logger.error("\u6570\u636e\u540c\u6b65\u51fa\u9519", e);
        }
        finally {
            try {
                Thread.sleep(3000L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            CommonReportUtil.deleteDir((String)snFolderPath);
            progressData.setProgressValueAndRefresh(1.0);
        }
        if (progressData.isSuccessFlag() && ((Set)progressData.getResult()).size() > 0) {
            errorLogs.addAll((Collection)progressData.getResult());
        }
        if (CollectionUtils.isEmpty(errorLogs)) {
            return null;
        }
        return StringUtils.join((Object[])errorLogs.toArray(), (String)";");
    }

    private void logMsg(Set result, Collection<String> msgList, String funcTitle) {
        if (null == msgList) {
            return;
        }
        for (String msg : msgList) {
            if (StringUtils.isEmpty((String)msg)) continue;
            result.add(String.format("\u3010%1$s\u3011%2$s", funcTitle, msg));
        }
    }

    public String unzipFile(MultipartFile importData, String sn) {
        try {
            String snFolderPath = ReportDataSyncUtil.getSnFolderPath(sn);
            ZipHelper.unzipFile((String)snFolderPath, (InputStream)importData.getInputStream());
            return snFolderPath;
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void afterPropertiesSet() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("GcDataSyncExport-%d").build();
        this.exportExecutorService = new ThreadPoolExecutor(this.dataSyncProperties.getCorePoolSize(), this.dataSyncProperties.getMaximumPoolSize(), 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(655350), namedThreadFactory);
        this.exportExecutorService.allowCoreThreadTimeOut(true);
        namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("GcDataSyncImport-%d").build();
        this.importExecutorService = new ThreadPoolExecutor(this.dataSyncProperties.getCorePoolSize(), this.dataSyncProperties.getMaximumPoolSize(), 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(655350), namedThreadFactory);
        this.exportExecutorService.allowCoreThreadTimeOut(true);
    }

    private void downloadExport(String sn, OutputStream outStream) throws IOException {
        String snFolderPath = ReportDataSyncUtil.getSnFolderPath(sn);
        ZipHelper.zipDirectory((String)snFolderPath, (OutputStream)outStream);
    }

    private ReportSyncExportTaskContext buirdExportContext(ReportDataSyncParams dataSyncParam, File rootFolder, ProgressDataImpl<Set> progressData) {
        ReportSyncExportTaskContext reportSyncExportTaskContext = new ReportSyncExportTaskContext();
        reportSyncExportTaskContext.setReportDataSyncParams(dataSyncParam);
        reportSyncExportTaskContext.setRootFolder(rootFolder);
        List<ReportDataSyncServerInfoVO> serverList = this.serverInfoService.listServerInfos(SyncTypeEnums.REPORTDATA);
        reportSyncExportTaskContext.setProgressData(progressData);
        if (!CollectionUtils.isEmpty(serverList)) {
            ReportDataSyncServerInfoVO serverInfoVO = serverList.get(0);
            ReportDataSyncServerInfoBase serverInfoVObase = new ReportDataSyncServerInfoBase();
            serverInfoVObase.setTargetEncryptType(serverInfoVO.getEncryptType());
            serverInfoVObase.setTargetPwd(serverInfoVO.getPwd());
            serverInfoVObase.setTargetUrl(serverInfoVO.getUrl());
            serverInfoVObase.setTargetUserName(serverInfoVO.getUserName());
            reportSyncExportTaskContext.setReportDataSyncServerInfoBase(serverInfoVObase);
        }
        return reportSyncExportTaskContext;
    }

    private /* synthetic */ void lambda$export$2(NpContext npContext, String snFolderPath, ReportDataSyncParams dataSyncParam, AtomicBoolean failFlag, CountDownLatch countDownLatch, IReportSyncExportTask task) {
        this.exportExecutorService.submit(() -> {
            try {
                NpContextHolder.setContext((NpContext)npContext);
                String filePath = CommonReportUtil.createNewPath((String)snFolderPath, (String)task.syncType().getCode());
                File rootFolder = new File(filePath);
                ReportSyncExportTaskContext reportSyncExportTaskContext = this.buirdExportContext(dataSyncParam, rootFolder, (ProgressDataImpl<Set>)new ProgressDataImpl());
                task.exec(reportSyncExportTaskContext);
            }
            catch (Exception e) {
                failFlag.set(true);
                logger.error(e.getMessage(), e);
            }
            finally {
                countDownLatch.countDown();
            }
        });
    }

    private static /* synthetic */ boolean lambda$export$0(ReportDataSyncParams dataSyncParam, IReportSyncExportTask task) {
        return task.syncType().getCode().equals(dataSyncParam.getSyncType()) && task.match(dataSyncParam);
    }
}

