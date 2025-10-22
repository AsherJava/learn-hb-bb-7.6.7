/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.logger.DataImportLogger
 */
package com.jiuqi.nr.io.service;

import com.jiuqi.nr.data.common.logger.DataImportLogger;
import com.jiuqi.nr.io.params.base.TableContext;
import java.io.File;
import java.util.Map;

public interface FileImportService {
    public Map<String, Object> dealFileData(File var1, TableContext var2) throws Exception;

    default public Map<String, Object> dealFileData(File file, TableContext tableContext, DataImportLogger dataImportLogger) throws Exception {
        return this.dealFileData(file, tableContext);
    }
}

