/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.parser.function;

import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataNode;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.parser.function.Q_ExpandingFunction;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.List;

public final class Q_IsLastLevel
extends Q_ExpandingFunction {
    private static final long serialVersionUID = 1L;

    public Q_IsLastLevel() {
        super("levelItem", "\u8981\u5224\u65ad\u5c42\u7ea7\u7684\u7ef4\u5ea6\u5b57\u6bb5\u6216\u4e3b\u63a7\u5355\u5143\u683c");
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        AxisDataNode levelData = this.findMasterData((ReportContext)context);
        if (levelData == null) {
            throw new SyntaxException("\u7a0b\u5e8f\u5904\u7406\u9519\u8bef\uff0c\u65e0\u6cd5\u8ba1\u7b97\u5c42\u7ea7\u4fe1\u606f");
        }
        return levelData.getChildrenSize() <= 0;
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 1;
    }

    public String name() {
        return "Q_IsLastLevel";
    }

    public String title() {
        return "\u5224\u65ad\u5c42\u7ea7\u7ef4\u5ea6\u6216\u4e3b\u63a7\u5355\u5143\u683c\u5bf9\u5e94\u7684\u5c42\u7ea7\u662f\u5426\u4e3a\u672b\u7ea7";
    }
}

