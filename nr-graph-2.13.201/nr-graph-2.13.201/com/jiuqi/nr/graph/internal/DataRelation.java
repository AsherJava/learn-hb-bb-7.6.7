/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.internal;

import com.jiuqi.nr.graph.IDataRelation;
import com.jiuqi.nr.graph.label.EdgeLabel;
import java.util.Objects;

public class DataRelation
implements IDataRelation {
    private final EdgeLabel label;
    private final String outKey;
    private final String inKey;

    public DataRelation(EdgeLabel label, String outKey, String inKey) {
        this.label = label;
        this.outKey = outKey;
        this.inKey = inKey;
    }

    @Override
    public EdgeLabel getLabel() {
        return this.label;
    }

    @Override
    public String getOutKey() {
        return this.outKey;
    }

    @Override
    public String getInKey() {
        return this.inKey;
    }

    public int hashCode() {
        return Objects.hash(this.inKey, this.label, this.outKey);
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
        DataRelation other = (DataRelation)obj;
        return Objects.equals(this.inKey, other.inKey) && Objects.equals(this.label, other.label) && Objects.equals(this.outKey, other.outKey);
    }

    public String toString() {
        return "DataRelation [label=" + this.label + ", outKey=" + this.outKey + ", inKey=" + this.inKey + "]";
    }
}

