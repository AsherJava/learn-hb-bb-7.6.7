/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.itextpdf.layout.Document
 *  com.itextpdf.layout.IPropertyContainer
 *  com.itextpdf.layout.element.Cell
 *  com.itextpdf.layout.element.Div
 *  com.itextpdf.layout.element.IBlockElement
 *  com.itextpdf.layout.element.Paragraph
 *  com.itextpdf.layout.element.Table
 *  com.jiuqi.va.print.adapt.PdfHandler
 *  com.jiuqi.va.query.exception.DefinedQueryRuntimeException
 *  com.jiuqi.va.query.template.enumerate.ParamTypeEnum
 */
package com.jiuqi.va.query.print;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.jiuqi.va.print.adapt.PdfHandler;
import com.jiuqi.va.query.common.service.FormulaExecuteHandlerUtil;
import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import com.jiuqi.va.query.print.GridCellProp;
import com.jiuqi.va.query.print.TableCellData;
import com.jiuqi.va.query.print.TableCellProp;
import com.jiuqi.va.query.print.TableGridData;
import com.jiuqi.va.query.print.TablePrintControl;
import com.jiuqi.va.query.print.TableRowTypeEnum;
import com.jiuqi.va.query.print.domain.QueryPrintThreadLocal;
import com.jiuqi.va.query.print.domain.TablePrintDrawContext;
import com.jiuqi.va.query.print.domain.tablle.CustomCell;
import com.jiuqi.va.query.print.domain.tablle.CustomTable;
import com.jiuqi.va.query.print.utils.QueryPrintControlUtils;
import com.jiuqi.va.query.template.enumerate.ParamTypeEnum;
import com.jiuqi.va.query.util.QueryPrintCellUtils;
import com.jiuqi.va.query.util.QueryPrintUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class PrintTableDraw {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    List<IBlockElement> drawControl(Document document, List<Map<String, Object>> tableData, Map<String, Object> tableConfig) {
        ArrayList<IBlockElement> elements = new ArrayList<IBlockElement>();
        if (Objects.isNull(tableData)) {
            return elements;
        }
        try {
            TablePrintControl tablePrintControl = new TablePrintControl();
            tablePrintControl.setProps(tableConfig);
            tablePrintControl.setParentWidth(QueryPrintUtil.getDocumentAvailableWidth(document));
            Map<String, GridCellProp> cellPropMap = PrintTableDraw.getTableCellPropMap(tablePrintControl);
            this.initParamThreadLocal(cellPropMap);
            TableGridData totalRows = this.getTotalRows(tablePrintControl, cellPropMap, tableData);
            List<IBlockElement> list = this.getBlockElements(document, tablePrintControl, totalRows);
            return list;
        }
        finally {
            QueryPrintThreadLocal.removeTablePrintLocal();
            QueryPrintThreadLocal.removeTablePrintCellContextLocal();
        }
    }

    public List<IBlockElement> getBlockElements(Document document, TablePrintControl tablePrintControl, TableGridData totalRows) {
        ArrayList<IBlockElement> elements = new ArrayList<IBlockElement>();
        if (Objects.isNull(totalRows)) {
            return elements;
        }
        int pdfPageCount = 0;
        HashMap<Integer, Table> tableMap = new HashMap<Integer, Table>(16);
        CustomTable pdfTable = QueryPrintUtil.newTable(tablePrintControl);
        tableMap.put(pdfPageCount, pdfTable);
        float borderOutSideWidth = tablePrintControl.getBorder().getWidth();
        float pageHeight = QueryPrintUtil.getDocumentAvailableHeight(document);
        float documentHeight = pageHeight - PdfHandler.getMarginBottom((IPropertyContainer)pdfTable) - PdfHandler.getMarginTop((IPropertyContainer)pdfTable) - borderOutSideWidth;
        QueryPrintUtil.setDocumentAvailableHeight(document, documentHeight);
        boolean excludeEndFlag = true;
        Map<Integer, Map<Integer, TableCellProp>> excludeEndMap = QueryPrintThreadLocal.getTablePrintDrawContext().getExcludeEndMap();
        HashMap<Integer, Integer> everyTableFloatRowMap = new HashMap<Integer, Integer>(16);
        TableGridData everyPageRows = new TableGridData();
        int rowSize = totalRows.getRowSize();
        int columns = tablePrintControl.getColumns();
        Map<String, GridCellProp> cellPropMap = QueryPrintThreadLocal.getTablePrintDrawContext().getCellPropMap();
        for (int i = 0; i < rowSize; ++i) {
            float maxCellHeight = 0.0f;
            TableRowTypeEnum rowType = totalRows.getRowType(i);
            if (rowType == TableRowTypeEnum.everyPage) {
                List<TableCellData> rowData = totalRows.getRowData(i);
                everyPageRows.addRow(rowData);
            }
            List<CustomCell> currentRowCells = new ArrayList<CustomCell>();
            ArrayList<TableCellData> currentRowDrawedList = new ArrayList<TableCellData>();
            for (int j = 0; j < columns; ++j) {
                TableCellData cellData = totalRows.getCell(i, j);
                if (cellData == null || cellData.getSpanColumn() == 0) continue;
                CustomCell cell = PrintTableDraw.drawGridCell(cellData, tablePrintControl);
                currentRowDrawedList.add(cellData);
                currentRowCells.add(cell);
                float cellHeight = QueryPrintCellUtils.getCellHeightWithPadding(cell) + 1.0f;
                maxCellHeight = Math.max(cellHeight, maxCellHeight);
            }
            if (excludeEndFlag) {
                this.decreaseExcludeEndRowHeight(excludeEndMap, tablePrintControl, document);
                excludeEndFlag = false;
            }
            if (PrintTableDraw.isNextPage(document, maxCellHeight, borderOutSideWidth)) {
                float originalHeight = pageHeight;
                QueryPrintUtil.resetDocumentAvailableHeight(document);
                QueryPrintUtil.decreaseDocumentAvailableHeight(document, borderOutSideWidth);
                pageHeight = QueryPrintUtil.getDocumentAvailableHeight(document);
                if (originalHeight >= 0.0f) {
                    if (this.canAddDiv(i, totalRows)) {
                        elements.add((IBlockElement)new Div());
                        float availableHeight = (pageHeight -= maxCellHeight) - PdfHandler.getMarginBottom((IPropertyContainer)pdfTable) - PdfHandler.getMarginTop((IPropertyContainer)pdfTable);
                        QueryPrintUtil.setDocumentAvailableHeight(document, availableHeight);
                    } else {
                        currentRowCells = QueryPrintCellUtils.addCellInCurrentRowWhileMergeCell(columns, i, currentRowCells, currentRowDrawedList, cellPropMap);
                        elements.add((IBlockElement)pdfTable);
                        pdfTable = QueryPrintUtil.newTable(tablePrintControl);
                        tableMap.put(++pdfPageCount, pdfTable);
                        excludeEndFlag = true;
                        float headerHeight = this.dealEveryPageRow(everyPageRows, pdfTable, tablePrintControl);
                        float availableHeight = pageHeight - PdfHandler.getMarginBottom((IPropertyContainer)pdfTable) - PdfHandler.getMarginTop((IPropertyContainer)pdfTable) - headerHeight;
                        QueryPrintUtil.setDocumentAvailableHeight(document, availableHeight);
                    }
                }
            }
            if (rowType == TableRowTypeEnum.floatRow) {
                Integer floatRowNum;
                Integer n = floatRowNum = Optional.ofNullable(everyTableFloatRowMap.get(pdfPageCount)).orElse(0);
                Integer n2 = floatRowNum = Integer.valueOf(floatRowNum + 1);
                everyTableFloatRowMap.put(pdfPageCount, floatRowNum);
            }
            currentRowCells.forEach(arg_0 -> ((CustomTable)pdfTable).addCell(arg_0));
            QueryPrintUtil.decreaseDocumentAvailableHeight(document, maxCellHeight);
        }
        this.appendExcludeEndRow(tableMap, excludeEndMap, columns, tablePrintControl, everyTableFloatRowMap);
        this.appendOnlyEndPageRow(pdfTable, columns, tablePrintControl, elements, document, pdfPageCount, tableMap);
        return elements;
    }

    private void appendOnlyEndPageRow(Table table, int columnSize, TablePrintControl tablePrintControl, List<IBlockElement> elements, Document document, int pdfPageCount, Map<Integer, Table> tableMap) {
        TablePrintDrawContext tablePrintContext = QueryPrintThreadLocal.getTablePrintDrawContext();
        if (Objects.isNull(tablePrintContext) || Objects.isNull(tablePrintContext.getOnlyEndMap())) {
            return;
        }
        Map<Integer, Map<Integer, TableCellData>> onlyEndMap = tablePrintContext.getOnlyEndMap();
        if (ObjectUtils.isEmpty(onlyEndMap)) {
            elements.add((IBlockElement)table);
            return;
        }
        Map<String, GridCellProp> cellPropMap = tablePrintContext.getCellPropMap();
        HashSet<String> drawedCellSet = new HashSet<String>();
        TableGridData onlyEndPageRows = new TableGridData();
        HashMap<Integer, ArrayList<Integer>> tempRowMap = new HashMap<Integer, ArrayList<Integer>>(16);
        float borderWidth = tablePrintControl.getBorder().getWidth();
        for (Map.Entry<Integer, Map<Integer, TableCellData>> entry : onlyEndMap.entrySet()) {
            Map<Integer, TableCellData> map = entry.getValue();
            int rowIndex = entry.getKey();
            List<CustomCell> currentRowCells = new ArrayList<CustomCell>();
            ArrayList<TableCellData> rowCellList = new ArrayList<TableCellData>();
            CurrentCell currentCell = new CurrentCell(0, 0);
            float maxCellHeight = 0.0f;
            float notSpanRowCellHeight = 0.0f;
            int maxSpanRow = 1;
            for (int i = 0; i < columnSize; ++i) {
                CustomCell cell;
                TableCellData tableCellData;
                TableCellData object = map.get(i);
                if (object != null) {
                    tableCellData = object;
                    cell = PrintTableDraw.drawGridCell(tableCellData, tablePrintControl);
                } else {
                    tableCellData = new TableCellData();
                    cell = new CustomCell();
                }
                PrintTableDraw.calcCurrentCell(drawedCellSet, currentCell);
                if (tableCellData.getSpanRow() == 0) {
                    ArrayList<Integer> list = (ArrayList<Integer>)tempRowMap.get(rowIndex);
                    if (ObjectUtils.isEmpty(list)) {
                        list = new ArrayList<Integer>();
                    }
                    list.add(i);
                    tempRowMap.put(rowIndex, list);
                    continue;
                }
                int spanRow = tableCellData.getSpanRow();
                maxSpanRow = Math.max(maxSpanRow, spanRow);
                int spanColumn = tableCellData.getSpanColumn();
                PrintTableDraw.addDrawedCell(rowIndex, i, spanRow, spanColumn, drawedCellSet);
                currentRowCells.add(cell);
                rowCellList.add(tableCellData);
                float cellHeight = QueryPrintCellUtils.getCellHeightWithPadding(cell) + 1.0f;
                if (spanRow < 2) {
                    notSpanRowCellHeight = Math.max(cellHeight, notSpanRowCellHeight);
                }
                maxCellHeight = Math.max(cellHeight, maxCellHeight);
            }
            if (PrintTableDraw.isOnlyEndPageRowNextPage(document, maxCellHeight, borderWidth)) {
                currentRowCells = QueryPrintCellUtils.addCellInCurrentRowWhileMergeCell(columnSize, rowIndex, currentRowCells, rowCellList, cellPropMap);
                elements.add((IBlockElement)table);
                table = QueryPrintUtil.newTable(tablePrintControl);
                QueryPrintUtil.resetDocumentAvailableHeight(document);
                tableMap.put(++pdfPageCount, table);
                float availableHeight = QueryPrintUtil.getDocumentAvailableHeight(document);
                QueryPrintUtil.setDocumentAvailableHeight(document, availableHeight);
            }
            if ((float)maxSpanRow * notSpanRowCellHeight > maxCellHeight) {
                maxCellHeight = notSpanRowCellHeight;
            }
            QueryPrintUtil.decreaseDocumentAvailableHeight(document, maxCellHeight);
            onlyEndPageRows.addRow(rowCellList);
            currentRowCells.forEach(arg_0 -> ((Table)table).addCell(arg_0));
        }
        elements.add((IBlockElement)table);
    }

    private static boolean isOnlyEndPageRowNextPage(Document document, float maxCellHeight, float borderWidth) {
        float availableHeight = QueryPrintUtil.getDocumentAvailableHeight(document);
        float residueHeight = availableHeight - borderWidth;
        if (residueHeight - maxCellHeight < 5.0f) {
            return true;
        }
        return residueHeight <= maxCellHeight || availableHeight <= 0.0f;
    }

    private static void addDrawedCell(int row, int col, int spanRow, int spanColumn, Set<String> drawedCellSet) {
        int i;
        int j = col;
        int targetRow = i + spanRow;
        int targetCol = j + spanColumn;
        for (i = row; i < targetRow; ++i) {
            while (j < targetCol) {
                drawedCellSet.add(i + "&" + j);
                ++j;
            }
        }
    }

    private static void calcCurrentCell(Set<String> drawedCellSet, CurrentCell currentCell) {
        while (drawedCellSet.contains(currentCell.rowIndex + "&" + currentCell.colIndex)) {
            ++currentCell.colIndex;
        }
    }

    private void appendExcludeEndRow(Map<Integer, Table> tableMap, Map<Integer, Map<Integer, TableCellProp>> excludeEndMap, int columns, TablePrintControl tablePrintControl, Map<Integer, Integer> pageSizeMap) {
        if (ObjectUtils.isEmpty(excludeEndMap)) {
            return;
        }
        for (Map.Entry<Integer, Table> tableMapEntry : tableMap.entrySet()) {
            Integer pageIndex = tableMapEntry.getKey();
            Table table = tableMapEntry.getValue();
            int start = 0;
            int end = 0;
            for (Map.Entry<Integer, Integer> pageSizeEntry : pageSizeMap.entrySet()) {
                Integer page = pageSizeEntry.getKey();
                Integer floatRow = pageSizeEntry.getValue();
                if (pageIndex >= page) {
                    end += floatRow.intValue();
                }
                if (pageIndex == 0 || pageIndex <= page) continue;
                start += floatRow.intValue();
            }
            ArrayList<Integer> tempList = new ArrayList<Integer>();
            tempList.add(start);
            tempList.add(end);
            for (Map.Entry<Integer, Map<Integer, TableCellProp>> entry : excludeEndMap.entrySet()) {
                ArrayList<Cell> currentRowCells = new ArrayList<Cell>();
                Map<Integer, TableCellProp> colMap = entry.getValue();
                for (int i = 0; i < columns; ++i) {
                    Cell cell;
                    TableCellProp tableCellProp = colMap.get(i);
                    if (Objects.nonNull(tableCellProp)) {
                        TableCellData cellData = new TableCellData();
                        String cellValue = "";
                        String expression = tableCellProp.getExpression();
                        if (StringUtils.hasText(expression)) {
                            cellValue = this.getCellValue(tableCellProp);
                        }
                        cellData.setCellData(cellValue);
                        cellData.setCellProp(tableCellProp);
                        cell = PrintTableDraw.drawGridCell(cellData, tablePrintControl);
                    } else {
                        cell = new Cell();
                    }
                    currentRowCells.add(cell);
                }
                currentRowCells.forEach(arg_0 -> ((Table)table).addCell(arg_0));
            }
        }
    }

    private String getCellValue(TableCellProp cellProp) {
        String showText = "";
        Object evaluateData = FormulaExecuteHandlerUtil.getFormulaEvaluateData(cellProp.getExpression(), ParamTypeEnum.STRING.getTypeName());
        showText = evaluateData == null ? "" : evaluateData.toString();
        return showText;
    }

    private boolean canAddDiv(int i, TableGridData totalRows) {
        TableRowTypeEnum rowType = totalRows.getRowType(i);
        if (TableRowTypeEnum.fixedRow == rowType) {
            return false;
        }
        return i == 0 || rowType == TableRowTypeEnum.everyPage || totalRows.getRowType(i - 1) == TableRowTypeEnum.everyPage;
    }

    private void decreaseExcludeEndRowHeight(Map<Integer, Map<Integer, TableCellProp>> excludeEndMap, TablePrintControl tablePrintControl, Document document) {
        if (Objects.isNull(excludeEndMap) || excludeEndMap.isEmpty()) {
            return;
        }
        int height = 0;
        for (Map.Entry<Integer, Map<Integer, TableCellProp>> entry : excludeEndMap.entrySet()) {
            Map<Integer, TableCellProp> map = entry.getValue();
            float maxHeight = 0.0f;
            for (Map.Entry<Integer, TableCellProp> tempEntry : map.entrySet()) {
                TableCellProp tableCellProp = tempEntry.getValue();
                TableCellData cellData = new TableCellData();
                String cellValue = "";
                String expression = tableCellProp.getExpression();
                if (StringUtils.hasText(expression)) {
                    cellValue = this.getCellValue(tableCellProp);
                }
                cellData.setCellData(cellValue);
                cellData.setCellProp(tableCellProp);
                CustomCell cell = PrintTableDraw.drawGridCell(cellData, tablePrintControl);
                float value = QueryPrintCellUtils.getCellHeightWithPadding(cell) + 1.0f;
                maxHeight = Math.max(value, maxHeight);
            }
            height = (int)((float)height + maxHeight);
        }
        float availableHeight = QueryPrintUtil.getDocumentAvailableHeight(document) - (float)height;
        QueryPrintUtil.setDocumentAvailableHeight(document, availableHeight);
    }

    private void initParamThreadLocal(Map<String, GridCellProp> cellPropMap) {
        TablePrintDrawContext tablePrintDrawContext = QueryPrintThreadLocal.getTablePrintDrawContext();
        if (Objects.isNull(tablePrintDrawContext)) {
            tablePrintDrawContext = new TablePrintDrawContext();
            QueryPrintThreadLocal.setTablePrintLocal(tablePrintDrawContext);
        }
        tablePrintDrawContext.setCellPropMap(cellPropMap);
        QueryPrintCellUtils.initTablePrintDrawCellThreadLocal();
    }

    private TableGridData getTotalRows(TablePrintControl tablePrintControl, Map<String, GridCellProp> cellPropMap, List<Map<String, Object>> tableData) {
        int rows = tablePrintControl.getRows();
        int columns = tablePrintControl.getColumns();
        Map<Integer, Integer> maxFloatRowNumMap = this.calculateMaxFloatRowNum(tableData);
        boolean emptyFlag = true;
        for (Map.Entry<Integer, Integer> entry : maxFloatRowNumMap.entrySet()) {
            Integer maxFloatRow = entry.getValue();
            if (maxFloatRow == null || maxFloatRow == 0) continue;
            emptyFlag = false;
            break;
        }
        if (emptyFlag && !tablePrintControl.getPrintEmptyTableFlag()) {
            return null;
        }
        TableGridData tableGridData = new TableGridData();
        int totalRows = 0;
        LinkedHashMap<Integer, Map<Integer, TableCellProp>> excludeEndMap = new LinkedHashMap<Integer, Map<Integer, TableCellProp>>(16);
        LinkedHashMap<Integer, Map<Integer, TableCellData>> onlyEndMap = new LinkedHashMap<Integer, Map<Integer, TableCellData>>(16);
        int onlyEndPageRowFlag = 0;
        int excludeEndRowFlag = 0;
        for (int row = 0; row < rows; ++row) {
            TableRowTypeEnum rowType = null;
            for (int col = 0; col < columns; ++col) {
                String cellPropKey = row + "###" + col;
                TableCellProp cellProp = (TableCellProp)cellPropMap.get(cellPropKey);
                if ((rowType = (TableRowTypeEnum)Optional.ofNullable(rowType).orElse(cellProp.getRowType())) == TableRowTypeEnum.floatRow) break;
                if (rowType == TableRowTypeEnum.excludeEnd) {
                    if (Objects.isNull(excludeEndMap.get(excludeEndRowFlag))) {
                        excludeEndMap.put(excludeEndRowFlag, new HashMap(16));
                    }
                    ((Map)excludeEndMap.get(excludeEndRowFlag)).put(col, cellProp);
                    continue;
                }
                TableCellData cellData = new TableCellData();
                Object evaluateData = FormulaExecuteHandlerUtil.getFormulaEvaluateData(cellProp.getExpression(), ParamTypeEnum.STRING.getTypeName());
                String cellValue = evaluateData == null ? "" : evaluateData.toString();
                cellData.setCellData(cellValue);
                cellData.setCellProp(cellProp);
                if (rowType == TableRowTypeEnum.onlyEndPage) {
                    if (Objects.isNull(onlyEndMap.get(onlyEndPageRowFlag))) {
                        onlyEndMap.put(onlyEndPageRowFlag, new HashMap(16));
                    }
                    ((Map)onlyEndMap.get(onlyEndPageRowFlag)).put(col, cellData);
                    continue;
                }
                tableGridData.addCell(totalRows, col, cellData);
            }
            if (rowType == TableRowTypeEnum.floatRow) {
                int rowSize = this.convertTableData(tableData, totalRows, columns, cellPropMap, tableGridData);
                totalRows += rowSize;
            } else if (rowType == TableRowTypeEnum.excludeEnd) {
                ++excludeEndRowFlag;
            } else if (rowType == TableRowTypeEnum.onlyEndPage) {
                ++onlyEndPageRowFlag;
            } else {
                ++totalRows;
            }
            if (rowType == TableRowTypeEnum.onlyEndPage) continue;
            ++onlyEndPageRowFlag;
        }
        QueryPrintThreadLocal.getTablePrintDrawContext().setExcludeEndMap(excludeEndMap);
        QueryPrintThreadLocal.getTablePrintDrawContext().setOnlyEndMap(onlyEndMap);
        return tableGridData;
    }

    private Map<Integer, Integer> calculateMaxFloatRowNum(List<Map<String, Object>> tableData) {
        LinkedHashMap<Integer, Integer> maxFloatRowNumMap = new LinkedHashMap<Integer, Integer>();
        maxFloatRowNumMap.put(0, tableData.size());
        return maxFloatRowNumMap;
    }

    private int convertTableData(List<Map<String, Object>> tableData, int rowIndex, int columns, Map<String, GridCellProp> cellPropMap, TableGridData tableGridData) {
        TableCellProp tableCellProp;
        TableGridData floatRowGridData = new TableGridData();
        if (CollectionUtils.isEmpty(tableData)) {
            return 0;
        }
        Map<String, Object> stringObjectMap = tableData.get(0);
        for (int i = 0; i < columns; ++i) {
            tableCellProp = (TableCellProp)cellPropMap.get(rowIndex + "###" + i);
            String expression = tableCellProp.getExpression();
            if (!StringUtils.hasText(expression) || expression.equals("index") || stringObjectMap.containsKey(expression)) continue;
            throw new DefinedQueryRuntimeException("print column are not in table");
        }
        int size = tableData.size();
        for (int r = 0; r < size; ++r) {
            Map<String, Object> row = tableData.get(r);
            ArrayList<TableCellData> rowDataList = new ArrayList<TableCellData>();
            for (int columnIndex = 0; columnIndex < columns; ++columnIndex) {
                tableCellProp = (TableCellProp)cellPropMap.get(rowIndex + "###" + columnIndex);
                String columnKey = tableCellProp.getExpression();
                if ("index".equals(columnKey)) {
                    rowDataList.add(new TableCellData(String.valueOf(r + 1), tableCellProp));
                    continue;
                }
                rowDataList.add(new TableCellData(Optional.ofNullable(row.get(columnKey)).orElse("").toString(), tableCellProp));
            }
            floatRowGridData.addRow(rowDataList);
        }
        int rowSize = floatRowGridData.getRowSize();
        for (int i = 0; i < rowSize; ++i) {
            List<TableCellData> rowData = floatRowGridData.getRowData(i);
            tableGridData.addRow(rowData);
        }
        return tableData.size();
    }

    private static Map<String, GridCellProp> getTableCellPropMap(TablePrintControl tablePrintControl) {
        return tablePrintControl.getCells().stream().collect(Collectors.toMap(key -> key.getRowIndex() + "###" + key.getColumnIndex(), value -> value));
    }

    private static CustomCell drawGridCell(TableCellData cellData, TablePrintControl tablePrintControl) {
        TableCellProp cellProp = cellData.getCellProp();
        int spanRow = cellProp.getSpanRow();
        if (cellData.getSpanRow() != spanRow) {
            spanRow = cellData.getSpanRow();
        }
        CustomCell cell = new CustomCell(spanRow, cellProp.getSpanColumn());
        QueryPrintCellUtils.setTableCellBorder(cell);
        QueryPrintCellUtils.setCellBackGroundColor(cell, cellProp);
        float totalWidth = tablePrintControl.getParentWidth() - tablePrintControl.getPaddingLeft() - tablePrintControl.getPaddingRight();
        float columnWidthWithSpanCol = tablePrintControl.getColumnWidthWithSpanCol(cellProp, totalWidth);
        columnWidthWithSpanCol = columnWidthWithSpanCol - QueryPrintControlUtils.calculateCellLeftRightPadding(cellProp) - 2.0f;
        cell.setWidth(columnWidthWithSpanCol);
        String key = cellProp.getRowIndex() + "###" + cellProp.getColumnIndex();
        QueryPrintCellUtils.setCellInfoInToThreadLocal(key, columnWidthWithSpanCol, null);
        cell.setHeight(tablePrintControl.getRowHeight(cellProp.getRowIndex()));
        PdfHandler.setTextAlignment((IPropertyContainer)cell, (String)cellProp.getTextAlignment());
        PdfHandler.setVerticalAlignment((IPropertyContainer)cell, (String)cellProp.getVerticalAlignment());
        PdfHandler.setHorizontalAlignment((IPropertyContainer)cell, (String)cellProp.getHorizontalAlignment());
        String str = StringUtils.hasText(cellData.getCellData()) ? cellData.getCellData() : "";
        String padding = cellProp.getPadding();
        String[] paddingArray = padding.split(" ");
        if (paddingArray.length == 4) {
            cell.setPaddingTop(Float.parseFloat(paddingArray[0]));
            cell.setPaddingRight(Float.parseFloat(paddingArray[1]));
            cell.setPaddingBottom(Float.parseFloat(paddingArray[2]));
            cell.setPaddingLeft(Float.parseFloat(paddingArray[3]));
        }
        if (tablePrintControl.getRowAutoHeight(cellProp.getRowIndex())) {
            Paragraph autoHeightParagraph = QueryPrintCellUtils.getAutoHeightParagraph(cell, str, cellProp);
            cell.add((IBlockElement)autoHeightParagraph);
            return cell;
        }
        if (cellProp.isFoldLine()) {
            Paragraph autoFoldParagraph = QueryPrintCellUtils.getAutoFoldParagraph(cell, str, cellProp);
            cell.add((IBlockElement)autoFoldParagraph);
            return cell;
        }
        Paragraph zoomParagraph = QueryPrintCellUtils.getZoomParagraph(cell, str, cellProp);
        cell.add((IBlockElement)zoomParagraph);
        return cell;
    }

    private float dealEveryPageRow(TableGridData tableGridData, Table pdfTable, TablePrintControl tablePrintControl) {
        if (tableGridData.getRowSize() == 0) {
            return 0.0f;
        }
        float totalHeight = 0.0f;
        float borderWidth = tablePrintControl.getBorder().getWidth();
        int rowSize = tableGridData.getRowSize();
        for (int i = 0; i < rowSize; ++i) {
            float maxCellHeight = 0.0f;
            int columns = tablePrintControl.getColumns();
            for (int i1 = 0; i1 < columns; ++i1) {
                TableCellData cellData = tableGridData.getCell(i, i1);
                if (cellData == null) {
                    pdfTable.addCell(new Cell());
                    continue;
                }
                if (cellData.getSpanColumn() == 0) continue;
                CustomCell cell = PrintTableDraw.drawGridCell(cellData, tablePrintControl);
                pdfTable.addCell((Cell)cell);
                float cellHeight = QueryPrintCellUtils.getCellHeightWithPadding(cell);
                maxCellHeight = Math.max(cellHeight, maxCellHeight);
            }
            totalHeight += maxCellHeight;
        }
        return totalHeight += borderWidth * (float)(tableGridData.getRowSize() + 1);
    }

    private static boolean isNextPage(Document document, float maxCellHeight, float borderWidth) {
        float availableHeight = QueryPrintUtil.getDocumentAvailableHeight(document);
        float residueHeight = availableHeight - borderWidth;
        if (residueHeight - maxCellHeight < 2.0f) {
            return true;
        }
        return residueHeight <= maxCellHeight || availableHeight <= 0.0f;
    }

    private static class CurrentCell {
        int rowIndex;
        int colIndex;

        CurrentCell(int row, int col) {
            this.rowIndex = row;
            this.colIndex = col;
        }
    }
}

