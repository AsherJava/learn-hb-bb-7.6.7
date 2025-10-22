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
package com.jiuqi.nr.single.core.para.maker.table;

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
import com.jiuqi.nr.single.core.para.util.WriteUtil;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ReportMaker {
    private Map<String, ReportFormImpl> repMap;
    private boolean isFloat = false;

    public ReportMaker(Map<String, ReportFormImpl> repMap) {
        this.repMap = repMap;
    }

    public final RepInfo savePara(RepInfo repInfo, String filePath, ParaInfo info, JQTFileMap map, String name, boolean isFloat) throws StreamException, IOException {
        this.isFloat = isFloat;
        return this.writeReport(repInfo, filePath, info, map, name);
    }

    private RepInfo writeReport(RepInfo repInfo, String filePath, ParaInfo info, JQTFileMap jqtFileMap, String name) throws StreamException, IOException {
        File aFile = new File(filePath);
        if (!aFile.exists()) {
            return null;
        }
        if (jqtFileMap == null) {
            return repInfo;
        }
        MemStream stream = null;
        stream = new MemStream();
        stream.loadFromFile(filePath);
        stream.seek(0L, 0);
        this.saveReport((Stream)stream, jqtFileMap, info, repInfo);
        return repInfo;
    }

    public void saveReport(Stream mask0, JQTFileMap jqtFileMap, ParaInfo info, RepInfo repInfo) throws StreamException {
        jqtFileMap.setCode(repInfo.getCode());
        this.writeRowColSize(mask0, jqtFileMap, info, repInfo);
        this.writeTitles(mask0, jqtFileMap, repInfo);
        this.writeDatas(mask0, jqtFileMap, info, repInfo);
        this.writeApperance(mask0, jqtFileMap, repInfo);
        this.saveReportDataImpl(repInfo);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void writeRowColSize(Stream stream, JQTFileMap jqtFileMap, ParaInfo info, RepInfo repInfo) throws StreamException {
        long SavePoint = stream.getPosition();
        try {
            int i;
            ReadUtil.skipStream(stream, jqtFileMap.getGridLayoutBlock());
            Stream sstream = stream;
            if (jqtFileMap.gethGridLayoutBLock() != 0L) {
                sstream = ReadUtil.decompressData(sstream);
            }
            WriteUtil.writeIntValue(sstream, repInfo.getDefColWidth());
            WriteUtil.writeIntValue(sstream, repInfo.getDefRowHeight());
            WriteUtil.writeIntValue(sstream, repInfo.getColCount());
            WriteUtil.writeIntValue(sstream, repInfo.getRowCount());
            for (i = 0; i < repInfo.getColWidthArr().length - 1; ++i) {
                WriteUtil.writeIntValue(sstream, repInfo.getColWidthArr()[i]);
            }
            for (i = 0; i < repInfo.getRowHeightArr().length - 1; ++i) {
                WriteUtil.writeIntValue(sstream, repInfo.getRowHeightArr()[i]);
            }
        }
        finally {
            stream.seek(SavePoint, 0);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void writeTitles(Stream stream, JQTFileMap jqtFileMap, RepInfo repInfo) throws StreamException {
        long SavePoint = stream.getPosition();
        try {
            ReadUtil.skipStream(stream, jqtFileMap.getStrMatrixBlock());
            Stream isStream = stream;
            if (jqtFileMap.gethStrMatrixBlock() != 0L) {
                isStream = ReadUtil.decompressData(isStream);
            }
            String[][] titleArr = repInfo.getTitleArr();
            int strMatrixColCount = titleArr.length;
            int strMatrixRowCount = titleArr[0].length;
            WriteUtil.writeIntValue(isStream, strMatrixColCount);
            WriteUtil.writeIntValue(isStream, strMatrixRowCount);
            for (int i = 0; i < strMatrixColCount; ++i) {
                for (int j = 0; j < strMatrixRowCount; ++j) {
                    int length = 0;
                    if (StringUtils.isNotEmpty((String)titleArr[i][j])) {
                        byte[] content = ReadUtil.getStringBytes(titleArr[i][j]);
                        if (content != null) {
                            length = content.length;
                        }
                        WriteUtil.writeIntValue(isStream, length);
                        if (length <= 0) continue;
                        isStream.write(content, 0, length);
                        continue;
                    }
                    length = 0;
                    WriteUtil.writeIntValue(isStream, length);
                }
            }
        }
        finally {
            stream.seek(SavePoint, 0);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void writeDatas(Stream stream, JQTFileMap jqtFileMap, ParaInfo info, RepInfo repInfo) throws StreamException {
        long SavePoint = stream.getPosition();
        try {
            ReadUtil.skipStream(stream, jqtFileMap.getAttrMatrixBlock());
            Stream isStream = stream;
            if (jqtFileMap.gethAttrMatrixBlock() != 0L) {
                isStream = ReadUtil.decompressData(stream);
            }
            int id = 4;
            WriteUtil.writeIntValue(isStream, id);
            int cc = repInfo.getDataArr().length;
            int rc = repInfo.getDataArr()[0].length;
            WriteUtil.writeIntValue(isStream, cc);
            WriteUtil.writeIntValue(isStream, rc);
            long[][] dataArr = repInfo.getDataArr();
            for (int i = 0; i < cc; ++i) {
                for (int j = 0; j < rc; ++j) {
                    WriteUtil.writeFlagValue(isStream, dataArr[i][j]);
                }
            }
            try {
                Grid2Data gridData = Grid2Data.bytesToGrid((byte[])repInfo.getReportData().getGridBytes());
                int colHeaderCount = gridData.getHeaderColumnCount();
                WriteUtil.writeIntValue(isStream, colHeaderCount);
                if (colHeaderCount > 0) {
                    WriteUtil.writeSkipStream(isStream, 4 * cc);
                }
                int rowHeaderCount = gridData.getHeaderRowCount();
                WriteUtil.writeIntValue(isStream, colHeaderCount);
                if (rowHeaderCount > 0) {
                    WriteUtil.writeSkipStream(isStream, 4 * rc);
                }
                int idEx = 4;
                WriteUtil.writeIntValue(isStream, idEx);
                int ccEx = repInfo.getDataExArr().length;
                int rcEx = repInfo.getDataExArr()[0].length;
                WriteUtil.writeIntValue(isStream, ccEx);
                WriteUtil.writeIntValue(isStream, rcEx);
                if (ccEx == cc && rcEx == rc) {
                    long[][] dataExArr = repInfo.getDataExArr();
                    for (int i = 0; i < ccEx; ++i) {
                        for (int j = 0; j < rcEx; ++j) {
                            WriteUtil.writeFlagValue(isStream, dataExArr[i][j]);
                        }
                    }
                }
            }
            catch (RuntimeException runtimeException) {
                // empty catch block
            }
        }
        finally {
            stream.seek(SavePoint, 0);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void writeApperance(Stream stream, JQTFileMap jqtFileMap, RepInfo repInfo) throws StreamException {
        long SavePoint = stream.getPosition();
        try {
            byte b1;
            int i;
            ReadUtil.skipStream(stream, jqtFileMap.getAppearanceBlock());
            Stream isStream = stream;
            if (jqtFileMap.gethAppearanceBlock() != 0) {
                isStream = ReadUtil.decompressData(stream);
            }
            boolean[] colHidden = repInfo.getColHidden();
            boolean[] rowHidden = repInfo.getRowHidden();
            String fontName = "\u5b8b\u4f53";
            WriteUtil.writeStreams(isStream, fontName);
            WriteUtil.writeSkipStream(isStream, 10);
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
            ReadUtil.readByteValue(isStream);
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
        this.calcMergeCells(gridData, dataArr, dataExArr);
        this.setCellAttr(gridData, dataArr, dataExArr);
        GridCellData c00 = gridData.getGridCellData(0, 0);
        c00.setBottomBorderStyle(1);
        c00.setRightBorderStyle(1);
        boolean[] rowsHidden = repInfo.getRowHidden();
        for (int i3 = 0; i3 < gridData.getRowCount(); ++i3) {
            gridData.setRowHidden(i3, rowsHidden[i3]);
        }
        boolean[] colsHidden = repInfo.getColHidden();
        for (int i4 = 0; i4 < gridData.getColumnCount(); ++i4) {
            gridData.setColumnHidden(i4, colsHidden[i4]);
        }
        gridData.setHeaderColumnCount(repInfo.getHeadCol());
        gridData.setHeaderRowCount(repInfo.getHeadRow());
        ReportDataImpl dataImpl = new ReportDataImpl();
        dataImpl.setData(Grid2Data.gridToBytes((Grid2Data)gridData));
        dataImpl.setColCount(gridData.getColumnCount());
        dataImpl.setRowCount(gridData.getRowCount());
        repInfo.setReportData(dataImpl);
    }

    private void calcMergeCells(Grid2Data gridData, long[][] dataArr, long[][] dataExArr) {
        for (int i = 0; i < dataArr.length; ++i) {
            for (int j = 0; j < dataArr[0].length; ++j) {
                long data;
                GridCellData cell = gridData.getGridCellData(i + 1, j + 1);
                long rightData = data = dataArr[i][j];
                long bottomData = data;
                int rowSpan = 0;
                int colSpan = 0;
                while (Utils.IsMergeBottom(bottomData)) {
                    bottomData = dataArr[i][j + ++rowSpan];
                }
                if (Utils.IsMergeLeft(data)) continue;
                while (Utils.IsMergeRight(rightData)) {
                    rightData = dataArr[i + ++colSpan][j];
                }
                if (colSpan <= 0 && rowSpan <= 0) continue;
                gridData.mergeCells(i + 1, j + 1, i + 1 + colSpan, j + 1 + rowSpan);
            }
        }
    }

    private void setCellAttr(Grid2Data gridData, long[][] dataArr, long[][] dataExArr) {
        boolean hasFont = false;
        if (dataExArr != null && dataExArr.length == dataArr.length && dataExArr[0].length == dataArr[0].length) {
            hasFont = true;
        }
        for (int i = 0; i < dataArr.length; ++i) {
            for (int j = 0; j < dataArr[0].length; ++j) {
                this.setCellAttr2(gridData, dataArr, dataExArr, i, j, hasFont);
            }
        }
    }

    private void setCellAttr2(Grid2Data gridData, long[][] dataArr, long[][] dataExArr, int i, int j, boolean hasFont) {
        boolean isfitFontSize;
        boolean emptyCell;
        GridCellData cell = gridData.getGridCellData(i + 1, j + 1);
        long data = dataArr[i][j];
        long dataEx = dataExArr[i][j];
        long rightData = data;
        long bottomData = data;
        long colorData = data;
        int rowSpan = 0;
        while (Utils.IsMergeBottom(bottomData)) {
            bottomData = dataArr[i][j + ++rowSpan];
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
        this.setFontStyle(data, dataEx, hasFont, cell);
    }

    private void setFontStyle(long data, long dataEx, boolean hasFont, GridCellData cell) {
        boolean isItalic;
        boolean isBold;
        int style = 0;
        boolean bl = isBold = (data & 0x200L) != 0L;
        if (isBold) {
            style |= 2;
        }
        boolean bl2 = isItalic = (data & 0x100L) != 0L;
        if (isItalic) {
            style |= 4;
        }
        if (hasFont) {
            try {
                boolean isStrikeOut;
                boolean isUnderLine;
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
                int pixelValue = Math.round((float)fontSize * 96.0f / 72.0f);
                cell.setFontSize(pixelValue);
                boolean bl3 = isUnderLine = (dataEx & 0x1000000L) != 0L;
                if (isUnderLine) {
                    style |= 8;
                }
                boolean bl4 = isStrikeOut = (dataEx & 0x2000000L) != 0L;
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

