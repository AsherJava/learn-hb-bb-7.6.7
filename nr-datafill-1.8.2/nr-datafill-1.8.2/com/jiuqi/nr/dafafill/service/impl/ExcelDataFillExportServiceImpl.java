/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.table.df.DataFrame
 *  com.jiuqi.nr.table.df.IKey
 *  com.jiuqi.nr.table.df.Index
 *  com.jiuqi.nvwa.cellbook.constant.CellBorderStyle
 *  com.jiuqi.nvwa.cellbook.constant.FillPatternType
 *  com.jiuqi.nvwa.cellbook.constant.HorizontalAlignment
 *  com.jiuqi.nvwa.cellbook.excel.CellSheetToExcel
 *  com.jiuqi.nvwa.cellbook.model.Cell
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 *  com.jiuqi.nvwa.cellbook.model.CellSheet
 *  com.jiuqi.nvwa.cellbook.model.DropBoxData
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.dafafill.service.impl;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dafafill.exception.DataFillRuntimeException;
import com.jiuqi.nr.dafafill.model.DFDimensionValue;
import com.jiuqi.nr.dafafill.model.DFDimensionValueGetService;
import com.jiuqi.nr.dafafill.model.DataFillDataQueryInfo;
import com.jiuqi.nr.dafafill.model.DataFillDataResult;
import com.jiuqi.nr.dafafill.model.DataFillEntityData;
import com.jiuqi.nr.dafafill.model.DataFillEntityDataBase;
import com.jiuqi.nr.dafafill.model.DataFillEntityDataQueryInfo;
import com.jiuqi.nr.dafafill.model.DataFillEntityDataResult;
import com.jiuqi.nr.dafafill.model.FieldFormat;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.CellType;
import com.jiuqi.nr.dafafill.model.enums.ColWidthType;
import com.jiuqi.nr.dafafill.model.enums.ExportType;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import com.jiuqi.nr.dafafill.model.enums.HeaderMode;
import com.jiuqi.nr.dafafill.model.enums.RatioType;
import com.jiuqi.nr.dafafill.model.table.DataFillBaseCell;
import com.jiuqi.nr.dafafill.model.table.DataFillEnumCell;
import com.jiuqi.nr.dafafill.model.table.DataFillExpressionCell;
import com.jiuqi.nr.dafafill.model.table.DataFillIndexData;
import com.jiuqi.nr.dafafill.model.table.DataFillIndexInfo;
import com.jiuqi.nr.dafafill.model.table.DataFillZBIndexData;
import com.jiuqi.nr.dafafill.service.IDFDimensionQueryFieldParser;
import com.jiuqi.nr.dafafill.service.IDataFillDataEnvService;
import com.jiuqi.nr.dafafill.service.IDataFillEntityDataService;
import com.jiuqi.nr.dafafill.service.IDataFillExportService;
import com.jiuqi.nr.dafafill.util.NrDataFillI18nUtil;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.table.df.DataFrame;
import com.jiuqi.nr.table.df.IKey;
import com.jiuqi.nr.table.df.Index;
import com.jiuqi.nvwa.cellbook.constant.CellBorderStyle;
import com.jiuqi.nvwa.cellbook.constant.FillPatternType;
import com.jiuqi.nvwa.cellbook.constant.HorizontalAlignment;
import com.jiuqi.nvwa.cellbook.excel.CellSheetToExcel;
import com.jiuqi.nvwa.cellbook.model.Cell;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.cellbook.model.CellSheet;
import com.jiuqi.nvwa.cellbook.model.DropBoxData;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ExcelDataFillExportServiceImpl
implements IDataFillExportService {
    private static Logger logger = LoggerFactory.getLogger(ExcelDataFillExportServiceImpl.class);
    @Autowired
    private IDataFillDataEnvService dataFillDataEnvService;
    @Autowired
    private IDataFillEntityDataService dataFillEntityDataService;
    @Autowired
    private IDFDimensionQueryFieldParser dFDimensionParser;
    @Autowired
    private DFDimensionValueGetService dfDimensionValueGetService;
    @Autowired
    private FileService fileService;
    @Resource(name="DataFillMessageSource")
    private MessageSource messageSource;

    @Override
    public boolean accept(ExportType exportType) {
        return ExportType.EXCEL == exportType;
    }

    @Override
    public void export(DataFillDataQueryInfo queryInfo, AsyncTaskMonitor monitor) {
        queryInfo.setPagerInfo(null);
        monitor.progressAndMessage(0.1, "\u5f00\u59cb\u67e5\u8be2");
        DataFillDataResult dataResult = this.dataFillDataEnvService.query(queryInfo, monitor);
        if (dataResult.isSuccess()) {
            monitor.progressAndMessage(0.6, "\u6784\u5efa\u8868\u683c\u6587\u4ef6");
            DataFrame<DataFillBaseCell> table = dataResult.getTable();
            List<DFDimensionValue> dimensionValues = queryInfo.getContext().getDimensionValues();
            ArrayList<DFDimensionValue> newdimensionValues = new ArrayList<DFDimensionValue>();
            for (DFDimensionValue dFDimensionValue : dimensionValues) {
                DFDimensionValue dimensionCopy = new DFDimensionValue();
                dimensionCopy.setName(dFDimensionValue.getName());
                String dfDimensionValue = this.dfDimensionValueGetService.getValues(dFDimensionValue, queryInfo.getContext().getModel());
                if (dfDimensionValue != null && dfDimensionValue.contains(";")) {
                    dimensionCopy.setValues(dfDimensionValue.split(";")[0]);
                } else {
                    dimensionCopy.setValues(dfDimensionValue);
                }
                newdimensionValues.add(dimensionCopy);
            }
            queryInfo.getContext().setDimensionValues(newdimensionValues);
            ArrayList<DropBoxData> dropBoxData = new ArrayList<DropBoxData>();
            monitor.progressAndMessage(0.7, "\u8868\u683c\u679a\u4e3e\u7c7b\u578b\u6570\u636e\u5904\u7406");
            for (QueryField queryField : queryInfo.getContext().model.getQueryFields()) {
                DataFillBaseCell cellData;
                String newVal;
                String oldVal;
                String[] originCellVal;
                DropBoxData addDropBoxData;
                Object colElement;
                int i;
                if (queryField.getDataType() != DataFieldType.STRING || !StringUtils.hasLength(queryField.getExpression())) continue;
                DataFillEntityDataQueryInfo entityDataQueryInfo = new DataFillEntityDataQueryInfo();
                entityDataQueryInfo.setContext(queryInfo.getContext());
                entityDataQueryInfo.setAllChildren(true);
                entityDataQueryInfo.setFullCode(queryField.getFullCode());
                DataFillEntityDataResult entityDataResult = this.dataFillEntityDataService.query(entityDataQueryInfo);
                Map<String, String> entityDataResultMap = entityDataResult.getItems().stream().collect(Collectors.toMap(DataFillEntityDataBase::getCode, DataFillEntityDataBase::getTitle));
                if (!entityDataResult.isSuccess()) continue;
                int colIndexCount = table.columns().size();
                int rowIndexCount = table.rows().size();
                boolean enumByCol = true;
                LinkedList<Integer> colNums = new LinkedList<Integer>();
                LinkedList<Integer> rowNums = new LinkedList<Integer>();
                int colAdjust = table.rows().getNames().length;
                for (int i2 = 0; i2 < table.rows().getSources().length; ++i2) {
                    Object rowSource = table.rows().getSources()[i2];
                    if (!(rowSource instanceof DataFillIndexInfo) || ((DataFillIndexInfo)rowSource).getHide() == null || !((DataFillIndexInfo)rowSource).getHide().booleanValue()) continue;
                    --colAdjust;
                }
                for (int c = 0; c < colIndexCount; ++c) {
                    Object col = table.columns().getKey(Integer.valueOf(c));
                    if (col instanceof IKey) {
                        for (i = 0; i < ((IKey)col).length(); ++i) {
                            colElement = ((IKey)col).getElement(i);
                            if (!(colElement instanceof DataFillZBIndexData) || !((DataFillZBIndexData)colElement).getSource().equals(queryField.getFullCode())) continue;
                            enumByCol = true;
                            colNums.add(c + colAdjust);
                        }
                        continue;
                    }
                    if (!(col instanceof DataFillZBIndexData)) continue;
                    if (((DataFillZBIndexData)col).getHide() != null && ((DataFillZBIndexData)col).getHide().booleanValue()) {
                        --colAdjust;
                    }
                    if (!((DataFillZBIndexData)col).getSource().equals(queryField.getFullCode())) continue;
                    enumByCol = true;
                    colNums.add(c + colAdjust);
                }
                if (colNums.size() == 0) {
                    for (int r = 0; r < rowIndexCount; ++r) {
                        Object row = table.rows().getKey(Integer.valueOf(r));
                        if (row instanceof IKey) {
                            for (i = 0; i < ((IKey)row).length(); ++i) {
                                colElement = ((IKey)row).getElement(i);
                                if (!(colElement instanceof DataFillZBIndexData) || !((DataFillZBIndexData)colElement).getSource().equals(queryField.getFullCode())) continue;
                                enumByCol = false;
                                rowNums.add(r + 1);
                            }
                            continue;
                        }
                        if (!(row instanceof DataFillZBIndexData) || !((DataFillZBIndexData)row).getSource().equals(queryField.getFullCode())) continue;
                        enumByCol = false;
                        rowNums.add(r + 1);
                    }
                }
                ArrayList<String> dropBoxDataValues = new ArrayList<String>();
                for (DataFillEntityData entityData : entityDataResult.getItems()) {
                    dropBoxDataValues.add(entityData.getCode() + "|" + entityData.getTitle());
                }
                if (enumByCol) {
                    for (Integer c : colNums) {
                        addDropBoxData = new DropBoxData();
                        addDropBoxData.setValues(dropBoxDataValues);
                        addDropBoxData.setByCol(enumByCol);
                        addDropBoxData.setColNum(c);
                        addDropBoxData.setRowNum(Integer.valueOf(1));
                        addDropBoxData.setEnueName(queryField.getTitle());
                        dropBoxData.add(addDropBoxData);
                        for (int r = 0; r < table.getData().size(); ++r) {
                            if (c - 1 >= colIndexCount || table.getData().get(c - 1, r) == null || ((DataFillBaseCell)table.getData().get(c - 1, r)).getValue() == null || !((DataFillBaseCell)table.getData().get(c - 1, r)).getValue().toString().contains(";")) continue;
                            for (String eachVal : originCellVal = ((DataFillBaseCell)table.getData().get(c - 1, r)).getValue().toString().split(";")) {
                                if (entityDataResultMap.get(eachVal) == null) continue;
                                oldVal = ((DataFillBaseCell)table.getData().get(c - 1, r)).getValue().toString();
                                newVal = oldVal.replaceFirst(eachVal, entityDataResultMap.get(eachVal));
                                cellData = new DataFillBaseCell();
                                cellData.setValue((Serializable)((Object)newVal));
                                table.getData().set(c - 1, r, (Object)cellData);
                            }
                        }
                    }
                    continue;
                }
                for (Integer r : rowNums) {
                    addDropBoxData = new DropBoxData();
                    addDropBoxData.setValues(dropBoxDataValues);
                    addDropBoxData.setByCol(enumByCol);
                    addDropBoxData.setColNum(Integer.valueOf(table.rows().getNames().length));
                    addDropBoxData.setRowNum(r);
                    addDropBoxData.setEnueName(queryField.getTitle());
                    dropBoxData.add(addDropBoxData);
                    for (int c = 0; c < colIndexCount; ++c) {
                        if (r >= rowIndexCount || ((DataFillBaseCell)table.getData().get(c, r.intValue())).getValue() == null || !((DataFillBaseCell)table.getData().get(c, r.intValue())).getValue().toString().contains(";")) continue;
                        for (String eachVal : originCellVal = ((DataFillBaseCell)table.getData().get(c, r.intValue())).getValue().toString().split(";")) {
                            if (entityDataResultMap.get(eachVal) == null) continue;
                            oldVal = ((DataFillBaseCell)table.getData().get(c, r.intValue())).getValue().toString();
                            newVal = oldVal.replaceFirst(eachVal, entityDataResultMap.get(eachVal));
                            cellData = new DataFillBaseCell();
                            cellData.setValue((Serializable)((Object)newVal));
                            table.getData().set(c, r.intValue(), (Object)cellData);
                        }
                    }
                }
            }
            queryInfo.getContext().setDimensionValues(dimensionValues);
            monitor.progressAndMessage(0.75, "\u8868\u683c\u5343\u5206\u6bd4\u7c7b\u578b\u6570\u636e\u5904\u7406");
            if (table != null && table.columns() != null && table.columns().size() > 0) {
                DataFillBaseCell cell;
                DataFillZBIndexData colData;
                BigDecimal bigDecimal = new BigDecimal("1000");
                if (table.columns().getKey(Integer.valueOf(0)) instanceof DataFillZBIndexData) {
                    for (int i = 0; i < table.columnSize(); ++i) {
                        colData = (DataFillZBIndexData)table.columns().getKey(Integer.valueOf(i));
                        if (colData.getFieldFormat() == null || RatioType.PERMIL != colData.getFieldFormat().getRatioType()) continue;
                        for (int j = 0; j < table.rowSize(); ++j) {
                            cell = (DataFillBaseCell)table.getData().get(i, j);
                            if (cell == null || cell.getValue() == null) continue;
                            cell.setValue(((BigDecimal)cell.getValue()).multiply(bigDecimal));
                        }
                    }
                } else {
                    for (int i = 0; i < table.rowSize(); ++i) {
                        if (!(((IKey)table.rows().getKey(Integer.valueOf(i))).getElement(1) instanceof DataFillZBIndexData) || RatioType.PERMIL != (colData = (DataFillZBIndexData)((IKey)table.rows().getKey(Integer.valueOf(i))).getElement(1)).getFieldFormat().getRatioType()) continue;
                        for (int j = 0; j < table.columnSize(); ++j) {
                            cell = (DataFillBaseCell)table.getData().get(j, i);
                            if (cell == null || cell.getValue() == null) continue;
                            cell.setValue(((BigDecimal)cell.getValue()).multiply(bigDecimal));
                        }
                    }
                }
            }
            try {
                monitor.progressAndMessage(0.8, "\u8f93\u51fa\u8868\u683c\u5230\u6587\u4ef6");
                CellSheet cellSheet = this.toCellBook(table, queryInfo);
                XSSFWorkbook workBook = CellSheetToExcel.createWorkBook();
                CellSheetToExcel cellSheetToExcel = new CellSheetToExcel(workBook);
                cellSheetToExcel.setDropBoxData(dropBoxData);
                cellSheetToExcel.writeToExcel(cellSheet, this.messageSource.getMessage("nr.dataFill.queryResults", null, NrDataFillI18nUtil.getCurrentLocale()));
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                workBook.write(out);
                byte[] bytes = out.toByteArray();
                ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
                LocalDateTime currentTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss.SSS");
                String formattedTime = currentTime.format(formatter).replace(" ", "");
                FileInfo fileInfo = this.fileService.area("DATA_FILL_EXPORT").uploadTemp(formattedTime + "\u81ea\u5b9a\u4e49\u5f55\u5165\u8868\u683c\u5bfc\u51fa.xlsx", (InputStream)inputStream);
                if (fileInfo != null) {
                    monitor.finish("\u5bfc\u51fa\u5b8c\u6210", (Object)fileInfo);
                }
                monitor.canceled("\u6587\u4ef6\u4e0a\u4f20\u5931\u8d25", null);
            }
            catch (IOException iOException) {
                logger.error("\u81ea\u5b9a\u4e49\u5f55\u5165\u5bfc\u51faexcel\u51fa\u9519\uff01", iOException);
                throw new DataFillRuntimeException("\u8f6c\u6362Excel\u62a5\u9519\uff01\uff1a" + dataResult.getMessage());
            }
        } else {
            monitor.error("\u67e5\u8be2\u51fa\u9519", null);
        }
    }

    private CellSheet toCellBook(DataFrame<DataFillBaseCell> table, DataFillDataQueryInfo queryInfo) {
        boolean displayIndent = queryInfo.getContext().getModel().getOtherOption().isDisplayIndent();
        HeaderMode tableHeaderMode = queryInfo.getContext().getModel().getOtherOption().getTableHeaderMode();
        boolean isMerge = false;
        if (tableHeaderMode == HeaderMode.MERGE) {
            isMerge = true;
        }
        Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionParser.getFieldTypeQueryFields(queryInfo.getContext());
        String masterDimsion = fieldTypeQueryFields.get((Object)FieldType.MASTER).get(0).getSimplifyFullCode();
        String headColor = "f2f2f2";
        String formulaColor = "ebf9f5";
        String borderColor = "8C8C8C";
        Index rows = table.rows();
        ArrayList r_levels = new ArrayList(rows.levels());
        Index columns = table.columns();
        ArrayList c_levels = new ArrayList(columns.levels());
        int rowNum = r_levels.size() + columns.count();
        int colNum = rows.count() + c_levels.size();
        CellBook cellBook = new CellBook();
        CellSheet createSheet = cellBook.createSheet("\u67e5\u8be2\u7ed3\u679c", "\u67e5\u8be2\u7ed3\u679c", rowNum, colNum);
        int rowHide = 0;
        for (int l = 0; l < rows.count(); ++l) {
            int beforRow;
            String name = rows.getName(l);
            DataFillIndexInfo indexInfo = (DataFillIndexInfo)rows.getSources()[l];
            if (null != indexInfo.getHide() && indexInfo.getHide().booleanValue()) {
                ++rowHide;
                continue;
            }
            String lastRowValue = "";
            int beginRowIndex = 1;
            Cell cell = createSheet.getCell(0, l - rowHide);
            cell.setValue(name);
            cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
            cell.setBackGroundColor(headColor);
            cell.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
            this.setBorder(borderColor, cell);
            ColWidthType colWidthType = indexInfo.getColWidthType();
            if (null != colWidthType) {
                switch (colWidthType) {
                    case AUTOMATIC: {
                        createSheet.setColAutoWide(l - rowHide, true);
                        break;
                    }
                    default: {
                        createSheet.setColWide(l - rowHide, indexInfo.getColWidth() != null && indexInfo.getColWidth() > 0 ? indexInfo.getColWidth() : 100);
                    }
                }
            }
            for (int c = 0; c < rows.size(); ++c) {
                Object rowData = r_levels.get(c);
                DataFillIndexData indexData = null;
                if (rowData instanceof IKey) {
                    IKey key = (IKey)rowData;
                    indexData = (DataFillIndexData)key.getElement(l);
                } else {
                    indexData = (DataFillIndexData)rowData;
                }
                cell = createSheet.getCell(c + columns.count(), l - rowHide);
                String title = indexData.getTitle();
                Integer level = indexData.getLevel();
                if (null != level) {
                    StringBuilder builder = new StringBuilder(title);
                    for (int i = 0; i < level; ++i) {
                        builder.insert(0, "  ");
                    }
                    title = builder.toString();
                }
                if (isMerge) {
                    int beforRow2;
                    if (!StringUtils.hasLength(lastRowValue)) {
                        lastRowValue = title;
                    } else if (!lastRowValue.equals(title) && (beforRow2 = c + columns.count() - 2) > 0) {
                        Cell before2Cell = createSheet.getCell(beforRow2, l - rowHide);
                        Cell beforeCell = createSheet.getCell(beforRow2 + 1, l - rowHide);
                        if (StringUtils.hasLength(beforeCell.getValue()) && before2Cell.getValue().equals(beforeCell.getValue())) {
                            createSheet.mergeCells(beginRowIndex, l - rowHide, c + columns.count() - beginRowIndex, 1);
                            lastRowValue = null;
                            beginRowIndex = c + columns.count();
                        }
                    }
                }
                cell.setValue(title);
                cell.setBackGroundColor(headColor);
                cell.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
                this.setBorder(borderColor, cell);
                if (displayIndent && masterDimsion.equals(indexInfo.getDimensionName())) {
                    cell.setHorizontalAlignment(HorizontalAlignment.LEFT);
                    continue;
                }
                cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
            }
            if (!isMerge || (beforRow = rows.size() - 2 + columns.count()) <= 0) continue;
            Cell beforeCell = createSheet.getCell(beforRow, l - rowHide);
            Cell lastCell = createSheet.getCell(beforRow + 1, l - rowHide);
            if (!StringUtils.hasLength(beforeCell.getValue()) || !beforeCell.getValue().equals(lastCell.getValue())) continue;
            createSheet.mergeCells(beginRowIndex, l - rowHide, rows.size() + columns.count() - beginRowIndex, 1);
            lastRowValue = null;
        }
        int rowCount = rows.count() - rowHide;
        int colDataHide = 0;
        for (int l = 0; l < columns.count(); ++l) {
            block18: for (int c = 0; c < columns.size(); ++c) {
                Object columnData = c_levels.get(c);
                DataFillIndexData indexData = null;
                if (columnData instanceof IKey) {
                    IKey key = (IKey)columnData;
                    indexData = (DataFillIndexData)key.getElement(l);
                } else {
                    indexData = (DataFillIndexData)columnData;
                }
                if (null != indexData.getHide() && indexData.getHide().booleanValue()) {
                    ++colDataHide;
                    continue;
                }
                Cell cell = createSheet.getCell(l, rowCount + c - colDataHide);
                cell.setValue(indexData.getTitle());
                cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
                cell.setBackGroundColor(headColor);
                cell.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
                this.setBorder(borderColor, cell);
                ColWidthType colWidthType = indexData.getColWidthType();
                if (null == colWidthType) continue;
                switch (colWidthType) {
                    case AUTOMATIC: {
                        createSheet.setColAutoWide(rowCount + c - colDataHide, true);
                        continue block18;
                    }
                    default: {
                        createSheet.setColWide(rowCount + c - colDataHide, indexData.getColWidth() != null && indexData.getColWidth() > 0 ? indexData.getColWidth() : 100);
                    }
                }
            }
        }
        createSheet.mergeCells(0, 0, columns.count(), 1);
        createSheet.mergeCells(0, 1, columns.count(), 1);
        this.setBorder(borderColor, createSheet.getCell(0, 0));
        this.setBorder(borderColor, createSheet.getCell(0, 1));
        for (int r = 0; r < table.size(); ++r) {
            int zbHide = 0;
            Object rowData = r_levels.get(r);
            DataFillZBIndexData zbIndexData = null;
            if (rowData instanceof IKey) {
                Cell elements;
                IKey key = (IKey)rowData;
                for (Cell object : elements = key.getElements()) {
                    if (!(object instanceof DataFillZBIndexData)) continue;
                    zbIndexData = (DataFillZBIndexData)object;
                }
            } else if (rowData instanceof DataFillZBIndexData) {
                zbIndexData = (DataFillZBIndexData)rowData;
            }
            block21: for (int c = 0; c < table.length(); ++c) {
                Object columnData = c_levels.get(c);
                if (columnData instanceof DataFillZBIndexData) {
                    zbIndexData = (DataFillZBIndexData)columnData;
                }
                if (null != zbIndexData.getHide() && zbIndexData.getHide().booleanValue()) {
                    ++zbHide;
                    continue;
                }
                DataFillBaseCell dataFillBaseCell = (DataFillBaseCell)table.get(c, r);
                Cell cell = createSheet.getCell(r + columns.count(), c + rowCount - zbHide);
                this.setBorder(borderColor, cell);
                if (null == dataFillBaseCell) continue;
                Object value = dataFillBaseCell.getValue();
                if (null == value) {
                    value = "";
                }
                cell.setValue(value.toString());
                CellType cellType = zbIndexData.getCellType();
                switch (cellType) {
                    case NUMBER: {
                        cell.setDataTypeId("1");
                        cell.setHorizontalAlignment(HorizontalAlignment.RIGHT);
                        FieldFormat fieldFormat = zbIndexData.getFieldFormat();
                        StringBuilder builder = new StringBuilder();
                        if (null == fieldFormat) {
                            builder.append("###");
                        } else {
                            boolean percent = fieldFormat.isPermil();
                            if (percent) {
                                builder.append("#,##0");
                            } else {
                                builder.append("0");
                            }
                            int decimal = fieldFormat.getDecimal();
                            if (decimal > 0) {
                                builder.append(".");
                                for (int i = 0; i < decimal; ++i) {
                                    builder.append("0");
                                }
                            }
                            if (fieldFormat.getRatioType() == RatioType.PERCENT) {
                                builder.append("%");
                            } else if (fieldFormat.getRatioType() == RatioType.PERMIL) {
                                builder.append("\"\u2030\"");
                            }
                        }
                        cell.setFormatter(builder.toString());
                        continue block21;
                    }
                    case STRING: {
                        cell.setHorizontalAlignment(HorizontalAlignment.LEFT);
                        cell.setDataTypeId("0");
                        continue block21;
                    }
                    case DATE: {
                        cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
                        cell.setDataTypeId("3");
                        cell.setFormatter("yyyy-MM-dd");
                        continue block21;
                    }
                    case ENUM: {
                        cell.setHorizontalAlignment(HorizontalAlignment.LEFT);
                        if (!(dataFillBaseCell instanceof DataFillEnumCell)) continue block21;
                        DataFillEnumCell enumCell = (DataFillEnumCell)dataFillBaseCell;
                        cell.setValue("-".equals(enumCell.getCode()) ? "-" : enumCell.toString());
                        continue block21;
                    }
                    case BOOLEAN: {
                        if (value instanceof Boolean) {
                            if (((Boolean)value).booleanValue()) {
                                cell.setValue("\u221a");
                            } else {
                                cell.setValue("\u00d7");
                            }
                            cell.setDataTypeId("0");
                        } else {
                            cell.setValue("\u00d7");
                        }
                        cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
                        continue block21;
                    }
                    case EXPRESSION: {
                        DataFillExpressionCell expressionCell = (DataFillExpressionCell)dataFillBaseCell;
                        cell.setFormula(expressionCell.getExpression());
                        cell.setDataTypeId("4");
                        cell.setHorizontalAlignment(HorizontalAlignment.GENERAL);
                        cell.setBackGroundColor(formulaColor);
                        cell.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
                        continue block21;
                    }
                }
            }
        }
        return createSheet;
    }

    private void setBorder(String borderColor, Cell cell) {
        cell.setRightBorderStyle(CellBorderStyle.THIN);
        cell.setRightBorderColor(borderColor);
        cell.setBottomBorderStyle(CellBorderStyle.THIN);
        cell.setBottomBorderColor(borderColor);
    }
}

