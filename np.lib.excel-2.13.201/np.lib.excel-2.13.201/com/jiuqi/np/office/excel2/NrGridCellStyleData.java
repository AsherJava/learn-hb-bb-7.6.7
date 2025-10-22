/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.grid2.GridCellStyleData
 *  com.jiuqi.nvwa.grid2.graphics.Font
 *  com.jiuqi.nvwa.grid2.graphics.ImageDescriptor
 */
package com.jiuqi.np.office.excel2;

import com.jiuqi.nvwa.grid2.GridCellStyleData;
import com.jiuqi.nvwa.grid2.graphics.Font;
import com.jiuqi.nvwa.grid2.graphics.ImageDescriptor;

public class NrGridCellStyleData
extends GridCellStyleData {
    private static final long serialVersionUID = -2982374502585086989L;
    private final GridCellStyleData gridCellStyleData;
    private int leftBorderColor;
    private int leftBorderStyle = -1;
    private int topBorderColor;
    private int topBorderStyle = -1;

    public NrGridCellStyleData(GridCellStyleData gridCellStyleData) {
        this.gridCellStyleData = gridCellStyleData;
    }

    public String getBackImageBounds() {
        return this.gridCellStyleData.getBackImageBounds();
    }

    public void setBackImageBounds(String backImageBounds) {
        this.gridCellStyleData.setBackImageBounds(backImageBounds);
    }

    public void setBackImagePosition(int horizon, int vectical) {
        this.gridCellStyleData.setBackImagePosition(horizon, vectical);
    }

    public Integer getGradientColor1() {
        return this.gridCellStyleData.getGradientColor1();
    }

    public void setGradientBackground(int color1, int color2, int dir) {
        this.gridCellStyleData.setGradientBackground(color1, color2, dir);
    }

    public void setGradientColor1(int gradientColor1) {
        this.gridCellStyleData.setGradientColor1(gradientColor1);
    }

    public Integer getGradientColor2() {
        return this.gridCellStyleData.getGradientColor2();
    }

    public void setGradientColor2(int gradientColor2) {
        this.gridCellStyleData.setGradientColor2(gradientColor2);
    }

    public Integer getGradientDirection() {
        return this.gridCellStyleData.getGradientDirection();
    }

    public void setGradientDirection(int gradientDirection) {
        this.gridCellStyleData.setGradientDirection(gradientDirection);
    }

    public int getBackgroundImageStyle() {
        return this.gridCellStyleData.getBackgroundImageStyle();
    }

    public void setBackgroundImageStyle(int style) {
        this.gridCellStyleData.setBackgroundImageStyle(style);
    }

    public int getHorizon() {
        return this.gridCellStyleData.getHorizon();
    }

    public int getVectical() {
        return this.gridCellStyleData.getVectical();
    }

    public int getRowSpan() {
        return this.gridCellStyleData.getRowSpan();
    }

    public void setRowSpan(int rowSpan) {
        this.gridCellStyleData.setRowSpan(rowSpan);
    }

    public int getColSpan() {
        return this.gridCellStyleData.getColSpan();
    }

    public void setColSpan(int colSpan) {
        this.gridCellStyleData.setColSpan(colSpan);
    }

    public ImageDescriptor getBackImage() {
        return this.gridCellStyleData.getBackImage();
    }

    public void setBackImage(ImageDescriptor backImage) {
        this.gridCellStyleData.setBackImage(backImage);
    }

    public int getBackGroundColor() {
        return this.gridCellStyleData.getBackGroundColor();
    }

    public void setBackGroundColor(int backGroundColor) {
        this.gridCellStyleData.setBackGroundColor(backGroundColor);
    }

    public int getForeGroundColor() {
        return this.gridCellStyleData.getForeGroundColor();
    }

    public void setForeGroundColor(int foreGroundColor) {
        this.gridCellStyleData.setForeGroundColor(foreGroundColor);
    }

    public int getBackGroundStyle() {
        return this.gridCellStyleData.getBackGroundStyle();
    }

    public void setBackGroundStyle(int backGroundStyle) {
        this.gridCellStyleData.setBackGroundStyle(backGroundStyle);
    }

    public String getFontName() {
        return this.gridCellStyleData.getFontName();
    }

    public void setFontName(String fontName) {
        this.gridCellStyleData.setFontName(fontName);
    }

    public int getFontSize() {
        return this.gridCellStyleData.getFontSize();
    }

    public void setFontSize(int fontSize) {
        this.gridCellStyleData.setFontSize(fontSize);
    }

    public int getFontStyle() {
        return this.gridCellStyleData.getFontStyle();
    }

    public void setFontStyle(int fontStyle) {
        this.gridCellStyleData.setFontStyle(fontStyle);
    }

    public Font getFont() {
        return this.gridCellStyleData.getFont();
    }

    public void setFont(Font font) {
        this.gridCellStyleData.setFont(font);
    }

    public int getRightBorderColor() {
        return this.gridCellStyleData.getRightBorderColor();
    }

    public void setRightBorderColor(int rightBorderColor) {
        this.gridCellStyleData.setRightBorderColor(rightBorderColor);
    }

    public int getRightBorderStyle() {
        return this.gridCellStyleData.getRightBorderStyle();
    }

    public void setRightBorderStyle(int rightBorderStyle) {
        this.gridCellStyleData.setRightBorderStyle(rightBorderStyle);
    }

    public int getBottomBorderColor() {
        return this.gridCellStyleData.getBottomBorderColor();
    }

    public void setBottomBorderColor(int bottomBorderColor) {
        this.gridCellStyleData.setBottomBorderColor(bottomBorderColor);
    }

    public int getBottomBorderStyle() {
        return this.gridCellStyleData.getBottomBorderStyle();
    }

    public void setBottomBorderStyle(int bottomBorderStyle) {
        this.gridCellStyleData.setBottomBorderStyle(bottomBorderStyle);
    }

    public int[] getBorderStyle() {
        return this.gridCellStyleData.getBorderStyle();
    }

    public int[] getBorderColor() {
        return this.gridCellStyleData.getBorderColor();
    }

    public int getDiagonalBorderColor() {
        return this.gridCellStyleData.getDiagonalBorderColor();
    }

    public void setDiagonalBorderColor(int diagonalBorderColor) {
        this.gridCellStyleData.setDiagonalBorderColor(diagonalBorderColor);
    }

    public int getDiagonalBorderStyle() {
        return this.gridCellStyleData.getDiagonalBorderStyle();
    }

    public void setDiagonalBorderStyle(int diagonalBorderStyle) {
        this.gridCellStyleData.setDiagonalBorderStyle(diagonalBorderStyle);
    }

    public int getInverseDiagonalBorderColor() {
        return this.gridCellStyleData.getInverseDiagonalBorderColor();
    }

    public void setInverseDiagonalBorderColor(int inverseDiagonalBorderColor) {
        this.gridCellStyleData.setInverseDiagonalBorderColor(inverseDiagonalBorderColor);
    }

    public int getInverseDiagonalBorderStyle() {
        return this.gridCellStyleData.getInverseDiagonalBorderStyle();
    }

    public void setInverseDiagonalBorderStyle(int inverseDiagonalBorderStyle) {
        this.gridCellStyleData.setInverseDiagonalBorderStyle(inverseDiagonalBorderStyle);
    }

    public boolean isSelectable() {
        return this.gridCellStyleData.isSelectable();
    }

    public void setSelectable(boolean selectable) {
        this.gridCellStyleData.setSelectable(selectable);
    }

    public boolean isEditable() {
        return this.gridCellStyleData.isEditable();
    }

    public void setEditable(boolean editable) {
        this.gridCellStyleData.setEditable(editable);
    }

    public boolean isWrapLine() {
        return this.gridCellStyleData.isWrapLine();
    }

    public void setWrapLine(boolean wrapLine) {
        this.gridCellStyleData.setWrapLine(wrapLine);
    }

    public int getIndent() {
        return this.gridCellStyleData.getIndent();
    }

    public void setIndent(int indent) {
        this.gridCellStyleData.setIndent(indent);
    }

    public int getVertAlign() {
        return this.gridCellStyleData.getVertAlign();
    }

    public void setVertAlign(int vertAlign) {
        this.gridCellStyleData.setVertAlign(vertAlign);
    }

    public int getHorzAlign() {
        return this.gridCellStyleData.getHorzAlign();
    }

    public void setHorzAlign(int horzAlign) {
        this.gridCellStyleData.setHorzAlign(horzAlign);
    }

    public boolean isVertText() {
        return this.gridCellStyleData.isVertText();
    }

    public void setVertText(boolean vertText) {
        this.gridCellStyleData.setVertText(vertText);
    }

    public boolean isSilverHead() {
        return this.gridCellStyleData.isSilverHead();
    }

    public void setSilverHead(boolean silverHead) {
        this.gridCellStyleData.setSilverHead(silverHead);
    }

    public boolean isMultiLine() {
        return this.gridCellStyleData.isMultiLine();
    }

    public void setMultiLine(boolean multiLine) {
        this.gridCellStyleData.setMultiLine(multiLine);
    }

    public boolean isFitFontSize() {
        return this.gridCellStyleData.isFitFontSize();
    }

    public void setFitFontSize(boolean fitFontSize) {
        this.gridCellStyleData.setFitFontSize(fitFontSize);
    }

    public void setDefault() {
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int hashCode() {
        return (String.valueOf(this.leftBorderStyle) + this.leftBorderColor + this.topBorderStyle + this.topBorderColor + this.gridCellStyleData.hashCode()).hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof NrGridCellStyleData) {
            NrGridCellStyleData temp = (NrGridCellStyleData)((Object)obj);
            return this.gridCellStyleData.equals(obj) && this.leftBorderColor == temp.leftBorderColor && this.leftBorderStyle == temp.leftBorderStyle && this.topBorderColor == temp.topBorderColor && this.topBorderStyle == temp.topBorderStyle;
        }
        return false;
    }

    public int getLeftBorderColor() {
        return this.leftBorderColor;
    }

    public void setLeftBorderColor(int leftBorderColor) {
        this.leftBorderColor = leftBorderColor;
    }

    public int getLeftBorderStyle() {
        return this.leftBorderStyle;
    }

    public void setLeftBorderStyle(int leftBorderStyle) {
        this.leftBorderStyle = leftBorderStyle;
    }

    public int getTopBorderColor() {
        return this.topBorderColor;
    }

    public void setTopBorderColor(int topBorderColor) {
        this.topBorderColor = topBorderColor;
    }

    public int getTopBorderStyle() {
        return this.topBorderStyle;
    }

    public void setTopBorderStyle(int topBorderStyle) {
        this.topBorderStyle = topBorderStyle;
    }
}

