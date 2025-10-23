/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.dto;

public class FormulaPathNode {
    private String key;
    private String parentKey;
    private String title;

    public static FormulaPathNode createBJNode() {
        FormulaPathNode formulaPathNode = new FormulaPathNode();
        formulaPathNode.setKey("BJ");
        formulaPathNode.setParentKey("BJ");
        formulaPathNode.setTitle("\u8868\u95f4\u516c\u5f0f");
        return formulaPathNode;
    }

    public FormulaPathNode() {
    }

    public FormulaPathNode(String key, String parentKey, String title) {
        this.key = key;
        this.parentKey = parentKey;
        this.title = title;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

