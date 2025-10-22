/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.param.CommonParams
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.data.excel.service;

import com.jiuqi.nr.data.common.param.CommonParams;
import com.jiuqi.nr.data.excel.obj.ExpExcelSyncResult;
import com.jiuqi.nr.data.excel.obj.ExpSingleFileResult;
import com.jiuqi.nr.data.excel.param.BatchExpPar;
import com.jiuqi.nr.data.excel.param.FormExpPar;
import com.jiuqi.nr.data.excel.param.SingleExpPar;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.io.OutputStream;

public interface IDataExportService {
    public ExpSingleFileResult expSingleFile(SingleExpPar var1, OutputStream var2, CommonParams var3) throws Exception;

    public ExpExcelSyncResult expExcelSync(BatchExpPar var1, String var2, CommonParams var3) throws Exception;

    @Deprecated
    public String expExcelAsync(BatchExpPar var1, String var2, CommonParams var3) throws Exception;

    public Grid2Data expGrid2Data(FormExpPar var1) throws Exception;

    public Grid2Data expGrid2Data(FormExpPar var1, Grid2Data var2) throws Exception;
}

