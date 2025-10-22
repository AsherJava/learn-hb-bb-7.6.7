/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 */
package com.jiuqi.gcreport.samecontrol.service;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import java.util.List;
import org.apache.poi.ss.usermodel.Workbook;

public interface GcSameCtrlOffsetExportService {
    public List<ExportExcelSheet> exportExcelSheets(ExportContext var1, Workbook var2);
}

