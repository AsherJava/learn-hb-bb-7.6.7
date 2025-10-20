/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.syntax.cell.Position
 */
package com.jiuqi.bi.quickreport.engine.style;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.build.CellValue;
import com.jiuqi.bi.quickreport.engine.build.StyleValue;
import com.jiuqi.bi.quickreport.engine.style.IStyleProcessor;
import com.jiuqi.bi.quickreport.engine.style.ReportStyleException;
import com.jiuqi.bi.quickreport.engine.style.StyleProcessor;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorksheet;
import com.jiuqi.bi.quickreport.model.IconStyle;
import com.jiuqi.bi.syntax.cell.Position;

public final class IconProcessor
extends StyleProcessor<IconStyle> {
    public IconProcessor(IStyleProcessor next) {
        super(next);
    }

    @Override
    protected void applyCellStyle(EngineWorksheet worksheet, Position position, IconStyle style) throws ReportStyleException {
        GridData grid = worksheet.getResultGrid().getGridData();
        CellValue cellValue = (CellValue)grid.getObj(position.col(), position.row());
        if (cellValue == null) {
            throw new ReportStyleException("\u7a0b\u5e8f\u9519\u8bef\uff0c\u65e0\u6cd5\u83b7\u53d6\u5355\u5143\u683c\u503c\u5185\u5bb9\uff1a" + worksheet.name() + "!" + position);
        }
        if (cellValue.styleValue == null) {
            cellValue.styleValue = new StyleValue();
        }
        cellValue.styleValue.setIconStyle(style);
    }
}

