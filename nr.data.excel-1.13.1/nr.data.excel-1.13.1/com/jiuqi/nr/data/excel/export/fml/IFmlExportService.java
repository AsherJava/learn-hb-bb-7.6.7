/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.export.fml;

import com.jiuqi.nr.data.excel.obj.ExportCache;
import com.jiuqi.nr.data.excel.obj.SheetInfo;
import java.util.Map;

public interface IFmlExportService {
    public Map<String, String> getExcelFormulas(SheetInfo var1, ExportCache var2);
}

