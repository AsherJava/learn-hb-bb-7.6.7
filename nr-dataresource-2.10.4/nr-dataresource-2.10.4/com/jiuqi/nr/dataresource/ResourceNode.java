/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataresource;

import org.springframework.lang.NonNull;

public class ResourceNode<E> {
    private final String key;
    private final int type;
    private E other;

    public ResourceNode(String key, int type) {
        this.key = key;
        this.type = type;
    }

    public ResourceNode(@NonNull String key, int type, @NonNull ResourceNode<E> parent) {
        this.key = key;
        this.type = type;
        this.other = parent.other;
    }

    public String getKey() {
        return this.key;
    }

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
        return "ResourceNode{key='" + this.key + '\'' + ", type=" + this.type + ", other=" + this.other + '}';
    }
}

