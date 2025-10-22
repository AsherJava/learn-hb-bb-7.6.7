/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.np.dataengine.parse.link;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.ArrayList;
import java.util.List;

class ParserAssistant {
    ParserAssistant() {
    }

    static class IntermNode
    extends ASTNode
    implements IASTNode {
        private static final long serialVersionUID = 1833668249401109310L;
        private List<IASTNode> parameters;

        public IntermNode(Token token, List<IASTNode> params) {
            super(token);
            this.parameters = params == null ? new ArrayList<IASTNode>() : new ArrayList<IASTNode>(params);
        }

        public ASTNodeType getNodeType() {
            return ASTNodeType.DYNAMICDATA;
        }

        public int getType(IContext context) throws SyntaxException {
            throw new SyntaxException("\u65e0\u6cd5\u786e\u5b9a\u8282\u70b9\u7684\u6570\u636e\u7c7b\u578b");
        }

        public Object evaluate(IContext context) throws SyntaxException {
            throw new SyntaxException("\u65e0\u6cd5\u6267\u884c\u8282\u70b9\u8fd0\u7b97");
        }

        public int childrenSize() {
            return this.parameters.size();
        }

        public IASTNode getChild(int index) {
            return this.parameters.get(index);
        }

        public void setChild(int index, IASTNode node) {
            if (index == this.parameters.size()) {
                this.parameters.add(node);
            } else {
                this.parameters.set(index, node);
            }
        }

        public void addChild(IASTNode node) {
            this.parameters.add(node);
        }

        public boolean isStatic(IContext context) {
            return false;
        }

        public void toString(StringBuilder buffer) {
            buffer.append('[');
            for (int i = 0; i < this.childrenSize(); ++i) {
                buffer.append(',');
                if (this.getChild(i) == null) continue;
                this.getChild(i).toString(buffer);
            }
            buffer.append(']');
        }
    }

    public static class URegionNode
    extends IntermNode {
        private static final long serialVersionUID = 8160847071546376461L;
        private String start;
        private String end;

        public URegionNode(Token token, String start, String end) {
            super(token, null);
            this.start = start;
            this.end = end;
        }

        public URegionNode(Token token, String text) {
            super(token, null);
            String[] items = text.split(":");
            this.start = items[0];
            this.end = items[1];
        }

        public String getStart() {
            return this.start;
        }

        public String getEnd() {
            return this.end;
        }
    }

    public static class UCellNode
    extends IntermNode {
        private static final long serialVersionUID = -5477261452739015580L;
        private String cellExpr;

        public UCellNode(Token token, String cellExpr) {
            super(token, null);
            this.cellExpr = cellExpr;
        }

        public String getCellExpr() {
            return this.cellExpr;
        }

        @Override
        public Object evaluate(IContext context) throws SyntaxException {
            return this.cellExpr;
        }
    }

    public static class SquareBracketNode
    extends IntermNode {
        private static final long serialVersionUID = 8649349141930832537L;

        public SquareBracketNode(Token token, List<IASTNode> params) {
            super(token, params);
        }

        public Token getWholeToken() {
            StringBuilder buffer = new StringBuilder();
            buffer.append('[');
            for (int i = 0; i < this.childrenSize(); ++i) {
                Token t;
                buffer.append(',');
                if (this.getChild(i) == null || (t = this.getChild(i).getToken()) == null) continue;
                buffer.append(t.text());
            }
            buffer.append(']');
            return new Token(buffer.toString(), this.token.line(), this.token.column(), this.token.index());
        }
    }
}

