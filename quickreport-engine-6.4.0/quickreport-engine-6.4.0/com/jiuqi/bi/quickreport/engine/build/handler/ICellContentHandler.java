/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.build.handler;

import com.jiuqi.bi.quickreport.engine.build.CellValue;
import com.jiuqi.bi.quickreport.engine.build.ReportBuildException;
import com.jiuqi.bi.quickreport.engine.build.handler.IHandlerContext;

public interface ICellContentHandler {
    public void setCell(IHandlerContext var1, int var2, int var3, CellValue var4) throws ReportBuildException;
}

