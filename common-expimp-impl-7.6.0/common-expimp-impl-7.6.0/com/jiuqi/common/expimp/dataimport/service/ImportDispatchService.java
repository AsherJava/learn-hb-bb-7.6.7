/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.web.context.BusinessContext
 *  com.jiuqi.common.web.context.BusinessContextHolder
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.common.expimp.dataimport.service;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.expimp.dataimport.ImportExecutor;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.web.context.BusinessContext;
import com.jiuqi.common.web.context.BusinessContextHolder;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImportDispatchService {
    @Autowired(required=false)
    private List<ImportExecutor> importExecutors = Collections.emptyList();
    private static final Logger LOGGER = LoggerFactory.getLogger(ImportDispatchService.class);

    @Async(value="dataExpImpTaskExecutor")
    public CompletableFuture<Object> doDispatch(NpContext npContext, LocaleContext localeContext, MultipartFile importFile, String dataImportExecutorName, String dataImportSn, String dataImportParam, BusinessContext businessContext) {
        BusinessContextHolder.setBusinessContextData((BusinessContext)businessContext);
        NpContextHolder.setContext((NpContext)npContext);
        ((NpContextImpl)npContext).setLocale(npContext.getLocale());
        LOGGER.info("\u5f00\u59cb\u6267\u884c\u6587\u4ef6\u5bfc\u5165\uff0c[\u6279\u6b21\u53f7\uff1a{}]\u3002", (Object)dataImportSn);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ImportContext context = null;
        try {
            ImportExecutor dataImportExecutor = this.findDataImportExecutor(dataImportExecutorName);
            context = this.buildDataImportContext(importFile.getName(), dataImportExecutorName, dataImportSn, dataImportParam);
            context.getProgressData().setProgressValueAndRefresh(0.05);
            Object result = dataImportExecutor.dataImport(importFile, context);
            CompletableFuture<Object> completableFuture = CompletableFuture.completedFuture(result);
            return completableFuture;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        finally {
            NpContextHolder.clearContext();
            LocaleContextHolder.resetLocaleContext();
            if (context != null) {
                context.getProgressData().setProgressValueAndRefresh(1.0);
            }
            stopWatch.stop();
            LOGGER.info("\u7ed3\u675f\u6267\u884c\u6587\u4ef6\u5bfc\u5165\uff0c[\u6279\u6b21\u53f7\uff1a{}]\uff0c \u8017\u65f6\uff1a[{}] \u79d2\u3002", (Object)dataImportSn, (Object)stopWatch.getTotalTimeSeconds());
        }
    }

    public Object dataImport(Workbook workbook, String dataImportExecutorName, String dataImportSn, String dataImportParam) {
        try {
            ImportExecutor dataImportExecutor = this.findDataImportExecutor(dataImportExecutorName);
            ImportContext context = this.buildDataImportContext(null, dataImportExecutorName, dataImportSn, dataImportParam);
            Object result = dataImportExecutor.dataImport(workbook, context);
            return result;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
    }

    private ImportContext buildDataImportContext(String fileName, String dataImportExecutorName, String dataImportSn, String dataImportParam) {
        ImportContext importContext = new ImportContext(dataImportSn);
        importContext.setExecutor(dataImportExecutorName);
        importContext.setParam(dataImportParam);
        importContext.setFileName(fileName);
        return importContext;
    }

    public ImportExecutor findDataImportExecutor(String dataImportExecutorName) {
        List dataImportExecutors = this.importExecutors.stream().filter(dataImportExecutor -> dataImportExecutor.getName().equals(dataImportExecutorName)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(dataImportExecutors)) {
            throw new BusinessRuntimeException(dataImportExecutorName + "\u6807\u8bc6\u6570\u636e\u5bfc\u5165\u6267\u884c\u5668\u5728\u7cfb\u7edf\u4e2d\u672a\u5339\u914d\u5230\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
        }
        if (dataImportExecutors.size() > 1) {
            String dataImportExecutorNames = dataImportExecutors.stream().map(dataImportExecutor -> dataImportExecutor.getClass().getName()).reduce("", (s1, s2) -> s1 + "," + s2);
            StringBuilder errorMsg = new StringBuilder().append(dataImportExecutorName + "\u6807\u8bc6\u6570\u636e\u5bfc\u5165\u6267\u884c\u5668\u5728\u7cfb\u7edf\u4e2d\u5339\u914d\u5230[").append(dataImportExecutors.size()).append("]\u4e2a\u6570\u636e\u5bfc\u5165\u6267\u884c\u5668[").append(dataImportExecutorNames).append("]\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002");
            throw new BusinessRuntimeException(errorMsg.toString());
        }
        return (ImportExecutor)dataImportExecutors.get(0);
    }

    public Object downloadImportResult(HttpServletRequest request, HttpServletResponse response, String dataImportExecutorName, String dataImportSn) throws Exception {
        ImportExecutor dataImportExecutor = this.findDataImportExecutor(dataImportExecutorName);
        Object importResult = dataImportExecutor.downloadDataImportResult(request, response, dataImportExecutorName, dataImportSn);
        return importResult;
    }
}

