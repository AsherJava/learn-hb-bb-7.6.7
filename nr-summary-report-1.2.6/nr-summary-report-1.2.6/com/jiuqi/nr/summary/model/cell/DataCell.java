/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 */
package com.jiuqi.nr.summary.model.cell;

import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.summary.model.cell.SummaryZb;
import java.io.Serializable;
import java.util.Date;

public class DataCell
implements Serializable {
    private String key;
    private int x;
    private int y;
    private int rowNum;
    private int colNum;
    private SummaryZb summaryZb;
    private String exp;
    private String expTitle;
    private DataFieldGatherType summaryMode;
    private Date modifyTime;

    public String getKey() {
        return this.key;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getRowNum() {
        return this.rowNum;
    }

    public int getColNum() {
        return this.colNum;
    }

    public SummaryZb getSummaryZb() {
        return this.summaryZb;
    }

    public String getExp() {
        return this.exp;
    }

    public String getExpTitle() {
        return this.expTitle;
    }

    public DataFieldGatherType getSummaryMode() {
        return this.summaryMode;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public void setSummaryZb(SummaryZb summaryZb) {
        this.summaryZb = summaryZb;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public void setExpTitle(String expTitle) {
        this.expTitle = expTitle;
    }

    public void setSummaryMode(DataFieldGatherType summaryMode) {
        this.summaryMode = summaryMode;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}

