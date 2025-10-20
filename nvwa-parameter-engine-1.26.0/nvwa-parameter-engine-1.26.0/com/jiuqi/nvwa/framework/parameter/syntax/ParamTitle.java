/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.nvwa.framework.parameter.syntax;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nvwa.framework.parameter.syntax.ParamNode;
import java.util.List;

public final class ParamTitle
extends Function {
    private static final long serialVersionUID = 6353258888947423511L;

    public ParamTitle() {
        this.parameters().add(new Parameter("param", 0, "\u53c2\u6570"));
    }

    public String name() {
        return "ParamTitle";
    }

    public String title() {
        return "\u83b7\u53d6\u53c2\u6570\u6807\u9898\u3002\u5f53\u4e3a\u9009\u62e9\u6a21\u5f0f\u65f6\uff0c\u8fd4\u56de\u9009\u62e9\u7684\u6807\u9898\u503c";
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (!(parameters.get(0) instanceof ParamNode)) {
            throw new SyntaxException(parameters.get(0).getToken(), "\u4f20\u5165\u53c2\u6570\u5fc5\u987b\u4e3a\u53c2\u6570\u7c7b\u578b\u5bf9\u8c61\u3002");
        }
        return super.validate(context, parameters);
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 0;
    }

    public String category() {
        return "\u53c2\u6570\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        ParamNode param = (ParamNode)parameters.get(0);
        param.setValueMode(2);
        return param.getParamValueTitle(context);
    }
}

