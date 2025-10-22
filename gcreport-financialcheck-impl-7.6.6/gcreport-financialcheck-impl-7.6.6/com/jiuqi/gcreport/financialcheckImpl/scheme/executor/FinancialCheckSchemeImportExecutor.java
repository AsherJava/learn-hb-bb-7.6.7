/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.common.ExpImpFileTypeEnum
 *  com.jiuqi.common.expimp.dataimport.ImportExecutor
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.financialcheckImpl.scheme.executor;

import com.jiuqi.common.expimp.common.ExpImpFileTypeEnum;
import com.jiuqi.common.expimp.dataimport.ImportExecutor;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.gcreport.financialcheckImpl.scheme.service.FinancialCheckSchemeService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FinancialCheckSchemeImportExecutor
implements ImportExecutor {
    @Autowired
    private FinancialCheckSchemeService financialCheckSchemeService;

    public String getName() {
        return "FinancialCheckSchemeImportExecutor";
    }

    public ExpImpFileTypeEnum getFileType() {
        return null;
    }

    public Object dataImport(MultipartFile importFile, ImportContext context) throws Exception {
        return "\u5bfc\u5165\u6210\u529f";
    }

    public Object downloadDataImportResult(HttpServletRequest request, HttpServletResponse response, String dataImportExecutorName, String dataImportSn) throws Exception {
        return null;
    }
}

