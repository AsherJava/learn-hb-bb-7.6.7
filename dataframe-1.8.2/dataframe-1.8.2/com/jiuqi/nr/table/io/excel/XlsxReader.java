/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterables
 */
package com.jiuqi.nr.table.io.excel;

import com.google.common.collect.Iterables;
import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.df.BlockData;
import com.jiuqi.nr.table.df.DataFrame;
import com.jiuqi.nr.table.df.Index;
import com.jiuqi.nr.table.io.DataReader;
import com.jiuqi.nr.table.io.ReaderRegistry;
import com.jiuqi.nr.table.io.Source;
import com.jiuqi.nr.table.io.excel.XlsxReadOptions;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.poi.ss.format.CellDateFormatter;
import org.apache.poi.ss.format.CellGeneralFormatter;
import org.apache.poi.ss.format.CellNumberFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

public class XlsxReader
implements DataReader<XlsxReadOptions> {
    public static final String SEPARATOR = "#^$";
    public static final String SPLIT = "\\#\\^\\$";
    private static final XlsxReader INSTANCE = new XlsxReader();

    public static void register(ReaderRegistry registry) {
        registry.registerExtension("xlsx", INSTANCE);
        registry.registerMimeType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", INSTANCE);
        registry.registerOptions(XlsxReadOptions.class, INSTANCE);
    }

    @Override
    public DataFrame<?> read(Source source) throws IOException {
        return this.read(XlsxReadOptions.builder(source).build());
    }

    @Override
    public DataFrame<?> read(XlsxReadOptions options) throws IOException {
        List<DataFrame<?>> dfs = null;
        dfs = this.readMultiple(options, true);
        if (options.sheetIndex() != null) {
            int index = options.sheetIndex();
            if (index < 0 || index >= dfs.size()) {
                throw new IndexOutOfBoundsException(String.format("Sheet index %d outside bounds. %d sheets found.", index, dfs.size()));
            }
            DataFrame<?> df = dfs.get(index);
            if (df == null) {
                throw new IllegalArgumentException(String.format("No table found at sheet index %d.", index));
            }
            return df;
        }
        return dfs.stream().filter(t -> t != null).findFirst().orElseThrow(() -> new IllegalArgumentException("No tables found."));
    }

    public List<DataFrame<?>> readMultiple(XlsxReadOptions options) throws IOException {
        return this.readMultiple(options, false);
    }

    /*
     * Exception decompiling
     */
    protected List<DataFrame<?>> readMultiple(XlsxReadOptions options, boolean includeNulls) throws IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private DataFrame<?> createTable(Sheet sheet, TableRange tableArea, XlsxReadOptions options) {
        Optional<List<String>> optHeaderNames = this.getHeaderNames(sheet, tableArea);
        optHeaderNames.ifPresent(h -> tableArea.startRow++);
        List<String> headerNames = optHeaderNames.orElse(this.calculateDefaultColumnNames(tableArea));
        if (options.allowDuplicateColumnNames()) {
            this.renameDuplicateColumnHeaders(headerNames);
        }
        BlockData bd = new BlockData();
        ArrayList<CellRangeAddress> cellRangeAddressList = new ArrayList<CellRangeAddress>();
        for (int i = 0; i < sheet.getNumMergedRegions(); ++i) {
            CellRangeAddress region = sheet.getMergedRegion(i);
            cellRangeAddressList.add(region);
        }
        for (int rowNum = tableArea.startRow; rowNum <= tableArea.endRow; ++rowNum) {
            Row row = sheet.getRow(rowNum);
            ArrayList<Object> rowData = new ArrayList<Object>();
            for (int colNum = 0; colNum < headerNames.size(); ++colNum) {
                int excelColNum = colNum + tableArea.startColumn;
                Cell cell = null;
                Optional<CellRangeAddress> findFirst = this.isMergeCell(cellRangeAddressList, rowNum, excelColNum);
                CellRangeAddress region = null;
                if (findFirst.isPresent()) {
                    region = findFirst.get();
                    cell = sheet.getRow(region.getFirstRow()).getCell(region.getFirstColumn(), Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                } else {
                    cell = row.getCell(excelColNum, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                }
                String columnName = headerNames.get(colNum);
                ColumnType columnType = this.getColumnType(colNum, columnName, sheet, excelColNum, tableArea, options);
                if (cell != null) {
                    Object value = this.appendValue(columnType, cell);
                    rowData.add(value);
                    continue;
                }
                Object missValue = null;
                rowData.add(missValue);
            }
            bd.add(rowData);
        }
        Index rows = new Index(bd.size());
        Index columns = new Index(headerNames, "_col_", headerNames.size());
        DataFrame df = new DataFrame(rows, columns, bd);
        df.setName(options.tableName() + "#" + sheet.getSheetName());
        return df;
    }

    private Optional<CellRangeAddress> isMergeCell(List<CellRangeAddress> cellRangeAddressList, int rowNum, int excelColNum) {
        Optional<CellRangeAddress> findFirst = cellRangeAddressList.stream().filter(e -> rowNum >= e.getFirstRow() && rowNum <= e.getLastRow() && excelColNum >= e.getFirstColumn() && excelColNum <= e.getLastColumn()).findFirst();
        return findFirst;
    }

    private ColumnType getColumnType(int colNum, String name, Sheet sheet, int excelColNum, TableRange tableRange, XlsxReadOptions options) {
        ColumnType columnType = options.columnTypeReadOptions().columnType(colNum, name).orElse(this.calculateColumnTypeForColumn(sheet, excelColNum, tableRange).orElse(ColumnType.STRING));
        return columnType;
    }

    private Object appendValue(ColumnType type, Cell cell) {
        CellType cellType = cell.getCellType() == CellType.FORMULA ? cell.getCachedFormulaResultType() : cell.getCellType();
        switch (cellType) {
            case STRING: {
                return cell.getRichStringCellValue().getString();
            }
            case NUMERIC: {
                String val;
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    LocalDateTime localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    if (type == ColumnType.STRING) {
                        String dataFormatStyle = cell.getCellStyle().getDataFormatString();
                        String val2 = "general".equalsIgnoreCase(dataFormatStyle) ? new CellGeneralFormatter().format(cell.getNumericCellValue()) : new CellDateFormatter(dataFormatStyle).format(cell.getDateCellValue());
                        return val2;
                    }
                    return localDate;
                }
                double num = cell.getNumericCellValue();
                if (type == ColumnType.INTEGER) {
                    if ((double)((int)num) == num) {
                        return (int)num;
                    }
                    if ((double)((long)num) == num) {
                        return (long)num;
                    }
                    return num;
                }
                if (type == ColumnType.LONG) {
                    if ((double)((long)num) == num) {
                        return (long)num;
                    }
                    return num;
                }
                if (type == ColumnType.DOUBLE) {
                    return num;
                }
                if (type != ColumnType.STRING) break;
                String dataFormatStyle = cell.getCellStyle().getDataFormatString();
                if ("general".equalsIgnoreCase(dataFormatStyle)) {
                    val = new CellGeneralFormatter().format(cell.getNumericCellValue());
                } else {
                    double numericCellValue = cell.getNumericCellValue();
                    val = null != dataFormatStyle && dataFormatStyle.length() > 0 ? new CellNumberFormatter(dataFormatStyle).format(numericCellValue) : numericCellValue + "";
                }
                return val;
            }
            case BOOLEAN: {
                if (type == ColumnType.BOOLEAN) {
                    return cell.getBooleanCellValue();
                }
                if (type != ColumnType.STRING) break;
                String val = new CellGeneralFormatter().format(cell.getBooleanCellValue());
                return val;
            }
        }
        return null;
    }

    private Optional<ColumnType> calculateColumnTypeForColumn(Sheet sheet, int col, TableRange tableRange) {
        Set<CellType> cellTypes = this.getCellTypes(sheet, col, tableRange);
        if (cellTypes.size() != 1) {
            return Optional.empty();
        }
        CellType cellType = (CellType)((Object)Iterables.get(cellTypes, (int)0));
        switch (cellType) {
            case STRING: {
                return Optional.of(ColumnType.STRING);
            }
            case NUMERIC: {
                return this.allNumericFieldsDateFormatted(sheet, col, tableRange) ? Optional.of(ColumnType.LOCAL_DATE_TIME) : Optional.of(ColumnType.INTEGER);
            }
            case BOOLEAN: {
                return Optional.of(ColumnType.BOOLEAN);
            }
        }
        return Optional.empty();
    }

    private Set<CellType> getCellTypes(Sheet sheet, int col, TableRange tableRange) {
        return IntStream.range(tableRange.startRow, tableRange.endRow + 1).mapToObj(sheet::getRow).filter(Objects::nonNull).map(row -> row.getCell(col)).filter(Objects::nonNull).filter(cell -> Optional.ofNullable(this.isBlank((Cell)cell)).orElse(false) == false).map(cell -> cell.getCellType() == CellType.FORMULA ? cell.getCachedFormulaResultType() : cell.getCellType()).collect(Collectors.toSet());
    }

    private boolean allNumericFieldsDateFormatted(Sheet sheet, int col, TableRange tableRange) {
        return IntStream.range(tableRange.startRow, tableRange.endRow + 1).mapToObj(sheet::getRow).filter(Objects::nonNull).map(row -> row.getCell(col)).filter(Objects::nonNull).filter(cell -> cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA && cell.getCachedFormulaResultType() == CellType.NUMERIC).allMatch(DateUtil::isCellDateFormatted);
    }

    private InputStream getInputStream(XlsxReadOptions options, byte[] bytes) throws FileNotFoundException {
        if (bytes != null) {
            return new ByteArrayInputStream(bytes);
        }
        if (options.source().inputStream() != null) {
            return options.source().inputStream();
        }
        return new FileInputStream(options.source().file());
    }

    private TableRange findTableArea(Sheet sheet) {
        int row1 = -1;
        int row2 = -1;
        TableRange lastRowArea = null;
        for (Row row : sheet) {
            TableRange rowArea = this.findRowArea(row);
            if (lastRowArea == null && rowArea != null) {
                if (row1 >= 0) continue;
                lastRowArea = rowArea;
                row2 = row1 = row.getRowNum();
                continue;
            }
            if (lastRowArea != null && rowArea == null) {
                if (row2 > row1) break;
                row1 = -1;
                continue;
            }
            if (lastRowArea == null && rowArea == null) {
                row1 = -1;
                continue;
            }
            if (rowArea.startColumn < lastRowArea.startColumn || rowArea.endColumn > lastRowArea.endColumn) {
                lastRowArea = null;
                row2 = -1;
                continue;
            }
            row2 = row.getRowNum();
        }
        return row1 >= 0 && lastRowArea != null ? new TableRange(row1, row2, lastRowArea.startColumn, lastRowArea.endColumn) : null;
    }

    private List<String> calculateDefaultColumnNames(TableRange tableArea) {
        return IntStream.range(tableArea.startColumn, tableArea.endColumn + 1).mapToObj(i -> "col" + i).collect(Collectors.toList());
    }

    private void renameDuplicateColumnHeaders(List<String> headerNames) {
        HashMap<String, Integer> nameCounter = new HashMap<String, Integer>();
        for (int i = 0; i < headerNames.size(); ++i) {
            String name = headerNames.get(i);
            Integer count = (Integer)nameCounter.get(name);
            if (count == null) {
                nameCounter.put(name, 1);
                continue;
            }
            Integer n = count;
            Integer n2 = count = Integer.valueOf(count + 1);
            nameCounter.put(name, count);
            headerNames.set(i, name + SEPARATOR + count);
        }
    }

    private TableRange findRowArea(Row row) {
        int col1 = -1;
        int col2 = -1;
        for (Cell cell : row) {
            Boolean blank = this.isBlank(cell);
            if (col1 < 0 && Boolean.FALSE.equals(blank)) {
                col2 = col1 = cell.getColumnIndex();
                continue;
            }
            if (col1 < 0 || col2 < col1) continue;
            if (Boolean.FALSE.equals(blank)) {
                col2 = cell.getColumnIndex();
                continue;
            }
            if (!Boolean.TRUE.equals(blank)) continue;
            break;
        }
        return col1 >= 0 && col2 >= col1 ? new TableRange(0, 0, col1, col2) : null;
    }

    private Boolean isBlank(Cell cell) {
        switch (cell.getCellType()) {
            case STRING: {
                if (cell.getRichStringCellValue().length() <= 0) break;
                return false;
            }
            case NUMERIC: {
                if (!(DateUtil.isCellDateFormatted(cell) ? cell.getDateCellValue() != null : cell.getNumericCellValue() != 0.0)) break;
                return false;
            }
            case BOOLEAN: {
                if (!cell.getBooleanCellValue()) break;
                return false;
            }
            case BLANK: {
                return true;
            }
        }
        return null;
    }

    private Optional<List<String>> getHeaderNames(Sheet sheet, TableRange tableArea) {
        Row row = sheet.getRow(tableArea.startRow);
        List headerNames = IntStream.range(tableArea.startColumn, tableArea.endColumn + 1).mapToObj(row::getCell).filter(cell -> cell.getCellType() == CellType.STRING).map(cell -> cell.getRichStringCellValue().getString()).collect(Collectors.toList());
        return headerNames.size() == tableArea.getColumnCount() ? Optional.of(headerNames) : Optional.empty();
    }

    private static class TableRange {
        private int startRow;
        private int endRow;
        private int startColumn;
        private int endColumn;

        TableRange(int startRow, int endRow, int startColumn, int endColumn) {
            this.startRow = startRow;
            this.endRow = endRow;
            this.startColumn = startColumn;
            this.endColumn = endColumn;
        }

        public int getColumnCount() {
            return this.endColumn - this.startColumn + 1;
        }
    }
}

