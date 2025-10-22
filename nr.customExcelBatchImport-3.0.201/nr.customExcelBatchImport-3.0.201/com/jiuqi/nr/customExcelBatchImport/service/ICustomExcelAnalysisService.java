/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.customExcelBatchImport.service;

import com.jiuqi.nr.customExcelBatchImport.bean.CustomExcelAnalysisResultInfo;
import com.jiuqi.nr.customExcelBatchImport.bean.CustomExcelCheckResultInfo;
import java.io.File;
import org.apache.poi.ss.usermodel.Workbook;

public interface ICustomExcelAnalysisService {
    public CustomExcelCheckResultInfo checkWorkbook(String var1, String var2, Workbook var3, String var4);

    public CustomExcelAnalysisResultInfo analysisWorkbookData(String var1, File var2, String var3);
}

