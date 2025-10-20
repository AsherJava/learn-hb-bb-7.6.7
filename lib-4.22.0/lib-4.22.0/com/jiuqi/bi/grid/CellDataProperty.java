/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.grid;

import com.jiuqi.bi.grid.CellDataPropertyIntf;
import com.jiuqi.bi.grid.GridCell;
import java.io.Serializable;

public class CellDataProperty
implements CellDataPropertyIntf,
Serializable {
    private static final long serialVersionUID = 4209191452023492210L;
    private byte dataType;
    private byte dataFlag;
    private byte editMode;
    private byte dataFormat;
    private short dataProperty;
    private GridCell cell;

    public CellDataProperty(GridCell cell) {
        if (cell == null) {
            throw new IllegalArgumentException("\u4f20\u5165\u7684cell\u4e3anull");
        }
        this.cell = cell;
    }

    public CellDataProperty(int dataType, int dataFlag, int editMode, int dataFormat, int dataProperty) {
        this.dataType = (byte)dataType;
        this.dataFlag = (byte)dataFlag;
        this.editMode = (byte)editMode;
        this.dataFormat = (byte)dataFormat;
        this.dataProperty = (short)dataProperty;
    }

    @Override
    public byte getDataType() {
        if (this.cell != null) {
            return (byte)this.cell.getType();
        }
        return this.dataType;
    }

    @Override
    public void setDataType(byte value) {
        if (value >= 0 && value <= 11) {
            if (this.cell != null) {
                this.cell.setType(value);
            }
        } else {
            throw new IllegalArgumentException("\u5355\u5143\u683c\u6570\u636e\u7c7b\u578b\u88ab\u9519\u8bef\u8bbe\u7f6e\uff01");
        }
        this.dataType = value;
    }

    @Override
    public byte getDataFlag() {
        if (this.cell != null) {
            return (byte)this.cell.getDataFlag();
        }
        return this.dataFlag;
    }

    @Override
    public void setDataFlag(byte value) {
        if (this.cell != null) {
            this.cell.setDataFlag(value);
        }
        this.dataFlag = value;
    }

    @Override
    public byte getEditMode() {
        if (this.cell != null) {
            return (byte)this.cell.getEditMode();
        }
        return this.editMode;
    }

    @Override
    public void setEditMode(byte value) {
        if (value >= 0 && value <= 9) {
            if (this.cell != null) {
                this.cell.setEditMode(value);
            }
        } else {
            throw new IllegalArgumentException("\u5355\u5143\u683c\u7f16\u8f91\u6a21\u5f0f\u88ab\u9519\u8bef\u8bbe\u7f6e\uff01");
        }
        this.editMode = value;
    }

    @Override
    public byte getDataFormat() {
        if (this.cell != null) {
            return (byte)this.cell.getDataFormat();
        }
        return this.dataFormat;
    }

    @Override
    public void setDataFormat(byte value) {
        if (this.cell != null) {
            this.cell.setDataFormat(value);
        }
        this.dataFormat = value;
    }

    @Override
    public short getDataProperty() {
        if (this.cell != null) {
            return (short)this.cell.getDataProperty();
        }
        return this.dataProperty;
    }

    @Override
    public void setDataProPerty(short value) {
        if (this.cell != null) {
            this.cell.setDataProperty(value);
        }
        this.dataProperty = value;
    }
}

