/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.format.IDataFormator
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.textprocessor.parse.func.basic;

import com.jiuqi.bi.dataset.textprocessor.TextFormulaContext;
import com.jiuqi.bi.dataset.textprocessor.parse.DSHelper;
import com.jiuqi.bi.dataset.textprocessor.parse.func.TFunction;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.format.IDataFormator;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.List;

public class LAG
extends TFunction {
    private static final long serialVersionUID = 7869831014954160786L;

    public LAG() {
        this.parameters().add(new Parameter("attribute", 0, "\u504f\u79fb\u7ef4\u5c5e\u6027"));
        this.parameters().add(new Parameter("offset", 3, "\u504f\u79fb\u91cf"));
    }

    public Object evalute(IContext context, List<IASTNode> paramNodes) throws SyntaxException {
        throw new SyntaxException();
    }

    @Override
    public boolean isInfiniteParameter() {
        return false;
    }

    public String name() {
        return "LAG";
    }

    public String title() {
        return "\u83b7\u53d6\u6307\u5b9a\u7684\u65f6\u671f\u7ef4\u5ea6\u504f\u79fb\u540e\u7684\u503c";
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        TextFormulaContext tfc = (TextFormulaContext)context;
        DSHelper helper = new DSHelper(tfc);
        for (int i = 1; i < parameters.size(); ++i) {
            IASTNode adjustNode = helper.adjust(null, parameters.get(i));
            parameters.set(i, adjustNode);
        }
        return super.validate(context, parameters);
    }

    public int getResultType(IContext context, List<IASTNode> paramNodes) throws SyntaxException {
        return 1;
    }

    public IDataFormator getDataFormator(IContext context) throws SyntaxException {
        return null;
    }

    @Override
    public boolean isDatasetNodeParam(int paramIndex) {
        return false;
    }
}

