/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Base64
 *  com.jiuqi.bi.util.JqLib
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.bi.grid;

import com.jiuqi.bi.grid.CellDataProperty;
import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.CurrencyCellProperty;
import com.jiuqi.bi.grid.DateCellProperty;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridColor;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.NumberCellProperty;
import com.jiuqi.bi.grid.TextCellProperty;
import com.jiuqi.bi.util.Base64;
import com.jiuqi.bi.util.JqLib;
import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

final class GridCellV1
implements Serializable {
    private static final long serialVersionUID = 7437735317988283343L;
    private int col;
    private int row;
    private byte[] data = new byte[20];
    protected GridData grid;
    static final int INDEX_REDGE = 9;
    static final int INDEX_BEDGE = 10;

    GridCellV1() {
    }

    protected void dataChanged() {
    }

    private static int BGR2RGB(int color) {
        return color;
    }

    private static int RGB2BGR(int color) {
        return color;
    }

    private boolean getBit(int bytePos, int bitPos) {
        return (this.data[bytePos] >> bitPos & 1) > 0;
    }

    private void setBit(int bytePos, int bitPos, boolean value) {
        byte flag = (byte)(1 << bitPos);
        this.data[bytePos] = value ? (byte)(this.data[bytePos] | flag) : (byte)(this.data[bytePos] & ~flag);
    }

    private static int byteToInt(byte b) {
        return b < 0 ? 127 + Math.abs(b) : b;
    }

    public final void init(GridData g, int aCol, int aRow) {
        this.init(g, aCol, aRow, g.getCellPropData(aCol, aRow));
    }

    public final void init(GridData g, int aCol, int aRow, byte[] celldata) {
        System.arraycopy(celldata, 0, this.data, 0, celldata.length);
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
                this.data[9] = rData[9];
            }
            if (range.bottom > range.top) {
                byte[] bData = g.internalGetCellPropData(aCol, range.bottom);
                this.data[10] = bData[10];
            }
        }
    }

    public void initRaw(GridData g, int aCol, int aRow) {
        this.internalInit(g, aCol, aRow, g.internalGetCellPropData(aCol, aRow));
    }

    final void internalInit(GridData g, int aCol, int aRow, byte[] celldata) {
        System.arraycopy(celldata, 0, this.data, 0, celldata.length);
        this.grid = g;
        this.col = aCol;
        this.row = aRow;
    }

    public final byte[] getPropData() {
        return this.data;
    }

    final void setPropData(byte[] data) {
        this.data = data;
    }

    public final int getBackColor() {
        return GridCellV1.BGR2RGB(this.grid.cellColors().get(GridCellV1.byteToInt(this.data[0])));
    }

    public final boolean getBackLines(int cellBackStyle) {
        if (cellBackStyle >= 0 && cellBackStyle <= 3) {
            return this.getBit(1, cellBackStyle + 4);
        }
        return false;
    }

    public final int getBackStyle() {
        return this.data[2] >> 0 & 0x3F;
    }

    public final int getBEdgeColor() {
        return GridCellV1.BGR2RGB(this.grid.edgeColors().get(this.data[10] >> 4 & 0xF));
    }

    public final int getBEdgeStyle() {
        return this.data[10] >> 0 & 0xF;
    }

    public final boolean getCanInput() {
        return this.getBit(2, 7);
    }

    public final boolean getCanModify() {
        return this.getBit(2, 6);
    }

    public final boolean getCanRead() {
        return this.getBit(4, 7);
    }

    public final boolean getCanSelect() {
        return this.getBit(4, 6);
    }

    public final boolean getCanWrite() {
        return this.getBit(5, 7);
    }

    public final int getDataType() {
        return this.data[3] >> 4 & 0xF;
    }

    public final boolean getEditJumpNext() {
        return this.getBit(5, 6);
    }

    public final String getEditMask() {
        return null;
    }

    public final int getEditMode() {
        return this.data[3] >> 0 & 0xF;
    }

    public final boolean getEmptyCell() {
        return this.getBit(6, 7);
    }

    public final boolean getFitFontSize() {
        return this.getBit(6, 6);
    }

    public final boolean getFontBold() {
        return this.getBit(7, 7);
    }

    public final int getFontColor() {
        int index = this.data[6] >> 0 & 0x3F;
        return GridCellV1.BGR2RGB(this.grid.fontColors().get(index));
    }

    public final int getFontHeight() {
        return this.getFontSize() * 96 / 72;
    }

    public final boolean getFontItalic() {
        return this.getBit(7, 6);
    }

    public final String getFontName() {
        int index = this.data[4] & 0x3F;
        return this.grid.fontNames().getFont(index);
    }

    public final int getFontSize() {
        int index = this.data[5] & 0x3F;
        int h = this.grid.fontSizes().get(index);
        return h;
    }

    public final boolean getFontStrikeOut() {
        return this.getBit(7, 5);
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

    public final boolean getFontUnderLine() {
        return this.getBit(7, 4);
    }

    public final int getHorzAlign() {
        return this.data[7] >> 0 & 7;
    }

    public final int getImeMode() {
        return this.data[11] >> 0 & 7;
    }

    public final int getIndent() {
        return this.data[1] >> 0 & 0xF;
    }

    public final int getLEdgeColor() {
        return this.col == 0 ? 0 : this.grid.getCellEx(this.col - 1, this.row).getREdgeColor();
    }

    public final int getLEdgeStyle() {
        return this.col == 0 ? 0 : this.grid.getCellEx(this.col - 1, this.row).getREdgeStyle();
    }

    public final boolean getMultiLine() {
        return this.getBit(8, 7);
    }

    public final boolean getReadOnly() {
        return !this.getCanModify() || !this.getCanWrite();
    }

    public final int getREdgeColor() {
        int index = this.data[9] >> 4 & 0xF;
        return GridCellV1.BGR2RGB(this.grid.edgeColors().get(index));
    }

    public final int getREdgeStyle() {
        return this.data[9] >> 0 & 0xF;
    }

    public final String getShowFormat() {
        return null;
    }

    public final boolean getSilverHead() {
        return this.getBit(8, 6);
    }

    public static boolean getSilverHead(byte[] data) {
        return (data[8] >> 6 & 1) > 0;
    }

    public final int getTEdgeColor() {
        return this.row == 0 ? 0 : this.grid.getCellEx(this.col, this.row - 1).getBEdgeColor();
    }

    public final int getTEdgeStyle() {
        return this.row == 0 ? 0 : this.grid.getCellEx(this.col, this.row - 1).getBEdgeStyle();
    }

    public final int getVertAlign() {
        return this.data[8] >> 0 & 7;
    }

    public final boolean getVertText() {
        return this.getBit(8, 5);
    }

    public final boolean getWrapLine() {
        return this.getBit(8, 4);
    }

    public final boolean getBoolean() {
        return JqLib.isBoolTrue((String)this.grid.getCellData(this.col, this.row));
    }

    public final Date getDate() {
        String text = this.grid.getCellData(this.col, this.row);
        return DateCellProperty.tryParseDate(text);
    }

    public final Date getDateTime() {
        return this.getDate();
    }

    public final double getFloat() {
        String strData = this.grid.getCellData(this.col, this.row);
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
        String strData = this.grid.getCellData(this.col, this.row);
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
        return this.grid.getCellData(this.col, this.row);
    }

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

    public final int getOutputKind() {
        return this.data[11] >> 4 & 3;
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

    public final void setBackColor(int value) {
        int color = GridCellV1.RGB2BGR(value);
        int index = this.grid.cellColors().indexOf(color);
        if (index < 0) {
            index = this.grid.cellColors().count() < 127 ? this.grid.cellColors().add(color) : 0;
        }
        this.data[0] = (byte)index;
        this.dataChanged();
    }

    public final void setBackColor(GridColor color) {
        this.setBackColor(color.value());
    }

    public final void setBackLines(int cellBackStyle, boolean value) {
        if (cellBackStyle >= 0 && cellBackStyle <= 3) {
            this.setBit(1, cellBackStyle + 4, value);
        }
        this.dataChanged();
    }

    public final void setBackStyle(int value) {
        this.data[2] = (byte)(this.data[2] & 0xC0 | (byte)value & 0x3F);
        if (value < 28) {
            this.setBit(8, 6, false);
        }
        this.dataChanged();
    }

    public final void setBEdgeColor(int value) {
        int color = GridCellV1.RGB2BGR(value);
        int index = this.grid.edgeColors().indexOf(color);
        if (index < 0) {
            index = this.grid.edgeColors().count() < 15 ? this.grid.edgeColors().add(color) : 0;
        }
        this.data[10] = (byte)(this.data[10] & 0xF | index << 4 & 0xF0);
        this.dataChanged();
    }

    public final void setBEdgeColor(GridColor color) {
        this.setBEdgeColor(color.value());
    }

    public final void setBEdgeStyle(int value) {
        this.data[10] = (byte)(this.data[10] & 0xF0 | value & 0xF);
        this.dataChanged();
    }

    public final void setBEdge(int color, int style) {
        this.setBEdgeColor(color);
        this.setBEdgeStyle(style);
    }

    public final void setCanInput(boolean value) {
        this.setBit(2, 7, value);
        this.dataChanged();
    }

    public final void setCanModify(boolean value) {
        this.setBit(2, 6, value);
        this.dataChanged();
    }

    public final void setCanRead(boolean value) {
        this.setBit(4, 7, value);
        this.dataChanged();
    }

    public final void setCanSelect(boolean value) {
        this.setBit(4, 6, value);
        this.dataChanged();
    }

    public final void setCanWrite(boolean value) {
        this.setBit(5, 7, value);
        this.dataChanged();
    }

    public final void setDataType(int value) {
        this.data[3] = (byte)(this.data[3] & 0xF | value << 4 & 0xF0);
        this.dataChanged();
    }

    public final void setEditJumpNext(boolean value) {
        this.setBit(5, 6, value);
        this.dataChanged();
    }

    public final void setEditMask(String value) {
    }

    public final void setEditMode(int value) {
        this.data[3] = (byte)(this.data[3] & 0xF0 | value & 0xF);
        this.dataChanged();
    }

    public final void setEmptyCell(boolean value) {
        this.setBit(6, 7, value);
        this.dataChanged();
    }

    public final void setFitFontSize(boolean value) {
        this.setBit(6, 6, value);
        this.dataChanged();
    }

    public final void setFontBold(boolean value) {
        this.setBit(7, 7, value);
        this.dataChanged();
    }

    public final void setFontColor(int value) {
        int color = GridCellV1.RGB2BGR(value);
        int index = this.grid.fontColors().indexOf(color);
        if (index < 0) {
            index = this.grid.fontColors().count() < 63 ? this.grid.fontColors().add(color) : 0;
        }
        this.data[6] = (byte)(this.data[6] & 0xC0 | index & 0x3F);
        this.dataChanged();
    }

    public final void setFontHeight(int value) {
        this.setFontSize(value * 72 / 96);
    }

    public final void setFontItalic(boolean value) {
        this.setBit(7, 6, value);
        this.dataChanged();
    }

    public final void setFontName(String value) {
        int index = this.grid.fontNames().indexOfFont(value);
        if (index == -1) {
            index = this.grid.fontNames().count() < 63 ? this.grid.fontNames().addFont(value) : 0;
        }
        this.data[4] = (byte)(this.data[4] & 0xC0 | index & 0x3F);
        this.dataChanged();
    }

    public final void setFontSize(int value) {
        int fontsize = value;
        int index = this.grid.fontSizes().indexOf(fontsize);
        if (index < 0) {
            index = this.grid.fontSizes().count() < 63 ? this.grid.fontSizes().add(fontsize) : 0;
        }
        this.data[5] = (byte)(this.data[5] & 0xC0 | index & 0x3F);
        this.dataChanged();
    }

    public final void setFontStrikeOut(boolean value) {
        this.setBit(7, 5, value);
        this.dataChanged();
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

    public final void setFontUnderLine(boolean value) {
        this.setBit(7, 4, value);
        this.dataChanged();
    }

    public final void setHorzAlign(int value) {
        this.data[7] = (byte)(this.data[7] & 0xF8 | value & 7);
        this.dataChanged();
    }

    public final void setImeMode(int value) {
        this.data[11] = (byte)(this.data[11] & 0xF8 | value & 7);
        this.dataChanged();
    }

    public final void setIndent(int value) {
        this.data[1] = (byte)(this.data[1] & 0xF0 | value & 0xF);
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

    public final void setMultiLine(boolean value) {
        this.setBit(8, 7, value);
        this.dataChanged();
    }

    public final void setREdgeColor(int value) {
        int color = GridCellV1.RGB2BGR(value);
        int index = this.grid.edgeColors().indexOf(color);
        if (index < 0) {
            index = this.grid.edgeColors().count() < 15 ? this.grid.edgeColors().add(color) : 0;
        }
        this.data[9] = (byte)(this.data[9] & 0xF | index << 4 & 0xF0);
        this.dataChanged();
    }

    public final void setREdgeColor(GridColor color) {
        this.setREdgeColor(color.value());
    }

    public final void setREdgeStyle(int value) {
        this.data[9] = (byte)(this.data[9] & 0xF0 | value & 0xF);
        this.dataChanged();
    }

    public final void setREdge(int color, int style) {
        this.setREdgeColor(color);
        this.setREdgeStyle(style);
    }

    public final void setShowFormat(String value) {
    }

    public final void setSilverHead(boolean value) {
        if (this.getBit(8, 6) == value) {
            return;
        }
        this.setBit(8, 6, value);
        if (value) {
            this.setBackColor(0xF2F2F2);
            this.setBackStyle(28);
            this.setLEdgeColor(0x3F3F3F);
            this.setREdgeColor(0x3F3F3F);
            this.setTEdgeColor(0x3F3F3F);
            this.setBEdgeColor(0x3F3F3F);
        } else {
            this.setBackColor(0xFFFFFF);
            this.setBackStyle(0);
            this.setLEdgeColor(0x3F3F3F);
            this.setREdgeColor(0x3F3F3F);
            this.setTEdgeColor(0x3F3F3F);
            this.setBEdgeColor(0x3F3F3F);
        }
        this.dataChanged();
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

    public final void setVertAlign(int value) {
        this.data[8] = (byte)(this.data[8] & 0xF8 | value & 7);
        this.dataChanged();
    }

    public final void setVertText(boolean value) {
        this.setBit(8, 5, value);
        this.dataChanged();
    }

    public final void setWrapLine(boolean value) {
        this.setBit(8, 4, value);
        this.dataChanged();
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
        this.grid.setCellData(this.col, this.row, value ? null : "");
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
            return Base64.base64ToByteArray((String)imgStr);
        }
        return null;
    }

    public final void setImageData(byte[] data, String imgExtension) {
        String ext = imgExtension == null || imgExtension.length() == 0 ? "JPG" : imgExtension.toUpperCase();
        String cellData = ext + ":" + Base64.byteArrayToBase64((byte[])data);
        this.setDataType(6);
        this.setCellData(cellData);
    }

    public final String[] getLinkInformation() {
        return GridCellV1.parserLinkInformation(this.getCellData());
    }

    public static final String[] parserLinkInformation(String linkValue) {
        String[] linkInfo = new String[3];
        if (linkValue != null) {
            if (linkValue.length() <= 5) {
                linkInfo[0] = linkValue;
            } else {
                String head = linkValue.substring(0, 5).toUpperCase();
                if (!head.equals("LINK:")) {
                    linkInfo[0] = linkValue;
                } else {
                    try {
                        byte[] data = Base64.base64ToByteArray((String)linkValue.substring(5));
                        MemStream stream = new MemStream();
                        stream.writeBuffer(data, 0, data.length);
                        stream.setPosition(0L);
                        int count = stream.readInt();
                        linkInfo[0] = stream.readString(count);
                        count = stream.readInt();
                        linkInfo[1] = stream.readString(count);
                        count = stream.readInt();
                        linkInfo[2] = stream.readString(count);
                    }
                    catch (Exception ex) {
                        linkInfo[0] = linkValue;
                        linkInfo[1] = null;
                        linkInfo[2] = null;
                        ex.printStackTrace();
                    }
                }
            }
        }
        return linkInfo;
    }

    public final void setLinkInformation(String text, String reference, String target) {
        this.setDataType(9);
        String data = GridCellV1.makeLinkInformation(text, reference, target);
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

    public static final String makeLinkInformation(String text, String reference, String target) {
        try {
            MemStream stream = new MemStream();
            GridCellV1.writeString((Stream)stream, text);
            GridCellV1.writeString((Stream)stream, reference);
            GridCellV1.writeString((Stream)stream, target);
            String data = Base64.byteArrayToBase64((byte[])stream.getBytes());
            return "LINK:" + data;
        }
        catch (StreamException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final int getDataFlag() {
        return this.data[12] & 0xF;
    }

    public final void setDataFlag(int value) {
        this.data[12] = (byte)(this.data[12] & 0xF0 | value & 0xF);
        this.dataChanged();
    }

    public final int getDataFormat() {
        return this.data[12] >>> 4;
    }

    public final void setDataFormat(int value) {
        this.data[12] = (byte)(this.data[12] & 0xF | (value & 0xF) << 4);
        this.dataChanged();
    }

    public final int getDataProperty() {
        int r = this.data[14];
        r = (r << 8) + this.data[13];
        return r;
    }

    public final void setDataProperty(int value) {
        this.data[14] = (byte)(value >>> 8);
        this.data[13] = (byte)(value & 0xFF);
        this.dataChanged();
    }

    public final void setOutputKind(int value) {
        this.data[11] = (byte)(this.data[11] & 0xCF | value << 4 & 0x30);
        this.dataChanged();
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

    public final String getShowText() {
        String storeText = this.getCellData();
        if (storeText == null) {
            return null;
        }
        int dataType = this.getDataType();
        CellDataProperty cdp = new CellDataProperty(this.getDataType(), this.getDataFlag(), this.getEditMode(), this.getDataFormat(), this.getDataProperty());
        switch (dataType) {
            case 1: {
                return new TextCellProperty(cdp).getShowText(storeText);
            }
            case 2: {
                return new NumberCellProperty(cdp).getShowText(storeText);
            }
            case 3: {
                return new CurrencyCellProperty(cdp).getShowText(storeText);
            }
            case 5: {
                return new DateCellProperty(cdp).getShowText(storeText);
            }
        }
        return storeText;
    }

    public final void setShowText(String showText) {
        this.setCellData(this.parseShowText(showText));
    }

    public final String parseEditText(String editText) {
        int dataType = this.getDataType();
        CellDataProperty cdp = new CellDataProperty(this.getDataType(), this.getDataFlag(), this.getEditMode(), this.getDataFormat(), this.getDataProperty());
        switch (dataType) {
            case 1: {
                return new TextCellProperty(cdp).parseEditText(editText);
            }
            case 2: {
                return new NumberCellProperty(cdp).parseEditText(editText);
            }
            case 3: {
                return new CurrencyCellProperty(cdp).parseEditText(editText);
            }
        }
        return editText;
    }

    public final String parseShowText(String showText) {
        int dataType = this.getDataType();
        CellDataProperty cdp = new CellDataProperty(this.getDataType(), this.getDataFlag(), this.getEditMode(), this.getDataFormat(), this.getDataProperty());
        switch (dataType) {
            case 1: {
                return new TextCellProperty(cdp).parseShowText(showText);
            }
            case 2: {
                return new NumberCellProperty(cdp).parseShowText(showText);
            }
            case 3: {
                return new CurrencyCellProperty(cdp).parseShowText(showText);
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
        if (!(obj instanceof GridCellV1)) {
            return false;
        }
        byte[] cmpData = ((GridCellV1)obj).getPropData();
        if (this.data.length != cmpData.length) {
            return false;
        }
        for (int i = 0; i < this.data.length; ++i) {
            if (this.data[i] == cmpData[i]) continue;
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result = 0;
        for (int i = 0; i < this.data.length; ++i) {
            result = 37 * result + this.data[i];
        }
        return result;
    }
}

