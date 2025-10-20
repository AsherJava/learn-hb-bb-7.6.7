/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.nodekeeper;

public interface IApplicationDetailProvider {
    public String getName();

    default public String getMachineCode() {
        return "";
    }
}

