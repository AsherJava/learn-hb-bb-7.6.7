/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.lib.reg.service;

public interface RoRegisterService {
    public String getRegisterCode(String var1, String var2, String var3, String var4, String var5);

    public String checkRegisterCode(String var1, String var2, String var3, String var4);

    public String getMachineCodeValue();

    public String getVersion();

    public String checkDateValid();

    public String decryptCode(String var1);
}

