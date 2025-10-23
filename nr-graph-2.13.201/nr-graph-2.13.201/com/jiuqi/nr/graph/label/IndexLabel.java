/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.label;

import com.jiuqi.nr.graph.label.AbstractLabel;

public final class IndexLabel
extends AbstractLabel {
    private static final long serialVersionUID = 4923351774330487269L;
    private int index;

    protected IndexLabel(int index, String name) {
        super(name);
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = super.hashCode();
        result = 31 * result + this.index;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        IndexLabel other = (IndexLabel)obj;
        return this.index == other.index;
    }
}

