/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.stat.device;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.stat.RecordBuffer;
import com.jiuqi.bi.dataset.stat.device.FieldStatDevice;

public final class DimKeyFieldStatDevice
extends FieldStatDevice {
    public DimKeyFieldStatDevice(int srcIndex, int destIndex) {
        super(srcIndex, destIndex);
    }

    @Override
    public void stat(BIDataRow dataRow, RecordBuffer recordBuf) {
        Object[] dest = recordBuf.getBuffer();
        dest[this.destIndex] = dataRow.getValue(this.srcIndex);
    }

    @Override
    public Object toValue(RecordBuffer recordBuf) {
        return recordBuf.getBuffer()[this.destIndex];
    }
}

