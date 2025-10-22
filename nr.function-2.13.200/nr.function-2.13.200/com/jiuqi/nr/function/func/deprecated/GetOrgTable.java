/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.function.func.deprecated;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.List;

public class GetOrgTable
extends Function
implements IReportFunction {
    private static final long serialVersionUID = 5726730860026432476L;

    public GetOrgTable() {
        this.parameters().add(new Parameter("versionGroup", 6, "\u7248\u672c\u5206\u7ec4"));
        this.parameters().add(new Parameter("dataTime", 6, "\u6570\u636e\u65f6\u671f(\u53ef\u7f3a\u7701)", true));
    }

    public String name() {
        return "GetOrgTable";
    }

    public String title() {
        return "\u6839\u636e\u7248\u672c\u5206\u7ec4\u83b7\u53d6\u5f53\u524d\u4e3b\u7ef4\u5ea6\u7248\u672c(\u4ec5\u7528\u4e8e\u517c\u5bb9JQR)";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (context instanceof QueryContext) {
            QueryContext qContext = (QueryContext)context;
            ExecutorContext exeContext = qContext.getExeContext();
            String mainDim = exeContext.getUnitDimension();
            return exeContext.getCache().getDataModelDefinitionsCache().getDimensionProvider().getDimensionTableName(exeContext, mainDim);
        }
        return null;
    }

    public boolean isDeprecated() {
        return true;
    }
}

