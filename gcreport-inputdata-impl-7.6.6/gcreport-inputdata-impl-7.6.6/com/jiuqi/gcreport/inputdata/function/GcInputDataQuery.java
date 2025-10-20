/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.gcreport.inputdata.function;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.gcreport.inputdata.function.util.GcFloatCopyExecutor;
import com.jiuqi.gcreport.inputdata.function.util.GcFloatCopyParaParser;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.List;

public class GcInputDataQuery
extends Function
implements IReportFunction {
    private static final long serialVersionUID = 3565622721625053981L;

    public GcInputDataQuery() {
        this.parameters().add(new Parameter("srcRelaExp", 6, "\u6e90\u6d6e\u52a8\u884c\u5173\u8054\u7684\u8868\u8fbe\u5f0f"));
        this.parameters().add(new Parameter("destRelaExp", 6, "\u76ee\u6807\u6d6e\u52a8\u884c\u5173\u8054\u7684\u8868\u8fbe\u5f0f"));
        this.parameters().add(new Parameter("srcFilter", 6, "\u6e90\u6d6e\u52a8\u884c\u8fc7\u6ee4\u6761\u4ef6"));
        this.parameters().add(new Parameter("assignExp", 6, "\u8d4b\u503c\u8868\u8fbe\u5f0f"));
        this.parameters().add(new Parameter("clearDestBeforeCopy", 1, "\u590d\u5236\u524d\u662f\u5426\u6e05\u9664\u76ee\u6807\u6d6e\u52a8\u884c\u7684\u6570\u636e"));
        this.parameters().add(new Parameter("copyMode", 6, "\u62f7\u8d1d\u65b9\u5f0f"));
        this.parameters().add(new Parameter("periodOffset", 3, "\u65f6\u671f\u504f\u79fb\u91cf"));
        this.parameters().add(new Parameter("srcPeriodType", 6, "\u6e90\u6d6e\u52a8\u884c\u65f6\u671f\u7c7b\u578b"));
        this.parameters().add(new Parameter("periodCount", 3, "\u6307\u5b9a\u53d6\u6e90\u65f6\u671f\u540e\u7684\u51e0\u671f\u6570\u636e"));
        this.parameters().add(new Parameter("copyColumnWhenAdd", 6, "\u65b0\u589e\u540c\u65f6\u590d\u5236\u5b57\u6bb5\u96c6\u5408(\u683c\u5f0f: src[Field1]=dest[Field1],src[Field2]=dest[Field2]...)"));
    }

    public String name() {
        return "GcInputDataQuery";
    }

    public String title() {
        return "\u6d6e\u52a8\u884c\u4e4b\u95f4\u6570\u636e\u62f7\u8d1d(GC_INPUTDATA\u6269\u5c55)";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 0;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext qContext = (QueryContext)context;
        try {
            GcFloatCopyParaParser parser = new GcFloatCopyParaParser(qContext, parameters);
            if (!parser.isValid()) {
                return false;
            }
            return new GcFloatCopyExecutor(parser, this).execute(qContext);
        }
        catch (Exception e) {
            throw new SyntaxException((Throwable)e);
        }
    }
}

