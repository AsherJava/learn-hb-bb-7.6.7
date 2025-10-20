/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.IntValue
 */
package com.jiuqi.bi.dataset.stat.device;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.stat.RecordBuffer;
import com.jiuqi.bi.dataset.stat.device.FieldStatDevice;
import com.jiuqi.bi.util.IntValue;

public final class CountStatDevice
extends FieldStatDevice {
    public CountStatDevice(int srcIndex, int destIndex, int[] dimColIdx, int[] parentColIdx) {
        super(srcIndex, destIndex, dimColIdx, parentColIdx);
    }

    @Override
    public void init(Object[] dest) {
        dest[this.destIndex] = new IntValue();
    }

    @Override
    public void stat(BIDataRow dataRow, RecordBuffer recordBuf) {
        Object[] dest = recordBuf.getBuffer();
        IntValue intVal = (IntValue)dest[this.destIndex];
        ++intVal.value;
    }

    @Override
    public Object toValue(RecordBuffer recordBuf) {
        IntValue count = (IntValue)recordBuf.getBuffer()[this.destIndex];
        return count.value;
    }
}

