/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.stat.device;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.stat.RecordBuffer;
import com.jiuqi.bi.dataset.stat.device.FieldStatDevice;

public final class StringSumStatDevice
extends FieldStatDevice {
    private static final char STR_SEPERATOR = ';';

    public StringSumStatDevice(int srcIndex, int destIndex) {
        super(srcIndex, destIndex);
    }

    @Override
    public void stat(BIDataRow dataRow, RecordBuffer recordBuf) {
        if (dataRow.wasNull(this.srcIndex)) {
            return;
        }
        String value = dataRow.getString(this.srcIndex);
        Object[] dest = recordBuf.getBuffer();
        StringBuilder buffer = (StringBuilder)dest[this.destIndex];
        if (buffer == null) {
            dest[this.destIndex] = new StringBuilder(value);
        } else {
            buffer.append(';').append(value);
        }
    }

    @Override
    public Object toValue(RecordBuffer recordBuf) {
        Object[] dest = recordBuf.getBuffer();
        return dest[this.destIndex] == null ? null : dest[this.destIndex].toString();
    }
}

