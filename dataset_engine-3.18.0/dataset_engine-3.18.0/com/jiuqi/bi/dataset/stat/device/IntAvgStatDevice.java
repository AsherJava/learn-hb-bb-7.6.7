/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.stat.device;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.stat.RecordBuffer;
import com.jiuqi.bi.dataset.stat.device.FieldStatDevice;

public final class IntAvgStatDevice
extends FieldStatDevice {
    public IntAvgStatDevice(int srcIndex, int destIndex, int[] dimColIdx, int[] parentColIdx) {
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
        Object[] dest = recordBuf.getBuffer();
        int value = dataRow.getInt(this.srcIndex);
        IntAvgValue avg = (IntAvgValue)dest[this.destIndex];
        if (avg == null) {
            dest[this.destIndex] = new IntAvgValue(value);
        } else {
            avg.addValue(value);
        }
    }

    @Override
    public Object toValue(RecordBuffer recordBuf) {
        IntAvgValue avg = (IntAvgValue)recordBuf.getBuffer()[this.destIndex];
        return avg == null ? null : Integer.valueOf(avg.getValue());
    }

    private static final class IntAvgValue {
        private int sum;
        private int count;

        public IntAvgValue(int initValue) {
            this.sum = initValue;
            this.count = 1;
        }

        public void addValue(int value) {
            this.sum += value;
            ++this.count;
        }

        public int getValue() {
            return this.sum / this.count;
        }
    }
}

