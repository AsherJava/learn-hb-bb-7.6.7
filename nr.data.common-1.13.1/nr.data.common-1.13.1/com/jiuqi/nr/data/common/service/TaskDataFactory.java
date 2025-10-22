/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Version
 */
package com.jiuqi.nr.data.common.service;

import com.jiuqi.bi.util.Version;
import com.jiuqi.nr.data.common.service.FileFinder;
import com.jiuqi.nr.data.common.service.FileWriter;
import com.jiuqi.nr.data.common.service.ImportResultDisplayCollector;
import com.jiuqi.nr.data.common.service.TransferContext;

public interface TaskDataFactory
extends ImportResultDisplayCollector {
    public Version getVersion();

    default public int getWeight() {
        return 1;
    }

    public void exportTaskData(TransferContext var1, FileWriter var2);

    public void importTaskData(TransferContext var1, FileFinder var2);
}

