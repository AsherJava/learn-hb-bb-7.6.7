/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.itextpdf.layout.borders.Border
 */
package com.jiuqi.va.query.print;

import com.itextpdf.layout.borders.Border;
import java.util.Objects;

public class BorderDTO {
    private Border topBorder;
    private Border leftBorder;
    private Border bottomBorder;
    private Border rightBorder;

    public BorderDTO(Border topBorder, Border leftBorder, Border bottomBorder, Border rightBorder) {
        this.topBorder = topBorder;
        this.leftBorder = leftBorder;
        this.bottomBorder = bottomBorder;
        this.rightBorder = rightBorder;
    }

    public float getLeftBorderWidth() {
        if (Objects.isNull(this.leftBorder)) {
            return 0.0f;
        }
        return this.leftBorder.getWidth();
    }

    public float getRightBorderWidth() {
        if (Objects.isNull(this.rightBorder)) {
            return 0.0f;
        }
        return this.rightBorder.getWidth();
    }

    public Border getTopBorder() {
        return this.topBorder;
    }

    public void setTopBorder(Border topBorder) {
        this.topBorder = topBorder;
    }

    public Border getLeftBorder() {
        return this.leftBorder;
    }

    public void setLeftBorder(Border leftBorder) {
        this.leftBorder = leftBorder;
    }

    public Border getBottomBorder() {
        return this.bottomBorder;
    }

    public void setBottomBorder(Border bottomBorder) {
        this.bottomBorder = bottomBorder;
    }

    public Border getRightBorder() {
        return this.rightBorder;
    }

    public void setRightBorder(Border rightBorder) {
        this.rightBorder = rightBorder;
    }
}

