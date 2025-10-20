/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.stat.device;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.stat.RecordBuffer;
import com.jiuqi.bi.dataset.stat.device.FieldStatDevice;
import java.math.BigDecimal;

public class BigDecimalAvgStatDevice
extends FieldStatDevice {
    public BigDecimalAvgStatDevice(int srcIndex, int destIndex, int[] dimColIdx, int[] parentColIdx) {
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
        BigDecimal value = dataRow.getBigDecimal(this.srcIndex);
        Object[] dest = recordBuf.getBuffer();
        BigDecimalAvgValue avg = (BigDecimalAvgValue)dest[this.destIndex];
        if (avg == null) {
            dest[this.destIndex] = new BigDecimalAvgValue(value);
        } else {
            avg.addValue(value);
        }
    }

    @Override
    public Object toValue(RecordBuffer recordBuf) {
        BigDecimalAvgValue avg = (BigDecimalAvgValue)recordBuf.getBuffer()[this.destIndex];
        return avg == null ? null : avg.getValue();
    }

    private static final class BigDecimalAvgValue {
        private BigDecimal sum;
        private int count;

        public BigDecimalAvgValue(BigDecimal initValue) {
            this.sum = initValue;
            this.count = 1;
        }

        public void addValue(BigDecimal value) {
            this.sum = this.sum.add(value);
            ++this.count;
        }

        public BigDecimal getValue() {
            return this.sum.divide(BigDecimal.valueOf(this.count));
        }
    }
}

