/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.lib.reg;

public interface SingleRegister {
    public String getLongToHex(int var1);

    public int getHexToLong(String var1);

    public String getStringToMD5(String var1);

    public int getRegisterCode(String var1, String var2, String var3, String var4);

    public int checkRegisterCode(String var1, String var2, String var3, String var4, String var5);

    public int getOriginMachine();

    public int getMachineValue(int var1);

    public int getMachineValue(String var1);
}

