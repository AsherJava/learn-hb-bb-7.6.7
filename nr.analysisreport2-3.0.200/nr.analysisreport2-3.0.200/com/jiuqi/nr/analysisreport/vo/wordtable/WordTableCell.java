/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jsoup.nodes.Element
 */
package com.jiuqi.nr.analysisreport.vo.wordtable;

import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.jsoup.nodes.Element;

public class WordTableCell {
    private Element td;
    private XWPFTableCell cell;
    private int rowNum;
    private int colNum;
    private String width;

    public WordTableCell(Element td, XWPFTableCell cell, int rowNum, int colNum, String width) {
        this.td = td;
        this.cell = cell;
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.width = width;
    }

    public Element getTd() {
        return this.td;
    }

    public XWPFTableCell getCell() {
        return this.cell;
    }

    public int getRowNum() {
        return this.rowNum;
    }

    public int getColNum() {
        return this.colNum;
    }

    public String getWidth() {
        return this.width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}

