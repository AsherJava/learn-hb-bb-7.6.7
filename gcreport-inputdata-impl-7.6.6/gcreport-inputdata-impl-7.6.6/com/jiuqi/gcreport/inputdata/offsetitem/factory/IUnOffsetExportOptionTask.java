/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.executor.common.CellStyleExportExcelSheet
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.inputdata.offsetitem.factory;

import com.jiuqi.gcreport.offsetitem.executor.common.CellStyleExportExcelSheet;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;

public interface IUnOffsetExportOptionTask {
    public CellStyleExportExcelSheet createSheet(QueryParamsVO var1, Boolean var2, int var3);
}

