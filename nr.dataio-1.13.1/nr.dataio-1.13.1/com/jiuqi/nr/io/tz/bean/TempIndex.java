/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.tz.bean;

import java.util.List;

public class TempIndex {
    List<String> filedNames;
    private boolean unique;

    public List<String> getFiledNames() {
        return this.filedNames;
    }

    public void setFiledNames(List<String> filedNames) {
        this.filedNames = filedNames;
    }

    public boolean isUnique() {
        return this.unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }
}

