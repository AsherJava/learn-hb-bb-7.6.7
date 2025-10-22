/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.data.engine.summary.parse.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import java.util.List;

public class LevelPrefixFunction
extends Function {
    private static final long serialVersionUID = -4562771098865625223L;

    public LevelPrefixFunction() {
        this.parameters().add(new Parameter("codeLevels", 0, "\u4ee3\u7801\u7ea7\u6b21\u7ed3\u6784,\u6bcf\u7ea7\u4e4b\u95f4\u7528\u5206\u53f7\u9694\u5f00"));
    }

    public String name() {
        return "LevelPrefix";
    }

    public String title() {
        return "\u6309\u4ee3\u7801\u7ea7\u6b21\u7ed3\u6784\u622a\u53d6\u5f53\u524d\u8282\u70b9\u7684\u7ea7\u6b21\u524d\u7f00";
    }

    public int getResultType(IContext paramIContext, List<IASTNode> paramList) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u903b\u8f91\u51fd\u6570";
    }

    public Object evalute(IContext paramIContext, List<IASTNode> paramList) throws SyntaxException {
        return null;
    }

    protected void toFormula(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        try {
            QueryContext qContext = (QueryContext)context;
            String codeLevelStr = parameters.get(0).evaluate(context).toString();
            String[] codeLevels = codeLevelStr.split(",|;");
            IEntityRow row = (IEntityRow)qContext.getCache().get("ROW");
            String prefix = "";
            String code = row.getEntityKeyData();
            String[] parents = row.getParentsEntityKeyDataPath();
            if (parents != null && codeLevels.length >= parents.length) {
                String levelStr = codeLevels[parents.length];
                int levelLength = Integer.valueOf(levelStr);
                if (levelLength > code.length()) {
                    levelLength = code.length();
                }
                prefix = code.substring(0, levelLength);
            }
            buffer.append("\"").append(prefix).append("\"");
        }
        catch (SyntaxException e) {
            throw new InterpretException(e.getMessage(), (Throwable)e);
        }
    }
}

