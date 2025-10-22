/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BlobValue
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.np.office.excel;

import com.jiuqi.bi.dataset.BlobValue;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.util.StringUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public final class DataSetExcelSerializer {
    private DataSet<?> dataset;
    private FieldReader[] readers;

    private DataSetExcelSerializer(DataSet<?> dataset) {
        this.dataset = dataset;
        this.initFieldReaders();
    }

    private void initFieldReaders() {
        Metadata metadata = this.dataset.getMetadata();
        this.readers = new FieldReader[metadata.getColumnCount()];
        block10: for (int i = 0; i < metadata.getColumnCount(); ++i) {
            Column column = metadata.getColumn(i);
            switch (column.getDataType()) {
                case 10: {
                    this.readers[i] = new BigDecimalFieldReader(i);
                    continue block10;
                }
                case 9: {
                    this.readers[i] = new BlobFieldReader(i);
                    continue block10;
                }
                case 1: {
                    this.readers[i] = new BooleanFieldReader(i);
                    continue block10;
                }
                case 2: {
                    this.readers[i] = new DateTimeFieldReader(i);
                    continue block10;
                }
                case 3: {
                    this.readers[i] = new DoubleFieldReader(i);
                    continue block10;
                }
                case 5: {
                    this.readers[i] = new IntFieldReader(i);
                    continue block10;
                }
                case 8: {
                    this.readers[i] = new LongFieldReader(i);
                    continue block10;
                }
                case 6: {
                    this.readers[i] = new StringFieldReader(i);
                    continue block10;
                }
                default: {
                    this.readers[i] = new UnknownFieldReader(i);
                }
            }
        }
    }

    private void save(Sheet sheet) throws IOException {
        Metadata metadata = this.dataset.getMetadata();
        Row title = sheet.createRow(0);
        for (int i = 0; i < metadata.getColumnCount(); ++i) {
            Column column = metadata.getColumn(i);
            Cell cell = title.createCell(i);
            if (StringUtils.isEmpty((String)column.getTitle())) {
                cell.setCellValue(column.getName());
                continue;
            }
            cell.setCellValue(column.getTitle());
        }
        Iterator iterator = this.dataset.iterator();
        int count = 1;
        while (iterator.hasNext()) {
            DataRow dataRow = (DataRow)iterator.next();
            Row excelRow = sheet.createRow(count);
            for (int i = 0; i < this.readers.length; ++i) {
                FieldReader reader = this.readers[i];
                Cell cell = excelRow.createCell(i);
                reader.read(dataRow, cell);
            }
            ++count;
        }
    }

    public static void saveToExcel(DataSet<?> dataSet, Sheet workSheet) throws IOException {
        DataSetExcelSerializer serializer = new DataSetExcelSerializer(dataSet);
        serializer.save(workSheet);
    }

    public static void saveToExcel(DataSet<?> dataSet, OutputStream outStream) throws IOException {
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        SXSSFSheet sheet = workbook.createSheet("Sheet1");
        DataSetExcelSerializer.saveToExcel(dataSet, sheet);
        workbook.write(outStream);
    }

    private final class UnknownFieldReader
    extends FieldReader {
        public UnknownFieldReader(int index) {
            super(index);
        }

        @Override
        public void read(DataRow row, Cell cell) {
            Object obj = row.getValue(this.index);
            if (obj != null) {
                cell.setCellValue(String.valueOf(obj));
            } else {
                cell.setCellValue("");
            }
            cell.setCellType(CellType.STRING);
        }
    }

    private final class LongFieldReader
    extends FieldReader {
        public LongFieldReader(int index) {
            super(index);
        }

        @Override
        public void read(DataRow row, Cell cell) {
            Object obj = row.getValue(this.index);
            if (obj != null) {
                long i = ((Number)obj).longValue();
                cell.setCellValue(i);
                cell.setCellType(CellType.NUMERIC);
            } else {
                cell.setCellValue("");
                cell.setCellType(CellType.STRING);
            }
        }
    }

    private final class BigDecimalFieldReader
    extends FieldReader {
        public BigDecimalFieldReader(int index) {
            super(index);
        }

        @Override
        public void read(DataRow row, Cell cell) {
            Object obj = row.getValue(this.index);
            if (obj != null) {
                BigDecimal doub = (BigDecimal)obj;
                cell.setCellValue(doub.doubleValue());
                cell.setCellType(CellType.NUMERIC);
            } else {
                cell.setCellValue("");
                cell.setCellType(CellType.STRING);
            }
        }
    }

    private final class BlobFieldReader
    extends FieldReader {
        public BlobFieldReader(int index) {
            super(index);
        }

        @Override
        public void read(DataRow row, Cell cell) {
            BlobValue value = (BlobValue)row.getValue(this.index);
            if (value != null) {
                cell.setCellValue(value.toString());
            } else {
                cell.setCellValue("");
            }
            cell.setCellType(CellType.STRING);
        }
    }

    private final class BooleanFieldReader
    extends FieldReader {
        public BooleanFieldReader(int index) {
            super(index);
        }

        @Override
        public void read(DataRow row, Cell cell) {
            Object obj = row.getValue(this.index);
            if (obj != null) {
                boolean value = (Boolean)obj;
                cell.setCellValue(value);
                cell.setCellType(CellType.BOOLEAN);
            } else {
                cell.setCellValue("");
                cell.setCellType(CellType.STRING);
            }
        }
    }

    private final class IntFieldReader
    extends FieldReader {
        public IntFieldReader(int index) {
            super(index);
        }

        @Override
        public void read(DataRow row, Cell cell) {
            Object obj = row.getValue(this.index);
            if (obj != null) {
                int i = ((Number)obj).intValue();
                cell.setCellValue(i);
                cell.setCellType(CellType.NUMERIC);
            } else {
                cell.setCellValue("");
                cell.setCellType(CellType.STRING);
            }
        }
    }

    private final class DateTimeFieldReader
    extends FieldReader {
        private SimpleDateFormat df;

        public DateTimeFieldReader(int index) {
            super(index);
            this.df = new SimpleDateFormat("yyyy-MM-dd");
        }

        @Override
        public void read(DataRow row, Cell cell) {
            Calendar calendar = row.getDate(this.index);
            String value = calendar == null ? "" : this.df.format(calendar.getTime());
            cell.setCellValue(value);
            cell.setCellType(CellType.STRING);
        }
    }

    private final class DoubleFieldReader
    extends FieldReader {
        public DoubleFieldReader(int index) {
            super(index);
        }

        @Override
        public void read(DataRow row, Cell cell) {
            Object obj = row.getValue(this.index);
            if (obj != null) {
                double value = ((Number)obj).doubleValue();
                cell.setCellValue(value);
                cell.setCellType(CellType.NUMERIC);
            } else {
                cell.setCellValue("");
                cell.setCellType(CellType.STRING);
            }
        }
    }

    private final class StringFieldReader
    extends FieldReader {
        public StringFieldReader(int index) {
            super(index);
        }

        @Override
        public void read(DataRow row, Cell cell) {
            String value = row.getString(this.index);
            if (value == null) {
                cell.setCellValue("");
            } else {
                cell.setCellValue(value);
            }
            cell.setCellType(CellType.STRING);
        }
    }

    private abstract class FieldReader {
        protected int index;

        public FieldReader(int index) {
            this.index = index;
        }

        public abstract void read(DataRow var1, Cell var2);
    }
}

