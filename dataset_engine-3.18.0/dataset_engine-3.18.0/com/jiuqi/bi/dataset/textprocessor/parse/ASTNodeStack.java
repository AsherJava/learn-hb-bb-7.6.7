/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.textprocessor.parse;

import java.util.List;
import java.util.Stack;

public class ASTNodeStack {
    public static final String TYPE_FUNCTION = "FUNCTION";
    public static final String TYPE_RESTRICT = "RESTRICT";
    private Stack<NodeInfo> stack = new Stack();

    public void pushFunction(String funcName) {
        NodeInfo info = new NodeInfo(funcName);
        info.type = TYPE_FUNCTION;
        this.stack.push(info);
    }

    public void pushRestrict(List<String> restrictName) {
        NodeInfo info = new NodeInfo(restrictName);
        info.type = TYPE_RESTRICT;
        this.stack.push(info);
    }

    public void pop() {
        this.stack.pop();
    }

    public int getCurCursor() {
        if (this.stack.isEmpty()) {
            return -1;
        }
        return this.stack.peek().cursor;
    }

    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    public String getCurNodeType() {
        if (this.stack.isEmpty()) {
            return null;
        }
        return this.stack.peek().type;
    }

    public Object getCurNodeData() {
        if (this.stack.isEmpty()) {
            return null;
        }
        return this.stack.peek().data;
    }

    public void nextCursor() {
        ++this.stack.peek().cursor;
    }

    class NodeInfo {
        public Object data;
        public String type;
        public int cursor;

        public NodeInfo(Object name) {
            this.data = name;
        }
    }
}

