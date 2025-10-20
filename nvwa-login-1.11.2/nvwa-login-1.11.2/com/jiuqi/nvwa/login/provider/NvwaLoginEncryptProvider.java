/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.login.provider;

public interface NvwaLoginEncryptProvider {
    public String getType();

    default public String getAlias() {
        return this.getType();
    }

    public String encrypt(String var1);

    public String decrypt(String var1);
}

