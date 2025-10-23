/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.core;

import com.jiuqi.nr.datascheme.api.core.ISchemeNode;
import org.springframework.lang.NonNull;

public class SchemeNode<E>
implements ISchemeNode {
    private final String key;
    private final int type;
    private E other;
    private Object data;

    @Override
    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public SchemeNode(String key, int type) {
        this.key = key;
        this.type = type;
    }

    public SchemeNode(@NonNull String key, int type, @NonNull SchemeNode<E> parent) {
        this.key = key;
        this.type = type;
        this.other = parent.other;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public int getType() {
        return this.type;
    }

    public E getOther() {
        return this.other;
    }

    public void setOther(E other) {
        this.other = other;
    }

    public String toString() {
        return "SchemeNode{key='" + this.key + '\'' + ", type=" + this.type + ", other=" + this.other + '}';
    }
}

