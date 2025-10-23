/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.label;

import com.jiuqi.nr.graph.label.ILabel;
import java.io.Serializable;

public abstract class AbstractLabel
implements ILabel,
Serializable {
    protected String name;

    protected AbstractLabel(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        AbstractLabel other = (AbstractLabel)obj;
        return !(this.name == null ? other.name != null : !this.name.equals(other.name));
    }

    public String toString() {
        return "AbstractLabel [name=" + this.name + "]";
    }
}

