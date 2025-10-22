/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.utils;

import com.jiuqi.nr.analysisreport.utils.Row;
import java.util.ArrayList;
import java.util.List;

public class Table {
    private List<Row> rows = new ArrayList<Row>();
    private StringBuffer style = new StringBuffer("border-collapse: collapse;margin-top: 5px;");
    private StringBuffer attribute = new StringBuffer(" width=");
    private String width = "100%";

    public Table(int rows, int cols) {
        for (int i = 0; i < rows; ++i) {
            Row row = new Row(cols);
            this.rows.add(row);
        }
    }

    public Table() {
    }

    public String getWidth() {
        return this.width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String toString() {
        StringBuffer suf = new StringBuffer("<table  ");
        suf.append(this.attribute.toString() + this.width).append(" style=\"");
        suf.append(this.style.toString());
        suf.append("\">");
        for (Row row : this.rows) {
            suf.append(row.toString());
        }
        suf.append("</table>");
        return suf.toString();
    }

    public Row getRow(int index) {
        return this.rows.get(index);
    }

    public Row addRow(int index) {
        Row row = new Row();
        this.rows.add(index + 1, row);
        return row;
    }

    public Row addRow() {
        Row row = new Row();
        this.rows.add(row);
        return row;
    }

    public void copyRows(int index) {
        Row m = this.getRow(index).clone();
        this.rows.add(index + 1, m);
    }

    public void copyRowFromSource(int target, int source) {
        Row m = this.getRow(source).clone();
        this.rows.add(target, m);
    }

    public void copyRows(int index, int num) {
        for (int i = 0; i < num; ++i) {
            this.copyRows(index + i);
        }
    }

    public List<Row> getRows() {
        return this.rows;
    }

    public StringBuffer getStyle() {
        return this.style;
    }

    public void addStyle(String style) {
        this.style.append(style);
    }

    public void clearStyle() {
        this.style = new StringBuffer("border-collapse: collapse;margin-top: 5px;");
    }

    public StringBuffer getAttribute() {
        return this.attribute;
    }
}

