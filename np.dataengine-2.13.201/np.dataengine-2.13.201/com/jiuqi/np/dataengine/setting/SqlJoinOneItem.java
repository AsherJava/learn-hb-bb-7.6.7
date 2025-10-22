/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.setting;

public class SqlJoinOneItem {
    private String srcField;
    private String desField;

    public SqlJoinOneItem(String srcField, String desField) {
        this.srcField = srcField;
        this.desField = desField;
    }

    public String getSrcField() {
        return this.srcField;
    }

    public String getDesField() {
        return this.desField;
    }
}

