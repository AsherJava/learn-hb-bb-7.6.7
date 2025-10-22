/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.io.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.io.params.base.TableContext;
import java.io.File;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface DataFileImportService {
    public List<Map<String, Object>> dataImport(TableContext var1, MultipartFile var2, AsyncTaskMonitor var3);

    public List<Map<String, Object>> dataImportFile(TableContext var1, File var2, AsyncTaskMonitor var3);

    public List<Map<String, Object>> dataImportFile(TableContext var1, File var2, AsyncTaskMonitor var3, boolean var4);
}

