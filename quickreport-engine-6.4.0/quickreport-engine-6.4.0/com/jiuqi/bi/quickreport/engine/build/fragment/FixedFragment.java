/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Position
 */
package com.jiuqi.bi.quickreport.engine.build.fragment;

import com.jiuqi.bi.quickreport.engine.area.FixedArea;
import com.jiuqi.bi.quickreport.engine.build.CellValue;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.build.fragment.GridFragment;
import com.jiuqi.bi.quickreport.engine.context.EvalCellInfo;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.context.filter.FilterAnalyzer;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.quickreport.engine.style.ReportStyleException;
import com.jiuqi.bi.quickreport.engine.style.StyleProcessor;
import com.jiuqi.bi.syntax.cell.Position;
import java.util.List;

public final class FixedFragment
extends GridFragment {
    private FixedArea area;

    public FixedFragment(FixedArea area) {
        this.area = area;
    }

    public FixedArea getArea() {
        return this.area;
    }

    @Override
    public void build() throws ReportBuildException {
        for (CellBindingInfo bindingInfo : this.area.getCells()) {
            if (bindingInfo.getCellMap() == null) continue;
            this.buildCell(bindingInfo);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void buildCell(CellBindingInfo bindingInfo) throws ReportBuildException {
        Position curPos = this.worksheet.getResultGrid().locateNewPostion(bindingInfo.getPosition().getPosition());
        this.initContext(bindingInfo, curPos);
        try {
            CellValue cellValue = this.calcCell(bindingInfo);
            this.setGridCell(cellValue, curPos);
            this.applyStyles(bindingInfo, curPos);
        }
        finally {
            this.clearContext();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void initContext(CellBindingInfo bindingInfo, Position curPos) throws ReportBuildException {
        block5: {
            this.beginFilter();
            try {
                List<IFilterDescriptor> filterDescrs;
                this.context.setCurrentCell(new EvalCellInfo(curPos, bindingInfo));
                List<IFilterDescriptor> colRowFilters = this.getColRowFilters(bindingInfo.getPosition().getPosition());
                this.pushFilters(colRowFilters);
                if (bindingInfo.getFilter() == null) break block5;
                try {
                    filterDescrs = FilterAnalyzer.createFilterDescriptor(this.context, bindingInfo, null);
                }
                catch (ReportContextException e) {
                    throw new ReportBuildException(e);
                }
                this.pushFilters(filterDescrs);
            }
            finally {
                this.endFilter();
            }
        }
    }

    private void setGridCell(CellValue cellValue, Position curPos) throws ReportBuildException {
        this.cellHandler.setCell(this.handlerContext, curPos.col(), curPos.row(), cellValue);
    }

    private void applyStyles(CellBindingInfo bindingInfo, Position curPos) throws ReportBuildException {
        try {
            StyleProcessor.applyStyles(this.context, this.worksheet, curPos, bindingInfo);
        }
        catch (ReportStyleException e) {
            throw new ReportBuildException("\u5904\u7406\u5355\u5143\u683c\u6761\u4ef6\u6837\u5f0f\u51fa\u9519\uff1a" + bindingInfo.getPosition(), e);
        }
    }

    private void clearContext() {
        this.context.getCurrentFilters().clear();
        this.context.setCurrentCell(null);
    }
}

