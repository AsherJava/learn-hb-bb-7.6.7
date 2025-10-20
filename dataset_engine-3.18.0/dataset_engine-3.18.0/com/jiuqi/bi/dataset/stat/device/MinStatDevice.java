/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 */
package com.jiuqi.bi.dataset.stat.device;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.stat.RecordBuffer;
import com.jiuqi.bi.dataset.stat.device.FieldStatDevice;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;

public final class MinStatDevice
extends FieldStatDevice {
    private int dataType;

    public MinStatDevice(int srcIndex, int destIndex, int dataType, int[] dimColIdx, int[] parentColIdx) {
        super(srcIndex, destIndex, dimColIdx, parentColIdx);
        this.dataType = dataType;
    }

    @Override
    public void stat(BIDataRow dataRow, RecordBuffer recordBuf) {
        Object[] dest = recordBuf.getBuffer();
        if (dataRow.getValue(this.srcIndex) == null || dataRow.getValue(this.srcIndex) == dest[this.destIndex]) {
            return;
        }
        if (!this.needStat(dataRow)) {
            return;
        }
        if (dest[this.destIndex] == null) {
            dest[this.destIndex] = dataRow.getValue(this.srcIndex);
        }
        int rs = 0;
        try {
            rs = DataType.compare((int)this.dataType, (Object)dataRow.getValue(this.srcIndex), (Object)dest[this.destIndex]);
        }
        catch (SyntaxException e) {
            throw new RuntimeException(e);
        }
        if (rs < 0) {
            dest[this.destIndex] = dataRow.getValue(this.srcIndex);
        }
    }

    @Override
    public Object toValue(RecordBuffer recordBuf) {
        return recordBuf.getBuffer()[this.destIndex];
    }
}

