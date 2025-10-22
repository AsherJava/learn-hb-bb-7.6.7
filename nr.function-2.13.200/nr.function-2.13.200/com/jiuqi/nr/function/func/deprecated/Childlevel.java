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
 */
package com.jiuqi.nr.function.func.deprecated;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import java.util.List;

public class Childlevel
extends Function
implements IReportFunction {
    private static final long serialVersionUID = 3572450693393992004L;

    public Childlevel() {
        this.parameters().add(new Parameter("unitCode", 6, "\u5355\u4f4d\u4ee3\u7801"));
    }

    public String name() {
        return "Childlevel";
    }

    public String title() {
        return "\u5f53\u524d\u5355\u4f4d\u4e3a\u6307\u5b9a\u5355\u4f4d\u7684\u4e0b\u7ea7\u7ea7\u6b21";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 3;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return null;
    }

    public boolean isDeprecated() {
        return true;
    }
}

