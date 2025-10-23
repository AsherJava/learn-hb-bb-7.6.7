/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataRow
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.office.excel.HSSFHelper
 *  com.jiuqi.bi.quickreport.builder.define.FieldDefine
 *  com.jiuqi.bi.quickreport.builder.define.FieldObject
 *  com.jiuqi.bi.quickreport.builder.define.GridDefine
 *  com.jiuqi.bi.quickreport.builder.define.GroupDefine
 *  com.jiuqi.bi.quickreport.builder.define.MeasureFieldDefine
 *  com.jiuqi.bi.text.TimeFieldFormat
 *  com.jiuqi.bi.types.DataTypes
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.zbquery.util;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.office.excel.HSSFHelper;
import com.jiuqi.bi.quickreport.builder.define.FieldDefine;
import com.jiuqi.bi.quickreport.builder.define.FieldObject;
import com.jiuqi.bi.quickreport.builder.define.GridDefine;
import com.jiuqi.bi.quickreport.builder.define.GroupDefine;
import com.jiuqi.bi.quickreport.builder.define.MeasureFieldDefine;
import com.jiuqi.bi.text.TimeFieldFormat;
import com.jiuqi.bi.types.DataTypes;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.zbquery.engine.dataset.QueryDSModelBuilder;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.util.PeriodUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class XlsxSimpleExporter {
    private static final short ROW_HEIGHT = 400;
    private static final String DATAFORMAT_TEXT = "@";
    private static final String DATAFORMAT_DATE = "yyyy-mm-dd";
    private static final String DATAFORMAT_DATE_TIME = "yyyy-mm-dd hh:mm:ss";
    private static final String CHAR_PERMILLAGE = "\u2030";
    private final OutputStream outputStream;
    private final QueryDSModelBuilder dsModelBuilder;
    private final GridDefine gridDefine;
    private final BIDataSet dataSet;
    private final String fileFormat;
    private SXSSFWorkbook workbook;
    private Sheet sheet;
    private List<CellWriter> cellWriters;
    private int rowIndex;
    private Map<String, CellStyle> styleCache;

    public XlsxSimpleExporter(OutputStream outputStream, QueryDSModelBuilder dsModelBuilder, GridDefine gridDefine, BIDataSet dataSet, String fileFormat) {
        this.outputStream = outputStream;
        this.dsModelBuilder = dsModelBuilder;
        this.gridDefine = gridDefine;
        this.dataSet = dataSet;
        this.fileFormat = fileFormat;
    }

    public void export() throws Exception {
        this.init();
        this.process();
        this.finish();
    }

    private void init() throws Exception {
        this.cellWriters = new ArrayList<CellWriter>();
        this.styleCache = new HashMap<String, CellStyle>();
        this.workbook = new SXSSFWorkbook();
        this.sheet = this.workbook.getNumberOfSheets() > 0 ? this.workbook.getSheetAt(0) : this.workbook.createSheet();
        this.rowIndex = 0;
        ArrayList gridFields = new ArrayList();
        gridFields.addAll(this.gridDefine.getRows());
        gridFields.addAll(this.gridDefine.getCols());
        for (FieldObject gridField : gridFields) {
            this.initCellWriter(gridField);
        }
    }

    private void initCellWriter(FieldObject gridField) {
        if (gridField instanceof FieldDefine) {
            if ("SYS_ROWNUM".equals(gridField.getName())) {
                return;
            }
            int fieldIndex = this.dataSet.getMetadata().indexOf(gridField.getName());
            if (fieldIndex != -1) {
                CellWriter cellWriter;
                FieldDefine fieldDefine = (FieldDefine)gridField;
                BIDataSetFieldInfo field = (BIDataSetFieldInfo)this.dataSet.getMetadata().getColumn(fieldIndex).getInfo();
                if (field.getFieldType() == FieldType.TIME_DIM) {
                    cellWriter = new PeriodCellWriter(fieldDefine, fieldIndex, this.getDimCellStyle(this.workbook), field);
                } else {
                    String dataFormat = field.getShowPattern();
                    int dataType = field.getValType();
                    cellWriter = dataType == 6 ? new StringCellWriter(fieldDefine, fieldIndex, this.getMeasureCellStyle(this.workbook, DATAFORMAT_TEXT)) : (DataTypes.isNumber((int)dataType) ? (dataFormat != null && dataFormat.contains(CHAR_PERMILLAGE) ? new PermillageCellWriter(fieldDefine, fieldIndex, this.getPermillageCellStyle(this.workbook), dataFormat) : new NumberCellWriter(fieldDefine, fieldIndex, this.getMeasureCellStyle(this.workbook, dataFormat))) : (dataType == 1 ? new BooleanCellWriter(fieldDefine, fieldIndex, this.getMeasureCellStyle(this.workbook, dataFormat)) : (dataType == 2 ? new DateCellWriter(fieldDefine, fieldIndex, this.getMeasureCellStyle(this.workbook, StringUtils.isEmpty((String)dataFormat) ? "yyyy-MM-dd" : dataFormat)) : new StringCellWriter(fieldDefine, fieldIndex, this.getMeasureCellStyle(this.workbook, dataFormat)))));
                }
                this.cellWriters.add(cellWriter);
            }
        } else {
            for (FieldObject childField : ((GroupDefine)gridField).getChildren()) {
                this.initCellWriter(childField);
            }
        }
    }

    private void process() throws Exception {
        int i;
        CellStyle headCellStyle = this.getHeadCellStyle(this.workbook);
        Row row = this.sheet.createRow(this.rowIndex++);
        row.setHeight((short)400);
        for (i = 0; i < this.cellWriters.size(); ++i) {
            String magnitudeTitle;
            FieldDefine fieldDefine = this.cellWriters.get((int)i).fieldDefine;
            Cell cell = row.createCell(i);
            cell.setCellStyle(headCellStyle);
            cell.setCellValue(fieldDefine.getTitle());
            this.sheet.setColumnWidth(i, HSSFHelper.pixelToWidth((int)(fieldDefine.getColWidth() > 0 ? fieldDefine.getColWidth() : 120)));
            if (!(fieldDefine instanceof MeasureFieldDefine) || !StringUtils.isNotEmpty((String)(magnitudeTitle = ((MeasureFieldDefine)fieldDefine).getMagnitudeTitle()))) continue;
            cell.setCellValue(fieldDefine.getTitle() + "(" + magnitudeTitle + ")");
        }
        this.sheet.createFreezePane(0, 1, 0, 1);
        for (i = 0; i < this.dataSet.getRecordCount(); ++i) {
            this.process(this.dataSet.get(i));
        }
    }

    private void process(BIDataRow row) throws Exception {
        Row sxssfRow = this.sheet.createRow(this.rowIndex++);
        sxssfRow.setHeight((short)400);
        for (int j = 0; j < this.cellWriters.size(); ++j) {
            Cell cell = sxssfRow.createCell(j);
            this.cellWriters.get(j).write(cell, row);
        }
    }

    private void finish() throws Exception {
        try {
            this.workbook.write(this.outputStream);
        }
        catch (IOException e) {
            throw new Exception(e);
        }
    }

    private CellStyle getHeadCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(workbook.createDataFormat().getFormat(DATAFORMAT_TEXT));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.index);
        cellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.index);
        cellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.index);
        cellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.index);
        return cellStyle;
    }

    private CellStyle getDimCellStyle(Workbook workbook) {
        CellStyle cellStyle = this.styleCache.get("_dim_cellStyle_");
        if (cellStyle == null) {
            cellStyle = workbook.createCellStyle();
            cellStyle.setDataFormat(workbook.createDataFormat().getFormat(DATAFORMAT_TEXT));
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.index);
            cellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.index);
            cellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.index);
            cellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.index);
            this.styleCache.put("_dim_cellStyle_", cellStyle);
        }
        return cellStyle;
    }

    private CellStyle getMeasureCellStyle(Workbook workbook, String pattern) {
        CellStyle cellStyle = this.styleCache.get(pattern);
        if (cellStyle == null) {
            cellStyle = workbook.createCellStyle();
            cellStyle.setDataFormat(workbook.createDataFormat().getFormat(pattern));
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.index);
            cellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.index);
            cellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.index);
            cellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.index);
            this.styleCache.put(pattern, cellStyle);
        }
        return cellStyle;
    }

    private CellStyle getPermillageCellStyle(Workbook workbook) {
        CellStyle cellStyle = this.styleCache.get("_permillage_cellStyle_");
        if (cellStyle == null) {
            cellStyle = workbook.createCellStyle();
            cellStyle.setDataFormat(workbook.createDataFormat().getFormat(DATAFORMAT_TEXT));
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setAlignment(HorizontalAlignment.RIGHT);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.index);
            cellStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.index);
            cellStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.index);
            cellStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.index);
            this.styleCache.put("_permillage_cellStyle_", cellStyle);
        }
        return cellStyle;
    }

    private class DateCellWriter
    extends CellWriter {
        public DateCellWriter(FieldDefine fieldDefine, int fieldIndex, CellStyle cellStyle) {
            super(fieldDefine, fieldIndex, cellStyle);
        }

        @Override
        public void write(Cell cell, BIDataRow row) {
            cell.setCellStyle(this.cellStyle);
            Object value = row.getValue(this.fieldIndex);
            if (value != null) {
                cell.setCellValue((Calendar)value);
            }
        }
    }

    private class BooleanCellWriter
    extends CellWriter {
        public BooleanCellWriter(FieldDefine fieldDefine, int fieldIndex, CellStyle cellStyle) {
            super(fieldDefine, fieldIndex, cellStyle);
        }

        @Override
        public void write(Cell cell, BIDataRow row) {
            cell.setCellStyle(this.cellStyle);
            Object value = row.getValue(this.fieldIndex);
            if (value != null) {
                cell.setCellValue((Boolean)value);
            }
        }
    }

    private class PermillageCellWriter
    extends CellWriter {
        private DecimalFormat decimalformat;

        public PermillageCellWriter(FieldDefine fieldDefine, int fieldIndex, CellStyle cellStyle, String dataFormat) {
            super(fieldDefine, fieldIndex, cellStyle);
            this.decimalformat = new DecimalFormat(dataFormat.replace(XlsxSimpleExporter.CHAR_PERMILLAGE, ""));
        }

        @Override
        public void write(Cell cell, BIDataRow row) {
            cell.setCellStyle(this.cellStyle);
            Object value = row.getValue(this.fieldIndex);
            if (value != null) {
                cell.setCellValue(this.decimalformat.format(((Number)value).doubleValue() * 1000.0) + XlsxSimpleExporter.CHAR_PERMILLAGE);
            }
        }
    }

    private class NumberCellWriter
    extends CellWriter {
        private double magnitudeValue;

        public NumberCellWriter(FieldDefine fieldDefine, int fieldIndex, CellStyle cellStyle) {
            super(fieldDefine, fieldIndex, cellStyle);
            this.magnitudeValue = 1.0;
            if (fieldDefine instanceof MeasureFieldDefine) {
                this.magnitudeValue = ((MeasureFieldDefine)fieldDefine).getMagnitudeValue();
                if (this.magnitudeValue == 0.0) {
                    this.magnitudeValue = 1.0;
                }
            }
        }

        @Override
        public void write(Cell cell, BIDataRow row) {
            cell.setCellStyle(this.cellStyle);
            Object value = row.getValue(this.fieldIndex);
            if (value != null) {
                if (this.magnitudeValue != 1.0) {
                    cell.setCellValue(((Number)value).doubleValue() / this.magnitudeValue);
                } else {
                    cell.setCellValue(((Number)value).doubleValue());
                }
            }
        }
    }

    private class PeriodCellWriter
    extends CellWriter {
        private TimeFieldFormat timeFormat;

        public PeriodCellWriter(FieldDefine fieldDefine, int fieldIndex, CellStyle cellStyle, BIDataSetFieldInfo field) {
            super(fieldDefine, fieldIndex, cellStyle);
            QueryDimension periodDim = XlsxSimpleExporter.this.dsModelBuilder.getQueryModelBuilder().getModelFinder().getPeriodDimension();
            this.timeFormat = new TimeFieldFormat(field.getTimegranularity().value(), field.getShowPattern(), field.getValType(), field.isTimekey(), field.getDataPattern());
            if (PeriodUtil.is13Period(periodDim)) {
                this.timeFormat.setFiscalMonth(periodDim.getMinFiscalMonth(), periodDim.getMaxFiscalMonth());
            }
        }

        @Override
        public void write(Cell cell, BIDataRow row) {
            cell.setCellStyle(this.cellStyle);
            Object value = row.getValue(this.fieldIndex);
            if (value != null) {
                cell.setCellValue(this.timeFormat.format(value));
            }
        }
    }

    private class StringCellWriter
    extends CellWriter {
        public StringCellWriter(FieldDefine fieldDefine, int fieldIndex, CellStyle cellStyle) {
            super(fieldDefine, fieldIndex, cellStyle);
        }

        @Override
        public void write(Cell cell, BIDataRow row) {
            cell.setCellStyle(this.cellStyle);
            Object value = row.getValue(this.fieldIndex);
            if (value != null) {
                cell.setCellValue(value.toString());
            }
        }
    }

    private abstract class CellWriter {
        protected FieldDefine fieldDefine;
        protected int fieldIndex;
        protected CellStyle cellStyle;

        public CellWriter(FieldDefine fieldDefine, int fieldIndex, CellStyle cellStyle) {
            this.fieldDefine = fieldDefine;
            this.fieldIndex = fieldIndex;
            this.cellStyle = cellStyle;
        }

        public abstract void write(Cell var1, BIDataRow var2);
    }
}

