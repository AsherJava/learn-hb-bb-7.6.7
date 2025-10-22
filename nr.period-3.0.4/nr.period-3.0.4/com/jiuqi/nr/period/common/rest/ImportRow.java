/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.common.rest;

import java.util.Date;

public class ImportRow {
    private String code;
    private String title;
    private Date start;
    private Date end;
    private int row;
    private int col;
    private String startValue;
    private String endValue;
    private String simpleTitle;

    public String getSimpleTitle() {
        return this.simpleTitle;
    }

    public void setSimpleTitle(String simpleTitle) {
        this.simpleTitle = simpleTitle;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStart() {
        return this.start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return this.end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return this.col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getStartValue() {
        return this.startValue;
    }

    public void setStartValue(String startValue) {
        this.startValue = startValue;
    }

    public String getEndValue() {
        return this.endValue;
    }

    public void setEndValue(String endValue) {
        this.endValue = endValue;
    }
}

