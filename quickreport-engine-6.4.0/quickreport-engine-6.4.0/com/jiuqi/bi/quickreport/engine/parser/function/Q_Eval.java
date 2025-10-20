/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.IMultiInstance
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.parser.function;

import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.context.filter.FilterAnalyzer;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.IMultiInstance;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.ArrayList;
import java.util.List;

public class Q_Eval
extends Function
implements IMultiInstance {
    private static final long serialVersionUID = 1L;
    private List<IFilterDescriptor> filters;
    public static final String NAME = "Q_Eval";

    public Q_Eval() {
        this.parameters().add(new Parameter("expr", 0, "\u6c42\u503c\u8868\u8fbe\u5f0f"));
        this.parameters().add(new Parameter("filter", 0, "\u9650\u5b9a\u6761\u4ef6", true));
    }

    public String name() {
        return NAME;
    }

    public String title() {
        return "\u5bf9\u9650\u5b9a\u7684\u8868\u8fbe\u5f0f\u8fdb\u884c\u6c42\u503c\u8ba1\u7b97";
    }

    public boolean isInfiniteParameter() {
        return true;
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (parameters == null || parameters.isEmpty()) {
            return 0;
        }
        return parameters.get(0).getType(context);
    }

    public String category() {
        return "\u5206\u6790\u8868\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        ReportContext rptContext = (ReportContext)context;
        if (this.filters == null) {
            this.buildFilters(rptContext, parameters);
        }
        rptContext.pushCurrentFilters(this.filters);
        Object result = parameters.get(0).evaluate(context);
        try {
            rptContext.popCurrentFilters(this.filters);
        }
        catch (ReportContextException e) {
            throw new SyntaxException((Throwable)e);
        }
        return result;
    }

    private void buildFilters(ReportContext context, List<IASTNode> parameters) throws SyntaxException {
        this.filters = new ArrayList<IFilterDescriptor>();
        for (int i = 1; i < parameters.size(); ++i) {
            List<IFilterDescriptor> curFilters;
            IASTNode param = parameters.get(i);
            try {
                curFilters = FilterAnalyzer.createFilterDescriptor((IContext)context, param);
            }
            catch (ReportContextException e) {
                throw new SyntaxException((Throwable)e);
            }
            this.filters.addAll(curFilters);
        }
    }

    public Object clone() {
        Q_Eval result = (Q_Eval)((Object)super.clone());
        result.filters = null;
        return result;
    }
}

