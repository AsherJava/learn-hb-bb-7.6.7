/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.fasterxml.jackson.databind.ser.std.RawSerializer
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.oss.ObjectStorageEngine
 *  com.jiuqi.bi.oss.ObjectStorageService
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.nr.attachment.utils.FileInfoBuilder
 *  com.jiuqi.nr.common.params.DimensionType
 *  com.jiuqi.nr.dataentry.paramInfo.BatchReturnInfo
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.analysisreport.service.impl;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.RawSerializer;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageEngine;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportDefine;
import com.jiuqi.nr.analysisreport.facade.DimensionConfigObj;
import com.jiuqi.nr.analysisreport.facade.DimensionObj;
import com.jiuqi.nr.analysisreport.helper.AnalysisHelper;
import com.jiuqi.nr.analysisreport.service.IAnalysisReport2WordService;
import com.jiuqi.nr.analysisreport.service.INrArBatchExportServie;
import com.jiuqi.nr.analysisreport.utils.AnaUtils;
import com.jiuqi.nr.analysisreport.utils.TaskExecutorUtils;
import com.jiuqi.nr.analysisreport.vo.ReportBaseVO;
import com.jiuqi.nr.analysisreport.vo.ReportExportVO;
import com.jiuqi.nr.attachment.utils.FileInfoBuilder;
import com.jiuqi.nr.common.params.DimensionType;
import com.jiuqi.nr.dataentry.paramInfo.BatchReturnInfo;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class NrArBatchExportServieImpl
implements INrArBatchExportServie {
    public static final String ORG_NAME = "orgName";
    public static final String ERRORMESSAGE = "errorMessage";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    public static final String ZIP = ".zip";
    public static final String BACTHEXPORTANALYSISREPORT = "bacthExportAnalysisReport";
    public static final String PDF = "pdf";
    public static final String DOCX = "docx";
    @Autowired
    private INvwaSystemOptionService systemOptionService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController runTimeController;
    @Autowired
    private IDataDefinitionRuntimeController definitionRuntimeController;
    @Autowired
    private IAnalysisReport2WordService analysisReport2WordService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private AnalysisHelper analysisHelper;
    private static final Logger log = LoggerFactory.getLogger(NrArBatchExportServieImpl.class);

    @Override
    public void batchExport(ReportExportVO reportExportVO, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        int successNum;
        List<String> errroCode;
        Date startDate;
        block21: {
            List<String> dataList;
            startDate = new Date();
            log.info("\u6279\u91cf\u5bfc\u51fa\u5f00\u59cb:" + asyncTaskMonitor.getTaskId());
            List<Future<List<List<String>>>> futures = null;
            ReportBaseVO.UnitDim mainDim = AnaUtils.getMainDim(reportExportVO.getChooseUnits());
            if (mainDim.getChooseAll()) {
                dataList = this.queryAllDataByDim(reportExportVO);
            } else {
                dataList = mainDim.getCodes();
                mainDim.setCodes(null);
            }
            ThreadPoolTaskExecutor threadPoolTaskExecutor = TaskExecutorUtils.buildThreadPool("com.jiuqi.nr.analysisreport", "batchExportThreadNum");
            futures = this.submitTask(dataList, reportExportVO, asyncTaskMonitor, threadPoolTaskExecutor);
            asyncTaskMonitor.progressAndMessage(0.1, "\u62c6\u5206\u4e3b\u7ef4\u5ea6\u5b8c\u6210\uff0c\u6b63\u5728\u751f\u6210\u62a5\u544a");
            List<List<String>> executeResult = this.waitFinishAndGetResult(futures);
            threadPoolTaskExecutor.shutdown();
            asyncTaskMonitor.progressAndMessage(0.9, "\u62a5\u544a\u751f\u6210\u5b8c\u6210\uff0c\u6b63\u5728\u751f\u6210\u538b\u7f29\u5305");
            List<String> attachKeys = executeResult.get(0);
            if (CollectionUtils.isEmpty(attachKeys)) {
                log.error("\u5206\u6790\u62a5\u544a\u6279\u91cf\u5bfc\u51fa\u5f02\u5e38\uff0c\u6240\u6709\u5355\u4f4d\u751f\u6210\u62a5\u544a\u5f02\u5e38");
                throw new RuntimeException("\u5206\u6790\u62a5\u544a\u6279\u91cf\u5bfc\u51fa\u5f02\u5e38\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
            }
            errroCode = executeResult.get(1);
            successNum = attachKeys.size();
            File resultFile = null;
            ObjectStorageService objectService = ObjectStorageEngine.newInstance().createTemporaryObjectService();
            try {
                resultFile = this.createZipArchive(attachKeys, objectService, asyncTaskMonitor);
                if (asyncTaskMonitor.isCancel()) break block21;
                try (FileInputStream inputStream = new FileInputStream(resultFile);){
                    objectService.upload(asyncTaskMonitor.getTaskId(), (InputStream)inputStream);
                }
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException("\u6279\u91cf\u5bfc\u51fa\u5206\u6790\u62a5\u544a\u5931\u8d25\uff0c\u751f\u6210\u538b\u7f29\u5305\u5931\u8d25");
            }
            finally {
                this.cleanupTempService(resultFile, attachKeys, objectService);
            }
        }
        BatchReturnInfo batchReturnInfo = new BatchReturnInfo();
        if (!asyncTaskMonitor.isCancel()) {
            batchReturnInfo.setMessage(errroCode);
            batchReturnInfo.setErrcnt(errroCode.size());
            batchReturnInfo.setSuccnt(successNum);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer((JsonSerializer)new RawSerializer(String.class));
        objectMapper.registerModule((Module)module);
        String msg = asyncTaskMonitor.isCancel() ? "\u5206\u6790\u62a5\u544a\u6279\u91cf\u5bfc\u51fa\u7ec8\u6b62" : "\u5206\u6790\u62a5\u544a\u6279\u91cf\u5bfc\u51fa\u6210\u529f";
        asyncTaskMonitor.finished(msg, (Object)objectMapper.writeValueAsString((Object)batchReturnInfo));
        log.info(msg + (new Date().getTime() - startDate.getTime()) / 1000L);
    }

    public List<IEntityRow> queryAllDataByDim(ReportExportVO batchExportVO) throws Exception {
        IEntityTable iEntityTable = this.buildEntityTable(batchExportVO);
        return iEntityTable.getAllRows();
    }

    public List<List<String>> waitFinishAndGetResult(List<Future<List<List<String>>>> futures) {
        log.info("\u5206\u6790\u62a5\u544a\u6279\u91cf\u5bfc\u51fa-\u7b49\u5f85\u62a5\u544a\u751f\u6210");
        ArrayList attachKeys = new ArrayList();
        ArrayList errorCodes = new ArrayList();
        try {
            do {
                Iterator<Future<List<List<String>>>> futureIterator = futures.iterator();
                while (futureIterator.hasNext()) {
                    Future<List<List<String>>> future = futureIterator.next();
                    if (!future.isDone()) continue;
                    try {
                        List<List<String>> executeResult = future.get();
                        if (executeResult != null) {
                            if (!CollectionUtils.isEmpty((Collection)executeResult.get(0))) {
                                attachKeys.addAll(executeResult.get(0));
                            }
                            if (!CollectionUtils.isEmpty((Collection)executeResult.get(1))) {
                                errorCodes.addAll(executeResult.get(1));
                            }
                        }
                    }
                    catch (Exception e) {
                        log.error(e.getMessage());
                    }
                    futureIterator.remove();
                }
            } while (!CollectionUtils.isEmpty(futures));
        }
        catch (Exception e) {
            log.info(e.getMessage(), e);
        }
        log.info("\u5206\u6790\u62a5\u544a\u6279\u91cf\u5bfc\u51fa-\u62a5\u544a\u751f\u6210\u5b8c\u6210");
        ArrayList<List<String>> result = new ArrayList<List<String>>();
        result.add(attachKeys);
        result.add(errorCodes);
        return result;
    }

    public List<Future<List<List<String>>>> submitTask(List<? extends Object> dataList, ReportExportVO reportExportVO, AsyncTaskMonitor asyncTaskMonitor, ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        log.info("\u5206\u6790\u62a5\u544a\u6279\u91cf\u5bfc\u51fa-\u5f00\u59cb\u62c6\u5206\u5355\u4f4d\u5e76\u63d0\u4ea4");
        int threadDealUnitNum = Integer.valueOf(this.systemOptionService.get("com.jiuqi.nr.analysisreport", "batchExportThreadDealUnitNum"));
        int size = dataList.size();
        ArrayList<Future<List<List<String>>>> futures = new ArrayList<Future<List<List<String>>>>();
        String periodTitle = this.getPeriodTitle(reportExportVO.getPeriod(), reportExportVO.getKey());
        AtomicInteger processedCount = new AtomicInteger();
        for (int i = 0; i < size; i += threadDealUnitNum) {
            int end = Math.min(i + threadDealUnitNum, size);
            List<? extends Object> subList = dataList.subList(i, end);
            futures.add(threadPoolTaskExecutor.submit(() -> this.executeBatchExport(reportExportVO, subList, asyncTaskMonitor, size, processedCount, periodTitle)));
        }
        log.info("\u5206\u6790\u62a5\u544a\u6279\u91cf\u5bfc\u51fa-\u62c6\u5206\u5355\u4f4d\u5e76\u63d0\u4ea4\u5b8c\u6210");
        return futures;
    }

    public String getPeriodTitle(ReportBaseVO.PeriodDim period, String templateKey) {
        String periodViewKey;
        block4: {
            periodViewKey = period.getViewKey();
            try {
                AnalysisReportDefine analysisReport = this.analysisHelper.getListByKey(templateKey);
                if (!StringUtils.hasLength(analysisReport.getDimension())) break block4;
                ObjectMapper objectMapper = new ObjectMapper();
                DimensionConfigObj dimensionConfigObj = (DimensionConfigObj)objectMapper.readValue(analysisReport.getDimension(), DimensionConfigObj.class);
                for (DimensionObj dimensionObj : dimensionConfigObj.getSrcDims()) {
                    if (DimensionType.DIMENSION_PERIOD != dimensionObj.getType()) continue;
                    if (dimensionObj.getConfig().getPeriodType() != 8) {
                        periodViewKey = String.valueOf((char)PeriodConsts.typeToCode((int)dimensionObj.getConfig().getPeriodType()));
                    }
                    break;
                }
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        IPeriodProvider provider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodViewKey);
        return provider.getPeriodTitle(period.getCalendarCode());
    }

    @Override
    public IEntityTable buildEntityTable(ReportExportVO exportVO) throws Exception {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)exportVO.getPeriodStr());
        EntityViewDefine entityViewDefine = this.runTimeController.buildEntityView(exportVO.getChooseUnits().get(0).getViewKey());
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(entityViewDefine);
        entityQuery.setMasterKeys(dimensionValueSet);
        ExecutorContext executorContext = new ExecutorContext(this.definitionRuntimeController);
        executorContext.setPeriodView(exportVO.getPeriodStr());
        return entityQuery.executeReader((IContext)executorContext);
    }

    public List<List<String>> executeBatchExport(ReportExportVO exportVO, List<? extends Object> unitDims, AsyncTaskMonitor asyncTaskMonitor, int allUnitSize, AtomicInteger processedCount, String periodTitle) throws Exception {
        LocalDateTime startTime = LocalDateTime.now();
        String batchId = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        if (asyncTaskMonitor.isCancel()) {
            log.info("\u5206\u6790\u62a5\u544a\u6279\u91cf\u5bfc\u51fa-\u5206\u7ec4[" + batchId + "]\u4e2d\u6b62\uff0c\u65f6\u95f4:" + startTime.format(formatter));
            return null;
        }
        log.info("\u5206\u6790\u62a5\u544a\u6279\u91cf\u5bfc\u51fa-\u5206\u7ec4[" + batchId + "]\u5f00\u59cb\u65f6\u95f4:" + startTime.format(formatter));
        ArrayList<String> attachKey = new ArrayList<String>();
        ArrayList<String> errors = new ArrayList<String>();
        ObjectMapper mapper = null;
        boolean isEntityRow = unitDims.get(0) instanceof IEntityRow;
        IEntityTable entityTable = null;
        if (!isEntityRow) {
            entityTable = this.buildEntityTable(exportVO);
        }
        try {
            ReportExportVO reportExportVO = exportVO.clone();
            this.cloneNpContext();
            boolean report2WordSuccess = true;
            ObjectStorageService objectService = ObjectStorageEngine.newInstance().createTemporaryObjectService();
            for (int i = 0; i < unitDims.size() && !asyncTaskMonitor.isCancel(); ++i) {
                IEntityRow entityRow = null;
                entityRow = isEntityRow ? (IEntityRow)unitDims.get(i) : entityTable.findByEntityKey((String)unitDims.get(i));
                ReportBaseVO.UnitDim unitDim = reportExportVO.getChooseUnits().get(0);
                unitDim.setCode(entityRow.getCode());
                unitDim.setKey(entityRow.getCode());
                unitDim.setTitle(entityRow.getTitle());
                String attachName = periodTitle + "-" + unitDim.getCode() + "-" + unitDim.getTitle();
                String fileKey = FileInfoBuilder.generateFileKey();
                try (ByteArrayOutputStream out = this.analysisReport2WordService.report2Word(reportExportVO, null);
                     ByteArrayInputStream attachInputStream = new ByteArrayInputStream(out.toByteArray());){
                    ObjectInfo attachInfo = new ObjectInfo();
                    attachInfo.setKey(fileKey);
                    attachInfo.setName(attachName);
                    attachInfo.setExtension(PDF.equals(reportExportVO.getExportType()) ? PDF : DOCX);
                    objectService.upload(fileKey, (InputStream)attachInputStream, attachInfo);
                    attachKey.add(fileKey);
                    report2WordSuccess = true;
                }
                catch (Exception e) {
                    report2WordSuccess = false;
                    if (mapper == null) {
                        mapper = new ObjectMapper();
                    }
                    ObjectNode error = mapper.createObjectNode();
                    error.put(ORG_NAME, unitDim.getTitle() + "(" + unitDim.getCode() + ")");
                    error.put(ERRORMESSAGE, e.getMessage());
                    errors.add(error.toString());
                    log.error("\u5206\u6790\u62a5\u544a\u6279\u91cf\u5bfc\u51fa-\u5206\u7ec4[" + batchId + "]-\u751f\u6210\u62a5\u544a\u5f02\u5e38\uff1a\u9519\u8bef\u5355\u4f4d[" + unitDim.getTitle() + "(" + unitDim.getCode() + ")]\u9519\u8bef\u539f\u56e0:" + e.getMessage(), e);
                }
                int currentCount = processedCount.incrementAndGet();
                double progress = 0.1 + (double)currentCount / (double)allUnitSize * 0.8;
                asyncTaskMonitor.progressAndMessage(progress, "\u5355\u4f4d[" + unitDim.getTitle() + "]\u7684\u62a5\u544a\u751f\u6210" + (report2WordSuccess ? "\u6210\u529f" : "\u5931\u8d25"));
            }
        }
        catch (Exception e) {
            log.error("\u5206\u6790\u62a5\u544a\u6279\u91cf\u5bfc\u51fa-\u5206\u7ec4[" + batchId + "]\u5bfc\u51fa\u5f02\u5e38" + e.getMessage(), e);
        }
        ArrayList<List<String>> result = new ArrayList<List<String>>();
        result.add(attachKey);
        result.add(errors);
        LocalDateTime endTime = LocalDateTime.now();
        log.info("\u5206\u6790\u62a5\u544a\u6279\u91cf\u5bfc\u51fa-\u5206\u7ec4[" + batchId + "]\u7ed3\u675f\u65f6\u95f4" + endTime.format(formatter) + ",\u8017\u65f6:" + Duration.between(startTime, endTime).getSeconds());
        return result;
    }

    private File createZipArchive(List<String> attachKeys, ObjectStorageService service, AsyncTaskMonitor monitor) throws Exception {
        log.info("\u5206\u6790\u62a5\u544a\u6279\u91cf\u5bfc\u51fa-\u5f00\u59cb\u751f\u6210\u538b\u7f29\u6587\u4ef6");
        HashMap<String, Integer> fileNameCountMap = new HashMap<String, Integer>();
        File tempFile = File.createTempFile(BACTHEXPORTANALYSISREPORT, ZIP);
        PathUtils.validatePathManipulation((String)tempFile.getAbsolutePath());
        try (FileOutputStream fos = new FileOutputStream(tempFile);
             ZipOutputStream zos = new ZipOutputStream(fos);){
            Iterator<String> iterator = attachKeys.iterator();
            while (iterator.hasNext()) {
                if (monitor.isCancel()) {
                    break;
                }
                String attachKey = iterator.next();
                ObjectInfo info = service.getObjectInfo(attachKey);
                String baseName = info.getName();
                int count = fileNameCountMap.merge(baseName, 1, Integer::sum);
                String fileName = (count == 1 ? baseName : baseName + "(" + (count - 1) + ")") + "." + info.getExtension();
                try (InputStream is = service.download(attachKey);){
                    zos.putNextEntry(new ZipEntry(fileName));
                    this.writeStreamtoZip(is, zos);
                    zos.closeEntry();
                }
                catch (Exception e) {
                    log.error("\u5904\u7406\u9644\u4ef6 {} \u5931\u8d25\uff0c\u9519\u8bef\u4fe1\u606f\uff1a{}", attachKey, e.getMessage(), e);
                }
                try {
                    service.deleteObject(attachKey);
                }
                catch (Exception e) {
                    log.error("\u5220\u9664\u9644\u4ef6 {} \u5931\u8d25\uff0c\u9519\u8bef\u4fe1\u606f\uff1a{}", attachKey, e.getMessage(), e);
                }
                iterator.remove();
            }
        }
        catch (Exception e) {
            log.error("\u751f\u6210\u538b\u7f29\u6587\u4ef6\u8fc7\u7a0b\u4e2d\u53d1\u751f\u5f02\u5e38\uff1a{}", (Object)e.getMessage(), (Object)e);
            throw new RuntimeException("\u751f\u6210\u538b\u7f29\u6587\u4ef6\u8fc7\u7a0b\u4e2d\u53d1\u751f\u5f02\u5e38");
        }
        log.info(monitor.isCancel() ? "\u5206\u6790\u62a5\u544a\u6279\u91cf\u5bfc\u51fa-\u4efb\u52a1\u505c\u6b62\u6267\u884c" : "\u5206\u6790\u62a5\u544a\u6279\u91cf\u5bfc\u51fa-\u538b\u7f29\u6587\u4ef6\u751f\u6210\u7ed3\u675f");
        return tempFile;
    }

    private void writeStreamtoZip(InputStream source, OutputStream target) throws IOException {
        int bytesRead;
        byte[] buffer = new byte[8192];
        while ((bytesRead = source.read(buffer)) != -1) {
            target.write(buffer, 0, bytesRead);
        }
    }

    private void cleanupTempService(File resultFile, List<String> attachKeys, ObjectStorageService service) {
        try {
            if (resultFile != null) {
                Files.deleteIfExists(resultFile.toPath());
            }
        }
        catch (Exception e) {
            log.info("\u4e34\u65f6\u538b\u7f29\u6587\u4ef6\u5220\u9664\u5f02\u5e38");
        }
        if (CollectionUtils.isEmpty(attachKeys)) {
            return;
        }
        attachKeys.forEach(key -> {
            try {
                service.deleteObject(key);
            }
            catch (Exception ex) {
                log.info("\u4e34\u65f6\u6587\u4ef6\u5220\u9664\u5f02\u5e38");
            }
        });
    }

    public void cloneNpContext() {
        NpContextImpl context = (NpContextImpl)NpContextHolder.createEmptyContext();
        NpContext originalContext = NpContextHolder.getContext();
        context.setTenant(originalContext.getTenant());
        context.setUser(originalContext.getUser());
        context.setIdentity(originalContext.getIdentity());
        context.setOrganization(originalContext.getOrganization());
        context.setLocale(originalContext.getLocale());
        context.setLoginDate(originalContext.getLoginDate());
        context.setIp(originalContext.getIp());
        String token = (String)NpContextHolder.getContext().getExtension("Authorization").get("Authorization");
        String cacheKey = token + System.nanoTime();
        log.info("cacheKey:" + cacheKey);
        context.getExtension("arvl").put("arvl", (Serializable)((Object)cacheKey));
        NpContextHolder.setContext((NpContext)context);
    }
}

