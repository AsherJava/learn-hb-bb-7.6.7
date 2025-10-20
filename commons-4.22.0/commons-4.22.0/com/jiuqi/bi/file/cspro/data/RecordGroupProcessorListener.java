/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.file.cspro.data;

import com.jiuqi.bi.file.cspro.data.DataRow;
import com.jiuqi.bi.file.cspro.dict.Dictionary;
import java.util.List;
import java.util.Map;

public interface RecordGroupProcessorListener {
    public void processGroupData(Dictionary var1, Map<String, List<DataRow>> var2) throws Exception;
}

