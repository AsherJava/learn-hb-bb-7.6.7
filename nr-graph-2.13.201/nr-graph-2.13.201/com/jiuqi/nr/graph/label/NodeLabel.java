/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.label;

import com.jiuqi.nr.graph.label.AbstractLabel;

public final class NodeLabel
extends AbstractLabel {
    private static final long serialVersionUID = -2170142525315904758L;
    private int index;

    protected NodeLabel(int index, String name) {
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
        NodeLabel other = (NodeLabel)obj;
        return this.index == other.index;
    }
}

