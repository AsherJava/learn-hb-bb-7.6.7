/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.dataentry.export;

import com.jiuqi.nr.dataentry.bean.IExportFacade;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.List;
import java.util.Map;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public interface IReportExport {
    public boolean export(IExportFacade var1, SXSSFWorkbook var2, Map<String, Map<String, Object>> var3, List<String> var4, Map<String, List<String>> var5);

    public Grid2Data generateGrid2Dta(IExportFacade var1, Grid2Data var2);
}

