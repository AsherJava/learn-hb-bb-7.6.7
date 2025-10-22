/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.DataFile
 *  com.jiuqi.nr.data.common.service.FileFinder
 */
package com.jiuqi.nr.data.text.service;

import com.jiuqi.nr.data.common.DataFile;
import com.jiuqi.nr.data.common.service.FileFinder;
import com.jiuqi.nr.data.text.DataFileSaveRes;
import com.jiuqi.nr.data.text.FieldDataSaveParam;
import com.jiuqi.nr.data.text.spi.IFieldDataMonitor;

public interface ImpFieldDataService {
    public DataFileSaveRes importData(FieldDataSaveParam var1, DataFile var2);

    public DataFileSaveRes importData(FieldDataSaveParam var1, DataFile var2, IFieldDataMonitor var3);

    public DataFileSaveRes importData(FieldDataSaveParam var1, FileFinder var2);

    public DataFileSaveRes importData(FieldDataSaveParam var1, FileFinder var2, IFieldDataMonitor var3);
}

