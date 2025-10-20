/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.CellField
 *  com.jiuqi.bi.grid.DateCellProperty
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.grid.NumberCellPropertyIntf
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.DateCellProperty;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.NumberCellPropertyIntf;
import com.jiuqi.bi.util.StringUtils;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

final class GridSerializer {
    private DataSet<?> dataset;

    public GridSerializer(DataSet<?> dataset) {
        this.dataset = dataset;
    }

    public void save(GridData gridData) {
        if (gridData.getRowCount() < 2 || gridData.getColCount() < 1) {
            return;
        }
        int colSize = Math.min(gridData.getColCount() - 1, this.dataset.getMetadata().getColumnCount());
        for (int col = 1; col <= colSize; ++col) {
            Column<?> field = this.dataset.getMetadata().getColumn(col - 1);
            GridCell cell = gridData.getCell(col, 1);
            cell.setType(1);
            cell.setHorzAlign(3);
            gridData.setCell(cell);
            cell.setCellData(StringUtils.isEmpty((String)field.getTitle()) ? field.getName() : field.getTitle());
        }
        this.save(gridData, new CellField(1, 2, gridData.getColCount() - 1, gridData.getRowCount() - 1));
    }

    public void load(GridData gridData) throws DataSetException {
        this.load(gridData, new CellField(1, 2, gridData.getColCount() - 1, gridData.getRowCount() - 1));
    }

    public void save(GridData gridData, CellField region) {
        CellProcessor[] processors = this.createCellProcessors(region);
        int rowNum = region.top;
        for (DataRow row : this.dataset) {
            for (int i = 0; i < processors.length; ++i) {
                GridCell cell = gridData.getCell(i + region.left, rowNum);
                Object value = row.getValue(i);
                processors[i].toCell(cell, value);
                gridData.setCell(cell);
            }
            ++rowNum;
        }
    }

    public void load(GridData gridData, CellField region) throws DataSetException {
        CellProcessor[] processors = this.createCellProcessors(region);
        this.dataset.clear();
        this.dataset.beginUpdate();
        for (int rowNum = region.top; rowNum <= region.bottom; ++rowNum) {
            boolean isNull = true;
            Object[] record = new Object[processors.length];
            for (int i = 0; i < processors.length; ++i) {
                Object value;
                GridCell cell = gridData.getCell(region.left + i, rowNum);
                record[i] = value = processors[i].fromCell(cell);
                if (value == null) continue;
                isNull = false;
            }
            if (!isNull && !this.dataset.add(record)) break;
        }
        this.dataset.endUpdate();
    }

    private CellProcessor[] createCellProcessors(CellField region) {
        int colSize = Math.min(region.right - region.left + 1, this.dataset.getMetadata().getColumnCount());
        CellProcessor[] processors = new CellProcessor[colSize];
        for (int i = 0; i < colSize; ++i) {
            processors[i] = this.createCellProceesor(this.dataset.getMetadata().getColumn(i));
        }
        return processors;
    }

    private CellProcessor createCellProceesor(Column<?> column) {
        switch (column.getDataType()) {
            case 3: {
                return new DoubleProcessor();
            }
            case 5: {
                return new IntegerProcessor();
            }
            case 6: {
                return new StringProcessor();
            }
            case 10: {
                return new BigDecimalProcessor();
            }
            case 1: {
                return new BooleanProcessor();
            }
            case 8: {
                return new LongProcessor();
            }
            case 2: {
                return new DateProcessor();
            }
        }
        return new DefaultProcessor();
    }

    private static final class DefaultProcessor
    extends CellProcessor {
        private DefaultProcessor() {
        }

        @Override
        public void toCell(GridCell cell, Object value) {
            cell.setType(0);
            cell.setCellData(value == null ? null : value.toString());
        }

        @Override
        public Object fromCell(GridCell cell) {
            return null;
        }
    }

    private static final class BooleanProcessor
    extends CellProcessor {
        private BooleanProcessor() {
        }

        @Override
        public void toCell(GridCell cell, Object value) {
            cell.setType(4);
            if (value == null) {
                cell.setCellData(null);
            } else {
                cell.setCellData((Boolean)value != false ? "1" : "0");
            }
        }

        @Override
        public Object fromCell(GridCell cell) {
            String data = cell.getCellData();
            if (StringUtils.isEmpty((String)data)) {
                return null;
            }
            return "1".equals(data) || "true".equalsIgnoreCase(data) ? Boolean.TRUE : Boolean.FALSE;
        }
    }

    private static final class BigDecimalProcessor
    extends CellProcessor {
        private BigDecimalProcessor() {
        }

        @Override
        public void toCell(GridCell cell, Object value) {
            cell.setType(2);
            cell.setHorzAlign(2);
            NumberCellPropertyIntf n = cell.toNumberCell();
            n.setDecimal(4);
            n.setThoundsMarks(true);
            cell.setCellData(value == null ? null : value.toString());
        }

        @Override
        public Object fromCell(GridCell cell) {
            String data = cell.getCellData();
            if (StringUtils.isEmpty((String)data)) {
                return null;
            }
            data = data.replace(",", "");
            return new BigDecimal(data);
        }
    }

    private static final class StringProcessor
    extends CellProcessor {
        private StringProcessor() {
        }

        @Override
        public void toCell(GridCell cell, Object value) {
            cell.setType(1);
            cell.setHorzAlign(1);
            cell.setCellData(value == null ? null : value.toString());
        }

        @Override
        public Object fromCell(GridCell cell) {
            return cell.getCellData();
        }
    }

    private static final class DateProcessor
    extends CellProcessor {
        private DateProcessor() {
        }

        @Override
        public void toCell(GridCell cell, Object value) {
            cell.setType(5);
            cell.setHorzAlign(3);
            DateCellProperty d = new DateCellProperty(cell);
            d.setDateShowType((short)0);
            if (value == null) {
                cell.setCellData(null);
            } else {
                Date date = ((Calendar)value).getTime();
                cell.setDate(date);
            }
        }

        @Override
        public Object fromCell(GridCell cell) {
            Date date = cell.getDate();
            if (date == null) {
                return null;
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal;
        }
    }

    private static final class LongProcessor
    extends CellProcessor {
        private LongProcessor() {
        }

        @Override
        public void toCell(GridCell cell, Object value) {
            cell.setType(2);
            cell.setHorzAlign(2);
            NumberCellPropertyIntf n = cell.toNumberCell();
            n.setDecimal(0);
            n.setThoundsMarks(true);
            if (value == null) {
                cell.setCellData(null);
            } else {
                long v = ((Number)value).longValue();
                cell.setCellData(Long.toString(v));
            }
        }

        @Override
        public Object fromCell(GridCell cell) {
            if (StringUtils.isEmpty((String)cell.getCellData())) {
                return null;
            }
            return (long)cell.getFloat();
        }
    }

    private static final class IntegerProcessor
    extends CellProcessor {
        private IntegerProcessor() {
        }

        @Override
        public void toCell(GridCell cell, Object value) {
            cell.setType(2);
            cell.setHorzAlign(2);
            NumberCellPropertyIntf n = cell.toNumberCell();
            n.setDecimal(0);
            n.setThoundsMarks(true);
            if (value == null) {
                cell.setCellData(null);
            } else {
                cell.setInteger(((Number)value).intValue());
            }
        }

        @Override
        public Object fromCell(GridCell cell) {
            if (StringUtils.isEmpty((String)cell.getCellData())) {
                return null;
            }
            return cell.getInteger();
        }
    }

    private static final class DoubleProcessor
    extends CellProcessor {
        private DoubleProcessor() {
        }

        @Override
        public void toCell(GridCell cell, Object value) {
            cell.setType(2);
            cell.setHorzAlign(2);
            NumberCellPropertyIntf n = cell.toNumberCell();
            n.setDecimal(2);
            n.setThoundsMarks(true);
            if (value == null) {
                cell.setCellData(null);
            } else {
                cell.setFloat(((Number)value).doubleValue());
            }
        }

        @Override
        public Object fromCell(GridCell cell) {
            if (StringUtils.isEmpty((String)cell.getCellData())) {
                return null;
            }
            return cell.getFloat();
        }
    }

    private static abstract class CellProcessor {
        private CellProcessor() {
        }

        public abstract void toCell(GridCell var1, Object var2);

        public abstract Object fromCell(GridCell var1);
    }
}

