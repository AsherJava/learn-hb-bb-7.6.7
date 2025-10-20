/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.cell.IRegionNode
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 */
package com.jiuqi.bi.quickreport.engine.writeback.workbook;

import com.jiuqi.bi.quickreport.engine.area.ExpandingArea;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataNode;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.parser.cell.ReportRegionNode;
import com.jiuqi.bi.quickreport.engine.writeback.workbook.WritebackRestrictions;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.cell.IRegionNode;
import com.jiuqi.bi.syntax.cell.IWorksheet;
import java.util.List;

public final class WritebackRegionNode
extends ReportRegionNode {
    private static final long serialVersionUID = 619064205459810910L;

    public WritebackRegionNode(IRegionNode regionNode, IWorksheet worksheet, int options) {
        super(regionNode.getToken(), worksheet, regionNode.getCellRegion(), options);
    }

    @Override
    protected boolean isRestrictMode(ReportContext context, ExpandingArea refArea) {
        if (context.getTag() instanceof WritebackRestrictions) {
            WritebackRestrictions writeRestrictions = (WritebackRestrictions)context.getTag();
            return writeRestrictions.getMasterArea() == refArea;
        }
        return super.isRestrictMode(context, refArea);
    }

    @Override
    protected List<AxisDataNode> calcRegionRestrictions(ReportContext context, ExpandingArea refArea) throws SyntaxException {
        if (context.getTag() instanceof WritebackRestrictions) {
            WritebackRestrictions writeRestrictions = (WritebackRestrictions)context.getTag();
            if (writeRestrictions.getMasterArea() == refArea) {
                return writeRestrictions.getAllRestrictions();
            }
            return super.calcRegionRestrictions(context, refArea);
        }
        return super.calcRegionRestrictions(context, refArea);
    }
}

