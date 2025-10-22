/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.common;

import java.io.Serializable;

public class SpecialValue
implements Serializable {
    private static final long serialVersionUID = -4507546385606130446L;
    private String strValue;

    public SpecialValue(String value) {
        this.strValue = value;
    }

    public final int compareTo(Object another) {
        if (another instanceof SpecialValue) {
            return this.strValue.compareTo(((SpecialValue)another).strValue);
        }
        return another != null ? this.getClass().hashCode() - another.getClass().hashCode() : 1;
    }

    public boolean equals(Object another) {
        return another instanceof SpecialValue && this.strValue.compareTo(((SpecialValue)another).strValue) == 0;
    }

    public int hashCode() {
        return this.strValue.hashCode();
    }

    public String toString() {
        return this.strValue;
    }
}

