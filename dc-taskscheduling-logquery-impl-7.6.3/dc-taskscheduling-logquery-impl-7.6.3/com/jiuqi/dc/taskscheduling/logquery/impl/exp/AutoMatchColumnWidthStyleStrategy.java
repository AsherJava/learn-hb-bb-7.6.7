/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.excel.enums.CellDataTypeEnum
 *  com.alibaba.excel.metadata.Head
 *  com.alibaba.excel.metadata.data.WriteCellData
 *  com.alibaba.excel.util.MapUtils
 *  com.alibaba.excel.write.metadata.holder.WriteSheetHolder
 *  com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy
 *  org.apache.commons.collections4.CollectionUtils
 *  org.apache.poi.ss.usermodel.Cell
 */
package com.jiuqi.dc.taskscheduling.logquery.impl.exp;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;

public class AutoMatchColumnWidthStyleStrategy
extends AbstractColumnWidthStyleStrategy {
    private int maxWidth = 100;
    private final Map<Integer, Map<Integer, Integer>> cache = MapUtils.newHashMapWithExpectedSize((int)8);

    public AutoMatchColumnWidthStyleStrategy(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        Integer maxColumnWidth;
        boolean needSetWidth;
        boolean bl = needSetWidth = isHead != false || !CollectionUtils.isEmpty(cellDataList);
        if (!needSetWidth) {
            return;
        }
        Map maxColumnWidthMap = this.cache.computeIfAbsent(writeSheetHolder.getSheetNo(), key -> new HashMap(16));
        Integer columnWidth = this.dataLength(cellDataList, cell, isHead);
        if (columnWidth < 0) {
            return;
        }
        if (columnWidth > this.maxWidth) {
            columnWidth = this.maxWidth;
        }
        if ((maxColumnWidth = (Integer)maxColumnWidthMap.get(cell.getColumnIndex())) == null || columnWidth > maxColumnWidth) {
            maxColumnWidthMap.put(cell.getColumnIndex(), columnWidth);
            writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), columnWidth * 256);
        }
    }

    private Integer dataLength(List<WriteCellData<?>> cellDataList, Cell cell, Boolean isHead) {
        if (isHead.booleanValue()) {
            return cell.getStringCellValue().getBytes().length;
        }
        WriteCellData<?> cellData = cellDataList.get(0);
        CellDataTypeEnum type = cellData.getType();
        if (type == null) {
            return -1;
        }
        switch (type) {
            case STRING: {
                return cellData.getStringValue().getBytes().length;
            }
            case BOOLEAN: {
                return cellData.getBooleanValue().toString().getBytes().length;
            }
            case NUMBER: {
                return cellData.getNumberValue().toString().getBytes().length;
            }
        }
        return -1;
    }
}

