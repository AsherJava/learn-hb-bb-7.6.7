/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.model.soulution;

import java.io.Serializable;

public class DimensionData
implements Serializable {
    private String name;
    private String values;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValues() {
        return this.values;
    }

    public void setValues(String values) {
        this.values = values;
    }
}

