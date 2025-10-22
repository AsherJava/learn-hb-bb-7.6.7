/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.office.excel2.label.ExcelLabel
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.data.excel.service.internal;

import com.jiuqi.np.office.excel2.label.ExcelLabel;
import com.jiuqi.nr.data.excel.obj.ExcelInfo;
import com.jiuqi.nr.data.excel.obj.ExportCache;
import com.jiuqi.nr.data.excel.obj.SheetInfo;
import com.jiuqi.nr.data.excel.param.Excel;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.List;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public interface IFormDataService {
    public boolean writeSheet(SXSSFWorkbook var1, SheetInfo var2, ExportCache var3);

    public Grid2Data getFormStyle(String var1);

    public Grid2Data fillFormData(Grid2Data var1, SheetInfo var2, ExportCache var3);

    public ExcelInfo getExcelInfo(Excel var1, ExportCache var2);

    public List<ExcelLabel> handleLabel(SheetInfo var1, Grid2Data var2, ExportCache var3);
}

