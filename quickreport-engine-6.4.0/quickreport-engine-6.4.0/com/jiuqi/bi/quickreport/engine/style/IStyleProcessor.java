/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.style;

import com.jiuqi.bi.quickreport.engine.parser.IReportExpression;
import com.jiuqi.bi.quickreport.engine.style.ReportStyleException;
import com.jiuqi.bi.syntax.cell.IWorksheet;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.List;

public interface IStyleProcessor {
    public void process(IContext var1, IWorksheet var2, Position var3) throws ReportStyleException;

    public Object getStyleInfo();

    public List<IReportExpression> getConditions();

    public IStyleProcessor next();
}

