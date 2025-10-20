/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.model;

import com.jiuqi.nvwa.cellbook.constant.CellStyleModel;
import java.io.Serializable;

public class SimpleCellStyle
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int numFmt = -1;
    private int font = -1;
    private int fill = -1;
    private int border = -1;
    private int layout = -1;
    private CellStyleModel model;

    public int getNumFmt() {
        return this.numFmt;
    }

    public int getFont() {
        return this.font;
    }

    public int getFill() {
        return this.fill;
    }

    public int getBorder() {
        return this.border;
    }

    public int getLayout() {
        return this.layout;
    }

    public CellStyleModel getModel() {
        return this.model;
    }

    public void setNumFmt(int numFmt) {
        this.numFmt = numFmt;
    }

    public void setFont(int font) {
        this.font = font;
    }

    public void setFill(int fill) {
        this.fill = fill;
    }

    public void setBorder(int border) {
        this.border = border;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public void setModel(CellStyleModel model) {
        this.model = model;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + this.border;
        result = 31 * result + this.fill;
        result = 31 * result + this.font;
        result = 31 * result + this.layout;
        result = 31 * result + (this.model == null ? 0 : this.model.hashCode());
        result = 31 * result + this.numFmt;
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
        SimpleCellStyle other = (SimpleCellStyle)obj;
        if (this.border != other.border) {
            return false;
        }
        if (this.fill != other.fill) {
            return false;
        }
        if (this.font != other.font) {
            return false;
        }
        if (this.layout != other.layout) {
            return false;
        }
        if (this.model != other.model) {
            return false;
        }
        return this.numFmt == other.numFmt;
    }
}

