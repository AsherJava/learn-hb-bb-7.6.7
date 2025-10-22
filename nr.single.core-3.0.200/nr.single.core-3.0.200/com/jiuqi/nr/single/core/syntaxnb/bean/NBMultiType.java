/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.syntaxnb.bean;

import com.jiuqi.nr.single.core.syntaxnb.bean.FinanceDataType;
import com.jiuqi.nr.single.core.syntaxnb.bean.NBSet;
import com.jiuqi.nr.single.core.syntaxnb.bean.NBTableCellType;
import com.jiuqi.nr.single.core.syntaxnb.common.NBTypeType;
import java.util.Date;

public class NBMultiType {
    private NBTypeType opType;
    private int opInt;
    private double opReal;
    private String opStr;
    private boolean opBool;
    private Date opDate;
    private NBSet opSet;
    private NBTableCellType opTable;
    private FinanceDataType OpFinance;
    private String Optext;

    public NBTypeType getOpType() {
        return this.opType;
    }

    public void setOpType(NBTypeType opType) {
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

    public Date getOpDate() {
        return this.opDate;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }

    public NBSet getOpSet() {
        if (this.opSet == null) {
            this.opSet = new NBSet();
        }
        return this.opSet;
    }

    public void setOpSet(NBSet opSet) {
        this.opSet = opSet;
    }

    public NBTableCellType getOpTable() {
        if (this.opTable == null) {
            this.opTable = new NBTableCellType();
        }
        return this.opTable;
    }

    public void setOpTable(NBTableCellType opTable) {
        this.opTable = opTable;
    }

    public FinanceDataType getOpFinance() {
        if (this.OpFinance == null) {
            this.OpFinance = new FinanceDataType();
        }
        return this.OpFinance;
    }

    public void setOpFinance(FinanceDataType opFinance) {
        this.OpFinance = opFinance;
    }

    public String getOptext() {
        return this.Optext;
    }

    public void setOptext(String optext) {
        this.Optext = optext;
    }

    public void copyFrom(NBMultiType source) {
        this.opType = source.getOpType();
        this.opInt = source.getOpInt();
        this.opReal = source.getOpReal();
        this.opStr = source.getOpStr();
        this.opBool = source.isOpBool();
        this.opDate = source.getOpDate();
        this.getOpTable().copyFrom(source.getOpTable());
        this.getOpSet().copyFrom(source.getOpSet());
        this.getOpFinance().copyFrom(source.getOpFinance());
        this.setOptext(source.getOptext());
    }
}

