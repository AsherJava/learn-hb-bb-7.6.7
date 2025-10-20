/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.excel.metadata.Head
 *  com.alibaba.excel.metadata.data.WriteCellData
 *  com.alibaba.excel.write.handler.CellWriteHandler
 *  com.alibaba.excel.write.metadata.holder.WriteSheetHolder
 *  com.alibaba.excel.write.metadata.holder.WriteTableHolder
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.util.CellRangeAddress
 */
package com.jiuqi.dc.adjustvchr.impl.impexp;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelFillCellMergeStrategy
implements CellWriteHandler {
    private int[] mergeColumnIndex;
    private int mergeRowIndex;
    private List<Integer> mergeRowNode;

    public ExcelFillCellMergeStrategy() {
    }

    public ExcelFillCellMergeStrategy(int mergeRowIndex, int[] mergeColumnIndex, List<Integer> mergeRowNode) {
        this.mergeRowIndex = mergeRowIndex;
        this.mergeColumnIndex = mergeColumnIndex;
        this.mergeRowNode = mergeRowNode;
    }

    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> list, Cell cell, Head head, Integer integer, Boolean aBoolean) {
        int curRowIndex = cell.getRowIndex();
        int curColIndex = cell.getColumnIndex();
        if (curRowIndex > this.mergeRowIndex) {
            for (int columnIndex : this.mergeColumnIndex) {
                if (curColIndex != columnIndex) continue;
                this.mergeWithPrevRow(writeSheetHolder, cell, curRowIndex, curColIndex);
                break;
            }
        }
    }

    private void mergeWithPrevRow(WriteSheetHolder writeSheetHolder, Cell cell, int curRowIndex, int curColIndex) {
        Row preRow = cell.getSheet().getRow(curRowIndex - 1);
        if (preRow == null) {
            preRow = writeSheetHolder.getCachedSheet().getRow(curRowIndex - 1);
        }
        if (!this.mergeRowNode.contains(curRowIndex)) {
            Sheet sheet = writeSheetHolder.getSheet();
            List mergeRegions = sheet.getMergedRegions();
            boolean isMerged = false;
            for (int i = 0; i < mergeRegions.size() && !isMerged; ++i) {
                CellRangeAddress cellRangeAddr = (CellRangeAddress)mergeRegions.get(i);
                if (!cellRangeAddr.isInRange(curRowIndex - 1, curColIndex)) continue;
                sheet.removeMergedRegion(i);
                cellRangeAddr.setLastRow(curRowIndex);
                sheet.addMergedRegion(cellRangeAddr);
                isMerged = true;
            }
            if (!isMerged) {
                CellRangeAddress cellRangeAddress = new CellRangeAddress(curRowIndex - 1, curRowIndex, curColIndex, curColIndex);
                sheet.addMergedRegion(cellRangeAddress);
            }
        }
    }
}

