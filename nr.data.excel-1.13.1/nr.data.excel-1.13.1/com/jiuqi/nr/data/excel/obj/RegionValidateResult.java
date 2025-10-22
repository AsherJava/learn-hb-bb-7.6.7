/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.obj;

public class RegionValidateResult {
    public static final int DATA_TYPE_NORMAL = 0;
    public static final int DATA_TYPE_EMPTY = 1;
    public static final int DATA_TYPE_ZERO = 2;
    public static final int DATA_TYPE_EMPTY_ZERO = 3;
    private boolean pass;
    private int type;

    public RegionValidateResult(boolean pass, int type) {
        this.pass = pass;
        this.type = type;
    }

    public boolean isPass() {
        return this.pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

