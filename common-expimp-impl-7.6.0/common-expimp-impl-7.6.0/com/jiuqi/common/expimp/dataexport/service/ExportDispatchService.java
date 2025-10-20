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
 */
package com.jiuqi.common.expimp.dataexport.service;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.expimp.dataexport.ExportExecutor;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
public class ExportDispatchService {
    @Autowired(required=false)
    private List<ExportExecutor> exportExecutors = Collections.emptyList();
    private static final Logger LOGGER = LoggerFactory.getLogger(ExportDispatchService.class);

    @Async(value="dataExpImpTaskExecutor")
    public CompletableFuture<Object> doDispatch(boolean isTemplateExport, NpContext npContext, LocaleContext localeContext, HttpServletRequest request, HttpServletResponse response, String dataExportExecutorName, String exportSn, String dataExportParam, boolean isAsync, BusinessContext businessContext) {
        CompletableFuture<Object> completableFuture;
        BusinessContextHolder.setBusinessContextData((BusinessContext)businessContext);
        NpContextHolder.setContext((NpContext)npContext);
        ((NpContextImpl)npContext).setLocale(npContext.getLocale());
        LOGGER.info("\u5f00\u59cb\u6267\u884c\u6587\u4ef6{}\uff0c[\u6267\u884c\u5668\uff1a{}\uff0c\u6279\u6b21\u53f7\uff1a{}]\u3002", isTemplateExport ? "\u6a21\u677f\u5bfc\u51fa" : "\u5bfc\u51fa", dataExportExecutorName, exportSn);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ExportContext context = null;
        boolean successFlag = true;
        try {
            ExportExecutor dataExportExecutor = this.findDataExportExecutor(dataExportExecutorName);
            context = this.buildDataExportContext(isTemplateExport, dataExportParam, dataExportExecutorName, exportSn, isAsync);
            context.getProgressData().setProgressValueAndRefresh(0.05);
            Object result = dataExportExecutor.dataExport(request, response, context);
            completableFuture = CompletableFuture.completedFuture(result);
        }
        catch (Exception e) {
            try {
                successFlag = false;
                throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
            }
            catch (Throwable throwable) {
                NpContextHolder.clearContext();
                LocaleContextHolder.resetLocaleContext();
                if (context != null) {
                    context.getProgressData().setSuccessFlag(successFlag);
                    context.getProgressData().setProgressValueAndRefresh(1.0);
                }
                stopWatch.stop();
                LOGGER.info("\u7ed3\u675f\u6267\u884c\u6587\u4ef6{}\uff0c[\u6267\u884c\u5668\uff1a{}\uff0c\u6279\u6b21\u53f7\uff1a{}]\uff0c \u8017\u65f6\uff1a[{}] \u79d2\u3002", isTemplateExport ? "\u6a21\u677f\u5bfc\u51fa" : "\u5bfc\u51fa", dataExportExecutorName, exportSn, stopWatch.getTotalTimeSeconds());
                throw throwable;
            }
        }
        NpContextHolder.clearContext();
        LocaleContextHolder.resetLocaleContext();
        if (context != null) {
            context.getProgressData().setSuccessFlag(successFlag);
            context.getProgressData().setProgressValueAndRefresh(1.0);
        }
        stopWatch.stop();
        LOGGER.info("\u7ed3\u675f\u6267\u884c\u6587\u4ef6{}\uff0c[\u6267\u884c\u5668\uff1a{}\uff0c\u6279\u6b21\u53f7\uff1a{}]\uff0c \u8017\u65f6\uff1a[{}] \u79d2\u3002", isTemplateExport ? "\u6a21\u677f\u5bfc\u51fa" : "\u5bfc\u51fa", dataExportExecutorName, exportSn, stopWatch.getTotalTimeSeconds());
        return completableFuture;
    }

    public ExportContext buildDataExportContext(boolean isTemplateExport, String dataExportParam, String dataExportExecutorName, String exportSn, boolean isAsync) {
        ExportContext exportContext = new ExportContext(exportSn);
        exportContext.setParam(dataExportParam);
        exportContext.setTemplateExport(isTemplateExport);
        exportContext.setExecutor(dataExportExecutorName);
        exportContext.setAsync(isAsync);
        return exportContext;
    }

    public ExportExecutor findDataExportExecutor(String dataExportExecutorName) {
        List dataExportExecutors = this.exportExecutors.stream().filter(dataExportExecutor -> dataExportExecutor.getName().equals(dataExportExecutorName)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(dataExportExecutors)) {
            throw new BusinessRuntimeException(dataExportExecutorName + "\u6807\u8bc6\u6570\u636e\u5bfc\u51fa\u6267\u884c\u5668\u5728\u7cfb\u7edf\u4e2d\u672a\u5339\u914d\u5230\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
        }
        if (dataExportExecutors.size() > 1) {
            String dataExportExecutorNames = dataExportExecutors.stream().map(dataExportExecutor -> dataExportExecutor.getClass().getName()).reduce("", (s1, s2) -> s1 + "," + s2);
            StringBuilder errorMsg = new StringBuilder().append(dataExportExecutorName + "\u6807\u8bc6\u6570\u636e\u5bfc\u51fa\u6267\u884c\u5668\u5728\u7cfb\u7edf\u4e2d\u5339\u914d\u5230[").append(dataExportExecutors.size()).append("]\u4e2a\u6570\u636e\u5bfc\u51fa\u6267\u884c\u5668[").append(dataExportExecutorNames).append("]\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002");
            throw new BusinessRuntimeException(errorMsg.toString());
        }
        return (ExportExecutor)dataExportExecutors.get(0);
    }
}

