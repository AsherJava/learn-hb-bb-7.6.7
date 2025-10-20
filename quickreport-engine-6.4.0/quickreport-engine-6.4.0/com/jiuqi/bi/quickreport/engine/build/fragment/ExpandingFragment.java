/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Region
 */
package com.jiuqi.bi.quickreport.engine.build.fragment;

import com.jiuqi.bi.quickreport.engine.area.ExpandingArea;
import com.jiuqi.bi.quickreport.engine.area.ExpandingAxis;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataNode;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisFrameBuilder;
import com.jiuqi.bi.quickreport.engine.build.expanding.GridExpander;
import com.jiuqi.bi.quickreport.engine.build.fragment.CellCalculator;
import com.jiuqi.bi.quickreport.engine.build.fragment.GridFragment;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.syntax.cell.Region;

public final class ExpandingFragment
extends GridFragment {
    private ExpandingArea area;
    private AxisDataNode colRoot;
    private AxisDataNode rowRoot;
    private Region region;

    public ExpandingFragment(ExpandingArea area) {
        this.area = area;
    }

    public ExpandingArea getArea() {
        return this.area;
    }

    @Override
    public void build() throws ReportBuildException {
        this.buildFrame();
        this.estimateCells();
        this.expandFrame();
        this.calcCells();
    }

    private void buildFrame() throws ReportBuildException {
        this.rowRoot = this.buildAxisFrame(this.area.getRowAxis(), this.area.getColAxis());
        this.area.getRowAxis().setData(this.rowRoot);
        this.colRoot = this.buildAxisFrame(this.area.getColAxis(), this.area.getRowAxis());
        this.area.getColAxis().setData(this.colRoot);
    }

    private AxisDataNode buildAxisFrame(ExpandingAxis axis, ExpandingAxis otherAxis) throws ReportBuildException {
        AxisFrameBuilder builder = new AxisFrameBuilder();
        builder.setAxis(axis);
        builder.setOtherAxis(otherAxis);
        builder.setContext(this.context);
        builder.setExpandingNulls(this.context.isExpandingNulls());
        return builder.build();
    }

    private void estimateCells() throws ReportBuildException {
        if (this.colRoot.getChildren().isEmpty() && this.rowRoot.getChildren().isEmpty()) {
            return;
        }
        int colSize = this.area.getColAxis().isEmpty() ? this.area.getRowAxis().getRegion().colSize() : (this.colRoot.getChildren().isEmpty() ? this.area.getColAxis().getRegion().colSize() : this.colRoot.estimateCellSize());
        int rowSize = this.area.getRowAxis().isEmpty() ? this.area.getColAxis().getRegion().rowSize() : (this.rowRoot.getChildren().isEmpty() ? this.area.getRowAxis().getRegion().rowSize() : this.rowRoot.estimateCellSize());
        try {
            this.context.estimateCells(colSize * rowSize);
        }
        catch (ReportContextException e) {
            throw new ReportBuildException(e);
        }
    }

    private void expandFrame() throws ReportBuildException {
        GridExpander expander = new GridExpander();
        expander.setArea(this.area);
        expander.setContext(this.context);
        expander.setWorksheet(this.worksheet);
        expander.setRowRoot(this.rowRoot);
        expander.setColRoot(this.colRoot);
        expander.setCellHandler(this.cellHandler);
        expander.expand();
        this.region = expander.getExpanedRegion();
    }

    private void calcCells() throws ReportBuildException {
        CellCalculator calculator = new CellCalculator();
        calculator.setContext(this.context);
        calculator.setArea(this.area);
        calculator.setRegion(this.region);
        calculator.setWorksheet(this.worksheet);
        calculator.setCellHandler(this.cellHandler);
        calculator.build();
    }
}

