/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.function;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.expression.DSFieldNode;
import com.jiuqi.bi.dataset.function.DSFunction;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.List;

public class Top
extends DSFunction {
    private static final long serialVersionUID = 1198242646090840942L;

    public Top() {
        this.parameters().add(new Parameter("n", 3, "\u8bb0\u5f55\u6570"));
        this.parameters().add(new Parameter("field", 0, "\u6392\u5e8f\u5b57\u6bb5"));
        this.parameters().add(new Parameter("filterExpr", 1, "\u8fc7\u6ee4\u8868\u8fbe\u5f0f", true));
    }

    @Override
    public Object evalute(IContext context, List<IASTNode> paramNodes, BIDataSet filterDs) throws SyntaxException {
        int n = this.analysisN(context, paramNodes.get(0));
        DSFieldNode fieldNode = (DSFieldNode)paramNodes.get(1);
        try {
            return ((BIDataSetImpl)filterDs).topN(n, fieldNode.getName());
        }
        catch (BIDataSetException e) {
            throw new SyntaxException(e.getMessage(), (Throwable)e);
        }
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        IASTNode node = parameters.get(1);
        if (!(node instanceof DSFieldNode)) {
            throw new SyntaxException("\u51fd\u6570\u53c2\u6570\u9519\u8bef\uff0c\u8282\u70b9\u3010" + node.interpret(context, Language.FORMULA, null) + "\u3011\u4e0d\u662f\u4e00\u4e2a\u6570\u636e\u96c6\u5b57\u6bb5\u8282\u70b9");
        }
        DSFieldNode dsNode = (DSFieldNode)node;
        BIDataSetFieldInfo info = dsNode.getFieldInfo();
        if (info.getFieldType() != FieldType.MEASURE) {
            throw new SyntaxException("\u5b57\u6bb5" + info.getName() + "\u4e0d\u662f\u4e00\u4e2a\u5ea6\u91cf\u5b57\u6bb5");
        }
        return super.validate(context, parameters);
    }

    private int analysisN(IContext context, IASTNode node) throws SyntaxException {
        Double value = (Double)node.evaluate(context);
        if (value != (double)value.intValue()) {
            throw new SyntaxException("DS_TOP\u51fd\u6570\u4e2d\u7684\u8bb0\u5f55\u6570\u5fc5\u987b\u4e3a\u6574\u6570");
        }
        if (value < 0.0) {
            throw new SyntaxException("DS_TOP\u51fd\u6570\u4e2d\u7684\u8bb0\u5f55\u6570\u5fc5\u987b\u5927\u4e8e0");
        }
        return value.intValue();
    }

    public String name() {
        return "DS_TOP";
    }

    public String title() {
        return "\u5c06\u8fc7\u6ee4\u8868\u8fbe\u5f0f\u8fc7\u6ee4\u540e\u7684\u6570\u636e\u96c6\u6309\u7167\u7ed9\u5b9a\u5b57\u6bb5\u8fdb\u884c\u6392\u5e8f\uff0c\u83b7\u53d6\u6392\u5e8f\u540e\u6700\u5927\u7684\u524dn\u6761\u8bb0\u5f55";
    }

    public int getResultType(IContext context, List<IASTNode> paramNodes) throws SyntaxException {
        return 5100;
    }
}

