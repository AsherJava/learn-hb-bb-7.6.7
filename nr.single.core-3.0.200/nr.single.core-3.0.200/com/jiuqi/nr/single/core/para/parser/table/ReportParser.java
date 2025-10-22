/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums$JIOBackgroundstyle
 */
package com.jiuqi.nr.single.core.para.parser.table;

import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.para.JQTFileMap;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.consts.ReportConsts;
import com.jiuqi.nr.single.core.para.impl.ReportDataImpl;
import com.jiuqi.nr.single.core.para.impl.ReportFormImpl;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import com.jiuqi.nr.single.core.para.util.Utils;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportParser {
    private static final Logger log = LoggerFactory.getLogger(ReportParser.class);
    private Map<String, ReportFormImpl> repMap;
    private boolean isFloat = false;

    public ReportParser(Map<String, ReportFormImpl> repMap) {
        this.repMap = repMap;
    }

    public ReportParser() {
        this.repMap = null;
    }

    public final RepInfo buildPara(String filePath, ParaInfo info, JQTFileMap map, String name, boolean isFloat) throws StreamException, IOException {
        this.isFloat = isFloat;
        return this.readReport(filePath, map);
    }

    public RepInfo readReport(String filePath, JQTFileMap jqtFileMap) throws StreamException, IOException {
        File aFile = new File(filePath);
        if (!aFile.exists()) {
            return null;
        }
        RepInfo repInfo = null;
        if (jqtFileMap == null) {
            return repInfo;
        }
        MemStream stream = null;
        stream = new MemStream();
        stream.loadFromFile(filePath);
        stream.seek(0L, 0);
        repInfo = this.initReport((Stream)stream, jqtFileMap);
        return repInfo;
    }

    public RepInfo readReport(byte[] data) throws StreamException {
        if (data == null) {
            return null;
        }
        RepInfo repInfo = null;
        MemStream stream = null;
        stream = new MemStream();
        stream.writeBuffer(data, 0, data.length);
        stream.seek(0L, 0);
        JQTFileMap jqtFileMap = this.initJQTFileMap(stream);
        stream.seek(0L, 0);
        repInfo = this.initReport((Stream)stream, jqtFileMap);
        return repInfo;
    }

    public RepInfo initReport(Stream mask0, JQTFileMap jqtFileMap) throws StreamException {
        RepInfo repInfo = new RepInfo();
        try {
            repInfo.setCode(jqtFileMap.getCode());
            this.readRowColSize(mask0, jqtFileMap, repInfo);
            this.readTitles(mask0, jqtFileMap, repInfo);
            this.readDatas(mask0, jqtFileMap, repInfo);
            this.readApperance(mask0, jqtFileMap, repInfo);
            this.saveReportDataImpl(repInfo);
        }
        catch (Exception ex) {
            log.error(repInfo.getCode() + "\u8868\u6837\u5b58\u5728\u95ee\u9898\uff1a" + ex.getMessage());
            throw ex;
        }
        return repInfo;
    }

    private JQTFileMap initJQTFileMap(MemStream iStream) throws StreamException {
        JQTFileMap jqtFileMap = new JQTFileMap();
        iStream.seek(0L, 0);
        ReadUtil.skipStream((Stream)iStream, 96);
        jqtFileMap.init((Stream)iStream);
        return jqtFileMap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void readRowColSize(Stream stream, JQTFileMap jqtFileMap, RepInfo repInfo) throws StreamException {
        long SavePoint = stream.getPosition();
        try {
            int i;
            ReadUtil.skipStream(stream, jqtFileMap.getGridLayoutBlock());
            Stream sstream = stream;
            if (jqtFileMap.gethGridLayoutBLock() != 0L) {
                sstream = ReadUtil.decompressData(sstream);
            }
            repInfo.setDefColWidth(ReadUtil.readIntValue(sstream));
            repInfo.setDefRowHeight(ReadUtil.readIntValue(sstream));
            int colCount = ReadUtil.readIntValue(sstream);
            int rowCount = ReadUtil.readIntValue(sstream);
            if (rowCount > 10000) {
                rowCount = 10000;
            }
            if (colCount <= 0 || rowCount <= 0) {
                throw new StreamException(repInfo.getCode() + "\uff1a\u89e3\u6790\u8868\u6837\u5b58\u5728\u5f02\u5e38\uff01");
            }
            int[] colWidthArr = new int[colCount - 1];
            int[] rowHeightArr = new int[rowCount - 1];
            for (i = 0; i < colCount - 1; ++i) {
                int curColWidth;
                colWidthArr[i] = curColWidth = ReadUtil.readIntValue(sstream);
            }
            for (i = 0; i < rowCount - 1; ++i) {
                int curRowHeight;
                rowHeightArr[i] = curRowHeight = ReadUtil.readIntValue(sstream);
            }
            repInfo.setColCount(colCount);
            repInfo.setRowCount(rowCount);
            repInfo.setColWidthArr(colWidthArr);
            repInfo.setRowHeightArr(rowHeightArr);
        }
        finally {
            stream.seek(SavePoint, 0);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void readTitles(Stream stream, JQTFileMap jqtFileMap, RepInfo repInfo) throws StreamException {
        long SavePoint = stream.getPosition();
        try {
            ReadUtil.skipStream(stream, jqtFileMap.getStrMatrixBlock());
            Stream isStream = stream;
            if (jqtFileMap.gethStrMatrixBlock() != 0L) {
                isStream = ReadUtil.decompressData(isStream);
            }
            int strMatrixColCount = ReadUtil.readIntValue(isStream);
            int strMatrixRowCount = ReadUtil.readIntValue(isStream);
            String[][] titleArr = new String[strMatrixColCount][strMatrixRowCount];
            for (int i = 0; i < strMatrixColCount; ++i) {
                for (int j = 0; j < strMatrixRowCount; ++j) {
                    int length = ReadUtil.readIntValue(isStream);
                    if (length == 0) {
                        titleArr[i][j] = "";
                        continue;
                    }
                    byte[] content = new byte[length];
                    isStream.read(content, 0, length);
                    titleArr[i][j] = ReadUtil.getFullString(content, false);
                }
            }
            repInfo.setTitleArr(titleArr);
        }
        finally {
            stream.seek(SavePoint, 0);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void readDatas(Stream stream, JQTFileMap jqtFileMap, RepInfo repInfo) throws StreamException {
        long SavePoint = stream.getPosition();
        try {
            ReadUtil.skipStream(stream, jqtFileMap.getAttrMatrixBlock());
            Stream isStream = stream;
            if (jqtFileMap.gethAttrMatrixBlock() != 0L) {
                isStream = ReadUtil.decompressData(stream);
            }
            int id = ReadUtil.readIntValue(isStream);
            int cc = ReadUtil.readIntValue(isStream);
            int rc = ReadUtil.readIntValue(isStream);
            long[][] dataArr = new long[cc][rc];
            for (int i = 0; i < cc; ++i) {
                for (int j = 0; j < rc; ++j) {
                    dataArr[i][j] = ReadUtil.readFlagValue(isStream);
                }
            }
            try {
                int headerRowCount;
                long[] headerColData = new long[cc];
                long[] headerRowData = new long[rc];
                int headerColCount = ReadUtil.readIntValue(isStream);
                if (headerColCount > 0) {
                    for (int j = 0; j < cc; ++j) {
                        headerColData[j] = j < headerColCount ? ReadUtil.readFlagValue(isStream) : 69763136L;
                    }
                }
                if ((headerRowCount = ReadUtil.readIntValue(isStream)) > 0) {
                    for (int j = 0; j < rc; ++j) {
                        headerRowData[j] = j < headerRowCount ? ReadUtil.readFlagValue(isStream) : 69763136L;
                    }
                }
                int idEx = ReadUtil.readIntValue(isStream);
                int ccEx = ReadUtil.readIntValue(isStream);
                int rcEx = ReadUtil.readIntValue(isStream);
                if (ccEx == cc && rcEx == rc) {
                    long[][] dataExArr = new long[cc][rc];
                    for (int i = 0; i < ccEx; ++i) {
                        for (int j = 0; j < rcEx; ++j) {
                            dataExArr[i][j] = ReadUtil.readFlagValue(isStream);
                        }
                    }
                    repInfo.setDataExArr(dataExArr);
                }
                repInfo.setHeaderColData(headerColData);
                repInfo.setHeaderRowData(headerRowData);
            }
            catch (RuntimeException runtimeException) {
                // empty catch block
            }
            repInfo.setDataArr(dataArr);
        }
        finally {
            stream.seek(SavePoint, 0);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void readApperance(Stream stream, JQTFileMap jqtFileMap, RepInfo repInfo) throws StreamException {
        long SavePoint = stream.getPosition();
        try {
            byte b1;
            int i;
            ReadUtil.skipStream(stream, jqtFileMap.getAppearanceBlock());
            Stream isStream = stream;
            if (jqtFileMap.gethAppearanceBlock() != 0) {
                isStream = ReadUtil.decompressData(stream);
            }
            boolean[] colHidden = new boolean[repInfo.getColCount()];
            boolean[] rowHidden = new boolean[repInfo.getRowCount()];
            String fontName = ReadUtil.readStreams(isStream);
            ReadUtil.skipStream(isStream, 10);
            int col = ReadUtil.readIntValue(isStream);
            int row = ReadUtil.readIntValue(isStream);
            col = ReadUtil.readIntValue(isStream);
            row = ReadUtil.readIntValue(isStream);
            repInfo.setHeadCol(col);
            repInfo.setHeadRow(row);
            int totalCount = ReadUtil.readIntValue(isStream);
            int[] colHeader = new int[totalCount];
            for (int i2 = 0; i2 < totalCount; ++i2) {
                colHeader[i2] = ReadUtil.readSmallIntValue(isStream);
            }
            totalCount = ReadUtil.readIntValue(isStream);
            int[] rowHeader = new int[totalCount];
            for (int i3 = 0; i3 < totalCount; ++i3) {
                rowHeader[i3] = ReadUtil.readSmallIntValue(isStream);
            }
            boolean autoHeadLock = ReadUtil.readByteValue(isStream) == 1;
            boolean b = false;
            for (i = 0; i < repInfo.getColCount(); ++i) {
                b1 = ReadUtil.readByteValue(isStream);
                b = false;
                b = b1 == 1;
                colHidden[i] = b;
            }
            for (i = 0; i < repInfo.getRowCount(); ++i) {
                b1 = ReadUtil.readByteValue(isStream);
                b = false;
                b = b1 == 1;
                rowHidden[i] = b;
            }
            repInfo.setRowHidden(rowHidden);
            repInfo.setColHidden(colHidden);
        }
        finally {
            stream.seek(SavePoint, 0);
        }
    }

    private void saveReportDataImpl(RepInfo repInfo) {
        int i;
        boolean index = false;
        int[] colWidthArr = repInfo.getColWidthArr();
        int[] rowHeightArr = repInfo.getRowHeightArr();
        Grid2Data gridData = new Grid2Data();
        gridData.setColumnCount(repInfo.getColCount());
        gridData.setRowCount(repInfo.getRowCount());
        gridData.setColumnWidth(0, 55);
        for (i = 0; i < colWidthArr.length; ++i) {
            gridData.setColumnWidth(i + 1, colWidthArr[i]);
        }
        for (i = 0; i < rowHeightArr.length; ++i) {
            gridData.setRowHeight(i + 1, rowHeightArr[i]);
        }
        String[][] titleArr = repInfo.getTitleArr();
        int colCount = titleArr.length;
        int rowCount = 0;
        if (colCount > 0) {
            rowCount = titleArr[0].length;
        }
        for (int i2 = 0; i2 < colCount; ++i2) {
            for (int j = 0; j < rowCount; ++j) {
                GridCellData cell = gridData.getGridCellData(i2 + 1, j + 1);
                String aCode = titleArr[i2][j];
                if (StringUtils.isNotEmpty((String)aCode)) {
                    aCode = aCode.replace("\n\r\r", "\n\r");
                }
                cell.setEditText(aCode);
                cell.setShowText(aCode);
            }
        }
        long[][] dataArr = repInfo.getDataArr();
        long[][] dataExArr = repInfo.getDataExArr();
        for (int i3 = 0; i3 < dataArr.length; ++i3) {
            for (int j = 0; j < dataArr[0].length; ++j) {
                long data;
                GridCellData cell = gridData.getGridCellData(i3 + 1, j + 1);
                long rightData = data = dataArr[i3][j];
                long bottomData = data;
                int rowSpan = 0;
                int colSpan = 0;
                while (Utils.IsMergeBottom(bottomData)) {
                    bottomData = dataArr[i3][j + ++rowSpan];
                }
                if (Utils.IsMergeLeft(data)) continue;
                while (Utils.IsMergeRight(rightData)) {
                    rightData = dataArr[i3 + ++colSpan][j];
                }
                if (colSpan <= 0 && rowSpan <= 0) continue;
                gridData.mergeCells(i3 + 1, j + 1, i3 + 1 + colSpan, j + 1 + rowSpan);
            }
        }
        boolean hasFont = false;
        if (dataExArr != null && dataExArr.length == dataArr.length && dataExArr[0].length == dataArr[0].length) {
            hasFont = true;
        }
        for (int i4 = -1; i4 < dataArr.length; ++i4) {
            for (int j = -1; j < dataArr[0].length; ++j) {
                boolean isItalic;
                boolean isBold;
                boolean isfitFontSize;
                boolean emptyCell;
                GridCellData cell = gridData.getGridCellData(i4 + 1, j + 1);
                long data = 0L;
                data = i4 == -1 && j == -1 ? 69894208L : (i4 == -1 ? repInfo.getHeaderRowData()[j] : (j == -1 ? repInfo.getHeaderColData()[i4] : dataArr[i4][j]));
                long rightData = data;
                long bottomData = data;
                long colorData = data;
                int rowSpan = 0;
                while (Utils.IsMergeBottom(bottomData)) {
                    bottomData = dataArr[i4][j + ++rowSpan];
                }
                int color = (int)(colorData & 0xF000000L);
                color >>= 24;
                if ((color = 15 - color) >= 0 && color < ReportConsts.COLORARR.length) {
                    cell.setBackGroundColor(ReportConsts.COLORARR[color]);
                }
                int cellBGStyle = (int)(data & 0xE00000L);
                cellBGStyle >>= 21;
                cellBGStyle = this.getGrid2CellBGStyle(cellBGStyle);
                cell.setBackGroundStyle(cellBGStyle);
                boolean silverHead = (data & 0x2000L) != 0L;
                cell.setSilverHead(silverHead);
                boolean autoFit = (data & 0x800L) != 0L;
                cell.setFitFontSize(autoFit);
                boolean editAble = (data & 0xFFFFFFFFA000C000L) == 0L;
                cell.setEditable(editAble);
                boolean bl = emptyCell = (data & 0x4000L) != 0L;
                if (emptyCell) {
                    cell.setEditable(false);
                    int lineStyle = (int)(data & 0xFL);
                    this.setCellEmptyStyle(lineStyle >>= 0, cell);
                }
                this.setCellStyleAndColor(data, cell, gridData);
                boolean vertText = (data & 0x1000L) != 0L;
                cell.setVertText(vertText);
                try {
                    int horzAlign = (int)(data & 0xC0L);
                    if ((horzAlign >>= 6) == 3) {
                        horzAlign = 1;
                    }
                    cell.setHorzAlign(ReportConsts.HTEXTALIGN[horzAlign]);
                }
                catch (RuntimeException e) {
                    cell.setHorzAlign(1);
                }
                try {
                    int vertAlign = (int)(data & 0x30L);
                    if ((vertAlign >>= 4) == 3) {
                        vertAlign = 0;
                    }
                    cell.setVertAlign(ReportConsts.VTEXTALIGN[vertAlign]);
                }
                catch (RuntimeException e) {
                    cell.setVertAlign(3);
                }
                boolean wrapLine = (data & 0x400L) != 0L;
                cell.setWrapLine(wrapLine);
                boolean bl2 = isfitFontSize = (data & 0x800L) != 0L;
                if (isfitFontSize) {
                    cell.setFitFontSize(isfitFontSize);
                }
                int style = 0;
                boolean bl3 = isBold = (data & 0x200L) != 0L;
                if (isBold) {
                    style |= 2;
                }
                boolean bl4 = isItalic = (data & 0x100L) != 0L;
                if (isItalic) {
                    style |= 4;
                }
                if (hasFont && i4 >= 0 && j >= 0) {
                    try {
                        boolean isStrikeOut;
                        boolean isUnderLine;
                        long dataEx = dataExArr[i4][j];
                        int fontIdx = (int)(dataEx & 0xFFL);
                        String fontName = ReportConsts.FONTNAMESETS[fontIdx];
                        if (fontIdx <= 0) {
                            fontName = "\u5b8b\u4f53";
                        }
                        cell.setFontName(fontName);
                        int fontSize = (int)(dataEx & 0xFF00L);
                        if ((fontSize >>= 8) == 0) {
                            fontSize = 10;
                        }
                        cell.setPersistenceData("fontSize", String.valueOf(fontSize));
                        float oldPixelValue = (float)fontSize * 96.0f / 72.0f;
                        int pixelValue = Math.round(oldPixelValue);
                        if ((float)pixelValue > oldPixelValue) {
                            --pixelValue;
                        }
                        cell.setFontSize(pixelValue);
                        boolean bl5 = isUnderLine = (dataEx & 0x1000000L) != 0L;
                        if (isUnderLine) {
                            style |= 8;
                        }
                        boolean bl6 = isStrikeOut = (dataEx & 0x2000000L) != 0L;
                        if (isStrikeOut) {
                            style |= 0x10;
                        }
                        cell.setForeGroundColor(0);
                    }
                    catch (RuntimeException runtimeException) {
                        // empty catch block
                    }
                }
                cell.setFontStyle(style);
                if (!emptyCell || !StringUtils.isEmpty((String)cell.getEditText())) continue;
                cell.setEditText("\u2014\u2014");
                cell.setShowText("\u2014\u2014");
                cell.setHorzAlign(ReportConsts.HTEXTALIGN[1]);
                cell.setEditable(false);
            }
        }
        GridCellData c00 = gridData.getGridCellData(0, 0);
        c00.setBottomBorderStyle(1);
        c00.setRightBorderStyle(1);
        boolean[] rowsHidden = repInfo.getRowHidden();
        for (int i5 = 0; i5 < gridData.getRowCount(); ++i5) {
            gridData.setRowHidden(i5, rowsHidden[i5]);
        }
        boolean[] colsHidden = repInfo.getColHidden();
        for (int i6 = 0; i6 < gridData.getColumnCount(); ++i6) {
            gridData.setColumnHidden(i6, colsHidden[i6]);
        }
        gridData.setHeaderColumnCount(repInfo.getHeadCol());
        gridData.setHeaderRowCount(repInfo.getHeadRow());
        ReportDataImpl dataImpl = new ReportDataImpl();
        dataImpl.setData(Grid2Data.gridToBytes((Grid2Data)gridData));
        dataImpl.setColCount(gridData.getColumnCount());
        dataImpl.setRowCount(gridData.getRowCount());
        repInfo.setReportData(dataImpl);
    }

    private int getGrid2CellBGStyle(int singleStype) {
        return GridEnums.JIOBackgroundstyle.valueOf((int)singleStype).getMap();
    }

    private void setCellEmptyStyle(int lineStyle, GridCellData cell) {
        switch (lineStyle) {
            case 0: {
                break;
            }
            case 1: {
                break;
            }
            case 2: {
                cell.setDiagonalBorderStyle(1);
                break;
            }
            case 3: {
                cell.setInverseDiagonalBorderStyle(1);
                break;
            }
            case 4: {
                cell.setDiagonalBorderStyle(1);
                cell.setInverseDiagonalBorderStyle(1);
            }
        }
    }

    private void setCellStyleAndColor(long data, GridCellData cell, Grid2Data gridData) {
        int horzEdge = (int)(data & 0xC0000L);
        switch (horzEdge >>= 18) {
            case 0: {
                cell.setBottomBorderStyle(1);
                break;
            }
            case 1: {
                cell.setBottomBorderStyle(0);
                break;
            }
            case 2: {
                cell.setBottomBorderStyle(-1);
                break;
            }
            case 3: {
                boolean ss = true;
                cell.setBottomBorderStyle(4);
            }
        }
        if (cell.getRowIndex() > 1) {
            GridCellData ss = gridData.getGridCellData(cell.getColIndex(), cell.getRowIndex() - 1);
        }
        int vertEdge = (int)(data & 0x30000L);
        switch (vertEdge >>= 16) {
            case 0: {
                cell.setRightBorderStyle(1);
                break;
            }
            case 1: {
                cell.setRightBorderStyle(0);
                break;
            }
            case 2: {
                cell.setRightBorderStyle(-1);
                break;
            }
            case 3: {
                boolean ss = true;
                cell.setRightBorderStyle(4);
            }
        }
        if (cell.getColIndex() > 1) {
            GridCellData gridCellData = gridData.getGridCellData(cell.getColIndex() - 1, cell.getRowIndex());
        }
    }
}

