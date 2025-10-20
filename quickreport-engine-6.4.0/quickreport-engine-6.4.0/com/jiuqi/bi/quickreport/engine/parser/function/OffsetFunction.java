/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.parser.function;

import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFieldNode;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.List;

public abstract class OffsetFunction
extends Function {
    private static final long serialVersionUID = 1L;

    public OffsetFunction() {
        this.parameters().add(new Parameter("dimField", 0, "\u504f\u79fb\u5b57\u6bb5"));
        this.parameters().add(new Parameter("offset", 3, "\u504f\u79fb\u91cf"));
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return parameters == null || parameters.isEmpty() ? 0 : parameters.get(0).getType(context);
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (!(parameters.get(0) instanceof DSFieldNode)) {
            throw new SyntaxException(parameters.get(0).getToken(), "\u53c2\u6570\u9519\u8bef\uff0c\u504f\u79fb\u64cd\u4f5c\u5fc5\u987b\u57fa\u4e8e\u5b57\u6bb5\u8fdb\u884c\u3002");
        }
        DSFieldNode fieldNode = (DSFieldNode)parameters.get(0);
        if (fieldNode.getField().getFieldType() != FieldType.TIME_DIM) {
            throw new SyntaxException(fieldNode.getToken(), "\u53c2\u6570\u9519\u8bef\uff0c\u504f\u79fb\u64cd\u4f5c\u4ec5\u652f\u6301\u65f6\u95f4\u7ef4\u5ea6\u5b57\u6bb5\u3002");
        }
        if (!fieldNode.getRestrictions().isEmpty()) {
            throw new SyntaxException(fieldNode.getToken(), "\u53c2\u6570\u9519\u8bef\uff0c\u504f\u79fb\u5b57\u6bb5\u4e0d\u5141\u8bb8\u518d\u8fdb\u884c\u9650\u5b9a\u64cd\u4f5c\u3002");
        }
        IASTNode offsetNode = parameters.get(1);
        if (!offsetNode.isStatic(context) || offsetNode.getType(context) != 3) {
            throw new SyntaxException(offsetNode.getToken(), "\u53c2\u6570\u9519\u8bef\uff0c\u6ca1\u6709\u6307\u5b9a\u6709\u6548\u7684\u504f\u79fb\u91cf\u3002");
        }
        return fieldNode.getType(context);
    }

    public String category() {
        return "\u504f\u79fb\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        throw new SyntaxException(this.name() + "()\u65e0\u6cd5\u8fdb\u884c\u72ec\u7acb\u8ba1\u7b97\u3002");
    }

    protected void toSQL(IContext context, List<IASTNode> parameters, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        throw new InterpretException(this.name() + "()\u4e0d\u652f\u6301\u4f18\u5316\u4e3aSQL\u6267\u884c\u3002");
    }
}

