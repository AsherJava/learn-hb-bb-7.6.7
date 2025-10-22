/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.utils;

import com.jiuqi.nr.analysisreport.utils.Col;
import java.util.ArrayList;
import java.util.List;

public class Row {
    private List<Col> cols = new ArrayList<Col>();
    private StringBuffer style = new StringBuffer("");
    private StringBuffer attribute = new StringBuffer("");

    public Col addCol(int i) {
        Col col = new Col();
        this.cols.add(i + 1, col);
        return col;
    }

    public Col addCol() {
        Col col = new Col();
        this.cols.add(col);
        return col;
    }

    public void removeCol(int index) {
        this.cols.remove(index);
    }

    public Row(int cols) {
        for (int i = 0; i < cols; ++i) {
            this.cols.add(new Col());
        }
    }

    public Row clone() {
        Row row = new Row();
        row.style = new StringBuffer(this.style);
        row.attribute = new StringBuffer(this.attribute);
        for (Col col : this.cols) {
            Col copyCol = col.clone();
            row.cols.add(copyCol);
        }
        return row;
    }

    public Row() {
    }

    public Col getCol(int index) {
        return this.cols.get(index);
    }

    public String toString() {
        StringBuffer suf = new StringBuffer("<tr ");
        suf.append(this.attribute.toString()).append(" style=\"");
        suf.append(this.style.toString());
        suf.append("\">");
        for (Col col : this.cols) {
            suf.append(col.toString());
        }
        suf.append("</tr>");
        return suf.toString();
    }

    public List<Col> getCols() {
        return this.cols;
    }

    public StringBuffer getStyle() {
        return this.style;
    }

    public StringBuffer getAttribute() {
        return this.attribute;
    }
}

