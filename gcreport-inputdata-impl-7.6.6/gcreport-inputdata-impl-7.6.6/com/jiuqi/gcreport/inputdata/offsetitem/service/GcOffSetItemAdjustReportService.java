/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.inputdata.offsetitem.service;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.util.List;
import java.util.Map;

public interface GcOffSetItemAdjustReportService {
    public List<Map<String, Object>> relateRecords(String var1, String var2, List<String> var3);

    public ExportExcelSheet exportRelateRecords(ExportContext var1, String var2, QueryParamsVO var3);
}

