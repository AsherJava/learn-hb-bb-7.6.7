/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.parse.AdvanceFunction
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.function.func.deprecated;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.parse.AdvanceFunction;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.function.func.floatcopy.DistributeFloatCopyExecutor;
import com.jiuqi.nr.function.func.floatcopy.DistributeFloatCopyParaParser;
import java.util.List;

public class DistributeFloatCopy
extends AdvanceFunction
implements IReportFunction {
    private static final long serialVersionUID = 3446236159217827715L;

    public DistributeFloatCopy() {
        this.parameters().add(new Parameter("srcRelaExp", 6, "\u6e90\u6d6e\u52a8\u884c\u5173\u8054\u7684\u8868\u8fbe\u5f0f"));
        this.parameters().add(new Parameter("destRelaExp", 6, "\u76ee\u6807\u6d6e\u52a8\u884c\u5173\u8054\u7684\u8868\u8fbe\u5f0f"));
        this.parameters().add(new Parameter("subUnitColExp", 6, "\u4e0b\u7ea7\u5217\u8868\u8fbe\u5f0f"));
        this.parameters().add(new Parameter("assignExp", 6, "\u8d4b\u503c\u8868\u8fbe\u5f0f"));
        this.parameters().add(new Parameter("clearDestBeforeCopy", 1, "\u590d\u5236\u524d\u662f\u5426\u6e05\u9664\u76ee\u6807\u6d6e\u52a8\u884c\u7684\u6570\u636e"));
        this.parameters().add(new Parameter("copyMode", 6, "\u62f7\u8d1d\u65b9\u5f0f"));
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext qContext = (QueryContext)context;
        try {
            DistributeFloatCopyParaParser parser = new DistributeFloatCopyParaParser(qContext, parameters);
            if (!parser.isValid()) {
                return false;
            }
            return new DistributeFloatCopyExecutor(parser, (IReportFunction)this).execute(qContext);
        }
        catch (Exception e) {
            throw new SyntaxException((Throwable)e);
        }
    }

    public int getResultType(IContext arg0, List<IASTNode> arg1) throws SyntaxException {
        return 0;
    }

    public String name() {
        return "DistributeFloatCopy";
    }

    public String title() {
        return "\u5411\u4e0b\u7ea7\u5206\u53d1\u6d6e\u52a8\u6570\u636e";
    }

    public boolean isDeprecated() {
        return true;
    }
}

