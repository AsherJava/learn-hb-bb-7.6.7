/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.index;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CharNode
implements Comparable<CharNode> {
    private char value;
    private boolean end;
    private CharNode parent;
    private List<CharNode> children;

    public CharNode() {
        this('\u0000', null);
    }

    public CharNode(char value, CharNode parent) {
        this.value = value;
        this.parent = parent;
        this.children = new ArrayList<CharNode>();
    }

    public char getValue() {
        return this.value;
    }

    void setValue(char value) {
        this.value = value;
    }

    public boolean isEnd() {
        return this.end;
    }

    void setEnd(boolean isEnd) {
        this.end = isEnd;
    }

    public CharNode getParent() {
        return this.parent;
    }

    public List<CharNode> getChildren() {
        return this.children;
    }

    public String toString() {
        return this.value == '\u0000' ? "#0" : Character.toString(this.value);
    }

    public void toText(StringBuilder buffer) {
        if (this.value == '\u0000') {
            return;
        }
        if (this.parent != null) {
            this.parent.toText(buffer);
        }
        buffer.append(this.value);
    }

    public String toText() {
        StringBuilder buffer = new StringBuilder();
        this.toText(buffer);
        return buffer.toString();
    }

    @Override
    public int compareTo(CharNode o) {
        return this.value - o.value;
    }

    public int hashCode() {
        return this.value;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof CharNode) {
            return this.value == ((CharNode)obj).value;
        }
        if (obj instanceof Character) {
            return this.value == ((Character)obj).charValue();
        }
        return false;
    }

    public CharNode add(String word, int fromIndex) {
        CharNode next = new CharNode(word.charAt(fromIndex), this);
        int p = Collections.binarySearch(this.children, next);
        if (p >= 0) {
            next = this.children.get(p);
        } else {
            int index = -p - 1;
            this.children.add(index, next);
        }
        if (fromIndex >= word.length() - 1) {
            next.setEnd(true);
            return next;
        }
        return next.add(word, fromIndex + 1);
    }

    public CharNode remove(String word, int fromIndex) {
        CharNode parent;
        int p;
        CharNode endNode = this.search(word, fromIndex);
        if (endNode == null || !endNode.isEnd()) {
            return null;
        }
        endNode.setEnd(false);
        CharNode node = endNode;
        while (node != this && node.getChildren().isEmpty() && (p = Collections.binarySearch((parent = node.getParent()).getChildren(), node)) >= 0) {
            parent.getChildren().remove(p);
            node.parent = null;
            node = parent;
        }
        return endNode;
    }

    CharNode search(String word, int fromIndex, CharNode charNode) {
        charNode.setValue(word.charAt(fromIndex));
        int p = Collections.binarySearch(this.children, charNode);
        if (p < 0) {
            return null;
        }
        CharNode next = this.children.get(p);
        if (fromIndex >= word.length() - 1) {
            return next;
        }
        return next.search(word, fromIndex + 1, charNode);
    }

    public CharNode search(String word, int fromIndex) {
        CharNode charNode = new CharNode();
        return this.search(word, fromIndex, charNode);
    }
}

