/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.label;

import com.jiuqi.nr.graph.label.AbstractLabel;

public final class EdgeLabel
extends AbstractLabel {
    private static final long serialVersionUID = 4868281355561594921L;
    private int outIndex;
    private int inIndex;

    protected EdgeLabel(int outIndex, int inIndex, String name) {
        super(name);
        this.outIndex = outIndex;
        this.inIndex = inIndex;
    }

    public int getOutIndex() {
        return this.outIndex;
    }

    public int getInIndex() {
        return this.inIndex;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = super.hashCode();
        result = 31 * result + this.inIndex;
        result = 31 * result + this.outIndex;
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
        EdgeLabel other = (EdgeLabel)obj;
        return this.inIndex == other.inIndex && this.outIndex == other.outIndex;
    }
}

