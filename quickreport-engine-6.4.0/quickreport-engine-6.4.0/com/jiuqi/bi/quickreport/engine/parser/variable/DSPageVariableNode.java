/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNode
 *  com.jiuqi.bi.syntax.format.IDataFormator
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.parser.variable;

import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.model.PageMode;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNode;
import com.jiuqi.bi.syntax.format.IDataFormator;
import com.jiuqi.bi.syntax.parser.IContext;
import java.text.DecimalFormat;
import java.text.Format;

public final class DSPageVariableNode
extends DynamicNode {
    private static final long serialVersionUID = 1710994649768253659L;
    public static final String DS_PAGENUMBER = "DS_PageNum";
    public static final String DS_PAGECOUNT = "DS_PageCount";
    public static final String DS_RECORDCOUNT = "DS_RecordCount";
    public static final String[] DS_VARNAMES = new String[]{"DS_PageNum", "DS_PageCount", "DS_RecordCount"};
    public static final String[] DS_VARTITLES = new String[]{"\u6570\u636e\u96c6\u5206\u9875\u65f6\u7684\u5f53\u524d\u9875\u7801\uff0c\u4ece1\u5f00\u59cb\u7f16\u53f7", "\u6570\u636e\u96c6\u5206\u9875\u65f6\u7684\u603b\u9875\u6570", "\u5206\u9875\u6570\u636e\u96c6\u7684\u603b\u8bb0\u5f55\u6570"};
    private final String varName;

    public DSPageVariableNode(Token token, String varName) {
        super(token);
        this.varName = varName;
    }

    public int getType(IContext context) throws SyntaxException {
        return 3;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        ReportContext rptContext = (ReportContext)context;
        if (rptContext.getReport().getPageInfo().getPageMode() != PageMode.DATASET) {
            throw new SyntaxException(this.token, "\u5f53\u524d\u62a5\u8868\u672a\u4f7f\u7528\u6570\u636e\u96c6\u5206\u9875\uff0c\u65e0\u6cd5\u4f7f\u7528\u53d8\u91cf" + this.toString() + "\u3002");
        }
        try {
            return this.valueOfVar(rptContext);
        }
        catch (ReportContextException e) {
            throw new SyntaxException(this.token, (Throwable)e);
        }
    }

    private Object valueOfVar(ReportContext rptContext) throws ReportContextException {
        if (DS_PAGECOUNT.equalsIgnoreCase(this.varName)) {
            return rptContext.getPageSize();
        }
        if (DS_PAGENUMBER.equalsIgnoreCase(this.varName)) {
            return rptContext.getPageNum();
        }
        if (DS_RECORDCOUNT.equalsIgnoreCase(this.varName)) {
            return rptContext.getRecordSize();
        }
        return null;
    }

    public void toString(StringBuilder buffer) {
        buffer.append(this.varName);
    }

    public IDataFormator getDataFormator(IContext context) throws SyntaxException {
        return new IDataFormator(){

            public Format getFormator(IContext context) throws SyntaxException {
                return new DecimalFormat("#0");
            }
        };
    }
}

