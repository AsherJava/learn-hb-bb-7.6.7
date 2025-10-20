/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.index;

import com.jiuqi.bi.util.index.CharNode;

public final class TextMatcher {
    private CharNode current;
    private CharNode tmpNode;

    TextMatcher(CharNode rootNode, CharNode tmpNode) {
        this.current = rootNode;
        this.tmpNode = tmpNode;
    }

    public TextMatcher(CharNode curNode) {
        this(curNode, new CharNode());
    }

    public boolean match(String word) {
        this.current = this.current.search(word, 0, this.tmpNode);
        return this.current != null;
    }

    public boolean isMatched() {
        return this.current != null && this.current.isEnd();
    }

    public String getText() {
        if (this.current == null) {
            return null;
        }
        return this.current.toText();
    }

    public String toString() {
        return this.current == null || this.current.getValue() == '\u0000' ? "(NONE)" : this.current.toText();
    }
}

