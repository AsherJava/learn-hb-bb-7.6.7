/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.model;

import com.jiuqi.nvwa.cellbook.constant.CellBorderStyle;
import com.jiuqi.nvwa.cellbook.model.CellBackGround;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.cellbook.model.CellBorders;
import com.jiuqi.nvwa.cellbook.model.CellColor;
import com.jiuqi.nvwa.cellbook.model.CellFont;
import com.jiuqi.nvwa.cellbook.model.CellFormatter;
import com.jiuqi.nvwa.cellbook.model.CellLayout;
import com.jiuqi.nvwa.cellbook.model.CellStyle;
import java.io.Serializable;
import java.util.List;

public class BookStyle
implements Serializable {
    private static final long serialVersionUID = 1L;
    private CellStyle defaultStyle = null;
    private String selector;
    private String serialNumberHeader;
    private String splitLine;
    private String padding;
    private CellBook _cellBook;

    public BookStyle(CellBook cellBook) {
        this._cellBook = cellBook;
        this.defaultStyle = new CellStyle(this._cellBook);
        this.defaultStyle.setFormatter(new CellFormatter());
        CellBorders cellBorders = new CellBorders(this._cellBook, CellBorderStyle.THIN, "D1D1D1");
        this.defaultStyle.setBorders(cellBorders);
        this.defaultStyle.setFont(new CellFont(this._cellBook));
        this.defaultStyle.setBackGround(new CellBackGround(this._cellBook));
        this.defaultStyle.setLayout(new CellLayout());
    }

    public List<CellColor> getPalette() {
        return this._cellBook.getTheme().getPalette();
    }

    public void setPalette(List<CellColor> palette) {
        this._cellBook.getTheme().setPalette(palette);
    }

    public CellStyle getDefaultStyle() {
        return this.defaultStyle;
    }

    public void setDefaultStyle(CellStyle defaultStyle) {
        this.defaultStyle = defaultStyle;
    }

    public String getSelector() {
        return this.selector;
    }

    public String getSerialNumberHeader() {
        return this.serialNumberHeader;
    }

    public String getSplitLine() {
        return this.splitLine;
    }

    public String getPadding() {
        return this.padding;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public void setSerialNumberHeader(String serialNumberHeader) {
        this.serialNumberHeader = serialNumberHeader;
    }

    public void setSplitLine(String splitLine) {
        this.splitLine = splitLine;
    }

    public void setPadding(String padding) {
        this.padding = padding;
    }
}

