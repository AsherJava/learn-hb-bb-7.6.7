/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.transmission.data.internal.file;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.transmission.data.dto.SyncSchemeParamDTO;
import com.jiuqi.nr.transmission.data.intf.DataImportResult;
import com.jiuqi.nr.transmission.data.intf.ImportParam;
import java.io.File;
import java.io.InputStream;

public interface FileHandleService {
    public DataImportResult fileImport(InputStream var1, AsyncTaskMonitor var2, ImportParam var3) throws Exception;

    public File fileExport(AsyncTaskMonitor var1, SyncSchemeParamDTO var2) throws Exception;
}

