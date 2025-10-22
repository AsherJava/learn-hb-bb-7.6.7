/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel2.link;

import java.io.Serializable;
import org.apache.poi.common.usermodel.HyperlinkType;

public class CellLink
implements Serializable {
    private static final long serialVersionUID = 1L;
    private HyperlinkType hyperlinkType;
    private String url;
    private String toSheetName;
    private int toCol;
    private int toRow;
    private int col;
    private int row;
    private String showText;

    public HyperlinkType getHyperlinkType() {
        return this.hyperlinkType;
    }

    public void setHyperlinkType(HyperlinkType hyperlinkType) {
        this.hyperlinkType = hyperlinkType;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getToCol() {
        return this.toCol;
    }

    public void setToCol(int toCol) {
        this.toCol = toCol;
    }

    public int getToRow() {
        return this.toRow;
    }

    public void setToRow(int toRow) {
        this.toRow = toRow;
    }

    public int getCol() {
        return this.col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getShowText() {
        return this.showText;
    }

    public void setShowText(String showText) {
        this.showText = showText;
    }

    public String getToSheetName() {
        return this.toSheetName;
    }

    public void setToSheetName(String toSheetName) {
        this.toSheetName = toSheetName;
    }
}

