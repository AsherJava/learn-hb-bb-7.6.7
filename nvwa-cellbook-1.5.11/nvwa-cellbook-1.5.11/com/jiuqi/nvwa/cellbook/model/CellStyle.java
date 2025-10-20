/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.model;

import com.jiuqi.nvwa.cellbook.constant.CellBorderStyle;
import com.jiuqi.nvwa.cellbook.constant.CellStyleModel;
import com.jiuqi.nvwa.cellbook.constant.FillPatternType;
import com.jiuqi.nvwa.cellbook.constant.HorizontalAlignment;
import com.jiuqi.nvwa.cellbook.constant.VerticalAlignment;
import com.jiuqi.nvwa.cellbook.model.CellBackGround;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.cellbook.model.CellBorders;
import com.jiuqi.nvwa.cellbook.model.CellFont;
import com.jiuqi.nvwa.cellbook.model.CellFormatter;
import com.jiuqi.nvwa.cellbook.model.CellLayout;
import java.io.Serializable;

public class CellStyle
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private CellStyleModel model = CellStyleModel.EDIT;
    private CellFormatter formatter;
    private CellBorders borders;
    private CellFont font;
    private CellBackGround backGround;
    private CellLayout layout;
    private CellBook _cellBook;

    public CellStyle(CellBook cellBook) {
        this._cellBook = cellBook;
    }

    protected CellStyle getDefaulteStyle() {
        return this._cellBook.getBookStyle().getDefaultStyle();
    }

    public String getFormatCode() {
        if (null == this.formatter) {
            return this.getDefaulteStyle().getFormatter().getFormatCode();
        }
        return this.formatter.getFormatCode();
    }

    public void setFormatCode(String formatCode) {
        if (null == this.formatter) {
            this.formatter = (CellFormatter)this.getDefaulteStyle().getFormatter().clone();
        }
        this.formatter.setFormatCode(formatCode);
    }

    public CellStyleModel getModel() {
        if (null == this.model) {
            return this.getDefaulteStyle().getModel();
        }
        return this.model;
    }

    public void setModel(CellStyleModel model) {
        this.model = model;
    }

    public String getFontName() {
        if (null == this.font) {
            return this.getDefaulteStyle().getFont().getName();
        }
        return this.font.getName();
    }

    public void setFontName(String fontName) {
        if (null == this.font) {
            this.font = (CellFont)this.getDefaulteStyle().getFont().clone();
        }
        this.font.setName(fontName);
    }

    @Deprecated
    public int getFontSize() {
        if (null == this.font) {
            return (int)this.getDefaulteStyle().getFont().getSizeF();
        }
        return (int)this.font.getSizeF();
    }

    public float getFontSizeF() {
        if (null == this.font) {
            return this.getDefaulteStyle().getFont().getSizeF();
        }
        return this.font.getSizeF();
    }

    @Deprecated
    public void setFontSize(int fontSize) {
        if (null == this.font) {
            this.font = (CellFont)this.getDefaulteStyle().getFont().clone();
        }
        this.font.setSizeF(fontSize);
    }

    public void setFontSize(float fontSize) {
        if (null == this.font) {
            this.font = (CellFont)this.getDefaulteStyle().getFont().clone();
        }
        this.font.setSizeF(fontSize);
    }

    public boolean isBold() {
        if (null == this.font) {
            return this.getDefaulteStyle().getFont().isBold();
        }
        return this.font.isBold();
    }

    public void setBold(boolean bold) {
        if (null == this.font) {
            this.font = (CellFont)this.getDefaulteStyle().getFont().clone();
        }
        this.font.setBold(bold);
    }

    public boolean isItalic() {
        if (null == this.font) {
            return this.getDefaulteStyle().getFont().isItalic();
        }
        return this.font.isItalic();
    }

    public void setItalic(boolean italic) {
        if (null == this.font) {
            this.font = (CellFont)this.getDefaulteStyle().getFont().clone();
        }
        this.font.setItalic(italic);
    }

    public boolean isUnderline() {
        if (null == this.font) {
            return this.getDefaulteStyle().getFont().isUnderline();
        }
        return this.font.isUnderline();
    }

    public void setUnderline(boolean underline) {
        if (null == this.font) {
            this.font = (CellFont)this.getDefaulteStyle().getFont().clone();
        }
        this.font.setUnderline(underline);
    }

    public boolean isInline() {
        if (null == this.font) {
            return this.getDefaulteStyle().getFont().isInline();
        }
        return this.font.isInline();
    }

    public void setInline(boolean inline) {
        if (null == this.font) {
            this.font = (CellFont)this.getDefaulteStyle().getFont().clone();
        }
        this.font.setInline(inline);
    }

    public int getFontColorIndex() {
        if (null == this.font) {
            return this.getDefaulteStyle().getFont().getColorIndex();
        }
        return this.font.getColorIndex();
    }

    public void setFontColorIndex(int fontColorIndex) {
        if (null == this.font) {
            this.font = (CellFont)this.getDefaulteStyle().getFont().clone();
        }
        this.font.setColorIndex(fontColorIndex);
    }

    public String getFontColor() {
        if (null == this.font) {
            return this.getDefaulteStyle().getFont().getColor();
        }
        return this.font.getColor();
    }

    public void setFontColor(String fontColor) {
        if (null == this.font) {
            this.font = (CellFont)this.getDefaulteStyle().getFont().clone();
        }
        this.font.setColor(fontColor);
    }

    public int getIndent() {
        if (null == this.layout) {
            return this.getDefaulteStyle().getLayout().getIndent();
        }
        return this.layout.getIndent();
    }

    public void setIndent(int indent) {
        if (null == this.layout) {
            this.layout = (CellLayout)this.getDefaulteStyle().getLayout().clone();
        }
        this.layout.setIndent(indent);
    }

    public boolean isFitFontSize() {
        if (null == this.layout) {
            return this.getDefaulteStyle().getLayout().isFitSize();
        }
        return this.layout.isFitSize();
    }

    public void setFitFontSize(boolean fitFontSize) {
        if (null == this.layout) {
            this.layout = (CellLayout)this.getDefaulteStyle().getLayout().clone();
        }
        this.layout.setFitSize(fitFontSize);
    }

    public boolean isWrapLine() {
        if (null == this.layout) {
            return this.getDefaulteStyle().getLayout().isWrapLine();
        }
        return this.layout.isWrapLine();
    }

    public void setWrapLine(boolean wrapLine) {
        if (null == this.layout) {
            this.layout = (CellLayout)this.getDefaulteStyle().getLayout().clone();
        }
        this.layout.setWrapLine(wrapLine);
    }

    public HorizontalAlignment getHorizontalAlignment() {
        if (null == this.layout) {
            return this.getDefaulteStyle().getLayout().getHorizontalAlignment();
        }
        return this.layout.getHorizontalAlignment();
    }

    public void setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
        if (null == this.layout) {
            this.layout = (CellLayout)this.getDefaulteStyle().getLayout().clone();
        }
        this.layout.setHorizontalAlignment(horizontalAlignment);
    }

    public VerticalAlignment getVerticalAlignment() {
        if (null == this.layout) {
            return this.getDefaulteStyle().getLayout().getVerticalAlignment();
        }
        return this.layout.getVerticalAlignment();
    }

    public void setVerticalAlignment(VerticalAlignment verticalAlignment) {
        if (null == this.layout) {
            this.layout = (CellLayout)this.getDefaulteStyle().getLayout().clone();
        }
        this.layout.setVerticalAlignment(verticalAlignment);
    }

    public void setTextRotation(int textRotation) {
        if (null == this.layout) {
            this.layout = (CellLayout)this.getDefaulteStyle().getLayout().clone();
        }
        this.layout.setTextRotation(textRotation);
    }

    public int getTextRotation() {
        if (null == this.layout) {
            return this.getDefaulteStyle().getLayout().getTextRotation();
        }
        return this.layout.getTextRotation();
    }

    public int getRightBorderColorIndex() {
        if (null == this.borders) {
            return this.getDefaulteStyle().getBorders().getRight().getColorIndex();
        }
        return this.borders.getRight().getColorIndex();
    }

    public void setRightBorderColorIndex(int rightBorderColorIndex) {
        if (null == this.borders) {
            this.borders = (CellBorders)this.getDefaulteStyle().getBorders().clone();
        }
        this.borders.getRight().setColorIndex(rightBorderColorIndex);
    }

    public String getRightBorderColor() {
        if (null == this.borders) {
            return this.getDefaulteStyle().getBorders().getRight().getColor();
        }
        return this.borders.getRight().getColor();
    }

    public void setRightBorderColor(String rightBorderColor) {
        if (null == this.borders) {
            this.borders = (CellBorders)this.getDefaulteStyle().getBorders().clone();
        }
        this.borders.getRight().setColor(rightBorderColor);
    }

    public CellBorderStyle getRightBorderStyle() {
        if (null == this.borders) {
            return this.getDefaulteStyle().getBorders().getRight().getStyle();
        }
        return this.borders.getRight().getStyle();
    }

    public void setRightBorderStyle(CellBorderStyle rightBorderStyle) {
        if (null == this.borders) {
            this.borders = (CellBorders)this.getDefaulteStyle().getBorders().clone();
        }
        this.borders.getRight().setStyle(rightBorderStyle);
    }

    public int getBottomBorderColorIndex() {
        if (null == this.borders) {
            return this.getDefaulteStyle().getBorders().getBottom().getColorIndex();
        }
        return this.borders.getBottom().getColorIndex();
    }

    public void setBottomBorderColorIndex(int bottomBorderColorIndex) {
        if (null == this.borders) {
            this.borders = (CellBorders)this.getDefaulteStyle().getBorders().clone();
        }
        this.borders.getBottom().setColorIndex(bottomBorderColorIndex);
    }

    public String getBottomBorderColor() {
        if (null == this.borders) {
            return this.getDefaulteStyle().getBorders().getBottom().getColor();
        }
        return this.borders.getBottom().getColor();
    }

    public void setBottomBorderColor(String bottomBorderColor) {
        if (null == this.borders) {
            this.borders = (CellBorders)this.getDefaulteStyle().getBorders().clone();
        }
        this.borders.getBottom().setColor(bottomBorderColor);
    }

    public CellBorderStyle getBottomBorderStyle() {
        if (null == this.borders) {
            return this.getDefaulteStyle().getBorders().getBottom().getStyle();
        }
        return this.borders.getBottom().getStyle();
    }

    public void setBottomBorderStyle(CellBorderStyle bottomBorderStyle) {
        if (null == this.borders) {
            this.borders = (CellBorders)this.getDefaulteStyle().getBorders().clone();
        }
        this.borders.getBottom().setStyle(bottomBorderStyle);
    }

    public int getLeftBorderColorIndex() {
        if (null == this.borders) {
            return this.getDefaulteStyle().getBorders().getLeft().getColorIndex();
        }
        return this.borders.getLeft().getColorIndex();
    }

    public void setLeftBorderColorIndex(int leftBorderColorIndex) {
        if (null == this.borders) {
            this.borders = (CellBorders)this.getDefaulteStyle().getBorders().clone();
        }
        this.borders.getLeft().setColorIndex(leftBorderColorIndex);
    }

    public String getLeftBorderColor() {
        if (null == this.borders) {
            return this.getDefaulteStyle().getBorders().getLeft().getColor();
        }
        return this.borders.getLeft().getColor();
    }

    public void setLeftBorderColor(String leftBorderColor) {
        if (null == this.borders) {
            this.borders = (CellBorders)this.getDefaulteStyle().getBorders().clone();
        }
        this.borders.getLeft().setColor(leftBorderColor);
    }

    public CellBorderStyle getLeftBorderStyle() {
        if (null == this.borders) {
            return this.getDefaulteStyle().getBorders().getLeft().getStyle();
        }
        return this.borders.getLeft().getStyle();
    }

    public void setLeftBorderStyle(CellBorderStyle leftBorderStyle) {
        if (null == this.borders) {
            this.borders = (CellBorders)this.getDefaulteStyle().getBorders().clone();
        }
        this.borders.getLeft().setStyle(leftBorderStyle);
    }

    public int getTopBorderColorIndex() {
        if (null == this.borders) {
            return this.getDefaulteStyle().getBorders().getTop().getColorIndex();
        }
        return this.borders.getTop().getColorIndex();
    }

    public void setTopBorderColorIndex(int topBorderColorIndex) {
        if (null == this.borders) {
            this.borders = (CellBorders)this.getDefaulteStyle().getBorders().clone();
        }
        this.borders.getTop().setColorIndex(topBorderColorIndex);
    }

    public String getTopBorderColor() {
        if (null == this.borders) {
            return this.getDefaulteStyle().getBorders().getTop().getColor();
        }
        return this.borders.getTop().getColor();
    }

    public void setTopBorderColor(String topBorderColor) {
        if (null == this.borders) {
            this.borders = (CellBorders)this.getDefaulteStyle().getBorders().clone();
        }
        this.borders.getTop().setColor(topBorderColor);
    }

    public CellBorderStyle getTopBorderStyle() {
        if (null == this.borders) {
            return this.getDefaulteStyle().getBorders().getTop().getStyle();
        }
        return this.borders.getTop().getStyle();
    }

    public void setTopBorderStyle(CellBorderStyle topBorderStyle) {
        if (null == this.borders) {
            this.borders = (CellBorders)this.getDefaulteStyle().getBorders().clone();
        }
        this.borders.getTop().setStyle(topBorderStyle);
    }

    public int getDiagonalUpColorIndex() {
        if (null == this.borders) {
            return this.getDefaulteStyle().getBorders().getDiagonalUp().getColorIndex();
        }
        return this.borders.getDiagonalUp().getColorIndex();
    }

    public void setDiagonalUpColorIndex(int diagonalUpColorIndex) {
        if (null == this.borders) {
            this.borders = (CellBorders)this.getDefaulteStyle().getBorders().clone();
        }
        this.borders.getDiagonalUp().setColorIndex(diagonalUpColorIndex);
    }

    public String getDiagonalUpColor() {
        if (null == this.borders) {
            return this.getDefaulteStyle().getBorders().getDiagonalUp().getColor();
        }
        return this.borders.getDiagonalUp().getColor();
    }

    public void setDiagonalUpColor(String diagonalUpColor) {
        if (null == this.borders) {
            this.borders = (CellBorders)this.getDefaulteStyle().getBorders().clone();
        }
        this.borders.getDiagonalUp().setColor(diagonalUpColor);
    }

    public CellBorderStyle getDiagonalUpStyle() {
        if (null == this.borders) {
            return this.getDefaulteStyle().getBorders().getDiagonalUp().getStyle();
        }
        return this.borders.getDiagonalUp().getStyle();
    }

    public void setDiagonalUpStyle(CellBorderStyle diagonalUpStyle) {
        if (null == this.borders) {
            this.borders = (CellBorders)this.getDefaulteStyle().getBorders().clone();
        }
        this.borders.getDiagonalUp().setStyle(diagonalUpStyle);
    }

    public int getDiagonalDownColorIndex() {
        if (null == this.borders) {
            return this.getDefaulteStyle().getBorders().getDiagonalDown().getColorIndex();
        }
        return this.borders.getDiagonalDown().getColorIndex();
    }

    public void setDiagonalDownColorIndex(int diagonalDownColorIndex) {
        if (null == this.borders) {
            this.borders = (CellBorders)this.getDefaulteStyle().getBorders().clone();
        }
        this.borders.getDiagonalDown().setColorIndex(diagonalDownColorIndex);
    }

    public String getDiagonalDownColor() {
        if (null == this.borders) {
            return this.getDefaulteStyle().getBorders().getDiagonalDown().getColor();
        }
        return this.borders.getDiagonalDown().getColor();
    }

    public void setDiagonalDownColor(String diagonalDownColor) {
        if (null == this.borders) {
            this.borders = (CellBorders)this.getDefaulteStyle().getBorders().clone();
        }
        this.borders.getDiagonalDown().setColor(diagonalDownColor);
    }

    public CellBorderStyle getDiagonalDownStyle() {
        if (null == this.borders) {
            return this.getDefaulteStyle().getBorders().getDiagonalDown().getStyle();
        }
        return this.borders.getDiagonalDown().getStyle();
    }

    public void setDiagonalDownStyle(CellBorderStyle diagonalDownStyle) {
        if (null == this.borders) {
            this.borders = (CellBorders)this.getDefaulteStyle().getBorders().clone();
        }
        this.borders.getDiagonalDown().setStyle(diagonalDownStyle);
    }

    public FillPatternType getFillPatternType() {
        if (null == this.backGround) {
            return this.getDefaulteStyle().getBackGround().getFillPatternType();
        }
        return this.backGround.getFillPatternType();
    }

    public void setFillPatternType(FillPatternType fillPatternType) {
        if (null == this.backGround) {
            this.backGround = (CellBackGround)this.getDefaulteStyle().getBackGround().clone();
        }
        this.backGround.setFillPatternType(fillPatternType);
    }

    public int getBackGroundColorIndex() {
        if (null == this.backGround) {
            return this.getDefaulteStyle().getBackGround().getColorIndex();
        }
        return this.backGround.getColorIndex();
    }

    public void setBackGroundColorIndex(int backGroundColorIndex) {
        if (null == this.backGround) {
            this.backGround = (CellBackGround)this.getDefaulteStyle().getBackGround().clone();
        }
        this.backGround.setColorIndex(backGroundColorIndex);
    }

    public String getBackGroundColor() {
        if (null == this.backGround) {
            return this.getDefaulteStyle().getBackGround().getColor();
        }
        return this.backGround.getColor();
    }

    public void setBackGroundColor(String backGroundColor) {
        if (null == this.backGround) {
            this.backGround = (CellBackGround)this.getDefaulteStyle().getBackGround().clone();
        }
        this.backGround.setColor(backGroundColor);
    }

    public String getBackGroundImg() {
        if (null == this.backGround) {
            return this.getDefaulteStyle().getBackGround().getBackGroundImg();
        }
        return this.backGround.getBackGroundImg();
    }

    public void setBackGroundImg(String backGroundImg) {
        if (null == this.backGround) {
            this.backGround = (CellBackGround)this.getDefaulteStyle().getBackGround().clone();
        }
        this.backGround.setBackGroundImg(backGroundImg);
    }

    public CellFormatter getFormatter() {
        return this.formatter;
    }

    public CellBorders getBorders() {
        return this.borders;
    }

    public CellFont getFont() {
        return this.font;
    }

    public CellBackGround getBackGround() {
        return this.backGround;
    }

    public Object clone() {
        CellStyle cellStyle = new CellStyle(this._cellBook);
        cellStyle.backGround = null != this.backGround ? (CellBackGround)this.backGround.clone() : null;
        cellStyle.borders = null != this.borders ? (CellBorders)this.borders.clone() : null;
        cellStyle.font = null != this.font ? (CellFont)this.font.clone() : null;
        cellStyle.formatter = null != this.formatter ? (CellFormatter)this.formatter.clone() : null;
        cellStyle.layout = null != this.layout ? (CellLayout)this.layout.clone() : null;
        cellStyle.model = this.model;
        return cellStyle;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.backGround == null ? 0 : this.backGround.hashCode());
        result = 31 * result + (this.borders == null ? 0 : this.borders.hashCode());
        result = 31 * result + (this.font == null ? 0 : this.font.hashCode());
        result = 31 * result + (this.formatter == null ? 0 : this.formatter.hashCode());
        result = 31 * result + (this.layout == null ? 0 : this.layout.hashCode());
        result = 31 * result + (this.model == null ? 0 : this.model.hashCode());
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
        CellStyle other = (CellStyle)obj;
        if (this.backGround == null ? other.backGround != null : !this.backGround.equals(other.backGround)) {
            return false;
        }
        if (this.borders == null ? other.borders != null : !this.borders.equals(other.borders)) {
            return false;
        }
        if (this.font == null ? other.font != null : !this.font.equals(other.font)) {
            return false;
        }
        if (this.formatter == null ? other.formatter != null : !this.formatter.equals(other.formatter)) {
            return false;
        }
        if (this.layout == null ? other.layout != null : !this.layout.equals(other.layout)) {
            return false;
        }
        return this.model == other.model;
    }

    public CellLayout getLayout() {
        return this.layout;
    }

    public void setFormatter(CellFormatter formatter) {
        this.formatter = formatter;
    }

    public void setBorders(CellBorders borders) {
        this.borders = borders;
    }

    public void setFont(CellFont font) {
        this.font = font;
    }

    public void setBackGround(CellBackGround backGround) {
        this.backGround = backGround;
    }

    public void setLayout(CellLayout layout) {
        this.layout = layout;
    }

    public CellBook get_cellBook() {
        return this._cellBook;
    }

    public void set_cellBook(CellBook _cellBook) {
        this._cellBook = _cellBook;
    }
}

