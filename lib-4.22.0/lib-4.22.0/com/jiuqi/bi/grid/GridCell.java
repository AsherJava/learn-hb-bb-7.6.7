/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.JqLib
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.bi.grid;

import com.jiuqi.bi.grid.BooleanCellProperty;
import com.jiuqi.bi.grid.CellBuffer;
import com.jiuqi.bi.grid.CellDataProperty;
import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.CurrencyCellProperty;
import com.jiuqi.bi.grid.CurrencyCellPropertyIntf;
import com.jiuqi.bi.grid.CustomCellProperty;
import com.jiuqi.bi.grid.DateCellProperty;
import com.jiuqi.bi.grid.GridColor;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.GridError;
import com.jiuqi.bi.grid.IntList;
import com.jiuqi.bi.grid.NumberCellProperty;
import com.jiuqi.bi.grid.NumberCellPropertyIntf;
import com.jiuqi.bi.grid.TextCellProperty;
import com.jiuqi.bi.grid.TextCellPropertyIntf;
import com.jiuqi.bi.util.JqLib;
import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class GridCell
implements Serializable {
    private static final long serialVersionUID = 7437735317988283343L;
    private int col;
    private int row;
    private CellBuffer buffer;
    protected GridData grid;
    static final int POS_REDGE = 13;
    static final int POS_BEDGE = 16;
    static final int LEN_EDGE = 3;

    public GridCell() {
        this.buffer = new CellBuffer(32);
    }

    public GridCell(GridCell cell) {
        this.col = cell.col;
        this.row = cell.row;
        this.buffer = cell.buffer.copyOf(32);
        this.grid = cell.grid;
    }

    static int toColorIndex(int color, IntList palette) {
        int index = palette.indexOf(color);
        if (index < 0) {
            index = palette.count() < 65535 ? palette.add(color) : 0;
        }
        return index;
    }

    int toFontNameIndex(String fontName) {
        int index = this.grid.fontNames().indexOfFont(fontName);
        if (index == -1) {
            index = this.grid.fontNames().count() < 255 ? this.grid.fontNames().addFont(fontName) : 0;
        }
        return index;
    }

    protected void dataChanged() {
    }

    public final void init(GridData g, int aCol, int aRow) {
        this.init(g, aCol, aRow, g.getCellPropData(aCol, aRow));
    }

    public final void init(GridData g, int aCol, int aRow, byte[] celldata) {
        this.buffer.fill(celldata);
        this.grid = g;
        this.col = aCol;
        this.row = aRow;
        if (aCol <= 0 || aRow <= 0) {
            return;
        }
        CellField range = g.getCellField(aCol, aRow);
        if (range != null) {
            if (range.right > range.left) {
                byte[] rData = g.internalGetCellPropData(range.right, aRow);
                GridCell.copyREdge(rData, this.buffer.getBuffer());
            }
            if (range.bottom > range.top) {
                byte[] bData = g.internalGetCellPropData(aCol, range.bottom);
                GridCell.copyBEdge(bData, this.buffer.getBuffer());
            }
        }
    }

    static void copyREdge(byte[] src, byte[] dest) {
        System.arraycopy(src, 13, dest, 13, 3);
    }

    static void copyBEdge(byte[] src, byte[] dest) {
        System.arraycopy(src, 16, dest, 16, 3);
    }

    byte[] snapshotEdges() {
        byte[] data = new byte[6];
        System.arraycopy(this.buffer.getBuffer(), 13, data, 0, data.length);
        return data;
    }

    void restoreEdges(byte[] data) {
        System.arraycopy(data, 0, this.buffer.getBuffer(), 13, 6);
    }

    public void initRaw(GridData g, int aCol, int aRow) {
        this.internalInit(g, aCol, aRow, g.internalGetCellPropData(aCol, aRow));
    }

    final void internalInit(GridData g, int aCol, int aRow, byte[] celldata) {
        this.buffer.fill(celldata);
        this.grid = g;
        this.col = aCol;
        this.row = aRow;
    }

    final CellBuffer getPropBuffer() {
        return this.buffer;
    }

    public final byte[] getPropData() {
        return this.buffer.getBuffer();
    }

    final void setPropData(byte[] data) {
        this.buffer.setBuffer(data);
    }

    public final int getBackColor() {
        int index = this.getBackColorIndex();
        return this.grid.cellColors().get(index);
    }

    final int getBackColorIndex() {
        return this.buffer.getUnsignedShort(0);
    }

    public final void setBackColor(int value) {
        int index = GridCell.toColorIndex(value, this.grid.cellColors());
        this.setBackColorIndex(index);
    }

    final void setBackColorIndex(int index) {
        this.buffer.setUnsighedShort(0, index);
        this.dataChanged();
    }

    public final int getBackAlpha() {
        return this.buffer.getByte(26);
    }

    public final void setBackAlpha(int value) {
        if (value < 0) {
            value = 0;
        } else if (value > 100) {
            value = 100;
        }
        this.buffer.setByte(26, value);
    }

    public final int getBackStyle() {
        return this.buffer.getByte(2);
    }

    public final void setBackStyle(int value) {
        this.buffer.setByte(2, value);
        if (value < 28) {
            this.setSilverHead(false);
        }
        this.dataChanged();
    }

    public final boolean getBackLines(int cellBackStyle) {
        if (cellBackStyle >= 0 && cellBackStyle < 8) {
            return this.buffer.getBit(3, cellBackStyle);
        }
        return false;
    }

    public final void setBackLines(int cellBackStyle, boolean value) {
        if (cellBackStyle >= 0 && cellBackStyle < 8) {
            this.buffer.setBit(3, cellBackStyle, value);
            this.dataChanged();
        }
    }

    public final int getFontColor() {
        int index = this.getFontColorIndex();
        return this.grid.fontColors().get(index);
    }

    final int getFontColorIndex() {
        return this.buffer.getUnsignedShort(4);
    }

    public final void setFontColor(int value) {
        int index = GridCell.toColorIndex(value, this.grid.fontColors());
        this.setFontColorIndex(index);
    }

    final void setFontColorIndex(int index) {
        this.buffer.setUnsighedShort(4, index);
        this.dataChanged();
    }

    public final String getFontName() {
        int index = this.getFontNameIndex();
        return this.grid.fontNames().getFont(index);
    }

    final int getFontNameIndex() {
        return this.buffer.getByte(6);
    }

    public final void setFontName(String value) {
        int index = this.toFontNameIndex(value);
        this.setFontNameIndex(index);
    }

    final void setFontNameIndex(int index) {
        this.buffer.setByte(6, index);
        this.dataChanged();
    }

    public final int getFontSize() {
        int d = this.buffer.getByte(7);
        if (d < 50) {
            return this.buffer.getByte(8);
        }
        return this.buffer.getByte(8) + 1;
    }

    public final float getFontSizeF() {
        int i = this.buffer.getByte(8);
        int d = this.buffer.getByte(7);
        return (float)i + (float)d / 100.0f;
    }

    public final void setFontSize(int value) {
        this.buffer.setByte(7, 0);
        this.buffer.setByte(8, value);
        this.dataChanged();
    }

    public final void setFontSizeF(float value) {
        int i = (int)value;
        int d = Math.round((value - (float)i) * 100.0f);
        this.buffer.setByte(7, d);
        this.buffer.setByte(8, i);
        this.dataChanged();
    }

    public final boolean getFontBold() {
        return this.buffer.getBit(9, 0);
    }

    public final void setFontBold(boolean value) {
        this.buffer.setBit(9, 0, value);
        this.dataChanged();
    }

    public final boolean getFontItalic() {
        return this.buffer.getBit(9, 1);
    }

    public final void setFontItalic(boolean value) {
        this.buffer.setBit(9, 1, value);
        this.dataChanged();
    }

    public final boolean getFontUnderLine() {
        return this.buffer.getBit(9, 2);
    }

    public final void setFontUnderLine(boolean value) {
        this.buffer.setBit(9, 2, value);
        this.dataChanged();
    }

    public final boolean getFontStrikeOut() {
        return this.buffer.getBit(9, 3);
    }

    public final void setFontStrikeOut(boolean value) {
        this.buffer.setBit(9, 3, value);
        this.dataChanged();
    }

    public final boolean getFitFontSize() {
        return this.buffer.getBit(9, 4);
    }

    public final void setFitFontSize(boolean value) {
        this.buffer.setBit(9, 4, value);
        this.dataChanged();
    }

    public final boolean getWrapLine() {
        return this.buffer.getBit(9, 5);
    }

    public final void setWrapLine(boolean value) {
        this.buffer.setBit(9, 5, value);
        this.dataChanged();
    }

    public final boolean getMultiLine() {
        return this.buffer.getBit(9, 6);
    }

    public final void setMultiLine(boolean value) {
        this.buffer.setBit(9, 6, value);
        this.dataChanged();
    }

    public final boolean getVertText() {
        return this.buffer.getBit(9, 7);
    }

    public final void setVertText(boolean value) {
        this.buffer.setBit(9, 7, value);
        this.dataChanged();
    }

    public final int getHorzAlign() {
        return this.buffer.getLow(10);
    }

    public final void setHorzAlign(int value) {
        this.buffer.setLow(10, value);
        this.dataChanged();
    }

    public final int getVertAlign() {
        return this.buffer.getHigh(10);
    }

    public final void setVertAlign(int value) {
        this.buffer.setHigh(10, value);
        this.dataChanged();
    }

    public final int getIndent() {
        return this.buffer.getByte(11);
    }

    public final void setIndent(int value) {
        this.buffer.setByte(11, value);
        this.dataChanged();
    }

    public final boolean getSilverHead() {
        return this.buffer.getBit(12, 0);
    }

    public void setSilverHead(boolean value) {
        if (this.getSilverHead() == value) {
            return;
        }
        this.buffer.setBit(12, 0, value);
        if (value) {
            this.setBackColor(0xF2F2F2);
            this.setBackStyle(28);
        } else {
            this.setBackColor(0xFFFFFF);
            this.setBackStyle(0);
        }
        this.dataChanged();
    }

    public final boolean getEmptyCell() {
        return this.buffer.getBit(12, 1);
    }

    public final void setEmptyCell(boolean value) {
        this.buffer.setBit(12, 1, value);
        this.dataChanged();
    }

    public final boolean getCanModify() {
        return this.buffer.getBit(12, 2);
    }

    public final void setCanModify(boolean value) {
        this.buffer.setBit(12, 2, value);
        this.dataChanged();
    }

    public final boolean getCanInput() {
        return this.buffer.getBit(12, 3);
    }

    public final void setCanInput(boolean value) {
        this.buffer.setBit(12, 3, value);
        this.dataChanged();
    }

    public final boolean getCanSelect() {
        return this.buffer.getBit(12, 4);
    }

    public final void setCanSelect(boolean value) {
        this.buffer.setBit(12, 4, value);
        this.dataChanged();
    }

    public final boolean getCanRead() {
        return this.buffer.getBit(12, 5);
    }

    public final void setCanRead(boolean value) {
        this.buffer.setBit(12, 5, value);
        this.dataChanged();
    }

    public final boolean getCanWrite() {
        return this.buffer.getBit(12, 6);
    }

    public final void setCanWrite(boolean value) {
        this.buffer.setBit(12, 6, value);
        this.dataChanged();
    }

    public final boolean getEditJumpNext() {
        return this.buffer.getBit(12, 7);
    }

    public final void setEditJumpNext(boolean value) {
        this.buffer.setBit(12, 7, value);
        this.dataChanged();
    }

    public final int getREdgeStyle() {
        return this.buffer.getByte(13);
    }

    public final void setREdgeStyle(int value) {
        this.buffer.setByte(13, value);
        this.dataChanged();
    }

    public final int getREdgeColor() {
        int index = this.getREdgeColorIndex();
        return this.grid.edgeColors().get(index);
    }

    final int getREdgeColorIndex() {
        return this.buffer.getUnsignedShort(14);
    }

    public final void setREdgeColor(int value) {
        int index = GridCell.toColorIndex(value, this.grid.edgeColors());
        this.setREdgeColorIndex(index);
    }

    final void setREdgeColorIndex(int index) {
        this.buffer.setUnsighedShort(14, index);
        this.dataChanged();
    }

    public final int getBEdgeStyle() {
        return this.buffer.getByte(16);
    }

    public final void setBEdgeStyle(int value) {
        this.buffer.setByte(16, value);
        this.dataChanged();
    }

    public final int getBEdgeColor() {
        int index = this.getBEdgeColorIndex();
        return this.grid.edgeColors().get(index);
    }

    final int getBEdgeColorIndex() {
        return this.buffer.getUnsignedShort(17);
    }

    public final void setBEdgeColor(int value) {
        int index = GridCell.toColorIndex(value, this.grid.edgeColors());
        this.setBEdgeColorIndex(index);
    }

    final void setBEdgeColorIndex(int index) {
        this.buffer.setUnsighedShort(17, index);
        this.dataChanged();
    }

    @Deprecated
    public final int getDataType() {
        return this.isHyperlink() ? 9 : this.buffer.getByte(19);
    }

    public final void setDataType(int value) {
        if (value == 9) {
            this.buffer.setByte(19, 0);
            this.setHyperlink(true);
        } else {
            this.buffer.setByte(19, value);
            this.setHyperlink(false);
        }
        this.dataChanged();
    }

    public final int getType() {
        return this.buffer.getByte(19);
    }

    public final void setType(int value) {
        if (value == 9) {
            throw new IllegalArgumentException("\u8be5\u65b9\u6cd5\u4e0d\u518d\u652f\u6301\u8bbe\u7f6e\u4e3a\u8d85\u94fe\u63a5\u7c7b\u578b\u5355\u5143\u683c");
        }
        this.buffer.setByte(19, value);
        this.dataChanged();
    }

    public final boolean isHyperlink() {
        return this.buffer.getBit(3, 4);
    }

    public final void setHyperlink(boolean value) {
        this.buffer.setBit(3, 4, value);
        this.dataChanged();
    }

    public final int getDataFlag() {
        return this.buffer.getByte(20);
    }

    public final void setDataFlag(int value) {
        this.buffer.setByte(20, value);
        this.dataChanged();
    }

    public final int getDataFormat() {
        return this.buffer.getByte(21);
    }

    public final void setDataFormat(int value) {
        this.buffer.setByte(21, value);
        this.dataChanged();
    }

    public final int getDataProperty() {
        return this.buffer.getUnsignedShort(22);
    }

    public final void setDataProperty(int value) {
        this.buffer.setUnsighedShort(22, value);
        this.dataChanged();
    }

    public final int getEditMode() {
        return this.buffer.getByte(24);
    }

    public final void setEditMode(int value) {
        this.buffer.setByte(24, value);
        this.dataChanged();
    }

    public final int getImeMode() {
        return this.buffer.getLow(25);
    }

    public final void setImeMode(int value) {
        this.buffer.setLow(25, value);
        this.dataChanged();
    }

    public final int getOutputKind() {
        return this.buffer.getHigh(25);
    }

    public final void setOutputKind(int value) {
        this.buffer.setHigh(25, value);
        this.dataChanged();
    }

    public final String getEditMask() {
        return null;
    }

    public final int getFontHeight() {
        return Math.round(this.getFontSizeF() * 96.0f / 72.0f);
    }

    public final boolean getFontStyle(int fontStyle) {
        switch (fontStyle) {
            case 0: {
                return this.getFontBold();
            }
            case 1: {
                return this.getFontItalic();
            }
            case 3: {
                return this.getFontStrikeOut();
            }
            case 2: {
                return this.getFontUnderLine();
            }
        }
        return false;
    }

    public final int getLEdgeColor() {
        return this.col == 0 ? 0 : this.grid.getCellEx(this.col - 1, this.row).getREdgeColor();
    }

    public final int getLEdgeStyle() {
        return this.col == 0 ? 0 : this.grid.getCellEx(this.col - 1, this.row).getREdgeStyle();
    }

    public final boolean getReadOnly() {
        return !this.getCanModify() || !this.getCanWrite();
    }

    public final String getShowFormat() {
        int index = this.getShowFormatIndex();
        return index <= 0 ? null : this.grid.cellFormats().get(index);
    }

    final int getShowFormatIndex() {
        return this.buffer.getByte(27);
    }

    public static boolean getSilverHead(byte[] data) {
        return new CellBuffer(data).getBit(12, 0);
    }

    public final int getTEdgeColor() {
        return this.row == 0 ? 0 : this.grid.getCellEx(this.col, this.row - 1).getBEdgeColor();
    }

    public final int getTEdgeStyle() {
        return this.row == 0 ? 0 : this.grid.getCellEx(this.col, this.row - 1).getBEdgeStyle();
    }

    public final boolean getBoolean() {
        return JqLib.isBoolTrue((String)this.getContent());
    }

    public final Date getDate() {
        String text = this.getContent();
        return DateCellProperty.tryParseDate(text);
    }

    public final Date getDateTime() {
        return this.getDate();
    }

    public final double getFloat() {
        String strData = this.getContent();
        if (strData == null) {
            return 0.0;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < strData.length(); ++i) {
            char ch = strData.charAt(i);
            if (Character.isDigit(ch) || ch == '.' || ch == '-' || ch == 'E' || ch == 'e') {
                buffer.append(ch);
                continue;
            }
            if (ch == ',') continue;
            buffer.append(' ');
        }
        String str = buffer.toString().trim();
        if (str.length() == 0) {
            return 0.0;
        }
        try {
            return Double.parseDouble(str);
        }
        catch (Exception e) {
            return 0.0;
        }
    }

    public final int getInteger() {
        String str;
        String strData = this.getContent();
        if (strData == null) {
            return 0;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < strData.length(); ++i) {
            char ch = strData.charAt(i);
            if (Character.isDigit(ch) || ch == '-') {
                buffer.append(ch);
                continue;
            }
            if (ch == '.') break;
        }
        if ((str = buffer.toString().trim()).length() == 0) {
            return 0;
        }
        try {
            return Integer.parseInt(str);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public final String getString() {
        return this.getContent();
    }

    @Deprecated
    public final double getTime() {
        return this.getFloat();
    }

    public final int getCellBottom() {
        CellField cf = this.grid.expandCell(this.col, this.row);
        return cf.bottom;
    }

    public final String getCellData() {
        return this.grid.getCellData(this.col, this.row);
    }

    public final String getContent() {
        String data = this.grid.getCellData(this.col, this.row);
        if (data == null || data.isEmpty()) {
            return data;
        }
        if (this.isHyperlink()) {
            String[] infos = GridCell.parserLinkInformation(data);
            return infos.length >= 4 ? infos[3] : infos[0];
        }
        return data;
    }

    public final Object getCellObj() {
        return this.grid.getObj(this.col, this.row);
    }

    public final CellField getCellField() {
        return this.grid.expandCell(this.col, this.row);
    }

    public final int getCellHeight() {
        CellField cf = this.grid.expandCell(this.col, this.row);
        int rt = 0;
        for (int i = cf.top; i <= cf.bottom; ++i) {
            rt += this.grid.getRowHeights(i);
        }
        return rt;
    }

    public final int getCellLeft() {
        return this.grid.expandCell((int)this.col, (int)this.row).left;
    }

    public final int getCellRight() {
        return this.grid.expandCell((int)this.col, (int)this.row).right;
    }

    public final int getCellTop() {
        return this.grid.expandCell((int)this.col, (int)this.row).top;
    }

    public final int getCellWidth() {
        CellField cf = this.grid.expandCell(this.col, this.row);
        int rt = 0;
        for (int i = cf.left; i <= cf.right; ++i) {
            rt += this.grid.getColWidths(i);
        }
        return rt;
    }

    public final int getColNum() {
        return this.col;
    }

    public final int getColSpan() {
        CellField cf = this.grid.expandCell(this.col, this.row);
        return cf.left == this.col && cf.top == this.row ? cf.right - cf.left + 1 : 0;
    }

    public final int getColWidth() {
        return this.grid.getColWidths(this.col);
    }

    public final boolean getHasConjection() {
        CellField cf = this.grid.expandCell(this.col, this.row);
        return cf.left != cf.right || cf.top != cf.bottom;
    }

    public final boolean getIsNull() {
        return this.grid.getCellData(this.col, this.row) == null;
    }

    public final boolean getIsZero() {
        return this.getIsNull() || this.getInteger() == 0;
    }

    public final int getRowHeight() {
        return this.grid.getRowHeights(this.row);
    }

    public final int getRowNum() {
        return this.row;
    }

    public final int getRowSpan() {
        CellField cf = this.grid.expandCell(this.col, this.row);
        return cf.left == this.col && cf.top == this.row ? cf.bottom - cf.top + 1 : 0;
    }

    public final boolean getVisible() {
        if (!this.grid.getColVisible(this.col) || !this.grid.getRowVisible(this.row)) {
            return false;
        }
        CellField cf = this.grid.expandCell(this.col, this.row);
        return cf.left == this.col && cf.top == this.row;
    }

    public final int getEdgeColor(int edge) {
        switch (edge) {
            case 0: {
                return this.getLEdgeColor();
            }
            case 1: {
                return this.getTEdgeColor();
            }
            case 2: {
                return this.getREdgeColor();
            }
            case 3: {
                return this.getBEdgeColor();
            }
        }
        throw new RuntimeException("\u9519\u8bef\u7684\u8fb9\u6846\u4f4d\u7f6e\uff01");
    }

    public final int getEdgeStyle(int edge) {
        switch (edge) {
            case 0: {
                return this.getLEdgeStyle();
            }
            case 1: {
                return this.getTEdgeStyle();
            }
            case 2: {
                return this.getREdgeStyle();
            }
            case 3: {
                return this.getBEdgeStyle();
            }
        }
        throw new RuntimeException("\u9519\u8bef\u7684\u8fb9\u6846\u4f4d\u7f6e\uff01");
    }

    public final void setEdgeColor(int edge, int color) {
        switch (edge) {
            case 0: {
                this.setLEdgeColor(color);
                break;
            }
            case 1: {
                this.setTEdgeColor(color);
                break;
            }
            case 2: {
                this.setREdgeColor(color);
                break;
            }
            case 3: {
                this.setBEdgeColor(color);
                break;
            }
            default: {
                throw new RuntimeException("\u9519\u8bef\u7684\u8fb9\u6846\u4f4d\u7f6e\uff01");
            }
        }
    }

    public final void setEdgeColor(int edge, GridColor color) {
        this.setEdgeColor(edge, color.value());
    }

    public final void setEdgeStyle(int edge, int style) {
        switch (edge) {
            case 0: {
                this.setLEdgeStyle(style);
                break;
            }
            case 1: {
                this.setTEdgeStyle(style);
                break;
            }
            case 2: {
                this.setREdgeStyle(style);
                break;
            }
            case 3: {
                this.setBEdgeStyle(style);
                break;
            }
            default: {
                throw new RuntimeException("\u9519\u8bef\u7684\u8fb9\u6846\u4f4d\u7f6e\uff01");
            }
        }
    }

    public final void setEdge(int edge, int color, int style) {
        switch (edge) {
            case 0: {
                this.setLEdge(color, style);
                break;
            }
            case 1: {
                this.setTEdge(color, style);
                break;
            }
            case 2: {
                this.setREdge(color, style);
                break;
            }
            case 3: {
                this.setBEdge(color, style);
                break;
            }
            default: {
                throw new RuntimeException("\u9519\u8bef\u7684\u8fb9\u6846\u4f4d\u7f6e\uff01");
            }
        }
    }

    public final void setEdge(int edge, GridColor color, int style) {
        this.setEdge(edge, color.value(), style);
    }

    public final void setBackColor(GridColor color) {
        this.setBackColor(color.value());
    }

    public final void setBEdgeColor(GridColor color) {
        this.setBEdgeColor(color.value());
    }

    public final void setBEdge(int color, int style) {
        this.setBEdgeColor(color);
        this.setBEdgeStyle(style);
    }

    public final void setEditMask(String value) {
    }

    public final void setFontHeight(int value) {
        this.setFontSize(value * 72 / 96);
    }

    public final void setFontStyle(int fontStyle, boolean value) {
        switch (fontStyle) {
            case 0: {
                this.setFontBold(value);
                break;
            }
            case 1: {
                this.setFontItalic(value);
                break;
            }
            case 3: {
                this.setFontStrikeOut(value);
                break;
            }
            case 2: {
                this.setFontUnderLine(value);
                break;
            }
            default: {
                return;
            }
        }
        this.dataChanged();
    }

    public void setLEdgeColor(int value) {
        if (this.col == 0) {
            return;
        }
        GridCell cell = this.grid.internalGetCell(this.col - 1, this.row);
        cell.setREdgeColor(value);
        this.grid.internalSetCell(this.col - 1, this.row, cell);
    }

    public final void setLEdgeColor(GridColor color) {
        this.setLEdgeColor(color.value());
    }

    public void setLEdgeStyle(int value) {
        if (this.col == 0) {
            return;
        }
        GridCell cell = this.grid.internalGetCell(this.col - 1, this.row);
        cell.setREdgeStyle(value);
        this.grid.internalSetCell(this.col - 1, this.row, cell);
    }

    public final void setLEdge(int color, int style) {
        this.setLEdgeColor(color);
        this.setLEdgeStyle(style);
    }

    public final void setREdgeColor(GridColor color) {
        this.setREdgeColor(color.value());
    }

    public final void setREdge(int color, int style) {
        this.setREdgeColor(color);
        this.setREdgeStyle(style);
    }

    public final void setShowFormat(String value) {
        int index = value == null || value.isEmpty() ? 0 : this.toShowFormatIndex(value);
        this.setShowFormatIndex(index);
    }

    final int toShowFormatIndex(String value) {
        int index = this.grid.cellFormats().add(value);
        if (index < 0) {
            throw new GridError("\u5355\u5143\u683c\u81ea\u5b9a\u4e49\u683c\u5f0f\u8fc7\u591a\uff0c\u65e0\u6cd5\u589e\u52a0\u65b0\u7684\u683c\u5f0f\uff1a" + value);
        }
        return index;
    }

    final void setShowFormatIndex(int index) {
        this.buffer.setByte(27, index);
    }

    public void setTEdgeColor(int value) {
        if (this.row == 0) {
            return;
        }
        GridCell cell = this.grid.getCellEx(this.col, this.row - 1);
        cell.setBEdgeColor(value);
        this.grid.setCell(this.col, this.row - 1, cell);
    }

    public final void setTEdgeColor(GridColor color) {
        this.setTEdgeColor(color.value());
    }

    public void setTEdgeStyle(int value) {
        if (this.row == 0) {
            return;
        }
        GridCell cell = this.grid.getCellEx(this.col, this.row - 1);
        cell.setBEdgeStyle(value);
        this.grid.setCell(this.col, this.row - 1, cell);
    }

    public final void setTEdge(int color, int style) {
        this.setTEdgeColor(color);
        this.setTEdgeStyle(style);
    }

    public final void setBoolean(boolean value) {
        this.grid.setCellData(this.col, this.row, String.valueOf(value));
    }

    public final void setDate(Date value) {
        if (value == null) {
            this.grid.setCellData(this.col, this.row, null);
            return;
        }
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String text = fmt.format(value);
        this.grid.setCellData(this.col, this.row, text);
    }

    public final void setDateTime(Date value) {
        this.setDate(value);
    }

    public final void setFloat(double value) {
        this.grid.setCellData(this.col, this.row, String.valueOf(value));
    }

    public final void setInteger(int value) {
        this.grid.setCellData(this.col, this.row, String.valueOf(value));
    }

    public final void setString(String value) {
        this.grid.setCellData(this.col, this.row, value);
    }

    public final void setTime(double value) {
        this.grid.setCellData(this.col, this.row, String.valueOf(value));
    }

    public final void setCellData(String value) {
        this.grid.setCellData(this.col, this.row, value);
    }

    public final void setCellObj(Object obj) {
        this.grid.setObj(this.col, this.row, obj);
    }

    public final void setColNum(int value) {
        this.col = value;
    }

    public final void setColWidth(int value) {
        this.grid.setColWidths(this.col, value);
    }

    public final void setHasConjection(boolean value) {
    }

    public final void setIsNull(boolean value) {
        if (value) {
            this.grid.setCellData(this.col, this.row, value ? null : "");
        }
    }

    public final void setRowHeight(int value) {
        this.grid.setRowHeights(this.row, value);
    }

    public final void setRowNum(int value) {
        this.row = value;
    }

    public final void setVisible(boolean value) {
    }

    public final String getCssClass() {
        return this.grid.getCellClass(this.col, this.row);
    }

    public final void setCssClass(String value) {
        this.grid.setCellClass(this.col, this.row, value);
    }

    public final String getImageReference() {
        String result = this.getCellData();
        if (result != null && result.indexOf(58) > 0) {
            String ext = result.substring(0, result.indexOf(58));
            if ((ext = ext.toUpperCase()).equals("JPG") || ext.equals("BMP") || ext.equals("GIF") || ext.equals("WMF") || ext.equals("PNG") || ext.equals("JPEG")) {
                result = null;
            }
        }
        return result;
    }

    public final void setImageReference(String ref) {
        this.setDataType(6);
        this.setCellData(ref);
    }

    public final String getImageType() {
        String data = this.getCellData();
        if (data == null) {
            return null;
        }
        int pos = data.indexOf(58);
        if (pos >= 0) {
            String ext = data.substring(0, pos);
            return ext.toUpperCase();
        }
        return null;
    }

    public final byte[] getImageData() {
        String data = this.getCellData();
        if (data == null) {
            return null;
        }
        int pos = data.indexOf(58);
        if (pos >= 0) {
            String imgStr = data.substring(pos + 1);
            return Base64.getDecoder().decode(imgStr);
        }
        return null;
    }

    public final void setImageData(byte[] data, String imgExtension) {
        String ext = imgExtension == null || imgExtension.length() == 0 ? "JPG" : imgExtension.toUpperCase();
        String cellData = ext + ":" + Base64.getEncoder().encodeToString(data);
        this.setDataType(6);
        this.setCellData(cellData);
    }

    public final String[] getLinkInformation() {
        return GridCell.parserLinkInformation(this.getCellData());
    }

    public static String[] parserLinkInformation(String linkValue) {
        String[] stringArray;
        if (linkValue == null) {
            return new String[3];
        }
        String showText = null;
        String reference = null;
        String target = null;
        String cellData = null;
        if (linkValue.length() <= 5) {
            showText = linkValue;
        } else {
            String head = linkValue.substring(0, 5).toUpperCase();
            if (!head.equals("LINK:")) {
                showText = linkValue;
            } else {
                try {
                    byte[] data = Base64.getDecoder().decode(linkValue.substring(5));
                    if (data[0] == 37 && data[3] == 37) {
                        linkValue = "ERROR:\u9519\u8bef\u7684\u8d85\u94fe\u63a5\u683c\u5f0f";
                        throw new Exception("\u4e0d\u5339\u914d\u7684Base64\u7f16\u7801\u5f62\u5f0f");
                    }
                    MemStream stream = new MemStream();
                    stream.writeBuffer(data, 0, data.length);
                    stream.setPosition(0L);
                    int count = stream.readInt();
                    showText = stream.readString(count);
                    count = stream.readInt();
                    reference = stream.readString(count);
                    count = stream.readInt();
                    target = stream.readString(count);
                    if (stream.getPosition() < stream.getSize()) {
                        count = stream.readInt();
                        cellData = stream.readString(count);
                    }
                }
                catch (Exception ex) {
                    showText = linkValue;
                    reference = null;
                    target = null;
                    cellData = null;
                }
            }
        }
        if (cellData == null) {
            String[] stringArray2 = new String[3];
            stringArray2[0] = showText;
            stringArray2[1] = reference;
            stringArray = stringArray2;
            stringArray2[2] = target;
        } else {
            String[] stringArray3 = new String[4];
            stringArray3[0] = showText;
            stringArray3[1] = reference;
            stringArray3[2] = target;
            stringArray = stringArray3;
            stringArray3[3] = cellData;
        }
        return stringArray;
    }

    public final void setLinkInformation(String text, String reference, String target) {
        this.setHyperlink(true);
        String data = GridCell.makeLinkInformation(text, reference, target);
        this.setCellData(data);
    }

    public final void setLinkInformation(String showText, String reference, String target, String cellData) {
        this.setHyperlink(true);
        String data = GridCell.makeLinkInformation(showText, reference, target, cellData);
        this.setCellData(data);
    }

    private static void writeString(Stream stream, String text) throws StreamException {
        if (text == null || text.length() == 0) {
            stream.writeInt(0);
        } else {
            byte[] data;
            try {
                data = text.getBytes(stream.getCharset());
            }
            catch (UnsupportedEncodingException e) {
                data = text.getBytes();
            }
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
        }
    }

    public static String makeLinkInformation(String text, String reference, String target) {
        return GridCell.makeLinkInformation(text, reference, target, null);
    }

    public static String makeLinkInformation(String showText, String reference, String target, String dataText) {
        try {
            MemStream stream = new MemStream();
            GridCell.writeString((Stream)stream, showText);
            GridCell.writeString((Stream)stream, reference);
            GridCell.writeString((Stream)stream, target);
            if (dataText != null) {
                GridCell.writeString((Stream)stream, dataText);
            }
            String data = Base64.getEncoder().encodeToString(stream.getBytes());
            return "LINK:" + data;
        }
        catch (StreamException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final String getScript() {
        return this.grid.getCellScript(this.col, this.row);
    }

    public final void setScript(String value) {
        this.grid.setCellScript(this.col, this.row, value);
    }

    public final int getWidth() {
        int width = 0;
        CellField cf = this.grid.expandCell(this.col, this.row);
        for (int i = cf.left; i <= cf.right; ++i) {
            width += this.grid.getColWidths(i);
        }
        return width;
    }

    public final int getHeight() {
        int height = 0;
        CellField cf = this.grid.expandCell(this.col, this.row);
        for (int i = cf.top; i <= cf.bottom; ++i) {
            height += this.grid.getRowHeights(i);
        }
        return height;
    }

    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("[");
        result.append(this.col);
        result.append(",");
        result.append(this.row);
        result.append("]:");
        result.append(this.getCellData());
        return result.toString();
    }

    public final String getEditText() {
        String storeText = this.getCellData();
        if (storeText == null) {
            return null;
        }
        int dataType = this.getDataType();
        CellDataProperty cdp = new CellDataProperty(this.getDataType(), this.getDataFlag(), this.getEditMode(), this.getDataFormat(), this.getDataProperty());
        switch (dataType) {
            case 1: {
                return new TextCellProperty(cdp).getEditText(storeText);
            }
            case 2: {
                return new NumberCellProperty(cdp).getEditText(storeText);
            }
            case 3: {
                return new CurrencyCellProperty(cdp).getEditText(storeText);
            }
        }
        return storeText;
    }

    public final void setEditText(String editText) {
        this.setCellData(this.parseEditText(editText));
    }

    private CellDataProperty createDataProperty() {
        return new CellDataProperty(this.getType(), this.getDataFlag(), this.getEditMode(), this.getDataFormat(), this.getDataProperty());
    }

    public final String getShowText() {
        String storeText = this.getContent();
        if (storeText == null || storeText.isEmpty()) {
            return null;
        }
        switch (this.getType()) {
            case 1: {
                return new TextCellProperty(this.createDataProperty()).getShowText(storeText);
            }
            case 2: {
                return new NumberCellProperty(this.createDataProperty()).getShowText(storeText);
            }
            case 3: {
                return new CurrencyCellProperty(this.createDataProperty()).getShowText(storeText);
            }
            case 4: {
                return new BooleanCellProperty(this.createDataProperty()).getShowText(storeText);
            }
            case 5: {
                return new DateCellProperty(this.createDataProperty()).getShowText(storeText);
            }
            case 100: {
                return new CustomCellProperty(this.getShowFormat()).getShowText(storeText);
            }
        }
        return storeText;
    }

    public final void setShowText(String showText) {
        this.setCellData(this.parseShowText(showText));
    }

    public final String parseEditText(String editText) {
        switch (this.getType()) {
            case 1: {
                return new TextCellProperty(this.createDataProperty()).parseEditText(editText);
            }
            case 2: {
                return new NumberCellProperty(this.createDataProperty()).parseEditText(editText);
            }
            case 3: {
                return new CurrencyCellProperty(this.createDataProperty()).parseEditText(editText);
            }
        }
        return editText;
    }

    public final String parseShowText(String showText) {
        switch (this.getType()) {
            case 1: {
                return new TextCellProperty(this.createDataProperty()).parseShowText(showText);
            }
            case 2: {
                return new NumberCellProperty(this.createDataProperty()).parseShowText(showText);
            }
            case 3: {
                return new CurrencyCellProperty(this.createDataProperty()).parseShowText(showText);
            }
        }
        return showText;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof GridCell)) {
            return false;
        }
        GridCell cell = (GridCell)obj;
        return this.buffer.equals(cell.buffer);
    }

    public int hashCode() {
        return this.buffer.hashCode();
    }

    public final NumberCellPropertyIntf toNumberCell() {
        return new NumberCellProperty(this);
    }

    public final TextCellPropertyIntf toTextCell() {
        return new TextCellProperty(this);
    }

    public final CurrencyCellPropertyIntf toCurrencyCell() {
        return new CurrencyCellProperty(this);
    }

    public final BooleanCellProperty toBooleanCell() {
        return new BooleanCellProperty(this);
    }
}

