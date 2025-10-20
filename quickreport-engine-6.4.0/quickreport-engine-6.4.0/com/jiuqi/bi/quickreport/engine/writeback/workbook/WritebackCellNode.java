/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.cell.ICellNode
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.writeback.workbook;

import com.jiuqi.bi.quickreport.engine.build.ResultGridData;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataNode;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.parser.cell.ReportCellNode;
import com.jiuqi.bi.quickreport.engine.writeback.workbook.WritebackRestrictions;
import com.jiuqi.bi.quickreport.model.ExpandMode;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.cell.ICellNode;
import com.jiuqi.bi.syntax.cell.IWorksheet;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.List;

public final class WritebackCellNode
extends ReportCellNode {
    private static final long serialVersionUID = 4479159995510316912L;
    private int axisIndex = -1;

    public WritebackCellNode(ICellNode cellNode, IWorksheet worksheet, int options) {
        super(cellNode.getToken(), worksheet, cellNode.getCellPosition(), options);
    }

    public int getAxisIndex() {
        return this.axisIndex;
    }

    public void setAxisIndex(int axisIndex) {
        this.axisIndex = axisIndex;
    }

    @Override
    public Object evaluate(IContext context) throws SyntaxException {
        ReportContext rptContext = (ReportContext)context;
        if (rptContext.getTag() instanceof WritebackRestrictions && this.axisIndex >= 0) {
            WritebackRestrictions writeRestrictions = (WritebackRestrictions)rptContext.getTag();
            AxisDataNode data = this.getCellInfo().getCellMap().getExpandMode() == ExpandMode.ROWEXPANDING ? writeRestrictions.getRowRestrictions()[this.axisIndex] : writeRestrictions.getColRestrictions()[this.axisIndex];
            return data.getValue();
        }
        return super.evaluate(context);
    }

    @Override
    protected ReportCellNode.ReadingMode getReadingMode(ReportContext context) {
        if (context.getTag() instanceof WritebackRestrictions) {
            WritebackRestrictions writeRestrictions = (WritebackRestrictions)context.getTag();
            if (this.getCellArea() == writeRestrictions.getMasterArea()) {
                return ReportCellNode.ReadingMode.RESTRICTED;
            }
            return super.getReadingMode(context);
        }
        return super.getReadingMode(context);
    }

    @Override
    protected List<AxisDataNode> calcCellRestrictions(ReportContext context, ResultGridData resultGrid) throws SyntaxException {
        if (context.getTag() instanceof WritebackRestrictions) {
            WritebackRestrictions writeRestrictions = (WritebackRestrictions)context.getTag();
            if (writeRestrictions.getMasterArea() == this.getCellArea()) {
                return writeRestrictions.getAllRestrictions();
            }
            return super.calcCellRestrictions(context, resultGrid);
        }
        return super.calcCellRestrictions(context, resultGrid);
    }
}

