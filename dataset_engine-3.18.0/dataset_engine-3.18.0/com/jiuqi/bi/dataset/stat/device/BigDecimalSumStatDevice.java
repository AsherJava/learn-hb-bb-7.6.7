/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.stat.device;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.stat.RecordBuffer;
import com.jiuqi.bi.dataset.stat.device.FieldStatDevice;
import java.math.BigDecimal;

public class BigDecimalSumStatDevice
extends FieldStatDevice {
    public BigDecimalSumStatDevice(int srcIndex, int destIndex, int[] dimColIdx, int[] parentColIdx) {
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
        BigDecimal sum = (BigDecimal)dest[this.destIndex];
        dest[this.destIndex] = sum == null ? value : sum.add(value);
    }

    @Override
    public Object toValue(RecordBuffer recordBuf) {
        return (BigDecimal)recordBuf.getBuffer()[this.destIndex];
    }
}

