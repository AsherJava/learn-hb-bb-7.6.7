/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.parameter.engine.IParameterEnv
 *  com.jiuqi.bi.syntax.cell.ICellProvider
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 */
package com.jiuqi.bi.quickreport.engine.parser;

import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.bi.quickreport.engine.parser.IReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.syntax.cell.ICellProvider;
import com.jiuqi.bi.syntax.parser.IContext;

public interface IReportParser {
    public static final int DATATYPE_DATASET = 5050;
    public static final int DATATYPE_HYPERLINK = 5051;
    public static final int ACTION_STYLE_PARSING = 1;

    public IContext getContext();

    @Deprecated
    public void setParamEnv(IParameterEnv var1);

    public void setParamEnv(com.jiuqi.nvwa.framework.parameter.IParameterEnv var1);

    public void setWorkbook(ICellProvider var1);

    public IReportExpression parseEval(String var1) throws ReportExpressionException;

    public IReportExpression parseCond(String var1) throws ReportExpressionException;

    public IReportExpression parseWriteback(String var1) throws ReportExpressionException;

    public Object getRawParser();

    public void beginAction(int var1);

    public void endAction(int var1);
}

