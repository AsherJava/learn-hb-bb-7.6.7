/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.grid;

import java.io.Serializable;

public interface CellDataPropertyIntf
extends Serializable {
    public byte getDataType();

    public void setDataType(byte var1);

    public byte getDataFlag();

    public void setDataFlag(byte var1);

    public byte getEditMode();

    public void setEditMode(byte var1);

    public byte getDataFormat();

    public void setDataFormat(byte var1);

    public short getDataProperty();

    public void setDataProPerty(short var1);
}

