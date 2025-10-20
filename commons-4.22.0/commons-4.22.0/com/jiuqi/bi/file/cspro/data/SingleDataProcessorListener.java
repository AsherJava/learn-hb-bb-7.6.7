/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.file.cspro.data;

import com.jiuqi.bi.file.cspro.data.DataRow;
import com.jiuqi.bi.file.cspro.dict.Dictionary;

public interface SingleDataProcessorListener {
    public void processSingleData(Dictionary var1, DataRow var2) throws Exception;

    public void finish() throws Exception;
}

