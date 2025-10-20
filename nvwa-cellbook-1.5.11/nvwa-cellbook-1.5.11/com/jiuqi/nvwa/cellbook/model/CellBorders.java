/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.model;

import com.jiuqi.nvwa.cellbook.constant.CellBorderStyle;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.cellbook.model.CellBorder;
import java.io.Serializable;

public class CellBorders
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private CellBorder right;
    private CellBorder left;
    private CellBorder top;
    private CellBorder bottom;
    private CellBorder diagonalUp;
    private CellBorder diagonalDown;
    private CellBook _cellBook;

    public CellBorders(CellBook cellBook) {
        this._cellBook = cellBook;
        this.right = new CellBorder(this._cellBook);
        this.left = new CellBorder(this._cellBook);
        this.top = new CellBorder(this._cellBook);
        this.bottom = new CellBorder(this._cellBook);
        this.diagonalUp = new CellBorder(this._cellBook);
        this.diagonalDown = new CellBorder(this._cellBook);
    }

    public CellBorders(CellBook cellBook, CellBorderStyle stytle, String color) {
        this._cellBook = cellBook;
        this.right = new CellBorder(this._cellBook, stytle, color);
        this.left = new CellBorder(this._cellBook, stytle, color);
        this.top = new CellBorder(this._cellBook, stytle, color);
        this.bottom = new CellBorder(this._cellBook, stytle, color);
        this.diagonalUp = new CellBorder(this._cellBook, stytle, color);
        this.diagonalDown = new CellBorder(this._cellBook, stytle, color);
    }

    public CellBorder getRight() {
        return this.right;
    }

    public void setRight(CellBorder right) {
        this.right = right;
    }

    public CellBorder getLeft() {
        return this.left;
    }

    public void setLeft(CellBorder left) {
        this.left = left;
    }

    public CellBorder getTop() {
        return this.top;
    }

    public void setTop(CellBorder top) {
        this.top = top;
    }

    public CellBorder getBottom() {
        return this.bottom;
    }

    public void setBottom(CellBorder bottom) {
        this.bottom = bottom;
    }

    public CellBorder getDiagonalUp() {
        return this.diagonalUp;
    }

    public void setDiagonalUp(CellBorder diagonalUp) {
        this.diagonalUp = diagonalUp;
    }

    public CellBorder getDiagonalDown() {
        return this.diagonalDown;
    }

    public void setDiagonalDown(CellBorder diagonalDown) {
        this.diagonalDown = diagonalDown;
    }

    public Object clone() {
        CellBorders cellBorders = new CellBorders(this._cellBook);
        cellBorders.setRight((CellBorder)this.right.clone());
        cellBorders.setLeft((CellBorder)this.left.clone());
        cellBorders.setTop((CellBorder)this.top.clone());
        cellBorders.setBottom((CellBorder)this.bottom.clone());
        cellBorders.setDiagonalUp((CellBorder)this.diagonalUp.clone());
        cellBorders.setDiagonalDown((CellBorder)this.diagonalDown.clone());
        return cellBorders;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.bottom == null ? 0 : this.bottom.hashCode());
        result = 31 * result + (this.diagonalDown == null ? 0 : this.diagonalDown.hashCode());
        result = 31 * result + (this.diagonalUp == null ? 0 : this.diagonalUp.hashCode());
        result = 31 * result + (this.left == null ? 0 : this.left.hashCode());
        result = 31 * result + (this.right == null ? 0 : this.right.hashCode());
        result = 31 * result + (this.top == null ? 0 : this.top.hashCode());
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
        CellBorders other = (CellBorders)obj;
        if (this.bottom == null ? other.bottom != null : !this.bottom.equals(other.bottom)) {
            return false;
        }
        if (this.diagonalDown == null ? other.diagonalDown != null : !this.diagonalDown.equals(other.diagonalDown)) {
            return false;
        }
        if (this.diagonalUp == null ? other.diagonalUp != null : !this.diagonalUp.equals(other.diagonalUp)) {
            return false;
        }
        if (this.left == null ? other.left != null : !this.left.equals(other.left)) {
            return false;
        }
        if (this.right == null ? other.right != null : !this.right.equals(other.right)) {
            return false;
        }
        return !(this.top == null ? other.top != null : !this.top.equals(other.top));
    }
}

