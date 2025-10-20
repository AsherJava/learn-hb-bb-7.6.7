/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.model;

import com.jiuqi.nvwa.cellbook.constant.FillPatternType;
import com.jiuqi.nvwa.cellbook.constant.StringUtils;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.cellbook.model.CellColor;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CellBackGround
implements Serializable,
Cloneable {
    public static final Logger LOGGER = LoggerFactory.getLogger("CellBook");
    private static final long serialVersionUID = 1L;
    private FillPatternType fillPatternType = FillPatternType.NO_FILL;
    private String backGroundImg = "";
    private int colorIndex = -1;
    private String color = "";
    private CellBook _cellBook;

    public CellBackGround(CellBook cellBook) {
        this._cellBook = cellBook;
    }

    public FillPatternType getFillPatternType() {
        return this.fillPatternType;
    }

    public void setFillPatternType(FillPatternType fillPatternType) {
        this.fillPatternType = fillPatternType;
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

    public String getBackGroundImg() {
        return this.backGroundImg;
    }

    public void setBackGroundImg(String backGroundImg) {
        if (null == backGroundImg) {
            backGroundImg = "";
        }
        this.backGroundImg = backGroundImg;
    }

    public Object clone() {
        CellBackGround cellBackGround = null;
        try {
            cellBackGround = (CellBackGround)super.clone();
        }
        catch (CloneNotSupportedException e) {
            LOGGER.error("\u590d\u5236\u5bf9\u8c61\u62a5\u9519\uff01", e);
        }
        return cellBackGround;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.backGroundImg == null ? 0 : this.backGroundImg.hashCode());
        result = 31 * result + (this.color == null ? 0 : this.color.hashCode());
        result = 31 * result + this.colorIndex;
        result = 31 * result + (this.fillPatternType == null ? 0 : this.fillPatternType.hashCode());
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
        CellBackGround other = (CellBackGround)obj;
        if (this.backGroundImg == null ? other.backGroundImg != null : !this.backGroundImg.equals(other.backGroundImg)) {
            return false;
        }
        if (this.color == null ? other.color != null : !this.color.equals(other.color)) {
            return false;
        }
        if (this.colorIndex != other.colorIndex) {
            return false;
        }
        return this.fillPatternType == other.fillPatternType;
    }
}

