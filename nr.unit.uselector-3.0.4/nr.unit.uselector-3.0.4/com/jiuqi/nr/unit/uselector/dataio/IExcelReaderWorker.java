/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.uselector.dataio;

import com.jiuqi.nr.unit.uselector.dataio.IRowData;
import java.io.InputStream;
import java.util.List;

public interface IExcelReaderWorker {
    public List<IRowData> readRows(InputStream var1) throws Exception;
}

