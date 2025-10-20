/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.stat.device;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.stat.RecordBuffer;
import com.jiuqi.bi.dataset.stat.device.FieldStatDevice;

public final class DimNameFieldStatDevice
extends FieldStatDevice {
    private int timekeyIndex;

    public DimNameFieldStatDevice(int srcIndex, int destIndex, int timekeyIndex) {
        super(srcIndex, destIndex);
        this.timekeyIndex = timekeyIndex;
    }

    @Override
    public void stat(BIDataRow dataRow, RecordBuffer recordBuf) {
        Object[] dest = recordBuf.getBuffer();
        if (this.timekeyIndex == -1) {
            dest[this.destIndex] = dataRow.getValue(this.srcIndex);
        } else {
            String recordTimekey = recordBuf.getMax_timekey();
            String datarowTimekey = dataRow.getString(this.timekeyIndex);
            if (recordTimekey == null || datarowTimekey.compareTo(recordTimekey) > 0) {
                dest[this.destIndex] = dataRow.getValue(this.srcIndex);
            }
        }
    }

    @Override
    public Object toValue(RecordBuffer recordBuf) {
        return recordBuf.getValue(this.destIndex);
    }
}

