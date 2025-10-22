/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.xg.process.Border
 *  com.jiuqi.xg.process.IDrawObject
 *  com.jiuqi.xg.process.ITemplateObject
 *  com.jiuqi.xg.process.table.obj.TableDrawObject
 */
package com.jiuqi.nr.definition.facade.print.common.interactor.adjustment;

import com.jiuqi.nr.definition.facade.print.common.interactor.PaginateInteractorBase;
import com.jiuqi.nr.definition.facade.print.common.interactor.adjustment.AdjustmentFilter;
import com.jiuqi.nr.definition.facade.print.common.interactor.adjustment.AdjustmentResponse;
import com.jiuqi.nr.definition.facade.print.common.interactor.adjustment.FilterChain;
import com.jiuqi.nr.definition.facade.print.common.other.PrintUtil;
import com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject;
import com.jiuqi.xg.process.Border;
import com.jiuqi.xg.process.IDrawObject;
import com.jiuqi.xg.process.ITemplateObject;
import com.jiuqi.xg.process.table.obj.TableDrawObject;
import org.springframework.stereotype.Component;

@Component
public class ReportAdjustmentFilter
implements AdjustmentFilter {
    @Override
    public void doFilter(ITemplateObject templateObj, IDrawObject drawObj, int pageIndex, PaginateInteractorBase pdfIPaginateInteractor, AdjustmentResponse res, FilterChain filterChain) {
        if (templateObj.getKind().equals("element_report")) {
            ReportTemplateObject reportTemplateObject = (ReportTemplateObject)templateObj;
            TableDrawObject tableDrawObjcet = (TableDrawObject)drawObj;
            boolean autoBorderLinePrint = reportTemplateObject.isAutoBorderLinePrint();
            boolean oddLoRc = reportTemplateObject.isOddLoRc();
            boolean evenLoRc = reportTemplateObject.isEvenLoRc();
            boolean underLinePrint = reportTemplateObject.isUnderLinePrint();
            if (underLinePrint) {
                PrintUtil.resetTableBottomLineBorderStyle(tableDrawObjcet, 1);
            }
            if (oddLoRc || evenLoRc) {
                Border outsideLineBorder = tableDrawObjcet.getLineConfig().getOutsideLineBorder();
                PrintUtil.setTableOutSideBorder(pageIndex, tableDrawObjcet, outsideLineBorder, oddLoRc, evenLoRc);
            }
            if (!autoBorderLinePrint) {
                PrintUtil.autoLineToNoneStyle(tableDrawObjcet);
            }
            res.setAdjustment(true);
            res.setMsg("ReportAdjustmentFilter==\u8c03\u6574");
            return;
        }
        filterChain.doFilter(templateObj, drawObj, pageIndex, pdfIPaginateInteractor, res);
    }
}

