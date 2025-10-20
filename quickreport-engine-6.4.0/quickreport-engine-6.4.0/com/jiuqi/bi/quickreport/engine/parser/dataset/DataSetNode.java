/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.engine.parser.dataset;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.parser.dataset.IDataSetNode;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;

public final class DataSetNode
extends DynamicNode
implements IDataSetNode {
    private static final long serialVersionUID = 433234295791709354L;
    private DSModel model;

    public DataSetNode(Token token, DSModel model) {
        super(token);
        this.model = model;
    }

    @Override
    public DSModel getDataSetModel() {
        return this.model;
    }

    public int getType(IContext context) throws SyntaxException {
        return 5050;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        ReportContext rptContext = (ReportContext)context;
        try {
            return rptContext.openDataSet(this.model.getName(), rptContext.getCurrentFilters());
        }
        catch (ReportContextException e) {
            throw new SyntaxException((Throwable)e);
        }
    }

    public void toString(StringBuilder buffer) {
        buffer.append(this.model.getName());
    }

    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append(this.model.getName());
    }

    protected void toExplain(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        if (StringUtils.isEmpty((String)this.model.getTitle())) {
            buffer.append(this.model.getName());
        } else {
            buffer.append(this.model.getTitle());
        }
    }
}

