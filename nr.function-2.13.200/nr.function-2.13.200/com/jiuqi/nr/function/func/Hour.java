/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.DataBuffer
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.DataBuffer;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import java.util.Calendar;
import java.util.List;

public class Hour
extends Function
implements IReportFunction {
    private static final long serialVersionUID = -3015817698544640822L;

    public Hour() {
        this.parameters().add(new Parameter("date", 2, "\u65e5\u671f"));
    }

    public String name() {
        return "Hour";
    }

    public String title() {
        return "\u8fd4\u56de\u65f6\u95f4\u503c\u4e2d\u5c0f\u65f6\u7684\u6570\u503c\uff0c\u4ecb\u4e8e0\u523023\u4e4b\u95f4\u7684\u6574\u6570";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 3;
    }

    public String category() {
        return "\u65e5\u671f\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object obj = parameters.get(0).evaluate(context);
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof Calendar)) {
            throw new SyntaxException("Hour()\u51fd\u6570\u7684\u7b2c\u4e00\u4e2a\u53c2\u6570\u5fc5\u987b\u4e3a\u65e5\u671f\u7c7b\u578b");
        }
        return DataBuffer.valueOf((int)((Calendar)obj).get(11));
    }
}

