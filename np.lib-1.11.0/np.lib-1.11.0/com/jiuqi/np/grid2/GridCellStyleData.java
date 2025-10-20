/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.grid2;

import com.jiuqi.np.graphics.ImageDescriptor;
import java.io.Serializable;

public class GridCellStyleData
implements Cloneable,
Serializable {
    private static final long serialVersionUID = 3556891693688018280L;
    private int backGroundColor;
    private int foreGroundColor;
    private int backGroundStyle;
    private int rightBorderColor;
    private int rightBorderStyle = -1;
    private int bottomBorderColor;
    private int bottomBorderStyle = -1;
    public int diagonalBorderColor = 0;
    public int diagonalBorderStyle = 0;
    public int inverseDiagonalBorderColor;
    public int inverseDiagonalBorderStyle = 0;
    private boolean selectable;
    private boolean editable;
    private boolean wrapLine;
    private int indent;
    private int vertAlign;
    private int horzAlign;
    private boolean vertText;
    private boolean silverHead;
    private boolean multiLine;
    private Integer horizon;
    private Integer vectical;
    private int rowSpan;
    private int colSpan;
    private String fontName;
    private int fontSize;
    private int fontStyle;
    private boolean fitFontSize;
    private Integer gradientColor1;
    private Integer gradientColor2;
    private Integer gradientDirection;
    private ImageDescriptor backImage;
    private int backGroundImageStyle;

    public void setBackImagePosition(int horizon, int vectical) {
        this.horizon = horizon;
        this.vectical = vectical;
    }

    public Integer getGradientColor1() {
        return this.gradientColor1;
    }

    public void setGradientBackground(int color1, int color2, int dir) {
        this.setGradientColor1(color1);
        this.setGradientColor2(color2);
        this.setGradientDirection(dir);
    }

    public void setGradientColor1(int gradientColor1) {
        this.gradientColor1 = gradientColor1;
    }

    public Integer getGradientColor2() {
        return this.gradientColor2;
    }

    public void setGradientColor2(int gradientColor2) {
        this.gradientColor2 = gradientColor2;
    }

    public Integer getGradientDirection() {
        return this.gradientDirection;
    }

    public void setGradientDirection(int gradientDirection) {
        this.gradientDirection = gradientDirection;
    }

    public int getBackgroundImageStyle() {
        return this.backGroundImageStyle;
    }

    public void setBackgroundImageStyle(int style) {
        this.backGroundImageStyle = style;
    }

    public Integer getHorizon() {
        return this.horizon;
    }

    public Integer getVectical() {
        return this.vectical;
    }

    public int getRowSpan() {
        return this.rowSpan;
    }

    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }

    public int getColSpan() {
        return this.colSpan;
    }

    public void setColSpan(int colSpan) {
        this.colSpan = colSpan;
    }

    public ImageDescriptor getBackImage() {
        return this.backImage;
    }

    public void setBackImage(ImageDescriptor backImage) {
        this.backImage = backImage;
    }

    public int getBackGroundColor() {
        return this.backGroundColor;
    }

    public void setBackGroundColor(int backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public int getForeGroundColor() {
        return this.foreGroundColor;
    }

    public void setForeGroundColor(int foreGroundColor) {
        this.foreGroundColor = foreGroundColor;
    }

    public int getBackGroundStyle() {
        return this.backGroundStyle;
    }

    public void setBackGroundStyle(int backGroundStyle) {
        this.backGroundStyle = backGroundStyle;
    }

    public String getFontName() {
        return this.fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public int getFontSize() {
        return this.fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getFontStyle() {
        return this.fontStyle;
    }

    public void setFontStyle(int fontStyle) {
        this.fontStyle = fontStyle;
    }

    public int getRightBorderColor() {
        return this.rightBorderColor;
    }

    public void setRightBorderColor(int rightBorderColor) {
        this.rightBorderColor = rightBorderColor;
    }

    public int getRightBorderStyle() {
        return this.rightBorderStyle;
    }

    public void setRightBorderStyle(int rightBorderStyle) {
        this.rightBorderStyle = rightBorderStyle;
    }

    public int getBottomBorderColor() {
        return this.bottomBorderColor;
    }

    public void setBottomBorderColor(int bottomBorderColor) {
        this.bottomBorderColor = bottomBorderColor;
    }

    public int getBottomBorderStyle() {
        return this.bottomBorderStyle;
    }

    public void setBottomBorderStyle(int bottomBorderStyle) {
        this.bottomBorderStyle = bottomBorderStyle;
    }

    public int getDiagonalBorderColor() {
        return this.diagonalBorderColor;
    }

    public void setDiagonalBorderColor(int diagonalBorderColor) {
        this.diagonalBorderColor = diagonalBorderColor;
    }

    public int getDiagonalBorderStyle() {
        return this.diagonalBorderStyle;
    }

    public void setDiagonalBorderStyle(int diagonalBorderStyle) {
        this.diagonalBorderStyle = diagonalBorderStyle;
    }

    public int getInverseDiagonalBorderColor() {
        return this.inverseDiagonalBorderColor;
    }

    public void setInverseDiagonalBorderColor(int inverseDiagonalBorderColor) {
        this.inverseDiagonalBorderColor = inverseDiagonalBorderColor;
    }

    public int getInverseDiagonalBorderStyle() {
        return this.inverseDiagonalBorderStyle;
    }

    public void setInverseDiagonalBorderStyle(int inverseDiagonalBorderStyle) {
        this.inverseDiagonalBorderStyle = inverseDiagonalBorderStyle;
    }

    public boolean isSelectable() {
        return this.selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public boolean isEditable() {
        return this.editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isWrapLine() {
        return this.wrapLine;
    }

    public void setWrapLine(boolean wrapLine) {
        this.wrapLine = wrapLine;
    }

    public int getIndent() {
        return this.indent;
    }

    public void setIndent(int indent) {
        this.indent = indent;
    }

    public int getVertAlign() {
        return this.vertAlign;
    }

    public void setVertAlign(int vertAlign) {
        this.vertAlign = vertAlign;
    }

    public int getHorzAlign() {
        return this.horzAlign;
    }

    public void setHorzAlign(int horzAlign) {
        this.horzAlign = horzAlign;
    }

    public boolean isVertText() {
        return this.vertText;
    }

    public void setVertText(boolean vertText) {
        this.vertText = vertText;
    }

    public boolean isSilverHead() {
        return this.silverHead;
    }

    public void setSilverHead(boolean silverHead) {
        this.silverHead = silverHead;
        if (silverHead) {
            this.setBackGroundColor(-16777201);
        } else if (this.getBackGroundColor() < 0) {
            this.setBackGroundColor(-16777211);
        }
    }

    public boolean isMultiLine() {
        return this.multiLine;
    }

    public void setMultiLine(boolean multiLine) {
        this.multiLine = multiLine;
    }

    public GridCellStyleData() {
        this.setDefault();
    }

    public boolean isFitFontSize() {
        return this.fitFontSize;
    }

    public void setFitFontSize(boolean fitFontSize) {
        this.fitFontSize = fitFontSize;
    }

    public void setDefault() {
        this.setRowSpan(1);
        this.setColSpan(1);
        this.setBackGroundColor(-1);
        this.setForeGroundColor(-1);
        this.setRightBorderColor(-1);
        this.setBottomBorderColor(-1);
        this.setIndent(0);
        this.setBackGroundStyle(0);
        this.setRightBorderStyle(0);
        this.setBottomBorderStyle(0);
        this.setSelectable(true);
        this.setEditable(true);
        this.setWrapLine(false);
        this.setFontName("\u5fae\u8f6f\u96c5\u9ed1");
        this.setFontSize(12);
        this.setFontStyle(1);
        this.setVertAlign(0);
        this.setHorzAlign(0);
        this.setVertText(false);
        this.setSilverHead(false);
        this.setMultiLine(true);
        this.setBackImagePosition(-1, -1);
        this.setBackgroundImageStyle(0);
        this.setFitFontSize(false);
    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int hashCode() {
        StringBuffer one = new StringBuffer();
        int imageHashcode = 0;
        if (null != this.backImage) {
            imageHashcode = this.backImage.hashCode();
        }
        int horizonHashcode = 0;
        if (null != this.horizon) {
            horizonHashcode = this.horizon.hashCode();
        }
        int vecticalHashcode = 0;
        if (null != this.vectical) {
            vecticalHashcode = this.vectical.hashCode();
        }
        int fontNameHashcode = 0;
        if (null != this.fontName) {
            fontNameHashcode = this.fontName.hashCode();
        }
        int gradientColor1Hashcode = 0;
        if (null != this.gradientColor1) {
            gradientColor1Hashcode = this.gradientColor1.hashCode();
        }
        int gradientColor2Hashcode = 0;
        if (null != this.gradientColor2) {
            gradientColor2Hashcode = this.gradientColor2.hashCode();
        }
        int gradientDirectionHashcode = 0;
        if (null != this.gradientDirection) {
            gradientDirectionHashcode = this.gradientDirection.hashCode();
        }
        return one.append(this.backGroundColor).append(this.foreGroundColor).append(this.backGroundStyle).append(this.rightBorderColor).append(this.rightBorderStyle).append(this.bottomBorderColor).append(this.bottomBorderStyle).append(this.diagonalBorderColor).append(this.diagonalBorderStyle).append(this.inverseDiagonalBorderColor).append(this.inverseDiagonalBorderStyle).append(this.selectable).append(this.editable).append(this.wrapLine).append(this.indent).append(this.vertAlign).append(this.horzAlign).append(this.vertText).append(this.silverHead).append(this.multiLine).append(horizonHashcode).append(vecticalHashcode).append(this.rowSpan).append(this.colSpan).append(fontNameHashcode).append(this.fontSize).append(this.fontStyle).append(this.fitFontSize).append(gradientColor1Hashcode).append(gradientColor2Hashcode).append(gradientDirectionHashcode).append(imageHashcode).append(this.backGroundImageStyle).toString().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof GridCellStyleData) {
            GridCellStyleData temp = (GridCellStyleData)obj;
            if (this.backImage != null && !this.backImage.equals(temp.backImage)) {
                return false;
            }
            if (this.horizon != null && !this.horizon.equals(temp.horizon)) {
                return false;
            }
            if (this.vectical != null && !this.horizon.equals(temp.horizon)) {
                return false;
            }
            if (this.fontName != null && !this.fontName.equals(temp.fontName)) {
                return false;
            }
            if (this.gradientColor1 != null && !this.gradientColor1.equals(temp.gradientColor1)) {
                return false;
            }
            if (this.gradientColor2 != null && !this.gradientColor2.equals(temp.gradientColor2)) {
                return false;
            }
            if (this.gradientDirection != null && !this.gradientDirection.equals(temp.gradientDirection)) {
                return false;
            }
            return this.backGroundColor == temp.backGroundColor && this.foreGroundColor == temp.foreGroundColor && this.backGroundColor == temp.backGroundColor && this.foreGroundColor == temp.foreGroundColor && this.backGroundStyle == temp.backGroundStyle && this.rightBorderColor == temp.rightBorderColor && this.rightBorderStyle == temp.rightBorderStyle && this.bottomBorderColor == temp.bottomBorderColor && this.bottomBorderStyle == temp.bottomBorderStyle && this.diagonalBorderStyle == temp.diagonalBorderStyle && this.diagonalBorderColor == temp.diagonalBorderColor && this.inverseDiagonalBorderColor == temp.inverseDiagonalBorderColor && this.inverseDiagonalBorderStyle == temp.inverseDiagonalBorderStyle && this.selectable == temp.selectable && this.editable == temp.editable && this.wrapLine == temp.wrapLine && this.indent == temp.indent && this.vertAlign == temp.vertAlign && this.horzAlign == temp.horzAlign && this.vertText == temp.vertText && this.silverHead == temp.silverHead && this.multiLine == temp.multiLine && this.rowSpan == temp.rowSpan && this.colSpan == temp.colSpan && this.fontSize == temp.fontSize && this.fontStyle == temp.fontStyle && this.fitFontSize == temp.fitFontSize && this.backGroundImageStyle == temp.backGroundImageStyle;
        }
        return false;
    }
}

