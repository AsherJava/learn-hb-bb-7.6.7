/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.nr.single.core.para.parser.table;

import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import com.jiuqi.nr.single.core.para.util.WriteUtil;

public class FieldDefBlockInfo {
    private int folatingIndex;
    private int fieldCount;
    private int childDefsCount;
    private int dataLength;
    private int totalLength;
    private int fieldDefsPosition;
    private int enumIdentiesPosition;
    private int captionsPosition;
    private int descriptionsPosition;
    private int extPropPosition;
    private int maskFlag;
    private long[] reserved = new long[5];
    private int floatCount;

    public final int getFolatingIndex() {
        return this.folatingIndex;
    }

    public final int getFieldCount() {
        return this.fieldCount;
    }

    public final int getChildDefsCount() {
        return this.childDefsCount;
    }

    public final int getDataLength() {
        return this.dataLength;
    }

    public final int getTotalLength() {
        return this.totalLength;
    }

    public final int getFieldDefsPosition() {
        return this.fieldDefsPosition;
    }

    public final int getEnumIdentiesPosition() {
        return this.enumIdentiesPosition;
    }

    public final int getCaptionsPosition() {
        return this.captionsPosition;
    }

    public final int getDescriptionsPosition() {
        return this.descriptionsPosition;
    }

    public final int getExtPropPosition() {
        return this.extPropPosition;
    }

    public final int getMaskFlag() {
        return this.maskFlag;
    }

    public final long[] getReserved() {
        return this.reserved;
    }

    public int size() {
        return 64;
    }

    public final int getFloatCount() {
        return this.floatCount;
    }

    public void init(Stream mask0) throws StreamException {
        this.folatingIndex = ReadUtil.readIntValue(mask0);
        this.fieldCount = ReadUtil.readIntValue(mask0);
        this.childDefsCount = ReadUtil.readIntValue(mask0);
        this.dataLength = ReadUtil.readIntValue(mask0);
        this.totalLength = ReadUtil.readIntValue(mask0);
        this.fieldDefsPosition = ReadUtil.readIntValue(mask0);
        this.enumIdentiesPosition = ReadUtil.readIntValue(mask0);
        this.captionsPosition = ReadUtil.readIntValue(mask0);
        this.descriptionsPosition = ReadUtil.readIntValue(mask0);
        this.extPropPosition = ReadUtil.readIntValue(mask0);
        this.maskFlag = ReadUtil.readIntValue(mask0);
        this.reserved[0] = ReadUtil.readIntValue(mask0);
        this.reserved[1] = ReadUtil.readIntValue(mask0);
        this.reserved[2] = ReadUtil.readIntValue(mask0);
        this.reserved[3] = ReadUtil.readIntValue(mask0);
        this.reserved[4] = ReadUtil.readIntValue(mask0);
        if (this.folatingIndex > 0) {
            this.floatCount = this.folatingIndex >> 16;
            this.folatingIndex &= 0xFF;
        }
    }

    public void save(Stream mask0) throws StreamException {
        WriteUtil.writeIntValue(mask0, this.folatingIndex);
        WriteUtil.writeIntValue(mask0, this.fieldCount);
        WriteUtil.writeIntValue(mask0, this.childDefsCount);
        WriteUtil.writeIntValue(mask0, this.dataLength);
        WriteUtil.writeIntValue(mask0, this.totalLength);
        WriteUtil.writeIntValue(mask0, this.fieldDefsPosition);
        WriteUtil.writeIntValue(mask0, this.enumIdentiesPosition);
        WriteUtil.writeIntValue(mask0, this.captionsPosition);
        WriteUtil.writeIntValue(mask0, this.descriptionsPosition);
        WriteUtil.writeIntValue(mask0, this.extPropPosition);
        WriteUtil.writeIntValue(mask0, this.maskFlag);
        WriteUtil.writeIntValue(mask0, (int)this.reserved[0]);
        WriteUtil.writeIntValue(mask0, (int)this.reserved[1]);
        WriteUtil.writeIntValue(mask0, (int)this.reserved[2]);
        WriteUtil.writeIntValue(mask0, (int)this.reserved[3]);
        WriteUtil.writeIntValue(mask0, (int)this.reserved[4]);
    }
}

