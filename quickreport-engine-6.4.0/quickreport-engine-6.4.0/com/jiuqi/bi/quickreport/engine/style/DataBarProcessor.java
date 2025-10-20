/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.cell.Position
 */
package com.jiuqi.bi.quickreport.engine.style;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.build.CellValue;
import com.jiuqi.bi.quickreport.engine.build.StyleValue;
import com.jiuqi.bi.quickreport.engine.result.DataBarInfo;
import com.jiuqi.bi.quickreport.engine.style.IStyleProcessor;
import com.jiuqi.bi.quickreport.engine.style.ReportStyleException;
import com.jiuqi.bi.quickreport.engine.style.StyleProcessor;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorksheet;
import com.jiuqi.bi.quickreport.model.DataBarStyle;
import com.jiuqi.bi.quickreport.model.StyleRegion;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.cell.Position;

public final class DataBarProcessor
extends StyleProcessor<DataBarStyle> {
    private final StyleRegion styleRegion;
    private Number minValue;
    private Number maxValue;

    public DataBarProcessor(IStyleProcessor next, StyleRegion styleRegion) {
        super(next);
        this.styleRegion = styleRegion;
    }

    @Override
    protected void applyCellStyle(EngineWorksheet worksheet, Position position, DataBarStyle style) throws ReportStyleException {
        CellValue cellValue = this.openCellValue(worksheet, position);
        if (cellValue == null) {
            return;
        }
        if (cellValue.styleValue == null) {
            cellValue.styleValue = new StyleValue();
        }
        cellValue.styleValue.setDataBarStyle(style);
        Number value = (Number)cellValue.value;
        if (value != null) {
            if (this.minValue == null || DataType.compare((Number)value, (Number)this.minValue) < 0) {
                this.minValue = value;
            }
            if (this.maxValue == null || DataType.compare((Number)value, (Number)this.maxValue) > 0) {
                this.maxValue = value;
            }
        }
    }

    private CellValue openCellValue(EngineWorksheet worksheet, Position position) throws ReportStyleException {
        GridData grid = worksheet.getResultGrid().getGridData();
        CellValue cellValue = (CellValue)grid.getObj(position.col(), position.row());
        if (cellValue == null) {
            throw new ReportStyleException("\u7a0b\u5e8f\u9519\u8bef\uff0c\u65e0\u6cd5\u83b7\u53d6\u5355\u5143\u683c\u503c\u5185\u5bb9\uff1a" + worksheet.name() + "!" + position);
        }
        return cellValue.value == null || cellValue.value instanceof Number ? cellValue : null;
    }

    @Override
    public Object getStyleInfo() {
        if (this.minValue == null || this.maxValue == null) {
            return null;
        }
        DataBarInfo info = new DataBarInfo();
        info.setMode(this.styleRegion.getDataBarMode());
        info.setDispStyle(this.styleRegion.getDataBarDispStyle());
        info.setGradual(this.styleRegion.isDataBarGradual());
        info.setDataBarOnly(this.styleRegion.isDataBarOnly());
        info.setMinValue(this.minValue.doubleValue());
        info.setMaxValue(this.maxValue.doubleValue());
        return info;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("DataBar(").append((Object)this.styleRegion.getDataBarMode()).append(", ").append((Object)this.styleRegion.getDataBarDispStyle());
        if (this.minValue != null && this.maxValue != null) {
            buffer.append(", ").append(this.minValue).append("~").append(this.maxValue);
        }
        buffer.append(')');
        return buffer.toString();
    }
}

