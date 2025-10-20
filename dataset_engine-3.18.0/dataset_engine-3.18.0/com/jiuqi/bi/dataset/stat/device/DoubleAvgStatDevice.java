/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.stat.device;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.stat.RecordBuffer;
import com.jiuqi.bi.dataset.stat.device.FieldStatDevice;

public class DoubleAvgStatDevice
extends FieldStatDevice {
    public DoubleAvgStatDevice(int srcIndex, int destIndex, int[] dimColIdx, int[] parentColIdx) {
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
        DoubleAvgValue avg = (DoubleAvgValue)dest[this.destIndex];
        if (avg == null) {
            dest[this.destIndex] = new DoubleAvgValue(value);
        } else {
            avg.addValue(value);
        }
    }

    @Override
    public Object toValue(RecordBuffer recordBuf) {
        DoubleAvgValue avg = (DoubleAvgValue)recordBuf.getBuffer()[this.destIndex];
        return avg == null ? null : Double.valueOf(avg.getValue());
    }

    private static final class DoubleAvgValue {
        private double sum;
        private int count;

        public DoubleAvgValue(double initValue) {
            this.sum = initValue;
            this.count = 1;
        }

        public void addValue(double value) {
            this.sum += value;
            ++this.count;
        }

        public double getValue() {
            return this.sum / (double)this.count;
        }
    }
}

