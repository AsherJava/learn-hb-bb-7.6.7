/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 */
package com.jiuqi.va.query.print;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.va.query.print.BorderTypeFullEnum;
import com.jiuqi.va.query.print.FontProp;
import java.util.Objects;

public class GridCellProp {
    private int rowIndex;
    private int columnIndex;
    private int spanRow;
    private int spanColumn;
    private String expression;
    private IExpression compiledExpression;
    private String padding;
    private FontProp font;
    private boolean foldLine;
    private boolean underlined;
    private boolean removelined;
    private String textAlignment;
    private String backgroundColor;
    private String horizontalAlignment;
    private String verticalAlignment;
    private String rightBorderColor;
    private BorderTypeFullEnum rightBorderStyle;
    private String bottomBorderColor;
    private BorderTypeFullEnum bottomBorderStyle;
    private String leftBorderColor;
    private BorderTypeFullEnum leftBorderStyle;
    private String topBorderColor;
    private BorderTypeFullEnum topBorderStyle;

    public int getRowIndex() {
        return this.rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return this.columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getSpanRow() {
        return this.spanRow;
    }

    public void setSpanRow(int spanRow) {
        this.spanRow = spanRow;
    }

    public int getSpanColumn() {
        return this.spanColumn;
    }

    public void setSpanColumn(int spanColumn) {
        this.spanColumn = spanColumn;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public IExpression getCompiledExpression() {
        return this.compiledExpression;
    }

    public void setCompiledExpression(IExpression compiledExpression) {
        this.compiledExpression = compiledExpression;
    }

    public String getPadding() {
        return this.padding;
    }

    public void setPadding(String padding) {
        this.padding = padding;
    }

    public FontProp getFont() {
        return this.font;
    }

    public void setFont(FontProp font) {
        this.font = font;
    }

    public boolean isFoldLine() {
        return this.foldLine;
    }

    public boolean isUnderlined() {
        return this.underlined;
    }

    public boolean isRemovelined() {
        return this.removelined;
    }

    public String getTextAlignment() {
        if (Objects.isNull(this.textAlignment)) {
            return this.horizontalAlignment;
        }
        return this.textAlignment;
    }

    public void setFoldLine(boolean foldLine) {
        this.foldLine = foldLine;
    }

    public void setUnderlined(boolean underlined) {
        this.underlined = underlined;
    }

    public void setRemovelined(boolean removelined) {
        this.removelined = removelined;
    }

    public void setTextAlignment(String textAlignment) {
        this.textAlignment = textAlignment;
    }

    public String getHorizontalAlignment() {
        return this.horizontalAlignment;
    }

    public void setHorizontalAlignment(String horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }

    public String getVerticalAlignment() {
        return this.verticalAlignment;
    }

    public void setVerticalAlignment(String verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }

    public String getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getRightBorderColor() {
        return this.rightBorderColor;
    }

    public void setRightBorderColor(String rightBorderColor) {
        this.rightBorderColor = rightBorderColor;
    }

    public BorderTypeFullEnum getRightBorderStyle() {
        return this.rightBorderStyle;
    }

    public void setRightBorderStyle(BorderTypeFullEnum rightBorderStyle) {
        this.rightBorderStyle = rightBorderStyle;
    }

    public String getBottomBorderColor() {
        return this.bottomBorderColor;
    }

    public void setBottomBorderColor(String bottomBorderColor) {
        this.bottomBorderColor = bottomBorderColor;
    }

    public BorderTypeFullEnum getBottomBorderStyle() {
        return this.bottomBorderStyle;
    }

    public void setBottomBorderStyle(BorderTypeFullEnum bottomBorderStyle) {
        this.bottomBorderStyle = bottomBorderStyle;
    }

    public String getLeftBorderColor() {
        return this.leftBorderColor;
    }

    public void setLeftBorderColor(String leftBorderColor) {
        this.leftBorderColor = leftBorderColor;
    }

    public BorderTypeFullEnum getLeftBorderStyle() {
        return this.leftBorderStyle;
    }

    public void setLeftBorderStyle(BorderTypeFullEnum leftBorderStyle) {
        this.leftBorderStyle = leftBorderStyle;
    }

    public String getTopBorderColor() {
        return this.topBorderColor;
    }

    public void setTopBorderColor(String topBorderColor) {
        this.topBorderColor = topBorderColor;
    }

    public BorderTypeFullEnum getTopBorderStyle() {
        return this.topBorderStyle;
    }

    public void setTopBorderStyle(BorderTypeFullEnum topBorderStyle) {
        this.topBorderStyle = topBorderStyle;
    }
}

