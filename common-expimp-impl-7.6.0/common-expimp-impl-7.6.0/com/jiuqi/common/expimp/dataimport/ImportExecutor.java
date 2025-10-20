/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.common.expimp.dataimport;

import com.jiuqi.common.expimp.common.ExpImpFileTypeEnum;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

public interface ImportExecutor {
    public String getName();

    public ExpImpFileTypeEnum getFileType();

    public Object dataImport(MultipartFile var1, ImportContext var2) throws Exception;

    default public Object dataImport(Workbook workbook, ImportContext context) {
        return null;
    }

    default public Object downloadDataImportResult(HttpServletRequest request, HttpServletResponse response, String dataImportExecutorName, String dataImportSn) throws Exception {
        return null;
    }
}

