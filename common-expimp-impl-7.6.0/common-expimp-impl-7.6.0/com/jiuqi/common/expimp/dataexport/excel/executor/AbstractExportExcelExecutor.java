/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.xssf.streaming.SXSSFWorkbook
 */
package com.jiuqi.common.expimp.dataexport.excel.executor;

import com.jiuqi.common.expimp.common.ExpImpFileTypeEnum;
import com.jiuqi.common.expimp.dataexport.ExportExecutor;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public abstract class AbstractExportExcelExecutor
implements ExportExecutor {
    @Override
    public final ExpImpFileTypeEnum getFileType() {
        return ExpImpFileTypeEnum.EXCEL;
    }

    public SXSSFWorkbook getWorkbook(ExportContext context) {
        return null;
    }
}

