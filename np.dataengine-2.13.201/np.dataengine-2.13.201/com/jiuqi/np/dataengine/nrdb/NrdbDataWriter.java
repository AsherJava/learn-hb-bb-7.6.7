/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.memdb.api.record.ArrayRecord
 */
package com.jiuqi.np.dataengine.nrdb;

import com.jiuqi.nvwa.memdb.api.record.ArrayRecord;

public class NrdbDataWriter {
    public void writeValue(ArrayRecord record, int index, Object value) {
        record.setValue(index, value);
    }
}

