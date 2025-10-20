/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.model;

import com.jiuqi.nvwa.cellbook.constant.HorizontalAlignment;
import com.jiuqi.nvwa.cellbook.constant.VerticalAlignment;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CellLayout
implements Serializable,
Cloneable {
    public static final Logger LOGGER = LoggerFactory.getLogger("CellBook");
    private static final long serialVersionUID = 1L;
    private int indent;
    private boolean fitSize;
    private boolean wrapLine;
    private HorizontalAlignment horizontalAlignment = HorizontalAlignment.GENERAL;
    private VerticalAlignment verticalAlignment = VerticalAlignment.CENTER;
    private int textRotation;

    public int getIndent() {
        return this.indent;
    }

    public boolean isFitSize() {
        return this.fitSize;
    }

    public boolean isWrapLine() {
        return this.wrapLine;
    }

    public HorizontalAlignment getHorizontalAlignment() {
        return this.horizontalAlignment;
    }

    public VerticalAlignment getVerticalAlignment() {
        return this.verticalAlignment;
    }

    public int getTextRotation() {
        return this.textRotation;
    }

    public void setIndent(int indent) {
        this.indent = indent;
    }

    public void setFitSize(boolean fitSize) {
        this.fitSize = fitSize;
    }

    public void setWrapLine(boolean wrapLine) {
        this.wrapLine = wrapLine;
    }

    public void setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }

    public void setVerticalAlignment(VerticalAlignment verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }

    public void setTextRotation(int textRotation) {
        this.textRotation = textRotation;
    }

    public Object clone() {
        CellLayout cellLayout = null;
        try {
            cellLayout = (CellLayout)super.clone();
        }
        catch (CloneNotSupportedException e) {
            LOGGER.error("\u590d\u5236\u5bf9\u8c61\u62a5\u9519\uff01", e);
        }
        return cellLayout;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.fitSize ? 1231 : 1237);
        result = 31 * result + (this.horizontalAlignment == null ? 0 : this.horizontalAlignment.hashCode());
        result = 31 * result + this.indent;
        result = 31 * result + this.textRotation;
        result = 31 * result + (this.verticalAlignment == null ? 0 : this.verticalAlignment.hashCode());
        result = 31 * result + (this.wrapLine ? 1231 : 1237);
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
        CellLayout other = (CellLayout)obj;
        if (this.fitSize != other.fitSize) {
            return false;
        }
        if (this.horizontalAlignment != other.horizontalAlignment) {
            return false;
        }
        if (this.indent != other.indent) {
            return false;
        }
        if (this.textRotation != other.textRotation) {
            return false;
        }
        if (this.verticalAlignment != other.verticalAlignment) {
            return false;
        }
        return this.wrapLine == other.wrapLine;
    }
}

