/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.grid.GridCell
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.util.JqLib
 *  com.jiuqi.xg.process.IDrawObject
 *  com.jiuqi.xg.process.ITemplateObject
 */
package com.jiuqi.nr.definition.facade.print.common.interactor.adjustment;

import com.jiuqi.grid.GridCell;
import com.jiuqi.grid.GridData;
import com.jiuqi.nr.definition.facade.print.common.define.element.tablelabel.TableLabelDrawObject;
import com.jiuqi.nr.definition.facade.print.common.interactor.PaginateInteractorBase;
import com.jiuqi.nr.definition.facade.print.common.interactor.adjustment.AdjustmentFilter;
import com.jiuqi.nr.definition.facade.print.common.interactor.adjustment.AdjustmentResponse;
import com.jiuqi.nr.definition.facade.print.common.interactor.adjustment.FilterChain;
import com.jiuqi.nr.definition.facade.print.common.other.PrintUtil;
import com.jiuqi.nr.definition.facade.print.common.parse.ParseContext;
import com.jiuqi.nr.definition.facade.print.common.parse.WordLabelParseExecuter;
import com.jiuqi.util.JqLib;
import com.jiuqi.xg.process.IDrawObject;
import com.jiuqi.xg.process.ITemplateObject;
import org.springframework.stereotype.Component;

@Component
public class TableLabelAdjustmentFilter
implements AdjustmentFilter {
    @Override
    public void doFilter(ITemplateObject templateObj, IDrawObject drawObj, int pageIndex, PaginateInteractorBase pdfIPaginateInteractor, AdjustmentResponse res, FilterChain filterChain) {
        if (templateObj.getKind().equals("element_tableLabel")) {
            TableLabelDrawObject tltDrawObj = (TableLabelDrawObject)drawObj;
            GridData gd = tltDrawObj.getGridData();
            ParseContext parseContext = PrintUtil.createLabelParseContext(pdfIPaginateInteractor.getParam().getFormKey(), PrintUtil.getTotalPageNumber(tltDrawObj, pdfIPaginateInteractor.getPageNumberGenerateStrategy()), pageIndex, pdfIPaginateInteractor.getPageNumberGenerateStrategy(), pdfIPaginateInteractor.getParam(), pdfIPaginateInteractor.getExecutorContext(), pdfIPaginateInteractor.getExpressionEvaluator());
            WordLabelParseExecuter instance = WordLabelParseExecuter.getInstance();
            for (int col = 0; col < gd.getColCount(); ++col) {
                for (int row = 0; row < gd.getRowCount(); ++row) {
                    GridCell cell = gd.getCell(col, row);
                    String unParsedExp = cell.getShowText();
                    if (unParsedExp == null) continue;
                    String processedContent = instance.execute(parseContext, unParsedExp, pdfIPaginateInteractor.getPatternAndValue());
                    if (!JqLib.isEmpty((String)processedContent) && PrintUtil.isNumber(processedContent)) {
                        processedContent = PrintUtil.formatDecimal(cell, processedContent);
                    }
                    cell.setShowText(processedContent);
                    gd.setCell(cell);
                }
            }
            res.setAdjustment(true);
            res.setMsg("TableLabelAdjustmentFilter==\u8c03\u6574");
            return;
        }
        filterChain.doFilter(templateObj, drawObj, pageIndex, pdfIPaginateInteractor, res);
    }
}

