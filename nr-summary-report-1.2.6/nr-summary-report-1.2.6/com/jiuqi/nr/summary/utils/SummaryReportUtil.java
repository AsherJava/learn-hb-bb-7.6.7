/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Position
 */
package com.jiuqi.nr.summary.utils;

import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.nr.summary.api.SummaryFormula;
import com.jiuqi.nr.summary.model.cell.DataCell;
import com.jiuqi.nr.summary.model.cell.MainCell;

public class SummaryReportUtil {
    public static Position getPosition(MainCell mainCell) {
        return SummaryReportUtil.getPosition(mainCell.getY() + 1, mainCell.getX() + 1);
    }

    public static Position getPosition(DataCell dataCell) {
        return SummaryReportUtil.getPosition(dataCell.getY() + 1, dataCell.getX() + 1);
    }

    public static Position getPosition(int col, int row) {
        return new Position(col, row);
    }

    public static String getBJFormulaRptId(String solutionKey) {
        return "BJGS_" + solutionKey;
    }

    public static boolean isBJFormula(SummaryFormula summaryFormula) {
        return summaryFormula.getSummaryReportKey().equals(SummaryReportUtil.getBJFormulaRptId(summaryFormula.getSummarySolutionKey()));
    }

    public static boolean isBJFormula(String rptId) {
        return rptId.startsWith("BJGS_");
    }
}

