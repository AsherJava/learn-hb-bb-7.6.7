/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.DoubleValue
 */
package com.jiuqi.bi.dataset.stat.device;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.stat.RecordBuffer;
import com.jiuqi.bi.dataset.stat.device.FieldStatDevice;
import com.jiuqi.bi.util.DoubleValue;

public final class DoubleSumStatDevice
extends FieldStatDevice {
    public DoubleSumStatDevice(int srcIndex, int destIndex, int[] dimColIdx, int[] parentColIdx) {
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
        double value = dataRow.getDouble(this.srcIndex);
        Object[] dest = recordBuf.getBuffer();
        DoubleValue sum = (DoubleValue)dest[this.destIndex];
        if (sum == null) {
            dest[this.destIndex] = new DoubleValue(value);
        } else {
            sum.value += value;
        }
    }

    @Override
    public Object toValue(RecordBuffer recordBuf) {
        DoubleValue sum = (DoubleValue)recordBuf.getBuffer()[this.destIndex];
        return sum == null ? null : Double.valueOf(sum.value);
    }
}

