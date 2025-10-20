/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.file.service.CommonFileService
 *  com.jiuqi.common.web.context.BusinessContextHolder
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.common.expimp.dataexport.web;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.expimp.dataexport.service.ExportDispatchService;
import com.jiuqi.common.file.service.CommonFileService;
import com.jiuqi.common.web.context.BusinessContextHolder;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.concurrent.CompletableFuture;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="commonExportController")
@RequestMapping(value={"/api/gcreport/v1/dataexport"})
public class ExportController {
    @Autowired
    private ExportDispatchService dataExportDispatchService;

    @PostMapping(value={"{executor}/{sn}/{isAsync}"})
    public void dataExport(HttpServletRequest request, HttpServletResponse response, @PathVariable(value="executor") String dataExportExecutorName, @PathVariable(value="sn") String dataExportSn, @PathVariable(value="isAsync") boolean isAsync, @RequestBody(required=false) String dataExportParam) {
        try {
            NpContext context = NpContextHolder.getContext();
            LocaleContext localeContext = LocaleContextHolder.getLocaleContext();
            CompletableFuture<Object> completableFuture = this.dataExportDispatchService.doDispatch(false, context, localeContext, request, response, dataExportExecutorName, dataExportSn, dataExportParam, isAsync, BusinessContextHolder.getBusinessContextData());
            if (!isAsync) {
                Object object = completableFuture.get();
            }
        }
        catch (Exception e) {
            Throwable cause = e.getCause();
            String exMsg = cause == null ? e.getMessage() : cause.getMessage();
            throw new BusinessRuntimeException(exMsg, (Throwable)e);
        }
    }

    @GetMapping(value={"/download/{sn}"})
    public void downloadExport(HttpServletResponse response, HttpServletRequest request, @PathVariable(value="sn") String dataExportSn) {
        try {
            CommonFileService commonFileService = (CommonFileService)SpringContextUtils.getBean(CommonFileService.class);
            commonFileService.downloadOssFile("EXPIMP_FILE_OSS", request, response, dataExportSn);
            commonFileService.deleteOssFile("EXPIMP_FILE_OSS", dataExportSn);
        }
        catch (Exception e) {
            Throwable cause = e.getCause();
            String exMsg = cause == null ? e.getMessage() : cause.getMessage();
            throw new BusinessRuntimeException(exMsg, (Throwable)e);
        }
    }

    @PostMapping(value={"template/{executor}"})
    public void dataExportTemplate(HttpServletRequest request, HttpServletResponse response, @PathVariable(value="executor") String dataExportExecutorName, @RequestBody(required=false) String dataExportParam) {
        try {
            String dataExportSn = UUIDUtils.newUUIDStr();
            NpContext context = NpContextHolder.getContext();
            LocaleContext localeContext = LocaleContextHolder.getLocaleContext();
            CompletableFuture<Object> future = this.dataExportDispatchService.doDispatch(true, context, localeContext, request, response, dataExportExecutorName, dataExportSn, dataExportParam, false, BusinessContextHolder.getBusinessContextData());
            future.get();
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
    }
}

