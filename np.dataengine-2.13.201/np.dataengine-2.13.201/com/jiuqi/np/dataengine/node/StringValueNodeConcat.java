/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.np.dataengine.node;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.List;

public class StringValueNodeConcat
extends ASTNode {
    private List<ASTNode> concatNodes;
    private String separator = " ";

    public StringValueNodeConcat(List<ASTNode> concatNodes) {
        super(null);
        this.setConcatNodes(concatNodes);
    }

    public IASTNode getChild(int index) {
        return (IASTNode)this.concatNodes.get(index);
    }

    public int childrenSize() {
        return this.concatNodes.size();
    }

    public ASTNodeType getNodeType() {
        return ASTNodeType.DYNAMICDATA;
    }

    public int getType(IContext context) throws SyntaxException {
        return 6;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        StringBuilder resultBuilder = new StringBuilder();
        if (this.separator == null) {
            this.separator = " ";
        }
        int c = this.concatNodes.size();
        for (int index = 0; index < c; ++index) {
            ASTNode node = this.concatNodes.get(index);
            int oldLen = resultBuilder.length();
            Object dataValue = node.evaluate(context);
            if (dataValue == null) continue;
            resultBuilder.append(dataValue);
            if (index >= c - 1 || oldLen >= resultBuilder.length()) continue;
            resultBuilder.append(this.separator);
        }
        return resultBuilder.toString();
    }

    public boolean isStatic(IContext context) {
        return false;
    }

    public void toString(StringBuilder buffer) {
    }

    public List<ASTNode> getConcatNodes() {
        return this.concatNodes;
    }

    public void setConcatNodes(List<ASTNode> concatNodes) {
        this.concatNodes = concatNodes;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }
}

