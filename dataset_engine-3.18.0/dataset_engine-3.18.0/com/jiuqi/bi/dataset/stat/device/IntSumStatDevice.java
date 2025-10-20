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

public final class IntSumStatDevice
extends FieldStatDevice {
    public IntSumStatDevice(int srcIndex, int destIndex, int[] dimColIdx, int[] parentColIdx) {
        super(srcIndex, destIndex, dimColIdx, parentColIdx);
    }

    @Override
    public void stat(BIDataRow dataRow, RecordBuffer recordBuf) {
        if (dataRow.wasNull(this.srcIndex)) {
            return;
        }
        if (!this.needStat(dataRow)) {
            return;
        }
        int value = dataRow.getInt(this.srcIndex);
        Object[] dest = recordBuf.getBuffer();
        IntValue sum = (IntValue)dest[this.destIndex];
        if (sum == null) {
            dest[this.destIndex] = new IntValue(value);
        } else {
            sum.value += value;
        }
    }

    @Override
    public Object toValue(RecordBuffer recordBuf) {
        IntValue sum = (IntValue)recordBuf.getBuffer()[this.destIndex];
        return sum == null ? null : Integer.valueOf(sum.value);
    }
}

