/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet
 */
package com.jiuqi.gcreport.rate.impl.service;

import com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet;
import java.util.List;
import java.util.Map;

public interface RateExportImportService {
    public Map<String, String> getRateExcelColumnTitleMap();

    public List<Map<String, Object>> rateExport(String var1, String var2, String var3, String var4, String var5, String var6);

    public void rateUpload(String var1, List<ImportExcelSheet> var2);
}

