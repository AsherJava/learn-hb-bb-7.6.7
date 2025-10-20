/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.function;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.function.DSFunction;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.List;

public class CRG
extends DSFunction {
    private static final long serialVersionUID = 4831152446856181585L;

    public CRG() {
        this.parameters().add(new Parameter("N", 3, "\u671f\u6570"));
        this.parameters().add(new Parameter("measure", 3, "\u5ea6\u91cf"));
        this.parameters().add(new Parameter("period", 6, "\u57fa\u51c6\u671f"));
        this.parameters().add(new Parameter("dimensionCondition", 0, "\u7ef4\u9650\u5b9a\u6761\u4ef6", true));
    }

    @Override
    public Object evalute(IContext context, List<IASTNode> paramNodes, BIDataSet filterDs) throws SyntaxException {
        return null;
    }

    public String name() {
        return "DS_CRG";
    }

    public String title() {
        return "\u4ece\u57fa\u51c6\u671f\u5f00\u59cb\uff0c\u8ba1\u7b97\u6700\u8fd1\u8fde\u7eedN\u671f\u7684\u590d\u5408\u589e\u957f\u7387";
    }

    public int getResultType(IContext context, List<IASTNode> paramNodes) throws SyntaxException {
        return 0;
    }
}

