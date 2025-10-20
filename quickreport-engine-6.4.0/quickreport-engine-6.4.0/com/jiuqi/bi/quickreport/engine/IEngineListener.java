/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine;

import com.jiuqi.bi.quickreport.engine.ReportEngineException;
import com.jiuqi.bi.quickreport.engine.area.GridArea;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.interaction.ReportInteraction;
import com.jiuqi.bi.quickreport.engine.layer.CalcLayer;
import com.jiuqi.bi.quickreport.engine.workbook.EngineWorkbook;
import java.util.List;

public interface IEngineListener {
    default public boolean begin(ReportContext context) throws ReportEngineException {
        return true;
    }

    default public boolean parse(EngineWorkbook workbook) throws ReportEngineException {
        return true;
    }

    default public boolean analyse(List<GridArea> areas, List<CalcLayer> layers) throws ReportEngineException {
        return true;
    }

    default public boolean build(EngineWorkbook workbook) throws ReportEngineException {
        return true;
    }

    default public boolean interact(ReportInteraction interaction) throws ReportEngineException {
        return true;
    }

    default public boolean writeback(EngineWorkbook workbook) throws ReportEngineException {
        return true;
    }

    default public boolean end(EngineWorkbook workbook) throws ReportEngineException {
        return true;
    }
}

