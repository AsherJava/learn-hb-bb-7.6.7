/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.stat.device;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.stat.RecordBuffer;
import com.jiuqi.bi.dataset.stat.device.FieldStatDevice;

public class DummyStatDevice
extends FieldStatDevice {
    public DummyStatDevice() {
        super(0, 0);
    }

    @Override
    public void stat(BIDataRow dataRow, RecordBuffer recordBuf) {
    }

    @Override
    public Object toValue(RecordBuffer recordBuf) {
        return null;
    }
}

