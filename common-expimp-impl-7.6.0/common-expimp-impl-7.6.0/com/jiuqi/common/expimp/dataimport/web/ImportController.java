/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.web.context.BusinessContextHolder
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.common.expimp.dataimport.web;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.expimp.dataimport.service.ImportDispatchService;
import com.jiuqi.common.web.context.BusinessContextHolder;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.concurrent.CompletableFuture;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController(value="commonImportController")
@RequestMapping(value={"/api/gcreport/v1/dataimport"})
public class ImportController {
    @Autowired
    private ImportDispatchService dataImportDispatchService;

    @PostMapping(value={"{executor}/{sn}"})
    public BusinessResponseEntity<Object> dataImport(@RequestParam(value="importFile") MultipartFile importFile, @PathVariable(value="executor") String dataImportExecutorName, @PathVariable(value="sn") String dataImportSn, @RequestParam(value="importParam", required=false) String dataImportParam) {
        try {
            NpContext context = NpContextHolder.getContext();
            LocaleContext localeContext = LocaleContextHolder.getLocaleContext();
            CompletableFuture<Object> completableFuture = this.dataImportDispatchService.doDispatch(context, localeContext, importFile, dataImportExecutorName, dataImportSn, dataImportParam, BusinessContextHolder.getBusinessContextData());
            Object result = completableFuture.get();
            return BusinessResponseEntity.ok((Object)result);
        }
        catch (Exception e) {
            Throwable cause = e.getCause();
            String exMsg = cause == null ? e.getMessage() : cause.getMessage();
            throw new BusinessRuntimeException(exMsg, (Throwable)e);
        }
    }

    @PostMapping(value={"result/{executor}/{sn}"})
    public BusinessResponseEntity<Object> downloadImportResult(HttpServletRequest request, HttpServletResponse response, @PathVariable(value="executor") String dataImportExecutorName, @PathVariable(value="sn") String dataImportSn) {
        Object imporResult;
        try {
            imporResult = this.dataImportDispatchService.downloadImportResult(request, response, dataImportExecutorName, dataImportSn);
        }
        catch (Exception e) {
            return BusinessResponseEntity.ok((Object)e);
        }
        return BusinessResponseEntity.ok((Object)imporResult);
    }
}

