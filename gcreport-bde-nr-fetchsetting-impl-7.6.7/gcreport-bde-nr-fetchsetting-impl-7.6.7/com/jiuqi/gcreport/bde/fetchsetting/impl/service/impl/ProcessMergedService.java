/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.RegionTypeEnum
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service.impl;

import com.jiuqi.bde.common.constant.RegionTypeEnum;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.ExcelRegionInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.CollectionUtils;

public class ProcessMergedService {
    public void processMergedRegions(ExportContext context, ExportExcelSheet excelSheet) {
        List<CellRangeAddress> headCellRangeAddresses;
        Integer columnMaxIndex = excelSheet.getColumnMaxIndex();
        if (columnMaxIndex != -1 && !CollectionUtils.isEmpty(headCellRangeAddresses = "\u586b\u62a5\u8bf4\u660e".equals(excelSheet.getSheetName()) ? this.buildExplainAutoMergeHead(excelSheet, context) : this.buildAutoMergeHead(excelSheet, context))) {
            excelSheet.getCellRangeAddresses().addAll(headCellRangeAddresses);
        }
    }

    private List<CellRangeAddress> buildExplainAutoMergeHead(ExportExcelSheet excelSheet, ExportContext context) {
        ArrayList<CellRangeAddress> cellRangeList = new ArrayList<CellRangeAddress>();
        List rowDatas = excelSheet.getRowDatas();
        List fetchSourceVOS = (List)context.getVarMap().get("FETCH_SOURCE");
        int offset = fetchSourceVOS.size();
        cellRangeList.add(new CellRangeAddress(0, 0, 0, 1));
        cellRangeList.add(new CellRangeAddress(25, 25, 0, 1));
        cellRangeList.add(new CellRangeAddress(29 + offset, 29 + offset, 0, 1));
        List<Integer> indexList = Arrays.asList(31 + offset, 48 + offset, 59 + offset, 70 + offset);
        for (Integer index : indexList) {
            int col = 0;
            List<Object> rowData = Arrays.asList((Object[])rowDatas.get(index));
            block1: while (col < rowData.size() - 1) {
                int colNum = col;
                int lastCol = 0;
                String headName = rowData.get(col).toString();
                while (true) {
                    if (col > rowData.size() - 1 || !headName.equals(rowData.get(col).toString())) {
                        if (colNum == lastCol) continue block1;
                        for (int i = 0; i < indexList.size() - 1; ++i) {
                            cellRangeList.add(new CellRangeAddress(index, index, colNum, lastCol));
                        }
                        continue block1;
                    }
                    lastCol = col++;
                }
            }
        }
        for (Integer index : indexList) {
            for (int i = 0; i < ((Object[])rowDatas.get(index)).length - 1; ++i) {
                if (!((Object[])rowDatas.get(index))[i].equals(((Object[])rowDatas.get(index + 1))[i])) continue;
                cellRangeList.add(new CellRangeAddress(index, index + 1, i, i));
            }
        }
        return cellRangeList;
    }

    private List<CellRangeAddress> buildAutoMergeHead(ExportExcelSheet excelSheet, ExportContext context) {
        List excelRegionInfos = (List)context.getVarMap().get(String.valueOf(excelSheet.getSheetNo()));
        ArrayList<CellRangeAddress> cellRangeList = new ArrayList<CellRangeAddress>();
        List rowDatas = excelSheet.getRowDatas();
        for (int headIndex = 0; headIndex < excelRegionInfos.size(); ++headIndex) {
            int index = RegionTypeEnum.FLOAT.equals((Object)((ExcelRegionInfo)excelRegionInfos.get(headIndex)).getRegionTypeEnum()) ? ((ExcelRegionInfo)excelRegionInfos.get(headIndex)).getStartIndex() + 2 : ((ExcelRegionInfo)excelRegionInfos.get(headIndex)).getStartIndex();
            Object[] rowData = (Object[])rowDatas.get(index);
            int col = 0;
            block1: while (col < rowData.length - 1) {
                int colNum = col;
                int lastCol = 0;
                String headName = rowData[col].toString();
                while (true) {
                    if (col > rowData.length - 1 || !headName.equals(rowData[col].toString())) {
                        if (colNum == lastCol) continue block1;
                        cellRangeList.add(new CellRangeAddress(index, index, colNum, lastCol));
                        continue block1;
                    }
                    lastCol = col++;
                }
            }
            for (int columnIndex = 0; columnIndex < rowData.length; ++columnIndex) {
                if (!rowData[columnIndex].equals(((Object[])rowDatas.get(index + 1))[columnIndex])) continue;
                cellRangeList.add(new CellRangeAddress(index, index + 1, columnIndex, columnIndex));
            }
        }
        return cellRangeList;
    }
}

