/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.area;

import com.jiuqi.bi.quickreport.engine.area.ExpandingAxis;
import com.jiuqi.bi.quickreport.engine.area.ExpandingRegion;
import com.jiuqi.bi.quickreport.engine.area.ReportAreaExpcetion;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.Collections;
import java.util.Comparator;

public final class RowAxis
extends ExpandingAxis {
    public boolean add(IContext context, CellBindingInfo bindingInfo, ExpandingAxis colAxis) throws ReportAreaExpcetion {
        this.check(bindingInfo);
        if (this.isEmpty()) {
            if (colAxis.isEmpty()) {
                return this.addInit(context, bindingInfo);
            }
            if (colAxis.isRelated(bindingInfo.getCellMap().getExpandRegion())) {
                return this.addInit(context, bindingInfo);
            }
            return false;
        }
        if (this.addNest(context, bindingInfo)) {
            return true;
        }
        if (colAxis.isRelated(bindingInfo.getCellMap().getExpandRegion())) {
            return this.addSide(context, bindingInfo);
        }
        return false;
    }

    private void check(CellBindingInfo bindingInfo) throws ReportAreaExpcetion {
        ExpandingRegion crossRegion = this.findCrossRegion(bindingInfo.getCellMap().getExpandRegion());
        if (crossRegion != null) {
            throw new ReportAreaExpcetion("\u9875\u7b7e\u201c" + bindingInfo.getPosition().getSheetName() + "\u201d\u4e2d\u68c0\u6d4b\u5230\u4ea4\u53c9\u7684\u884c\u6d6e\u52a8\u533a\u57df\uff08" + bindingInfo.getCellMap().getExpandRegion().toString() + ", " + crossRegion.getRegion().toString() + "\uff09\u3002");
        }
    }

    @Override
    void sort() {
        Comparator comparator = (o1, o2) -> o1.getRegion().top() - o2.getRegion().top();
        Collections.sort(this.expandingRegions, comparator);
        for (ExpandingRegion subRegion : this.expandingRegions) {
            subRegion.sort(comparator);
        }
    }

    @Override
    void validate(ExpandingAxis colAxis) throws ReportAreaExpcetion {
        super.validate(colAxis);
        if (!colAxis.isEmpty()) {
            int right = colAxis.getRegion().right();
            for (ExpandingRegion expandingRegion : this) {
                if (expandingRegion.getRegion().right() < right) {
                    throw new ReportAreaExpcetion("\u9875\u7b7e\u201c" + expandingRegion.getMasterCell().getPosition().getSheetName() + "\u201d\u4e2d\u884c\u6d6e\u52a8\u533a\u57df\uff08" + expandingRegion.getRegion() + "\uff09\u8303\u56f4\u9519\u8bef\uff0c\u81f3\u5c11\u5e94\u8be5\u4e0e\u680f\u6d6e\u52a8\u533a\u57df\uff08" + colAxis.getRegion() + "\uff09\u53f3\u4fa7\u5bf9\u9f50\u3002");
                }
                if (!colAxis.getRegion().contains(expandingRegion.getMasterCellPosition())) continue;
                throw new ReportAreaExpcetion(expandingRegion.getMasterCell().getPosition() + "\u4e3a\u6d6e\u52a8\u884c\u4e3b\u63a7\u5355\u5143\u683c\uff0c\u7981\u6b62\u88ab\u5305\u542b\u5728\u6d6e\u52a8\u680f\u533a\u57df\u4e2d\u3002");
            }
        }
    }
}

