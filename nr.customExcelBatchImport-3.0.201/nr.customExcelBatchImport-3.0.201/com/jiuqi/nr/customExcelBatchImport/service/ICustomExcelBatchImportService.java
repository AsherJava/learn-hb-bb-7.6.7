/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.customExcelBatchImport.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.customExcelBatchImport.bean.CustomExcelOptionInfo;
import com.jiuqi.nr.customExcelBatchImport.bean.CustomExcelReturnInfo;
import com.jiuqi.nr.customExcelBatchImport.bean.ErrorInfo;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import java.io.File;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public interface ICustomExcelBatchImportService {
    public List<FileInfo> getTemplateFileList(String var1, String var2);

    public List<List<ErrorInfo>> coverTemplateFile(String var1, String var2, String var3);

    public void downloadTempleFiles(String var1, String var2, String var3, HttpServletResponse var4);

    public CustomExcelReturnInfo downExampleNoEnableManage(String var1, String var2);

    public void downExampleEnableManage(String var1, String var2, HttpServletResponse var3);

    public ReturnInfo deleteTemplate(String var1);

    public void beforeImport(CustomExcelOptionInfo var1, AsyncTaskMonitor var2);

    public void customExcelImport(CustomExcelOptionInfo var1, List<File> var2, String var3, AsyncTaskMonitor var4);
}

