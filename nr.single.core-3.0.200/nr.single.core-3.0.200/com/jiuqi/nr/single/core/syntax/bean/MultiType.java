/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntax.bean;

import com.jiuqi.nr.single.core.syntax.bean.BZZDataType;
import com.jiuqi.nr.single.core.syntax.bean.CodeCellType;
import com.jiuqi.nr.single.core.syntax.bean.ExistDataType;
import com.jiuqi.nr.single.core.syntax.bean.TableCellType;
import com.jiuqi.nr.single.core.syntax.common.TypeType;
import java.util.Date;

public class MultiType {
    private TypeType opType;
    private int opInt;
    private double opReal;
    private String opStr;
    private boolean opBool;
    private Date opDate;
    private TableCellType opTable;
    private CodeCellType opCode;
    private ExistDataType opExist;
    private BZZDataType opBZZ;

    public TypeType getOpType() {
        return this.opType;
    }

    public void setOpType(TypeType opType) {
        this.opType = opType;
    }

    public int getOpInt() {
        return this.opInt;
    }

    public void setOpInt(int opInt) {
        this.opInt = opInt;
    }

    public double getOpReal() {
        return this.opReal;
    }

    public void setOpReal(double opReal) {
        this.opReal = opReal;
    }

    public String getOpStr() {
        return this.opStr;
    }

    public void setOpStr(String opStr) {
        this.opStr = opStr;
    }

    public boolean isOpBool() {
        return this.opBool;
    }

    public void setOpBool(boolean opBool) {
        this.opBool = opBool;
    }

    public TableCellType getOpTable() {
        if (this.opTable == null) {
            this.opTable = new TableCellType();
        }
        return this.opTable;
    }

    public void setOpTable(TableCellType opTable) {
        this.opTable = opTable;
    }

    public CodeCellType getOpCode() {
        if (this.opCode == null) {
            this.opCode = new CodeCellType();
        }
        return this.opCode;
    }

    public void setOpCode(CodeCellType opCode) {
        this.opCode = opCode;
    }

    public ExistDataType getOpExist() {
        if (this.opExist == null) {
            this.opExist = new ExistDataType();
        }
        return this.opExist;
    }

    public void setOpExist(ExistDataType opExist) {
        this.opExist = opExist;
    }

    public BZZDataType getOpBZZ() {
        if (this.opBZZ == null) {
            this.opBZZ = new BZZDataType();
        }
        return this.opBZZ;
    }

    public void setOpBZZ(BZZDataType opBZZ) {
        this.opBZZ = opBZZ;
    }

    public void copyFrom(MultiType source) {
        this.opType = source.getOpType();
        this.opInt = source.getOpInt();
        this.opReal = source.getOpReal();
        this.opStr = source.getOpStr();
        this.opBool = source.isOpBool();
        this.getOpTable().copyFrom(source.getOpTable());
        this.getOpCode().copyFrom(source.getOpCode());
        this.getOpExist().copyFrom(source.getOpExist());
        this.getOpBZZ().copyFrom(source.getOpBZZ());
    }
}

