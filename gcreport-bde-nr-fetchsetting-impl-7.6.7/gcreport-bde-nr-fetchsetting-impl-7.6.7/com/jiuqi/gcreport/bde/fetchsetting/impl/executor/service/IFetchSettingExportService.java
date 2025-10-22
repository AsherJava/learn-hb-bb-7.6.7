/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.service;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FetchSettingExportContext;
import java.util.List;
import org.apache.poi.ss.usermodel.Workbook;

public interface IFetchSettingExportService {
    public String getBizType();

    public List<ExportExcelSheet> listExportExcelSheets(ExportContext var1, FetchSettingExportContext var2, Workbook var3);
}

