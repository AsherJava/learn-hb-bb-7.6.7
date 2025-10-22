/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.impl.nested;

public class LinkNode {
    private String value;
    private LinkNode next;

    public LinkNode() {
    }

    public LinkNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public LinkNode getNext() {
        return this.next;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setNext(LinkNode next) {
        this.next = next;
    }
}

