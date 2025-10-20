/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.stat.device;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.stat.RecordBuffer;
import com.jiuqi.bi.dataset.stat.device.FieldStatDevice;

public class TimePointStatDevice
extends FieldStatDevice {
    private int sys_timekeyIndex;

    public TimePointStatDevice(int srcIndex, int destIndex, int sys_timekeyIndex) {
        super(srcIndex, destIndex);
        this.sys_timekeyIndex = sys_timekeyIndex;
    }

    @Override
    public void stat(BIDataRow dataRow, RecordBuffer recordBuf) {
        Object value = dataRow.getValue(this.srcIndex);
        String bufTimekey = recordBuf.getMax_timekey();
        String curTimekey = null;
        if (this.sys_timekeyIndex >= 0) {
            curTimekey = dataRow.getString(this.sys_timekeyIndex);
        }
        if (bufTimekey == null) {
            Object[] dest = recordBuf.getBuffer();
            dest[this.destIndex] = value;
        } else if (curTimekey != null && bufTimekey.compareTo(curTimekey) <= 0) {
            Object[] dest = recordBuf.getBuffer();
            dest[this.destIndex] = value;
        }
    }

    @Override
    public Object toValue(RecordBuffer recordBuf) {
        return recordBuf.getValue(this.destIndex);
    }
}

