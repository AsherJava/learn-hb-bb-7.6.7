/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.parser.function;

import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DataSetNode;
import com.jiuqi.bi.quickreport.engine.parser.function.DataSetFunction;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.List;
import java.util.stream.Collectors;

public final class DS_AggregateByTree
extends DataSetFunction {
    private static final long serialVersionUID = 1L;

    public DS_AggregateByTree() {
        this.parameters().add(new Parameter("dataDS", 5050, "\u6c47\u603b\u6570\u636e"));
        this.parameters().add(new Parameter("dimDS", 5050, "\u6c47\u603b\u7ef4\u5ea6"));
    }

    public String name() {
        return "DS_AggregateByTree";
    }

    public String[] aliases() {
        return new String[]{"DS_ABT"};
    }

    public String title() {
        return "\u6309\u7ef4\u5ea6\u6811\u5f62\u5c42\u7ea7\u5bf9\u6570\u636e\u8fdb\u884c\u9010\u7ea7\u6c47\u603b\uff0c\u53ef\u7b80\u5199\u4e3aDS_ABT";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 5050;
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        for (IASTNode param : parameters) {
            if (param instanceof DataSetNode) continue;
            throw new SyntaxException(param.getToken(), "\u4e0d\u662f\u6709\u6548\u7684\u6570\u636e\u96c6\u540d\u79f0");
        }
        return super.validate(context, parameters);
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        List dsNames = parameters.stream().map(p -> ((DataSetNode)p).getDataSetModel().getName()).collect(Collectors.toList());
        try {
            return ((ReportContext)context).aggregateByTree((String)dsNames.get(0), dsNames.subList(1, dsNames.size()));
        }
        catch (ReportContextException e) {
            throw new SyntaxException((Throwable)e);
        }
    }
}

