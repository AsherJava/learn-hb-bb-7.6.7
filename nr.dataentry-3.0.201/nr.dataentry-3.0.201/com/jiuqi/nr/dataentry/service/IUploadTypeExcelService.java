/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.common.importdata.ImportResultExcelFileObject
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.common.importdata.ImportResultExcelFileObject;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import java.util.List;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public interface IUploadTypeExcelService {
    public ImportResultExcelFileObject upload(Workbook var1, String var2, UploadParam var3, AsyncTaskMonitor var4, double var5, double var7, ReadWriteAccessCacheManager var9);

    public ImportResultExcelFileObject upload(List<Sheet> var1, String var2, List<UploadParam> var3, AsyncTaskMonitor var4, double var5, double var7, ReadWriteAccessCacheManager var9);
}

