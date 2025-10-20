/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.storage;

public class ParameterResourceIdentify {
    private String name;
    private String ownerName;
    private String ownerType;

    public ParameterResourceIdentify(String name) {
        this.name = name;
    }

    public ParameterResourceIdentify(String name, String ownerName, String ownerType) {
        this.name = name;
        this.ownerName = ownerName;
        this.ownerType = ownerType;
    }

    public String getName() {
        return this.name;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public String getOwnerType() {
        return this.ownerType;
    }
}

