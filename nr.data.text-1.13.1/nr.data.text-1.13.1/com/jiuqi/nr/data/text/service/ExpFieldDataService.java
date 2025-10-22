/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.DataFile
 */
package com.jiuqi.nr.data.text.service;

import com.jiuqi.nr.data.common.DataFile;
import com.jiuqi.nr.data.text.IFieldFileParam;
import com.jiuqi.nr.data.text.spi.IFieldDataMonitor;

public interface ExpFieldDataService {
    public DataFile export(IFieldFileParam var1);

    public DataFile export(IFieldFileParam var1, IFieldDataMonitor var2);
}

