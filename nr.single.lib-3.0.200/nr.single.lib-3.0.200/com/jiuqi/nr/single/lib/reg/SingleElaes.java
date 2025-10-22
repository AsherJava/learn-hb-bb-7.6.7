/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.lib.reg;

public interface SingleElaes {
    public void encryptAESFile(String var1, String var2, String var3);

    public void decryptAESFile(String var1, String var2, String var3);

    public String encryptAESString(String var1, String var2, boolean var3);

    public String decryptAESString(String var1, int var2, String var3, boolean var4);

    public String decryptAESString(byte[] var1, String var2, boolean var3);

    public byte[] decryptAESStringToBytes(byte[] var1, String var2, boolean var3);

    public String decryptAESStringFromFile(String var1, String var2, boolean var3);

    public void encryptAESFileEx(String var1, String var2);

    public void decryptAESFileEx(String var1, String var2);

    public String encryptToBase64(String var1, String var2);

    public String decryptFromBase64(String var1, String var2);
}

