/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.file.cspro.data;

import com.jiuqi.bi.file.cspro.data.DataRow;
import com.jiuqi.bi.file.cspro.dict.Dictionary;
import java.util.List;

public interface AllDataProcessorListener {
    public void processAllData(Dictionary var1, List<DataRow> var2) throws Exception;
}

