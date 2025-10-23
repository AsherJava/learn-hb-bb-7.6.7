/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.reportparser.IReportCellProvider
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 */
package com.jiuqi.nr.task.form.formio.formula;

import com.jiuqi.bi.syntax.reportparser.IReportCellProvider;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.nr.task.form.formio.dto.ImportReverseResultDTO;
import com.jiuqi.nr.task.form.formio.formula.ReportCellProvider;
import java.util.Map;

public class ReportFormulaParserBuilder {
    private Map<String, ImportReverseResultDTO> reverseResultDTOMap;

    public ReportFormulaParserBuilder addResult(Map<String, ImportReverseResultDTO> resultDTOMap) {
        this.reverseResultDTOMap = resultDTOMap;
        return this;
    }

    public ReportFormulaParser build() {
        ReportFormulaParser parser = ReportFormulaParser.getInstance();
        parser.registerCellProvider((IReportCellProvider)new ReportCellProvider(this.reverseResultDTOMap));
        parser.setJQReportMode(false);
        parser.setExcelMode(true);
        return parser;
    }
}

