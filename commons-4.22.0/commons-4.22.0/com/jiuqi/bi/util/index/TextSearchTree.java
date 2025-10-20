/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.index;

import com.jiuqi.bi.util.index.CharNode;
import com.jiuqi.bi.util.index.TextMatcher;
import java.util.ArrayList;
import java.util.List;

public class TextSearchTree {
    private CharNode root = new CharNode();
    private CharNode tmpNode = new CharNode();

    public boolean isEmpty() {
        return this.root.getChildren().isEmpty();
    }

    public void add(String word) {
        if (word == null || word.length() == 0) {
            return;
        }
        this.root.add(word, 0);
    }

    public boolean remove(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }
        return this.root.remove(word, 0) != null;
    }

    public boolean match(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }
        CharNode end = this.root.search(word, 0, this.tmpNode);
        return end != null && end.isEnd();
    }

    public TextMatcher matcher() {
        return new TextMatcher(this.root, new CharNode());
    }

    public List<String> getAllWords() {
        ArrayList<String> words = new ArrayList<String>();
        this.getWords(this.root, words);
        return words;
    }

    private void getWords(CharNode node, List<String> words) {
        if (node.isEnd()) {
            words.add(node.toText());
        }
        for (CharNode subNode : node.getChildren()) {
            this.getWords(subNode, words);
        }
    }

    public String toString() {
        return this.getAllWords().toString();
    }
}

