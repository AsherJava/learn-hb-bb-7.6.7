/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.CellField
 */
package com.jiuqi.bi.quickreport.engine.build.handler;

import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.quickreport.engine.area.ExpandingRegion;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataNode;
import com.jiuqi.bi.quickreport.engine.build.handler.IHandlerContext;

@FunctionalInterface
public interface IHeaderHandler {
    public void processRegion(IHandlerContext var1, ExpandingRegion var2, AxisDataNode var3, CellField var4) throws ReportBuildException;
}

