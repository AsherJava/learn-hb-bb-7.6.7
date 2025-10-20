/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.model;

import com.jiuqi.nvwa.cellbook.constant.StringUtils;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.cellbook.model.CellColor;
import java.io.Serializable;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CellFont
implements Serializable,
Cloneable {
    public static final Logger LOGGER = LoggerFactory.getLogger("CellBook");
    private static final long serialVersionUID = 1L;
    private String name = "\u5fae\u8f6f\u96c5\u9ed1";
    private float size = 14.0f;
    private int colorIndex = 1;
    private String color;
    private boolean bold;
    private boolean italic;
    private boolean underline;
    private boolean inline;
    private CellBook _cellBook;

    public CellFont(CellBook cellBook) {
        this._cellBook = cellBook;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Deprecated
    public int getSize() {
        return (int)this.size;
    }

    @Deprecated
    public void setSize(int size) {
        this.size = size;
    }

    public float getSizeF() {
        return this.size;
    }

    public void setSizeF(float size) {
        this.size = size;
    }

    public boolean isBold() {
        return this.bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isItalic() {
        return this.italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public boolean isUnderline() {
        return this.underline;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    public boolean isInline() {
        return this.inline;
    }

    public void setInline(boolean inline) {
        this.inline = inline;
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

    public Object clone() {
        CellFont cellFont = null;
        try {
            cellFont = (CellFont)super.clone();
        }
        catch (CloneNotSupportedException e) {
            LOGGER.error("\u590d\u5236\u5bf9\u8c61\u62a5\u9519\uff01", e);
        }
        return cellFont;
    }

    public int hashCode() {
        return Objects.hash(this.bold, this.color, this.colorIndex, this.inline, this.italic, this.name, Float.valueOf(this.size), this.underline);
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
        CellFont other = (CellFont)obj;
        return this.bold == other.bold && Objects.equals(this.color, other.color) && this.colorIndex == other.colorIndex && this.inline == other.inline && this.italic == other.italic && Objects.equals(this.name, other.name) && Double.doubleToLongBits(this.size) == Double.doubleToLongBits(other.size) && this.underline == other.underline;
    }
}

