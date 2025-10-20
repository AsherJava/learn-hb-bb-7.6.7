/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.model;

import com.jiuqi.nvwa.cellbook.constant.CellBorderStyle;
import com.jiuqi.nvwa.cellbook.constant.StringUtils;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.cellbook.model.CellColor;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CellBorder
implements Serializable,
Cloneable {
    public static final Logger LOGGER = LoggerFactory.getLogger("CellBook");
    private static final long serialVersionUID = 1L;
    private int colorIndex = -1;
    private String color;
    private CellBorderStyle style = CellBorderStyle.THIN;
    private CellBook _cellBook;

    public CellBorder(CellBook cellBook) {
        this._cellBook = cellBook;
    }

    public CellBorder(CellBook cellBook, CellBorderStyle stytle, String color) {
        this._cellBook = cellBook;
        this.style = stytle;
        this.color = color;
    }

    public int getColorIndex() {
        return this.colorIndex;
    }

    public void setColorIndex(int colorIndex) {
        this.colorIndex = colorIndex;
    }

    public String getColor() {
        if (this.colorIndex > -1) {
            return this._cellBook.getBookStyle().getPalette().get(this.colorIndex).getHexString();
        }
        return StringUtils.isEmpty(this.color) ? "" : this.color;
    }

    public void setColor(String color) {
        if (!StringUtils.isEmpty(color)) {
            CellColor cellColor = new CellColor(color);
            int indexOf = this._cellBook.getBookStyle().getPalette().indexOf(cellColor);
            if (indexOf > -1) {
                this.colorIndex = indexOf;
                this.color = "";
            } else {
                this.colorIndex = -1;
                this.color = color;
            }
        } else {
            this.colorIndex = -1;
            this.color = "";
        }
    }

    public CellBorderStyle getStyle() {
        return this.style;
    }

    public void setStyle(CellBorderStyle style) {
        this.style = style;
    }

    public Object clone() {
        CellBorder cellBorder = null;
        try {
            cellBorder = (CellBorder)super.clone();
        }
        catch (CloneNotSupportedException e) {
            LOGGER.error("\u590d\u5236\u5bf9\u8c61\u62a5\u9519\uff01", e);
        }
        return cellBorder;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.color == null ? 0 : this.color.hashCode());
        result = 31 * result + this.colorIndex;
        result = 31 * result + (this.style == null ? 0 : this.style.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        CellBorder other = (CellBorder)obj;
        if (this.color == null ? other.color != null : !this.color.equals(other.color)) {
            return false;
        }
        if (this.colorIndex != other.colorIndex) {
            return false;
        }
        return this.style == other.style;
    }
}

