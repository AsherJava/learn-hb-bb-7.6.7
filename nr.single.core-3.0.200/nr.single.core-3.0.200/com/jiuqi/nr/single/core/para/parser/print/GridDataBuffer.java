/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.CellField
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridCellProperty
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.grid.LabelItem
 *  com.jiuqi.bi.grid.LabelItems
 *  com.jiuqi.bi.util.Base64
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.para.parser.print;

import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridCellProperty;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.LabelItem;
import com.jiuqi.bi.grid.LabelItems;
import com.jiuqi.bi.util.Base64;
import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.para.consts.AppendStrPos;
import com.jiuqi.nr.single.core.para.parser.print.CellValueList;
import com.jiuqi.nr.single.core.para.parser.print.ColRowPair;
import com.jiuqi.nr.single.core.para.parser.print.GridPrintMan;
import com.jiuqi.nr.single.core.para.parser.print.GridPrintTextData;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GridDataBuffer {
    private List<String> FFontNames = new ArrayList<String>();
    private List<Integer> FFontSizes = new ArrayList<Integer>();
    private List<CellField> FFieldList = new ArrayList<CellField>();
    private List<Integer> FColWidths = new ArrayList<Integer>();
    private List<Boolean> FColVisible = new ArrayList<Boolean>();
    private List<Boolean> FColAutoSize = new ArrayList<Boolean>();
    private List<Integer> FRowHeights = new ArrayList<Integer>();
    private List<Boolean> FRowVisible = new ArrayList<Boolean>();
    private List<Boolean> FRowAutoSize = new ArrayList<Boolean>();
    private List<Integer> FCellColors = new ArrayList<Integer>();
    private List<Integer> FFontColors = new ArrayList<Integer>();
    private List<Integer> FEdgeColors = new ArrayList<Integer>();
    private List<byte[]> FPropList = new ArrayList<byte[]>();
    private List<Short> FPropIndexs = new ArrayList<Short>();
    private CellValueList FValueList = new CellValueList();
    private CellValueList FCellCSS = new CellValueList();
    private CellValueList FCellScript = new CellValueList();
    private List<Boolean> FHideDataCol = new ArrayList<Boolean>();
    private List<Boolean> FHideDataRow = new ArrayList<Boolean>();
    private LabelItems labelItems = new LabelItems();
    private String gridDataClass = "";
    private String referenceBase = "";
    private boolean horzCenter;
    private int horzOffset;
    private boolean vertCenter;
    private int vertOffset;
    private int FLockTopCol;
    private int FLockBottomCol;
    private int FLockTopRow;
    private int FLockBottomRow;
    private int FCurCol;
    private int FCurRow;
    private int FColCountPerPage;
    private int FRowCountPerPage;
    private boolean FRowPagingFirst;

    public static String PrintDataToBytes(GridPrintMan gridPrintMan, RepInfo repInfo) throws IOException, StreamException {
        GridDataBuffer GridDataBuffer2 = new GridDataBuffer();
        MemStream stream = new MemStream();
        GridDataBuffer2.SaveToStream((Stream)stream);
        int size = (int)stream.getSize();
        byte[] tmpBytes = new byte[size];
        stream.read(tmpBytes, 0, size);
        String result = "";
        result = Base64.byteArrayToBase64((byte[])tmpBytes);
        return result;
    }

    public final boolean assignFrom(GridPrintMan gridPrintMan, RepInfo repInfo) {
        int i;
        GridData gridData = new GridData();
        this.gridDataClass = gridData.getDataClass();
        this.referenceBase = gridData.getReferenceBase();
        this.horzCenter = gridData.getHorzCenter();
        this.horzOffset = gridData.getHorzOffset();
        this.vertCenter = gridData.getVertCenter();
        this.vertOffset = gridData.getVertOffset();
        for (int col = 0; col < gridData.getColCount(); ++col) {
            this.FColWidths.add(gridData.getColWidths(col));
            this.FColVisible.add(gridData.getColVisible(col));
            this.FColAutoSize.add(gridData.getColAutoSize(col));
        }
        for (int row = 0; row < gridData.getRowCount(); ++row) {
            this.FRowHeights.add(gridData.getRowHeights(row));
            this.FRowVisible.add(gridData.getRowAutoSize(row));
            this.FRowAutoSize.add(gridData.getRowAutoSize(row));
        }
        for (i = 0; i < gridData.fontColors().count(); ++i) {
            this.FFontColors.add(gridData.fontColors().get(i));
        }
        for (i = 0; i < gridData.edgeColors().count(); ++i) {
            this.FEdgeColors.add(gridData.edgeColors().get(i));
        }
        this.assignFromData(gridData);
        this.FLockTopCol = gridData.getScrollTopCol();
        this.FLockBottomCol = gridData.lockBottomCol();
        this.FLockTopRow = gridData.lockTopRow();
        this.FLockBottomRow = gridData.lockBottomRow();
        this.FCurCol = gridData.curCol();
        this.FCurRow = gridData.curRow();
        this.GetLabelItem(gridPrintMan, gridData, repInfo);
        this.FColCountPerPage = gridPrintMan.getHorzPages();
        this.FRowCountPerPage = gridPrintMan.getVertPages();
        this.FRowPagingFirst = gridPrintMan.getRowFirst();
        return true;
    }

    private void assignFromData(GridData gridData) {
        for (int col = 0; col < gridData.getColCount(); ++col) {
            for (int row = 0; row < gridData.getRowCount(); ++row) {
                this.assignFromCell(gridData, col, row);
            }
        }
    }

    private void assignFromCell(GridData gridData, int col, int row) {
        this.FCellColors.add(gridData.getCell(col, row).getBackColor());
        byte[] buffer = new byte[20];
        GridCell cell = gridData.getCell(col, row);
        GridCellProperty prop = null;
        if (!this.FFontNames.contains(cell.getFontName())) {
            this.FFontNames.add(cell.getFontName());
        }
        if (!this.FFontSizes.contains(cell.getFontSize())) {
            this.FFontSizes.add(cell.getFontSize());
        }
        if (cell.getCellLeft() == col && cell.getCellTop() == row) {
            if (cell.getHasConjection()) {
                this.FFieldList.add(cell.getCellField());
            }
            prop = (GridCellProperty)cell;
        } else {
            prop = gridData.getCellForChange(col, row);
            if (!this.FFontNames.contains(prop.getFontName())) {
                this.FFontNames.add(prop.getFontName());
            }
            if (!this.FFontSizes.contains(prop.getFontSize())) {
                this.FFontSizes.add(prop.getFontSize());
            }
        }
        this.PropToBuffer(prop, buffer);
        this.PropToBufferEx(prop, buffer);
        this.FPropList.add(buffer);
        this.FPropIndexs.add((short)this.FPropList.size());
        ColRowPair colRowPair = new ColRowPair();
        colRowPair.setCol(col);
        colRowPair.setRow(row);
        if (!StringUtils.isEmpty((String)cell.getCellData())) {
            this.FValueList.add(colRowPair, cell.getCellData());
        }
        if (!StringUtils.isEmpty((String)cell.getCssClass())) {
            this.FCellCSS.add(colRowPair, cell.getCssClass());
        }
        if (!StringUtils.isEmpty((String)cell.getScript())) {
            this.FCellScript.add(colRowPair, cell.getScript());
        }
    }

    public final void SaveToStream(Stream stream) throws IOException, StreamException {
        byte[] newBytes;
        String fontName;
        int i;
        stream.write((byte)0);
        int size = 12;
        stream.writeInt(size);
        stream.writeString("BIFF");
        stream.writeInt(2);
        stream.writeInt(0x1010000);
        stream.write((byte)18);
        size = 0;
        stream.writeInt(size);
        stream.write((byte)6);
        size = 4;
        for (i = 0; i < this.FFontNames.size(); ++i) {
            fontName = this.FFontNames.get(i);
            int n = fontName.length();
            ++size;
            size += n;
        }
        stream.writeInt(size);
        for (i = 0; i < this.FFontNames.size(); ++i) {
            fontName = this.FFontNames.get(i);
            byte by = (byte)fontName.length();
            stream.write(by);
            stream.writeString(fontName);
        }
        this.WriteIntBiff(stream, (byte)8, this.FFontSizes);
        this.WriteIntBiff(stream, (byte)7, this.FFontColors);
        this.WriteIntBiff(stream, (byte)10, this.FCellColors);
        this.WriteIntBiff(stream, (byte)9, this.FEdgeColors);
        this.WriteIntBiff(stream, (byte)2, this.FColWidths);
        this.WriteBoolBiff(stream, (byte)13, this.FColAutoSize);
        this.WriteBoolBiff(stream, (byte)12, this.FColVisible);
        this.WriteBoolBiff(stream, (byte)21, this.FHideDataCol);
        this.WriteIntBiff(stream, (byte)3, this.FRowHeights);
        this.WriteBoolBiff(stream, (byte)15, this.FRowAutoSize);
        this.WriteBoolBiff(stream, (byte)14, this.FRowVisible);
        this.WriteBoolBiff(stream, (byte)22, this.FHideDataRow);
        stream.write((byte)4);
        MemStream tmpStream = new MemStream();
        this.FValueList.saveToStream((Stream)tmpStream);
        size = (int)tmpStream.getSize();
        stream.writeInt(size);
        byte[] tmpB = new byte[(int)tmpStream.getSize()];
        tmpStream.read(tmpB, 0, tmpB.length);
        stream.write(tmpB, 0, tmpB.length);
        stream.write((byte)11);
        size = this.FPropList.size() * 12;
        stream.writeInt(size);
        for (byte[] bytese : this.FPropList) {
            newBytes = new byte[12];
            System.arraycopy(bytese, 0, newBytes, 0, 12);
            stream.write(newBytes, 0, newBytes.length);
        }
        this.WriteShortBiff(stream, (byte)1, this.FPropIndexs);
        stream.write((byte)5);
        size = this.FFieldList.size() * 3 * 4;
        stream.writeInt(size);
        for (CellField cell : this.FFieldList) {
            stream.writeInt(cell.left);
            stream.writeInt(cell.top);
            stream.writeInt(cell.right);
            stream.writeInt(cell.bottom);
        }
        stream.write((byte)16);
        size = 16;
        stream.writeInt(size);
        stream.writeInt(this.FLockTopCol);
        stream.writeInt(this.FLockTopRow);
        stream.writeInt(this.FLockBottomCol);
        stream.writeInt(this.FLockBottomRow);
        stream.write((byte)17);
        size = 8;
        stream.writeInt(size);
        stream.writeInt(this.FCurCol);
        stream.writeInt(this.FCurRow);
        stream.write((byte)19);
        size = this.FPropList.size() * 8;
        stream.writeInt(size);
        for (byte[] bytese : this.FPropList) {
            newBytes = new byte[8];
            System.arraycopy(bytese, 12, newBytes, 0, 8);
            stream.write(newBytes, 0, newBytes.length);
        }
        stream.write((byte)20);
        MemStream memStream = new MemStream();
        this.labelItems.saveToStream((Stream)memStream);
        size = (int)memStream.getSize();
        stream.writeInt(size);
        byte[] msB = new byte[(int)tmpStream.getSize()];
        tmpStream.read(msB, 0, tmpB.length);
        stream.write(msB, 0, tmpB.length);
        stream.write((byte)23);
        size = 9;
        stream.writeInt(size);
        stream.writeInt(this.FColCountPerPage);
        stream.writeInt(this.FRowCountPerPage);
        stream.writeBool(this.FRowPagingFirst);
        stream.write((byte)24);
        MemStream ccStream = new MemStream();
        this.FCellCSS.saveToStream((Stream)ccStream);
        size = (int)ccStream.getSize();
        stream.writeInt(size);
        byte[] ccB = new byte[size];
        ccStream.read(ccB, 0, size);
        stream.write(ccB, 0, size);
        stream.write((byte)25);
        size = 4;
        stream.writeInt(size);
        int Len = "horzoffset".length();
        stream.writeInt(Len);
        stream.writeString("horzoffset");
        Len = new Integer(this.horzOffset).toString().length();
        stream.writeInt(Len);
        stream.writeString(new Integer(this.horzOffset).toString());
        Len = "vertoffset".length();
        stream.writeInt(Len);
        stream.writeString("vertoffset");
        Len = new Integer(this.vertOffset).toString().length();
        stream.writeInt(Len);
        stream.writeString(new Integer(this.vertOffset).toString());
        Len = "horzcenter".length();
        stream.writeInt(Len);
        stream.writeString("horzcenter");
        String str = this.horzCenter ? "1" : "0";
        Len = str.length();
        stream.writeInt(Len);
        stream.writeString(str);
        Len = "vertcenter".length();
        stream.writeInt(Len);
        stream.writeString("vertcenter");
        str = this.vertCenter ? "1" : "0";
        Len = str.length();
        stream.writeInt(Len);
        stream.writeString(str);
        stream.writeString("KEYS");
        stream.write((byte)26);
        MemStream tmpStream1 = new MemStream();
        this.FCellScript.saveToStream((Stream)tmpStream1);
        size = (int)tmpStream1.getSize();
        stream.writeInt(size);
        byte[] csB = new byte[size];
        tmpStream1.read(csB, 0, size);
        stream.write(csB, 0, size);
        stream.write((byte)27);
        size = 0;
        stream.writeInt(size);
        stream.write((byte)127);
        size = 0;
        stream.writeInt(size);
    }

    private void WriteIntBiff(Stream stream, byte sign, List<Integer> valueList) throws StreamException {
        int size = valueList.size() * 4;
        stream.write(sign);
        stream.writeInt(size);
        for (int value : valueList) {
            stream.writeInt(value);
        }
    }

    private void WriteBoolBiff(Stream stream, byte sign, List<Boolean> valueList) throws StreamException {
        int size = valueList.size() / 8;
        if (valueList.size() % 8 != 0) {
            ++size;
        }
        stream.write(sign);
        stream.writeInt(size);
        byte[] b = new byte[size * 8];
        int index = 0;
        for (boolean b1 : valueList) {
            b[index] = 0;
            if (b1) {
                b[index] = 1;
            }
            ++index;
        }
        stream.write(b, 0, size * 8);
    }

    private void WriteShortBiff(Stream stream, byte sign, List<Short> valueList) throws StreamException {
        int size = valueList.size() * 2;
        stream.write(sign);
        stream.writeInt(size);
        for (Short value : valueList) {
            stream.writeShort(value.shortValue());
        }
    }

    private void PropToBuffer(GridCellProperty prop, byte[] buffer) {
        buffer[0] = (byte)this.FCellColors.indexOf(prop.getBackColor());
        this.SetByteBit(buffer, 1, 7, prop.getBackLines(0));
        this.SetByteBit(buffer, 1, 6, prop.getBackLines(1));
        this.SetByteBit(buffer, 1, 5, prop.getBackLines(2));
        this.SetByteBit(buffer, 1, 4, prop.getBackLines(3));
        this.SetByteLow(buffer, 1, (byte)4, (byte)prop.getIndent());
        this.SetByteBit(buffer, 2, 7, prop.getCanInput());
        this.SetByteBit(buffer, 2, 6, prop.getCanModify());
        this.SetByteLow(buffer, 2, (byte)6, (byte)prop.getBackStyle());
        this.SetByteHigh(buffer, 3, (byte)4, (byte)prop.getDataType());
        this.SetByteLow(buffer, 3, (byte)4, (byte)prop.getEditMode());
        this.SetByteBit(buffer, 4, 7, prop.getCanRead());
        this.SetByteBit(buffer, 4, 6, prop.getCanSelect());
        this.SetByteLow(buffer, 4, (byte)6, (byte)this.FFontNames.indexOf(prop.getFontName()));
        this.SetByteBit(buffer, 5, 7, prop.getCanWrite());
        this.SetByteBit(buffer, 5, 6, prop.getEditJumpNext());
        this.SetByteLow(buffer, 5, (byte)6, (byte)this.FFontSizes.indexOf(prop.getFontSize()));
        this.SetByteBit(buffer, 6, 7, prop.getEmptyCell());
        this.SetByteBit(buffer, 6, 6, prop.getFitFontSize());
        this.SetByteLow(buffer, 6, (byte)6, (byte)this.FFontColors.indexOf(prop.getFontColor()));
        this.SetByteBit(buffer, 7, 7, prop.getFontBold());
        this.SetByteBit(buffer, 7, 6, prop.getFontItalic());
        this.SetByteBit(buffer, 7, 5, prop.getFontStrikeOut());
        this.SetByteBit(buffer, 7, 4, prop.getFontUnderLine());
        this.SetByteLow(buffer, 7, (byte)3, (byte)prop.getHorzAlign());
        this.SetByteBit(buffer, 8, 7, prop.getMultiLine());
        this.SetByteBit(buffer, 8, 6, prop.getSilverHead());
        this.SetByteBit(buffer, 8, 5, prop.getVertText());
        this.SetByteBit(buffer, 8, 4, prop.getWrapLine());
        this.SetByteLow(buffer, 8, (byte)3, (byte)prop.getVertAlign());
        this.SetByteHigh(buffer, 9, (byte)4, (byte)this.FEdgeColors.indexOf(prop.getREdgeColor()));
        this.SetByteLow(buffer, 9, (byte)4, (byte)prop.getREdgeStyle());
        this.SetByteHigh(buffer, 10, (byte)4, (byte)this.FEdgeColors.indexOf(prop.getBEdgeColor()));
        this.SetByteLow(buffer, 10, (byte)4, (byte)prop.getBEdgeStyle());
        this.SetByteLow(buffer, 11, (byte)3, (byte)prop.getImeMode());
        this.SetByteHigh(buffer, 11, (byte)4, (byte)prop.getOutputKind());
    }

    private void PropToBufferEx(GridCellProperty prop, byte[] buffer) {
        byte W;
        this.SetByteLow(buffer, 12, (byte)4, (byte)prop.getDataFlag());
        this.SetByteHigh(buffer, 12, (byte)4, (byte)prop.getDataFlag());
        buffer[13] = W = (byte)prop.getDataProperty();
    }

    private void SetByteBit(byte[] buffer, int bytePos, int bitPos, boolean value) {
        buffer[bytePos] = value ? (byte)(buffer[bytePos] | 1 << bitPos) : (byte)(buffer[bytePos] & ~(1 << bitPos));
    }

    private void SetByteLow(byte[] bArr, int index, byte bits, byte value) {
        byte b = bArr[index];
        b = (byte)(b >> bits);
        b = (byte)(b << bits);
        bArr[index] = b = (byte)(b | value);
    }

    private void SetByteHigh(byte[] bArr, int index, byte bits, byte value) {
        byte b = bArr[index];
        b = (byte)(b << bits);
        b = (byte)(b >> bits);
        bArr[index] = b = (byte)(b | value << 8 - bits);
    }

    private void GetLabelItem(GridPrintMan gridPrintMan, GridData jioGridData, RepInfo repInfo) {
        for (int i = 0; i < gridPrintMan.getTextsDef().getCount(); ++i) {
            GridPrintTextData textData = gridPrintMan.getTextsDef().getTextData(i);
            LabelItem labelItem = jioGridData.getLabels().add();
            labelItem.setName("NBLabel" + i);
            labelItem.setPosition(this.GetGGPos(textData.getPosition()));
            String text = "";
            if (textData.getComments().equals(repInfo.getTitle())) {
                text = "<D>#RPTMAINTITLE</D>";
            } else if (textData.getComments().equals("\u91d1\u989d\u5355\u4f4d\uff1a" + repInfo.getMoneyUnit())) {
                text = "\u91d1\u989d\u5355\u4f4d\uff1a<D>#RPTMONEYUNIT</D>";
            } else if (textData.getComments().equals("\u62a5\u8868\u7f16\u53f7\uff1a" + repInfo.getSubNo())) {
                text = "\u62a5\u8868\u7f16\u53f7\uff1a<D>#REPORTNUM</D>";
            } else if (textData.getComments().equals(repInfo.getSubNo()) || textData.getComments().equals("\u62a5\u8868\u7f16\u53f7")) {
                text = "<D>#REPORTNUM</D>";
            }
            text = text.replace("<D>", "");
            text = text.replace("</D>", "");
            text = text.replace("%D", "{#PageNumber}");
            text = text.replace("%d", "{#PageNumber}");
            labelItem.setExpression(text);
            labelItem.getFont().setName(textData.getFontData().getName());
            labelItem.getFont().setColor(textData.getFontData().getColor());
            labelItem.getFont().setStylevalue((int)textData.getFontData().getStyles());
        }
    }

    private int GetGGPos(int position) {
        int result = 0;
        switch (position) {
            case 7: {
                result = AppendStrPos.ASPPAGE_TOPLEFT.getValue();
                break;
            }
            case 6: {
                result = AppendStrPos.ASPPAGE_TOPCENTER.getValue();
                break;
            }
            case 8: {
                result = AppendStrPos.ASPPAGE_TOPRIGHT.getValue();
                break;
            }
            case 0: {
                result = AppendStrPos.ASPGRID_TOPLEFT.getValue();
                break;
            }
            case 4: {
                result = AppendStrPos.ASPGRID_TOPCENTER.getValue();
                break;
            }
            case 1: {
                result = AppendStrPos.ASPGRID_TOPRIGHT.getValue();
                break;
            }
            case 2: {
                result = AppendStrPos.ASPGRID_BOTTOMLEFT.getValue();
                break;
            }
            case 5: {
                result = AppendStrPos.ASPGRID_BOTTOMCENTER.getValue();
                break;
            }
            case 3: {
                result = AppendStrPos.ASPGRID_BOTTOMRIGHT.getValue();
                break;
            }
            case 10: {
                result = AppendStrPos.ASPPAGE_BOTTOMLEFT.getValue();
                break;
            }
            case 9: {
                result = AppendStrPos.ASPPAGE_BOTTOMCENTER.getValue();
                break;
            }
            case 11: {
                result = AppendStrPos.ASPPAGE_BOTTOMRIGHT.getValue();
            }
        }
        return result;
    }
}

