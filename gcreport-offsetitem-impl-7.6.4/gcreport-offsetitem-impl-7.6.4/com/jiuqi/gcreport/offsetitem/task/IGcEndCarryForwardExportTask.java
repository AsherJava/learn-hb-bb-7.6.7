/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryTableVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.offsetitem.task;

import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryTableVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;

public interface IGcEndCarryForwardExportTask {
    public ExportExcelSheet exportMinorityRecoveryWorkPaper(QueryParamsVO var1, MinorityRecoveryTableVO var2);
}

